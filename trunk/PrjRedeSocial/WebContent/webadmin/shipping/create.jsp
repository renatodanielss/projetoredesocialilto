<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainShipping" %>
<%@ page import="HardCore.Shipping" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainShipping maintainShipping = new UCmaintainShipping(mytext);
	Shipping shipping = maintainShipping.getCreate(mysession, myrequest, myresponse, myconfig, db);
	Page mypage = new Page(mytext);
%>
<%@ include file="create.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>