package com.iliketo.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.CommentDAO;
import com.iliketo.dao.ForumDAO;
import com.iliketo.dao.IliketoDAO;
import com.iliketo.dao.LikesDAO;
import com.iliketo.dao.TopicDAO;
import com.iliketo.model.Collection;
import com.iliketo.model.Comment;
import com.iliketo.model.Forum;
import com.iliketo.model.Likes;
import com.iliketo.model.Member;
import com.iliketo.model.Topic;
import com.iliketo.service.NotificationService;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ColumnsSingleton;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class LikesController {
	

	static final Logger log = Logger.getLogger(LikesController.class);
	
	/**
	 * Método intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
	
	@RequestMapping(value={"/ajax/likes/isLikeOrUnlike"})
	public void isLikeOrUnlike(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);	//DB
		LikesDAO dao = new LikesDAO(db, null);
		
		String tipoPost = request.getParameter("tipoPost");
		String idPost = request.getParameter("idPost"); 
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		String status = "";
		//valida curtida
		if (dao.jaCurtiu(tipoPost, idPost, myUserid)) {
			status = "unlike";
		}else{
			//like
			status = "like";			
		}
		String total = dao.getTotalCurtidas(tipoPost, idPost);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", status);
    	jsonObj.put("total", total);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().write(jsonObj.toString());
		
	}	
	
	@RequestMapping(value={"/ajax/likes/likeOrUnlike"})
	public void likeOrUnlike(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);	//DB
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		String myUserid = (String) request.getSession().getAttribute("userid");
		String nickname = (String)((Member) request.getSession().getAttribute("member")).getNickname();
		String photo = (String)((Member) request.getSession().getAttribute("member")).getPathPhoto();
		LikesDAO dao = new LikesDAO(db, null);
		
		Likes curtir = (Likes)cms.getObjectOfParameter(Likes.class);
		curtir.setIdMember(myUserid);
		curtir.setNickname(nickname);
		curtir.setPathPhoto(photo);
		//curtir.setIdOwner(idOwner);
				
		String status = "";
		//valida curtida
		if (dao.jaCurtiu(curtir.getPostType(), curtir.getIdPost(), myUserid)) {
			//unlike
			dao.excluirCurtir(curtir.getPostType(), curtir.getIdPost(), myUserid);
			status = "unlike";
		}else{
			//like
			String idCreated = dao.curtir(curtir);
			status = "like";
			//gerar notificacoes
			NotificationService.createNotification(db, "", "curtir", idCreated, Str.INCLUDED, myUserid);
		}
		String total = dao.getTotalCurtidas(curtir.getPostType(), curtir.getIdPost());
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", status);
    	jsonObj.put("total", total);
    	
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().write(jsonObj.toString());
		
	}	
	
	@RequestMapping(value={"/ajax/likes/listLikes"})
	public void listLikes(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		
		String tipoPost = request.getParameter("tipoPost");
		String idPost = request.getParameter("idPost"); 
		
		String SQL = "select m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
			+ "from dbmembers as m join dblikes as t1 on m.id_member = t1.fk_user_id "
			+ "where t1.post_type = '" +tipoPost+ "' and t1.fk_post_id = '" +idPost+ "';";
		
		String[][] alias = new String[][] { {"dbmembers", "m"}, {"dblikes", "t1"} };
		SQL = CS.transformSQLReal(SQL, alias);
		System.out.println("SQL: " + SQL);
		LinkedHashMap<String,HashMap<String,String>> registros = db.query_records(SQL);
		
		String listEntry = cms.getPageListEntry("1096");
		ArrayList<Member> listaMembros = new ArrayList<Member>();
		
		for(String rec : registros.keySet()){
			Member m = new Member();
			m.setIdMember(registros.get(rec).get("id_member"));
			m.setNickname(registros.get(rec).get("nickname"));
			m.setPathPhoto(registros.get(rec).get("path_photo_member"));
			listaMembros.add(m);
		}
		
		StringBuilder resultHTML = new StringBuilder();
		for(Member m : listaMembros){			
			resultHTML.append(cms.parseBindingModelBean(listEntry, m));			
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().write(resultHTML.toString());	
	}
	
}
