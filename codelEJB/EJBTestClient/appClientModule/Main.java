import java.util.Properties;

import javax.naming.*;

import domain.ContactServiceRemote;

public class Main {
	
	public static void main(String[] args) {
		try {
			Properties p = new Properties();
			p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			p.put(Context.URL_PKG_PREFIXES, "jboss.naming:org.jnp.interfaces");
			p.put(Context.PROVIDER_URL, "localhost:1099");
			
		    Context context = new InitialContext(p);
		        
		    ContactServiceRemote beanRemote = (ContactServiceRemote) context.lookup("ServiceBean");
		    System.out.println(beanRemote.welcome());
		} catch (NamingException e) {
	         e.printStackTrace();
	    }
	}

}