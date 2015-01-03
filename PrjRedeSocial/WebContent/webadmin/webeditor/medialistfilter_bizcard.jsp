<%@ page pageEncoding="UTF-8" %>
<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainMedia" %>
<%@ page import="HardCore.Imagetype" %>
<%@ page import="HardCore.Imagegroup" %>
<%
	UCmaintainMedia maintainMedia = new UCmaintainMedia(mytext);
	maintainMedia.getAccess(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="medialistfilter_bizcard.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>