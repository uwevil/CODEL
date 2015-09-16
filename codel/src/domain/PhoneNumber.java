package domain;

public class PhoneNumber
{
	protected int id;
	protected String phoneKind;
	protected String phoneNumber;
	protected Contact contact;
	
	public PhoneNumber()
	{
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getPhoneKind()
	{
		return phoneKind;
	}

	public void setPhoneKind(String phoneKind)
	{
		this.phoneKind = phoneKind;
	}

	public String getPhoneNumber() 
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public Contact getContact()
	{
		return contact;
	}

	public void setContact(Contact contact)
	{
		this.contact = contact;
	}

	
	
}
