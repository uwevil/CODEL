package jsf;

import java.io.Serializable;
import java.util.Iterator;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sun.faces.context.SessionMap;

import domain.Address;
import domain.Contact;
import domain.ContactGroup;
import domain.Entreprise;
import domain.PhoneNumber;

@SuppressWarnings("serial")
public class AutoAddJSF implements Serializable{

	private ServiceJSF service;

	public AutoAddJSF() {
		// TODO Auto-generated constructor stub
	}

	public ServiceJSF getService() {
		return service;
	}

	public void setService(ServiceJSF service) {
		this.service = service;
	}

	@SuppressWarnings("unchecked")
	public String add(){
		Contact c = new Contact("ICIER", "PAUL", "PAUL@POLICE.FR");
		c.setAddress(new Address("JEAN JAURES", "LYON", "69000", "FRANCE"));
		
		PhoneNumber p = new PhoneNumber("MOBILENUMBER", "17");
		p.setContact(c);
		c.getPhoneNumbers().add(p);
		
		p = new PhoneNumber("HOMENUMBER", "17");
		p.setContact(c);
		c.getPhoneNumbers().add(p);
		
		ContactGroup g = new ContactGroup("SOS");
		g.getContacts().add(c);
		c.getBooks().add(g);
		
		service.addContact(c);
		
		c = new Contact("POMP", "PIERRE", "PIERRE@POMPIER.FR");
		c.setAddress(new Address("BD ARNAUD", "NANTERRE", "92000", "FRANCE"));
		
		p = new PhoneNumber("MOBILENUMBER", "18");
		p.setContact(c);
		c.getPhoneNumbers().add(p);
		
		p = new PhoneNumber("HOMENUMBER", "18");
		p.setContact(c);
		c.getPhoneNumbers().add(p);
		
		g = new ContactGroup("SOS");
		g.getContacts().add(c);
		c.getBooks().add(g);
		
		g = new ContactGroup("Amis");
		g.getContacts().add(c);
		c.getBooks().add(g);
		
		service.addContact(c);
		
		Entreprise e = new Entreprise(1432112452);
		
		e.setFirstName("AIRBUS");
		e.setLastName("INC");
		e.setEmail("CONTACT@AIRBUS.INC");
		
		e.setAddress(new Address("AIR", "BUS", "0000", ""));
		
		p = new PhoneNumber("MOBILENUMBER", "012");
		p.setContact(e);
		e.getPhoneNumbers().add(p);
		
		p = new PhoneNumber("HOMENUMBER", "345");
		p.setContact(e);
		e.getPhoneNumbers().add(p);
		
		p = new PhoneNumber("FAXNUMBER", "678");
		p.setContact(e);
		e.getPhoneNumbers().add(p);
		
		g = new ContactGroup("Collegues");
		g.getContacts().add(e);
		e.getBooks().add(g);
		
		g = new ContactGroup("Amis");
		g.getContacts().add(e);
		e.getBooks().add(g);
		
		service.addContact(e);
		
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(
						(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext());
		
		for (int i = 0; i < 2; i++){
			String name = "springContactID" + i;
			c = (Contact) context.getBean(name);
			Contact c_tmp = new Contact(c.getFirstName(), c.getLastName(), c.getEmail());
					
			for (Iterator<PhoneNumber> iterator = c.getPhoneNumbers().iterator(); iterator.hasNext();){
				p = iterator.next();
				PhoneNumber p_tmp = new PhoneNumber(p.getPhoneKind(), p.getPhoneNumber());
				p_tmp.setContact(c_tmp);
				c_tmp.getPhoneNumbers().add(p_tmp);
				
			}
			
			c_tmp.setAddress(new Address(c.getAddress().getStreet(), c.getAddress().getCity(), 
					c.getAddress().getZip(), c.getAddress().getCountry()));
			
			for (Iterator<ContactGroup> iterator = c.getBooks().iterator(); iterator.hasNext();){
				g = iterator.next();
				ContactGroup g_tmp = new ContactGroup(g.getGroupName());
				g_tmp.getContacts().add(c_tmp);
				c_tmp.getBooks().add(g_tmp);
			}
			service.addContact(c_tmp);

		}
		
		String name = "springEntrepriseID2";
		c = (Entreprise) context.getBean(name);
		
		Entreprise c_tmp = new Entreprise(((Entreprise) c).getNumSiret());
		c_tmp.setFirstName(c.getFirstName());
		c_tmp.setLastName(c.getLastName());
		c_tmp.setEmail(c.getEmail());

		for (Iterator<PhoneNumber> iterator = c.getPhoneNumbers().iterator(); iterator.hasNext();){
			p = iterator.next();
			PhoneNumber p_tmp = new PhoneNumber(p.getPhoneKind(), p.getPhoneNumber());
			p_tmp.setContact(c_tmp);
			c_tmp.getPhoneNumbers().add(p_tmp);			
		}
		
		c_tmp.setAddress(new Address(c.getAddress().getStreet(), c.getAddress().getCity(), 
				c.getAddress().getZip(), c.getAddress().getCountry()));
		
		for (Iterator<ContactGroup> iterator = c.getBooks().iterator(); iterator.hasNext();){
			g = iterator.next();
			ContactGroup g_tmp = new ContactGroup(g.getGroupName());
			g_tmp.getContacts().add(c_tmp);
			c_tmp.getBooks().add(g_tmp);
		}

		service.addContact(c_tmp);
		
		return "accueilJSF";
	}
}
