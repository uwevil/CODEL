package service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import domain.Contact;

/**
 * Session Bean implementation class ContactService
 */
@Stateless(mappedName="service")
public class ContactService implements ContactServiceRemote {

	@PersistenceContext(unitName="manager")
	EntityManager entityManager;
	
	private Object findObject(String firstName, String lastName){
		String squery = "SELECT c FROM Contact c WHERE c.firstName = :fn and c.lastName = :ln";
		Query q = entityManager.createQuery(squery);
		
		q.setParameter("fn", firstName);
		q.setParameter("ln", lastName);

		try{
			Contact ctmp = (Contact) q.getSingleResult();
			return ctmp;
		}catch (NoResultException e){
			return null;
		}
	}
	
	public boolean addContact(Contact contact) {
		Object o = findObject(contact.getFirstName(), contact.getLastName());
		
		if (o == null){
			entityManager.persist(contact);
			return true;
		}else{
			return false;
		}
		
	}

	public boolean deleteContact(Contact contact) {
		Object o = findObject(contact.getFirstName(), contact.getLastName());
		if (o != null){
			entityManager.remove(o);
		}
		return true;
	}

	public boolean updateContact(Contact contact, long id) {
		return false;
	}

	public Contact searchContact(Contact contact) {
		return entityManager.find(Contact.class, contact.getId());
	}

	public String welcome() {
		return "OKKKK loll";
	}



}
