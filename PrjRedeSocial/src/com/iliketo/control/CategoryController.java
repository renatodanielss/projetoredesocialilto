package com.iliketo.control;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.CategoryDAO;
import com.iliketo.dao.CollectionDAO;
import com.iliketo.dao.ForumDAO;
import com.iliketo.dao.HobbyDAO;
import com.iliketo.dao.IliketoDAO;
import com.iliketo.dao.InterestDAO;
import com.iliketo.model.Announce;
import com.iliketo.model.Category;
import com.iliketo.model.Collection;
import com.iliketo.model.Event;
import com.iliketo.model.Forum;
import com.iliketo.model.Interest;
import com.iliketo.service.NotificationService;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ColumnsSingleton;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class CategoryController {
	
	
	static final Logger log = Logger.getLogger(CategoryController.class);
	
	/**
	 * M�todo intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
	
	@RequestMapping(value={"/searchCategory"})
	public void searchCategory(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException{
		
		log.info(request.getRequestURL());
		
		String name = request.getParameter("category");
		DB db = (DB)request.getAttribute(Str.CONNECTION_DB);
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		
		String SQLCategory = "select t1.id_category as id_category, t1.name_category as name_category from dbcategory t1"
				+ " where t1.name_category ilike '" +name+ "%' limit 4;";
		String[][] aliasCat = { {"dbcategory", "t1"} };
		SQLCategory = CS.transformSQLReal(SQLCategory, aliasCat);

		LinkedHashMap<String,HashMap<String,String>> recordsCategory  = db.query_records(SQLCategory);

		JSONArray respJson = new JSONArray();
		for(String rec : recordsCategory.keySet()){
			String idCategry = recordsCategory.get(rec).get("id_category");
			String nameCategory = recordsCategory.get(rec).get("name_category");
		    JSONObject obj  = new JSONObject();
	        obj.put("name", nameCategory);
	        obj.put("id", idCategry);
		    respJson.put(obj);
		}
		String json = respJson.toString();
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().write(json);
	}
	
	/**
	 * Pesquisa categoria e retorna uma lista das categorias
	 */
	@RequestMapping(value={"/ajaxSearchCategory/listEntryJoin"})
	public void searchCategoryListEntry(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException{
		
		log.info(request.getRequestURL());
		
		DB db = (DB)request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);	
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		
		String myUserid = (String) request.getSession().getAttribute("userid");		
		String name = request.getParameter("category").trim();
		String tipo = request.getParameter("collection_interest");
		
		if(!name.isEmpty()){
			while(name.contains("  ")){
				name = name.replaceAll("  ", " ");
			}		
			String[] array = name.split(" ");
			
			String SQLCategory = "select t1.id_category as id_category, t1.name_category as name_category, "
					+ "t1.date_created as date_created, "
					+ "(select count(t2.id_collection) from dbcollection t2 where t1.id_category = t2.fk_category_id) as total1, "
					+ "(select count(t3.id_interest) from dbinterest t3 where t1.id_category = t3.fk_category_id) as total2, "
					+ "case when t1.name_category ilike '" +name+ "%' then 1 "
					+ "     when t1.name_category ilike '% " +name+ "%' then 2 ";
					if(array.length >= 2){
						SQLCategory += " when (t1.name_category ilike '" +array[0]+ "%' or t1.name_category ilike '% " +array[0]+ "%') ";
						for(int i = 1; i < array.length; i++){
							SQLCategory +=  "and (t1.name_category ilike '" +array[i]+ "%' or t1.name_category ilike '% " +array[i]+ "%') ";
						}
						SQLCategory += " then 3 ";
					}
					SQLCategory += " else 4 end as prioridade "
					+ "from dbcategory t1 "
					+ "where t1.hobby != 'yes' and (t1.name_category ilike '%" +name+ "%' ";
					if(array.length >= 2){
						SQLCategory += " or (t1.name_category ilike '" +array[0]+ "%' or t1.name_category ilike '% " +array[0]+ "%') ";
						for(int i = 1; i < array.length; i++){
							SQLCategory +=  "and (t1.name_category ilike '" +array[i]+ "%' or t1.name_category ilike '% " +array[i]+ "%') ";
						}
					}
					SQLCategory +=  " ) order by prioridade, t1.name_category limit 5;";
			String[][] aliasCat = { {"dbcategory", "t1"}, {"dbcollection", "t2"}, {"dbinterest", "t3"} };
			SQLCategory = CS.transformSQLReal(SQLCategory, aliasCat);
			System.out.println("Search SQLCategory: " + SQLCategory);
			LinkedHashMap<String,HashMap<String,String>> recordsCategory = db.query_records(SQLCategory);
			ArrayList<Category> lista = new ArrayList<Category>();
	
			for(String rec : recordsCategory.keySet()){
				String idCategory = recordsCategory.get(rec).get("id_category");
				String nameCategory = recordsCategory.get(rec).get("name_category");
				String data = recordsCategory.get(rec).get("date_created");
				String total1 = recordsCategory.get(rec).get("total1");
				String total2 = recordsCategory.get(rec).get("total2");
				Category categoria = new Category();
				categoria.setIdCategory(idCategory);
				categoria.setNameCategory(nameCategory);
				categoria.setDateCreated(data);
				categoria.setTotal(Integer.toString(Integer.parseInt(total1) + Integer.parseInt(total2)));
				lista.add(categoria);
			}
			
			CollectionDAO collectionDAO = new CollectionDAO(db, request);
			InterestDAO interestDAO = new InterestDAO(db, request);
			
			List<Collection> listCollection = collectionDAO.listCollectionByUser(myUserid);
			List<Interest> listInterest = interestDAO.listInterestByUser(myUserid);
			
			String listEntry;
			if(request.getParameter("listEntry") != null && request.getParameter("listEntry").equals("1020")){
				listEntry = cms.getPageListEntry("1020");		//List Category - Choose Category Join - register your interest Entry
			}else{
				listEntry = cms.getPageListEntry("915");		//List History - Choose Category Join Entry
			}			
			StringBuffer resultHTML = new StringBuffer();
			
			
			//linguagem
			final String LING = request.getLocale().getLanguage();
			
			//valida se existe categoria
			if(name.length() >= 3){
				if(!IliketoDAO.readRecordExistsDatabase(db, "dbcategory", "name_category", name)){
					String s = listEntry;
					s = s.replaceAll("@@@id_category@@@", "");
					s = s.replaceAll("@@@name_category@@@", name);
					s = s.replaceAll("Created:", "");
					s = s.replaceAll("Criado:", "");
					s = s.replaceAll("@@@date_created@@@", "");
					s = s.replaceAll("Total collectors:", "");
					s = s.replaceAll("Total colecionadores:", "");
					s = s.replaceAll("@@@total@@@", "");
					s = s.replaceAll("@@@join@@@", LING.equals("pt") ? "Criar nova categoria" : "Create new category");		//botao criar categoria
					s = s.replaceAll("@@@hidden@@@", "button");								//botao
					s = s.replaceAll("@@@info@@@", "");										//info				
					s = s.replaceAll("@@@info2@@@", LING.equals("pt") ? "<br>Categoria n�o existe!" : "<br>Category does not exist!"); //info2 categoria nao existe
					s = s.replaceAll("@@@action@@@", "criarNovaCategoria('"+name+"');");	//funcao javascript
					resultHTML.append(s);
				}
			}
			
			for(int i = 0; i < lista.size(); i++){
				//procura nas colecoes e interesses se o colecionador jah participa da categoria pesquisada
				String join = "join";
				for(Collection colecao : listCollection){
					if(colecao.getIdCategory().equals(lista.get(i).getIdCategory())){
						join = "unjoin collector";
						break;
					}
				}
				for(Interest interest : listInterest){
					if(interest.getIdCategory().equals(lista.get(i).getIdCategory())){
						join = "unjoin interest";
						break;
					}
				}
				/*
				 * Join
				 */
				String dataFormatada = "";
				try {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date data = format.parse(lista.get(i).getDateCreated());
					dataFormatada = new SimpleDateFormat("dd-MM-yyyy").format(data);
				} catch (ParseException e) {
					log.warn("ERRO PARSE DATA/n" + e);
				}
				String s = listEntry;
				s = s.replaceAll("@@@id_category@@@", lista.get(i).getIdCategory());
				s = s.replaceAll("@@@name_category@@@", lista.get(i).getNameCategory());			
				s = s.replaceAll("@@@date_created@@@", dataFormatada);
				s = s.replaceAll("@@@total@@@", lista.get(i).getTotal());
				s = s.replaceAll("@@@action@@@", "joinCategory('"+lista.get(i).getIdCategory()+"', '"+lista.get(i).getNameCategory()+"');");
				s = s.replaceAll("@@@info2@@@", "");				//info2
				if(join.equalsIgnoreCase("join")){
					if(tipo.equalsIgnoreCase("collection")){
						//pesquisa como colecao
						s = s.replaceAll("@@@join@@@", LING.equals("pt") ? "Entrar como colecionador" : "Join like collector");	//botao para participar como colecionador
						s = s.replaceAll("@@@hidden@@@", "button");				//botao oculto
						s = s.replaceAll("@@@info@@@", "");						//informacao jah participa
					}else{
						//pesquisa como interesse
						s = s.replaceAll("@@@join@@@", LING.equals("pt") ? "Entrar como interessado" : "Join like interested");	//botao para participar como colecionador
						s = s.replaceAll("@@@hidden@@@", "button");				//botao oculto
						s = s.replaceAll("@@@info@@@", "");						//informacao jah participa
					}				
				}else if(join.equalsIgnoreCase("unjoin collector")){
					//ja participa como colecionador 
					s = s.replaceAll("@@@join@@@", "Unjoin");					//botao unjoin
					s = s.replaceAll("@@@hidden@@@", "hidden");					//botao oculto
					s = s.replaceAll("@@@info@@@", LING.equals("pt") ? "Voc� j� entrou como colecionador" : "You already joined like collector");//informacao jah participa
				}else{
					//jah participa como interessado
					if(tipo.equalsIgnoreCase("collection")){
						s = s.replaceAll("@@@join@@@", LING.equals("pt") ? "Entrar como colecionador" : "Join like collector");			//botao para participar como colecionador
						s = s.replaceAll("@@@hidden@@@", "button");						//botao oculto
						s = s.replaceAll("@@@info@@@", LING.equals("pt") ? "Voc� j� tem um interesse" : "You already have a interest");	//informacao jah participa
					}else{
						s = s.replaceAll("@@@join@@@", "Unjoin");						//botao unjoin
						s = s.replaceAll("@@@hidden@@@", "hidden");						//botao oculto
						s = s.replaceAll("@@@info@@@", LING.equals("pt") ? "Voc� j� entrou como interessado" : "You already joined like interested");	//informacao jah participa
					}
				}
				resultHTML.append(s);
			}
			
			response.setContentType("text/html");
			response.getWriter().write(resultHTML.toString());
		}else{
			response.setContentType("text/html");
			response.getWriter().write("");
		}
	}

	/**
	 * Cria nova categoria com ajax
	 */
	@RequestMapping(value={"/ajaxCreateCategory"})
	private void ajaxCreateCategory(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		log.info(request.getRequestURL());
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);		//BD
		String cat = request.getParameter("category").trim();		//nome categoria
		
		//valida se existe categoria
		if(cat.length() >= 2){
			if(!IliketoDAO.readRecordExistsDatabase(db, "dbcategory", "name_category", cat)){
				//Cria nova categoria/grupo
				CategoryDAO categoryDAO = new CategoryDAO(db, request);
				Category category = new Category();
				category.setNameCategory(cat);
				category.setHobby("no");
				String idCreated = categoryDAO.create(category);
				
				//cria novo forum da categoria/grupo
				ForumDAO forumDAO = new ForumDAO(db, request);
				Forum forum = new Forum();
				forum.setIdCategory(idCreated);
				forum.setNameCategory(cat);			
				forumDAO.create(forum);

				log.info("Nova categoria/grupo criado - name: " + cat);
				response.setContentType("text/html");
				response.getWriter().write("ok");
				return;
			}
		}
		log.info("Nao criou categoria, erro ou jah existe! - name: " + cat);
		response.setContentType("text/html");
		response.getWriter().write("erro");
	}
	
	/**
	 * Entra para categoria com ajax
	 */
	@RequestMapping(value={"/ajaxJoinCategoryCollection"})
	private void ajaxJoinCategoryCollection(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		log.info(request.getRequestURL());
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CollectionDAO dao = new CollectionDAO(db, request);
		
		String idCollection = request.getParameter("idCollection");
		String idCategory = request.getParameter("idCat");
		String nameCategory = request.getParameter("nameCat");
		Collection collection = (Collection) dao.readById(idCollection, Collection.class);	//ler colecao
		
		if(collection.getIdCategory() == null || (!collection.getIdCategory().equals(idCategory))){		//valida se colecao pertence a categoria
			//entra para categoria
			collection.setIdCategory(idCategory);
			collection.setNameCategory(nameCategory);
			collection.setDescription(null);
			//por padrao todas notificacoes sao ativadas
			collection.setNotificCollection("Activate");
			collection.setNotificItem("Activate");
			collection.setNotificVideo("Activate");
			collection.setNotificEvent("Activate");
			collection.setNotificAnnounce("Activate");
			collection.setNotificTopic("Activate");
			collection.setNotificComment("Activate");
			
			dao.update(collection, false);								//atualiza colecao
			log.info("Cole��o: '"+ collection.getNameCollection() +"' entrou para a categoria/grupo: " + nameCategory);
			
			//cria notificacao para o grupo da categoria
			String myUserid = (String) request.getSession().getAttribute("userid");
			NotificationService.createNotification(db, idCategory, "collection", idCollection, Str.JOINED, myUserid);
			
			//remove interesse do membro no grupo se houver
			for( Interest i : new InterestDAO(db, null).listInterestByUser((String) request.getSession().getAttribute("userid")) ){
				if(i.getIdCategory() != null && i.getIdCategory().equals(idCategory)){				
					new InterestDAO(db, null).deleteInterest(i.getIdInterest());				//exclui interesse
					log.info("Interesse: '"+ i.getIdInterest() +"' removido da categoria/grupo: " + nameCategory);
				}
			}
		}		
		response.setContentType("text/html");
		response.getWriter().write("ok");
	}
	
	/**
	 * Entra para categoria com ajax
	 */
	@RequestMapping(value={"/ajaxJoinCategoryInterest"})
	private void ajaxJoinCategoryInterest(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		log.info(request.getRequestURL());
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		InterestDAO interestDAO = new InterestDAO(db, request);
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		String idCategory = request.getParameter("idCat");
		String nameCategory = request.getParameter("nameCat");
		
		if(idCategory != null && !idCategory.equals("") && nameCategory != null && !nameCategory.equals("")){
			Interest interest = new Interest();
			interest.setIdCategory(idCategory);
			interest.setNameCategory(nameCategory);
			interest.setIdMember(myUserid);
			//por padrao todas notificacoes sao ativadas
			interest.setNotificCollection("Activate");
			interest.setNotificItem("Activate");
			interest.setNotificVideo("Activate");
			interest.setNotificEvent("Activate");
			interest.setNotificAnnounce("Activate");
			interest.setNotificTopic("Activate");
			interest.setNotificComment("Activate");
			
			String idCreate = interestDAO.create(interest);		//cria interesse
			log.info("Interesse criado ok, id interesse: " + idCreate + " - nome categoria/grupo: " + interest.getNameCategory());
		}
		response.setContentType("text/html");
		response.getWriter().write("ok");
	}
	
	/**
	 * Lista interesses
	 */
	@RequestMapping(value={"/ajaxListInterests"})
	private void ajaxListInterests(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		log.info(request.getRequestURL());
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);	
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		InterestDAO interestDAO = new InterestDAO(db, request);
		List<Interest> listInterest = interestDAO.listInterestByUser(myUserid);

		String listEntry;		
		if(request.getParameter("listEntry") != null && !request.getParameter("listEntry").isEmpty()){
			listEntry = cms.getPageListEntry(request.getParameter("listEntry"));
		}else{
			listEntry = cms.getPageListEntry("697");		//List interest
		}
		
		HashMap<Class, String> mapModelListEntry = new HashMap<Class, String>();
		
		//lista objeto, lista entrada para cada tipo de objeto
		mapModelListEntry.put(Interest.class, listEntry);
		StringBuilder resultHTML = cms.parseBindingModelBean(listInterest, mapModelListEntry);
		
		response.setContentType("text/html");
		response.getWriter().write(resultHTML.toString());
	}
	
	
	/**
	 * Redireciona para pagina do grupo para nao membros
	 */
	@RequestMapping(value={"/groupCategory/join"})
	private String joinGroupCategory(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);		//BD
		String idCat = request.getParameter("idCat");				//id categoria
		
		if(idCat != null && !idCat.isEmpty()){
			CategoryDAO dao = new CategoryDAO(db, request);
			Category category = (Category) dao.readById(idCat, Category.class);			
			//valida categoria
			if(category != null){
				ModelILiketo model = new ModelILiketo(request, response);
				model.addAttribute("category", category);
				return "page.jsp?id=703";			//page join group of category
			}
		}
		return "/page.jsp?id=invalidPage"; 			//pagina invalida
	}
	
	/**
	 * Redireciona para pagina join do grupo
	 */
	@RequestMapping(value={"/groupCategory/joinGroup"})
	private String joinGroup(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);		//BD
		String idCat = request.getParameter("idCat");				//id categoria
		
		if(idCat != null && !idCat.isEmpty()){
			CategoryDAO dao = new CategoryDAO(db, request);
			Category category = (Category) dao.readById(idCat, Category.class);			
			//valida categoria
			if(category != null){
				ModelILiketo model = new ModelILiketo(request, response);
				model.addAttribute("category", category);
				return "page.jsp?id=695"; 		//page join group
			}
		}
		return "/page.jsp?id=invalidPage"; 		//pagina invalida		
	}
	
	/**
	 * Unjoin do membro no grupo
	 */
	@RequestMapping(value={"/groupCategory/unjoin"})
	private String unjoin(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CollectionDAO collectionDAO = new CollectionDAO(db, request);
		InterestDAO interestDAO = new InterestDAO(db, request);
		
		String idCategory = request.getParameter("idCat");
		String myUserid = (String) request.getSession().getAttribute("userid");
		List<Collection> listCollection = collectionDAO.listCollectionByUser(myUserid);
		List<Interest> listInterest = interestDAO.listInterestByUser(myUserid);
		
		//remove colecao do membro no grupo
		for(Collection c : listCollection){
			if(c.getIdCategory() != null && c.getIdCategory().equals(idCategory)){				
				c.setIdCategory("");
				c.setNameCategory("");
				collectionDAO.update(c, false);				//atualiza colecao
				log.info("Cole��o: '"+ c.getNameCollection() +"' saiu da categoria/grupo: " + idCategory);
			}
		}
		//remove interesse do membro no grupo
		for(Interest i : listInterest){
			if(i.getIdCategory() != null && i.getIdCategory().equals(idCategory)){
				interestDAO.deleteInterest(i.getIdInterest());					//exclui interesse
				log.info("Interesse: '"+ i.getIdInterest() +"' removido da categoria/grupo: " + idCategory);
			}
		}
		
		//return "page.jsp?id=703"; 	//page join group of category
		return "redirect:/ilt/groupCategory/group?idCat=" + idCategory;
	}
	
	/**
	 * Unjoin do membro no grupo
	 */
	@RequestMapping(value={"/groupCategory/unjoinHobby"})
	private String unjoinHobby(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		HobbyDAO dao = new HobbyDAO(db, request);
		
		String myUserid = (String) request.getSession().getAttribute("userid");
		String idCategory = request.getParameter("idCat");
		
		String SQL = "select h.id_hobby as id_hobby from dbhobby h where h.fk_user_id = '" +myUserid+ "' and h.fk_category_id = '" + idCategory+"'";
		String[][] alias = { {"dbhobby", "h"} };
		SQL = CS.transformSQLReal(SQL, alias);
		HashMap<String,String> registro  = db.query_record(SQL);
		
		//deleta o hobby do membro
		if(registro != null){
			String id = registro.get("id_hobby");
			if(id != null && !id.isEmpty()){
				dao.deleteHobby(id);
			}
		}
		
		//page join group of category
		return "redirect:/ilt/groupCategory/group?idCat=" + idCategory;
	}
	
	/**
	 * Cria nova categoria/grupo para o interesse
	 */
	@RequestMapping(value={"/groupCategory/interest/createCategory"})
	private String interestCreateCategory(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);	//BD
		String cat = request.getParameter("name_category");		//nome categoria
		
		//valida se existe categoria		
		if(IliketoDAO.readRecordExistsDatabase(db, "dbcategory", "name_category", cat)){
			
			//retorna mesma pagina com o erro que ja existe a categoria
			String msgErro = "Category already exists, choose category below or other name!";			
			return "redirect:/page.jsp?id=696&error=" + msgErro + "&category=" + cat;
			
		}else{
		
			//Cria nova categoria/grupo
			CategoryDAO categoryDAO = new CategoryDAO(db, request);
			Category category = new Category();
			category.setNameCategory(cat);
			String idCreated = categoryDAO.create(category);
			
			//cria novo forum da categoria/grupo
			ForumDAO forumDAO = new ForumDAO(db, request);
			Forum forum = new Forum();
			forum.setIdCategory(idCreated);
			forum.setNameCategory(cat);			
			forumDAO.create(forum);

			//Category e forum criados				
			log.info("Create Category/Group - name: " + cat);				
			
			//action redireciona para criar novo interesse e participar da categoria nova
			String action = "redirect:/ilt/interest/createInterest?idCat=" + idCreated + "&nameCat=" + cat; 
			return action;
		}
	}
	
	
	/**
	 * Cria categoria/grupo para colecao
	 */
	@RequestMapping(value={"/groupCategory/collection/createCategory"})
	private String collectionCreateCategory(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);			//BD
		String cat = request.getParameter("name_category");				//nome categoria
		String idCollection = request.getParameter("idCollection");		//id colecao
		
		//valida se existe categoria
		if(IliketoDAO.readRecordExistsDatabase(db, "dbcategory", "name_category", cat)){
			
			//retorna mesma pagina com o erro que ja existe a categoria
			String msgErro = "Note: Category already exists, choose category below or choose other name!";
			return "/page.jsp?id=622&idCollection="+idCollection+"&error=" + msgErro + "&category=" + cat;
			
		}else{
		
			//Cria nova categoria/grupo
			CategoryDAO categoryDAO = new CategoryDAO(db, request);
			Category category = new Category();
			category.setNameCategory(cat);
			String idCreated = categoryDAO.create(category);
			
			//cria novo forum da categoria/grupo
			ForumDAO forumDAO = new ForumDAO(db, request);
			Forum forum = new Forum();
			forum.setIdCategory(idCreated);
			forum.setNameCategory(cat);
			forumDAO.create(forum);

			//Category e forum criados
			log.info("Create Category/Group - name: " + cat);			
			
			//action redireciona para criar novo interesse e participar da categoria nova
			String action = "redirect:/ilt/collection/participateCategory?idCat=" + idCreated + "&nameCat=" + cat+"&idCollection="+idCollection;
			return action;
		}
	}
	
	
	/* REDIRECIONAMENTOS DAS PAGINAS DA CATEGORIA/GRUPO */
	
	
	/** 1 Redireciona para pagina principal do grupo */
	@RequestMapping(value={"/groupCategory/group"})
	private String groupCategoryHome(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);		//BD
		String idCat = request.getParameter("idCat");				//id categoria
		String myUserid = (String) request.getSession().getAttribute("userid");	
		
		if(idCat != null && !idCat.isEmpty()){
			CategoryDAO dao = new CategoryDAO(db, request);
			Category category = (Category) dao.readById(idCat, Category.class);
			//valida categoria
			if(category != null){
				ForumDAO forumDAO = new ForumDAO(db, request);
				Forum forum = (Forum) forumDAO.readByColumn("fk_category_id", idCat, Forum.class);	//recupera forum
				ModelILiketo model = new ModelILiketo(request, response);
				model.addAttribute("category", category);
				model.addAttribute("forum", forum);
				//verifica se entrou para o grupo
				if(IliketoDAO.validateMemberInCategory(db, idCat, myUserid)){
					model.addAttribute("join", "unjoin");	//jah participa, sair
				}else{
					model.addAttribute("join", "join");		//nao participa, entrar
				}
				
				//set request lista dos anuncios e eventos direcionados para este grupo
				request.setAttribute("listDirectedAds", AdvertiserController.getListDirectedAdsOneGroupEntry(idCat, request));				
				request.setAttribute("listDirectedEvents", AdvertiserController.getListDirectedEventsOneGroupEntry(idCat, request));				
				
				//verifica grupo colecoes/interesses e grupo de hobbies
				if(category.getHobby().equalsIgnoreCase("yes")){
					return "page.jsp?id=1088";		//pagina grupo categoria para hobbies
				}else{					
					return "page.jsp?id=623";		//pagina grupo categoria de colecao/interesse
				}
			}
		}
		return "/page.jsp?id=invalidPage"; 		//pagina invalida
	}
	
	/** 2 Redireciona para pagina Trade do grupo */
	@RequestMapping(value={"/groupCategory/trade"})
	private String groupCategoryTrade(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);		//BD
		String idCat = request.getParameter("idCat");				//id categoria
		
		if(idCat != null && !idCat.isEmpty()){
			CategoryDAO dao = new CategoryDAO(db, request);
			Category category = (Category) dao.readById(idCat, Category.class);			
			//valida categoria
			if(category != null){
				ModelILiketo model = new ModelILiketo(request, response);
				model.addAttribute("category", category);

				//set request lista dos anuncios e eventos direcionados para este grupo
				request.setAttribute("listDirectedAds", AdvertiserController.getListDirectedAdsOneGroupEntry(idCat, request));				
				request.setAttribute("listDirectedEvents", AdvertiserController.getListDirectedEventsOneGroupEntry(idCat, request));
				return "page.jsp?id=686";	//pagina trade
			}
		}
		return "/page.jsp?id=invalidPage"; 				//pagina invalida
	}
	
	/** 3 Redireciona para pagina Auction do grupo */
	@RequestMapping(value={"/groupCategory/auction"})
	private String groupCategoryAuction(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);		//BD
		String idCat = request.getParameter("idCat");				//id categoria
		
		if(idCat != null && !idCat.isEmpty()){
			CategoryDAO dao = new CategoryDAO(db, request);
			Category category = (Category) dao.readById(idCat, Category.class);			
			//valida categoria
			if(category != null){
				ModelILiketo model = new ModelILiketo(request, response);
				model.addAttribute("category", category);
				
				//set request lista dos anuncios e eventos direcionados para este grupo
				request.setAttribute("listDirectedAds", AdvertiserController.getListDirectedAdsOneGroupEntry(idCat, request));				
				request.setAttribute("listDirectedEvents", AdvertiserController.getListDirectedEventsOneGroupEntry(idCat, request));
				return "page.jsp?id=723";	//pagina Auction
			}
		}
		return "/page.jsp?id=invalidPage"; 				//pagina invalida
	}
	
	/** 4 Redireciona para pagina Store do grupo */
	@RequestMapping(value={"/groupCategory/store"})
	private String groupCategoryStore(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);		//BD
		String idCat = request.getParameter("idCat");				//id categoria
		
		if(idCat != null && !idCat.isEmpty()){
			CategoryDAO dao = new CategoryDAO(db, request);
			Category category = (Category) dao.readById(idCat, Category.class);			
			//valida categoria
			if(category != null){
				ModelILiketo model = new ModelILiketo(request, response);
				model.addAttribute("category", category);
				
				//set request lista dos anuncios e eventos direcionados para este grupo
				request.setAttribute("listDirectedAds", AdvertiserController.getListDirectedAdsOneGroupEntry(idCat, request));				
				request.setAttribute("listDirectedEvents", AdvertiserController.getListDirectedEventsOneGroupEntry(idCat, request));
				return "page.jsp?id=781";	//pagina Store
			}
		}
		return "/page.jsp?id=invalidPage"; 				//pagina invalida
	}
	
	/** 5 Redireciona para pagina Forum do grupo */
	@RequestMapping(value={"/groupCategory/forum"})
	private String groupCategoryForum(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);		//BD
		String idCat = request.getParameter("idCat");				//id categoria
		
		if(idCat != null && !idCat.isEmpty()){
			CategoryDAO dao = new CategoryDAO(db, request);
			Category category = (Category) dao.readById(idCat, Category.class);		//recupera categoria		
			//valida categoria
			if(category != null){
				ForumDAO forumDAO = new ForumDAO(db, request);
				Forum forum = (Forum) forumDAO.readByColumn("fk_category_id", idCat, Forum.class);	//recupera forum
				ModelILiketo model = new ModelILiketo(request, response);
				model.addAttribute("category", category);
				model.addAttribute("forum", forum);
				
				//set request lista dos anuncios e eventos direcionados para este grupo
				request.setAttribute("listDirectedAds", AdvertiserController.getListDirectedAdsOneGroupEntry(idCat, request));				
				request.setAttribute("listDirectedEvents", AdvertiserController.getListDirectedEventsOneGroupEntry(idCat, request));
				return "page.jsp?id=675";	//pagina Forum
			}
		}
		return "/page.jsp?id=invalidPage"; 				//pagina invalida
	}
	
	/** 6 Redireciona para pagina Collections do grupo */
	@RequestMapping(value={"/groupCategory/collections"})
	private String groupCategoryCollections(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);		//BD
		String idCat = request.getParameter("idCat");				//id categoria
		
		if(idCat != null && !idCat.isEmpty()){
			CategoryDAO dao = new CategoryDAO(db, request);
			Category category = (Category) dao.readById(idCat, Category.class);			
			//valida categoria
			if(category != null){
				ModelILiketo model = new ModelILiketo(request, response);
				model.addAttribute("category", category);
				
				//set request lista dos anuncios e eventos direcionados para este grupo
				request.setAttribute("listDirectedAds", AdvertiserController.getListDirectedAdsOneGroupEntry(idCat, request));				
				request.setAttribute("listDirectedEvents", AdvertiserController.getListDirectedEventsOneGroupEntry(idCat, request));
				return "page.jsp?id=867";	//pagina Collections
			}
		}
		return "/page.jsp?id=invalidPage"; 				//pagina invalida
	}
	
	/** 7 Redireciona para pagina Events do grupo */
	@RequestMapping(value={"/groupCategory/events"})
	private String groupCategoryEvents(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);		//BD
		String idCat = request.getParameter("idCat");				//id categoria
		
		if(idCat != null && !idCat.isEmpty()){
			CategoryDAO dao = new CategoryDAO(db, request);
			Category category = (Category) dao.readById(idCat, Category.class);			
			//valida categoria
			if(category != null){
				ModelILiketo model = new ModelILiketo(request, response);
				model.addAttribute("category", category);
				
				//set request lista dos anuncios e eventos direcionados para este grupo
				request.setAttribute("listDirectedAds", AdvertiserController.getListDirectedAdsOneGroupEntry(idCat, request));				
				request.setAttribute("listDirectedEvents", AdvertiserController.getListDirectedEventsOneGroupEntry(idCat, request));
				return "page.jsp?id=653";	//pagina Events
			}
		}
		return "/page.jsp?id=invalidPage"; 				//pagina invalida
	}
	
	/** 8 Redireciona para pagina Members do grupo */
	@RequestMapping(value={"/groupCategory/members"})
	private String groupCategoryMembers(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);		//BD
		String idCat = request.getParameter("idCat");				//id categoria
		
		if(idCat != null && !idCat.isEmpty()){
			CategoryDAO dao = new CategoryDAO(db, request);
			Category category = (Category) dao.readById(idCat, Category.class);			
			//valida categoria
			if(category != null){
				ModelILiketo model = new ModelILiketo(request, response);
				model.addAttribute("category", category);
				
				//set request lista dos anuncios e eventos direcionados para este grupo
				request.setAttribute("listDirectedAds", AdvertiserController.getListDirectedAdsOneGroupEntry(idCat, request));				
				request.setAttribute("listDirectedEvents", AdvertiserController.getListDirectedEventsOneGroupEntry(idCat, request));
				
				//verifica grupo colecoes/interesses e grupo de hobbies
				if(category.getHobby().equalsIgnoreCase("yes")){
					return "page.jsp?id=1089";	//pagina members hobbies
				}else{					
					return "page.jsp?id=679";	//pagina Members
				}
			}
		}
		return "/page.jsp?id=invalidPage"; 				//pagina invalida
	}
	
	/** Redireciona para pagina todos anuncios dos grupos */
	@RequestMapping(value={"/groups/announcements"})
	private String todosAnunciosGrupos(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		InterestDAO interestDAO = new InterestDAO(db, request);
		CollectionDAO collectionDAO = new CollectionDAO(db, request);
		List<Interest> listInterest = interestDAO.listInterestByUser(myUserid);
		List<Collection> listCollection = collectionDAO.listCollectionByUser(myUserid);
		
		String condicao = "";
		if(!listCollection.isEmpty()){
			condicao += " t1.fk_category_id = '" +listCollection.get(0).getIdCategory()+ "' ";
			for(int i = 1; i < listCollection.size(); i++){
				condicao += " or t1.fk_category_id = '" +listCollection.get(i).getIdCategory()+ "' ";
			}
		}
		if(!listInterest.isEmpty()){
			condicao += (condicao.equals("") ? "" : " or ") + " t1.fk_category_id = '" +listInterest.get(0).getIdCategory()+ "' ";
			for(int i = 1; i < listInterest.size(); i++){
				condicao += " or t1.fk_category_id = '" +listInterest.get(i).getIdCategory()+ "' ";
			}
		}
		
		//valida se existe algum grupo que o membro participa
		if(!condicao.isEmpty()){
			condicao = " (" + condicao + ") ";	//select todos anuncios que pertence aos grupos do membro
		
			String SQL = "select * from dbannounce t1 "
					+ " where t1.status ilike 'For%' and " + condicao
					+ " order by t1.featured desc, t1.date_updated desc limit 30;";		//ordem destaque yes e data updated
			
			String[][] alias = { {"dbannounce", "t1"} };
			SQL = CS.transformSQLReal(SQL, alias);
			
			LinkedHashMap<String,HashMap<String,String>> records  = db.query_records(SQL);
			ArrayList<Announce> listaAnuncios = new ArrayList<Announce>();
			
			String dbad = "dbannounce";
			for(String rec : records.keySet()){
				Announce anuncio = new Announce();
				anuncio.setIdAnnounce(records.get(rec).get(CS.getCOL(db, dbad, "id_announce")));
				anuncio.setIdItem(records.get(rec).get(CS.getCOL(db, dbad, "fk_item_id")));
				anuncio.setIdCollection(records.get(rec).get(CS.getCOL(db, dbad, "fk_collection_id")));
				anuncio.setIdCategory(records.get(rec).get(CS.getCOL(db, dbad, "fk_category_id")));
				anuncio.setNameCategory(records.get(rec).get(CS.getCOL(db, dbad, "name_category")));
				anuncio.setTitle(records.get(rec).get(CS.getCOL(db, dbad, "title")));
				anuncio.setDescription(records.get(rec).get(CS.getCOL(db, dbad, "description")));
				anuncio.setTypeAnnounce(records.get(rec).get(CS.getCOL(db, dbad, "type_announce")));
				anuncio.setPriceFixed(records.get(rec).get(CS.getCOL(db, dbad, "price_fixed")));
				anuncio.setPriceInitial(records.get(rec).get(CS.getCOL(db, dbad, "price_initial")));
				anuncio.setDateInitial(records.get(rec).get(CS.getCOL(db, dbad, "date_initial")));
				anuncio.setBidActual(records.get(rec).get(CS.getCOL(db, dbad, "bid_actual")));
				anuncio.setLasting(records.get(rec).get(CS.getCOL(db, dbad, "lasting")));
				anuncio.setTotalBids(records.get(rec).get(CS.getCOL(db, dbad, "total_bids")));
				anuncio.setDateCreated(records.get(rec).get(CS.getCOL(db, dbad, "date_created")));
				anuncio.setDateUpdated(records.get(rec).get(CS.getCOL(db, dbad, "date_updated")));
				anuncio.setPathPhotoAd(records.get(rec).get(CS.getCOL(db, dbad, "path_photo_ad")));
				anuncio.setOfferedPrice(records.get(rec).get(CS.getCOL(db, dbad, "offered_price")));
				anuncio.setDetails(records.get(rec).get(CS.getCOL(db, dbad, "details")));
				anuncio.setFeatured(records.get(rec).get(CS.getCOL(db, dbad, "featured")));
				anuncio.setStatus(records.get(rec).get(CS.getCOL(db, dbad, "status")));
				listaAnuncios.add(anuncio);
			}
			
			if(!listaAnuncios.isEmpty()){
				
				//Cms config para pages
				CmsConfigILiketo cms = new CmsConfigILiketo(request, null);
				String listEntrySell = cms.getPageListEntry("1016");				//list anuncio - all ads sell
				String listEntryAuction = cms.getPageListEntry("1036");				//list anuncio - all ads auction
				String listEntryExchange = cms.getPageListEntry("1037");			//list anuncio - all ads exchange
				String listEntrySellEx = cms.getPageListEntry("1038");				//list anuncio - all ads sell/exchange
				String listEntryPurchase = cms.getPageListEntry("1039");			//list anuncio - all ads purchase
				
				StringBuilder resultHTML = new StringBuilder("");
				
				for(Announce ad : listaAnuncios){
					if(ad.getFeatured().equals("yes")){
						ad.setFeatured("div_com_destaque");		//anuncio com destaque utiliza um class css diferente
					}else{
						ad.setFeatured("div_sem_destaque");		//class css anuncio sem destaque
					}
					//parse do objeto na lista de entrada que corresponde ao tipo de anuncio
					if(ad.getTypeAnnounce().equals("Sell")){						
						resultHTML.append(cms.parseBindingModelBean(listEntrySell, ad));
					}else if(ad.getTypeAnnounce().equals("Auction")){
						resultHTML.append(cms.parseBindingModelBean(listEntryAuction, ad));
					}else if(ad.getTypeAnnounce().equals("Exchange")){
						resultHTML.append(cms.parseBindingModelBean(listEntryExchange, ad));
					}else if(ad.getTypeAnnounce().equals("Sell/Exchange")){
						resultHTML.append(cms.parseBindingModelBean(listEntrySellEx, ad));
					}else if(ad.getTypeAnnounce().equals("Purchase")){
						resultHTML.append(cms.parseBindingModelBean(listEntryPurchase, ad));
					}
				}
				
				ModelILiketo model = new ModelILiketo(request, null);
				model.addAttribute("listAllAdsGroups", resultHTML);
				return "/page.jsp?id=1015"; 	//pagina todos anuncios dos grupos que pariticipa
			}
		}
		
		ModelILiketo model = new ModelILiketo(request, null);
		model.addAttribute("listAllAdsGroups", "There are no ads of groups!");
		return "/page.jsp?id=1015"; 			//pagina todos anuncios dos grupos que pariticipa
	}
	
	
}
