package HardCore;

import java.io.*;
import java.io.File;
import java.net.*;
import java.net.HttpURLConnection;
import java.lang.reflect.Method;
import java.text.*;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;

public class Response {
	private HttpServletResponse response;



	public Response() {
		response = null;
	}



	public Response(HttpServletResponse httpresponse) {
		response = httpresponse;
	}



	public HttpServletResponse getResponse() {
		return response;
	}



	public void sendRedirect(String url) {
		try {
			if ((response != null) && (url != null)) response.sendRedirect(Common.crlf_clean(url));
		} catch (Exception e) {
		}
	}



	public String encodeRedirectURL(String value) {
		if ((response != null) && (value != null)) return response.encodeRedirectURL(value);
		return null;
	}



	public void setContentType(String value) {
		if ((response != null) && (value != null)) response.setContentType(value);
	}



	public void setHeader(String name, String value) {
		if ((response != null) && (name != null) && (value != null)) response.setHeader(name, value);
	}



	public void setDateHeader(String name, long value) {
		if ((response != null) && (name != null)) response.setDateHeader(name, value);
	}



	public void flushBuffer() {
		try {
			if (response != null) response.flushBuffer();
		} catch (Exception e) {
		}
	}



	public void returnFile(String filename, ServletContext servletcontext) {
		returnFile(filename, servletcontext, "", filename, "GET");
	}
	public void returnFile(String filename, ServletContext servletcontext, String contenttype) {
		returnFile(filename, servletcontext, contenttype, filename, "GET");
	}
	public void returnFile(String filename, ServletContext servletcontext, String contenttype, String title) {
		returnFile(filename, servletcontext, contenttype, filename, "GET");
	}
	public void returnFile(String filename, ServletContext servletcontext, String contenttype, String title, String method) {
		if (response == null) return;
		if (contenttype.equals("")) {
			String mybasefilename = filename.replaceAll("\\.[0-9]*$", "");
			String filenameextension = filename.replaceAll("^.*\\.(.*?)$", "$1");
			if (filenameextension.equals("txt")) {
				contenttype = "text/plain";
			} else if (filenameextension.equals("htm")) {
				contenttype = "text/html";
			} else if (filenameextension.equals("html")) {
				contenttype = "text/html";
			} else if (filenameextension.equals("css")) {
				contenttype = "text/css";
			} else if (filenameextension.equals("js")) {
				contenttype = "text/javascript";
			} else if (filenameextension.equals("pdf")) {
				contenttype = "application/pdf";
			} else if (filenameextension.equals("gif")) {
				contenttype = "image/gif";
			} else if (filenameextension.equals("jpeg")) {
				contenttype = "image/jpeg";
			} else if (filenameextension.equals("jpg")) {
				contenttype = "image/jpeg";
			} else if (filenameextension.equals("png")) {
				contenttype = "image/x-png";
			} else if (filenameextension.equals("swf")) {
				contenttype = "application/x-shockwave-flash";
			} else if (filenameextension.equals("class")) {
				contenttype = "application/octet-stream";

			} else if (filenameextension.equals("mp3")) {
				contenttype = "audio/mpeg";
			} else if (filenameextension.equals("ogg")) {
				contenttype = "audio/ogg";
			} else if (filenameextension.equals("wav")) {
				contenttype = "audio/wav";

			} else if (filenameextension.equals("mp4")) {
				contenttype = "video/mp4";
			} else if (filenameextension.equals("m4v")) {
				contenttype = "video/mp4";
			} else if (filenameextension.equals("ogv")) {
				contenttype = "video/ogg";
			} else if (filenameextension.equals("ogx")) {
				contenttype = "video/ogg";
			} else if (filenameextension.equals("webm")) {
				contenttype = "video/webm";

			} else {
				contenttype = "application/octet-stream";
			}
			if (contenttype.equals("")) {
				try {
//					contenttype = servletcontext.getMimeType(fh.getName());
					contenttype = servletcontext.getMimeType(title);
				} catch (Exception e) {
				}
			}
		}
		title = title.replaceAll("\\.[0-9]*$", "");

		if ((! filename.startsWith("http:")) && (! filename.startsWith("https:"))) {
			File fh = new File(filename);
			if (fh.exists()) {
				response.setContentType(contenttype);
				response.setHeader("Content-disposition", "filename=\"" + title.substring(title.lastIndexOf("/")+1) + "\"" );
				response.setContentLength((int) fh.length());
				if (! method.equals("HEAD")) {
					FileInputStream inputstream = null;
					try {
						ServletOutputStream servletoutputstream = response.getOutputStream();
						inputstream = new FileInputStream(fh);
						byte[] bytes  = new byte[1024*64];
						int bytesread = 0;
						while ((bytesread = inputstream.read(bytes)) > 0) {
							servletoutputstream.write(bytes, 0, bytesread);
							servletoutputstream.flush();
						}
						inputstream.close();
					} catch (IOException e) {
						try { if (inputstream != null) inputstream.close(); } catch (IOException ee) { }
					}
				}
			}

		} else {
			URLConnection cn = null;
			InputStream inputstream = null;
			try {
				response.setContentType(contenttype);
				response.setHeader("Content-disposition", "filename=\"" + title.substring(title.lastIndexOf("/")+1) + "\"" );

				String myurl = filename;
				myurl = myurl.replaceAll(" ", "+");
				URL url = new URL(myurl);
				cn = url.openConnection();
				cn.setUseCaches(false);
				cn.setDoInput(true);
				try {
					Method connectTimeoutMethod = cn.getClass().getMethod("setConnectTimeout", new Class[]{ Integer.class });
					if (connectTimeoutMethod != null) connectTimeoutMethod.invoke(cn, new Object[]{ new Integer(10000) });
					Method readTimeoutMethod = cn.getClass().getMethod("setReadTimeout", new Class[]{ Integer.class });
					if (readTimeoutMethod != null) readTimeoutMethod.invoke(cn, new Object[]{ new Integer(10000) });
				} catch (Exception e) {
					System.out.println("WARNING:HardCore/Response.returnFile:"+myurl+":"+e);
				}
				ServletOutputStream servletoutputstream = response.getOutputStream();
				inputstream = cn.getInputStream();
				byte[] bytes = new byte[1024];
				int bytesread;
				while ((bytesread = inputstream.read(bytes)) > 0) {
					servletoutputstream.write(bytes, 0, bytesread);
				}
				inputstream.close();
				inputstream = null;
				servletoutputstream = null;
				if ((cn != null) && (cn.getInputStream() != null)) cn.getInputStream().close();
				if ((cn != null) && (cn.getOutputStream() != null)) cn.getOutputStream().close();
				cn = null;
				url = null;
			} catch (Exception e) {
				try { if (inputstream != null) inputstream.close(); } catch (IOException ee) { }
			} finally {
				try {
					if (inputstream != null) inputstream.close();
				} catch (Exception e) {
				}
				inputstream = null;
				try {
					if ((cn != null) && (cn.getInputStream() != null)) cn.getInputStream().close();
				} catch (Exception e) {
				}
				try {
					if ((cn != null) && (cn.getOutputStream() != null)) cn.getOutputStream().close();
				} catch (Exception e) {
				}
				cn = null;
			}
		}
	}



	public void setCookie(String name, String value) {
		if (response != null) {
			try {
				Cookie cookie = new Cookie(name, java.net.URLEncoder.encode(value, "UTF-8"));
    				response.addCookie(cookie);
    			} catch (Exception e) {
    			}
		}
	}
	public void setCookie(String name, String value, int age) {
		if (response != null) {
			try {
				Cookie cookie = new Cookie(name, java.net.URLEncoder.encode(value, "UTF-8"));
				cookie.setMaxAge(age);
				cookie.setPath("/");
    				response.addCookie(cookie);
    			} catch (Exception e) {
    			}
		}
	}



}
