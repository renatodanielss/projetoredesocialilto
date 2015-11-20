package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.InterestDAO;
import com.iliketo.model.Interest;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class InterestController {
	

	static final Logger log = Logger.getLogger(InterestController.class);
	
	/**
	 * Método intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
	
	@RequestMapping(value={"/interest/registerInterest"})
	public String registerInterest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		
		return "/page.jsp?id=696"; 			//page add the group to your interest
		
	}
	
	@RequestMapping(value={"/interest/profile"})
	public String myInterestProfile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		InterestDAO interestDAO = new InterestDAO(db, request);
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		String id = request.getParameter("id");										//id interesse
		Interest interest = (Interest) interestDAO.readById(id, Interest.class);	//ler dados interesse
		
		//valida interesse do membro
		if(interest != null && interest.getIdMember().equals(myUserid)){
			ModelILiketo model = new ModelILiketo(request, response);
			model.addAttribute("interest", interest);
			return "/page.jsp?id=699"; 				//page interest profile
		}else{
			return "page.jsp?id=xxx"; 				//page conteudo nao disponivel
		}
	}
	
	/**
	 * Metodo cria um interesse e faz o join para adicionar o grupo nos interesses
	 */
	@RequestMapping(value={"/interest/createInterest"})
	public String createInterest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		InterestDAO interestDAO = new InterestDAO(db, request);
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		String idCategory = request.getParameter("idCat");
		String nameCategory = request.getParameter("nameCat");
		
		if(idCategory != null && !idCategory.equals("") && nameCategory != null && !nameCategory.equals("")){
			Interest interest = new Interest();
			interest.setIdCategory(idCategory);
			interest.setNameCategory(nameCategory);
			interest.setIdMember(myUserid);
			//por padrao todas notificacoes sao ativadas
			interest.setNotificCollection("Activate");
			interest.setNotificItem("Activate");
			interest.setNotificVideo("Activate");
			interest.setNotificEvent("Activate");
			interest.setNotificAnnounce("Activate");
			interest.setNotificTopic("Activate");
			interest.setNotificComment("Activate");
			
			String idCreate = interestDAO.create(interest);		//cria interesse
			log.info("Interesse criado ok, id interesse: " + idCreate + " - nome categoria/grupo: " + interest.getNameCategory());
		}
		
		return "redirect:/ilt/groupCategory?idCat=" + idCategory + "&cat=" + nameCategory; 	//sucess page group
		
	}
		
	

}
