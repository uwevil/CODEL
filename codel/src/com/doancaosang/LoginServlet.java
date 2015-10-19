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

import domain.DAOContact;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet()
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		String id = request.getParameter("id");
		String pass = request.getParameter("password");
		
		HttpSession session = request.getSession(true);
		
		if (id.length() == 0 || pass.length() == 0)
		{
			session.setAttribute("authenticated", null);
			session.setMaxInactiveInterval(120);
			response.sendRedirect("login.html");
			return;
		}
		
		if (id.equals(pass))
		{	
			session.setAttribute("authenticated", "OK");
			session.setMaxInactiveInterval(120);
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			DAOContact daoContact = (DAOContact) context.getBean("springDAOContactID");
			
			session.setAttribute("list", daoContact.welcome());
			response.sendRedirect("accueil.jsp");
		}
		else
		{
			session.setAttribute("authenticated", null);
			session.setMaxInactiveInterval(120);
			response.sendRedirect("login.html");
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
