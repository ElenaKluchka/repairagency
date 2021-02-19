<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"
    name="Login">
    <link href="${path }/css/modal.css" rel="stylesheet" type="text/css" media="all" />
     <link href="${path }/css/repairagency.css" rel="stylesheet" type="text/css" media="all" />
     <link rel="shortcut icon" href="#">  
    <script type="text/javascript" src="${path }/js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript" src="${path }/js/modal.js"></script>
<title>Welcome</title>
</head>
<body>
    <!-- <h1>Hello ${client.name}</h1> -->
    <form action="${path }/do/client/addOrder" method="post" style="width:400px">
        <h3 style="text-align: center">Create your order</h3>
            <div class="container">     
		        <input type="hidden" name="command" value="AddOrder">
		        Name: <input type="text" id="orderName" name="orderName" required> <br>
		        <br> Description: <textarea name="orderDescription" id="orderDescription" 
		             class="description" placeholder="Write description of your problem..."  maxlength="500" required></textarea>
		        <button type="submit" class="blueBut">Save order</button>
            </div>
    </form>
    Your balance:
    ${client.balance==0?'0.00f':client.balance}
    <h3>Your orders:</h3>
    <table style="width:100%" id= "orders">
        <tr>
            <th>Number</th>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Date</th>
            <th>Payment</th>
            <th>State</th>
            
            <th>Feedback</th>
            
        </tr>
        <c:forEach items="${client.orders}" var="order" varStatus="count">   
        <tr>
      <!--     <td> <c:out value="${count.index+1}" /></td> -->
            <td>${order.id}</td>
            <td>${order.name}</td>
            <td>${order.description}</td>
            <td>${order.price}</td>
            <td>${order.date.dayOfMonth}.${order.date.month.value}.${order.date.year}</td>
         <!--      <td>${order.date}</td>-->
            <td>${order.managementState=='NEW'?' wait for payment':order.managementState}</td>
            <td>${order.workState=='NEW'?'in work':order.workState}
            </td>
     
            <td>
                  <c:if test = "${ order.workState eq 'FINISHED'}">   
                 <c:if test = "${ empty order.feedback}">
                   <!--      <input type="button" value="Leave feedback " onclick="fbLikeDump();" /> -->
                        <button id="myBtn" onclick="showFeedbackModal('${order.id}');">Feedback</button>
                 </c:if>
                 <c:if test = "${not empty order.feedback}">
                 ${order.feedback}
                 </c:if>
                </c:if>  
            </td>
           
        </tr>
         </c:forEach>
    </table>
    
    <br>
    <!-- <form action="${path }/do/logout" method="get"> -->
    <form action="${path }/do/logout" method="get">
        <input type="hidden" id="filfI" name="command" value="Logout">
        <input type="submit" value="Logout">
    </form>
    
    <div id="myModal" class="modal">

      <!-- Modal content -->
      <div class="modal-content" style="width:500;">
        <span class="close">&times;</span>
       <div class="container">
       <form action="${path }/do/client/feedback" method="post">   
        <input type="hidden"  name="command" value="Feedback">
        <input type="hidden" id="orderId" name="orderId" >
        <label for="subject">Subject</label>   <br>   
        <textarea name="feedback" class="textO" placeholder="Write something.." maxlength="500" required></textarea>        
        <input type="submit" value="Save">       
       </form>
        <button id="okMod" onclick="closeModal();">Cancel</button>
       </div> 
    </div>
   </div>
   <script>
   var modal = document.getElementById("myModal");
      function showFeedbackModal(orderId) {
          modal.style.display = "block";
          document.getElementById("orderId").value = orderId;
      }
      function closeModal() {
          modal.style.display = "none";
      }
</script>
</body>
</html>