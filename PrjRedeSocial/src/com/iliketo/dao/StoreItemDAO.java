package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import com.iliketo.util.Str;

import HardCore.DB;

public class StoreItemDAO extends GenericDAO{
	
	public StoreItemDAO(DB db, HttpServletRequest request){
		super(db, "dbstoreitem", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos do StoreItemDAO abaixo
	
	public void deleteItem(String idDeleteItem) {
		
		DB db = super.getDb();
		
		//nome da foto para deletar
		String namePhotoDelete = IliketoDAO.getValueOfDatabase(db, "photo_store_item", "dbstoreitem", "id_item", idDeleteItem);		
		
		String localImagePath = (String) super.getRequest().getSession().getAttribute(Str.STORAGE);			//local da pasta das imagens armazenadas
		super.deleteFilePhysically(namePhotoDelete, localImagePath); //metodo deleta fisicamente
		
		IliketoDAO.deleteDadosIliketo(db, "dbstoreitem", "id", idDeleteItem); //metodo deleta dados na database
		
		log.info("***Delete StoreItem*** - id="+idDeleteItem+" delete ok!");
	}

}
