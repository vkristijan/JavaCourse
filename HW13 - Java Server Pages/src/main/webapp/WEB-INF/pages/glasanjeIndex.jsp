<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="style.jsp">
		<title> Glasanje </title>
	</head>

	<body>
		<h1>Glasanje za omiljeni bend:</h1>
		<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na
			link kako biste glasali!</p>
		<ol>
			<c:forEach var="element" items="${votingEntries}">
				<li>
					<a href= "glasanje-glasaj?id=${element.getValue().getId() }"> 
						${element.getValue().getName()} 
					</a> 
				</li>
			</c:forEach>
		</ol>
	</body>
</html>