<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainImagegroups" %>
<%@ page import="HardCore.Imagegroup" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainImagegroups maintainImagegroups = new UCmaintainImagegroups(mytext);
	Imagegroup imagegroup = maintainImagegroups.getCreate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="create.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>