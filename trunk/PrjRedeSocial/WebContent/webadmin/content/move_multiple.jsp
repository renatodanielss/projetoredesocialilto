<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	maintainContent.doMoveMultiple(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	String error = maintainContent.getError();
%>
<%@ include file="move_multiple.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>