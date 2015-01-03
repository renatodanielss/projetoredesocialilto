<%@ page pageEncoding="UTF-8" %>
<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainMedia" %>
<%@ page import="java.net.URLEncoder" %>
<%
	UCmaintainMedia maintainMedia = new UCmaintainMedia(mytext);
	maintainMedia.getAccess(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="mediamanager_wcm.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>