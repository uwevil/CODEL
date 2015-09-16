package com.doancaosang;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.DAOContact;

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
		
		if (request.getParameter("id").length() < 1)
		{
			response.sendRedirect("updateContact.jsp");
			return;
		}
		
		long id;
		try 
		{
			id = Long.parseLong(request.getParameter("id"));
		}
		catch (NumberFormatException e)
		{
			response.sendRedirect("updateContact.jsp");
			return;
		}
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		DAOContact daoContact = new DAOContact();
		daoContact.updateContact(id, firstName, lastName, email);
		
		response.getWriter().append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\">"
				+ "<title>UpdateContact OK</title>"
				+ "</head><body><h1>Contact updated</h1>"
				+ id + "</br>"
				+ firstName + "</br>"
				+ lastName + "</br>"
				+ email + "</br>"
				+ "<a href=\"updateContact.jsp\">"
				+ "Retour</a>"
				+ "</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
