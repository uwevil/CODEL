package domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtil;

public class DAOContact {

	public DAOContact()
	{
	}
	
	public List<Object> welcome()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();	
		
		session.beginTransaction();

		String requestQuery = new String("from Contact");
		
		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>) session.createQuery(requestQuery).list();
		
		if (list == null)
		{
			session.close();
			return null;
		}
		
		session.close();
		
		return list;
	}
	
	public boolean addContact(Object contact)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();	
		
		session.beginTransaction();

		String requestQuery = new String("from Contact where firstName = :f and lastName = :l");
			
		if (contact.getClass().getName().contains("Entreprise"))
		{
			Entreprise e = (Entreprise) contact;
			Object c = session.createQuery(requestQuery)
					.setString("f", e.getFirstName())
					.setString("l", e.getLastName())
					.uniqueResult();

			if (c != null)
			{
				session.close();	
				return false;
			}
			else
			{					
				Set<ContactGroup> books = e.getBooks();
				Set<ContactGroup> newBooks = new HashSet<>();
				
				Iterator<ContactGroup> iterator = books.iterator();
				
				while (iterator.hasNext())
				{
					ContactGroup group = iterator.next();
					requestQuery = new String("from ContactGroup where groupName = :g");
					
					System.out.println(group.getGroupName());
					
					ContactGroup groupExisted = (ContactGroup) session.createQuery(requestQuery)
							.setString("g", group.getGroupName())
							.uniqueResult();
										
					if (groupExisted != null)
					{
						newBooks.add(groupExisted);
						(groupExisted).getContacts().add(e);
					}
					else
					{
						newBooks.add(group);
						group.getContacts().add(e);
					}
				}
				
				e.setBooks(newBooks);
				
				session.persist(e);
				
				@SuppressWarnings("unused")
				Contact contactCreated = (Contact) session.load(Contact.class, e.getId());

			}

		}
		else
		{
			Contact e = (Contact) contact;

			Object c = session.createQuery(requestQuery)
					.setString("f", e.getFirstName())
					.setString("l", e.getLastName())
					.uniqueResult();

			if (c != null)
			{
				session.close();	
				return false;
			}
			else
			{					
				Set<ContactGroup> books = e.getBooks();
				Set<ContactGroup> newBooks = new HashSet<>();
				Iterator<ContactGroup> iterator = books.iterator();
				
				while (iterator.hasNext())
				{
					ContactGroup group = iterator.next();
					requestQuery = new String("from ContactGroup where groupName = :g");
										
					ContactGroup groupExisted = (ContactGroup) session.createQuery(requestQuery)
							.setString("g", group.getGroupName())
							.uniqueResult();
					
					if (groupExisted != null)
					{
						newBooks.add(groupExisted);
						(groupExisted).getContacts().add(e);
					}
					else
					{
						newBooks.add(group);
						group.getContacts().add(e);
					}
				}
				
				e.setBooks(newBooks);
				
				session.persist(e);
				
				@SuppressWarnings("unused")
				Contact contactCreated = (Contact) session.load(Contact.class, e.getId());

			}

		}
		session.getTransaction().commit();
		session.close();	
		return true;
	}
	
	public boolean deleteContact(Contact contact)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();	
		
		session.beginTransaction();

		String requestQuery = new String("from Contact where firstName = '" + contact.getFirstName() 
						+ "' and lastName = '" + contact.getLastName() + "'"
						);
		
		Object c = session.createQuery(requestQuery).uniqueResult();
		
		if (c == null)
		{
			session.close();
			return false;
		}
				
		Set<PhoneNumber> numbers = ((Contact)c).getPhoneNumbers();
		
		for (Iterator<PhoneNumber> iterator = numbers.iterator(); iterator.hasNext();)
			session.delete(iterator.next());		
				
		((Contact)c).setPhoneNumbers(null);
		
		Set<ContactGroup> groups = ((Contact)c).getBooks();
				
		for (Iterator<ContactGroup> iterator = groups.iterator(); iterator.hasNext();)
		{
			ContactGroup g = iterator.next();
			g.getContacts().remove(c);
			
			if (g.getContacts().size() < 1)
				session.delete(g);
		}
			
		((Contact)c).getBooks().clear();

		
		session.delete(c);
		session.delete(((Contact)c).getAddress());

		session.getTransaction().commit();
		session.close();
		return true;
	}
	
	public boolean updateContact(Object contact, long id)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();	
		
		session.beginTransaction();
			
		if (contact.getClass().getName().contains("Entreprise"))
		{
			Entreprise e = (Entreprise) session.get(Entreprise.class, id);
			
			Entreprise e_tmp = (Entreprise) contact;
			
			if ( e.getVersion() != e_tmp.getVersion() ) 
			{
				session.close();
				return false;
			}
			
			if (e != null)
			{
				Set<ContactGroup> contactGroups = e.getBooks();

				for (Iterator<ContactGroup> iterator = contactGroups.iterator(); iterator.hasNext();)
				{
					ContactGroup g = iterator.next();					
					g.getContacts().remove(e);	
					
					if (g.getContacts().size() < 1)
						session.delete(g);
				} 
				
				contactGroups.clear();
				
				for (Iterator<ContactGroup> iterator2 = e_tmp.getBooks().iterator(); 
						iterator2.hasNext();) { 					
					ContactGroup group = iterator2.next();
					
					String requestQuery = new String("from ContactGroup where groupName = :g");
										
					ContactGroup groupExisted = (ContactGroup) session.createQuery(requestQuery)
							.setString("g", group.getGroupName())
							.uniqueResult();
					
					if (groupExisted != null)
					{
						e.getBooks().add(groupExisted);
						(groupExisted).getContacts().add(e);
					}
					else
					{
						e.getBooks().add(group);
						group.getContacts().add(e);
						session.persist(group);
					}					
			    }
				
				e.setFirstName(e_tmp.getFirstName());
				e.setLastName(e_tmp.getLastName());
				
				e.setEmail(e_tmp.getEmail());
				e.getAddress().setStreet(e_tmp.getAddress().getStreet());
				e.getAddress().setZip(e_tmp.getAddress().getZip());
				e.getAddress().setCity(e_tmp.getAddress().getCity());
				e.getAddress().setCountry(e_tmp.getAddress().getCountry());

				for (Iterator<PhoneNumber> iterator = e.getPhoneNumbers().iterator(); 
						iterator.hasNext();) {
					PhoneNumber p = iterator.next();
					
					for (Iterator<PhoneNumber> iterator2 = e_tmp.getPhoneNumbers().iterator(); 
							iterator2.hasNext();)
					{
						PhoneNumber p2 = iterator2.next();

						if (p.getPhoneKind().equals(p2.getPhoneKind()))
						{
							p.setPhoneNumber(p2.getPhoneNumber());
						}
					}
				}
				
				session.flush();
			}
			
			session.saveOrUpdate(e);
		}
		else
		{
			Contact e = (Contact) session.get(Contact.class, id);
			
			Contact e_tmp = (Contact) contact;
			
			if ( e.getVersion() != e_tmp.getVersion() ) 
				throw new StaleObjectStateException("Contact", session);
			
			if (e != null)
			{
				Set<ContactGroup> contactGroups = e.getBooks();

				for (Iterator<ContactGroup> iterator = contactGroups.iterator(); iterator.hasNext();)
				{
					ContactGroup g = iterator.next();					
					g.getContacts().remove(e);	

					if (g.getContacts().size() < 1)
						session.delete(g);
				} 
				
				contactGroups.clear();
				
				for (Iterator<ContactGroup> iterator2 = e_tmp.getBooks().iterator(); 
						iterator2.hasNext();) { 					
					ContactGroup group = iterator2.next();
					
					String requestQuery = new String("from ContactGroup where groupName = :g");
										
					ContactGroup groupExisted = (ContactGroup) session.createQuery(requestQuery)
							.setString("g", group.getGroupName())
							.uniqueResult();
					
					if (groupExisted != null)
					{
						e.getBooks().add(groupExisted);
						(groupExisted).getContacts().add(e);
					}
					else
					{
						e.getBooks().add(group);
						group.getContacts().add(e);
						session.persist(group);
					}					
			    }
				
				e.setFirstName(e_tmp.getFirstName());
				e.setLastName(e_tmp.getLastName());
				
				e.setEmail(e_tmp.getEmail());
				e.getAddress().setStreet(e_tmp.getAddress().getStreet());
				e.getAddress().setZip(e_tmp.getAddress().getZip());
				e.getAddress().setCity(e_tmp.getAddress().getCity());
				e.getAddress().setCountry(e_tmp.getAddress().getCountry());				
				
				for (Iterator<PhoneNumber> iterator = e.getPhoneNumbers().iterator(); 
						iterator.hasNext();) {
					PhoneNumber p = iterator.next();
					
					for (Iterator<PhoneNumber> iterator2 = e_tmp.getPhoneNumbers().iterator(); 
							iterator2.hasNext();)
					{
						PhoneNumber p2 = iterator2.next();

			//			System.out.println("-" + p.getPhoneKind() + "-" + p2.getPhoneKind() + "-");

						
						if (p.getPhoneKind().equals(p2.getPhoneKind()))
						{
							p.setPhoneNumber(p2.getPhoneNumber());
						}
					}
				}
				
				session.flush();
			}
			
			session.saveOrUpdate(e);
		}
		
		session.getTransaction().commit();
		session.close();	
		return true;		
	}
	
	public Object searchContact(Contact contact)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();	
		
		session.beginTransaction();

		String requestQuery = new String("from Contact where firstName = '" + contact.getFirstName() 
						+ "' and lastName = '" + contact.getLastName() + "'"
						);
		
		Object c = session.createQuery(requestQuery).uniqueResult();
		
		if (c == null)
		{
			session.close();
			return null;
		}
				
		Hibernate.initialize(c);
		Hibernate.initialize(((Contact)c).address);
		Hibernate.initialize(((Contact)c).phoneNumbers);
		Hibernate.initialize(((Contact)c).phoneNumbers);		
		Hibernate.initialize(((Contact)c).books);
		
		session.close();
		return c;
	}
	
	public List<Object> testHQL2 (String requestQuery)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();	
		
		session.beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<Object> list = session.createQuery(requestQuery).list();
		
		if (list == null)
		{
			session.close();
			return null;
		}
		
		for (Iterator<Object> iterator = list.iterator(); iterator.hasNext();)
		{
			Object[] contacts = (Object[])(iterator.next());
							
			if (contacts.length > 0)
			{	
				for (int i = 0; i < contacts.length; i++)
				{
					Contact c_tmp = (Contact)contacts[i];
					c_tmp.getAddress().getStreet();
				}
			}
		}
		
		
		session.close();
		return list;
	}
	
	public List<Object> testHQL (String requestQuery)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();	
		
		session.beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<Object> list = session.createQuery(requestQuery).list();
		
		if (list == null)
		{
			session.close();
			return null;
		}
		
		
		session.close();
		return list;
	}
	
	public List<Object> testCriteria (String street, String zip, String city, String country)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();	
		
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Contact.class)
				.addOrder(Order.asc("id"))
				.createCriteria("address");
		
		if (street != null && street.length() >= 1)
			criteria.add(Restrictions.eq("street", street));
				
		if (zip != null && zip.length() >= 1)
			criteria.add(Restrictions.eq("zip", zip));
		
		if (city != null && city.length() >= 1)
			criteria.add(Restrictions.eq("city", city));
		
		if (country != null && country.length() >= 1)
			criteria.add(Restrictions.eq("country", country));
		
		criteria.setFetchMode("address", FetchMode.JOIN);
		
		@SuppressWarnings("unchecked")
		List<Object> list = criteria.list();
		
		if (list == null)
		{
			session.close();
			return null;
		}
		
		session.close();
		return list;
	}
	
	public List<Object> testCriteriaExample ()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();	
		
		session.beginTransaction();
		
		Contact c = new Contact();
		c.setEmail("");
		
		@SuppressWarnings("unchecked")
		List<Object> list = session.createCriteria(Contact.class)
			.add(Restrictions.eqProperty("street", "street"))
			.add(Example.create(c))
			.addOrder(Order.asc("id"))
			.list();
		
		if (list == null)
		{
			session.close();
			return null;
		}
		
		session.close();
		return list;
	}
	
}
