<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainProducttypes" %>
<%@ page import="HardCore.Producttype" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainProducttypes maintainProducttypes = new UCmaintainProducttypes(mytext);
	Producttype producttype = maintainProducttypes.getCreate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="create.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>