<%@ page pageEncoding="UTF-8" %>
<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainHyperlinks" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Contentgroup" %>
<%@ page import="HardCore.Contenttype" %>
<%@ page import="HardCore.Imagegroup" %>
<%@ page import="HardCore.Imagetype" %>
<%@ page import="HardCore.Filegroup" %>
<%@ page import="HardCore.Filetype" %>
<%@ page import="HardCore.Linkgroup" %>
<%@ page import="HardCore.Linktype" %>
<%@ page import="HardCore.Productgroup" %>
<%@ page import="HardCore.Producttype" %>
<%@ page import="HardCore.Usergroup" %>
<%@ page import="HardCore.Usertype" %>
<%@ page import="HardCore.Version" %>
<%@ page import="HardCore.Website" %>
<%@ page import="HardCore.Common" %>
<%@ page import="java.net.URLEncoder" %>
<%
	UCmaintainHyperlinks maintainHyperlinks = new UCmaintainHyperlinks(mytext);
	maintainHyperlinks.getAccess(mysession, myrequest, myresponse, myconfig, db);
	Content products = maintainHyperlinks.getProductsRecords(myrequest, myconfig, db);
	Content pages = maintainHyperlinks.getPagesRecords(myrequest, myconfig, db);
	Content images = maintainHyperlinks.getImagesRecords(myrequest, myconfig, db);
	Content files = maintainHyperlinks.getFilesRecords(myrequest, myconfig, db);
	Content links = maintainHyperlinks.getLinksRecords(myrequest, myconfig, db);
	Version versions = maintainHyperlinks.getVersionsRecords(myrequest, myconfig, db);
	Website websites = maintainHyperlinks.getWebsitesRecords(myrequest, myconfig, db);
	Usertype usertypes = maintainHyperlinks.getUsertypesRecords(myrequest, myconfig, db);
	Usergroup usergroups = maintainHyperlinks.getUsergroupsRecords(myrequest, myconfig, db);
%>
<%@ include file="hyperlinklist_wcm.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>