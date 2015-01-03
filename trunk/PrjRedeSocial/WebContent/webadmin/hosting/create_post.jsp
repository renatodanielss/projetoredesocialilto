<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainHosting" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainHosting maintainHosting = new UCmaintainHosting(mytext);
	String error = maintainHosting.doCreate(servletcontext, DOCUMENT_ROOT, inifile, mysession, myrequest, myresponse, myconfig, db, original_database);
%>
<%@ include file="create_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>