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
 * Servlet implementation class UpdateContact
 */
@WebServlet("/UpdateContact")
public class UpdateContact extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateContact() {
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
		
		if (c == null)
		{
			response.getWriter().append(Header.header 
					+ "<title>Update Contact</title>" 
					+ Header.menu_updateContact
					+ "<h3>Contact not found</h3>"
					+ "</body></html>");
		}
		else
		{			
			if (c.getClass().getName().contains("Entreprise"))
			{
				Entreprise e = (Entreprise) c;
				response.getWriter().append(Header.header 
						+ "<title>Update Contact</title>" 
						+ Header.menu_updateContact
						+ "<h3>Contact found</h3>"
						+ "<form method=\"post\" action=\"UpdateContactSuite\">"
						+ "<table border=\"0\">"
						+ "<tr><th>First name</th><th>" + "<input type=\"text\" name=\"firstName\" value=\""
						+ e.getFirstName() + "\" size=\"25\" ></th></tr>"
						+ "<tr><th>Last name</th><th>" + "<input type=\"text\" name=\"lastName\" value=\""
						+ e.getLastName() + "\" size=\"25\">" + "</th></tr>" 
						+ "<tr><th>Numero SIRET</th><th>" + "<input type=\"text\" name=\"numSiret\" value=\""
						+ e.getNumSiret() + "\" size=\"25\">" + "</th></tr>");
				
				Set<PhoneNumber> phoneNumbers = e.getPhoneNumbers();
				
				for (Iterator<PhoneNumber> iterator = phoneNumbers.iterator(); iterator.hasNext();)
				{
					PhoneNumber p = iterator.next();
					if (p.getPhoneKind().equals("mobileNumber".toUpperCase()))
					{
						response.getWriter().append("<tr><th>Mobile number</th><th>" 
								+ "<input type=\"text\" name=\"mobileNumber\" value=\""
								+ p.getPhoneNumber() + "\" size=\"25\">" + "</th></tr>");
					}
					else if (p.getPhoneKind().equals("homeNumber".toUpperCase()))
					{
						response.getWriter().append("<tr><th>Home number</th><th>" 
								+ "<input type=\"text\" name=\"homeNumber\" value=\""
								+ p.getPhoneNumber() + "\" size=\"25\">" + "</th></tr>");
					}
					else
					{
						response.getWriter().append("<tr><th>Fax</th><th>" 
								+ "<input type=\"text\" name=\"faxNumber\" value=\""
								+ p.getPhoneNumber() + "\" size=\"25\">" + "</th></tr>");
					}
				}
					
				response.getWriter().append(
						"<tr><th>Email</th><th>" 
							+ "<input type=\"text\" name=\"email\" value=\""
							+ e.getEmail() + "\" size=\"25\"></th></tr>" 
						+ "<tr><th>Street</th><th>" 
							+ "<input type=\"text\" name=\"street\" value=\""
							+ e.getAddress().getStreet() + "\" size=\"25\"></th></tr>" 
						+ "<tr><th>Zip</th><th>" 
							+ "<input type=\"text\" name=\"zip\" value=\""
							+ e.getAddress().getZip() + "\" size=\"25\"></th></tr>"  
						+ "<tr><th>City</th><th>" 
							+ "<input type=\"text\" name=\"city\" value=\""
							+ e.getAddress().getCity() + "\" size=\"25\"></th></tr>"  
						+ "<tr><th>Country</th><th>" 
							+ "<input type=\"text\" name=\"country\" value=\""
							+ e.getAddress().getCountry() + "\" size=\"25\"></th></tr>" 
						+ "</table>"
						+ "<p><div class=\"dropdown\"><h4>Groupe</h4><ul>"
						);
				
				Set<ContactGroup> contactGroups = e.getBooks();

				String[] groups = new String[3];
				groups[0] = new String("Amis");
				groups[1] = new String("Collegues");
				groups[2] = new String("Famille");

				int[] existGroups = {0,0,0};
				
				for (Iterator<ContactGroup> iterator = contactGroups.iterator(); iterator.hasNext();)
				{
					String groupName = iterator.next().getGroupName();
					if (groupName.equals(groups[0]))
						existGroups[0] = 1;
					if (groupName.equals(groups[1]))
						existGroups[1] = 1;
					if (groupName.equals(groups[2]))
						existGroups[2] = 1;
						
					response.getWriter().append("<li><input type=\"checkbox\" name=\"group\" value=\""
							+ groupName + "\" checked/>" + groupName + "</li>");
				}
				
				if (existGroups[0] != 1)
					response.getWriter().append("<li><input type=\"checkbox\" name=\"group\" value=\""
							+ groups[0] + "\"/>" + groups[0] + "</li>");
				if (existGroups[1] != 1)
					response.getWriter().append("<li><input type=\"checkbox\" name=\"group\" value=\""
							+ groups[1] + "\"/>" + groups[1] + "</li>");
				if (existGroups[2] != 1)
					response.getWriter().append("<li><input type=\"checkbox\" name=\"group\" value=\""
							+ groups[2] + "\"/>" + groups[2] + "</li>");
				
				response.getWriter().append( 
						"<li><input type=\"text\" name=\"newGroup\" laceholder=\"Create New Group\"/></li>"
						+ " </ul></div></p><p>"
						+ "<input type=\"submit\" value=\"Update\"/>"
						+ "<input type=\"reset\" value=\"Reset\"/></form></p>");

				response.getWriter().append("</body></html>");
			}
			else
			{
				Contact e = (Contact) c;
				response.getWriter().append(Header.header 
						+ "<title>Update Contact</title>" 
						+ Header.menu_updateContact
						+ "<h3>Contact found</h3>"
						+ "<form method=\"post\" action=\"UpdateContactSuite\">"
						+ "<table border=\"0\">"
						+ "<tr><th>First name</th><th>" + "<input type=\"text\" name=\"firstName\" value=\""
						+ e.getFirstName() + "\" size=\"25\"></th></tr>"
						+ "<tr><th>Last name</th><th>" + "<input type=\"text\" name=\"lastName\" value=\""
						+ e.getLastName() + "\" size=\"25\">" + "</th></tr>" 
						);
				//  readonly=\"readonly\"
				Set<PhoneNumber> phoneNumbers = e.getPhoneNumbers();
				
				for (Iterator<PhoneNumber> iterator = phoneNumbers.iterator(); iterator.hasNext();)
				{
					PhoneNumber p = iterator.next();
					if (p.getPhoneKind().equals("mobileNumber".toUpperCase()))
					{
						response.getWriter().append("<tr><th>Mobile number</th><th>" 
								+ "<input type=\"text\" name=\"mobileNumber\" value=\""
								+ p.getPhoneNumber() + "\" size=\"25\">" + "</th></tr>");
					}
					else if (p.getPhoneKind().equals("homeNumber".toUpperCase()))
					{
						response.getWriter().append("<tr><th>Home number</th><th>" 
								+ "<input type=\"text\" name=\"homeNumber\" value=\""
								+ p.getPhoneNumber() + "\" size=\"25\">" + "</th></tr>");
					}
					else
					{
						response.getWriter().append("<tr><th>Fax</th><th>" 
								+ "<input type=\"text\" name=\"faxNumber\" value=\""
								+ p.getPhoneNumber() + "\" size=\"25\">" + "</th></tr>");
					}
				}
					
				response.getWriter().append(
						"<tr><th>Email</th><th>" 
							+ "<input type=\"text\" name=\"email\" value=\""
							+ e.getEmail() + "\" size=\"25\"></th></tr>" 
						+ "<tr><th>Street</th><th>" 
							+ "<input type=\"text\" name=\"street\" value=\""
							+ e.getAddress().getStreet() + "\" size=\"25\"></th></tr>" 
						+ "<tr><th>Zip</th><th>" 
							+ "<input type=\"text\" name=\"zip\" value=\""
							+ e.getAddress().getZip() + "\" size=\"25\"></th></tr>"  
						+ "<tr><th>City</th><th>" 
							+ "<input type=\"text\" name=\"city\" value=\""
							+ e.getAddress().getCity() + "\" size=\"25\"></th></tr>"  
						+ "<tr><th>Country</th><th>" 
							+ "<input type=\"text\" name=\"country\" value=\""
							+ e.getAddress().getCountry() + "\" size=\"25\"></th></tr>" 
						+ "</table>"
						+ "<p><div class=\"dropdown\"><h4>Groupe</h4><ul>"
						);
				
				Set<ContactGroup> contactGroups = e.getBooks();

				String[] groups = new String[3];
				groups[0] = new String("Amis");
				groups[1] = new String("Collegues");
				groups[2] = new String("Famille");

				int[] existGroups = {0,0,0};
				
				for (Iterator<ContactGroup> iterator = contactGroups.iterator(); iterator.hasNext();)
				{
					String groupName = iterator.next().getGroupName();
					if (groupName.equals(groups[0]))
						existGroups[0] = 1;
					if (groupName.equals(groups[1]))
						existGroups[1] = 1;
					if (groupName.equals(groups[2]))
						existGroups[2] = 1;
						
					response.getWriter().append("<li><input type=\"checkbox\" name=\"group\" value=\""
							+ groupName + "\" checked/>" + groupName + "</li>");
				}
				
				if (existGroups[0] != 1)
					response.getWriter().append("<li><input type=\"checkbox\" name=\"group\" value=\""
							+ groups[0] + "\"/>" + groups[0] + "</li>");
				if (existGroups[1] != 1)
					response.getWriter().append("<li><input type=\"checkbox\" name=\"group\" value=\""
							+ groups[1] + "\"/>" + groups[1] + "</li>");
				if (existGroups[2] != 1)
					response.getWriter().append("<li><input type=\"checkbox\" name=\"group\" value=\""
							+ groups[2] + "\"/>" + groups[2] + "</li>");
				
				response.getWriter().append( 
						"<li><input type=\"text\" name=\"newGroup\" laceholder=\"Create New Group\"/></li>"
						+ " </ul></div></p><p>"
						+ "<input type=\"submit\" value=\"Update\"/>"
						+ "<input type=\"reset\" value=\"Reset\"/></form></p>");

				response.getWriter().append("</body></html>");	
			}
			
			
			session.setAttribute("contact", c);
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
