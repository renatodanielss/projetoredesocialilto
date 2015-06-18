package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

public class ItemDAO extends GenericDAO{
	
	public ItemDAO(DB db, HttpServletRequest request){
		super(db, "dbcollectionitem", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos do ItemDAO abaixo
	
}
