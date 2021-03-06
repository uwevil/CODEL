<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="cssmenu/styles.css">
	<link rel="stylesheet" href="cssaccueil/style.css">
   <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
   <script src="cssmenu/script.js"></script>
<title>Accueil</title>
</head>

<body>
<div id='cssmenu'>
<ul>
	<li class='active'><a href='accueil.jsp'>Home</a></li>
   <li><a href='searchContact.jsp'>Search</a></li>
   <li><a href='addContact.jsp'>Add</a></li>
   <li><a href='updateContact.jsp'>Update</a></li>
   <li><a href='removeContact.jsp'>Remove</a></li>
   <li class="test"><a href='testRequest.jsp'>Test request</a></li>
   <li class="test"><a href='testSpring.jsp'>Test Spring</a></li>
   <li class='logout'><a href='LogoutServlet'>Log out</a></li>
</ul>
</div>

<h1>Bienvenüe au site de gestion de contacts</h1>
<%@ page import="domain.DAOContact" %>
<%@ page import="domain.Contact" %>
<% 

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
%>

<h2>Liste de vos contacts</h2>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext" %>
<%
//DAOContact daoContact = new DAOContact();
//List<Object> list = daoContact.welcome();

ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			DAOContact daoContact = (DAOContact) context.getBean("springDAOContactID");

List<Object> list = daoContact.welcome();

if (list.size() > 0)
{
	out.print("<table border=\"1\">");
	out.print("<tr><th>Prénom</th><th>Nom</th></tr>");	

	for (Iterator<Object> iterator = list.iterator(); iterator.hasNext();)
	{
		Contact c = (Contact)(iterator.next());
		out.print("<tr><th>"+c.getFirstName() + "</th>");
		out.print("<th>" + c.getLastName() + "</th></tr>");
	}
	
	out.print("</table>");	
}

Contact c_test = daoContact.testSecondCache();	
%>

<br/><hr/><br/>Test<br/>
<div>
contactID = 1<br/>
<span>firstName: <% if (c_test != null) c_test.getFirstName(); %></span>
</div>

</body>
</html>