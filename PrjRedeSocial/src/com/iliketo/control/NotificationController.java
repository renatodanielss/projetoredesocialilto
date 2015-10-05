package com.iliketo.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.CollectionDAO;
import com.iliketo.dao.InterestDAO;
import com.iliketo.model.Collection;
import com.iliketo.model.Interest;
import com.iliketo.model.Member;
import com.iliketo.service.NotificationService;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class NotificationController {
	
	
	/**
	 * Redireciona para pagina configuracao de notificacao do grupo
	 */
	@RequestMapping(value={"/notification/groupCategory/settings"})
	public String pageNoticiationsSettingsGroup(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @NotificationController url='/notification/group/settings'");
		
		//db e dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		InterestDAO interestDAO = new InterestDAO(db, request);
		CollectionDAO collectionDAO = new CollectionDAO(db, request);
		
		String idCategory = request.getParameter("idCat");
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		
		//verifica se o membro possui uma colecao ou interesse no grupo (obs configuracoes de notificacoes sao vinculadas a colecao ou interesse q pertence ao grupo)
		List<Collection> listCollection = collectionDAO.listCollectionByUser(myUserid);
		List<Interest> listInterest = interestDAO.listInterestByUser(myUserid);
		
		for(Collection c : listCollection){
			if(c.getIdCategory() != null && c.getIdCategory().equals(idCategory)){
				ModelILiketo model = new ModelILiketo(request, response);
				model.addAttribute("collection", c);		//set model recuperar na view jsp
				return "page.jsp?id=807";					//page group notifications
			}
		}
		for(Interest i : listInterest){
			if(i.getIdCategory() != null && i.getIdCategory().equals(idCategory)){
				ModelILiketo model = new ModelILiketo(request, response);
				model.addAttribute("interest", i);			//set model recuperar na view jsp
				return "page.jsp?id=810";					//page group notifications
			}
		}
		
		return "page.jsp?id=703"; 	//membro sem acesso ao grupo, page join group of category
	}
	
	/**
	 * Salva configuracoes da notificacao do grupo no interesse
	 */
	@RequestMapping(value={"/notification/interest/saveNotifications"})
	public String saveInterestNotifications(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @NotificationController url='/notification/interest/saveNotifications");
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);				//db
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);		//cms
		InterestDAO dao = new InterestDAO(db, request);						//dao
		
		Interest interest = (Interest) cms.getObjectOfParameter(Interest.class);	 	//objeto dados do form html		
		
		dao.updateNotificationSettings(interest);			//atualiza dados
		System.out.println("Log - " + "Configuracoes de notificacoes salvas - id interesse: " + interest.getIdInterest());
		
		return "redirect:/ilt/groupCategory?idCat=" + interest.getIdCategory() + "&cat=" + interest.getNameCategory(); 	//sucess page category of group
	}
	
	/**
	 * Salva configuracoes da notificacao do grupo na colecao
	 */
	@RequestMapping(value={"/notification/collection/saveNotifications"})
	public String saveCollectionNotifications(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @NotificationController url='/notification/collection/saveNotifications");
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);				//db
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);		//cms
		CollectionDAO dao = new CollectionDAO(db, request);					//dao
		
		Collection collection = (Collection) cms.getObjectOfParameter(Collection.class); //objeto dados do form html		
		
		dao.updateNotificationSettings(collection);				//atualiza dados		
		System.out.println("Log - " + "Configuracoes de notificacoes salvas - id colecao: " + collection.getIdCollection());
		
		return "redirect:/ilt/groupCategory?idCat=" + collection.getIdCategory() + "&cat=" + collection.getNameCategory(); 	//sucess page category of group
	}
	
	
	
	/**
	 * Retorna o total de notificacao nao lida pelo ajax
	 */
	@RequestMapping(value={"/notification/ajaxTotalNotifications"})
	public void totalNotification(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @NotificationController url='/notification/ajaxTotalNotifications'");
		
		String totalNews = Integer.toString(NotificationService.totalNotifications(request));
		System.out.println("\nTotal novas notificacoes: " + totalNews + "\n");
		
		response.getWriter().write(new String(totalNews.getBytes("UTF-8")));				//retorna total notificacao ajax
	}
	
	/**
	 * Retorna uma lista de historico de notificacao pelo ajax
	 */
	@RequestMapping(value={"/notification/ajaxListNotifications"})
	public void notificationHistoric(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @NotificationController url='/notification/ajaxListNotifications'");
		
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);			//cms		
		Member member = (Member) request.getSession().getAttribute("member");	//member session
		
		String div = NotificationService.listHistoricNotification(cms, member, "809");	//page list entry notification template
		
		response.getWriter().write(new String(div.getBytes("UTF-8")));				//retorna ajax lista hostorico notificacao 		
	}
	
	/**
	 * Redireciona para pagina todas notificacoes - page more notifications
	 */
	@RequestMapping(value={"/notifications"})
	public String pageMoreNotificationHistoric(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @NotificationController url='/notifications'");
		
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);			//cms		
		Member member = (Member) request.getSession().getAttribute("member");	//member session
		
		String div = NotificationService.listHistoricNotification(cms, member, "808");	//page more notifications
		
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("listMoreNotificationsEntry", div);		//recuperar historico na view jsp
		
		return "page.jsp?id=736";									//page more notifications
	}
	
	
	/**
	 * Retorna json de novas notificacoes para mobile
	 */
	@RequestMapping(value={"/notification/ajaxNotificationsMobile"})
	public void notificationsMobile(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println("Log - " + "request @NotificationController url='/notification/ajaxNotificationsMobile'");
		
		JSONArray json = NotificationService.newsNotificationsMobile(request);	
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		if(json != null){
			System.out.println("\nJSON Notific Mobile: " + json.toString());
			response.getWriter().write(new String(json.toString().getBytes("UTF-8")));
		}else{
			System.out.println("\nJSON Notific Mobile: total 0");
			response.getWriter().write("");
		}
	}

}
