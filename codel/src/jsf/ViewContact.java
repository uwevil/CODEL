package jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import domain.Contact;
import domain.ContactGroup;
import domain.PhoneNumber;

@SuppressWarnings("serial")
public class ViewContact implements Serializable{
	private Contact contact;
	private String fax;
	private String mobile;
	private String home;
	
	private List<ContactGroup> groups;
	
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

	public List<ContactGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<ContactGroup> groups) {
		this.groups = groups;
	}

	public String view(Contact c){
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
		
		groups = new ArrayList<ContactGroup>(c.getBooks());	
		
		return "viewContactJSF";
	}
	
	public String edit(Contact c){
		return "editContactJSF";
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
}
