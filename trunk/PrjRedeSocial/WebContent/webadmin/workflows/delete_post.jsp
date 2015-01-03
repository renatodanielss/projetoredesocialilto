<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainWorkflows" %>
<%@ page import="HardCore.Workflow" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainWorkflows maintainWorkflows = new UCmaintainWorkflows(mytext);
	Workflow workflow = maintainWorkflows.doDelete(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>