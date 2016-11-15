<% 
	Object color = request.getSession().getAttribute("pickedBgCol");
	if (color == null) {
		color = "white";
	}
%>

body {
    background-color:  <%=color%> ;
}

table.rez td {
	text-align: center;
}
