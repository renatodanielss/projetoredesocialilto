<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainData" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Data" %>
<%@ page import="HardCore.Databases" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainData maintainData = new UCmaintainData(mytext);
	Data data = maintainData.getDelete(mysession, myrequest, myresponse, myconfig, db);
	Content content = new Content(mytext);
	Page mypage = new Page(mytext);
%>
<%@ include file="delete.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>