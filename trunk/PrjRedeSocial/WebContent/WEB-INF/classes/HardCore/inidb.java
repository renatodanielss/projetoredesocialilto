package HardCore;

import java.io.*;
import java.io.File;
import java.nio.*;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.regex.*;

public class inidb {



	public static String ReadINI(String file, String section, String key, String value) {
		return inidb.ReadINI(file, section, key, value, "", null, null);
	}
	public static String ReadINI(String file, String section, String key, String value, String inifile, HashMap<String,String> ini_cache_database, HashMap<String,String> ini_cache_URLrootpath) {
		String my_value = "" + value;
		boolean cached = false;

		if (file.equals(inifile)) {
			if (key.equals("database")) {
				if (ini_cache_database != null) {
					if (ini_cache_database.containsKey(section)) {
						my_value = "" + ini_cache_database.get(section);
						cached = true;
					}
				}
			} else if (key.equals("URLrootpath")) {
				if (ini_cache_URLrootpath != null) {
					if (ini_cache_URLrootpath.containsKey(section)) {
						my_value = "" + ini_cache_URLrootpath.get(section);
						cached = true;
					}
				}
			}
		}
	
		if (! cached) {
			File fh = new File(file);
			if (fh.exists()) {
				BufferedReader input = null;
				try {
					input = new BufferedReader(new FileReader(file));
					String my_line;
					Pattern p = Pattern.compile("^\\[" + section + "\\]" + key + "=(.*)$");
					while ((my_line = input.readLine()) != null) {
						Matcher m = p.matcher(my_line);
						if (m.find()) {
							my_value = m.group(1);
							break;
						}
					}
					input.close();
				} catch (FileNotFoundException e) {
					if (input != null) try { input.close(); } catch (IOException ee) { ; }
				} catch (IOException e) {
					if (input != null) try { input.close(); } catch (IOException ee) { ; }
				}
			}
		}
		return my_value;
	}



	public static void WriteINI(String file, String section, String key, String value) {
		BufferedReader input = null;
		PrintWriter output = null;
		try {
			FileOutputStream foslock = new FileOutputStream(file + ".lock");
			FileLock filelock = null;
			try {
				filelock = foslock.getChannel().lock();
			} catch (IOException e) {
			}
			String file_content = "";
			boolean replaced = false;
			File fh = new File(file);
			if (fh.exists()) {
				input = new BufferedReader(new FileReader(file));
				String my_line;
				Pattern p = Pattern.compile("^\\[" + section + "\\]" + key + "=(.*)$");
				while ((my_line = input.readLine()) != null) {
					Matcher m = p.matcher(my_line);
					if (m.find()) {
						file_content += "[" + section + "]" + key + "=" + value + "\r\n";
						replaced = true;
					} else if (my_line.equals("%>")) {
						// IGNORE
					} else {
						file_content += my_line + "\r\n";
					}
				}
				input.close();
			} else {
				file_content += "<%" + "\r\n";
			}
			if (! replaced) {
				file_content += "[" + section + "]" + key + "=" + value + "\r\n";
			}
			file_content += "%>";
			output = new PrintWriter(new FileOutputStream(file));
			if (output != null) {
				output.print(file_content);
				output.close();
			}
			if (filelock != null) filelock.release();
			foslock.close();
			File fhlock = new File(file + ".lock");
			if (fhlock.exists()) fhlock.delete();
		} catch (FileNotFoundException e) {
			if (input != null) try { input.close(); } catch (IOException ee) { ; }
			if (output != null) try { output.close(); } catch (Exception ee) { ; }
		} catch (IOException e) {
			if (input != null) try { input.close(); } catch (IOException ee) { ; }
			if (output != null) try { output.close(); } catch (Exception ee) { ; }
		}
	}



	public static void DeleteINI(String file, String section, String key, String value) {
		BufferedReader input = null;
		PrintWriter output = null;
		try {
			FileOutputStream foslock = new FileOutputStream(file + ".lock");
			FileLock filelock = null;
			try {
				filelock = foslock.getChannel().lock();
			} catch (IOException e) {
			}
			String file_content = "";
			boolean replaced = false;
			File fh = new File(file);
			if (fh.exists()) {
				input = new BufferedReader(new FileReader(file));
				String my_line;
				Pattern p = Pattern.compile("^\\[" + section + "\\]" + key + "=(.*)$");
				while ((my_line = input.readLine()) != null) {
					Matcher m = p.matcher(my_line);
					if (m.find()) {
						// DELETE
					} else if (my_line.equals("%>")) {
						// IGNORE
					} else {
						file_content += my_line + "\r\n";
					}
				}
				input.close();
			} else {
				file_content += "<%" + "\r\n";
			}
			file_content += "%>";
			output = new PrintWriter(new FileOutputStream(file));
			if (output != null) {
				output.print(file_content);
				output.close();
			}
			if (filelock != null) filelock.release();
			foslock.close();
			File fhlock = new File(file + ".lock");
			if (fhlock.exists()) fhlock.delete();
		} catch (FileNotFoundException e) {
			if (input != null) try { input.close(); } catch (IOException ee) { ; }
			if (output != null) try { output.close(); } catch (Exception ee) { ; }
		} catch (IOException e) {
			if (input != null) try { input.close(); } catch (IOException ee) { ; }
			if (output != null) try { output.close(); } catch (Exception ee) { ; }
		}
	}



	public static HashMap<String,HashMap<String,String>> AllINI(String file) {
		HashMap<String,HashMap<String,String>> my_ini = new HashMap<String,HashMap<String,String>>();
		File fh = new File(file);
		if (fh.exists()) {
			BufferedReader input = null;
			try {
				input = new BufferedReader(new FileReader(file));
				String my_line;
				Pattern p = Pattern.compile("^\\[(.*)\\]([^=]*)=(.*)$");
				while ((my_line = input.readLine()) != null) {
					Matcher m = p.matcher(my_line);
					if (m.find()) {
						String my_section = m.group(1);
						String my_key = m.group(2);
						String my_value = m.group(3);
						if (! my_ini.containsKey(my_section)) my_ini.put(my_section, new HashMap<String,String>());
						((HashMap<String,String>)my_ini.get(my_section)).put(my_key, my_value);
					}
				}
				input.close();
			} catch (FileNotFoundException e) {
				if (input != null) try { input.close(); } catch (IOException ee) { ; }
			} catch (IOException e) {
				if (input != null) try { input.close(); } catch (IOException ee) { ; }
			}
		}
		return my_ini;
	}



	public static String ReadJSP(String file, String section, String key, String value) {
		String my_value = "" + value;
		boolean cached = false;

		File fh = new File(file);
		if (fh.exists()) {
			BufferedReader input = null;
			try {
				input = new BufferedReader(new FileReader(file));
				String my_line;
				Pattern p = Pattern.compile("^" + key + "\\.put\\(\"" + section + "\",\"(.*)\"\\);$");
				while ((my_line = input.readLine()) != null) {
					Matcher m = p.matcher(my_line);
					if (m.find()) {
						my_value = m.group(1);
						break;
					}
				}
				input.close();
			} catch (FileNotFoundException e) {
				if (input != null) try { input.close(); } catch (IOException ee) { ; }
			} catch (IOException e) {
				if (input != null) try { input.close(); } catch (IOException ee) { ; }
			}
		}
		return my_value;
	}



	public static void WriteJSP(String file, String section, String key, String value) {
		BufferedReader input = null;
		PrintWriter output = null;
		try {
			FileOutputStream foslock = new FileOutputStream(file + ".lock");
			FileLock filelock = null;
			try {
				filelock = foslock.getChannel().lock();
			} catch (IOException e) {
			}
			String file_content = "";
			boolean replaced = false;
			File fh = new File(file);
			if (fh.exists()) {
				input = new BufferedReader(new FileReader(file));
				String my_line;
				Pattern p = Pattern.compile("^" + key + "\\.put\\(\"" + section + "\",\"(.*)\"\\);$");
				while ((my_line = input.readLine()) != null) {
					Matcher m = p.matcher(my_line);
					if (m.find()) {
						file_content += key + ".put(\"" + section + "\",\"" + value.replaceAll("\\\\", "\\\\\\\\") + "\");" + "\r\n";
						replaced = true;
					} else if (my_line.equals("%>")) {
						// IGNORE
					} else {
						file_content += my_line + "\r\n";
					}
				}
				input.close();
			} else {
				file_content += "<%" + "\r\n";
			}
			if (! replaced) {
				file_content += key + ".put(\"" + section + "\",\"" + value.replaceAll("\\\\", "\\\\\\\\") + "\");" + "\r\n";
			}
			file_content += "%>";
			output = new PrintWriter(new FileOutputStream(file));
			if (output != null) {
				output.print(file_content);
				output.close();
			}
			if (filelock != null) filelock.release();
			foslock.close();
			File fhlock = new File(file + ".lock");
			if (fhlock.exists()) fhlock.delete();
		} catch (FileNotFoundException e) {
			if (input != null) try { input.close(); } catch (IOException ee) { ; }
			if (output != null) try { output.close(); } catch (Exception ee) { ; }
		} catch (IOException e) {
			if (input != null) try { input.close(); } catch (IOException ee) { ; }
			if (output != null) try { output.close(); } catch (Exception ee) { ; }
		}
	}
	
	
	
	public static void DeleteJSP(String file, String section, String key, String value) {
		BufferedReader input = null;
		PrintWriter output = null;
		try {
			FileOutputStream foslock = new FileOutputStream(file + ".lock");
			FileLock filelock = null;
			try {
				filelock = foslock.getChannel().lock();
			} catch (IOException e) {
			}
			String file_content = "";
			boolean replaced = false;
			File fh = new File(file);
			if (fh.exists()) {
				input = new BufferedReader(new FileReader(file));
				String my_line;
				Pattern p = Pattern.compile("^" + key + "\\.put\\(\"" + section + "\",\"(.*)\"\\);$");
				while ((my_line = input.readLine()) != null) {
					Matcher m = p.matcher(my_line);
					if (m.find()) {
						// DELETE
					} else if (my_line.equals("%>")) {
						// IGNORE
					} else {
						file_content += my_line + "\r\n";
					}
				}
				input.close();
			} else {
				file_content += "<%" + "\r\n";
			}
			file_content += "%>";
			output = new PrintWriter(new FileOutputStream(file));
			if (output != null) {
				output.print(file_content);
				output.close();
			}
			if (filelock != null) filelock.release();
			foslock.close();
			File fhlock = new File(file + ".lock");
			if (fhlock.exists()) fhlock.delete();
		} catch (FileNotFoundException e) {
			if (input != null) try { input.close(); } catch (IOException ee) { ; }
			if (output != null) try { output.close(); } catch (Exception ee) { ; }
		} catch (IOException e) {
			if (input != null) try { input.close(); } catch (IOException ee) { ; }
			if (output != null) try { output.close(); } catch (Exception ee) { ; }
		}
	}
	
	
	
	public static HashMap<String,HashMap<String,String>> AllJSP(String file) {
		HashMap<String,HashMap<String,String>> my_ini = new HashMap<String,HashMap<String,String>>();
		File fh = new File(file);
		if (fh.exists()) {
			BufferedReader input = null;
			try {
				input = new BufferedReader(new FileReader(file));
				String my_line;
				Pattern p = Pattern.compile("^(.*)\\.put\\(\"(.*)\",\"(.*)\"\\);$");
				while ((my_line = input.readLine()) != null) {
					Matcher m = p.matcher(my_line);
					if (m.find()) {
						String my_section = m.group(2);
						String my_key = m.group(1);
						String my_value = m.group(3).replaceAll("\\\\\\\\", "\\\\");
						if (! my_ini.containsKey(my_section)) my_ini.put(my_section, new HashMap<String,String>());
						((HashMap<String,String>)my_ini.get(my_section)).put(my_key, my_value);
					}
				}
				input.close();
			} catch (FileNotFoundException e) {
				if (input != null) try { input.close(); } catch (IOException ee) { ; }
			} catch (IOException e) {
				if (input != null) try { input.close(); } catch (IOException ee) { ; }
			}
		}
		return my_ini;
	}



}
