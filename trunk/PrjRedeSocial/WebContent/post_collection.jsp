<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>
<%

//faz o set na sess�o com uma variavel de controle para informar que a solicita��o vem da jsp "post_collection"
System.out.println("CONTROL - " + "post_collection");
//mysession.set("control", "post_collection");

cms.CMSLog(myrequest.getParameter("id"), "post", myrequest.getParameter("database"));

if (! myrequest.getParameter("database").equals("")) {
	//UCmaintainData maintainData = new UCmaintainData(mytext);
	UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
	Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
} else {
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
}


//depois tentar remover da sess�o o controle mysession.set("control", "post_collection");


%>



<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
