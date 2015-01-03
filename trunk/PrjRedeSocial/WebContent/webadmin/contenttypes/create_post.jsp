<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContenttypes" %>
<%@ page import="HardCore.Contenttype" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainContenttypes maintainContenttypes = new UCmaintainContenttypes(mytext);
	Contenttype contenttype = maintainContenttypes.doCreate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="create_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>