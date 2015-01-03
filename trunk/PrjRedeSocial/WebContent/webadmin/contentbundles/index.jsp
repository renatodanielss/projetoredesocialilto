<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContentbundles" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainContentbundles maintainContentbundles = new UCmaintainContentbundles(mytext);
	ArrayList contentbundles = maintainContentbundles.getIndex(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>