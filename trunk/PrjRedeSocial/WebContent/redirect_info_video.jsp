<%@page import="com.iliketo.dao.IliketoDAO"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="com.iliketo.util.*" %>

<%


//verificar se � a minha cole��o que est� na lista de pesquisa
String idVideo = myrequest.getParameter("idVideo"); //recupera o id do item
String IDMember = myrequest.getParameter("idMember"); // recupera do parametro o id do usuario membro colecionador
String IDCollection = myrequest.getParameter("idCollection"); //recupera o id da cole��o deste item
String myIdUser = mysession.get("userid"); //meu id de usuario na sess�o
if(IDCollection.equals("")){
	IDCollection = IliketoDAO.getValueOfDatabase(db, "fk_collection_id", "dbcollectionvideo", "id_video", idVideo);
}

if(myIdUser.equals(IDMember)){
	
	//se for o item da minha cole��o, redireciona para a page "My Item"	
	System.out.println("Log - " + "Mostrar Tela My video");	
			
	mysession.set(Str.S_ID_VIDEO, idVideo);//seta na session o id do item do colecionador
	System.out.println("Set session s_id_video = " + idVideo);
	mysession.set(Str.S_ID_COLLECTION, IDCollection);//seta na session o id da minha cole��o
	System.out.println("Set session s_id_collection=" + IDCollection);
	myresponse.sendRedirect("/page.jsp?id=655");
		
}else{
	
	//se for item de outro colecionador, redireciona para a page "Item of Collector"
	System.out.println("Log - " + "Mostrar Tela Video of Collector");
	
	mysession.set(Str.S_ID_VIDEO, idVideo);//seta na session o id do item do colecionador
	System.out.println("Set session s_id_video = " + idVideo);
	mysession.set(Str.S_ID_MEMBER_COLLECTOR, IDMember);//seta na session o id_member do colecionador visitado
	System.out.println("Set session s_id_member_collector=" + IDMember);
	mysession.set(Str.S_ID_COLLECTOR, IDCollection);//seta na session o id da cole��o do colecionador
	System.out.println("Set session s_id_collector=" + IDCollection);
	myresponse.sendRedirect("/page.jsp?id=657");
	
}

%>

<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
