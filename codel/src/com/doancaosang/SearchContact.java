package com.doancaosang;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.DAOContact;

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
		
		long id = Long.parseLong(request.getParameter("id"));
		
		DAOContact daoContact = new DAOContact();
		daoContact.searchContact(id);
		
		response.getWriter().append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\">"
				+ "<title>SearchContact OK</title>"
				+ "</head><body><h1>Contact found</h1>"
				+ id + "</br>"
				+ "<a href=\"searchContact.jsp\">Retour</a>"
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
