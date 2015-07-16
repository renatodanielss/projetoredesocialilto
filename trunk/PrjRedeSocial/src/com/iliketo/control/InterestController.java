package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.InterestDAO;
import com.iliketo.model.Interest;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.Str;


@Controller
public class InterestController {
	

	@RequestMapping(value={"/interest/registerInterest"})
	public String registerInterest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @InterestController url='/interest/registerInterest'");
		
		return "/page.jsp?id=695"; 			//page form register interest
		
	}
	
	@RequestMapping(value={"/interest/createInterest"})
	public String createInterest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @InterestController url='/interest/createInterest'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		HttpSession session = request.getSession();
		InterestDAO interestDAO = new InterestDAO(db, request);
		
		Interest interest = (Interest) cms.getObjectOfParameter(Interest.class);	//objeto com dados do form
		String idCreate = interestDAO.create(interest);								//cria interesse
		session.setAttribute(Str.S_ID_INTEREST, idCreate);							//seta id na sessao
		
		System.out.println("Log - Interesse criado ok, id interesse: " + idCreate + " - nome: " + interest.getNameInterest());
		
		return "redirect:/page.jsp?id=696"; 		//sucess
		
	}
		
	

}
