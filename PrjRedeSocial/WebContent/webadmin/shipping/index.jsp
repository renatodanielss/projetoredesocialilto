<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainShipping" %>
<%@ page import="HardCore.Shipping" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainShipping maintainShipping = new UCmaintainShipping(mytext);
	Shipping shippingEdit = maintainShipping.getIndex(mysession, myrequest, myresponse, myconfig, db);
	Shipping shippingCreate = maintainShipping.getCreateRecords();
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>