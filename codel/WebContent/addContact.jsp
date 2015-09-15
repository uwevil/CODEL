<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>addContact</title>
</head>
<body>
<p>
	<form method="post" action="NewContact.java">
		id: <input type="text" name="id" size="25">
		First Name: <input type="text" name="firstName" size="25">
		Last Name: <input type="text" name="lastName" size="25"></br>
		Email: <input type="text" name="email" size="25">
	<p></p>
		<input type="submit" value="Submit">
		<input type="reset" value="Reset">
	</form>
</p>
</body>
</html>