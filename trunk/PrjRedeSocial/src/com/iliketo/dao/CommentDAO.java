package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

public class CommentDAO extends GenericDAO{
	
	public CommentDAO(DB db, HttpServletRequest request){
		super(db, "dbforumcomment", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos da CommentDAO abaixo
	
}