<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainUsertests" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.Usertest" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainUsertests maintainUsertests = new UCmaintainUsertests(mytext);
	Usertest usertest = maintainUsertests.getCreate(mysession, myrequest, myresponse, myconfig, db);
	Page mypage = new Page();
%>
<%@ include file="create.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>