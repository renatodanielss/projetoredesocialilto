package com.iliketo.control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.CollectionDAO;
import com.iliketo.model.Collection;
import com.iliketo.model.Member;
import com.iliketo.service.NotificationService;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class NotificationController {
	
	
	/**
	 * Redireciona para pagina configuracao de notificacao da colecao
	 */
	@RequestMapping(value={"/notification/collection/settings"})
	public String pageNoticiationsSettings(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @VideoController url='/notification/collection/settings'");
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);				//db
		CollectionDAO dao = new CollectionDAO(db, request);					//dao
		
		String idCol = request.getParameter("id");		
		Collection collection = (Collection) dao.readById(idCol, Collection.class);	//ler dados colecao
		
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("collection", collection);	//set model recuperar na view jsp
		
		return "page.jsp?id=807";						//page Collection notifications 
	}
	
	/**
	 * Salva configuracoes da notificacao
	 */
	@RequestMapping(value={"/notification/collection/saveNotifications"})
	public String saveCollectionNotifications(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @VideoController url='/notification/collection/saveNotifications");
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);				//db
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);		//cms
		CollectionDAO dao = new CollectionDAO(db, request);					//dao
		
		Collection collection = (Collection) cms.getObjectOfParameter(Collection.class); //objeto dados do form html		
		
		dao.updateNotificationSettings(collection);				//atualiza dados		
		System.out.println("Log - " + "Configuracoes de notificacoes salvas - id colecao: " + collection.getIdCollection());
		
		return "redirect:/ilt/collection/profile?id=" + collection.getIdCollection();	 //success page collection profile
	}
	
	/**
	 * Retorna o total de notificacao nao lida pelo ajax
	 */
	@RequestMapping(value={"/notification/ajaxTotalNotifications"})
	public void totalNotification(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @VideoController url='/notification/ajaxTotalNotifications'");
		
		String totalNews = Integer.toString(NotificationService.totalNotifications(request));
		System.out.println("\nTotal novas notificacoes: " + totalNews + "\n");
		
		response.getWriter().write(totalNews);				//retorna total notificacao ajax
	}
	
	/**
	 * Retorna uma lista de historico de notificacao pelo ajax
	 */
	@RequestMapping(value={"/notification/ajaxListNotifications"})
	public void notificationHistoric(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @VideoController url='/notification/ajaxListNotifications'");
		
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);			//cms		
		Member member = (Member) request.getSession().getAttribute("member");	//member session
		
		String div = NotificationService.listHistoricNotification(cms, member, "809");	//page list entry notification template
		
		response.getWriter().write(div);				//retorna ajax lista hostorico notificacao 		
	}
	
	/**
	 * Redireciona para pagina todas notificacoes - page more notifications
	 */
	@RequestMapping(value={"/notifications"})
	public String pageMoreNotificationHistoric(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @VideoController url='/notifications'");
		
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);			//cms		
		Member member = (Member) request.getSession().getAttribute("member");	//member session
		
		String div = NotificationService.listHistoricNotification(cms, member, "808");	//page more notifications
		
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("listMoreNotificationsEntry", div);		//recuperar historico na view jsp
		
		return "page.jsp?id=736";									//page more notifications
	}
	
	

}
