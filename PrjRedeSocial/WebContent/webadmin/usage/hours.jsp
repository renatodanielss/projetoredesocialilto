<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCreportUsage" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Common" %>
<%
	UCreportUsage reportUsage = new UCreportUsage(mytext);
	HashMap usage = reportUsage.getHours(mysession, myrequest, myresponse, myconfig, db, logdb, database);
%>
<%@ include file="hours.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>