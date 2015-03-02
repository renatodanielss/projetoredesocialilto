package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import HardCore.DB;
import com.iliketo.dao.IliketoDAO;


public class UserController {
	
	private String tableUsers = "users";
	private String dbmembers = "dbmembers";
	
	/**
	 * Validate all field form
	 * @param db
	 * @param request
	 * @return
	 */
	public boolean validateFieldsForm(DB db, HttpServletRequest request){
		
		if(!validateEmail(db, request)){
			return false;
		}else if(!validateUsername(db, request)){
			return false;
		}
		return true;
		
	}

	/**
	 * Validate email exists
	 * @param db
	 * @param request
	 * @return
	 */
	public boolean validateEmail(DB db, HttpServletRequest request){
		String msg = "";
		if(IliketoDAO.readRecordExistsTable(db, tableUsers, "email", request.getParameter("email"))){
			msg = "<br>" + "Please verify, user already exists for this email!"; //msg padr�o do erro para outros idioma "<br>" + text.display("register.error.exists");
			System.out.println("Log - Email = " + request.getParameter("email") + " already exists");
			request.setAttribute("msgError", msg);
			return false;
		}
		return true;
	}
	
	/**
	 * Validate username exists
	 * @param db
	 * @param request
	 * @return
	 */
	public boolean validateUsername(DB db, HttpServletRequest request){
		String msg = "";
		if(IliketoDAO.readRecordExistsTable(db, tableUsers, "username", request.getParameter("username"))){
			msg = "<br>" + "Please verify, username already exists!"; //msg padr�o do erro para outros idioma "<br>" + text.display("register.error.exists");
			System.out.println("Log - Username = " + request.getParameter("username") + " already exists");
			request.setAttribute("msgError", msg);
			return false;
		}
		return true;
		
	}
	
	public String getIdCreatedUser(DB db, String nameColumn, String value){
		
		return IliketoDAO.readIdCreatedTable(db, tableUsers, nameColumn, value);
		
	}
	
}