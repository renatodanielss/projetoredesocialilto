package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

public class ForumDAO extends GenericDAO{
	
	public ForumDAO(DB db, HttpServletRequest request){
		super(db, "dbforum", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos da ForumDAO abaixo
	
}
