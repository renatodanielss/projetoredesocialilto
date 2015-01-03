<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainCurrencies" %>
<%@ page import="HardCore.Currency" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainCurrencies maintainCurrencies = new UCmaintainCurrencies(mytext);
	Currency currencies = maintainCurrencies.getDelete(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>