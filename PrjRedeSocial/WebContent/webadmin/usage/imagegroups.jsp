<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCreportUsage" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Common" %>
<%
	UCreportUsage reportUsage = new UCreportUsage(mytext);
	HashMap usage = reportUsage.getImagegroups(mysession, myrequest, myresponse, myconfig, db, logdb, database);
%>
<%@ include file="imagegroups.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>