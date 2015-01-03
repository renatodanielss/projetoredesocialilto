<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainOrders" %>
<%@ page import="HardCore.Order" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%
	UCmaintainOrders maintainOrders = new UCmaintainOrders(mytext);
	maintainOrders.doDeleteMultiple(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	String error = maintainOrders.getError();
%>
<%@ include file="delete_multiple_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>