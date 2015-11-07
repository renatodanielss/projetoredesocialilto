package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.CollectionDAO;
import com.iliketo.dao.IliketoDAO;
import com.iliketo.dao.InterestDAO;
import com.iliketo.dao.ItemDAO;
import com.iliketo.exception.ImageILiketoException;
import com.iliketo.exception.StorageILiketoException;
import com.iliketo.model.Collection;
import com.iliketo.model.Interest;
import com.iliketo.model.Item;
import com.iliketo.service.NotificationService;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class CollectionController {
	
	/**
	 * Redireciona para perfil da minha colecao ou outro colecionador
	 */
	@RequestMapping(value={"/collection/profile"})
	public String collectorProfile(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CollectionController url='/collection/profile'");
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);						//db
		HttpSession session = request.getSession();									//session
		
		String myIdUser = (String) request.getSession().getAttribute("userid"); 	//my userid
		String IdCollection = request.getParameter("id"); 							//id da colecao
		String IDMember = IliketoDAO.getValueOfDatabase(db, "fk_user_id", "dbcollection", "id_collection", IdCollection);

		
		//valida membro dono da colecao
		if(myIdUser.equals(IDMember)){
			session.setAttribute(Str.S_ID_COLLECTION, IdCollection);	//seta na session o id da coleção
			return "page.jsp?id=505";									//page collection profile
		}else{
			session.setAttribute(Str.S_ID_COLLECTOR, IdCollection);		//seta na session o id da coleção
			session.setAttribute(Str.S_ID_MEMBER_COLLECTOR, IDMember);	//seta na session id membro dono da colecao
			return "page.jsp?id=529";									//page collection profile terceiro
		}
	}
	
	
	@RequestMapping(value={"/collection/registerCollection"})
	public String registerCollection(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CollectionController url='/collection/registerCollection'");
		
		if(ModelILiketo.validateAndProcessError(request)){
			//valida e mostra error na pagina
			System.out.println("Log - " + "Erro ao adicionar colecao. Tela formulario registrar nova colecao");
		}else{			
			request.getSession().removeAttribute("newCollection");		//limpa colecao da session
			System.out.println("Log - " + "Tela formulario registrar nova colecao");		
		}		
		return "page.jsp?id=410";		//page form register collection
		
	}
	
	@RequestMapping(value={"/collection/createCollection"})
	public String createCollection(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CollectionController url='/collection/createCollection'");
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);				//db
		HttpSession session = request.getSession();							//session
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);		//cms
		CollectionDAO collectionDAO = new CollectionDAO(db, request);		//dao
		
		Collection collection = (Collection) cms.getObjectOfParameter(Collection.class); 	//objeto com dados do form
		ModelILiketo model = new ModelILiketo(request, response);
		
		try {			
			cms.processFileuploadImage(collection);					//salva arquivos
			String idCreated = "";
			if(session.getAttribute("newCollection") == null){
				idCreated = collectionDAO.create(collection);		//create
				collection.setIdCollection(idCreated);
				System.out.println("Log - " + "Create colecao - name: " + collection.getNameCollection());
			}else{
				idCreated = ((Collection) session.getAttribute("newCollection")).getIdCollection();
				collection.setIdCollection(idCreated);
				collectionDAO.update(collection, true);					//update, exemplo: back da pagina ou refresh
				System.out.println("Log - " + "Update colecao - name: " + collection.getNameCollection());
			}
			session.setAttribute("newCollection", collection);							//add nova colecao na session, evitar duplicidade
			session.setAttribute(Str.S_COLLECTION, collection.getNameCollection());		//add nome coleção na session	
			session.setAttribute(Str.S_ID_COLLECTION, collection.getIdCollection());	//add id coleção na session	
			
			return "redirect:/ilt/collection/registerCollection/addItems";				//page form add new items
			
		} catch (StorageILiketoException e) {
			model.addAttribute("collection", collection);
			model.addMessageError("freeSpace", "You do not have enough free space, needed " +cms.getSizeFilesInBytes()/1024+ " KB.");
			return model.redirectError("/ilt/collection/registerCollection"); 			//page form register collection
		} catch (ImageILiketoException e) {
			model.addAttribute("collection", collection);
			model.addMessageError("imageFormat", "Upload only Image in jpg format.");
			return model.redirectError("/ilt/collection/registerCollection"); 			//page form register collection
		}
		
	}

	@RequestMapping(value={"/collection/registerCollection/addItems"})
	public String addNewItems(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CollectionController url='/collection/registerCollection/addItems'");
		
		if(ModelILiketo.validateAndProcessError(request)){
			//valida e mostra error na pagina
			System.out.println("Log - " + "Erro ao adicionar novos itens. Tela formulario registrar novos itens da colecao");
		}else{
			System.out.println("Log - " + "Tela formulario registrar novos itens da colecao");
			ModelILiketo model = new ModelILiketo(request, response);
			model.addAttribute("collection", request.getSession().getAttribute("newCollection"));	//model na view jsp
		}
		return "page.jsp?id=90";		//page form add new itens
		
	}
	
	@RequestMapping(value={"/collection/registerCollection/createItems"})
	public String createItems(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @ItemController url='/collection/registerCollection/createItems'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		HttpSession session = request.getSession();							//session
		ItemDAO itemDAO = new ItemDAO(db, request);
		Collection collection = (Collection) session.getAttribute("newCollection");
		
		Object[] items  = cms.getObjectsFileOfParameter(Item.class);				//array objetos com os items
		for(Object item : items){
			((Item)item).setIdCollection(collection.getIdCollection());				//seta fk_collection_id no item
			((Item)item).setIdMember(collection.getIdMember());						//seta fk_user_id no item
		}
		
		ModelILiketo model = new ModelILiketo(request, response);
		try {
			cms.processFileuploadImages(items);								//salva arquivos			
		} catch (StorageILiketoException e) {
			model.addMessageError("freeSpace", "You do not have enough free space, needed " +cms.getSizeFilesInBytes()/1024+ " KB.");	//msg erro
			return model.redirectError("/ilt/collection/registerCollection/addItems");	//page form add new item
		} catch (ImageILiketoException e) {
			model.addMessageError("imageFormat", "Upload only Image in jpg format."); 	//msg erro
			return model.redirectError("/ilt/collection/registerCollection/addItems");	//page form add new item
		}
		
		itemDAO.creates(items);							//cria items
		session.removeAttribute("newCollection");		//limpa colecao da session
		
		return "redirect:/page.jsp?id=474";	//page choose category
	}
	
	
	@RequestMapping(value={"/collection/edit"})
	public String editCollection(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CollectionController url='/collection/edit'");
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CollectionDAO collectionDAO = new CollectionDAO(db, request);
		
		if(ModelILiketo.validateAndProcessError(request)){	
			//valida e mostra error na pagina
			System.out.println("Log - " + "Erro ao salvar atualizacao da colecao. Tela formulario editar colecao");
		}else{
			String id = request.getParameter("id");													//id colecao
			Collection collection = (Collection) collectionDAO.readById(id, Collection.class);		//ler colecao
			
			ModelILiketo model = new ModelILiketo(request, response);
			model.addAttribute("collection", collection);	//dados atual da colecao
		}

		return "page.jsp?id=806";							//page form edit collection
	}
	
	
	@RequestMapping(value={"/collection/save"})
	public String saveCollection(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @CollectionController url='/collection/save'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		CollectionDAO collectionDAO = new CollectionDAO(db, request);
		
		Collection collection = (Collection) cms.getObjectOfParameter(Collection.class);	//objeto com dados do form
		
		ModelILiketo model = new ModelILiketo(request, response);
		try {
			cms.processFileuploadImage(collection);											//salva arquivos
		} catch (StorageILiketoException e) {
			model.addAttribute("collection", collection);
			model.addMessageError("freeSpace", "You do not have enough free space, needed " +cms.getSizeFilesInBytes()/1024+ " KB.");
			return model.redirectError("/ilt/collection/edit?id=" + collection.getIdCollection()); 	//page form edit collection
		} catch (ImageILiketoException e) {
			model.addAttribute("collection", collection);
			model.addMessageError("imageFormat", "Upload only Image in jpg format.");
			return model.redirectError("/ilt/collection/edit?id=" + collection.getIdCollection()); 	//page form edit collection
		}
		
		collectionDAO.update(collection, true);												//atualiza colecao
		
		//cria notificacao para o grupo da categoria
		String idCategory = collection.getIdCategory();
		if(idCategory != null && !idCategory.equals("")){
			String myUserid = (String) request.getSession().getAttribute("userid");
			NotificationService.createNotification(db, idCategory, "collection", collection.getIdCollection(), Str.UPDATED, myUserid);
		}
		
		return "redirect:/ilt/collection/profile?id=" + collection.getIdCollection();	//success
	}
	
	@RequestMapping(value={"/collection/delete"})
	public String deleteCollection(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @CollectionController url='/collection/delete'");
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CollectionDAO collectionDAO = new CollectionDAO(db, request);
		
		String id = request.getParameter("id");	//id colecao
		collectionDAO.deleteCollection(id);		//delete colecao
		
		return "redirect:/page.jsp?id=48";		//success
	}
	
	@RequestMapping(value={"/collection/participateCategory"})
	public String participateCategory(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @CollectionController url='/collection/participateCategory'");
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CollectionDAO dao = new CollectionDAO(db, request);
		
		String idCollection = request.getParameter("idCollection");
		String idCategory = request.getParameter("idCat");
		String nameCategory = request.getParameter("nameCat");
		Collection collection = (Collection) dao.readById(idCollection, Collection.class);	//ler colecao
		
		if(!collection.getIdCategory().equals(idCategory)){		//valida se colecao pertence a categoria
			//entra para categoria
			collection.setIdCategory(idCategory);
			collection.setNameCategory(nameCategory);
			collection.setDescription(null);
			//por padrao todas notificacoes sao ativadas
			collection.setNotificCollection("Activate");
			collection.setNotificItem("Activate");
			collection.setNotificVideo("Activate");
			collection.setNotificEvent("Activate");
			collection.setNotificAnnounce("Activate");
			collection.setNotificTopic("Activate");
			collection.setNotificComment("Activate");
			
			dao.update(collection, false);								//atualiza colecao
			System.out.println("Log - Coleção: '"+ collection.getNameCollection() +"' entrou para a categoria/grupo: " + nameCategory);
			
			//cria notificacao para o grupo da categoria
			String myUserid = (String) request.getSession().getAttribute("userid");
			NotificationService.createNotification(db, idCategory, "collection", idCollection, Str.JOINED, myUserid);
			
			//remove interesse do membro no grupo se houver
			for( Interest i : new InterestDAO(db, null).listInterestByUser((String) request.getSession().getAttribute("userid")) ){
				if(i.getIdCategory() != null && i.getIdCategory().equals(idCategory)){				
					new InterestDAO(db, null).deleteInterest(i.getIdInterest());				//exclui interesse
					System.out.println("Log - Interesse: '"+ i.getIdInterest() +"' removido da categoria/grupo: " + nameCategory);
				}
			}
		}
		
		return "redirect:/ilt/groupCategory?idCat=" + idCategory + "&cat=" + nameCategory; 		//sucess page category of group
	}
	
	@RequestMapping(value={"/collection/ajaxParticipateCategory"})
	public void participateAndLeaveCategory(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @CollectionController url='/collection/ajaxParticipateCategory'");
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CollectionDAO dao = new CollectionDAO(db, request);
		
		String idCollection = request.getParameter("idCollection");
		String idCategory = request.getParameter("idCat");
		String nameCategory = request.getParameter("nameCat");
		String resultButton = "";
		
		Collection collection = (Collection) dao.readById(idCollection, Collection.class);		
		
		if(!collection.getIdCategory().equals(idCategory)){		//valida se colecao pertence a categoria
			//entra para categoria
			collection.setIdCategory(idCategory);
			collection.setNameCategory(nameCategory);
			dao.update(collection, false);						//update categoria da colecao
			System.out.println("Log - Coleção: '"+ idCollection +"' entrou para a categoria/grupo: " + nameCategory);
			resultButton = "Leave category";
			
			//cria notificacao para o grupo da categoria
			String myUserid = (String) request.getSession().getAttribute("userid");
			NotificationService.createNotification(db, idCategory, "collection", idCollection, Str.JOINED, myUserid);
			
		}else{
			//sai da categoria
			collection.setIdCategory("");
			collection.setNameCategory("");
			dao.update(collection, false);						//update categoria da colecao
			System.out.println("Log - Coleção: '"+ idCollection +"' saiu da categoria/grupo: " + nameCategory);
			resultButton = "Participate";
		}
		
		response.getWriter().write(resultButton);				//retorna ajax com valor do botao
	}

}
