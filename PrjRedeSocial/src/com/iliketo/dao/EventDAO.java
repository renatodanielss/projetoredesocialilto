package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

import com.iliketo.util.Str;

public class EventDAO extends GenericDAO{
	
	public EventDAO(DB db, HttpServletRequest request){
		super(db, "dbevent", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos do EventDAO abaixo
	
	/**
	 * Metodo deleta dados de um evento e a foto do mesmo.
	 * @param idDeleteEvent
	 */
	public void deleteEvent(String idDeleteEvent) {
		
		DB db = super.getDb();
		
		//nome da foto para deletar
		String namePhotoDelete = IliketoDAO.getValueOfDatabase(db, "path_photo_event", "dbevent", "id_event", idDeleteEvent);		
		
		String localImagePath = (String) super.getRequest().getSession().getAttribute(Str.STORAGE);			//local da pasta das imagens armazenadas
		super.deleteFilePhysically(namePhotoDelete, localImagePath); 		//metodo deleta fisicamente
		
		IliketoDAO.deleteDadosIliketo(db, "dbevent", "id", idDeleteEvent); 	//metodo deleta dados na database

	}
	
}
