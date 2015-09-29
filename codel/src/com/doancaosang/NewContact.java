package com.doancaosang;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.DAOContact;

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
		if (session.getAttribute("authenticated") == null)
		{
			session.invalidate();
			response.sendRedirect("login.html");
			return;
		}
		
		String firstName = request.getParameter("firstName");
		
		if (firstName.length() < 1)
		{
			response.sendRedirect("addContact.jsp");
			return;
		}
		
		String lastName = request.getParameter("lastName");
		
		String email = request.getParameter("email");
				
		if (email.length() > 1 && !email.matches("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[_a-zA-Z0-9-]+(\\.[a-zA-Z0-9]+)+$"))
		{
			response.sendRedirect("addContact.jsp");
			return;
		}
		
		DAOContact daoContact = new DAOContact();
		daoContact.addContact(firstName, lastName, email);
		
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
				+ "gggggg" + "</br>"
				+ firstName + "</br>"
				+ lastName + "</br>"
				+ email + "</br>"
				+ request.getParameter("newGroup").toString() + "</br>"
				+ email + "</br>"
				+ "</body></html>");
		
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
