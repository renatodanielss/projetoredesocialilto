package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

public class TopicDAO extends GenericDAO{
	
	public TopicDAO(DB db, HttpServletRequest request){
		super(db, "dbforumtopic", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos da TopicDAO abaixo
	
}
