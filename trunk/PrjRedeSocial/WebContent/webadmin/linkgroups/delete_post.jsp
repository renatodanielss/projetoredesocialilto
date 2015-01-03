<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainLinkgroups" %>
<%@ page import="HardCore.Linkgroup" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainLinkgroups maintainLinkgroups = new UCmaintainLinkgroups(mytext);
	Linkgroup linkgroup = maintainLinkgroups.doDelete(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>