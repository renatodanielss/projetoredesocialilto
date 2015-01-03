<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainLinktypes" %>
<%@ page import="HardCore.Linktype" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainLinktypes maintainLinktypes = new UCmaintainLinktypes(mytext);
	Linktype linktype = maintainLinktypes.getIndex(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>