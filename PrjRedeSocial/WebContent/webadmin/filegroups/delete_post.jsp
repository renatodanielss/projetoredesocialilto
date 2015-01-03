<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainFilegroups" %>
<%@ page import="HardCore.Filegroup" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainFilegroups maintainFilegroups = new UCmaintainFilegroups(mytext);
	Filegroup filegroup = maintainFilegroups.doDelete(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>