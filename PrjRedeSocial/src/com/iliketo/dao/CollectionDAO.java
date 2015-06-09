package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import HardCore.DB;

public class CollectionDAO extends GenericDAO{
	
	public CollectionDAO(DB db, HttpServletRequest request, HttpServletResponse response){
		super(db, "dbcollection", request, response);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos da CollectionDAO abaixo
	
}
