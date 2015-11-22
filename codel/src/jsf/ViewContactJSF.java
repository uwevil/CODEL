package jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import domain.Contact;
import domain.ContactGroup;
import domain.PhoneNumber;

@SuppressWarnings("serial")
public class ViewContactJSF implements Serializable{
	private ServiceJSF service;
	
	private Contact contact;
	
	public ServiceJSF getService() {
		return service;
	}

	public void setService(ServiceJSF service) {
		this.service = service;
	}

	private String fax;
	private String mobile;
	private String home;
	
	private List<String> groups;
	
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

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public String view(Contact c){
		if (!ControlAccessJSF.getOK())
			return null;
		
		this.setContact(c);
		
		for (Iterator<PhoneNumber> iterator = c.getPhoneNumbers().iterator(); iterator.hasNext();){
			PhoneNumber p = iterator.next();
			if (p.getPhoneKind().contains("MOBILE")){
				this.mobile = p.getPhoneNumber();
			}else if (p.getPhoneKind().contains("HOME")){
				this.home = p.getPhoneNumber();
			}else{
				this.fax = p.getPhoneNumber();
			}
		}
		
		groups = new ArrayList<String>();
		
		for (Iterator<ContactGroup> iterator = c.getBooks().iterator(); iterator.hasNext();){
			groups.add(iterator.next().getGroupName());
		}
				
		return "viewContactJSF";
	}
	
	public String edit(Contact c){
		if (!ControlAccessJSF.getOK())
			return null;
		
		if (!groups.contains("Amis")){
			groups.add("Amis");
		}
		if (!groups.contains("Famille")){
			groups.add("Famille");
		}
		if (!groups.contains("Collegues")){
			groups.add("Collegues");
		}
		
		return "editContactJSF";
	}
	
	public String delete(Contact c){
		if (!ControlAccessJSF.getOK())
			return null;
		
		return service.deleteContact(c);
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
}
