<%@ include file="config.jsp" %>
<%@ page import="HardCore.UCaccessAdministration" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.http" %>
<%
	UCaccessAdministration.doIndex(mytext, "", mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="index.statistics.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>