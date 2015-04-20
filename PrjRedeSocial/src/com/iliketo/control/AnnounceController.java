package com.iliketo.control;

import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import HardCore.DB;
import HardCore.Request;
import HardCore.Response;
import HardCore.Session;

import com.iliketo.bean.AnnounceJB;
import com.iliketo.dao.IliketoDAO;
import com.iliketo.util.ColumnsSingleton;

public class AnnounceController {
	
	/**
	 * Metodo executa passo 1 - Informacoes do Anuncio
	 * Recupera informacoes do item ou colecao, seta na sessao o AnnounceJB
	 * Redireciona para tela passo 2 - Pagamento
	 *  
	 * *campos da database dbannounce*
	 * id_announce
	 * type_announce(sell, exchange, sell/exchange, auction)
	 * price_fixed
	 * price_initial
	 * bid_actual
	 * lasting
	 * total_bids
	 * date_created
	 * date_updated
	 * title
	 * description
	 * name_category
	 * fk_collection_id
	 * fk_item_id
	 * fk_user_id
	 * fk_category_id
	 * path_photo_ad
	 * @param db
	 * @param myrequest
	 * @param mysession
	 * @param myresponse
	 * @param idItem
	 */	
	public void announceItemCollectionPass1(DB db, Request myrequest, Session mysession, Response myresponse, String content, String idContent){
		
		String myUserId = mysession.get("userid");
		String fk_user_id = "";
		
		if(content.equals("item")){
			fk_user_id = IliketoDAO.getValueOfDatabase(db, "fk_user_id", "dbcollectionitem", "id_item", idContent);
		}else if(content.equals("collection")){
			fk_user_id = IliketoDAO.getValueOfDatabase(db, "fk_user_id", "dbcollection", "id_collection", idContent);
		}
		
		//valida se o item pertence ao usuario na sessao
		if(myUserId.equals(fk_user_id)){
			
			ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
			HashMap<String,String> record = null;
			String SQL = "";
			AnnounceJB announceJB = new AnnounceJB();
			
			if(content.equals("item")){
				announceJB.setIdItem(idContent);
				announceJB.setIdCollection("");
				SQL = "select "
					  + "t1.title_item as title, "
					  + "t1.description_item as description, "
					  + "t1.path_photo_item as path_photo_ad, "
					  + "c1.name_category as name_category, "
					  + "c1.fk_category_id as fk_category_id "
					  + "from dbcollectionitem t1 join dbcollection c1 "
					  + "on t1.fk_collection_id = c1.id_collection "
					  + "where t1.id_item = '" +idContent+ "';";
				String[][] aliasTable = { {"dbcollectionitem", "t1"}, {"dbcollection", "c1"} };		
				SQL = CS.transformSQLReal(SQL, aliasTable);
				record  = db.query_record(SQL);				
				
			}else if(content.equals("collection")){
				announceJB.setIdCollection(idContent);
				announceJB.setIdItem("");
				
				SQL = "select "
					  + "c1.name_collection as title, "
					  + "c1.description as description, "
					  + "c1.name_category as name_category, "
					  + "c1.fk_category_id as fk_category_id, "
					  + "c1.path_photo_collection as path_photo_ad "
					  + "from dbcollection c1 "
					  + "where c1.id_collection ='" +idContent+ "';";
				String[][] aliasTable = { {"dbcollection", "c1"} };
				SQL = CS.transformSQLReal(SQL, aliasTable);
				record  = db.query_record(SQL);				
			}
			
			//seta informacoes do conteudo item ou colecao no AnuncioJB			
			announceJB.setTitle(record.get("title"));
			announceJB.setDescription(record.get("description"));
			announceJB.setNameCategory(record.get("name_category"));
			announceJB.setIdCategory(record.get("fk_category_id"));
			announceJB.setPath_photo_ad(record.get("path_photo_ad"));
			announceJB.setIdMember(myUserId);

			announceJB.setTypeAnnounce(myrequest.getParameter("type_announce"));
			announceJB.setPriceFixed(myrequest.getParameter("price_fixed"));
			announceJB.setPriceInitial(myrequest.getParameter("price_initial"));
			announceJB.setLasting(myrequest.getParameter("lasting"));
			announceJB.setBidActual(myrequest.getParameter("price_initial"));		//lance atual = preco inicial
			announceJB.setTotalBids("0");											//total lances comecao com "0"
			
			//recupera informacoes do cartao do membro na database
			readInfoCard(db, myrequest, myUserId);
			
			mysession.getSession().setAttribute("announceJB", announceJB);	//set no HttpSession
			HttpServletRequest httpReq = myrequest.getRequest();
			httpReq.setAttribute("announceJB", announceJB);					//set no HttpServletRequest (replace codigos especiais)
			
			//chama jsp passo2
			System.out.println("Log - " + "Mostrar tela Detalhes do Pagamento - passo 2");
			try {				
				RequestDispatcher rq = myrequest.getRequest().getRequestDispatcher("/page.jsp?id=659");			
				rq.forward(myrequest.getRequest(), myresponse.getResponse());	//tela passo 2
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * Metodo executa passo 2 - Detalhes do Pagamento
	 * Seta na sessao os dados do cartao informado
	 * Redireciona para tela passo 3 - Confirmacao	 * 
	 * @param db
	 * @param myrequest
	 * @param mysession
	 * @param myresponse
	 */
	public void announcePaymentPass2(DB db, Request myrequest, Session mysession, Response myresponse){
		
		//details card payment
		HttpServletRequest httpReq = myrequest.getRequest();
		
		httpReq.setAttribute("user_card_type", myrequest.getParameter("user_card_type"));
		httpReq.setAttribute("user_card_number", myrequest.getParameter("user_card_number"));
		httpReq.setAttribute("user_card_issuedmonth", myrequest.getParameter("user_card_issuedmonth"));
		httpReq.setAttribute("user_card_issuedyear", myrequest.getParameter("user_card_issuedyear"));
		httpReq.setAttribute("user_card_expirymonth", myrequest.getParameter("user_card_expirymonth"));
		httpReq.setAttribute("user_card_expiryyear", myrequest.getParameter("user_card_expiryyear"));
		httpReq.setAttribute("user_card_name", myrequest.getParameter("user_card_name"));
		httpReq.setAttribute("user_card_cvc", myrequest.getParameter("user_card_cvc"));
		httpReq.setAttribute("user_card_issue", myrequest.getParameter("user_card_issue"));
		httpReq.setAttribute("user_card_postalcode", myrequest.getParameter("user_card_postalcode"));
		
		//salva novas informacoes do cartao novo ou mantem o atual atualizado
		saveInfoCard(db, myrequest, mysession.get("userid"));
		
		AnnounceJB announceJB = (AnnounceJB) mysession.getSession().getAttribute("announceJB");	//recupera anuncio da sessao
		httpReq.setAttribute("announceJB", announceJB);											//set anuncio no HttpServletRequest (replace codigos especiais)
		
		//chama jsp passo3 confirmar pagamento
		System.out.println("Log - " + "Mostrar tela confirmar pagamento - passo 3");
		try {
			
			RequestDispatcher rq = myrequest.getRequest().getRequestDispatcher("/page.jsp?id=660");		
			rq.forward(myrequest.getRequest(), myresponse.getResponse());	//tela passo 3
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Metodo executa passo 3 - Confirmacao do anuncio e pagamento
	 * Realiza a operacao de pagamento
	 * Valida pagamento
	 * Redireciona para /post_announce.jsp gravar registro do anuncio na database	 * 
	 * @param db
	 * @param myrequest
	 * @param mysession
	 * @param myresponse
	 */
	public void announceConfirmPass3(DB db, Request myrequest, Session mysession, Response myresponse){
		
		//confirm payment
		//1 recupera informacoes dos dados do cartao na sessao		
		//2 operacao de pagamento
		//3 valida pagamento
		//4 registra anuncio na database
		
		//chama jsp para gravar na database o novo anuncio
		try {
			
			AnnounceJB announceJB = (AnnounceJB) mysession.getSession().getAttribute("announceJB");	//recupera anuncio da sessao
			HttpServletRequest httpReq = myrequest.getRequest();
			httpReq.setAttribute("announceJB", announceJB);											//set anuncio no HttpServletRequest (replace codigos especiais)
			
			//jsp post_announce.jsp grava dados na database dbannounce
			System.out.println("Log - " + "Confirma pagamento ok!");
			RequestDispatcher rq = myrequest.getRequest().getRequestDispatcher("/post_announce.jsp");
			rq.forward(myrequest.getRequest(), myresponse.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Recupera informacoes dos dados do cartao do membro na tabela users e seta na sessao
	 * @param db
	 * @param mysession
	 * @param myUserId
	 */
	private void readInfoCard(DB db, Request myrequest, String myUserId){

		HttpServletRequest httpReq = myrequest.getRequest();
		
		//recupera informacoes do cartao do membro na tabela users
		String SQL = 
			    "select "
			  + "card_type, "
			  + "card_number, "
			  + "card_issuedmonth, "
			  + "card_issuedyear, "
			  + "card_expirymonth, "
			  + "card_expiryyear, "
			  + "card_name, "
			  + "card_cvc, "
			  + "card_issue, "
			  + "card_postalcode "
			  + "from users "			  
			  + "where id = '" +myUserId+ "';";
	
		HashMap<String,String> record  = db.query_record(SQL);
		
		//seta na sessao informacoes do cartao do membro
		if(record != null){
			httpReq.setAttribute("user_card_type", record.get("card_type"));
			httpReq.setAttribute("user_card_number", record.get("card_number"));
			httpReq.setAttribute("user_card_issuedmonth", record.get("card_issuedmonth"));
			httpReq.setAttribute("user_card_issuedyear", record.get("card_issuedyear"));
			httpReq.setAttribute("user_card_expirymonth", record.get("card_expirymonth"));
			httpReq.setAttribute("user_card_expiryyear", record.get("card_expiryyear"));
			httpReq.setAttribute("user_card_name", record.get("card_name"));
			httpReq.setAttribute("user_card_cvc", record.get("card_cvc"));
			httpReq.setAttribute("user_card_issue", record.get("card_issue"));
			httpReq.setAttribute("user_card_postalcode", record.get("card_postalcode"));
		}
		
		
	}
	
	/**
	 * Salva dados do cartao do membro que esta na sessao ou atualiza o atual na tabela users
	 * @param db
	 * @param httpReq
	 * @param myUserId
	 */
	private void saveInfoCard(DB db, Request myrequest, String myUserId) {
		
		HttpServletRequest httpReq = myrequest.getRequest();
		
		//recupera da sessao informacoes do cartao do membro e atualiza na table users		
		String SQLupdate = 
			    "card_type='" + httpReq.getAttribute("user_card_type") + "', "
			  + "card_number='" + httpReq.getAttribute("user_card_number") + "', "
			  + "card_issuedmonth='" + httpReq.getAttribute("user_card_issuedmonth") + "', "
			  + "card_issuedyear='" + httpReq.getAttribute("user_card_issuedyear") + "', "
			  + "card_expirymonth='" + httpReq.getAttribute("user_card_expirymonth") + "', "
			  + "card_expiryyear='" + httpReq.getAttribute("user_card_expiryyear") + "', "
			  + "card_name='" + httpReq.getAttribute("user_card_name") + "', "
			  + "card_cvc='" + httpReq.getAttribute("user_card_cvc") + "', "
			  + "card_issue='" + httpReq.getAttribute("user_card_issue") + "', "
			  + "card_postalcode='" + httpReq.getAttribute("user_card_postalcode") + "' ";
		
		db.updateSet("users", "id", myUserId, SQLupdate);
		
	}
	
}

	
	

