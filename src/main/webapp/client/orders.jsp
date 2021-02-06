<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"
	name="Login">
	<link href="css/login.css" rel="stylesheet" type="text/css" media="all"/>
<title>Welcome</title>
</head>
<body>
	<h1>Hello ${loggedUser}</h1>
	<form action="order" method="post">
		<p>Please create your order</p>

		Name: <input type="text" id="orderName" name="orderName"><br>
		<br> Description: <input type="text" id="orderDescription"
			name="orderDescription"><br> <br>
		<button type="submit">Create</button>
	</form>
	<h3>Your balance:</h3>
	${client.balance}
	<h3>Your orders:</h3>
	<table style="width:100%">
		<tr>
			<th>N</th>
			<th>Name</th>
			<th>Description></th>
			<th>Price</th>
			<th>Date</th>
			<th>Payment</th>
			<th>State</th>
		</tr>
		<c:forEach items="${client.orders}" var="order" varStatus="count">   
		<tr>
		  <td> <c:out value="${count.index+1}" /></td>
			<td>${order.name}</td>
			<td>${order.description}</td>
			<td>${order.price}</td>
			<td>${order.date.dayOfMonth}.${order.date.month.value}.${order.date.year}</td>
		 <!--      <td>${order.date}</td>-->
			<td>${order.managementState=='NEW'?' wait for payment':order.managementState}</td>
			<td>${order.workState=='NEW'?'in work':order.workState}
			</td>
		</tr>
		 </c:forEach>
	</table>
	<br>
	<br>
	<form action="logout" method="post">
		<input type="submit" value="Logout">
	</form>
</body>
</html>