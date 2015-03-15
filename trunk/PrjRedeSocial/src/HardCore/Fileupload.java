package HardCore;

import java.io.*;
import java.nio.charset.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

import javax.servlet.http.*;
import javax.servlet.ServletInputStream;

import com.iliketo.util.Str;

public class Fileupload {
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



	public Fileupload(Request request, String rootpath, String myfilepathname) {
		if ((request != null) && (rootpath != null) && (myfilepathname != null)) {
			httpservletrequest = request.getRequest();
			setCharset(request.getCharset());
			filepathname = myfilepathname;
			pathname = rootpath + filepathname;
			try {
				processFileupload(request, pathname, 0);
			} catch (Exception e) {
				myfileupload = new HashMap<String,Object>();
			}
		}
	}
	public Fileupload(Request request, String rootpath, String myfilepathname, int randomize) {
		if ((request != null) && (rootpath != null) && (myfilepathname != null)) {
			httpservletrequest = request.getRequest();
			setCharset(request.getCharset());
			filepathname = myfilepathname;
			pathname = rootpath + filepathname;
			try {
				processFileupload(request, pathname, randomize);
			} catch (Exception e) {
				myfileupload = new HashMap<String,Object>();
			}
		}
	}
	public Fileupload(Request request, String rootpath, String myfilepathname, int randomize, String mycharset) {
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
				processFileupload(request, pathname, randomize);
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



	private void processFileupload(Request request, String pathname, int randomize) throws Exception {
		String myforminputname = "";
		String myforminputvalue = "";
		
		ServletInputStream in = request.getInputStream();
		int contentLength = request.getContentLength();
		FileOutputStream file = null;
	
		String expected = "";
		String boundary = "";
	        byte[] prevbytes  = new byte[1024];
	        byte[] lastbytes  = new byte[1024];
		int totalBytesRead = 0;
		int prevBytesRead = 0;
		int lastBytesRead = 0;
		while ((totalBytesRead < contentLength) && (lastBytesRead > -1)) {
	        	lastbytes  = new byte[lastbytes.length];
			try {
				lastBytesRead = in.readLine(lastbytes, 0, lastbytes.length);
			} catch (IOException e) {
				System.out.println("ERROR:Fileupload:processFileupload:IOException:" + e);
				throw e;
			}
			String data = "";
			try {
				if (lastBytesRead > -1) {
					data = new String(lastbytes, fileuploadcharset).substring(0,lastBytesRead);
				}
			} catch (Exception e) {
			}
			if (lastBytesRead > -1) {
				totalBytesRead += lastBytesRead;
			}
			if (totalBytesRead == 0) {
				Enumeration parameternames = request.getParameterNames();
				while (parameternames.hasMoreElements()) {
					String inputname = "" + parameternames.nextElement();
					String[] inputvalues = request.getParameters(inputname);
					setParameters(inputname, inputvalues);
				}
			}

			if ((expected.equals("")) && (! data.startsWith("-"))) {
				data = "" + data.replaceAll("[\\r\\n]", "");
				String[] parameters = data.split("&");
				for (int i=0; i<parameters.length; i++) {
					String parameter = parameters[i];
					Pattern p = Pattern.compile("([^ =]+)=(.*)");
					Matcher m = p.matcher(parameter);
					if (m.find()) {
						String name = m.group(1);
						String value = URLDecoder.decode(m.group(2));
						addParameter(name, value);
					}
				}
			} else if (expected.equals("")) {
				boundary = "" + data.replaceAll("[\\r\\n]", "");
				expected = "Content-Disposition";
			} else if (expected.equals("Content-Disposition")) {
				HashMap<String,String> myforminput = new HashMap<String,String>();
				if (Pattern.compile("Content-Disposition: form-data;").matcher(data).find()) {
					Pattern p = Pattern.compile("([^ =]+)=\"([^\"]+)\"");
					Matcher m = p.matcher(data);
					while (m.find()) {
						String name = "" + m.group(1);
						String value = "" + m.group(2);
						myforminput.put(name, value);
					}
				}
				myforminputname = "" + myforminput.get("name");
				myforminputvalue = "";
				if (myforminput.get("filename") != null) {
					setParameter(myforminputname, "");
					setParameter("name", myforminputname);
					String filename = "" + myforminput.get("filename");
					String myforminputfilename = "" + myforminput.get("filename");
					file = processFileupload_file(myforminputname, myforminputfilename, pathname, filename, randomize);
				} else {
					file = null;
				}
				expected = "blank";
			} else if (expected.equals("blank")) {
				if (! Pattern.compile("^[\\r\\n]*$").matcher(data).find()) {
					Pattern p = Pattern.compile("^([^:]+): (.*?)$");
					Matcher m = p.matcher(data);
					if (m.find()) {
						String name = m.group(1);
						String value = m.group(2);
						addParameter(myforminputname + "." + name, value);
					}
				} else {
					expected = "Content";
				}
			} else if (expected.equals("Content")) {
				if (! Pattern.compile("^" + boundary + "(--)?[\\r\\n]*$").matcher(data).find()) {
					if (file != null) {
						if ((totalBytesRead >= contentLength - boundary.length() - 4) && ((new String(prevbytes, fileuploadcharset)).matches(".*[\\r\\n]+$"))) {
							if (prevBytesRead > 2) {
								try {
									file.write(prevbytes, 0, prevBytesRead-2);
								} catch (IOException e) {
								}
							}
						} else {
							if (prevBytesRead > 0) {
								try {
									file.write(prevbytes, 0, prevBytesRead);
								} catch (IOException e) {
								}
							}
						}
						prevbytes = lastbytes;
						prevBytesRead = lastBytesRead;
					} else {
						myforminputvalue += data;
					}
				} else {
					if (file != null) {
						if (prevBytesRead > 2) {
							try {
								file.write(prevbytes, 0, prevBytesRead-2);
							} catch (IOException e) {
							}
						}
						try {
							file.close();
						} catch (IOException e) {
						}
						file = null;
					} else {
//						Pattern p = Pattern.compile("^(?s)(.*?)[\\r\\n]*$");
//						Pattern p = Pattern.compile("^(?s)(.*?)[\\r\\n]$");
						Pattern p = Pattern.compile("^(?s)(.*?)$");
						Matcher m = p.matcher(myforminputvalue);
						if (m.find()) {
							addParameter(myforminputname, m.group(1));
						}
					}
					myforminputname = "";
					myforminputvalue = "";
					expected = "Content-Disposition";
					prevBytesRead = 0;
				}
			}
		}

		// client-side edited image file data to override uploaded file
		if ((! getParameter("file_data").equals("")) && (! getParameter("server_filename").equals(""))) {
			String filename = getParameter("server_filename");
			String myforminputfilename = getParameter("server_filename");
			try {
				file = processFileupload_file("file", myforminputfilename, pathname, filename, randomize);
				byte[] filedata = Base64.decode(getParameter("file_data").replaceAll("^data:[^/]+/[^;]+;base64,", ""));
				file.write(filedata, 0, filedata.length);
				file.close();
			} catch (Exception e) {
				file = null;
			}
		}
	}



	private FileOutputStream processFileupload_file(String myforminputname, String myforminputfilename, String pathname, String filename, int randomize) throws Exception {
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
					file = new FileOutputStream(pathname + myfilename);
					
					//Obs: parametro "photo_collection" corresponde ao campo type="file" para upload da imagem
					//parametro "path_photo_collection" corresponde ao campo da database dbcollection usado para gravar nome completo da imagem
					//parametro "photo_item" corresponde ao campo type=file para upload da imagem
					//parametro "path_photo_item" corresponde ao campo da database dbcollectionitem usado para gravar nome completo da imagem
					
					if(myforminputname.equals(Str.PHOTO_COLLECTION)){
						//path_photo_collection = é o campo da dbcollection para armazenar o nome da imagem de coleção
						addParameter(Str.PATH_PHOTO_COLLECTION, filename);
						System.out.println("UPLOAD - photo collection = " + filename);
						System.out.println("path = " + pathname);					
					}
					if(myforminputname.equals(Str.PHOTO_ITEM)){
						//path_photo_item = é o campo da dbcollectionitem para armazenar o nome da imagem item
						addParameter(Str.PATH_PHOTO_ITEM, filename);
						System.out.println("UPLOAD - photo item = " + filename);
						System.out.println("path = " + pathname);
					}					
					if(myforminputname.equals(Str.PHOTO_MEMBER)){						
						//add o parametro com o nome 'path_photo_member', para armazenar o valor do nome da foto no banco de dados q terá o mesmo nome na coluna
						addParameter(Str.PATH_PHOTO_MEMBER, filename);
						System.out.println("UPLOAD - photo member add = " + filename);
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
