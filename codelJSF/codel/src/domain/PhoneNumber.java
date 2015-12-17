package domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PhoneNumber implements Serializable
{
	protected long id;
	protected String phoneKind;
	protected String phoneNumber;
	
	protected Contact contact;
	
	private long version;

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public PhoneNumber()
	{
	}
	
	public PhoneNumber(String phoneKind, String phoneNumber)
	{
		this.phoneKind = phoneKind;
		this.phoneNumber = phoneNumber;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
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
