package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

public class CategoryDAO extends GenericDAO{
	
	public CategoryDAO(DB db, HttpServletRequest request){
		super(db, "dbcategory", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos da CategoryDAO abaixo
	
}
