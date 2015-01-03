<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCreportOrders" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Common" %>
<%
	UCreportOrders reportOrders = new UCreportOrders(mytext);
	HashMap usage = reportOrders.getMonths(mysession, myrequest, myresponse, myconfig, db, logdb, database);
%>
<%@ include file="months.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>