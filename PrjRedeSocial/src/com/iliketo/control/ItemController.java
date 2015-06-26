package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.ItemDAO;
import com.iliketo.exception.ImageILiketoException;
import com.iliketo.exception.StorageILiketoException;
import com.iliketo.model.Item;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class ItemController {
	
	
	@RequestMapping(value={"/item/addItems"})
	public String addItems(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @ItemController url='/item/addItems'");
		
		return "page.jsp?id=654";	//page form add more items
 	}
	
	@RequestMapping(value={"/item/createItems"})
	public String createItems(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @ItemController url='/item/createItems'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		ItemDAO itemDAO = new ItemDAO(db, request);
		
		Item[] items  = (Item[]) cms.getObjectOfParameter(Item.class);	//objeto com dados do form
		
		ModelILiketo model = new ModelILiketo(request, response);
		try {
			cms.processFileuploadImages(items);							//salva arquivos			
		} catch (StorageILiketoException e) {
			model.addMessageError("freeSpace", "You do not have enough free space, needed " +cms.getSizeFilesInBytes()/1024+ " KB.");	//msg erro
			return model.redirectError("/ilt/addItems");		//page form add more item
		} catch (ImageILiketoException e) {
			model.addMessageError("imageFormat", "Upload only Image in jpg format."); 													//msg erro
			return model.redirectError("/ilt/addItems");		//page form add more item
		}
		
		itemDAO.creates(items);				//cria items
		
		return "redirect:/page.jsp?id=48";			//success
	}
	
	
	@RequestMapping(value={"/item/edit"})
	public String editItem(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @ItemController url='/item/edit'");
		
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
		
		System.out.println("Log - " + "request @ItemController url='/item/save'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		ItemDAO itemDAO = new ItemDAO(db, request);
		
		Item item  = (Item) cms.getObjectOfParameter(Item.class);	//objeto com dados do form
		
		itemDAO.update(item);			//atualiza item
		
		return "redirect:/page.jsp?id=48";		//success
		
	}
	
	
	@RequestMapping(value={"/item/delete"})
	public String deleteItem(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @ItemController url='/item/delete'");
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		ItemDAO itemDAO = new ItemDAO(db, request);
		
		String id = request.getParameter("id");
		itemDAO.deleteItem(id);					//delete item
		
		return "redirect:/page.jsp?id=48";		//success
	}

}
