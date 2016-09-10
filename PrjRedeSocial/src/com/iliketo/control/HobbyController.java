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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import HardCore.DB;

import com.iliketo.dao.HobbyDAO;
import com.iliketo.dao.HobbyFotoDAO;
import com.iliketo.dao.HobbyVideoDAO;
import com.iliketo.exception.ImageILiketoException;
import com.iliketo.exception.StorageILiketoException;
import com.iliketo.exception.VideoILiketoException;
import com.iliketo.model.Hobby;
import com.iliketo.model.HobbyFoto;
import com.iliketo.model.HobbyVideo;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ColumnsSingleton;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.ResponseILiketoo;
import com.iliketo.util.Str;


@Controller
public class HobbyController {
	
	private @Autowired HttpServletRequest request;
	//private @Autowired HttpServletResponse response;
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
	public String addHobby(){		
		if(ModelILiketo.validateAndProcessError(request)){
			//valida e mostra error na pagina
			log.warn("Erro ao adicionar hobby - usuario jah possui o hobby.");
		}
		return "page.jsp?id=1086";					//page form Add new hobby
	}
	
	
	@RequestMapping(value={"/hobby/createHobby"})
	public String createHobby(HttpServletResponse response) throws Exception{		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		String myUserid = (String) request.getSession().getAttribute("userid");		
		HobbyDAO hobbyDAO = new HobbyDAO(db, request);
		Hobby hobby = (Hobby) cms.getObjectOfParameter(Hobby.class);
		hobby.setIdMember(myUserid);
		
		//valida se jah participa do grupo hobby
		if(!hobbyDAO.usuarioJaPossuiHobby(hobby, myUserid)){			
			String idRegistro = hobbyDAO.create(hobby);											//add hobby
			return "redirect:/ilt/hobbyProfile/photos/" + idRegistro;
		}else{
			//jah participa do hobby
			ModelILiketo model = new ModelILiketo(request, response);
			model.addMessageError("hobbyError", "You already add this hobby!<br>"); 	//msg erro
			return model.redirectError("/ilt/hobby/add");								//page form add hobby
		}
	}
	
	@RequestMapping(value={"/hobby/myHobbies"})
	public String myHobbies(){
		
		return "page.jsp?id=10xx";					//page lista todos meus hobbies
	}
	
	/**
	 * Metodo retorna array json com todas supercategorias de hobby
	 */
	@RequestMapping("/hobby/ajaxSuperCategoria")
	public void superCategoria(@RequestParam(required=true, value="idioma") String idioma,
			HttpServletResponse response) throws JSONException, IOException{
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		
		String SQLCategory = "select t1.id_category as id_category, t1.super_category as super_category "
				+ "from dbcategory t1 where t1.super_category != '' and idiom = '" +idioma+ "' and t1.hobby = 'yes';";
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
	public void subCategoria(@RequestParam(required=true, value="superCategoria") String superCategoria,
			@RequestParam(required=true, value="idioma") String idioma,
			HttpServletResponse response) throws JSONException, IOException{
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		
		String SQLCategory = "select t1.id_category as id_category, t1.name_category as name_category from dbcategory t1"
				+ " where t1.super_category ilike '" +superCategoria+ "' and idiom = '" +idioma+ "' and t1.hobby = 'yes';";
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

	/**
	 * Redireciona pagina para visualizar meu hobby ou terceiro
	 */
	@RequestMapping(value={"/hobbyProfile/{abaTela}/{idHobby}"}, method=RequestMethod.GET)
	public String perfilHobby(@PathVariable String abaTela, @PathVariable String idHobby,
			HttpServletResponse response){
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		ModelILiketo model = new ModelILiketo(request, response);
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		if(ModelILiketo.validateAndProcessError(request)){
			//valida e mostra error na pagina
			log.warn("Erro ao criar adicionar uma foto de perfil ou capa do membro.");
		}
		
		//visualizar abas do perfil hobby (fotos, videos, anuncios)
		if(abaTela.equals("photos") || abaTela.equals("videos")
				|| abaTela.equals("ads")){
			model.addAttribute("abaTela", abaTela);			
			HobbyDAO dao = new HobbyDAO(db, request);
			Hobby hobby = (Hobby) dao.readById(idHobby, Hobby.class);
			if(hobby != null){
				model.addAttribute("hobby", hobby);			
				if(hobby.getIdMember().equals(myUserid)){
					return "/page.jsp?id=1107";		//meu perfil hobby
				}else{
					return "/page.jsp?id=1114";		//profile terceiro hobby
				}
			}
		}
		return "page.jsp?id=invalidPage";	//pagina invalida, nao achou hobby ou pagina perfil		
	}
	
	/**
	 * Adiciona ou altera a foto da capa
	 */
	@RequestMapping(value={"/hobbyProfile/addCapa/{idHobby}"})
	public String adicionarFotoCapa(HttpServletResponse response, @PathVariable String idHobby) throws Exception{
		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		HobbyDAO dao = new HobbyDAO(db, request);
		Hobby hobby = new Hobby();
		
		ModelILiketo model = new ModelILiketo(request, response);
		try {
			cms.processFileuploadImage(hobby);
		} catch (StorageILiketoException e) {
			model.addAttribute("hobby", hobby);
			model.addMessageError("freeSpace", "You do not have enough free space, needed " +cms.getSizeFilesInBytes()/1024+ " KB.");	//msg erro
			return model.redirectError("/ilt/hobbyProfile/photos/" + idHobby);			//page perfil
		} catch (ImageILiketoException e) {
			model.addAttribute("hobby", hobby);
			model.addMessageError("imageFormat", "Upload only Image in jpg format."); 						//msg erro
			return model.redirectError("/ilt/hobbyProfile/photos/" + idHobby);			//page perfil
		}
		//seta ids no hobby para atualizar
		hobby.setId(idHobby);
		hobby.setIdHobby(idHobby);
		dao.update(hobby, true);
		model.addAttribute("hobby", hobby);
		return "redirect:/ilt/hobbyProfile/photos/" + idHobby;
	}
	
	@RequestMapping(value={"/hobbyProfile/removeCapa/{idHobby}"})
	public String removerFotoCapa(HttpServletResponse response, @PathVariable String idHobby) throws Exception{		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		HobbyDAO dao = new HobbyDAO(db, request);
		String myUserid = (String) request.getSession().getAttribute("userid");
		Hobby hobby = (Hobby) dao.readById(idHobby, Hobby.class);
		
		//remove foto de capa
		if(hobby != null && hobby.getIdMember().equals(myUserid)){
			hobby.setFotoDeCapa("");
			dao.update(hobby, true);
			return "redirect:/ilt/hobbyProfile/photos/" + idHobby;
		}else{
			return "page.jsp?id=invalidPage";	//pagina invalida ou hobby nao pertence ao usuario na session
		}
	}
	
	//FOTOS DO HOBBY
	@RequestMapping(value={"/hobbyProfile/photos/add/{idHobby}"})
	public String addItems(@PathVariable String idHobby){
		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		String myUserid = (String) request.getSession().getAttribute("userid");
		HobbyDAO dao = new HobbyDAO(db, request);
		Hobby hobby = (Hobby) dao.readById(idHobby, Hobby.class);

		//valida my hobby
		if(hobby != null){
			if(hobby.getIdMember().equals(myUserid)){
				if(ModelILiketo.validateAndProcessError(request)){
					//valida e mostra error na pagina
					log.warn("Erro ao adicionar itens. Tela formulario add mais itens");
				}
				return "page.jsp?id=xxxx&idHobby=" + idHobby;	//pagina form adicionar fotos do hobby
			}
		}
		return "page.jsp?id=invalidPage";	//pagina invalida, id do hobby nao pertence a esse usuario
 	}
	
	@RequestMapping(value={"/hobbyProfile/photos/create/{idHobby}"})
	public String createItems(HttpServletResponse response, @PathVariable String idHobby) throws Exception{
		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		String myUserid = (String) request.getSession().getAttribute("userid");
		Hobby h = (Hobby) new HobbyDAO(db, null).readById(idHobby, Hobby.class);
		
		//valida hobby do usuario na session
		if(h != null && h.getIdMember().equals(myUserid)){
			Object[] fotosHobby  = cms.getObjectsFileOfParameter(HobbyFoto.class);	//array objetos com os items
			for(Object item : fotosHobby){
				((HobbyFoto)item).setIdHobby(idHobby);								//seta fk_user_id no hobby
				((HobbyFoto)item).setIdMember(myUserid);
			}		
			ModelILiketo model = new ModelILiketo(request, response);
			try {
				cms.processFileuploadImages(fotosHobby);							//salva arquivos			
			} catch (StorageILiketoException e) {
				model.addMessageError("freeSpace", "You do not have enough free space, needed " +cms.getSizeFilesInBytes()/1024+ " KB.");	//msg erro
				return model.redirectError("/ilt/hobbyProfile/photos/add/" + idHobby);				//page form add more item
			} catch (ImageILiketoException e) {
				model.addMessageError("imageFormat", "Upload only Image in jpg format."); 													//msg erro
				return model.redirectError("/ilt/hobbyProfile/photos/add/" + idHobby);				//page form add more item
			}		
			new HobbyFotoDAO(db, request).creates(fotosHobby);							//cria foto do hobby		
			return "redirect:/ilt/hobbyProfile/photos/" + idHobby;					//success
		}
		return "page.jsp?id=invalidPage";	//pagina invalida, id do hobby nao pertence a esse usuario		
	}
	
	@RequestMapping(value={"/hobbyProfile/videos/create/{idHobby}"})
	public String createVideo(HttpServletResponse response, @PathVariable String idHobby) throws Exception{
		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		String myUserid = (String) request.getSession().getAttribute("userid");
		Hobby h = (Hobby) new HobbyDAO(db, null).readById(idHobby, Hobby.class);
		
		//valida hobby do usuario na session
		if(h != null && h.getIdMember().equals(myUserid)){
			ModelILiketo model = new ModelILiketo(request, response);
			HobbyVideo video = (HobbyVideo) cms.getObjectOfParameter(HobbyVideo.class);	//objeto com dados do form
			video.setIdHobby(idHobby);
			video.setIdMember(myUserid);
			try {
				cms.processFileuploadVideo(video);			//salva arquivos			
			} catch (StorageILiketoException e) {			
				model.addMessageError("freeSpace", "You do not have enough free space, needed " +cms.getSizeFilesInBytes()/1024+ " KB."); //msg erro
				return model.redirectError("/ilt/hobbyProfile/videos/" + idHobby);
			} catch (VideoILiketoException e) {
				model.addMessageError("videoFormat", "Video in MP4 format with duration until 2 minutes.");
				return model.redirectError("/ilt/hobbyProfile/videos/" + idHobby);
			}
			new HobbyVideoDAO(db, request).create(video);				//cria video
			return "redirect:/ilt/hobbyProfile/videos/" + idHobby;	//success
		}
		return "page.jsp?id=invalidPage";	//pagina invalida, id do hobby nao pertence a esse usuario
	}
	
	@RequestMapping(value={"/hobbyProfile/videos/update/{idHobby}/{idVideo}"})
	public String salvarVideo(HttpServletResponse response, @PathVariable String idHobby, @PathVariable String idVideo) throws Exception{		
		log.info(request.getRequestURL());
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		HobbyVideo video = (HobbyVideo) cms.getObjectOfParameter(HobbyVideo.class);	//objeto com dados do form
		video.setId(idVideo);
		video.setIdVideo(idVideo);
		new HobbyVideoDAO(db, request).update(video, false);	//salva video
		return "redirect:/ilt/hobbyProfile/videos/" + idHobby;	//success
	}
	
	@RequestMapping(value={"/hobbyProfile/photos/update/{idHobby}/{idFoto}"})
	public String salvarFoto(HttpServletResponse response, @PathVariable String idHobby, @PathVariable String idFoto) throws Exception{		
		log.info(request.getRequestURL());
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		HobbyFoto foto = (HobbyFoto) cms.getObjectOfParameter(HobbyFoto.class);	//objeto com dados do form
		foto.setId(idFoto);
		foto.setIdFoto(idFoto);
		new HobbyFotoDAO(db, request).update(foto, false);		//salva foto
		return "redirect:/ilt/hobbyProfile/photos/" + idHobby;	//success
	}
	
	@RequestMapping(value={"/hobbyProfile/videos/delete/{idHobby}/{idVideo}"})
	public String excluirVideo(HttpServletResponse response, @PathVariable String idHobby, @PathVariable String idVideo) throws Exception{
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		new HobbyVideoDAO(db, request).deleteVideo(idVideo);
		return "redirect:/ilt/hobbyProfile/videos/" + idHobby;
	}
	
	@RequestMapping(value={"/hobbyProfile/photos/delete/{idHobby}/{idFoto}"})
	public String excuirFoto(HttpServletResponse response, @PathVariable String idHobby, @PathVariable String idFoto) throws Exception{		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		new HobbyFotoDAO(db, request).deleteFoto(idFoto);
		return "redirect:/ilt/hobbyProfile/photos/" + idHobby;
	}
}
