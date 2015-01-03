package HardCore;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.*;
import javax.servlet.http.*;

public class Request {
	private HttpServletRequest request;
	private HashMap<String,Object> parameters = new HashMap<String,Object>();
	private String charset = "";

	public static String parametercharset = "iso-8859-1";



	public Request() {
		request = null;
	}



	public Request(HttpServletRequest httprequest) {
		request = httprequest;
	}



	public HttpServletRequest getRequest() {
		return request;
	}



	public String getCharset() {
		return "" + charset;
	}



	public void setCharset(String mycharset) {
		charset = mycharset;
	}



	public boolean parameterExists(String name) {
		if (parameters.get(name) != null) {
			return true;
		} else if ((request != null) && (request.getParameter(name) != null)) {
			return true;
		} else {
			return false;
		}
	}



@SuppressWarnings("unchecked")
	public Enumeration getParameterNames() {
		if (request != null) {
			ArrayList<String> names = new ArrayList<String>();
			names.addAll(Collections.list(request.getParameterNames()));
			names.addAll(parameters.keySet());
			return Collections.enumeration(names);
		} else {
			return null;
		}
	}



	public String getParameter(String name) {
		if (name == null) {
			return "";
		} else if (parameters.get(name) != null) {
			return ((String[])parameters.get(name))[0];
		} else if ((request != null) && (request.getParameter(name) != null)) {
			String value = "" + request.getParameter(name);
			if ((! charset.equals("")) && (! parametercharset.equals("")) && (! charset.toLowerCase().equals(parametercharset))) {
				try {
					value = new String(value.getBytes(parametercharset), charset);
				} catch (UnsupportedEncodingException e) {
				}
			}
			return value;
		} else {
			return "";
		}
	}



	public String[] getParameters(String name) {
		if (name == null) {
			return new String[0];
		} else if (parameters.get(name) != null) {
			return (String[])parameters.get(name);
		} else if ((request != null) && (request.getParameterValues(name) != null)) {
			String[] parametervalues = request.getParameterValues(name);
			String[] values = new String[parametervalues.length];
			for (int i=0; i<parametervalues.length; i++) {
				values[i] = "" + parametervalues[i];
			}
			if ((! charset.equals("")) && (! parametercharset.equals("")) && (! charset.toLowerCase().equals(parametercharset))) {
				try {
					for (int i=0; i<values.length; i++) {
						values[i] = new String(values[i].getBytes(parametercharset), charset);
					}
				} catch (UnsupportedEncodingException e) {
				}
			}
			return values;
		} else {
			return new String[0];
		}
	}



	public void addParameter(String name, String value) {
		if (parameters.get(name) == null) {
			String[] values = new String[1];
			values[0] = value;
			parameters.put(name, values);
		} else {
			String[] values = ((String[])parameters.get(name));
			values = Common.array_redim(values, values.length+1);
			values[values.length-1] = value;
			parameters.put(name, values);
		}
	}



	public void setParameter(String name, String value) {
		String[] values = new String[1];
		values[0] = value;
		parameters.put(name, values);
	}



	public void setParameters(String name, String[] values) {
		parameters.put(name, values);
	}



	public String getMethod() {
		if ((request != null) && (request.getMethod() != null)) {
			return request.getMethod();
		} else {
			return "";
		}
	}



	public String getURL() {
		String myurl = "";
		if ((request != null) && (request.getRequestURL() != null)) {
			myurl = "" + request.getRequestURL();
			String myquery = request.getQueryString();
			if (myquery != null) {
				myurl += "?" + myquery;
			}
		}
		return myurl;
	}



	public String getRequestURL() {
		if ((request != null) && (request.getRequestURL() != null)) {
			return "" + request.getRequestURL();
		} else {
			return "";
		}
	}



	public String getRequestURI() {
		if ((request != null) && (request.getRequestURI() != null)) {
			return "" + request.getRequestURI();
		} else {
			return "";
		}
	}



	public String getPathInfo() {
		if ((request != null) && (request.getPathInfo() != null)) {
			return "" + request.getPathInfo();
		} else {
			return "";
		}
	}



	public String getQueryString() {
		if ((request != null) && (request.getQueryString() != null)) {
			return "" + request.getQueryString();
		} else {
			return "";
		}
	}



	public String getProtocol() {
		if ((request != null) && (request.getScheme() != null)) {
			if (! request.getScheme().equalsIgnoreCase("http")) {
				return "https://";
			} else {
				return "http://";
			}
		} else {
			return "";
		}
	}



	public String getServerName() {
		if ((request != null) && (request.getServerName() != null)) {
			return "" + request.getServerName();
		} else {
			return "";
		}
	}



	public String getServerPort() {
		if (request != null) {
			if ((! getProtocol().equals("http://")) && (request.getServerPort() != 0) && (request.getServerPort() != 1) && (request.getServerPort() != 443)) {
				return ":" + String.valueOf(request.getServerPort());
			} else if ((getProtocol().equals("http://")) && (request.getServerPort() != 0) && (request.getServerPort() != 1) && (request.getServerPort() != 80)) {
				return ":" + String.valueOf(request.getServerPort());
			} else {
				return "";
			}
		} else {
			return "";
		}
	}



	public String getServletPath() {
		if ((request != null) && (request.getServletPath() != null)) {
			return "" + request.getServletPath();
		} else {
			return "";
		}
	}



	public String getRemoteUser() {
		if ((request != null) && (request.getRemoteUser() != null)) {
			return "" + request.getRemoteUser();
		} else {
			return "";
		}
	}



	public String getRemoteHost() {
		if ((request != null) && (request.getRemoteHost() != null)) {
			return "" + request.getRemoteHost();
		} else {
			return "";
		}
	}



	public String getRemoteAddr() {
		if ((request != null) && (request.getRemoteAddr() != null)) {
			return "" + request.getRemoteAddr();
		} else {
			return "";
		}
	}



	public String getUserAgent() {
		return "" + getHeader("User-Agent");
	}



	public ServletInputStream getInputStream() {
		try {
			return request.getInputStream();
		} catch (IOException e) {
		}
		return null;
	}



	public int getContentLength() {
		return request.getContentLength();
	}



	public Locale getLocale() {
		return request.getLocale();
	}



	public String getPreferredLanguage() {
//		return request.getLocale().toString();
		String mylanguage = "";
		String[] mylanguages = ("" + getRequest().getHeader("Accept-Language")).split(",");
		if (mylanguages.length > 0) {
			mylanguage = "" + mylanguages[0];
		}
		return mylanguage;
	}



	public String getAcceptedLanguages() {
//		String mylanguages = "";
//		Enumeration mylocales = request.getLocales();
//		while (mylocales.hasMoreElements()) {
//			if (! mylanguages.equals("")) mylanguages += ",";
//			mylanguages += mylocales.nextElement().toString();
//		}
		String mylanguages = "" + getRequest().getHeader("Accept-Language");
		return mylanguages;
	}



	public String getHeader(String name) {
		if ((request != null) && (request.getHeader(name) != null)) {
			return request.getHeader(name);
		} else {
			return "";
		}
	}



	public Object getAttribute(String name) {
		if ((request != null) && (request.getAttribute(name) != null)) {
    			return request.getAttribute(name);
		} else {
			return null;
		}
	}



	public void setAttribute(String name, Object value) {
		if (request != null) {
    			request.setAttribute(name, value);
		}
	}



	public String getCookie(String name) {
		if (request != null) {
			try {
				Cookie[] cookies = request.getCookies();
				if (cookies != null) {
					for (int i=0; i<cookies.length; i++) {
						if (cookies[i].getName().equals(name)) {
							return "" + java.net.URLDecoder.decode(cookies[i].getValue(), "UTF-8");
						}
					}
				}
			} catch (Exception e) {
			}
    			return "";
		} else {
			return "";
		}
	}

}
