package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import HardCore.DB;

public class AnnounceDAO extends GenericDAO{
	
	public AnnounceDAO(DB db, HttpServletRequest request, HttpServletResponse response){
		super(db, "dbannounce", request, response);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos da AnnounceDAO abaixo
	
}
