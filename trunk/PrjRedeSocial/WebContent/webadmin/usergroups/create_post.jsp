<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainUsergroups" %>
<%@ page import="HardCore.Usergroup" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainUsergroups maintainUsergroups = new UCmaintainUsergroups(mytext);
	Usergroup usergroup = maintainUsergroups.doCreate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="create_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>