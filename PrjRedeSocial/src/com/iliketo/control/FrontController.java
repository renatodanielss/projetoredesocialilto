package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iliketo.util.LogUtilsILiketoo;


@Controller
public class FrontController {
	
	
	static final Logger log = Logger.getLogger(FrontController.class);
	@Autowired HttpServletRequest request;
	
	/**
	 * Método intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
	
	@RequestMapping(value={"/myEvents"})
	public String paginaMinhaListaEventos(){		
		return "page.jsp?id=739";
	}
	
	@RequestMapping(value={"/myAds/sale"})
	public String paginaMeusAnunciosVenda(){	
		return "page.jsp?id=751&id_member=" + request.getSession().getAttribute("userid").toString();
	}
	
	@RequestMapping(value={"/myAds/exchange"})
	public String paginaMeusAnunciosTroca(){		
		return "page.jsp?id=751&id_member=" + request.getSession().getAttribute("userid").toString();
	}
	
	@RequestMapping(value={"/myAds/purchase"})
	public String paginaMeusAnunciosCompra(){		
		return "page.jsp?id=751&id_member=" + request.getSession().getAttribute("userid").toString();
	}
	
	@RequestMapping(value={"/settings"})
	public String paginaConfiguracoes(){	
		return "page.jsp?id=671";
	}
	
	
}
