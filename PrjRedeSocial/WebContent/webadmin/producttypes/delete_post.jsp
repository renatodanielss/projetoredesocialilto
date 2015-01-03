<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainProducttypes" %>
<%@ page import="HardCore.Producttype" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainProducttypes maintainProducttypes = new UCmaintainProducttypes(mytext);
	Producttype producttype = maintainProducttypes.doDelete(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>