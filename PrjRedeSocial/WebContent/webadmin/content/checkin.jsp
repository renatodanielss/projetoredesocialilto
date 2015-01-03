<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.doCheckin(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	String error = maintainContent.getError();
%> 
<%@ include file="checkin.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>