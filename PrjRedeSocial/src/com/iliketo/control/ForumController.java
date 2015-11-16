package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.CategoryDAO;
import com.iliketo.dao.CommentDAO;
import com.iliketo.dao.ForumDAO;
import com.iliketo.dao.TopicDAO;
import com.iliketo.model.Category;
import com.iliketo.model.Comment;
import com.iliketo.model.Forum;
import com.iliketo.model.Topic;
import com.iliketo.service.NotificationService;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class ForumController {
	

	/**
	 * Redireciona para pagina do comentario do topico
	 */
	@RequestMapping(value={"/group/forum/topic"})
	public String forumTopic(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @ForumController url='/group/forum/topic'");
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);	//DB
		String idTopic = request.getParameter("idTop"); 		//id do topico
		String idForum = request.getParameter("idForum"); 		//id do forum
		
		ForumDAO forumDAO = new ForumDAO(db, request);
		TopicDAO topicDAO = new TopicDAO(db, request);
		Forum forum = (Forum) forumDAO.readById(idForum, Forum.class);
		Topic topico = (Topic) topicDAO.readById(idTopic, Topic.class);
		
		//valida forum e topico
		if(forum != null && topico != null){
			ModelILiketo model = new ModelILiketo(request, response);
			model.addAttribute("forum", forum);
			model.addAttribute("topic", topico);
			return "/page.jsp?id=628&topic=" + idTopic; 	//page comment topic
		}
		
		return "/page.jsp?id=invalidPage"; 					//pagina invalida
	}
	
	@RequestMapping(value={"/group/forum/createTopic"})
	public String createTopic(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @ForumController url='/group/forum/createTopic'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		TopicDAO topicDAO = new TopicDAO(db, request);
		
		Topic topic = (Topic) cms.getObjectOfParameter(Topic.class);	//objeto com dados do form
		String idCreate = topicDAO.create(topic);						//cria topico
		
		//cria notificacao para o grupo da categoria
		String idCategory = (String) request.getSession().getAttribute("s_id_category");
		if(idCategory != null && !idCategory.equals("")){
			String myUserid = (String) request.getSession().getAttribute("userid");
			NotificationService.createNotification(db, idCategory, "topic", idCreate, Str.INCLUDED, myUserid);
		}
		
		return "redirect:/ilt/group/forum/topic?id=" + idCreate; 		//redirect para page comment topic
		
	}
	
	@RequestMapping(value={"/group/forum/topic/createComment"})
	public String createComment(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @ForumController url='/group/forum/topic/createComment'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		CommentDAO commentDAO = new CommentDAO(db, request);
		
		Comment comment = (Comment) cms.getObjectOfParameter(Comment.class);	//objeto com dados do form
		String idCreate = commentDAO.create(comment);							//cria comentario
		
		//cria notificacao para o grupo da categoria
		String idCategory = (String) request.getSession().getAttribute("s_id_category");
		if(idCategory != null && !idCategory.equals("")){
			String myUserid = (String) request.getSession().getAttribute("userid");
			NotificationService.createNotification(db, idCategory, "comment", idCreate, Str.INCLUDED, myUserid);
		}
		
		return "redirect:/ilt/group/forum/topic?id=" + comment.getIdTopic(); 	//redirect para page comment topic
		
	}	
	

}
