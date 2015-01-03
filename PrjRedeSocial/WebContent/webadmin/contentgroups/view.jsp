<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContentgroups" %>
<%@ page import="HardCore.Contentgroup" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainContentgroups maintainContentgroups = new UCmaintainContentgroups(mytext);
	Contentgroup contentgroup = maintainContentgroups.getView(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="view.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>