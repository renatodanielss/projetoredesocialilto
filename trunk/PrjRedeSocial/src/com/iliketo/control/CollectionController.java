package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.CollectionDAO;
import com.iliketo.dao.IliketoDAO;
import com.iliketo.exception.ImageILiketoException;
import com.iliketo.exception.StorageILiketoException;
import com.iliketo.model.Collection;
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
	
	
	@RequestMapping(value={"/collection/edit"})
	public String editCollection(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CollectionController url='/collection/edit'");
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CollectionDAO collectionDAO = new CollectionDAO(db, request);
		
		String id = request.getParameter("id");													//id colecao
		Collection collection = (Collection) collectionDAO.readById(id, Collection.class);		//ler colecao
		
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("collection", collection);	//dados atual da colecao

		return "page.jsp?id=45";						//page form edit collection
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
			return "redirect:/ilt/collection/edit?id=" + collection.getIdCollection(); 	//page form edit collection
		} catch (ImageILiketoException e) {
			model.addAttribute("collection", collection);
			model.addMessageError("imageFormat", "Upload only Image in jpg format.");
			return "redirect:/ilt/collection/edit?id=" + collection.getIdCollection(); 	//page form edit collection
		}
		
		collectionDAO.update(collection);												//atualiza colecao
		
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

}
