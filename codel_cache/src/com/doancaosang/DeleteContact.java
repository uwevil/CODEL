package com.doancaosang;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
		
		/*
		DAOContact daoContact = new DAOContact();
		*/
		
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		DAOContact daoContact = (DAOContact) context.getBean("springDAOContactID");
		
		System.out.println(request.getParameter("submit"));
		daoContact.deleteContact(contact);
			
		response.getWriter().append(Header.header 
				+ "<title>Delete Contact</title>"
				+ Header.menu_deleteContact
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
