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
	<link rel="stylesheet" href="cssAddContact/addContact.css">
   <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
   <script src="cssmenu/script.js"></script><title>addContact</title>
</head>
<body>
<div id='cssmenu'>
<ul>
	<li><a href='accueil.jsp'>Home</a></li>
   <li><a href='searchContact.jsp'>Search</a></li>
   <li><a href='addContact.jsp'>Add</a></li>
   <li><a href='updateContact.jsp'>Update</a></li>
   <li><a href='removeContact.jsp'>Remove</a></li>
   <li class='active'><a href='testRequest.jsp'>Test request</a></li>
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
<h4>Test request</h4>
<p>
	<form method="post" action="TestRequest">
	<div>
		<input class="test" type="submit" name="hql" value="Requête HQL"/>
		<input class="test" type="submit" name="criteria" value="Requête avec Criteria"/>
		<input class="test" type="submit" name="example" value="Requête avec Examples"/>
	</div>
	<div>
		<textarea name="textarea" rows="5" cols="70" placeholder="Saisir une requête HQL ici."></textarea><br>
	</div>
		<input type="submit" value="Run"/>
		<input type="reset" value="Reset"/>
	</form>
</p>
</body>
</html>