<%@ page language = "java" session="true" contentType="text/html; charset=UTF-8" %>

<%
	long startTime = (long)getServletContext().getAttribute("time");
	long currentTime = System.currentTimeMillis();
	
	long diff = currentTime - startTime;
	
	StringBuilder answer = new StringBuilder();
	
	long yearMillis = 365 * 24 * 60 * 60 * 1000;
	long years = diff / yearMillis;
	if (years > 0){
		diff -= (years * yearMillis);
		answer.append(years + " years ");
	}
	
	long dayMillis = 24 * 60 * 60 * 1000;
	long days = diff / dayMillis;
	if (days > 0){
		diff -= (days * dayMillis);
		answer.append(days + " days ");
	}
	
	long hourMills = 60 * 60 * 1000;
	long hours = diff / hourMills;
	if (hours > 0){
		diff -= (hours * hourMills);
		answer.append(hours + " hours ");
	}
	
	long minuteMills = 60 * 1000;
	long minutes = diff / minuteMills;
	if (minutes > 0){
		diff -= (minutes * minuteMills);
		answer.append(minutes + " minutes ");
	}
	
	long secondMills = 1000;
	long seconds = diff / secondMills;
	if (seconds > 0){
		diff -= (seconds * secondMills);
		answer.append(seconds + " seconds ");
	}
	
	if (diff > 0){
		answer.append(diff + " miliseconds ");
	}
%>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="style.jsp">
		<title>Color chooser</title>
	</head>
	
	<body>
		<h1> The server is running for: <%= answer %> </h1>
		
	</body>
</html>
