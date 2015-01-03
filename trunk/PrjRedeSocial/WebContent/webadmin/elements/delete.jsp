<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainElements" %>
<%@ page import="HardCore.Element" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainElements maintainElements = new UCmaintainElements(mytext);
	Element element = maintainElements.getDelete(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>