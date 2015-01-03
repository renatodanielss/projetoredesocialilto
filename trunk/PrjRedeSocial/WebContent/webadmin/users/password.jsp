<%@ include file="../config.jsp" %>
<%@ page import="HardCore.RequireUser" %>
<%@ page import="HardCore.UCmaintainUsers" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.User" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainUsers maintainUsers = new UCmaintainUsers(mytext);
	User user = maintainUsers.getPassword(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	String error = maintainUsers.getError();
%>
<%@ include file="password.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>