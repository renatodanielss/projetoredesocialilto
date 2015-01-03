<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainWorkflows" %>
<%@ page import="HardCore.Workflow" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainWorkflows maintainWorkflows = new UCmaintainWorkflows(mytext);
	Workflow workflow = maintainWorkflows.getDelete(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>