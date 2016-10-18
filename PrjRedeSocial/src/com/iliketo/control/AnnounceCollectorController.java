package com.iliketo.control;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import HardCore.DB;

import com.iliketo.control.EmailController.tipoEmail;
import com.iliketo.dao.AnnounceDAO;
import com.iliketo.dao.AuctionBidDAO;
import com.iliketo.dao.CollectionDAO;
import com.iliketo.dao.HobbyDAO;
import com.iliketo.dao.ItemDAO;
import com.iliketo.exception.ImageILiketoException;
import com.iliketo.exception.StorageILiketoException;
import com.iliketo.model.Announce;
import com.iliketo.model.AuctionBid;
import com.iliketo.model.Collection;
import com.iliketo.model.Hobby;
import com.iliketo.model.Item;
import com.iliketo.service.NotificationService;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class AnnounceCollectorController {


	static final Logger log = Logger.getLogger(AnnounceCollectorController.class);	
	
	
	/**
	 * Método intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
	
	@RequestMapping(value={"/registerAnnounce/collector/purchase"})
	public String announceCollectorPurchase(HttpServletRequest request, HttpServletResponse response){		
		log.info(request.getRequestURL());	
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("anuncioDeCompra", "sim");
		return "page.jsp?id=658"; //page form Register Purchase
	}
	
	@RequestMapping(value={"/registerAnnounce/hobby/{idHobby}/item"})
	public String announceCollectorItemHobby(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String idHobby){		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		Hobby h = (Hobby) new HobbyDAO(db, null).readById(idHobby, Hobby.class);
		
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("anuncioDeHobby", "sim");
		model.addAttribute("nomeCategoria", h.getNameCategory());
		model.addAttribute("idCategoria", h.getIdCategory());
		model.addAttribute("hobby", h);
		
		if(ModelILiketo.validateAndProcessError(request)){
			//valida e mostra error na pagina
			log.warn("Erro ao adicionar foto do anuncio de hobby!");
		}
		return "page.jsp?id=658"; 	//page form edit your announce
	}
	
	@RequestMapping(value={"/registerAnnounce/collector/{idItem}/itemOfCollection"})
	public String announceCollectorItem(HttpServletRequest request, HttpServletResponse response, @PathVariable String idItem){
		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		ModelILiketo model = new ModelILiketo(request, response);
		
		Item item = (Item) new ItemDAO(db, null).readById(idItem, Item.class);
		if(item != null){
			model.addAttribute("item", item);
			model.addAttribute("anuncioItemDeColecao", "sim");			
			Collection colecao = (Collection) new CollectionDAO(db, null).readById(item.getIdCollection(), Collection.class);
			model.addAttribute("colecao", colecao);
		}
		
		if(ModelILiketo.validateAndProcessError(request)){
			//valida e mostra error na pagina
			log.warn("Erro ao adicionar foto do anuncio de item da colecao!");
		}
		return "page.jsp?id=658"; //page form edit your announce
	}
	
	@RequestMapping(value={"/registerAnnounce/collector/particularItem"})
	public String announceParticularItem(HttpServletRequest request, HttpServletResponse response){
		log.info(request.getRequestURL());
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("itemParticular", "sim");
		
		if(ModelILiketo.validateAndProcessError(request)){
			//valida e mostra error na pagina
			log.warn("Erro ao adicionar foto do anuncio de item da colecao!");
		}
		return "page.jsp?id=658"; //page form edit your announce
	}

	@RequestMapping(value={"/registerAnnounce/collector/paymentPromotion"})
	public void announceCollectorPromotion(HttpServletRequest request, HttpServletResponse response) throws IOException, StorageILiketoException, ServletException{
		
		log.info(request.getRequestURL());		
		//TEM PROMOCAO PARA ESTE ANUNCIO
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		ModelILiketo model = new ModelILiketo(request, response);
		Announce anuncio = (Announce) cms.getObjectOfParameter(Announce.class);
		try{
			if(!anuncio.getTypeAnnounce().equals("Purchase")){
				if(anuncio.getIdItem() != null && !anuncio.getIdItem().isEmpty()){
					//anuncio do item que foi adicionado na colecao - copia imagem do item existente
					String imagemItem = cms.processFileuploadCopiarImagemAnuncio(anuncio.getPathPhotoAd());
					anuncio.setPathPhotoAd(imagemItem);
				}else{
					//anuncio de um item que nao foi adicionado na colecao - salva nova imagem
					cms.processFileuploadImagemAnuncio(anuncio);
				}
			}
		} catch (StorageILiketoException e) {
			throw e;
		} catch (ImageILiketoException e) {
			model.addMessageError("imageFormat", "Upload only Image in jpg format.");
			//trata pagina retorno
			if(request.getRequestURI().contains("particularItem")){				
				model.redirectError("/ilt/registerAnnounce/collector/particularItem");
			}else if(request.getRequestURI().contains("itemOfCollection")){
				model.redirectError("/ilt/registerAnnounce/collector/" +anuncio.getIdItem()+ "/itemOfCollection");
			}else if(request.getRequestURI().contains("purchase")){
				model.redirectError("/ilt/registerAnnounce/collector/purchase");
			}else if(request.getRequestURI().contains("hobbby")){
				model.redirectError("/ilt/registerAnnounce/hobby/" +anuncio.getIdHobby()+ "/item");
			}
		} catch (IOException e) {
			throw e;
		}
		anuncio.setStatus("For sale");
		anuncio.setFeatured("no");
		anuncio.setPaymentStatus("Completed");
		AnnounceDAO dao = new AnnounceDAO(db, request);
		String idRegistro = dao.create(anuncio);
		anuncio.setIdAnnounce(idRegistro);
		
		log.info("Anuncio de Promocao cadastrado com sucesso - Id: " + idRegistro + " Tipo: "+anuncio.getTypeAnnounce());
		HashMap<String, String> mapPages = new HashMap<>();
		mapPages.put("1162", cms.getPageListEntry("1162"));
		mapPages.put("1092", cms.getPageListEntry("1092"));
		
		//cria notificacao e envio de emails em background
		Thread run = new Thread(new Runnable(){
			@Override
			public void run() {
				String myUserid = anuncio.getIdMember();
				String idCategory = anuncio.getIdCategory();
				if(idCategory != null && !idCategory.equals("")){					
					NotificationService.createNotification(db, idCategory, "announce", anuncio.getIdAnnounce(), Str.INCLUDED, anuncio.getIdMember());
				}
				EmailController email = new EmailController(tipoEmail.EMAIL_ANUNCIO);
				email.enviaEmailNovoAnuncioColecionadorLoja(anuncio, idCategory, myUserid, db, request, mapPages);
			}
		});
		run.start();
		
		//retorna pagina visualizar meu anuncio
		response.sendRedirect("/ilt/ads?id=" + idRegistro);		
	}
	
	/**
	 * Metodo redireciona para visualizar pagina do anuncio
	 */
	@RequestMapping(value={"/ads"})
	public String pageAds(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		AnnounceDAO dao = new AnnounceDAO(db, request);
		String myUserid = (String) request.getSession().getAttribute("userid");
				
		String id = request.getParameter("id");								//id do anuncio
		Announce announce = (Announce) dao.readById(id, Announce.class);	//ler anuncio
		
		//identificar paginas de anuncios do membro
		String pageMeuLeilao = "page.jsp?id=705";				//pagina meu anuncio leilao para item
		String pageMeuAnuncio = "page.jsp?id=729";				//pagina meu anuncio venda/troca para item		
		String pageMeuAnuncioCompra = "page.jsp?id=729";		//pagina meu anuncio de compra
		//String pageMeuAnuncioLoja = "page.jsp?id=862";			//pagina meu anuncio de loja
		
		String pageLeilaoTerceiro = "page.jsp?id=706";			//pagina terceiro anuncio leilao para item
		String pageAnuncioTerceiro = "page.jsp?id=728";			//pagina terceiro venda/troca para item
		String pageAnuncioCompraTerceiro = "page.jsp?id=728";	//pagina terceiro anuncio de compra
		//String pageAnuncioLoja = "page.jsp?id=861";				//pagina terceiro anuncio de loja
		
		String pageVisualizarAnuncio = "";						//pagina para redirecionar
		
		if(announce != null){
			ModelILiketo model = new ModelILiketo(request, response);
			model.addAttribute("announce", announce);							//recuperar dados do anuncio na jsp
			if(announce.getIdMember().equals(myUserid)){
				//visao do meu anuncio
				//if(announce.getAccountType().equals("Store")){
					//pageVisualizarAnuncio = pageMeuAnuncioLoja;				//anuncio de loja
				//}else{
					if(announce.getTypeAnnounce().equalsIgnoreCase("Auction")){				
						if(!announce.getIdItem().equals("")){	
							pageVisualizarAnuncio = pageMeuLeilao;			//leilao para item
						}
					}else if(announce.getTypeAnnounce().equals("Purchase")){
						pageVisualizarAnuncio = pageMeuAnuncioCompra;		//anuncio de compra
					}else{
						if(announce.getAdHobby().equalsIgnoreCase("y") || !announce.getIdHobby().isEmpty()){
							pageVisualizarAnuncio = pageMeuAnuncio;		//anuncio venda e troca para hobby
						}else{
							pageVisualizarAnuncio = pageMeuAnuncio;		//venda/troca para item
						}
					}
				//}
			}else{
				//visao de terceiros do anuncio
				//valida anuncio pago
				if(announce.getStatus().equals("Pending pay")){		
					
					log.info("Anuncio cadastrado com status 'Pendente pagamento' - Id: " + id +" - visualizacao disponivel somente para o vendedor!");
					pageVisualizarAnuncio = "page.jsp?id=994";		//Pagina anuncio indisponivel, em processo de pagamento.
					
				}else{
					
					//if(announce.getAccountType().equals("Store")){
					//pageVisualizarAnuncio = pageAnuncioLoja;					//anuncio de loja
					//}else{
						if(announce.getTypeAnnounce().equalsIgnoreCase("Auction")){				
							if(!announce.getIdItem().equals("")){	
								pageVisualizarAnuncio = pageLeilaoTerceiro;		//leilao para item
							}
						}else if(announce.getTypeAnnounce().equals("Purchase")){
							pageVisualizarAnuncio = pageAnuncioCompraTerceiro;	//anuncio de compra
						}else{
							if(announce.getAdHobby().equalsIgnoreCase("y") || !announce.getIdHobby().isEmpty()){
								pageVisualizarAnuncio = pageAnuncioTerceiro;	//anuncio venda e troca para hobby
							}else{
								pageVisualizarAnuncio = pageAnuncioTerceiro;	//venda/troca para item
							}
						}
					//}
					
				}
			}
		}
		
		//valida pagina correta do anuncio
		if(!pageVisualizarAnuncio.equals("")){			
			return pageVisualizarAnuncio;		//redireciona para pagina correspondente ao anuncio
		}else{
			return "page.jsp?id=invalid"; 			//page conteudo nao disponivel
		}
		
	}
	
	/**
	 * Metodo faz o lance de precos no anuncio por solicitacao ajax e retorna resposta com conteudo 'ok' para jsp
	 */
	@RequestMapping(value={"/ajax/ads/bid"})
	public void adsBid(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException{
		
		log.info("ajax " + request.getRequestURL());
		
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
	    		try {
		    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	    		
		    		long miliInicial = format.parse(announce.getDateInitial()).getTime();	//milisegundos inicial
		    		long miliFinal = miliInicial + ( Integer.parseInt(announce.getLasting()) * (1000*60*60*24) );
		    		long miliAgora = new java.util.Date().getTime(); 								//milisegundos agora
		    		//valida leilao encerrado
		    		if(miliFinal > miliAgora){
		    			String valueBid = String.format( "%.2f", Double.parseDouble(bid)); //format double
						AuctionBid auction = new AuctionBid();
						auction.setIdAnnounce(id);
						auction.setIdMember(myUserid);
						auction.setBid(valueBid);
						auctionDAO.create(auction);			//cria lance de leilao				
						int total = Integer.parseInt(announce.getTotalBids()) + 1;	//incrementa total de lances
						announce.setTotalBids(Integer.toString(total));	//seta total de lances no anuncio				
						announce.setBidActual(valueBid);				//seta valor lance no anuncio
						announce.setBidUserId(myUserid);				//seta id membro proprietario do lance
						announceDAO.update(announce, false);			//atualiza anuncio			
						jsonObj.put("resposta", "ok");					//resposta lance ok
						jsonObj.put("valueBid", valueBid);
						jsonObj.put("totalBids", announce.getTotalBids());
		    		}else{
		    			//encerrado
		    			jsonObj.put("resposta", "ended");			//erro leilao encerrado
						jsonObj.put("valueBid", announce.getBidActual());
						jsonObj.put("totalBids", announce.getTotalBids());
		    		}
				} catch (ParseException e) {
					log.info("Error ParseException URI: " + request.getRequestURI() +" - query: "+ request.getQueryString());
					log.warn(e.getMessage());
				}
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
		
		log.info(request.getRequestURL());
				
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
		
		log.info(request.getRequestURL());
		
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
	
	@RequestMapping(value={"/announce/changeStatus"})
	public String changeStatus(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		AnnounceDAO announceDAO = new AnnounceDAO(db, request);
		
		String id = request.getParameter("id");										//id anuncio
		String status = request.getParameter("status");								//status
		Announce announce = (Announce) announceDAO.readById(id, Announce.class);	//ler anuncio
		
		//valida status
		String myUserid = (String) request.getSession().getAttribute("userid");
		if(announce.getIdMember().equals(myUserid)){
			if(!status.equals("")){
				announce.setStatus(status);
				announceDAO.update(announce, false);
			}
		}
				
		return "redirect:/ilt/ads?id=" + id; 	//success - page anuncio
	}
	
	
	@RequestMapping(value={"/announce/edit"})
	public String editAnnounce(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		AnnounceDAO announceDAO = new AnnounceDAO(db, request);
		
		String id = request.getParameter("id");										//id anuncio
		Announce announce = (Announce) announceDAO.readById(id, Announce.class);	//ler anuncio
		
		//valida anuncio membro
		String myUserid = (String) request.getSession().getAttribute("userid");
		if(announce.getIdMember().equals(myUserid)){
			ModelILiketo model = new ModelILiketo(request, response);
			model.addAttribute("announce", announce);		//dados atual do anuncio				
			return "page.jsp?id=888";						//page form edit announcement
		}else{
			return "page.jsp?id=invalid";					//invalid page
		}		
	}
	
	@RequestMapping(value={"/announce/save"})
	public String save(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		AnnounceDAO announceDAO = new AnnounceDAO(db, request);
		
		Announce announce = (Announce) cms.getObjectOfParameter(Announce.class);		//objeto com dados do form
		announceDAO.update(announce, false);
				
		return "redirect:/ilt/ads?id=" + announce.getIdAnnounce(); 					//success - page anuncio
	}
	
	@RequestMapping(value={"/announce/saveRating"}, method = RequestMethod.POST)
	public String saveRating(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		String idAds = request.getParameter("id_announce");
		String rating = request.getParameter("rating");
		
		if(idAds != null && !idAds.isEmpty() && rating != null && !rating.isEmpty()){
			AnnounceDAO dao = new AnnounceDAO(db, request);
			Announce anuncio = new Announce();
			anuncio.setIdAnnounce(idAds);
			anuncio.setRating(rating);			
			dao.update(anuncio, false);
			log.info("Id collector: " + myUserid + " avaliou a classificacao de venda do anuncio: " + idAds);
		}
		
		return "redirect:/ilt/ads?id=" + idAds; 		//success - classificacao salva
	}
	
	@RequestMapping(value={"/announce/buyFeatured"}, method = RequestMethod.POST)
	public String buyFeatured(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
				
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		AnnounceDAO announceDAO = new AnnounceDAO(db, request);
		String myUserid = (String) request.getSession().getAttribute("userid");
		String id = request.getParameter("id");										//id do anuncio
		Announce anuncio = (Announce) announceDAO.readById(id, Announce.class);		//ler anuncio
		
		//valida anuncio
		if(anuncio != null && anuncio.getIdMember().equals(myUserid)){
			ModelILiketo model = new ModelILiketo(request, response);
			model.addAttribute("announce", anuncio);
			return "page.jsp?id=856"; 		//page ads see bids
		}
		
		return "/page.jsp?id=invalid";		//pagina conteudo indisponivel
	}
	

	@RequestMapping(value={"/testeEnviaEmailAnuncioCategorias"})
	public void testeEnviaEmailAnuncioCategorias(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		AnnounceDAO dao = new AnnounceDAO(db, request);
		String myUserid = (String) request.getSession().getAttribute("userid");
				
		String id = request.getParameter("id");
		Announce anuncio = (Announce) dao.readById(id, Announce.class);
		EmailController email = new EmailController(tipoEmail.EMAIL_ANUNCIO);
		email.enviaEmailNovoAnuncioColecionadorLoja(anuncio, anuncio.getIdCategory(), myUserid, db, request, null);
	}
}
