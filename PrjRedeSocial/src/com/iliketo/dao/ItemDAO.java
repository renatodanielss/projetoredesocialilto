package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

import com.iliketo.util.Str;

public class ItemDAO extends GenericDAO{
	
	public ItemDAO(DB db, HttpServletRequest request){
		super(db, "dbcollectionitem", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos do ItemDAO abaixo
	
	public void deleteItem(String idDeleteItem) {
		
		DB db = super.getDb();
		
		//nome da foto para deletar
		String namePhotoDelete = IliketoDAO.getValueOfDatabase(db, "path_photo_item", "dbcollectionitem", "id_item", idDeleteItem);		
		
		String localImagePath = (String) super.getRequest().getSession().getAttribute(Str.STORAGE);			//local da pasta das imagens armazenadas
		super.deleteFilePhysically(namePhotoDelete, localImagePath); //metodo deleta fisicamente
		
		IliketoDAO.deleteDadosIliketo(db, "dbcollectionitem", "id", idDeleteItem); //metodo deleta dados na database
		
		//delete anuncio do item se houver
		IliketoDAO.deleteDadosIliketo(db, "dbannounce", "fk_item_id", idDeleteItem); //metodo deleta dados na database
		
	}
	
}
