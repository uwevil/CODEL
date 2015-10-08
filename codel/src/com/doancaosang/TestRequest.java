package com.doancaosang;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Contact;
import domain.DAOContact;

/**
 * Servlet implementation class TestRequest
 */
@WebServlet("/TestRequest")
public class TestRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestRequest() {
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
		
		String s = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" "
				+ "\"http://www.w3.org/TR/html4/loose.dtd\">"
				+ "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
				+ "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
				+ "<link rel=\"stylesheet\" href=\"cssmenu/styles.css\">"
				+ "	<link rel=\"stylesheet\" href=\"cssaccueil/style.css\">"
				+ "	<link rel=\"stylesheet\" href=\"cssAddContact/addContact.css\">"
				+ "<script src=\"http://code.jquery.com/jquery-latest.min.js\" type=\"text/javascript\"></script>"
				+ "<script src=\"cssmenu/script.js\"></script>"
				+ "<title>Add contact</title>";
		
		String s2 = "</head><body><div id='cssmenu'><ul>"
				+ "<li><a href='accueil.jsp'>Home</a></li>"
				+ "<li><a href='searchContact.jsp'>Search</a></li>"
				+ "<li><a href='addContact.jsp'>Add</a></li>"
				+ "<li><a href='updateContact.jsp'>Update</a></li>"
				+ "<li><a href='removeContact.jsp'>Remove</a></li>"
				+ "<li class='active' class=\"testRequest\"><a href='testRequest.jsp'>Test request</a></li>"
				+ "<li class='logout'><a href='LogoutServlet'>Log out</a></li>"
				+ "</ul>"
				+"</div>";
		
		
		Enumeration<String> enumeration = request.getParameterNames();
		String typeRequest = enumeration.nextElement();
		
		DAOContact daoContact = new DAOContact();
		List<Object> list = new ArrayList<Object>();
		String requestQuery = "";
		
		if (typeRequest.equals("hql"))
		{
			response.getWriter().append(s + s2 + "<h3>HQL</h3>");
			requestQuery = new String("from Contact c where c.address.country = 'fr'");
			list = daoContact.testHQL(requestQuery);	
			response.getWriter().append(requestQuery);
			response.getWriter().append("<table>");

			for (Iterator<Object> iterator = list.iterator(); iterator.hasNext();)
			{
				Contact c = (Contact)(iterator.next());
				response.getWriter().append("<tr><th>"+c.getFirstName() + "</th>");
				response.getWriter().append("<th>" + c.getLastName() + "</th></tr>");
			}
			
			response.getWriter().append("</table>");
		}
		
		
		response.getWriter().append("</body></html>");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
