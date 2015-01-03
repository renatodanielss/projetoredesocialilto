<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%@ page import="java.net.URLEncoder" %>
<%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page masterpage = maintainContent.getView(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	Content schedulepage = maintainContent.getSchedule(mysession, myrequest, myresponse, myconfig, db);
	String error = maintainContent.getError();
%>
<%@ include file="schedule.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>