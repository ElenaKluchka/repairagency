<%@ include file="/jspf/page_taglib.jspf"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>

<html>
<c:set var="title" value="Find customer" />
<%@ include file="/jspf/logged_user_head.jspf"%>
<body>
	<div id="header">
		<ul>
			<li><a href="${path }/do/manager/manage_orders?command=ManagerOrders">Manage
					orders</a></li>
			<li><a class="active" href="">Find customer</a></li>

			<li style="float: right"><a class="activeLan" href="#about">RU</a></li>
			<li style="float: right"><a href="#about">EN</a></li>
		</ul>
	</div>
	<div id="sidebar">
		<p style="color: red">${error}</p>
		<br>
		<form action="${path }/do/manager/findCustomer">
			<h3 style="text-align: center">Find customer</h3>
			<br>
			<div class="container">
				<input type="hidden" name="command" value="FindCustomer"> <label
					for="subject">Find by Name</label> <input type="text" name="uname"
					value="${searchCustomer.name }" placeholder="name"><br>
				Or find by <label for="subject">Phone in format
					+380(xx)xxx-xx-x</label><br> <input id="online_phone" type="tel"
					maxlength="50" value="${searchCustomer.phone}" name="phone"
					autofocus="autofocus"
					pattern="(\+380[-_()\s]+)?|\+380\s?[(]{0,1}[0-9]{2}[)]{0,1}\s?\d{3}[-]{0,1}\d{2}[-]{0,1}\d{2}"
					placeholder="+380(__)___-__-__"><br>
				<button type="submit" class="blueBut">Find</button>
			</div>
		</form>
	</div>
	<div class="content">

		<c:if test="${not empty message}">
			<br>
			<p style="color: red">${message}</p>
		</c:if>
		<c:if test="${not empty customer }">
			<h3>Customer information:</h3>
		    Name: ${customer.name }<br>
		    Phone: ${customer.phone }<br>
		    Balance:${customer.balance }
		     <form action="${path }/do/manager/setBalance" method="post">
				<p style="color: red">${message}</p>
				<input type="hidden" name="command" value="SetCustomerBalance">
				<input type="hidden" name="customerId" value="${customer.id}">
				<input type="number" name="balance" required><br>
				<button type="submit" class="littleBut">Set balance</button>
			</form>
			<br>		               
		    email: ${customer.email }		<br>
			<h3>Orders:</h3>
			<table style="width: 100%" id="orders">
				<tr>
					<th>Number</th>
					<th>Name</th>
					<th>Date</th>
					<th>Payment State</th>
					<th>Work State</th>
					<th>Price</th>
					<th>Master</th>
					<th></th>
				</tr>
				<c:forEach items="${customer.orders}" var="order" varStatus="count">
					<tr>
						<td>${order.id}</td>
						<td>${order.name}</td>
						<td><my:date dat="${order.date}"/></td>
						<td>${order.managementState}</td>
						<td>${order.workState}</td>
						<td>${order.price}</td>

						<td>				
				            <my:masters par="${order }"/>
						</td>
						<td><button id="myBtn"
								onclick="location.href='${path }/do/manager/editOrderForm?command=EditOrderForm&orderId=${order.id}';">
								Edit</button></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
</body>
</html>