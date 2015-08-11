package com.iliketo.control;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.AnnounceDAO;
import com.iliketo.dao.StoreItemDAO;
import com.iliketo.dao.UserCardDAO;
import com.iliketo.exception.ImageILiketoException;
import com.iliketo.exception.StorageILiketoException;
import com.iliketo.model.Announce;
import com.iliketo.model.StoreItem;
import com.iliketo.model.UserCard;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class AnnounceStoreController {

	@RequestMapping(value={"/registerAnnounce/store"})
	public String announceStoreChoose(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @AnnounceStoreController url='/registerAnnounce/store'");
		
		return "page.jsp?id=757"; //page form choose type announce
	}
	
	@RequestMapping(value={"/registerAnnounce/store/sale"})
	public String announceStoreSale(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @AnnounceStoreController url='/registerAnnounce/store/sale'");
		
		return "page.jsp?id=758"; //page form Register Sale 
		
	}
	
	@RequestMapping(value={"/registerAnnounce/store/auction"})
	public String announceStoreAuction(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @AnnounceStoreController url='/registerAnnounce/store/auction'");
		
		return "page.jsp?id=762"; //page form Register Auction 
	}
	
	@RequestMapping(value={"/registerAnnounce/store/exchange"})
	public String announceStoreExchange(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @AnnounceStoreController url='/registerAnnounce/store/exchange'");
		
		return "page.jsp?id=763"; //page form Register Exchange 
	}
	
	@RequestMapping(value={"/registerAnnounce/store/purchase"})
	public String announceStorePurchase(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @AnnounceStoreController url='/registerAnnounce/store/purchase'");
		
		return "page.jsp?id=774"; //page form Register Purchase 
	}
	
	@RequestMapping(value={"/registerAnnounce/store/payment"})
	public String announceStorePayment(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @AnnounceStoreController url='/registerAnnounce/store/payment'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		
		AnnounceDAO announceDAO = new AnnounceDAO(db, request);
		StoreItemDAO storeItemDAO = new StoreItemDAO(db, request);
		HttpSession session = request.getSession();
		
		
		Announce announce = (Announce) cms.getObjectOfParameter(Announce.class); 	//popula um objeto com dados do form
		Object[] itemsPhotos = cms.getObjectsFileOfParameter(StoreItem.class); 		//popula vetor de objetos quando há um ou varios input "file"
		
		String idCreated = "";
		if(announce.getTypeAnnounce().equals("Purchase")){
			idCreated = announceDAO.create(announce);												//salva anuncio
		}else{
			//Sell, Auction, Exchange
			if(announce.getTypeAnnounce().equals("Auction")){
				String dataInicialLeilao = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
				announce.setDateInitial(dataInicialLeilao);
			}
			try {
				cms.processFileuploadImages(itemsPhotos);								//salva arquivos
			} catch (StorageILiketoException e) {
				//return msg erro nao possui espaco de armazenamento suficiente
			} catch (ImageILiketoException e) {
				//return msg erro formato de imagem invalido
			}
			announce.setPathPhotoAd(((StoreItem)itemsPhotos[0]).getPhotoStoreItem());	//seta foto principal			
			idCreated = announceDAO.create(announce);									//salva anuncio
			for(Object item : itemsPhotos){
				((StoreItem)item).setIdAnnounce(idCreated); 							//seta fk_announce_id
			}		
			storeItemDAO.creates(itemsPhotos);											//salva fotos item loja
		}		
		//set announce session
		announce.setIdAnnounce(idCreated);
		session.setAttribute("announce", announce);
		
		//dados cartao do membro
		UserCardDAO userCardDAO = new UserCardDAO(db);		
		UserCard userCard = userCardDAO.readInfoCard((String) session.getAttribute("userid"));
		
		//model view jsp para binding do bean
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("userCard", userCard);
		
		return "page.jsp?id=800"; //page form payment
	}
	
	@RequestMapping(value={"/registerAnnounce/store/confirm"})
	public String announceStoreConfirm(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @AnnounceStoreController url='/registerAnnounce/store/confirm'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		UserCardDAO userCardDAO = new UserCardDAO(db);
		HttpSession session = request.getSession();	
		
		UserCard userCard = (UserCard) cms.getObjectOfParameter(UserCard.class);	//recupera objeto com dados do form
		
		userCardDAO.saveInfoCard(userCard);											//salva dados atualizados do cartao
		session.setAttribute("userCard", userCard);									//set dados cartao na session
		
		//model view jsp para binding do bean
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("userCard", userCard);
		model.addAttribute("announce", (Announce) session.getAttribute("announce"));
		
		return "page.jsp?id=801"; //page confirm
	}
	
	@RequestMapping(value={"/registerAnnounce/store/addAnnounce"})
	public String announceStoreaddAnnounce(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @AnnounceStoreController url='/registerAnnounce/store/addAnnounce'");
		
		HttpSession session = request.getSession();		
		Announce announce = (Announce) session.getAttribute("announce");	//recupera anuncio da sessao
		session.removeAttribute("announce");								//remove da sessao
		session.removeAttribute("userCard");								//remove da sessao
		
		System.out.println("Log - " + "Anuncio de loja cadastrado com sucesso!");
		
		return "redirect:/ilt/ads?id=" + announce.getIdAnnounce(); 			//success - page anuncio criado
	}
	
	
	@RequestMapping(value={"/ajax/negotiateBuy"})
	public void announceStoreaNegotiateBuy(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "ajax @AnnounceStoreController url='/ajax/negotiateBuy'");
		
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
