<%@ page pageEncoding="UTF-8" %>
<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainMedia" %>
<%
	String error = "";
	UCmaintainMedia maintainMedia = new UCmaintainMedia(mytext);
	maintainMedia.doCategory(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="mediacategory_wcm.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>