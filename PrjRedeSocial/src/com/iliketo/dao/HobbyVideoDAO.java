package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

import com.iliketo.util.Str;

public class HobbyVideoDAO extends GenericDAO{
	
	public HobbyVideoDAO(DB db, HttpServletRequest request){
		super(db, "dbhobbyvideo", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	
	public void deleteVideo(String idDelete) {
		
		DB db = super.getDb();		
		//nome do video para deletar
		String nameFileDelete = IliketoDAO.getValueOfDatabase(db, "pathVideo", "dbhobbyvideo", "id_video", idDelete);			
		String localVideoPath = (String) super.getRequest().getSession().getAttribute(Str.STORAGE);			//local da pasta das imagens armazenadas
		super.deleteFilePhysically(nameFileDelete, localVideoPath); 		//metodo deleta fisicamente		
		IliketoDAO.deleteDadosIliketo(db, "dbhobbyvideo", "id", idDelete); 	//metodo deleta dados na database
		
	}
	
}
