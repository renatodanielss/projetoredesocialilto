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

import HardCore.Request;
import HardCore.RequireUser;
import HardCore.Response;
import HardCore.Session;
import HardCore.Text;

/**
 * Servlet Filter implementation class FilterPageJSP
 */
@WebFilter(filterName="FilterPageJSP", urlPatterns={ "/page.jsp", "/home.jsp", "/index.jsp", "/" })
public class FilterPageJSP implements Filter {

    /**
     * Default constructor. 
     */
    public FilterPageJSP() {
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
		//Valida usuario na session
		Request myrequest = new Request(req);
		Response myresponse = new Response(res);
		Session mysession = new Session(req.getSession());
		if(req.getSession().getAttribute("member") == null){
			//remove username e id da sessao
			mysession.remove("username");
			mysession.remove("userid");
		}		
		boolean accesspermission = RequireUser.User(new Text(), mysession.get("username"), myrequest, myresponse, mysession, null);
		
		if(accesspermission && !(req.getSession().getAttribute("member") == null)){
			//valida acesso login - permissao ok
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
