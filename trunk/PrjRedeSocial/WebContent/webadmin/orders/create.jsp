<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainOrders" %>
<%@ page import="HardCore.Order" %>
<%@ page import="HardCore.Workflow" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Common" %>
<%@ page import="java.net.URLEncoder" %>
<%
	UCmaintainOrders maintainOrders = new UCmaintainOrders(mytext);
	Order order = maintainOrders.getCreate(mysession, myrequest, myresponse, myconfig, db);
	Workflow workflow = new Workflow(mytext);
%>
<%@ include file="create.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>