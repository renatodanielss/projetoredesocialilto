package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import HardCore.DB;

public class StoreItemDAO extends GenericDAO{
	
	public StoreItemDAO(DB db, HttpServletRequest request, HttpServletResponse response){
		super(db, "dbstoreitem", request, response);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos do StoreItemDAO abaixo
	
}
