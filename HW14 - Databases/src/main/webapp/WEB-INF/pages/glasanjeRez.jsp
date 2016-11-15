<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
	<head>
		<link rel="stylesheet" type="text/css" href="style.jsp">
		<title> Rezultati glasanja </title>
	</head>
	<body>
		<h1>Rezultati glasanja</h1>
		<p>Ovo su rezultati glasanja.</p>
		<table border="1" class="rez">
			<thead>
				<tr>
					<th>Bend</th>
					<th>Broj glasova</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="element" items="${votingResults}">
					<tr> 
						<td> ${ element.optionTitle } </td>
						<td> ${ element.votesCount } </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<h2>Grafički prikaz rezultata</h2>
		<img alt="Pie-chart" src='glasanje-grafika?pollId=<c:out value="${pollId}"/>' width="400" height="400"/>
		
		<h2>Rezultati u XLS formatu</h2>
		<p>
			Rezultati u XLS formatu dostupni su
			<a href='glasanje-xls?pollId=<c:out value="${pollId}"/>'>ovdje</a>
		</p>
		
		<h2>Razno</h2>
		<p>Dodatni detalji o pobjedniku:</p>
		<ul>
			<c:forEach var="element" items="${winners}">
				<li><a
					href="${element.optionLink}"
					target="_blank">${element.optionTitle}</a></li>
			</c:forEach>
		</ul>
		
		<h2> Povratak </h2>
		<a href=".\">Return</a>
	</body>
</html>