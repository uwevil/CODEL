import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import domain.Contact;
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
		    
		    beanRemote.addContact(c);
		    c.setId(1);
		    System.out.println(beanRemote.searchContact(c).getEmail());
		} catch (NamingException e) {
	         e.printStackTrace();
	    }
	}
}