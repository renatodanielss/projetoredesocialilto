<%@ page pageEncoding="UTF-8" %>
<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainHyperlinks" %>
<%@ page import="HardCore.Contenttype" %>
<%@ page import="HardCore.Contentgroup" %>
<%@ page import="HardCore.Element" %>
<%@ page import="HardCore.Imagetype" %>
<%@ page import="HardCore.Imagegroup" %>
<%@ page import="HardCore.Filetype" %>
<%@ page import="HardCore.Filegroup" %>
<%@ page import="HardCore.Linktype" %>
<%@ page import="HardCore.Linkgroup" %>
<%@ page import="HardCore.Producttype" %>
<%@ page import="HardCore.Productgroup" %>
<%@ page import="HardCore.Usertype" %>
<%@ page import="HardCore.Usergroup" %>
<%
	UCmaintainHyperlinks maintainHyperlinks = new UCmaintainHyperlinks(mytext);
	maintainHyperlinks.getAccess(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="selectorlistfilter_wcm.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>