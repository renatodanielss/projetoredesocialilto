<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainUsertests" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.Usertest" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainUsertests maintainUsertests = new UCmaintainUsertests(mytext);
	Usertest usertest = maintainUsertests.getDelete(mysession, myrequest, myresponse, myconfig, db);
	Page mypage = new Page();
%>
<%@ include file="delete.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>