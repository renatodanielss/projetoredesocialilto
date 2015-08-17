package com.iliketo.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.AnnounceDAO;
import com.iliketo.dao.AuctionBidDAO;
import com.iliketo.dao.CollectionDAO;
import com.iliketo.dao.ItemDAO;
import com.iliketo.dao.UserCardDAO;
import com.iliketo.model.Announce;
import com.iliketo.model.AuctionBid;
import com.iliketo.model.Collection;
import com.iliketo.model.Item;
import com.iliketo.model.UserCard;
import com.iliketo.service.NotificationService;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class AnnounceCollectorController {


	@RequestMapping(value={"/registerAnnounce/collector/purchase"})
	public String announceCollectorPurchase(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @AnnounceCollectorController url='/registerAnnounce/collector/purchase'");
				
		return "page.jsp?id=754"; //page form Register Purchase
	}
	
	@RequestMapping(value={"/registerAnnounce/collector/item"})
	public String announceCollectorItem(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @AnnounceCollectorController url='/registerAnnounce/collector/item'");
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		ItemDAO itemDAO = new ItemDAO(db, request);
		
		String id = request.getParameter("idItem");					//id item
		Item item = (Item) itemDAO.readById(id, Item.class);		//ler item
		
		HttpSession session = request.getSession();
		session.setAttribute("item", item);							//set item da colecao na session
		
		
		//model view jsp para binding do bean
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("item", item);
				
		return "page.jsp?id=658"; //page form edit your announce
	}
	
	@RequestMapping(value={"/registerAnnounce/collector/collection"})
	public String announceCollectorCollection(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @AnnounceCollectorController url='/registerAnnounce/collector/collection'");
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CollectionDAO collectionDAO = new CollectionDAO(db, request);
		
		String id = request.getParameter("idCollection");									//id colecao
		Collection collection = (Collection) collectionDAO.readById(id, Collection.class);	//ler colecao
		
		HttpSession session = request.getSession();
		session.setAttribute("collection", collection);										//set colecao na session
		
		
		//model view jsp para binding do bean
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("collection", collection);
				
		return "page.jsp?id=664"; //page form edit your announce
	}
	
	
	@RequestMapping(value={"/registerAnnounce/collector/payment"})
	public String announceCollectorPayment(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @AnnounceCollectorController url='/registerAnnounce/collector/payment'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		CollectionDAO collectionDAO = new CollectionDAO(db, request);
		HttpSession session = request.getSession();
				
		Announce announce = (Announce) cms.getObjectOfParameter(Announce.class); 	//popula um objeto com dados do form
		
		//Purchase
		if(announce.getTypeAnnounce().equals("Purchase")){
			//announce.setPathPhotoAd(); //photo default registro de compra
		}else{
			
			//Sell, Auction, Exchange			
			//pagamento para anuncio de item
			Item item = (Item) session.getAttribute("item");
			if(item != null){	//item
				String idCollection = item.getIdCollection();
				Collection col = (Collection) collectionDAO.readById(idCollection, Collection.class);				
				//set dados do item no anuncio
				announce.setIdItem(item.getIdItem());
				announce.setTitle(item.getTitle());
				announce.setDescription(item.getDescription());
				announce.setPathPhotoAd(item.getPathPhoto());
				announce.setIdCategory(col.getIdCategory());
				announce.setNameCategory(col.getNameCategory());
			}
			
			//pagamento para anuncio de colecao
			Collection collection = (Collection) session.getAttribute("collection");
			if(collection != null){
				//set dados da colecao no anuncio
				announce.setIdCollection(collection.getIdCollection());
				announce.setTitle(collection.getNameCollection());
				announce.setDescription(collection.getDescription());
				announce.setPathPhotoAd(collection.getPathPhoto());
				announce.setIdCategory(collection.getIdCategory());
				announce.setNameCategory(collection.getNameCategory());
			}
		}
		
		session.setAttribute("announce", announce);							//set anuncio na session
		
		//dados cartao do membro
		UserCardDAO userCardDAO = new UserCardDAO(db);		
		UserCard userCard = userCardDAO.readInfoCard((String) session.getAttribute("userid"));
		
		
		//model view jsp para binding do bean
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("userCard", userCard);
		
		return "page.jsp?id=659"; //page form payment
	}
	
	@RequestMapping(value={"/registerAnnounce/collector/confirm"})
	public String announceCollectorConfirm(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @AnnounceCollectorController url='/registerAnnounce/collector/confirm'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		UserCardDAO userCardDAO = new UserCardDAO(db);
		HttpSession session = request.getSession();	
		
		UserCard userCard = (UserCard) cms.getObjectOfParameter(UserCard.class);	//recupera objeto com dados do form
		
		userCardDAO.saveInfoCard(userCard);									//salva dados atualizados do cartao
		session.setAttribute("userCard", userCard);							//set dados cartao na session
		
		
		//model view jsp para binding do bean
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("userCard", userCard);
		model.addAttribute("announce", (Announce) session.getAttribute("announce"));
		
		return "page.jsp?id=660"; //page confirm
	}
	
	@RequestMapping(value={"/registerAnnounce/collector/addAnnounce"})
	public String announceCollectorAddAnnounce(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @AnnounceCollectorController url='/registerAnnounce/collector/addAnnounce'");
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		AnnounceDAO announceDAO = new AnnounceDAO(db, request);		
		HttpSession session = request.getSession();		
		
		Announce announce = (Announce) session.getAttribute("announce");	 	//recupera anuncio da sessao
		UserCard userCard = (UserCard) session.getAttribute("userCard");	 	//recupera dados do cartao da sessao		
		String idCreated = "";
		
		if(announce != null && userCard != null){
			
			idCreated = announceDAO.create(announce);							//salva anuncio no bd
			//***operacao para pagamento do anuncio aqui***
			
			
			//remove atributos da sessao
			session.removeAttribute("item");
			session.removeAttribute("collection");
			session.removeAttribute("announce");
			session.removeAttribute("userCard");
			System.out.println("Log - " + "Anuncio cadastrado com sucesso!");
			
			//cria notificacao para o grupo da categoria
			String idCategory = announce.getIdCategory();
			if(idCategory != null && !idCategory.equals("")){
				String myUserid = (String) request.getSession().getAttribute("userid");
				NotificationService.createNotification(db, idCategory, "announce", idCreated, Str.INCLUDED, myUserid);
			}
			
		}else{
			System.out.println("Log - " + "Error acesso invalido, anuncio nao cadastrado!");
			return "page.jsp?id=48"; 					//invalid - redirect page my collections
		}
			
		
		return "redirect:/ilt/ads?id=" + idCreated; 	//success - page anuncio criado
	}
	
	
	/**
	 * Metodo redireciona para visualizar pagina do anuncio
	 */
	@RequestMapping(value={"/ads"})
	public String pageAds(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @AnnounceCollectorController url='/ads'");
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		AnnounceDAO dao = new AnnounceDAO(db, request);
		String myUserid = (String) request.getSession().getAttribute("userid");
				
		String id = request.getParameter("id");								//id do anuncio
		Announce announce = (Announce) dao.readById(id, Announce.class);	//ler anuncio
		
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("announce", announce);							//recuperar dados do anuncio na jsp
		
		
		//identificar paginas de anuncios do membro
		String pageMeuLeilao = "page.jsp?id=705";				//pagina meu anuncio leilao para item
		String pageMeuAnuncio = "page.jsp?id=729";				//pagina meu anuncio venda/troca para item		
		String pageMeuAnuncioCompra = "page.jsp?id=xxx";		//pagina meu anuncio de compra
		String pageMeuAnuncioLoja = "page.jsp?id=862";			//pagina meu anuncio de loja
		
		String pageLeilaoTerceiro = "page.jsp?id=706";			//pagina terceiro anuncio leilao para item
		String pageAnuncioTerceiro = "page.jsp?id=728";			//pagina terceiro venda/troca para item
		String pageAnuncioCompraTerceiro = "page.jsp?id=xxx";	//pagina terceiro anuncio de compra
		String pageAnuncioLoja = "page.jsp?id=861";				//pagina terceiro anuncio de loja
		
		String pageVisualizarAnuncio = "";						//pagina para redirecionar
		
		if(announce.getIdMember().equals(myUserid)){
			//visao do meu anuncio
			if(announce.getAccountType().equals("Store")){
				pageVisualizarAnuncio = pageMeuAnuncioLoja;				//anuncio de loja
			}else{
				if(announce.getTypeAnnounce().equalsIgnoreCase("Auction")){				
					if(!announce.getIdItem().equals("")){	
						pageVisualizarAnuncio = pageMeuLeilao;			//leilao para item
					}
				}else if(announce.getTypeAnnounce().equals("Purchase")){
					pageVisualizarAnuncio = pageMeuAnuncioCompra;		//anuncio de compra
				}else{
					if(!announce.getIdItem().equals("")){
						pageVisualizarAnuncio = pageMeuAnuncio;			//venda/troca para item
					}
				}
			}
		}else{
			//visao de terceiros do anuncio
			if(announce.getAccountType().equals("Store")){
				pageVisualizarAnuncio = pageAnuncioLoja;				//anuncio de loja
			}else{
				if(announce.getTypeAnnounce().equalsIgnoreCase("Auction")){				
					if(!announce.getIdItem().equals("")){	
						pageVisualizarAnuncio = pageLeilaoTerceiro;		//leilao para item
					}
				}else if(announce.getTypeAnnounce().equals("Purchase")){
					pageVisualizarAnuncio = pageAnuncioCompraTerceiro;	//anuncio de compra
				}else{
					if(!announce.getIdItem().equals("")){
						pageVisualizarAnuncio = pageAnuncioTerceiro;	//venda/troca para item
					}
				}
			}
		}
		
		//valida pagina correta do anuncio
		if(!pageVisualizarAnuncio.equals("")){			
			return pageVisualizarAnuncio;		//redireciona para pagina correspondente ao anuncio
		}else{
			return "page.jsp?id=xxx"; 			//page conteudo nao disponivel
		}
		
	}
	
	/**
	 * Metodo faz o lance de precos no anuncio por solicitacao ajax e retorna resposta com conteudo 'ok' para jsp
	 */
	@RequestMapping(value={"/ajax/ads/bid"})
	public void adsBid(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException{
		
		System.out.println("Log - " + "ajax @AnnounceCollectorController url='/ajax/ads/bid'");
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		AnnounceDAO announceDAO = new AnnounceDAO(db, request);
		AuctionBidDAO auctionDAO = new AuctionBidDAO(db, request);
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		String id = request.getParameter("idAnnounce");								//id do anuncio
		String bid = request.getParameter("bid");									//valor novo lance
		Announce announce = (Announce) announceDAO.readById(id, Announce.class);	//ler anuncio
		
		JSONArray jsonArray = new JSONArray();	//resposta json
	    JSONObject jsonObj = new JSONObject();
	    
		//valida novo lance
		Pattern p = Pattern.compile("^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$");	//ex 1000.99
		Matcher m = p.matcher(bid);
	    if (m.find()) {
	    	if(Double.parseDouble(bid) > Double.parseDouble(announce.getBidActual().replaceAll(",", "."))){
	    		//format double
	    		String valueBid = String.format( "%.2f", Double.parseDouble(bid));
				AuctionBid auction = new AuctionBid();
				auction.setIdAnnounce(id);
				auction.setIdMember(myUserid);
				auction.setBid(valueBid);
				auctionDAO.create(auction);			//cria lance de leilao				
				int total = Integer.parseInt(announce.getTotalBids()) + 1;	//incrementa total de lances
				announce.setTotalBids(Integer.toString(total));	//seta total de lances no anuncio				
				announce.setBidActual(valueBid);				//seta valor lance no anuncio
				announce.setBidUserId(myUserid);				//seta id membro proprietario do lance
				announceDAO.update(announce);					//atualiza anuncio			
				jsonObj.put("resposta", "ok");					//resposta lance ok
				jsonObj.put("valueBid", valueBid);
				jsonObj.put("totalBids", announce.getTotalBids());
			}else{
				jsonObj.put("resposta", "below");				//erro valor lance tem q ser maior q atual
				jsonObj.put("valueBid", announce.getBidActual());
				jsonObj.put("totalBids", announce.getTotalBids());
			}
	    }else{
	    	jsonObj.put("resposta", "invalid!");				//erro valor lance invalido
	    	jsonObj.put("valueBid", announce.getBidActual());
	    	jsonObj.put("totalBids", announce.getTotalBids());
	    }
	    
	    jsonArray.put(jsonObj);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().write(jsonArray.toString());
	}
	
	/**
	 * Metodo redireciona para page Visualizar todos lances do anuncio
	 */
	@RequestMapping(value={"/ads/seeBids"})
	public String seeBids(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @AnnounceCollectorController url='/ads/seeBids'");
				
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		AnnounceDAO announceDAO = new AnnounceDAO(db, request);
		
		String id = request.getParameter("id");										//id do anuncio
		Announce announce = (Announce) announceDAO.readById(id, Announce.class);	//ler anuncio
		
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("announce", announce);									//recuperar dados do anuncio na jsp
		
		return "page.jsp?id=856"; //page ads see bids
	}
	
	
	@RequestMapping(value={"/announce/delete"})
	public String deleteCollection(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @AnnounceCollectorController url='/announce/delete'");
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		AnnounceDAO announceDAO = new AnnounceDAO(db, request);
		
		String id = request.getParameter("id");										//id anuncio
		Announce announce = (Announce) announceDAO.readById(id, Announce.class);	//ler anuncio
		
		String myUserid = (String) request.getSession().getAttribute("userid");
		String redirect = "";
		if(announce.getIdMember().equals(myUserid)){
			if(announce.getTypeAnnounce().contains("Sell")){
				redirect = "/page.jsp?id=751&id_member=" + myUserid;
			}else if(announce.getTypeAnnounce().contains("Auction")){
				redirect = "/page.jsp?id=753&id_member=" + myUserid;
			}else if(announce.getTypeAnnounce().contains("Exchange")){
				redirect = "/page.jsp?id=752&id_member=" + myUserid;
			}else if(announce.getTypeAnnounce().contains("Purchase")){
				redirect = "/page.jsp?id=756&id_member=" + myUserid;
			}
			announceDAO.deleteAnnounce(id);		//deleta anuncio
		}
				
		return "redirect:" + redirect;			//success
	}
	
	
}
