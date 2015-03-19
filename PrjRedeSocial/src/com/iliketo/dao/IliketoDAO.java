package com.iliketo.dao;

import java.util.HashMap;

import HardCore.Configuration;
import HardCore.DB;
import HardCore.Databases;
import HardCore.Text;

public class IliketoDAO {
	
	/**
	 * Método static responsável por verificar se o valor de um campo na tabela do banco de dados existe.
	 * Se existir retorna true
	 * @param db
	 * @param id
	 * return exists
	 */
	public static boolean readRecordExistsTable(DB db, String table, String nameColumn, String value) {
		
		if (db == null) return false;
		if (! value.equals("")) {
			String SQL = "select * from " + table + " where " + nameColumn + "=" + db.quote(value);
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Método static responsável por verificar se o valor de um campo na database Asbru existe.
	 * Se existir retorna true
	 * @param db
	 * @param id
	 * return exists
	 */
	public static boolean readRecordExistsDatabase(DB db, String nameDatabase, String nameColumn, String value) {
		
		Databases database = new Databases(new Text());
		database.read(db, new Configuration(), nameDatabase);
				
		String dataTable = database.getTable();				//retorna o nome real no banco de dados, exemplo data7, data8
		String dataCol = "col" + (String) database.namedcolumns.get(nameColumn).get("id");;	//retorna a coluna real no banco de dados, exemplo col1, col2
		
		return readRecordExistsTable(db, dataTable, dataCol, value);	//chama método para consultar no banco de dados	
		
	}
	
	/**
	 * Método static responsável por retornar o valor do id do usuario registrado na tabela users
	 * Se existir retorna String id
	 * obs método utilizado somente para cadastro de um novo usuario, recuperar id para gravar na dbmmbers.
	 * @param db
	 * @param nameColumn coluna comparar
	 * @param value valor comparado
	 * return String Id
	 */
	public static String readIdUserCreatedTable(DB db, String table, String columnUsername, String value) {
		
		if (db == null) return null;
		if (! value.equals("")) {
			String SQL = "select id from " + table + " where " + columnUsername + "=" + db.quote(value);
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				String idCreated = row.get("id");
				return idCreated;
			}
		}
		
		return null;
	}
	
	/**
	 * Método valida e confirma username e password do membro
	 * @param db
	 * @param nameDatabase
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean authenticUsernamePassword(DB db, String nameDatabase, String username, String password) {
		
		if (db == null) return false;
		
		if (username != null && !username.equals("") && password != null && !password.equals("")) {
			
			String SQL = "select * from users where username = " + db.quote(username)
					+ " and password = " + db.quote(password);
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				return true;
			}
		}
		
		return false;
	}
}
