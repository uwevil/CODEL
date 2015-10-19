package domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.*;

import util.HibernateUtil;

public class DAOContact extends HibernateDaoSupport{
		
	@SuppressWarnings("unused")
	private HibernateTemplate hibernateTemplate;
	
	public DAOContact()
	{}
	
	public void setHibernateTemplate(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		
	}
		
	public List<Object> welcome()
	{		
		String requestQuery = new String("from Contact");
		
		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>) getHibernateTemplate().find(requestQuery);
		
		if (list == null)
		{
			return null;
		}
				
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public boolean addContact(Object contact)
	{			
		if (contact.getClass().getName().contains("Entreprise"))
		{
			Entreprise e = (Entreprise) contact;
			
			String requestQuery = new String("from Contact "
					+ "where firstName = '" + e.getFirstName() + "'" 
					+ " and lastName = '" + e.getLastName() + "'");

			List<Object> list = (List<Object>) getHibernateTemplate().find(requestQuery);

			if (list != null && list.size() > 0)
			{
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
					requestQuery = new String("from ContactGroup where groupName = '" + group.getGroupName() + "'");
										
					List<ContactGroup> groupExisted = (List<ContactGroup>) getHibernateTemplate().find(requestQuery);
										
					if (groupExisted != null && groupExisted.size() > 0)
					{
						newBooks.add(groupExisted.get(0));
						groupExisted.get(0).getContacts().add(e);
					}
					else
					{
						newBooks.add(group);
						group.getContacts().add(e);
					}
				}
				
				e.setBooks(newBooks);
				
				getHibernateTemplate().persist(e);
			}
		}
		else
		{
			Contact e = (Contact) contact;

			String requestQuery = new String("from Contact "
					+ "where firstName = '" + e.getFirstName() + "'" 
					+ " and lastName = '" + e.getLastName() + "'");

			List<Object> list = (List<Object>) getHibernateTemplate().find(requestQuery);

			if (list != null && list.size() > 0)
			{
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
					requestQuery = new String("from ContactGroup where groupName = '" + group.getGroupName() + "'");
										
					List<ContactGroup> groupExisted = (List<ContactGroup>) getHibernateTemplate().find(requestQuery);
										
					if (groupExisted != null && groupExisted.size() > 0)
					{
						newBooks.add(groupExisted.get(0));
						groupExisted.get(0).getContacts().add(e);
					}
					else
					{
						newBooks.add(group);
						group.getContacts().add(e);
					}
				}
				
				e.setBooks(newBooks);
				
				getHibernateTemplate().persist(e);
			}

		}
			
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean deleteContact(Contact contact)
	{
		String requestQuery = new String("from Contact where firstName = '" + contact.getFirstName() 
						+ "' and lastName = '" + contact.getLastName() + "'"
						);
		
		List<Contact> list = (List<Contact>) getHibernateTemplate().find(requestQuery);
		
		if (list == null || list.size() == 0)
		{
			return false;
		}
		
		Contact c = list.get(0);
				
		Set<PhoneNumber> numbers = ((Contact)c).getPhoneNumbers();
		
		for (Iterator<PhoneNumber> iterator = numbers.iterator(); iterator.hasNext();)
			getHibernateTemplate().delete(iterator.next());		
				
		((Contact)c).setPhoneNumbers(null);
		
		Set<ContactGroup> groups = ((Contact)c).getBooks();
				
		for (Iterator<ContactGroup> iterator = groups.iterator(); iterator.hasNext();)
		{
			ContactGroup g = iterator.next();
			g.getContacts().remove(c);
			
			if (g.getContacts().size() < 1)
				getHibernateTemplate().delete(g);
		}
			
		((Contact)c).getBooks().clear();

		
		getHibernateTemplate().delete(c);
		getHibernateTemplate().delete(((Contact)c).getAddress());

		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean updateContact(Object contact, long id)
	{			
		if (contact.getClass().getName().contains("Entreprise"))
		{
			Entreprise e = (Entreprise) getHibernateTemplate().get(Entreprise.class, id);
			
			Entreprise e_tmp = (Entreprise) contact;
			
			if ( e.getVersion() != e_tmp.getVersion() ) 
			{
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
						getHibernateTemplate().delete(g);
				} 
				
				contactGroups.clear();
				
				for (Iterator<ContactGroup> iterator2 = e_tmp.getBooks().iterator(); 
						iterator2.hasNext();) { 					
					ContactGroup group = iterator2.next();
					
					String requestQuery = new String("from ContactGroup "
							+ "where groupName = '" + group.getGroupName() + "'");
								
					List<ContactGroup> list = (List<ContactGroup>) getHibernateTemplate().find(requestQuery);
										
					if (list != null && list.size() > 0)
					{
						ContactGroup groupExisted = list.get(0);

						e.getBooks().add(groupExisted);
						(groupExisted).getContacts().add(e);
					}
					else
					{
						e.getBooks().add(group);
						group.getContacts().add(e);
						getHibernateTemplate().persist(group);
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
				
				getHibernateTemplate().flush();
			}
			
			getHibernateTemplate().saveOrUpdate(e);
		}
		else
		{
			Contact e = (Contact) getHibernateTemplate().get(Contact.class, id);
						
			Contact e_tmp = (Contact) contact;
			
			if ( e.getVersion() != e_tmp.getVersion() ) 
			{
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
						getHibernateTemplate().delete(g);
				} 
				
				contactGroups.clear();
				
				for (Iterator<ContactGroup> iterator2 = e_tmp.getBooks().iterator(); 
						iterator2.hasNext();) { 					
					ContactGroup group = iterator2.next();
					
					String requestQuery = new String("from ContactGroup "
							+ "where groupName = '" + group.getGroupName() + "'");
								
					List<ContactGroup> list = (List<ContactGroup>) getHibernateTemplate().find(requestQuery);
										
					if (list != null && list.size() > 0)
					{
						ContactGroup groupExisted = list.get(0);

						e.getBooks().add(groupExisted);
						(groupExisted).getContacts().add(e);
					}
					else
					{
						e.getBooks().add(group);
						group.getContacts().add(e);
						getHibernateTemplate().persist(group);
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
				
				getHibernateTemplate().flush();
			}
			
			getHibernateTemplate().saveOrUpdate(e);
		}
		
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
