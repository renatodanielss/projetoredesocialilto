package com.iliketo.control;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.iliketo.dao.GenericDAO;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;

import HardCore.DB;


@Controller
public class PageUtilsController {

	
	static final Logger log = Logger.getLogger(PageUtilsController.class);
	
	/**
	 * Método intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
	
	/**
	 * Metodo retorna uma lista de entrada de solicitacao ajax
	 * @param idPage - id da pagina de List Entry
	 * @param className - contem os nomes das classes para instancia o objeto retornado da camada de persistencia.
	 * Ex: "Collection" ou "Collection,item"
	 * @param idIliketoo - contem o id dos registros para camada de persistencia recuperar o objeto.
	 * Ex: "1001" ou "1001,1002"
	 */
	@RequestMapping(value={"/ajaxPageListEntryBinding"})
	public void getPageListEntryBinding(HttpServletRequest request, 
			HttpServletResponse response, 
			@RequestParam(required=true, value="idPage") String idPage,
			@RequestParam(required=false, value="className") String className,
			@RequestParam(required=false, value="idIliketoo") String idIliketoo) 
					throws IOException, ClassNotFoundException, 
					InstantiationException, IllegalAccessException, 
					IllegalArgumentException, InvocationTargetException, 
					NoSuchMethodException, SecurityException{
		
		log.info(request.getRequestURL() + " - PageUtils [email: " + request.getSession().getAttribute("email") + "]");
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		ModelILiketo model = new ModelILiketo(request, response);
		
		//set objetos parse view
		String tokenClassName[] = className.split(",");
		String tokenIdIliketoo[] = idIliketoo.split(",");
		for(int i = 0; i < tokenClassName.length && tokenClassName.length == tokenIdIliketoo.length; i++){
			if(tokenClassName[i] != null && !tokenClassName[i].isEmpty()
					&&tokenIdIliketoo[i] != null && !tokenIdIliketoo[i].isEmpty()){
					Class clazz = Class.forName("com.iliketo.model." + tokenClassName[i]);
					Class clazzDAO = Class.forName("com.iliketo.dao." + tokenClassName[i] + "DAO");
					Class array[] = {DB.class, HttpServletRequest.class};
					GenericDAO dao = (GenericDAO)clazzDAO.getConstructor(array).newInstance(db, request);
					model.addAttribute("objeto", clazz.cast(dao.readById(tokenIdIliketoo[i], clazz)));
			}
		}
		String conteudoPagina = cms.getPageBinding(idPage);		
		response.getWriter().write(conteudoPagina);
		response.setStatus(200);
	}
	
		
}
