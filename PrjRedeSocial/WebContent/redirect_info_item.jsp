<%@page import="com.iliketo.dao.IliketoDAO"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="com.iliketo.util.*" %>

<%


//verificar se é a minha coleção que está na lista de pesquisa
String idItem = myrequest.getParameter("idItem"); //recupera o id do item
String IDMember = myrequest.getParameter("idMember"); // recupera do parametro o id do usuario membro colecionador
String IDCollection = myrequest.getParameter("idCollection"); //recupera o id da coleção deste item
String myIdUser = mysession.get("userid"); //meu id de usuario na sessão
if(IDCollection.equals("")){
	IDCollection = IliketoDAO.getValueOfDatabase(db, "fk_collection_id", "dbcollectionitem", "id_item", idItem);
}

if(myIdUser.equals(IDMember)){
	
	//se for o item da minha coleção, redireciona para a page "My Item"	
	System.out.println("Log - " + "Mostrar Tela My Item");	
			
	mysession.set(Str.S_ID_ITEM, idItem);//seta na session o id do item do colecionador
	System.out.println("Set session s_id_item = " + idItem);
	mysession.set(Str.S_ID_COLLECTION, IDCollection);//seta na session o id da minha coleção
	System.out.println("Set session s_id_collection=" + IDCollection);
	myresponse.sendRedirect("/page.jsp?id=594");
		
}else{
	
	//se for item de outro colecionador, redireciona para a page "Item of Collector"
	System.out.println("Log - " + "Mostrar Tela Item of Collector");
	
	mysession.set(Str.S_ID_ITEM, idItem);//seta na session o id do item do colecionador
	System.out.println("Set session s_id_item = " + idItem);
	mysession.set(Str.S_ID_MEMBER_COLLECTOR, IDMember);//seta na session o id_member do colecionador visitado
	System.out.println("Set session s_id_member_collector=" + IDMember);
	mysession.set(Str.S_ID_COLLECTOR, IDCollection);//seta na session o id da coleção do colecionador
	System.out.println("Set session s_id_collector=" + IDCollection);
	myresponse.sendRedirect("/page.jsp?id=595");
	
}

%>

<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
