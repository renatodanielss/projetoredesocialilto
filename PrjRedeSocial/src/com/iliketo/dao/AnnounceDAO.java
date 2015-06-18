package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

public class AnnounceDAO extends GenericDAO{
	
	public AnnounceDAO(DB db, HttpServletRequest request){
		super(db, "dbannounce", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos da AnnounceDAO abaixo
	
}
