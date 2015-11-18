package jsf;

import java.io.Serializable;
import java.util.List;

import domain.Contact;
import domain.DAOContact;

@SuppressWarnings("serial")
public class Service implements Serializable{

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
		if (daoContact.addContact(c)){
			return "addedContactJSF";
		}else{
			return "accueilJSF";
		}
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
		if (daoContact.updateContact(c, c.getId())){
			return "accueilJSF";
		}else{
			return "errorVersion";
		}
	}
	
	public boolean isEntreprise(Object o){
		if (o.getClass().getName().contains("Entreprise"))
			return true;
		return false;
	}

}
