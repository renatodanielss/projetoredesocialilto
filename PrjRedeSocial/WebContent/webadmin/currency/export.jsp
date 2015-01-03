<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainCurrencies" %><%@ page import="HardCore.Currency" %><%@ page import="HardCore.MenuContent" %><%
	UCmaintainCurrencies maintainCurrencies = new UCmaintainCurrencies(mytext);
	Currency currencies;
	if (! myrequest.getMethod().equals("POST")) {
		currencies = maintainCurrencies.getExport(mysession, myrequest, myresponse, myconfig, db);
		%><%@ include file="export.jsp.html" %><%
	} else {
		currencies = maintainCurrencies.doExport(mysession, myrequest, myresponse, myconfig, db);
		%><%@ include file="export.jsp.csv" %><%
	}
%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>