<%@ page pageEncoding="UTF-8" %>
<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainMedia" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Common" %>
<%@ page import="java.net.URLEncoder" %>
<%
	UCmaintainMedia maintainMedia = new UCmaintainMedia(mytext);
	maintainMedia.getAccess(mysession, myrequest, myresponse, myconfig, db);
	Content images = maintainMedia.getImagesRecords(myrequest, myconfig, db);
%>
<%@ include file="medialist_bizcard.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>