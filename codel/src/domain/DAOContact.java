package domain;

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
	
	public void updateContact(long id, Contact contact)
	{
		Object c = this.searchContact(contact);
		
		if (c == null)
			this.addContact(contact);		
	}
	
	public Object searchContact(Contact contact)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();	
		
		session.beginTransaction();
		Hibernate.initialize(contact);

		String requestQuery = new String("from Contact where firstName = '" + contact.getFirstName() 
						+ "' and lastName = '" + contact.getLastName() + "'");
		
		Object c = session.createQuery(requestQuery).uniqueResult();
				
		session.close();
		
		return c;
	}
}
