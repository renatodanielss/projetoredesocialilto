package com.iliketo.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.CategoryDAO;
import com.iliketo.dao.CollectionDAO;
import com.iliketo.dao.ForumDAO;
import com.iliketo.dao.IliketoDAO;
import com.iliketo.dao.InterestDAO;
import com.iliketo.model.Category;
import com.iliketo.model.Collection;
import com.iliketo.model.Forum;
import com.iliketo.model.Interest;
import com.iliketo.util.ColumnsSingleton;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class CategoryController {
	
	@RequestMapping(value={"/searchCategory"})
	public void searchCategory(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException{
		
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
	 * Redireciona para pagina do grupo para nao membros
	 */
	@RequestMapping(value={"/groupCategory/join"})
	private String joinGroupCategory(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CategoryController url='/groupCategory/join'");
		
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
		
		System.out.println("Log - " + "request @CategoryController url='/groupCategory/joinGroup'");
		
		return "page.jsp?id=695"; 	//page join group
	}
	
	/**
	 * Unjoin do membro no grupo
	 */
	@RequestMapping(value={"/groupCategory/unjoin"})
	private String unjoin(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CategoryController url='/groupCategory/unjoin'");
		
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
				System.out.println("Log - Coleção: '"+ c.getNameCollection() +"' saiu da categoria/grupo: " + idCategory);
			}
		}
		//remove interesse do membro no grupo
		for(Interest i : listInterest){
			if(i.getIdCategory() != null && i.getIdCategory().equals(idCategory)){
				interestDAO.deleteInterest(i.getIdInterest());					//exclui interesse
				System.out.println("Log - Interesse: '"+ i.getIdInterest() +"' removido da categoria/grupo: " + idCategory);
			}
		}
		
		return "page.jsp?id=703"; 	//page join group of category
	}
	
	
	/**
	 * Cria nova categoria/grupo para o interesse
	 */
	@RequestMapping(value={"/groupCategory/interest/createCategory"})
	private String interestCreateCategory(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CategoryController url='/groupCategory/interest/createCategory'");
		
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
			System.out.println("Log - Create Category/Group - name: " + cat);				
			
			//action redireciona para criar novo interesse e participar da categoria nova
			String action = "/ilt/interest/createInterest?idCat=" + idCreated + "&nameCat=" + cat; 
			return action;
		}
	}
	
	
	/**
	 * Cria categoria/grupo para colecao
	 */
	@RequestMapping(value={"/groupCategory/collection/createCategory"})
	private String collectionCreateCategory(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CategoryController url='/groupCategory/collection/createCategory'");
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);			//BD
		String cat = request.getParameter("name_category");				//nome categoria
		String idCollection = request.getParameter("idCollection");		//id colecao
		
		//valida se existe categoria
		if(IliketoDAO.readRecordExistsDatabase(db, "dbcategory", "name_category", cat)){
			
			//retorna mesma pagina com o erro que ja existe a categoria
			String msgErro = "Category already exists, choose category below or other name!";
			return "redirect:/page.jsp?id=622&error=" + msgErro + "&category=" + cat;
			
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
			System.out.println("Log - Create Category/Group - name: " + cat);			
			
			//action redireciona para criar novo interesse e participar da categoria nova
			String action = "/ilt/collection/participateCategory?idCat=" + idCreated + "&nameCat=" + cat+"&idCollection="+idCollection;
			return action;
		}
	}
	
	
	/* REDIRECIONAMENTOS DAS PAGINAS DA CATEGORIA/GRUPO */
	
	
	/** 1 Redireciona para pagina principal do grupo */
	@RequestMapping(value={"/groupCategory/group"})
	private String groupCategoryHome(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CategoryController url='/groupCategory/group'");
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);		//BD
		String idCat = request.getParameter("idCat");				//id categoria
		
		if(idCat != null && !idCat.isEmpty()){
			CategoryDAO dao = new CategoryDAO(db, request);
			Category category = (Category) dao.readById(idCat, Category.class);			
			//valida categoria
			if(category != null){
				ForumDAO forumDAO = new ForumDAO(db, request);
				Forum forum = (Forum) forumDAO.readById(idCat, Forum.class);		//recupera forum
				ModelILiketo model = new ModelILiketo(request, response);
				model.addAttribute("category", category);
				model.addAttribute("forum", forum);
				return "page.jsp?id=623";	//pagina grupo
			}
		}
		return "/page.jsp?id=invalidPage"; 				//pagina invalida
	}
	
	/** 2 Redireciona para pagina Trade do grupo */
	@RequestMapping(value={"/groupCategory/trade"})
	private String groupCategoryTrade(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CategoryController url='/groupCategory/trade'");
		
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
				return "page.jsp?id=686";	//pagina trade
			}
		}
		return "/page.jsp?id=invalidPage"; 				//pagina invalida
	}
	
	/** 3 Redireciona para pagina Auction do grupo */
	@RequestMapping(value={"/groupCategory/auction"})
	private String groupCategoryAuction(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CategoryController url='/groupCategory/auction'");
		
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
				return "page.jsp?id=723";	//pagina Auction
			}
		}
		return "/page.jsp?id=invalidPage"; 				//pagina invalida
	}
	
	/** 4 Redireciona para pagina Store do grupo */
	@RequestMapping(value={"/groupCategory/store"})
	private String groupCategoryStore(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CategoryController url='/groupCategory/store'");
		
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
				return "page.jsp?id=781";	//pagina Store
			}
		}
		return "/page.jsp?id=invalidPage"; 				//pagina invalida
	}
	
	/** 5 Redireciona para pagina Forum do grupo */
	@RequestMapping(value={"/groupCategory/forum"})
	private String groupCategoryForum(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CategoryController url='/groupCategory/forum'");
		
		//dao
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);		//BD
		String idCat = request.getParameter("idCat");				//id categoria
		
		if(idCat != null && !idCat.isEmpty()){
			CategoryDAO dao = new CategoryDAO(db, request);
			Category category = (Category) dao.readById(idCat, Category.class);		//recupera categoria		
			//valida categoria
			if(category != null){
				ForumDAO forumDAO = new ForumDAO(db, request);
				Forum forum = (Forum) forumDAO.readById(idCat, Forum.class);		//recupera forum
				ModelILiketo model = new ModelILiketo(request, response);
				model.addAttribute("category", category);
				model.addAttribute("forum", forum);
				return "page.jsp?id=675";	//pagina Forum
			}
		}
		return "/page.jsp?id=invalidPage"; 				//pagina invalida
	}
	
	/** 6 Redireciona para pagina Collections do grupo */
	@RequestMapping(value={"/groupCategory/collections"})
	private String groupCategoryCollections(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CategoryController url='/groupCategory/collections'");
		
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
				return "page.jsp?id=867";	//pagina Collections
			}
		}
		return "/page.jsp?id=invalidPage"; 				//pagina invalida
	}
	
	/** 7 Redireciona para pagina Events do grupo */
	@RequestMapping(value={"/groupCategory/events"})
	private String groupCategoryEvents(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CategoryController url='/groupCategory/events'");
		
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
				return "page.jsp?id=653";	//pagina Events
			}
		}
		return "/page.jsp?id=invalidPage"; 				//pagina invalida
	}
	
	/** 8 Redireciona para pagina Members do grupo */
	@RequestMapping(value={"/groupCategory/members"})
	private String groupCategoryMembers(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CategoryController url='/groupCategory/members'");
		
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
				return "page.jsp?id=679";	//pagina Members
			}
		}
		return "/page.jsp?id=invalidPage"; 				//pagina invalida
	}
	
	

}
