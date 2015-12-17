package domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="Address")
public class Address implements Serializable
{

	protected long id;
	protected String street;
	protected String city;
	protected String zip;
	protected String country;

	private long version;

	@Column(name="VERSION")
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
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ADDRESSID")
	public long getId()
	{
		return this.id;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	@Column(name="STREET")
	public String getStreet()
	{
		return this.street;
	}
	
	public void setStreet(String street)
	{
		this.street = street;
	}
	
	@Column(name="CITY")
	public String getCity()
	{
		return this.city;
	}
	
	public void setCity(String city)
	{
		this.city = city;
	}
	
	@Column(name="ZIP")
	public String getZip()
	{
		return this.zip;
	}
	
	public void setZip(String zip)
	{
		this.zip = zip;
	}
	
	@Column(name="COUNTRY")
	public String getCountry()
	{
		return this.country;
	}
	
	public void setCountry(String country)
	{
		this.country = country;
	}
	
}
