package domain;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="PhoneNumber")
public class PhoneNumber implements Serializable
{
	protected long id;
	protected String phoneKind;
	protected String phoneNumber;
	
	protected Contact contact;
	
	private long version;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PHONENUMBERID")
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}
	
	@Version
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

	@Column(name="PHONEKIND")
	public String getPhoneKind()
	{
		return phoneKind;
	}

	public void setPhoneKind(String phoneKind)
	{
		this.phoneKind = phoneKind;
	}

	@Column(name="PHONENUMBER")
	public String getPhoneNumber() 
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	@ManyToOne
	public Contact getContact()
	{
		return contact;
	}

	public void setContact(Contact contact)
	{
		this.contact = contact;
	}

	
	
}
