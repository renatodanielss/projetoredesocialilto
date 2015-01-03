<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainUsergroups" %>
<%@ page import="HardCore.Usergroup" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainUsergroups maintainUsergroups = new UCmaintainUsergroups(mytext);
	Usergroup usergroup = maintainUsergroups.getIndex(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>