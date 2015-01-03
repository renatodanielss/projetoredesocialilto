<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainProductgroups" %>
<%@ page import="HardCore.Productgroup" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainProductgroups maintainProductgroups = new UCmaintainProductgroups(mytext);
	Productgroup productgroup = maintainProductgroups.getIndex(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>