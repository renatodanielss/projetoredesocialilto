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

import HardCore.DB;

import com.iliketo.util.Str;

/**
 * Servlet Filter implementation class FilterPageILiketo
 */
@WebFilter(filterName="FilterPageILiketo", urlPatterns={ "/ilt/*" })
public class FilterPageILiketo implements Filter {

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
		
		//include da jsp 'config.jsp' para inicializar configuracoes do Asbru
		//include da jsp 'config.jsp' abre uma conexao com banco de dados e pendura no request		
		if(db == null || db.isClosed()){
			req.getRequestDispatcher("/config.jsp").include(req, res);	//Abre conexao
		}
		
		
		chain.doFilter(request, response);
		
		
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
