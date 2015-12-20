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
import domain.PhoneNumber;

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

	@SuppressWarnings("unchecked")
	public boolean updateContact(Contact contact, long id) {
		Contact e = (Contact) entityManager.find(Contact.class, id);		
		Contact e_tmp = (Contact) contact;
		
		if ( e.getVersion() != e_tmp.getVersion() ) {
			return false;
		}

		if (e != null){
			Set<ContactGroup> contactGroups = e.getBooks();

			for (Iterator<ContactGroup> iterator = contactGroups.iterator(); iterator.hasNext();)
			{
				ContactGroup g = iterator.next();					
				g.getContacts().remove(e);	
				
				if (g.getContacts().size() < 1)
					entityManager.remove(g);
			} 
			
			contactGroups.clear();
			
			for (Iterator<ContactGroup> iterator2 = e_tmp.getBooks().iterator(); 
					iterator2.hasNext();){ 					
				ContactGroup group = iterator2.next();
				
				String requestQuery = new String("from ContactGroup "
						+ "where groupName = '" + group.getGroupName() + "'");
				Query q = entityManager.createQuery(requestQuery);
				List<ContactGroup> list = (List<ContactGroup>) q.getResultList();
									
				if (list != null && list.size() > 0){
					ContactGroup groupExisted = list.get(0);
					e.getBooks().add(groupExisted);
					(groupExisted).getContacts().add(e);
				}else{
					e.getBooks().add(group);
					group.getContacts().add(e);
				}	
		    }
			
			e.setFirstName(e_tmp.getFirstName());
			e.setLastName(e_tmp.getLastName());
			
			e.setEmail(e_tmp.getEmail());
			e.getAddress().setStreet(e_tmp.getAddress().getStreet());
			e.getAddress().setZip(e_tmp.getAddress().getZip());
			e.getAddress().setCity(e_tmp.getAddress().getCity());
			e.getAddress().setCountry(e_tmp.getAddress().getCountry());

			for (Iterator<PhoneNumber> iterator = e.getPhoneNumbers().iterator(); iterator.hasNext();){
				PhoneNumber p = iterator.next();
				
				for (Iterator<PhoneNumber> iterator2 = e_tmp.getPhoneNumbers().iterator(); 
						iterator2.hasNext();){
					PhoneNumber p2 = iterator2.next();

					if (p.getPhoneKind().equals(p2.getPhoneKind())){
						p.setPhoneNumber(p2.getPhoneNumber());
					}
				}
			}
			
			entityManager.flush();
		}
		
		entityManager.merge(e);		
		
		return true;
	}
	
	public boolean deleteContact(Contact contact) {
		Object o = findContact(contact.getFirstName(), contact.getLastName());
		if (o == null){
			return false;
		}
		
		Contact c = (Contact) o;
		Set<PhoneNumber> numbers = c.getPhoneNumbers();
		
		for (Iterator<PhoneNumber> iterator = numbers.iterator(); iterator.hasNext();)
			entityManager.remove(iterator.next());		
		
		c.setPhoneNumbers(null);
		
		Set<ContactGroup> groups = c.getBooks();
				
		for (Iterator<ContactGroup> iterator = groups.iterator(); iterator.hasNext();)
		{
			ContactGroup g = iterator.next();
			g.getContacts().remove(c);
			
			if (g.getContacts().size() < 1)
				entityManager.remove(g);
		}
			
		c.getBooks().clear();

		entityManager.remove(c);				
		entityManager.remove(c.getAddress());
		return true;
	}
	
	public Contact searchContactByName(String firstName, String lastName){
		Object o = this.findContact(firstName, lastName);
		if (o == null)
			return null;
		return (Contact) o; 
	}

	public Contact searchContactById(Contact contact, long id) {
		return entityManager.find(Contact.class, id);
	}

	public String welcome() {
		return "OKKKK loll";
	}



}
