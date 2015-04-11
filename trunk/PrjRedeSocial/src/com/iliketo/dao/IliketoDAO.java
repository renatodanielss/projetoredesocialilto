package com.iliketo.dao;

import java.util.HashMap;

import com.iliketo.util.ColumnsSingleton;

import HardCore.Configuration;
import HardCore.DB;
import HardCore.Data;
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
			String SQL = "select * from " + table + " where " + nameColumn + " ilike " + db.quote(value);
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
		
		if (db == null) return false;
		
		if (! value.equals("")) {
			Databases database = new Databases(new Text());
			database.read(db, new Configuration(), nameDatabase);
					
			String dataid = database.getTable();
			String colid = "";
			if(!nameColumn.equals("id")){
				colid = "col" + (String) database.namedcolumns.get(nameColumn).get("id");	
			}else{
				colid = "id";
			}
			
			String SQL = "select * from " + dataid + " where " + colid + " ilike " + db.quote(value);
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				return true;
			}
		}
		
		return false;
		
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
	
	/**
	 * Método responsavel por excluir os dados do membro na tabela users
	 * nameDatabase - database
	 * nameColumn - coluna para comparar
	 * value - valor a ser comparado
	 * @param db
	 * @param database
	 * @param Column
	 * @param id
	 */
	public static void deleteDadosMembroIliketo(DB db, String nameDatabase, String nameColumn, String value) {
		
		if (db == null) return;
		
		if ((value != null) && (! value.equals("")) && nameDatabase.equals("users")) {			
			db.delete(nameDatabase, nameColumn, value);
		}
	}
	
	/**
	 * Método responsavel por deletar qualquer conteudo de uma database/table
	 * nameDatabase - database
	 * nameColumn - coluna para comparar
	 * value - valor a ser comparado
	 * @param db
	 * @param database
	 * @param Column
	 * @param id
	 */
	public static void deleteDadosIliketo(DB db, String nameDatabase, String nameColumn, String value) {
		
		if (db == null) return;
		
		if ((value != null) && (! value.equals(""))) {
			
			Databases database = new Databases(new Text());
			database.read(db, new Configuration(), nameDatabase);
					
			String dataid = database.getTable();
			String colid = "";
			if(!nameColumn.equals("id")){
				colid = "col" + (String) database.namedcolumns.get(nameColumn).get("id");	
			}else{
				colid = "id";
			}
			
			db.delete(dataid, colid, value);
		}
	}

	/**
	 * Método genérico retorna o valor de um campo especifico, informar o campo de retorno no valueOf
	 * valueOf - valor do campo que deseja retorno
	 * nameDatabase - database
	 * nameColumn - coluna para comparar
	 * value - valor a ser comparado
	 * @param db
	 * @param valueOf
	 * @param nameDatabase
	 * @param nameColumn
	 * @param value
	 * @return
	 */
	public static String getValueOfDatabase(DB db, String valueOf, String nameDatabase, String nameColumn, String value) {
		
		if (db == null) return null;
		
		Databases database = new Databases(new Text());
		database.read(db, new Configuration(), nameDatabase);
				
		String dataid = database.getTable();
		String colid = "";
		if(!nameColumn.equals("id")){
			colid = "col" + (String) database.namedcolumns.get(nameColumn).get("id");	
		}else{
			colid = "id";
		}
		
		Data data = new Data();
		HashMap<String, String> rows = data.readWhereILiketo(db, dataid, colid, value);
		
		String colid_val = "";
		if(!valueOf.equals("id")){
			colid_val = "col" + (String) database.namedcolumns.get(valueOf).get("id");	
		}else{
			colid_val = "id";
		}
		
		if(rows != null){
			if(rows.get(colid_val) != null){			
				String valueFind = "" + rows.get(colid_val);			
				return valueFind;
			}
		}
		
		return null;
	}
	
	/**
	 * Método static responsável por verificar se uma colecao pertence a uma categoria.
	 * Se a colecao informada no parametro conter o id da categoria retorna true
	 * @param db
	 * @param idCollection
	 * @param idCategory
	 * return exists
	 */
	public static boolean validateCollectionCategory(DB db, String idCollection, String idCategory){

		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		String SQL = "select c.id_collection from dbcollection c where c.id_collection = "+idCollection+" and c.fk_category_id = " + idCategory;
		
		String[][] tableAlias = { {"dbcollection", "c"} };
		SQL = CS.transformSQLReal(SQL, tableAlias);	
		
		HashMap<String,String> row = db.query_record(SQL);		
		if (row != null) {
			return true;	//find true
		}
		
		return false;
	}
}
