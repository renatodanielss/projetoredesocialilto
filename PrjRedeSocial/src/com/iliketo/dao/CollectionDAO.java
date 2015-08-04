package com.iliketo.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

import com.iliketo.model.Collection;
import com.iliketo.util.ColumnsSingleton;
import com.iliketo.util.Str;

public class CollectionDAO extends GenericDAO{
	
	public CollectionDAO(DB db, HttpServletRequest request){
		super(db, "dbcollection", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos da CollectionDAO abaixo
	
	
	/**
	 * Metodo atualiza somente os campos de configuracao de notificacao da colecao
	 * @param collection
	 */
	public void updateNotificationSettings(Collection collection){
			
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
			String SQLupdate = col1 + "='" + collection.getNotificCollection() + "', "
					+ col2 + "='" + collection.getNotificItem() + "', "
					+ col3 + "='" + collection.getNotificVideo() + "', "
					+ col4 + "='" + collection.getNotificEvent() + "', "
					+ col5 + "='" + collection.getNotificAnnounce() + "', "
					+ col6 + "='" + collection.getNotificTopic() + "', "
					+ col7 + "='" + collection.getNotificComment() + "'";
			
			super.getDb().updateSet(dataid, "id", collection.getIdCollection(), SQLupdate);
			
	}
	
	public void deleteCollection(String idDeleteCollection) {

		DB db = super.getDb();
		ArrayList<String> listNamesPhotoDelete = new ArrayList<String>(); //lista de imagens para deletar
		
		String namePhotoDelete = IliketoDAO.getValueOfDatabase(db, "path_photo_collection", "dbcollection", "id_collection", idDeleteCollection);		
		listNamesPhotoDelete.add(namePhotoDelete); 	//adiciona imagem na lista
		IliketoDAO.deleteDadosIliketo(db, "dbcollection", "id", idDeleteCollection); //metodo deleta dados na database
		
		//Enquanto existir itens na colecao
		while(IliketoDAO.readRecordExistsDatabase(db, "dbcollectionitem", "fk_collection_id", idDeleteCollection)){
			
			String idRealItem = IliketoDAO.getValueOfDatabase(db, "id", "dbcollectionitem", "fk_collection_id", idDeleteCollection);
			String namePhotoItem = IliketoDAO.getValueOfDatabase(db, "path_photo_item", "dbcollectionitem", "id", idRealItem);		
			listNamesPhotoDelete.add(namePhotoItem); 
			IliketoDAO.deleteDadosIliketo(db, "dbcollectionitem", "id", idRealItem);
		}
		
		//Enquanto existir video na colecao
		while(IliketoDAO.readRecordExistsDatabase(db, "dbcollectionvideo", "fk_collection_id", idDeleteCollection)){
			
			String idRealVideo = IliketoDAO.getValueOfDatabase(db, "id", "dbcollectionvideo", "fk_collection_id", idDeleteCollection);
			String nameFileVideo = IliketoDAO.getValueOfDatabase(db, "path_file_video", "dbcollectionvideo", "id", idRealVideo);		
			listNamesPhotoDelete.add(nameFileVideo); 
			IliketoDAO.deleteDadosIliketo(db, "dbcollectionvideo", "id", idRealVideo);
		}
			
		String localImagePath = (String) super.getRequest().getSession().getAttribute(Str.STORAGE);			//local da pasta das imagens armazenadas
		super.deleteListFilesPhysically(listNamesPhotoDelete, localImagePath); //metodo deleta uma lista de arquivos fisicamente
		
		
		//calcula e salva espaco usado de armazenamento
		this.calculateTotalFilesMemberInBytes();
		
	}
	
}