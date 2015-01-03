<%@ page pageEncoding="UTF-8" %>
<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainMedia" %>
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
<%@ page import="HardCore.Common" %>
<%@ page import="java.net.URLEncoder" %>
<%
	UCmaintainMedia maintainMedia = new UCmaintainMedia(mytext);
	maintainMedia.getAccess(mysession, myrequest, myresponse, myconfig, db);
	Content images = maintainMedia.getImagesRecords(myrequest, myconfig, db);
	Content files = maintainMedia.getFilesRecords(myrequest, myconfig, db);
	if (myrequest.getParameter("category").equals("")) {
		images.closeRecords(db);
		files.closeRecords(db);
	}
%>
<%@ include file="medialist_wcm.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>