package HardCore;

import java.io.*;
import java.io.File;
import java.lang.Integer;
import java.net.URLEncoder;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Common {
	private static String encoding = "UTF-8";
	private static HashMap<String,Long> profiler = new HashMap<String,Long>();
	private static HashMap<String,Long> profilertotal = new HashMap<String,Long>();



	public static void charset(String myencoding) {
		if ((myencoding != null) && (! myencoding.equals(""))) {
			encoding = myencoding;
		}
	}



	public static String[] validateFormData(Fileupload request) {
		HashMap<String,String> invalid = new HashMap<String,String>();
		Iterator params = request.getParameterNames();
		while (params.hasNext()) {
			String name = "" + params.next();
			String value = "" + request.getParameter(name);
			if (request.parameterExists("validate_" + name)) {
				String[] validates = request.getParameters("validate_" + name);
				for (int i=0; i<validates.length; i++) {
					String validate = "" + validates[i];
					String validate_format = "";
					String validate_operand = "";
					String validate_value = "";
					Pattern p = Pattern.compile("^(.+?)(<=|>=|&lt;=|&gt;=|=|!=|<|>|&lt;|&gt;)(.+?)$");
					Matcher m = p.matcher(validate);
					if (m.find()) {
						validate_format = "" + m.group(1);
						validate_operand = "" + m.group(2);
						if (request.parameterExists("" + m.group(3))) {
							validate_value = "" + request.getParameter("" + m.group(3));
						} else {
							validate_value = "" + m.group(3);
						}
					} else {
						validate_format = validate;
						validate_operand = "";
						validate_value = "";
					}
					if (! validate_format.equals("")) {
						if ((validate_format.equals("date")) && (! Pattern.compile("^\\d\\d\\d\\d-\\d\\d-\\d\\d$").matcher(value).find())) {
							if (invalid.get(name) == null) invalid.put(name,name);
						} else if ((validate_format.equals("datetime")) && (! Pattern.compile("^\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d(:\\d\\d)?$").matcher(value).find())) {
							if (invalid.get(name) == null) invalid.put(name,name);
						} else if ((validate_format.equals("time")) && (! Pattern.compile("^\\d\\d:\\d\\d(:\\d\\d)?$").matcher(value).find())) {
							if (invalid.get(name) == null) invalid.put(name,name);
						} else if ((validate_format.equals("number")) && (! Pattern.compile("^[-+]?[0-9]+[\\.,0-9]*$").matcher(value).find())) {
							if (invalid.get(name) == null) invalid.put(name,name);
						} else if ((validate_format.equals("text")) && (value.equals(""))) {
							if (invalid.get(name) == null) invalid.put(name,name);
						}
						if (validate_format.equals("number")) {
							if ((validate_operand.equals("=")) && (Common.number(value) != Common.number(validate_value))) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals("!=")) && (Common.number(value) == Common.number(validate_value))) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals("<") || validate_operand.equals("&lt;")) && (Common.number(value) >= Common.number(validate_value))) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals(">") || validate_operand.equals("&gt;")) && (Common.number(value) <= Common.number(validate_value))) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals("<=") || validate_operand.equals("&lt;=")) && (Common.number(value) > Common.number(validate_value))) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals(">=") || validate_operand.equals("&gt;=")) && (Common.number(value) < Common.number(validate_value))) {
								if (invalid.get(name) == null) invalid.put(name,name);
							}
						} else {
							if ((validate_operand.equals("=")) && (! value.equals(validate_value))) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals("!=")) && (value.equals(validate_value))) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals("<") || validate_operand.equals("&lt;")) && (value.compareTo(validate_value) >= 0)) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals(">") || validate_operand.equals("&gt;")) && (value.compareTo(validate_value) <= 0)) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals("<=") || validate_operand.equals("&lt;=")) && (value.compareTo(validate_value) > 0)) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals(">=") || validate_operand.equals("&gt;=")) && (value.compareTo(validate_value) < 0)) {
								if (invalid.get(name) == null) invalid.put(name,name);
							}
						}
					}
				}
			}
		}
		String[] keys = new String[invalid.size()];
		invalid.keySet().toArray(keys);
		return keys;
	}



	public static String[] validateFormData(Request request) {
		HashMap<String,String> invalid = new HashMap<String,String>();
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String name = "" + params.nextElement();
			String value = "" + request.getParameter(name);
			if (request.parameterExists("validate_" + name)) {
				String[] validates = request.getParameters("validate_" + name);
				for (int i=0; i<validates.length; i++) {
					String validate = "" + validates[i];
					String validate_format = "";
					String validate_operand = "";
					String validate_value = "";
					Pattern p = Pattern.compile("^(.+?)(<=|>=|&lt;=|&gt;=|=|!=|<|>|&lt;|&gt;)(.+?)$");
					Matcher m = p.matcher(validate);
					if (m.find()) {
						validate_format = "" + m.group(1);
						validate_operand = "" + m.group(2);
						if (request.parameterExists("" + m.group(3))) {
							validate_value = "" + request.getParameter("" + m.group(3));
						} else {
							validate_value = "" + m.group(3);
						}
					} else {
						validate_format = validate;
						validate_operand = "";
						validate_value = "";
					}
					if (! validate_format.equals("")) {
						if ((validate_format.equals("date")) && (! Pattern.compile("^\\d\\d\\d\\d-\\d\\d-\\d\\d$").matcher(value).find())) {
							if (invalid.get(name) == null) invalid.put(name,name);
						} else if ((validate_format.equals("datetime")) && (! Pattern.compile("^\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d(:\\d\\d)?$").matcher(value).find())) {
							if (invalid.get(name) == null) invalid.put(name,name);
						} else if ((validate_format.equals("time")) && (! Pattern.compile("^\\d\\d:\\d\\d(:\\d\\d)?$").matcher(value).find())) {
							if (invalid.get(name) == null) invalid.put(name,name);
						} else if ((validate_format.equals("number")) && (! Pattern.compile("^[-+]?[0-9]+[\\.,0-9]*$").matcher(value).find())) {
							if (invalid.get(name) == null) invalid.put(name,name);
						} else if ((validate_format.equals("text")) && (value.equals(""))) {
							if (invalid.get(name) == null) invalid.put(name,name);
						}
						if (validate_format.equals("number")) {
							if ((validate_operand.equals("=")) && (Common.number(value) != Common.number(validate_value))) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals("!=")) && (Common.number(value) == Common.number(validate_value))) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals("<") || validate_operand.equals("&lt;")) && (Common.number(value) >= Common.number(validate_value))) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals(">") || validate_operand.equals("&gt;")) && (Common.number(value) <= Common.number(validate_value))) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals("<=") || validate_operand.equals("&lt;=")) && (Common.number(value) > Common.number(validate_value))) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals(">=") || validate_operand.equals("&gt;=")) && (Common.number(value) < Common.number(validate_value))) {
								if (invalid.get(name) == null) invalid.put(name,name);
							}
						} else {
							if ((validate_operand.equals("=")) && (! value.equals(validate_value))) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals("!=")) && (value.equals(validate_value))) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals("<") || validate_operand.equals("&lt;")) && (value.compareTo(validate_value) >= 0)) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals(">") || validate_operand.equals("&gt;")) && (value.compareTo(validate_value) <= 0)) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals("<=") || validate_operand.equals("&lt;=")) && (value.compareTo(validate_value) > 0)) {
								if (invalid.get(name) == null) invalid.put(name,name);
							} else if ((validate_operand.equals(">=") || validate_operand.equals("&gt;=")) && (value.compareTo(validate_value) < 0)) {
								if (invalid.get(name) == null) invalid.put(name,name);
							}
						}
					}
				}
			}
		}
		String[] keys = new String[invalid.size()];
		invalid.keySet().toArray(keys);
		return keys;
	}



	static public String[] array_unshift(String[] oldarray, String value) {
		String[] newarray = new String[oldarray.length + 1];
		System.arraycopy(oldarray, 0, newarray, 1, oldarray.length);
		newarray[0] = value;
		return newarray;
	}



	static public String[] array_redim(String[] oldarray, int newsize) {
		String[] newarray;
		if (oldarray.length > newsize) {
			newarray = new String[newsize];
			System.arraycopy(oldarray, 0, newarray, 0, newsize);
		} else if (oldarray.length < newsize) {
			newarray = new String[newsize];
			System.arraycopy(oldarray, 0, newarray, 0, oldarray.length);
			for (int i=oldarray.length; i<newsize; i++) {
				newarray[i] = "";
			}
		} else {
			newarray = oldarray;
		}
		return newarray;
	}



	public static LinkedHashMap<String,String> array_merge(LinkedHashMap<String,String> array1, LinkedHashMap<String,String> array2) {
		LinkedHashMap<String,String> newarray = new LinkedHashMap<String,String>();
		if (array1 != null) {
			Iterator keys = array1.keySet().iterator();
			while (keys.hasNext()) {
				String key = "" + keys.next();
				newarray.put(key, array1.get(key));
			}
		}
		if (array2 != null) {
			Iterator keys = array2.keySet().iterator();
			while (keys.hasNext()) {
				String key = "" + keys.next();
				newarray.put(key, array2.get(key));
			}
		}
		return newarray;
	}
	public static HashMap<String,String> array_merge(HashMap<String,String> array1, HashMap<String,String> array2) {
		HashMap<String,String> newarray = new HashMap<String,String>();
		if (array1 != null) {
			Iterator keys = array1.keySet().iterator();
			while (keys.hasNext()) {
				String key = "" + keys.next();
				newarray.put(key, array1.get(key));
			}
		}
		if (array2 != null) {
			Iterator keys = array2.keySet().iterator();
			while (keys.hasNext()) {
				String key = "" + keys.next();
				newarray.put(key, array2.get(key));
			}
		}
		return newarray;
	}
	public static String[] array_merge(String[] array1, String[] array2) {
		if (array1 == null || array1.length == 0) return array2;
		if (array2 == null || array2.length == 0) return array1;
		List<String> result = new ArrayList<String>(Arrays.asList(array1));
		result.retainAll(Arrays.asList(array2));
		return ((String[]) result.toArray(new String[result.size()]));
	}



	public static LinkedHashMap<String,String> array_merge_values(LinkedHashMap<String,String> array1, LinkedHashMap<String,String> array2) {
		LinkedHashMap<String,String> newarray = new LinkedHashMap<String,String>();
		if (array1 != null) {
			Iterator keys = array1.keySet().iterator();
			while (keys.hasNext()) {
				String key = "" + keys.next();
				newarray.put(array1.get(key), array1.get(key));
			}
		}
		if (array2 != null) {
			Iterator keys = array2.keySet().iterator();
			while (keys.hasNext()) {
				String key = "" + keys.next();
				newarray.put(array2.get(key), array2.get(key));
			}
		}
		return newarray;
	}



	public static String[] array_intersect(String[] values1, String[] values2) {
		HashMap<String,String> commonvalues = new HashMap<String,String>();
		HashMap<String,String> myvalues = new HashMap<String,String>();
		if (values1 != null) {
			for (int i=0; i<values1.length; i++) {
				myvalues.put("" + values1[i], "" + values1[i]);
			}
		}
		if (values2 != null) {
			for (int i=0; i<values2.length; i++) {
				if (myvalues.get("" + values2[i]) != null) {
					commonvalues.put("" + values1[i], "" + values1[i]);
				}
			}
		}
		return (String[]) commonvalues.values().toArray(new String[0]);
	}



	static public Date strtodate(String date) {
		return strtodate("YYYY-mm-dd HH:MM:SS", date);
	}
	static public Date strtodate(String dateformat, String datestring) {
		Date date = null;
		if (dateformat.equals("")) {
			dateformat = "YYYY-mm-dd HH:MM:SS";
		}
		String mydateformat = ("" + dateformat).replaceAll("YYYY", "yyyy").replaceAll("MM", "QQ").replaceAll("mm", "MM").replaceAll("QQ", "mm").replaceAll("SS", "ss");
		while (mydateformat.length()>0) {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(mydateformat);
			simpledateformat.setLenient(true);
			try {
				date = simpledateformat.parse(datestring);
				return date;
			} catch (ParseException e) {
				mydateformat = mydateformat.substring(0, mydateformat.length()-1);
			}
		}
		return date;
	}



	static public Date strtotime(String date) {
		return strtodate("YYYY-mm-dd HH:MM:SS", date);
	}



	static public String strftime(String format, Calendar date) {
		return strftime(format, date.getTime());
	}
	static public String strftime(String format, Date date) {
		if (format.endsWith(":ss")) {
			Locale locale = null;
			return strftime(format, date, locale);
		} else if (format.endsWith(":mm")) {
			Locale locale = null;
			return strftime(format, date, locale);
		} else if (format.endsWith(":HH")) {
			Locale locale = null;
			return strftime(format, date, locale);
		} else if (Pattern.compile("^(.*):([a-zA-Z][a-zA-Z][a-zA-Z]?):([a-zA-Z][a-zA-Z][a-zA-Z]?)$").matcher(format).find()) {
			String localecountry = format.replaceAll("^(.*):([a-zA-Z][a-zA-Z][a-zA-Z]?):([a-zA-Z][a-zA-Z][a-zA-Z]?)$", "$3");
			String localelanguage = format.replaceAll("^(.*):([a-zA-Z][a-zA-Z][a-zA-Z]?):([a-zA-Z][a-zA-Z][a-zA-Z]?)$", "$2");
			format = format.replaceAll("^(.*):([a-zA-Z][a-zA-Z][a-zA-Z]?):([a-zA-Z][a-zA-Z][a-zA-Z]?)$", "$1");
			return strftime(format, date, localelanguage, localecountry);
		} else if (Pattern.compile("^(.*):([a-zA-Z][a-zA-Z][a-zA-Z]?)$").matcher(format).find()) {
			String localelanguage = format.replaceAll("^(.*):([a-zA-Z][a-zA-Z][a-zA-Z]?)$", "$2");
			format = format.replaceAll("^(.*):([a-zA-Z][a-zA-Z][a-zA-Z]?)$", "$1");
			return strftime(format, date, localelanguage);
		} else {
			Locale locale = null;
			return strftime(format, date, locale);
		}
	}
	static public String strftime(String format, Calendar date, String localelanguage) {
		return strftime(format, date.getTime(), localelanguage);
	}
	static public String strftime(String format, Date date, String localelanguage) {
		Locale locale = null;
		if (! localelanguage.equals("")) {
			try {
				locale = new Locale(localelanguage);
			} catch (Exception e) {
				locale = null;
			}
		}
		return strftime(format, date, locale);
	}
	static public String strftime(String format, Calendar date, String localelanguage, String localecountry) {
		return strftime(format, date.getTime(), localelanguage, localecountry);
	}
	static public String strftime(String format, Date date, String localelanguage, String localecountry) {
		Locale locale = null;
		if ((! localelanguage.equals("")) && (! localecountry.equals(""))) {
			try {
				locale = new Locale(localelanguage, localecountry);
			} catch (Exception e) {
				locale = null;
			}
		}
		return strftime(format, date, locale);
	}
	static public String strftime(String format, Calendar date, Locale locale) {
		return strftime(format, date.getTime(), locale);
	}
	static public String strftime(String format, Date date, Locale locale) {
		format = format.replaceAll("%S", "ss");
		format = format.replaceAll("%M", "mm");
		format = format.replaceAll("%H", "HH");
		format = format.replaceAll("%I", "hh");
		format = format.replaceAll("%p", "a");
		format = format.replaceAll("%a", "EEE");
		format = format.replaceAll("%A", "EEEEE");
		format = format.replaceAll("%d", "dd");
		format = format.replaceAll("%e", "d");
		format = format.replaceAll("%j", "D");
		format = format.replaceAll("%U", "ww");
		format = format.replaceAll("%V", "ww");
		format = format.replaceAll("%W", "ww");
		format = format.replaceAll("%b", "MMM");
		format = format.replaceAll("%B", "MMMMM");
		format = format.replaceAll("%m", "MM");
		format = format.replaceAll("%y", "yy");
		format = format.replaceAll("%Y", "yyyy");
		format = format.replaceAll("%C", "");
		format = format.replaceAll("%z", "z");
		format = format.replaceAll("%Z", "Z");
		format = format.replaceAll("%[a-zA-Z0-9]*", "");
		try {
			if (locale == null) {
				return (new SimpleDateFormat(format)).format(date);
			} else {
				return (new SimpleDateFormat(format, locale)).format(date);
			}
		} catch (Exception e) {
			return "";
		}
	}



	static public String SQL_clean(String value) {
		if (value == null) return value;
		value = value.replaceAll("\\\\", "");
		value = value.replaceAll("'", "");
		value = value.replaceAll(";", "");
		value = value.replaceAll("=", "");
		value = value.replaceAll("\"", "");
		value = value.replaceAll("\r", "");
		value = value.replaceAll("\n", "");
		value = value.replaceAll("\\(", "");
		value = value.replaceAll("\\)", "");
		return value;
	}



	static public String crlf_clean(String value) {
		if (value == null) return value;
		value = value.replaceAll("\\r", "");
		value = value.replaceAll("\\n", "");
		return value;
	}



	static public String integer(String value) {
		if (value == null) return value;
		if (value.startsWith("-")) {
			value = "-" + value.replaceAll("[^0-9]", "");
		} else {
			value = value.replaceAll("[^0-9]", "");
		}
		if (value.equals("")) {
			value = "0";
		}
		return value;
	}



	static public String SQLlength(DB db, String column) {
		if ((db.db_type(db.getDatabase()).equals("access")) || (db.db_type(db.getDatabase()).equals("mssql"))) {
			return "len(" + column + ")";
		} else {
			return "length(" + column + ")";
		}
	}



	static public String SQLnumber(DB db, String column) {
		String SQL = "";
		if (db.db_type(db.getDatabase()).equals("access")) {
			SQL = "cdbl(" + column + ")";
		} else if (db.db_type(db.getDatabase()).equals("mssql")) {
//			SQL = "cast(isnull(" + column + ",0) as float)";
//			SQL = "cast(" + column + " as decimal)";
//			SQL = "cast(" + column + " as float)";
//			SQL = "convert(decimal(10,5), cast(" + column + " as nvarchar(250)))";
			SQL = "cast(cast(" + column + " as nvarchar(250)) as float)";
		} else if (db.db_type(db.getDatabase()).equals("mysql")) {
			SQL = "cast(" + column + " as decimal)";
		} else if (db.db_type(db.getDatabase()).equals("pgsql")) {
//			SQL = "cast(" + column + " as int)";
			SQL = "cast(" + column + " as float)";
		} else if (db.db_type(db.getDatabase()).equals("oracle")) {
			SQL = "to_number(" + column + ")";
		} else if (db.db_type(db.getDatabase()).equals("db2")) {
//			SQL = "cast(" + column + " as real)";
//			SQL = "cast(" + column + " as double)";
			SQL = "cast(" + column + " as float)";
		} else {
			SQL = "" + column;
		}
		return SQL;
	}



	static public String SQLlimit(String db_type, String key, String select, String from, String where, String order, boolean myreverse, int offset, int rows) {
		String SQL = "";
		int last = offset + rows - 1;
		if (where.equals("")) where = "1=1";
		String reverse = "" + order;
//		if (myreverse) reverse = order + " DESC";
		if ((db_type.equals("access")) || (db_type.equals("mssql"))) {
			if (offset > 0) {
				SQL = "SELECT " + select + " FROM " + from + " WHERE " + key + " IN ( SELECT TOP " + rows + " " + key + " FROM " + from + " WHERE " + where + " AND " + key + " NOT IN (SELECT TOP " + offset + " " + key + " FROM " + from + " WHERE " + where + " ORDER BY " + order + " ) ORDER BY " + order + " ) ORDER BY " + order;
			} else {
				SQL = "SELECT " + select + " FROM " + from + " WHERE " + key + " IN ( SELECT TOP " + rows + " " + key + " FROM " + from + " WHERE " + where + " ORDER BY " + order + " ) ORDER BY " + order;
			}
		} else if (db_type.equals("mysql")) {
			SQL = "SELECT " + select + " FROM " + from + " WHERE " + where + " ORDER BY " + order + " LIMIT " + offset + "," + rows;
		} else if (db_type.equals("pgsql")) {
			SQL = "SELECT " + select + " FROM " + from + " WHERE " + where + " ORDER BY " + order + " LIMIT " + rows + " OFFSET " + offset;
		} else if (db_type.equals("oracle")) {
			SQL = "SELECT * FROM ( SELECT A.*, ROWNUM r FROM ( SELECT " + select + " FROM " + from + " WHERE " + where + " ORDER BY " + order + " ) A WHERE ROWNUM <= " + (1+last) + " ) B WHERE r >= " + (1+offset);
		} else if (db_type.equals("db2")) {
			SQL = "SELECT * FROM ( SELECT * FROM ( SELECT " + select + " FROM " + from + " WHERE " + where + " ORDER BY " + order + " FETCH FIRST " + last + " ROWS ONLY ) foo ORDER BY " + reverse.replaceAll("[a-zA-Z]+\\.", "") + " FETCH FIRST " + rows + " ROWS ONLY ) bar ORDER BY " + order.replaceAll("[a-zA-Z]+\\.", "");
		} else {
			SQL = "SELECT " + select + " FROM " + from + " X WHERE " + where + " AND ( SELECT COUNT(*) FROM " + from + " WHERE " + key + " > X." + key + " ) BETWEEN " + offset + " AND " + last + " ORDER BY " + order;
		}
		return SQL;
	}



	static public String SQL_list_row_ids(DB db, HashMap<String,HashMap<String,String>> rows) {
		return SQL_list_row_ids(rows, false);
	}
	static public String SQL_list_row_ids(HashMap<String,HashMap<String,String>> rows) {
		return SQL_list_row_ids(rows, false);
	}
	static public String SQL_list_row_ids(DB db, HashMap<String,HashMap<String,String>> rows, boolean is_numeric) {
		return SQL_list_row_ids(rows, is_numeric);
	}
	static public String SQL_list_row_ids(HashMap<String,HashMap<String,String>> rows, boolean is_numeric) {
		String myvalues = "";
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			String rowskey = "" + rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(rowskey);
			Iterator rowIterator = row.keySet().iterator();
			if (rowIterator.hasNext()) {
				String rowkey = "" + rowIterator.next();
				String value = "" + row.get(rowkey);
				if (! myvalues.equals("")) {
					myvalues = myvalues + ",";
				}
				if (! is_numeric) {
					myvalues = myvalues + DB.db_quote(value);
				} else {
					myvalues = myvalues + value;
				}
			}
		}
		if (myvalues.equals("")) {
			if (! is_numeric) {
				myvalues = "''";
			} else {
				myvalues = "null";
			}
		}
		return myvalues;
	}



	static public String SQL_list_values(DB db, HashMap<String,String> values) {
		return SQL_list_values((String[])values.keySet().toArray(new String[0]));
	}
	static public String SQL_list_values(HashMap<String,String> values) {
		return SQL_list_values((String[])values.keySet().toArray(new String[0]));
	}
	static public String SQL_list_values(DB db, Collection<String> values) {
		return SQL_list_values((String[])values.toArray(new String[0]));
	}
	static public String SQL_list_values(Collection<String> values) {
		return SQL_list_values((String[])values.toArray(new String[0]));
	}
	static public String SQL_list_values(DB db, Set<String> values) {
		return SQL_list_values((String[])values.toArray(new String[0]));
	}
	static public String SQL_list_values(Set<String> values) {
		return SQL_list_values((String[])values.toArray(new String[0]));
	}
	static public String SQL_list_values(DB db, String[] values) {
		return SQL_list_values(values);
	}
	static public String SQL_list_values(String[] values) {
		String myvalues = "";
		for (int i=0; i<values.length; i++) {
			String value = values[i];
			if (! myvalues.equals("")) {
				myvalues = myvalues + ",";
			}
			myvalues = myvalues + DB.db_quote(value);
		}
		if (myvalues.equals("")) {
			myvalues = "''";
		}
		return myvalues;
	}



	static public String SQLwhere(String SQLwhere, String expression) {
		if (SQLwhere.equals("-")) {
			SQLwhere = " ";
		} else if (! SQLwhere.equals("")) {
			SQLwhere = SQLwhere + " and ";
		} else {
			SQLwhere = " where ";
		}
		SQLwhere = SQLwhere + expression;
		return SQLwhere;
	}



	static public String SQLwhere_in(String SQLwhere, String column, String value) {
		return SQLwhere_in(null, SQLwhere, column, value);
	}
	static public String SQLwhere_in(DB db, String SQLwhere, String column, String value) {
		if (SQLwhere.equals("-")) {
			SQLwhere = " ";
		} else if (! SQLwhere.equals("")) {
			SQLwhere = SQLwhere + " and ";
		} else {
			SQLwhere = " where ";
		}
		if ((value == null) || (value.equals(""))) {
			if (db == null) {
				SQLwhere = SQLwhere + "((" + column + " = '') or (" + column + " is null))";
			} else {
				SQLwhere = SQLwhere + db.is_blank(column);
			}
		} else {
			if (db == null) {
				SQLwhere = SQLwhere + "(" + column + " in (" + DB.db_quote(value).replaceAll(",", "','") + ")" + ")";
			} else if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQLwhere = SQLwhere + "(cast(" + column + " as varchar) in (" + DB.db_quote(value).replaceAll(",", "','") + ")" + ")";
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQLwhere = SQLwhere + "(dbms_lob.substr(" + column + ",255) in (" + DB.db_quote(value).replaceAll(",", "','") + ")" + ")";
			} else {
				SQLwhere = SQLwhere + "(" + column + " in (" + DB.db_quote(value).replaceAll(",", "','") + ")" + ")";
			}
		}
		return SQLwhere;
	}



	static public String SQLwhere_not_in(String SQLwhere, String column, String value) {
		return SQLwhere_not_in(null, SQLwhere, column, value);
	}
	static public String SQLwhere_not_in(DB db, String SQLwhere, String column, String value) {
		if (SQLwhere.equals("-")) {
			SQLwhere = " ";
		} else if (! SQLwhere.equals("")) {
			SQLwhere = SQLwhere + " and ";
		} else {
			SQLwhere = " where ";
		}
		if ((value == null) || (value.equals(""))) {
			if (db == null) {
				SQLwhere = SQLwhere + "((" + column + " <> '') and (" + column + " is not null))";
			} else {
				SQLwhere = SQLwhere + db.is_not_blank(column);
			}
		} else {
			if (db == null) {
				SQLwhere = SQLwhere + "(" + column + " not in (" + DB.db_quote(value).replaceAll(",", "','") + ")" + ")";
			} else if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQLwhere = SQLwhere + "(cast(" + column + " as varchar) not in (" + DB.db_quote(value).replaceAll(",", "','") + ")" + ")";
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQLwhere = SQLwhere + "(dbms_lob.substr(" + column + ",255) not in (" + DB.db_quote(value).replaceAll(",", "','") + ")" + ")";
			} else {
				SQLwhere = SQLwhere + "(" + column + " not in (" + DB.db_quote(value).replaceAll(",", "','") + ")" + ")";
			}
		}
		return SQLwhere;
	}



	static public String SQLwhere_equals(String SQLwhere, String column, String value) {
		return SQLwhere_equals(null, SQLwhere, column, value);
	}
	static public String SQLwhere_equals(DB db, String SQLwhere, String column, String value) {
		if (SQLwhere.equals("-")) {
			SQLwhere = " ";
		} else if (! SQLwhere.equals("")) {
			SQLwhere = SQLwhere + " and ";
		} else {
			SQLwhere = " where ";
		}
		if ((value == null) || (value.equals(""))) {
			if (db == null) {
				SQLwhere = SQLwhere + "(" + column + " is null or " + column + "='')";
			} else if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQLwhere = SQLwhere + "(" + column + " is null or substring(" + column + ",1,250)='')";
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQLwhere = SQLwhere + "(" + column + " is null or to_char(" + column + ")='')";
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQLwhere = SQLwhere + "(" + column + " is null or varchar(" + column + ",250)='')";
			} else {
				SQLwhere = SQLwhere + "(" + column + " is null or " + column + "='')";
			}
		} else {
			if (db == null) {
				SQLwhere = SQLwhere + column + "=" + DB.db_quote(value);
			} else if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQLwhere = SQLwhere + "substring(" + column + ",1,250)=" + DB.db_quote(value);
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQLwhere = SQLwhere + "to_char(" + column + ")=" + DB.db_quote(value);
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQLwhere = SQLwhere + "varchar(" + column + ",250)=" + DB.db_quote(value);
			} else {
				SQLwhere = SQLwhere + column + "=" + DB.db_quote(value);
			}
		}
		return SQLwhere;
	}



	static public String SQLwhere_equals_or(String SQLwhere, String column1, String value1, String column2, String value2) {
		return SQLwhere_equals_or(null, SQLwhere, column1, value1, column2, value2);
	}
	static public String SQLwhere_equals_or(DB db, String SQLwhere, String column1, String value1, String column2, String value2) {
		if (SQLwhere.equals("-")) {
			SQLwhere = " ";
		} else if (! SQLwhere.equals("")) {
			SQLwhere = SQLwhere + " and ";
		} else {
			SQLwhere = " where ";
		}
		SQLwhere = SQLwhere + "((";
		if ((value1 == null) || (value1.equals(""))) {
			if (db == null) {
				SQLwhere = SQLwhere + "(" + column1 + " is null or " + column1 + "='')";
			} else if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQLwhere = SQLwhere + "(" + column1 + " is null or substring(" + column1 + ",1,250)='')";
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQLwhere = SQLwhere + "(" + column1 + " is null or to_char(" + column1 + ")='')";
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQLwhere = SQLwhere + "(" + column1 + " is null or varchar(" + column1 + ",250)='')";
			} else {
				SQLwhere = SQLwhere + "(" + column1 + " is null or " + column1 + "='')";
			}
		} else {
			if (db == null) {
				SQLwhere = SQLwhere + column1 + "=" + DB.db_quote(value1);
			} else if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQLwhere = SQLwhere + "substring(" + column1 + ",1,250)=" + DB.db_quote(value1);
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQLwhere = SQLwhere + "to_char(" + column1 + ")=" + DB.db_quote(value1);
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQLwhere = SQLwhere + "varchar(" + column1 + ",250)=" + DB.db_quote(value1);
			} else {
				SQLwhere = SQLwhere + column1 + "=" + DB.db_quote(value1);
			}
		}
		SQLwhere = SQLwhere + ") or (";
		if ((value2 == null) || (value2.equals(""))) {
			if (db == null) {
				SQLwhere = SQLwhere + "(" + column2 + " is null or " + column2 + "='')";
			} else if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQLwhere = SQLwhere + "(" + column2 + " is null or substring(" + column2 + ",1,250)='')";
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQLwhere = SQLwhere + "(" + column2 + " is null or to_char(" + column2 + ")='')";
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQLwhere = SQLwhere + "(" + column2 + " is null or varchar(" + column2 + ",250)='')";
			} else {
				SQLwhere = SQLwhere + "(" + column2 + " is null or " + column2 + "='')";
			}
		} else {
			if (db == null) {
				SQLwhere = SQLwhere + column2 + "=" + DB.db_quote(value2);
			} else if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQLwhere = SQLwhere + "substring(" + column2 + ",1,250)=" + DB.db_quote(value2);
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQLwhere = SQLwhere + "to_char(" + column2 + ")=" + DB.db_quote(value2);
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQLwhere = SQLwhere + "varchar(" + column2 + ",250)=" + DB.db_quote(value2);
			} else {
				SQLwhere = SQLwhere + column2 + "=" + DB.db_quote(value2);
			}
		}
		SQLwhere = SQLwhere + "))";
		return SQLwhere;
	}



	static public String SQLwhere_not_equals(String SQLwhere, String column, String value) {
		return SQLwhere_not_equals(null, SQLwhere, column, value);
	}
	static public String SQLwhere_not_equals(DB db, String SQLwhere, String column, String value) {
		if (SQLwhere.equals("-")) {
			SQLwhere = " ";
		} else if (! SQLwhere.equals("")) {
			SQLwhere = SQLwhere + " and ";
		} else {
			SQLwhere = " where ";
		}
		if ((value == null) || (value.equals(""))) {
			if (db == null) {
				SQLwhere = SQLwhere + "((" + column + " is not null) and (" + column + "<>''))";
			} else if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQLwhere = SQLwhere + "((" + column + " is not null) and (substring(" + column + ",1,250)<>''))";
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQLwhere = SQLwhere + "((" + column + " is not null) and (to_char(" + column + ")<>''))";
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQLwhere = SQLwhere + "((" + column + " is not null) and (varchar(" + column + ",250)<>''))";
			} else {
				SQLwhere = SQLwhere + "((" + column + " is not null) and (" + column + "<>''))";
			}
		} else {
			if (db == null) {
				SQLwhere = SQLwhere + "((" + column + " is null) or (" + column + "<>" + DB.db_quote(value) + "))";
			} else if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQLwhere = SQLwhere + "((" + column + " is null) or (substring(" + column + ",1,250)<>" + DB.db_quote(value) + "))";
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQLwhere = SQLwhere + "((" + column + " is null) or (to_char(" + column + ")<>" + DB.db_quote(value) + "))";
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQLwhere = SQLwhere + "((" + column + " is null) or (varchar(" + column + ",250)<>" + DB.db_quote(value) + "))";
			} else {
				SQLwhere = SQLwhere + "((" + column + " is null) or (" + column + "<>" + DB.db_quote(value) + "))";
			}
		}
		return SQLwhere;
	}



	static public String SQLwhere_not_contains(DB db, Configuration config, String SQLwhere, String column, String value) {
		if ((db.db_type(db.getDatabase()).equals("access")) || (db.db_type(db.getDatabase()).equals("mssql"))) {
			value = value.replaceAll("\\[", "[[]");
		}
		if (SQLwhere.equals("-")) {
			SQLwhere = " ";
		} else if (! SQLwhere.equals("")) {
			SQLwhere = SQLwhere + " and ";
		} else {
			SQLwhere = " where ";
		}
		if ((value == null) || (value.equals(""))) {
			SQLwhere = SQLwhere + "((" + column + " is not null) and (" + column + " not like ''))";
		} else if ((db.db_type(db.getDatabase()).equals("mysql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (not match (" + column + ") against (" + DB.db_quote(value) + "))";
		} else if ((db.db_type(db.getDatabase()).equals("mssql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (not contains (" + column + ", " + DB.db_quote(value) + ". 1))";
		} else if ((db.db_type(db.getDatabase()).equals("oracle")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (not contains (" + column + ", " + DB.db_quote(value) + ". 1)>0)";
		} else if ((db.db_type(db.getDatabase()).equals("db2")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (not contains (" + column + ", " + DB.db_quote(value) + ". 1)=1)";
		} else if ((db.db_type(db.getDatabase()).equals("pgsql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (not to_tsvector(" + column + ") @@ to_tsquery(" + DB.db_quote(value) + "))";
		} else {
			SQLwhere = SQLwhere + "(" + column + " not like " + DB.db_quote("%" + value + "%") + ")";
		}
		return SQLwhere;
	}



	static public String SQLwhere_contains(DB db, Configuration config, String SQLwhere, String column, String value) {
		if ((db.db_type(db.getDatabase()).equals("access")) || (db.db_type(db.getDatabase()).equals("mssql"))) {
			value = value.replaceAll("\\[", "[[]");
		}
		if (SQLwhere.equals("-")) {
			SQLwhere = " ";
		} else if (! SQLwhere.equals("")) {
			SQLwhere = SQLwhere + " and ";
		} else {
			SQLwhere = " where ";
		}
		if ((value == null) || (value.equals(""))) {
			SQLwhere = SQLwhere + "(" + column + " is null or " + column + " like '')";
		} else if ((db.db_type(db.getDatabase()).equals("mysql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (match (" + column + ") against (" + DB.db_quote(value) + "))";
		} else if ((db.db_type(db.getDatabase()).equals("mssql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column + ", " + DB.db_quote(value) + ". 1))";
		} else if ((db.db_type(db.getDatabase()).equals("oracle")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column + ", " + DB.db_quote(value) + ". 1)>0)";
		} else if ((db.db_type(db.getDatabase()).equals("db2")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column + ", " + DB.db_quote(value) + ". 1)=1)";
		} else if ((db.db_type(db.getDatabase()).equals("pgsql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (to_tsvector(" + column + ") @@ to_tsquery(" + DB.db_quote(value) + "))";
		} else {
			SQLwhere = SQLwhere + column + " like " + DB.db_quote("%" + value + "%");
		}
		return SQLwhere;
	}



	static public String SQLwhere_contains_or(DB db, Configuration config, String SQLwhere, String column1, String value1, String column2, String value2) {
		if ((db.db_type(db.getDatabase()).equals("access")) || (db.db_type(db.getDatabase()).equals("mssql"))) {
			value1 = value1.replaceAll("\\[", "[[]");
			value2 = value2.replaceAll("\\[", "[[]");
		}
		if (SQLwhere.equals("-")) {
			SQLwhere = " ";
		} else if (! SQLwhere.equals("")) {
			SQLwhere = SQLwhere + " and ";
		} else {
			SQLwhere = " where ";
		}
		SQLwhere = SQLwhere + "((";
		if ((value1 == null) || (value1.equals(""))) {
			SQLwhere = SQLwhere + "(" + column1 + " is null or " + column1 + " like '')";
		} else if ((db.db_type(db.getDatabase()).equals("mysql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (match (" + column1 + ") against (" + DB.db_quote(value1) + "))";
		} else if ((db.db_type(db.getDatabase()).equals("mssql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column1 + ", " + DB.db_quote(value1) + ". 1))";
		} else if ((db.db_type(db.getDatabase()).equals("oracle")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column1 + ", " + DB.db_quote(value1) + ". 1)>0)";
		} else if ((db.db_type(db.getDatabase()).equals("db2")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column1 + ", " + DB.db_quote(value1) + ". 1)=1)";
		} else if ((db.db_type(db.getDatabase()).equals("pgsql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (to_tsvector(" + column1 + ") @@ to_tsquery(" + DB.db_quote(value1) + "))";
		} else {
			SQLwhere = SQLwhere + column1 + " like " + DB.db_quote("%" + value1 + "%");
		}
		SQLwhere = SQLwhere + ") or (";
		if ((value2 == null) || (value2.equals(""))) {
			SQLwhere = SQLwhere + "(" + column2 + " is null or " + column2 + " like '')";
		} else if ((db.db_type(db.getDatabase()).equals("mysql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (match (" + column2 + ") against (" + DB.db_quote(value2) + "))";
		} else if ((db.db_type(db.getDatabase()).equals("mssql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column2 + ", " + DB.db_quote(value2) + ". 1))";
		} else if ((db.db_type(db.getDatabase()).equals("oracle")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column2 + ", " + DB.db_quote(value2) + ". 1)>0)";
		} else if ((db.db_type(db.getDatabase()).equals("db2")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column2 + ", " + DB.db_quote(value2) + ". 1)=1)";
		} else if ((db.db_type(db.getDatabase()).equals("pgsql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (to_tsvector(" + column2 + ") @@ to_tsquery(" + DB.db_quote(value2) + "))";
		} else {
			SQLwhere = SQLwhere + column2 + " like " + DB.db_quote("%" + value2 + "%");
		}
		SQLwhere = SQLwhere + "))";

		return SQLwhere;
	}



	static public String SQLwhere_contains_or_or(DB db, Configuration config, String SQLwhere, String column1, String value1, String column2, String value2, String column3, String value3) {
		if ((db.db_type(db.getDatabase()).equals("access")) || (db.db_type(db.getDatabase()).equals("mssql"))) {
			value1 = value1.replaceAll("\\[", "[[]");
			value2 = value2.replaceAll("\\[", "[[]");
			value3 = value3.replaceAll("\\[", "[[]");
		}
		if (SQLwhere.equals("-")) {
			SQLwhere = " ";
		} else if (! SQLwhere.equals("")) {
			SQLwhere = SQLwhere + " and ";
		} else {
			SQLwhere = " where ";
		}
		SQLwhere = SQLwhere + "((";
		if ((value1 == null) || (value1.equals(""))) {
			SQLwhere = SQLwhere + "(" + column1 + " is null or " + column1 + " like '')";
		} else if ((db.db_type(db.getDatabase()).equals("mysql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (match (" + column1 + ") against (" + DB.db_quote(value1) + "))";
		} else if ((db.db_type(db.getDatabase()).equals("mssql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column1 + ", " + DB.db_quote(value1) + ". 1))";
		} else if ((db.db_type(db.getDatabase()).equals("oracle")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column1 + ", " + DB.db_quote(value1) + ". 1)>0)";
		} else if ((db.db_type(db.getDatabase()).equals("db2")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column1 + ", " + DB.db_quote(value1) + ". 1)=1)";
		} else if ((db.db_type(db.getDatabase()).equals("pgsql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (to_tsvector(" + column1 + ") @@ to_tsquery(" + DB.db_quote(value1) + "))";
		} else {
			SQLwhere = SQLwhere + column1 + " like " + DB.db_quote("%" + value1 + "%");
		}
		SQLwhere = SQLwhere + ") or (";
		if ((value2 == null) || (value2.equals(""))) {
			SQLwhere = SQLwhere + "(" + column2 + " is null or " + column2 + " like '')";
		} else if ((db.db_type(db.getDatabase()).equals("mysql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (match (" + column2 + ") against (" + DB.db_quote(value2) + "))";
		} else if ((db.db_type(db.getDatabase()).equals("mssql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column2 + ", " + DB.db_quote(value2) + ". 1))";
		} else if ((db.db_type(db.getDatabase()).equals("oracle")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column2 + ", " + DB.db_quote(value2) + ". 1)>0)";
		} else if ((db.db_type(db.getDatabase()).equals("db2")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column2 + ", " + DB.db_quote(value2) + ". 1)=1)";
		} else if ((db.db_type(db.getDatabase()).equals("pgsql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (to_tsvector(" + column2 + ") @@ to_tsquery(" + DB.db_quote(value2) + "))";
		} else {
			SQLwhere = SQLwhere + column2 + " like " + DB.db_quote("%" + value2 + "%");
		}
		SQLwhere = SQLwhere + ") or (";
		if ((value3 == null) || (value3.equals(""))) {
			SQLwhere = SQLwhere + "(" + column3 + " is null or " + column3 + " like '')";
		} else if ((db.db_type(db.getDatabase()).equals("mysql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (match (" + column3 + ") against (" + DB.db_quote(value3) + "))";
		} else if ((db.db_type(db.getDatabase()).equals("mssql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column3 + ", " + DB.db_quote(value3) + ". 1))";
		} else if ((db.db_type(db.getDatabase()).equals("oracle")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column3 + ", " + DB.db_quote(value3) + ". 1)>0)";
		} else if ((db.db_type(db.getDatabase()).equals("db2")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (contains (" + column3 + ", " + DB.db_quote(value3) + ". 1)=1)";
		} else if ((db.db_type(db.getDatabase()).equals("pgsql")) && (config.get(db, "use_fulltext_indexing").equals("yes"))) {
			SQLwhere = SQLwhere + " (to_tsvector(" + column3 + ") @@ to_tsquery(" + DB.db_quote(value3) + "))";
		} else {
			SQLwhere = SQLwhere + column3 + " like " + DB.db_quote("%" + value3 + "%");
		}
		SQLwhere = SQLwhere + "))";

		return SQLwhere;
	}



	static public String SQLwhere_like(DB db, String SQLwhere, String column, String value) {
		if ((db.db_type(db.getDatabase()).equals("access")) || (db.db_type(db.getDatabase()).equals("mssql"))) {
			value = value.replaceAll("\\[", "[[]");
		}
		if (SQLwhere.equals("-")) {
			SQLwhere = " ";
		} else if (! SQLwhere.equals("")) {
			SQLwhere = SQLwhere + " and ";
		} else {
			SQLwhere = " where ";
		}
		if ((value == null) || (value.equals(""))) {
			SQLwhere = SQLwhere + "(" + column + " is null or " + column + " like '')";
		} else {
			SQLwhere = SQLwhere + column + " like " + DB.db_quote(value);
		}
		return SQLwhere;
	}



	static public String SQLwhere_like_or(DB db, String SQLwhere, String column1, String value1, String column2, String value2) {
		if ((db.db_type(db.getDatabase()).equals("access")) || (db.db_type(db.getDatabase()).equals("mssql"))) {
			value1 = value1.replaceAll("\\[", "[[]");
			value2 = value2.replaceAll("\\[", "[[]");
		}
		if (SQLwhere.equals("-")) {
			SQLwhere = " ";
		} else if (! SQLwhere.equals("")) {
			SQLwhere = SQLwhere + " and ";
		} else {
			SQLwhere = " where ";
		}
		SQLwhere = SQLwhere + "((";
		if ((value1 == null) || (value1.equals(""))) {
			SQLwhere = SQLwhere + "(" + column1 + " is null or " + column1 + " like '')";
		} else {
			SQLwhere = SQLwhere + column1 + " like " + DB.db_quote(value1);
		}
		SQLwhere = SQLwhere + ") or (";
		if ((value2 == null) || (value2.equals(""))) {
			SQLwhere = SQLwhere + "(" + column2 + " is null or " + column2 + " like " + DB.db_quote(value2) + ")";
		} else {
			SQLwhere = SQLwhere + column2 + " like " + DB.db_quote(value2);
		}
		SQLwhere = SQLwhere + "))";
		return SQLwhere;
	}



	static public String SQLwhere_like_or_or(DB db, String SQLwhere, String column1, String value1, String column2, String value2, String column3, String value3) {
		if ((db.db_type(db.getDatabase()).equals("access")) || (db.db_type(db.getDatabase()).equals("mssql"))) {
			value1 = value1.replaceAll("\\[", "[[]");
			value2 = value2.replaceAll("\\[", "[[]");
		}
		if (SQLwhere.equals("-")) {
			SQLwhere = " ";
		} else if (! SQLwhere.equals("")) {
			SQLwhere = SQLwhere + " and ";
		} else {
			SQLwhere = " where ";
		}
		SQLwhere = SQLwhere + "((";
		if ((value1 == null) || (value1.equals(""))) {
			SQLwhere = SQLwhere + "(" + column1 + " is null or " + column1 + " like '')";
		} else {
			SQLwhere = SQLwhere + column1 + " like " + DB.db_quote(value1);
		}
		SQLwhere = SQLwhere + ") or (";
		if ((value2 == null) || (value2.equals(""))) {
			SQLwhere = SQLwhere + "(" + column2 + " is null or " + column2 + " like " + DB.db_quote(value2) + ")";
		} else {
			SQLwhere = SQLwhere + column2 + " like " + DB.db_quote(value2);
		}
		SQLwhere = SQLwhere + ") or (";
		if ((value3 == null) || (value3.equals(""))) {
			SQLwhere = SQLwhere + "(" + column3 + " is null or " + column3 + " like " + DB.db_quote(value3) + ")";
		} else {
			SQLwhere = SQLwhere + column3 + " like " + DB.db_quote(value3);
		}
		SQLwhere = SQLwhere + "))";
		return SQLwhere;
	}



	static public String SQLwhere_like_or_or_or(DB db, String SQLwhere, String column1, String value1, String column2, String value2, String column3, String value3, String column4, String value4) {
		if ((db.db_type(db.getDatabase()).equals("access")) || (db.db_type(db.getDatabase()).equals("mssql"))) {
			value1 = value1.replaceAll("\\[", "[[]");
			value2 = value2.replaceAll("\\[", "[[]");
		}
		if (SQLwhere.equals("-")) {
			SQLwhere = " ";
		} else if (! SQLwhere.equals("")) {
			SQLwhere = SQLwhere + " and ";
		} else {
			SQLwhere = " where ";
		}
		SQLwhere = SQLwhere + "((";
		if ((value1 == null) || (value1.equals(""))) {
			SQLwhere = SQLwhere + "(" + column1 + " is null or " + column1 + " like '')";
		} else {
			SQLwhere = SQLwhere + column1 + " like " + DB.db_quote(value1);
		}
		SQLwhere = SQLwhere + ") or (";
		if ((value2 == null) || (value2.equals(""))) {
			SQLwhere = SQLwhere + "(" + column2 + " is null or " + column2 + " like " + DB.db_quote(value2) + ")";
		} else {
			SQLwhere = SQLwhere + column2 + " like " + DB.db_quote(value2);
		}
		SQLwhere = SQLwhere + ") or (";
		if ((value3 == null) || (value3.equals(""))) {
			SQLwhere = SQLwhere + "(" + column3 + " is null or " + column3 + " like " + DB.db_quote(value3) + ")";
		} else {
			SQLwhere = SQLwhere + column3 + " like " + DB.db_quote(value3);
		}
		SQLwhere = SQLwhere + ") or (";
		if ((value4 == null) || (value4.equals(""))) {
			SQLwhere = SQLwhere + "(" + column4 + " is null or " + column4 + " like " + DB.db_quote(value4) + ")";
		} else {
			SQLwhere = SQLwhere + column4 + " like " + DB.db_quote(value4);
		}
		SQLwhere = SQLwhere + "))";
		return SQLwhere;
	}



	static public String SQLwhere_equals_blank_or_partial(DB db, String SQLwhere, String column, String value, String separator) {
		if (SQLwhere.equals("-")) {
			SQLwhere = " ";
		} else if (! SQLwhere.equals("")) {
			SQLwhere += " and ";
		} else {
			SQLwhere = " where ";
		}
		SQLwhere += "(";
		if (db == null) {
			SQLwhere += "(" + column + " is null or " + column + "='')";
		} else if (db.db_type(db.getDatabase()).equals("mssql")) {
			SQLwhere += "(" + column + " is null or substring(" + column + ",1,250)='')";
		} else if (db.db_type(db.getDatabase()).equals("oracle")) {
			SQLwhere += "(" + column + " is null or to_char(" + column + ")='')";
		} else if (db.db_type(db.getDatabase()).equals("db2")) {
			SQLwhere += "(" + column + " is null or varchar(" + column + ",250)='')";
		} else {
			SQLwhere += "(" + column + " is null or " + column + "='')";
		}
		String prefix = "";
		String postfix = "";
		String[] parts = value.split("\\Q" + separator + "\\E");
		for (int i=0; i<parts.length; i++) {
			prefix = prefix + parts[i];
			postfix = parts[parts.length-1-i] + postfix;
			if (i < parts.length-1) {
				prefix = prefix + separator;
				postfix = separator + postfix;
			}
			if (db == null) {
				SQLwhere += " or (" + column + "=" + DB.db_quote(prefix) + ")";
				if (! prefix.equals(postfix)) {
					SQLwhere += " or (" + column + "=" + DB.db_quote(postfix) + ")";
				}
			} else if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQLwhere += " or (substring(" + column + ",1,250)=" + DB.db_quote(prefix) + ")";
				if (! prefix.equals(postfix)) {
					SQLwhere += " or (substring(" + column + ",1,250)=" + DB.db_quote(postfix) + ")";
				}
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQLwhere += " or (to_char(" + column + ")=" + DB.db_quote(prefix) + ")";
				if (! prefix.equals(postfix)) {
					SQLwhere += " or (to_char(" + column + ")=" + DB.db_quote(postfix) + ")";
				}
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQLwhere += " or (varchar(" + column + ",250)=" + DB.db_quote(prefix) + ")";
				if (! prefix.equals(postfix)) {
					SQLwhere += " or (varchar(" + column + ",250)=" + DB.db_quote(postfix) + ")";
				}
			} else {
				SQLwhere += " or (" + column + "=" + DB.db_quote(prefix) + ")";
				if (! prefix.equals(postfix)) {
					SQLwhere += " or (" + column + "=" + DB.db_quote(postfix) + ")";
				}
			}
		}
		SQLwhere += ")";
		return SQLwhere;
	}



	static public String select_options(DB db, String table, String column, String selectedlist) {
		return select_options_where(db, table, column, selectedlist, "");
	}
	static public String select_options(DB db, String table, String column, String valuecolumn, String selectedlist) {
		return select_options_where(db, table, column, valuecolumn, selectedlist, "");
	}



	static public String select_options_not_blank(DB db, String table, String column, String selectedlist) {
		return select_options_where(db, table, column, selectedlist, "where " + db.is_not_blank(column));
	}
	static public String select_options_not_blank(DB db, String table, String column, String valuecolumn, String selectedlist) {
		return select_options_where(db, table, column, valuecolumn, selectedlist, "where " + db.is_not_blank(column));
	}



	static public String select_options_where(DB db, String table, String column, String selectedlist, String SQLwhere) {
		return select_options_where(db, table, column, column, selectedlist, SQLwhere);
	}
	static public String select_options_where(DB db, String table, String column, String valuecolumn, String selectedlist, String SQLwhere) {
		return select_options_where(db, table, column, valuecolumn, selectedlist, SQLwhere, "");
	}
	static public String select_options_where(DB db, String table, String column, String valuecolumn, String selectedlist, String SQLwhere, String idcolumn) {
		if (db == null) return "";
		StringBuffer options = new StringBuffer();
		String[] selected = selectedlist.split("\\|");
//		String SQL = "select distinct " + column + " from " + table + " order by " + column;
// "select distinct column ... order by column" may not work with Microsoft Access - use column number instead
		String SQL = "";
		String SQLcolumns = Common.SQL_clean(column);
		if ((! valuecolumn.equals("")) && (! valuecolumn.equals(column))) {
			SQLcolumns += "," + Common.SQL_clean(valuecolumn);
		}
		if ((! idcolumn.equals("")) && (! idcolumn.equals(column)) && (! idcolumn.equals(valuecolumn))) {
			SQLcolumns += "," + Common.SQL_clean(idcolumn);
		}
		SQL = "select distinct " + SQLcolumns + " from " + Common.SQL_clean(table) + " " + SQLwhere + " order by 1";
		LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
		for (int i=0; i<rows.size(); i++) {
			HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
			String name = "" + row.get(column);
			String value = "" + row.get(valuecolumn);
			options.append("<option value=\"");
			options.append(html.encode(value));
			options.append("\"");
			for (int j=0; j<selected.length; j++) {
				if ((selected[j] != null) && (selected[j].equals(value))) {
					options.append(" selected");
				}
			}
			options.append(">");
			options.append(name);
			if (! idcolumn.equals("")) {
				options.append(" (" + html.encode("" + row.get(idcolumn)) + ")");
			}
		}
		return "" + options;
	}



	static public String select_options_id(DB db, String table, String column, String selectedlist) {
		if (db == null) return "";
		StringBuffer options = new StringBuffer();
		String[] selected = selectedlist.split("\\|");
//		String SQL = "select id, " + column + " from " + table + " order by " + column;
// "select id, column ... order by column" may not work with Microsoft Access - use column number instead
		String SQL = "";
		if (db.db_type(db.getDatabase()).equals("mssql")) {
			SQL = "select id, substring(" + Common.SQL_clean(column) + ",1,250) as " + Common.SQL_clean(column) + " from " + Common.SQL_clean(table) + " order by 2";
		} else if (db.db_type(db.getDatabase()).equals("oracle")) {
			SQL = "select id, to_char(" + Common.SQL_clean(column) + ") as " + Common.SQL_clean(column) + " from " + Common.SQL_clean(table) + " order by 2";
		} else if (db.db_type(db.getDatabase()).equals("db2")) {
			SQL = "select id, varchar(" + Common.SQL_clean(column) + ",250) as " + Common.SQL_clean(column) + " from " + Common.SQL_clean(table) + " order by 2";
		} else {
			SQL = "select id, " + Common.SQL_clean(column) + " from " + Common.SQL_clean(table) + " order by 2";
		}
		LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
		for (int i=0; i<rows.size(); i++) {
			HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
			String name = "" + row.get(column);
			String value = "" + row.get("id");
			options.append("<option value=\"");
			options.append(html.encode(value));
			options.append("\"");
			for (int j=0; j<selected.length; j++) {
				if ((selected[j] != null) && (selected[j].equals(value))) {
					options.append(" selected");
				}
			}
			options.append(">");
			options.append(name + " [" + html.encode(value) + "]");
		}
		return "" + options;
	}



	static public String select_options_id(DB db, String table, String[] columns, String selectedlist, String optionformat) {
		return select_options_id(db, table, columns, selectedlist, optionformat, false);
	}
	static public String select_options_id(DB db, String table, String[] columns, String selectedlist, String optionformat, boolean selectedonly) {
		if (db == null) return "";
		StringBuffer options = new StringBuffer();
		String[] selected = selectedlist.split("\\|");
//		String SQL = "select id, " + column + " from " + table + " order by " + column;
// "select id, column ... order by column" may not work with Microsoft Access - use column number instead
		String SQL = "select distinct id";
		String SQLorder = "";
		for (int c=0; c<columns.length; c++) {
			String column = columns[c];
			if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQL += ", substring(" + Common.SQL_clean(column) + ",1,250) as " + Common.SQL_clean(column);
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQL += ", to_char(" + Common.SQL_clean(column) + ") as " + Common.SQL_clean(column);
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQL += ", varchar(" + Common.SQL_clean(column) + ",250) as " + Common.SQL_clean(column);
			} else {
				SQL += ", " + Common.SQL_clean(column);
			}
			if (! SQLorder.equals("")) SQLorder += ",";
			SQLorder += "" + (c+1);
		}
		SQL += " from " + Common.SQL_clean(table) + " order by " + SQLorder;
		LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
		for (int i=0; i<rows.size(); i++) {
			HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
			String name = "" + optionformat;
			Pattern p = Pattern.compile("@@@([^@]+?)@@@");
			Matcher m = p.matcher(optionformat);
			while (m.find()) {
				String attributename = "" + m.group(1);
				name = name.replaceAll("@@@" + attributename + "@@@", "" + row.get(attributename));
			}
			String value = "" + row.get("id");
			boolean selectedoption = false;
			StringBuffer option = new StringBuffer();
			option.append("<option value=\"");
			option.append(html.encode(value));
			option.append("\"");
			for (int j=0; j<selected.length; j++) {
				if ((selected[j] != null) && (selected[j].equals(value))) {
					option.append(" selected");
					selectedoption = true;
				}
			}
			option.append(">");
			option.append(name + " [" + html.encode(value) + "]");
			if ((! selectedonly) || selectedoption) {
				options.append(option);
			}
		}
		return "" + options;
	}



	static public String select_options_value(DB db, String table, String[] columns, String selectedlist, String optionformat) {
		return select_options_value(db, table, columns, selectedlist, optionformat, false);
	}
	static public String select_options_value(DB db, String table, String[] columns, String selectedlist, String optionformat, boolean selectedonly) {
		if (db == null) return "";
		StringBuffer options = new StringBuffer();
		String[] selected = selectedlist.split("\\|");
//		String SQL = "select id, " + column + " from " + table + " order by " + column;
// "select id, column ... order by column" may not work with Microsoft Access - use column number instead
		String SQL = "select distinct id";
		String SQLorder = "";
		for (int c=0; c<columns.length; c++) {
			String column = columns[c];
			if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQL += ", substring(" + Common.SQL_clean(column) + ",1,250) as " + Common.SQL_clean(column);
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQL += ", to_char(" + Common.SQL_clean(column) + ") as " + Common.SQL_clean(column);
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQL += ", varchar(" + Common.SQL_clean(column) + ",250) as " + Common.SQL_clean(column);
			} else {
				SQL += ", " + Common.SQL_clean(column);
			}
			if (! SQLorder.equals("")) SQLorder += ",";
			SQLorder += "" + (c+1);
		}
		SQL += " from " + Common.SQL_clean(table) + " order by " + SQLorder;
		LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
		for (int i=0; i<rows.size(); i++) {
			HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
			String name = "" + optionformat;
			Pattern p = Pattern.compile("@@@([^@]+?)@@@");
			Matcher m = p.matcher(optionformat);
			while (m.find()) {
				String attributename = "" + m.group(1);
				name = name.replaceAll("@@@" + attributename + "@@@", "" + row.get(attributename));
			}
			String value = "" + name;
			boolean selectedoption = false;
			StringBuffer option = new StringBuffer();
			option.append("<option value=\"");
			option.append(html.encode(value));
			option.append("\"");
			for (int j=0; j<selected.length; j++) {
				if ((selected[j] != null) && (selected[j].equals(value))) {
					option.append(" selected");
					selectedoption = true;
				}
			}
			option.append(">");
			option.append(name);
			if ((! selectedonly) || selectedoption) {
				options.append(option);
			}
		}
		return "" + options;
	}



	static public String select_options_value(DB db, String[] tables, String[] columns, String selectedlist, String optionformat) {
		return select_options_value(db, tables, columns, new HashMap<String,String>(), selectedlist, optionformat, false);
	}
	static public String select_options_value(DB db, String[] tables, String[] columns, String selectedlist, String optionformat, boolean selectedonly) {
		return select_options_value(db, tables, columns, new HashMap<String,String>(), selectedlist, optionformat, false);
	}
	static public String select_options_value(DB db, String[] tables, String[] columns, HashMap<String,String> joincolumns, String selectedlist, String optionformat) {
		return select_options_value(db, tables, columns, joincolumns, selectedlist, optionformat, false);
	}
	static public String select_options_value(DB db, String[] tables, String[] columns, HashMap<String,String> joincolumns, String selectedlist, String optionformat, boolean selectedonly) {
		if (db == null) return "";
		StringBuffer options = new StringBuffer();
		String[] selected = selectedlist.split("\\|");
//		String SQL = "select id, " + column + " from " + table + " order by " + column;
// "select id, column ... order by column" may not work with Microsoft Access - use column number instead
		String SQL = "";
		String SQLorder = "";
		for (int c=0; c<columns.length; c++) {
			if (! SQL.equals("")) SQL += ", ";
			String column = columns[c];
			if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQL += "substring(" + Common.SQL_clean(column) + ",1,250) as " + Common.SQL_clean(column.replaceAll("\\.", "_"));
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQL += "to_char(" + Common.SQL_clean(column) + ") as " + Common.SQL_clean(column.replaceAll("\\.", "_"));
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQL += "varchar(" + Common.SQL_clean(column) + ",250) as " + Common.SQL_clean(column.replaceAll("\\.", "_"));
			} else {
				SQL += "" + Common.SQL_clean(column) + " as " + Common.SQL_clean(column.replaceAll("\\.", "_"));
			}
			if (! SQLorder.equals("")) SQLorder += ",";
			SQLorder += "" + (c+1);
		}
		String SQLwhere = "";
		Iterator jc = joincolumns.keySet().iterator();
		while (jc.hasNext()) {
			String column1 = "" + jc.next();
			String column2 = "" + joincolumns.get(column1);
			if (! SQLwhere.equals("")) SQLwhere += " and ";
			SQLwhere += "(" + column1 + "=" + column2 + ")";
		}
		if (! SQLwhere.equals("")) SQLwhere = " where " + SQLwhere;
		if (! SQL.equals("")) {
			SQL = "select distinct " + SQL + " from " + Common.SQL_clean(Common.join(",", tables)) + SQLwhere + " order by " + SQLorder;
		}
		LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
		for (int i=0; i<rows.size(); i++) {
			HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
			String name = "" + optionformat;
			Pattern p = Pattern.compile("@@@([^@]+?)@@@");
			Matcher m = p.matcher(optionformat);
			while (m.find()) {
				String attributename = "" + m.group(1);
				name = name.replaceAll("@@@" + attributename + "@@@", "" + row.get(attributename.replaceAll("\\.", "_")));
			}
			String value = "" + name;
			boolean selectedoption = false;
			StringBuffer option = new StringBuffer();
			option.append("<option value=\"");
			option.append(html.encode(value.replaceAll("\\|", "/")));
			option.append("\"");
			for (int j=0; j<selected.length; j++) {
				if ((selected[j] != null) && (selected[j].equals(value))) {
					option.append(" selected");
					selectedoption = true;
				}
			}
			option.append(">");
			option.append(name);
			if ((! selectedonly) || selectedoption) {
				options.append(option);
			}
		}
		return "" + options;
	}



	static public String output_column_X2(DB db, String table, String column, String pre_output, String mid_output, String post_output) {
		if (db == null) return "";
		StringBuffer output = new StringBuffer();
		String SQL = "select " + Common.SQL_clean(column) + " from " + Common.SQL_clean(table) + " order by " + Common.SQL_clean(column);
		LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
		for (int i=0; i<rows.size(); i++) {
			HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
			String value = "" + row.get(column);
			output.append(pre_output + value.replaceAll("'", "\\\\'").replaceAll("\"", "\\\\\"") + mid_output + value.replaceAll("'", "\\\\\\\\\\\\'").replaceAll("\"", "%22") + post_output);
		}
		return "" + output;
	}



	static public String csv(DB db, String table, String column) {
		return csv(db, table, column, column);
	}
	static public String csv(DB db, String table, String column, String orderby) {
		return csv(db, table, column, orderby, "");
	}
	static public String csv(DB db, String table, String column, String orderby, String where) {
		if (db == null) return "";
		String output = "";
		String SQL = "select " + Common.SQL_clean(column) + " from " + Common.SQL_clean(table) + " " + where + " order by " + Common.SQL_clean(orderby);
		LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
		for (int i=0; i<rows.size(); i++) {
			HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
			String value = "" + row.get(column);
			if (! output.equals("")) output += ",";
			output += value;
		}
		return "" + output;
	}



	static public String html_javascript_specialcharacters(String content) {
		content = content.replaceAll("\\\\", "\\\\\\\\");
		content = content.replaceAll("'", "\\\\'");
		content = content.replaceAll("\r", "\\\\r");
		content = content.replaceAll("\n", "\\\\n");
		content = content.replaceAll("\u0080", "&euro;");
		content = content.replaceAll("\u0081", "&#129;"); // ???
		content = content.replaceAll("\u0082", "&#130;"); // comma
		content = content.replaceAll("\u0083", "&#131;"); // florin
		content = content.replaceAll("\u0084", "&#132;"); // right double quote
		content = content.replaceAll("\u0085", "&#133;"); // elipsis
		content = content.replaceAll("\u0086", "&#134;"); // dagger
		content = content.replaceAll("\u0087", "&#135;"); // double dagger
		content = content.replaceAll("\u0088", "&#136;"); // circumflex
		content = content.replaceAll("\u0089", "&#137;"); // permil
		content = content.replaceAll("\u008a", "&#138;"); // underscore
		content = content.replaceAll("\u008b", "&#139;"); // less than
		content = content.replaceAll("\u008c", "&#140;"); // capital OE ligature
		content = content.replaceAll("\u008d", "&#141;"); // ???
		content = content.replaceAll("\u008e", "&#142;"); // ???
		content = content.replaceAll("\u008f", "&#143;"); // ???
		content = content.replaceAll("\u0090", "&#144;"); // ???
		content = content.replaceAll("\u0091", "&#145;"); // left single quote
		content = content.replaceAll("\u0092", "&#146;"); // right single quote
		content = content.replaceAll("\u0093", "&#147;"); // left double quote
		content = content.replaceAll("\u0094", "&#148;"); // right double quote
		content = content.replaceAll("\u0095", "&#149;"); // bullet
		content = content.replaceAll("\u0096", "&#150;"); // en dash
		content = content.replaceAll("\u0097", "&#151;"); // em dash
		content = content.replaceAll("\u0098", "&#152;"); // tilde
		content = content.replaceAll("\u0099", "&#153;"); // tm
		content = content.replaceAll("\u009a", "&#154;"); // underscore
		content = content.replaceAll("\u009b", "&#155;"); // greater than
		content = content.replaceAll("\u009c", "&#156;"); // lowercase oe ligature
		content = content.replaceAll("\u009d", "&#157;"); // ???
		content = content.replaceAll("\u009e", "&#158;"); // ???
		content = content.replaceAll("\u009f", "&#159;"); // capital Y umlaut
		content = content.replaceAll("\u00a0", "&nbsp;");
		content = content.replaceAll("\u00a1", "&iexcl;");
		content = content.replaceAll("\u00a2", "&cent;");
		content = content.replaceAll("\u00a3", "&pound;");
		content = content.replaceAll("\u00a4", "&curren;");
		content = content.replaceAll("\u00a5", "&yen;");
		content = content.replaceAll("\u00a6", "&brvbar;");
		content = content.replaceAll("\u00a7", "&sect;");
		content = content.replaceAll("\u00a8", "&uml;");
		content = content.replaceAll("\u00a9", "&copy;");
		content = content.replaceAll("\u00aa", "&ordf;");
		content = content.replaceAll("\u00ab", "&laquo;");
		content = content.replaceAll("\u00ac", "&not;");
		content = content.replaceAll("\u00ad", "&shy;");
		content = content.replaceAll("\u00ae", "&reg;");
		content = content.replaceAll("\u00af", "&macr;");
		content = content.replaceAll("\u00b0", "&deg;");
		content = content.replaceAll("\u00b1", "&plusmn;");
		content = content.replaceAll("\u00b2", "&sup2;");
		content = content.replaceAll("\u00b3", "&sup3;");
		content = content.replaceAll("\u00b4", "&acute;");
		content = content.replaceAll("\u00b5", "&micro;");
		content = content.replaceAll("\u00b6", "&para;");
		content = content.replaceAll("\u00b7", "&middot;");
		content = content.replaceAll("\u00b8", "&cedil;");
		content = content.replaceAll("\u00b9", "&sup1;");
		content = content.replaceAll("\u00ba", "&ordm;");
		content = content.replaceAll("\u00bb", "&raquo;");
		content = content.replaceAll("\u00bc", "&frac14;");
		content = content.replaceAll("\u00bd", "&frac12;");
		content = content.replaceAll("\u00be", "&frac34;");
		content = content.replaceAll("\u00bf", "&iquest;");
		content = content.replaceAll("\u00c0", "&Agrave;");
		content = content.replaceAll("\u00c1", "&Aacute;");
		content = content.replaceAll("\u00c2", "&Acirc;");
		content = content.replaceAll("\u00c3", "&Atilde;");
		content = content.replaceAll("\u00c4", "&Auml;");
		content = content.replaceAll("\u00c5", "&Aring;");
		content = content.replaceAll("\u00c6", "&AElig;");
		content = content.replaceAll("\u00c7", "&Ccedil;");
		content = content.replaceAll("\u00c8", "&Egrave;");
		content = content.replaceAll("\u00c9", "&Eacute;");
		content = content.replaceAll("\u00ca", "&Ecirc;");
		content = content.replaceAll("\u00cb", "&Euml;");
		content = content.replaceAll("\u00cc", "&Igrave;");
		content = content.replaceAll("\u00cd", "&Iacute;");
		content = content.replaceAll("\u00ce", "&Icirc;");
		content = content.replaceAll("\u00cf", "&Iuml;");
		content = content.replaceAll("\u00d0", "&ETH;");
		content = content.replaceAll("\u00d1", "&Ntilde;");
		content = content.replaceAll("\u00d2", "&Ograve;");
		content = content.replaceAll("\u00d3", "&Oacute;");
		content = content.replaceAll("\u00d4", "&Ocirc;");
		content = content.replaceAll("\u00d5", "&Otilde;");
		content = content.replaceAll("\u00d6", "&Ouml;");
		content = content.replaceAll("\u00d7", "&times;");
		content = content.replaceAll("\u00d8", "&Oslash;");
		content = content.replaceAll("\u00d9", "&Ugrave;");
		content = content.replaceAll("\u00da", "&Uacute;");
		content = content.replaceAll("\u00db", "&Ucirc;");
		content = content.replaceAll("\u00dc", "&Uuml;");
		content = content.replaceAll("\u00dd", "&Yacute;");
		content = content.replaceAll("\u00de", "&THORN;");
		content = content.replaceAll("\u00df", "&szlig;");
		content = content.replaceAll("\u00e0", "&agrave;");
		content = content.replaceAll("\u00e1", "&aacute;");
		content = content.replaceAll("\u00e2", "&acirc;");
		content = content.replaceAll("\u00e3", "&atilde;");
		content = content.replaceAll("\u00e4", "&auml;");
		content = content.replaceAll("\u00e5", "&aring;");
		content = content.replaceAll("\u00e6", "&aelig;");
		content = content.replaceAll("\u00e7", "&ccedil;");
		content = content.replaceAll("\u00e8", "&egrave;");
		content = content.replaceAll("\u00e9", "&eacute;");
		content = content.replaceAll("\u00ea", "&ecirc;");
		content = content.replaceAll("\u00eb", "&euml;");
		content = content.replaceAll("\u00ec", "&igrave;");
		content = content.replaceAll("\u00ed", "&iacute;");
		content = content.replaceAll("\u00ee", "&icirc;");
		content = content.replaceAll("\u00ef", "&iuml;");
		content = content.replaceAll("\u00f0", "&eth;");
		content = content.replaceAll("\u00f1", "&ntilde;");
		content = content.replaceAll("\u00f2", "&ograve;");
		content = content.replaceAll("\u00f3", "&oacute;");
		content = content.replaceAll("\u00f4", "&ocirc;");
		content = content.replaceAll("\u00f5", "&otilde;");
		content = content.replaceAll("\u00f6", "&ouml;");
		content = content.replaceAll("\u00f7", "&divide;");
		content = content.replaceAll("\u00f8", "&oslash;");
		content = content.replaceAll("\u00f9", "&ugrave;");
		content = content.replaceAll("\u00fa", "&uacute;");
		content = content.replaceAll("\u00fb", "&ucirc;");
		content = content.replaceAll("\u00fc", "&uuml;");
		content = content.replaceAll("\u00fd", "&yacute;");
		content = content.replaceAll("\u00fe", "&thorn;");
		content = content.replaceAll("\u00ff", "&yuml;");
		return "" + content;
	}



	static public String numberformat(double value, int decimals) {
		return numberformat("" + value, decimals, 1);
	}
	static public String numberformat(float value, int decimals) {
		return numberformat("" + value, decimals, 1);
	}
	static public String numberformat(String value, int decimals) {
		return numberformat(value, decimals, 1);
	}
	static public String numberformat(String value, int decimals, int integers) {
		if ((value == null) || (value.equals("null")) || (value.equals("")) || (value.equals("0null"))) {
			value = "0";
		}
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		format.setMinimumIntegerDigits(integers);
		format.setMinimumFractionDigits(decimals);
		format.setMaximumFractionDigits(decimals);
		return format.format(number(value));
	}
	static public String numberformat(double value, int decimals, boolean grouping) {
		return numberformat("" + value, decimals, grouping);
	}
	static public String numberformat(float value, int decimals, boolean grouping) {
		return numberformat("" + value, decimals, grouping);
	}
	static public String numberformat(String value, int decimals, boolean grouping) {
		if ((value == null) || (value.equals("null")) || (value.equals("")) || (value.equals("0null"))) {
			value = "0";
		}
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(grouping);
		format.setMinimumIntegerDigits(1);
		format.setMinimumFractionDigits(decimals);
		format.setMaximumFractionDigits(decimals);
		return format.format(number(value));
	}



	static public int parse_int(Object value) {
		if (value == null) {
			return 0;
		} else if (("" + value).startsWith("-")) {
			return Integer.parseInt("" + value);
		} else {
			return Integer.parseInt("0" + value);
		}
	}



	static public long parse_long(Object value) {
		if (value == null) {
			return 0;
		} else if (("" + value).startsWith("-")) {
			return Long.parseLong("" + value);
		} else {
			return Long.parseLong("0" + value);
		}
	}



	static public float parse_float(Object value) {
		if (value == null) {
			return 0;
		} else if (("" + value).startsWith("-")) {
			return Float.parseFloat("" + value);
		} else {
			return Float.parseFloat("0" + value);
		}
	}



	static public double parse_double(Object value) {
		if (value == null) {
			return 0;
		} else if (("" + value).startsWith("-")) {
			return Double.parseDouble("" + value);
		} else {
			return Double.parseDouble("0" + value);
		}
	}



	static public boolean numeric(String stringvalue) {
		return stringvalue.matches("^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$");
	}



	static public double number(Object stringvalue) {
		if (stringvalue == null) return 0;
		try {
			return Double.parseDouble("" + NumberFormat.getInstance().parse("" + stringvalue));
		} catch (Exception e) {
			return 0;
		}
	}
	static public double number(String stringvalue) {
		if (stringvalue == null) return 0;
		if (stringvalue.equals("")) return 0;
		try {
			return Double.parseDouble("" + NumberFormat.getInstance().parse("" + stringvalue));
		} catch (NumberFormatException e) {
			return 0;
		} catch (ParseException e) {
			return 0;
		}
	}



	static public long integernumber(Object stringvalue) {
		if (stringvalue == null) return 0;
		try {
			return Long.parseLong("" + NumberFormat.getInstance().parse("" + stringvalue));
		} catch (NumberFormatException e) {
			return 0;
		} catch (ParseException e) {
			return 0;
		}
	}
	static public long integernumber(String stringvalue) {
		if (stringvalue == null) return 0;
		if (stringvalue.equals("")) return 0;
		try {
			return Long.parseLong("" + NumberFormat.getInstance().parse("" + stringvalue));
		} catch (NumberFormatException e) {
			return 0;
		} catch (ParseException e) {
			return 0;
		}
	}



	static public int intnumber(Object stringvalue) {
		if (stringvalue == null) return 0;
		try {
			return Integer.parseInt("" + NumberFormat.getInstance().parse("" + stringvalue));
		} catch (NumberFormatException e) {
			return 0;
		} catch (ParseException e) {
			return 0;
		}
	}
	static public int intnumber(String stringvalue) {
		if (stringvalue == null) return 0;
		if (stringvalue.equals("")) return 0;
		try {
			return Integer.parseInt("" + NumberFormat.getInstance().parse("" + stringvalue));
		} catch (NumberFormatException e) {
			return 0;
		} catch (ParseException e) {
			return 0;
		}
	}



	static public void createFolder(String filepath) {
		filepath = filepath.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		if (filepath.lastIndexOf("/")>=0) {
			String folderpath = filepath.substring(0,filepath.lastIndexOf("/"));
			File dir = new File(folderpath);
			if (! dir.exists()) {
				dir.mkdirs();
			}
		}
	}



	static public void copyFolder(String source, String destination) {
		source = source.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		destination = destination.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		File dir = new File(source);
		if (dir.exists()) {
			String[] items = dir.list();
			if (items != null) {
				for (int i=0; i<items.length; i++) {
					File item = new File(source + "/" + items[i]);
					if ((items[i].equals(".")) || (items[i].equals(".."))) {
						// ignore
					} else if (item.isFile()) {
						new File(destination).mkdirs();
						copyFile(source + "/" + items[i], destination + "/" + items[i]);
					} else if (item.isDirectory()) {
						new File(destination + "/" + items[i]).mkdirs();
						copyFolder(source + "/" + items[i], destination + "/" + items[i]);
					}
				}
			}
		}
	}



	static public void deleteFolder(String folder) throws Exception {
		deleteFolder(folder, true, true);
	}
	static public void deleteFolder(String folder, boolean recursively) throws Exception {
		deleteFolder(folder, recursively, true);
	}
	static public void deleteFolder(String folder, boolean recursively, boolean prune) throws Exception {
		folder = folder.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		File dir = new File(folder);
		if (dir.exists() && dir.isDirectory() && recursively) {
			String[] items = dir.list();
			for (int i=0; i<items.length; i++) {
				File item = new File(folder + "/" + items[i]);
				if ((items[i].equals(".")) || (items[i].equals(".."))) {
					// ignore
				} else if (item.isFile()) {
					deleteFile(folder + "/" + items[i]);
				} else if (item.isDirectory()) {
					deleteFolder(folder + "/" + items[i], recursively);
				}
			}
		}
		try {
			if (dir.exists()) dir.delete();
		} catch (Exception e) {
		}
		if (prune) {
			pruneFolder(folder);
		}
	}



	static public void pruneFolder(String folder) throws Exception {
		folder = folder.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		File dir = new File(folder);
		if (folder.lastIndexOf("/")>=0) {
			folder = folder.substring(0, folder.lastIndexOf("/"));
			dir = new File(folder);
			if (dir.exists() && dir.isDirectory()) {
				boolean empty = true;
				String[] items = dir.list();
				for (int i=0; i<items.length; i++) {
					File item = new File(folder + "/" + items[i]);
					if ((items[i].equals(".")) || (items[i].equals(".."))) {
						// ignore
					} else if (item.isFile()) {
						empty = false;
						break;
					} else if (item.isDirectory()) {
						empty = false;
						break;
					}
				}
				if (empty) {
					deleteFolder(folder, false, true);
				}
			}
		}
	}



	static public String listFolder(String folder) {
		folder = folder.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		String output = "";
		File dir = new File(folder);
		if (dir.exists()) {
			String[] items = dir.list();
			for (int i=0; i<items.length; i++) {
				File item = new File(folder + "/" + items[i]);
				if ((items[i].equals(".")) || (items[i].equals(".."))) {
					// ignore
				} else if (item.isFile()) {
					output += items[i] + "\r\n";
				} else if (item.isDirectory()) {
					output += items[i] + "/" + "\r\n";
				}
			}
		}
		return output;
	}



	static public String listFolders(String folder) {
		folder = folder.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		String output = "";
		File dir = new File(folder);
		if (dir.exists()) {
			String[] items = dir.list();
			for (int i=0; i<items.length; i++) {
				File item = new File(folder + "/" + items[i]);
				if ((items[i].equals(".")) || (items[i].equals(".."))) {
					// ignore
				} else if (item.isFile()) {
					// ignore
				} else if (item.isDirectory()) {
					output += items[i] + "/" + "\r\n";
				}
			}
		}
		return output;
	}



	static public String listFiles(String folder) {
		folder = folder.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		String output = "";
		File dir = new File(folder);
		if (dir.exists()) {
			String[] items = dir.list();
			for (int i=0; i<items.length; i++) {
				File item = new File(folder + "/" + items[i]);
				if ((items[i].equals(".")) || (items[i].equals(".."))) {
					// ignore
				} else if (item.isFile()) {
					output += items[i] + "\r\n";
				} else if (item.isDirectory()) {
					// ignore
				}
			}
		}
		return output;
	}



	static public boolean folderExists(String filename) {
		filename = filename.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		File fh = new File(filename);
		if (fh.exists() && fh.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}
	static public boolean folderExists(String filename, Text text, ServletContext myserver, DB db, Configuration myconfig, Session mysession, Request myrequest, Response myresponse) {
		filename = filename.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		File fh = new File(filename);
		if (fh.exists() && fh.isDirectory()) {
			return true;
		} else {
			filename = filename.replaceAll("^\\Q" + Common.getRealPath(myserver, "/") + "\\E", "");
			filename = filename.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
			String exists = Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/exists"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + filename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:folderExists1\"", "", myserver, mysession, myrequest, myresponse);
			if (exists.equals("folder")) {
				return true;
			} else if (exists.equals("true")) {
				return true;
			} else {
				return false;
			}
		}
	}



	static public boolean fileExists(String filename) {
		filename = filename.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		File fh = new File(filename);
		if (fh.exists() && fh.isFile()) {
			return true;
		} else {
			return false;
		}
	}
	static public boolean fileExists(String filename, Text text, ServletContext myserver, DB db, Configuration myconfig, Session mysession, Request myrequest, Response myresponse) {
		filename = filename.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		File fh = new File(filename);
		if (fh.exists() && fh.isFile()) {
			return true;
		} else {
			filename = filename.replaceAll("^\\Q" + Common.getRealPath(myserver, "/") + "\\E", "");
			filename = filename.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
			String exists = Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/exists"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + filename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:fileExists1\"", "", myserver, mysession, myrequest, myresponse);
			if (exists.equals("file")) {
				return true;
			} else if (exists.equals("true")) {
				return true;
			} else {
				return false;
			}
		}
	}



	static public boolean fileOrFolderExists(String filename) {
		filename = filename.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		File fh = new File(filename);
		if (fh.exists()) {
			return true;
		} else {
			return false;
		}
	}
	static public boolean fileOrFolderExists(String filename, Text text, ServletContext myserver, DB db, Configuration myconfig, Session mysession, Request myrequest, Response myresponse) {
		filename = filename.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		File fh = new File(filename);
		if (fh.exists()) {
			return true;
		} else {
			filename = filename.replaceAll("^\\Q" + Common.getRealPath(myserver, "/") + "\\E", "");
			filename = filename.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
			String exists = Common.executeFile(Common.getRealPath(myserver, "/" + text.display("adminpath") + "/api/exists"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + filename + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:fileOrFolderExists1\"", "", myserver, mysession.getSession(), myrequest.getRequest(), myresponse.getResponse());
			if (! exists.equals("")) {
				return true;
			} else {
				return false;
			}
		}
	}



	static public String fileLastModified(String filename) {
		filename = filename.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		File fh = new File(filename);
		if (fh.exists() && fh.isFile()) {
			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(fh.lastModified()));
			return timestamp;
		} else {
			return "";
		}
	}



	static public String executeFile(String filename, String parameters) {
		return executeFile(filename, parameters, "", null, (HttpSession)null, (HttpServletRequest)null, (HttpServletResponse)null);
	}
	static public String executeFile(String filename, String parameters, String defaultoutput) {
		return executeFile(filename, parameters, defaultoutput, null, (HttpSession)null, (HttpServletRequest)null, (HttpServletResponse)null);
	}
	static public String executeFile(String filename, String parameters, String defaultoutput, ServletContext server, Session session, Request request, Response response) {
		return executeFile(filename, parameters, defaultoutput, server, session.getSession(), request.getRequest(), response.getResponse());
}
	static public String executeFile(String filename, String parameters, String defaultoutput, ServletContext server, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		filename = filename.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		String output = "";
		File fh = new File(filename);
		if (fh.exists()) {
			defaultoutput = "";
			try {
				Runtime rt = Runtime.getRuntime();
//				Process ps = rt.exec("\"" + filename + "\" " + parameters);
				String[] myparameters = parameters.replaceAll("^\"", "").replaceAll("\"$", "").split("\" \"");
				String[] cmd = new String[1 + myparameters.length];
				cmd[0] = filename;
				for (int i=0; i<myparameters.length; i++) {
					cmd[1+i] = myparameters[i];
				}
				Process ps = rt.exec(cmd);
				int rc = ps.waitFor();
				BufferedReader is  = new BufferedReader(new InputStreamReader(ps.getInputStream()));
				BufferedReader es  = new BufferedReader(new InputStreamReader(ps.getErrorStream()));
				String myline = "";
				while ((myline = is.readLine()) != null) {
					output += myline;
				}
				String myerror = "";
				while ((myline = es.readLine()) != null) {
					myerror += myline;
				}
				if (! myerror.equals("")) {
					System.out.println("Hardcore/Common.executeFile:\"" + filename + "\" " + parameters + " - " + myerror);
				}
			} catch (IOException e) {
				System.out.println("Hardcore/Common.executeFile:IOException:\"" + filename + "\" " + parameters + " - " + e);
			} catch (InterruptedException e) {
				System.out.println("Hardcore/Common.executeFile:InterruptedException:\"" + filename + "\" " + parameters + " - " + e);
			}
		}
		fh = new File(filename + ".jsp");
		if (fh.exists() && (server != null) && (session != null) && (request != null) && (response != null)) {
			defaultoutput = "";
			output = Common.execute(filename + ".jsp", "parameters", parameters, null, server, session, request, response);
		}
		fh = new File(filename + ".bat");
		if (fh.exists()) {
			defaultoutput = "";
			try {
				Runtime rt = Runtime.getRuntime();
//				Process ps = rt.exec("cmd.exe /c \"\"" + filename + ".bat\" " + parameters + "\"");
				String[] myparameters = parameters.replaceAll("^\"", "").replaceAll("\"$", "").split("\" \"");
				String[] cmd = new String[3 + myparameters.length];
				cmd[0] = "cmd.exe" ;
				cmd[1] = "/c" ;
				cmd[2] = filename + ".bat";
				for (int i=0; i<myparameters.length; i++) {
					cmd[3+i] = myparameters[i];
				}
				Process ps = rt.exec(cmd);
				int rc = ps.waitFor();
				BufferedReader is  = new BufferedReader(new InputStreamReader(ps.getInputStream()));
				BufferedReader es  = new BufferedReader(new InputStreamReader(ps.getErrorStream()));
				String myline = "";
				while ((myline = is.readLine()) != null) {
					output += myline;
				}
				String myerror = "";
				while ((myline = es.readLine()) != null) {
					myerror += myline;
				}
				if (! myerror.equals("")) {
					System.out.println("Hardcore/Common.executeFile:cmd.exe /c \"\"" + filename + ".bat\" " + parameters + "\" - " + myerror);
				}
			} catch (IOException e) {
				System.out.println("Hardcore/Common.executeFile:IOException:cmd.exe /c \"\"" + filename + ".bat\" " + parameters + "\" - " + e);
			} catch (InterruptedException e) {
				System.out.println("Hardcore/Common.executeFile:InterruptedException:cmd.exe /c \"\"" + filename + ".bat\" " + parameters + "\" - " + e);
			}
		}
		fh = new File(filename + ".sh");
		if (fh.exists()) {
			defaultoutput = "";
			try {
				Runtime rt = Runtime.getRuntime();
//				Process ps = rt.exec("/bin/sh \"" + filename + ".sh\" " + parameters);
				String[] myparameters = parameters.replaceAll("^\"", "").replaceAll("\"$", "").split("\" \"");
				String[] cmd = new String[2 + myparameters.length];
				cmd[0] = "/bin/sh" ;
				cmd[1] = filename + ".sh";
				for (int i=0; i<myparameters.length; i++) {
					cmd[2+i] = myparameters[i];
				}
				Process ps = rt.exec(cmd);
				int rc = ps.waitFor();
				BufferedReader is  = new BufferedReader(new InputStreamReader(ps.getInputStream()));
				BufferedReader es  = new BufferedReader(new InputStreamReader(ps.getErrorStream()));
				String myline = "";
				while ((myline = is.readLine()) != null) {
					output += myline;
				}
				String myerror = "";
				while ((myline = es.readLine()) != null) {
					myerror += myline;
				}
				if (! myerror.equals("")) {
					System.out.println("Hardcore/Common.executeFile:/bin/sh \"" + filename + ".sh\" " + parameters + " - " + myerror);
				}
			} catch (IOException e) {
				System.out.println("Hardcore/Common.executeFile:IOException:/bin/sh \"\"" + filename + ".sh\" " + parameters + "\" - " + e);
			} catch (InterruptedException e) {
				System.out.println("Hardcore/Common.executeFile:InterruptedException:/bin/sh \"\"" + filename + ".sh\" " + parameters + "\" - " + e);
			}
		}
		if (output.equals("")) {
			return defaultoutput;
		} else {
			return output;
		}
	}



	static public String readFile(String filename) {
		filename = filename.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		String content = "";
		File fh = new File(filename);
		if (fh.exists()) {
			BufferedReader input = null;
			try {
				input = new BufferedReader(new FileReader(filename));
				String my_line;
				while ((my_line = input.readLine()) != null) {
					content = content + my_line + "\r\n";
				}
				input.close();
			} catch (FileNotFoundException e) {
				if (input != null) try { input.close(); } catch (IOException ee) { ; }
			} catch (IOException e) {
				if (input != null) try { input.close(); } catch (IOException ee) { ; }
			}
		}
		return content;
	}
	static public String readFile(String filename, String encoding) {
		filename = filename.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		String content = "";
		File fh = new File(filename);
		if (fh.exists()) {
			BufferedReader input = null;
			try {
				if ((encoding == null) || (encoding.equals(""))) {
					input = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
				} else {
					input = new BufferedReader(new InputStreamReader(new FileInputStream(filename), encoding));
				}
				String my_line;
				while ((my_line = input.readLine()) != null) {
					content = content + my_line + "\r\n";
				}
				input.close();
			} catch (FileNotFoundException e) {
				if (input != null) try { input.close(); } catch (IOException ee) { ; }
			} catch (IOException e) {
				if (input != null) try { input.close(); } catch (IOException ee) { ; }
			}
		}
		return content;
	}



	static public void writeFile(String filename, String content) {
		filename = filename.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		PrintWriter output = null;
		try {
			createFolder(filename);
			FileOutputStream foslock = new FileOutputStream(filename + ".lock");
			FileLock filelock = null;
			try {
				filelock = foslock.getChannel().lock();
			} catch (IOException e) {
			}
			output = new PrintWriter(new FileOutputStream(filename));
			output.print(content);
			output.close();
			if (filelock != null) filelock.release();
			foslock.close();
			File fhlock = new File(filename + ".lock");
			if (fhlock.exists()) fhlock.delete();
		} catch (FileNotFoundException e) {
			if (output != null) output.close();
		} catch (IOException e) {
			if (output != null) output.close();
		}
	}
	static public void writeFile(String filename, String content, String encoding) {
		filename = filename.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		BufferedWriter output = null;
		try {
			createFolder(filename);
			FileOutputStream foslock = new FileOutputStream(filename + ".lock");
			FileLock filelock = null;
			try {
				filelock = foslock.getChannel().lock();
			} catch (IOException e) {
			}
			if ((encoding == null) || (encoding.equals(""))) {
				output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
			} else {
				output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), encoding));
			}
			output.write(content);
			output.close();
			if (filelock != null) filelock.release();
			foslock.close();
			File fhlock = new File(filename + ".lock");
			if (fhlock.exists()) fhlock.delete();
		} catch (FileNotFoundException e) {
			if (output != null) try { output.close(); } catch (IOException ee) { ; }
		} catch (IOException e) {
			if (output != null) try { output.close(); } catch (IOException ee) { ; }
		}
	}



	static public boolean copyFile(String source, String destination) {
		source = source.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		destination = destination.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		File fh = new File(source);
		if (fh.exists()) {
			createFolder(destination);
			BufferedInputStream is = null;
			BufferedOutputStream os = null;
			try {
				is = new BufferedInputStream(new FileInputStream(source));
				os = new BufferedOutputStream(new FileOutputStream(destination));
				int b;
				while ((b = is.read()) != -1) {
					os.write(b);
				}
				is.close();
				os.close();
				return true;
			} catch (FileNotFoundException e) {
				if (is != null) try { is.close(); } catch (IOException ee) { ; }
				if (os != null) try { os.close(); } catch (IOException ee) { ; }
				return false;
			} catch (IOException e) {
				if (is != null) try { is.close(); } catch (IOException ee) { ; }
				if (os != null) try { os.close(); } catch (IOException ee) { ; }
				return false;
			}
		} else {
			return false;
		}
	}



	static public boolean moveFile(String source, String destination) throws Exception {
		source = source.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		destination = destination.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		File fhs = new File(source);
		File fhd = new File(destination);
		if ((fhs.exists()) && (! fhd.exists())) {
			createFolder(destination);
			fhs.renameTo(fhd);
			pruneFolder(source);
			return ((! fhs.exists()) && (fhd.exists()));
		} else {
			return false;
		}
	}



	static public boolean deleteFile(String filename) throws Exception {
		filename = filename.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		File fh = new File(filename);
		if (fh.exists()) fh.delete();
		pruneFolder(filename);
		return (! fh.exists());
	}



	static public String findUniqueFilename(String URLrootpath, String server_filename) {
		return findUniqueFilename(URLrootpath, server_filename, "file", "txt");
	}
	static public String findUniqueFilename(String URLrootpath, String server_filename, String default_basefilename, String default_filenameextension) {
		return findUniqueFilename(URLrootpath, server_filename, "file", "txt", null, null, null, null, null, null, null);
	}
	static public String findUniqueFilename(String URLrootpath, String server_filename, Text text, ServletContext myserver, DB db, Configuration myconfig, Session mysession, Request myrequest, Response myresponse) {
		return findUniqueFilename(URLrootpath, server_filename, "file", "txt", text, myserver, db, myconfig, mysession, myrequest, myresponse);
	}
	static public String findUniqueFilename(String URLrootpath, String server_filename, String default_basefilename, String default_filenameextension, Text text, ServletContext myserver, DB db, Configuration myconfig, Session mysession, Request myrequest, Response myresponse) {
		String filenameextensionparts[] = default_filenameextension.split("\\.");
		if (filenameextensionparts.length > 0) {
			default_filenameextension = filenameextensionparts[filenameextensionparts.length-1];
		}
		if (folderExists(URLrootpath + server_filename, text, myserver, db, myconfig, mysession, myrequest, myresponse)) {
			if ((! server_filename.endsWith("/")) && (! server_filename.endsWith("\\"))) {
				server_filename += "/";
			}
			server_filename += default_basefilename + "." + default_filenameextension;
		}
		if (fileOrFolderExists((URLrootpath + server_filename).replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/"), text, myserver, db, myconfig, mysession, myrequest, myresponse) || db.query("select id from content where server_filename=" + db.quote(server_filename)) || db.query("select id from content_public where server_filename=" + db.quote(server_filename)) || db.query("select id from content_archive where server_filename=" + db.quote(server_filename))) {
			String filenameparts[] = server_filename.split("\\.");
			String filenameextension = "";
			String basefilename = "";
			if (filenameparts.length > 1) {
				basefilename = filenameparts[0];
				filenameextension = filenameparts[filenameparts.length-1];
			} else {
				basefilename = filenameparts[0];
				filenameextension = default_filenameextension;
			}
			int j;
			if (1 <= filenameparts.length-2) {
				for (j=1; j<=(filenameparts.length-2); j++) {
					basefilename = basefilename + "." + filenameparts[j];
				}
			}
			j = 1;
			while (fileOrFolderExists((URLrootpath + basefilename + "_" + j + "." + filenameextension).replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/"), text, myserver, db, myconfig, mysession, myrequest, myresponse) || db.query("select id from content where server_filename=" + db.quote(basefilename + "_" + j + "." + filenameextension)) || db.query("select id from content_public where server_filename=" + db.quote(basefilename + "_" + j + "." + filenameextension)) || db.query("select id from content_archive where server_filename=" + db.quote(basefilename + "_" + j + "." + filenameextension))) {
				j = j+1;
			}
			server_filename = basefilename + "_" + j + "." + filenameextension;
		}
		return server_filename;
	}



	static public String DateFormat(String format, long datetime) {
		String myformat = sprintf(format, new String[] { "" + datetime } );
		SimpleDateFormat formatter = new SimpleDateFormat(myformat);
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		return formatter.format(new java.util.Date(datetime*1000));
	}



	static public String sprintf(String format, String[] param) {
		NumberFormat number04d = NumberFormat.getInstance();
		number04d.setGroupingUsed(false);
		number04d.setMinimumIntegerDigits(4);
		number04d.setMinimumFractionDigits(0);
		number04d.setMaximumFractionDigits(0);
		NumberFormat number02d = NumberFormat.getInstance();
		number02d.setGroupingUsed(false);
		number02d.setMinimumIntegerDigits(2);
		number02d.setMinimumFractionDigits(0);
		number02d.setMaximumFractionDigits(0);
		NumberFormat number01d = NumberFormat.getInstance();
		number01d.setGroupingUsed(false);
		number01d.setMinimumIntegerDigits(1);
		number01d.setMinimumFractionDigits(0);
		number01d.setMaximumFractionDigits(0);
		NumberFormat number11f = NumberFormat.getInstance();
		number11f.setGroupingUsed(false);
		number11f.setMinimumIntegerDigits(1);
		number11f.setMinimumFractionDigits(1);
		number11f.setMaximumFractionDigits(1);
		NumberFormat number08d = NumberFormat.getInstance();
		number08d.setGroupingUsed(false);
		number08d.setMinimumIntegerDigits(8);
		number08d.setMinimumFractionDigits(0);
		number08d.setMaximumFractionDigits(0);

		if (format.equals("%04d-%02d-%02d")) {
			return number04d.format(Double.parseDouble(param[0])) + "-" + number02d.format(Double.parseDouble(param[1])) + "-" + number02d.format(Double.parseDouble(param[2]));
		} else if (format.equals("%04d-%02d")) {
			return number04d.format(Double.parseDouble(param[0])) + "-" + number02d.format(Double.parseDouble(param[1]));
		} else if (format.equals("%04d")) {
			return number04d.format(Double.parseDouble(param[0]));
		} else if (format.equals("%02d")) {
			return number02d.format(Double.parseDouble(param[0]));
		} else if (format.equals("%01d")) {
			return number01d.format(Double.parseDouble(param[0]));
		} else if (format.equals("%01.1f")) {
			return number11f.format(Double.parseDouble(param[0]));
		} else if (format.equals("%08d")) {
			return number08d.format(Double.parseDouble(param[0]));
		} else {
			String output = "";
			int myparam = 0;
			Matcher m = Pattern.compile("(.*?)(%[0-9]+d|%[.0-9]+f|%s)").matcher(format);
			while (m.find()) {
				String mytext = "" + m.group(1);
				String myformat = "" + m.group(2);
				output += mytext;
				if (myformat.equals("%08d")) {
					output += "" + number08d.format(Double.parseDouble(param[myparam]));
				} else if (myformat.equals("%04d")) {
					output += "" + number04d.format(Double.parseDouble(param[myparam]));
				} else if (myformat.equals("%02d")) {
					output += "" + number02d.format(Double.parseDouble(param[myparam]));
				} else if (myformat.equals("%01d")) {
					output += "" + number01d.format(Double.parseDouble(param[myparam]));
				} else if (myformat.equals("%01.1f")) {
					output += "" + number11f.format(Double.parseDouble(param[myparam]));
				} else if (myformat.equals("%s")) {
					output += "" + param[myparam];
				} else {
					output += "" + myformat;
				}
				myparam++;
			}
			if (myparam == 0) output = format;
			return output;
		}
	}



	static public String toString(Object stringvalue) {
		if (stringvalue == null) {
			return "";
		} else {
			return "" + stringvalue;
		}
	}



	static public Iterator SortedHashMapKeySetIterator(HashMap<String,String> hashmap) {
		Vector<String> v = new Vector<String>(hashmap.keySet());
		Collections.sort(v, String.CASE_INSENSITIVE_ORDER);
		Iterator i = v.iterator();
		return i;
	}



	static public LinkedHashMap<String,String> LinkedHashMapSortedByKey(HashMap<String,String> unsortedMap, boolean ascending) {
		List<String> unsortedKeys = new ArrayList<String>(unsortedMap.keySet());
		try {
			unsortedKeys = new ArrayList<String>(unsortedMap.keySet());
			Collections.sort(unsortedKeys, String.CASE_INSENSITIVE_ORDER);
		} catch(Exception e) {
			try {
				unsortedKeys = new ArrayList<String>(unsortedMap.keySet());
				Collections.sort(unsortedKeys);
			} catch(Exception ee) {
			}
		}
		if (! ascending) Collections.reverse(unsortedKeys);
		LinkedHashMap<String,String> sortedMap = new LinkedHashMap<String,String>();
		Iterator keyIterator = unsortedKeys.iterator();
		while (keyIterator.hasNext()) {
			String key = "" + keyIterator.next();
			String value = unsortedMap.get(key);
			sortedMap.put(key, value);
		}
		return sortedMap;
	}



	static public LinkedHashMap<String,String> LinkedHashMapSortedByValue(HashMap<String,String> unsortedMap, boolean ascending) {
		List<String> unsortedKeys = new ArrayList<String>(unsortedMap.keySet());
		List<String> unsortedValues = new ArrayList<String>(unsortedMap.values());
		try {
			unsortedKeys = new ArrayList<String>(unsortedMap.keySet());
			unsortedValues = new ArrayList<String>(unsortedMap.values());
			Collections.sort(unsortedValues, String.CASE_INSENSITIVE_ORDER);
			Collections.sort(unsortedKeys, String.CASE_INSENSITIVE_ORDER);
		} catch(Exception e) {
			try {
				unsortedKeys = new ArrayList<String>(unsortedMap.keySet());
				unsortedValues = new ArrayList<String>(unsortedMap.values());
				Collections.sort(unsortedValues);
				Collections.sort(unsortedKeys);
			} catch(Exception ee) {
			}
		}
		if (! ascending) Collections.reverse(unsortedValues);
		LinkedHashMap<String,String> sortedMap = new LinkedHashMap<String,String>();
		Iterator valueIterator = unsortedValues.iterator();
		while (valueIterator.hasNext()) {
			String value = "" + valueIterator.next();
			Iterator keyIterator = unsortedKeys.iterator();
			while (keyIterator.hasNext()) {
				String key = "" + keyIterator.next();
				if (unsortedMap.get(key).toString().equals(value.toString())) {
					unsortedKeys.remove(key);
					sortedMap.put(key, value);
					break;
				}
			}
		}
		return sortedMap;
	}



	static public LinkedHashMap<String,Long> LinkedHashMapSortedByNumericValue(HashMap<String,String> unsortedMap, boolean ascending) {
		HashMap<String,Long> myunsortedMap = new HashMap<String,Long>();
		Iterator mykeyIterator = unsortedMap.keySet().iterator();
		while (mykeyIterator.hasNext()) {
			String key = "" + mykeyIterator.next();
			myunsortedMap.put(key, new Long(Common.integernumber(unsortedMap.get(key).toString())));
		}

		List<String> unsortedKeys = new ArrayList<String>(myunsortedMap.keySet());
		List<Long> unsortedValues = new ArrayList<Long>(myunsortedMap.values());
		try {
			unsortedKeys = new ArrayList<String>(myunsortedMap.keySet());
			unsortedValues = new ArrayList<Long>(myunsortedMap.values());
			Collections.sort(unsortedValues);
			Collections.sort(unsortedKeys, String.CASE_INSENSITIVE_ORDER);
		} catch(Exception e) {
			try {
				unsortedKeys = new ArrayList<String>(myunsortedMap.keySet());
				unsortedValues = new ArrayList<Long>(myunsortedMap.values());
				Collections.sort(unsortedValues);
				Collections.sort(unsortedKeys);
			} catch(Exception ee) {
			}
		}
		if (! ascending) Collections.reverse(unsortedValues);
		LinkedHashMap<String,Long> sortedMap = new LinkedHashMap<String,Long>();
		Iterator valueIterator = unsortedValues.iterator();
		while (valueIterator.hasNext()) {
			Long value = (Long)valueIterator.next();
			Iterator keyIterator = unsortedKeys.iterator();
			while (keyIterator.hasNext()) {
				String key = "" + keyIterator.next();
				if (myunsortedMap.get(key).toString().equals(value.toString())) {
					unsortedKeys.remove(key);
					sortedMap.put(key, value);
					break;
				}
			}
		}
		return sortedMap;
	}



	static public LinkedHashMap<String,String> LinkedHashMapKeysSortedByNumericValues(HashMap<String,LinkedHashMap<String,String>> unsortedMap, String attribute1, boolean ascending) {
		return LinkedHashMapKeysSortedByNumericValues(unsortedMap, attribute1, null, null, null, null, ascending);
	}
	static public LinkedHashMap<String,String> LinkedHashMapKeysSortedByNumericValues(HashMap<String,LinkedHashMap<String,String>> unsortedMap, String attribute1, String attribute2, boolean ascending) {
		return LinkedHashMapKeysSortedByNumericValues(unsortedMap, attribute1, attribute2, null, null, null, ascending);
	}
	static public LinkedHashMap<String,String> LinkedHashMapKeysSortedByNumericValues(HashMap<String,LinkedHashMap<String,String>> unsortedMap, String attribute1, String attribute2, String attribute3, boolean ascending) {
		return LinkedHashMapKeysSortedByNumericValues(unsortedMap, attribute1, attribute2, attribute3, null, null, ascending);
	}
	static public LinkedHashMap<String,String> LinkedHashMapKeysSortedByNumericValues(HashMap<String,LinkedHashMap<String,String>> unsortedMap, String attribute1, String attribute2, String attribute3, String attribute4, boolean ascending) {
		return LinkedHashMapKeysSortedByNumericValues(unsortedMap, attribute1, attribute2, attribute3, attribute4, null, ascending);
	}
	static public LinkedHashMap<String,String> LinkedHashMapKeysSortedByNumericValues(HashMap<String,LinkedHashMap<String,String>> unsortedMap, String attribute1, String attribute2, String attribute3, String attribute4, String attribute5, boolean ascending) {
		LinkedHashMap<String,String> sortedKeys = new LinkedHashMap<String,String>();

		// outer loop - simply loop number of keys times
		Iterator outerloop = ((LinkedHashMap)unsortedMap.get(attribute1)).keySet().iterator();
		while (outerloop.hasNext()) {
			String dummy = "" + outerloop.next();
	
			String bestkey = null;
			double bestvalue1 = -1;
			double bestvalue2 = -1;
			double bestvalue3 = -1;
			double bestvalue4 = -1;
			double bestvalue5 = -1;
					
			// inner loop - find largest map entry not already in sorted map
			Iterator innerloop = ((LinkedHashMap)unsortedMap.get(attribute1)).keySet().iterator();
			while (innerloop.hasNext()) {
				String key = "" + innerloop.next();
				if (sortedKeys.get(key) == null) {
					double value1 = 0;
					double value2 = 0;
					double value3 = 0;
					double value4 = 0;
					double value5 = 0;
					if ((attribute1 != null) && (unsortedMap.get(attribute1) != null)) {
						value1 = Common.number(((HashMap)unsortedMap.get(attribute1)).get(key));
					}
					if ((attribute2 != null) && (unsortedMap.get(attribute2) != null)) {
						value2 = Common.number(((HashMap)unsortedMap.get(attribute2)).get(key));
					}
					if ((attribute3 != null) && (unsortedMap.get(attribute3) != null)) {
						value3 = Common.number(((HashMap)unsortedMap.get(attribute3)).get(key));
					}
					if ((attribute4 != null) && (unsortedMap.get(attribute4) != null)) {
						value4 = Common.number(((HashMap)unsortedMap.get(attribute4)).get(key));
					}
					if ((attribute5 != null) && (unsortedMap.get(attribute5) != null)) {
						value5 = Common.number(((HashMap)unsortedMap.get(attribute5)).get(key));
					}
					if (value1 < bestvalue1) {
						// ignore
					} else if ((value1 == bestvalue1) && (value2 < bestvalue2))  {
						// ignore
					} else if ((value1 == bestvalue1) && (value2 == bestvalue2) && (value3 < bestvalue3))  {
						// ignore
					} else if ((value1 == bestvalue1) && (value2 == bestvalue2) && (value3 == bestvalue3) && (value4 < bestvalue4))  {
						// ignore
					} else if ((value1 == bestvalue1) && (value2 == bestvalue2) && (value3 == bestvalue3) && (value4 == bestvalue4) && (value5 < bestvalue5))  {
						// ignore
					} else if ((value1 == bestvalue1) && (value2 == bestvalue2) && (value3 == bestvalue3) && (value4 == bestvalue4) && (value5 == bestvalue5) && ((bestkey == null) || (key.compareTo(bestkey) > 0))) {
						// ignore
					} else {
						bestkey = "" + key;
						bestvalue1 = value1;
						bestvalue2 = value2;
						bestvalue3 = value3;
						bestvalue4 = value4;
						bestvalue5 = value5;
					}
				}
			}
			sortedKeys.put(bestkey, bestkey);
		}
		if (ascending) {
			ArrayList<String> reverseKeys = new ArrayList<String>(sortedKeys.keySet());
			Collections.reverse(reverseKeys);
			sortedKeys = new LinkedHashMap<String,String>();
			Iterator reverseKeysIterator = reverseKeys.iterator();
			while (reverseKeysIterator.hasNext()) {
				String key = "" + reverseKeysIterator.next();
				sortedKeys.put(key, key);
			}
		}
		return sortedKeys;
	}



	public static String join(String token, String[] strings) {
		StringBuffer sb = new StringBuffer();
		if (strings.length>0) {
			for (int x = 0; x<(strings.length-1); x++ ) {
				sb.append(""+strings[x]);
				sb.append(token);
			}
			sb.append(strings[strings.length-1]);
		}
		return(sb.toString());
	}



	public static String join(String token, Object[] strings) {
		StringBuffer sb = new StringBuffer();
		if (strings.length>0) {
			for (int x = 0; x<(strings.length-1); x++ ) {
				sb.append(""+strings[x]);
				sb.append(token);
			}
			sb.append(strings[strings.length-1]);
		}
		return(sb.toString());
	}



	public static String quote_csv(String value) {
		if (value.indexOf(",") >= 0) {
			return "\"" + value.replaceAll("\"", "\"\"") + "\"";
		} else if (value.indexOf("\"") >= 0) {
			return "\"" + value.replaceAll("\"", "\"\"") + "\"";
		} else if (value.indexOf("\r") >= 0) {
			return "\"" + value.replaceAll("\"", "\"\"") + "\"";
		} else if (value.indexOf("\n") >= 0) {
			return "\"" + value.replaceAll("\"", "\"\"") + "\"";
		} else {
			return "" + value;
		}
	}



	public static String include(String filename, ServletContext server, Session session, Request request, Response response) {
		String output = "";
		try {
			output = execute(filename, null, null, null, null, null, server, session.getSession(), request.getRequest(), response.getResponse());
		} catch (Exception e) {
		}
		return output;
	}
	public static String include(String filename, ServletContext server, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		String output = "";
		try {
			output = execute(filename, null, null, null, null, null, server, session, request, response);
		} catch (Exception e) {
		}
		return output;
	}
	public static String execute(String filename, String attributename, Object attributevalue, String outputname, ServletContext server, Session session, Request request, Response response) {
		return execute(filename, attributename, attributevalue, null, null, outputname, server, session.getSession(), request.getRequest(), response.getResponse());
	}
	public static String execute(String filename, String attributename, Object attributevalue, String outputname, ServletContext server, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		return execute(filename, attributename, attributevalue, null, null, outputname, server, session, request, response);
	}
	public static String execute(String filename, String attributename, Object attributevalue, String attributename2, Object attributevalue2, String outputname, ServletContext server, Session session, Request request, Response response) {
		return execute(filename, attributename, attributevalue, attributename2, attributevalue2, outputname, server, session.getSession(), request.getRequest(), response.getResponse());
	}
	public static String execute(String filename, String attributename, Object attributevalue, String attributename2, Object attributevalue2, String outputname, ServletContext server, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		filename = filename.replaceAll("^\\Q" + Common.getRealPath(server, "/") + "\\E", "/");
		filename = filename.replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		String output = "";
		if ((server != null) && (filename != null) && (! filename.equals(""))) {
			File fh = new File(Common.getRealPath(server, filename));
			if (fh.exists()) {
				if ((attributename != null) && (! attributename.equals(""))) {
					request.setAttribute(attributename, attributevalue);
				}
				if ((attributename2 != null) && (! attributename2.equals(""))) {
					request.setAttribute(attributename2, attributevalue2);
				}
				RequestDispatcher dispatcher = server.getRequestDispatcher(filename);
				try {
					if (dispatcher != null) {
						BufferedHttpResponseWrapper responsewrapper = new BufferedHttpResponseWrapper(response);
						dispatcher.include(request, responsewrapper);
						String myoutput = new String(responsewrapper.getOutput());
						if ((myoutput.startsWith("WCM:REDIRECT:")) && ((session.getAttribute("mode") == null) || ((! session.getAttribute("mode").equals("admin")) && (! session.getAttribute("mode").equals("preview"))))) {
							Request myrequest = new Request(request);
							String myredirect = myoutput.replaceAll("^WCM:REDIRECT:", "");
							if ((myredirect.startsWith("http://")) || (myredirect.startsWith("https://"))) {
								response.sendRedirect(Common.crlf_clean(myredirect));
							} else if (! myredirect.equals("")) {
								response.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + Common.crlf_clean(myredirect));
							} else {
								response.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/");
							}
						} else if ((myoutput.startsWith("WCM:FORWARD:")) && ((session.getAttribute("mode") == null) || ((! session.getAttribute("mode").equals("admin")) && (! session.getAttribute("mode").equals("preview"))))) {
							String myredirect = myoutput.replaceAll("^WCM:FORWARD:", "");
							try {
								dispatcher = server.getRequestDispatcher(Common.crlf_clean(myredirect));
								if (dispatcher != null) dispatcher.forward(request, response);
							} catch (Exception e) {
							}
						} else {
							output += myoutput;
						}
					}
				} catch (Exception e) {
					System.out.println("ERROR:Common:execute:"+filename+":"+e);
					if ((outputname != null) && (! outputname.equals(""))) {
						session.setAttribute(outputname, "");
					}
				}
				if ((outputname != null) && (! outputname.equals(""))) {
					output = "" + session.getAttribute(outputname);
					session.setAttribute(outputname, "");
				}
			}
		}
		return output;
	}



	public static String serialize(Request request) {
		String output = "";
@SuppressWarnings("unchecked")
		ArrayList<String> names = Collections.list(request.getParameterNames());
		for (int i=0; i<names.size(); i++) {
			String[] values = request.getParameters("" + names.get(i));
			if (values.length > 0) {
				for (int j=values.length-1; j>=0; j--) {
					output += "<" + names.get(i) + ":" + j + ">" + html.encode(values[j]) + "</" + names.get(i) + ">" + "\r\n";
				}
			} else {
					output += "<" + names.get(i) + ":" + 0 + ">" + "</" + names.get(i) + ">" + "\r\n";
			}
		}
		return output;
	}



	public static void unserialize(String data, Request request) {
		HashMap<String,String[]> parameters = new HashMap<String,String[]>();
		Pattern pattern = Pattern.compile("<(.+):(.+)>(.*?)</(.+)>", Pattern.MULTILINE);
		Matcher matches = pattern.matcher(data);
		while (matches.find()) {
			String name = "" + matches.group(1);
			int index = Common.intnumber("" + matches.group(2));
			String value = "" + matches.group(3);
			String name2 = "" + matches.group(4);
			if (parameters.get(name) == null) {
				parameters.put(name, new String[index+1]);
			}
			((String[])parameters.get(name))[index] = value;
			if (! request.parameterExists(name)) request.setParameters(name, (String[])parameters.get(name));
		}
	}



	public static String getRealPath(ServletContext server, String relativepathfilename) {
		String realpath = server.getRealPath(relativepathfilename).replaceAll("[\\\\]", "/").replaceAll("^//", "\\\\\\\\").replaceAll("/+", "/");
		if (((relativepathfilename.endsWith("/")) || (folderExists(realpath))) && (! realpath.endsWith("/")) && (! realpath.endsWith("\\"))) {
			realpath += "/";
		}
		return realpath;
	}



	public static int getLineBreakCount(String data) {
		return data.split("\r\n|\r|\n").length;
	}



	public static void printStackTrace() {
		Exception e = new Exception();
		e.fillInStackTrace();
		e.printStackTrace();
	}
	public static String getStackTrace() {
		String output = "";
		for (StackTraceElement ste : Thread.currentThread().getStackTrace()) output += ste + "\r\n";
		return output;
	}



	public static String htmlentities(String content) {
		return html.encodeHtmlEntities(content);
	}



	public static boolean startsWithAnyOf(String redirect, String valid_redirects) {
		boolean output = false;
		String[] valid = valid_redirects.trim().replaceAll("\r", "\n").replaceAll("\n+", "\n").split("\n");
		for (int i=0; i<valid.length; i++) {
			if ((! valid[i].trim().equals("")) && (redirect.startsWith(valid[i].trim()))) output = true;
		}
		return output;
	}



	public static String redirectURL(DB db, Configuration myconfig, String url, String defaulturl) {
		if (! url.equals("")) {
//			if ((url.startsWith("http://")) || (url.startsWith("https://"))) {
			if (Pattern.compile("^([^:]+?):(.+?)$").matcher(url).find()) {
				if ((! myconfig.get(db, "redirect_urls").trim().equals("")) && (! Common.startsWithAnyOf(Common.crlf_clean(url), myconfig.get(db, "redirect_urls")))) {
					url = defaulturl;
				} else {
					// ok - allowed
				}
			} else {
				// ok - local
//				url = myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + Common.crlf_clean(url);
			}
		}
		return url;
	}



	public static String authorize(Session mysession, String name) {
		String token = "";
		token = mysession.get("authorize_" + name);
		if (token.equals("")) {
			for (int j=0; j<32; j++) {
				token = "" + token + (char)('a' + Integer.parseInt(Common.numberformat("" + Math.random()*25, 0)));
			}
		}
		mysession.set("authorize_" + name, token);
		String output = "<input type=\"hidden\" name=\"authorize_" + name + "\" value=\"" + token + "\">";
		return output;
	}



	public static boolean authorized(Session mysession, Request myrequest, String name) {
		if ((mysession.get("authorize_" + name).equals(myrequest.getParameter("authorize_" + name))) && (! mysession.get("authorize_" + name).equals(""))) {
			return true;
		} else {
			return false;
		}
	}



	public static void profile(String timer, String label) {
		profile(timer, label, "", false);
	}
	public static void profile(String timer, String label, boolean reset) {
		profile(timer, label, "", reset);
	}
	public static void profile(String timer, String label, String comment) {
		profile(timer, label, comment, false);
	}
	public static void profile(String timer, String label, String comment, boolean reset) {
		long now = new Date().getTime();
		if (reset || (profiler.get(timer) == null)) {
			profiler.put(timer, now);
			profilertotal.put(timer, (long)0);
		}
		long timed = now - profiler.get(timer);
		long total = timed + profilertotal.get(timer);
		profiler.put(timer, now);
		profilertotal.put(timer, total);
		System.out.println("PROFILE:" + timer + ":" + label + ":" + timed + ":" + total + ":" + now + ":" + comment);
	}



	public static void DEBUG(String message) {
		System.out.println(message);
	}



}
