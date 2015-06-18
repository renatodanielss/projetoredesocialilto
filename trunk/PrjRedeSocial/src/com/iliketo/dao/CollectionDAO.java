package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

public class CollectionDAO extends GenericDAO{
	
	public CollectionDAO(DB db, HttpServletRequest request){
		super(db, "dbcollection", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos da CollectionDAO abaixo
	
}
