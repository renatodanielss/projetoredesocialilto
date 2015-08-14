package com.iliketo.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

import com.iliketo.model.Interest;
import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.util.ColumnsSingleton;

public class InterestDAO extends GenericDAO{
	
	public InterestDAO(DB db, HttpServletRequest request){
		super(db, "dbinterest", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos da InterestDAO abaixo
	
	
	/**
	 * Metodo atualiza somente os campos de configuracao de notificacao do interesse
	 * @param Interest
	 */
	public void updateNotificationSettings(Interest Interest){
			
			DB db = super.getDb();
			String nameDatabase = super.getNameDatabase();
			ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
			
			String dataid = CS.getDATA(db, nameDatabase);
			String col1 = CS.getCOL(db, nameDatabase, "notific_collection");
			String col2 = CS.getCOL(db, nameDatabase, "notific_item");
			String col3 = CS.getCOL(db, nameDatabase, "notific_video");
			String col4 = CS.getCOL(db, nameDatabase, "notific_event");
			String col5 = CS.getCOL(db, nameDatabase, "notific_announce");
			String col6 = CS.getCOL(db, nameDatabase, "notific_topic");
			String col7 = CS.getCOL(db, nameDatabase, "notific_comment");
			String SQLupdate = col1 + "='" + Interest.getNotificCollection() + "', "
					+ col2 + "='" + Interest.getNotificItem() + "', "
					+ col3 + "='" + Interest.getNotificVideo() + "', "
					+ col4 + "='" + Interest.getNotificEvent() + "', "
					+ col5 + "='" + Interest.getNotificAnnounce() + "', "
					+ col6 + "='" + Interest.getNotificTopic() + "', "
					+ col7 + "='" + Interest.getNotificComment() + "'";
			
			db.updateSet(dataid, "id", Interest.getIdInterest(), SQLupdate);
			
	}
	
	
	/**
	 * Metodo deleta um interesse do membro
	 * @param idDeleteInterest
	 */
	public void deleteInterest(String idDeleteInterest) {
		
		if (!idDeleteInterest.equals(null) && !idDeleteInterest.equals("")){
			IliketoDAO.deleteDadosIliketo(super.getDb(), "dbinterest", "id", idDeleteInterest); //metodo deleta dados do interesse no bd
		}
		
	}
	

	/**
	 * Metodo retorna uma lista de interesse de um membro.
	 */
	public List<Interest> listInterestByUser(String idMember){
		
		DB db = super.getDb();
		String nameDatabase = super.getNameDatabase();
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		String dataid = CS.getDATA(db, nameDatabase);
		
		String SQL = "SELECT * FROM " + dataid + " WHERE " + CS.getCOL(db, nameDatabase, "fk_user_id") + " = '" + idMember +"';";
		HashMap<String, HashMap<String, String>> rows = db.query_records(SQL);
		
		List<Interest> list = new ArrayList<Interest>();
		try {
			
			if(rows != null){
				for(String record : rows.keySet()){
					Interest object = new Interest();
					for(Field atributo : object.getClass().getDeclaredFields()) {						
						atributo.setAccessible(true);
						ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);
						if(coluna != null && !coluna.name().equals("")){
							//seta no objeto o valor do registro, recuperado pelo nome da coluna na anotacao do objeto
							atributo.set(object, rows.get(record).get(CS.getCOL(db, nameDatabase, coluna.name())));
						}
					}
					//atributos da superclasse ContentILiketo
					Field f1 = object.getClass().getSuperclass().getDeclaredField("id");
					Field f2 = object.getClass().getSuperclass().getDeclaredField("dateCreated");
					Field f3 = object.getClass().getSuperclass().getDeclaredField("dateUpdated");
					f1.setAccessible(true);
					f2.setAccessible(true);
					f3.setAccessible(true);
					f1.set(object, rows.get(record).get(CS.getCOL(db, nameDatabase, "id")));
					f2.set(object, rows.get(record).get(CS.getCOL(db, nameDatabase, "date_created")));
					f3.set(object, rows.get(record).get(CS.getCOL(db, nameDatabase, "date_updated")));
					
					list.add(object);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}
