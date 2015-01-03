<%@ page pageEncoding="UTF-8" %>
<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainHyperlinks" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Contentgroup" %>
<%@ page import="HardCore.Contenttype" %>
<%@ page import="HardCore.Image" %>
<%@ page import="HardCore.Imagegroup" %>
<%@ page import="HardCore.Imagetype" %>
<%@ page import="HardCore.File" %>
<%@ page import="HardCore.Filegroup" %>
<%@ page import="HardCore.Filetype" %>
<%@ page import="HardCore.Linkgroup" %>
<%@ page import="HardCore.Linktype" %>
<%@ page import="HardCore.Productgroup" %>
<%@ page import="HardCore.Producttype" %>
<%@ page import="java.net.URLEncoder" %>
<%
	UCmaintainHyperlinks maintainHyperlinks = new UCmaintainHyperlinks(mytext);
	maintainHyperlinks.getAccess(mysession, myrequest, myresponse, myconfig, db);
	String select_options = maintainHyperlinks.select_options(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="hyperlinkuploader_wcm.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>