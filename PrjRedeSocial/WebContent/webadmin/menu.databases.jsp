<%@ include file="config.jsp" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Website" %>
<%@ page import="HardCore.Workflow" %>
<%@ page import="HardCore.UCaccessAdministration" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.UCmaintainWebsites" %>
<%
	if (! myrequest.getParameter("menu").equals("")) {
		mysession.set("menu", myrequest.getParameter("menu"));
	}
%>
<%@ include file="menu.jsp.html" %>
<%@ include file="menu.databases.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>