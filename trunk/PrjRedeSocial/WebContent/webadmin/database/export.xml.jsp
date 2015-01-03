<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCconfigureSystem" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCconfigureSystem configureSystem = new UCconfigureSystem(mytext);
	configureSystem.doExport(out, servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>