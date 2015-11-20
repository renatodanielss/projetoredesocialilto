package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;
import HardCore.UCmaintainContent;

import com.iliketo.dao.IliketoDAO;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.Str;


@Controller
public class MemberTestController {

	
	static final Logger log = Logger.getLogger(MemberTestController.class);
	
	/**
	 * Método intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
	
	@RequestMapping(value={"/registerCollectorExemplo"})
	public String registerCollectorExemplo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "register member collector url='/registerCollectorExemplo'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		
		//memberDAO dao1 = new CollectorDAO(db, request, response);				//dbmembers
		//MemberCollectorDAO dao2 = new CollectorDAO(db, request, response);	//dbmemberscollector
		
		//Member member = (MemberCollector) cms.getObjectOfParameter(Member.class, request); 						//popula objeto com os dados do form
		//MemberCollector collector = (MemberCollector) cms.getObjectOfParameter(MemberCollector.class, request); 	//popula objeto com os dados do form
		
		//validacoes de username e email
		//if() { return "page.jsp?id=xxx"; } //exemplo retornar pagina erro de cadastro

		UCmaintainContent maintainContent = new UCmaintainContent(cms.getMytext());
		maintainContent.doRegister(cms.getServletcontext(), cms.getDOCUMENT_ROOT(), cms.getMysession(), 
				cms.getMyrequest(), cms.getMyresponse(), cms.getMyconfig(), db, cms.getWebsite(), cms.getDatabase());		
		
		//recupera o registro do usuario criado na tabela users
		String idRegisterUser = IliketoDAO.readIdUserCreatedTable(db, "users", "username", request.getParameter("username"));		
		
		//member.setIdMember(idRegisterUser); 		//seta a chave estrangeira
		//dao1.create(member);	//cria membro generico	
		
		//collector.setIdMember(idRegisterUser); 	//seta a chave estrangeira
		//dao2.create(collector);					//cria membro collector
		
		return "page.jsp?id=xxx"; //page success
	}
	
	@RequestMapping(value={"/registerStoreExemplo"})
	public String registerStoreExemplo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("Log - " + "register member store url='/registerStoreExemplo'");
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		
		//memberDAO dao1 = new CollectorDAO(db, request, response);				//dbmembers
		//MemberStoreDAO dao2 = new CollectorDAO(db, request, response);		//dbmembersstore
		
		//Member member = (MemberCollector) cms.getObjectOfParameter(Member.class, request); 			//popula objeto com os dados do form
		//MemberStore store = (MemberCollector) cms.getObjectOfParameter(MemberStore.class, request); 	//popula objeto com os dados do form
		
		//validacoes de username e email
		//if() { return "page.jsp?id=xxx"; } //exemplo retornar pagina erro de cadastro
		
		UCmaintainContent maintainContent = new UCmaintainContent(cms.getMytext());
		maintainContent.doRegister(cms.getServletcontext(), cms.getDOCUMENT_ROOT(), cms.getMysession(), 
				cms.getMyrequest(), cms.getMyresponse(), cms.getMyconfig(), db, cms.getWebsite(), cms.getDatabase());		
		
		//recupera o registro do usuario criado na tabela users
		String idRegisterUser = IliketoDAO.readIdUserCreatedTable(db, "users", "username", request.getParameter("username"));	
		
		//member.setIdMember(idRegisterUser); 		//seta a chave estrangeira
		//dao1.create(member);						//cria membro generico	
		
		//store.setIdMember(idCriado);				//seta a chave estrangeira
		//dao3.create(store);						//cria membro collector
		
		return "page.jsp?id=xxx"; //page success
	}

		
}
