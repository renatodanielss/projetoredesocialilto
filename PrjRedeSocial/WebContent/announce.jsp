<%@page import="com.iliketo.dao.IliketoDAO"%>
<%@page import="com.iliketo.util.Str"%>
<%@page import="com.iliketo.bean.AnnounceJB"%>
<%@page import="com.iliketo.control.AnnounceController"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>


<%

String idItem = myrequest.getParameter("idItem");					//id item para anunciar
String idCollection = myrequest.getParameter("idCollection");		//id colecao para anunciar

String checkout = myrequest.getParameter("checkout");				//autenticacao de passo a passo, campo hidden form
AnnounceController controller = new AnnounceController();

if(!idItem.equals("") && checkout.equals("")){
	System.out.println("Log - " + "Mostrar tela anunciar item - passo 1");
	RequestDispatcher rq = myrequest.getRequest().getRequestDispatcher("/page.jsp?id=658");			
	rq.forward(myrequest.getRequest(), myresponse.getResponse());	//mostrar tela anunciar item passo1
	
}else if(!idCollection.equals("") && checkout.equals("")){
	System.out.println("Log - " + "Mostrar tela anunciar colecao - passo 1");
	RequestDispatcher rq = myrequest.getRequest().getRequestDispatcher("/page.jsp?id=664");			
	rq.forward(myrequest.getRequest(), myresponse.getResponse());	//mostrar tela anunciar colecao passo1
	
}else if(!checkout.equals("")){
	
	if(checkout.equals("pass1")){
		System.out.println("Log - " + "executa action announce.jsp passo 1");
		if(!idItem.equals("")){
			controller.announceItemCollectionPass1(db, myrequest, mysession, myresponse, "item", idItem);		 //executa passo1 anunciar item
		}else{
			controller.announceItemCollectionPass1(db, myrequest, mysession, myresponse, "collection", idCollection);//executa passo1 anunciar colecao
		}
		
	}else if(session.getAttribute("announceJB") != null){ //valida announceJB na sessao
		if(checkout.equals("pass2")){
			System.out.println("Log - " + "executa action announce.jsp passo 2");
			controller.announcePaymentPass2(db, myrequest, mysession, myresponse);	//executa passo2
		}else if(checkout.equals("pass3")){
			System.out.println("Log - " + "executa action announce.jsp passo 3");
			controller.announceConfirmPass3(db, myrequest, mysession, myresponse); 	//executa passo3
		}
	
	}else{
		System.out.println("Log - " + "Error acesso invalido ao servico anuncio.jsp");
		myresponse.sendRedirect("/home.jsp");	//redireciona para home ou pagina no found padrao, conteudo nao disponivel
	}
	
}else{
	System.out.println("Log - " + "Error acesso invalido ao servico anuncio.jsp, sem dados do item ou colecao");
	myresponse.sendRedirect("/page.jsp?id=48");	//redireciona para minhas coleções ou pagina no found padrao, conteudo nao disponivel
}


%>

<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
