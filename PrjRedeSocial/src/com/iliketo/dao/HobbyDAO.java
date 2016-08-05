package com.iliketo.dao;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.iliketo.model.Hobby;
import com.iliketo.util.ColumnsSingleton;

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
	
	public boolean usuarioJaPossuiHobby(Hobby hobby, String myUserid){
		
		ColumnsSingleton CS = ColumnsSingleton.getInstance(super.getDb());
		String SQL = "select h.id_hobby from dbhobby h where h.fk_user_id = '" +myUserid+ "' and h.fk_category_id = '" + hobby.getIdCategory()+"'";
		String[][] alias = { {"dbhobby", "h"} };
		SQL = CS.transformSQLReal(SQL, alias);
		HashMap<String,String> registro  = super.getDb().query_record(SQL);
		if(registro == null)
			return false;
		else
			return true;
	}
	
}
