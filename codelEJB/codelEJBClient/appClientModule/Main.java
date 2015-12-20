import java.util.Iterator;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import domain.Address;
import domain.Contact;
import domain.ContactGroup;
import domain.Entreprise;
import domain.PhoneNumber;
import service.ContactServiceRemote;

public class Main {
	
	public static void main(String[] args) {
		try {
		/*	Properties p = new Properties();
			p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			p.put(Context.URL_PKG_PREFIXES, "jboss.naming:org.jnp.interfaces");
			p.put(Context.PROVIDER_URL, "localhost:1099");
			
		    Context context = new InitialContext(p);
		*/
			 Context context = new InitialContext();
		    ContactServiceRemote beanRemote = (ContactServiceRemote) context.lookup("service");
		    System.out.println(beanRemote.welcome());
		    
		    Contact c = new Contact("a", "b", "a@c.d");
		    c.setAddress(new Address("street", "city", "zip", "country"));
		    PhoneNumber mobile = new PhoneNumber("mobile", "111");
		    PhoneNumber fax = new PhoneNumber("fax", "222");

		    mobile.setContact(c);
		    fax.setContact(c);

		    c.getPhoneNumbers().add(mobile);
		    c.getPhoneNumbers().add(fax);

		    ContactGroup g = new ContactGroup("Amis");
		    g.getContacts().add(c);
		    c.getBooks().add(g);
		    
		    g = new ContactGroup("Famille");
		    g.getContacts().add(c);
		    c.getBooks().add(g);
		    
		    if (beanRemote.addContact(c)){
		    	c.setId(1);
			    System.out.println(beanRemote.searchContact(c).getEmail());
			    System.out.println(beanRemote.searchContact(c).getAddress().getStreet());
			    
			    Set<PhoneNumber> phoneNumbers = (Set<PhoneNumber>) beanRemote.searchContact(c).getPhoneNumbers();
			    for (Iterator<PhoneNumber> iterator = phoneNumbers.iterator(); iterator.hasNext();){
			    	PhoneNumber p = iterator.next();
			    	System.out.println(p.getPhoneKind() + " : " + p.getPhoneNumber());
			    }
			    
			    Set<ContactGroup> books = beanRemote.searchContact(c).getBooks();
			    for (Iterator<ContactGroup> iterator = books.iterator(); iterator.hasNext();){
			    	ContactGroup tmp = iterator.next();
			    	System.out.println(tmp.getGroupName());
			    }
		    }else{
		    	System.out.println(c.getFirstName() + " " + c.getLastName() + " existed");
		    }
		    
		    c = new Contact("aaaa", "bbbbb", "a@c.d");
		  
		    g = new ContactGroup("Famille");
		    g.getContacts().add(c);
		    c.getBooks().add(g);
		    
		    beanRemote.addContact(c);
		    
		    System.out.println("aaddd contact 2 OK");
		    
		    c = new Contact("ddddd", "eeeee", "a@c.d");
			  
		    g = new ContactGroup("Amis");
		    g.getContacts().add(c);
		    c.getBooks().add(g);
		    g = new ContactGroup("Famille");
		    g.getContacts().add(c);
		    c.getBooks().add(g);
		    
		    beanRemote.addContact(c);
		    
		    System.out.println("aaddd contact 3 OK");
		    
		    
		    Entreprise e = new Entreprise(1234567);
		    e.setFirstName("e");
		    e.setLastName("eee");
		    e.setEmail("e@e.c");
		    
		    e.setAddress(new Address());
		    
		    mobile = new PhoneNumber("mobile", "");
		    fax = new PhoneNumber("fax", "");

		    mobile.setContact(e);
		    fax.setContact(e);

		    e.getPhoneNumbers().add(mobile);
		    e.getPhoneNumbers().add(fax);
		    
		    g = new ContactGroup("Amis");
		    g.getContacts().add(e);
		    e.getBooks().add(g);
		    
		    g = new ContactGroup("Famille");
		    g.getContacts().add(e);
		    e.getBooks().add(g);
		    
		    g = new ContactGroup("Entreprise");
		    g.getContacts().add(e);
		    e.getBooks().add(g);
		    
		    if (!beanRemote.addContact(e)){
		    	System.out.println(e.getFirstName() + " " + e.getLastName() + " existed");
		    }

		    beanRemote.deleteContact(c);
		    System.out.println("OKK");
		    beanRemote.deleteContact(new Contact("1", "z", ""));
		    System.out.println("OKKK2");
		} catch (NamingException e) {
	         e.printStackTrace();
	    }
	}
}