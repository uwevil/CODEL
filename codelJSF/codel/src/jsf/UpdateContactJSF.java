package jsf;

import java.io.Serializable;
import java.util.HashSet;

import domain.Address;
import domain.Contact;
import domain.ContactGroup;
import domain.PhoneNumber;

@SuppressWarnings("serial")
public class UpdateContactJSF implements Serializable {	
	private ServiceJSF service;
	
	private ViewContactJSF viewContact;

	private String[] newGroups;
	
	private String newGroup;
		
	public ServiceJSF getService() {
		return service;
	}

	public void setService(ServiceJSF service) {
		this.service = service;
	}
	
	public UpdateContactJSF() {
		// TODO Auto-generated constructor stub
	}

	public String update(Contact c){
		if (!ControlAccessJSF.getOK())
			return null;
		
		Address address = new Address(viewContact.getContact().getAddress().getStreet().toUpperCase(), 
				viewContact.getContact().getAddress().getCity().toUpperCase(), 
				viewContact.getContact().getAddress().getZip().toUpperCase(), 
				viewContact.getContact().getAddress().getCountry().toUpperCase());
		
		if (viewContact.getContact().getEmail().length() > 0 
				&& !viewContact.getContact().getEmail()
				.matches("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[_a-zA-Z0-9-]+(\\.[a-zA-Z0-9]+)+$")){
			return null;
		}
		
		c.setEmail(viewContact.getContact().getEmail().toUpperCase());
		c.setAddress(address);
		
		c.setPhoneNumbers(new HashSet<PhoneNumber>());
		
		PhoneNumber p = new PhoneNumber("MOBILENUMBER", viewContact.getMobile());
		p.setContact(c);
		c.getPhoneNumbers().add(p);
		
		p = new PhoneNumber("HOMENUMBER", viewContact.getHome());
		p.setContact(c);
		c.getPhoneNumbers().add(p);
		
		p = new PhoneNumber("FAXNUMBER", viewContact.getFax());
		p.setContact(c);
		c.getPhoneNumbers().add(p);
		
		c.setBooks(new HashSet<ContactGroup>());
		
		for(int i = 0; i < newGroups.length; i++){
			ContactGroup g = new ContactGroup(newGroups[i]);
			g.getContacts().add(c);
			c.getBooks().add(g);
		}
		
		if (newGroup != null && newGroup.length() > 0){
			ContactGroup g = new ContactGroup(newGroup);
			g.getContacts().add(c);
			c.getBooks().add(g);
		}
		
		return service.updateContact(c);
	}

	public String getNewGroup() {
		return newGroup;
	}

	public void setNewGroup(String newGroup) {
		this.newGroup = newGroup;
	}

	public ViewContactJSF getViewContact() {
		return viewContact;
	}

	public void setViewContact(ViewContactJSF viewContact) {
		this.viewContact = viewContact;
	}

	public String[] getNewGroups() {
		return newGroups;
	}

	public void setNewGroups(String[] newGroups) {
		this.newGroups = newGroups;
	}

}
