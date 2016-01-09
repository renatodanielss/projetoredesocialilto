package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.CommentDAO;
import com.iliketo.dao.ForumDAO;
import com.iliketo.dao.IliketoDAO;
import com.iliketo.dao.TopicDAO;
import com.iliketo.model.Comment;
import com.iliketo.model.Forum;
import com.iliketo.model.Topic;
import com.iliketo.service.NotificationService;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class ForumController {
	

	static final Logger log = Logger.getLogger(ForumController.class);
	
	/**
	 * Método intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
	
	/**
	 * Redireciona para pagina do comentario do topico
	 */
	@RequestMapping(value={"/group/forum/topic"})
	public String forumTopic(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);	//DB
		String idTopic = request.getParameter("idTop"); 		//id do topico
		String idForum = request.getParameter("idForum"); 		//id do forum
		
		ForumDAO forumDAO = new ForumDAO(db, request);
		TopicDAO topicDAO = new TopicDAO(db, request);
		Forum forum = (Forum) forumDAO.readById(idForum, Forum.class);
		Topic topico = (Topic) topicDAO.readById(idTopic, Topic.class);
		
		//valida forum e topico
		if(forum != null && topico != null && forum.getIdForum().equals(topico.getIdForum())){
			ModelILiketo model = new ModelILiketo(request, response);
			model.addAttribute("forum", forum);
			model.addAttribute("topic", topico);
			return "/page.jsp?id=628&topic=" + idTopic; 	//page comment topic
		}
		
		return "/page.jsp?id=invalidPage"; 					//pagina invalida
	}
	
	@RequestMapping(value={"/group/forum/createTopic"})
	public String createTopic(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		TopicDAO topicDAO = new TopicDAO(db, request);
		
		Topic topic = (Topic) cms.getObjectOfParameter(Topic.class);	//objeto com dados do form
		String idCreate = topicDAO.create(topic);						//cria topico
		
		//cria notificacao para o grupo da categoria
		String idCategory = IliketoDAO.getValueOfDatabase(db, "fk_category_id", "dbforum", "id_forum", topic.getIdForum());
		if(idCategory != null && !idCategory.equals("")){
			String myUserid = (String) request.getSession().getAttribute("userid");
			NotificationService.createNotification(db, idCategory, "topic", idCreate, Str.INCLUDED, myUserid);
		}
		
		return "redirect:/ilt/group/forum/topic?idTop=" + idCreate + "&idForum=" + topic.getIdForum(); 	//redirect para page comment topic
		
	}
	
	@RequestMapping(value={"/group/forum/topic/createComment"})
	public String createComment(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		CommentDAO commentDAO = new CommentDAO(db, request);
		String idForum = request.getParameter("idForum"); 		//id do forum
		
		Comment comment = (Comment) cms.getObjectOfParameter(Comment.class);	//objeto com dados do form
		String idCreate = commentDAO.create(comment);							//cria comentario
		
		//cria notificacao para o grupo da categoria
		String idCategory = IliketoDAO.getValueOfDatabase(db, "fk_category_id", "dbforum", "id_forum", idForum);
		if(idCategory != null && !idCategory.equals("")){
			String myUserid = (String) request.getSession().getAttribute("userid");
			NotificationService.createNotification(db, idCategory, "comment", idCreate, Str.INCLUDED, myUserid);
		}
		
		return "redirect:/ilt/group/forum/topic?idTop=" + comment.getIdTopic() + "&idForum=" + idForum; 	//redirect para page comment topic
		
	}	
	

}
