<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCreportOrders" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Common" %>
<%
	UCreportOrders reportOrders = new UCreportOrders(mytext);
	HashMap usage = reportOrders.getProductgroups(mysession, myrequest, myresponse, myconfig, db, logdb, database);
%>
<%@ include file="productgroups.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>