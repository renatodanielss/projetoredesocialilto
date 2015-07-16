package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

import com.iliketo.model.Interest;
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
}
