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
   <script src="cssmenu/script.js"></script><title>Update Contact</title>
</head>
<body>
<div id='cssmenu'>
<ul>
	<li><a href='accueil.jsp'>Home</a></li>
   <li><a href='searchContact.jsp'>Search</a></li>
   <li><a href='addContact.jsp'>Add</a></li>
   <li class='active'><a href='updateContact.jsp'>Update</a></li>
   <li><a href='removeContact.jsp'>Remove</a></li>
   <li class='logout'><a href='LogoutServlet'>Log out</a></li>
</ul>
</div>
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
<h4>Mettez à jour votre contact</h4>
<p>
	<form method="post" action="UpdateContact">
		<input type="text" name="firstName" placeholder="First name" size="25" required>	
		<input type="text" name="lastName" placeholder="Last Name" size="25" required><br>	
	<p></p>
		<input type="submit" value="Update">
		<input type="reset" value="Reset">
	</form>
</p>
</body>
</html>