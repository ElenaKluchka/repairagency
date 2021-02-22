<%@ include file="jspf/page_taglib.jspf" %>
  
<html>
<head>
<c:set var="title" value="Registration" />
<%@ include file="jspf/login_head.jspf" %>

<body>
<%@ include file="jspf/header.jspf" %>
<div align="center">
    <p style="color: red">${error}</p><!--   <fmt:message key=""/> -->
    
    <form action="${path }/do/signup" method="post" style= "width: 300px">
       <input type="hidden" id="filfI" name="command" value="Signup">
        <h3 style="text-align:center"><fmt:message key="signup_jsp.label.registr"/></h3>
        <div class="container">
        
            <label for="uname">
            <b><fmt:message key="signup_jsp.label.name"/></b></label>             
             <input type="text"  value="${newCustomer.name }" placeholder="<fmt:message key="signup_jsp.placeholder.name"/>" name="uname" maxlength="45" required>      
            
              <label for="email"><b>Email</b></label>
             <input type="email" value="${newCustomer.email }" placeholder="Enter email" name="email" minlength="3" maxlength="20" required> 
  
            <label for="phone"><fmt:message key="signup_jsp.label.phone"/></label>
   
            <input id="online_phone" value="${newCustomer.phone}"
             type="tel" maxlength="25"
             name="phone"  autofocus="autofocus"      
         pattern="\+380\s?[\(]{0,1}[0-9]{2}[\)]{0,1}\s?\d{3}[-]{0,1}\d{2}[-]{0,1}\d{2}"       
        placeholder="<fmt:message key="signup_jsp.placeholder.phone"/>" required>  
            
            <label for="psw"><b><fmt:message key="signup_jsp.label.password"/></b></label> 
        <!--     <input type="password" placeholder="Enter Password" name="psw" >       -->      
            
        <input type="password" placeholder="<fmt:message key="signup_jsp.placeholder.psw"/>" name="psw" required minlength="6" maxlength="15">

            <button type="submit" class ="blueBut"> <fmt:message key="signup_jsp.button.registr"/></button>           
        </div>

        <div class="container" style="background-color: #f1f1f1">
        <!--    <button type="button" class="cancelbtn">Cancel</button>-->
        <!--    <span class="psw">Forgot <a href="#">password?</a></span>-->
        </div>
    </form>
    </div>   
</body>
</html>