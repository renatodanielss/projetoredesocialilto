<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainLinktypes" %>
<%@ page import="HardCore.Linktype" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainLinktypes maintainLinktypes = new UCmaintainLinktypes(mytext);
	Linktype linktype = maintainLinktypes.doUpdate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="update_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>