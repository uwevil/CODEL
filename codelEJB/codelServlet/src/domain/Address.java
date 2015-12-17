package domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Address implements Serializable
{

	protected long id;
	protected String street;
	protected String city;
	protected String zip;
	protected String country;

	private long version;

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Address()
	{
		street = null;
		city = null;
		zip = null;
		country = null;
	}

	public Address(String street, String city, String zip, String country)
	{
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.country = country;
	}
	
	public long getId()
	{
		return this.id;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	public String getStreet()
	{
		return this.street;
	}
	
	public void setStreet(String street)
	{
		this.street = street;
	}
	
	public String getCity()
	{
		return this.city;
	}
	
	public void setCity(String city)
	{
		this.city = city;
	}
	
	public String getZip()
	{
		return this.zip;
	}
	
	public void setZip(String zip)
	{
		this.zip = zip;
	}
	
	public String getCountry()
	{
		return this.country;
	}
	
	public void setCountry(String country)
	{
		this.country = country;
	}
	
}
