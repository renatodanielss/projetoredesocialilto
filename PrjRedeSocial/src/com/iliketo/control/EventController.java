package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.EventDAO;
import com.iliketo.dao.IliketoDAO;
import com.iliketo.exception.ImageILiketoException;
import com.iliketo.exception.StorageILiketoException;
import com.iliketo.model.Event;
import com.iliketo.service.NotificationService;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class EventController {
	
	/**
	 * Redireciona pagina para visualizar meu event ou event de outro colecionador
	 */
	@RequestMapping(value={"/event/view"})
	public String eventView(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @EventController url='/event/view'");
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);			//db
		EventDAO dao = new EventDAO(db, request);						//dao
		
		String idEvent = request.getParameter("id");
		String myIdUser = (String) request.getSession().getAttribute("userid");
		Event event = (Event) dao.readById(idEvent, Event.class);
		
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("event", event);
		
		//valida membro proprietario do evento
		if(myIdUser.equals(event.getIdMember())){
			return "/page.jsp?id=737";					//page visualizar my event
		}else{
			return "/page.jsp?id=875";					//page visualizar event of collector
		}
	}
	
	@RequestMapping(value={"/event/newEvent"})
	public String addEvent(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @EventController url='/event/newEvent'");
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
	
	
	@RequestMapping(value={"/event/edit"})
	public String editEvent(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @EventController url='/event/edit'");
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		EventDAO eventDAO = new EventDAO(db, request);
		
		String id = request.getParameter("id");
		Event event = (Event) eventDAO.readById(id, Event.class);
		
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("event", event);		//dados atual do event
				
		return "page.jsp?id=874";				//page form edit event
	}
	
	@RequestMapping(value={"/event/save"})
	public String saveEvent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @EventController url='/event/save'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		EventDAO eventDAO = new EventDAO(db, request);
		
		Event event = (Event) cms.getObjectOfParameter(Event.class);	//objeto com dados do form
		
		eventDAO.update(event, false);									//atualiza event
		
		//cria notificacao para o grupo da categoria
		String idCategory = event.getIdCategory();
		if(idCategory != null && !idCategory.equals("")){
			String myUserid = (String) request.getSession().getAttribute("userid");
			NotificationService.createNotification(db, idCategory, "event", event.getIdEvent(), Str.UPDATED, myUserid);
		}
		
		return "redirect:/ilt/event/view?id=" + event.getIdEvent();		//success
	}
	
	@RequestMapping(value={"/event/delete"})
	public String deleteEvent(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @EventController url='/event/delete'");
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		EventDAO eventDAO = new EventDAO(db, request);
		
		String id = request.getParameter("id");
		eventDAO.deleteEvent(id);				//delete event
		
		return "redirect:/page.jsp?id=739";		//success
	}
	

}
