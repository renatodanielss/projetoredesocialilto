<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainOrders" %>
<%@ page import="HardCore.Order" %>
<%@ page import="HardCore.Workflow" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="java.net.URLEncoder" %>
<%
	UCmaintainOrders maintainOrders = new UCmaintainOrders(mytext);
	Order order = maintainOrders.getDelete(mysession, myrequest, myresponse, myconfig, db);
	Workflow workflow = new Workflow(mytext);
%>
<%@ include file="delete.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>