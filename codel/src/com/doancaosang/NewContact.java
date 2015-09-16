package com.doancaosang;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		if (request.getParameter("id").length() < 1)
		{
			response.sendRedirect("addContact.jsp");
			return;
		}
		
		long id;
		try 
		{
			id = Long.parseLong(request.getParameter("id"));
		}
		catch (NumberFormatException e)
		{
			response.sendRedirect("addContact.jsp");
			return;
		}
		
		String firstName = request.getParameter("firstName");
		
		if (firstName.length() < 1)
		{
			response.sendRedirect("addContact.jsp");
			return;
		}
		
		String lastName = request.getParameter("lastName");
		
		if (lastName.length() < 1)
		{
			response.sendRedirect("addContact.jsp");
			return;
		}
		
		String email = request.getParameter("email");
				
		if (!email.matches("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)+$"))
		{
			response.sendRedirect("addContact.jsp");
			return;
		}
		
		DAOContact daoContact = new DAOContact();
		daoContact.addContact(id, firstName, lastName, email);
		
		response.getWriter().append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\">"
				+ "<title>AddContact OK</title>"
				+ "</head><body><h1>Contact added</h1>"
				+ id + "</br>"
				+ firstName + "</br>"
				+ lastName + "</br>"
				+ email + "</br>"
				+ "<a href=\"addContact.jsp\">"
				+ "Retour</a>"
				+ "</body></html>");
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
