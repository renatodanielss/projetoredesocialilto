<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainData" %>
<%@ page import="HardCore.Data" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%
	UCmaintainData maintainData = new UCmaintainData(mytext);
	Data data = maintainData.getDeleteMultiple(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete_multiple.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>