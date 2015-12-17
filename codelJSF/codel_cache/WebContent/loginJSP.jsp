<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login OK</title>
</head>
<body>
<% 
	String id = request.getParameter("id");
	String pass = request.getParameter("password");
		
	if (id.length() == 0 || pass.length() == 0)
	{
		session.setAttribute("authenticated", null);
		session.setMaxInactiveInterval(60);
		response.sendRedirect("login.html");
		return;
	}
	
	if (id.equals(pass))
	{
		session.setAttribute("authenticated", "OK");
		session.setMaxInactiveInterval(60);
		response.sendRedirect("accueil.jsp");
	}
	else
	{
		session.setAttribute("authenticated", null);
		session.setMaxInactiveInterval(60);
		response.sendRedirect("login.html");
	}
%>
</body>
</html>