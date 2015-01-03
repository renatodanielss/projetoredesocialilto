<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainElements" %>
<%@ page import="HardCore.Element" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainElements maintainElements = new UCmaintainElements(mytext);
	Element element = maintainElements.getUpdate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="update.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>