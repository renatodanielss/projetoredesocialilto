<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCconfigureSystem" %>
<%@ page import="HardCore.Currency" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.User" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%
	UCconfigureSystem configureSystem = new UCconfigureSystem(mytext);
	configureSystem.doConfigure(ini_database, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
	String error = configureSystem.getError();
%>
<%@ include file="hosting.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>