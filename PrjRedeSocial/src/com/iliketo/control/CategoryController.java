package com.iliketo.control;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.IliketoDAO;
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
		String idCategory = request.getParameter("id");							//id da categoria requisitado na url
		
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

}
