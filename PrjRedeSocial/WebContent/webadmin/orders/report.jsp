<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCreportOrders" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Common" %>
<%@ page import="HardCore.http" %>
<%
	UCreportOrders reportOrders = new UCreportOrders(mytext);
	if (reportOrders.getAccess(mysession, myrequest, myresponse, myconfig, db, logdb, database)) {
%>
<%@ include file="report.jsp.html" %>
<%
	}
%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>