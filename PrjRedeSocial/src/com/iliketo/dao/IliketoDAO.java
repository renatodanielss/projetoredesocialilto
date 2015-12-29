package com.iliketo.dao;

import java.util.HashMap;

import HardCore.Configuration;
import HardCore.DB;
import HardCore.Data;
import HardCore.Databases;
import HardCore.Text;

import com.iliketo.bean.AnnounceJB;
import com.iliketo.util.ColumnsSingleton;

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
		
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		
		String dataid = CS.getDATA(db, nameDatabase);		
		String colid = CS.getCOL(db, nameDatabase, nameColumn);

		Data data = new Data();
		HashMap<String, String> rows = data.readWhereILiketo(db, dataid, colid, value);
		
		String colid_val = "";
		if(!valueOf.equals("id")){
			colid_val = CS.getCOL(db, nameDatabase, valueOf);	
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
	
	public static boolean validateMemberInCategory(DB db, String idCategory, String myUserid){

		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		String SQL1 = "select c.id_collection from dbcollection c where c.fk_user_id = "+myUserid+" and c.fk_category_id = " + idCategory;
		String SQL2 = "select i.id_interest from dbinterest i where i.fk_user_id = "+myUserid+" and i.fk_category_id = " + idCategory;
		
		String[][] tableAlias1 = { {"dbcollection", "c"} };
		SQL1 = CS.transformSQLReal(SQL1, tableAlias1);
		String[][] tableAlias2 = { {"dbinterest", "i"} };
		SQL2 = CS.transformSQLReal(SQL2, tableAlias2);	
		
		HashMap<String,String> row1 = db.query_record(SQL1);
		HashMap<String,String> row2 = db.query_record(SQL2);	
		if (row1 != null || row2 != null) {
			return true;	//find true
		}
		
		return false;
	}
	
	/**
	 * Método genérico retorna a quantidade registros encontrados
	 * valueCount - nome do campo que deseja contar
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
	public static String getValueCountDatabase(DB db, String valueCount, String nameDatabase, String nameColumn, String value){
		String count = "0";
		
		if (db == null) return null;
		
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		
		String SQL = "SELECT COUNT(t1." +valueCount+ ") as total FROM " + nameDatabase + " as t1 WHERE t1." + nameColumn +"="+ value;
		String[][] tableAlias = { {nameDatabase, "t1" } };
		SQL = CS.transformSQLReal(SQL, tableAlias);
		
		HashMap<String, String> rows = db.query_record(SQL);
		
		if(rows != null){
			if(rows.get("total") != null){			
				count = "" + rows.get("total");			
			}
		}
		
		return count;
		
	}
	
	/**
	 * Metodo consulta e retorna o objeto AnnounceJB do banco de dados
	 * Passar o id para comparar com o campo id_announce da dbannounce
	 * Se não existir anuncio retorna null
	 * @param db
	 * @param idAnnounce
	 * @return AnnounceJB
	 */
	public static AnnounceJB readAnnounceJB(DB db, String idAnnounce){
		AnnounceJB announceJB = null;		
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		
		String SQL = "SELECT * FROM dbannounce as t1 WHERE t1.id_announce = '" + idAnnounce +"';";
		String[][] tableAlias = { {"dbannounce", "t1" } };
		SQL = CS.transformSQLReal(SQL, tableAlias);
		
		HashMap<String, String> rows = db.query_record(SQL);
		
		if(rows != null){
			announceJB = new AnnounceJB();
			announceJB.setIdAnnounce(idAnnounce);
			announceJB.setIdItem(rows.get(CS.getCOL(db, "dbannounce","fk_item_id")));
			announceJB.setIdCollection(rows.get(CS.getCOL(db, "dbannounce","fk_collection_id")));
			announceJB.setIdCategory(rows.get(CS.getCOL(db, "dbannounce","fk_category_id")));
			announceJB.setIdMember(rows.get(CS.getCOL(db, "dbannounce","fk_user_id")));
			announceJB.setNameCategory(rows.get(CS.getCOL(db, "dbannounce","name_category")));
			announceJB.setTitle(rows.get(CS.getCOL(db, "dbannounce","title")));
			announceJB.setDescription(rows.get(CS.getCOL(db, "dbannounce", "description")));
			announceJB.setTypeAnnounce(rows.get(CS.getCOL(db, "dbannounce", "type_announce")));
			announceJB.setPriceFixed(rows.get(CS.getCOL(db, "dbannounce", "price_fixed")));
			announceJB.setPriceInitial(rows.get(CS.getCOL(db, "dbannounce", "price_initial")));
			announceJB.setBidActual(rows.get(CS.getCOL(db, "dbannounce", "bid_actual")));
			announceJB.setLasting(rows.get(CS.getCOL(db, "dbannounce", "lasting")));
			announceJB.setTotalBids(rows.get(CS.getCOL(db, "dbannounce", "total_bids")));
			announceJB.setDateCreated(rows.get(CS.getCOL(db, "dbannounce", "date_created")));
			announceJB.setDateUpdated(rows.get(CS.getCOL(db, "dbannounce", "date_updated")));
			announceJB.setPath_photo_ad(rows.get(CS.getCOL(db, "dbannounce", "path_photo_ad")));
		}
		
		return announceJB;
	}
	
}
