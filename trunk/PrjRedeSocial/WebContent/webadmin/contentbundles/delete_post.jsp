<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContentbundles" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainContentbundles maintainContentbundles = new UCmaintainContentbundles(mytext);
	maintainContentbundles.doDelete(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>