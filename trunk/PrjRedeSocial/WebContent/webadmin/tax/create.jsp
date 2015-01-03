<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainTax" %>
<%@ page import="HardCore.Tax" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainTax maintainTax = new UCmaintainTax(mytext);
	Tax tax = maintainTax.getCreate(mysession, myrequest, myresponse, myconfig, db);
	Page mypage = new Page(mytext);
%>
<%@ include file="create.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>