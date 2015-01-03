<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainOrderitems" %>
<%@ page import="HardCore.Orderitem" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainOrderitems maintainOrderitems = new UCmaintainOrderitems(mytext);
	Orderitem orderitem = maintainOrderitems.doCreate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="create_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>