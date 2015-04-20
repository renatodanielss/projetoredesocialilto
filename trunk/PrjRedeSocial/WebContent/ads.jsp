<%@page import="com.iliketo.dao.IliketoDAO"%>
<%@page import="com.iliketo.bean.AnnounceJB"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="com.iliketo.util.*" %>

<%

System.out.println("Log - " + "Mostrar tela de Anúncio");

String idAnnounce = myrequest.getParameter("id"); 			//recupera o id do anuncio

//valida id do anuncio
if(!idAnnounce.equals("")){ 
		
	AnnounceJB announceJB = IliketoDAO.readAnnounceJB(db, idAnnounce);
	if(announceJB != null){
		
		mysession.set("s_id_announce", idAnnounce);
		String myUserId = mysession.get("userid");
		String pageMyAdAuctionItem = "page.jsp?id=705";			//pagina meu anuncio leilao item
		String pageMyAdFixedItem = "";							//pagina meu anuncio preco fixo item
		String pageMyAdAuctionCollection = "";					//pagina meu anuncio leilao colecao
		String pageMyAdFixedCollection = "";					//pagina meu anuncio preco fixo colecao
		
		String pageYourAdAuctionItem = "page.jsp?id=706";		//pagina terceiro anuncio leilao item
		String pageYourAdFixedItem = "";						//pagina terceiro anuncio preco fixo item
		String pageYourAdAuctionCollection = "";				//pagina terceiro anuncio leilao colecao
		String pageYourAdFixedCollection = "";					//pagina terceiro anuncio preco fixo colecao
		
		String pageViewAd = "";	//pagina visualizar anuncio
		
		//verifica se o anuncio pertence ao usuario da sessao
		if(announceJB.getIdMember().equals(myUserId)){
			//visao do meu anuncio
			if(announceJB.getTypeAnnounce().equalsIgnoreCase("Auction")){
				//leilao
				if(!announceJB.getIdItem().equals("")){
					pageViewAd = pageMyAdAuctionItem;
				}else{
					pageViewAd = pageMyAdAuctionCollection;
				}
			}else{
				//preco fixo
				if(!announceJB.getIdItem().equals("")){
					pageViewAd = pageMyAdFixedItem;
				}else{
					pageViewAd = pageMyAdFixedCollection;
				}
			}
		}else{
			//visao de terceiros do anuncio
			if(announceJB.getTypeAnnounce().equalsIgnoreCase("Auction")){
				//leilao
				if(!announceJB.getIdItem().equals("")){
					pageViewAd = pageYourAdAuctionItem;
				}else{
					pageViewAd = pageYourAdAuctionCollection;
				}
			}else{
				//preco fixo
				if(!announceJB.getIdItem().equals("")){
					pageViewAd = pageYourAdFixedItem;
				}else{
					pageViewAd = pageYourAdFixedCollection;
				}
			}
		}
		
		//valida pagina visualizacao
		if(!pageViewAd.equals("")){
			//seta no HttpRequest o objeto announceJB para recuperar na Page.java e realizar o replace dos codigos especiais
			request.setAttribute("announceJB", announceJB);	
			RequestDispatcher rq = myrequest.getRequest().getRequestDispatcher(pageViewAd);			
			rq.forward(myrequest.getRequest(), myresponse.getResponse());	//tela passo 2	
			
		}else{
			System.out.println("Log - " + "Não achou id do item ou colecao no anuncio");
			myresponse.sendRedirect("/home.jsp");	//redireciona para home ou pagina not found padrao, pagina nao disponivel
		}
		
	}else{
		System.out.println("Log - " + "Não achou anuncio no banco de dados");
		myresponse.sendRedirect("/home.jsp");	//redireciona para home ou pagina not found padrao, pagina nao disponivel
	}
	
}else{
	System.out.println("Log - " + "Não achou id do anuncio no parametro ou nao existe");
	myresponse.sendRedirect("/home.jsp");	//redireciona para home ou pagina not found padrao, pagina nao disponivel
}

%>


<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
