<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainCurrencies" %><%@ page import="HardCore.MenuContent" %><%
	String output = "";
	UCmaintainCurrencies maintainCurrencies = new UCmaintainCurrencies(mytext);
	if (! myrequest.getMethod().equals("POST")) {
		maintainCurrencies.getImport(mysession, myrequest, myresponse, myconfig, db);
	} else {
//		maintainCurrencies.doImport(out, servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
		output = maintainCurrencies.doImport(null, servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
	}
%>
<%@ include file="import.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>