package com.iliketo.dao;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

import com.iliketo.model.Likes;
import com.iliketo.util.ColumnsSingleton;
import com.iliketo.util.Str;

public class LikesDAO extends GenericDAO{
	
	public LikesDAO(DB db, HttpServletRequest request){
		super(db, "dblikes", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos do LikesDAO abaixo
	
	public void curtir(Likes curtir){
		super.create(curtir);
		log.info("LIKE - post_type: " +curtir.getPostType()+ " fk_post_id: " +curtir.getIdPost()+ " fk_user_id: " + curtir.getIdMember());
	}
	
	public void excluirCurtir(String tipoPost, String idPost, String myUserid) {		
		DB db = super.getDb();
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		String SQL = "t1.post_type = '" +tipoPost+ "' and t1.fk_post_id = '" +idPost+ "' and t1.fk_user_id = '" + myUserid+"';";		
		String[][] aliasSQL = { {"dblikes", "t1"} };
		SQL = CS.transformSQLReal(SQL, aliasSQL);
		String table = CS.getDATA(db, super.getNameDatabase());
		db.deleteWhere(table + " as t1", SQL);
		log.info("***data.delete***");
		log.info("UNLIKE - post_type: " +tipoPost+ " fk_post_id: " +idPost+ " fk_user_id: " + myUserid);
	}
	
	public boolean jaCurtiu(String tipoPost, String idPost, String myUserid) {		
		DB db = super.getDb();
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		String SQL = "select t1.id from dblikes as t1 where " 
				+ "t1.post_type = '" +tipoPost+ "' and t1.fk_post_id = '" +idPost+ "' and t1.fk_user_id = '" + myUserid+"';";		
		String[][] aliasSQL = { {"dblikes", "t1"} };
		SQL = CS.transformSQLReal(SQL, aliasSQL);
		HashMap<String,String> row = db.query_record(SQL);
		if (row != null) {
			return true;
		}
		return false;
	}
	
	public String getTotalCurtidas(String tipoPost, String idPost) {		
		DB db = super.getDb();
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		String SQL = "SELECT COUNT(t1.id) as total FROM dblikes as t1 WHERE "
				+ "t1.post_type = '" +tipoPost+ "' and t1.fk_post_id = '" +idPost+ "';";		
		String[][] tableAlias = { {"dblikes", "t1" } };
		SQL = CS.transformSQLReal(SQL, tableAlias);		
		HashMap<String, String> rows = db.query_record(SQL);		
		String count = "0";
		if(rows != null){
			if(rows.get("total") != null){			
				count = "" + rows.get("total");			
			}
		}
		return count;
	}
	
	
}
