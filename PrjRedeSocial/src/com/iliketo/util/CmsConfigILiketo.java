package com.iliketo.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import HardCore.Cms;
import HardCore.Common;
import HardCore.Configuration;
import HardCore.DB;
import HardCore.Fileupload;
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
		myconfig.setTemp("URLuploadpath", "upload/");	//configura��o pasta 'upload' dentro do diret�rio raiz
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
	 * Metodo retorna o tamanho total em KB dos arquivos que esta no request
	 * @param request
	 * @return
	 */
	public long getSizeFilesInKB(HttpServletRequest request){
		
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
		if(sizeFiles > 0){
			sizeFiles = sizeFiles/1024; // = sizeInBytesFiles/1024 = 'xxx KB'
		}
		return sizeFiles;
	}
	
	/**
	 * M�todo retorna um objeto do tipo referenciado no parametro clazz 
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
	 * M�todo retorna um vetor de v�rios objetos do tipo referenciado no parametro clazz, usado quando h� upload de mais arquivos 
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
				if(content.contains("${" + nameBean + ".")){
					//Object
					Object obj = mapModel.get(nameBean);
					//Field
					for (Field atributo : obj.getClass().getDeclaredFields()) {
						if(content.contains("${" + nameBean + "." + atributo.getName() + "}")){	//${objeto.atributo}						
							try {
								atributo.setAccessible(true);
								Object value = atributo.get(obj);
								if(value != null){
									content = content.replaceAll("\\$\\{" + nameBean + "." + atributo.getName() + "}", value.toString());
								}else{
									content = content.replaceAll("\\$\\{" + nameBean + "." + atributo.getName() + "}", "null");
								}
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}							
						}else{
							//${objeto.objeto.atributo}
						}
					}
				}else{
					System.out.println("Bean: '" + nameBean + "' foi declarado no ModelILiketo, mas nao e utilizado na pagina!");
				}
			}
			if(content.contains("${")){
				System.out.println("Conteudo da pagina contem expression: '${', mas nao foi declarado o bean no ModelILiketo!");
			}
			
		}else{
			if(content.contains("${")){
				System.out.println("Conteudo da pagina contem expression: '${', mas nao foi declarado o bean no ModelILiketo!");
			}
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
				for (Field atributo : obj.getClass().getDeclaredFields()) {
					if(content.contains("${" + atributo.getName() + "}")){
						
						try {
							atributo.setAccessible(true);
							Object value = atributo.get(obj);
							if(value != null){
								content = content.replaceAll("\\$\\{" + atributo.getName() + "}", value.toString());
							}else{
								content = content.replaceAll("\\$\\{" + atributo.getName() + "}", "null");
							}
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
						
					}
				}
				builder.append(content);	//incrementa string com conteudo
			}else{
				System.out.println("Bean: '" + bean.getClass() + "' foi declarado na listModel, mas nao e utilizado nas paginas de listEntry!");
			}
			if(content.contains("${")){
				System.out.println("Conteudo da pagina listEntry: '" + mapModelListEntry.get(bean.getClass()) + "' contem expression: '${', mas nao achou o atributo no bean!");
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