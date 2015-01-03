<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainData" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Data" %>
<%@ page import="HardCore.Databases" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainData maintainData = new UCmaintainData(mytext);
	Data data = maintainData.getView(mysession, myrequest, myresponse, myconfig, db);
	Content content = new Content(mytext);
	Page mypage = new Page(mytext);
%>
<%@ include file="view.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>