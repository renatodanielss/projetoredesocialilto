<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainDiscounts" %>
<%@ page import="HardCore.Discount" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainDiscounts maintainDiscounts = new UCmaintainDiscounts(mytext);
	Discount discountEdit = maintainDiscounts.getIndex(mysession, myrequest, myresponse, myconfig, db);
	Discount discountCreate = maintainDiscounts.getCreateRecords();
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>