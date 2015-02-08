<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %><%



cms.CMSLog(myrequest.getParameter("id"), "post", myrequest.getParameter("database"));

if (! myrequest.getParameter("database").equals("")) {
	UCmaintainData maintainData = new UCmaintainData(mytext);
	Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
} else {
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
}

System.out.println("my session antes = " + mysession.getSession().getAttribute("collection"));
mysession.getSession().setAttribute("collection", request.getParameter("name_collection"));
System.out.println("my session now = " + mysession.getSession().getAttribute("collection"));

%>

<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>
