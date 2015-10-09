package domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
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
					
					System.out.println("hereee");
					
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
			iterator.next().getContacts().remove(c);
				
		session.delete(c);
		session.delete(((Contact)c).getAddress());

		session.getTransaction().commit();
		session.close();
		return true;
	}
	
	public boolean updateContact(Contact contact)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();	
		
		session.beginTransaction();
			
		session.saveOrUpdate(contact);

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
		Hibernate.initialize(((Contact)c).books);
		
		session.close();
		return c;
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
