<%@ page pageEncoding="UTF-8" %>
<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainHyperlinks" %>
<%@ page import="HardCore.Contenttype" %>
<%@ page import="HardCore.Contentgroup" %>
<%@ page import="HardCore.Imagetype" %>
<%@ page import="HardCore.Imagegroup" %>
<%@ page import="HardCore.Filetype" %>
<%@ page import="HardCore.Filegroup" %>
<%@ page import="HardCore.Linktype" %>
<%@ page import="HardCore.Linkgroup" %>
<%@ page import="HardCore.Producttype" %>
<%@ page import="HardCore.Productgroup" %>
<%
	UCmaintainHyperlinks maintainHyperlinks = new UCmaintainHyperlinks(mytext);
	maintainHyperlinks.getAccess(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="hyperlinklistfilter_wcm.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>