<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>
<%@ page import="com.iliketo.util.*" %>

<%

System.out.println("Log - " + "execute action post_collection");


cms.CMSLog(myrequest.getParameter("id"), "post", myrequest.getParameter("database"));

if (! myrequest.getParameter("database").equals("")) {
	//UCmaintainData maintainData = new UCmaintainData(mytext);
	UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
	Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
	
	String ID = data.getId(); //reupera o ID gerado	
	System.out.println("Log - ID gerado da coleção = " + ID);
	//Seta o nome da coleção recuperado pelo parametro
	mysession.set(myconfig.get(db, "getset") + Str.S_ID_COLLECTION, ID);//seta na session
	System.out.println("Set session s_id_collection=" + ID);
	
	String post = "/post.jsp?database=dbcollection&id=" + ID;
	%>
	<!-- Faz update na database dbcollection para adicionar o ID gerado pelo sistema na coluna id_collection para obter um id de referencia para coleção -->
	<jsp:forward page="<%=post%>">
		<jsp:param value="<%=ID%>" name="id_collection"/>
		<jsp:param value="/page.jsp?id=90" name="redirect"/>
	</jsp:forward>
	<%

	
} else {
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
}



%>



<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
