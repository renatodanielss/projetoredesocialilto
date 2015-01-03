<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainImageformats" %>
<%@ page import="HardCore.Imageformat" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainImageformats maintainImageformats = new UCmaintainImageformats(mytext);
	Imageformat imageformat = maintainImageformats.doCreate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="create_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>