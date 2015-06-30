package com.iliketo.util;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe auxiliar utilizada para armazenar objetos no escopo de requisicao para fazer o parse do bean na view
 * 
 * @author osvaldimar.costa
 *
 */
public class ModelILiketo{
	
	HttpServletRequest request;
	HttpServletResponse response;
	HashMap map = new HashMap();
	HashMap mapError = new HashMap();

	public ModelILiketo(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
		if(request.getAttribute("modelILiketo") != null){
			map = (HashMap) request.getAttribute("modelILiketo");
		}
		if(request.getAttribute("modelILiketoError") != null){
			mapError = (HashMap) request.getAttribute("modelILiketoError");
		}
	}
	
	/**
	 * Metodo redireciona para pagina correspondente ao erro, seta erro na session para recuperar em outra pagina e removo o erro.
	 * @param url
	 * @return
	 */
	public String redirectError(String url){
		try {
			request.getSession().setAttribute("modelILiketoError", mapError);
			request.getSession().setAttribute("modelILiketo", map);
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Metodo verifica se existe erro na session, se sim remove e atribui o erro ao request para recuperar na jsp.
	 * @param url
	 * @return
	 */
	public static boolean validateAndProcessError(HttpServletRequest request){
		HashMap mapError = (HashMap) request.getSession().getAttribute("modelILiketoError");	
		HashMap map = (HashMap) request.getSession().getAttribute("modelILiketo");
		//valida se existe error na pagina anterior
		if(mapError != null){
			request.setAttribute("modelILiketoError", mapError);		//atribui o erro ao request, recuperar na jsp ${error:nomeDoErro}
			request.setAttribute("modelILiketo", map);
			request.getSession().removeAttribute("modelILiketoError");	//remove erro da session
			request.getSession().removeAttribute("modelILiketo");
			return true;
		}else{
			return false;
		}			
	}

	/**
	 * Método adiciona um objeto que será recuperado na pagina jsp utilizando expressao: ${objeto.atributo}
	 * @param name
	 * @param object
	 */
	public void addAttribute(String name, Object object){
		map.put(name, object);
		request.setAttribute("modelILiketo", map);
	}
	
	/**
	 * Método adiciona uma String de mensagem de erro que será recuperado na pagina jsp utilizando expressao: ${error:nomeDoErro}
	 * @param nameError - nome do erro
	 * @param msgError - mensagem de erro
	 */
	public void addMessageError(String nameError, String msgError){
		mapError.put(nameError, msgError);
		request.setAttribute("modelILiketoError", mapError);
	}
	
	public Object get(String name){
		return map.get(name);
	}
	
	public void removeAttibute(String name){
		map.remove(name);
		request.setAttribute("modelILiketo", map);
	}
	
	public boolean containsAttribute(String name){
		return map.containsKey(name);
	}

}
