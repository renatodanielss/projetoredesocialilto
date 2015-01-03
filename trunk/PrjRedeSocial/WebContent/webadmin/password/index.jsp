<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCretrievePassword" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCretrievePassword retrievePassword = new UCretrievePassword(mytext);
	String error = retrievePassword.emailSuperadminPassword(servletcontext, mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>