<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainWorkflows" %>
<%@ page import="HardCore.Workflow" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainWorkflows maintainWorkflows = new UCmaintainWorkflows(mytext);
	Workflow workflow = maintainWorkflows.doCreate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="create_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>