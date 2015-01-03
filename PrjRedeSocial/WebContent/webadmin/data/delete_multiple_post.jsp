<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainData" %>
<%@ page import="HardCore.Data" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%
	UCmaintainData maintainData = new UCmaintainData(mytext);
	maintainData.doDeleteMultiple(mysession, myrequest, myresponse, myconfig, db);
	String error = "";
%>
<%@ include file="delete_multiple_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>