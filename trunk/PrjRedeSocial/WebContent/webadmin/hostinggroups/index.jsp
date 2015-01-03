<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainHostinggroups" %>
<%@ page import="HardCore.Hostinggroup" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainHostinggroups maintainHostinggroups = new UCmaintainHostinggroups(mytext);
	Hostinggroup hostinggroup = maintainHostinggroups.getIndex(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>