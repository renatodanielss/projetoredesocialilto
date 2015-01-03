<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCreportUsage" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Common" %>
<%
	UCreportUsage reportUsage = new UCreportUsage(mytext);
	HashMap usage = reportUsage.getWeeks(mysession, myrequest, myresponse, myconfig, db, logdb, database);
%>
<%@ include file="weeks.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>