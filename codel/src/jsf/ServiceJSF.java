package jsf;

import java.io.Serializable;
import java.util.List;

import domain.Address;
import domain.Contact;
import domain.DAOContact;

@SuppressWarnings("serial")
public class ServiceJSF implements Serializable{

	DAOContact daoContact;
	
	public DAOContact getDaoContact() {
		return daoContact;
	}

	public void setDaoContact(DAOContact daoContact) {
		this.daoContact = daoContact;
	}

	public ServiceJSF() {
	}
	
	public List<Object> welcome(){
		if (!ControlAccessJSF.getOK())
			return null;
		return daoContact.welcome();
	}
	
	public String addContact(Contact c){
		if (!ControlAccessJSF.getOK())
			return "loginJSF.jsf";
		if (daoContact.addContact(c)){
			return "addedContactJSF";
		}else{
			return "accueilJSF";
		}
	}
	
	public String deleteContact(Contact c){
		if (!ControlAccessJSF.getOK())
			return "loginJSF.jsf";
		
		daoContact.deleteContact(c);
		return "accueilJSF";
	}
	
	public String deleteContactAll(){
		if (!ControlAccessJSF.getOK())
			return "loginJSF.jsf";
		
		daoContact.deleteContactAll();
		return "accueilJSF";
	}
	
	
	public String updateContact(Contact c){
		if (!ControlAccessJSF.getOK())
			return "loginJSF.jsf";
		if (daoContact.updateContact(c, c.getId())){
			return "accueilJSF";
		}else{
			return "errorVersionJSF";
		}
	}
	
	public List<Object> groups(){
		return daoContact.getGroups();
	}
	
	public Object searchContactName(String firstName, String lastName){
		return daoContact.searchContact(new Contact(firstName, lastName, null));
	}
	
	public List<Object> searchFirstName(String firstName){
		return daoContact.searchFirstName(firstName);
	}
	
	public List<Object> searchLastName(String lastName){
		return daoContact.searchLastName(lastName);
	}
	
	public List<Object> searchEmail(String email){
		if (email == null || email.length() < 1)
			return daoContact.searchEmail("");
		
		return daoContact.searchEmail(email);
	}
	
	public List<Object> searchAddress(String street, String zip, String city, String country){
		Address a = new Address();
		if (street == null)
			a.setStreet("");
		else
			a.setStreet(street);
		
		if (zip == null)
			a.setZip("");
		else
			a.setZip(zip);
		
		if (city == null)
			a.setCity("");
		else
			a.setCity(city);
		
		if (country == null)
			a.setCountry("");
		else
			a.setCountry(country);
		
		return daoContact.searchAddress(a);
	}
	
	public boolean isEntreprise(Object o){
		if (o.getClass().getName().contains("Entreprise"))
			return true;
		return false;
	}

}
