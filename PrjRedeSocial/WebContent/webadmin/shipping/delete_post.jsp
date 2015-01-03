<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainShipping" %>
<%@ page import="HardCore.Shipping" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainShipping maintainShipping = new UCmaintainShipping(mytext);
	Shipping shipping = maintainShipping.doDelete(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>