<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainOrderitems" %>
<%@ page import="HardCore.Orderitem" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%@ page import="HardCore.Common" %>
<%
	UCmaintainOrderitems maintainOrderitems = new UCmaintainOrderitems(mytext);
	Orderitem orderitem = maintainOrderitems.getView(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="view.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>