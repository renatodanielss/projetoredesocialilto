<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainTax" %>
<%@ page import="HardCore.Tax" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainTax maintainTax = new UCmaintainTax(mytext);
	Tax taxEdit = maintainTax.getIndex(mysession, myrequest, myresponse, myconfig, db);
	Tax taxCreate = maintainTax.getCreateRecords();
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>