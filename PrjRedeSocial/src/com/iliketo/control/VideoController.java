package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.CollectionDAO;
import com.iliketo.dao.IliketoDAO;
import com.iliketo.dao.VideoDAO;
import com.iliketo.exception.StorageILiketoException;
import com.iliketo.exception.VideoILiketoException;
import com.iliketo.model.Collection;
import com.iliketo.model.Video;
import com.iliketo.service.NotificationService;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class VideoController {
	
	
	static final Logger log = Logger.getLogger(VideoController.class);
	
	/**
	 * Método intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
	
	/**
	 * Redireciona pagina para visualizar meu video ou video de outro colecionador
	 */
	@RequestMapping(value={"/video/view"})
	public String videoView(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);						//db
		VideoDAO dao = new VideoDAO(db, request);									//dao
		
		String idVideo = request.getParameter("id"); 								//id do video
		String myIdUser = (String) request.getSession().getAttribute("userid"); 	//my userid
		Video video = (Video) dao.readById(idVideo, Video.class);					//ler video
		
		//valida membro dono do video
		if(video != null){	
			ModelILiketo model = new ModelILiketo(request, response);
			model.addAttribute("video", video);
			if(myIdUser.equals(video.getIdMember())){
				return "/page.jsp?id=655";			//page visualizar my video
			}else{
				return "/page.jsp?id=657";			//page visualizar video terceiro
			}			
		}else{
			log.info("Video nao encontrado ou indisponivel!");
			return "/page.jsp?id=invalid";			//invalid page
		}
	}
	
	@RequestMapping(value={"/video/addVideo"})
	public String addVideo(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);						//db
		String myUserid = (String) request.getSession().getAttribute("userid");
		String idCollection = (String) request.getParameter("id_col");
		CollectionDAO dao = new CollectionDAO(db, request);
		Collection colecao = (Collection) dao.readById(idCollection, Collection.class);
		
		//valida colecao
		if(colecao != null && colecao.getIdMember().equals(myUserid)){	
			if(ModelILiketo.validateAndProcessError(request)){
				//valida e mostra error na pagina
				log.warn("Erro ao adicionar video. Tela formulario add video");
			}
			return "page.jsp?id=654&id_col=" + idCollection;		//page form add video
		}else{
			return "page.jsp?id=invalid";						//invalid page
		}
			
	}
	
	@RequestMapping(value={"/video/createVideo"})
	public String createVideo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		VideoDAO videoDAO = new VideoDAO(db, request);
		
		Video video = (Video) cms.getObjectOfParameter(Video.class);	//objeto com dados do form
		
		ModelILiketo model = new ModelILiketo(request, response);
		try {
			cms.processFileuploadVideo(video);							//salva arquivos			
		} catch (StorageILiketoException e) {			
			model.addAttribute("video", video);
			model.addMessageError("freeSpace", "You do not have enough free space, needed " +cms.getSizeFilesInBytes()/1024+ " KB.");	//msg erro
			return model.redirectError("/ilt/video/addVideo");			//page form add video
		} catch (VideoILiketoException e) {
			model.addAttribute("video", video);
			model.addMessageError("videoFormat", "Video in MP4 format with duration until 2 minutes."); 	//msg erro
			return model.redirectError("/ilt/video/addVideo");			//page form add video
		}
		
		String idCreate = videoDAO.create(video);											//cria video
		String idCollection = video.getIdCollection();
		
		//cria notificacao para o grupo da categoria
		String idCategory = IliketoDAO.getValueOfDatabase(db, "fk_category_id", "dbcollection", "id_collection", idCollection);
		if(idCategory != null && !idCategory.equals("")){
			String myUserid = (String) request.getSession().getAttribute("userid");
			NotificationService.createNotification(db, idCategory, "video", idCreate, Str.INCLUDED, myUserid);
		}
		
		return "redirect:/ilt/collection/profile?id=" + idCollection;	//success
	}
	
	
	@RequestMapping(value={"/video/edit"})
	public String editVideo(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		VideoDAO videoDAO = new VideoDAO(db, request);
		
		String id = request.getParameter("id");								//id video
		Video video = (Video) videoDAO.readById(id, Video.class);			//ler video
		
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("video", video);		//dados atual do video
				
		return "page.jsp?id=804";				//page form edit video
	}
	
	@RequestMapping(value={"/video/save"})
	public String saveVideo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		VideoDAO videoDAO = new VideoDAO(db, request);
		
		Video video = (Video) cms.getObjectOfParameter(Video.class);	//objeto com dados do form
		
		videoDAO.update(video, false);									//atualiza video
		String idCollection = video.getIdCollection();
		
		//cria notificacao para o grupo da categoria
		String idCategory = IliketoDAO.getValueOfDatabase(db, "fk_category_id", "dbcollection", "id_collection", idCollection);
		if(idCategory != null && !idCategory.equals("")){
			NotificationService.createNotification(db, idCategory, "video", video.getIdVideo(), Str.UPDATED, video.getIdMember());
		}
		
		return "redirect:/ilt/collection/profile?id=" + idCollection;	//success
	}
	
	@RequestMapping(value={"/video/delete"})
	public String deleteVideo(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		VideoDAO videoDAO = new VideoDAO(db, request);
		
		String id = request.getParameter("id");
		videoDAO.deleteVideo(id);				//delete video
		
		return "redirect:/page.jsp?id=48";		//success
	}

}
