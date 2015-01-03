<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainWebsites" %>
<%@ page import="HardCore.Website" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainWebsites maintainWebsites = new UCmaintainWebsites(mytext);
	Website mywebsite = maintainWebsites.getCreate(mysession, myrequest, myresponse, myconfig, db);
	Page mypage = new Page(mytext);
%>
<%@ include file="create.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>