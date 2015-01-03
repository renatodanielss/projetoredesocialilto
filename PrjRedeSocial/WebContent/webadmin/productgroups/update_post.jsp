<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainProductgroups" %>
<%@ page import="HardCore.Productgroup" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainProductgroups maintainProductgroups = new UCmaintainProductgroups(mytext);
	Productgroup productgroup = maintainProductgroups.doUpdate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="update_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>