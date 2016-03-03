package com.iliketo.control;

import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.HobbyDAO;
import com.iliketo.dao.IliketoDAO;
import com.iliketo.model.Hobby;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ColumnsSingleton;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class HobbyController {
	
	
	static final Logger log = Logger.getLogger(HobbyController.class);
	
	/**
	 * Método intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
	
	@RequestMapping(value={"/hobby/add"})
	public String addHobby(HttpServletRequest request, HttpServletResponse response){
		
		if(ModelILiketo.validateAndProcessError(request)){
			//valida e mostra error na pagina
			log.warn("Erro ao adicionar hobby - usuario jah possui o hobby.");
		}
		return "page.jsp?id=1086";					//page form Add new hobby
	}
	
	
	@RequestMapping(value={"/hobby/createHobby"})
	public String createHobby(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		HobbyDAO hobbyDAO = new HobbyDAO(db, request);		
		Hobby hobby = (Hobby) cms.getObjectOfParameter(Hobby.class);	//objeto com dados do form

		String SQL = "select h.id_hobby from dbhobby h where h.fk_user_id = '" +myUserid+ "' and h.fk_category_id = '" + hobby.getIdCategory()+"'";
		String[][] alias = { {"dbhobby", "h"} };
		SQL = CS.transformSQLReal(SQL, alias);
		HashMap<String,String> registro  = db.query_record(SQL);
		
		//valida se jah participa do grupo hobby
		if(registro == null){			
			hobbyDAO.create(hobby);											//add hobby
			String idCat = hobby.getIdCategory();			
			return "redirect:/ilt/groupCategory/group?idCat=" + idCat;		//success group hobby
		}else{
			//jah participa do hobby
			ModelILiketo model = new ModelILiketo(request, response);
			model.addMessageError("hobbyError", "You already add this hobby!<br>"); 	//msg erro
			return model.redirectError("/ilt/hobby/add");								//page form add hobby
		}		
	}
	
	@RequestMapping(value={"/hobby/myHobbies"})
	public String myHobbies(HttpServletRequest request, HttpServletResponse response){
		
		return "page.jsp?id=10xx";					//page lista todos meus hobbies
	}	

}
