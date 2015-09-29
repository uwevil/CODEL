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
   <li class='active'><a href='addContact.jsp'>Add</a></li>
   <li><a href='updateContact.jsp'>Update</a></li>
   <li><a href='removeContact.jsp'>Remove</a></li>
   <li class='logout'><a href='LogoutServlet'>Log out</a></li>
</ul>
</div>
<h4>Ajoutez votre contact</h4>
<p>
	<form method="post" action="NewContact">
		<input type="text" name="firstName" placeholder="First name" size="25" required><br>	
		<input type="text" name="lastName" placeholder="Last Name" size="25"><br>	
		<input type="text" name="mobileNumber" placeholder="Mobile" size="25">
		<input type="text" name="houseNumber" placeholder="Telephone" size="25">	
		<input type="text" name="faxNumber" placeholder="Fax" size="25"><br>	
		<input type="text" name="email" placeholder="Email" size="25"><br>	
		<input type="text" name="street" placeholder="Street" size="25"></br>
		<input type="text" name="zip" placeholder="Zip" size="25">
		<input type="text" name="city" placeholder="City" size="25">
		<input type="text" name="country" placeholder="Country" size="25"><br>		
		
		<p>
       	 <div class="dropdown">
<h4>Groupe</h4>
            <ul>
                <li>
                    <input type="checkbox" name="group" value="Amis" />Amis</li>
                <li>
                    <input type="checkbox" name="group" value="Collegues" />Collègues</li>
                <li>
                    <input type="checkbox" name="group" value="Famille" />Famille</li>
                <li>
                	<input type="text" name="newGroup" placeholder="Create New Group"/></li>
            </ul>
        </div>
		</p>
	
		<input type="submit" value="Submit">
		<input type="reset" value="Reset">
	</form>
</body>
</html>