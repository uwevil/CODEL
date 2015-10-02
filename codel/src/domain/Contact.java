package domain;

import java.util.HashSet;
import java.util.Set;

public class Contact
{
	protected String firstName;
	protected String lastName;
	protected String email;
	protected long id;
	
	protected Address address;
	
	protected Set<ContactGroup> books = new HashSet<ContactGroup>();
	protected Set<PhoneNumber> phoneNumbers = new HashSet<PhoneNumber>();
	
	public Contact()
	{	
	}
	
	public Contact(String firstName, String lastName, String email)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public Set<ContactGroup> getBooks()
	{
		return this.books;
	}

	public void setBooks(Set<ContactGroup> books)
	{
		this.books = books;
	}

	public Set<PhoneNumber> getPhoneNumbers()
	{
		return phoneNumbers;
	}

	public void setPhoneNumbers(Set<PhoneNumber> phoneNumbers)
	{
		this.phoneNumbers = phoneNumbers;
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
	
}

























