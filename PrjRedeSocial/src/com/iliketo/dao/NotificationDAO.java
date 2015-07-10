package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

import com.iliketo.util.Str;

public class NotificationDAO extends GenericDAO{
	
	public NotificationDAO(DB db, HttpServletRequest request){
		super(db, "dbgroupnotification", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos do GroupNotificationDAO abaixo

	
}
