package com.iliketo.dao;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.FileILiketo;
import com.iliketo.model.annotation.IdILiketo;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.Str;

import HardCore.Configuration;
import HardCore.DB;
import HardCore.Data;
import HardCore.Databases;
import HardCore.Fileupload;
import HardCore.Text;

public class AdvertisingDAO extends GenericDAO{
	
	public AdvertisingDAO(DB db, HttpServletRequest request){
		super(db, "dbadvertising", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos da AdvertisingDAO abaixo
	
	/**
	 * Metodo deleta um anuncio do banco de dados e a foto do anuncio
	 * @param idDeleteAd
	 */
	public void deleteAd(String idDeleteAd) {
		
		//configuracao diretorio media anunciante
		CmsConfigILiketo cms = new CmsConfigILiketo(super.getRequest(), null);
		String armazenamentoAnunciante = cms.getDOCUMENT_ROOT_UPLOAD() + "/" + cms.getFOLDER_ADVERTISER();
		DB db = super.getDb();
		
		//nome do arquivo para deletar
		String nameFileDelete = IliketoDAO.getValueOfDatabase(db, "path_ad", "dbadvertising", "id_advertising", idDeleteAd);		
		
		String localPath = armazenamentoAnunciante; 	   						//local armazenamento
		super.deleteFilePhysically(nameFileDelete, localPath); 					//metodo deleta fisicamente
		
		IliketoDAO.deleteDadosIliketo(db, "dbadvertising", "id", idDeleteAd); 	//metodo deleta dados na database
	}
	

	/**
	 * Metodo atualiza registro do anuncio no banco de dados
	 * Objeto deve conter o valor do id chave para atualizar.
	 * Se deletarArquivo for true e conter files, imagens ou video, deleta o antigo e atualiza o novo
	 * @param Advertising
	 * @param deletarArquivo
	 */
	public void update(Object object, boolean deletarArquivo){
		
		//configuracao diretorio media anunciante
		CmsConfigILiketo cms = new CmsConfigILiketo(super.getRequest(), null);
		String armazenamentoAnunciante = cms.getDOCUMENT_ROOT_UPLOAD() + "/" + cms.getFOLDER_ADVERTISER();
		
		Fileupload filepost = new Fileupload(null, null, null);
		String idReal = "";	//id real para update
		Object old = null;
		try {
			for(Field atributo : object.getClass().getDeclaredFields()) {
				atributo.setAccessible(true);
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
							if(deletarArquivo){
								//deleta arquivo antigo fisicamente para atualizar o novo
								String nameFileDelete = (String) atributo.get(old);			//nome do arquivo no objecto do registro antigo antes do update
								if(nameFileDelete != null && !nameFileDelete.equals("")){			
									String localFilePath = (String) armazenamentoAnunciante; 		//local arquivado
									//verifica arquivos defaults
									if(!nameFileDelete.equals("avatar_male.png") && !nameFileDelete.equals("avatar_female.png") && !nameFileDelete.equals("avatar_store.png")){
										deleteFilePhysically(nameFileDelete, localFilePath);	//exclui arquivo e calcula espaco utilizado
									}
								}
								filepost.setParameter(coluna.name(), ""+value);	//set no filepost "coluna", "valor" para atualizar no BD
							}
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
		database.read(super.getDb(), new Configuration(), super.getNameDatabase());				
				
		Data data = new Data();
		data.read(super.getDb(), "data" + database.getId(), idReal);		
		String created = data.getCreated(database.columns);
		String createdby = data.getCreatedBy(database.columns);
		data.getFormData(database.columns, filepost);
		data.adjustContent(database.columns);
		data.setCreated(database.columns, created, createdby);
		data.setUpdated(database.columns, timestamp, "username");
		data.update(super.getDb(), "data" + database.getId(), database.columns);
		log.info("***data.update anunciante***");
		Iterator it = filepost.getParameterNames();
		while(it.hasNext()){
			String s = (String) it.next();
			log.info("name input/column: " + s + " - value: " + filepost.getParameter(s));
		}

	}
	
}
