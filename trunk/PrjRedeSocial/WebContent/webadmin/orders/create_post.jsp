<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainOrders" %>
<%@ page import="HardCore.Order" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainOrders maintainOrders = new UCmaintainOrders(mytext);
	Order order = maintainOrders.doCreate(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	String error = maintainOrders.getError();
%>
<%@ include file="create_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>