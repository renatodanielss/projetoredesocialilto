<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainImageformats" %>
<%@ page import="HardCore.Imageformat" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainImageformats maintainImageformats = new UCmaintainImageformats(mytext);
	Imageformat imageformat = maintainImageformats.doDelete(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>