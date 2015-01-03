<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContentpackages" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainContentpackages maintainContentpackages = new UCmaintainContentpackages(mytext);
	maintainContentpackages.doUpdate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="update_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>