<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainElements" %>
<%@ page import="HardCore.Element" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainElements maintainElements = new UCmaintainElements(mytext);
	Element element = maintainElements.getCreate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="create.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>