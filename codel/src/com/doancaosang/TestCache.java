package com.doancaosang;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import domain.Contact;
import domain.DAOContact;
import util.HibernateUtil;

/**
 * Servlet implementation class TestCache
 */
@WebServlet("/TestCache")
public class TestCache extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestCache() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//session1 envoie un requete SQL vers le base des donnees
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
		
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		DAOContact daoContact = (DAOContact) context.getBean("springDAOContactID");

		response.getWriter().append(Header.header
				+ "<title>Test Spring</title>" 
				+ Header.menu_testSpring + "<h3>Spring</h3>");
		
		Session session1 = HibernateUtil.getSessionFactory().openSession();		
		session1.beginTransaction();	
		@SuppressWarnings("unchecked")
		List<Contact> list = session1.createQuery("from Contact").list();
		session1.getTransaction().commit();
				
		System.out.println("-------------------------------------------------");
		
		//session2 n'envoie pas de requete vers le base des donnees car il recupere les donnees depuis le cache secondaire
		//donc on ne peut pas voir la sortie de requete SQL apres "------"
		
		Session session2 = HibernateUtil.getSessionFactory().openSession();		
		session2.beginTransaction();
		Contact c = (Contact)session2.load(Contact.class, 1L);
		System.out.println(c.getFirstName()+" "+c.getLastName());
		session2.getTransaction().commit();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
