package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import HardCore.DB;

public class VideoDAO extends GenericDAO{
	
	public VideoDAO(DB db, HttpServletRequest request){
		super(db, "dbcollectionvideo", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos do VideoDAO abaixo
	
}
