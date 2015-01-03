<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainTax" %>
<%@ page import="HardCore.Tax" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainTax maintainTax = new UCmaintainTax(mytext);
	Tax tax = maintainTax.getView(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="view.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>