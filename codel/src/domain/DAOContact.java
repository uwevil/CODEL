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
		System.out.println("Update to JDBC : ");
	}
	
	public Contact searchContact(long id)
	{
		System.out.println("Search in JDBC : " + id);
		return new Contact(null, null, null, id);
	}
}
