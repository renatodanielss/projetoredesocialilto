package HardCore;

import java.io.*;
import java.net.*;
import java.net.HttpURLConnection;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;

public class http {
	public static boolean DEBUG = false;
	public static boolean GC = false;



	public static String check(String host) {
		return check(host, "", "", null);
	}
	public static String check(String host, Request request) {
		return check(host, "", "", request);
	}
	public static String check(String host, String path) {
		return check(host, path, "", null);
	}
	public static String check(String host, String path, Request request) {
		return check(host, path, "", request);
	}
	public static String check(String host, String path, String data) {
		return check(host, path, data, null);
	}
	public static String check(String host, String path, String data, Request request) {
		if (DEBUG) { System.gc(); System.out.println("DEBUG:HardCore/http.check:pre:"+host+":::"+path+":::"+data+":::"+Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory()); }
		String error = "";	       
		if ((host == null) || (host.equals(""))) return null;
		HttpURLConnection cn = null;
		InputStream in = null;
		try {
			if ((! host.startsWith("http://")) && (! host.startsWith("https://"))) {
				host = "http://" + host;
			}
			String myurl = host + path;
			if (! data.equals("")) myurl += "?" + data;
			myurl = myurl.replaceAll(" ", "+");
			URL url = new URL(myurl);
			cn = (HttpURLConnection)url.openConnection();
			if (request != null) {
				String auth = request.getHeader("Authorization");
				if ((auth != null) && (! auth.equals(""))) {
					cn.setRequestProperty("Authorization", auth);
				}
			}
			cn.setUseCaches(false);
			cn.setRequestMethod("HEAD");
			cn.connect();
			error += cn.getResponseCode() + " " + cn.getResponseMessage();
			cn.disconnect();
			cn = null;
			url = null;
		} catch (Exception e) {
		} finally {
			try {
				if (in != null) in.close();
			} catch (Exception e) {
			}
			in = null;
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
		if (GC) System.gc();
		if (DEBUG) { System.gc(); System.out.println("DEBUG:HardCore/http.check:post:"+host+":::"+path+":::"+data+":::"+Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory()); }
		return error;
	}



	public static String get(String host) {
		return get(host, "", "", null);
	}
	public static String get(String host, Request request) {
		return get(host, "", "", request);
	}
	public static String get(String host, String path) {
		return get(host, path, "", null);
	}
	public static String get(String host, String path, Request request) {
		return get(host, path, "", request);
	}
	public static String get(String host, String path, String data) {
		return get(host, path, data, null);
	}
	public static String get(String host, String path, String data, Request request) {
		if (DEBUG) { System.gc(); System.out.println("DEBUG:HardCore/http.get:pre:"+host+":::"+path+":::"+data+":::"+Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory()); }
		String output = null;	       
		if ((host == null) || (host.equals(""))) return null;
		HttpURLConnection cn = null;
		InputStream in = null;
		try {
			if ((! host.startsWith("http://")) && (! host.startsWith("https://"))) {
				host = "http://" + host;
			}
			String myurl = host + path;
			if (! data.equals("")) myurl += "?" + data;
			myurl = myurl.replaceAll(" ", "+");
			URL url = new URL(myurl);
			cn = (HttpURLConnection)url.openConnection();
			if (request != null) {
				String auth = request.getHeader("Authorization");
				if ((auth != null) && (! auth.equals(""))) {
					cn.setRequestProperty("Authorization", auth);
				}
			}
			cn.setUseCaches(false);
			cn.setDoInput(true);
			try {
				Method connectTimeoutMethod = cn.getClass().getMethod("setConnectTimeout", new Class[]{ Integer.class });
				if (connectTimeoutMethod != null) connectTimeoutMethod.invoke(cn, new Object[]{ new Integer(10000) });
				Method readTimeoutMethod = cn.getClass().getMethod("setReadTimeout", new Class[]{ Integer.class });
				if (readTimeoutMethod != null) readTimeoutMethod.invoke(cn, new Object[]{ new Integer(10000) });
			} catch (Exception e) {
				if (DEBUG) System.out.println("WARNING:HardCore/http.get:"+myurl+":"+e);
			}
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			in = cn.getInputStream();
			byte[] buf = new byte[1024];
			int rc;
			while ((rc = in.read(buf)) > 0) {
				bout.write(buf, 0, rc);
			}
			output = bout.toString();
			bout = null;
			in.close();
			in = null;
			if ((cn != null) && (cn.getInputStream() != null)) cn.getInputStream().close();
			if ((cn != null) && (cn.getOutputStream() != null)) cn.getOutputStream().close();
			cn = null;
			url = null;
		} catch (Exception e) {
		} finally {
			try {
				if (in != null) in.close();
			} catch (Exception e) {
			}
			in = null;
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
		if (GC) System.gc();
		if (DEBUG) { System.gc(); System.out.println("DEBUG:HardCore/http.get:post:"+host+":::"+path+":::"+data+":::"+Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory()); }
		return output;
	}



	public static String put_file(String filename, String host) {
		return put_file(filename, host, "", "", null);
	}
	public static String put_file(String filename, String host, Request request) {
		return put_file(filename, host, "", "", request);
	}
	public static String put_file(String filename, String host, String path) {
		return put_file(filename, host, path, "", null);
	}
	public static String put_file(String filename, String host, String path, Request request) {
		return put_file(filename, host, path, "", request);
	}
	public static String put_file(String filename, String host, String path, String data) {
		return put_file(filename, host, path, data, null);
	}
	public static String put_file(String filename, String host, String path, String data, Request request) {
		if (DEBUG) { System.gc(); System.out.println("DEBUG:HardCore/http.put_file:pre:"+host+":::"+path+":::"+data+":::"+Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory()); }
		String output = null;	       
		if ((host == null) || (host.equals(""))) return null;
//QQQQ if not file exists return
		HttpURLConnection cn = null;
		InputStream in = null;
		try {
			if ((! host.startsWith("http://")) && (! host.startsWith("https://"))) {
				host = "http://" + host;
			}
			String myurl = host + path;
			if (! data.equals("")) myurl += "?" + data;
			myurl = myurl.replaceAll(" ", "+");
			URL url = new URL(myurl);
			cn = (HttpURLConnection)url.openConnection();
			if (request != null) {
				String auth = request.getHeader("Authorization");
				if ((auth != null) && (! auth.equals(""))) {
					cn.setRequestProperty("Authorization", auth);
				}
			}
			cn.setUseCaches(false);
			cn.setDoInput(true);
			try {
				Method connectTimeoutMethod = cn.getClass().getMethod("setConnectTimeout", new Class[]{ Integer.class });
				if (connectTimeoutMethod != null) connectTimeoutMethod.invoke(cn, new Object[]{ new Integer(10000) });
//QQQQQ writeTimeoutMethod ???
				Method readTimeoutMethod = cn.getClass().getMethod("setReadTimeout", new Class[]{ Integer.class });
				if (readTimeoutMethod != null) readTimeoutMethod.invoke(cn, new Object[]{ new Integer(10000) });
			} catch (Exception e) {
				if (DEBUG) System.out.println("WARNING:HardCore/http.put_file:"+myurl+":"+e);
			}
//QQQQQ reverse the following to upload file
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			in = cn.getInputStream();
			byte[] buf = new byte[1024];
			int rc;
			while ((rc = in.read(buf)) > 0) {
				bout.write(buf, 0, rc);
			}
			output = bout.toString();
			bout = null;
			in.close();
			in = null;
//QQQQ
			if ((cn != null) && (cn.getInputStream() != null)) cn.getInputStream().close();
			if ((cn != null) && (cn.getOutputStream() != null)) cn.getOutputStream().close();
			cn = null;
			url = null;
		} catch (Exception e) {
		} finally {
			try {
				if (in != null) in.close();
			} catch (Exception e) {
			}
			in = null;
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
		if (GC) System.gc();
		if (DEBUG) { System.gc(); System.out.println("DEBUG:HardCore/http.put_file:post:"+host+":::"+path+":::"+data+":::"+Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory()); }
		return output;
	}



	public static String post(String host, String path, String data) {
		return post(host, path, data, "");
	}



	public static String post(String host, String path, HashMap data) {
		String formdata = "";
		Random random = new Random();
		String boundary = "---------------------------" + Long.toString(random.nextLong(),36) + Long.toString(random.nextLong(),36) + Long.toString(random.nextLong(),36);
		Iterator i = data.keySet().iterator();
		while (i.hasNext()) {
			String name = "" + i.next();
			String value = "" + data.get(name);
			formdata += "--" + boundary;
			formdata += "\r\n" + "Content-Disposition: form-data; name=\"" + name + "\"" + "\r\n" + "\r\n";
			formdata += value + "\r\n";
		}
		formdata += "--" + boundary + "--";
		return post(host, path, formdata, boundary);
	}



	public static String post(String host, String path, HttpServletRequest data) {
		String formdata = "";
		Random random = new Random();
		String boundary = "---------------------------" + Long.toString(random.nextLong(),36) + Long.toString(random.nextLong(),36) + Long.toString(random.nextLong(),36);
		Enumeration i = data.getParameterNames();
		while (i.hasMoreElements()) {
			String name = "" + i.nextElement();
			String[] values = data.getParameterValues(name);
			for (int j=0; j<values.length; j++) {
				formdata += "--" + boundary;
				formdata += "\r\n" + "Content-Disposition: form-data; name=\"" + name + "\"" + "\r\n" + "\r\n";
				formdata += values[j] + "\r\n";
			}
		}
		formdata += "--" + boundary + "--";
		return post(host, path, formdata, boundary);
	}



	public static String post(String host, String path, String data, String boundary) {
		return post(host, path, data, boundary, null);
	}
	public static String post(String host, String path, String data, String boundary, Request request) {
		if (DEBUG) { System.gc(); System.out.println("DEBUG:HardCore/http.post:pre:"+host+":::"+path+":::"+data.length()+":::"+Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory()); }
		String output = null;	       
		if ((host == null) || (host.equals(""))) return null;
		HttpURLConnection cn = null;
//		OutputStream out = null;
//		InputStream in = null;
		BufferedWriter out = null;
		BufferedReader in = null;
		try {
			if ((! host.startsWith("http://")) && (! host.startsWith("https://"))) {
				host = "http://" + host;
			}
			String myurl = host + path;
			myurl = myurl.replaceAll(" ", "+");
			URL url = new URL(myurl);
			cn = (HttpURLConnection)url.openConnection();
			if (request != null) {
				String auth = request.getHeader("Authorization");
				if ((auth != null) && (! auth.equals(""))) {
					cn.setRequestProperty("Authorization", auth);
				}
			}
			cn.setUseCaches(false);
			cn.setDoInput(true);
			cn.setDoOutput(true);
			if ((boundary != null) && (! boundary.equals(""))) {
				cn.setRequestProperty("Content-Type", "multipart/form-data; charset=UTF8; boundary=" + boundary);
			}
			try {
				Method connectTimeoutMethod = cn.getClass().getMethod("setConnectTimeout", new Class[]{ Integer.class });
				if (connectTimeoutMethod != null) connectTimeoutMethod.invoke(cn, new Object[]{ new Integer(10000) });
				Method readTimeoutMethod = cn.getClass().getMethod("setReadTimeout", new Class[]{ Integer.class });
				if (readTimeoutMethod != null) readTimeoutMethod.invoke(cn, new Object[]{ new Integer(10000) });
			} catch (Exception e) {
				if (DEBUG) System.out.println("WARNING:HardCore/http.post:"+myurl+":"+e);
			}

			/* raw request - may not handle utf-8 correctly */
			/*
			OutputStream out = null;
			out = cn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			out = null;
			*/

			out = new BufferedWriter(new OutputStreamWriter(cn.getOutputStream() , "UTF8"));
			out.write(data, 0, data.length());
			out.flush();
			out.close();
			out = null;

			/* raw response - may not handle utf-8 correctly */
			/*
			InputStream in = null;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			in = cn.getInputStream();
			byte[] buf = new byte[1024];
			int rc;
			while ((rc = in.read(buf)) > 0) {
				bout.write(buf, 0, rc);
			}
			output = bout.toString()
			bout = null;
			in.close();
			in = null;
			cn.disconnect();
			if ((cn != null) && (cn.getInputStream() != null)) cn.getInputStream().close();
			if ((cn != null) && (cn.getOutputStream() != null)) cn.getOutputStream().close();
			cn.close();
			cn = null;
			url = null;
			*/

			StringBuffer bout = new StringBuffer();
			in = new BufferedReader(new InputStreamReader(cn.getInputStream(), "UTF8"));
			String myline;
			while ((myline = in.readLine()) != null) {
				bout.append(myline + "\r\n");
			}
			output = "" + bout;
			bout = null;
			in.close();
			in = null;
			if ((cn != null) && (cn.getInputStream() != null)) cn.getInputStream().close();
			if ((cn != null) && (cn.getOutputStream() != null)) cn.getOutputStream().close();
			cn = null;
			url = null;

		} catch (Exception e) {
		} finally {
			try {
				if (out != null) out.close();
			} catch (Exception e) {
			}
			try {
				if (in != null) in.close();
			} catch (Exception e) {
			}
			in = null;
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
		if (GC) System.gc();
		if (DEBUG) { System.gc(); System.out.println("DEBUG:HardCore/http.post:post:"+host+":::"+path+":::"+data.length()+":::"+Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory()); }
		return output;
	}



}
