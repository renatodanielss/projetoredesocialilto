package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.InterestDAO;
import com.iliketo.model.Interest;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class InterestController {
	

	@RequestMapping(value={"/interest/registerInterest"})
	public String registerInterest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @InterestController url='/interest/registerInterest'");
		
		return "/page.jsp?id=696"; 			//page add the group to your interest
		
	}
	
	@RequestMapping(value={"/interest/profile"})
	public String myInterestProfile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @InterestController url='/interest/profile'");
		
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
		
		System.out.println("Log - " + "request @InterestController url='/interest/createInterest'");
		
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
			System.out.println("Log - Interesse criado ok, id interesse: " + idCreate + " - nome categoria/grupo: " + interest.getNameCategory());
		}
		
		return "redirect:/ilt/groupCategory?id=" + idCategory + "&cat=" + nameCategory; 	//sucess page group
		
	}
		
	

}
