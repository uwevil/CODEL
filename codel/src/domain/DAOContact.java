package domain;

import org.hibernate.Session;

import util.HibernateUtil;

public class DAOContact {

	public DAOContact()
	{
	}
	
	public void addContact(String firstName, String lastName, String email)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();	
		
		session.beginTransaction();
		
		Contact contact = new Contact();
		contact.setFirstName(firstName);
		contact.setLastName(lastName);
		contact.setEmail(email);
		
		session.save(contact);
		
		@SuppressWarnings("unused")
		Contact contactCreated = (Contact) session.load(Contact.class, contact.getId());
		
		session.getTransaction().commit();
		session.close();		
	}
	
	public void deleteContact(long id)
	{
		System.out.println("Delete in JDBC : " + id);
	}
	
	public void updateContact(long id, String firstName, String lastName, String email)
	{
		Contact c = this.searchContact(id);
		
		if (c == null)
			this.addContact(firstName, lastName, email);
		else
		{
			if (firstName != null)
				c.setFirstName(firstName);
			if (lastName != null)
				c.setLastName(lastName);
			if (email != null)
			{
				if (email.matches("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)+$"))
				{			
					c.setEmail(email);
				}
			}
		}
		
		
		
		System.out.println("Update to JDBC : " + id);
	}
	
	public Contact searchContact(long id)
	{
		System.out.println("Search in JDBC : " + id);
		return new Contact(null, null, null);
	}
}
