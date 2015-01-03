<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.doCreate(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
	String error = maintainContent.getError();
%>
<%@ include file="create_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>