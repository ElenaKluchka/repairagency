<%@ include file="jspf/page_taglib.jspf" %>
	
<html>
<c:set var="title" value="Login" />
<%@ include file="jspf/login_head.jspf" %>

<body>
<%@ include file="jspf/header.jspf" %>
<div align="center">
	<p style="color: red">${error}</p>	
	${lang}
	${cookie['lang'].value}
	<form action="${path }/do/login" method="post" style= "width: 300px">
	   <input type="hidden" id="filfI" name="command" value="Login">
		<h3 style="text-align:center"><fmt:message key="login_jsp.label.login"/></h3>
		<div class="container">
			<label for="uname">
			<b><fmt:message key="login_jsp.link.name"/></b></label> 
			<input type="text" value="${login}" placeholder='<fmt:message key="login_jsp.placeholder.name"/>' name="uname" maxlength="45" required>
			<label for="psw"><b><fmt:message key="login_jsp.label.password"/></b></label> 
			<input type="password" placeholder="<fmt:message key="login_jsp.placeholder.psw"/>" name="psw" minlength="6" maxlength="15" required >

			<button type="submit" class ="blueBut"><fmt:message key="login_jsp.button.login"/></button>
	<!--  		<label> <input type="checkbox" checked="checked"
				name="remember"> Remember me
			</label>-->
			<a href="${path }/signup.jsp"><fmt:message key="login_jsp.link.registration"/></a>
		</div>

		<div class="container" style="background-color: #f1f1f1">
		<!--	<button type="button" class="cancelbtn">Cancel</button>-->
		<!--	<span class="psw">Forgot <a href="#">password?</a></span>-->
		</div>
	</form>
	</div>
</body>
</html>
