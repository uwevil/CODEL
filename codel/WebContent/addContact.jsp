<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>addContact</title>
</head>
<body>
<h4>Ajoutez votre contact</h4>
<p>
	<form method="post" action="NewContact">
		<input type="text" name="id" placeholder="id" size="25"></br>
		<input type="text" name="firstName" placeholder="First name" size="25"><br>	
		<input type="text" name="lastName" placeholder="Last Name" size="25"><br>	
		<input type="text" name="email" placeholder="Email" size="25"><br>	
	<p></p>
		<input type="submit" value="Submit">
		<input type="reset" value="Reset">
	</form>
</p>
<a href="accueil.jsp">Retour</a>
</body>
</html>