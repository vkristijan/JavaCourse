<%@page import="hr.fer.zemris.java.tecaj_13.model.Poll"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List"%>

<html>
<body>
	<b>Choose a poll to vote:</b>
	<br>

	<ul>
		<c:forEach var="element" items="${polls}">
			<li>
				<a href="glasanje?pollID=${element.id}"> ${element.title} </a>
			</li>
		</c:forEach>
	</ul>

</body>
</html>