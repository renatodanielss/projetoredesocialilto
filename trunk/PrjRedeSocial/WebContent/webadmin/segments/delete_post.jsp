<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainSegments" %>
<%@ page import="HardCore.Segment" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainSegments maintainSegments = new UCmaintainSegments(mytext);
	Segment segment = maintainSegments.doDelete(mysession, myrequest, myresponse, myconfig, db);
	String error = maintainSegments.getError();
%>
<%@ include file="delete_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>