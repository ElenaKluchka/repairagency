<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"
	name="Login">	
<title>Welcome</title>
</head>
<body>
	<h1>Hello ${loggedUser}</h1>
	<form action="order" method="post">
		<p>Please create your order</p>
		
        Name: <input type="text" id="name" name="name"><br><br>
        Name: <input type="text" id="description" name="description"><br><br>
         <button type="submit">Create</button>
	</form>
	<h3>Your balance:</h3> ${client.balance}
	<h3>Your orders:</h3>	
	<c:forEach items="${client.orders}" var="item">
     ${item.name}
</c:forEach>
</body>
</html>