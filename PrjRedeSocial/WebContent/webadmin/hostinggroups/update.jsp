<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainHostinggroups" %>
<%@ page import="HardCore.Hostinggroup" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainHostinggroups maintainHostinggroups = new UCmaintainHostinggroups(mytext);
	Hostinggroup hostinggroup = maintainHostinggroups.getUpdate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="update.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>