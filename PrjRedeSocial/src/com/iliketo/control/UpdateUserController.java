package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import HardCore.DB;
import com.iliketo.dao.IliketoDAO;


public class UpdateUserController {
	
	private String tableUsers = "users";
	private String dbmembers = "dbmembers";
	private String msg = "";
	
	/**
	 * Validate all field form
	 * @param db
	 * @param request
	 * @return
	 */
	public boolean validateFieldsForm(DB db, HttpServletRequest request){
		boolean erro = true;
		
		if(!validateEmail(db, request)){
			erro = false;
		}
		if(!validateUsername(db, request)){
			erro = false;
		}
		return erro;
		
	}

	/**
	 * Validate email exists
	 * @param db
	 * @param request
	 * @return
	 */
	public boolean validateEmail(DB db, HttpServletRequest request){
		if (request.getParameter("email") != null){
			if(IliketoDAO.readRecordExistsTable(db, tableUsers, "email", request.getParameter("email"))){
				msg = "User already exists for this email!"; //msg padrão do erro para outros idioma "<br>" + text.display("register.error.exists");
				System.out.println("Log - Email = " + request.getParameter("email") + " already exists");
				request.setAttribute("msgError", msg);
				return false;
			}
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
		if (request.getParameter("username") != null){
			if(IliketoDAO.readRecordExistsTable(db, tableUsers, "username", request.getParameter("username"))){
				msg = "Username already exists!"; //msg padrão do erro para outros idioma "<br>" + text.display("register.error.exists");
				System.out.println("Log - Username = " + request.getParameter("username") + " already exists");
				request.setAttribute("msgError", msg);
				return false;
			}
		}
		return true;
		
	}
	
	/**
	 * Validate password exists
	 * @param db
	 * @param request
	 * @return
	 */
	public boolean validatePassword(DB db, HttpServletRequest request){
		if (request.getParameter("old_password") != null){
			if(!IliketoDAO.authenticUsernamePassword(db, tableUsers, request.getParameter("username"), request.getParameter("old_password"))){
				msg = "Old password is not correct!"; //msg padrão do erro para outros idioma "<br>" + text.display("register.error.exists");
				System.out.println("Log - Password = " + request.getParameter("username") + " is not correct!");
				request.setAttribute("msgError", msg);
				return false;
			}
		}
		return true;
		
	}
	
	/**
	 * M�todo realiza a pesquisa do usu�rio na tabela users, passando o username e o valor para comparar
	 * Ent�o retorna o id encontrado
	 * @param db
	 * @param columnUsername
	 * @param value
	 * @return Id do usuario
	 */
	public String getIdCreatedUser(DB db, String columnUsername, String value){
		
		return IliketoDAO.readIdUserCreatedTable(db, tableUsers, columnUsername, value);
		
	}
	
}