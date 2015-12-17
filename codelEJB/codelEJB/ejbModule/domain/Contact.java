package domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


@SuppressWarnings("serial")
@Entity
@Table(name="Contact")
public class Contact implements Serializable
{
	protected String firstName;
	protected String lastName;
	protected String email;
	protected long id;
	
	protected Address address;
	
//	protected Set<ContactGroup> books = new HashSet<ContactGroup>();
//	protected Set<PhoneNumber> phoneNumbers = new HashSet<PhoneNumber>();
	
	private long version;

	@Column(name="VERSION")
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
	
	public Contact()
	{	
	}
	
	public Contact(String firstName, String lastName, String email)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CONTACTID")
	public long getId()
	{
		return this.id;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	@Column(name="EMAIL")
	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
	
	@Column(name="FIRSTNAME")
	public String getFirstName()
	{
		return this.firstName;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	@Column(name="LASTNAME")
	public String getLastName()
	{
		return this.lastName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, fetch=FetchType.EAGER)
//	@JoinColumn(name="ADDRESSID", nullable=false)
	public Address getAddress()
	{
		return this.address;
	}
	
	public void setAddress(Address address)
	{
		this.address = address;
	}
	
	/*
	@OneToMany(mappedBy="contact")
	public Set<PhoneNumber> getPhoneNumbers()
	{
		return phoneNumbers;
	}

	public void setPhoneNumbers(Set<PhoneNumber> phoneNumbers)
	{
		this.phoneNumbers = phoneNumbers;
	}

	
/*	public Set<ContactGroup> getBooks()
	{
		return this.books;
	}

	public void setBooks(Set<ContactGroup> books)
	{
		this.books = books;
	}

	

*/	
}

























