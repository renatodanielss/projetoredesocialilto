<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCconfigureSystem" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCconfigureSystem configureSystem = new UCconfigureSystem(mytext);
	configureSystem.doDelete(out, servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="backup_delete.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>