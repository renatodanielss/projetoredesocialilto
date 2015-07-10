package com.iliketo.control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.AnnounceDAO;
import com.iliketo.dao.CollectionDAO;
import com.iliketo.dao.ItemDAO;
import com.iliketo.dao.UserCardDAO;
import com.iliketo.model.Announce;
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
			
		
		return "redirect:/ads.jsp?id=" + idCreated; 	//success - page anuncio criado
	}
		
}
