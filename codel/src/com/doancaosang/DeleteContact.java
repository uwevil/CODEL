package com.doancaosang;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Contact;
import domain.DAOContact;

/**
 * Servlet implementation class DeleteContact
 */
@WebServlet("/DeleteContact")
public class DeleteContact extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteContact() {
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
		
		DAOContact daoContact = new DAOContact();
		daoContact.deleteContact(contact);
			
		String s = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
				+ "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
				+ "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
				+ "<link rel=\"stylesheet\" href=\"cssmenu/styles.css\">"
				+ "	<link rel=\"stylesheet\" href=\"cssaccueil/style.css\">"
				+ "<script src=\"http://code.jquery.com/jquery-latest.min.js\" type=\"text/javascript\"></script>"
				+ "<script src=\"cssmenu/script.js\"></script>"
				+ "<title>Delete contact</title>";
		
		String s2 = "</head><body><div id='cssmenu'><ul>"
				+ "<li><a href='accueil.jsp'>Home</a></li>"
				+ "<li><a href='searchContact.jsp'>Search</a></li>"
				+ "<li><a href='addContact.jsp'>Add</a></li>"
				+ "<li><a href='updateContact.jsp'>Update</a></li>"
				+ "<li class='active'><a href='removeContact.jsp'>Remove</a></li>"
				+ "<li class='logout'><a href='LogoutServlet'>Log out</a></li>"
				+ "</ul>"
				+"</div>";
		
		response.getWriter().append(s + s2
					+ "<h3>Contact deleted</h3>"
					+ "</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
