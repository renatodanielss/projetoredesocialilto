package com.iliketo.dao;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import HardCore.Common;
import HardCore.Configuration;
import HardCore.DB;
import HardCore.Data;
import HardCore.Databases;
import HardCore.Fileupload;
import HardCore.Text;

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.FileILiketo;
import com.iliketo.model.annotation.IdILiketo;
import com.iliketo.util.ColumnsSingleton;
import com.iliketo.util.Str;

public abstract class GenericDAO {
	
	private DB db;
	private String nameDatabase;
	private HttpServletRequest request;
	
	public GenericDAO(DB db, String nameDatabase, HttpServletRequest request){
		this.db = db;
		this.nameDatabase = nameDatabase;
	}
	
		
	//metodos CRUD generico de operacoes basicas no banco de dados
	
	/**
	 * M�todo cria um novo registro no banco de dados com os dados do objeto
	 * Se houver arquivos e o atributo do objeto conter a annotation FileILiketo, os mesmo s�o criados e gravados
	 * @param object
	 * @return String id do novo registro
	 */
	public String create(Object object) {
		String nameIdPrimaryKey = "";
		Fileupload fileupload = new Fileupload(null, null, null);
		
		//identifica cada atributo do objeto para setar o nome da coluna da database no filepost
		for(Field atributo : object.getClass().getDeclaredFields()) {			
			try {
				atributo.setAccessible(true);
				Object value = atributo.get(object);
				if(value != null){
					ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);	//@ColumnILiketo
					if(coluna != null && !coluna.name().equals("")){
						fileupload.setParameter(coluna.name(), ""+value);//exemplo myrequest.setParameter("nameCollection", "valor")
					}
				}
				IdILiketo id = atributo.getAnnotation(IdILiketo.class);//@IdILiketo
				if(id != null){
					ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);	//@ColumnILiketo
					nameIdPrimaryKey = coluna.name(); //exemplo id_member, id_collection
				}
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}				
		}

		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		Databases database = new Databases(new Text());
		database.read(db, new Configuration(), nameDatabase);		
		Data data = new Data();
		data.getFormData(database.columns, fileupload);
		data.adjustContent(database.columns);
		data.setCreated(database.columns, timestamp,"username");
		data.setUpdated(database.columns, timestamp, "username");
		data.create(db, "data" + database.getId(), database.columns);
		
		
		String idRegister = data.getId();	//recupera id do registro gerado pelo sistema
		Fileupload filepostUpateID = new Fileupload(null, null, null);
		filepostUpateID.setParameter(nameIdPrimaryKey, idRegister);	//exemplo: filepostUpateID.setParameter("id_member", "123");
		
		String created = data.getCreated(database.columns);
		String createdby = data.getCreatedBy(database.columns);
		data.getFormData(database.columns, filepostUpateID);
		data.adjustContent(database.columns);
		data.setCreated(database.columns, created, createdby);
		data.setUpdated(database.columns, timestamp, "username");
		data.update(db, "data" + database.getId(), database.columns);
		
		System.out.println("***data.create***");
		Iterator it = fileupload.getParameterNames();
		while(it.hasNext()){
			String s = (String) it.next();
			System.out.println("name input/column: " + s + " - value: " + fileupload.getParameter(s));
		}
		System.out.println();
		
		if(idRegister != null && !idRegister.equals("") && !idRegister.equals("null")){
			return idRegister;
		}
		return null;
	}
	
	/**
	 * M�todo usa um vetor de objetos do mesmo tipo para gravar novos registros no banco de dados
	 * Os objetdos devem conter a annotation FileILiketo, os arquivos s�o criados e gravados
	 * @param object
	 * @return String id do novo registro
	 */
	public void creates(Object[] arrayObjects) {
		String nameIdPrimaryKey = "";
		
		for(int i= 0; i < arrayObjects.length; i++){
			
			Fileupload fileupload = new Fileupload(null, null, null);
			Object object = arrayObjects[i];
			
			for(Field atributo : object.getClass().getDeclaredFields()) {			
				try {
					atributo.setAccessible(true);
					Object value = atributo.get(object);
					if(value != null){
						ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);	//@ColumnILiketo
						if(coluna != null && !coluna.name().equals("")){
							fileupload.setParameter(coluna.name(), ""+value);							
						}
					}
					IdILiketo id = atributo.getAnnotation(IdILiketo.class);//@IdILiketo
					if(id != null){
						ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);	//@ColumnILiketo
						nameIdPrimaryKey = coluna.name(); //exemplo id_member, id_collection
					}
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			Databases database = new Databases(new Text());
			database.read(db, new Configuration(), nameDatabase);
			
			Data data = new Data();
			data.getFormData(database.columns, fileupload);
			data.adjustContent(database.columns);
			data.setCreated(database.columns, timestamp, "username");
			data.setUpdated(database.columns, timestamp, "username");
			data.create(db, "data" + database.getId(), database.columns);
			
			String idRegister = data.getId();	//recupera id do registro gerado pelo sistema
			Fileupload filepostUpateID = new Fileupload(null, null, null);
			filepostUpateID.setParameter(nameIdPrimaryKey, idRegister);	//exemplo: filepostUpateID.setParameter("id_member", "123");
			
			String created = data.getCreated(database.columns);
			String createdby = data.getCreatedBy(database.columns);
			data.getFormData(database.columns, filepostUpateID);
			data.adjustContent(database.columns);
			data.setCreated(database.columns, created, createdby);
			data.setUpdated(database.columns, timestamp, "username");
			data.update(db, "data" + database.getId(), database.columns);
			
			System.out.println("***data.create***");
			Iterator it = fileupload.getParameterNames();
			while(it.hasNext()){
				String s = (String) it.next();
				System.out.println("name input/column: " + s + " - value: " + fileupload.getParameter(s));
			}
			System.out.println();
		}
	}
	
	/**
	 * Metodo atualiza registro do objeto no banco de dados
	 * Objeto deve conter o valor do id chave para atualizar
	 * Se conter files, imagens ou video, deleta o antigo e atualiza o novo
	 * @param object
	 */
	public void update(Object object){
		
		Fileupload filepost = new Fileupload(null, null, null);
		String idReal = "";	//id real para update
		Object old = null;
		try {
			for(Field atributo : object.getClass().getDeclaredFields()) {
				IdILiketo id = atributo.getAnnotation(IdILiketo.class);
				if(id != null){
					String value = (String) atributo.get(object);
					ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);
					old = readByColumn(coluna.name(), value, object.getClass());
				}
			}
			if(old != null){
				Field f = old.getClass().getSuperclass().getDeclaredField("id");
				f.setAccessible(true);
				idReal = (String) f.get(old);		//idReal asbru
			}
			for(Field atributo : object.getClass().getDeclaredFields()) {
				atributo.setAccessible(true);
				Object value = atributo.get(object);
				if(value != null){
					ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);
					if(coluna != null && !coluna.name().equals("")){
						//verifica antes nome do arquivo antigo para deletar
						FileILiketo file = atributo.getAnnotation(FileILiketo.class);
						if(file != null){
							//deleta arquivo antigo fisicamente para atualizar o novo
							String nameFileDelete = (String) atributo.get(old);			//nome do arquivo no objecto do registro antigo antes do update
							if(nameFileDelete != null && !nameFileDelete.equals("")){
								String localFilePath = (String) request.getSession().getAttribute(Str.STORAGE); 		//local arquivado
								//verifica arquivos defaults
								if(!nameFileDelete.equals("avatar_male.png") && !nameFileDelete.equals("avatar_female.png") && !nameFileDelete.equals("avatar_store.png")){
									deleteFilePhysically(nameFileDelete, localFilePath);
								}
							}
							filepost.setParameter(coluna.name(), ""+value);	//set no filepost "coluna", "valor" para atualizar no BD
						}else{
							filepost.setParameter(coluna.name(), ""+value);	//set no filepost "coluna", "valor" para atualizar no BD
						}
					}				
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

		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		Databases database = new Databases(new Text());
		database.read(db, new Configuration(), nameDatabase);				
				
		Data data = new Data();
		data.read(db, "data" + database.getId(), idReal);		
		String created = data.getCreated(database.columns);
		String createdby = data.getCreatedBy(database.columns);
		data.getFormData(database.columns, filepost);
		data.adjustContent(database.columns);
		data.setCreated(database.columns, created, createdby);
		data.setUpdated(database.columns, timestamp, "username");
		data.update(db, "data" + database.getId(), database.columns);
		System.out.println("***data.update***");
		Iterator it = filepost.getParameterNames();
		while(it.hasNext()){
			String s = (String) it.next();
			System.out.println("name input/column: " + s + " - value: " + filepost.getParameter(s));
		}
		System.out.println();
	}
	
	/**
	 * Metodo utilizado somente pela classe GenericDAO para deletar um arquivo fisicamente
	 * @param nameFileDelete
	 * @param localFilePath
	 */
	private void deleteFilePhysically(String nameFileDelete, String localFilePath){
		
		if(nameFileDelete != null && !nameFileDelete.equals("")){
			String pathFileName = localFilePath + nameFileDelete;
			try {
				
				Common.deleteFile(pathFileName);
				System.out.println("Log - Delete File ok local: " + pathFileName);
				
			} catch (Exception e) {
				System.out.println("Log - Error Delete File local: " + pathFileName);
				e.printStackTrace();
			}
		} else {
			System.out.println("Log - Delete File name: " +nameFileDelete+ " no exists!");
		}
	}
	
	/**
	 * Metodo deleta registro do objeto no banco de dados
	 * Objeto deve conter o valor do id chave para deletar
	 * N�o pode conter um atributo do tipo file, este metodo generico nao deleta arquivo fisicamente em cascata
	 * @param object
	 */
	public void delete(Object object){
		
		String nameIdPrimaryKey = "";
		String value = "";
		
		for(Field atributo : object.getClass().getDeclaredFields()) {			
			try {
				atributo.setAccessible(true);
				IdILiketo id = atributo.getAnnotation(IdILiketo.class);//@IdILiketo
				if(id != null){
					value = (String) atributo.get(object);
					ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);	//@ColumnILiketo
					nameIdPrimaryKey = coluna.name(); //exemplo id_member, id_collection
				}
				FileILiketo file = atributo.getAnnotation(FileILiketo.class);
				if(file != null){
					new Exception("N�o pode conter um atributo do tipo file, este metodo generico nao deleta arquivo fisicamente em cascata");
				}
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if ((nameIdPrimaryKey != null) && (!nameIdPrimaryKey.equals("")) && (value != null) && (!value.equals(""))) {
			ColumnsSingleton cs = ColumnsSingleton.getInstance(db);
			String dataid = cs.getDATA(db, nameDatabase);
			//db.delete(dataid, nameIdPrimaryKey, value);
		}
	}
	
	/**
	 * Metodo deleta registro da tabela users pelo valor da chave id_member
	 * @param idMember
	 */
	public void deleteTableUsers(String id_member) {
		if ((id_member != null) && (! id_member.equals(""))) {			
			//db.delete("users", "id", id_member);
		}
	}
	
	/**
	 * Metodo pesquisa o registro no banco de dados pelo id e retorna o objeto com os dados
	 * Obs passar o id do registro e type Class do objeto para popular.
	 * @param id
	 * @param clazz
	 * @return Object do type class do parametro
	 */
	public Object readById(String id, Class clazz){
		
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		String dataid = CS.getDATA(db, nameDatabase);
		
		String SQL = "SELECT * FROM " + dataid + " WHERE id = '" + id +"';";
		HashMap<String, String> row = db.query_record(SQL);
		
		Object object = null;
		try {
				
			object = clazz.newInstance();			
			if(row != null){
				for(Field atributo : object.getClass().getDeclaredFields()) {						
					atributo.setAccessible(true);
					ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);
					if(coluna != null && !coluna.name().equals("")){
						atributo.set(object, row.get(CS.getCOL(db, nameDatabase, coluna.name())));	//seta no objeto o valor do registro, recuperado pelo nome da coluna na anotacao do objeto
					}
				}
				//atributos da superclasse ContentILiketo
				Field f1 = object.getClass().getSuperclass().getDeclaredField("id");
				Field f2 = object.getClass().getSuperclass().getDeclaredField("dateCreated");
				Field f3 = object.getClass().getSuperclass().getDeclaredField("dateUpdated");
				f1.setAccessible(true);
				f2.setAccessible(true);
				f3.setAccessible(true);
				f1.set(object, row.get(CS.getCOL(db, nameDatabase, "id")));
				f2.set(object, row.get(CS.getCOL(db, nameDatabase, "date_created")));
				f3.set(object, row.get(CS.getCOL(db, nameDatabase, "date_updated")));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		return object;
	}
	
	/**
	 * Metodo pesquisa o registro no banco de dados pela coluna e retorna o objeto com os dados
	 * Obs passar a coluna e o valor do registro e type Class do objeto para popular.
	 * @param id
	 * @param clazz
	 * @return Object do type class do parametro
	 */
	public Object readByColumn(String column, String value, Class clazz){
		
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		String dataid = CS.getDATA(db, nameDatabase);
		
		String SQL = "SELECT * FROM " + dataid + " WHERE " + CS.getCOL(db, nameDatabase, column) + " = '" + value +"';";
		HashMap<String, String> row = db.query_record(SQL);
		
		Object object = null;
		try {
				
			object = clazz.newInstance();			
			if(row != null){
				for(Field atributo : object.getClass().getDeclaredFields()) {						
					atributo.setAccessible(true);
					ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);
					if(coluna != null && !coluna.name().equals("")){
						atributo.set(object, row.get(CS.getCOL(db, nameDatabase, coluna.name())));	//seta no objeto o valor do registro, recuperado pelo nome da coluna na anotacao do objeto
					}
				}
				//atributos da superclasse ContentILiketo
				Field f1 = object.getClass().getSuperclass().getDeclaredField("id");
				Field f2 = object.getClass().getSuperclass().getDeclaredField("dateCreated");
				Field f3 = object.getClass().getSuperclass().getDeclaredField("dateUpdated");
				f1.setAccessible(true);
				f2.setAccessible(true);
				f3.setAccessible(true);
				f1.set(object, row.get(CS.getCOL(db, nameDatabase, "id")));
				f2.set(object, row.get(CS.getCOL(db, nameDatabase, "date_created")));
				f3.set(object, row.get(CS.getCOL(db, nameDatabase, "date_updated")));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		return object;
	}

	public DB getDb() {
		return db;
	}

	public String getNameDatabase() {
		return nameDatabase;
	}
	
}
