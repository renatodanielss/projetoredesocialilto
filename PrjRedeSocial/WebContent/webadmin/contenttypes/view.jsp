<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContenttypes" %>
<%@ page import="HardCore.Contenttype" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainContenttypes maintainContenttypes = new UCmaintainContenttypes(mytext);
	Contenttype contenttype = maintainContenttypes.getView(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="view.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>