package com.iliketo.control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.AnnounceDAO;
import com.iliketo.dao.StoreItemDAO;
import com.iliketo.exception.ImageILiketoException;
import com.iliketo.exception.StorageILiketoException;
import com.iliketo.model.Announce;
import com.iliketo.model.StoreItem;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class AnnounceStoreController {

	
	static final Logger log = Logger.getLogger(AnnounceStoreController.class);
	
	/**
	 * Método intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
	
	@RequestMapping(value={"/registerAnnounce/store"})
	public String announceStoreChoose(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		if(ModelILiketo.validateAndProcessError(request)){
			//valida e mostra error na pagina
			log.warn("Erro ao adicionar imagem item de loja. Tela formulario registrar anuncio");
		}
		
		return "page.jsp?id=757"; //page form choose type announce
	}
	
	@RequestMapping(value={"/registerAnnounce/store/sale"})
	public String announceStoreSale(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		log.info(request.getRequestURL());
		//remove atributos da sessao
		request.getSession().removeAttribute("announce");
		request.getSession().removeAttribute("idCreateItems");
		
		return "page.jsp?id=758"; //page form Register Sale 
		
	}
	
	@RequestMapping(value={"/registerAnnounce/store/auction"})
	public String announceStoreAuction(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		log.info(request.getRequestURL());
		//remove atributos da sessao
		request.getSession().removeAttribute("announce");
		request.getSession().removeAttribute("idCreateItems");
		
		return "page.jsp?id=762"; //page form Register Auction 
	}
	
	@RequestMapping(value={"/registerAnnounce/store/exchange"})
	public String announceStoreExchange(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		log.info(request.getRequestURL());
		//remove atributos da sessao
		request.getSession().removeAttribute("announce");
		request.getSession().removeAttribute("idCreateItems");
		
		return "page.jsp?id=763"; //page form Register Exchange 
	}
	
	@RequestMapping(value={"/registerAnnounce/store/purchase"})
	public String announceStorePurchase(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		log.info(request.getRequestURL());
		//remove atributos da sessao
		request.getSession().removeAttribute("announce");
		request.getSession().removeAttribute("idCreateItems");
		
		return "page.jsp?id=774"; //page form Register Purchase 
	}
	
	@RequestMapping(value={"/registerAnnounce/store/payment"})
	public String announceStorePayment(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		log.info(request.getRequestURL());
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		
		AnnounceDAO announceDAO = new AnnounceDAO(db, request);
		StoreItemDAO storeItemDAO = new StoreItemDAO(db, request);
		HttpSession session = request.getSession();
		
		
		Announce announce = (Announce) cms.getObjectOfParameter(Announce.class); 	//popula um objeto com dados do form
		Object[] itemsPhotos = cms.getObjectsFileOfParameter(StoreItem.class); 		//popula vetor de objetos quando há um ou varios input "file"
		announce.setStatus("Pending pay");											//pendente pagamento
		announce.setRating("0");
		
		String idCreated = "";
		if(announce.getTypeAnnounce().equals("Purchase")){
			if(session.getAttribute("announce") == null){
				idCreated = announceDAO.create(announce);	//salva novo anuncio no bd
			}else{
				announceDAO.update(announce, false);		//salva edicao do anuncio no bd
				idCreated = announce.getIdAnnounce();
			}
		}else{
			//Sell, Auction, Exchange
			ModelILiketo model = new ModelILiketo(request, response);
			try {
				cms.processFileuploadImages(itemsPhotos);								//salva arquivos
			} catch (StorageILiketoException e) {
				//return msg erro nao possui espaco de armazenamento suficiente
				model.addMessageError("freeSpace", "You do not have enough free space, needed " +cms.getSizeFilesInBytes()/1024+ " KB.");	//msg erro
				return model.redirectError("/ilt/registerAnnounce/store");
			} catch (ImageILiketoException e) {
				//return msg erro formato de imagem invalido
				model.addMessageError("imageFormat", "Upload only Image in jpg format."); 													//msg erro
				return model.redirectError("/ilt/registerAnnounce/store");
			}
			announce.setPathPhotoAd(((StoreItem)itemsPhotos[0]).getPhotoStoreItem());	//seta foto principal			
			if(session.getAttribute("announce") == null){
				idCreated = announceDAO.create(announce);	//salva novo anuncio no bd
			}else{
				announceDAO.update(announce, false);		//salva edicao do anuncio no bd
				idCreated = announce.getIdAnnounce();
				String[] items = (String[])session.getAttribute("idCreateItems");
				if(items != null){
					for(String i : items){
						storeItemDAO.deleteItem(i);		//deleta fotos items da loja para atualizar as novas
					}
				}
			}
			for(Object item : itemsPhotos){
				((StoreItem)item).setIdAnnounce(idCreated); 							//seta fk_announce_id
			}		
			String[] idCreateItems = storeItemDAO.creates(itemsPhotos);				//salva fotos item loja
			session.setAttribute("idCreateItems", idCreateItems);
		}		
		log.info("Anuncio cadastrado com sucesso - Id: " + idCreated + " Status: Pendente pagamento!");
		
		//set announce session
		announce.setId(idCreated);
		announce.setIdAnnounce(idCreated);
		session.setAttribute("announce", announce);
		
		//model view jsp para binding do bean
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("announce", announce);
		
		
		return "page.jsp?id=800"; //page form payment
	}
	
	/*
	@RequestMapping(value={"/registerAnnounce/store/addAnnounce"})
	public String announceStoreaddAnnounce(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		log.info(request.getRequestURL());
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		HttpSession session = request.getSession();		
		Announce announce = (Announce) session.getAttribute("announce");	//recupera anuncio da sessao
		session.removeAttribute("announce");								//remove da sessao
		
		log.info("Anuncio de loja cadastrado com sucesso!");
		
		/**
		//cria notificacao para o grupo da categoria
		String idCategory = announce.getIdCategory();
		if(idCategory != null && !idCategory.equals("")){
			String myUserid = (String) request.getSession().getAttribute("userid");
			NotificationService.createNotification(db, idCategory, "announce", announce.getIdAnnounce(), Str.INCLUDED, myUserid);
			if(announce.getTypeAnnounce().equals("Auction")){
				//notificacao aviso uma hora antes leilao
				NotificationService.createNotificationAuctionOneHour(db, idCategory, "announce", announce.getIdAnnounce(), Str.AUCTION_HOUR, myUserid, announce.getDateInitial());
			}
		}
		
		
		return "redirect:/ilt/ads?id=" + announce.getIdAnnounce(); 			//success - page anuncio criado
	}
	*/
	
	@RequestMapping(value={"/ajax/negotiateBuy"})
	public void announceStoreaNegotiateBuy(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		log.info("ajax " + request.getRequestURI());
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		AnnounceDAO dao = new AnnounceDAO(db, request);
		
		String id = request.getParameter("id");								//id do anuncio
		Announce announce = (Announce) dao.readById(id, Announce.class);	//ler anuncio
		
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("announce", announce);							//add objeto escopo request
		
		
		//retorna lista de entrada, faz binding do objeto setado no ModelILiketo
		String div = cms.getPageBinding("765");								//id List Group - Store Negotiate Buy Entry

		response.getWriter().write(div);
	}
		
}
