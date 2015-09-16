package domain;

public class DAOContact {

	public DAOContact()
	{
	}
	
	public void addContact(long id, String firstName, String lastName, String email)
	{
		Contact c = new Contact(firstName, lastName, email, id);
		System.out.println("Add to JDBC : " + c.toString());
	}
	
	public void deleteContact(long id)
	{
		System.out.println("Delete in JDBC : " + id);
	}
	
	public void updateContact(long id, String firstName, String lastName, String email)
	{
		Contact c = this.searchContact(id);
		
		if (c == null)
			this.addContact(id, firstName, lastName, email);
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
		return new Contact(null, null, null, id);
	}
}
