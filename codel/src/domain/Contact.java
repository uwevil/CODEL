package domain;

import java.util.ArrayList;

public class Contact
{
	protected String firstName;
	protected String lastName;
	protected String email;
	protected long id;
	
	protected Address address;
	
	protected ArrayList<ContactGroup> books;
	protected ArrayList<PhoneNumber> phoneNumber;
	
	public Contact()
	{	
	}
	
	public Contact(String firstName, String lastName, String email)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public ArrayList<ContactGroup> getBooks()
	{
		return books;
	}
	
	public void setGroup(ContactGroup group)
	{
		this.books.add(group);
	}
	
	public ContactGroup getGroup(int index)
	{
		return this.books.get(index);
	}

	public void setBooks(ArrayList<ContactGroup> books)
	{
		this.books = books;
	}

	public ArrayList<PhoneNumber> getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(ArrayList<PhoneNumber> phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public Address getAddress()
	{
		return this.address;
	}
	
	public void setAddress(Address address)
	{
		this.address = address;
	}
	
	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getFirstName()
	{
		return this.firstName;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return this.lastName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public long getId()
	{
		return this.id;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	public String toString()
	{
		return   "ID         : " + this.id + "\n"
			   + "First name : " + this.firstName + "\n"
			   + "Last name  : " + this.lastName + "\n"
			   + "Email      : " + this.email + "\n"; 
	}
}

























