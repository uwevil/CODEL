package service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.*;

import domain.Address;
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
	
	@SuppressWarnings("unchecked")
	public boolean deleteContactAll()
	{
		String requestQuery = new String("from Contact");
		Query q = entityManager.createQuery(requestQuery);
		
		List<Contact> list = (List<Contact>) q.getResultList();
		
		if (list == null || list.size() == 0){
			return false;
		}
		
		for (int i = 0; i < list.size(); i++){
			Contact c = list.get(i);
			
			Set<PhoneNumber> numbers = ((Contact)c).getPhoneNumbers();
			
			for (Iterator<PhoneNumber> iterator = numbers.iterator(); iterator.hasNext();)
				entityManager.remove(iterator.next());		
					
			((Contact)c).setPhoneNumbers(null);
			
			Set<ContactGroup> groups = ((Contact)c).getBooks();
					
			for (Iterator<ContactGroup> iterator = groups.iterator(); iterator.hasNext();){
				ContactGroup g = iterator.next();
				g.getContacts().remove(c);
				
				if (g.getContacts().size() < 1)
					entityManager.remove(g);
			}
				
			((Contact)c).getBooks().clear();

			entityManager.remove(c);
			entityManager.remove(((Contact)c).getAddress());
		}

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

	@SuppressWarnings("unchecked")
	public List<Object> searchFirstName(String firstName){
		String requestQuery = new String("from Contact where firstName = '" + firstName.toUpperCase() + "'");
		Query q = entityManager.createQuery(requestQuery);
		
		List<Object> list = (List<Object>) q.getResultList();

		if (list == null || list.size() < 1){
			return null;
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> searchLastName(String lastName){
		String requestQuery = new String("from Contact where lastName = '" + lastName.toUpperCase() + "'");
		Query q = entityManager.createQuery(requestQuery);

		List<Object> list = (List<Object>) q.getResultList();

		if (list == null || list.size() < 1){
			return null;
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> searchNumSiret(String numSiret){
		long num;
		
		try{
			num = Long.parseLong(numSiret);
		}catch(NumberFormatException e){
			return null;
		}
		
		String requestQuery = new String("from Contact where numSiret = '" + num + "'");
		Query q = entityManager.createQuery(requestQuery);

		List<Object> list = (List<Object>) q.getResultList();

		if (list == null || list.size() < 1){
			return null;
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> searchEmail(String email){
		String requestQuery;
		if (email.length() > 0){
			requestQuery = new String("from Contact as c where c.email = '" + email.toUpperCase() + "'");
		}else{
			requestQuery = new String("from Contact");
		}
		
		Query q = entityManager.createQuery(requestQuery);

		List<Object> list = (List<Object>) q.getResultList();

		if (list == null || list.size() < 1){
			return null;
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> searchAddress(Address address){
		String requestQuery = new String("from Contact as c where ");
		
		int length = requestQuery.length();
		
		if (requestQuery.length() == length && address.getStreet().length() > 0){
			requestQuery += "c.address.street = '" + address.getStreet().toUpperCase() + "'";
		}
		
		if (requestQuery.length() == length && address.getZip().length() > 0){
			requestQuery += "c.address.zip = '" + address.getZip().toUpperCase() + "'";
		}else if (address.getZip().length() > 0){
			requestQuery += " and c.address.zip = '" + address.getZip().toUpperCase() + "'";
		}
		
		if (requestQuery.length() == length && address.getCity().length() > 0){
			requestQuery += "c.address.city = '" + address.getCity().toUpperCase() + "'";
		}else if (address.getCity().length() > 0){
			requestQuery += " and c.address.city = '" + address.getCity().toUpperCase() + "'";
		}
		
		if (requestQuery.length() == length && address.getCountry().length() > 0){
			requestQuery += "c.address.country = '" + address.getCountry().toUpperCase() + "'";
		}else if (address.getCountry().length() > 0){
			requestQuery += " and c.address.country = '" + address.getCountry().toUpperCase() + "'" ;
		}
		
		Query q = entityManager.createQuery(requestQuery);
		List<Object> list = (List<Object>) q.getResultList();

		if (list == null || list.size() < 1){
			return null;
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> searchNumber(String number){
		String requestQuery = new String("from PhoneNumber as p where p.phoneNumber = '" + number + "'");
		
		Query q = entityManager.createQuery(requestQuery);
		List<Object> list = (List<Object>) q.getResultList();

		if (list == null || list.size() < 1){
			return null;
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> searchGroups(List<String> groups){
		String requestQuery = new String("from ContactGroup as g where ");

		int length = requestQuery.length();

		for (int i = 0; i < groups.size(); i++){
			String s = groups.get(i);

			if (requestQuery.length() == length && s.length() > 0){
				requestQuery += "g.groupName = '" + s + "'";
			}else{
				requestQuery += " or g.groupName = '" + s + "'";
			}
		}
		
		Query q = entityManager.createQuery(requestQuery);
		List<Object> list = (List<Object>) q.getResultList();
		
		if (list == null || list.size() < 1){
			return null;
		}
		
		return list;
	}
	
	public String welcome() {
		return "Welcome";
	}



}
