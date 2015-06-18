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
	
	@RequestMapping(value={"/registerVideo"})
	public String registerVideo(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @VideoController url='/registerVideo'");
		
		return "page.jsp?id=654";	//page form register video
	}
	
	@RequestMapping(value={"/addVideo"})
	public String addVideo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @VideoController url='/addVideo'");
		
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
			return model.redirectError("/ilt/registerVideo");			//page form register video
		} catch (VideoILiketoException e) {
			model.addAttribute("video", video);
			model.addMessageError("videoFormat", "Video in MP4 format with duration until 2 minutes."); 	//msg erro
			return model.redirectError("/ilt/registerVideo");			//page form register video
		}
		
		videoDAO.create(video);							//salva video
		
		return "page.jsp?id=48";						//success
	}
	
	

}
