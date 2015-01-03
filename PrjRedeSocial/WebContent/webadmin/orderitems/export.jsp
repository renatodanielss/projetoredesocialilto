<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainOrderitems" %><%@ page import="HardCore.MenuContent" %><%
	UCmaintainOrderitems maintainOrderitems = new UCmaintainOrderitems(mytext);
	LinkedHashMap orderitems;
	if (! myrequest.getMethod().equals("POST")) {
		maintainOrderitems.getExport(mysession, myrequest, myresponse, myconfig, db);
		%><%@ include file="export.jsp.html" %><%
	} else {
		orderitems = maintainOrderitems.doExport(mysession, myrequest, myresponse, myconfig, db);
		%><%@ include file="export.jsp.csv" %><%
	}
%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>