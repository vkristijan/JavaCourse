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
						<td> ${ element.getName() } </td>
						<td> ${ element.getNumberOfVotes() } </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<h2>Grafički prikaz rezultata</h2>
		<img alt="Pie-chart" src='glasanje-grafika' width="400" height="400"/>
		
		<h2>Rezultati u XLS formatu</h2>
		<p>
			Rezultati u XLS formatu dostupni su <a href='glasanje-xls'>ovdje</a>
		</p>
		
		<h2>Razno</h2>
		<p>Primjeri pjesama pobjedničkih bendova:</p>
		<ul>
			<li><a
				href="${votingResults.get(0).getUrl()}"
				target="_blank">${votingResults.get(0).getName()}</a></li>
			<li><a
				href="${votingResults.get(1).getUrl()}"
				target="_blank">${votingResults.get(1).getName()}</a></li>
		</ul>
		
		<h2> Povratak </h2>
		<a href=".\index.jsp">Return</a>
	</body>
</html>