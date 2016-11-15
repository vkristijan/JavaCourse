<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<% 
	Object errorMsg = request.getAttribute("error");
	if (errorMsg == null) {
		errorMsg = "";
	}
%>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="style.jsp">
		<title> Error </title>
	</head>
	
	<body>
		<h1><%= errorMsg %></h1>
		
		<a href=".\index.jsp">Return</a>
	</body>
</html>