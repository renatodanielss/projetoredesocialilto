<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.Workflow" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%@ page import="HardCore.Common" %>
<%@ page import="java.net.URLEncoder" %>
<%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.getView(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	HashMap dependents = mypage.dependents(myconfig, db);
%>
<%@ include file="dependencies.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>