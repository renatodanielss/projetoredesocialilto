<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainDatabases" %>
<%@ page import="HardCore.Common" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.Databases" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainDatabases maintainDatabases = new UCmaintainDatabases(mytext);
	Databases databases = maintainDatabases.getUpdate(mysession, myrequest, myresponse, myconfig, db);
	Page mypage = new Page(mytext);
%>
<%@ include file="update.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>