<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContentpackages" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainContentpackages maintainContentpackages = new UCmaintainContentpackages(mytext);
	maintainContentpackages.doDelete(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>