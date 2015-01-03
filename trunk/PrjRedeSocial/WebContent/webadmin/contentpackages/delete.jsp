<%@ include file="../config.jsp" %>
<%@ page import="HardCore.Element" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.UCmaintainContentpackages" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	UCmaintainContentpackages maintainContentpackages = new UCmaintainContentpackages(mytext);
	String contentpackage = maintainContentpackages.getDelete(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>