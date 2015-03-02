<%@page import="com.iliketo.dao.IliketoDAO"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="com.iliketo.util.*" %>

<%


//verificar se é a minha coleção que está na lista de pesquisa
String IDCollector = myrequest.getParameter("idCollector"); //recupera o id da coleção do colecionador da pesquisa
String IDMember = myrequest.getParameter("idMember"); // recupera do parametro o id do usuario membro colecionador pesquisa
String myIdUser = mysession.get("userid"); //meu id de usuario na sessão

//Verifica todos os atributos e valores pendurados na sessão
//String nome, valor;
//for(Enumeration e = session.getAttributeNames(); e.hasMoreElements();){
   //nome = (String)e.nextElement();
   //valor = session.getAttribute(nome).toString();
  //System.out.println("Atributo: " + nome + " - Valor: " + valor);
//}

if(myIdUser.equals(IDMember)){
	//se for a minha coleção selecionada na pesquisa, redireciona para my profile collection
	%>
	<jsp:forward page="/redirect_profile_collection.jsp">
		<jsp:param value="<%=IDCollector%>" name="myIdCollection"/>
	</jsp:forward>
	<%
	
}else{
	
	System.out.println("Log - " + "Mostrar Tela Profile Colecionador Search");
	
	//Seta o id da coleção
	mysession.set(myconfig.get(db, "getset") + Str.S_ID_COLLECTOR, IDCollector);//seta na session o id da coleção do colecionador
	System.out.println("Set session s_id_collector=" + IDCollector);
	
	mysession.set(myconfig.get(db, "getset") + Str.S_ID_MEMBER_COLLECTOR, IDMember);//seta na session o id de usuario do colecionador
	System.out.println("Set session s_id_member_collector=" + IDMember);
	
	myresponse.sendRedirect("/page.jsp?id=529");	//redireciona para o id que contem a pagina de Profile Collector
}

%>


<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
