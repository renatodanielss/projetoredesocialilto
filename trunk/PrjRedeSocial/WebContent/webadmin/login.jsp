<%@ include file="config.jsp" %>
<%@ page import="HardCore.UCaccessAdministration" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCaccessAdministration accessAdministration = new UCaccessAdministration();
	String url = accessAdministration.getLoginURL(mysession, myrequest, myresponse, myconfig, db, mytext);
%>
<%@ include file="login.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>