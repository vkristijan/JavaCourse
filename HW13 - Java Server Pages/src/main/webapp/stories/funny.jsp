<%@page import="java.util.Random"%>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %>

<%
	Random rnd = new Random();
	int r = rnd.nextInt(256);
	int g = rnd.nextInt(256);
	int b = rnd.nextInt(256);
%>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../style.jsp">
		<title>Funny story</title>
	</head>
	
	<body>
		<font color='rgb(<%= r %>, <%= g %>, <%= b %>)'>
			99 little bugs in the code. <br />
			99 bugs in the code. <br />
			Take one down, patch it arround. <br />
			<br />
			127 little bugs in the code... <br />
		</font>
	</body>
</html>
