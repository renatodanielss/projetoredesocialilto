package com.iliketo.util;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe auxiliar utilizada para armazenar objetos no escopo de requisicao para fazer o parse do bean na view
 * 
 * @author osvaldimar.costa
 *
 */
public class ModelILiketo{
	
	HttpServletRequest request;
	HashMap map = new HashMap();

	public ModelILiketo(HttpServletRequest request){
		this.request = request;
		if(request.getAttribute("modelILiketo") != null){
			map = (HashMap) request.getAttribute("modelILiketo");
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
