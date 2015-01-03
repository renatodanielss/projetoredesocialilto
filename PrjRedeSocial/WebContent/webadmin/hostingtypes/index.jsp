<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainHostingtypes" %>
<%@ page import="HardCore.Hostingtype" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainHostingtypes maintainHostingtypes = new UCmaintainHostingtypes(mytext);
	Hostingtype hostingtype = maintainHostingtypes.getIndex(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>