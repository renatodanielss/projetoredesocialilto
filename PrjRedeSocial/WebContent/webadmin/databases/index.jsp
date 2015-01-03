<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainDatabases" %>
<%@ page import="HardCore.Databases" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainDatabases maintainDatabases = new UCmaintainDatabases(mytext);
	Databases databases = maintainDatabases.getIndex(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>