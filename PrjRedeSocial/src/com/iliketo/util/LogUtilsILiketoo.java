package com.iliketo.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class LogUtilsILiketoo {
	
	
	public LogUtilsILiketoo(){		
	}
	
	public static void printURLSolicitada(HttpServletRequest req, Logger log){
		log.info("URLSolicitada: "+req.getRequestURL()+"?"+req.getQueryString());
	}
	
	public static String getUsername(HttpServletRequest req){
		String username = (String) req.getSession().getAttribute("username");
		return " [user: " + username + "]";
	}
	
	public static void mostrarLogStackException(Exception ex, Logger log, 
			HttpServletRequest req, HttpServletResponse res, String pageError){
		log.error("CAUSA ERRO: " + ex);
		log.error("LINHA ERRO: " + stackTrace(ex.getStackTrace()));
		ex.printStackTrace();
		try {
			//pagina de erro ou servidor indiponivel
			res.sendRedirect(pageError);
		} catch (IOException e) {
			log.error(e); e.printStackTrace();
		}
	}
	
	
	private static String stackTrace(StackTraceElement[] stackTraceElements) {
	    if (stackTraceElements == null){
	        return "";
	    }	    
	    StringBuilder stringBuilder = new StringBuilder();
	    for (StackTraceElement element : stackTraceElements){
	        stringBuilder.append(element.toString()).append("\n");
	    }
	    return stringBuilder.toString();
	}
	
}
