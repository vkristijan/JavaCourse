<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List"%>

<html>
	<head>
		<title> Login </title>
	</head>
	<body>
		<div align="center">
			<c:choose>
				<c:when test="${userId == null}">
					<h1> Login </h1>
			
					<form action = "./login" method = "post">
						<table>
							<tr>
								<td> Username: </td>
								<td> <input type = "text" value = "${username}" name = "username" size = "25"/> </td>
							</tr>
							
							<tr>
								<td> Password: </td>
								<td> <input type = "password" name = "password" size = "25"/> </td>
							</tr>
							
							<tr>
								<td colspan="2"> ${error} </td>
							</tr>
							
							<tr>
								<td> <a href="./register">Register</a> </td>
								<td> <input type = "submit" value = "Login"/> </td>
							</tr>
						</table>
					</form>
				</c:when>
				<c:otherwise>
					<h1> Welcome back ${firstName} </h1>
					<a href = "./logout"> Logout </a>
				</c:otherwise>
			</c:choose>
			
			<h1> Registered authors </h1>
			<ul>
				<c:forEach var="element" items="${authors}">
					<li> <a href = "./author/${element.nick}"> ${element.nick} </a> </li>
				</c:forEach>
			</ul>
		</div>
	</body>
</html>
