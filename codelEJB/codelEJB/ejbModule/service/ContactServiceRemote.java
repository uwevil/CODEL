package service;

import javax.ejb.Remote;

import domain.Contact;

@Remote
public interface ContactServiceRemote {
	public boolean addContact(Contact contact);
	public boolean deleteContact(Contact contact);
	public boolean updateContact(Contact contact, long id);
	public Contact searchContactById(Contact contact, long id);
	public Contact searchContactByName(String firstName, String lastName);

	public String welcome();

}
