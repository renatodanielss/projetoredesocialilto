<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainFilegroups" %>
<%@ page import="HardCore.Filegroup" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainFilegroups maintainFilegroups = new UCmaintainFilegroups(mytext);
	Filegroup filegroup = maintainFilegroups.doCreate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="create_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>