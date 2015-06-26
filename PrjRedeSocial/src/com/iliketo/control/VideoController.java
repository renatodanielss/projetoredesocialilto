package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.VideoDAO;
import com.iliketo.exception.StorageILiketoException;
import com.iliketo.exception.VideoILiketoException;
import com.iliketo.model.Video;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class VideoController {
	
	@RequestMapping(value={"/video/addVideo"})
	public String addVideo(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @VideoController url='/video/addVideo'");
		
		return "page.jsp?id=654";	//page form add video
	}
	
	@RequestMapping(value={"/video/createVideo"})
	public String createVideo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @VideoController url='/video/createVideo'");
		
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
		
		videoDAO.create(video);					//cria video
		
		return "page.jsp?id=48";		//success
	}
	
	
	@RequestMapping(value={"/video/edit"})
	public String editVideo(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @VideoController url='/video/edit'");
		
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
		
		System.out.println("Log - " + "request @VideoController url='/video/save'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		VideoDAO videoDAO = new VideoDAO(db, request);
		
		Video video = (Video) cms.getObjectOfParameter(Video.class);	//objeto com dados do form
		
		videoDAO.update(video);					//atualiza video
		
		return "page.jsp?id=48";		//success
	}
	
	@RequestMapping(value={"/video/delete"})
	public String deleteVideo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @VideoController url='/video/delete'");
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		VideoDAO videoDAO = new VideoDAO(db, request);
		
		String id = request.getParameter("id");
		videoDAO.deleteVideo(id);				//delete video
		
		return "page.jsp?id=48";		//success
	}

}
