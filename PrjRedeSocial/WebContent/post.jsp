<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %><%

cms.CMSLog(myrequest.getParameter("id"), "post", myrequest.getParameter("database"));

if (! myrequest.getParameter("database").equals("")) {
	UCmaintainData maintainData = new UCmaintainData(mytext);
	Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
} else {
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
}

%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>
