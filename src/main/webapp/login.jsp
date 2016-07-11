<%@ page contentType="text/html;charset=UTF-8" import="java.util.List,java.util.Map"%> 

<html>
<head>
<link rel="stylesheet" type="text/css" href="css/login.css">
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/login.js"></script>
</head>
<body>

<section class="login">
	<div class="titulo">Staff Login</div>
	<div align="center" style="color:white">${it.error}</div>
	<form id="loginForm" action="login" method="post" enctype="application/x-www-form-urlencoded">
    	<input type="text" required title="Username required" placeholder="Username" id="username" name ="userName" data-icon="U">
        <input type="password" required title="Password required" placeholder="Password" id="password" name = "password" data-icon="x">
        <div class="olvido">
        	<div class="col"><a href="register.html" title="Ver Carásteres">Register</a></div>
            <div class="col"><a href="#" title="Recuperar Password">Fortgot Password?</a></div>
        </div>
       <!--  <input type="button" name="login" class="enviar" id="submit" value="Submit"> -->
        <a href="#" class="enviar" id="submit" >Submit</a>
    </form>
</section>
</body>
</html>