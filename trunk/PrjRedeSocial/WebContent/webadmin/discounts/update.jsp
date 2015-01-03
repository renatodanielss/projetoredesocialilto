<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainDiscounts" %>
<%@ page import="HardCore.Discount" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainDiscounts maintainDiscounts = new UCmaintainDiscounts(mytext);
	Discount discount = maintainDiscounts.getUpdate(mysession, myrequest, myresponse, myconfig, db);
	Page mypage = new Page(mytext);
%>
<%@ include file="update.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>