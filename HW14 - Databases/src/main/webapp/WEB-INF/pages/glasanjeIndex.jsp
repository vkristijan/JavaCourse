<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title> Glasanje </title>
	</head>

	<body>
		<h1> ${title} </h1>
		<p> ${message} </p>
		<ol>
			<c:forEach var="element" items="${votingEntries}">
				<li>
					<a href= "glasanje-glasaj?id=${element.id}"> 
						${element.optionTitle} 
					</a> 
				</li>
			</c:forEach>
		</ol>
	</body>
</html>