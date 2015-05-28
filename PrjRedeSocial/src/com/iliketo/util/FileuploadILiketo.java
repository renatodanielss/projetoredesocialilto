package com.iliketo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import HardCore.Base64;
import HardCore.Common;
import HardCore.Request;
import HardCore.URLDecoder;

public class FileuploadILiketo {
	private HttpServletRequest httpservletrequest = null;
	private HashMap<String,Object> myfileupload = new HashMap<String,Object>();
	private String pathname = "";
	private String filepathname = "";
	private String charsetname = "";
	private Charset charset = null;

	public static String blocked_files = "\\.(asp|aspx|ascx|jsp|php|php3|php4|phtml|phps|cgi|sh|pl)$";
	public String parametercharsetname = "iso-8859-1";
	public String fileuploadcharsetname = "ISO-8859-1";
	public Charset parametercharset = Charset.forName("iso-8859-1");
	public Charset fileuploadcharset = Charset.forName("ISO-8859-1");


	
	public FileuploadILiketo(Request request, String rootpath, String myfilepathname, int randomize, InputStream stream, HashMap<String,String> mapMyFormInput) {
		if ((request != null) && (rootpath != null) && (myfilepathname != null)) {
			httpservletrequest = request.getRequest();
			setCharset(request.getCharset());
			filepathname = myfilepathname;
			pathname = rootpath + filepathname;
			try {
				processFileupload(request, pathname, randomize, stream, mapMyFormInput);
			} catch (Exception e) {
				myfileupload = new HashMap<String,Object>();
			}
		}
	}
	
	public FileuploadILiketo(Request request, String rootpath, String myfilepathname) {
		if ((request != null) && (rootpath != null) && (myfilepathname != null)) {
			httpservletrequest = request.getRequest();
			setCharset(request.getCharset());
			filepathname = myfilepathname;
			pathname = rootpath + filepathname;
			try {
				processFileupload(request, pathname, 0, null, null);
			} catch (Exception e) {
				myfileupload = new HashMap<String,Object>();
			}
		}
	}
	public FileuploadILiketo(Request request, String rootpath, String myfilepathname, int randomize) {
		if ((request != null) && (rootpath != null) && (myfilepathname != null)) {
			httpservletrequest = request.getRequest();
			setCharset(request.getCharset());
			filepathname = myfilepathname;
			pathname = rootpath + filepathname;
			try {
				processFileupload(request, pathname, randomize, null, null);
			} catch (Exception e) {
				myfileupload = new HashMap<String,Object>();
			}
		}
	}
	public FileuploadILiketo(Request request, String rootpath, String myfilepathname, int randomize, String mycharset) {
		if ((request != null) && (rootpath != null) && (myfilepathname != null)) {
			httpservletrequest = request.getRequest();
			if (mycharset != null) {
				setCharset(mycharset);
			} else {
				setCharset(request.getCharset());
			}
			filepathname = myfilepathname;
			pathname = rootpath + filepathname;
			try {
				processFileupload(request, pathname, randomize, null, null);
			} catch (Exception e) {
				myfileupload = new HashMap<String,Object>();
			}
		}
	}



	public HttpServletRequest getRequest() {
		return httpservletrequest;
	}



	public String getCharset() {
		return "" + charsetname;
	}
	public void setCharset(String mycharset) {
		charsetname = mycharset;
		if (! mycharset.equals("")) {
			try {
				charset = Charset.forName(mycharset);
			} catch (Exception e) {
				charset = Charset.forName("ISO-8859-1");
			}
		}
	}



	public boolean parameterExists(String name) {
		if ((myfileupload != null) && (myfileupload.containsKey(name))) {
			return true;
		} else {
			return false;
		}
	}



	public Iterator getParameterNames() {
		if (myfileupload != null) {
			return myfileupload.keySet().iterator();
		} else {
			return null;
		}
	}



	public String getParameter(String name) {
		if ((myfileupload != null) && (myfileupload.get(name) != null)) {
			String value = "" + ((String[])myfileupload.get(name))[0];
			if ((! charsetname.equals("")) && (! parametercharsetname.equals("")) && (! charsetname.toLowerCase().equals(parametercharsetname))) {
				try {
					value = new String(value.getBytes(parametercharsetname), charset);
				} catch (Exception e) {
				}
			}
			return value;
		} else {
			return "";
		}
	}



	public String[] getParameters(String name) {
		if ((myfileupload != null) && (myfileupload.get(name) != null)) {
			String[] values = ((String[])myfileupload.get(name));
			if ((! charsetname.equals("")) && (! parametercharsetname.equals("")) && (! charsetname.toLowerCase().equals(parametercharsetname))) {
				try {
					for (int i=0; i<values.length; i++) {
						values[i] = new String(values[i].getBytes(parametercharsetname), charset);
					}
				} catch (Exception e) {
				}
			}
			return values;
		} else {
			return new String[0];
		}
	}



	public void addParameter(String name, String value) {
		if (myfileupload == null) return;
		if (myfileupload.get(name) == null) {
			String[] values = new String[1];
			values[0] = value;
			myfileupload.put(name, values);
		} else {
			String[] values = ((String[])myfileupload.get(name));
			values = Common.array_redim(values, values.length+1);
			values[values.length-1] = value;
			myfileupload.put(name, values);
		}
	}



	public void setParameter(String name, String value) {
		if (myfileupload == null) return;
		String[] values = new String[1];
		values[0] = value;
		myfileupload.put(name, values);
	}



	public void setParameters(String name, String[] values) {
		if (myfileupload == null) return;
		myfileupload.put(name, values);
	}



	public boolean exists(String name) {
		if (myfileupload == null) return false;
		return myfileupload.containsKey(name);
	}



	private void processFileupload(Request request, String pathname, int randomize, InputStream stream,HashMap<String,String> mapMyFormInput) throws Exception {
		
		String myforminputname = "" + mapMyFormInput.get("name");
		if (mapMyFormInput.get("filename") != null) {
			setParameter(myforminputname, "");
			setParameter("name", myforminputname);
			String filename = "" + mapMyFormInput.get("filename");
			String myforminputfilename = "" + mapMyFormInput.get("filename");
			processFileupload_file(myforminputname, myforminputfilename, pathname, filename, randomize, stream);
		}
	}

	private FileOutputStream processFileupload_file(String myforminputname, String myforminputfilename, String pathname, String filename, int randomize, InputStream stream) throws Exception {
		FileOutputStream file = null;
		filename = filename.replaceAll("\\*", "");
		filename = filename.replaceAll("\\.\\.", ".");
		setParameter(myforminputname + ".fullpathname", filename);
		if (filename.lastIndexOf("\\") >= 0) {
			filename = filename.substring(filename.lastIndexOf("\\")+1);
		}
		if (filename.lastIndexOf("/") >= 0) {
			filename = filename.substring(filename.lastIndexOf("/")+1);
		}
		setParameter(myforminputname + ".filename", filename);
		if (filename.lastIndexOf(".") == -1) {
			setParameter(myforminputname + ".basefilename", filename);
			setParameter(myforminputname + ".filenameextension", "");
		} else {
			setParameter(myforminputname + ".basefilename", filename.substring(0, filename.lastIndexOf(".")));
			setParameter(myforminputname + ".filenameextension", filename.substring(filename.lastIndexOf(".")+1));
		}

//		setParameter(myforminputname + ".upload_filename", "" + myforminput.get("filename"));
		setParameter(myforminputname + ".upload_filename", "" + myforminputfilename);
		setParameter(myforminputname + ".server_filename", "" + filepathname + getParameter(myforminputname + ".filename"));

		if (randomize < 0) {
			file = null;
                } else {
                        java.io.File f = new java.io.File(pathname + getParameter(myforminputname + ".filename"));
			int i = 1;
			if (randomize > 0) {
				String randomfilename = "";
				for (int j=0; j<32; j++) {
					randomfilename = "" + randomfilename + (char)('a' + Integer.parseInt(Common.numberformat("" + Math.random()*25, 0)));
				}
				setParameter(myforminputname + ".filename", "" + getParameter(myforminputname + ".basefilename") + "_" + randomfilename + "." + getParameter(myforminputname + ".filenameextension"));
				setParameter(myforminputname + ".server_filename", "" + filepathname + getParameter(myforminputname + ".filename"));
			}
			while (f.exists()) {
				if (randomize > 0) {
					String randomfilename = "";
					for (int j=0; j<32; j++) {
						randomfilename = "" + randomfilename + (char)('a' + Integer.parseInt(Common.numberformat("" + Math.random()*25, 0)));
					}
					setParameter(myforminputname + ".filename", "" + getParameter(myforminputname + ".basefilename") + "_" + randomfilename + "." + getParameter(myforminputname + ".filenameextension"));
					setParameter(myforminputname + ".server_filename", "" + filepathname + getParameter(myforminputname + ".filename"));
				} else {
					i = i + 1;
					setParameter(myforminputname + ".filename", "" + getParameter(myforminputname + ".basefilename") + "_" + i + "." + getParameter(myforminputname + ".filenameextension"));
					setParameter(myforminputname + ".server_filename", "" + filepathname + getParameter(myforminputname + ".filename"));
				}
				f = new java.io.File(pathname + getParameter(myforminputname + ".filename"));
			}
			filename = "" + getParameter(myforminputname + ".filename");

			if (! Pattern.compile(blocked_files).matcher(filename).find()) {
				try {
					String myfilename = "" + filename;
					if ((! charsetname.equals("")) && (! parametercharsetname.equals("")) && (! charsetname.toLowerCase().equals(parametercharsetname))) {
						try {
							myfilename = new String(myfilename.getBytes(parametercharsetname), charset);
						} catch (Exception e) {
						}
					}
					//file = new FileOutputStream(pathname + myfilename);
		            File uploadedFile = new File(pathname + myfilename);
		            byte[] bytes = new byte[2048];
		            int read = 0;
		            OutputStream outpuStream = new FileOutputStream(new File(uploadedFile.getAbsolutePath()));
		            while ((read = stream.read(bytes)) != -1) {
		                outpuStream.write(bytes, 0, read);
		            }
		            outpuStream.close();
					
					if(myforminputname.equals(Str.FILE)){						
						//add o parametro com o nome 'path_file_default', para armazenar o valor do nome da foto evento no banco de dados q terá o mesmo nome na coluna
						addParameter(Str.PATH_FILE_DEFAULT, filename);
						System.out.println("UPLOAD - file = " + filename);
						System.out.println("path = " + pathname);
					}
				} catch (Exception e) {
					file = null;
				}
			} else {
				file = null;
			}
		}
		return file;
	}
	


} 
