<%@page import="mvc.login_logout.dao.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Welcome</title>
</head>
<body>
<% 
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
%>

	<h1>Profile</h1>
	<p><%=session.getAttribute("user")%></p>
	
	<p>Profile</p>

	<p>
		<a href="logout">Logout</a>
	</p>

</body>
</html>