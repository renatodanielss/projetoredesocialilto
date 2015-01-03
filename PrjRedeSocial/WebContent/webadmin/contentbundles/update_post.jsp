<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContentbundles" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainContentbundles maintainContentbundles = new UCmaintainContentbundles(mytext);
	maintainContentbundles.doUpdate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="update_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>