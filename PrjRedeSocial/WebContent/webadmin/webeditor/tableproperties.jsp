<%@ page pageEncoding="UTF-8" %>
<%@ include file="../config.jsp" %>
<%@ page import="HardCore.Content" %>
<%
	Content content = new Content(mytext);
%>
<%@ include file="tableproperties.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>