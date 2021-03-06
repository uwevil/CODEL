package domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.*;

@SuppressWarnings("serial")
public class DAOContact extends HibernateDaoSupport implements Serializable{
		
	public static HibernateTemplate hibernateTemplate;
	
	public DAOContact()
	{}
	
	public static void setHibernateTemplate(SessionFactory sessionFactory){
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
		
	public List<Object> welcome()
	{		
		String requestQuery = new String("from Contact");
		
		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>) getHibernateTemplate().find(requestQuery);
		
		if (list == null)
		{
			return null;
		}
				
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public boolean addContact(Object contact)
	{					
		if (contact.getClass().getName().contains("Entreprise"))
		{
			Entreprise e = (Entreprise) contact;
			
			String requestQuery = new String("from Contact "
					+ "where firstName = '" + e.getFirstName() + "'" 
					+ " and lastName = '" + e.getLastName() + "'");

			List<Object> list = (List<Object>) getHibernateTemplate().find(requestQuery);

			if (list != null && list.size() > 0)
			{
				return false;
			}
			else
			{					
				Set<ContactGroup> books = e.getBooks();
				Set<ContactGroup> newBooks = new HashSet<>();
				
				Iterator<ContactGroup> iterator = books.iterator();
				
				while (iterator.hasNext())
				{
					ContactGroup group = iterator.next();
					requestQuery = new String("from ContactGroup where groupName = '" + group.getGroupName() + "'");
										
					List<ContactGroup> groupExisted = (List<ContactGroup>) getHibernateTemplate().find(requestQuery);
										
					if (groupExisted != null && groupExisted.size() > 0)
					{
						newBooks.add(groupExisted.get(0));
						groupExisted.get(0).getContacts().add(e);
					}
					else
					{
						newBooks.add(group);
						group.getContacts().add(e);
					}
				}
				
				e.setBooks(newBooks);
				
				getHibernateTemplate().persist(e);
			}
		}
		else
		{
			Contact e = (Contact) contact;

			String requestQuery = new String("from Contact "
					+ "where firstName = '" + e.getFirstName() + "'" 
					+ " and lastName = '" + e.getLastName() + "'");

			List<Object> list = (List<Object>) getHibernateTemplate().find(requestQuery);

			if (list != null && list.size() > 0)
			{
				return false;
			}
			else
			{	
				Set<ContactGroup> books = e.getBooks();
				Set<ContactGroup> newBooks = new HashSet<>();
				Iterator<ContactGroup> iterator = books.iterator();
				
				while (iterator.hasNext())
				{
					ContactGroup group = iterator.next();
					requestQuery = new String("from ContactGroup where groupName = '" + group.getGroupName() + "'");
										
					List<ContactGroup> groupExisted = (List<ContactGroup>) getHibernateTemplate().find(requestQuery);
			
					if (groupExisted != null && groupExisted.size() > 0)
					{
						newBooks.add(groupExisted.get(0));
						groupExisted.get(0).getContacts().add(e);
					}
					else
					{
						newBooks.add(group);
						group.getContacts().add(e);
					}
				}
				
				e.setBooks(newBooks);

				getHibernateTemplate().persist(e);
			}

		}
			
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean deleteContact(Contact contact)
	{
		String requestQuery = new String("from Contact where firstName = '" + contact.getFirstName() 
						+ "' and lastName = '" + contact.getLastName() + "'"
						);
		
		List<Contact> list = (List<Contact>) getHibernateTemplate().find(requestQuery);
		
		if (list == null || list.size() == 0)
		{
			return false;
		}
		
		Contact c = list.get(0);
				
		Set<PhoneNumber> numbers = ((Contact)c).getPhoneNumbers();
		
		for (Iterator<PhoneNumber> iterator = numbers.iterator(); iterator.hasNext();)
			getHibernateTemplate().delete(iterator.next());		
				
		((Contact)c).setPhoneNumbers(null);
		
		Set<ContactGroup> groups = ((Contact)c).getBooks();
				
		for (Iterator<ContactGroup> iterator = groups.iterator(); iterator.hasNext();)
		{
			ContactGroup g = iterator.next();
			g.getContacts().remove(c);
			
			if (g.getContacts().size() < 1)
				getHibernateTemplate().delete(g);
		}
			
		((Contact)c).getBooks().clear();

		
		getHibernateTemplate().delete(c);
		getHibernateTemplate().delete(((Contact)c).getAddress());

		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean deleteContactAll()
	{
		String requestQuery = new String("from Contact");
		
		List<Contact> list = (List<Contact>) getHibernateTemplate().find(requestQuery);
		
		if (list == null || list.size() == 0)
		{
			return false;
		}
		
		for (int i = 0; i < list.size(); i++){
			Contact c = list.get(i);
			
			Set<PhoneNumber> numbers = ((Contact)c).getPhoneNumbers();
			
			for (Iterator<PhoneNumber> iterator = numbers.iterator(); iterator.hasNext();)
				getHibernateTemplate().delete(iterator.next());		
					
			((Contact)c).setPhoneNumbers(null);
			
			Set<ContactGroup> groups = ((Contact)c).getBooks();
					
			for (Iterator<ContactGroup> iterator = groups.iterator(); iterator.hasNext();)
			{
				ContactGroup g = iterator.next();
				g.getContacts().remove(c);
				
				if (g.getContacts().size() < 1)
					getHibernateTemplate().delete(g);
			}
				
			((Contact)c).getBooks().clear();

			getHibernateTemplate().delete(c);
			getHibernateTemplate().delete(((Contact)c).getAddress());
		}

		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean updateContact(Object contact, long id)
	{			
		if (contact.getClass().getName().contains("Entreprise"))
		{			
			Entreprise e = (Entreprise) getHibernateTemplate().load(Entreprise.class, id);
	
			Entreprise e_tmp = (Entreprise) contact;
			
			if ( e.getVersion() != e_tmp.getVersion() ) 
			{
				return false;
			}
			
			if (e != null)
			{
				Set<ContactGroup> contactGroups = e.getBooks();

				for (Iterator<ContactGroup> iterator = contactGroups.iterator(); iterator.hasNext();)
				{
					ContactGroup g = iterator.next();					
					g.getContacts().remove(e);	
					
					if (g.getContacts().size() < 1)
						getHibernateTemplate().delete(g);
				} 
				
				contactGroups.clear();
				
				for (Iterator<ContactGroup> iterator2 = e_tmp.getBooks().iterator(); 
						iterator2.hasNext();) { 					
					ContactGroup group = iterator2.next();
					
					String requestQuery = new String("from ContactGroup "
							+ "where groupName = '" + group.getGroupName() + "'");
								
					List<ContactGroup> list = (List<ContactGroup>) getHibernateTemplate().find(requestQuery);
										
					if (list != null && list.size() > 0)
					{
						ContactGroup groupExisted = list.get(0);

						e.getBooks().add(groupExisted);
						(groupExisted).getContacts().add(e);
					}
					else
					{
						e.getBooks().add(group);
						group.getContacts().add(e);
						getHibernateTemplate().persist(group);
					}					
			    }
				
				e.setFirstName(e_tmp.getFirstName());
				e.setLastName(e_tmp.getLastName());
				
				e.setEmail(e_tmp.getEmail());
				e.getAddress().setStreet(e_tmp.getAddress().getStreet());
				e.getAddress().setZip(e_tmp.getAddress().getZip());
				e.getAddress().setCity(e_tmp.getAddress().getCity());
				e.getAddress().setCountry(e_tmp.getAddress().getCountry());

				for (Iterator<PhoneNumber> iterator = e.getPhoneNumbers().iterator(); 
						iterator.hasNext();) {
					PhoneNumber p = iterator.next();
					
					for (Iterator<PhoneNumber> iterator2 = e_tmp.getPhoneNumbers().iterator(); 
							iterator2.hasNext();)
					{
						PhoneNumber p2 = iterator2.next();

						if (p.getPhoneKind().equals(p2.getPhoneKind()))
						{
							p.setPhoneNumber(p2.getPhoneNumber());
						}
					}
				}
				
				getHibernateTemplate().flush();
			}
			
			getHibernateTemplate().saveOrUpdate(e);
		}
		else
		{			
			Contact e = (Contact) getHibernateTemplate().load(Contact.class, id);
						
			Contact e_tmp = (Contact) contact;
			
			if ( e.getVersion() != e_tmp.getVersion() ) 
			{
				return false;
			}
			
			if (e != null)
			{
				Set<ContactGroup> contactGroups = e.getBooks();

				for (Iterator<ContactGroup> iterator = contactGroups.iterator(); iterator.hasNext();)
				{
					ContactGroup g = iterator.next();					
					g.getContacts().remove(e);	
					
					if (g.getContacts().size() < 1)
						getHibernateTemplate().delete(g);
				} 
				
				contactGroups.clear();
				
				for (Iterator<ContactGroup> iterator2 = e_tmp.getBooks().iterator(); 
						iterator2.hasNext();) { 					
					ContactGroup group = iterator2.next();
					
					String requestQuery = new String("from ContactGroup "
							+ "where groupName = '" + group.getGroupName() + "'");
								
					List<ContactGroup> list = (List<ContactGroup>) getHibernateTemplate().find(requestQuery);
										
					if (list != null && list.size() > 0)
					{
						ContactGroup groupExisted = list.get(0);

						e.getBooks().add(groupExisted);
						(groupExisted).getContacts().add(e);
					}
					else
					{
						e.getBooks().add(group);
						group.getContacts().add(e);
						getHibernateTemplate().persist(group);
					}					
			    }
				
				e.setFirstName(e_tmp.getFirstName());
				e.setLastName(e_tmp.getLastName());
				
				e.setEmail(e_tmp.getEmail());
				e.getAddress().setStreet(e_tmp.getAddress().getStreet());
				e.getAddress().setZip(e_tmp.getAddress().getZip());
				e.getAddress().setCity(e_tmp.getAddress().getCity());
				e.getAddress().setCountry(e_tmp.getAddress().getCountry());

				for (Iterator<PhoneNumber> iterator = e.getPhoneNumbers().iterator(); 
						iterator.hasNext();) {
					PhoneNumber p = iterator.next();
					
					for (Iterator<PhoneNumber> iterator2 = e_tmp.getPhoneNumbers().iterator(); 
							iterator2.hasNext();)
					{
						PhoneNumber p2 = iterator2.next();

						if (p.getPhoneKind().equals(p2.getPhoneKind()))
						{
							p.setPhoneNumber(p2.getPhoneNumber());
						}
					}
				}
				
				getHibernateTemplate().flush();
			}
			
			getHibernateTemplate().saveOrUpdate(e);
		}
		
		return true;		
	}
	
	@SuppressWarnings("unchecked")
	public Object searchContact(Contact contact)
	{
		String requestQuery = new String("from Contact where firstName = '" + contact.getFirstName() 
						+ "' and lastName = '" + contact.getLastName() + "'"
						);
		
		List<Contact> list = (List<Contact>) getHibernateTemplate().find(requestQuery);
		
		if (list == null || list.size() < 1)
		{
			return null;
		}
		
		Contact c = list.get(0);
		
		Hibernate.initialize(c);
		Hibernate.initialize(c.address);
		Hibernate.initialize(c.phoneNumbers);		
		Hibernate.initialize(c.books);
		
		return c;
	}
	
	public List<Object> testHQL2 (String requestQuery)
	{
		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>) getHibernateTemplate().find(requestQuery);
		
		if (list == null)
		{
			return null;
		}
		
		for (Iterator<Object> iterator = list.iterator(); iterator.hasNext();)
		{
			Object[] contacts = (Object[])(iterator.next());
							
			if (contacts.length > 0)
			{	
				for (int i = 0; i < contacts.length; i++)
				{
					Contact c_tmp = (Contact)contacts[i];
					c_tmp.getAddress().getStreet();
				}
			}
		}
				
		return list;
	}
	
	public List<Object> testHQL (String requestQuery)
	{
		@SuppressWarnings("unchecked")
		List<Object> list =(List<Object>) getHibernateTemplate().find(requestQuery);
		
		if (list == null)
		{
			return null;
		}
		
		
		return list;
	}
	
	public List<Object> testCriteria (String street, String zip, String city, String country)
	{
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Contact.class)
				.addOrder(Order.asc("id"))
				.createCriteria("address");
		
		if (street != null && street.length() >= 1)
			criteria.add(Restrictions.eq("street", street));
				
		if (zip != null && zip.length() >= 1)
			criteria.add(Restrictions.eq("zip", zip));
		
		if (city != null && city.length() >= 1)
			criteria.add(Restrictions.eq("city", city));
		
		if (country != null && country.length() >= 1)
			criteria.add(Restrictions.eq("country", country));
		
		criteria.setFetchMode("address", FetchMode.JOIN);
		
		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>) getHibernateTemplate().findByCriteria(criteria);
		
		if (list == null)
		{
			return null;
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> testCriteriaExample ()
	{
		Contact c = new Contact();
		c.setEmail("");
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Contact.class)
			.add(Restrictions.eqProperty("street", "street"))
			.add(Example.create(c))
			.addOrder(Order.asc("id"));
		
		List<Object> list = (List<Object>) getHibernateTemplate().findByCriteria(criteria);
		
		if (list == null)
		{
			return null;
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getGroups(){
		String requestQuery = new String("from ContactGroup"
		);

		List<Object> list = (List<Object>) getHibernateTemplate().find(requestQuery);

		if (list == null || list.size() < 1)
		{
			return null;
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> searchFirstName(String firstName){
		String requestQuery = new String("from Contact where firstName = '" + firstName.toUpperCase() + "'");

		List<Object> list = (List<Object>) getHibernateTemplate().find(requestQuery);

		if (list == null || list.size() < 1)
		{
			return null;
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> searchLastName(String lastName){
		String requestQuery = new String("from Contact where lastName = '" + lastName.toUpperCase() + "'");

		List<Object> list = (List<Object>) getHibernateTemplate().find(requestQuery);

		if (list == null || list.size() < 1)
		{
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

		List<Object> list = (List<Object>) getHibernateTemplate().find(requestQuery);

		if (list == null || list.size() < 1)
		{
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

		List<Object> list = (List<Object>) getHibernateTemplate().find(requestQuery);

		if (list == null || list.size() < 1)
		{
			return null;
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> searchAddress(Address address){
		String requestQuery = new String("from Contact as c where ");
		
		int length = requestQuery.length();
		
		if (requestQuery.length() == length && address.getStreet().length() > 0){
			requestQuery += "c.address.street = '" + address.street.toUpperCase() + "'";
		}
		
		if (requestQuery.length() == length && address.getZip().length() > 0){
			requestQuery += "c.address.zip = '" + address.zip.toUpperCase() + "'";
		}else if (address.getZip().length() > 0){
			requestQuery += " and c.address.zip = '" + address.zip.toUpperCase() + "'";
		}
		
		if (requestQuery.length() == length && address.getCity().length() > 0){
			requestQuery += "c.address.city = '" + address.city.toUpperCase() + "'";
		}else if (address.getCity().length() > 0){
			requestQuery += " and c.address.city = '" + address.city.toUpperCase() + "'";
		}
		
		if (requestQuery.length() == length && address.getCountry().length() > 0){
			requestQuery += "c.address.country = '" + address.country.toUpperCase() + "'";
		}else if (address.getCountry().length() > 0){
			requestQuery += " and c.address.country = '" + address.country.toUpperCase() + "'" ;
		}
		
		List<Object> list = (List<Object>) getHibernateTemplate().find(requestQuery);

		if (list == null || list.size() < 1)
		{
			return null;
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> searchNumber(String number){
		String requestQuery = new String("from PhoneNumber as p where p.phoneNumber = '" + number + "'");
		
		List<Object> list = (List<Object>) getHibernateTemplate().find(requestQuery);

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
		
		List<Object> list = (List<Object>) getHibernateTemplate().find(requestQuery);

		if (list == null || list.size() < 1)
		{
			return null;
		}
		
		return list;
	}
}
