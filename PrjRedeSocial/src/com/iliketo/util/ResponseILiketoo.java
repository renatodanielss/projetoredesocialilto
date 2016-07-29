package com.iliketo.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class ResponseILiketoo {

	
	public static void respostaJSONObject(HttpServletResponse res, JSONObject jsonObj) throws IOException{
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		res.getWriter().write(jsonObj.toString());
	}
	
	public static void respostaJSONArray(HttpServletResponse res, JSONArray jsonArray) throws IOException{
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		res.getWriter().write(jsonArray.toString());
	}
	
	public static void respostaHtml(HttpServletResponse res, String conteudoHtml) throws IOException{
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html");
		res.getWriter().write(conteudoHtml.toString());
	}
}
