package com.iliketo.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

import com.iliketo.model.Collection;
import com.iliketo.model.annotation.ColumnILiketo;
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
	
	
	/**
	 * Metodo retorna uma lista de colecao de um membro.
	 */
	public List<Collection> listCollectionByUser(String idMember){
		
		DB db = super.getDb();
		String nameDatabase = super.getNameDatabase();		
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		String dataid = CS.getDATA(db, nameDatabase);
		
		String SQL = "SELECT * FROM " + dataid + " WHERE " + CS.getCOL(db, nameDatabase, "id_member") + " = '" + idMember +"';";
		HashMap<String, HashMap<String, String>> rows = db.query_records(SQL);
		
		List<Collection> list = new ArrayList<Collection>();
		try {
			
			if(rows != null){
				for(String record : rows.keySet()){
					Collection object = new Collection();
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
