<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCreportOrders" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Common" %>
<%@ page import="HardCore.Currency" %>
<%
	UCreportOrders reportOrders = new UCreportOrders(mytext);
	HashMap usage = reportOrders.getSummary(mysession, myrequest, myresponse, myconfig, db, logdb, database);
%>
<%@ include file="summary.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>