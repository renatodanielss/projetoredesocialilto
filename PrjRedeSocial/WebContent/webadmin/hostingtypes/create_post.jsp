<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainHostingtypes" %>
<%@ page import="HardCore.Hostingtype" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainHostingtypes maintainHostingtypes = new UCmaintainHostingtypes(mytext);
	Hostingtype hostingtype = maintainHostingtypes.doCreate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="create_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>