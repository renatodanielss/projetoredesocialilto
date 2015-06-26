package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

import com.iliketo.util.Str;

public class VideoDAO extends GenericDAO{
	
	public VideoDAO(DB db, HttpServletRequest request){
		super(db, "dbcollectionvideo", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos do VideoDAO abaixo
	
	public void deleteVideo(String idDeleteVideo) {
		
		DB db = super.getDb();
		
		//nome do video para deletar
		String nameVideoDelete = IliketoDAO.getValueOfDatabase(db, "path_file_video", "dbcollectionvideo", "id_video", idDeleteVideo);		
		
		String localPath = (String) super.getRequest().getSession().getAttribute(Str.STORAGE); 	   //local armazenamento
		super.deleteFilePhysically(nameVideoDelete, localPath); //metodo deleta fisicamente
		
		IliketoDAO.deleteDadosIliketo(db, "dbcollectionvideo", "id", idDeleteVideo); //metodo deleta dados na database

	}
	
}
