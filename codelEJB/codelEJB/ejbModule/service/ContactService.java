package service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.*;

import domain.Contact;
import domain.ContactGroup;
import domain.Entreprise;

/**
 * Session Bean implementation class ContactService
 */
@Stateless(mappedName="service")
public class ContactService implements ContactServiceRemote {

	@PersistenceContext(unitName="manager")
	EntityManager entityManager;
	
	private Object findContact(String firstName, String lastName){
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
	
	@SuppressWarnings("unchecked")
	public boolean addContact(Contact contact) {
		Object o = findContact(contact.getFirstName(), contact.getLastName());
		
		if (o != null){
			return false;
		}else{
			if (contact.getClass().getName().contains("Contact")){
				Set<ContactGroup> books = contact.getBooks();
				Set<ContactGroup> newBooks = new HashSet<ContactGroup>();
				Iterator<ContactGroup> iterator = books.iterator();
				contact.setBooks(newBooks);
				entityManager.persist(contact);

				while (iterator.hasNext()){
					ContactGroup group = iterator.next();
					String requestQuery = new String("FROM ContactGroup g WHERE g.groupName = '" 
							+ group.getGroupName() + "'");
					Query q = entityManager.createQuery(requestQuery);	
					
					List<ContactGroup> groupExisted = q.getResultList();
								
					if (groupExisted != null && groupExisted.size() > 0){
						newBooks.add(groupExisted.get(0));
						groupExisted.get(0).getContacts().add(contact);
						entityManager.merge(groupExisted.get(0));
					}else{
						newBooks.add(group);
						group.getContacts().add(contact);
						entityManager.persist(group);
					}
				}
				
				entityManager.merge(contact);
			}else{
				Entreprise e = (Entreprise) contact;
				
				Set<ContactGroup> books = e.getBooks();
				Set<ContactGroup> newBooks = new HashSet<ContactGroup>();
				Iterator<ContactGroup> iterator = books.iterator();
								
				while (iterator.hasNext()){
					ContactGroup group = iterator.next();
					String requestQuery = new String("FROM ContactGroup g WHERE g.groupName = '" 
							+ group.getGroupName() + "'");
					Query q = entityManager.createQuery(requestQuery);	
					
					List<ContactGroup> groupExisted = q.getResultList();
								
					if (groupExisted != null && groupExisted.size() > 0){
						newBooks.add(groupExisted.get(0));
						groupExisted.get(0).getContacts().add(e);
					}else{
						newBooks.add(group);
						group.getContacts().add(e);
					}
				}
				
				e.setBooks(newBooks);
				entityManager.persist(e);
			}
			
			return true;
		}

	}

	public boolean deleteContact(Contact contact) {
		Object o = findContact(contact.getFirstName(), contact.getLastName());
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
