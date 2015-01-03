<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Page" %>
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
<%@ page import="HardCore.Workflow" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	String error = maintainContent.doSearchReplace(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
	Workflow workflow = new Workflow(mytext);
%>
<%@ include file="searchreplace_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>