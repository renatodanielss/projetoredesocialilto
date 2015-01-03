<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainData" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.Databases" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainData maintainData = new UCmaintainData(mytext);
	Databases databases = maintainData.getImport(mysession, myrequest, myresponse, myconfig, db);
	Page mypage = new Page(mytext);
%>
<%@ include file="import.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>