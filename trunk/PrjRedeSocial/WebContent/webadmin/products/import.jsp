<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainProducts" %><%@ page import="HardCore.MenuContent" %><%
	String output = "";
	UCmaintainProducts maintainProducts = new UCmaintainProducts(mytext);
	if (! myrequest.getMethod().equals("POST")) {
		maintainProducts.getImport(mysession, myrequest, myresponse, myconfig, db);
	} else {
//		maintainProducts.doImport(out, servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
		output = maintainProducts.doImport(null, servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
	}
%>
<%@ include file="import.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>