package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.CommentDAO;
import com.iliketo.dao.TopicDAO;
import com.iliketo.model.Comment;
import com.iliketo.model.Topic;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.Str;


@Controller
public class ForumController {
	

	/**
	 * Redireciona para pagina do comentario do topico
	 */
	@RequestMapping(value={"/group/forum/topic"})
	public String forumTopic(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @ForumController url='/group/forum/topic'");
		
		String id = request.getParameter("id"); 		//id do topico
		
		return "/page.jsp?id=628&topic=" + id; 			//page comment topic
		
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
		
		//seta dados da mensagem na notificaoGrupo
		//dao.create(notificaoGrupo);	//cria notificacao
		
		return "redirect:/ilt/group/forum/topic?id=" + comment.getIdTopic(); 	//redirect para page comment topic
		
	}	
	

}
