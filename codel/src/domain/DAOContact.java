package domain;

import java.util.Iterator;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import util.HibernateUtil;

public class DAOContact {

	public DAOContact()
	{
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
			System.out.println(((Contact)c).getFirstName());
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
	
	public void deleteContact(long id)
	{
		System.out.println("Delete in JDBC : " + id);
	}
	
	public Object updateContact(Contact contact)
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
			
			session.update(contact);
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
		
		Hibernate.initialize(c);
		Hibernate.initialize(((Contact)c).phoneNumbers);
				
		((Contact)c).getBooks();
		
		return c;
	}
}
