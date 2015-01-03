<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%@ page import="java.net.URLEncoder" %>
<%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page masterpage = maintainContent.getView(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	Content archivepage = maintainContent.getArchive(mysession, myrequest, myresponse, myconfig, db);
	String error = maintainContent.getError();
%>
<%@ include file="archive.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>