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
 <div id="header"><h1>Manage orders</h1></div>
  <div id="sidebar">
    <h2 class="filter">Filter BY</h2>
    <form action="${path }/do/manager/filter">
     <input type="hidden" name="command" value="FilterOrders">
    <h3> State</h3>
    
	    <input type="checkbox" name="state" value="NEW"> New<BR>
	    <input type="checkbox" name="state" value="WAIT_FOR_PAYMENT"> Wait for payment<BR>
	    <input type="checkbox" name="state" value="PAYED"> PAYED<BR>
	    <input type="checkbox" name="state" value="CANCELED"> Canceled<BR>
    <h3>Work  state</h3>
	    <input type="checkbox" name="work_state" value="NEW"> New<BR>
	    <input type="checkbox" name="work_state" value="IN_WORK"> In work<BR>
	    <input type="checkbox" name="work_state" value="FINISHED"> Finished<BR>    
    <h3>Master</h3>
		<select name="masters">		
            <option value="0">Select master</option>               
			<c:forEach items="${mastersList}" var="master">
				<option value="${master.id}">${master.name}</option>
			</c:forEach>
		</select><br> 
		<input type="submit" id="changeSt" value="Submit">
     </form>
  </div>
  <div id="content">
  <!--   <input type="button" onclick="location.href='https://google.com';" value="Go to Google" /> -->
    Sort By <a href="${path }/do/manager/sort?command=ManagerSortOrders&param=date" class="button"> Date </a>&nbsp
    <a href="${path }/do/manager/sort?command=ManagerSortOrders&param=price" class="button"> Price </a>
    <a href="${path }/do/manager/sort?command=ManagerSortOrders&param=managment_state" class="button"> State </a>&nbsp&nbsp
    <a href="${path }/do/manager/sort?command=ManagerSortOrders&param=work_state" class="button"> Work state </a>
    
 <!--    <input type="button" onclick="location.href='${path }/do/sort?command=ManagerSortOrders&param=date'" value="DATE" /> -->
    
    <table style="width: 100%" id= "orders">
        <tr>
            <th>Number</th>
            <th>Name</th>           
            <th>Date</th>            
            <th>State</th>
            <th>Work State</th>
            <th>Price</th>
            <th></th>
        </tr>
        <c:forEach items="${orders}" var="order" varStatus="count">
            <tr>
                <td>${order.id}</td>
                <td>${order.name}</td>
                <td>${order.date.dayOfMonth}.${order.date.month.value}.${order.date.year}</td>
                <td>${order.managementState}</td> 
                <td>${order.workState}</td>
                <td>${order.price}</td>
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
    </div>
    <div id="myModal" class="modal">

      <!-- Modal content -->
      <div class="modal-content" style="width:500;">
        <span class="close">&times;</span>
       <div class="container">
       <form action="${path }/do/master/changeOrderState" method="post">   
        <input type="hidden"  name="command" value="ChangeOrderWorkState">        
        <input type="hidden" id="orderId" name="orderId" >
        <input type="hidden" id="newState" name="newState" >
        Number: <label id="N"></label>   <br>
        Order name: <label id="name"></label>   <br>
        Date: <label id="date"></label>   <br>
        Description: <label id="description"></label>   <br>
                       
        <input type="submit" id="changeSt" value="OK">           
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