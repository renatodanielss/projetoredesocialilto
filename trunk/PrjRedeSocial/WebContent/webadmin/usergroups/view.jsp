<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainUsergroups" %>
<%@ page import="HardCore.Usergroup" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Page" %>
<%
	UCmaintainUsergroups maintainUsergroups = new UCmaintainUsergroups(mytext);
	Usergroup usergroup = maintainUsergroups.getView(mysession, myrequest, myresponse, myconfig, db);
	HashMap subgroups = usergroup.subgroups(db);
	HashMap supergroups = usergroup.supergroups(db);
	Page mypage = new Page(mytext);
%>
<%@ include file="view.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>