<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List"%>

<html>
	<head>
		<title> Register </title>
	</head>
	<body>
		<div align="center">
			<h1> Register </h1>
	
			<form action = "./register" method = "post">
				<table>
					<tr>
						<td> First name: </td>
						<td> <input type = "text" name = "firstName" size = "25"/> </td>
						<td> ${errors.get("firstName")} </td>
					</tr>
					
					<tr>
						<td> Last name: </td>
						<td> <input type = "text" name = "lastName" size = "25"/> </td>
						<td> ${errors.get("lastName")} </td>
					</tr>
					
					<tr>
						<td> E-mail: </td>
						<td> <input type = "text" name = "email" size = "25"/> </td>
						<td> ${errors.get("email")} </td>
					</tr>
					
					<tr>
						<td> Nick: </td>
						<td> <input type = "text" name = "nick" size = "25"/> </td>
						<td> ${errors.get("username")} </td>
					</tr>
					
					<tr>
						<td> Password: </td>
						<td> <input type = "password" name = "password" size = "25"/> </td>
						<td> ${errors.get("username")} </td>
					</tr>
					
					<tr>
						<td>  </td>
						<td> <input type = "submit" value = "Register"/> </td>
					</tr>
				</table>
			</form>
			
			<ul>
				<c:forEach var="element" items="${authors}">
					<li> ${element.nick} </li>
				</c:forEach>
			</ul>
		</div>
	</body>
</html>
