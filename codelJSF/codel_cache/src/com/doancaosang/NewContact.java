package com.doancaosang;

import java.io.IOException;
import java.util.Locale;

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
 * Servlet implementation class NewContact
 */
@WebServlet("/NewContact")
public class NewContact extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewContact() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
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
		
		String firstName = request.getParameter("firstName");
		firstName = firstName.toUpperCase(Locale.ROOT);
				
		if (firstName.length() < 1)
		{
			response.sendRedirect("addContact.jsp");
			return;
		}
		
		String lastName = request.getParameter("lastName");
		lastName = lastName.toUpperCase(Locale.ROOT);

		if (lastName.length() < 1)
		{
			response.sendRedirect("addContact.jsp");
			return;
		}
		
		String email = request.getParameter("email");
		email = email.toUpperCase(Locale.ROOT);

		if (email.length() > 1 && !email.matches("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[_a-zA-Z0-9-]+(\\.[a-zA-Z0-9]+)+$"))
		{
			response.sendRedirect("addContact.jsp");
			return;
		}
		
		String street = request.getParameter("street").toUpperCase();
		String city = request.getParameter("city").toUpperCase();
		String zip = request.getParameter("zip").toUpperCase();
		String country = request.getParameter("country").toUpperCase();
				
		String mobileNumber = request.getParameter("mobileNumber").toUpperCase();
		String homeNumber = request.getParameter("homeNumber").toUpperCase();
		String faxNumber = request.getParameter("faxNumber").toUpperCase();
		
		String[] checkboxes = request.getParameterValues("group");

		String numSiret = request.getParameter("numSiret");
		
		Entreprise e = new Entreprise();
		Contact c = new Contact();

		if (numSiret != null && numSiret.length() >= 1)
		{
			Address addr = new Address(street, city, zip, country);
			
			PhoneNumber mNumber = new PhoneNumber("mobileNumber".toUpperCase(), mobileNumber);
			PhoneNumber hNumber = new PhoneNumber("homeNumber".toUpperCase(), homeNumber);
			PhoneNumber fNumber = new PhoneNumber("faxNumber".toUpperCase(), faxNumber);
			
			mNumber.setContact(e);
			hNumber.setContact(e);
			fNumber.setContact(e);
			
			e.setFirstName(firstName);
			e.setLastName(lastName);
			e.setEmail(email);
			e.setAddress(addr);
			e.getPhoneNumbers().add(mNumber);
			e.getPhoneNumbers().add(hNumber);
			e.getPhoneNumbers().add(fNumber);
						 
			if (checkboxes != null) {
			    for (int i = 0; i < checkboxes.length; ++i) { 
			    	ContactGroup g = new ContactGroup(checkboxes[i]);
			    	g.getContacts().add(e);
			        e.getBooks().add(g);
			    }
			}
			
			String newGroup = request.getParameter("newGroup");
			if (newGroup.length() >= 1)
			{
				ContactGroup g = new ContactGroup(newGroup);
				g.getContacts().add(e);
				e.getBooks().add(g);
			}
			
			e.setNumSiret(Long.parseLong(numSiret));
		}
		else
		{
			Address addr = new Address(street, city, zip, country);
			
			PhoneNumber mNumber = new PhoneNumber("mobileNumber".toUpperCase(), mobileNumber);
			PhoneNumber hNumber = new PhoneNumber("homeNumber".toUpperCase(), homeNumber);
			PhoneNumber fNumber = new PhoneNumber("faxNumber".toUpperCase(), faxNumber);
			
			mNumber.setContact(c);
			hNumber.setContact(c);
			fNumber.setContact(c);
			
			c.setFirstName(firstName);
			c.setLastName(lastName);
			c.setEmail(email);
			c.setAddress(addr);
			c.getPhoneNumbers().add(mNumber);
			c.getPhoneNumbers().add(hNumber);
			c.getPhoneNumbers().add(fNumber);
						 
			if (checkboxes != null) {
			    for (int i = 0; i < checkboxes.length; ++i) { 
			    	ContactGroup g = new ContactGroup(checkboxes[i]);
			    	g.getContacts().add(c);
			        c.getBooks().add(g);
			    }
			}
			
			String newGroup = request.getParameter("newGroup");
			if (newGroup.length() >= 1)
			{
				ContactGroup g = new ContactGroup(newGroup);
				g.getContacts().add(c);
				c.getBooks().add(g);
			}
		}
		
	//	DAOContact daoContact = new DAOContact();
		
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		DAOContact daoContact = (DAOContact) context.getBean("springDAOContactID");
		
		boolean ok = false;
		
		if (numSiret.length() >= 1){
			ok = daoContact.addContact(e);
		}else{
			ok = daoContact.addContact(c);
		}
		
		if (ok)
		{	
			response.getWriter().append(Header.header
					+ "<title>Add contact</title>"
					+ Header.menu_newContact
					+ "<h3>Contact added</h3>"
					+ "<table border=\"1\">"
					+ "<tr><th>First name</th><th>" + firstName + "</th></tr>" 
					+ "<tr><th>Last name</th><th>" + lastName + "</th></tr>" 
					+ (numSiret.length() >= 1 ? "<tr><th>Numero SIRET</th><th>" + Long.parseLong(numSiret) + "</th></tr>" : "")
					+ "<tr><th>Mobile number</th><th>" + mobileNumber + "</th>" 
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
					+ Header.menu_newContact
					+ "<h2>Contact existed</h2>"
					+ "<h3>Do you want to update? Go to Update.<h3>"
					+ "<table border=\"1\">"
					+ "<tr><th>First name</th><th>" + firstName + "</th></tr>" 
					+ "<tr><th>Last name</th><th>" + lastName + "</th></tr>" 
					+"<table>");
			
			response.getWriter().append("</body></html>");
		}
		
		
		
		/*
		Enumeration<String> test = request.getParameterNames();
		while (test.hasMoreElements())
		{
			System.out.println(test.nextElement());
		}
		
		String[] checkboxes = request.getParameterValues("group");
		 
		if (checkboxes == null) {
		    // no checkboxes selected
		    System.out.println (" Non Cochée ");// Non cochée 
		} else { 
		    for (int i = 0; i < checkboxes.length; ++i) {   
		        System.out.println("  " + checkboxes[i]);
		    }
		}
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		doGet(request, response);
	}

}
