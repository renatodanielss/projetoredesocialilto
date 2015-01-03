<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainFilegroups" %>
<%@ page import="HardCore.Filegroup" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainFilegroups maintainFilegroups = new UCmaintainFilegroups(mytext);
	Filegroup filegroup = maintainFilegroups.getIndex(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>