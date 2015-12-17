package service;

import javax.ejb.Remote;

import domain.Contact;

@Remote
public interface ContactServiceRemote {
	public boolean addContact(Contact contact);
	public boolean deleteContact(Contact contact);
	public boolean updateContact(Contact contact, long id);
	public Contact searchContact(Contact contact);
	public String welcome();

}
