<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"
	name="Login">
<link href="${path }/css/login.css" rel="stylesheet" type="text/css" media="all" />

<title>Login Application</title>
</head>
<body>
<div class="content">
	<p style="color: red">${error}</p>	
	
	<form action="${path }/do/login" method="post" style= "width: 300px">
	   <input type="hidden" id="filfI" name="command" value="Login">
		<h3 style="text-align:center">Login</h3>
		<div class="container">
			<label for="uname">
			<b>Username</b></label> 
			<input type="text" placeholder="Enter Username" name="uname" required> 
			<label for="psw"><b>Password</b></label> 
			<input type="password" placeholder="Enter Password" name="psw" required>
			

			<button type="submit" class ="blueBut">Login</button>
	<!--  		<label> <input type="checkbox" checked="checked"
				name="remember"> Remember me
			</label>-->
		</div>

		<div class="container" style="background-color: #f1f1f1">
		<!--	<button type="button" class="cancelbtn">Cancel</button>-->
		<!--	<span class="psw">Forgot <a href="#">password?</a></span>-->
		</div>
	</form>
	</div>
</body>
</html>
