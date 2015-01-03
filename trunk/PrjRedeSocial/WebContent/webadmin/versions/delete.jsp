<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainVersions" %>
<%@ page import="HardCore.Version" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainVersions maintainVersions = new UCmaintainVersions(mytext);
	Version version = maintainVersions.getDelete(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>