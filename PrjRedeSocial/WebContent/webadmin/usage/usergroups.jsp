<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCreportUsage" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Common" %>
<%
	UCreportUsage reportUsage = new UCreportUsage(mytext);
	HashMap usage = reportUsage.getUsergroups(mysession, myrequest, myresponse, myconfig, db, logdb, database);
%>
<%@ include file="usergroups.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>