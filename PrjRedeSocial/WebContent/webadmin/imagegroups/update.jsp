<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainImagegroups" %>
<%@ page import="HardCore.Imagegroup" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainImagegroups maintainImagegroups = new UCmaintainImagegroups(mytext);
	Imagegroup imagegroup = maintainImagegroups.getUpdate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="update.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>