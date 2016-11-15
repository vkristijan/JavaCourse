<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List"%>

<html>
	<head>
		<title> Blog entries </title>
	</head>
	<body>
		<div align="center">
			<c:choose>
				<c:when test="${firstName != null}">
					${firstName} ${lastName} <a href = "../logout"> Logout </a>
				</c:when>
			</c:choose>
			
			<h1> Blog entries </h1>
			<ul>
				<c:forEach var="element" items="${entries}">
					<li> <a href = "./${author}/${element.id}"> ${element.title} </a> </li>
				</c:forEach>
			</ul>
			
			<c:choose>
				<c:when test="${author.equals(nick)}">
					<form action="./${nick}/new" method="POST">
						<input type="submit" name="newPost" value="New blog post"/>
					</form>
				</c:when>
			</c:choose>
			
			<a href = "/blog/"> Home </a>
		</div>
	</body>
</html>
