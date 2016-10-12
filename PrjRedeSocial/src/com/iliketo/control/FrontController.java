package com.iliketo.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping(value={"/myInterests"})
	public String paginaMeusInteresses(){		
		return "page.jsp?id=698";
	}
	
	@RequestMapping(value={"/myGroups"})
	public String paginaMeusGrupos(){	
		return "page.jsp?id=792";
	}
	
	/**
	 * Abaixo paginas mapeadas das Configuracoes/Settings dos membros
	 */	
	@RequestMapping(value={"/settings"})
	public String paginaConfiguracoes(){	
		return "page.jsp?id=671";
	}
	
	@RequestMapping(value={"/settings/{subPaginas}"})
	public String paginaConfiguracoesSubPaginas(@PathVariable String subPaginas){		
		Map<String, String> mapPaginas = new HashMap<>();
		mapPaginas.put("local", "672");
		mapPaginas.put("details", "328");
		mapPaginas.put("emails", "673");
		mapPaginas.put("phone", "676");
		mapPaginas.put("storage", "803");
		mapPaginas.put("buyStorage", "1149");
		mapPaginas.put("privacy", "677");
		mapPaginas.put("credentials", "678");
		mapPaginas.put("deactivateAccount", "682");
		
		String paginaId = mapPaginas.get(subPaginas);
		if(paginaId != null){
			return "page.jsp?id=" + paginaId;
		}
		return "page.jsp?id=invalid";
	}
	
	
	/**
	 * Abaixo paginas mapeadas das Configuracoes/Settings dos membros
	 */	
	@RequestMapping(value={"/help"})
	public String paginaAjuda(){	
		return "page.jsp?id=1127";
	}
	
	@RequestMapping(value={"/help/{subPaginas}"})
	public String paginaAjudaSubPaginas(@PathVariable String subPaginas){		
		Map<String, String> mapPaginas = new HashMap<>();
		mapPaginas.put("createCollection", "1128");
		mapPaginas.put("addItems", "1138");
		mapPaginas.put("addVideos", "1140");
		mapPaginas.put("joinGroup", "1143");
		mapPaginas.put("joinHobby", "1146");		
		//mapPaginas.put("addPhotoHobby", "");
		//mapPaginas.put("addVideoHobby", "");
		//mapPaginas.put("createAds", "");
		//mapPaginas.put("createEvents", "");
		
		String paginaId = mapPaginas.get(subPaginas);
		if(paginaId != null){
			return "page.jsp?id=" + paginaId;
		}
		return "page.jsp?id=invalid";
	}
	
}
