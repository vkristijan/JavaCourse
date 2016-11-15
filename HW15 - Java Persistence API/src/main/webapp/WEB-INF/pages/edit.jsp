<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List"%>

<html>
	<head>
		<title> ${title} - edit </title>
	</head>
	<body>
		<div align="center">
			<c:choose>
				<c:when test="${firstName != null}">
					${firstName} ${lastName} <a href = "../../logout"> Logout </a>
				</c:when>
			</c:choose>
		
			<h1>
				Edit post
			</h1>
			<c:choose>
				<c:when test="${author.equals(nick)}">
					<form action="../../edit" method="POST">
						<table>
							<tr>
								<td> Title: </td>
								<td> <input type = "text" name = "title" value="${title}"  size = "60"/> </td>
							</tr>
							
							<tr>
								<td> Text: </td>
							</tr>
							
							<tr>
								<td colspan = "2"> 
									<textarea name = "text" rows = "15" cols = "90">
										${text}
									</textarea>
								</td>
							</tr>
	
						</table>
						
						<input type="submit" value="Edit post" name = "add"/>
						<input type="hidden" name="entryId" value="${entryId}">
					</form>
				</c:when>
				<c:otherwise>
					<h1>
						You are not allowed to edit this blog entry!
					</h1>
				</c:otherwise>
			</c:choose>
			
			<a href = "/blog"> Home </a>
		</div>
	</body>
</html>
