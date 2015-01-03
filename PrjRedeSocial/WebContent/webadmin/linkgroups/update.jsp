<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainLinkgroups" %>
<%@ page import="HardCore.Linkgroup" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainLinkgroups maintainLinkgroups = new UCmaintainLinkgroups(mytext);
	Linkgroup linkgroup = maintainLinkgroups.getUpdate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="update.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>