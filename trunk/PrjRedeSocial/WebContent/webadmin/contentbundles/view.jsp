<%@ include file="../config.jsp" %>
<%@ page import="HardCore.Element" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.UCmaintainContentbundles" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	UCmaintainContentbundles maintainContentbundles = new UCmaintainContentbundles(mytext);
	String contentbundle = maintainContentbundles.getView(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="view.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>