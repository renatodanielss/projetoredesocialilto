<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainProducttypes" %>
<%@ page import="HardCore.Producttype" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainProducttypes maintainProducttypes = new UCmaintainProducttypes(mytext);
	Producttype producttype = maintainProducttypes.getUpdate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="update.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>