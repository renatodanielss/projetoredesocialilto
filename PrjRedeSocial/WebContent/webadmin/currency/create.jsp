<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainCurrencies" %>
<%@ page import="HardCore.Currency" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainCurrencies maintainCurrencies = new UCmaintainCurrencies(mytext);
	Currency currencies = maintainCurrencies.getCreate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="create.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>