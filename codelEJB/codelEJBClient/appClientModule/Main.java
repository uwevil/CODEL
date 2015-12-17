import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import domain.Address;
import domain.Contact;
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
	//	    c.getPhoneNumbers().add(new PhoneNumber("mobile", "111"));
	//	    c.getPhoneNumbers().add(new PhoneNumber("fax", "222"));

		    beanRemote.addContact(c);
		    c.setId(1);
		    System.out.println(beanRemote.searchContact(c).getEmail());
		    System.out.println(beanRemote.searchContact(c).getAddress().getStreet());
		} catch (NamingException e) {
	         e.printStackTrace();
	    }
	}
}