<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"
	name="Login">
<link href="${path }/css/modal.css" rel="stylesheet" type="text/css"
	media="all" />
	<link href="${path }/css/repairagency.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="${path }/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="${path }/js/modal.js"></script>
<title>Welcome</title>
</head>
<body>
	<h3>Your orders:</h3>
	<table style="width: 100%" id= "orders">
		<tr>
			<th>N</th>
			<th>Name</th>			
			<th>Date</th>
			<th>State</th>
			<th></th>
		</tr>
		<c:forEach items="${orders}" var="order" varStatus="count">
			<tr>
				<td>${order.id}</td>
				<td>${order.name}</td>
<!-- 				<td>${order.description}</td> -->
				<td>${order.date.dayOfMonth}.${order.date.month.value}.${order.date.year}</td>
				<!--      <td>${order.date}</td>-->

				<td>${order.workState}</td> 
		<!-- 		     <c:if test="${ order.workState eq 'NEW'}">           
                        <button id="myBtn"	onclick="changeOrderState('${order.id}');"> Change to: In work</button>
					</c:if> <c:if test="${ order.workState eq 'IN_WORK'}">          
                       <button id="myBtn" onclick="changeOrderState('${order.id}');">Change to: Finished</button>
					</c:if> -->
				<td><button id="myBtn" 
				onclick="showDetailsModal('${order.id}','${order.name}',
				'${order.date.dayOfMonth}.${order.date.month.value}.${order.date.year}','${order.description}','${order.workState}');">Details</button></td>				
						
			</tr>
		</c:forEach>
	</table>
	<br>
  <form action="${path }/do/logout" method="get">
        <input type="hidden" id="filfI" name="command" value="Logout">
        <input type="submit" value="Logout">
    </form>
    
    <div id="myModal" class="modal">

      <!-- Modal content -->
      <div class="modal-content" style="width:500;">
        <span class="close">&times;</span>
       <div class="container">
       <form action="${path }/do/master/changeOrderState" method="post">   
        <input type="hidden"  name="command" value="ChangeOrderWorkState">        
        <input type="hidden" id="orderId" name="orderId" >
        <input type="hidden" id="newState" name="newState" >
        N: <label id="N"></label>   <br>
        Order name: <label id="name"></label>   <br>
        Date: <label id="date"></label>   <br>
        Description: <label id="description"></label>   <br>
                       
        <input type="submit" id="changeSt" value="">           
       </form>      
       </div> 
    </div>
   </div>
   <script>
   var modal = document.getElementById("myModal");
      function showDetailsModal(id,name, date,description, workState) {
          modal.style.display = "block";

          $("#orderId").val(id);
          $("#N").html(id);
          $("#name").html(name);
          $("#date").html(date);
          $("#description").html(description);
          if(workState =='NEW'){
        	  $('#changeSt').show();
        	  $('#changeSt').val('Take an order in work');
        	  $('#newState').val('IN_WORK');
          }else if(workState =='IN_WORK'){
        	  $('#changeSt').show();
        	  $('#changeSt').val('FINISHED');
        	  $('#newState').val('FINISHED');
          }else if(workState =='FINISHED'){
        	  $('#changeSt').hide();
          }
      }
</script>
</body>
</html>