<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainProducts" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.MenuContent" %><%

	UCmaintainProducts maintainProducts = new UCmaintainProducts(mytext);
	Content product;
	if (! myrequest.getMethod().equals("POST")) {
		product = maintainProducts.getExport(mysession, myrequest, myresponse, myconfig, db);
		%><%@ include file="export.jsp.html" %><%
	} else {
		product = maintainProducts.doExport(mysession, myrequest, myresponse, myconfig, db);
		%><%@ include file="export.jsp.csv" %><%
	}
%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>