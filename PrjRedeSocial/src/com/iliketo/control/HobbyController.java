package com.iliketo.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import HardCore.DB;

import com.iliketo.dao.CategoryDAO;
import com.iliketo.dao.HobbyDAO;
import com.iliketo.dao.IliketoDAO;
import com.iliketo.model.Hobby;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ColumnsSingleton;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.ResponseILiketoo;
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
		
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		HobbyDAO hobbyDAO = new HobbyDAO(db, request);		
		Hobby hobby = (Hobby) cms.getObjectOfParameter(Hobby.class);	//objeto com dados do form

		//valida se jah participa do grupo hobby
		if(hobbyDAO.usuarioJaPossuiHobby(hobby, myUserid)){			
			hobbyDAO.create(hobby);											//add hobby
			String idCat = hobby.getIdCategory();			
			//return "redirect:/ilt/groupCategory/group?idCat=" + idCat;		//success group hobby
			return "redirect:/ilt/hobbyProfile/" + idCat;
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
	
	/**
	 * Metodo retorna array json com todas supercategorias de hobby
	 */
	@RequestMapping("/hobby/ajaxSuperCategoria")
	public void superCategoria(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException{
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		String SQLCategory = "select t1.id_category as id_category, t1.super_category as super_category "
				+ "from dbcategory t1 left join dbhobby as t2 on t1.id_category = t2.fk_category_id "
				+ " where t1.super_category != '' and t1.hobby = 'yes' and t2.fk_user_id != '" +myUserid+ "';";
		String[][] aliasCat = { {"dbcategory", "t1"} };
		SQLCategory = CS.transformSQLReal(SQLCategory, aliasCat);
		LinkedHashMap<String,HashMap<String,String>> recordsCategory  = db.query_records(SQLCategory);

		JSONArray respJson = new JSONArray();
		for(String rec : recordsCategory.keySet()){
			String idCategry = recordsCategory.get(rec).get("id_category");
			String nameSupCategory = recordsCategory.get(rec).get("super_category");
		    JSONObject obj  = new JSONObject();
	        obj.put("name", nameSupCategory);
	        obj.put("id", idCategry);
		    respJson.put(obj);
		}
		ResponseILiketoo.respostaJSONArray(response, respJson);
	}
	
	/**
	 * Metodo recebe uma String superCategoria e retorna array json com suas subcategorias do hobby
	 * @param superCategoria - Aeromodelismo
	 */
	@RequestMapping("/hobby/ajaxSubCategoria")
	public void subCategoria(HttpServletRequest request, HttpServletResponse response,
								@RequestParam(required=true, value="superCategoria") String superCategoria) throws JSONException, IOException{
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		
		String SQLCategory = "select t1.id_category as id_category, t1.name_category as name_category from dbcategory t1"
				+ " where t1.super_category ilike '" +superCategoria+ "' and t1.hobby = 'yes';";
		String[][] aliasCat = { {"dbcategory", "t1"} };
		SQLCategory = CS.transformSQLReal(SQLCategory, aliasCat);
		LinkedHashMap<String,HashMap<String,String>> recordsCategory  = db.query_records(SQLCategory);

		JSONArray respJson = new JSONArray();
		for(String rec : recordsCategory.keySet()){
			String idCategry = recordsCategory.get(rec).get("id_category");
			String nameCategory = recordsCategory.get(rec).get("name_category");
		    JSONObject obj  = new JSONObject();
	        obj.put("name", nameCategory);
	        obj.put("id", idCategry);
		    respJson.put(obj);
		}
		ResponseILiketoo.respostaJSONArray(response, respJson);
	}

}
