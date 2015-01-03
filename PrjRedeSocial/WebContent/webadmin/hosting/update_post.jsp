<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainHosting" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainHosting maintainHosting = new UCmaintainHosting(mytext);
	String error = maintainHosting.doUpdate(servletcontext, DOCUMENT_ROOT, inifile, mysession, myrequest, myresponse, myconfig, db, original_database);
%>
<%@ include file="update_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>