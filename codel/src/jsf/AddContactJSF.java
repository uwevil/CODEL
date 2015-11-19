package jsf;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import domain.Address;
import domain.Contact;
import domain.ContactGroup;
import domain.Entreprise;
import domain.PhoneNumber;

@SuppressWarnings("serial")
@ManagedBean(name="addContact")
@SessionScoped

public class AddContactJSF implements Serializable{
	private String firstName;
	private String lastName;
	private String numSiret;
	private String email;
	private String home;
	private String fax;
	private String mobile;
	private String street;
	private String zip;
	private String city;
	private String country;
	
	private Object contact;
	
	private ServiceJSF service;
	
	private ArrayList<String> books = new ArrayList<String>();
	
	private String newGroup;
	
	public AddContactJSF(){
		books.add("Amis");
		books.add("Famille");
		books.add("Collegues");
	}

	public String getNewGroup() {
		return newGroup;
	}

	public void setNewGroup(String newGroup) {
		this.newGroup = newGroup;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNumSiret() {
		return numSiret;
	}

	public void setNumSiret(String numSiret) {
		this.numSiret = numSiret;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public ArrayList<String> getBooks() {
		return books;
	}

	public void setBooks(ArrayList<String> books) {
		this.books = books;
	}
	
	public String add(){
		Address address = new Address(street.toUpperCase(), city.toUpperCase(), zip.toUpperCase(), country.toUpperCase());
		
		if (email.length() > 1 && !email.matches("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[_a-zA-Z0-9-]+(\\.[a-zA-Z0-9]+)+$"))
		{			
			return null;
		}
		
		if (this.numSiret == null || this.numSiret.length() < 1){
			Contact c = new Contact(firstName.toUpperCase(), lastName.toUpperCase(), email.toUpperCase());
			c.setAddress(address);
			PhoneNumber p = new PhoneNumber("MOBILENUMBER", mobile);
			p.setContact(c);
			c.getPhoneNumbers().add(p);
			
			p = new PhoneNumber("HOMENUMBER", home);
			p.setContact(c);
			c.getPhoneNumbers().add(p);
			
			p = new PhoneNumber("FAXNUMBER", fax);
			p.setContact(c);
			c.getPhoneNumbers().add(p);
			
			for (int i = 0; i < books.size(); i++){
				ContactGroup g = new ContactGroup(books.get(i));
				g.getContacts().add(c);
				c.getBooks().add(g);
			}
			
			if (newGroup != null && newGroup.length() >= 1){
				books.add(newGroup);
				ContactGroup g = new ContactGroup(newGroup);
				g.getContacts().add(c);
				c.getBooks().add(g);
			}
			
			this.contact = c;
			return service.addContact(c);
		}else{
			long num;
			
			try{
				num = Long.parseLong(numSiret);
			}catch(NumberFormatException e){
				return null;
			}
			
			Entreprise c = new Entreprise(num);
			c.setFirstName(firstName.toUpperCase());
			c.setLastName(lastName.toUpperCase());
			c.setEmail(email.toUpperCase());
			
			c.setAddress(address);
			PhoneNumber p = new PhoneNumber("MOBILENUMBER", mobile);
			p.setContact(c);
			c.getPhoneNumbers().add(p);
			
			p = new PhoneNumber("HOMENUMBER", home);
			p.setContact(c);
			c.getPhoneNumbers().add(p);
			
			p = new PhoneNumber("FAXNUMBER", fax);
			p.setContact(c);
			c.getPhoneNumbers().add(p);
			
			for (int i = 0; i < books.size(); i++){
				ContactGroup g = new ContactGroup(books.get(i));
				g.getContacts().add(c);
				c.getBooks().add(g);
			}
			
			if (newGroup != null && newGroup.length() >= 1){
				books.add(newGroup);
				ContactGroup g = new ContactGroup(newGroup);
				g.getContacts().add(c);
				c.getBooks().add(g);
			}
			
			this.contact = c;
			return service.addContact(c);
		}		
	}
	
	public String reset(){
		this.firstName = "";
		this.lastName = "";
		return "addContactJSF";
	}

	public ServiceJSF getService() {
		return service;
	}

	public void setService(ServiceJSF service) {
		this.service = service;
	}

	public Object getContact() {
		return contact;
	}

	public void setContact(Object contact) {
		this.contact = contact;
	}
}
