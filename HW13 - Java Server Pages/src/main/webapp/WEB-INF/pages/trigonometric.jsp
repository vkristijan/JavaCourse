<%@ page session="true" contentType="text/html;  charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="style.jsp">
		<title> Trigonometric values </title>
	</head>
	
	<body>
	
		<table border='1'>
			<tr>
				<td> <strong>x</strong> </td>
				<td> <strong>sin(x)</strong> </td>
				<td> <strong>cos(x)</strong> </td>
			</tr>
		
			<c:forEach var="element" items="${trigValues}">
				<tr>
					<td> ${element.getX()} </td>
					<td> ${element.getSin()} </td>
					<td> ${element.getCos()} </td>
				</tr>
			</c:forEach>
		</table>
		
		<a href=".\index.jsp">Return</a>
	</body>
</html>