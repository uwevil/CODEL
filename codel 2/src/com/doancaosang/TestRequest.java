package com.doancaosang;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import domain.Contact;
import domain.ContactGroup;
import domain.DAOContact;
import domain.Entreprise;
import domain.PhoneNumber;

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
				
		DAOContact daoContact = new DAOContact();
		List<Object> list = new ArrayList<Object>();
		String requestQuery = "";
		
		if (request.getParameter("hql") != null)
		{
			response.getWriter().append(s + s2 + "<h3>HQL</h3>");
			requestQuery = new String("from Contact as c "
					+ "where c.firstName = '" + request.getParameter("firstName") + "' "
					+ "and c.lastName = '" + request.getParameter("lastName") + "'"
					);
			response.getWriter().append("<h4>" + requestQuery + "</h4>");
			response.getWriter().append("<table>");
			response.getWriter().append("<tr><th>FIRST NAME</th><th>LAST NAME</th></tr>");
			list = daoContact.testHQL(requestQuery);	
			
			for (Iterator<Object> iterator = list.iterator(); iterator.hasNext();)
			{
				Contact c = (Contact)(iterator.next());
				response.getWriter().append("<tr><th>"+c.getFirstName() + "</th>");
				response.getWriter().append("<th>" + c.getLastName() + "</th></tr>");
			}

			response.getWriter().append("</table>");
		}
		else if (request.getParameter("hql2") != null)
		{
			response.getWriter().append(s + s2 + "<h3>HQL 2</h3>");
			requestQuery = new String("from Contact as c1, Contact as c2 "
					+ "where c1.address.street = c2.address.street and c1.id < c2.id"
					);
			response.getWriter().append("<h4>" + requestQuery + "</h4>");
			response.getWriter().append("<table>");
			response.getWriter().append("<tr><th>STREET</th><th>FIRST NAME</th><th>LAST NAME</th></tr>");
			
			list = daoContact.testHQL2(requestQuery);	

			Hashtable<String, ArrayList<Contact>> list_tmp = new Hashtable<>();
			
			for (Iterator<Object> iterator = list.iterator(); iterator.hasNext();)
			{
				Object[] contacts = (Object[])(iterator.next());
								
				if (contacts.length > 0)
				{
					if (!list_tmp.containsKey(((Contact)contacts[0]).getAddress().getStreet()))
						list_tmp.put(((Contact)contacts[0]).getAddress().getStreet(), new ArrayList<Contact>());
					
					for (int i = 0; i < contacts.length; i++)
					{
						Contact c_tmp = (Contact)contacts[i];
						String street = c_tmp.getAddress().getStreet();
						ArrayList<Contact> arrayList = list_tmp.get(street);
						
						if (arrayList.size() < 1)
						{
							arrayList.add(c_tmp);
						}
						else
						{
							int j = 0;
							for (j = 0; j < arrayList.size(); j++)
							{
								if (c_tmp.getFirstName().equals(arrayList.get(j).getFirstName()) 
										&& c_tmp.getLastName().equals(arrayList.get(j).getLastName()))
									break;
							}
							
							if (j >= arrayList.size())
							{
								arrayList.add(c_tmp);
							}
						}	
					}
				}
			}
			
			Enumeration<String> streets = list_tmp.keys();
			
			while(streets.hasMoreElements())
			{
				String street = streets.nextElement();
				ArrayList<Contact> arrayList = list_tmp.get(street);
				
				for (int i = 0; i < arrayList.size(); i++)
				{
					if (street.length() < 1)
						response.getWriter().append("<tr><th>UNKNOWN</th>");
					else
						response.getWriter().append("<tr><th>" + street + "</th>");

						response.getWriter().append("<th>" + arrayList.get(i).getFirstName() + "</th>"
							+ "<th>" + arrayList.get(i).getLastName() + "</th></tr>");
					
				}
				
				response.getWriter().append("</tr>");
			}
		}
		else if (request.getParameter("criteria") != null)
		{
			response.getWriter().append(s + s2 + "<h3>Criteria</h3>");
			
			String street = request.getParameter("street");
			String zip = request.getParameter("zip");
			String city = request.getParameter("city");
			String country = request.getParameter("country");
			
			requestQuery = new String("from Contact as c "
					+ "where street = '" + street 
					+ "' and zip = '" + zip 
					+ "' and city = '" + city 
					+ "' and country = '" + country + "'"
					);
			response.getWriter().append("<h4>" + requestQuery + "</h4>");
			response.getWriter().append("<table>");
			response.getWriter().append("<tr><th>FIRST NAME</th><th>LAST NAME</th>"
					+ "<th>Street</th><th>Zip</th><th>City</th><th>Country</th></tr>");
			
			list = daoContact.testCriteria(street, zip, city, country);	
			
			Hashtable<String, Contact> hashtable = new Hashtable<>();
			
			for (Iterator<Object> iterator = list.iterator(); iterator.hasNext();)
			{
				Contact c = (Contact)(iterator.next());
				String key = c.getFirstName()+c.getLastName();
				
				if (!hashtable.containsKey(key))
					hashtable.put(key, c);
			}
			
			Enumeration<String> enumeration = hashtable.keys();
			
			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement();
				Contact c = hashtable.get(key);
				
				response.getWriter().append("<tr><th>"+c.getFirstName() + "</th>");
				response.getWriter().append("<th>" + c.getLastName() + "</th><");
				response.getWriter().append("<th>" + c.getAddress().getStreet() + "</th><");
				response.getWriter().append("<th>" + c.getAddress().getZip() + "</th><");
				response.getWriter().append("<th>" + c.getAddress().getCity() + "</th><");
				response.getWriter().append("<th>" + c.getAddress().getCountry() + "</th></tr>");	
			}
			
			response.getWriter().append("</table>");
		}
		else if (request.getParameter("example") != null)
		{
			response.getWriter().append(s + s2 + "<h3>Request by the Example</h3>");
			String email = request.getParameter("email");
			requestQuery = new String("from Contact as c "
					+ "where c.email = '" + email + "'"
					);
			response.getWriter().append("<h4>" + requestQuery + "</h4>");
			response.getWriter().append("<table>");
			
			list = daoContact.testHQL(requestQuery);	
			
			for (Iterator<Object> iterator = list.iterator(); iterator.hasNext();)
			{
				Contact c = (Contact)(iterator.next());
				response.getWriter().append("<tr><th>"+c.getFirstName() + "</th>");
				response.getWriter().append("<th>" + c.getLastName() + "</th></tr>");
			}

			response.getWriter().append("</table>");
		}
		else if (request.getParameter("hql3") != null)
		{
			response.getWriter().append(s + s2 + "<h3>HQL 3</h3>");
			requestQuery = "from Contact c "
					+ "left join fetch c.phoneNumbers "
					+ "left join fetch c.address "
					+ "left join fetch c.books ";
			
			response.getWriter().append("<h4>" + requestQuery + "</h4>");

			list = daoContact.testHQL(requestQuery);	
			
			Hashtable<String, Object> hashtable = new Hashtable<>();
			
			for (Iterator<Object> iterator = list.iterator(); iterator.hasNext();)
			{
				Contact c = (Contact)(iterator.next());
				String key = c.getFirstName()+c.getLastName();
				
				if (!hashtable.containsKey(key))
					hashtable.put(key, c);
			}
			
			Enumeration<String> enumeration = hashtable.keys();
			
			while (enumeration.hasMoreElements()) {
				String key = (String) enumeration.nextElement();
				Object c = hashtable.get(key);
				
				if (c.getClass().getName().contains("Entreprise"))
				{
					Entreprise e = (Entreprise)c;
					response.getWriter().append("<table border='1'>");
					response.getWriter().append("<tr><th>First name</th><th>" + e.getFirstName() + "</th></tr>");
					response.getWriter().append("<tr><th>Last name</th><th>" + e.getLastName() + "</th></tr>");
					response.getWriter().append("<tr><th>Numero SIRET</th><th>" + e.getNumSiret() + "</th></tr>");

					for (Iterator<PhoneNumber> iterator2 = e.getPhoneNumbers().iterator(); iterator2.hasNext();)
					{
						PhoneNumber p = iterator2.next();
						
						if (p.getPhoneKind().equals("mobileNumber"))
						{
							response.getWriter().append("<tr><th>Mobile</th><th>" 
									+ p.getPhoneNumber() + "</th></tr>");
						}
						else if (p.getPhoneKind().equals("homeNumber"))
						{
							response.getWriter().append("<tr><th>Home</th><th>" + p.getPhoneNumber() + "</th></tr>");
						}
						else
						{
							response.getWriter().append("<tr><th>Fax</th><th>" + p.getPhoneNumber() + "</th></tr>");
						}
					}
					
					
					response.getWriter().append("<tr><th>Email</th><th>" + e.getEmail() + "</th></tr>");

					response.getWriter().append("<tr><th>Street</th><th>" + e.getAddress().getStreet() + "</th></tr>");
					response.getWriter().append("<tr><th>Zip</th><th>" + e.getAddress().getZip() + "</th></tr>");
					response.getWriter().append("<tr><th>City</th><th>" + e.getAddress().getCity() + "</th></tr>");
					response.getWriter().append("<tr><th>Country</th><th>" + e.getAddress().getCountry() + "</th></tr>");
					response.getWriter().append("<tr><th>Group</th><th>");
					response.getWriter().append("<table>");					
			
					for (Iterator<ContactGroup> iterator2 = e.getBooks().iterator(); iterator2.hasNext();)
					{
						ContactGroup g = iterator2.next();
						response.getWriter().append("<tr><th>" + g.getGroupName() + "</th></tr>");
					}
					
					response.getWriter().append("</table>");
					response.getWriter().append("</th></tr>");					
				}
				else
				{
					Contact e = (Contact)c;
					response.getWriter().append("<table border='1'>");
					response.getWriter().append("<tr><th>First name</th><th>" + e.getFirstName() + "</th></tr>");
					response.getWriter().append("<tr><th>Last name</th><th>" + e.getLastName() + "</th></tr>");
				
					for (Iterator<PhoneNumber> iterator2 = e.getPhoneNumbers().iterator(); iterator2.hasNext();)
					{
						PhoneNumber p = iterator2.next();
						
						if (p.getPhoneKind().equals("mobileNumber"))
						{
							response.getWriter().append("<tr><th>Mobile</th><th>" 
									+ p.getPhoneNumber() + "</th></tr>");
						}
						else if (p.getPhoneKind().equals("homeNumber"))
						{
							response.getWriter().append("<tr><th>Home</th><th>" + p.getPhoneNumber() + "</th></tr>");
						}
						else
						{
							response.getWriter().append("<tr><th>Fax</th><th>" + p.getPhoneNumber() + "</th></tr>");
						}
					}
					
					response.getWriter().append("<tr><th>Email</th><th>" + e.getEmail() + "</th></tr>");

					response.getWriter().append("<tr><th>Street</th><th>" + e.getAddress().getStreet() + "</th></tr>");
					response.getWriter().append("<tr><th>Zip</th><th>" + e.getAddress().getZip() + "</th></tr>");
					response.getWriter().append("<tr><th>City</th><th>" + e.getAddress().getCity() + "</th></tr>");
					response.getWriter().append("<tr><th>Country</th><th>" + e.getAddress().getCountry() + "</th></tr>");
					response.getWriter().append("<tr><th>Group</th><th>");
					response.getWriter().append("<table>");
								
					for (Iterator<ContactGroup> iterator2 = e.getBooks().iterator(); iterator2.hasNext();)
					{
						ContactGroup g = iterator2.next();
						response.getWriter().append("<tr><th>" + g.getGroupName() + "</th></tr>");
					}		
					response.getWriter().append("</table>");
					response.getWriter().append("</th></tr>");
					response.getWriter().append("</table>");

				}				
			}
			
		}
		else
		{
			response.getWriter().append(s + s2 + "<h3>Custom HQL</h3>");
			requestQuery = request.getParameter("textarea");
			
			response.getWriter().append("<h4>" + requestQuery + "</h4>");
			response.getWriter().append("<table>");
			response.getWriter().append("<tr><th>FIRST NAME</th><th>LAST NAME</th></tr>");
			
			list = daoContact.testHQL(requestQuery);	
			
			Hashtable<String, Object> hashtable = new Hashtable<>();
			
			for (Iterator<Object> iterator = list.iterator(); iterator.hasNext();)
			{
				Contact c = (Contact)(iterator.next());
				String key = c.getFirstName()+c.getLastName();
				
				if (!hashtable.containsKey(key))
					hashtable.put(key, c);
			}
			
			Enumeration<String> enumeration = hashtable.keys();
			
			while (enumeration.hasMoreElements()) {
				String key = (String) enumeration.nextElement();
				Contact c = (Contact)(hashtable.get(key));
				
				response.getWriter().append("<tr><th>"+c.getFirstName() + "</th>");
				response.getWriter().append("<th>" + c.getLastName() + "</th></tr>");
			}

			response.getWriter().append("</table>");
		}
		
		response.getWriter().append("<p><a class=\"back\" href='testRequest.jsp'>BACK</a></p>");
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
