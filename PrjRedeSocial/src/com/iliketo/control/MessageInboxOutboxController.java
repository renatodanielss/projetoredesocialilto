package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.CommentDAO;
import com.iliketo.dao.MessageInboxDAO;
import com.iliketo.model.Comment;
import com.iliketo.model.MessageInbox;
import com.iliketo.service.NotificationService;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class MessageInboxOutboxController {
	

	/**
	 * Redireciona para pagina caixa de entrada de mensagens
	 */
	@RequestMapping(value={"/message/inbox"})
	public String inbox(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @MessageInboxOutboxController url='/message/inbox'");
		
		return "/page.jsp?id=811"; 			//page message inbox
		
	}
	
	/**
	 * Redireciona para pagina caixa de saida de mensagens
	 */
	@RequestMapping(value={"/message/outbox"})
	public String outbox(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @MessageInboxOutboxController url='/message/outbox'");
		
		return "/page.jsp?id=813"; 			//page message outbox
		
	}
	
	/**
	 * Redireciona para pagina visualizar mensagem recebida
	 */
	@RequestMapping(value={"/message/inbox/view"})
	public String viewMessageInbox(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @MessageInboxOutboxController url='/message/inbox/view'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		MessageInboxDAO messageDAO = new MessageInboxDAO(db, request);
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		String id = request.getParameter("id");
		MessageInbox messageInbox = (MessageInbox) messageDAO.readById(id, MessageInbox.class); //ler dados atual da mensagem
		
		//valida mensagem caixa de entrada do membro(destinatario)
		if(messageInbox != null && messageInbox.getReceiverIdMember().equals(myUserid)){			
			ModelILiketo model = new ModelILiketo(request, response);
			messageInbox.setWasRead("y");						//set msg lida
			messageDAO.update(messageInbox);					//atualiza mensagem lida
			model.addAttribute("messageInbox", messageInbox);	//add objeto recuperar na jsp
			return "/page.jsp?id=837"; 							//page visualizar mensagem			
		}else{
			return "/page.jsp?id=xxx"; 							//page conteudo nao disponivel
		}
		
	}
	
	/**
	 * Redireciona para pagina visualizar mensagem enviada
	 */
	@RequestMapping(value={"/message/outbox/view"})
	public String viewMessageOutbox(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @MessageInboxOutboxController url='/message/outbox/view'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		MessageInboxDAO messageDAO = new MessageInboxDAO(db, request);
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		String id = request.getParameter("id");
		MessageInbox messageInbox = (MessageInbox) messageDAO.readById(id, MessageInbox.class); //ler dados atual da mensagem
		
		//valida mensagem caixa de saida do membro(remetente)
		if(messageInbox != null && messageInbox.getSenderIdMember().equals(myUserid)){			
			ModelILiketo model = new ModelILiketo(request, response);
			model.addAttribute("messageInbox", messageInbox);	//add objeto recuperar na jsp
			return "/page.jsp?id=839"; 							//page visualizar mensagem			
		}else{
			return "/page.jsp?id=xxx"; 							//page conteudo nao disponivel
		}
		
	}
	
	@RequestMapping(value={"/ajax/message/send"})
	public void createAndSendMessage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "request @MessageInboxOutboxController url='/ajax/message/send'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		MessageInboxDAO messageDAO = new MessageInboxDAO(db, request);
		
		MessageInbox message = (MessageInbox) cms.getObjectOfParameter(MessageInbox.class);	//objeto com dados do form
		
		messageDAO.create(message);				//cria e envia mensagem		
		response.getWriter().write("ok");		//enviado com sucesso, retorna 'ok' para view
		
	}
	
	/**
	 * Metodo atualiza mensagem para lida ou nao lida conforme o status atual da mensagem.
	 */
	@RequestMapping(value={"/ajax/message/wasReadAndNotRead"})
	public void read(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "ajax @MessageInboxOutboxController url='/ajax/message/wasReadAndNotRead'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		MessageInboxDAO messageDAO = new MessageInboxDAO(db, request);
		
		String id = request.getParameter("id");
		MessageInbox message = (MessageInbox) messageDAO.readById(id, MessageInbox.class); //ler dados atual da mensagem
		
		boolean isRead;
		if(message.getWasRead().equals("y")){
			//se foi lida, entao altera para "nao lida"
			isRead = false;
			message.setWasRead("n");
		}else{
			//se nao foi lida, entao altera para "lida"
			isRead = true;
			message.setWasRead("y");
		}
		
		messageDAO.update(message);							//atualiza mensagem lida ou nao lida		
		response.getWriter().write(String.valueOf(isRead));	//retorna "true = lida" ou "false = nao lida"
		
	}
	

}
