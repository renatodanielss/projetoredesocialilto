<%@ include file="../config.jsp" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.RequireUser" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="java.util.Iterator" %>
<%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	String output = maintainContent.doStructure(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	String error = maintainContent.getError();
%>
<%@ include file="structure_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>