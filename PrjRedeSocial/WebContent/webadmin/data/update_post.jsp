<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainData" %>
<%@ page import="HardCore.Data" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainData maintainData = new UCmaintainData(mytext);
	Data data = maintainData.doUpdate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="update_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>