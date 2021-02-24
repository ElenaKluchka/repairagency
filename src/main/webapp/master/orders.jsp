<%@ include file="/jspf/page_taglib.jspf" %>
<html>
<c:set var="title" value="Manage orders" />
<%@ include file="/jspf/logged_user_head.jspf" %>
<body>                                               <!--   <fmt:message key=""/> -->
<%@ include file="/jspf/header.jspf" %>
     
	<h3><fmt:message key="orders_jsp.label.orders"/>:</h3>
	<table style="width: 100%" id= "orders">
		<tr>
			<th><fmt:message key="order.number"/></th>
			<th><fmt:message key="order.name"/></th>			
			<th><fmt:message key="order.date"/></th>
			<th><fmt:message key="order.state"/></th>
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
				<td>
					<c:if test="${order.workState ne 'FINISHED' }">
						<button id="myBtn" onclick="showDetailsModal('${order.id}','${order.name}',
						'${stdDatum }','${order.description}','${order.workState}');"><fmt:message key="order.details"/></button>
					</c:if>
			    </td>				
						
			</tr>
		</c:forEach>
	</table>
	<br>
	 
    <div id="myModal" class="modal">

      <!-- Modal content -->
      <div class="modal-content" style="width:500;">
        <span class="close">&times;</span>
       <div class="container">
       <form action="${path }/do/master/changeOrderState" method="post">   
        <input type="hidden"  name="command" value="ChangeOrderWorkState">        
        <input type="hidden" id="orderId" name="orderId" >
        <input type="hidden" id="newState" name="newState" >
        <fmt:message key="order.number"/>: <label id="N"></label>   <br>
        <fmt:message key="order.name"/>: <label id="name"></label>   <br>
        <fmt:message key="order.date"/>: <label id="date"></label>   <br>
        <fmt:message key="order.description"/>: <label id="description"></label>   <br>
                       
        <input type="submit" id="changeSt" value="">
         <button  id ="okMod" onclick="closeModal();"> <fmt:message key="button.close"/></button>           
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
        	  $('#changeSt').val('<fmt:message key="orders_jsp.master.modal.button"/>');
        	  $('#newState').val('IN_WORK');
          }else if(workState =='IN_WORK'){
        	  $('#changeSt').show();  
        	  $('#changeSt').val('<fmt:message key="orders_jsp.master.modal.button.finished"/>');
        	  $('#newState').val('FINISHED');
          }else if(workState =='FINISHED'){
        	  $('#changeSt').hide();
        	  $('#okMod').show();
          }
      }
      
      // Get the button that opens the modal
      var btn = document.getElementById("myBtn");

      // Get the <span> element that closes the modal
      var span = document.getElementsByClassName("close")[0];

      $(document).ready(function() {
          span.onclick = function() {
              modal.style.display = "none";
          }
          
          // When the user clicks anywhere outside of the modal, close it
          window.onclick = function(event) {
              if (event.target == modal) {
                  modal.style.display = "none";
              }
          }
      });
      function closeModal(){
    	  modal.style.display = "none";
    	  }

</script>
</body>
</html>