package service;

import java.util.List;

import javax.ejb.Remote;

import domain.Address;
import domain.Contact;

@Remote
public interface ContactServiceRemote {
	public boolean addContact(Contact contact);
	public boolean deleteContact(Contact contact);
	public boolean deleteContactAll();
	public boolean updateContact(Contact contact, long id);
	public Contact searchContactById(Contact contact, long id);
	public Contact searchContactByName(String firstName, String lastName);

	public List<Object> searchFirstName(String firstName);
	public List<Object> searchLastName(String lastName);
	public List<Object> searchNumSiret(String numSiret);
	public List<Object> searchEmail(String email);
	public List<Object> searchAddress(Address address);
	public List<Object> searchNumber(String number);
	public List<Object> searchGroups(List<String> groups);
	
	public String welcome();

}
