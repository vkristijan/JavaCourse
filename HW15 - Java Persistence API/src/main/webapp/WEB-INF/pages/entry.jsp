<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List"%>

<html>
	<head>
		<title> ${title} </title>
	</head>
	<body>
		<div align="center">
			<c:choose>
				<c:when test="${firstName != null}">
					${firstName} ${lastName} <a href = "../../logout"> Logout </a>
				</c:when>
			</c:choose>
		
			<h1>${title}</h1>
			<h2>${author}, ${date}</h2>
			
			${text}
			
			<c:choose>
				<c:when test="${author.equals(nick)}">
					<form action="./edit" method="POST">
						<input type="submit" value="Edit post" name = "add"/>
						<input type="hidden" name="entryId" value="${entryId}">
					</form>
				</c:when>
			</c:choose>
			
			<br/>
			<br/>
			
			<h3> Comments: </h3>
			<table>
				<c:forEach var="element" items="${comments}">
					<tr>
						<td>
							<strong>${element.usersEMail}</strong>
							${element.postedOn}
						</td>
					</tr>
					<tr>
						<td>
							${element.message}
							<br/> <br/>
						</td>
					</tr>
				</c:forEach>
			</table>
			
			<form action="../../newComment" method="POST">
				<table>
					<tr>
						<td>
							E-mail:
						</td>
						<td> 
							<input type = "text" name = "email" size = "25" value = "${email}">
						</td>
					</tr>
				
					<tr>
						<td colspan = "2"> 
							<textarea name = "message" rows = "5" cols = "40">
							</textarea>
						</td>
					</tr>
					
					<tr>
						<td>
							<input type="submit" value="Add comment" name = "add"/>
						</td>
					</tr>
				</table>
				<input type="hidden" name="blogEntry" value="${entryId}">
			</form>
			
			<a href = "/blog"> Home </a>
		</div>
	</body>
</html>
