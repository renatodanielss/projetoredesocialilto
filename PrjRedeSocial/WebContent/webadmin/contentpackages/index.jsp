<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContentpackages" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainContentpackages maintainContentpackages = new UCmaintainContentpackages(mytext);
	ArrayList contentpackages = maintainContentpackages.getIndex(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>