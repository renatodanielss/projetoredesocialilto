package HardCore;

import java.io.*;
import java.io.File;
import java.net.*;
import java.net.HttpURLConnection;
import java.lang.reflect.Method;
import javax.servlet.http.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;

public class Server {
	private ServletContext servletcontext;



	public Server(ServletContext myservletcontext) {
		servletcontext = servletcontext;
	}



	public ServletContext getServletContext() {
		return servletcontext;
	}



//	public void setCookie(String name, String value) {
//		if (response != null) {
//			try {
//				Cookie cookie = new Cookie(name, value);
//    				response.addCookie(cookie);
//    			} catch (Exception e) {
//    			}
//		}
//	}



}
