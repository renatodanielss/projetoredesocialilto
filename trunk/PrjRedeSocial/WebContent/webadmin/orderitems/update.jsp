<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainOrderitems" %>
<%@ page import="HardCore.Orderitem" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%@ page import="HardCore.Common" %>
<%@ page import="java.net.URLEncoder" %>
<%
	UCmaintainOrderitems maintainOrderitems = new UCmaintainOrderitems(mytext);
	Orderitem orderitem = maintainOrderitems.getUpdate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="update.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>