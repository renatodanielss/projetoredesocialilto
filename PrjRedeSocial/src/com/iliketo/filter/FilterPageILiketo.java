package com.iliketo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import HardCore.DB;
import HardCore.Request;
import HardCore.RequireUser;
import HardCore.Response;
import HardCore.Session;
import HardCore.Text;

import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.Str;

/**
 * Servlet Filter implementation class FilterPageILiketo
 */
@WebFilter(filterName="FilterPageILiketo", urlPatterns={ "/ilt/*" })
public class FilterPageILiketo implements Filter {

	static final Logger log = Logger.getLogger(FilterPageILiketo.class);
	
    /**
     * Default constructor. 
     */
    public FilterPageILiketo() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;	
		DB db = (DB) req.getAttribute(Str.CONNECTION_DB);
		
		//armazena no scope request o atributo para indicar paginacao em pages usadas pelo Spring no diretorio WEB-INF/views
		String uri = req.getRequestURI();
		String query = req.getQueryString();
		String pagination = uri + (query == null ? "" : "?" +query);
		req.setAttribute("PAGINATION_ILT", pagination);
		
		
		//include da jsp 'config.jsp' para inicializar configuracoes do Asbru
		//include da jsp 'config.jsp' abre uma conexao com banco de dados e pendura no request		
		if(db == null || db.isClosed()){
			req.getRequestDispatcher("/config.jsp").include(req, res);	//Abre conexao
		}
		
		//Valida usuario na session
		Request myrequest = new Request(req);
		Response myresponse = new Response(res);
		Session mysession = new Session(req.getSession());
		//valida member
		if(req.getSession().getAttribute("member") == null){
			if((myrequest.getParameter("timeline") != null && !myrequest.getParameter("timeline").equals("")) || myrequest.getRequestURI().contains("ajax")){
				if(myrequest.getRequestURI().contains("ajaxTotalNotifications")){
					res.setStatus(401);
					res.setContentType("text/html");
					res.getWriter().write("Session expired");	//member null, session expired
				}
			}else{
				//remove username e id da sessao
				mysession.remove("username");
				mysession.remove("userid");
			}
		}
		
		boolean accesspermission = RequireUser.User(new Text(), mysession.get("username"), myrequest, myresponse, mysession, db);
		
		if(accesspermission && !(req.getSession().getAttribute("member") == null)){
			//valida acesso login - permissao ok
			try{
				chain.doFilter(request, response);
			}catch(Exception e){
				log.error("CAUSA ERRO: " + e.getCause());
				log.error("MENSAGEM ERRO: " + e.getMessage());
				log.error("LINHA ERRO: " + LogUtilsILiketoo.stackTrace(e.getStackTrace()));
			}
		}
		
		if(req.getRequestURL().toString().contains("/ilt/pagamentos/notificacaoIPN")){
			//valida acesso notificacao NIP paypal - permissao ok
			chain.doFilter(request, response);
		}
		
		
		//fecha conexao
		db = (DB) req.getAttribute(Str.CONNECTION_DB);
		if(db != null){
			db.close();
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
