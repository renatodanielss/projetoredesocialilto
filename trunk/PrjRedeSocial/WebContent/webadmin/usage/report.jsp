<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCreportUsage" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Common" %>
<%@ page import="HardCore.http" %>
<%
	UCreportUsage reportUsage = new UCreportUsage(mytext);
	if (reportUsage.getAccess(mysession, myrequest, myresponse, myconfig, db, logdb, database)) {
%>
<%@ include file="report.jsp.html" %>
<%
	}
%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>