<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>

<%

//faz o set na sessão com uma variavel de controle para informar que a solicitação vem da jsp "post_add_item"
System.out.println("CONTROL - " + "post_add_item");
mysession.set("control", "post_add_item");

cms.CMSLog(myrequest.getParameter("id"), "post", myrequest.getParameter("database"));

if (! myrequest.getParameter("database").equals("")) {
	UCmaintainData maintainData = new UCmaintainData(mytext);
	Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
} else {
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
}


//depois tentar remover da sessão o controle mysession.set("control", "post_add_item");


%>



<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
