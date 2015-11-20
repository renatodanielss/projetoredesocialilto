package com.iliketo.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.catalina.Context;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import HardCore.Cms;
import HardCore.Common;
import HardCore.Configuration;
import HardCore.DB;
import HardCore.Page;
import HardCore.Request;
import HardCore.Response;
import HardCore.Session;
import HardCore.Text;
import HardCore.UCbrowseWebsite;
import HardCore.Website;

import com.iliketo.control.VideoController;
import com.iliketo.dao.MemberDAO;
import com.iliketo.exception.ImageILiketoException;
import com.iliketo.exception.StorageILiketoException;
import com.iliketo.exception.VideoILiketoException;
import com.iliketo.model.Member;
import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.FileILiketo;

/**
 * Classe contem as configuracoes do Sistema
 * @author OSVALDIMAR
 *
 */
public class CmsConfigILiketo {
	
	static final Logger log = Logger.getLogger(CmsConfigILiketo.class);
	
	private ServletContext servletcontext;
	private String DOCUMENT_ROOT_UPLOAD;
	private String DOCUMENT_ROOT;
	private Request myrequest;
	private Response myresponse;
	private Session mysession;
	private Text mytext;
	private Website website;
	private Configuration myconfig;
	private String database;
	private Cms cms;
	private List<FileItem> fileItems = null;
	private MemberDAO memberDAO;

	public CmsConfigILiketo(HttpServletRequest request, HttpServletResponse response){

		servletcontext = request.getServletContext();		
		DOCUMENT_ROOT = Common.getRealPath(servletcontext, "/").replaceAll("[\\\\/]$", "");		
		myrequest = new Request(request);
		myresponse = new Response(response);
		mysession = new Session(request.getSession());
		mytext = new Text(myrequest);
		website = new Website(mytext);
		myconfig = new Configuration();
		setDatabase((String) request.getAttribute("database"));
		String URLrootpath = "/";
		myconfig.setTemp("URLrootpath", URLrootpath);
		myconfig.setTemp("URLfilepath", "file/");
		myconfig.setTemp("URLimagepath", "image/");
		myconfig.setTemp("URLstylesheetpath", "stylesheet/");
		myconfig.setTemp("URLuploadpath", "upload/");			//configura��o pasta 'upload' dentro do diret�rio raiz
		memberDAO = new MemberDAO((DB) request.getAttribute(Str.CONNECTION_DB), request);	//dao membro
		setCmsAsbru(request);
		//diretorio de upload definido no Media Library do Asbru
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		if(myconfig.get(db, "csrootpath") != null && !myconfig.get(db, "csrootpath").equals("")){
			DOCUMENT_ROOT_UPLOAD = myconfig.get("csrootpath");
		}else{
			DOCUMENT_ROOT_UPLOAD = DOCUMENT_ROOT;
		}
		
	}
	
	/**
	 * Metodo valida se existe espaco disponivel de armazenamento do membro
	 * @param member
	 * @param uploadBytes
	 * @return
	 */
	public boolean validateFreeSpaceStorage(Member member, long uploadBytes){
		
		long usedSpace = Long.parseLong(member.getUsedSpace());
		long totalSpace = Long.parseLong(member.getTotalSpace());
		//valida armazenamento
		if(usedSpace + uploadBytes <= totalSpace){ 	//524288 KB > 536870912 bytes
			log.info("Size files upload = " + (uploadBytes>0?uploadBytes/1024:0) + " KB - " + uploadBytes + " bytes");
			return true;
		}		
		return false;
	}
	
	/**
	 * Metodo faz upload de uma imagem que contenha no form <input type="file" name="file"> 
	 * e seta o nome do arquivo gerado no atributo do objeto passado no parametro que tenha anotacao @FileILiketo
	 * @param object
	 * @return object
	 * @throws StorageILiketoException
	 * @throws ImageILiketoException 
	 */
	public Object processFileuploadImage(Object object) throws StorageILiketoException, ImageILiketoException{	
		try {
			return processFileupload(object, "image");				//upload de imagem
		} catch (ImageILiketoException | VideoILiketoException e) {
			throw new ImageILiketoException(e.getMessage());
		}	
	}
	
	/**
	 * Metodo faz upload de um video que contenha no form <input type="file" name="file"> 
	 * e seta o nome do arquivo gerado no atributo do objeto passado no parametro que tenha anotacao @FileILiketo
	 * @param object
	 * @return object
	 * @throws StorageILiketoException
	 * @throws VideoILiketoException 
	 */
	public Object processFileuploadVideo(Object object) throws StorageILiketoException, VideoILiketoException{	
		try {
			return processFileupload(object, "video");				//upload de video
		} catch (ImageILiketoException | VideoILiketoException e) {
			throw new VideoILiketoException(e.getMessage());
		}				
	}
	
	/**Metodo privado faz upload, valida extensao, valida duracao video, valida e atualiza armazenamento.
	 */
	private Object processFileupload(Object object, String typeFile) throws StorageILiketoException, ImageILiketoException, VideoILiketoException{
		
		HttpServletRequest request = myrequest.getRequest();
		String myUserId = mysession.get("userid");
		Member member = ((Member) memberDAO.readByColumn("id_member", myUserId, Member.class));
		long uploadBytes = getSizeFilesInBytes();
		
		//valida armazenamento disponivel
		if(validateFreeSpaceStorage(member, uploadBytes)){
			try{
				boolean isMultipart = ServletFileUpload.isMultipartContent(request);
				if(isMultipart){
					FileItemFactory factory = new DiskFileItemFactory();
			        ServletFileUpload upload = new ServletFileUpload(factory);
			        List<FileItem> list;
			        Iterator items;
			        if(fileItems == null){
			        	list = upload.parseRequest(request);
			        	items = list.iterator();
			        	fileItems = list;
			        }else{
			        	items = fileItems.iterator();
			        } 
					while (items.hasNext()){
				        FileItem item = (FileItem) items.next();
				        if (!item.isFormField() && !item.getName().equals("")) {
				        	if(typeFile.equals("image")){	
				        		FileuploadILiketo.validateExtensionImage(item.getName());	//valida extensao image
				        	}else{
				        		FileuploadILiketo.validateExtensionVideo(item.getName());	//valida extensao video
				        	}
			        		InputStream stream = item.getInputStream();
					        HashMap<String,String> mapMyFormInput = new HashMap<String,String>();
			            	mapMyFormInput.put("name", item.getFieldName());
			            	mapMyFormInput.put("filename", item.getName());
					        FileuploadILiketo filepostIliketo = new FileuploadILiketo(myrequest, DOCUMENT_ROOT_UPLOAD + myconfig.get((DB)request.getAttribute(Str.CONNECTION_DB), "URLrootpath"), 
					        		myconfig.get((DB)request.getAttribute(Str.CONNECTION_DB), "URLuploadpath"), 32, stream, mapMyFormInput);
					        String path_file_name = filepostIliketo.getParameter(Str.PATH_FILE_DEFAULT);					        
					        
					        //verifica e valida duracao de video
					        if(typeFile.equals("video")){
					        	FileuploadILiketo.validateDurationVideo(mysession.get(Str.STORAGE), path_file_name);
					        }
					        for(Field atributo : object.getClass().getDeclaredFields()){
								atributo.setAccessible(true);
								FileILiketo file = atributo.getAnnotation(FileILiketo.class);
								if(file != null){
									ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);
									if(coluna != null && !coluna.name().equals("")){
										atributo.set(object, path_file_name);	//seta no objeto o valor do nome do arquivo gerado pelo sistema
									}
								}
							}
				        }
					}
				}
			} catch (ImageILiketoException im) {
				throw im;
			} catch (VideoILiketoException vi) {
				throw vi;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return object;
		
		}else{
			throw new StorageILiketoException();
		}
	}
	
	/**
	 * Metodo faz upload de varias imagens que contenha no form varios <input type="file" name="file"> 
	 * e seta o nome do arquivo gerado no atributo de cada objeto correspondente no vetor passado no parametro que tenha anotacao @FileILiketo
	 * @param object
	 * @return Object[] 
	 * @throws StorageILiketoException
	 * @throws ImageILiketoException 
	 */
	public Object[] processFileuploadImages(Object[] objects) throws StorageILiketoException, ImageILiketoException{	
		try {
			return processFileuploads(objects, "image");				//upload de imagems
		} catch (ImageILiketoException | VideoILiketoException e) {
			throw new ImageILiketoException(e.getMessage());
		}	
	}
	
	/**Metodo privado faz upload, valida extensao, valida duracao video, valida e atualiza armazenamento.
	 */
	private Object[] processFileuploads(Object[] objects, String typeFile) throws ImageILiketoException, VideoILiketoException, StorageILiketoException{
		
		HttpServletRequest request = myrequest.getRequest();		
		String myUserId = mysession.get("userid");
		Member member = ((Member) memberDAO.readByColumn("id_member", myUserId, Member.class));
		long uploadBytes = getSizeFilesInBytes();
		
		//valida armazenamento disponivel
		if(validateFreeSpaceStorage(member, uploadBytes)){
			try{
				boolean isMultipart = ServletFileUpload.isMultipartContent(request);
				if(isMultipart){
					FileItemFactory factory = new DiskFileItemFactory();
			        ServletFileUpload upload = new ServletFileUpload(factory);
			        List<FileItem> list;
			        Iterator items;
			        if(fileItems == null){
			        	list = upload.parseRequest(request);
			        	items = list.iterator();
			        	fileItems = list;
			        }else{
			        	items = fileItems.iterator();
			        }
			        //valida extensao
			        Iterator itemsExtension = fileItems.iterator();
			        while (itemsExtension.hasNext()){
				        FileItem item = (FileItem) itemsExtension.next();
			            if (!item.isFormField() && !item.getName().equals("")) {
			            	//valida extensao para cada input file do form
			            	if(typeFile.equals("image")){
				        		FileuploadILiketo.validateExtensionImage(item.getName());	//valida extensao image
				        	}
			            }
			        }
			        
			        int i = 0;
					while (items.hasNext() && i < objects.length){
				        FileItem item = (FileItem) items.next();
			            if (!item.isFormField() && !item.getName().equals("")) {
			            	InputStream stream = item.getInputStream();
			            	HashMap<String,String> mapMyFormInput = new HashMap<String,String>();
			            	mapMyFormInput.put("name", item.getFieldName());
			            	mapMyFormInput.put("filename", item.getName());
					        FileuploadILiketo filepostIliketo = new FileuploadILiketo(myrequest, DOCUMENT_ROOT_UPLOAD + myconfig.get((DB)request.getAttribute(Str.CONNECTION_DB), "URLrootpath"), 
					        		myconfig.get((DB)request.getAttribute(Str.CONNECTION_DB), "URLuploadpath"), 32, stream, mapMyFormInput);
					        String path_file_name = filepostIliketo.getParameter(Str.PATH_FILE_DEFAULT);	        

					        for(Field atributo : objects[i].getClass().getDeclaredFields()) {
								atributo.setAccessible(true);
								FileILiketo file = atributo.getAnnotation(FileILiketo.class);
								if(file != null){
									ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);
									if(coluna != null && !coluna.name().equals("")){
										atributo.set(objects[i], path_file_name);	//seta no objeto o valor do nome do arquivo gerado pelo sistema
									}
								}
							}
					        i++;
			            }
			        }
				}
			} catch (ImageILiketoException im) {
				throw im;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return objects;
			
		}else{
			throw new StorageILiketoException();
		}
	}
		
	/**
	 * Metodo retorna o total de arquivos type=file que esta no request
	 * @param request
	 * @return
	 */
	public int getTotalFiles(){
		HttpServletRequest request = myrequest.getRequest();
		int totalFiles = 0;
		try{
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if(isMultipart){
				FileItemFactory factory = new DiskFileItemFactory();
		        ServletFileUpload upload = new ServletFileUpload(factory);
		        List<FileItem> list;
		        Iterator items;
		        if(fileItems == null){
		        	list = upload.parseRequest(request);
		        	items = list.iterator();
		        	fileItems = list;
		        }else{
		        	items = fileItems.iterator();
		        }      
		        while (items.hasNext()){
		        	FileItem item = (FileItem) items.next();
		            if (!item.isFormField()) {		            	
		            	String filename = item.getName();	                
		                if ((filename != null) && (!filename.equals(""))){	//se conter o nome do arquivo		                	
		                	totalFiles++;
		                }
		            }
		        }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return totalFiles;
	}
	
	/**
	 * Metodo retorna o tamanho total em bytes dos arquivos que esta no request
	 * @param request
	 * @return
	 */
	public long getSizeFilesInBytes(){
		HttpServletRequest request = myrequest.getRequest();
		long sizeFiles = 0;
		try{
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if(isMultipart){
				FileItemFactory factory = new DiskFileItemFactory();
		        ServletFileUpload upload = new ServletFileUpload(factory);
		        List<FileItem> list;
		        Iterator items;
		        if(fileItems == null){
		        	list = upload.parseRequest(request);
		        	items = list.iterator();
		        	fileItems = list;
		        }else{
		        	items = fileItems.iterator();
		        }		        
				while (items.hasNext()){
		            FileItem item = (FileItem) items.next();
		            if (!item.isFormField()){
		            	String filename = item.getName();	                
		                if ((filename != null) && (!filename.equals(""))){	//se conter o nome do arquivo		                	
		                	sizeFiles = sizeFiles + item.getSize();
		                }
		            }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sizeFiles;
	}
	
	/**
	 * M�todo retorna o tamanho total de arquivos em bytes que o usuario da sessao possui.
	 * @param request
	 * @return
	 */
	public long getTotalTodosArquivosUserInBytes(HttpServletRequest request){
		//obs 512 MB > 524288 KB > 536870912 bytes
		long sizeTotal = 0;
		DB db = (DB)request.getAttribute(Str.CONNECTION_DB);
		String rootpath = DOCUMENT_ROOT + myconfig.get(db, "URLrootpath");
		String filepathname = myconfig.get(db, "URLuploadpath");
		String pathname = rootpath + filepathname;
		String myfilename = "";
		String myUserid = mysession.get("userid");
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);

		String SQLCollection = "select c1.path_photo_collection as path_photo_collection from dbcollection c1 where c1.fk_user_id = '" +myUserid+ "';";
		String[][] aliasCollection = { {"dbcollection", "c1"} };		
		SQLCollection = CS.transformSQLReal(SQLCollection, aliasCollection);
		
		String SQLItem = "select i.path_photo_item as path_photo_item from dbcollectionitem i where i.fk_user_id = '" +myUserid+ "';";		
		String[][] aliasItem = { {"dbcollectionitem", "i"} };
		SQLItem = CS.transformSQLReal(SQLItem, aliasItem);
		
		String SQLVideo = "select v.path_file_video as path_file_video from dbcollectionvideo v where v.fk_user_id = '" +myUserid+ "';";
		String[][] aliasVideo = { {"dbcollectionvideo", "v"} };
		SQLVideo = CS.transformSQLReal(SQLVideo, aliasVideo);
		
		String SQLEvent = "select e.path_photo_event as path_photo_event from dbevent e where e.fk_user_id = '" +myUserid+ "';";
		String[][] aliasEvent = { {"dbevent", "e"} };
		SQLEvent = CS.transformSQLReal(SQLEvent, aliasEvent);
		
		String SQLStore = "select t1.photo_store_item as photo_store_item from dbstoreitem t1"
				+ " join dbannounce t2 on t2.id_announce = t1.fk_announce_id where t2.fk_user_id = '" +myUserid+ "';";
		String[][] aliasStore = { {"dbstoreitem", "t1"}, {"dbannounce", "t2"} };
		SQLStore = CS.transformSQLReal(SQLStore, aliasStore);
		
		LinkedHashMap<String,HashMap<String,String>> recordsCollections  = db.query_records(SQLCollection); //map de registros collections
		LinkedHashMap<String,HashMap<String,String>> recordsItems  = db.query_records(SQLItem); 			//map de registros items
		LinkedHashMap<String,HashMap<String,String>> recordsVideos  = db.query_records(SQLVideo); 			//map de registros videos
		LinkedHashMap<String,HashMap<String,String>> recordsEvent  = db.query_records(SQLEvent); 			//map de registros eventos
		LinkedHashMap<String,HashMap<String,String>> recordsStore  = db.query_records(SQLStore); 			//map de registros fotos item de loja
		
		log.info("\nArquivos do usuario na sessao:");
		for(String rec : recordsCollections.keySet()){			
			myfilename = recordsCollections.get(rec).get("path_photo_collection");
			File file = new File (pathname + myfilename);
			if(file.exists()){
				log.info("Photo collection: " + myfilename + " - " + file.length() + " bytes");
				sizeTotal += file.length();
			}
		}
		for(String rec : recordsItems.keySet()){			
			myfilename = recordsItems.get(rec).get("path_photo_item");
			File file = new File (pathname + myfilename);
			if(file.exists()){
				log.info("Photo item: " + myfilename + " - " + file.length() + " bytes");
				sizeTotal += file.length();
			}
		}
		for(String rec : recordsVideos.keySet()){			
			myfilename = recordsVideos.get(rec).get("path_file_video");
			File file = new File (pathname + myfilename);
			if(file.exists()){
				log.info("File video: " + myfilename + " - " + file.length() + " bytes");
				sizeTotal += file.length();
			}
		}
		for(String rec : recordsEvent.keySet()){			
			myfilename = recordsEvent.get(rec).get("path_photo_event");
			File file = new File (pathname + myfilename);
			if(file.exists()){
				log.info("Photo event: " + myfilename + " - " + file.length() + " bytes");
				sizeTotal += file.length();
			}
		}
		for(String rec : recordsStore.keySet()){			
			myfilename = recordsStore.get(rec).get("photo_store_item");
			File file = new File (pathname + myfilename);
			if(file.exists()){
				log.info("Photo store item: " + myfilename + " - " + file.length() + " bytes");
				sizeTotal += file.length();
			}
		}
		
		return sizeTotal;
	}
	
	/**
	 * M�todo retorna um objeto do tipo referenciado no parametro clazz 
	 * Retorna objeto com os dados populados nos atributos. O "name" dos inputs do form tem que ser igual a "coluna" na database ou "nome do atributo" do objeto
	 * @param clazz
	 * @return
	 */
	public Object getObjectOfParameter(Class clazz){
		HttpServletRequest request = myrequest.getRequest();
		Object object = null;
		try{
			object = clazz.newInstance();
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if(isMultipart){
				FileItemFactory factory = new DiskFileItemFactory();
		        ServletFileUpload upload = new ServletFileUpload(factory);
		        List<FileItem> list;
		        Iterator items;
		        if(fileItems == null){
		        	list = upload.parseRequest(request);
		        	items = list.iterator();
		        	fileItems = list;
		        }else{
		        	items = fileItems.iterator();
		        }
		        while (items.hasNext()){
		        	FileItem item = (FileItem) items.next();
		            if (item.isFormField()) {
		            	String inputName = item.getFieldName();
						for(Field atributo : object.getClass().getDeclaredFields()) {						
							atributo.setAccessible(true);
							ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);
							if(coluna != null && !coluna.name().equals("")){
								//name do input pode ser igual a = "name_collection ou nameCollection" (coluna na database ou nome do atributo do bean)
								if(inputName.equals(coluna.name()) || inputName.equals(atributo.getName())){
									String value = item.getString();
									atributo.set(object, value);	//seta no objeto o valor do parametro
								}
							}
						}
		            }
		        }
			}else{        
				Enumeration e = request.getParameterNames();
				while(e.hasMoreElements()){
					String inputName = (String) e.nextElement();					
					for(Field atributo : object.getClass().getDeclaredFields()) {						
						atributo.setAccessible(true);
						ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);
						if(coluna != null && !coluna.name().equals("")){
							//name do input pode ser igual a = "name_collection ou nameCollection" (coluna na database ou nome do atributo do bean)
							if(inputName.equals(coluna.name()) || inputName.equals(atributo.getName())){
								atributo.set(object, request.getParameter(coluna.name()));	//seta no objeto o valor do parametro
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * M�todo retorna um vetor de v�rios objetos do tipo referenciado no parametro clazz, usado quando h� upload de mais arquivos 
	 * Retorna objeto com os dados populados nos atributos. O "name" dos inputs do form tem que ser igual a "coluna" na database ou "nome do atributo" do objeto
	 * @param clazz
	 * @return
	 */
	public Object[] getObjectsFileOfParameter(Class clazz){
		HttpServletRequest request = myrequest.getRequest();
		Object[] arrayObjects = null;		
		try{
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if(isMultipart){
				FileItemFactory factory = new DiskFileItemFactory();
		        ServletFileUpload upload = new ServletFileUpload(factory);		       
		        List<FileItem> list;
		        Iterator items;
		        if(fileItems == null){
		        	list = upload.parseRequest(request);
		        	items = list.iterator();
		        	fileItems = list;
		        }else{
		        	items = fileItems.iterator();
		        }
				int totalInputFiles = getTotalFiles();
		        arrayObjects = new Object[totalInputFiles];
		        HashMap<String, String[]> map = new HashMap<String, String[]>();
		        int i = 0;
		        while (items.hasNext()){
		        	FileItem item = (FileItem) items.next();
	            	String inputName = item.getFieldName();
	            	String valueInput = item.getString();
	            	if(map.get(inputName) == null){
	            		map.put(inputName, new String[]{valueInput});
	            	}else{
	            		String[] oldValues = map.get(inputName);
	            		String[] values = new String[oldValues.length+1];
	            		for(int j = 0; j < values.length-1; j++){
	            			values[j] = oldValues[j];
	            		}
	            		values[values.length-1] = valueInput;
	            		map.put(inputName, values);
	            	}
		        }
		        for(int j = 0; j < totalInputFiles; j++){
		        	Object object = clazz.newInstance();
		        	Iterator iter = map.keySet().iterator();
		        	while(iter.hasNext()){
		        		String inputName = (String) iter.next();
						for(Field atributo : object.getClass().getDeclaredFields()) {						
							atributo.setAccessible(true);
							ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);
							if(coluna != null && !coluna.name().equals("")){
								//name do input pode ser igual a = "name_collection ou nameCollection" (coluna na database ou nome do atributo do bean)
								if(inputName.equals(coluna.name()) || inputName.equals(atributo.getName())){
									String value = ((String[]) map.get(inputName))[j];
									atributo.set(object, value);	//seta no objeto o valor do parametro
								}
							}
						}
		            }
		            arrayObjects[j] = object;
		        }
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayObjects;
	}

	/**
	 * Metodo faz o parse do bean na view
	 * @param content
	 * @return
	 */
	public String parseBindingModelBean(String content){
		
		//map ModelILiketo
		HashMap mapModel = (HashMap) myrequest.getRequest().getAttribute("modelILiketo");
		HashMap mapModelError = (HashMap) myrequest.getRequest().getAttribute("modelILiketoError");
		
		if(mapModel != null && !mapModel.isEmpty()){
			Iterator it = mapModel.keySet().iterator();
			while(it.hasNext()){
				String nameBean = "" + it.next();				
				if(content.contains("${" + nameBean  + "}")){ //${objeto}
					content = content.replaceAll("\\$\\{" + nameBean + "}", mapModel.get(nameBean).toString());
				}
				if(content.contains("@@@" + nameBean  + "@@@")){ //@@@objeto@@@
					content = content.replaceAll("@@@" + nameBean + "@@@", mapModel.get(nameBean).toString());
				}
				if(content.contains("${" + nameBean + ".") || content.contains("@@@")){
					try {
						//Object
						Object obj = mapModel.get(nameBean);
						//Field					
						for (Field atributo : obj.getClass().getDeclaredFields()) {
							//Expression Language jsp '${ }'
							if(content.contains("${" + nameBean + "." + atributo.getName() + "}")){	//${objeto.atributo}						
								atributo.setAccessible(true);
								Object value = atributo.get(obj);
								if(value != null){
									content = content.replaceAll("\\$\\{" + nameBean + "." + atributo.getName() + "}", value.toString());
								}else{
									content = content.replaceAll("\\$\\{" + nameBean + "." + atributo.getName() + "}", "");
								}								
							}else if(content.contains("${" + nameBean + "." + atributo.getName() + ".")){ //${objeto.objeto.atributo}							
								atributo.setAccessible(true);
								for (Field at : atributo.get(obj).getClass().getDeclaredFields()) {
									if(content.contains("${" + nameBean + "." + atributo.getName() + "." + at.getName() + "}")){	//${objeto.objeto.atributo}
										at.setAccessible(true);
										Object value = at.get(atributo.get(obj));
										if(value != null){
											content = content.replaceAll("\\$\\{" + nameBean + "." + atributo.getName() + "." + at.getName() + "}", value.toString());
										}else{
											content = content.replaceAll("\\$\\{" + nameBean + "." + atributo.getName() + "." + at.getName() + "}", "");
										}
									}									
								}								
							}else{
								//codigo especial do asbru '@@@'
								atributo.setAccessible(true);
								ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);
								if(coluna != null && !coluna.name().equals("")){
									if(content.contains("@@@" + coluna.name() + "@@@")){	//@@@colunadatabase@@@
										Object value =  atributo.get(obj);
										if(value != null){
											content = content.replaceAll("@@@" + coluna.name() + "@@@", value.toString());
										}else{
											content = content.replaceAll("@@@" + coluna.name() + "@@@", "");
										}
									}
								}
							}
						}
						//atributos da superclasse ContentILiketo
						Field f1 = obj.getClass().getSuperclass().getDeclaredField("dateCreated");
						Field f2 = obj.getClass().getSuperclass().getDeclaredField("dateUpdated");
						f1.setAccessible(true);
						f2.setAccessible(true);
						Object value1 =  f1.get(obj);
						Object value2 =  f2.get(obj);
						if(content.contains("@@@" + "data_created" + "@@@")){
							content = content.replaceAll("@@@" + "data_created" + "@@@", (value1 == null ? "":value1.toString()));
						}
						if(content.contains("${" + nameBean + "." + f1.getName() + "}")){
							content = content.replaceAll("\\$\\{" + nameBean + "." + f1.getName() + "}", (value1 == null ? "":value1.toString()));
						}
						if(content.contains("@@@" + "date_updated" + "@@@")){
							content = content.replaceAll("@@@" + "date_updated" + "@@@", (value2 == null ? "":value2.toString()));
						}
						if(content.contains("${" + nameBean + "." + f2.getName() + "}")){
							content = content.replaceAll("\\$\\{" + nameBean + "." + f2.getName() + "}", (value2 == null ? "":value2.toString()));
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						//e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
				}
			}
		}	
		if(mapModelError != null && !mapModelError.isEmpty()){
			//se contem expressao ${error:}
			if(content.contains("${error:")){
				Iterator it = mapModelError.keySet().iterator();
				while(it.hasNext()){
					String nameError = "" + it.next();
					if(content.contains("${error:" + nameError + "}")){				//${error:nomeErro}
						String value = (String) mapModelError.get(nameError);		//valor erro
						content = content.replaceAll("\\$\\{error:" + nameError + "}", value);
					}
				}
			}
		}
		//se contem expressao ${error:}
		while(content.contains("${error:")){
			int begin = content.indexOf("${error:");
			int end = content.indexOf("}", begin);
			if(begin >= 1 && end >= 1){
				//log.debug("substring teste msg error: " + content.substring(begin, end+1));
				content = content.replaceAll("\\$\\{error:" + content.substring(begin+8, end+1), "");
			}else if(begin >= 1){
				content = content.replaceAll("\\$\\{error:", "");
			}
		}
		//se contem expressao '${'
		while(content.contains("${")){
			int begin = content.indexOf("${");
			int end = content.indexOf("}", begin);
			if(begin >= 1 && end >= 1){
				//log.debug("substring expressao geral: " + content.substring(begin, end+1));
				content = content.replaceAll("\\$\\{" + content.substring(begin+2, end+1), "");
			}else if(begin >= 1){
				break;
			}
		}
		return content;
		
	}
	
	/**
	 * Metodo faz o parse do bean na view de uma list
	 * @param content
	 * @return
	 */
	public StringBuilder parseBindingModelBean(List listModel, HashMap<Class, String> mapModelListEntry){
		
		StringBuilder builder = new StringBuilder();
		
		for(Object bean : listModel){
			String content = "";
			if(mapModelListEntry.get(bean.getClass()) != null){
				content = mapModelListEntry.get(bean.getClass());
			}
			
			Object obj = bean;
			if(!content.isEmpty()){
				try{
					for(Field atributo : obj.getClass().getDeclaredFields()){
						//Expression Language jsp '${ }'
						if(content.contains("${" + atributo.getName() + "}")){ //${atributo}
							atributo.setAccessible(true);
							Object value = atributo.get(obj);
							if(value != null){
								content = content.replaceAll("\\$\\{" + atributo.getName() + "}", value.toString());
							}else{
								content = content.replaceAll("\\$\\{" + atributo.getName() + "}", "");
							}
						}else if(content.contains("${" + atributo.getName() + ".")){ //${objeto.atributo}							
							atributo.setAccessible(true);
							for (Field at : atributo.get(obj).getClass().getDeclaredFields()) {
								if(content.contains("${" + atributo.getName() + "." + at.getName() + "}")){	//${objeto.atributo}
									at.setAccessible(true);
									Object value = at.get(atributo.get(obj));
									if(value != null){
										content = content.replaceAll("\\$\\{" + atributo.getName() + "." + at.getName() + "}", value.toString());
									}else{
										content = content.replaceAll("\\$\\{" + atributo.getName() + "." + at.getName() + "}", "");
									}
								}									
							}
						}
						//codigo especial do asbru '@@@'
						ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);
						if(coluna != null && !coluna.name().equals("")){
							if(content.contains("@@@" + coluna.name() + "@@@")){	//@@@colunadatabase@@@
								atributo.setAccessible(true);
								Object value = atributo.get(obj);
								if(value != null){
									content = content.replaceAll("@@@" + coluna.name() + "@@@", value.toString());
								}else{
									content = content.replaceAll("@@@" + coluna.name() + "@@@", "");
								}
							}
						}
					}
					//atributos da superclasse ContentILiketo
					Field f1 = obj.getClass().getSuperclass().getDeclaredField("dateCreated");
					Field f2 = obj.getClass().getSuperclass().getDeclaredField("dateUpdated");
					f1.setAccessible(true);
					f2.setAccessible(true);
					Object value1 =  f1.get(obj);
					Object value2 =  f2.get(obj);
					if(content.contains("@@@" + "data_created" + "@@@")){
						content = content.replaceAll("@@@" + "data_created" + "@@@", (value1 == null ? "":value1.toString()));
					}
					if(content.contains("${" + f1.getName() + "}")){
						content = content.replaceAll("\\$\\{" + f1.getName() + "}", (value1 == null ? "":value1.toString()));
					}
					if(content.contains("@@@" + "date_updated" + "@@@")){
						content = content.replaceAll("@@@" + "date_updated" + "@@@", (value2 == null ? "":value2.toString()));
					}
					if(content.contains("${" + f2.getName() + "}")){
						content = content.replaceAll("\\$\\{" + f2.getName() + "}", (value2 == null ? "":value2.toString()));
					}					
					builder.append(content);	//incrementa string com conteudo
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		
		return builder;
	}
	
	/**
	 * Metodo retorna uma pagina de listEntry
	 * @param content
	 * @return
	 */
	public String getPageListEntry(String idPage){
		
		DB db = (DB) myrequest.getRequest().getAttribute(Str.CONNECTION_DB);
		String listEntry = null;
		try {
			
			UCbrowseWebsite browseWebsite = new UCbrowseWebsite(mytext);
			Page pageEntry = browseWebsite.getPageById(idPage, servletcontext, mysession, myrequest, myresponse, myconfig, db, website);
			listEntry = pageEntry.getBody();
		
		} catch (Exception e) {
			log.warn("Ocorreu um problema para carregar pagina, idPage: " + idPage);
			e.printStackTrace();
			try {
				myresponse.getResponse().sendRedirect("/pageNotFound");
			} catch (IOException e1) {
				log.warn("Nao achou pagina '/pageNotFound'");
				e1.printStackTrace();
			}
		}
		return listEntry;
	}
	
	/**
	 * Metodo retorna uma pagina para utilizar com Ajax
	 * @param content
	 * @return
	 */
	public String getPageBinding(String idPage){
		
		String content = null;
		try {
			content = (cms.Page(idPage).getBody());
		} catch (Exception e) {
			log.warn("Ocorreu um problema para carregar pagina, idPage: " + idPage);
			e.printStackTrace();
			try {
				myresponse.getResponse().sendRedirect("/pageNotFound");
			} catch (IOException e1) {
				log.warn("Nao achou pagina '/pageNotFound'");
				e1.printStackTrace();
			}
		}
		return content;
	}
	
	/**
	 * Metodo gera uma chave aleatoria e retorna a String com o valor gerado.
	 * @param randomize - quantidade de caracteres
	 * @return
	 */
	public static String generateRandomKey(int randomize){
		
		String randomfilename = "";
		if (randomize > 0) {			
			for (int j=0; j<randomize; j++) {
				randomfilename = "" + randomfilename + (char)('a' + Integer.parseInt(Common.numberformat("" + Math.random()*25, 0)));
			}
		}else{
			return null;
		}
		log.info("Gerar chave aleatoria: " + randomfilename);
		return randomfilename;
	}
	
	//getters and setters

	public void setCmsAsbru(HttpServletRequest request){
		
		DB db = (DB) request.getAttribute("connectionDB");
		PageContext pageContext = null;
		JspWriter out = null;
		DB logdb = null;
		
		Cms cms = new Cms(this, mytext, servletcontext, mysession, myrequest, myresponse, myconfig, db, logdb, website, DOCUMENT_ROOT, pageContext, out);
		
		this.cms = cms;
	}
	
	public Cms getCmsAsbru(){
		return cms;	
	}
		
	public ServletContext getServletcontext() {
		return servletcontext;
	}

	public void setServletcontext(ServletContext servletcontext) {
		this.servletcontext = servletcontext;
	}

	public String getDOCUMENT_ROOT() {
		return DOCUMENT_ROOT;
	}

	public void setDOCUMENT_ROOT(String dOCUMENT_ROOT) {
		DOCUMENT_ROOT = dOCUMENT_ROOT;
	}

	public Request getMyrequest() {
		return myrequest;
	}

	public void setMyrequest(Request myrequest) {
		this.myrequest = myrequest;
	}

	public Response getMyresponse() {
		return myresponse;
	}

	public void setMyresponse(Response myresponse) {
		this.myresponse = myresponse;
	}

	public Session getMysession() {
		return mysession;
	}

	public void setMysession(Session mysession) {
		this.mysession = mysession;
	}

	public Text getMytext() {
		return mytext;
	}

	public void setMytext(Text mytext) {
		this.mytext = mytext;
	}

	public Website getWebsite() {
		return website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

	public Configuration getMyconfig() {
		return myconfig;
	}

	public void setMyconfig(Configuration myconfig) {
		this.myconfig = myconfig;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}
	
	
	
}
