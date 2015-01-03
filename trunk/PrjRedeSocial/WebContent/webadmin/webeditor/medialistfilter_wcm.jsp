<%@ page pageEncoding="UTF-8" %>
<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainMedia" %>
<%@ page import="HardCore.Imagetype" %>
<%@ page import="HardCore.Imagegroup" %>
<%@ page import="HardCore.Filetype" %>
<%@ page import="HardCore.Filegroup" %>
<%
	UCmaintainMedia maintainMedia = new UCmaintainMedia(mytext);
	maintainMedia.getAccess(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="medialistfilter_wcm.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>