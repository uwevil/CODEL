package domain;


import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;

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
	
	public boolean addContact(Contact contact)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();	
		
		session.beginTransaction();

		String requestQuery = new String("from Contact where firstName = :f and lastName = :l");
			
		Object c = session.createQuery(requestQuery)
				.setString("f", contact.getFirstName())
				.setString("l", contact.getLastName())
				.uniqueResult();

		if (c != null)
		{
			session.close();	
			return false;
		}
		else
		{			
			session.persist(contact);
			
			@SuppressWarnings("unused")
			Contact contactCreated = (Contact) session.load(Contact.class, contact.getId());

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
		
		session.delete(c);
		
		session.getTransaction().commit();
		session.close();
		return true;
	}
	
	public boolean updateContact(Contact contact)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();	
		
		session.beginTransaction();

		String requestQuery = new String("from Contact where firstName = :f and lastName = :l");
			
		Object c = session.createQuery(requestQuery)
				.setString("f", contact.getFirstName())
				.setString("l", contact.getLastName())
				.uniqueResult();

		if (c != null)
		{
			c = session.merge(contact);
			session.saveOrUpdate(c);
		}
		else
		{
			session.persist(contact);
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
		Hibernate.initialize(((Contact)c).books);
		
		session.close();
		return c;
	}
}
