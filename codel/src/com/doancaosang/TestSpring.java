package com.doancaosang;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import domain.Address;
import domain.Contact;
import domain.ContactGroup;
import domain.DAOContact;
import domain.Entreprise;
import domain.PhoneNumber;

/**
 * Servlet implementation class TestSpring
 */
@WebServlet("/TestSpring")
public class TestSpring extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestSpring() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		
		HttpSession session = request.getSession(false);
		
		if (session == null)
		{
			response.sendRedirect("login.html");
			return;
		}
		
		if (session.getAttribute("authenticated") == null)
		{
			session.invalidate();
			response.sendRedirect("login.html");
			return;
		}
		
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		DAOContact daoContact = (DAOContact) context.getBean("springDAOContactID");

		response.getWriter().append(Header.header
				+ "<title>Test Spring</title>" 
				+ Header.menu_testSpring + "<h3>Spring</h3>");
		
		for (int i = 0; i < 2; i++){
			String name = "springContactID" + i;
			System.out.print(name + " ");
			Contact c = (Contact) context.getBean(name);
			Contact c_tmp = new Contact(c.getFirstName(), c.getLastName(), c.getEmail());
			
			System.out.println(c);
			System.out.println("First name : " + c.getFirstName());
			System.out.println("Last name : " + c.getLastName());
			System.out.println("Email : " + c.getEmail());
			
			for (Iterator<PhoneNumber> iterator = c.getPhoneNumbers().iterator(); iterator.hasNext();){
				PhoneNumber p = iterator.next();
				PhoneNumber p_tmp = new PhoneNumber(p.getPhoneKind(), p.getPhoneNumber());
				p_tmp.setContact(c_tmp);
				c_tmp.getPhoneNumbers().add(p_tmp);
				
				System.out.println(p.getPhoneKind() + " : " + p.getPhoneNumber());
			}
			
			System.out.println("Street : " + c.getAddress().getStreet());
			System.out.println("Zip : " + c.getAddress().getZip());
			System.out.println("City : " + c.getAddress().getCity());
			System.out.println("Country : " + c.getAddress().getCountry());
			c_tmp.setAddress(new Address(c.getAddress().getStreet(), c.getAddress().getCity(), 
					c.getAddress().getZip(), c.getAddress().getCountry()));
			
			System.out.println("Group : ");
			for (Iterator<ContactGroup> iterator = c.getBooks().iterator(); iterator.hasNext();){
				ContactGroup g = iterator.next();
				ContactGroup g_tmp = new ContactGroup(g.getGroupName());
				g_tmp.getContacts().add(c_tmp);
				c_tmp.getBooks().add(g_tmp);
				System.out.println("  " + g.getGroupName() + " " + g);
				
				for (Iterator<Contact> iterator2 = g.getContacts().iterator(); iterator2.hasNext();){
					Contact c1 = iterator2.next();
					System.out.println("   Contact:     " + c1);
				}
			}
			System.out.println();
			daoContact.addContact(c_tmp);

		}
		
		String name = "springEntrepriseID2";
		System.out.print(name + " ");
		Entreprise c = (Entreprise) context.getBean(name);
		
		Entreprise c_tmp = new Entreprise(c.getNumSiret());
		c_tmp.setFirstName(c.getFirstName());
		c_tmp.setLastName(c.getLastName());
		c_tmp.setEmail(c.getEmail());

		System.out.println(c);
		System.out.println("First name : " + c.getFirstName());
		System.out.println("Last name : " + c.getLastName());
		System.out.println("SIRET : " + c.getNumSiret());
		System.out.println("Email : " + c.getEmail());
		
		for (Iterator<PhoneNumber> iterator = c.getPhoneNumbers().iterator(); iterator.hasNext();){
			PhoneNumber p = iterator.next();
			PhoneNumber p_tmp = new PhoneNumber(p.getPhoneKind(), p.getPhoneNumber());
			p_tmp.setContact(c_tmp);
			c_tmp.getPhoneNumbers().add(p_tmp);
			
			System.out.println(p.getPhoneKind() + " : " + p.getPhoneNumber());
		}
		
		System.out.println("Street : " + c.getAddress().getStreet());
		System.out.println("Zip : " + c.getAddress().getZip());
		System.out.println("City : " + c.getAddress().getCity());
		System.out.println("Country : " + c.getAddress().getCountry());
		c_tmp.setAddress(new Address(c.getAddress().getStreet(), c.getAddress().getCity(), 
				c.getAddress().getZip(), c.getAddress().getCountry()));
		
		System.out.println("Group : ");
		for (Iterator<ContactGroup> iterator = c.getBooks().iterator(); iterator.hasNext();){
			ContactGroup g = iterator.next();
			ContactGroup g_tmp = new ContactGroup(g.getGroupName());
			g_tmp.getContacts().add(c_tmp);
			c_tmp.getBooks().add(g_tmp);
			
			System.out.println("  " + g.getGroupName() + " " + g);
			
			for (Iterator<Contact> iterator2 = g.getContacts().iterator(); iterator2.hasNext();){
				Contact c1 = iterator2.next();
				System.out.println("   Contact:     " + c1);
			}
		}
		System.out.println();
		daoContact.addContact(c_tmp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
