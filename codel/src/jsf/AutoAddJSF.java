package jsf;

import java.io.Serializable;

import domain.Address;
import domain.Contact;
import domain.ContactGroup;
import domain.Entreprise;
import domain.PhoneNumber;

@SuppressWarnings("serial")
public class AutoAddJSF implements Serializable{

	private Contact c1;
	private Contact c2;
	private Entreprise e1;
	
	private ServiceJSF service;
	
	public Contact getC1() {
		return c1;
	}


	public void setC1(Contact c1) {
		this.c1 = c1;
	}


	public Contact getC2() {
		return c2;
	}


	public void setC2(Contact c2) {
		this.c2 = c2;
	}


	public Entreprise getE1() {
		return e1;
	}


	public void setE1(Entreprise e1) {
		this.e1 = e1;
	}


	public AutoAddJSF() {
		// TODO Auto-generated constructor stub
	}


	public ServiceJSF getService() {
		return service;
	}


	public void setService(ServiceJSF service) {
		this.service = service;
	}

	public String add(){
		Contact c = new Contact("ICIER", "PAUL", "PAUL@POLICE.FR");
		c.setAddress(new Address("JEAN JAURES", "LYON", "69000", "FRANCE"));
		
		PhoneNumber p = new PhoneNumber("MOBILENUMBER", "17");
		p.setContact(c);
		c.getPhoneNumbers().add(p);
		
		p = new PhoneNumber("HOMENUMBER", "17");
		p.setContact(c);
		c.getPhoneNumbers().add(p);
		
		ContactGroup g = new ContactGroup("SOS");
		g.getContacts().add(c);
		c.getBooks().add(g);
		
		service.addContact(c);
		
		c = new Contact("POMP", "PIERRE", "PIERRE@POMPIER.FR");
		c.setAddress(new Address("BD ARNAUD", "NANTERRE", "92000", "FRANCE"));
		
		p = new PhoneNumber("MOBILENUMBER", "18");
		p.setContact(c);
		c.getPhoneNumbers().add(p);
		
		p = new PhoneNumber("HOMENUMBER", "18");
		p.setContact(c);
		c.getPhoneNumbers().add(p);
		
		g = new ContactGroup("SOS");
		g.getContacts().add(c);
		c.getBooks().add(g);
		
		g = new ContactGroup("Amis");
		g.getContacts().add(c);
		c.getBooks().add(g);
		
		service.addContact(c);
		
		Entreprise e = new Entreprise(1432112452);
		
		e.setFirstName("AIRBUS");
		e.setLastName("INC");
		e.setEmail("CONTACT@AIRBUS.INC");
		
		e.setAddress(new Address("AIR", "BUS", "0000", ""));
		
		p = new PhoneNumber("MOBILENUMBER", "012");
		p.setContact(e);
		e.getPhoneNumbers().add(p);
		
		p = new PhoneNumber("HOMENUMBER", "345");
		p.setContact(e);
		e.getPhoneNumbers().add(p);
		
		p = new PhoneNumber("FAXNUMBER", "678");
		p.setContact(e);
		e.getPhoneNumbers().add(p);
		
		g = new ContactGroup("Collegues");
		g.getContacts().add(e);
		e.getBooks().add(g);
		
		g = new ContactGroup("Amis");
		g.getContacts().add(e);
		e.getBooks().add(g);
		
		service.addContact(e);
		
		return "accueilJSF";
	}
}
