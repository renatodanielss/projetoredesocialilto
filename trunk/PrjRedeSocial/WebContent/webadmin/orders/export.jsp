<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainOrders" %><%@ page import="HardCore.MenuContent" %><%
	UCmaintainOrders maintainOrders = new UCmaintainOrders(mytext);
	LinkedHashMap orders;
	if (! myrequest.getMethod().equals("POST")) {
		maintainOrders.getExport(mysession, myrequest, myresponse, myconfig, db);
		%><%@ include file="export.jsp.html" %><%
	} else {
		orders = maintainOrders.doExport(mysession, myrequest, myresponse, myconfig, db);
		%><%@ include file="export.jsp.csv" %><%
	}
%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>