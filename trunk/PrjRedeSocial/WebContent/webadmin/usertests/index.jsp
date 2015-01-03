<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainUsertests" %>
<%@ page import="HardCore.Usertest" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainUsertests maintainUsertests = new UCmaintainUsertests(mytext);
	Usertest usertest = maintainUsertests.getIndex(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>