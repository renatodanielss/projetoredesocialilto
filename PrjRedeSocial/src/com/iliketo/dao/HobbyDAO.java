package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

public class HobbyDAO extends GenericDAO{
	
	public HobbyDAO(DB db, HttpServletRequest request){
		super(db, "dbhobby", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos do HobbyDAO abaixo	
	
	/**
	 * Metodo deleta o registro de um hobby.
	 * @param idDelete
	 */
	public void deleteHobby(String idDelete) {
		
		IliketoDAO.deleteDadosIliketo(super.getDb(), "dbhobby", "id", idDelete); 	//metodo deleta dados na database

	}
	
}
