<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainOrders" %>
<%@ page import="HardCore.Order" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%
	UCmaintainOrders maintainOrders = new UCmaintainOrders(mytext);
	maintainOrders.doMoveMultiple(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	String error = maintainOrders.getError();
%>
<%@ include file="move_multiple.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>