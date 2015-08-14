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

import com.iliketo.dao.CollectionDAO;
import com.iliketo.dao.IliketoDAO;
import com.iliketo.dao.InterestDAO;
import com.iliketo.model.Collection;
import com.iliketo.model.Interest;
import com.iliketo.util.ColumnsSingleton;
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
	 * Redireciona para pagina principal do grupo
	 */
	@RequestMapping(value={"/groupCategory"})
	private String groupCategory(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CategoryController url='/groupCategory'");
		
		HttpSession session = request.getSession();		
		String s_id_category = (String) session.getAttribute("s_id_category");	//id da categoria na sessao
		String idCategory = request.getParameter("idCat");						//id da categoria requisitado na url
		
		//seta configuracoes do id da categoria, grupo e forum na sessao
		if(s_id_category == null || !idCategory.equals(s_id_category)){
			DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
			String idGroup = IliketoDAO.getValueOfDatabase(db, "id_group", "dbgroup", "fk_category_id", idCategory);
			String idForum = IliketoDAO.getValueOfDatabase(db, "id_forum", "dbforum", "fk_group_id", idGroup);
			session.setAttribute("s_id_category", idCategory);
			session.setAttribute("s_id_group", idGroup);
			session.setAttribute("s_id_forum", idForum);
		}
		
		return "page.jsp?id=623&group=" + session.getAttribute("idGroup"); 	//page group of category
	}
	
	/**
	 * Redireciona para pagina do grupo para nao membros
	 */
	@RequestMapping(value={"/groupCategory/join"})
	private String joinGroupCategory(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("Log - " + "request @CategoryController url='/groupCategory/join'");
		
		HttpSession session = request.getSession();
		String s_id_category = (String) session.getAttribute("s_id_category");	//id da categoria na sessao
		String idCategory = request.getParameter("idCat");						//id da categoria requisitado na url
		
		//seta configuracoes do id da categoria, grupo e forum na sessao
		if(s_id_category == null || !idCategory.equals(s_id_category)){
			DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
			String idGroup = IliketoDAO.getValueOfDatabase(db, "id_group", "dbgroup", "fk_category_id", idCategory);
			String idForum = IliketoDAO.getValueOfDatabase(db, "id_forum", "dbforum", "fk_group_id", idGroup);
			session.setAttribute("s_id_category", idCategory);
			session.setAttribute("s_id_group", idGroup);
			session.setAttribute("s_id_forum", idForum);
		}
		
		return "page.jsp?id=703"; 	//page join group of category
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
				collectionDAO.update(c);				//atualiza colecao
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
