<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainWebsites" %>
<%@ page import="HardCore.Website" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainWebsites maintainWebsites = new UCmaintainWebsites(mytext);
	Website mywebsite = maintainWebsites.getIndex(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>