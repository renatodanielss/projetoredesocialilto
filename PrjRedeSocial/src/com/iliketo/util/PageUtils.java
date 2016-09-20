package com.iliketo.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iliketo.control.HobbyController;

@Controller
public class PageUtils {

	private @Autowired HttpServletRequest request;
	static final Logger log = Logger.getLogger(HobbyController.class);
	
	/**
	 * Método intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
	
	@RequestMapping(value={"/ajaxListaPaginacao/mais/{link}"}, method=RequestMethod.GET)
	public void ajaxListaPaginacao(HttpServletResponse response,
			@PathVariable String link) throws IOException{
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("link", link);
		String conteudoHtml = "";
		ResponseILiketoo.respostaHtml(response, conteudoHtml);
	}

}
