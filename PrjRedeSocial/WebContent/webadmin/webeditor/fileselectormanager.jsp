<%@ page pageEncoding="UTF-8" %>
<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainHyperlinks" %>
<%@ page import="java.net.URLEncoder" %>
<%
	UCmaintainHyperlinks maintainHyperlinks = new UCmaintainHyperlinks(mytext);
	maintainHyperlinks.getAccess(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="fileselectormanager.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>