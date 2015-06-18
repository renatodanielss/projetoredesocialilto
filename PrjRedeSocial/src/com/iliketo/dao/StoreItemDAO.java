package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

public class StoreItemDAO extends GenericDAO{
	
	public StoreItemDAO(DB db, HttpServletRequest request){
		super(db, "dbstoreitem", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos do StoreItemDAO abaixo
	
}
