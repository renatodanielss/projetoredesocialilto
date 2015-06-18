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
	 * Metodo faz o reload da pagina atual com a mensagem de erro ou redireciona para uma outra pagina de erro.
	 * @param url
	 * @return
	 */
	public String redirectError(String url){
		try {
			RequestDispatcher rq = request.getRequestDispatcher(url);
			rq.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
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
