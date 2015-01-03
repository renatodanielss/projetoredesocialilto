<%@ include file="config.jsp" %>
<%@ page import="HardCore.UCaccessAdministration" %>
<%
	mysession.set("log", "");
	UCaccessAdministration accessAdministration = new UCaccessAdministration();
	accessAdministration.doLogin(mytext, servletcontext, mysession, myrequest, myresponse, myconfig, db, database);
%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>