package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import HardCore.DB;

public class ItemDAO extends GenericDAO{
	
	public ItemDAO(DB db, HttpServletRequest request, HttpServletResponse response){
		super(db, "dbcollectionitem", request, response);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos do ItemDAO abaixo
	
}
