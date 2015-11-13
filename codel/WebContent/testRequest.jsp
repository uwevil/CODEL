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
   <li class="test"><a href='testSpring.jsp'>Test Spring</a></li>
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
		<p>
			<input type="text" name="firstName" placeholder="firstName" size="25">
			<input type="text" name="lastName" placeholder="lastName" size="25"><br>
			<input class="test" type="submit" name="hql" value="Requête HQL"/><br>
		</p>
		<p>
			<input class="test" type="submit" name="hql2" value="Requête HQL 2"/>		
			<input class="test" type="submit" name="hql3" value="Requête HQL 3"/><br>
		</p>
		
		<p>
			<input type="text" name="street" placeholder="Street" size="25">
			<input type="text" name="zip" placeholder="Zip" size="25">
			<input type="text" name="city" placeholder="City" size="25">
			<input type="text" name="country" placeholder="Country" size="25"><br>
			<input class="test" type="submit" name="criteria" value="Requête avec Criteria"/><br>
		</p>
		<p>
			<input type="text" name="email" placeholder="Email" size="25"><br>
			<input class="test" type="submit" name="example" value="Requête avec Examples"/><br>
		</p>
	</div>
	<div>
		<textarea name="textarea" rows="5" cols="70" 
			placeholder="Saissir la requête HQL.">from Contact c 
			left join fetch c.phoneNumbers
			left join fetch c.address 
			left join fetch c.books 
			
		</textarea><br>
	</div>
		<input type="submit" value="Run"/>
		<input type="reset" value="Reset"/>
	</form>
	
</p>
</body>
</html>