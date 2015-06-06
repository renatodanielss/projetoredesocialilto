package com.iliketo.control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.AnnounceDAO;
import com.iliketo.dao.StoreItemDAO;
import com.iliketo.model.Announce;
import com.iliketo.model.StoreItem;
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
		
		AnnounceDAO announceDAO = new AnnounceDAO(db, request, response);
		StoreItemDAO storeItemDAO = new StoreItemDAO(db, request, response);
		
		long sizeFiles = cms.getSizeFilesInBytes(request); //tamanho todos arquivos
		System.out.println("sizeFiles = " + (sizeFiles>0?sizeFiles/1024:0) + " KB - " + sizeFiles + " bytes");
		
		
		Announce announce = (Announce) cms.getObjectOfParameter(Announce.class, request); 	//popula um objeto com dados do form
		Object[] itemsPhotos = cms.getObjectsFileOfParameter(StoreItem.class, request); 	//popula vetor de objetos quando há um ou varios input "file"
		
		
		if(announce.getTypeAnnounce().equals("Purchase")){
			announceDAO.create(announce);												//salva anuncio
		}else{
			//Sell, Auction, Exchange
			cms.processFileupload(itemsPhotos, request); 								//salva arquivos
			announce.setPathPhotoAd(((StoreItem)itemsPhotos[0]).getPhotoStoreItem());	//seta foto principal			
			String idCreated = announceDAO.create(announce);							//salva anuncio
			for(Object item : itemsPhotos){
				((StoreItem)item).setIdAnnounce(idCreated); 							//seta fk_announce_id
			}		
			storeItemDAO.creates(itemsPhotos);											//salva fotos item loja
		}		
		
		return "page.jsp?id=659"; //page form payment
	}
	
	@RequestMapping(value={"/registerAnnounce/store/confirm"})
	public String announceStoreConfirm(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @AnnounceStoreController url='/registerAnnounce/store/confirm'");
		
		return "page.jsp?id=660"; //page confirm
	}
	
	@RequestMapping(value={"/registerAnnounce/store/addAnnounce"})
	public String announceStoreaddAnnounce(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @AnnounceStoreController url='/registerAnnounce/store/addAnnounce'");
		
		return "redirect:page.jsp?id=160"; //page success
	}
	
	
	@RequestMapping(value={"/ajax/negotiateBuy"})
	public void announceStoreaNegotiateBuy(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "ajax @AnnounceStoreController url='/ajax/negotiateBuy'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		AnnounceDAO dao = new AnnounceDAO(db, request, response);
		
		String id = request.getParameter("id");								//id do anuncio
		Announce announce = (Announce) dao.readById(id, Announce.class);	//ler anuncio
		
		ModelILiketo model = new ModelILiketo(request);
		model.addAttribute("announce", announce);							//add objeto escopo request
		
		
		//retorna lista de entrada, faz binding do objeto setado no ModelILiketo
		String div = cms.getPageBinding("765");								//id List Group - Store Negotiate Buy Entry

		response.getWriter().write(div);
	}
		
}
