package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.AnnounceDAO;
import com.iliketo.dao.CollectionDAO;
import com.iliketo.dao.IliketoDAO;
import com.iliketo.dao.ItemDAO;
import com.iliketo.exception.ImageILiketoException;
import com.iliketo.exception.StorageILiketoException;
import com.iliketo.model.Announce;
import com.iliketo.model.Collection;
import com.iliketo.model.Item;
import com.iliketo.service.NotificationService;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class ItemController {
	
	
	static final Logger log = Logger.getLogger(ItemController.class);
	
	/**
	 * Método intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
	
	/**
	 * Redireciona pagina para visualizar meu item ou item de outro colecionador
	 */
	@RequestMapping(value={"/item/view"})
	public String itemView(HttpServletRequest request, HttpServletResponse response){
		
		log.info(LogUtilsILiketoo.getUsername(request) + request.getRequestURL());
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);						//db
		ItemDAO dao = new ItemDAO(db, request);										//dao
		
		String idItem = request.getParameter("id"); 								//id do item
		String myIdUser = (String) request.getSession().getAttribute("userid"); 	//my userid
		Item item = (Item) dao.readById(idItem, Item.class);						//ler item
		
		if(item != null){	
			ModelILiketo model = new ModelILiketo(request, response);
			model.addAttribute("item", item);
			if(myIdUser.equals(item.getIdMember())){
				return "/page.jsp?id=594";			//page visualizar my item
			}else{
				return "/page.jsp?id=595";			//page visualizar item terceiro
			}			
		}else{
			log.info("Item nao encontrado ou indisponivel!");
			return "/page.jsp?id=invalid";			//invalid page
		}
		
	}
	
	
	@RequestMapping(value={"/item/addItems"})
	public String addItems(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);						//db
		String myUserid = (String) request.getSession().getAttribute("userid");
		String idCollection = (String) request.getParameter("idCol");
		CollectionDAO dao = new CollectionDAO(db, request);
		Collection colecao = (Collection) dao.readById(idCollection, Collection.class);
		
		//valida colecao
		if(colecao != null && colecao.getIdMember().equals(myUserid)){		
			if(ModelILiketo.validateAndProcessError(request)){
				//valida e mostra error na pagina
				log.warn("Erro ao adicionar itens. Tela formulario add mais itens");
			}
			return "page.jsp?id=596&idCol=" + idCollection;	//page form add more items
		}else{
			return "page.jsp?id=invalid";	//invalid page
		}
 	}
	
	@RequestMapping(value={"/item/createItems"})
	public String createItems(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		ItemDAO itemDAO = new ItemDAO(db, request);
		String idCollection = (String) request.getParameter("idCol");
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		Object[] items  = cms.getObjectsFileOfParameter(Item.class);				//array objetos com os items
		for(Object item : items){
			((Item)item).setIdCollection(idCollection);				//seta fk_collection_id no item
			((Item)item).setIdMember(myUserid);						//seta fk_user_id no item
		}
		
		ModelILiketo model = new ModelILiketo(request, response);
		try {
			cms.processFileuploadImages(items);							//salva arquivos			
		} catch (StorageILiketoException e) {
			model.addMessageError("freeSpace", "You do not have enough free space, needed " +cms.getSizeFilesInBytes()/1024+ " KB.");	//msg erro
			return model.redirectError("/ilt/item/addItems?idCol=" + idCollection);				//page form add more item
		} catch (ImageILiketoException e) {
			model.addMessageError("imageFormat", "Upload only Image in jpg format."); 													//msg erro
			return model.redirectError("/ilt/item/addItems?idCol=" + idCollection);				//page form add more item
		}
		
		String[] idCreates = itemDAO.creates(items);											//cria items
		
		//cria notificacao para o grupo da categoria
		String idCategory = IliketoDAO.getValueOfDatabase(db, "fk_category_id", "dbcollection", "id_collection", idCollection);
		if(idCategory != null && !idCategory.equals("")){
			for(String id : idCreates){
				NotificationService.createNotification(db, idCategory, "item", id, Str.INCLUDED, myUserid);
			}
		}
		
		return "redirect:/ilt/collection/profile?id=" + idCollection;	//success
	}
	
	
	@RequestMapping(value={"/item/edit"})
	public String editItem(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		ItemDAO itemDAO = new ItemDAO(db, request);
		
		String id = request.getParameter("id");									//id item
		Item item = (Item) itemDAO.readById(id, Item.class);					//ler item
		
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("item", item);			//dados atual do item
		
		return "page.jsp?id=805";					//page form edit item
 	}
	
	@RequestMapping(value={"/item/save"})
	public String saveItem(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		ItemDAO itemDAO = new ItemDAO(db, request);
		
		Item item  = (Item) cms.getObjectOfParameter(Item.class);		//objeto com dados do form
		
		itemDAO.update(item, false);									//atualiza item
		
		//verifica e atualiza anuncio
		AnnounceDAO dao = new AnnounceDAO(db, request);
		Announce a = (Announce) dao.readByColumn("fk_item_id", item.getIdItem(), Announce.class);
		if(a != null && a.getId() != null && !a.getId().equals("")){
			a.setTitle(item.getTitle());
			a.setDescription(item.getDescription());
			dao.update(a, false);
		}
				
		String idCollection = item.getIdCollection();
		
		//cria notificacao para o grupo da categoria
		String idCategory = IliketoDAO.getValueOfDatabase(db, "fk_category_id", "dbcollection", "id_collection", idCollection);
		if(idCategory != null && !idCategory.equals("")){
			NotificationService.createNotification(db, idCategory, "item", item.getIdItem(), Str.UPDATED, item.getIdMember());
		}
		
		return "redirect:/ilt/collection/profile?id=" + idCollection;	//success
		
	}
	
	
	@RequestMapping(value={"/item/delete"})
	public String deleteItem(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		ItemDAO itemDAO = new ItemDAO(db, request);
		
		String id = request.getParameter("id");
		itemDAO.deleteItem(id);					//delete item
		
		return "redirect:/page.jsp?id=48";		//success
	}

}
