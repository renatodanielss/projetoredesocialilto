package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

import com.iliketo.util.Str;

public class HobbyFotoDAO extends GenericDAO{
	
	public HobbyFotoDAO(DB db, HttpServletRequest request){
		super(db, "dbhobbyfoto", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos para FotoHobby abaixo
	
	public void deleteFoto(String idDelete) {
		
		DB db = super.getDb();		
		//nome da foto para deletar
		String namePhotoDelete = IliketoDAO.getValueOfDatabase(db, "foto", "dbhobbyfoto", "id_foto", idDelete);			
		String localImagePath = (String) super.getRequest().getSession().getAttribute(Str.STORAGE);			//local da pasta das imagens armazenadas
		super.deleteFilePhysically(namePhotoDelete, localImagePath); 		//metodo deleta fisicamente		
		IliketoDAO.deleteDadosIliketo(db, "dbhobbyfoto", "id", idDelete); 	//metodo deleta dados na database
		
	}
	
}
