package jsf;

import java.util.List;

import domain.Contact;
import domain.DAOContact;

public class Service {

	DAOContact daoContact;
	
	public DAOContact getDaoContact() {
		return daoContact;
	}

	public void setDaoContact(DAOContact daoContact) {
		this.daoContact = daoContact;
	}

	public Service() {
	}
	
	public List<Object> welcome(){
		if (!ControlAccess.getOK())
			return null;
		return daoContact.welcome();
	}
	
	public String addContact(Contact c){
		if (!ControlAccess.getOK())
			return "loginJSF.jsf";
		daoContact.addContact(c);
		return "accueilJSF";
	}
	
	public String deleteContact(Contact c){
		if (!ControlAccess.getOK())
			return "loginJSF.jsf";
		daoContact.deleteContact(c);
		return "accueilJSF";
	}
	
	public String updateContact(Contact c){
		if (!ControlAccess.getOK())
			return "loginJSF.jsf";
		System.out.println("update " + c.getId());
		return "accueilJSF";
	}
	
	public boolean isEntreprise(Object o){
		if (o.getClass().getName().contains("Entreprise"))
			return true;
		return false;
	}

}
