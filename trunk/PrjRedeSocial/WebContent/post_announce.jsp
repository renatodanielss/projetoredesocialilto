<%@page import="com.iliketo.dao.IliketoDAO"%>
<%@page import="com.iliketo.util.Str"%>
<%@page import="com.iliketo.bean.AnnounceJB"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>


<%

System.out.println("Log - " + "execute action post_announce");
AnnounceJB announceJB = (AnnounceJB) mysession.getSession().getAttribute("announceJB");

if(announceJB != null){

	//cria registro de um novo anuncio
	myrequest.setParameter("database", "dbannounce");	//set database
	
	//seta no myrequest os names dos campos do input form iguais os campos database dbannounce
	myrequest.setParameter("type_announce", announceJB.getTypeAnnounce());
	myrequest.setParameter("price_fixed", announceJB.getPriceFixed());
	myrequest.setParameter("price_initial", announceJB.getPriceInitial());
	myrequest.setParameter("bid_actual", announceJB.getBidActual());
	myrequest.setParameter("lasting", announceJB.getLasting());
	myrequest.setParameter("total_bids", announceJB.getTotalBids());
	myrequest.setParameter("title", announceJB.getTitle());
	myrequest.setParameter("description", announceJB.getDescription());
	myrequest.setParameter("name_category", announceJB.getNameCategory());
	myrequest.setParameter("fk_collection_id", announceJB.getIdCollection());
	myrequest.setParameter("fk_item_id", announceJB.getIdItem());
	myrequest.setParameter("fk_user_id", announceJB.getIdMember());
	myrequest.setParameter("fk_category_id", announceJB.getIdCategory());
	myrequest.setParameter("path_photo_ad", announceJB.getPath_photo_ad());
	
	//metodo doPost grava dados na dbannounce
	UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
	Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_announce");
	
	String idAnnounce = data.getId();
	if(idAnnounce != null && !idAnnounce.equals("")){
		System.out.println("Log - " + "Anuncio cadastrado com sucesso");
		
		//redireciona tela visualizar meu anuncio
		if(!announceJB.getIdItem().equals("")){
			session.setAttribute("announceJB", announceJB);
			mysession.getSession().removeAttribute("announceJB");
			RequestDispatcher rq = myrequest.getRequest().getRequestDispatcher("/ads.jsp?id=" + idAnnounce);		
			rq.forward(myrequest.getRequest(), myresponse.getResponse());	//ads.jsp responsavel por direcionar para tela visualizacao de anuncio
		}
		
	}else{
		System.out.println("Log - " + "Error anuncio nao cadastrado!");		
	}		
}else{
	System.out.println("Log - " + "Error anuncio nao cadastrado!");
}

%>


<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
