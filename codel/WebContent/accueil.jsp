<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Accueil</title>
</head>
<body>
<h1>Login OK</h1>
Toutes les actions possibles sont:
<p>
	<form method="post" action="searchContact.jsp">
		<input type="submit" value="searchContact">
	</form>
	<form method="post" action="addContact.jsp">
		<input type="submit" value="addContact">
	</form>
	<form method="post" action="updateContact.jsp">
		<input type="submit" value="updateContact">
	</form>
	<form method="post" action="removeContact.jsp">
		<input type="submit" value="removeContact">
	</form>
</p>
<a href="login.html">Retour</a>
</body>
</html>