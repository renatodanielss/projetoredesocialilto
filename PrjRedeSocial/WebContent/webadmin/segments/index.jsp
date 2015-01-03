<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainSegments" %>
<%@ page import="HardCore.Segment" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainSegments maintainSegments = new UCmaintainSegments(mytext);
	Segment segment = maintainSegments.getIndex(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>