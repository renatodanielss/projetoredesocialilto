package com.iliketo.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.iliketo.control.VideoController;
import com.iliketo.util.LogUtilsILiketoo;

/**
 * Servlet Filter implementation class FilterWebadmin
 */
@WebFilter(filterName="FilterWebadmin", urlPatterns="/webadmin/login.jsp")
public class FilterWebadmin implements Filter {

	static final Logger log = Logger.getLogger(FilterWebadmin.class);
	private String acessoWebadmin;
	
    /**
     * Default constructor. 
     */
    public FilterWebadmin() {
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
		if(this.acessoWebadmin != null && acessoWebadmin.equals("nao")){
			log.warn("Acesso ao recurso '/webadmin/login.jsp' nao autorizado!");
			LogUtilsILiketoo.printURLSolicitada((HttpServletRequest)request, log);
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendRedirect("/");
		}else{
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		try {
			Properties prop = new Properties();
			String filename = "config/config_iliketoo.properties";
			InputStream input = this.getClass().getClassLoader().getResourceAsStream(filename);  
			if(input==null){
		        log.error("Arquivo properties de configuracoes nao encontrado: " + filename);
			    return;
			}
			prop.load(input);
			this.acessoWebadmin = prop.getProperty("ACESSO_WEBADMIN");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
