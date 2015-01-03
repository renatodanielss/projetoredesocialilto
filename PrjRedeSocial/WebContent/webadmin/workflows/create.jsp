<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainWorkflows" %>
<%@ page import="HardCore.Workflow" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.Contentgroup" %>
<%@ page import="HardCore.Contenttype" %>
<%@ page import="HardCore.Filegroup" %>
<%@ page import="HardCore.Filetype" %>
<%@ page import="HardCore.Imagegroup" %>
<%@ page import="HardCore.Imagetype" %>
<%@ page import="HardCore.Linkgroup" %>
<%@ page import="HardCore.Linktype" %>
<%@ page import="HardCore.Productgroup" %>
<%@ page import="HardCore.Producttype" %>
<%@ page import="HardCore.Element" %>
<%@ page import="HardCore.Version" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainWorkflows maintainWorkflows = new UCmaintainWorkflows(mytext);
	Workflow workflow = maintainWorkflows.getCreate(mysession, myrequest, myresponse, myconfig, db);
	Page mypage = new Page(mytext);
%>
<%@ include file="create.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>