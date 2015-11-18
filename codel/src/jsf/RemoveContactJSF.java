package jsf;

import java.io.Serializable;

import domain.Contact;

@SuppressWarnings("serial")
public class RemoveContactJSF implements Serializable {

	private ServiceJSF service;
	private String firstName;
	private String lastName;

	public ServiceJSF getService() {
		return service;
	}

	public void setService(ServiceJSF service) {
		this.service = service;
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
	
	public RemoveContactJSF() {
		// TODO Auto-generated constructor stub
	}
	
	public String remove(){
		return service.deleteContact(new Contact(firstName, lastName, null));
	}
	
	public String removeAll(){
		return service.deleteContactAll();
	}

}
