package service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import domain.Contact;

/**
 * Session Bean implementation class ContactService
 */
@Stateless(mappedName="service")
public class ContactService implements ContactServiceRemote {

	@PersistenceContext(unitName="manager")
	EntityManager entityManager;
	
	public boolean addContact(Contact contact) {
		entityManager.persist(contact);
		
		return true;
	}

	public boolean deleteContact(Contact contact) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateContact(Contact contact, long id) {
		// TODO Auto-generated method stub
		return false;
	}

	public Contact searchContact(Contact contact) {
		return entityManager.find(Contact.class, contact.getId());
	}

	public String welcome() {
		return "OKKKK loll";
	}



}
