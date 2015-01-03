<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainDiscounts" %>
<%@ page import="HardCore.Discount" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainDiscounts maintainDiscounts = new UCmaintainDiscounts(mytext);
	Discount discount = maintainDiscounts.doDelete(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>