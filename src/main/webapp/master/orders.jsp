<%@ include file="/jspf/page_taglib.jspf" %>
<html>
<c:set var="title" value="Manage orders" />
<%@ include file="/jspf/logged_user_head.jspf" %>
<body>
<%@ include file="/jspf/header.jspf" %>
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
				<td>     
				    <fmt:parseDate  value="${order.date}"  type="date" pattern="yyyy-MM-dd" var="parsedDate" />
                    <fmt:formatDate value="${parsedDate}" type="date" pattern="dd.MM.yyyy" var="stdDatum" />
                    ${stdDatum }
                </td>			
				<td>${order.workState}</td>
				<td><button id="myBtn" 
				onclick="showDetailsModal('${order.id}','${order.name}',
				'${stdDatum }','${order.description}','${order.workState}');">Details</button></td>				
						
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
         <button  id ="okMod" onclick="closeModal();">OK</button>           
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
          $('#okMod').hide();
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
        	  $('#okMod').show();
          }
      }
</script>
</body>
</html>