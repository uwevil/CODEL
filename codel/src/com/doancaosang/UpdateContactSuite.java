package com.doancaosang;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			response.sendRedirect("addContact.jsp");
			return;
		}
		
		String lastName = request.getParameter("lastName").toUpperCase();
		
		if (lastName.length() < 1)
		{
			response.sendRedirect("addContact.jsp");
			return;
		}
		
		String email = request.getParameter("email").toUpperCase();
				
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
				
		Object contact = session.getAttribute("contact");

		boolean ok = true;
		
		if (contact.getClass().getName().contains("Entreprise"))
		{
			String numSiret = request.getParameter("numSiret");

			Entreprise e = (Entreprise)contact;
			
			e.setFirstName(firstName);
			e.setLastName(lastName);
			e.setNumSiret(Long.parseLong(numSiret));
			e.setEmail(email);
			
			e.getAddress().setStreet(street);
			e.getAddress().setCity(city);
			e.getAddress().setZip(zip);
			e.getAddress().setCountry(country);
			
			Set<PhoneNumber> phoneNumbers = e.getPhoneNumbers();
			
			for (Iterator<PhoneNumber> iterator = phoneNumbers.iterator(); iterator.hasNext();)
			{
				PhoneNumber p = iterator.next();
				if (p.getPhoneKind().equals("mobileNumber"))
				{
					p.setPhoneNumber(mobileNumber);
				}
				else if (p.getPhoneKind().equals("homeNumber"))
				{
					p.setPhoneNumber(homeNumber);
				}
				else
				{
					p.setPhoneNumber(faxNumber);
				}
			}
						 
			if (checkboxes != null) {
				Set<ContactGroup> contactGroups = new HashSet<ContactGroup>();
				e.setBooks(contactGroups);
				
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

			DAOContact daoContact = new DAOContact();
			ok = daoContact.updateContact(e);			
		}
		else
		{
			Contact e = (Contact)contact;
			
			e.setFirstName(firstName);
			e.setLastName(lastName);
			e.setEmail(email);
			
			e.getAddress().setStreet(street);
			e.getAddress().setCity(city);
			e.getAddress().setZip(zip);
			e.getAddress().setCountry(country);
			
			Set<PhoneNumber> phoneNumbers = e.getPhoneNumbers();
			
			for (Iterator<PhoneNumber> iterator = phoneNumbers.iterator(); iterator.hasNext();)
			{
				PhoneNumber p = iterator.next();
				if (p.getPhoneKind().equals("mobileNumber"))
				{
					p.setPhoneNumber(mobileNumber);
				}
				else if (p.getPhoneKind().equals("homeNumber"))
				{
					p.setPhoneNumber(homeNumber);
				}
				else
				{
					p.setPhoneNumber(faxNumber);
				}
			}
						 
			if (checkboxes != null) {
				Set<ContactGroup> contactGroups = new HashSet<ContactGroup>();
				e.setBooks(contactGroups);
				
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

			DAOContact daoContact = new DAOContact();
			ok = daoContact.updateContact(e);
		}
		
		if (ok)
		{
			String s = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
					+ "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
					+ "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
					+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
					+ "<link rel=\"stylesheet\" href=\"cssmenu/styles.css\">"
					+ "	<link rel=\"stylesheet\" href=\"cssaccueil/style.css\">"
					+ "<script src=\"http://code.jquery.com/jquery-latest.min.js\" type=\"text/javascript\"></script>"
					+ "<script src=\"cssmenu/script.js\"></script>"
					+ "<title>Add contact</title>";
			
			String s2 = "</head><body><div id='cssmenu'><ul>"
					+ "<li><a href='accueil.jsp'>Home</a></li>"
					+ "<li><a href='searchContact.jsp'>Search</a></li>"
					+ "<li class='active'><a href='addContact.jsp'>Add</a></li>"
					+ "<li><a href='updateContact.jsp'>Update</a></li>"
					+ "<li><a href='removeContact.jsp'>Remove</a></li>"
					+ "<li class='logout'><a href='LogoutServlet'>Log out</a></li>"
					+ "</ul>"
					+"</div>";
			
			response.getWriter().append(s + s2
					+ "<h3>Contact added</h3>"
					+ "<table border=\"1\">"
					+ "<tr><th>First name</th><th>" + firstName + "</th></tr>" 
					+ "<tr><th>Last name</th><th>" + lastName + "</th></tr>" 
					);
			
			String numSiret = request.getParameter("numSiret");
			
			if (numSiret != null && numSiret.length() >= 1)
				response.getWriter().append("<tr><th>Numero SIRET</th><th>" + Long.parseLong(numSiret) + "</th></tr>");
			
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
			
			response.getWriter().append("</table></th></tr></table>");

			response.getWriter().append("</body></html>");	
		}
		else
		{
			String s = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
					+ "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
					+ "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
					+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
					+ "<link rel=\"stylesheet\" href=\"cssmenu/styles.css\">"
					+ "	<link rel=\"stylesheet\" href=\"cssaccueil/style.css\">"
					+ "<script src=\"http://code.jquery.com/jquery-latest.min.js\" type=\"text/javascript\"></script>"
					+ "<script src=\"cssmenu/script.js\"></script>"
					+ "<title>Add contact</title>";
			
			String s2 = "</head><body><div id='cssmenu'><ul>"
					+ "<li><a href='accueil.jsp'>Home</a></li>"
					+ "<li><a href='searchContact.jsp'>Search</a></li>"
					+ "<li class='active'><a href='addContact.jsp'>Add</a></li>"
					+ "<li><a href='updateContact.jsp'>Update</a></li>"
					+ "<li><a href='removeContact.jsp'>Remove</a></li>"
					+ "<li class='logout'><a href='LogoutServlet'>Log out</a></li>"
					+ "</ul>"
					+"</div>";
			
			response.getWriter().append(s + s2
					+ "<h2>Contact existed</h2>"
					+ "<h3>Do you want to update? Go to Update.<h3>"
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
