<%@ include file="/jspf/page_taglib.jspf" %>
<html>
<c:set var="title" value="Manage orders" />
<%@ include file="/jspf/logged_user_head.jspf" %>
<body>
<%@ include file="/jspf/header.jspf" %>  <!--   <fmt:message key=""/> -->

    <form action="${path }/do/client/addOrder" method="post" style="width:400px">
        <h3 style="text-align: center"><fmt:message key="orders_jsp.label.create"/></h3>
            <div class="container">     
		        <input type="hidden" name="command" value="AddOrder">
		        <fmt:message key="order.name"/>: <input type="text" id="orderName" name="orderName" required> <br>
		        <br> <fmt:message key="order.description"/>: <textarea name="orderDescription" id="orderDescription" 
		             class="description" placeholder=" <fmt:message key="orders_jsp.descript"/>" 
		              maxlength="500" required></textarea>
		        <button type="submit" class="blueBut"> <fmt:message key="orders_jsp.label.create"/></button>
            </div>
    </form>
    <fmt:message key="orders_jsp.balance"/>:
    ${client.balance==0?'0.00f':client.balance}
    <h3><fmt:message key="orders_jsp.label.orders"/>:</h3>
    <table style="width:100%" id= "orders">
        <tr>
            <th><fmt:message key="order.number"/></th>
            <th><fmt:message key="order.name"/></th>
            <th><fmt:message key="order.description"/></th>
            <th><fmt:message key="order.price"/></th>
            <th><fmt:message key="order.date"/></th>
            <th><fmt:message key="order.payment"/></th>
            <th><fmt:message key="order.state"/></th>
            
            <th><fmt:message key="order.feedback"/></th>
            
        </tr>
        <c:forEach items="${client.orders}" var="order" varStatus="count">   
        <tr>      
            <td>${order.id}</td>
            <td>${order.name}</td>
            <td>${order.description}</td>
            <td>${order.price}</td>
            <td>            
                <my:date dat="${order.date}"/>
            </td>
            <td>${order.managementState=='NEW'?' wait for payment':order.managementState}</td>
            <td>${order.workState=='NEW'?'in work':order.workState}
            </td>
     
            <td>
                  <c:if test = "${ order.workState eq 'FINISHED'}">   
                 <c:if test = "${ empty order.feedback}">
                        <button id="myBtn" onclick="showFeedbackModal('${order.id}');"><fmt:message key="orders_jsp.button.feedback"/></button>
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
    
    <div id="myModal" class="modal"><fmt:message key=""/>

      <!-- Modal content -->
      <div class="modal-content" style="width:500;">
        <span class="close">&times;</span>
       <div class="containerModal">
       <form action="${path }/do/client/feedback" method="post">   
        <input type="hidden"  name="command" value="Feedback">
        <input type="hidden" id="orderId" name="orderId" >
        <label for="subject"><fmt:message key="orders_jsp.modal.feedback"/></label>   <br>   
        <textarea name="feedback" class="textModal" placeholder="<fmt:message key="orders_jsp.modal.text"/>" maxlength="500" required></textarea>
        <div class="submMod">        
        <input type="submit" value="<fmt:message key="button.save"/>">  
         <button id="okMod" onclick="closeModal();"><fmt:message key="button.cancel"/></button>  
         </div>   
       </form>
       
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