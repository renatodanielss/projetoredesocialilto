<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCconfigureSystem" %>
<%@ page import="HardCore.Currency" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.User" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCconfigureSystem configureSystem = new UCconfigureSystem(mytext);
	configureSystem.doConfigure(ini_database, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
	String error = configureSystem.getError();
	Currency mycurrencies = configureSystem.getCurrencies();
	Page mypage = configureSystem.getPage();
	User myuser = configureSystem.getUser();
%>
<%@ include file="presentation.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>