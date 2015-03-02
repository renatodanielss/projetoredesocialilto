<%@page import="com.iliketo.dao.IliketoDAO"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="com.iliketo.util.*" %>

<%


//verificar se é a minha coleção que está na lista de pesquisa
String IDCollector = (String) myrequest.getParameter("idCollector"); //recupera o id da coleção do colecionador para visitar
String IDMember = (String) myrequest.getParameter("idMember"); // recupera do parametro o id do usuario membro da coleção pesquisada
	
String myIdUser = IliketoDAO.readIdCreatedTable(db, "users", "username", mysession.get("username")); //meu id de usuario na sessão
String idCollectorSearch = myrequest.getParameter("idMember");	//id do membro recuperado do parametro pelo request

if(myIdUser.equals(idCollectorSearch)){
	//se for a minha coleção selecionada na pesquisa, redireciona para my profile collection
	%>
	<jsp:forward page="/redirect_profile_collection.jsp">
		<jsp:param value="<%=IDCollector%>" name="myIdCollection"/>
	</jsp:forward>
	<%
	
}else{
	
	System.out.println("Log - " + "Mostrar tela Profile Colecionador");
	
	//Seta o id da coleção
	mysession.set(myconfig.get(db, "getset") + Str.S_ID_COLLECTOR, IDCollector);//seta na session o id da coleção do colecionador
	System.out.println("Set session s_id_collector=" + IDCollector);
	
	mysession.set(myconfig.get(db, "getset") + Str.S_ID_MEMBER_COLLECTOR, IDMember);//seta na session o id da coleção do colecionador
	System.out.println("Set session s_id_member_collector=" + IDMember);
	
	myresponse.sendRedirect("/page.jsp?id=529");	//redireciona para o id que contem a pagina de Profile Collector
}

%>


<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
