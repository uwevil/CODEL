package com.doancaosang;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
 * Servlet implementation class UpdateContactSuite
 */
@WebServlet("/UpdateContactSuite")
public class UpdateContactSuite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateContactSuite() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
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
		
		String firstName = request.getParameter("firstName").toUpperCase();
		
		if (firstName.length() < 1)
		{
			response.sendRedirect("updateContact.jsp");
			return;
		}
		
		String lastName = request.getParameter("lastName").toUpperCase();
		
		if (lastName.length() < 1)
		{
			response.sendRedirect("updateContact.jsp");
			return;
		}
		
		String email = request.getParameter("email").toUpperCase();
				
		if (email.length() > 0 && 
				!email.matches("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[_a-zA-Z0-9-]+(\\.[a-zA-Z0-9]+)+$"))
		{
			response.sendRedirect("updateContact.jsp");
			return;
		}
		
		String street = request.getParameter("street").toUpperCase();
		String city = request.getParameter("city").toUpperCase();
		String zip = request.getParameter("zip").toUpperCase();
		String country = request.getParameter("country").toUpperCase();
				
		String mobileNumber = request.getParameter("mobileNumber");
		String homeNumber = request.getParameter("homeNumber");
		String faxNumber = request.getParameter("faxNumber");
		
		if (!mobileNumber.matches("^[0-9]*$"))
			mobileNumber = new String("");
				
		if (!homeNumber.matches("^[0-9]*$"))
			homeNumber = new String("");
				
		if (!faxNumber.matches("^[0-9]*$"))
			faxNumber = new String("");
		
		String[] checkboxes = request.getParameterValues("group");
				
		Object contact = session.getAttribute("contact");

		boolean ok = true;		
		
		if (contact.getClass().getName().contains("Entreprise"))
		{
			String numSiret = request.getParameter("numSiret");

			Entreprise e = new Entreprise();
			
			if (!numSiret.matches("^[0-9]*$")){
				e.setNumSiret(((Entreprise) contact).getNumSiret());
			}else{
				e.setNumSiret(Long.parseLong(numSiret));
			}
			
			e.setVersion(((Entreprise) contact).getVersion());
			
			e.setFirstName(firstName);
			e.setLastName(lastName);
			e.setEmail(email);
			
			e.setAddress(new Address());
			e.getAddress().setStreet(street);
			e.getAddress().setCity(city);
			e.getAddress().setZip(zip);
			e.getAddress().setCountry(country);
			
			e.getPhoneNumbers().add(new PhoneNumber("MOBILENUMBER", mobileNumber));
			e.getPhoneNumbers().add(new PhoneNumber("HOMENUMBER", homeNumber));
			e.getPhoneNumbers().add(new PhoneNumber("FAXNUMBER", faxNumber));

			if (checkboxes != null) {
				Set<ContactGroup> contactGroups = new HashSet<ContactGroup>();
				e.setBooks(contactGroups);
				
			    for (int i = 0; i < checkboxes.length; ++i) { 
			    	ContactGroup g = new ContactGroup(checkboxes[i]);
			    	g.getContacts().add(e);
			        e.getBooks().add(g);
			    }
			}
			else
			{
				Set<ContactGroup> contactGroups = new HashSet<ContactGroup>();
				e.setBooks(contactGroups);
			}
			
			String newGroup = request.getParameter("newGroup");
			if (newGroup.length() >= 1)
			{
				ContactGroup g = new ContactGroup(newGroup);
				g.getContacts().add(e);
				e.getBooks().add(g);
			}

	//		DAOContact daoContact = new DAOContact();
			
			ApplicationContext context = 
					WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			DAOContact daoContact = (DAOContact) context.getBean("springDAOContactID");
			
			ok = daoContact.updateContact(e, ((Entreprise) contact).getId());
					
		}
		else
		{
			Contact e = new Contact();
			
			e.setVersion(((Contact) contact).getVersion());
			
			e.setFirstName(firstName);
			e.setLastName(lastName);
			e.setEmail(email);
			
			e.setAddress(new Address());
			e.getAddress().setStreet(street);
			e.getAddress().setCity(city);
			e.getAddress().setZip(zip);
			e.getAddress().setCountry(country);
			
			e.getPhoneNumbers().add(new PhoneNumber("MOBILENUMBER", mobileNumber));
			e.getPhoneNumbers().add(new PhoneNumber("HOMENUMBER", homeNumber));
			e.getPhoneNumbers().add(new PhoneNumber("FAXNUMBER", faxNumber));
						 
			if (checkboxes != null) {
				Set<ContactGroup> contactGroups = new HashSet<ContactGroup>();
				e.setBooks(contactGroups);
				
			    for (int i = 0; i < checkboxes.length; ++i) { 
			    	ContactGroup g = new ContactGroup(checkboxes[i]);
			    	g.getContacts().add(e);
			        e.getBooks().add(g);
			    }
			}
			else
			{
				Set<ContactGroup> contactGroups = new HashSet<ContactGroup>();
				e.setBooks(contactGroups);
			}
			
			String newGroup = request.getParameter("newGroup");
			if (newGroup.length() >= 1)
			{
				ContactGroup g = new ContactGroup(newGroup);
				g.getContacts().add(e);
				e.getBooks().add(g);
			}

	//		DAOContact daoContact = new DAOContact();
		
			ApplicationContext context = 
					WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			DAOContact daoContact = (DAOContact) context.getBean("springDAOContactID");
			
			ok = daoContact.updateContact(e, ((Contact) contact).getId());
		}
		
		if (ok)
		{	
			response.getWriter().append(Header.header
					+ "<title>Update Contact</title>"
					+ Header.menu_updateContact
					+ "<h3>Contact added</h3>"
					+ "<table border=\"1\">"
					+ "<tr><th>First name</th><th>" + firstName + "</th></tr>" 
					+ "<tr><th>Last name</th><th>" + lastName + "</th></tr>" 
					);
			
			String numSiret = request.getParameter("numSiret");
			
			if (numSiret != null && numSiret.length() >= 1){
				if (!numSiret.matches("^[0-9]*$")){
					response.getWriter().append("<tr><th>Numero SIRET</th><th>" + ((Entreprise) contact).getNumSiret() + "</th></tr>");
				}else{
					response.getWriter().append("<tr><th>Numero SIRET</th><th>" + Long.parseLong(numSiret) + "</th></tr>");
				}
			}
			
			response.getWriter().append("<tr><th>Mobile number</th><th>" + mobileNumber + "</th>" 
					+ "<tr><th>Home number</th><th>" + homeNumber + "</th>" 
					+ "<tr><th>Fax</th><th>" + faxNumber + "</th></tr>" 
					+ "<tr><th>Email</th><th>" + email + "</th></tr>" 
					+ "<tr><th>Street</th><th>" + street + "</th></tr>" 
					+ "<tr><th>Zip</th><th>" + zip + "</th></tr>" 
					+ "<tr><th>City</th><th>" + city + "</th></tr>" 
					+ "<tr><th>Country</th><th>" + country + "</th></tr>"
					+ "<tr><th>Group</th><th><table>");
			
			if (checkboxes != null) {
			    for (int i = 0; i < checkboxes.length; ++i) { 
			    	response.getWriter().append("<tr><th>" + checkboxes[i] + "<th></tr>");
			    }
			}
			
			String newGroup = request.getParameter("newGroup");
			if (newGroup.length() >= 1)
			{
		    	response.getWriter().append("<tr><th>" + newGroup + "<th></tr>");
			}
			
			response.getWriter().append("</table></th></tr></table>");

			response.getWriter().append("</body></html>");	
		}
		else
		{	
			response.getWriter().append(Header.header
					+ "<title>Add contact</title>"
					+ Header.menu_updateContact
					+ "<h2>Contact erreur</h2>"
					+ "<h3>Erreur VERSION<h3>"
					+ "<table border=\"1\">"
					+ "<tr><th>First name</th><th>" + firstName + "</th></tr>" 
					+ "<tr><th>Last name</th><th>" + lastName + "</th></tr>" 
					+"<table>");
			
			response.getWriter().append("</body></html>");
		}
		
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
