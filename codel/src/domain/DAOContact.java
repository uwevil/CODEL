package domain;

import org.hibernate.Session;

import util.HibernateUtil;

public class DAOContact {

	public DAOContact()
	{
	}
	
	public void addContact(Contact contact)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();	
		
		session.beginTransaction();
		
		session.persist(contact);
		
		@SuppressWarnings("unused")
		Contact contactCreated = (Contact) session.load(Contact.class, contact.getId());
		
		session.getTransaction().commit();
		session.close();
	}
	
	public void deleteContact(long id)
	{
		System.out.println("Delete in JDBC : " + id);
	}
	
	public void updateContact(long id, Contact contact)
	{
		Contact c = this.searchContact(id);
		
		if (c == null)
			this.addContact(contact);		
	}
	
	public Contact searchContact(long id)
	{
		System.out.println("Search in JDBC : " + id);
		return new Contact(null, null, null);
	}
}
