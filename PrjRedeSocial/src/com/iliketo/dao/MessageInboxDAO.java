package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

public class MessageInboxDAO extends GenericDAO{
	
	public MessageInboxDAO(DB db, HttpServletRequest request){
		super(db, "dbmessageinbox", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos da MessageInboxDAO abaixo
	
}
