<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainDatabases" %>
<%@ page import="HardCore.Databases" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainDatabases maintainDatabases = new UCmaintainDatabases(mytext);
	Databases databases = maintainDatabases.doUpdate(out, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, database);
%>
<%@ include file="update_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>