<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="${path }/css/login.css" rel="stylesheet" type="text/css" media="all" />
<title>Registration</title>
</head>
<body>
<div class="content" align="center">
    <p style="color: red">${error}</p>  
    
    <form action="${path }/do/signup" method="post" style= "width: 300px">
       <input type="hidden" id="filfI" name="command" value="Signup">
        <h3 style="text-align:center">Login</h3>
        <div class="container">
        
            <label for="uname">
            <b>Username</b></label>             
            <input type="text" placeholder="Enter Username" name="uname" required> 
            
              <label for="email"><b>Email</b></label>
            <input type="email" placeholder="Enter email" name="email" required>
            
            <label for="phone">Phone</label>
            <input type="tel" placeholder="Enter Phone" name="phone" pattern="[0-9]{5}[-][0-9]{7}[-][0-9]{1}" required >
            
            <label for="psw"><b>Password</b></label> 
            <input type="password" placeholder="Enter Password" name="psw" required minlength="6" maxlength="10">            

            <button type="submit" class ="blueBut">Registration</button>           
        </div>

        <div class="container" style="background-color: #f1f1f1">
        <!--    <button type="button" class="cancelbtn">Cancel</button>-->
        <!--    <span class="psw">Forgot <a href="#">password?</a></span>-->
        </div>
    </form>
    </div>
</body>
</html>