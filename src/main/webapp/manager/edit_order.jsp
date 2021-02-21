<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"
	name="Login">
<link href="${path }/css/modal.css" rel="stylesheet" type="text/css"
	media="all" />
<link href="${path }/css/repairagency.css" rel="stylesheet"
	type="text/css" media="all" />
<link rel="shortcut icon" href="#">
<script type="text/javascript" src="${path }/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="${path }/js/modal.js"></script>
<title>Welcome</title>
</head>
<body>
	<div id="header">
		<ul>
			<li><a href="${path }/manager/manage_orders.jsp">Manage
					orders</a></li>
			<li><a class="active" href="">Find customer</a></li>

			<li style="float: right"><a class="activeLan" href="#about">RU</a></li>
			<li style="float: right"><a href="#about">EN</a></li>
		</ul>
	</div>
	<div class="container">
	    <form action="${path }/do/manager/editOrder" method="post" style="width:400px">
	      <input type="hidden" name="command" value="EditOrder">
	      <input type="hidden" name="orderId" value="${order.id }">
        <h3 style="text-align: center">Edit Order</h3>
            <div class="container">
            <p style="color: green">${successMessage}</p>
                <table>
                <tr>                
                <td>Number: </td><td> ${order.id}</td> </tr>
                <tr>
               <td> Name: </td> <td>${order.name}</td></tr>
               <tr>
                <td>Date: </td><td>${order.date.dayOfMonth}.${order.date.month.value}.${order.date.year}</td>
                </tr>
                <tr>  <td>Description: </td><td>${order.description}</td></tr>
                <tr><td>Work state:  </td><td>${order.workState}</td></tr>
                <tr><td>Price:   </td>
                    <td><input type="number" name ="price" value="${order.price}" min="0" step=".01">
                             <!--  <button id="setPriceBut${order.id}" onclick="setPrice('${order.id}');">Set price</button><br> --> 
                               </td>
                               </tr>
               <tr>
               <td>Payment state: </td>
					<td><select name="managementState" size=1 >
							<option value="NEW"
								${order.managementState=='NEW'? 'selected' :'' }>NEW</option>
							<option value="WAIT_FOR_PAYMENT"
								${order.managementState=='WAIT_FOR_PAYMENT'? 'selected' :'' }>WAIT_FOR_PAYMENT</option>
							<option value="PAYED"
								${order.managementState=='PAYED'? 'selected' :'' }>PAYED</option>
							<option value="CANCELED"
								${order.managementState=='CANCELED'? 'selected' :'' }>CANCELED</option>
					      </select>
					</td>
					</tr>
				<tr> 
			     <td>Masters: </td>
			     <td>
			         <c:forEach items="${order.masters}" var="master"
                            varStatus="count">
                        ${master.name}<br>
                        </c:forEach>
                       <br>
                       Add master:
                 <select name="masters">
	                <option value="0">Select master</option>
	                <c:forEach items="${mastersList}" var="master">
	                    <option value="${master.id}">${master.name}</option>
	                </c:forEach>
	               </select>   
	                </td>    
                 </tr>                                     
               </table>
               <input type="submit" id="changeSt" value="Save"> 
            </div>
    </form>
	</div>
</body>
</html>