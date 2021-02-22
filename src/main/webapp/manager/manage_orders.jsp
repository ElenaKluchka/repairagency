<%@ include file="/jspf/page_taglib.jspf" %>

<html>
<c:set var="title" value="Manage orders" />
<%@ include file="/jspf/logged_user_head.jspf" %>
<body>
	<div id="header">       
        <ul>
            <li><a class="active" href="">Manage orders</a></li>
            <li><a href="${path }/manager/find_customer.jsp">Find customer</a></li>
            
            <%@ include file="/jspf/header_right_part.jspf" %>
        </ul>
    </div>
	<div id="sidebar">
		<h2 class="filter">Filter BY</h2>
		<form action="${path }/do/manager/filter">
			<input type="hidden" name="command" value="FilterOrders">
			<h3>Payment State</h3>

			<input type="checkbox" name="state" value="NEW"> New<BR>
			<input type="checkbox" name="state" value="WAIT_FOR_PAYMENT">
			Wait for payment<BR> <input type="checkbox" name="state"
				value="PAYED"> Payed<BR> <input type="checkbox"
				name="state" value="CANCELED"> Canceled<BR>
			<h3>Work state</h3>
			<input type="checkbox" name="work_state" value="NEW"> New<BR>
			<input type="checkbox" name="work_state" value="IN_WORK"> In work<BR> 
			<input type="checkbox" name="work_state" value="FINISHED"> Finished<BR>
			<h3>Master</h3>
			<select name="masters">
				<option value="0">Select master</option>
				<c:forEach items="${mastersList}" var="master">
					<option value="${master.id}">${master.name}</option>
				</c:forEach>
			</select><br> <input type="submit" id="changeSt" value="Submit">
		</form>
	</div>
	<div id="content">
		<!--   <input type="button" onclick="location.href='https://google.com';" value="Go to Google" /> -->
		Sort all orders by <a
			href="${path }/do/manager/sort?command=ManagerSortOrders&param=date"
			class="button"> Date </a> &nbsp; <a
			href="${path }/do/manager/sort?command=ManagerSortOrders&param=price"
			class="button"> Price </a> &nbsp; &nbsp; <a
			href="${path }/do/manager/sort?command=ManagerSortOrders&param=managment_state"
			class="button"> Payment State </a>&nbsp; &nbsp; <a
			href="${path }/do/manager/sort?command=ManagerSortOrders&param=work_state"
			class="button"> Work state </a>

		<!--    <input type="button" onclick="location.href='${path }/do/sort?command=ManagerSortOrders&param=date'" value="DATE" /> -->

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
		 <c:choose>
              <c:when test="${not empty message}">
           <br> <p style="color: red">${message}</p>
            </c:when>
            <c:otherwise>
			<c:forEach items="${orders}" var="order" varStatus="count">
				<tr>
					<td>${order.id}</td>
					<td>${order.name}</td>
					<td>					   
					   <my:date dat="${order.date}"/>
                    </td>
					<td>${order.managementState}</td>
					<td>${order.workState}</td>
					<td>
					${order.price}    
					</td>
					<td>
					   <my:masters par="${order }"/>
					</td>
					<td><button id="myBtn"
							onclick="showDetailsModal('${order.id}','${order.name}',
							'${stdDatum }','${order.description}','${order.workState}');">Details</button></td>

				</tr>
			</c:forEach>
			</c:otherwise>
			</c:choose>
		</table>
	<br>
	
<!-- Pagination -->

	<c:choose>
        <c:when test="${page == 1}">Previous </c:when>
        <c:otherwise>
            <a href="${path }/do/manager/manage_orders?command=ManagerOrders&page=${page-1}">Previous</a>
        </c:otherwise>
    </c:choose>
    
    <c:choose>
        <c:when test="${page == maxPage}">Next</c:when>
        <c:otherwise>
            <a href="${path }/do/manager/manage_orders?command=ManagerOrders&page=${page+1}">Next</a>
        </c:otherwise>
    </c:choose>
	</div>
	
<!-- Modal window to view details of order -->	
	<div id="myModal" class="modal">

		<!-- Modal content -->
		<div class="modal-content" style="width: 500;">
			<span class="close">&times;</span>
			<div class="container">
				<!--     <form action="${path }/do/master/changeOrderState" method="post">   
        <input type="hidden"  name="command" value="ChangeOrderWorkState">        
        <input type="hidden" id="orderId" name="orderId" >
        <input type="hidden" id="newState" name="newState" > -->
				Number: <label id="N"></label> <br> Order name: <label
					id="name"></label> <br> Date: <label id="date"></label> <br>
				Description: <label id="description"></label> <br>

				<!--        <input type="submit" id="changeSt" value="OK"> -->
				<button id="okMod" onclick="closeModal();">OK</button>
			</div>
		</div>
	</div>
	<script>
	   function setPrice(orderId){
		   var buttonId="#setPriceBut"+orderId;
		   $(buttonId).html("Save");
		   $(buttonId).attr('onclick','location.href=');
	   }
	
		var modal = document.getElementById("myModal");
		function showDetailsModal(id, name, date, description, workState) {
			modal.style.display = "block";

			$("#orderId").val(id);
			$("#N").html(id);
			$("#name").html(name);
			$("#date").html(date);
			$("#description").html(description);
		}
		function closeModal() {
			modal.style.display = "none";
		}
	</script>
</body>
</html>