package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import com.iliketo.util.Str;

import HardCore.DB;

public class AnnounceDAO extends GenericDAO{
	
	public AnnounceDAO(DB db, HttpServletRequest request){
		super(db, "dbannounce", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos da AnnounceDAO abaixo
	
	/**
	 * Metodo deleta um anuncio do banco de dados
	 * @param idDeleteAd
	 */
	public void deleteAnnounce(String idDeleteAd) {
		
		IliketoDAO.deleteDadosIliketo(super.getDb(), "dbannounce", "id", idDeleteAd); 	//metodo deleta dados na database

	}

}
