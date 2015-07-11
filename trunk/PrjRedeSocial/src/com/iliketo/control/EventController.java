package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.EventDAO;
import com.iliketo.exception.ImageILiketoException;
import com.iliketo.exception.StorageILiketoException;
import com.iliketo.model.Event;
import com.iliketo.service.NotificationService;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class EventController {
	
	
	
	@RequestMapping(value={"/event/newEvent"})
	public String addVideo(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @EventController url='/event/createEvent'");
		if(ModelILiketo.validateAndProcessError(request)){
			//valida e mostra error na pagina
			System.out.println("Log - " + "Erro ao criar um evento. Mostrar Tela formulario Create new event");
		}
		
		return "page.jsp?id=649";	//page form Create new event
	}
	
	
	@RequestMapping(value={"/event/createEvent"})
	public String createEvent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @EventController url='/event/createEvent'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		EventDAO eventDAO = new EventDAO(db, request);
		
		Event event = (Event) cms.getObjectOfParameter(Event.class);	//objeto com dados do form
		
		ModelILiketo model = new ModelILiketo(request, response);
		try {
			cms.processFileuploadImage(event);							//salva arquivos			
		} catch (StorageILiketoException e) {			
			model.addAttribute("event", event);
			model.addMessageError("freeSpace", "You do not have enough free space, needed " +cms.getSizeFilesInBytes()/1024+ " KB.");	//msg erro
			return model.redirectError("/ilt/event/newEvent");			//page form create event
		} catch (ImageILiketoException e) {
			model.addAttribute("event", event);
			model.addMessageError("imageFormat", "Upload only Image in jpg format."); 	//msg erro
			return model.redirectError("/ilt/event/newEvent");			//page form create event
		}
		
		String idCreate = eventDAO.create(event);						//cria evento
		
		//cria notificacao para o grupo da categoria
		String idCategory = event.getIdCategory();
		if(idCategory != null && !idCategory.equals("")){
			String myUserid = (String) request.getSession().getAttribute("userid");
			NotificationService.createNotification(db, idCategory, "event", idCreate, Str.INCLUDED, myUserid);
		}
		
		return "redirect:/page.jsp?id=623&group=" + event.getIdGroup();	//success event group
	}
	
	

}
