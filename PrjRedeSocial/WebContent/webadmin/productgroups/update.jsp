<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainProductgroups" %>
<%@ page import="HardCore.Productgroup" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainProductgroups maintainProductgroups = new UCmaintainProductgroups(mytext);
	Productgroup productgroup = maintainProductgroups.getUpdate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="update.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>