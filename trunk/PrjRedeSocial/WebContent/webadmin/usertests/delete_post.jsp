<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainUsertests" %>
<%@ page import="HardCore.Usertest" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainUsertests maintainUsertests = new UCmaintainUsertests(mytext);
	Usertest usertest = maintainUsertests.doDelete(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>