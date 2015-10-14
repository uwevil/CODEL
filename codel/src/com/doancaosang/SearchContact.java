package com.doancaosang;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import domain.Contact;
import domain.ContactGroup;
import domain.DAOContact;
import domain.Entreprise;
import domain.PhoneNumber;

/**
 * Servlet implementation class SearchContact
 */
@WebServlet("/SearchContact")
public class SearchContact extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchContact() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(false);
		
		if (session.getAttribute("authenticated") == null)
		{
			session.invalidate();
			response.sendRedirect("login.html");
			return;
		}
				
		String firstName = request.getParameter("firstName").toUpperCase();
		String lastName = request.getParameter("lastName").toUpperCase();
		/*
		String email = request.getParameter("email");
				
		if (email.length() > 1 && !email.matches("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[_a-zA-Z0-9-]+(\\.[a-zA-Z0-9]+)+$"))
		{
			response.sendRedirect("addContact.jsp");
			return;
		}
		
		String street = request.getParameter("street");
		String city = request.getParameter("city");
		String zip = request.getParameter("zip");
		String country = request.getParameter("country");
		
		String mobileNumber = request.getParameter("mobileNumber");
		String homeNumber = request.getParameter("homeNumber");
		String faxNumber = request.getParameter("faxNumber");
		
		String[] checkboxes = request.getParameterValues("group");

		String numSiret = request.getParameter("numSiret");
		*/
		
		if (firstName.length() < 1)
		{
			response.sendRedirect("addContact.jsp");
			return;
		}
				
		if (lastName.length() < 1)
		{
			response.sendRedirect("addContact.jsp");
			return;
		}
		
		Contact contact = new Contact();
		contact.setFirstName(firstName);
		contact.setLastName(lastName);
		
	//	DAOContact daoContact = new DAOContact();
		
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		DAOContact daoContact = (DAOContact) context.getBean("springDAOContactID");
		
		Object c = daoContact.searchContact(contact);
		
		String s = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
				+ "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
				+ "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
				+ "<link rel=\"stylesheet\" href=\"cssmenu/styles.css\">"
				+ "	<link rel=\"stylesheet\" href=\"cssaccueil/style.css\">"
				+ "<script src=\"http://code.jquery.com/jquery-latest.min.js\" type=\"text/javascript\"></script>"
				+ "<script src=\"cssmenu/script.js\"></script>"
				+ "<title>Search contact</title>";
		
		String s2 = "</head><body><div id='cssmenu'><ul>"
				+ "<li><a href='accueil.jsp'>Home</a></li>"
				+ "<li class='active'><a href='searchContact.jsp'>Search</a></li>"
				+ "<li><a href='addContact.jsp'>Add</a></li>"
				+ "<li><a href='updateContact.jsp'>Update</a></li>"
				+ "<li><a href='removeContact.jsp'>Remove</a></li>"
				+ "<li class=\"testRequest\"><a href='testRequest.jsp'>Test request</a></li>"
				+ "<li class='logout'><a href='LogoutServlet'>Log out</a></li>"
				+ "</ul>"
				+"</div>";
		
		if (c == null)
		{
			response.getWriter().append(s + s2
					+ "<h3>Contact not found</h3>"
					+ "</body></html>");
		}
		else
		{
			if (c.getClass().getName().contains("Entreprise"))
			{
				Entreprise e = (Entreprise) c;
				response.getWriter().append(s + s2
						+ "<h3>Contact found</h3>"
						+ "<table border=\"1\">"
						+ "<tr><th>First name</th><th>" + e.getFirstName() + "</th></tr>" 
						+ "<tr><th>Last name</th><th>" + e.getLastName() + "</th></tr>" 
						+ "<tr><th>Numero SIRET</th><th>" + e.getNumSiret() + "</th></tr>");
				
				Set<PhoneNumber> phoneNumbers = e.getPhoneNumbers();
				
				for (Iterator<PhoneNumber> iterator = phoneNumbers.iterator(); iterator.hasNext();)
				{
					PhoneNumber p = iterator.next();
					if (p.getPhoneKind().equals("mobileNumber"))
					{
						response.getWriter().append("<tr><th>Mobile number</th><th>" + p.getPhoneNumber() + "</th>");
					}
					else if (p.getPhoneKind().equals("homeNumber"))
					{
						response.getWriter().append("<tr><th>Home number</th><th>" + p.getPhoneNumber() + "</th>");
					}
					else
					{
						response.getWriter().append("<tr><th>Fax</th><th>" + p.getPhoneNumber() + "</th>");
					}
				}
					
				response.getWriter().append("<tr><th>Email</th><th>" + e.getEmail() + "</th></tr>" 
						+ "<tr><th>Street</th><th>" + e.getAddress().getStreet() + "</th></tr>" 
						+ "<tr><th>Zip</th><th>" + e.getAddress().getZip() + "</th></tr>" 
						+ "<tr><th>City</th><th>" + e.getAddress().getCity() + "</th></tr>" 
						+ "<tr><th>Country</th><th>" + e.getAddress().getCountry() + "</th></tr>"
						+ "<tr><th>Group</th><th><table>");
				
				Set<ContactGroup> contactGroups = e.getBooks();

				for (Iterator<ContactGroup> iterator = contactGroups.iterator(); iterator.hasNext();)
				{
			    	response.getWriter().append("<tr><th>" + iterator.next().getGroupName() + "<th></tr>");
				}
				
				response.getWriter().append("</table></th></tr></table>");

				response.getWriter().append("</body></html>");	
			}
			else
			{
				Contact e = (Contact) c;
				response.getWriter().append(s + s2
						+ "<h3>Contact found</h3>"
						+ "<table border=\"1\">"
						+ "<tr><th>First name</th><th>" + e.getFirstName() + "</th></tr>" 
						+ "<tr><th>Last name</th><th>" + e.getLastName() + "</th></tr>");
				
				Set<PhoneNumber> phoneNumbers = e.getPhoneNumbers();
				
				for (Iterator<PhoneNumber> iterator = phoneNumbers.iterator(); iterator.hasNext();)
				{
					PhoneNumber p = iterator.next();
					if (p.getPhoneKind().equals("mobileNumber"))
					{
						response.getWriter().append("<tr><th>Mobile number</th><th>" + p.getPhoneNumber() + "</th>");
					}
					else if (p.getPhoneKind().equals("homeNumber"))
					{
						response.getWriter().append("<tr><th>Home number</th><th>" + p.getPhoneNumber() + "</th>");
					}
					else
					{
						response.getWriter().append("<tr><th>Fax</th><th>" + p.getPhoneNumber() + "</th>");
					}
				}
				
				response.getWriter().append("<tr><th>Email</th><th>" + e.getEmail() + "</th></tr>" 
						+ "<tr><th>Street</th><th>" + e.getAddress().getStreet() + "</th></tr>" 
						+ "<tr><th>Zip</th><th>" + e.getAddress().getZip() + "</th></tr>" 
						+ "<tr><th>City</th><th>" + e.getAddress().getCity() + "</th></tr>" 
						+ "<tr><th>Country</th><th>" + e.getAddress().getCountry() + "</th></tr>"
						+ "<tr><th>Group</th><th><table>");
				
				Set<ContactGroup> contactGroups = e.getBooks();

				for (Iterator<ContactGroup> iterator = contactGroups.iterator(); iterator.hasNext();)
				{
			    	response.getWriter().append("<tr><th>" + iterator.next().getGroupName() + "<th></tr>");
				}
				
				response.getWriter().append("</table></th></tr></table>");

				response.getWriter().append("</body></html>");	
			}
		}
		
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
