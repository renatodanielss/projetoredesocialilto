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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.FileILiketo;

/**
 * Classe contem as configuracoes do Sistema
 * @author OSVALDIMAR
 *
 */
public class CmsConfigILiketo {
	
	private ServletContext servletcontext;
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
		myconfig.setTemp("URLuploadpath", "upload/");	//configuração pasta 'upload' dentro do diretório raiz
		setCmsAsbru(request);
		
	}
	
	/**
	 * Metodo faz upload de um arquivo que contenha no form <input type="file" name="file"> 
	 * e seta o nome do arquivo gerado no atributo do objeto passado no parametro que tenha anotacao @FileILiketo
	 * @param object
	 * @param request
	 * @return
	 */
	public Object processFileupload(Object object, HttpServletRequest request){
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
				        InputStream stream = item.getInputStream();
				        HashMap<String,String> mapMyFormInput = new HashMap<String,String>();
		            	mapMyFormInput.put("name", item.getFieldName());
		            	mapMyFormInput.put("filename", item.getName());
				        FileuploadILiketo filepostIliketo = new FileuploadILiketo(myrequest, DOCUMENT_ROOT + myconfig.get((DB)request.getAttribute(Str.CONNECTION_DB), "URLrootpath"), 
				        		myconfig.get((DB)request.getAttribute(Str.CONNECTION_DB), "URLuploadpath"), 32, stream, mapMyFormInput);
				        String path_file_name = filepostIliketo.getParameter(Str.PATH_FILE_DEFAULT);   
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * Metodo faz upload de varios arquivos que contenha no form varios <input type="file" name="file"> 
	 * e seta o nome do arquivo gerado no atributo de cada objeto correspondente no vetor passado no parametro que tenha anotacao @FileILiketo
	 * @param object
	 * @param request
	 * @return
	 */
	public Object[] processFileupload(Object[] objects, HttpServletRequest request){
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
		        int i = 0;
				while (items.hasNext() && i < objects.length){
			        FileItem item = (FileItem) items.next();
		            if (!item.isFormField()) {
		            	InputStream stream = item.getInputStream();
		            	HashMap<String,String> mapMyFormInput = new HashMap<String,String>();
		            	mapMyFormInput.put("name", item.getFieldName());
		            	mapMyFormInput.put("filename", item.getName());
				        FileuploadILiketo filepostIliketo = new FileuploadILiketo(myrequest, DOCUMENT_ROOT + myconfig.get((DB)request.getAttribute(Str.CONNECTION_DB), "URLrootpath"), 
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects;
	}
	
	/**
	 * Metodo retorna o total de arquivos type=file que esta no request
	 * @param request
	 * @return
	 */
	public int getTotalFiles(HttpServletRequest request){
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
	public long getSizeFilesInBytes(HttpServletRequest request){
		
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
	 * Método retorna o tamanho total de arquivos em bytes que o usuario da sessao possui.
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
		
		System.out.println("\nArquivos do usuario na sessao:");
		for(String rec : recordsCollections.keySet()){			
			myfilename = recordsCollections.get(rec).get("path_photo_collection");
			File file = new File (pathname + myfilename);
			if(file.exists()){
				System.out.println("Photo collection: " + myfilename + " - " + file.length() + " bytes");
				sizeTotal += file.length();
			}
		}
		for(String rec : recordsItems.keySet()){			
			myfilename = recordsItems.get(rec).get("path_photo_item");
			File file = new File (pathname + myfilename);
			if(file.exists()){
				System.out.println("Photo item: " + myfilename + " - " + file.length() + " bytes");
				sizeTotal += file.length();
			}
		}
		for(String rec : recordsVideos.keySet()){			
			myfilename = recordsVideos.get(rec).get("path_file_video");
			File file = new File (pathname + myfilename);
			if(file.exists()){
				System.out.println("File video: " + myfilename + " - " + file.length() + " bytes");
				sizeTotal += file.length();
			}
		}
		for(String rec : recordsEvent.keySet()){			
			myfilename = recordsEvent.get(rec).get("path_photo_event");
			File file = new File (pathname + myfilename);
			if(file.exists()){
				System.out.println("Photo event: " + myfilename + " - " + file.length() + " bytes");
				sizeTotal += file.length();
			}
		}
		for(String rec : recordsStore.keySet()){			
			myfilename = recordsStore.get(rec).get("photo_store_item");
			File file = new File (pathname + myfilename);
			if(file.exists()){
				System.out.println("Photo store item: " + myfilename + " - " + file.length() + " bytes");
				sizeTotal += file.length();
			}
		}
		
		return sizeTotal;
	}
	
	/**
	 * Método retorna um objeto do tipo referenciado no parametro clazz 
	 * Retorna objeto com os dados populados nos atributos. O "name" dos inputs do form tem que ser igual a "coluna" na database ou "nome do atributo" do objeto
	 * @param clazz
	 * @param request
	 * @return
	 */
	public Object getObjectOfParameter(Class clazz, HttpServletRequest request){
		
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
	 * Método retorna um vetor de vários objetos do tipo referenciado no parametro clazz, usado quando há upload de mais arquivos 
	 * Retorna objeto com os dados populados nos atributos. O "name" dos inputs do form tem que ser igual a "coluna" na database ou "nome do atributo" do objeto
	 * @param clazz
	 * @param request
	 * @return
	 */
	public Object[] getObjectsFileOfParameter(Class clazz, HttpServletRequest request){
		
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
				int totalInputFiles = getTotalFiles(request);
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
		
		if(mapModel != null && !mapModel.isEmpty()){
			Iterator it = mapModel.keySet().iterator();
			while(it.hasNext()){
				String nameBean = "" + it.next();				
				if(content.contains("${" + nameBean  + "}")){ //${objeto}
					content = content.replaceAll("\\$\\{" + nameBean + "}", nameBean);
				}
				if(content.contains("@@@" + nameBean  + "@@@")){ //@@@objeto@@@
					content = content.replaceAll("@@@" + nameBean + "@@@", nameBean);
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
									content = content.replaceAll("\\$\\{" + nameBean + "." + atributo.getName() + "}", "null");
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
											content = content.replaceAll("\\$\\{" + nameBean + "." + atributo.getName() + "." + at.getName() + "}", "null");
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
											content = content.replaceAll("@@@" + coluna.name() + "@@@", "null");
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
							content = content.replaceAll("@@@" + "data_created" + "@@@", (value1 == null ? "null":value1.toString()));
						}
						if(content.contains("${" + nameBean + "." + f1.getName() + "}")){
							content = content.replaceAll("\\$\\{" + nameBean + "." + f1.getName() + "}", (value1 == null ? "null":value1.toString()));
						}
						if(content.contains("@@@" + "date_updated" + "@@@")){
							content = content.replaceAll("@@@" + "date_updated" + "@@@", (value2 == null ? "null":value2.toString()));
						}
						if(content.contains("${" + nameBean + "." + f2.getName() + "}")){
							content = content.replaceAll("\\$\\{" + nameBean + "." + f2.getName() + "}", (value2 == null ? "null":value2.toString()));
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
				}
			}
		}else{
			return null;
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
								content = content.replaceAll("\\$\\{" + atributo.getName() + "}", "null");
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
										content = content.replaceAll("\\$\\{" + atributo.getName() + "." + at.getName() + "}", "null");
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
									content = content.replaceAll("@@@" + coluna.name() + "@@@", "null");
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
						content = content.replaceAll("@@@" + "data_created" + "@@@", (value1 == null ? "null":value1.toString()));
					}
					if(content.contains("${" + f1.getName() + "}")){
						content = content.replaceAll("\\$\\{" + f1.getName() + "}", (value1 == null ? "null":value1.toString()));
					}
					if(content.contains("@@@" + "date_updated" + "@@@")){
						content = content.replaceAll("@@@" + "date_updated" + "@@@", (value2 == null ? "null":value2.toString()));
					}
					if(content.contains("${" + f2.getName() + "}")){
						content = content.replaceAll("\\$\\{" + f2.getName() + "}", (value2 == null ? "null":value2.toString()));
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
			System.out.println("Ocorreu um problema para carregar pagina, idPage: " + idPage);
			e.printStackTrace();
			try {
				myresponse.getResponse().sendRedirect("/pageNotFound");
			} catch (IOException e1) {
				System.out.println("Nao achou pagina '/pageNotFound'");
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
			System.out.println("Ocorreu um problema para carregar pagina, idPage: " + idPage);
			e.printStackTrace();
			try {
				myresponse.getResponse().sendRedirect("/pageNotFound");
			} catch (IOException e1) {
				System.out.println("Nao achou pagina '/pageNotFound'");
				e1.printStackTrace();
			}
		}
		return content;
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
