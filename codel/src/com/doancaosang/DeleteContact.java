package com.doancaosang;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
//		response.getWriter().append("Served at: ").append(request.getContextPath());
			long id = Long.parseLong(request.getParameter("id"));
			DAOContact daoContact = new DAOContact();
			daoContact.deleteContact(id);
			
			response.getWriter().append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\">"
					+ "<title>DeleteContact OK</title>"
					+ "</head><body><h1>Contact removed</h1>"
					+ id + "</br>"
					+ "<a href=\"removeContact.jsp\">Retour</a>"
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
