package HardCore;

import java.io.*;
import java.io.File;
import java.math.BigDecimal;
import java.security.*;
import java.sql.*;
import java.text.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.regex.*;
import javax.naming.*;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.sql.*;

public class DB {
	public static boolean DEBUG = false;
	public boolean useNonCrossRefIds = false;
	public String error = "";
	public String errors = "";
	private Text text = new Text();
	private String databaseconnection = "";
	private String databaseconnectionstring = "";
	private boolean drivermanager = false;
	private boolean datasource = false;
	private String driver = "";
	private String username = "";
	private String password = "";
	private String database = "";
	private String databasename = ""; // internal label used for Cache - not to be used to connect to database
	private Connection realdb = null;
	private String databaseerror = "";
	private HashMap<String,HashMap<String,String>> tableinfo = new HashMap<String,HashMap<String,String>>();

	// do not keep any "word" character classes except the ones explicitly specified
//	public static String connectionstring_invalid_chars = "[^-=!: *.,;_?&%$@#/\\\\{}()a-zA-Z0-9]";
	// "\\w" may keep invalid Unicode encoded characters for InitialContext and Reference
	public static String connectionstring_invalid_chars = "[^-=!: *.,;_?&%$@#/\\\\{}()a-zA-Z0-9\\w]";
	// "\\p{L}\\p{N}" matches Unicode character that are neither letters nor numbers
//	public static String connectionstring_invalid_chars = "[^-=!: *.,;_?&%$@#/\\\\{}()a-zA-Z0-9\\p{L}\\p{N}]";


	public static String context_provider = null;
//	public static String context_provider = "file:///tmp";
	public static String context_factory = "com.sun.jndi.fscontext.RefFSContextFactory";
	public static String datasource_factory = "org.apache.commons.dbcp.BasicDataSourceFactory";
	public static String datasource_prefix = "jdbc/AsbruWCM/";
	public static String datasource_maxActive = "1000";
	public static String datasource_maxIdle = "100";
	public static String datasource_maxWait = "10000";

	private boolean encryption_enabled = false;
	private String decrypted_chars = "";
	private String encrypted_chars = "";
	private String encrypted_prefix = "";

	public static String blocked_files = "\\.(asp|aspx|ascx|jsp|php|php3|php4|phtml|phps|cgi|sh|pl)$";



	public DB() {
	}



	public DB(Text mytext) {
		if (mytext != null) text = mytext;
	}



	protected void finalize() throws java.lang.Throwable {
		if (DEBUG) System.out.println("HardCore/DB.finalize:close:"+realdb);
		close();
		super.finalize();
	}



/*
	BASIC DATABASE CONNECTION HANDLING
*/



	/* reconnect */

	public void reconnect() {
		close();
		connect(databaseconnection, databaseconnectionstring);
	}



	/* connect */

	public void connect(String mydatabaseconnection, String mydatabase) {
		mydatabaseconnection = mydatabaseconnection.replaceAll("[\r\n]", "");
		mydatabaseconnection = mydatabaseconnection.replaceAll("\\u0000", "");
		mydatabaseconnection = mydatabaseconnection.replaceAll(connectionstring_invalid_chars, "");
		mydatabase = mydatabase.replaceAll("[\r\n]", "");
		mydatabase = mydatabase.replaceAll("\\u0000", "");
		mydatabase = mydatabase.replaceAll(connectionstring_invalid_chars, "");
		databaseconnection = mydatabaseconnection;
		databaseconnectionstring = mydatabase;

		// EXPERIMENTAL: automatic conversion of different programming language format database connection strings
		if (! mydatabase.equals(translatedconnectionstring(mydatabase))) {
			mydatabase = translatedconnectionstring(mydatabase);
			mydatabaseconnection = DB.DSN(mydatabase);
		}

		close();
		drivermanager = false;
		datasource = false;
		driver = "";
		username = "";
		password = "";
		database = "";
		databasename = "";

		Pattern p = Pattern.compile("^([^:@]*):([^:@]*)@(.*)$");
		Matcher m = p.matcher(mydatabaseconnection);
		if (m.find()) {
			datasource = true;
			username = "" + m.group(1);
			password = "" + m.group(2);
			database = "" + m.group(3);
			if (DEBUG) System.out.println("HardCore/DB.connect:datasource:"+datasource+":"+username+":"+password+":"+database);
		} else {
			p = Pattern.compile("^([^:@]*):([^:@]*):([^:@]*)@(.*)$");
			m = p.matcher(mydatabaseconnection);
			if (m.find()) {
				drivermanager = true;
				driver = "" + m.group(1);
				username = "" + m.group(2);
				password = "" + m.group(3);
				database = "" + m.group(4);
				if (DEBUG) System.out.println("HardCore/DB.connect:drivermanager:"+drivermanager+":"+driver+":"+username+":"+password+":"+database);
			}
		}

		String mydatabasename = "" + mydatabase;
// QQQ TODO: handle database connection strings without database name where the default database for the address is used
// QQQ TODO: handle database connection strings without database name where the default database for the username is used
		mydatabasename = mydatabasename.replaceAll("^.*/(.+?)$", "$1");
		mydatabasename = mydatabasename.replaceAll("^.*:(.+?)$", "$1");
		mydatabasename = mydatabasename.replaceAll("^.*\\\\(.+?)$", "$1");
		mydatabasename = mydatabasename.replaceAll("^(.*)\\.mdb$", "$1");
		mydatabasename = mydatabasename.replaceAll("^database\\.(.*)$", "$1");
		if (mydatabasename.equals("")) mydatabasename = "WCM";
		databasename = mydatabasename;
		if (DEBUG) System.out.println("HardCore/DB.connect:databasename:"+databasename);

		realdb = connection();
		if (realdb == null) {
			System.out.println("HardCore/DB.connect:NULL");
		}
	}



	public static String translatedconnectionstring(String mydatabase) {
		// EXPERIMENTAL: automatic conversion of different programming language format database connection strings
		mydatabase = mydatabase.replaceAll(connectionstring_invalid_chars, "");
		if (mydatabase.matches("^mysql:mysql://(.+):(.+)@(.+)/(.+)$")) {
			// PHP MYSQL
			Pattern pattern = Pattern.compile("^mysql:mysql://(.+):(.+)@(.+)/(.+)$");
			Matcher matches = pattern.matcher(mydatabase);
			if (matches.find()) {
				return "mysql:com.mysql.jdbc.Driver:" + matches.group(1) + ":" + matches.group(2) + "@jdbc:mysql://" + matches.group(3) + "/" + matches.group(4) + "";
			}
		}
		return mydatabase;
	}



	public Connection connection() {
		if (realdb != null) try { realdb.close(); } catch (SQLException e) { ; }
		realdb = null;
//		databaseerror = "";
		if (drivermanager) {
			String mydatasourcename = "";
			Pattern p1 = Pattern.compile("^.*/([^/?]*)$");
			Pattern p2 = Pattern.compile("^.*/([^/?]*)\\?.*$");
			Matcher m1 = p1.matcher(database);
			Matcher m2 = p2.matcher(database);
			if (m1.find()) {
				mydatasourcename = datasource_prefix + m1.group(1);
			} else if (m2.find()) {
				mydatasourcename = datasource_prefix + m2.group(1);
			}
			if (connectionDataSource(username, password, mydatasourcename)) {
				// ok - found data source for same database name
				if (DEBUG) System.out.println("HardCore/DB.connection:drivermanager-datasource:found:"+database+":"+username+":"+password+":"+mydatasourcename);
			} else if (connectionNewDataSource(driver, username, password, database, mydatasourcename)) {
				// ok - added new data source for database name
				if (DEBUG) System.out.println("HardCore/DB.connection:drivermanager-datasource:added:"+driver+":"+database+":"+username+":"+password+":"+mydatasourcename);
			} else {
				connectionDriverManager(driver, username, password, database);
			}
		} else if (datasource) {
			connectionDataSource(username, password, database);
		}
		return realdb;
	}



	public boolean connectionDataSource(String username, String password, String database) {
if (DEBUG) System.out.println("HardCore/DB.connectionDataSource:"+context_factory+":"+context_provider+":"+datasource_factory+":"+driver+":"+username+":"+password+":"+database);
		Context ctx = null;
		DataSource ds = null;
		try {
			if (! database.startsWith(datasource_prefix)) {
				ctx = new InitialContext();
				try {
					ds = (DataSource)ctx.lookup(database);
				} catch (Exception e) {
					// Apache Tomcat may need "java:comp/env/" prefix
					try {
						ds = (DataSource)ctx.lookup("java:comp/env/" + database);
					} catch (Exception ee) {
						// datasource not found
						if (DEBUG) System.out.println("HardCore/DB.connectionDataSource:datasource:Exception:"+database+":"+username+":"+password+":"+e);
						if (DEBUG) System.out.println("HardCore/DB.connectionDataSource:datasource:Exception:"+database+":"+username+":"+password+":"+ee);
					}
				}
			} else if ((ds == null) && (context_factory != null) && (context_provider != null) && (datasource_factory != null)) {
				Hashtable<String,String> env = new Hashtable<String,String>();
				env.put(Context.INITIAL_CONTEXT_FACTORY, context_factory);
				env.put(Context.PROVIDER_URL, context_provider);
				try {
					ctx = new InitialContext(env);
					try {
						ds = (DataSource)ctx.lookup(database);
					} catch (ClassCastException e) {
						// datasource_factory jars not found (commons-dbcp commons-pool commons-collections)
						if (DEBUG) System.out.println("HardCore/DB.connectionDataSource:datasource:ClassCastException:"+context_factory+":"+context_provider+":"+datasource_factory+":"+database+":"+username+":"+password+":"+e);
					} catch (NamingException e) {
						if (DEBUG) System.out.println("HardCore/DB.connectionDataSource:datasource:NamingException:"+context_factory+":"+context_provider+":"+datasource_factory+":"+database+":"+username+":"+password+":"+e);
					} catch (NoClassDefFoundError e) {
						if (DEBUG) System.out.println("HardCore/DB.connectionDataSource:datasource:NoClassDefFoundError:"+context_factory+":"+context_provider+":"+datasource_factory+":"+database+":"+username+":"+password+":"+e);
					}
				} catch (NamingException e) {
					// context_factory jars not found (fscontext.jar providerutil.jar)
					// context_provider not found
					if (DEBUG) System.out.println("HardCore/DB.connectionDataSource:context:NamingException:"+context_factory+":"+context_provider+":"+database+":"+username+":"+password+":"+e);
					context_provider = null;
					context_factory = null;
				}
			}

			if (ds == null) return false;
			if (DEBUG) try { ds.setLogWriter(new PrintWriter(new FileOutputStream("DataSource.log"))); } catch (FileNotFoundException e) { e.printStackTrace(); }

//			realdb = ds.getConnection(username, password);
			if (ds instanceof ConnectionPoolDataSource) {
				try {
					realdb = (Connection)((ConnectionPoolDataSource)ds).getPooledConnection(username, password);
				} catch (UnsupportedOperationException e) {
					// Apache Tomcat may not support connection parameters - username and password must be configured for datasource instead
					realdb = (Connection)((ConnectionPoolDataSource)ds).getPooledConnection();
				}
			} else {
				try {
					realdb = ((DataSource)ds).getConnection(username, password);
				} catch (UnsupportedOperationException e) {
					realdb = ((DataSource)ds).getConnection();
				}
			}
			if (DEBUG) System.out.println("HardCore/DB.connectionDataSource:datasource:"+database+":"+username+":"+password+":"+realdb);
			return true;
		} catch (NamingException e) {
			if (DEBUG) System.out.println("HardCore/DB.connectionDataSource:NamingException:"+database+":"+username+":"+password+":"+e);
		} catch (SQLException e) {
			if (DEBUG) System.out.println("HardCore/DB.connectionDataSource:SQLException:"+database+":"+username+":"+password+":"+e);
		}
		return false;
	}



	public boolean connectionNewDataSource(String driver, String username, String password, String database, String datasourcename) {
if (DEBUG) System.out.println("HardCore/DB.connectionNewDataSource:"+context_factory+":"+context_provider+":"+datasource_factory+":"+driver+":"+username+":"+password+":"+database+":"+datasourcename);
		if ((context_factory == null) || (context_provider == null) || (datasource_factory == null)) return false;
		Context ctx = null;
		DataSource ds = null;
		try {
			Hashtable<String,String> env = new Hashtable<String,String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, context_factory);
			env.put(Context.PROVIDER_URL, context_provider);
			ctx = new InitialContext(env);
			Reference ref = new Reference("javax.sql.DataSource", datasource_factory, null);
			ref.add(new StringRefAddr("driverClassName", driver.replaceAll("[\r\n].*", "").replaceAll("[\r\n]", "")));
			ref.add(new StringRefAddr("url", database.replaceAll("[\r\n].*", "").replaceAll("[\r\n]", "")));
			ref.add(new StringRefAddr("username", username.replaceAll("[\r\n].*", "").replaceAll("[\r\n]", "")));
			ref.add(new StringRefAddr("password", password.replaceAll("[\r\n].*", "").replaceAll("[\r\n]", "")));
			ref.add(new StringRefAddr("maxActive", datasource_maxActive));
			ref.add(new StringRefAddr("maxIdle", datasource_maxIdle));
			ref.add(new StringRefAddr("maxWait", datasource_maxWait));
			ctx.rebind(datasourcename, ref);
			if (DEBUG) System.out.println("HardCore/DB.connectionNewDataSource:OK:"+context_factory+":"+context_provider+":"+datasource_factory+":"+driver+":"+database+":"+username+":"+password+":"+datasourcename);
			return connectionDataSource(username, password, datasourcename);
    		} catch (NoInitialContextException e) {
			databaseerror += "<p>" + e + "</p>" + "\r\n";
			if (DEBUG) System.out.println("HardCore/DB.connectionNewDataSource:NoInitialContextException:"+context_factory+":"+context_provider+":"+datasource_factory+":"+driver+":"+database+":"+username+":"+password+":"+datasourcename+":"+e);
			// context_factory jars not found (fscontext.jar providerutil.jar)
			// context_provider malformed
			context_provider = null;
			context_factory = null;
    		} catch (NamingException e) {
			databaseerror += "<p>" + e + "</p>" + "\r\n";
			if (DEBUG) System.out.println("HardCore/DB.connectionNewDataSource:NamingException:"+context_factory+":"+context_provider+":"+datasource_factory+":"+driver+":"+database+":"+username+":"+password+":"+datasourcename+":"+e);
			// ????? datasource_factory jars not found (commons-dbcp commons-pool commons-collections)
//			datasource_factory = null;
    		} catch (IllegalArgumentException e) {
			databaseerror += "<p>" + e + "</p>" + "\r\n";
			if (DEBUG) System.out.println("HardCore/DB.connectionNewDataSource:IllegalArgumentException:"+context_factory+":"+context_provider+":"+datasource_factory+":"+driver+":"+database+":"+username+":"+password+":"+datasourcename+":"+e);
			// ????? invalid Unicode characters in database connection string
		}
		return false;
	}



	public boolean connectionDriverManager(String driver, String username, String password, String database) {
if (DEBUG) System.out.println("HardCore/DB.connectionDriverManager:"+context_factory+":"+context_provider+":"+datasource_factory+":"+driver+":"+username+":"+password+":"+database);
		Class mydrivermanager = null;
		try {
			Class.forName(driver);
			if (DEBUG) try { DriverManager.setLogWriter(new PrintWriter(new FileOutputStream("DriverManager.log"))); } catch (FileNotFoundException e) { e.printStackTrace(); }
			realdb = DriverManager.getConnection(database, username, password);
			if (DEBUG) System.out.println("HardCore/DB.connectionDriverManager:drivermanager:"+driver+":"+database+":"+username+":"+password+":"+realdb);
			return true;
		} catch (ClassNotFoundException e) {
			databaseerror += "<p>" + e + "</p>" + "\r\n";
			if (DEBUG) System.out.println("HardCore/DB.connectionDriverManager:ClassNotFoundException:"+driver+":"+database+":"+username+":"+password+":"+e);
		} catch (SQLException e) {
			databaseerror += "<p>" + e + "</p>" + "\r\n";
			if (DEBUG) System.out.println("HardCore/DB.connectionDriverManager:SQLException:"+driver+":"+database+":"+username+":"+password+":"+e);
		} catch (NullPointerException e) {
			databaseerror += "<p>" + e + "</p>" + "\r\n";
			if (DEBUG) System.out.println("HardCore/DB.connectionDriverManager:NullPointerException:"+driver+":"+database+":"+username+":"+password+":"+e);
		}
		return false;
	}



	/* close */

	public void close() {
		if (realdb != null) {
			try {
				if (DEBUG) System.out.println("HardCore/DB.close:"+realdb);
				realdb.close();
			} catch (SQLException e) {
			}
			realdb = null;
		}
	}



	/* isClosed */

	public boolean isClosed() {
		try {
			if (realdb == null) {
				return true;
			} else {
				return realdb.isClosed();
			}
		} catch (SQLException e) {
		}
		return false;
	}



	/* isError */

	public boolean isError() {
		try {
			if ((realdb == null) || ((realdb.getWarnings() != null) && (! realdb.getWarnings().equals("")) && (! ("" + realdb.getWarnings()).startsWith("sun.jdbc.odbc.JdbcOdbcSQLWarning: [Microsoft][ODBC SQL Server Driver][SQL Server]Changed database context to ")) && (! ("" + realdb.getWarnings()).startsWith("java.sql.SQLWarning: [Microsoft][SQLServer 2000 Driver for JDBC]Database changed to ")))) {
				return true;
			} else if (! databaseerror.equals("")) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
		}
		return false;
	}



	/* getMessage */

	public String getMessage() {
		try {
			if ((realdb != null) && (realdb.getWarnings() != null) && (! realdb.getWarnings().equals(""))) {
				return "" + realdb.getWarnings();
			} else {
				return "" + databaseerror;
			}
		} catch (SQLException e) {
		}
		return "";
	}



	/* getWarnings */

	public String getWarnings() {
		try {
			if ((realdb != null) && (realdb.getWarnings() != null) && (! realdb.getWarnings().equals("")) && (! ("" + realdb.getWarnings()).startsWith("sun.jdbc.odbc.JdbcOdbcSQLWarning: [Microsoft][ODBC SQL Server Driver][SQL Server]Changed database context to ")) && (! ("" + realdb.getWarnings()).startsWith("java.sql.SQLWarning: [Microsoft][SQLServer 2000 Driver for JDBC]Database changed to "))) {
				return "" + realdb.getWarnings();
			} else {
				return "" + databaseerror;
			}
		} catch (SQLException e) {
		}
		return "";
	}



	/* getDebugInfo */

	public String getDebugInfo() {
		return "";
	}



	public Statement createStatement() {
		Statement st = null;
		if (realdb == null) reconnect();
		if (realdb != null) {
			try { if (realdb.isClosed()) reconnect(); } catch (SQLException e) { }
			try {
				if (realdb != null) st = realdb.createStatement();
				if (st != null) return st;
			} catch (SQLException e) {
				if (st != null) try { st.close(); } catch (SQLException ee) { ; }
				st = null;
			}
			try {
				reconnect();
				if (realdb != null) st = realdb.createStatement();
				if (st != null) return st;
			} catch (SQLException e) {
				if (st != null) try { st.close(); } catch (SQLException ee) { ; }
				st = null;
			}
		}
		return st;
	}



	public PreparedStatement prepareStatement(String SQL) {
		if (DEBUG) System.out.println("HardCore/DB.prepareStatement:"+SQL);
		PreparedStatement ps = null;
		if (realdb == null) reconnect();
		if (realdb != null) {
			try { if (realdb.isClosed()) reconnect(); } catch (SQLException e) { }
			try {
				if (realdb != null) ps = realdb.prepareStatement(SQL);
				if (ps != null) return ps;
			} catch (SQLException e) {
				if (ps != null) try { ps.close(); } catch (SQLException ee) { ; }
				ps = null;
			}
			try {
				reconnect();
				if (realdb != null) ps = realdb.prepareStatement(SQL);
				if (ps != null) return ps;
			} catch (SQLException e) {
				if (ps != null) try { ps.close(); } catch (SQLException ee) { ; }
				ps = null;
			}
		}
		return ps;
	}



/*
	DATABASE SERVER SPECIFIC UTILITY FUNCIONS
*/



	/* getDatabase */

	public String getDatabase() {
		return databaseconnectionstring;
	}



	/* getDatabaseName */

	public String getDatabaseName() {
		return "" + databasename;
	}

	/* setDatabaseName */

	public void setDatabaseName(String mydatabasename) {
		databasename = "" + mydatabasename;
	}



	/* DSN */

	public static String DSN(String database) {
		if (database == null) {
			return "";
		} else if ((database.length() > 6) && (database.substring(0,6).equals("mysql:"))) {
			return database.substring(6);
		} else if ((database.length() > 6) && (database.substring(0,6).equals("mssql:"))) {
			return database.substring(6);
		} else if ((database.length() > 7) && (database.substring(0,7).equals("oracle:"))) {
			return database.substring(7);
		} else if ((database.length() > 4) && (database.substring(0,4).equals("db2:"))) {
			return database.substring(4);
		} else if ((database.length() > 6) && (database.substring(0,6).equals("pgsql:"))) {
			return database.substring(6);
		} else if ((database.length() > 7) && (database.substring(0,7).equals("access:"))) {
			return database.substring(7);
		} else  {
			return database;
		}
	}



	/* equals */

	public static String equals(String database) {
		if (database == null) {
			return " = ";
		} else if ((database.length() > 6) && database.substring(0,6).equals("mysql:")) {
			return " = ";
		} else if ((database.length() > 6) && database.substring(0,6).equals("mssql:")) {
			return " like ";
		} else if ((database.length() > 7) && database.substring(0,7).equals("oracle:")) {
			return " like ";
		} else if ((database.length() > 4) && database.substring(0,4).equals("db2:")) {
			return " = ";
		} else if ((database.length() > 6) && database.substring(0,6).equals("pgsql:")) {
			return " = ";
		} else if ((database.length() > 7) && database.substring(0,7).equals("access:")) {
			return " = ";
		} else  {
			return " = ";
		}
	}



	/* random */

	public static String random(String database) {
		if (database == null) {
			return "rand()";
		} else if ((database.length() > 6) && database.substring(0,6).equals("mysql:")) {
			return "rand()";
		} else if ((database.length() > 6) && database.substring(0,6).equals("mssql:")) {
			return "rand()";
		} else if ((database.length() > 7) && database.substring(0,7).equals("oracle:")) {
			return "rand()";
		} else if ((database.length() > 4) && database.substring(0,4).equals("db2:")) {
			return "rand()";
		} else if ((database.length() > 6) && database.substring(0,6).equals("pgsql:")) {
			return "rand()";
		} else if ((database.length() > 7) && database.substring(0,7).equals("access:")) {
			return "Rnd()";
		} else  {
			return "rand()";
		}
	}



	/* db_type */

	public static String db_type(String database) {
		if (database == null) {
			return "";
		} else if ((database.length() >= 6) && database.substring(0,6).equals("mysql:")) {
			return "mysql";
		} else if ((database.length() >= 6) && database.substring(0,6).equals("mssql:")) {
			return "mssql";
		} else if ((database.length() >= 7) && database.substring(0,7).equals("oracle:")) {
			return "oracle";
		} else if ((database.length() >= 4) && database.substring(0,4).equals("db2:")) {
			return "db2";
		} else if ((database.length() >= 6) && database.substring(0,6).equals("pgsql:")) {
			return "pgsql";
		} else if ((database.length() >= 7) && database.substring(0,7).equals("access:")) {
			return "access";
		} else  {
			return "";
		}
	}



	/* db_quote */

	public static String db_quote(String value) {
		return "'" + value.replaceAll("'", "''") + "'";
	}



	/* quote */

	public String quote(String value) {
		return "'" + value.replaceAll("'", "''") + "'";
	}



	/* quote_csv */

	public String quote_csv(String values) {
		return "'" + values.replaceAll("'", "''").replaceAll(",", "','") + "'";
	}



	/* quote_psv */

	public String quote_psv(String values) {
		return "'" + values.replaceAll("'", "''").replaceAll("|", "','") + "'";
	}



	/* is_not_blank */

	public String is_not_blank(String column) {
		if (db_type(getDatabase()).equals("oracle")) {
			return "(" + column + " is not null)";
		} else {
			return "((" + column + " not like '') and (" + column + " is not null))";
		}
	}



	/* is_blank */

	public String is_blank(String column) {
		if (db_type(getDatabase()).equals("oracle")) {
			return "(" + column + " is null)";
		} else {
			return "((" + column + " = '') or (" + column + " is null))";
		}
	}



	/* execute_sql */

	public void execute_sql(JspWriter out, String filename, String table, String column, String size) {
		File fh = new File(filename);
		if (fh.exists()) {
			BufferedReader input = null;
			try {
				input = new BufferedReader(new FileReader(filename));
				String my_line = "";
				String SQL = "";
				while ((my_line = input.readLine()) != null) {
					if ((my_line.equals("")) && (! SQL.equals(""))) {
						SQL = SQL.replaceAll("@@@table@@@", table.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")).replaceAll("@@@column@@@", column.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")).replaceAll("@@@size@@@", size.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
						if (SQL.startsWith("#")) {
							if (out != null) out.println("<p><b>" + SQL.substring(1) + "</b></p>" + "\r\n");
							if (out != null) out.flush();
						} else if (SQL.startsWith("!")) {
							SQL = SQL.substring(1);
							if (DEBUG) System.out.println("HardCore/DB.execute_sql:"+SQL);
							try {
								execute(SQL);
							} catch (Exception e) {
							}
						} else {
							if (DEBUG) System.out.println("HardCore/DB.execute_sql:"+SQL);
							try {
								execute(out, SQL);
							} catch (Exception e) {
								System.out.println("HardCore/DB.execute_sql:"+e);
							}
						}
						SQL = "";
					} else if (! my_line.equals("")) {
						SQL = "" + SQL + my_line + "\n";
					}
				}
				input.close();
				input = null;
			} catch (Exception e) {
				System.out.println("HardCore/DB.execute_sql:"+e);
				if (input != null) try { input.close(); } catch (Exception ee) { ; }
				input = null;
			}
		}
	}



	/* execute */

	public boolean execute(String SQL) {
		return execute(null, SQL);
	}
	public boolean execute(JspWriter out, String SQL) {
		SQL = SQL.replaceAll("[;\r\n]$", "").replaceAll("[/\r\n]$", "");
		Statement st = null;
		try {
			if (DEBUG) System.out.println("HardCore/DB.execute:"+SQL);
			st = createStatement();
			st.execute(SQL);
			if ((getWarnings() != null) && (! getWarnings().equals(""))) {
				if (out != null) out.println("<p><pre>" + SQL + "</pre>");
				if (out != null) out.println("<p>ERROR:" + getWarnings() + "</p>");
				if (out != null) out.flush();
			}
			st.close();
			st = null;
			return true;
		} catch (Exception e) {
			System.out.println("HardCore/DB.execute:"+SQL+":"+e);
			if (st != null) try { st.close(); } catch (SQLException ee) { ; }
			st = null;
		}
		return true;
	}



/*
	DATABASE QUERY UTILITY FUNCIONS
*/



	/* columntype */

	public String columntype(String table, String column) {
		String columntype = "";
		String SQL = "select " + column + " from " + table + " where 1=0";
		Statement st = null;
		ResultSet rs = null;
		try {
			st = createStatement();
			if (DEBUG) System.out.println("HardCore/DB.columntype:"+SQL);
			rs = st.executeQuery(SQL);
			columntype = "" + rs.getMetaData().getColumnTypeName(1);
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			System.out.println("HardCore/DB.columntype:" + e + " - " + SQL);
			if (rs != null) try { rs.close(); } catch (SQLException ee) { ; }
			rs = null;
			if (st != null) try { st.close(); } catch (SQLException ee) { ; }
			st = null;
		}
		rs = null;
		st = null;
		return columntype.toLowerCase().replaceAll("decimal", "numeric").replaceAll("number", "numeric");
	}



	/* query */

	public boolean query(String SQL) {
		boolean value = false;
		Statement st = null;
		ResultSet rs = null;
		try {
			st = createStatement();
			if (DEBUG) System.out.println("HardCore/DB.query:"+SQL);
			rs = st.executeQuery(SQL);
			if (rs.next()) {
				value = true;
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			value = false;
			System.out.println("HardCore/DB.query:" + e + " - " + SQL);
			if (rs != null) try { rs.close(); } catch (SQLException ee) { ; }
			rs = null;
			if (st != null) try { st.close(); } catch (SQLException ee) { ; }
			st = null;
		}
		rs = null;
		st = null;
		return value;
	}



	/* query_string */

	public String query_string(String SQL) {
		String value = "";
		Statement st = null;
		ResultSet rs = null;
		try {
			st = createStatement();
			if (DEBUG) System.out.println("HardCore/DB.query_string:"+SQL);
			rs = st.executeQuery(SQL);
			if (rs.next()) {
				if (rs.getString(1) != null) {
					value = "" + rs.getString(1);
				} else {
					value = "";
				}
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			value = "";
			System.out.println("HardCore/DB.query_string:" + e + " - " + SQL);
			if (rs != null) try { rs.close(); } catch (SQLException ee) { ; }
			rs = null;
			if (st != null) try { st.close(); } catch (SQLException ee) { ; }
			st = null;
		}
		rs = null;
		st = null;
		return value;
	}
	public String query_string(String SQL, String column) {
		HashMap record = query_record(SQL);
		String value = "" + record.get(column);
		return value;
	}



	/* query_value */

	public Double query_value(String SQL) {
		double value = 0;
		Statement st = null;
		ResultSet rs = null;
		try {
			st = createStatement();
			if (DEBUG) System.out.println("HardCore/DB.query_value:"+SQL);
			rs = st.executeQuery(SQL);
			if (rs.next()) {
				value = rs.getDouble(1);
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			value = 0;
			System.out.println("HardCore/DB.query_value:" + e + " - " + SQL);
			if (rs != null) try { rs.close(); } catch (SQLException ee) { ; }
			rs = null;
			if (st != null) try { st.close(); } catch (SQLException ee) { ; }
			st = null;
		}
		rs = null;
		st = null;
		return new Double(value);
	}
	public Double query_value(String SQL, String column) {
		try {
			HashMap record = query_record(SQL);
			String value = "" + record.get(column);
			return Double.parseDouble("" + NumberFormat.getInstance().parse(value));
		} catch (Exception e) {
			return 0.0;
		}
	}



	/* query_values */

	public LinkedHashMap<String,String> query_values(String SQL) {
		LinkedHashMap<String,String> values = new LinkedHashMap<String,String>();
		LinkedHashMap<String,HashMap<String,String>> rows = query_records(SQL);
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			Object rowskey = rowsIterator.next();
			HashMap<String,String> row = (HashMap<String,String>)rows.get(rowskey);
			Iterator rowIterator = row.keySet().iterator();
			if (rowIterator.hasNext()) {
				Object rowkey = rowIterator.next();
				if (row.get(rowkey) != null) {
					values.put("" + values.size(), "" + row.get(rowkey));
				} else {
					values.put("" + values.size(), "");
				}
			}
		}
		return values;
	}



	/* query_record */

	public HashMap<String,String> query_record(String SQL) {
		return query_record(SQL, true);
	}
	public HashMap<String,String> query_record(String SQL, boolean log_errors) {
		HashMap<String,String> record = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			st = createStatement();
			if (st != null) {
				if (DEBUG) System.out.println("HardCore/DB.query_record:"+SQL);
				rs = st.executeQuery(SQL);
				if (rs.next()) {
					record = resultSetToLowerCase(rs);
				}
				rs.close();
				rs = null;
				st.close();
				st = null;
			}
		} catch (SQLException e) {
			if (log_errors) System.out.println("HardCore/DB.query_record:" + e + " - " + SQL);
			if (rs != null) try { rs.close(); } catch (SQLException ee) { ; }
			rs = null;
			if (st != null) try { st.close(); } catch (SQLException ee) { ; }
			st = null;
		}
		rs = null;
		st = null;
		return record;
	}



	/* query_records */

	public LinkedHashMap<String,HashMap<String,String>> query_records(String SQL) {
		LinkedHashMap<String,HashMap<String,String>> records = new LinkedHashMap<String,HashMap<String,String>>();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = createStatement();
			if (st != null) {
				if (DEBUG) System.out.println("HardCore/DB.query_records:"+SQL);
				rs = st.executeQuery(SQL);
				while (rs.next()) {
					HashMap<String,String> record = resultSetToLowerCase(rs);
					records.put("" + records.size(), record);
				}
				rs.close();
				rs = null;
				st.close();
				st = null;
			}
		} catch (SQLException e) {
			System.out.println("HardCore/DB.query_records:" + e + " - " + SQL);
//if (DEBUG) try { ds.setLogWriter(new PrintWriter(new FileOutputStream("DEBUG.log"))); } catch (FileNotFoundException e) { e.printStackTrace(); }
			if (rs != null) try { rs.close(); } catch (SQLException ee) { ; }
			rs = null;
			if (st != null) try { st.close(); } catch (SQLException ee) { ; }
			st = null;
		}
		rs = null;
		st = null;
		return records;
	}



	/* closeRecords */

	public void closeRecords(Statement records_st) {
		if (records_st != null) try { records_st.close(); } catch (Exception e) { ; }
	}



	/* records */

	public Statement records(String SQL) {
		Statement records_st = null;
		ResultSet records_rs = null;
		try {
			if (! SQL.equals("")) {
				records_st = createStatement();
				if (DEBUG) System.out.println("HardCore/DB.records:"+SQL);
				if (records_st != null) records_rs = records_st.executeQuery(SQL);
				return records_st;
			} else {
				return null;
			}
		} catch (Exception e) {
			error = "ERROR:DB:records:" + e + " " + getWarnings() + " - " + SQL;
			errors += "<p>" + error + "</p>" + "\r\n";
			if (DEBUG) System.out.println(error);
			if (records_rs != null) try { records_rs.close(); } catch (Exception ee) { ; }
			if (records_st != null) try { records_st.close(); } catch (Exception ee) { ; }
		} finally {
		}
		return null;
	}



	public HashMap<String,String> records(Statement records_st) {
		if (records_st == null) return null;
		HashMap<String,String> record = new HashMap<String,String>();
		try {
			ResultSet records_rs = records_st.getResultSet();
			if (records_rs == null) {
				return null;
			} else if ((! isClosed()) && records_rs.next()) {
				record = resultSetToLowerCase(records_rs);
				return record;
			} else {
				closeRecords(records_st);
				return null;
			}
		} catch (Exception e) {
			error = "ERROR:DB:records:" + e + " " + getWarnings();
			errors += "<p>" + error + "</p>" + "\r\n";
			if (DEBUG) System.out.println(error);
		} finally {
		}
		return null;
	}



	/* select */

	public HashMap<String,String> select(String table, String id, String value) {
		HashMap<String,String> record = new HashMap<String,String>();
		String SQL = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			SQL = "select * from " + Common.SQL_clean(table) + " where " + Common.SQL_clean(id) + "=?";
			if (DEBUG) System.out.println("HardCore/DB.select:"+SQL);
			ps = prepareStatement(SQL);
			if (ps != null) {
				int p = 1;
				ps.setString(p++, value);
				rs = ps.executeQuery();
				if (rs.next()) {
					record = resultSetToLowerCase(rs);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
		} catch (Exception e) {
			error = "ERROR:DB:select:" + e + " " + getWarnings() + " - " + SQL;
			errors += "<p>" + error + "</p>" + "\r\n";
			if (DEBUG) System.out.println(error);
			if (rs != null) try { rs.close(); } catch (Exception ee) { ; }
			rs = null;
			if (ps != null) try { ps.close(); } catch (Exception ee) { ; }
			ps = null;
		}
		rs = null;
		ps = null;
		return record;
	}



	public HashMap<String,String> select(String table, HashMap values) {
		return select(table, values, "");
	}
	public HashMap<String,String> select(String table, HashMap values, String SQLorder) {
		HashMap<String,String> record = new HashMap<String,String>();
		String SQL = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			SQL = "select * from " + Common.SQL_clean(table);
			String SQLwhere = "";
			Iterator valuesIterator = values.keySet().iterator();
			while (valuesIterator.hasNext()) {
				String attribute = "" + valuesIterator.next();
				if (! SQLwhere.equals("")) {
					SQLwhere += " and ";
				}
				if ((attribute.equals("resource")) && (db_type(getDatabase()).equals("oracle"))) {
					SQLwhere += "\"" + attribute + "\"=?";
				} else {
					if (("" + values.get(attribute)).equals("")) {
						SQLwhere += "(" + Common.SQL_clean("" + attribute) + "=? or " + Common.SQL_clean("" + attribute) + " is null)";
					} else {
						SQLwhere += "" + Common.SQL_clean("" + attribute) + "=?";
					}
				}
			}
			if (! SQLwhere.equals("")) {
				SQL += " where " + SQLwhere;
			}
			if (! SQLorder.equals("")) {
				SQL += " " + SQLorder;
			}
			if (DEBUG) System.out.println("HardCore/DB.select:"+SQL);
			ps = prepareStatement(SQL);
			if (ps != null) {
				int p = 1;
				valuesIterator = values.keySet().iterator();
				while (valuesIterator.hasNext()) {
					String attribute = "" + valuesIterator.next();
					if (numeric_table_column(table, attribute)) {
						try {
							ps.setBigDecimal(p, new BigDecimal("" + values.get(attribute)));
						} catch (Exception ee) {
							try {
								ps.setDouble(p, Common.parse_double(values.get(attribute)));
							} catch (Exception eee) {
								try {
									ps.setFloat(p, Common.parse_float(values.get(attribute)));
								} catch (Exception eeee) {
									try {
										ps.setLong(p, Common.integernumber("" + values.get(attribute)));
									} catch (Exception eeeee) {
										try {
											ps.setInt(p, Common.intnumber("" + values.get(attribute)));
										} catch (Exception eeeeee) {
											if (DEBUG) System.out.println("HardCore/DB.select:"+table+":"+attribute+":"+values.get(attribute));
										}
									}
								}
							}
						}
						p++;
					} else {
						ps.setString(p++, "" + values.get(attribute));
					}
				}
				rs = ps.executeQuery();
				if (rs.next()) {
					record = resultSetToLowerCase(rs);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
		} catch (Exception e) {
			error = "ERROR:DB:select:" + e + " " + getWarnings() + " - " + SQL;
			errors += "<p>" + error + "</p>" + "\r\n";
			if (DEBUG) System.out.println(error);
			if (rs != null) try { rs.close(); } catch (Exception ee) { ; }
			rs = null;
			if (ps != null) try { ps.close(); } catch (Exception ee) { ; }
			ps = null;
		}
		rs = null;
		ps = null;
		return record;
	}



	/* selectWhere */

	public HashMap<String,String> selectWhere(String table, String SQLwhere) {
		HashMap<String,String> record = new HashMap<String,String>();
		String SQL = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			SQL = "select * from " + Common.SQL_clean(table) + " where " + SQLwhere;
			if (DEBUG) System.out.println("HardCore/DB.selectWhere:"+SQL);
			ps = prepareStatement(SQL);
			if (ps != null) {
				rs = ps.executeQuery();
				if (rs.next()) {
					record = resultSetToLowerCase(rs);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
		} catch (Exception e) {
			error = "ERROR:DB:selectWhere:" + e + " " + getWarnings() + " - " + SQL;
			errors += "<p>" + error + "</p>" + "\r\n";
			if (DEBUG) System.out.println(error);
			if (rs != null) try { rs.close(); } catch (Exception ee) { ; }
			rs = null;
			if (ps != null) try { ps.close(); } catch (Exception ee) { ; }
			ps = null;
		}
		rs = null;
		ps = null;
		return record;
	}



	/* selectWhereValues */

	public HashMap<String,String> selectWhereValues(String SQL, HashMap<String,String> values) {
		HashMap<String,String> record = new HashMap<String,String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (DEBUG) System.out.println("HardCore/DB.selectWhereValues:"+SQL);
			ps = prepareStatement(SQL);
			if (ps != null) {
				int p = 1;
				Iterator valuesIterator = values.keySet().iterator();
				while (valuesIterator.hasNext()) {
					String attribute = "" + valuesIterator.next();
					ps.setString(p++, "" + values.get(attribute));
				}
				rs = ps.executeQuery();
				if (rs.next()) {
					record = resultSetToLowerCase(rs);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
		} catch (Exception e) {
			error = "ERROR:DB:selectWhereValues:" + e + " " + getWarnings() + " - " + SQL;
			errors += "<p>" + error + "</p>" + "\r\n";
			if (DEBUG) System.out.println(error);
			if (rs != null) try { rs.close(); } catch (Exception ee) { ; }
			rs = null;
			if (ps != null) try { ps.close(); } catch (Exception ee) { ; }
			ps = null;
		}
		rs = null;
		ps = null;
		return record;
	}



	/* selectAll */

	public LinkedHashMap<String,HashMap<String,String>> selectAll(String table, String id, String value) {
		LinkedHashMap<String,HashMap<String,String>> records = new LinkedHashMap<String,HashMap<String,String>>();
		String SQL = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			SQL = "select * from " + Common.SQL_clean(table) + " where " + Common.SQL_clean(id) + "=?";
			if (DEBUG) System.out.println("HardCore/DB.selectAll:"+SQL);
			ps = prepareStatement(SQL);
			if (ps != null) {
				int p = 1;
				ps.setString(p++, value);
				rs = ps.executeQuery();
				while (rs.next()) {
					HashMap<String,String> record = resultSetToLowerCase(rs);
					records.put("" + records.size(), record);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
		} catch (Exception e) {
			System.out.println("ERROR:DB:selectAll:" + e + " " + getWarnings() + " - " + SQL);
			if (rs != null) try { rs.close(); } catch (Exception ee) { ; }
			rs = null;
			if (ps != null) try { ps.close(); } catch (Exception ee) { ; }
			ps = null;
		}
		rs = null;
		ps = null;
		return records;
	}



	public LinkedHashMap<String,HashMap<String,String>> selectAll(String table, HashMap<String,String> values) {
		return selectAll(table, values, "");
	}
	public LinkedHashMap<String,HashMap<String,String>> selectAll(String table, HashMap<String,String> values, String SQLorder) {
		LinkedHashMap<String,HashMap<String,String>> records = new LinkedHashMap<String,HashMap<String,String>>();
		String SQL = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			SQL = "select * from " + Common.SQL_clean(table);
			String SQLwhere = "";
			Iterator valuesIterator = values.keySet().iterator();
			while (valuesIterator.hasNext()) {
				String attribute = "" + valuesIterator.next();
				if (! SQLwhere.equals("")) {
					SQLwhere += " and ";
				}
				if ((attribute.equals("resource")) && (db_type(getDatabase()).equals("oracle"))) {
					SQLwhere += "\"" + attribute + "\"=?";
				} else {
					if (("" + values.get(attribute)).equals("")) {
						SQLwhere += "(" + Common.SQL_clean("" + attribute) + "=? or " + Common.SQL_clean("" + attribute) + " is null)";
					} else {
						SQLwhere += "" + Common.SQL_clean("" + attribute) + "=?";
					}
				}
			}
			if (! SQLwhere.equals("")) {
				SQL += " where " + SQLwhere;
			}
			if (! SQLorder.equals("")) {
				SQL += " " + SQLorder;
			}
			if (DEBUG) System.out.println("HardCore/DB.selectAll:"+SQL);
			ps = prepareStatement(SQL);
			if (ps != null) {
				int p = 1;
				valuesIterator = values.keySet().iterator();
				while (valuesIterator.hasNext()) {
					String attribute = "" + valuesIterator.next();
					ps.setString(p++, "" + values.get(attribute));
				}
				rs = ps.executeQuery();
				while (rs.next()) {
					HashMap<String,String> record = resultSetToLowerCase(rs);
					records.put("" + records.size(), record);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
		} catch (Exception e) {
			error = "ERROR:DB:selectAll:" + e + " " + getWarnings() + " - " + SQL;
			errors += "<p>" + error + "</p>" + "\r\n";
			if (DEBUG) System.out.println(error);
			if (rs != null) try { rs.close(); } catch (Exception ee) { ; }
			rs = null;
			if (ps != null) try { ps.close(); } catch (Exception ee) { ; }
			ps = null;
		}
		rs = null;
		ps = null;
		return records;
	}



	/* selectAllWhere */

	public LinkedHashMap<String,HashMap<String,String>> selectAllWhere(String table, String SQLwhere) {
		LinkedHashMap<String,HashMap<String,String>> records = new LinkedHashMap<String,HashMap<String,String>>();
		String SQL = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			SQL = "select * from " + Common.SQL_clean(table) + " where " + SQLwhere;
			if (DEBUG) System.out.println("HardCore/DB.selectAllWhere:"+SQL);
			ps = prepareStatement(SQL);
			if (ps != null) {
				rs = ps.executeQuery();
				while (rs.next()) {
					HashMap<String,String> record = resultSetToLowerCase(rs);
					records.put("" + records.size(), record);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
		} catch (Exception e) {
			System.out.println("ERROR:DB:selectAllWhere:" + e + " " + getWarnings() + " - " + SQL);
			if (rs != null) try { rs.close(); } catch (Exception ee) { ; }
			rs = null;
			if (ps != null) try { ps.close(); } catch (Exception ee) { ; }
			ps = null;
		}
		rs = null;
		ps = null;
		return records;
	}



	/* insert */

	public boolean insert(String table, HashMap<String,String> data) {
		String SQL = "";
		PreparedStatement ps = null;
		try {
			String SQLcolumns = "";
			String SQLvalues = "";
			Iterator dataIterator = data.keySet().iterator();
			while (dataIterator.hasNext()) {
				String attribute = "" + dataIterator.next();
				if (! SQLcolumns.equals("")) {
					SQLcolumns += ",";
					SQLvalues += ",";
				}
				if ((attribute.equals("resource")) && (db_type(getDatabase()).equals("oracle"))) {
					SQLcolumns += "\"" + attribute + "\"";
				} else {
					SQLcolumns += "" + Common.SQL_clean("" + attribute);
				}
				SQLvalues += "?";
			}
			SQL = "insert into " + Common.SQL_clean(table) + "(" + SQLcolumns + ") values (" + SQLvalues + ")";
			if (DEBUG) System.out.println("HardCore/DB.insert:"+SQL);
			ps = prepareStatement(SQL);
			if (ps != null) {
				int p = 1;
				dataIterator = data.keySet().iterator();
				while (dataIterator.hasNext()) {
					String attribute = "" + dataIterator.next();
					if (numeric_table_column(table, attribute)) {
						try {
							ps.setBigDecimal(p, new BigDecimal("" + data.get(attribute)));
						} catch (Exception ee) {
							try {
								ps.setDouble(p, Common.parse_double(data.get(attribute)));
							} catch (Exception eee) {
								try {
									ps.setFloat(p, Common.parse_float(data.get(attribute)));
								} catch (Exception eeee) {
									try {
										ps.setLong(p, Common.integernumber("" + data.get(attribute)));
									} catch (Exception eeeee) {
										try {
											ps.setInt(p, Common.intnumber("" + data.get(attribute)));
										} catch (Exception eeeeee) {
											if (DEBUG) System.out.println("HardCore/DB.insert:"+table+":"+attribute+":"+data.get(attribute));
										}
									}
								}
							}
						}
						p++;
					} else {
						ps.setString(p++, "" + data.get(attribute));
					}
				}
				ps.executeUpdate();
				ps.close();
			} else {
				error = "ERROR:DB:insert:" + getWarnings() + " - " + SQL;
				errors += "<p>" + error + "</p>" + "\r\n";
				if (DEBUG) System.out.println(error);
			}
		} catch (Exception e) {
			error = "ERROR:DB:insert:" + e + " " + getWarnings() + " - " + SQL;
			errors += "<p>" + error + "</p>" + "\r\n";
			if (DEBUG) System.out.println(error);
			if (ps != null) try { ps.close(); } catch (Exception ee) { ; }
		} finally {
		}
		return true;
	}



	/* rename */

	public boolean rename(String table, String column, String oldvalue, String newvalue) {
		String SQL = "";
		PreparedStatement ps = null;
		try {
			SQL = "update " + Common.SQL_clean(table) + " set " + Common.SQL_clean(column) + "=? where " + Common.SQL_clean(column) + "=?";
			if (DEBUG) System.out.println("HardCore/DB.rename:"+SQL);
			ps = prepareStatement(SQL);
			if (ps != null) {
				int p = 1;
				ps.setString(p++, newvalue);
				ps.setString(p++, oldvalue);
				ps.executeUpdate();
				ps.close();
				ps = null;
			} else {
				error = "ERROR:DB:rename:" + getWarnings() + " - " + SQL + " - " + oldvalue + " - " + newvalue;
				errors += "<p>" + error + "</p>" + "\r\n";
				if (DEBUG) System.out.println(error);
			}
		} catch (Exception e) {
			error = "ERROR:DB:rename:" + e + " " + getWarnings() + " - " + SQL + " - " + oldvalue + " - " + newvalue;
			if (DEBUG) System.out.println(error);
			if (ps != null) try { ps.close(); ps = null; } catch (Exception ee) { ; }
		}
		return true;
	}



	/* renameWhere */

	public boolean renameWhere(String table, String column, String SQLwhere, String oldvalue, String newvalue) {
		String SQL = "";
		PreparedStatement ps = null;
		try {
			SQL = "update " + Common.SQL_clean(table) + " set " + Common.SQL_clean(column) + "=? where " + Common.SQL_clean(column) + "=? and " + SQLwhere;
			if (DEBUG) System.out.println("HardCore/DB.renameWhere:"+SQL);
			ps = prepareStatement(SQL);
			if (ps != null) {
				int p = 1;
				ps.setString(p++, newvalue);
				ps.setString(p++, oldvalue);
				ps.executeUpdate();
				ps.close();
				ps = null;
			} else {
				error = "ERROR:DB:renameWhere:" + getWarnings() + " - " + SQL + " - " + oldvalue + " - " + newvalue;
				errors += "<p>" + error + "</p>" + "\r\n";
				if (DEBUG) System.out.println(error);
			}
		} catch (Exception e) {
			error = "ERROR:DB:renameWhere:" + e + " " + getWarnings() + " - " + SQL + " - " + oldvalue + " - " + newvalue;
			if (DEBUG) System.out.println(error);
			if (ps != null) try { ps.close(); ps = null; } catch (Exception ee) { ; }
		}
		return true;
	}



	/* update */

	public boolean update(String table, String id, String value, HashMap<String,String> data) {
		String SQL = "";
		PreparedStatement ps = null;
		try {
			String SQLupdate = "";
			Iterator dataIterator = data.keySet().iterator();
			while (dataIterator.hasNext()) {
				String attribute = "" + dataIterator.next();
				if (! SQLupdate.equals("")) {
					SQLupdate += ",";
				}
				if ((attribute.equals("resource")) && (db_type(getDatabase()).equals("oracle"))) {
					SQLupdate += "\"" + attribute + "\"=?";
				} else {
					SQLupdate += "" + Common.SQL_clean("" + attribute) + "=?";
				}
			}
			SQL = "update " + Common.SQL_clean(table) + " set " + SQLupdate + " where " + Common.SQL_clean(id) + "=?";
			if (DEBUG) System.out.println("HardCore/DB.update:"+SQL);
			ps = prepareStatement(SQL);
			if (ps != null) {
				int p = 1;
				dataIterator = data.keySet().iterator();
				while (dataIterator.hasNext()) {
					String attribute = "" + dataIterator.next();
					if (numeric_table_column(table, attribute)) {
						try {
							ps.setBigDecimal(p, new BigDecimal("" + data.get(attribute)));
						} catch (Exception ee) {
							try {
								ps.setDouble(p, Common.parse_double(data.get(attribute)));
							} catch (Exception eee) {
								try {
									ps.setFloat(p, Common.parse_float(data.get(attribute)));
								} catch (Exception eeee) {
									try {
										ps.setLong(p, Common.integernumber("" + data.get(attribute)));
									} catch (Exception eeeee) {
										try {
											ps.setInt(p, Common.intnumber("" + data.get(attribute)));
										} catch (Exception eeeeee) {
											if (DEBUG) System.out.println("HardCore/DB.update:"+table+":"+attribute+":"+data.get(attribute));
										}
									}
								}
							}
						}
						p++;
					} else {
						ps.setString(p++, "" + data.get(attribute));
					}
				}
				ps.setString(p++, "" + value);
				ps.executeUpdate();
				ps.close();
			} else {
				error = "ERROR:DB:update:" + getWarnings() + " - " + SQL;
				errors += "<p>" + error + "</p>" + "\r\n";
				if (DEBUG) System.out.println(error);
			}
		} catch (Exception e) {
			error = "ERROR:DB:update:" + e + " " + getWarnings() + " - " + SQL;
			errors += "<p>" + error + "</p>" + "\r\n";
			if (DEBUG) System.out.println(error);
			if (ps != null) try { ps.close(); } catch (Exception ee) { ; }
		} finally {
		}
		return true;
	}



	/* updateSet */

	public boolean updateSet(String table, String id, String value, String SQLupdate) {
		String SQL = "";
		PreparedStatement ps = null;
		try {
			SQL = "update " + Common.SQL_clean(table) + " set " + SQLupdate + " where " + Common.SQL_clean(id) + "=?";
			if (DEBUG) System.out.println("HardCore/DB.updateSet:"+SQL);
			ps = prepareStatement(SQL);
			int p = 1;
			ps.setString(p++, "" + value);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			error = "ERROR:DB:updateSet:" + e + " " + getWarnings() + " - " + SQL;
			errors += "<p>" + error + "</p>" + "\r\n";
			if (DEBUG) System.out.println(error);
			if (ps != null) try { ps.close(); } catch (Exception ee) { ; }
		} finally {
		}
		return true;
	}



	/* updateWhere */

	public boolean updateWhere(String table, String SQLwhere, HashMap<String,String> data) {
		String SQL = "";
		PreparedStatement ps = null;
		try {
			String SQLupdate = "";
			Iterator dataIterator = data.keySet().iterator();
			while (dataIterator.hasNext()) {
				String attribute = "" + dataIterator.next();
				if (! SQLupdate.equals("")) {
					SQLupdate += ",";
				}
				SQLupdate += "" + Common.SQL_clean("" + attribute) + "=?";
			}
			SQL = "update " + Common.SQL_clean(table) + " set " + SQLupdate + " where " + SQLwhere;
			if (DEBUG) System.out.println("HardCore/DB.updateWhere:"+SQL);
			ps = prepareStatement(SQL);
			if (ps != null) {
				int p = 1;
				dataIterator = data.keySet().iterator();
				while (dataIterator.hasNext()) {
					String attribute = "" + dataIterator.next();
					if (numeric_table_column(table, attribute)) {
						try {
							ps.setBigDecimal(p, new BigDecimal("" + data.get(attribute)));
						} catch (Exception ee) {
							try {
								ps.setDouble(p, Common.parse_double(data.get(attribute)));
							} catch (Exception eee) {
								try {
									ps.setFloat(p, Common.parse_float(data.get(attribute)));
								} catch (Exception eeee) {
									try {
										ps.setLong(p, Common.integernumber("" + data.get(attribute)));
									} catch (Exception eeeee) {
										try {
											ps.setInt(p, Common.intnumber("" + data.get(attribute)));
										} catch (Exception eeeeee) {
											if (DEBUG) System.out.println("HardCore/DB.updateWhere:"+table+":"+attribute+":"+data.get(attribute));
										}
									}
								}
							}
						}
						p++;
					} else {
						ps.setString(p++, "" + data.get(attribute));
					}
				}
				ps.executeUpdate();
				ps.close();
			} else {
				error = "ERROR:DB:updateWhere:" + getWarnings() + " - " + SQL;
				errors += "<p>" + error + "</p>" + "\r\n";
				if (DEBUG) System.out.println(error);
			}
		} catch (Exception e) {
			error = "ERROR:DB:updateWhere:" + e + " " + getWarnings() + " - " + SQL;
			errors += "<p>" + error + "</p>" + "\r\n";
			if (DEBUG) System.out.println(error);
			if (ps != null) try { ps.close(); } catch (Exception ee) { ; }
		} finally {
		}
		return true;
	}



	/* delete */

	public boolean delete(String table, String id, String value) {
		String SQL = "";
		PreparedStatement ps = null;
		try {
			SQL = "delete from " + Common.SQL_clean(table) + " where " + Common.SQL_clean(id) + "=?";
			if (DEBUG) System.out.println("HardCore/DB.delete:"+SQL);
			ps = prepareStatement(SQL);
			int p = 1;
			ps.setString(p++, "" + value);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			error = "ERROR:DB:delete:" + e + " " + getWarnings() + " - " + SQL;
			errors += "<p>" + error + "</p>" + "\r\n";
			if (DEBUG) System.out.println(error);
			if (ps != null) try { ps.close(); } catch (Exception ee) { ; }
		} finally {
		}
		return true;
	}



	public boolean delete(String table, String id, String value, String id2, String value2) {
		String SQL = "";
		PreparedStatement ps = null;
		try {
			SQL = "delete from " + Common.SQL_clean(table) + " where " + Common.SQL_clean(id) + "=? and " + Common.SQL_clean(id2) + "=?";
			if (DEBUG) System.out.println("HardCore/DB.delete:"+SQL);
			ps = prepareStatement(SQL);
			int p = 1;
			ps.setString(p++, "" + value);
			ps.setString(p++, "" + value2);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			error = "ERROR:DB:delete:" + e + " " + getWarnings() + " - " + SQL;
			errors += "<p>" + error + "</p>" + "\r\n";
			if (DEBUG) System.out.println(error);
			if (ps != null) try { ps.close(); } catch (Exception ee) { ; }
		} finally {
		}
		return true;
	}



	/* deleteAll */

	public boolean deleteAll(String table) {
		String SQL = "";
		PreparedStatement ps = null;
		try {
			SQL = "delete from " + Common.SQL_clean(table);
			if (DEBUG) System.out.println("HardCore/DB.deleteAll:"+SQL);
			ps = prepareStatement(SQL);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			error = "ERROR:DB:deleteAll:" + e + " " + getWarnings() + " - " + SQL;
			errors += "<p>" + error + "</p>" + "\r\n";
			if (DEBUG) System.out.println(error);
			if (ps != null) try { ps.close(); } catch (Exception ee) { ; }
		} finally {
		}
		return true;
	}



	/* deleteWhere */

	public boolean deleteWhere(String table, String SQLwhere) {
		String SQL = "";
		PreparedStatement ps = null;
		try {
			SQL = "delete from " + Common.SQL_clean(table) + " where " + SQLwhere;
			if (DEBUG) System.out.println("HardCore/DB.deleteWhere:"+SQL);
			ps = prepareStatement(SQL);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			error = "ERROR:DB:deleteWhere:" + e + " " + getWarnings() + " - " + SQL;
			errors += "<p>" + error + "</p>" + "\r\n";
			if (DEBUG) System.out.println(error);
			if (ps != null) try { ps.close(); } catch (Exception ee) { ; }
		} finally {
		}
		return true;
	}



	public HashMap<String,String> record(ResultSet rs) {
		return resultSetToLowerCase(rs);
	}



	public static HashMap<String,String> resultSetToLowerCase(ResultSet rs) {
		HashMap<String,String> record = new HashMap<String,String>();
		try {
			ResultSetMetaData md = rs.getMetaData();
			int count = md.getColumnCount();
			for (int i=1; i<=count; i++) {
				String columnname = md.getColumnLabel(i).toLowerCase();
				if ((columnname == null) || (columnname.equals(""))) {
					columnname = md.getColumnName(i).toLowerCase();
				}
				if ((columnname.equals("locked")) || (columnname.equals("locked_user")) || (columnname.equals("locked_creator")) || (columnname.equals("locked_developer")) || (columnname.equals("locked_editor")) || (columnname.equals("locked_publisher")) || (columnname.equals("locked_administrator")) || (columnname.equals("locked_schedule")) || (columnname.equals("locked_unschedule")) || (columnname.equals("element_id")) || (columnname.equals("futureint1")) || (columnname.equals("futureint2")) || (columnname.equals("futureint3"))) {
					if (rs != null) {
						try {
							record.put(columnname, "" + rs.getInt(i));
						} catch (NumberFormatException e) {
							record.put(columnname, "0");
						}
					} else {
						record.put(columnname, "0");
					}
				} else {
					String columnvalue = "";
					if (rs != null) {
						try {
							columnvalue = rs.getString(i);
						} catch (NullPointerException e) {
							System.out.println("HardCore/DB.resultSetToLowerCase:"+i+"/"+count+":"+columnname+" - " + e);
//							e.printStackTrace();
							if (rs != null) try { rs.close(); } catch (SQLException ee) { ; }
							rs = null;
						}
					}
					if (columnvalue != null) {
						record.put(columnname, ("" + columnvalue));
					} else {
						record.put(columnname, "");
					}
				}
			}
		} catch (SQLException e) {
		}
		return record;
	}



/*
	DATABASE INITIALIZATION, IMPORT AND EXPORT
*/



	/* importXML */

	public void importXML(PrintWriter fileout, JspWriter out, ServletContext server, String DOCUMENT_ROOT, String ini_database, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, String filename, String column_quote, String column_unquote, String equals, boolean supersuperadmin, String database, HashMap importparams) throws Exception {
		HashMap<String,HashMap<String,String>> inserted_ids = new HashMap<String,HashMap<String,String>>();
		Databases databases = new Databases(text);
		int i;

		boolean file_opened = false;
		boolean xml_defined = false;
		boolean database_opened = false;
		boolean table_opened = false;
		boolean row_opened = false;
		boolean column_opened = false;
		boolean folder_opened = false;
		DataOutputStream file_opened_fh = null;

		Pattern xmlRegEx = Pattern.compile("^<\\?xml version=\"1\\.0\" encoding=\"UTF-8\" standalone=\"yes\" \\?>$");
		Pattern databaseRegEx = Pattern.compile("^<database name=\"(Asbru|HardCore|HeartCore) Web Content Management\">$");
		Pattern databaseEndRegEx = Pattern.compile("</database>$");
		Pattern tableRegEx = Pattern.compile("^ <table name=\"([^\"]+)\">$");
		Pattern tableEndRegEx = Pattern.compile("^ </table>$");
		Pattern rowRegEx = Pattern.compile("^  <row>$");
		Pattern rowEndRegEx = Pattern.compile("^  </row>$");
		Pattern columnRegEx = Pattern.compile("^   <column name=\"([^\"]+)\" value=\"([^\"]*)\" />$");
		Pattern columnNameRegEx = Pattern.compile("name=\"([^\"]+)\"");
		Pattern columnValueRegEx = Pattern.compile("value=\"([^\"]*)\"");
		Pattern columnOpenRegEx = Pattern.compile("^   <column name=\"([^\"]+)\" value=\"([^\"]*)$");
		Pattern columnEndRegEx = Pattern.compile("^([^\"]*)\" />$");
		Pattern tableDataRegEx = Pattern.compile("^data([0-9]+)$");
		Pattern columnDataRegEx = Pattern.compile("^col([0-9]+)$");
		Pattern folderRegEx = Pattern.compile("^ <folder name=\"([^\"]*)\">$");
		Pattern folderEndRegEx = Pattern.compile("^ </folder>$");
		Pattern fileRegEx = Pattern.compile("^  <file name=\"([^\"]+)\">$");
		Pattern fileEndRegEx = Pattern.compile("^  </file>$");

		try {
			out.print("\r\n" + "<p>" + text.display("config.database.importing.started").replaceAll("\\.xxx", ".jsp") + "</p>" + "\r\n");
			out.flush();
			myresponse.flushBuffer();
			fileout.print("\r\n" + "<p>" + text.display("config.database.importing.started").replaceAll("\\.xxx", ".jsp") + "</p>" + "\r\n");
			fileout.flush();
		} catch (IOException ee) {
		}

		String error = "";

		BufferedReader file = null;
		try {
			file = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF8"));
		} catch (Exception e) {
			if (file != null) try { file.close(); } catch (IOException ee) { ; }
			file = null;
		}
		String my_line;
		String my_data = "";
		String my_file = "";
		String my_table = "";
		StringBuffer my_column = new StringBuffer("");
		String my_folder = "";
		HashMap<String,String> row = new HashMap<String,String>();
		if (file != null) {
			while ((my_line = read_line(file)) != null) {
				String my_line_file = "";
				if (file_opened) {
					my_line_file = "" + my_line;
					if (my_line.length() > 10) my_line = "" + my_line.substring(0,10);
				} else {
					Iterator myparams = importparams.keySet().iterator();
					while (myparams.hasNext()) {
						String param = "" + myparams.next();
						String value = "" + importparams.get(param);
						my_line = my_line.replaceAll(param, value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					}
					myparams = importparams.keySet().iterator();
					while (myparams.hasNext()) {
						String param = "" + myparams.next();
						String value = "" + importparams.get(param);
						my_line = my_line.replaceAll(param, value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					}
					myparams = importparams.keySet().iterator();
					while (myparams.hasNext()) {
						String param = "" + myparams.next();
						String value = "" + importparams.get(param);
						my_line = my_line.replaceAll(param, value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					}
				}

				if (xmlRegEx.matcher(my_line).find()) {
					if (xml_defined == true) {
						error = text.display("error.database.import.xml.defined");
					} else {
						xml_defined = true;
					}

				} else if (databaseRegEx.matcher(my_line).find()) {
					if (xml_defined == false) {
						error = text.display("error.database.import.xml.undefined");
					} else if (database_opened == true) {
						error = text.display("error.database.import.database.opened");
					} else {
						database_opened = true;
					}

				} else if (databaseEndRegEx.matcher(my_line).find()) {
					if (xml_defined == false) {
						error = text.display("error.database.import.xml.undefined");
					} else if (database_opened == false) {
						error = text.display("error.database.import.database.unopened");
					} else {
						database_opened = false;
					}

				} else if (tableRegEx.matcher(my_line).find()) {
					if (xml_defined == false) {
						error = text.display("error.database.import.xml.undefined");
					} else if (database_opened == false) {
						error = text.display("error.database.import.database.unopened");
					} else if (table_opened == true) {
						error = text.display("error.database.import.table.opened");
					} else {
						table_opened = true;
						Matcher matches = tableRegEx.matcher(my_line);
						matches.find();
						my_table = matches.group(0).substring(14, matches.group(0).length()-2);
						try {
							out.print("\r\n" + "<p>" + my_table + "</p>" + "\r\n");
							out.flush();
							myresponse.flushBuffer();
							fileout.print("\r\n" + "<p>" + my_table + "</p>" + "\r\n");
							fileout.flush();
						} catch (IOException e) {
						}
					}

				} else if (tableEndRegEx.matcher(my_line).find()) {
					if (xml_defined == false) {
						error = text.display("error.database.import.xml.undefined");
					} else if (database_opened == false) {
						error = text.display("error.database.import.database.unopened");
					} else if (table_opened == false) {
						error = text.display("error.database.import.table.unopened");
					} else {
						table_opened = false;
					}

				} else if (rowRegEx.matcher(my_line).find()) {
					if (xml_defined == false) {
						error = text.display("error.database.import.xml.undefined");
					} else if (database_opened == false) {
						error = text.display("error.database.import.database.unopened");
					} else if (table_opened == false) {
						error = text.display("error.database.import.tableunopened");
					} else if (row_opened == true) {
						error = text.display("error.database.import.row.opened");
					} else {
						row_opened = true;
						row = new HashMap<String,String>();
					}

				} else if (rowEndRegEx.matcher(my_line).find()) {
					if (xml_defined == false) {
						error = text.display("error.database.import.xml.undefined");
					} else if (database_opened == false) {
						error = text.display("error.database.import.database.unopened");
					} else if (table_opened == false) {
						error = text.display("error.database.import.table.unopened");
					} else if (row_opened == false) {
						error = text.display("error.database.import.row.unopened");
					} else {
						insert_row(fileout, out, myrequest, myresponse, my_table, row, inserted_ids, column_quote, column_unquote, equals, tableDataRegEx, columnDataRegEx, DOCUMENT_ROOT, this, myconfig, database, databases);
						row = null;
						row_opened = false;
					}

				} else if (columnRegEx.matcher(my_line).find()) {
					if (xml_defined == false) {
						error = text.display("error.database.import.xml.undefined");
					} else if (database_opened == false) {
						error = text.display("error.database.import.database.unopened");
					} else if (table_opened == false) {
						error = text.display("error.database.import.table.unopened");
					} else if (row_opened == false) {
						error = text.display("error.database.import.row.unopened");
					} else if (column_opened == true) {
						error = text.display("error.database.import.column.opened");
					} else {
						Matcher matches = columnNameRegEx.matcher(my_line);
						matches.find();
						String my_column_name = matches.group(0).substring(6, matches.group(0).length()-1);
						matches = columnValueRegEx.matcher(my_line);
						matches.find();
						String my_column_value = matches.group(0).substring(7, matches.group(0).length()-1);
						if (! my_column_name.equals("")) {
							row.put(my_column_name, my_column_value);
						}
					}

				} else if (columnOpenRegEx.matcher(my_line).find()) {
					if (xml_defined == false) {
						error = text.display("error.database.import.xml.undefined");
					} else if (database_opened == false) {
						error = text.display("error.database.import.database.unopened");
					} else if (table_opened == false) {
						error = text.display("error.database.import.table.unopened");
					} else if (row_opened == false) {
						error = text.display("error.database.import.row.unopened");
					} else if (column_opened == true) {
						error = text.display("error.database.import.column.opened");
					} else {
						column_opened = true;
						my_column = new StringBuffer("" + my_line);
					}

				} else if (columnEndRegEx.matcher(my_line).find()) {
					if (xml_defined == false) {
						error = text.display("error.database.import.xml.undefined");
					} else if (database_opened == false) {
						error = text.display("error.database.import.database.unopened");
					} else if (table_opened == false) {
						error = text.display("error.database.import.table.unopened");
					} else if (row_opened == false) {
						error = text.display("error.database.import.row.unopened");
					} else if (column_opened == false) {
						error = text.display("error.database.import.column.unopened");
					} else {
						column_opened = false;
						my_column.append("\r\n" + my_line);
						Matcher matches = columnNameRegEx.matcher(""+my_column);
						matches.find();
						String my_column_name = matches.group(0).substring(6, matches.group(0).length()-1);
						matches = columnValueRegEx.matcher("" + my_column);
						matches.find();
						String my_column_value = matches.group(0).substring(7, matches.group(0).length()-1);
						if (! my_column_name.equals("")) {
							row.put(my_column_name, my_column_value);
						}
					}

				} else if (column_opened == true) {
					my_column.append("\r\n" + my_line);

				} else if (folderRegEx.matcher(my_line).find()) {
					if (xml_defined == false) {
						error = text.display("error.database.import.xml.undefined");
					} else if (database_opened == false) {
						error = text.display("error.database.import.database.unopened");
					} else if (folder_opened == true) {
						error = text.display("error.database.import.folder.opened");
					} else {
						folder_opened = true;
						Matcher matches = folderRegEx.matcher(my_line);
						matches.find();
						my_folder = matches.group(0).substring(15, matches.group(0).length()-2);
						File fh = new File(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + my_folder);
						if (! fh.exists()) {
							new File(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + my_folder).mkdirs();
						}
						try {
							out.print("\r\n" + "<p>/" + my_folder + "</p>" + "\r\n");
							out.flush();
							myresponse.flushBuffer();
							fileout.print("\r\n" + "<p>/" + my_folder + "</p>" + "\r\n");
							fileout.flush();
						} catch (IOException e) {
						}
					}

				} else if (folderEndRegEx.matcher(my_line).find()) {
					if (xml_defined == false) {
						error = text.display("error.database.import.xml.undefined");
					} else if (database_opened == false) {
						error = text.display("error.database.import.database.unopened");
					} else if (folder_opened == false) {
						error = text.display("error.database.import.folder.unopened");
					} else {
						folder_opened = false;
					}

				} else if (fileRegEx.matcher(my_line).find()) {
					if (xml_defined == false) {
						error = text.display("error.database.import.xml.undefined");
					} else if (database_opened == false) {
						error = text.display("error.database.import.database.unopened");
					} else if (folder_opened == false) {
						error = text.display("error.database.import.folder.unopened");
					} else if (file_opened == true) {
						error = text.display("error.database.import.file.opened");
					} else {
						file_opened = true;
						Matcher matches = fileRegEx.matcher(my_line);
						matches.find();
						my_file = matches.group(0).substring(14, matches.group(0).length()-2);
						my_data = "";
						try {
							out.print(". ");
							out.flush();
							myresponse.flushBuffer();
							fileout.print(". ");
							fileout.flush();
							file_opened_fh = new DataOutputStream(new FileOutputStream(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + my_folder + my_file));
						} catch (Exception e) {
						}
					}

				} else if (fileEndRegEx.matcher(my_line).find()) {
					if (xml_defined == false) {
						error = text.display("error.database.import.xml.undefined");
					} else if (database_opened == false) {
						error = text.display("error.database.import.database.unopened");
					} else if (folder_opened == false) {
						error = text.display("error.database.import.folder.unopened");
					} else if (file_opened == false) {
						error = text.display("error.database.import.file.unopened");
					} else {
						file_opened = false;
						if (supersuperadmin || (! Pattern.compile(blocked_files).matcher(my_file).find())) {
							if (file_opened_fh != null) {
								try {
									file_opened_fh.close();
									file_opened_fh = null;
								} catch (FileNotFoundException e) {
								} catch (IOException e) {
								}
								Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/upload"), "\"" + myconfig.get(this, "URLrootpath").replaceAll("^/", "") + my_folder + my_file + "\"" + " " + "\"" + myconfig.get(this, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:importXML1\"", "", server, mysession, myrequest, myresponse);
							}
						}
						my_data = "";
					}

				} else if (file_opened == true) {
					my_data = "" + my_data + my_line_file;
					while (! my_data.equals("")) {
						byte[] filedata;
						if (my_data.length()<2048) {
							filedata = Base64.decode(my_data);
							my_data = "";
						} else {
							filedata = Base64.decode(my_data.substring(0,1024));
							my_data = my_data.substring(1024);
						}
						if (file_opened_fh != null) {
							try {
								file_opened_fh.write(filedata, 0, filedata.length);
							} catch (FileNotFoundException e) {
							} catch (IOException e) {
							}
						}
					}
					my_data = "";

				} else if (my_line.equals("")) {
					// ignore

				} else {
					try {
						out.println("\r\n" + "ERROR:" + html.encode(my_line) + "\r\n");
						out.flush();
						myresponse.flushBuffer();
						fileout.println("\r\n" + "ERROR:" + html.encode(my_line) + "\r\n");
						fileout.flush();
					} catch (IOException ee) {
					}
				}
			}
			try {
				file.close();
				file = null;
			} catch (IOException ee) {
			}
		}

		try {
			out.print("\r\n" + "<p>" + text.display("config.database.importing.references") + "</p>" + "\r\n");
			out.flush();
			myresponse.flushBuffer();
			fileout.print("\r\n" + "<p>" + text.display("config.database.importing.references") + "</p>" + "\r\n");
			fileout.flush();
		} catch (IOException ee) {
		}

		adjust_dependencies(fileout, out, server, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, inserted_ids, equals);

		try {
			out.print("\r\n" + "<p>" + text.display("config.database.importing.metainfo") + "</p>" + "\r\n");
			out.flush();
			myresponse.flushBuffer();
			fileout.print("\r\n" + "<p>" + text.display("config.database.importing.metainfo") + "</p>" + "\r\n");
			fileout.flush();
		} catch (IOException ee) {
		}

		Content metacontent = new Content(text);
		metacontent.update_metainfo_all(this, myconfig);

		try {
			out.print("\r\n" + "<p>" + text.display("config.database.importing.completed") + "</p>" + "\r\n");
			out.flush();
			myresponse.flushBuffer();
			fileout.print("\r\n" + "<p>" + text.display("config.database.importing.completed") + "</p>" + "\r\n");
			fileout.flush();
		} catch (IOException ee) {
		}

		if (ini_database.equals("default")) {
			Configuration my_config = new Configuration();
			inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", ini_database, "database_version", my_config.get(this, "database_version"));
			inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", ini_database, "superadmin", my_config.get(this, "superadmin"));
			inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", ini_database, "default_page", my_config.get(this, "default_page"));
			inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", ini_database, "default_template", my_config.get(this, "default_template"));
			inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", ini_database, "default_stylesheet", my_config.get(this, "default_stylesheet"));
			inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", ini_database, "default_version", my_config.get(this, "default_version"));
		}
	}



	/* min_inserted_id */

	private int min_inserted_id(HashMap<String,HashMap<String,String>> inserted_ids, String table) {
		int id = max_inserted_id(inserted_ids, table);
		if (inserted_ids.containsKey(table)) {
			Iterator keys = ((HashMap<String,String>)inserted_ids.get(table)).keySet().iterator();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				int value = Common.parse_int(((HashMap<String,String>)inserted_ids.get(table)).get(key));
				if ((! key.equals("")) && (value != 0)) {
					if (id > value) {
						id = value;
					}
				}
			}
		}
		return id;
	}



	/* max_inserted_id */

	private int max_inserted_id(HashMap<String,HashMap<String,String>> inserted_ids, String table) {
		int id = 0;
		if (inserted_ids.containsKey(table)) {
			Iterator keys = ((HashMap<String,String>)inserted_ids.get(table)).keySet().iterator();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				int value = Common.parse_int(((HashMap<String,String>)inserted_ids.get(table)).get(key));
				if ((! key.equals("")) && (value != 0)) {
					if (id < value) {
						id = value;
					}
				}
			}
		}
		return id;
	}



	/* sql_insert_into */

	private String sql_insert_into(PrintWriter fileout, JspWriter out, Request myrequest, Response myresponse, String table, HashMap<String,String> SQLdata, String auto_id_column, String auto_id_value, String column_quote, String column_unquote, String equals) {
		if (auto_id_value == null) { auto_id_value = ""; }
		String sql_insert_into_value = "" + auto_id_value;

		PreparedStatement ps = null;
		String[] insertcolumns = (String[])SQLdata.keySet().toArray(new String[0]);
		String SQLcolumns = "";
		String SQLprepare = "";
		HashMap<String,String> mySQLdata = new HashMap<String,String>();
		for (int i=0; i<insertcolumns.length; i++) {
			String column = "" + insertcolumns[i];
			if (table.equals("users") && (column.equals("password")) && User.DIGEST) {
				SQLdata.put(column, this.digest((String)SQLdata.get(column), 250));
			} else if (table.equals("users") && (column.equals("keywords") || column.equals("description") || column.equals("password") || column.equals("email") || column.equals("card_type") || column.equals("card_number") || column.equals("card_issuedmonth") || column.equals("card_issuedyear") || column.equals("card_expirymonth") || column.equals("card_expiryyear") || column.equals("card_name") || column.equals("card_cvc") || column.equals("card_issue") || column.equals("card_postalcode") || column.equals("delivery_name") || column.equals("delivery_organisation") || column.equals("delivery_address") || column.equals("delivery_postalcode") || column.equals("delivery_city") || column.equals("delivery_state") || column.equals("delivery_country") || column.equals("delivery_phone") || column.equals("delivery_fax") || column.equals("delivery_email") || column.equals("delivery_website") || column.equals("invoice_name") || column.equals("invoice_organisation") || column.equals("invoice_address") || column.equals("invoice_postalcode") || column.equals("invoice_city") || column.equals("invoice_state") || column.equals("invoice_country") || column.equals("invoice_phone") || column.equals("invoice_fax") || column.equals("invoice_email") || column.equals("invoice_website") || column.equals("notes") || column.equals("userinfo"))) {
				SQLdata.put(column, this.encrypt((String)SQLdata.get(column)));
			}
			String value = "" + SQLdata.get(column);
			if (! column.equals(auto_id_column)) {
				if (! SQLcolumns.equals("")) {
					SQLcolumns += ", ";
				}
				if (! SQLprepare.equals("")) {
					SQLprepare += ", ";
				}
				if ((db_type(getDatabase()).equals("oracle")) && (column.equals("resource"))) {
					SQLcolumns += "\"" + Common.SQL_clean(column) + "\"";
					mySQLdata.put("\"" + Common.SQL_clean(column) + "\"", value);
				} else {
					SQLcolumns += column_quote + Common.SQL_clean(column) + column_unquote;
					mySQLdata.put("" + column_quote + Common.SQL_clean(column) + column_unquote, value);
				}
				SQLprepare += "?";
			}
		}

		if (table.equals("config")) {
			if ((! SQLdata.get("configname").equals("URLrootpath")) && (! SQLdata.get("configname").equals("URLfilepath")) && (! SQLdata.get("configname").equals("URLimagepath")) && (! SQLdata.get("configname").equals("URLstylesheetpath")) && (! SQLdata.get("configname").equals("URLuploadpath"))) {
				Configuration myconfig = new Configuration();
				myconfig.set(this, "" + SQLdata.get("configname"), "" + SQLdata.get("configvalue"));
			}
		} else {
			insert(table, mySQLdata);
		}

		if (! auto_id_column.equals("")) {
			sql_insert_into_value = inserted_id(fileout, out, myrequest, myresponse, table, auto_id_column, auto_id_value, insertcolumns, SQLdata);
		}
		try {
			out.print(". ");
			out.flush();
			myresponse.flushBuffer();
			fileout.print(". ");
			fileout.flush();
		} catch (IOException ee) {
		}
		return sql_insert_into_value;
	}



	/* inserted_id */

	private String inserted_id(PrintWriter fileout, JspWriter out, Request myrequest, Response myresponse, String table, String auto_id_column, String auto_id_value, String[] insertcolumns, HashMap SQLdata) {
		String id = "";
		String SQLwhere = "";
		for (int i=0; i<insertcolumns.length; i++) {
			String column = "" + insertcolumns[i];
			if (! column.equals(auto_id_column)) {
				if ((SQLdata.get(column) == null) || ((""+SQLdata.get(column)).equals(""))) {
					if (! SQLwhere.equals("")) {
						SQLwhere += " and ";
					}
					if (numeric_table_column(table, column)) {
						SQLwhere += "(" + Common.SQL_clean(column) + " is null or " + Common.SQL_clean(column) + " like '' or " + Common.SQL_clean(column) + " = 0)";
					} else {
						if ((db_type(getDatabase()).equals("oracle")) && (column.equals("resource"))) {
							SQLwhere += "(\"" + Common.SQL_clean(column) + "\" is null or \"" + Common.SQL_clean(column) + "\" like '')";
						} else {
							SQLwhere += "(" + Common.SQL_clean(column) + " is null or " + Common.SQL_clean(column) + " like '')";
						}
					}
				} else if ((SQLdata.get(column) != null) && (("" + SQLdata.get(column)).length() < 256) && (("" + SQLdata.get(column)).indexOf("\\") == -1)) {
					if (! SQLwhere.equals("")) {
						SQLwhere += " and ";
					}
					if (numeric_table_column(table, column)) {
						SQLwhere += Common.SQL_clean(column) + " = " + SQLdata.get(column);
					} else {
						if ((db_type(getDatabase()).equals("oracle")) && (column.equals("resource"))) {
							SQLwhere += "\"" + Common.SQL_clean(column) + "\" like " + quote("" + SQLdata.get(column));
						} else {
							SQLwhere += Common.SQL_clean(column) + " like " + quote("" + SQLdata.get(column));
						}
					}
				}
			}
		}
		Statement st = null;
		ResultSet rs = null;
		try {
			st = createStatement();
			String SQL = "select * from " + Common.SQL_clean(table) + " where " + SQLwhere + " order by " + Common.SQL_clean(auto_id_column) + " desc";
			if (DEBUG) System.out.println("HardCore/DB.inserted_id:"+SQL);
			rs = st.executeQuery(SQL);
	 		boolean inserted_row = false;
	 		String row_auto_id_value = "";
			while ((rs.next()) && (! inserted_row)) {
				HashMap<String,String> row = DB.resultSetToLowerCase(rs);
				inserted_row = true;
				Iterator rowcolumns = row.keySet().iterator();
				while (rowcolumns.hasNext()) {
					String column = (String) rowcolumns.next();
					String value = "" + row.get(column);
					if (! column.equals(auto_id_column)) {
						if (SQLdata.get(column) != null) {
							if (numeric_table_column(table, column)) {
								float mySQLdata = Common.parse_float(SQLdata.get(column));
								float myvalue = Common.parse_float(value);
								if (mySQLdata != myvalue) {
									inserted_row = false;
if (DEBUG) System.out.println("HardCore/DB.inserted_id:numeric:"+table+":"+auto_id_column+":"+auto_id_value+":"+column+":::"+value+":::"+myvalue+":::"+SQLdata.get(column)+":::"+mySQLdata);
								}
							} else if (! value.replaceAll("\r\n\r\n","\r\n").replaceAll("\n\n","\n").equals(("" + SQLdata.get(column)).replaceAll("\r\n\r\n","\r\n").replaceAll("\n\n","\n"))) {
								inserted_row = false;
if (DEBUG) System.out.println("HardCore/DB.inserted_id:textual:"+table+":"+auto_id_column+":"+auto_id_value+":"+column+":::"+(value.replaceAll("\r\n\r\n","\r\n").replaceAll("\n\n","\n"))+":::"+(("" + SQLdata.get(column)).replaceAll("\r\n\r\n","\r\n").replaceAll("\n\n","\n")));
							}
						} else {
							if (numeric_table_column(table, column)) {
								float mySQLdata = Common.parse_float(SQLdata.get(column));
								float myvalue = Common.parse_float(value);
								if (mySQLdata != myvalue) {
									inserted_row = false;
if (DEBUG) System.out.println("HardCore/DB.inserted_id:numeric:null:"+table+":"+auto_id_column+":"+auto_id_value+":"+column+":::"+value);
								}
							} else if (! value.equals("")) {
								inserted_row = false;
if (DEBUG) System.out.println("HardCore/DB.inserted_id:textual:null:"+table+":"+auto_id_column+":"+auto_id_value+":"+column+":::"+value);
							}
						}
					} else {
						row_auto_id_value = "" + value;
					}
				}
				if (inserted_row) {
					id = "" + row_auto_id_value;
				}
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
			if (! inserted_row) {
if (DEBUG) System.out.println("HardCore/DB.inserted_id:"+table+":"+auto_id_column+":"+auto_id_value+":"+"select * from " + Common.SQL_clean(table) + " where " + SQLwhere + " order by " + Common.SQL_clean(auto_id_column) + " desc");
				try {
					out.print("\r\n" + " % " + auto_id_value + " % " + "\r\n");
					out.flush();
					myresponse.flushBuffer();
					fileout.print("\r\n" + " % " + auto_id_value + " % " + "\r\n");
					fileout.flush();
				} catch (IOException e) {
				}
			}
		} catch (SQLException e) {
			System.out.println("HardCore/DB.inserted_id:"+table+":"+auto_id_column+":"+id+":"+e);
			if (rs != null) try { rs.close(); } catch (SQLException ee) { ; }
			rs = null;
			if (st != null) try { st.close(); } catch (SQLException ee) { ; }
			st = null;
		}
		rs = null;
		st = null;
		return id;
	}



	/* numeric_table_column */

	private boolean numeric_table_column(String table, String column) {
		if ((tableinfo.get(table) != null) && (((HashMap)tableinfo.get(table)).get(column) != null) && ((""+((HashMap)tableinfo.get(table)).get(column)).equals("number"))) {
			return true;
		} else if ((table.equals("heatmaps")) && ((column.equals("id")) || (column.equals("action")))) {
			return false;
		} else if ((table.equals("heatmaps")) && ((column.equals("x")) || (column.equals("y")) || (column.equals("w")) || (column.equals("h")))) {
			return true;
		} else if ((column.equals("content_id")) || (column.equals("content_archiveid")) || (column.equals("element_id")) || (column.equals("element_order")) || (column.equals("dependency_id")) || (column.equals("id")) || (column.equals("failed")) || (column.equals("locked")) || (column.equals("locked_user")) || (column.equals("locked_creator")) || (column.equals("locked_developer")) || (column.equals("locked_editor")) || (column.equals("locked_publisher")) || (column.equals("locked_administrator")) || (column.equals("locked_schedule")) || (column.equals("locked_unschedule")) || (column.equals("user_id")) || (column.equals("order_id")) || (column.equals("product_stock_amount")) || (column.equals("product_lowstock_amount")) || (column.equals("product_restocked_amount"))) {
			return true;
		} else if ((column.equals("futureint1")) || (column.equals("futureint2")) || (column.equals("futureint3"))) {
			return true;
		} else if ((table.equals("orderitems")) && ((column.equals("order_id")) || (column.equals("product_id")) || (column.equals("item_quantity")) || (column.equals("item_subtotal")) || (column.equals("item_subtotal_base")) || (column.equals("item_total")) || (column.equals("item_total_base")) || (column.equals("discount_total")) || (column.equals("discount_total_base")) || (column.equals("shipping_total")) || (column.equals("shipping_total_base")) || (column.equals("tax_total")) || (column.equals("tax_total_base")))) {
			return true;
		} else if ((table.equals("orders")) && ((column.equals("createdyear")) || (column.equals("createdmonth")) || (column.equals("createdday")) || (column.equals("createdweek")) || (column.equals("createdweekday")) || (column.equals("createdhour")) || (column.equals("createdmin")) || (column.equals("createdsec")) || (column.equals("sessionduration")) || (column.equals("requestid")) || (column.equals("refererid")) || (column.equals("refererduration")) || (column.equals("locked_user")) || (column.equals("locked_creator")) || (column.equals("locked_editor")) || (column.equals("locked_publisher")) || (column.equals("locked_administrator")) || (column.equals("order_quantity")) || (column.equals("order_subtotal")) || (column.equals("order_subtotal_base")) || (column.equals("tax_total")) || (column.equals("tax_total_base")) || (column.equals("shipping_total")) || (column.equals("shipping_total_base")) || (column.equals("discount_total")) || (column.equals("discount_total_base")) || (column.equals("order_total")) || (column.equals("order_total_base")))) {
			return true;
		} else if ((table.equals("usagelog")) && ((column.equals("datetimeyear")) || (column.equals("datetimemonth")) || (column.equals("datetimeday")) || (column.equals("datetimeweek")) || (column.equals("datetimeweekday")) || (column.equals("datetimehour")) || (column.equals("datetimemin")) || (column.equals("datetimesec")) || (column.equals("visitorduration")) || (column.equals("sessionduration")) || (column.equals("requestid")) || (column.equals("refererid")) || (column.equals("refererduration")) || (column.equals("hits")) || (column.equals("pageviews")) || (column.equals("visits")) || (column.equals("visitors")) || (column.equals("clienthosts")))) {
			return true;
		} else if ((table.equals("users")) && (column.equals("product_points"))) {
			return true;
		} else if ((table.equals("discounts")) && ((column.equals("user_points_min")) || (column.equals("user_points_cost")))) {
			return true;
		} else if (((table.equals("discounts")) || (table.equals("shipping")) || (table.equals("tax"))) && ((column.equals("quantity_from")) || (column.equals("quantity_to")) || (column.equals("total_from")) || (column.equals("total_to")) || (column.equals("product_weight_from")) || (column.equals("product_weight_to")) || (column.equals("product_volume_from")) || (column.equals("product_volume_to")) || (column.equals("product_width_from")) || (column.equals("product_width_to")) || (column.equals("product_height_from")) || (column.equals("product_height_to")) || (column.equals("product_depth_from")) || (column.equals("product_depth_to")) || (column.equals("total_weight_from")) || (column.equals("total_weight_to")) || (column.equals("total_volume_from")) || (column.equals("total_volume_to")) || (column.equals("total_width_from")) || (column.equals("total_width_to")) || (column.equals("total_height_from")) || (column.equals("total_height_to")) || (column.equals("total_depth_from")) || (column.equals("total_depth_to")) || (column.equals("discount_quantity")) || (column.equals("discount_quantity2")) || (column.equals("discount_amount")) || (column.equals("ship_order")) || (column.equals("ship_item")) || (column.equals("ship_percent")) || (column.equals("ship_total")) || (column.equals("tax_order")) || (column.equals("tax_item")) || (column.equals("tax_percent")) || (column.equals("tax_total")) || (column.equals("user_points_min")) || (column.equals("user_points_cost")))) {
			return true;
		} else if (columntype(table, column).equals("numeric")) {
			return true;
		} else {
			return false;
		}
	}



	/* insert_row */

	private void insert_row(PrintWriter fileout, JspWriter out, Request myrequest, Response myresponse, String my_table, HashMap row, HashMap<String,HashMap<String,String>> inserted_ids, String column_quote, String column_unquote, String equals, Pattern tableDataRegEx, Pattern columnDataRegEx, String DOCUMENT_ROOT, DB db, Configuration myconfig, String database, Databases databases) {
		String auto_id_column= "";
		if ((my_table.equals("content_public")) || (my_table.equals("config")) || (my_table.equals("content_elements")) || (my_table.equals("content_archive_elements")) || (my_table.equals("content_public_elements")) || (my_table.equals("usergroups2")) || (my_table.equals("usertypes2"))) {
			auto_id_column = "";
		} else if (my_table.equals("content_archive")) {
			auto_id_column = "archiveid";
		} else {
			auto_id_column = "id";
		}

		HashMap<String,String> SQLdata = new HashMap<String,String>();
		String SQLwhere = "";
		Iterator rowendcolumns = row.keySet().iterator();
		while (rowendcolumns.hasNext()) {
			String column = (String) rowendcolumns.next();
			String value = "" + html.decode("" + row.get(column));
			if (column.equals(auto_id_column)) {
				// ignore
			} else {
				SQLwhere = add_column(SQLwhere, my_table, column, value, SQLdata, inserted_ids, equals, db, myconfig, databases, tableDataRegEx, columnDataRegEx);
			}
		}

		if (my_table.equals("config")) {
			if ((my_table.equals("config")) && ((SQLdata.get("configname").equals("default_page")) || (SQLdata.get("configname").equals("default_page_nonexisting")) || (SQLdata.get("configname").equals("default_page_unpublished")) || (SQLdata.get("configname").equals("default_page_expired")) || (SQLdata.get("configname").equals("default_template")) || (SQLdata.get("configname").equals("default_stylesheet")) || (SQLdata.get("configname").equals("default_login")) || (SQLdata.get("configname").equals("default_checkout_entry")) || (SQLdata.get("configname").equals("default_checkout_page")) || (SQLdata.get("configname").equals("default_empty_page")) || (SQLdata.get("configname").equals("default_complete_entry")) || (SQLdata.get("configname").equals("default_complete_page")) || (SQLdata.get("configname").equals("default_confirm_entry")) || (SQLdata.get("configname").equals("default_confirm_page")) || (SQLdata.get("configname").equals("default_confirmation_entry")) || (SQLdata.get("configname").equals("default_confirmation_page")) || (SQLdata.get("configname").equals("default_guestbook_entry")) || (SQLdata.get("configname").equals("default_guestbook_page")) || (SQLdata.get("configname").equals("default_notification_entry")) || (SQLdata.get("configname").equals("default_notification_page")) || (SQLdata.get("configname").equals("default_notify_entry")) || (SQLdata.get("configname").equals("default_notify_page")) || (SQLdata.get("configname").equals("default_ordertracking_entry")) || (SQLdata.get("configname").equals("default_ordertracking_page")) || (SQLdata.get("configname").equals("default_searchresults_entry")) || (SQLdata.get("configname").equals("default_searchresults_page")) || (SQLdata.get("configname").equals("default_shopcart_entry")) || (SQLdata.get("configname").equals("default_shopcart_page")) || (SQLdata.get("configname").equals("default_shopcartsummary_entry")) || (SQLdata.get("configname").equals("default_shopcartsummary_page")) || (SQLdata.get("configname").equals("default_list_entry")) || (SQLdata.get("configname").equals("default_publish_ready")) || (SQLdata.get("configname").equals("default_register_confirmation_page")) || (SQLdata.get("configname").equals("default_register_notification_page")) || (SQLdata.get("configname").equals("default_personal_admin_page")) || (SQLdata.get("configname").equals("retrieve_password_confirmation")) || (SQLdata.get("configname").equals("retrieve_password_email")) || (SQLdata.get("configname").equals("retrieve_password_error")) || (SQLdata.get("configname").equals("retrieve_password_page")) || (SQLdata.get("configname").equals("default_list_entry")) || (SQLdata.get("configname").equals("default_publish_ready")))) {
				if ((((HashMap<String,String>)inserted_ids.get("content")) != null) && (((HashMap<String,String>)inserted_ids.get("content")).get(SQLdata.get("configvalue")) != null)) {
					SQLdata.put("configvalue", ((HashMap<String,String>)inserted_ids.get("content")).get(SQLdata.get("configvalue")));
					SQLwhere = "configname" + equals + quote("" + SQLdata.get("configname")) + " and " + "configvalue" + equals + quote("" + SQLdata.get("configvalue"));
				} else {
					SQLdata.put("configvalue", "");
					SQLwhere = "configname" + equals + quote("" + SQLdata.get("configname")) + " and " + "configvalue" + equals + quote("" + SQLdata.get("configvalue"));
				}
			} else if ((my_table.equals("config")) && ((SQLdata.get("configname").equals("print_contents")) || (SQLdata.get("configname").equals("print_products")) || (SQLdata.get("configname").equals("print_orders")) || (SQLdata.get("configname").equals("print_users")))) {
				SQLdata.put("configvalue", adjust_ids("" + SQLdata.get("configvalue"), ((HashMap)inserted_ids.get("content"))));
				SQLwhere = "configname" + equals + quote("" + SQLdata.get("configname")) + " and " + "configvalue" + equals + quote("" + SQLdata.get("configvalue"));
			} else if ((my_table.equals("config")) && (SQLdata.get("configname").equals("default_currency"))) {
				if ((((HashMap<String,String>)inserted_ids.get("currencies")) != null) && (((HashMap<String,String>)inserted_ids.get("currencies")).get(SQLdata.get("configvalue")) != null)) {
					SQLdata.put("configvalue", ((HashMap<String,String>)inserted_ids.get("currencies")).get(SQLdata.get("configvalue")));
					SQLwhere = "configname" + equals + quote("" + SQLdata.get("configname")) + " and " + "configvalue" + equals + quote("" + SQLdata.get("configvalue"));
				} else {
					SQLdata.put("configvalue", "");
					SQLwhere = "configname" + equals + quote("" + SQLdata.get("configname")) + " and " + "configvalue" + equals + quote("" + SQLdata.get("configvalue"));
				}
			}
		}

		if (((my_table.equals("content_archive")) || (my_table.equals("content_public"))) && (Common.parse_int(SQLdata.get("id")) == 0)) {
			try {
				out.print("\r\n" + " % " + row.get("id") + " : " + row.get("title") + " % " + "\r\n");
				out.flush();
				myresponse.flushBuffer();
				fileout.print("\r\n" + " % " + row.get("id") + " : " + row.get("title") + " % " + "\r\n");
				fileout.flush();
			} catch (IOException e) {
			}
		} else if (auto_id_column.equals("")) {
			String auto_id_value = "";
			if (SQLdata.get("id") != null) auto_id_value = "" + SQLdata.get("id");
			int inserted_id = Common.parse_int(sql_insert_into(fileout, out, myrequest, myresponse, my_table, SQLdata, "", auto_id_value, column_quote, column_unquote, equals));
		} else if (tableDataRegEx.matcher(my_table).find()) {
			String auto_id_value = "";
			if (row.get(auto_id_column) != null) auto_id_value = "" + row.get(auto_id_column);
			int inserted_id = Common.parse_int(sql_insert_into(fileout, out, myrequest, myresponse, "data" + ((HashMap<String,String>)inserted_ids.get("data")).get(my_table.substring(4)), SQLdata, auto_id_column, auto_id_value, column_quote, column_unquote, equals));
			if (! inserted_ids.containsKey("data" + ((HashMap<String,String>)inserted_ids.get("data")).get(my_table.substring(4)))) {
				inserted_ids.put("data" + ((HashMap<String,String>)inserted_ids.get("data")).get(my_table.substring(4)), new HashMap<String,String>());
			}
			((HashMap<String,String>)inserted_ids.get("data" + ((HashMap<String,String>)inserted_ids.get("data")).get(my_table.substring(4)))).put("" + row.get(auto_id_column), "" + inserted_id);
		} else {
			String auto_id_value = "";
			if (row.get(auto_id_column) != null) auto_id_value = "" + row.get(auto_id_column);
			int inserted_id = Common.parse_int(sql_insert_into(fileout, out, myrequest, myresponse, my_table, SQLdata, auto_id_column, auto_id_value, column_quote, column_unquote, equals));
			if (! inserted_ids.containsKey(my_table)) {
				inserted_ids.put(my_table, new HashMap<String,String>());
			}
			((HashMap<String,String>)inserted_ids.get(my_table)).put("" + row.get(auto_id_column), "" + inserted_id);

			if (my_table.equals("data")) {
				databases = new Databases(text);
				databases.read(db, myconfig, "" + inserted_id);
				UCmaintainDatabases maintainDatabases = new UCmaintainDatabases(text);
				tableinfo.put("data"+databases.getId(), maintainDatabases.doCreateData(out, DOCUMENT_ROOT, db, database, databases));
			}

//			inserted_id = 0
//			inserted_junk = 0
//			While (inserted_id < CLng(row.Item(auto_id_column)))
//				If ((inserted_id > 0) AND (inserted_junk = 0)) {
//					inserted_junk = inserted_id
//				}
//				inserted_id = CLng(sql_insert_into(my_table, SQLdata, auto_id_column, row.Item(auto_id_column), column_quote, column_unquote, equals))
//			Wend
//			If ((inserted_id > 0) AND (inserted_junk > 0) AND (inserted_junk < inserted_id)) {
//				SQL = "delete from " & my_table & " where " & auto_id_column & "<" & inserted_id & " and " & auto_id_column & ">=" & inserted_junk
//				rs = st.executeQuery(SQL)
//			}
//			If (inserted_id > CLng(row.Item(auto_id_column))) {
//				Response.Write "<p>ERROR: Automatic database table id sequence number out of order. Please recreate/reinitialize database to import correctly."
//				Response.Flush
//			}
		}
	}



	/* add_column */

	private String add_column(String SQLwhere, String my_table, String column, String value, HashMap<String,String> SQLdata, HashMap<String,HashMap<String,String>> inserted_ids, String equals, DB db, Configuration myconfig, Databases databases, Pattern tableDataRegEx, Pattern columnDataRegEx) {
		if (! SQLwhere.equals("")) {
			SQLwhere += " and ";
		}

		if ((my_table.equals("content_archive")) && (column.equals("id"))) {
			if ((inserted_ids.get("content") != null) && (((HashMap<String,String>)inserted_ids.get("content")).get(value) != null)) {
				SQLdata.put(column, "" + Common.parse_int(((HashMap<String,String>)inserted_ids.get("content")).get(value)));
				SQLwhere += column + "=" + Common.parse_int(((HashMap<String,String>)inserted_ids.get("content")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("content_public")) && (column.equals("id"))) {
			if ((inserted_ids.get("content") != null) && (((HashMap)inserted_ids.get("content")).get(value) != null)) {
				SQLdata.put(column, "" + Common.parse_int(((HashMap)inserted_ids.get("content")).get(value)));
				SQLwhere += column + "=" + Common.parse_int(((HashMap)inserted_ids.get("content")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("content_elements")) && ((column.equals("content_id")) || (column.equals("element_id"))) && ((! value.equals("")) && (! value.equals("0")) && (! value.equals("-1")) && (! value.equals("-2")))) {
			if ((inserted_ids.get("content") != null) && (((HashMap)inserted_ids.get("content")).get(value) != null)) {
				SQLdata.put(column, "" + Common.parse_int(((HashMap)inserted_ids.get("content")).get(value)));
				SQLwhere += column + "=" + Common.parse_int(((HashMap)inserted_ids.get("content")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("content_elements")) && ((column.equals("content_id")) || (column.equals("element_id")))) {
			SQLdata.put(column, "" + Common.parse_int(value));
			SQLwhere += column + "=" + Common.parse_int(value);
		} else if ((my_table.equals("content_archive_elements")) && (column.equals("content_archiveid"))) {
			if ((inserted_ids.get("content_archive") != null) && (((HashMap)inserted_ids.get("content_archive")).get(value) != null)) {
				SQLdata.put(column, "" + Common.parse_int(((HashMap)inserted_ids.get("content_archive")).get(value)));
				SQLwhere += column + "=" + Common.parse_int(((HashMap)inserted_ids.get("content_archive")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("content_archive_elements")) && ((column.equals("content_id")) || (column.equals("element_id"))) && ((! value.equals("")) && (! value.equals("0")) && (! value.equals("-1")) && (! value.equals("-2")))) {
			if ((inserted_ids.get("content") != null) && (((HashMap)inserted_ids.get("content")).get(value) != null)) {
				SQLdata.put(column, "" + Common.parse_int(((HashMap)inserted_ids.get("content")).get(value)));
				SQLwhere += column + "=" + Common.parse_int(((HashMap)inserted_ids.get("content")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("content_archive_elements")) && ((column.equals("content_id")) || (column.equals("element_id")))) {
			SQLdata.put(column, "" + Common.parse_int(value));
			SQLwhere += column + "=" + Common.parse_int(value);
		} else if ((my_table.equals("content_public_elements")) && ((column.equals("content_id")) || (column.equals("element_id"))) && ((! value.equals("")) && (! value.equals("0")) && (! value.equals("-1")) && (! value.equals("-2")))) {
			if ((inserted_ids.get("content") != null) && (((HashMap)inserted_ids.get("content")).get(value) != null)) {
				SQLdata.put(column, "" + Common.parse_int(((HashMap)inserted_ids.get("content")).get(value)));
				SQLwhere += column + "=" + Common.parse_int(((HashMap)inserted_ids.get("content")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("content_public_elements")) && ((column.equals("content_id")) || (column.equals("element_id")))) {
			SQLdata.put(column, "" + Common.parse_int(value));
			SQLwhere += column + "=" + Common.parse_int(value);
		} else if ((my_table.equals("websites")) && ((column.equals("default_page")) || (column.equals("default_page_nonexisting")) || (column.equals("default_page_unpublished")) || (column.equals("default_page_expired")) || (column.equals("default_login")) || (column.equals("default_searchresults_page")) || (column.equals("default_searchresults_entry")) || (column.equals("default_list_entry")) || (column.equals("default_publish_ready")) || (column.equals("default_register_confirm_page")) || (column.equals("default_register_notify_page")) || (column.equals("default_personal_admin_page")) || (column.equals("retrieve_password_page")) || (column.equals("retrieve_password_confirmation")) || (column.equals("retrieve_password_email")) || (column.equals("retrieve_password_error")))) {
			if ((inserted_ids.get("content") != null) && (((HashMap)inserted_ids.get("content")).get(value) != null)) {
				SQLdata.put(column, "" + ((HashMap)inserted_ids.get("content")).get(value));
				SQLwhere += column + equals + quote("" + ((HashMap)inserted_ids.get("content")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("websites")) && (column.equals("default_template"))) {
			if ((inserted_ids.get("content") != null) && (((HashMap)inserted_ids.get("content")).get(value) != null)) {
				SQLdata.put(column, "" + ((HashMap)inserted_ids.get("content")).get(value));
				SQLwhere += column + equals + quote("" + ((HashMap)inserted_ids.get("content")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("websites")) && (column.equals("default_stylesheet"))) {
			if ((inserted_ids.get("content") != null) && (((HashMap)inserted_ids.get("content")).get(value) != null)) {
				SQLdata.put(column, "" + ((HashMap)inserted_ids.get("content")).get(value));
				SQLwhere += column + equals + quote("" + ((HashMap)inserted_ids.get("content")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("workflow")) && (column.equals("notify_email"))) {
			if ((inserted_ids.get("content") != null) && (((HashMap)inserted_ids.get("content")).get(value) != null)) {
				SQLdata.put(column, "" + ((HashMap)inserted_ids.get("content")).get(value));
				SQLwhere += column + equals + quote("" + ((HashMap)inserted_ids.get("content")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
// MISSING: workflow contentchanges
		} else if ((my_table.equals("orderitems")) && ((column.equals("product_id")) || (column.equals("product_email")) || (column.equals("product_page")))) {
			if ((inserted_ids.get("content") != null) && (((HashMap)inserted_ids.get("content")).get(value) != null)) {
				SQLdata.put(column, "" + Common.parse_int(((HashMap)inserted_ids.get("content")).get(value)));
				SQLwhere += column + "=" + Common.parse_int(((HashMap)inserted_ids.get("content")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}

		} else if ((my_table.equals("content")) && (column.equals("product_currency"))) {
			if ((inserted_ids.get("currencies") != null) && (((HashMap)inserted_ids.get("currencies")).get(value) != null)) {
				SQLdata.put(column, "" + ((HashMap)inserted_ids.get("currencies")).get(value));
				SQLwhere += column + equals + quote("" + ((HashMap)inserted_ids.get("currencies")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("content_archive")) && (column.equals("product_currency"))) {
			if ((inserted_ids.get("currencies") != null) && (((HashMap)inserted_ids.get("currencies")).get(value) != null)) {
				SQLdata.put(column, "" + ((HashMap)inserted_ids.get("currencies")).get(value));
				SQLwhere += column + equals + quote("" + ((HashMap)inserted_ids.get("currencies")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("content_public")) && (column.equals("product_currency"))) {
			if ((inserted_ids.get("currencies") != null) && (((HashMap)inserted_ids.get("currencies")).get(value) != null)) {
				SQLdata.put(column, "" + ((HashMap)inserted_ids.get("currencies")).get(value));
				SQLwhere += column + equals + quote("" + ((HashMap)inserted_ids.get("currencies")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}

		} else if ((my_table.equals("hosting")) && (column.equals("user_id"))) {
			if ((inserted_ids.get("users") != null) && (((HashMap)inserted_ids.get("users")).get(value) != null)) {
				SQLdata.put(column, "" + Common.parse_int(((HashMap)inserted_ids.get("users")).get(value)));
				SQLwhere += column + "=" + Common.parse_int(((HashMap)inserted_ids.get("users")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
//		} else if ((my_table.equals("orders")) && (column.equals("order_currency"))) {
//			if ((inserted_ids.get("currencies") != null) && (((HashMap)inserted_ids.get("currencies")).get(value) != null)) {
//				SQLdata.put(column, "" + ((HashMap)inserted_ids.get("currencies")).get(value));
//				SQLwhere += column + equals + quote("" + ((HashMap)inserted_ids.get("currencies")).get(value));
//			} else {
//				SQLdata.put(column, "");
//				SQLwhere += column + equals + quote("");
//			}
		} else if ((my_table.equals("orders")) && (column.equals("user_id"))) {
			if ((inserted_ids.get("users") != null) && (((HashMap)inserted_ids.get("users")).get(value) != null)) {
				SQLdata.put(column, "" + Common.parse_int(((HashMap)inserted_ids.get("users")).get(value)));
				SQLwhere += column + "=" + Common.parse_int(((HashMap)inserted_ids.get("users")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("orderitems")) && (column.equals("order_id"))) {
			if ((inserted_ids.get("orders") != null) && (((HashMap)inserted_ids.get("orders")).get(value) != null)) {
				SQLdata.put(column, "" + Common.parse_int(((HashMap)inserted_ids.get("orders")).get(value)));
				SQLwhere += column + "=" + Common.parse_int(((HashMap)inserted_ids.get("orders")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
//		} else if ((my_table.equals("orderitems")) && (column.equals("product_currency"))) {
//			if ((inserted_ids.get("currencies") != null) && (((HashMap)inserted_ids.get("currencies")).get(value) != null)) {
//				SQLdata.put(column, ((HashMap)inserted_ids.get("currencies")).get(value));
//				SQLwhere += column + equals + quote("" + ((HashMap)inserted_ids.get("currencies")).get(value));
//			} else if (useNonCrossRefIds) {
//				SQLdata.put(column, "" + value);
//				SQLwhere += column + equals + quote("" + value);
//			} else {
//				SQLdata.put(column, "");
//				SQLwhere += column + equals + quote("");
//			}
		} else if ((my_table.equals("shipping")) && (column.equals("product_id"))) {
			if ((inserted_ids.get("content") != null) && (((HashMap)inserted_ids.get("content")).get(value) != null)) {
				SQLdata.put(column, "" + ((HashMap)inserted_ids.get("content")).get(value));
				SQLwhere += column + equals + quote("" + ((HashMap)inserted_ids.get("content")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("shipping")) && ((column.equals("ship_currency")) || (column.equals("total_currency")))) {
			if ((inserted_ids.get("currencies") != null) && (((HashMap)inserted_ids.get("currencies")).get(value) != null)) {
				SQLdata.put(column, "" + ((HashMap)inserted_ids.get("currencies")).get(value));
				SQLwhere += column + equals + quote("" + ((HashMap)inserted_ids.get("currencies")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("tax")) && (column.equals("product_id"))) {
			if ((inserted_ids.get("content") != null) && (((HashMap)inserted_ids.get("content")).get(value) != null)) {
				SQLdata.put(column, "" + ((HashMap)inserted_ids.get("content")).get(value));
				SQLwhere += column + equals + quote("" + ((HashMap)inserted_ids.get("content")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("tax")) && ((column.equals("tax_currency")) || (column.equals("total_currency")))) {
			if ((inserted_ids.get("currencies") != null) && (((HashMap)inserted_ids.get("currencies")).get(value) != null)) {
				SQLdata.put(column, "" + ((HashMap)inserted_ids.get("currencies")).get(value));
				SQLwhere += column + equals + quote("" + ((HashMap)inserted_ids.get("currencies")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("versions")) && (column.equals("currencies"))) {
			if ((inserted_ids.get("currencies") != null) && (((HashMap)inserted_ids.get("currencies")).get(value) != null)) {
				SQLdata.put(column, "" + ((HashMap)inserted_ids.get("currencies")).get(value));
				SQLwhere += column + equals + quote("" + ((HashMap)inserted_ids.get("currencies")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("user2groups")) && (column.equals("user_id"))) {
			if ((inserted_ids.get("users") != null) && (((HashMap)inserted_ids.get("users")).get(value) != null)) {
				SQLdata.put(column, "" + Common.parse_int(((HashMap)inserted_ids.get("users")).get(value)));
				SQLwhere += column + "=" + Common.parse_int(((HashMap)inserted_ids.get("users")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("user2types")) && (column.equals("user_id"))) {
			if ((inserted_ids.get("users") != null) && (((HashMap)inserted_ids.get("users")).get(value) != null)) {
				SQLdata.put(column, "" + Common.parse_int(((HashMap)inserted_ids.get("users")).get(value)));
				SQLwhere += column + "=" + Common.parse_int(((HashMap)inserted_ids.get("users")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("usergroups")) && (column.equals("login_page"))) {
			if ((inserted_ids.get("content") != null) && (((HashMap)inserted_ids.get("content")).get(value) != null)) {
				SQLdata.put(column, "" + ((HashMap)inserted_ids.get("content")).get(value));
				SQLwhere += column + equals + quote("" + ((HashMap)inserted_ids.get("content")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("usertypes")) && (column.equals("login_page"))) {
			if ((inserted_ids.get("content") != null) && (((HashMap)inserted_ids.get("content")).get(value) != null)) {
				SQLdata.put(column, "" + ((HashMap)inserted_ids.get("content")).get(value));
				SQLwhere += column + equals + quote("" + ((HashMap)inserted_ids.get("content")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}

		} else if ((my_table.equals("data")) && (column.equals("searchresults"))) {
			if ((inserted_ids.get("content") != null) && (((HashMap)inserted_ids.get("content")).get(value) != null)) {
				SQLdata.put(column, "" + Common.parse_int(((HashMap)inserted_ids.get("content")).get(value)));
				SQLwhere += column + "=" + Common.parse_int(((HashMap)inserted_ids.get("content")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("data")) && (column.equals("searchresult"))) {
			if ((inserted_ids.get("content") != null) && (((HashMap)inserted_ids.get("content")).get(value) != null)) {
				SQLdata.put(column, "" + Common.parse_int(((HashMap)inserted_ids.get("content")).get(value)));
				SQLwhere += column + "=" + Common.parse_int(((HashMap)inserted_ids.get("content")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if ((my_table.equals("data")) && (column.equals("viewpage"))) {
			if ((inserted_ids.get("content") != null) && (((HashMap)inserted_ids.get("content")).get(value) != null)) {
				SQLdata.put(column, "" + Common.parse_int(((HashMap)inserted_ids.get("content")).get(value)));
				SQLwhere += column + "=" + Common.parse_int(((HashMap)inserted_ids.get("content")).get(value));
			} else if (useNonCrossRefIds) {
				SQLdata.put(column, "" + value);
				SQLwhere += column + equals + quote("" + value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		} else if (tableDataRegEx.matcher(my_table).find()) {
			if (! databases.getId().equals("" + ((HashMap)inserted_ids.get("data")).get(my_table.substring(4)))) {
				databases.read(db, myconfig, "" + ((HashMap)inserted_ids.get("data")).get(my_table.substring(4)));
			}
			if (columnDataRegEx.matcher(column).find()) {
				String mycolumnid = column.substring(3);
				Iterator mycolumns = databases.columns.keySet().iterator();
				while (mycolumns.hasNext()) {
					HashMap mycolumn = (HashMap)databases.columns.get("" + mycolumns.next());
					if (mycolumn.get("id").equals(mycolumnid)) {
						if ((mycolumn.get("type").equals("content")) || (mycolumn.get("type").equals("contents")) || (mycolumn.get("type").equals("page")) || (mycolumn.get("type").equals("pages")) || (mycolumn.get("type").equals("image")) || (mycolumn.get("type").equals("images")) || (mycolumn.get("type").equals("file")) || (mycolumn.get("type").equals("files")) || (mycolumn.get("type").equals("link")) || (mycolumn.get("type").equals("links")) || (mycolumn.get("type").equals("product")) || (mycolumn.get("type").equals("products")) || (mycolumn.get("type").equals("element")) || (mycolumn.get("type").equals("elements"))) {
							String adjusted_value = "";
							String[] myids = value.split("\\|");
							for (int i=0; i<myids.length; i++) {
								String myid = myids[i];
								if (! adjusted_value.equals("")) {
									adjusted_value += "|";
								}
								if (((HashMap)inserted_ids.get("content")) != null) {
									adjusted_value += "" + ((HashMap)inserted_ids.get("content")).get(myid);
								}
							}
							value = adjusted_value;
						}
					}
				}
			}
			SQLdata.put(column, value);
			SQLwhere += column + equals + quote(value);

		} else if (column.equals("id")) {
			SQLdata.put(column, "" + Common.parse_int(value));
			SQLwhere += column + "=" + Common.parse_int(value);
		} else if ((column.equals("locked")) || (column.equals("locked_user")) || (column.equals("locked_creator")) || (column.equals("locked_developer")) || (column.equals("locked_editor")) || (column.equals("locked_publisher")) || (column.equals("locked_administrator")) || (column.equals("locked_schedule")) || (column.equals("locked_unschedule")) || (column.equals("quantity_from")) || (column.equals("quantity_to")) || (column.equals("futureint1")) || (column.equals("futureint2")) || (column.equals("futureint3"))) {
			SQLdata.put(column, "" + Common.parse_int(value));
			SQLwhere += column + "=" + Common.parse_int(value);
		} else if ((column.equals("total_from")) || (column.equals("total_to")) || (column.equals("ship_order")) || (column.equals("ship_item")) || (column.equals("ship_percent")) || (column.equals("tax_order")) || (column.equals("tax_item")) || (column.equals("tax_percent"))) {
			SQLdata.put(column, "" + Common.parse_float(value));
			SQLwhere += column + "=" + Common.parse_float(value);
		} else {
			if (value != null) {
				SQLdata.put(column, value);
				SQLwhere += column + equals + quote(value);
			} else {
				SQLdata.put(column, "");
				SQLwhere += column + equals + quote("");
			}
		}
		return "" + SQLwhere;
	}



	/* adjust_dependencies */

	private void adjust_dependencies(PrintWriter fileout, JspWriter out, ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, HashMap<String,HashMap<String,String>> inserted_ids, String equals) throws Exception {
		String content_min_id = "" + min_inserted_id(inserted_ids, "content");
		String content_max_id = "" + max_inserted_id(inserted_ids, "content");
		String content_archive_min_id = "" + min_inserted_id(inserted_ids, "content_archive");
		String content_archive_max_id = "" + max_inserted_id(inserted_ids, "content_archive");

		HashMap<String,String> update_ids;
		Iterator update_idss;

		if (inserted_ids.containsKey("data")) {
			Iterator database_ids = ((HashMap)inserted_ids.get("data")).keySet().iterator();
			while (database_ids.hasNext()) {
				String old_database_id = (String) database_ids.next();
				String new_database_id = "" + ((HashMap)inserted_ids.get("data")).get(old_database_id);
				Databases databases = new Databases(text);
				databases.read(this, myconfig, "" + new_database_id);
				Iterator mycolumns = databases.columns.keySet().iterator();
				while (mycolumns.hasNext()) {
					HashMap mycolumn = (HashMap)databases.columns.get("" + mycolumns.next());
					String id = "" + mycolumn.get("id");
					String order = "" + mycolumn.get("order");
					String name = "" + mycolumn.get("name");
					String index = "" + mycolumn.get("index");
					String type = "" + mycolumn.get("type");
					String param1 = "" + mycolumn.get("param1");
					String param2 = "" + mycolumn.get("param2");
					String options = "" + mycolumn.get("options");
					String cols = param1;
					String rows = param2;
					String width = param1;
					String height = param2;
					String digits = param1;
					String decimals = param2;
					String contentclass = param2;
					String size = param1;
					String size2 = param2;
					String databasename = param2;
					String databasecontent = options;
					String column = "col" + id;
					String columnid = "";
					Iterator dbcolumns = databases.columns.keySet().iterator();
					while (dbcolumns.hasNext()) {
						HashMap dbcolumn = (HashMap)databases.columns.get("" + dbcolumns.next());
						if (databasecontent.equals(dbcolumn.get("name"))) { columnid = "" + dbcolumn.get("id"); }
					}
					if ((mycolumn.get("type").equals("datum")) || (mycolumn.get("type").equals("data"))) {
						Databases contentdatabase = new Databases(text);
						contentdatabase.readTitle("", "", "", "", "", "", "", this, myconfig, databasename);
						String contentdatabase_id = contentdatabase.getId();
						String data_min_id = "" + min_inserted_id(inserted_ids, "data"+new_database_id);
						String data_max_id = "" + max_inserted_id(inserted_ids, "data"+new_database_id);
						Data data = new Data();
						data.records(this, "select * from data" + new_database_id + " where id>=" + data_min_id + " and id<=" + data_max_id);
						while (data.records(this, "")) {
							String old_ids = "" + data.getContent(column);
							String new_ids = "";
							String[] myids = old_ids.split("\\|");
							for (int i=0; i<myids.length; i++) {
								String old_id = "" + myids[i];
								String new_id = "" + ((HashMap)inserted_ids.get("data" + contentdatabase_id)).get(old_id);
								if ((old_id != null) && (! old_id.equals("")) && (new_id != null)) {
									if (! new_ids.equals("")) {
										new_ids += "|";
									}
									new_ids += "" + new_id;
								}
							}
							data.setContent(column, new_ids);
							data.update(this, "data" + new_database_id, databases.columns);
						}
					}
				}
			}
		}

		if ((inserted_ids.containsKey("content")) && (! content_min_id.equals("")) && (! content_max_id.equals(""))) {
			String SQL;
			SQL = "select id from content where ((contentclass = " + quote("page") + " or contentclass = " + quote("stylesheet") + " or contentclass = " + quote("product") + ") and " + is_not_blank("server_filename") + ") and id>=" + content_min_id + " and id<=" + content_max_id;
			Content list_content = new Content(text);
			list_content.records("", "", "", "", "", "", "", this, myconfig, SQL);
			while (list_content.records("", "", "", "", "", "", "", this, myconfig, "")) {
				mysession.set("mode", "");
				Page record_content = new Page(text);
				record_content.read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", this, myconfig, list_content.getId(), "content", "id", "", myconfig.get(this, "default_version"), "", "", "", "", myconfig.get(this, "default_template"), "", myconfig.get(this, "default_stylesheet"), "", myconfig.get(this, "default_currency"), myconfig.get(this, "default_shopcartsummary_page"), myconfig.get(this, "default_shopcartsummary_entry"));
				String filecontent = "";
				if ((myconfig.get(this, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.html?$").matcher(record_content.getServerFilename()).find())) {
					filecontent = Common.readFile(DOCUMENT_ROOT + "/" + record_content.getContentClass() + ".original.html");
//handled elsewhere by Page.outputStaticFile
				} else if ((myconfig.get(this, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.css$").matcher(record_content.getServerFilename()).find())) {
					filecontent = Common.readFile(DOCUMENT_ROOT + "/" + record_content.getContentClass() + ".original.css");
//handled elsewhere by Page.outputStaticFile
				} else if ((myconfig.get(this, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.js$").matcher(record_content.getServerFilename()).find())) {
					filecontent = Common.readFile(DOCUMENT_ROOT + "/" + record_content.getContentClass() + ".original.js");
//handled elsewhere by Page.outputStaticFile
				} else if (myconfig.get(this, "URLrootpath").equals("/")) {
					filecontent = Common.readFile(DOCUMENT_ROOT + "/" + record_content.getContentClass() + ".original.jsp");
				} else {
					filecontent = Common.readFile(DOCUMENT_ROOT + "/" + record_content.getContentClass() + ".original.hosting.jsp");
				}
				filecontent = filecontent.replaceAll("myrequest\\.getParameter\\(\"id\"\\)", "\"" + record_content.getId() + "\"");
				if ((myconfig.get(this, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.html?$").matcher(record_content.getServerFilename()).find())) {
					out.println("\r\n" + "<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename() + "<br>" + "\r\n");
					fileout.println("\r\n" + "<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename() + "<br>" + "\r\n");
					Common.deleteFile(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename());
					Common.writeFile(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename(), filecontent, myconfig.get(this, "charset"));
				} else if ((myconfig.get(this, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.css$").matcher(record_content.getServerFilename()).find())) {
					out.println("\r\n" + "<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename() + "<br>" + "\r\n");
					fileout.println("\r\n" + "<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename() + "<br>" + "\r\n");
					Common.deleteFile(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename());
					Common.writeFile(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename(), filecontent, myconfig.get(this, "charset"));
				} else if ((myconfig.get(this, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.js$").matcher(record_content.getServerFilename()).find())) {
					out.println("\r\n" + "<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename() + "<br>" + "\r\n");
					fileout.println("\r\n" + "<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename() + "<br>" + "\r\n");
					Common.deleteFile(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename());
					Common.writeFile(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename(), filecontent, myconfig.get(this, "charset"));
				} else if (! Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.jsp$").matcher(record_content.getServerFilename()).find()) {
					out.println("\r\n" + "<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename() + "/index.jsp" + "<br>" + "\r\n");
					fileout.println("\r\n" + "<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename() + "/index.jsp" + "<br>" + "\r\n");
					new File(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename()).mkdirs();
					Common.deleteFile(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename() + "/index.jsp");
					Common.writeFile(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename() + "/index.jsp", filecontent, myconfig.get(this, "charset"));
				} else if (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.jsp$").matcher(record_content.getServerFilename()).find()) {
					out.println("\r\n" + "<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename() + "<br>" + "\r\n");
					fileout.println("\r\n" + "<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename() + "<br>" + "\r\n");
					Common.deleteFile(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename());
					Common.writeFile(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename(), filecontent, myconfig.get(this, "charset"));
				} else {
					out.println("\r\n" + "<p>%" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename() + "%<br>" + "\r\n");
					fileout.println("\r\n" + "<p>%" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + record_content.getServerFilename() + "%<br>" + "\r\n");
				}
				mysession.set("mode", "");
				record_content.outputStaticFile(server, myrequest.getRequest(), myresponse.getResponse(), mysession.getSession(), null, myconfig, this);
			}
		}

		if (inserted_ids.containsKey("users")) {
			HashMap<String,String> remaining_update_ids = new HashMap<String,String>();
			Iterator old_ids = ((HashMap)inserted_ids.get("users")).keySet().iterator();
			while (old_ids.hasNext()) {
				String old_id = (String) old_ids.next();
				String new_id = "" + ((HashMap)inserted_ids.get("users")).get(old_id);
				remaining_update_ids.put(old_id, new_id);
			}
			boolean done = false;
			boolean force = false;
			while (! done) {
				done = true;
				boolean updated = false;
				old_ids = ((HashMap)inserted_ids.get("users")).keySet().iterator();
				while (old_ids.hasNext()) {
					String old_id = (String) old_ids.next();
					String new_id = "" + ((HashMap)inserted_ids.get("users")).get(old_id);
					if ((! force) && (remaining_update_ids.get(old_id) != null) && (remaining_update_ids.get(new_id) != null)) {
						done = false; // skip this for now and do later
					} else if (remaining_update_ids.get(old_id) != null) {
						remaining_update_ids.put(old_id, null); // free this for updating next
						force = false; // update this so do not force updating the rest
						updated = true; // update this so do not force updating the rest next time

						String SQL;
						SQL = "select id from content where (content like " + quote("%value=" + old_id + "%") + " or content like " + quote("%value=\"" + old_id + "\"%") + ") and id>=" + content_min_id + " and id<=" + content_max_id;
						update_ids = new HashMap<String,String>();
						Content list_content = new Content(text);
						list_content.records("", "", "", "", "", "", "", this, myconfig, SQL);
						while (list_content.records("", "", "", "", "", "", "", this, myconfig, "")) {
							update_ids.put(list_content.getId(), list_content.getId());
						}
						update_idss = update_ids.keySet().iterator();
						while (update_idss.hasNext()) {
							String update_id = (String) update_idss.next();
							Content record_content = new Content(text);
							record_content.read(this, myconfig, update_id, "content", "id");

							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)name=(user_id)([^<]+)value=" + old_id + "([^0-9])", "<input$1name=$2$3value=" + new_id + "$4"));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)name=(user_id)([^<]+)value=\"" + old_id + "\"", "<input$1name=$2$3value=\"" + new_id + "\""));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)name=\"(user_id)\"([^<]+)value=" + old_id + "([^0-9])", "<input$1name=\"$2\"$3value=" + new_id + "$4"));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)name=\"(user_id)\"([^<]+)value=\"" + old_id + "\"", "<input$1name=\"$2\"$3value=\"" + new_id + "\""));

							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)value=" + old_id + "([^<0-9])([^<]*)name=(user_id)", "<input$1value=" + new_id + "$2$3name=$4"));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)value=\"" + old_id + "\"([^<]*)name=(user_id)", "<input$1value=\"" + new_id + "\"$2name=$3"));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)value=" + old_id + "([^<0-9])([^<]*)name=\"(user_id)\"", "<input$1value=" + new_id + "$2$3name=\"$4\""));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)value=\"" + old_id + "\"([^<]*)name=\"(user_id)\"", "<input$1value=\"" + new_id + "\"$2name=\"$3\""));

							record_content.update(this, myconfig, record_content.getId(), "content", "id");
						}

						SQL = "select archiveid from content_archive where (content like " + quote("%value=" + old_id + "%") + " or content like " + quote("%value=\"" + old_id + "\"%") + ") and archiveid>=" + content_archive_min_id + " and archiveid<=" + content_archive_max_id;
						update_ids = new HashMap<String,String>();
						list_content = new Content(text);
						list_content.records("", "", "", "", "", "", "", this, myconfig, SQL);
						while (list_content.records("", "", "", "", "", "", "", this, myconfig, "")) {
							update_ids.put(list_content.getArchiveId(), list_content.getArchiveId());
						}
						update_idss = update_ids.keySet().iterator();
						while (update_idss.hasNext()) {
							String update_id = (String) update_idss.next();
							Content record_content = new Content(text);
							record_content.read(this, myconfig, update_id, "content_archive", "archiveid");

							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)name=(user_id)([^<]+)value=" + old_id + "([^0-9])", "<input$1name=$2$3value=" + new_id + "$4"));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)name=(user_id)([^<]+)value=\"" + old_id + "\"", "<input$1name=$2$3value=\"" + new_id + "\""));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)name=\"(user_id)\"([^<]+)value=" + old_id + "([^0-9])", "<input$1name=\"$2\"$3value=" + new_id + "$4"));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)name=\"(user_id)\"([^<]+)value=\"" + old_id + "\"", "<input$1name=\"$2\"$3value=\"" + new_id + "\""));

							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)value=" + old_id + "([^<0-9])([^<]*)name=(user_id)", "<input$1value=" + new_id + "$2$3name=$4"));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)value=\"" + old_id + "\"([^<]*)name=(user_id)", "<input$1value=\"" + new_id + "\"$2name=$3"));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)value=" + old_id + "([^<0-9])([^<]*)name=\"(user_id)\"", "<input$1value=" + new_id + "$2$3name=\"$4\""));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)value=\"" + old_id + "\"([^<]*)name=\"(user_id)\"", "<input$1value=\"" + new_id + "\"$2name=\"$3\""));

							record_content.update(this, myconfig, record_content.getArchiveId(), "content_archive", "archiveid");
						}

						SQL = "select id from content_public where (content like " + quote("%value=" + old_id + "%") + " or content like " + quote("%value=\"" + old_id + "\"%") + ") and id>=" + content_min_id + " and id<=" + content_max_id;
						update_ids = new HashMap<String,String>();
						list_content = new Content(text);
						list_content.records("", "", "", "", "", "", "", this, myconfig, SQL);
						while (list_content.records("", "", "", "", "", "", "", this, myconfig, "")) {
							update_ids.put(list_content.getId(), list_content.getId());
						}
						update_idss = update_ids.keySet().iterator();
						while (update_idss.hasNext()) {
							String update_id = (String) update_idss.next();
							Content record_content = new Content(text);
							record_content.read(this, myconfig, update_id, "content_public", "id");

							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)name=(user_id)([^<]+)value=" + old_id + "([^0-9])", "<input$1name=$2$3value=" + new_id + "$4"));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)name=(user_id)([^<]+)value=\"" + old_id + "\"", "<input$1name=$2$3value=\"" + new_id + "\""));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)name=\"(user_id)\"([^<]+)value=" + old_id + "([^0-9])", "<input$1name=\"$2\"$3value=" + new_id + "$4"));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)name=\"(user_id)\"([^<]+)value=\"" + old_id + "\"", "<input$1name=\"$2\"$3value=\"" + new_id + "\""));

							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)value=" + old_id + "([^<0-9])([^<]*)name=(user_id)", "<input$1value=" + new_id + "$2$3name=$4"));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)value=\"" + old_id + "\"([^<]*)name=(user_id)", "<input$1value=\"" + new_id + "\"$2name=$3"));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)value=" + old_id + "([^<0-9])([^<]*)name=\"(user_id)\"", "<input$1value=" + new_id + "$2$3name=\"$4\""));
							record_content.setContent(record_content.getContent().replaceAll("<input([^<]+)value=\"" + old_id + "\"([^<]*)name=\"(user_id)\"", "<input$1value=\"" + new_id + "\"$2name=\"$3\""));

							record_content.update(this, myconfig, record_content.getId(), "content_public", "id");
						}

						try {
							out.print(". ");
							out.flush();
							myresponse.flushBuffer();
							fileout.print(". ");
							fileout.flush();
						} catch (IOException ee) {
						}
					}
				}
				if (updated) {
					force = false; // one or more has been updated so more may have been freed
				} else {
					force = true; // none has been updated so force next/rest if any more left
				}
			}
		}

		try {
			out.print("\r\n" + "<p>" + "\r\n");
			out.flush();
			myresponse.flushBuffer();
			fileout.print("\r\n" + "<p>" + "\r\n");
			fileout.flush();
		} catch (IOException ee) {
		}

		if (inserted_ids.containsKey("content")) {
			HashMap<String,String> remaining_update_ids = new HashMap<String,String>();
			Iterator old_ids = ((HashMap)inserted_ids.get("content")).keySet().iterator();
			while (old_ids.hasNext()) {
				String old_id = (String) old_ids.next();
				String new_id = "" + ((HashMap)inserted_ids.get("content")).get(old_id);
				if (! old_id.equals(new_id)) {
					remaining_update_ids.put(old_id, new_id);
				}
			}
			boolean done = false;
			boolean force = false;
			while (! done) {
				done = true;
				boolean updated = false;
				old_ids = ((HashMap)inserted_ids.get("content")).keySet().iterator();
				while (old_ids.hasNext()) {
					String old_id = (String) old_ids.next();
					String new_id = "" + ((HashMap)inserted_ids.get("content")).get(old_id);
					if ((! force) && (remaining_update_ids.get(old_id) != null) && (remaining_update_ids.get(new_id) != null)) {
						done = false; // skip this for now and do later
					} else if (remaining_update_ids.get(old_id) != null) {
						remaining_update_ids.put(old_id, null); // free this for updating next
						force = false; // update this so do not force updating the rest
						updated = true; // update this so do not force updating the rest next time

						execute("update content set version_master=" + quote(new_id) + " where version_master=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set template=" + quote(new_id) + " where template=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
//						execute("update content set stylesheet=" + quote(new_id) + " where stylesheet=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set page_top=" + quote(new_id) + " where page_top=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set page_up=" + quote(new_id) + " where page_up=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set page_next=" + quote(new_id) + " where page_next=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set page_previous=" + quote(new_id) + " where page_previous=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set page_first=" + quote(new_id) + " where page_first=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set page_last=" + quote(new_id) + " where page_last=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set image1=" + quote(new_id) + " where image1=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set image2=" + quote(new_id) + " where image2=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set image3=" + quote(new_id) + " where image3=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set file1=" + quote(new_id) + " where file1=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set file2=" + quote(new_id) + " where file2=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set file3=" + quote(new_id) + " where file3=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set link1=" + quote(new_id) + " where link1=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set link2=" + quote(new_id) + " where link2=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set link3=" + quote(new_id) + " where link3=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set product_email=" + quote(new_id) + " where product_email=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content set product_page=" + quote(new_id) + " where product_page=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);

						execute("update content_archive set version_master=" + quote(new_id) + " where version_master=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set template=" + quote(new_id) + " where template=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
//						execute("update content_archive set stylesheet=" + quote(new_id) + " where stylesheet=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set page_top=" + quote(new_id) + " where page_top=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set page_up=" + quote(new_id) + " where page_up=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set page_next=" + quote(new_id) + " where page_next=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set page_previous=" + quote(new_id) + " where page_previous=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set page_first=" + quote(new_id) + " where page_first=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set page_last=" + quote(new_id) + " where page_last=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set image1=" + quote(new_id) + " where image1=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set image2=" + quote(new_id) + " where image2=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set image3=" + quote(new_id) + " where image3=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set file1=" + quote(new_id) + " where file1=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set file2=" + quote(new_id) + " where file2=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set file3=" + quote(new_id) + " where file3=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set link1=" + quote(new_id) + " where link1=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set link2=" + quote(new_id) + " where link2=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set link3=" + quote(new_id) + " where link3=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set product_email=" + quote(new_id) + " where product_email=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);
						execute("update content_archive set product_page=" + quote(new_id) + " where product_page=" + quote(old_id) + " and id>=" + content_archive_min_id + " and id<=" + content_archive_max_id);

						execute("update content_public set version_master=" + quote(new_id) + " where version_master=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set template=" + quote(new_id) + " where template=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
//						execute("update content_public set stylesheet=" + quote(new_id) + " where stylesheet=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set page_top=" + quote(new_id) + " where page_top=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set page_up=" + quote(new_id) + " where page_up=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set page_next=" + quote(new_id) + " where page_next=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set page_previous=" + quote(new_id) + " where page_previous=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set page_first=" + quote(new_id) + " where page_first=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set page_last=" + quote(new_id) + " where page_last=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set image1=" + quote(new_id) + " where image1=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set image2=" + quote(new_id) + " where image2=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set image3=" + quote(new_id) + " where image3=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set file1=" + quote(new_id) + " where file1=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set file2=" + quote(new_id) + " where file2=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set file3=" + quote(new_id) + " where file3=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set link1=" + quote(new_id) + " where link1=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set link2=" + quote(new_id) + " where link2=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set link3=" + quote(new_id) + " where link3=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set product_email=" + quote(new_id) + " where product_email=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);
						execute("update content_public set product_page=" + quote(new_id) + " where product_page=" + quote(old_id) + " and id>=" + content_min_id + " and id<=" + content_max_id);

						String SQL;
						SQL = "select id from content where (content like " + quote("%=" + old_id + ".%") + " or content like " + quote("%=" + old_id + ":%") + " or content like " + quote("%=" + old_id + ")%") + " or content like " + quote("%include:" + old_id + "%") + " or content like " + quote("%entry=" + old_id + "%") + " or content like " + quote("%header=" + old_id + "%") + " or content like " + quote("%footer=" + old_id + "%") + " or content like " + quote("%none=" + old_id + "%") + " or content like " + quote("%id=" + old_id + "%") + " or content like " + quote("%id!=" + old_id + "%") + " or content like " + quote("%id in (%" + old_id + "%)") + " or content like " + quote("%top=" + old_id + "%") + " or content like " + quote("%top!=" + old_id + "%") + " or content like " + quote("%top in (%" + old_id + "%)") + " or content like " + quote("%up=" + old_id + "%") + " or content like " + quote("%up!=" + old_id + "%") + " or content like " + quote("%up in (%" + old_id + "%)") + " or content like " + quote("%previous=" + old_id + "%") + " or content like " + quote("%previous!=" + old_id + "%") + " or content like " + quote("%previous in (%" + old_id + "%)") + " or content like " + quote("%next=" + old_id + "%") + " or content like " + quote("%next!=" + old_id + "%") + " or content like " + quote("%next in (%" + old_id + "%)") + " or content like " + quote("%first=" + old_id + "%") + " or content like " + quote("%first!=" + old_id + "%") + " or content like " + quote("%first in (%" + old_id + "%)") + " or content like " + quote("%last=" + old_id + "%") + " or content like " + quote("%last!=" + old_id + "%") + " or content like " + quote("%last in (%" + old_id + "%)") + " or content like " + quote("%value=" + old_id + "%") + " or content like " + quote("%value=\"" + old_id + "\"%") + " or content like " + quote("%id=" + old_id + "%") + " or content like " + quote("%id=\"" + old_id + "%") + " or content like " + quote("%stylesheet=" + old_id + "%") + " or content like " + quote("%stylesheet=\"" + old_id + "%") + " or content like " + quote("%template=" + old_id + "%") + " or content like " + quote("%template=\"" + old_id + "%") + " or content like " + quote("%add=" + old_id + "%") + " or content like " + quote("%add=\"" + old_id + "%") + " or content like " + quote("%item=" + old_id + "%") + " or content like " + quote("%item=\"" + old_id + "%") + " or summary like " + quote("%include:" + old_id + "%") + " or summary like " + quote("%entry=" + old_id + "%") + " or summary like " + quote("%header=" + old_id + "%") + " or summary like " + quote("%footer=" + old_id + "%") + " or summary like " + quote("%none=" + old_id + "%") + " or summary like " + quote("%id=" + old_id + "%") + " or summary like " + quote("%id!=" + old_id + "%") + " or summary like " + quote("%id in (%" + old_id + "%)") + " or summary like " + quote("%top=" + old_id + "%") + " or summary like " + quote("%top!=" + old_id + "%") + " or summary like " + quote("%top in (%" + old_id + "%)") + " or summary like " + quote("%up=" + old_id + "%") + " or summary like " + quote("%up!=" + old_id + "%") + " or summary like " + quote("%up in (%" + old_id + "%)") + " or summary like " + quote("%previous=" + old_id + "%") + " or summary like " + quote("%previous!=" + old_id + "%") + " or summary like " + quote("%previous in (%" + old_id + "%)") + " or summary like " + quote("%next=" + old_id + "%") + " or summary like " + quote("%next!=" + old_id + "%") + " or summary like " + quote("%next in (%" + old_id + "%)") + " or summary like " + quote("%first=" + old_id + "%") + " or summary like " + quote("%first!=" + old_id + "%") + " or summary like " + quote("%first in (%" + old_id + "%)") + " or summary like " + quote("%last=" + old_id + "%") + " or summary like " + quote("%last!=" + old_id + "%") + " or summary like " + quote("%last in (%" + old_id + "%)") + " or summary like " + quote("%value=" + old_id + "%") + " or summary like " + quote("%value=\"" + old_id + "\"%") + " or summary like " + quote("%id=" + old_id + "%") + " or summary like " + quote("%id=\"" + old_id + "%") + " or summary like " + quote("%stylesheet=" + old_id + "%") + " or summary like " + quote("%stylesheet=\"" + old_id + "%") + " or summary like " + quote("%template=" + old_id + "%") + " or summary like " + quote("%template=\"" + old_id + "%") + " or summary like " + quote("%add=" + old_id + "%") + " or summary like " + quote("%add=\"" + old_id + "%") + " or summary like " + quote("%item=" + old_id + "%") + " or summary like " + quote("%item=\"" + old_id + "%") + " or htmlheader like " + quote("%include:" + old_id + "%") + " or htmlheader like " + quote("%entry=" + old_id + "%") + " or htmlheader like " + quote("%header=" + old_id + "%") + " or htmlheader like " + quote("%footer=" + old_id + "%") + " or htmlheader like " + quote("%none=" + old_id + "%") + " or htmlheader like " + quote("%id=" + old_id + "%") + " or htmlheader like " + quote("%id!=" + old_id + "%") + " or htmlheader like " + quote("%id in (%" + old_id + "%)") + " or htmlheader like " + quote("%top=" + old_id + "%") + " or htmlheader like " + quote("%top!=" + old_id + "%") + " or htmlheader like " + quote("%top in (%" + old_id + "%)") + " or htmlheader like " + quote("%up=" + old_id + "%") + " or htmlheader like " + quote("%up!=" + old_id + "%") + " or htmlheader like " + quote("%up in (%" + old_id + "%)") + " or htmlheader like " + quote("%previous=" + old_id + "%") + " or htmlheader like " + quote("%previous!=" + old_id + "%") + " or htmlheader like " + quote("%previous in (%" + old_id + "%)") + " or htmlheader like " + quote("%next=" + old_id + "%") + " or htmlheader like " + quote("%next!=" + old_id + "%") + " or htmlheader like " + quote("%next in (%" + old_id + "%)") + " or htmlheader like " + quote("%first=" + old_id + "%") + " or htmlheader like " + quote("%first!=" + old_id + "%") + " or htmlheader like " + quote("%first in (%" + old_id + "%)") + " or htmlheader like " + quote("%last=" + old_id + "%") + " or htmlheader like " + quote("%last!=" + old_id + "%") + " or htmlheader like " + quote("%last in (%" + old_id + "%)") + " or htmlheader like " + quote("%value=" + old_id + "%") + " or htmlheader like " + quote("%value=\"" + old_id + "\"%") + " or htmlheader like " + quote("%id=" + old_id + "%") + " or htmlheader like " + quote("%id=\"" + old_id + "%") + " or htmlheader like " + quote("%stylesheet=" + old_id + "%") + " or htmlheader like " + quote("%stylesheet=\"" + old_id + "%") + " or htmlheader like " + quote("%template=" + old_id + "%") + " or htmlheader like " + quote("%template=\"" + old_id + "%") + " or htmlheader like " + quote("%add=" + old_id + "%") + " or htmlheader like " + quote("%add=\"" + old_id + "%") + " or htmlheader like " + quote("%item=" + old_id + "%") + " or htmlheader like " + quote("%item=\"" + old_id + "%") + " or htmlattributes like " + quote("%include:" + old_id + "%") + " or htmlattributes like " + quote("%entry=" + old_id + "%") + " or htmlattributes like " + quote("%header=" + old_id + "%") + " or htmlattributes like " + quote("%footer=" + old_id + "%") + " or htmlattributes like " + quote("%none=" + old_id + "%") + " or htmlattributes like " + quote("%id=" + old_id + "%") + " or htmlattributes like " + quote("%id!=" + old_id + "%") + " or htmlattributes like " + quote("%id in (%" + old_id + "%)") + " or htmlattributes like " + quote("%top=" + old_id + "%") + " or htmlattributes like " + quote("%top!=" + old_id + "%") + " or htmlattributes like " + quote("%top in (%" + old_id + "%)") + " or htmlattributes like " + quote("%up=" + old_id + "%") + " or htmlattributes like " + quote("%up!=" + old_id + "%") + " or htmlattributes like " + quote("%up in (%" + old_id + "%)") + " or htmlattributes like " + quote("%previous=" + old_id + "%") + " or htmlattributes like " + quote("%previous!=" + old_id + "%") + " or htmlattributes like " + quote("%previous in (%" + old_id + "%)") + " or htmlattributes like " + quote("%next=" + old_id + "%") + " or htmlattributes like " + quote("%next!=" + old_id + "%") + " or htmlattributes like " + quote("%next in (%" + old_id + "%)") + " or htmlattributes like " + quote("%first=" + old_id + "%") + " or htmlattributes like " + quote("%first!=" + old_id + "%") + " or htmlattributes like " + quote("%first in (%" + old_id + "%)") + " or htmlattributes like " + quote("%last=" + old_id + "%") + " or htmlattributes like " + quote("%last!=" + old_id + "%") + " or htmlattributes like " + quote("%last in (%" + old_id + "%)") + " or htmlattributes like " + quote("%value=" + old_id + "%") + " or htmlattributes like " + quote("%value=\"" + old_id + "\"%") + " or htmlattributes like " + quote("%id=" + old_id + "%") + " or htmlattributes like " + quote("%id=\"" + old_id + "%") + " or htmlattributes like " + quote("%stylesheet=" + old_id + "%") + " or htmlattributes like " + quote("%stylesheet=\"" + old_id + "%") + " or htmlattributes like " + quote("%template=" + old_id + "%") + " or htmlattributes like " + quote("%template=\"" + old_id + "%") + " or htmlattributes like " + quote("%add=" + old_id + "%") + " or htmlattributes like " + quote("%add=\"" + old_id + "%") + " or htmlattributes like " + quote("%item=" + old_id + "%") + " or htmlattributes like " + quote("%item=\"" + old_id + "%") + " or product_options like " + quote("%<hosting:publish_email>" + old_id + "</hosting:publish_email>%") + " or product_options like " + quote("%<hosting:notify_email>" + old_id + "</hosting:notify_email>%") + " or product_options like " + quote("%<hosting:unpublish_email>" + old_id + "</hosting:unpublish_email>%") + ") and id>=" + content_min_id + " and id<=" + content_max_id;
						update_ids = new HashMap<String,String>();
						Content list_content = new Content(text);
						list_content.records("", "", "", "", "", "", "", this, myconfig, SQL);
						while (list_content.records("", "", "", "", "", "", "", this, myconfig, "")) {
							update_ids.put(list_content.getId(), list_content.getId());
						}
						update_idss = update_ids.keySet().iterator();
						while (update_idss.hasNext()) {
							String update_id = (String) update_idss.next();
							Content record_content = new Content(text);
							record_content.read(this, myconfig, update_id, "content", "id");
							record_content.setContent(adjust_links(record_content.getContent(), old_id, new_id));
							record_content.setSummary(adjust_links(record_content.getSummary(), old_id, new_id));
							record_content.setHtmlHeader(adjust_links(record_content.getHtmlHeader(), old_id, new_id));
							record_content.setHtmlAttributes(adjust_links(record_content.getHtmlAttributes(), old_id, new_id));
							record_content.setProductOptions(adjust_product_options(record_content.getProductOptions(), old_id, new_id));
							record_content.update(this, myconfig, record_content.getId(), "content", "id");
						}

						SQL = "select archiveid from content_archive where (content like " + quote("%=" + old_id + ".%") + " or content like " + quote("%=" + old_id + ":%") + " or content like " + quote("%=" + old_id + ")%") + " or content like " + quote("%include:" + old_id + "%") + " or content like " + quote("%entry=" + old_id + "%") + " or content like " + quote("%header=" + old_id + "%") + " or content like " + quote("%footer=" + old_id + "%") + " or content like " + quote("%none=" + old_id + "%") + " or content like " + quote("%id=" + old_id + "%") + " or content like " + quote("%id!=" + old_id + "%") + " or content like " + quote("%id in (%" + old_id + "%)") + " or content like " + quote("%top=" + old_id + "%") + " or content like " + quote("%top!=" + old_id + "%") + " or content like " + quote("%top in (%" + old_id + "%)") + " or content like " + quote("%up=" + old_id + "%") + " or content like " + quote("%up!=" + old_id + "%") + " or content like " + quote("%up in (%" + old_id + "%)") + " or content like " + quote("%previous=" + old_id + "%") + " or content like " + quote("%previous!=" + old_id + "%") + " or content like " + quote("%previous in (%" + old_id + "%)") + " or content like " + quote("%next=" + old_id + "%") + " or content like " + quote("%next!=" + old_id + "%") + " or content like " + quote("%next in (%" + old_id + "%)") + " or content like " + quote("%first=" + old_id + "%") + " or content like " + quote("%first!=" + old_id + "%") + " or content like " + quote("%first in (%" + old_id + "%)") + " or content like " + quote("%last=" + old_id + "%") + " or content like " + quote("%last!=" + old_id + "%") + " or content like " + quote("%last in (%" + old_id + "%)") + " or content like " + quote("%value=" + old_id + "%") + " or content like " + quote("%value=\"" + old_id + "\"%") + " or content like " + quote("%id=" + old_id + "%") + " or content like " + quote("%id=\"" + old_id + "%") + " or content like " + quote("%stylesheet=" + old_id + "%") + " or content like " + quote("%stylesheet=\"" + old_id + "%") + " or content like " + quote("%template=" + old_id + "%") + " or content like " + quote("%template=\"" + old_id + "%") + " or content like " + quote("%add=" + old_id + "%") + " or content like " + quote("%add=\"" + old_id + "%") + " or content like " + quote("%item=" + old_id + "%") + " or content like " + quote("%item=\"" + old_id + "%") + " or summary like " + quote("%include:" + old_id + "%") + " or summary like " + quote("%entry=" + old_id + "%") + " or summary like " + quote("%header=" + old_id + "%") + " or summary like " + quote("%footer=" + old_id + "%") + " or summary like " + quote("%none=" + old_id + "%") + " or summary like " + quote("%id=" + old_id + "%") + " or summary like " + quote("%id!=" + old_id + "%") + " or summary like " + quote("%id in (%" + old_id + "%)") + " or summary like " + quote("%top=" + old_id + "%") + " or summary like " + quote("%top!=" + old_id + "%") + " or summary like " + quote("%top in (%" + old_id + "%)") + " or summary like " + quote("%up=" + old_id + "%") + " or summary like " + quote("%up!=" + old_id + "%") + " or summary like " + quote("%up in (%" + old_id + "%)") + " or summary like " + quote("%previous=" + old_id + "%") + " or summary like " + quote("%previous!=" + old_id + "%") + " or summary like " + quote("%previous in (%" + old_id + "%)") + " or summary like " + quote("%next=" + old_id + "%") + " or summary like " + quote("%next!=" + old_id + "%") + " or summary like " + quote("%next in (%" + old_id + "%)") + " or summary like " + quote("%first=" + old_id + "%") + " or summary like " + quote("%first!=" + old_id + "%") + " or summary like " + quote("%first in (%" + old_id + "%)") + " or summary like " + quote("%last=" + old_id + "%") + " or summary like " + quote("%last!=" + old_id + "%") + " or summary like " + quote("%last in (%" + old_id + "%)") + " or summary like " + quote("%value=" + old_id + "%") + " or summary like " + quote("%value=\"" + old_id + "\"%") + " or summary like " + quote("%id=" + old_id + "%") + " or summary like " + quote("%id=\"" + old_id + "%") + " or summary like " + quote("%stylesheet=" + old_id + "%") + " or summary like " + quote("%stylesheet=\"" + old_id + "%") + " or summary like " + quote("%template=" + old_id + "%") + " or summary like " + quote("%template=\"" + old_id + "%") + " or summary like " + quote("%add=" + old_id + "%") + " or summary like " + quote("%add=\"" + old_id + "%") + " or summary like " + quote("%item=" + old_id + "%") + " or summary like " + quote("%item=\"" + old_id + "%") + " or htmlheader like " + quote("%include:" + old_id + "%") + " or htmlheader like " + quote("%entry=" + old_id + "%") + " or htmlheader like " + quote("%header=" + old_id + "%") + " or htmlheader like " + quote("%footer=" + old_id + "%") + " or htmlheader like " + quote("%none=" + old_id + "%") + " or htmlheader like " + quote("%id=" + old_id + "%") + " or htmlheader like " + quote("%id!=" + old_id + "%") + " or htmlheader like " + quote("%id in (%" + old_id + "%)") + " or htmlheader like " + quote("%top=" + old_id + "%") + " or htmlheader like " + quote("%top!=" + old_id + "%") + " or htmlheader like " + quote("%top in (%" + old_id + "%)") + " or htmlheader like " + quote("%up=" + old_id + "%") + " or htmlheader like " + quote("%up!=" + old_id + "%") + " or htmlheader like " + quote("%up in (%" + old_id + "%)") + " or htmlheader like " + quote("%previous=" + old_id + "%") + " or htmlheader like " + quote("%previous!=" + old_id + "%") + " or htmlheader like " + quote("%previous in (%" + old_id + "%)") + " or htmlheader like " + quote("%next=" + old_id + "%") + " or htmlheader like " + quote("%next!=" + old_id + "%") + " or htmlheader like " + quote("%next in (%" + old_id + "%)") + " or htmlheader like " + quote("%first=" + old_id + "%") + " or htmlheader like " + quote("%first!=" + old_id + "%") + " or htmlheader like " + quote("%first in (%" + old_id + "%)") + " or htmlheader like " + quote("%last=" + old_id + "%") + " or htmlheader like " + quote("%last!=" + old_id + "%") + " or htmlheader like " + quote("%last in (%" + old_id + "%)") + " or htmlheader like " + quote("%value=" + old_id + "%") + " or htmlheader like " + quote("%value=\"" + old_id + "\"%") + " or htmlheader like " + quote("%id=" + old_id + "%") + " or htmlheader like " + quote("%id=\"" + old_id + "%") + " or htmlheader like " + quote("%stylesheet=" + old_id + "%") + " or htmlheader like " + quote("%stylesheet=\"" + old_id + "%") + " or htmlheader like " + quote("%template=" + old_id + "%") + " or htmlheader like " + quote("%template=\"" + old_id + "%") + " or htmlheader like " + quote("%add=" + old_id + "%") + " or htmlheader like " + quote("%add=\"" + old_id + "%") + " or htmlheader like " + quote("%item=" + old_id + "%") + " or htmlheader like " + quote("%item=\"" + old_id + "%") + " or htmlattributes like " + quote("%include:" + old_id + "%") + " or htmlattributes like " + quote("%entry=" + old_id + "%") + " or htmlattributes like " + quote("%header=" + old_id + "%") + " or htmlattributes like " + quote("%footer=" + old_id + "%") + " or htmlattributes like " + quote("%none=" + old_id + "%") + " or htmlattributes like " + quote("%id=" + old_id + "%") + " or htmlattributes like " + quote("%id!=" + old_id + "%") + " or htmlattributes like " + quote("%id in (%" + old_id + "%)") + " or htmlattributes like " + quote("%top=" + old_id + "%") + " or htmlattributes like " + quote("%top!=" + old_id + "%") + " or htmlattributes like " + quote("%top in (%" + old_id + "%)") + " or htmlattributes like " + quote("%up=" + old_id + "%") + " or htmlattributes like " + quote("%up!=" + old_id + "%") + " or htmlattributes like " + quote("%up in (%" + old_id + "%)") + " or htmlattributes like " + quote("%previous=" + old_id + "%") + " or htmlattributes like " + quote("%previous!=" + old_id + "%") + " or htmlattributes like " + quote("%previous in (%" + old_id + "%)") + " or htmlattributes like " + quote("%next=" + old_id + "%") + " or htmlattributes like " + quote("%next!=" + old_id + "%") + " or htmlattributes like " + quote("%next in (%" + old_id + "%)") + " or htmlattributes like " + quote("%first=" + old_id + "%") + " or htmlattributes like " + quote("%first!=" + old_id + "%") + " or htmlattributes like " + quote("%first in (%" + old_id + "%)") + " or htmlattributes like " + quote("%last=" + old_id + "%") + " or htmlattributes like " + quote("%last!=" + old_id + "%") + " or htmlattributes like " + quote("%last in (%" + old_id + "%)") + " or htmlattributes like " + quote("%value=" + old_id + "%") + " or htmlattributes like " + quote("%value=\"" + old_id + "\"%") + " or htmlattributes like " + quote("%id=" + old_id + "%") + " or htmlattributes like " + quote("%id=\"" + old_id + "%") + " or htmlattributes like " + quote("%stylesheet=" + old_id + "%") + " or htmlattributes like " + quote("%stylesheet=\"" + old_id + "%") + " or htmlattributes like " + quote("%template=" + old_id + "%") + " or htmlattributes like " + quote("%template=\"" + old_id + "%") + " or htmlattributes like " + quote("%add=" + old_id + "%") + " or htmlattributes like " + quote("%add=\"" + old_id + "%") + " or htmlattributes like " + quote("%item=" + old_id + "%") + " or htmlattributes like " + quote("%item=\"" + old_id + "%") + " or product_options like " + quote("%<hosting:publish_email>" + old_id + "</hosting:publish_email>%") + " or product_options like " + quote("%<hosting:notify_email>" + old_id + "</hosting:notify_email>%") + " or product_options like " + quote("%<hosting:unpublish_email>" + old_id + "</hosting:unpublish_email>%") + ") and id>=" + content_min_id + " and id<=" + content_max_id;
						update_ids = new HashMap<String,String>();
						list_content = new Content(text);
						list_content.records("", "", "", "", "", "", "", this, myconfig, SQL);
						while (list_content.records("", "", "", "", "", "", "", this, myconfig, "")) {
							update_ids.put(list_content.getArchiveId(), list_content.getId());
						}
						update_idss = update_ids.keySet().iterator();
						while (update_idss.hasNext()) {
							String update_id = (String) update_idss.next();
							Content record_content = new Content(text);
							record_content.read(this, myconfig, update_id, "content_archive", "archiveid");
							record_content.setContent(adjust_links(record_content.getContent(), old_id, new_id));
							record_content.setSummary(adjust_links(record_content.getSummary(), old_id, new_id));
							record_content.setHtmlHeader(adjust_links(record_content.getHtmlHeader(), old_id, new_id));
							record_content.setHtmlAttributes(adjust_links(record_content.getHtmlAttributes(), old_id, new_id));
							record_content.setProductOptions(adjust_product_options(record_content.getProductOptions(), old_id, new_id));
							record_content.update(this, myconfig, record_content.getArchiveId(), "content_archive", "archiveid");
						}

						SQL = "select id from content_public where (content like " + quote("%=" + old_id + ".%") + " or content like " + quote("%=" + old_id + ":%") + " or content like " + quote("%=" + old_id + ")%") + " or content like " + quote("%include:" + old_id + "%") + " or content like " + quote("%entry=" + old_id + "%") + " or content like " + quote("%header=" + old_id + "%") + " or content like " + quote("%footer=" + old_id + "%") + " or content like " + quote("%none=" + old_id + "%") + " or content like " + quote("%id=" + old_id + "%") + " or content like " + quote("%id!=" + old_id + "%") + " or content like " + quote("%id in (%" + old_id + "%)") + " or content like " + quote("%top=" + old_id + "%") + " or content like " + quote("%top!=" + old_id + "%") + " or content like " + quote("%top in (%" + old_id + "%)") + " or content like " + quote("%up=" + old_id + "%") + " or content like " + quote("%up!=" + old_id + "%") + " or content like " + quote("%up in (%" + old_id + "%)") + " or content like " + quote("%previous=" + old_id + "%") + " or content like " + quote("%previous!=" + old_id + "%") + " or content like " + quote("%previous in (%" + old_id + "%)") + " or content like " + quote("%next=" + old_id + "%") + " or content like " + quote("%next!=" + old_id + "%") + " or content like " + quote("%next in (%" + old_id + "%)") + " or content like " + quote("%first=" + old_id + "%") + " or content like " + quote("%first!=" + old_id + "%") + " or content like " + quote("%first in (%" + old_id + "%)") + " or content like " + quote("%last=" + old_id + "%") + " or content like " + quote("%last!=" + old_id + "%") + " or content like " + quote("%last in (%" + old_id + "%)") + " or content like " + quote("%value=" + old_id + "%") + " or content like " + quote("%value=\"" + old_id + "\"%") + " or content like " + quote("%id=" + old_id + "%") + " or content like " + quote("%id=\"" + old_id + "%") + " or content like " + quote("%stylesheet=" + old_id + "%") + " or content like " + quote("%stylesheet=\"" + old_id + "%") + " or content like " + quote("%template=" + old_id + "%") + " or content like " + quote("%template=\"" + old_id + "%") + " or content like " + quote("%add=" + old_id + "%") + " or content like " + quote("%add=\"" + old_id + "%") + " or content like " + quote("%item=" + old_id + "%") + " or content like " + quote("%item=\"" + old_id + "%") + " or summary like " + quote("%include:" + old_id + "%") + " or summary like " + quote("%entry=" + old_id + "%") + " or summary like " + quote("%header=" + old_id + "%") + " or summary like " + quote("%footer=" + old_id + "%") + " or summary like " + quote("%none=" + old_id + "%") + " or summary like " + quote("%id=" + old_id + "%") + " or summary like " + quote("%id!=" + old_id + "%") + " or summary like " + quote("%id in (%" + old_id + "%)") + " or summary like " + quote("%top=" + old_id + "%") + " or summary like " + quote("%top!=" + old_id + "%") + " or summary like " + quote("%top in (%" + old_id + "%)") + " or summary like " + quote("%up=" + old_id + "%") + " or summary like " + quote("%up!=" + old_id + "%") + " or summary like " + quote("%up in (%" + old_id + "%)") + " or summary like " + quote("%previous=" + old_id + "%") + " or summary like " + quote("%previous!=" + old_id + "%") + " or summary like " + quote("%previous in (%" + old_id + "%)") + " or summary like " + quote("%next=" + old_id + "%") + " or summary like " + quote("%next!=" + old_id + "%") + " or summary like " + quote("%next in (%" + old_id + "%)") + " or summary like " + quote("%first=" + old_id + "%") + " or summary like " + quote("%first!=" + old_id + "%") + " or summary like " + quote("%first in (%" + old_id + "%)") + " or summary like " + quote("%last=" + old_id + "%") + " or summary like " + quote("%last!=" + old_id + "%") + " or summary like " + quote("%last in (%" + old_id + "%)") + " or summary like " + quote("%value=" + old_id + "%") + " or summary like " + quote("%value=\"" + old_id + "\"%") + " or summary like " + quote("%id=" + old_id + "%") + " or summary like " + quote("%id=\"" + old_id + "%") + " or summary like " + quote("%stylesheet=" + old_id + "%") + " or summary like " + quote("%stylesheet=\"" + old_id + "%") + " or summary like " + quote("%template=" + old_id + "%") + " or summary like " + quote("%template=\"" + old_id + "%") + " or summary like " + quote("%add=" + old_id + "%") + " or summary like " + quote("%add=\"" + old_id + "%") + " or summary like " + quote("%item=" + old_id + "%") + " or summary like " + quote("%item=\"" + old_id + "%") + " or htmlheader like " + quote("%include:" + old_id + "%") + " or htmlheader like " + quote("%entry=" + old_id + "%") + " or htmlheader like " + quote("%header=" + old_id + "%") + " or htmlheader like " + quote("%footer=" + old_id + "%") + " or htmlheader like " + quote("%none=" + old_id + "%") + " or htmlheader like " + quote("%id=" + old_id + "%") + " or htmlheader like " + quote("%id!=" + old_id + "%") + " or htmlheader like " + quote("%id in (%" + old_id + "%)") + " or htmlheader like " + quote("%top=" + old_id + "%") + " or htmlheader like " + quote("%top!=" + old_id + "%") + " or htmlheader like " + quote("%top in (%" + old_id + "%)") + " or htmlheader like " + quote("%up=" + old_id + "%") + " or htmlheader like " + quote("%up!=" + old_id + "%") + " or htmlheader like " + quote("%up in (%" + old_id + "%)") + " or htmlheader like " + quote("%previous=" + old_id + "%") + " or htmlheader like " + quote("%previous!=" + old_id + "%") + " or htmlheader like " + quote("%previous in (%" + old_id + "%)") + " or htmlheader like " + quote("%next=" + old_id + "%") + " or htmlheader like " + quote("%next!=" + old_id + "%") + " or htmlheader like " + quote("%next in (%" + old_id + "%)") + " or htmlheader like " + quote("%first=" + old_id + "%") + " or htmlheader like " + quote("%first!=" + old_id + "%") + " or htmlheader like " + quote("%first in (%" + old_id + "%)") + " or htmlheader like " + quote("%last=" + old_id + "%") + " or htmlheader like " + quote("%last!=" + old_id + "%") + " or htmlheader like " + quote("%last in (%" + old_id + "%)") + " or htmlheader like " + quote("%value=" + old_id + "%") + " or htmlheader like " + quote("%value=\"" + old_id + "\"%") + " or htmlheader like " + quote("%id=" + old_id + "%") + " or htmlheader like " + quote("%id=\"" + old_id + "%") + " or htmlheader like " + quote("%stylesheet=" + old_id + "%") + " or htmlheader like " + quote("%stylesheet=\"" + old_id + "%") + " or htmlheader like " + quote("%template=" + old_id + "%") + " or htmlheader like " + quote("%template=\"" + old_id + "%") + " or htmlheader like " + quote("%add=" + old_id + "%") + " or htmlheader like " + quote("%add=\"" + old_id + "%") + " or htmlheader like " + quote("%item=" + old_id + "%") + " or htmlheader like " + quote("%item=\"" + old_id + "%") + " or htmlattributes like " + quote("%include:" + old_id + "%") + " or htmlattributes like " + quote("%entry=" + old_id + "%") + " or htmlattributes like " + quote("%header=" + old_id + "%") + " or htmlattributes like " + quote("%footer=" + old_id + "%") + " or htmlattributes like " + quote("%none=" + old_id + "%") + " or htmlattributes like " + quote("%id=" + old_id + "%") + " or htmlattributes like " + quote("%id!=" + old_id + "%") + " or htmlattributes like " + quote("%id in (%" + old_id + "%)") + " or htmlattributes like " + quote("%top=" + old_id + "%") + " or htmlattributes like " + quote("%top!=" + old_id + "%") + " or htmlattributes like " + quote("%top in (%" + old_id + "%)") + " or htmlattributes like " + quote("%up=" + old_id + "%") + " or htmlattributes like " + quote("%up!=" + old_id + "%") + " or htmlattributes like " + quote("%up in (%" + old_id + "%)") + " or htmlattributes like " + quote("%previous=" + old_id + "%") + " or htmlattributes like " + quote("%previous!=" + old_id + "%") + " or htmlattributes like " + quote("%previous in (%" + old_id + "%)") + " or htmlattributes like " + quote("%next=" + old_id + "%") + " or htmlattributes like " + quote("%next!=" + old_id + "%") + " or htmlattributes like " + quote("%next in (%" + old_id + "%)") + " or htmlattributes like " + quote("%first=" + old_id + "%") + " or htmlattributes like " + quote("%first!=" + old_id + "%") + " or htmlattributes like " + quote("%first in (%" + old_id + "%)") + " or htmlattributes like " + quote("%last=" + old_id + "%") + " or htmlattributes like " + quote("%last!=" + old_id + "%") + " or htmlattributes like " + quote("%last in (%" + old_id + "%)") + " or htmlattributes like " + quote("%value=" + old_id + "%") + " or htmlattributes like " + quote("%value=\"" + old_id + "\"%") + " or htmlattributes like " + quote("%id=" + old_id + "%") + " or htmlattributes like " + quote("%id=\"" + old_id + "%") + " or htmlattributes like " + quote("%stylesheet=" + old_id + "%") + " or htmlattributes like " + quote("%stylesheet=\"" + old_id + "%") + " or htmlattributes like " + quote("%template=" + old_id + "%") + " or htmlattributes like " + quote("%template=\"" + old_id + "%") + " or htmlattributes like " + quote("%add=" + old_id + "%") + " or htmlattributes like " + quote("%add=\"" + old_id + "%") + " or htmlattributes like " + quote("%item=" + old_id + "%") + " or htmlattributes like " + quote("%item=\"" + old_id + "%") + " or product_options like " + quote("%<hosting:publish_email>" + old_id + "</hosting:publish_email>%") + " or product_options like " + quote("%<hosting:notify_email>" + old_id + "</hosting:notify_email>%") + " or product_options like " + quote("%<hosting:unpublish_email>" + old_id + "</hosting:unpublish_email>%") + ") and id>=" + content_min_id + " and id<=" + content_max_id;
						update_ids = new HashMap<String,String>();
						list_content = new Content(text);
						list_content.records("", "", "", "", "", "", "", this, myconfig, SQL);
						while (list_content.records("", "", "", "", "", "", "", this, myconfig, "")) {
							update_ids.put(list_content.getId(), list_content.getId());
						}
						update_idss = update_ids.keySet().iterator();
						while (update_idss.hasNext()) {
							String update_id = (String) update_idss.next();
							Content record_content = new Content(text);
							record_content.read(this, myconfig, update_id, "content_public", "id");
							record_content.setContent(adjust_links(record_content.getContent(), old_id, new_id));
							record_content.setSummary(adjust_links(record_content.getSummary(), old_id, new_id));
							record_content.setHtmlHeader(adjust_links(record_content.getHtmlHeader(), old_id, new_id));
							record_content.setHtmlAttributes(adjust_links(record_content.getHtmlAttributes(), old_id, new_id));
							record_content.setProductOptions(adjust_product_options(record_content.getProductOptions(), old_id, new_id));
							record_content.update(this, myconfig, record_content.getId(), "content_public", "id");
						}

						try {
							out.print(". ");
							out.flush();
							myresponse.flushBuffer();
							fileout.print(". ");
							fileout.flush();
						} catch (IOException ee) {
						}
					}
				}
				if (updated) {
					force = false; // one or more has been updated so more may have been freed
				} else {
					force = true; // none has been updated so force next/rest if any more left
				}
			}
		}

		if (inserted_ids.containsKey("content")) {
			String SQL = "";
			if (db_type(getDatabase()).equals("mssql")) {
				SQL = "select id from content where (" + is_not_blank("stylesheet") + " or " + is_not_blank("scripts") + " or " + is_not_blank("related") + " or (users_users is not null and substring(users_users,1,250) <> '') or (creators_users is not null and substring(creators_users,1,250) <> '') or (editors_users is not null and substring(editors_users,1,250) <> '') or (publishers_users is not null and substring(publishers_users,1,250) <> '') or (developers_users is not null and substring(developers_users,1,250) <> '') or (administrators_users is not null and substring(administrators_users,1,250) <> '')) and id>=" + content_min_id + " and id<=" + content_max_id;
			} else if (db_type(getDatabase()).equals("oracle")) {
				SQL = "select id from content where (" + is_not_blank("stylesheet") + " or " + is_not_blank("scripts") + " or " + is_not_blank("related") + " or (users_users is not null and to_char(users_users) <> '') or (creators_users is not null and to_char(creators_users) <> '') or (editors_users is not null and to_char(editors_users) <> '') or (publishers_users is not null and to_char(publishers_users) <> '') or (developers_users is not null and to_char(developers_users) <> '') or (administrators_users is not null and to_char(administrators_users) <> '')) and id>=" + content_min_id + " and id<=" + content_max_id;
			} else if (db_type(getDatabase()).equals("db2")) {
				SQL = "select id from content where (" + is_not_blank("stylesheet") + " or " + is_not_blank("scripts") + " or " + is_not_blank("related") + " or (users_users is not null and varchar(users_users,250) <> '') or (creators_users is not null and varchar(creators_users,250) <> '') or (editors_users is not null and varchar(editors_users,250) <> '') or (publishers_users is not null and varchar(publishers_users,250) <> '') or (developers_users is not null and varchar(developers_users,250) <> '') or (administrators_users is not null and varchar(administrators_users,250) <> '')) and id>=" + content_min_id + " and id<=" + content_max_id;
			} else {
				SQL = "select id from content where (" + is_not_blank("stylesheet") + " or " + is_not_blank("scripts") + " or " + is_not_blank("related") + " or " + is_not_blank("users_users") + " or " + is_not_blank("creators_users") + " or " + is_not_blank("editors_users") + " or " + is_not_blank("publishers_users") + " or " + is_not_blank("developers_users") + " or " + is_not_blank("administrators_users") + ") and id>=" + content_min_id + " and id<=" + content_max_id;
			}
			Content list_content = new Content(text);
			list_content.records("", "", "", "", "", "", "", this, myconfig, SQL);
			while (list_content.records("", "", "", "", "", "", "", this, myconfig, "")) {
				Content record_content = new Content();
				record_content.read(this, myconfig, list_content.getId(), "content", "id");
				record_content.setStyleSheet(adjust_ids(record_content.getStyleSheet(), ((HashMap)inserted_ids.get("content"))));
				record_content.setScripts(adjust_ids(record_content.getScripts(), ((HashMap)inserted_ids.get("content"))));
				record_content.setRelated(adjust_ids(record_content.getRelated(), ((HashMap)inserted_ids.get("content"))));
				record_content.setUsersUsers(adjust_ids(record_content.getUsersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setCreatorsUsers(adjust_ids(record_content.getCreatorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setEditorsUsers(adjust_ids(record_content.getEditorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setPublishersUsers(adjust_ids(record_content.getPublishersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setDevelopersUsers(adjust_ids(record_content.getDevelopersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setAdministratorsUsers(adjust_ids(record_content.getAdministratorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.update(this, myconfig, record_content.getId(), "content", "id");

				try {
					out.print(". ");
					out.flush();
					myresponse.flushBuffer();
					fileout.print(". ");
					fileout.flush();
				} catch (IOException ee) {
				}
			}

			if (db_type(getDatabase()).equals("mssql")) {
				SQL = "select id from content_archive where (" + is_not_blank("stylesheet") + " or " + is_not_blank("scripts") + " or " + is_not_blank("related") + " or (users_users is not null and substring(users_users,1,250) <> '') or (creators_users is not null and substring(creators_users,1,250) <> '') or (editors_users is not null and substring(editors_users,1,250) <> '') or (publishers_users is not null and substring(publishers_users,1,250) <> '') or (developers_users is not null and substring(developers_users,1,250) <> '') or (administrators_users is not null and substring(administrators_users,1,250) <> '')) and id>=" + content_min_id + " and id<=" + content_max_id;
			} else if (db_type(getDatabase()).equals("oracle")) {
				SQL = "select id from content_archive where (" + is_not_blank("stylesheet") + " or " + is_not_blank("scripts") + " or " + is_not_blank("related") + " or (users_users is not null and to_char(users_users) <> '') or (creators_users is not null and to_char(creators_users) <> '') or (editors_users is not null and to_char(editors_users) <> '') or (publishers_users is not null and to_char(publishers_users) <> '') or (developers_users is not null and to_char(developers_users) <> '') or (administrators_users is not null and to_char(administrators_users) <> '')) and id>=" + content_min_id + " and id<=" + content_max_id;
			} else if (db_type(getDatabase()).equals("db2")) {
				SQL = "select id from content_archive where (" + is_not_blank("stylesheet") + " or " + is_not_blank("scripts") + " or " + is_not_blank("related") + " or (users_users is not null and varchar(users_users,250) <> '') or (creators_users is not null and varchar(creators_users,250) <> '') or (editors_users is not null and varchar(editors_users,250) <> '') or (publishers_users is not null and varchar(publishers_users,250) <> '') or (developers_users is not null and varchar(developers_users,250) <> '') or (administrators_users is not null and varchar(administrators_users,250) <> '')) and id>=" + content_min_id + " and id<=" + content_max_id;
			} else {
				SQL = "select id from content_archive where (" + is_not_blank("stylesheet") + " or " + is_not_blank("scripts") + " or " + is_not_blank("related") + " or " + is_not_blank("users_users") + " or " + is_not_blank("creators_users") + " or " + is_not_blank("editors_users") + " or " + is_not_blank("publishers_users") + " or " + is_not_blank("developers_users") + " or " + is_not_blank("administrators_users") + ") and id>=" + content_min_id + " and id<=" + content_max_id;
			}
			list_content = new Content(text);
			list_content.records("", "", "", "", "", "", "", this, myconfig, SQL);
			while (list_content.records("", "", "", "", "", "", "", this, myconfig, "")) {
				Content record_content = new Content();
				record_content.read(this, myconfig, list_content.getId(), "content_archive", "id");
				record_content.setStyleSheet(adjust_ids(record_content.getStyleSheet(), ((HashMap)inserted_ids.get("content"))));
				record_content.setScripts(adjust_ids(record_content.getScripts(), ((HashMap)inserted_ids.get("content"))));
				record_content.setRelated(adjust_ids(record_content.getRelated(), ((HashMap)inserted_ids.get("content"))));
				record_content.setUsersUsers(adjust_ids(record_content.getUsersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setCreatorsUsers(adjust_ids(record_content.getCreatorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setEditorsUsers(adjust_ids(record_content.getEditorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setPublishersUsers(adjust_ids(record_content.getPublishersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setDevelopersUsers(adjust_ids(record_content.getDevelopersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setAdministratorsUsers(adjust_ids(record_content.getAdministratorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.update(this, myconfig, record_content.getId(), "content_archive", "id");

				try {
					out.print(". ");
					out.flush();
					myresponse.flushBuffer();
					fileout.print(". ");
					fileout.flush();
				} catch (IOException ee) {
				}
			}

			if (db_type(getDatabase()).equals("mssql")) {
				SQL = "select id from content_public where (" + is_not_blank("stylesheet") + " or " + is_not_blank("scripts") + " or " + is_not_blank("related") + " or (users_users is not null and substring(users_users,1,250) <> '') or (creators_users is not null and substring(creators_users,1,250) <> '') or (editors_users is not null and substring(editors_users,1,250) <> '') or (publishers_users is not null and substring(publishers_users,1,250) <> '') or (developers_users is not null and substring(developers_users,1,250) <> '') or (administrators_users is not null and substring(administrators_users,1,250) <> '')) and id>=" + content_min_id + " and id<=" + content_max_id;
			} else if (db_type(getDatabase()).equals("oracle")) {
				SQL = "select id from content_public where (" + is_not_blank("stylesheet") + " or " + is_not_blank("scripts") + " or " + is_not_blank("related") + " or (users_users is not null and to_char(users_users) <> '') or (creators_users is not null and to_char(creators_users) <> '') or (editors_users is not null and to_char(editors_users) <> '') or (publishers_users is not null and to_char(publishers_users) <> '') or (developers_users is not null and to_char(developers_users) <> '') or (administrators_users is not null and to_char(administrators_users) <> '')) and id>=" + content_min_id + " and id<=" + content_max_id;
			} else if (db_type(getDatabase()).equals("db2")) {
				SQL = "select id from content_public where (" + is_not_blank("stylesheet") + " or " + is_not_blank("scripts") + " or " + is_not_blank("related") + " or (users_users is not null and varchar(users_users,250) <> '') or (creators_users is not null and varchar(creators_users,250) <> '') or (editors_users is not null and varchar(editors_users,250) <> '') or (publishers_users is not null and varchar(publishers_users,250) <> '') or (developers_users is not null and varchar(developers_users,250) <> '') or (administrators_users is not null and varchar(administrators_users,250) <> '')) and id>=" + content_min_id + " and id<=" + content_max_id;
			} else {
				SQL = "select id from content_public where (" + is_not_blank("stylesheet") + " or " + is_not_blank("scripts") + " or " + is_not_blank("related") + " or " + is_not_blank("users_users") + " or " + is_not_blank("creators_users") + " or " + is_not_blank("editors_users") + " or " + is_not_blank("publishers_users") + " or " + is_not_blank("developers_users") + " or " + is_not_blank("administrators_users") + ") and id>=" + content_min_id + " and id<=" + content_max_id;
			}
			list_content = new Content(text);
			list_content.records("", "", "", "", "", "", "", this, myconfig, SQL);
			while (list_content.records("", "", "", "", "", "", "", this, myconfig, "")) {
				Content record_content = new Content();
				record_content.read(this, myconfig, list_content.getId(), "content_public", "id");
				record_content.setStyleSheet(adjust_ids(record_content.getStyleSheet(), ((HashMap)inserted_ids.get("content"))));
				record_content.setScripts(adjust_ids(record_content.getScripts(), ((HashMap)inserted_ids.get("content"))));
				record_content.setRelated(adjust_ids(record_content.getRelated(), ((HashMap)inserted_ids.get("content"))));
				record_content.setUsersUsers(adjust_ids(record_content.getUsersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setCreatorsUsers(adjust_ids(record_content.getCreatorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setEditorsUsers(adjust_ids(record_content.getEditorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setPublishersUsers(adjust_ids(record_content.getPublishersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setDevelopersUsers(adjust_ids(record_content.getDevelopersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setAdministratorsUsers(adjust_ids(record_content.getAdministratorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.update(this, myconfig, record_content.getId(), "content_public", "id");

				try {
					out.print(". ");
					out.flush();
					myresponse.flushBuffer();
					fileout.print(". ");
					fileout.flush();
				} catch (IOException ee) {
				}
			}
		}

		if (inserted_ids.containsKey("contentgroups")) {
			int min_id = min_inserted_id(inserted_ids, "contentgroups");
			int max_id = max_inserted_id(inserted_ids, "contentgroups");
			String SQL = "select id from contentgroups where id>=" + min_id + " and id<=" + max_id;
			Contentgroup list_content = new Contentgroup();
			list_content.records(this, SQL);
			while (list_content.records(this, "")) {
				Contentgroup record_content = new Contentgroup();
				record_content.read(this, list_content.getId());
				record_content.setTemplate(adjust_ids(record_content.getTemplate(), ((HashMap)inserted_ids.get("content"))));
				record_content.setStyleSheet(adjust_ids(record_content.getStyleSheet(), ((HashMap)inserted_ids.get("content"))));
				record_content.setUsersUsers(adjust_ids(record_content.getUsersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setCreatorsUsers(adjust_ids(record_content.getCreatorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setEditorsUsers(adjust_ids(record_content.getEditorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setPublishersUsers(adjust_ids(record_content.getPublishersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setDevelopersUsers(adjust_ids(record_content.getDevelopersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setAdministratorsUsers(adjust_ids(record_content.getAdministratorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.update(this);

				try {
					out.print(". ");
					out.flush();
					myresponse.flushBuffer();
					fileout.print(". ");
					fileout.flush();
				} catch (IOException ee) {
				}
			}
		}

		if (inserted_ids.containsKey("contenttypes")) {
			int min_id = min_inserted_id(inserted_ids, "contenttypes");
			int max_id = max_inserted_id(inserted_ids, "contenttypes");
			String SQL = "select id from contenttypes where id>=" + min_id + " and id<=" + max_id;
			Contenttype list_content = new Contenttype();
			list_content.records(this, SQL);
			while (list_content.records(this, "")) {
				Contenttype record_content = new Contenttype();
				record_content.read(this, list_content.getId());
				record_content.setTemplate(adjust_ids(record_content.getTemplate(), ((HashMap)inserted_ids.get("content"))));
				record_content.setStyleSheet(adjust_ids(record_content.getStyleSheet(), ((HashMap)inserted_ids.get("content"))));
				record_content.setUsersUsers(adjust_ids(record_content.getUsersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setCreatorsUsers(adjust_ids(record_content.getCreatorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setEditorsUsers(adjust_ids(record_content.getEditorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setPublishersUsers(adjust_ids(record_content.getPublishersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setDevelopersUsers(adjust_ids(record_content.getDevelopersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setAdministratorsUsers(adjust_ids(record_content.getAdministratorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.update(this);

				try {
					out.print(". ");
					out.flush();
					myresponse.flushBuffer();
					fileout.print(". ");
					fileout.flush();
				} catch (IOException ee) {
				}
			}
		}

		if (inserted_ids.containsKey("imagegroups")) {
			int min_id = min_inserted_id(inserted_ids, "imagegroups");
			int max_id = max_inserted_id(inserted_ids, "imagegroups");
			String SQL = "select id from imagegroups where id>=" + min_id + " and id<=" + max_id;
			Imagegroup list_content = new Imagegroup();
			list_content.records(this, SQL);
			while (list_content.records(this, "")) {
				Imagegroup record_content = new Imagegroup();
				record_content.read(this, list_content.getId());
				record_content.setTemplate(adjust_ids(record_content.getTemplate(), ((HashMap)inserted_ids.get("content"))));
				record_content.setStyleSheet(adjust_ids(record_content.getStyleSheet(), ((HashMap)inserted_ids.get("content"))));
				record_content.setUsersUsers(adjust_ids(record_content.getUsersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setCreatorsUsers(adjust_ids(record_content.getCreatorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setEditorsUsers(adjust_ids(record_content.getEditorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setPublishersUsers(adjust_ids(record_content.getPublishersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setDevelopersUsers(adjust_ids(record_content.getDevelopersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setAdministratorsUsers(adjust_ids(record_content.getAdministratorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.update(this);

				try {
					out.print(". ");
					out.flush();
					myresponse.flushBuffer();
					fileout.print(". ");
					fileout.flush();
				} catch (IOException ee) {
				}
			}
		}

		if (inserted_ids.containsKey("imagetypes")) {
			int min_id = min_inserted_id(inserted_ids, "imagetypes");
			int max_id = max_inserted_id(inserted_ids, "imagetypes");
			String SQL = "select id from imagetypes where id>=" + min_id + " and id<=" + max_id;
			Imagetype list_content = new Imagetype();
			list_content.records(this, SQL);
			while (list_content.records(this, "")) {
				Imagetype record_content = new Imagetype();
				record_content.read(this, list_content.getId());
				record_content.setTemplate(adjust_ids(record_content.getTemplate(), ((HashMap)inserted_ids.get("content"))));
				record_content.setStyleSheet(adjust_ids(record_content.getStyleSheet(), ((HashMap)inserted_ids.get("content"))));
				record_content.setUsersUsers(adjust_ids(record_content.getUsersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setCreatorsUsers(adjust_ids(record_content.getCreatorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setEditorsUsers(adjust_ids(record_content.getEditorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setPublishersUsers(adjust_ids(record_content.getPublishersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setDevelopersUsers(adjust_ids(record_content.getDevelopersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setAdministratorsUsers(adjust_ids(record_content.getAdministratorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.update(this);

				try {
					out.print(". ");
					out.flush();
					myresponse.flushBuffer();
					fileout.print(". ");
					fileout.flush();
				} catch (IOException ee) {
				}
			}
		}

		if (inserted_ids.containsKey("filegroups")) {
			int min_id = min_inserted_id(inserted_ids, "filegroups");
			int max_id = max_inserted_id(inserted_ids, "filegroups");
			String SQL = "select id from filegroups where id>=" + min_id + " and id<=" + max_id;
			Filegroup list_content = new Filegroup();
			list_content.records(this, SQL);
			while (list_content.records(this, "")) {
				Filegroup record_content = new Filegroup();
				record_content.read(this, list_content.getId());
				record_content.setTemplate(adjust_ids(record_content.getTemplate(), ((HashMap)inserted_ids.get("content"))));
				record_content.setStyleSheet(adjust_ids(record_content.getStyleSheet(), ((HashMap)inserted_ids.get("content"))));
				record_content.setUsersUsers(adjust_ids(record_content.getUsersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setCreatorsUsers(adjust_ids(record_content.getCreatorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setEditorsUsers(adjust_ids(record_content.getEditorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setPublishersUsers(adjust_ids(record_content.getPublishersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setDevelopersUsers(adjust_ids(record_content.getDevelopersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setAdministratorsUsers(adjust_ids(record_content.getAdministratorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.update(this);

				try {
					out.print(". ");
					out.flush();
					myresponse.flushBuffer();
					fileout.print(". ");
					fileout.flush();
				} catch (IOException ee) {
				}
			}
		}

		if (inserted_ids.containsKey("filetypes")) {
			int min_id = min_inserted_id(inserted_ids, "filetypes");
			int max_id = max_inserted_id(inserted_ids, "filetypes");
			String SQL = "select id from filetypes where id>=" + min_id + " and id<=" + max_id;
			Filetype list_content = new Filetype();
			list_content.records(this, SQL);
			while (list_content.records(this, "")) {
				Filetype record_content = new Filetype();
				record_content.read(this, list_content.getId());
				record_content.setTemplate(adjust_ids(record_content.getTemplate(), ((HashMap)inserted_ids.get("content"))));
				record_content.setStyleSheet(adjust_ids(record_content.getStyleSheet(), ((HashMap)inserted_ids.get("content"))));
				record_content.setUsersUsers(adjust_ids(record_content.getUsersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setCreatorsUsers(adjust_ids(record_content.getCreatorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setEditorsUsers(adjust_ids(record_content.getEditorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setPublishersUsers(adjust_ids(record_content.getPublishersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setDevelopersUsers(adjust_ids(record_content.getDevelopersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setAdministratorsUsers(adjust_ids(record_content.getAdministratorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.update(this);

				try {
					out.print(". ");
					out.flush();
					myresponse.flushBuffer();
					fileout.print(". ");
					fileout.flush();
				} catch (IOException ee) {
				}
			}
		}

		if (inserted_ids.containsKey("linkgroups")) {
			int min_id = min_inserted_id(inserted_ids, "linkgroups");
			int max_id = max_inserted_id(inserted_ids, "linkgroups");
			String SQL = "select id from linkgroups where id>=" + min_id + " and id<=" + max_id;
			Linkgroup list_content = new Linkgroup();
			list_content.records(this, SQL);
			while (list_content.records(this, "")) {
				Linkgroup record_content = new Linkgroup();
				record_content.read(this, list_content.getId());
				record_content.setTemplate(adjust_ids(record_content.getTemplate(), ((HashMap)inserted_ids.get("content"))));
				record_content.setStyleSheet(adjust_ids(record_content.getStyleSheet(), ((HashMap)inserted_ids.get("content"))));
				record_content.setUsersUsers(adjust_ids(record_content.getUsersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setCreatorsUsers(adjust_ids(record_content.getCreatorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setEditorsUsers(adjust_ids(record_content.getEditorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setPublishersUsers(adjust_ids(record_content.getPublishersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setDevelopersUsers(adjust_ids(record_content.getDevelopersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setAdministratorsUsers(adjust_ids(record_content.getAdministratorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.update(this);

				try {
					out.print(". ");
					out.flush();
					myresponse.flushBuffer();
					fileout.print(". ");
					fileout.flush();
				} catch (IOException ee) {
				}
			}
		}

		if (inserted_ids.containsKey("linktypes")) {
			int min_id = min_inserted_id(inserted_ids, "linktypes");
			int max_id = max_inserted_id(inserted_ids, "linktypes");
			String SQL = "select id from linktypes where id>=" + min_id + " and id<=" + max_id;
			Linktype list_content = new Linktype();
			list_content.records(this, SQL);
			while (list_content.records(this, "")) {
				Linktype record_content = new Linktype();
				record_content.read(this, list_content.getId());
				record_content.setTemplate(adjust_ids(record_content.getTemplate(), ((HashMap)inserted_ids.get("content"))));
				record_content.setStyleSheet(adjust_ids(record_content.getStyleSheet(), ((HashMap)inserted_ids.get("content"))));
				record_content.setUsersUsers(adjust_ids(record_content.getUsersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setCreatorsUsers(adjust_ids(record_content.getCreatorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setEditorsUsers(adjust_ids(record_content.getEditorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setPublishersUsers(adjust_ids(record_content.getPublishersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setDevelopersUsers(adjust_ids(record_content.getDevelopersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setAdministratorsUsers(adjust_ids(record_content.getAdministratorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.update(this);

				try {
					out.print(". ");
					out.flush();
					myresponse.flushBuffer();
					fileout.print(". ");
					fileout.flush();
				} catch (IOException ee) {
				}
			}
		}

		if (inserted_ids.containsKey("productgroups")) {
			int min_id = min_inserted_id(inserted_ids, "productgroups");
			int max_id = max_inserted_id(inserted_ids, "productgroups");
			String SQL = "select id from productgroups where id>=" + min_id + " and id<=" + max_id;
			Productgroup list_content = new Productgroup();
			list_content.records(this, SQL);
			while (list_content.records(this, "")) {
				Productgroup record_content = new Productgroup();
				record_content.read(this, list_content.getId());
				record_content.setTemplate(adjust_ids(record_content.getTemplate(), ((HashMap)inserted_ids.get("content"))));
				record_content.setStyleSheet(adjust_ids(record_content.getStyleSheet(), ((HashMap)inserted_ids.get("content"))));
				record_content.setUsersUsers(adjust_ids(record_content.getUsersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setCreatorsUsers(adjust_ids(record_content.getCreatorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setEditorsUsers(adjust_ids(record_content.getEditorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setPublishersUsers(adjust_ids(record_content.getPublishersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setDevelopersUsers(adjust_ids(record_content.getDevelopersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setAdministratorsUsers(adjust_ids(record_content.getAdministratorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.update(this);

				try {
					out.print(". ");
					out.flush();
					myresponse.flushBuffer();
					fileout.print(". ");
					fileout.flush();
				} catch (IOException ee) {
				}
			}
		}

		if (inserted_ids.containsKey("producttypes")) {
			int min_id = min_inserted_id(inserted_ids, "producttypes");
			int max_id = max_inserted_id(inserted_ids, "producttypes");
			String SQL = "select id from producttypes where id>=" + min_id + " and id<=" + max_id;
			Producttype list_content = new Producttype();
			list_content.records(this, SQL);
			while (list_content.records(this, "")) {
				Producttype record_content = new Producttype();
				record_content.read(this, list_content.getId());
				record_content.setTemplate(adjust_ids(record_content.getTemplate(), ((HashMap)inserted_ids.get("content"))));
				record_content.setStyleSheet(adjust_ids(record_content.getStyleSheet(), ((HashMap)inserted_ids.get("content"))));
				record_content.setUsersUsers(adjust_ids(record_content.getUsersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setCreatorsUsers(adjust_ids(record_content.getCreatorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setEditorsUsers(adjust_ids(record_content.getEditorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setPublishersUsers(adjust_ids(record_content.getPublishersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setDevelopersUsers(adjust_ids(record_content.getDevelopersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setAdministratorsUsers(adjust_ids(record_content.getAdministratorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.update(this);

				try {
					out.print(". ");
					out.flush();
					myresponse.flushBuffer();
					fileout.print(". ");
					fileout.flush();
				} catch (IOException ee) {
				}
			}
		}

		if (inserted_ids.containsKey("data") && inserted_ids.containsKey("users")) {
			int min_id = min_inserted_id(inserted_ids, "data");
			int max_id = max_inserted_id(inserted_ids, "data");
			String SQL = "select id from data where id>=" + min_id + " and id<=" + max_id;
			Databases list_content = new Databases();
			list_content.records("", "", "", "", "", "", "", this, myconfig, SQL);
			while (list_content.records("", "", "", "", "", "", "", this, myconfig, "")) {
				Databases record_content = new Databases();
				record_content.read(this, myconfig, list_content.getId());
				record_content.setUsersUsers(adjust_ids(record_content.getUsersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setCreatorsUsers(adjust_ids(record_content.getCreatorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setEditorsUsers(adjust_ids(record_content.getEditorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setPublishersUsers(adjust_ids(record_content.getPublishersUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.setAdministratorsUsers(adjust_ids(record_content.getAdministratorsUsers(), ((HashMap)inserted_ids.get("users"))));
				record_content.update(this);

				try {
					out.print(". ");
					out.flush();
					myresponse.flushBuffer();
					fileout.print(". ");
					fileout.flush();
				} catch (IOException ee) {
				}
			}
		}
	}
	private String read_line(BufferedReader file) {
		String my_line = null;
		try {
			my_line = file.readLine();
		} catch (IOException e) {
			my_line = null;
		}
		return my_line;
	}



	/* exportXML */

	public void exportXML(JspWriter out, ServletContext server, String servername, String DOCUMENT_ROOT, Configuration myconfig, Request myrequest) {
		boolean export_content = false;
		boolean export_images = false;
		boolean export_files = false;
		boolean export_ecommerce = false;
		boolean export_databases = false;
		boolean export_users = false;
		boolean export_workflow = false;
		boolean export_websites = false;
		boolean export_config = false;
		boolean export_hosting = false;
		boolean export_other = false;
		boolean export_all_folder_files = false;
		boolean export_blanks = false;
		if (myrequest.getParameter("export_content").equals("yes")) export_content = true;
		if (myrequest.getParameter("export_images").equals("yes")) export_images = true;
		if (myrequest.getParameter("export_files").equals("yes")) export_files = true;
		if (myrequest.getParameter("export_ecommerce").equals("yes")) export_ecommerce = true;
		if (myrequest.getParameter("export_databases").equals("yes")) export_databases = true;
		if (myrequest.getParameter("export_users").equals("yes")) export_users = true;
		if (myrequest.getParameter("export_workflow").equals("yes")) export_workflow = true;
		if (myrequest.getParameter("export_websites").equals("yes")) export_websites = true;
		if (myrequest.getParameter("export_config").equals("yes")) export_config = true;
		if (myrequest.getParameter("export_hosting").equals("yes")) export_hosting = true;
		if (myrequest.getParameter("export_other").equals("yes")) export_other = true;
		if (myrequest.getParameter("export_allfolderfiles").equals("yes")) export_all_folder_files = true;
		if (myrequest.getParameter("export_blanks").equals("yes")) export_blanks = true;

		String filename = "/" + text.display("adminpath") + "/database/database";
		filename += "." + servername;
		filename += "." + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()).replaceAll(":", "-").replaceAll(" ", "_");
		filename += ".";
		for (int j=1; j<32; j++) {
			filename += "" + (char)('a' + Integer.parseInt(Common.numberformat("" + Math.random()*25, 0)));
		}
		filename += ".xml";

		try {
			out.println("<p><b>Exporting database to:</b><br><nobr>" + Common.getRealPath(server, filename) + "</nobr></p><hr>");
			out.flush();
		} catch (IOException e) {
		}

		PrintWriter file = null;
		if (! myrequest.getParameter("description").equals("")) {
			try {
				file = new PrintWriter(new FileOutputStream(Common.getRealPath(server, filename.replaceAll("\\.xml", ".html"))));
			} catch (Exception e) {
			}
			file.println(myrequest.getParameter("description").replaceAll("\r\n", "<br>").replaceAll("\r", "<br>").replaceAll("\n", "<br>"));
			file.close();
		}

		file = null;
		try {
			file = new PrintWriter(new OutputStreamWriter(new FileOutputStream(Common.getRealPath(server, filename)), "UTF8"));
		} catch (Exception e) {
			System.out.println("HardCore/DB.exportXML:" + e);
		}

		file.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>");
		file.println("<database name=\"Asbru Web Content Management\">");

		if (export_content || export_ecommerce) export_table(out, file, "currencies", "id", "", export_blanks, true);
		if (export_content) export_table(out, file, "content", "id", export_blanks);
		if (export_content) export_table(out, file, "content_elements", "content_id", "content_id,element,element_order", export_blanks, true);						// must follow content
		if (export_content) export_table(out, file, "content_public", "id", export_blanks);												// must follow content
		if (export_content) export_table(out, file, "content_public_elements", "content_id", "content_id,element,element_order", export_blanks, true);					// must follow content
		if (export_content) export_table(out, file, "content_archive", "archiveid", export_blanks);											// must follow content
		if (export_content) export_table(out, file, "content_archive_elements", "content_archiveid", "content_archiveid,content_id,element,element_order", export_blanks, true);	// must follow content + content_archive
		if (export_content) export_table(out, file, "contentgroups", "id", export_blanks);
		if (export_content) export_table(out, file, "contenttypes", "id", export_blanks);
		if (export_content) export_table(out, file, "fileformats", "id", export_blanks);
		if (export_content) export_table(out, file, "filegroups", "id", export_blanks);
		if (export_content) export_table(out, file, "filetypes", "id", export_blanks);
		if (export_hosting) export_table(out, file, "hosting", "id", export_blanks);
		if (export_hosting) export_table(out, file, "hostinggroups", "id", export_blanks);
		if (export_hosting) export_table(out, file, "hostingtypes", "id", export_blanks);
		if (export_content) export_table(out, file, "imageformats", "id", export_blanks);
		if (export_content) export_table(out, file, "imagegroups", "id", export_blanks);
		if (export_content) export_table(out, file, "imagetypes", "id", export_blanks);
		if (export_content) export_table(out, file, "linkgroups", "id", export_blanks);
		if (export_content) export_table(out, file, "linktypes", "id", export_blanks);
		if (export_content) export_table(out, file, "productgroups", "id", export_blanks);
		if (export_content) export_table(out, file, "producttypes", "id", export_blanks);
		if (export_other) export_table(out, file, "fonts", "id", export_blanks);
		if (export_content) export_table(out, file, "elements", "id", export_blanks);
		if (export_content) export_table(out, file, "versions", "id", export_blanks);
		if (export_content) export_table(out, file, "segments", "id", export_blanks);
		if (export_content) export_table(out, file, "usertests", "id", export_blanks);
		if (export_users) export_table(out, file, "usergroups", "id", export_blanks);
		if (export_users) export_table(out, file, "usertypes", "id", export_blanks);
		if (export_users) export_table(out, file, "usergroups2", "usergroup", export_blanks);
		if (export_users) export_table(out, file, "usertypes2", "usertype", export_blanks);
		if (export_users) export_table(out, file, "users", "id", export_blanks);
		if (export_users) export_table(out, file, "user2groups", "user_id,usergroup", export_blanks);
		if (export_users) export_table(out, file, "user2types", "user_id,usertype", export_blanks);
		if (export_users) export_table(out, file, "users_usergroups", "id", export_blanks);
		if (export_users) export_table(out, file, "users_usertypes", "id", export_blanks);
		if (export_users) export_table(out, file, "permissions", "id", export_blanks);
		if (export_other) export_table(out, file, "guestbook", "id", export_blanks);
		if (export_ecommerce) export_table(out, file, "discounts", "id", "", export_blanks, true);		// must follow currencies
		if (export_ecommerce) export_table(out, file, "shipping", "id", "", export_blanks, true);		// must follow currencies
		if (export_ecommerce) export_table(out, file, "tax", "id", "", export_blanks, true);			// must follow currencies
		if (export_ecommerce) export_table(out, file, "orders", "id", "", export_blanks, true);			// must follow currencies + users
		if (export_ecommerce) export_table(out, file, "orderitems", "id", "", export_blanks, true);		// must follow content + currencies + orders
		if (export_websites) export_table(out, file, "websites", "id", export_blanks);				// must follow content
		if (export_workflow) export_table(out, file, "workflow", "id", export_blanks);				// must follow content

		if (export_databases) export_table(out, file, "data", "id", "", export_blanks, true);
		Databases databases = new Databases(text);
		String SQL = "select * from data order by id";
		databases.records("", "", "", "", "", "", "", this, myconfig, SQL);
		while(databases.records("", "", "", "", "", "", "", this, myconfig, "")) {
			if (export_databases) export_table(out, file, "data" + databases.getId(), "id", export_blanks);
		}

		if (export_config) export_table(out, file, "config", "configname", true);			// must follow content + currencies

		if (export_files) {
			HashMap<String,String> filenames = contentclass_filenames("file");
			export_folder(out, file, servername, DOCUMENT_ROOT, "", myconfig, filenames);
			if (export_all_folder_files) {
				export_folder(out, file, servername, DOCUMENT_ROOT, myconfig.get(this, "URLfilepath"), myconfig, null);
			}
		}
		if (export_images) {
			HashMap<String,String> filenames = contentclass_filenames("image");
			export_folder(out, file, servername, DOCUMENT_ROOT, "", myconfig, filenames);
			if (export_all_folder_files) {
				export_folder(out, file, servername, DOCUMENT_ROOT, myconfig.get(this, "URLimagepath"), myconfig, null);
			}
		}

		file.println("</database>");

		file.close();
		file = null;

		try {
			out.println("<p><hr><b>Done - Download:</b><br><nobr><a href=\"" + filename + "\">" + filename + "</a></nobr></p>");
			out.println("<script>window.opener.location.reload();</script>");
			out.flush();
		} catch (IOException e) {
		}
	}



	private HashMap<String,String> contentclass_filenames(String contentclass) {
		HashMap<String,String> filenames = new HashMap<String,String>();
		String SQL = "select server_filename from content where " + is_not_blank("server_filename") + " and contentclass in (" + quote(contentclass) + ")";
		LinkedHashMap values = query_values(SQL);
		for (int i=0; i<values.size(); i++) {
			String value = "" + values.get("" + i);
			filenames.put(value, value);
		}
		SQL = "select server_filename from content_public where " + is_not_blank("server_filename") + " and contentclass in (" + quote(contentclass) + ")";
		values = query_values(SQL);
		for (int i=0; i<values.size(); i++) {
			String value = "" + values.get("" + i);
			filenames.put(value, value);
		}
		SQL = "select server_filename from content_archive where " + is_not_blank("server_filename") + " and contentclass in (" + quote(contentclass) + ")";
		values = query_values(SQL);
		for (int i=0; i<values.size(); i++) {
			String value = "" + values.get("" + i);
			filenames.put(value, value);
		}
		return filenames;
	}



	/* export_folder */

	private void export_folder(JspWriter out, PrintWriter file, String servername, String DOCUMENT_ROOT, String folder, Configuration myconfig) {
		export_folder(out, file, servername, DOCUMENT_ROOT, folder, myconfig, null);
	}
	private void export_folder(JspWriter out, PrintWriter file, String servername, String DOCUMENT_ROOT, String folder, Configuration myconfig, HashMap<String,String> filenames) {
		try {
			out.println("<p>" + myconfig.get(this, "URLrootpath") + folder + "</p>");
			out.print("<p>");
			out.flush();
		} catch (IOException e) {
		}
		File fh = new File(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + folder);
		if (fh.exists()) {
			String folderbegin = " <folder name=\"" + folder + "\">";
			String folderend = " </folder>";
			String[] files = new File(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + folder).list();
			for (int i=0; i<files.length; i++) {
				try {
					out.print(". ");
					out.flush();
				} catch (IOException e) {
				}
				fh = new File(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + folder + files[i]);
				if (fh.exists() && fh.isFile() && ((filenames == null) || (filenames.get(folder + files[i]) != null))) {
					if (filenames != null) filenames.put(folder + files[i], null);
					try {
						if (! folderbegin.equals("")) {
							file.println(folderbegin);
							folderbegin = "";
						}
						file.println("  <file name=\"" + files[i] + "\">");
						DataInputStream input = new DataInputStream(new FileInputStream(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + folder + files[i]));
						byte[] data = new byte[0];
						byte[] buffer = new byte[4096];
						int readLength = 0;
						while ((readLength = input.read(buffer, 0, buffer.length)) > 0) {
							byte[] tmp = new byte[data.length + readLength];
							System.arraycopy(data, 0, tmp, 0, data.length);
							System.arraycopy(buffer, 0, tmp, data.length, readLength);
							data = tmp;
							file.println("" + Base64.encodeBytes(data).replaceAll("[\r\n]",""));
							data = new byte[0];
						}
						input.close();
						input = null;
						file.println("  </file>");
					} catch (FileNotFoundException e) {
					} catch (IOException e) {
					}
				}
			}
			if (folderbegin.equals("")) {
				file.println(folderend);
			}
			for (int i=0; i<files.length; i++) {
				try {
					out.print(". ");
					out.flush();
				} catch (IOException e) {
				}
				fh = new File(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + folder + files[i]);
				if (fh.exists() && fh.isDirectory() && (filenames != null)) {
					Iterator myfilenames = filenames.keySet().iterator();
					while (myfilenames.hasNext()) {
						String mypathname = "" + myfilenames.next();
						if (mypathname.startsWith(folder + files[i] + "/")) {
							export_folder(out, file, servername, DOCUMENT_ROOT, folder + files[i] + "/", myconfig, filenames);
							break;
						}
					}
				}
			}
		}
		try {
			out.println("</p>");
			out.flush();
		} catch (IOException e) {
		}
	}



	/* export_table */

	private void export_table(JspWriter out, PrintWriter file, String table, String id, boolean export_blanks) {
		export_table(out, file, table, id, "", export_blanks, export_blanks);
	}
	private void export_table(JspWriter out, PrintWriter file, String table, String id, String orderby, boolean export_blanks) {
		export_table(out, file, table, id, orderby, export_blanks, export_blanks);
	}
	private void export_table(JspWriter out, PrintWriter file, String table, String id, String orderby, boolean export_blanks, boolean export_zeros) {
		try {
			out.println("<p>" + table + "</p>");
			out.println("<p>");
			out.flush();
		} catch (IOException e) {
		}
		file.println(" <table name=\"" + table + "\">");
		String SQL = "select * from " + Common.SQL_clean(table) + " order by " + Common.SQL_clean(id);
		if (! orderby.equals("")) {
			SQL = "select * from " + Common.SQL_clean(table) + " order by " + Common.SQL_clean(orderby);
		}
		Statement st = null;
		ResultSet rs = null;
		try {
			st = createStatement();
			if (DEBUG) System.out.println("HardCore/DB.export_table:"+SQL);
			rs = st.executeQuery(SQL);
			while (rs.next()) {
				HashMap<String,String> row = DB.resultSetToLowerCase(rs);
				try {
					out.print(". ");
					out.flush();
				} catch (IOException e) {
				}
				file.println("  <row>");
				ResultSetMetaData columns = rs.getMetaData();
				for (int i=1; i<=columns.getColumnCount(); i++) {
					String column = columns.getColumnName(i).toLowerCase();
					String value = "";
					if (row.get(column) != null) value = "" + row.get(column);
					value = value.replaceAll("^( *)", "").replaceAll("( *)$", "");
					if (table.equals("users") && (column.equals("keywords") || column.equals("description") || column.equals("password") || column.equals("email") || column.equals("card_type") || column.equals("card_number") || column.equals("card_issuedmonth") || column.equals("card_issuedyear") || column.equals("card_expirymonth") || column.equals("card_expiryyear") || column.equals("card_name") || column.equals("card_cvc") || column.equals("card_issue") || column.equals("card_postalcode") || column.equals("delivery_name") || column.equals("delivery_organisation") || column.equals("delivery_address") || column.equals("delivery_postalcode") || column.equals("delivery_city") || column.equals("delivery_state") || column.equals("delivery_country") || column.equals("delivery_phone") || column.equals("delivery_fax") || column.equals("delivery_email") || column.equals("delivery_website") || column.equals("invoice_name") || column.equals("invoice_organisation") || column.equals("invoice_address") || column.equals("invoice_postalcode") || column.equals("invoice_city") || column.equals("invoice_state") || column.equals("invoice_country") || column.equals("invoice_phone") || column.equals("invoice_fax") || column.equals("invoice_email") || column.equals("invoice_website") || column.equals("notes") || column.equals("userinfo"))) {
						value = this.decrypt(value);
					}
					if ((value.equals("")) && (! export_blanks)) {
						// skip
					} else if (numeric_table_column(table, column.toLowerCase()) && (value.equals("0")) && (! export_zeros)) {
						// skip
					} else {
						file.println("   <column name=\"" + column.toLowerCase() + "\" value=\"" + html.encode(value) + "\" />");
					}
				}
				file.println("  </row>");
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
		} catch (SQLException e) {
			if (rs != null) try { rs.close(); } catch (SQLException ee) { ; }
			rs = null;
			if (st != null) try { st.close(); } catch (SQLException ee) { ; }
			st = null;
		}
		rs = null;
		st = null;
		file.println(" </table>");
		try {
			out.println("</p>");
			out.flush();
		} catch (IOException e) {
		}
	}



	/* import_folder */

	public void import_folder(JspWriter out, DB db, String equals, Configuration myconfig, String DOCUMENT_ROOT, String rootpath, String folder, String contentclass) {
		try {
			out.println("<p>" + rootpath + folder + "</p>");
			out.print("<p>");
			out.flush();
		} catch (IOException e) {
		}
		File fh = new File(DOCUMENT_ROOT + rootpath + folder);
		if (fh.exists()) {
			String[] files = new File(DOCUMENT_ROOT + rootpath + folder).list();
			for (int i=0; i<files.length; i++) {
				try {
					out.print(". ");
					out.flush();
				} catch (IOException e) {
				}
				fh = new File(DOCUMENT_ROOT + myconfig.get(this, "URLrootpath") + folder + files[i]);
				if (fh.exists() && fh.isFile()) {
					String filename = files[i];
					String filenameextension = filename.substring(filename.lastIndexOf(".")+1);
					HashMap row = query_records("select id from " + Common.SQL_clean(contentclass) + "formats where filenameextension=" + quote(Common.SQL_clean(filenameextension)));
					if (row.size() > 0) {
						String SQL = "";
						if (db_type(getDatabase()).equals("mssql")) {
							SQL = "select id from content where server_filename is not null and substring(server_filename,1,250)" + equals + "" + quote(Common.SQL_clean(folder + filename));
						} else if (db_type(getDatabase()).equals("oracle")) {
							SQL = "select id from content where server_filename is not null and to_char(server_filename)" + equals + "" + quote(Common.SQL_clean(folder + filename));
						} else if (db_type(getDatabase()).equals("db2")) {
							SQL = "select id from content where server_filename is not null and varchar(server_filename,250)" + equals + "" + quote(Common.SQL_clean(folder + filename));
						} else {
							SQL = "select id from content where server_filename" + equals + "" + quote(Common.SQL_clean(folder + filename));
						}
						if (DEBUG) System.out.println("HardCore/DB.import_folder:"+SQL);
						row = query_records(SQL);
						if (row.size() == 0) {
							Content content = new Content(text);
							content.setTitle(filename);
							content.setContentClass(contentclass);
							content.setServerFilename(folder + filename);
							String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
							String username = myconfig.get(db, "superadmin");
							content.setCreated(timestamp);
							content.setCreatedBy(username);
							content.setUpdated(timestamp);
							content.setUpdatedBy(username);
							content.setPublished(timestamp);
							content.setPublishedBy(username);
							content.create(db, myconfig, "content", "id");
							content.create(db, myconfig, "content_public", "id");
						}
					}
				}
			}
		}
		try {
			out.println("</p>");
			out.flush();
		} catch (IOException e) {
		}
	}



	/* import_file_select_options */

	public static String import_file_select_options(Text text, String DOCUMENT_ROOT, String servername) {
		String options = "";
		LinkedHashMap<String,String> datafiles = new LinkedHashMap<String,String>();
		File fh = new File(DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/");
		if (fh.exists()) {
			String[] files = new File(DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/").list();
			for (int i=0; i<files.length; i++) {
				datafiles.put(files[i], files[i]);
			}
			datafiles = Common.LinkedHashMapSortedByValue(datafiles, true);
			Iterator entries = datafiles.keySet().iterator();
			while (entries.hasNext()) {
				String entry = "" + entries.next();
				Pattern publicPattern = Pattern.compile("^(database)\\.([-,_a-zA-Z0-9\\(\\)]*)\\.xml$");
				Matcher publicMatches = publicPattern.matcher(entry);
				Pattern privatePattern = Pattern.compile("^(database|hardcore)\\." + servername + "\\.([-_0-9]*)\\.([a-zA-Z0-9]*)\\.xml$");
				Matcher privateMatches = privatePattern.matcher(entry);
				if (publicMatches.find()) {
					options = options + "<option value=\"" + entry + "\">" + publicMatches.group(2).replaceAll("_", " ");
				} else if (privateMatches.find()) {
					options = options + "<option value=\"" + entry + "\">" + privateMatches.group(2).replaceAll("_", " ");
				}
			}
		}
		return options;
	}



	/* import_file_radio_buttons */

	public static String import_file_radio_buttons(Text text, String type, int number_of_columns, String DOCUMENT_ROOT, String servername, DB db, Configuration myconfig) {
		String options = "";
		LinkedHashMap<String,String> datafiles = new LinkedHashMap<String,String>();
		int number_of_datafiles = 0;
		HashMap<String,String> options_starts = new HashMap<String,String>();
		HashMap<String,String> options_cells = new HashMap<String,String>();
		HashMap<String,String> options_ends = new HashMap<String,String>();
		File fh = new File(DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/");
		if (fh.exists()) {
			String[] files = new File(DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/").list();
			for (int i=0; i<files.length; i++) {
				if ((files[i].startsWith("database.") || files[i].startsWith("hardcore.")) && (files[i].endsWith(".xml"))) {
					datafiles.put(files[i], files[i]);
				}
			}
			datafiles = Common.LinkedHashMapSortedByValue(datafiles, true);
			Iterator entries = datafiles.keySet().iterator();
			while (entries.hasNext()) {
				String entry = "" + entries.next();
				String description = "";
				fh = new File(DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/" + entry.replaceAll(".xml$", ".html"));
				if (fh.exists()) {
					BufferedReader input = null;
					try {
						input = new BufferedReader(new FileReader(fh));
						String my_line;
						while ((my_line = input.readLine()) != null) {
							description += "" + my_line;
						}
						input.close();
					} catch (FileNotFoundException e) {
						if (input != null) try { input.close(); } catch (IOException ee) { ; }
					} catch (IOException e) {
						if (input != null) try { input.close(); } catch (IOException ee) { ; }
					}
				}

				options_starts.put("" + (number_of_datafiles + 1), "");
				options_cells.put("" + (number_of_datafiles + 1), "");
				options_cells.put("" + (number_of_datafiles + 1), "");
				options_ends.put("" + (number_of_datafiles + 1), "");
				Pattern publicPattern = Pattern.compile("^(database)\\.([-,_a-zA-Z0-9\\(\\)]*)\\.xml$");
				Matcher publicMatches = publicPattern.matcher(entry);
				Pattern privatePattern = Pattern.compile("^(database|hardcore)\\." + servername + "\\.([0-9][0-9][0-9][0-9])-([0-9][0-9])-([0-9][0-9])_([0-9][0-9])-([0-9][0-9])-([0-9][0-9])\\.([a-zA-Z0-9]*)\\.xml$");
				Matcher privateMatches = privatePattern.matcher(entry);
				if (publicMatches.find()) {
					Pattern addonPattern = Pattern.compile("[^a-z]Add-On[^a-z]");
					Matcher addonMatches = addonPattern.matcher(entry);
					Pattern websitedesignPattern = Pattern.compile("[^a-z](Website_Template|Website_Design)[^a-z]");
					Matcher websitedesignMatches = websitedesignPattern.matcher(entry);
					Pattern communityPattern = Pattern.compile("[^a-z]Community[^a-z]");
					Matcher communityMatches = communityPattern.matcher(entry);
					Pattern databasesPattern = Pattern.compile("[^a-z]Databases[^a-z]");
					Matcher databasesMatches = databasesPattern.matcher(entry);
					Pattern ecommercePattern = Pattern.compile("[^a-z]E-Commerce[^a-z]");
					Matcher ecommerceMatches = ecommercePattern.matcher(entry);
					Pattern statisticsPattern = Pattern.compile("[^a-z]Statistics[^a-z]");
					Matcher statisticsMatches = statisticsPattern.matcher(entry);
					Pattern personalPattern = Pattern.compile("[^a-z](Personal|Website)[^a-z]");
					Matcher personalMatches = personalPattern.matcher(entry);
					Pattern professionalPattern = Pattern.compile("[^a-z]Professional[^a-z]");
					Matcher professionalMatches = professionalPattern.matcher(entry);
					Pattern enterprisePattern = Pattern.compile("[^a-z]Enterprise[^a-z]");
					Matcher enterpriseMatches = enterprisePattern.matcher(entry);
					Pattern hostingPattern = Pattern.compile("[^a-z]Hosting[^a-z]");
					Matcher hostingMatches = hostingPattern.matcher(entry);
					Pattern suitePattern = Pattern.compile("[^a-z]Suite[^a-z]");
					Matcher suiteMatches = suitePattern.matcher(entry);
					boolean addonMatchFound = addonMatches.find();
					boolean websitedesignMatchFound = websitedesignMatches.find();
					boolean communityMatchFound = communityMatches.find();
					boolean databasesMatchFound = databasesMatches.find();
					boolean ecommerceMatchFound = ecommerceMatches.find();
					boolean statisticsMatchFound = statisticsMatches.find();
					boolean personalMatchFound = personalMatches.find();
					boolean professionalMatchFound = professionalMatches.find();
					boolean enterpriseMatchFound = enterpriseMatches.find();
					boolean hostingMatchFound = hostingMatches.find();
					boolean suiteMatchFound = suiteMatches.find();
					if ((type.equals("")) || ((type.equals("addons")) && addonMatchFound) || ((type.equals("designs")) && websitedesignMatchFound) || ((type.equals("websites")) && (! addonMatchFound) && (! websitedesignMatchFound))) {
						boolean disabled = false;
						if ((type.equals("addons")) && addonMatchFound && (communityMatchFound && (! License.valid(db, myconfig, "community")))) {
							disabled = true;
						}
						if ((type.equals("addons")) && addonMatchFound && (databasesMatchFound && (! License.valid(db, myconfig, "databases")))) {
							disabled = true;
						}
						if ((type.equals("addons")) && addonMatchFound && (ecommerceMatchFound && (! License.valid(db, myconfig, "ecommerce")))) {
							disabled = true;
						}
						if ((type.equals("addons")) && addonMatchFound && (statisticsMatchFound && (! License.valid(db, myconfig, "statistics")))) {
							disabled = true;
						}
						if ((type.equals("addons")) && addonMatchFound && (personalMatchFound && (! License.valid(db, myconfig, "personal")))) {
							disabled = true;
						}
						if ((type.equals("addons")) && addonMatchFound && (professionalMatchFound && (! License.valid(db, myconfig, "professional")))) {
							disabled = true;
						}
						if ((type.equals("addons")) && addonMatchFound && (enterpriseMatchFound && (! License.valid(db, myconfig, "enterprise")))) {
							disabled = true;
						}
						if ((type.equals("addons")) && addonMatchFound && (hostingMatchFound && (! License.valid(db, myconfig, "hosting")))) {
							disabled = true;
						}
						if ((type.equals("websites")) && (communityMatchFound && (! License.valid(db, myconfig, "community")))) {
							disabled = true;
						}
						if ((type.equals("websites")) && (databasesMatchFound && (! License.valid(db, myconfig, "databases")))) {
							disabled = true;
						}
						if ((type.equals("websites")) && (ecommerceMatchFound && (! License.valid(db, myconfig, "ecommerce")))) {
							disabled = true;
						}
						if ((type.equals("websites")) && (statisticsMatchFound && (! License.valid(db, myconfig, "statistics")))) {
							disabled = true;
						}
						if ((type.equals("websites")) && (personalMatchFound && (! License.valid(db, myconfig, "personal")))) {
							disabled = true;
						}
						if ((type.equals("websites")) && (professionalMatchFound && (! License.valid(db, myconfig, "professional")))) {
							disabled = true;
						}
						if ((type.equals("websites")) && (enterpriseMatchFound && (! License.valid(db, myconfig, "enterprise")))) {
							disabled = true;
						}
						if ((type.equals("websites")) && (hostingMatchFound && (! License.valid(db, myconfig, "hosting")))) {
							disabled = true;
						}
						if ((type.equals("websites")) && (suiteMatchFound && ((! License.valid(db, myconfig, "community")) || (! License.valid(db, myconfig, "databases")) || (! License.valid(db, myconfig, "ecommerce"))))) {
							disabled = true;
						}

						number_of_datafiles += 1;
//						if (disabled) {
//							options_starts.put("" + number_of_datafiles, "" + options_starts.get("" + number_of_datafiles) + "<tr valign=\"top\" class=\"WCMimportfile_disabled\">" + "\r\n");
//						} else {
							options_starts.put("" + number_of_datafiles, "" + options_starts.get("" + number_of_datafiles) + "<tr valign=\"top\" class=\"WCMimportfile\">" + "\r\n");
//						}
//						options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<th valign=\"top\">");
//						if (disabled) {
//							options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<input disabled class=\"importfile\" type=\"radio\" name=\"importfile\" value=\"" + entry + "\">");
//						} else {
//							options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<input class=\"importfile\" type=\"radio\" name=\"importfile\" value=\"" + entry + "\" onclick=\"if (this.form.submitbutton) this.form.submitbutton.disabled = false;\">");
//						}
//						options_cells.put("" + number_of_datafiles, options_cells.get("" + number_of_datafiles) + "</th>" + "\r\n");
						if (disabled) {
							options_cells.put("" + number_of_datafiles, options_cells.get("" + number_of_datafiles) + "<td valign=\"top\" class=\"WCMimportfile_disabled\" style=\"width: " + Math.floor(100/number_of_columns) + "%\">");
						} else {
							options_cells.put("" + number_of_datafiles, options_cells.get("" + number_of_datafiles) + "<td valign=\"top\" class=\"WCMimportfile\" style=\"width: " + Math.floor(100/number_of_columns) + "%\">");
						}
//						options_cells.put("" + number_of_datafiles, options_cells.get("" + number_of_datafiles) + "<td valign=\"top\">");
						String display_name = "";
						if (type.equals("designs")) {
							display_name = publicMatches.group(2).replaceAll("_", " ").replaceAll("Website Template - ", "").replaceAll("Website Design - ", "");
						} else {
							display_name = publicMatches.group(2).replaceAll("_", " ");
						}
						options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<b>" + display_name + "</b>");
						if (type.equals("addons")) {
							if (disabled) {
								options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<br><input disabled class=\"importfileselect\" value=\"" + text.display("config.database.addons.select") + "\" onclick=\"doSubmit(this,'" + entry + "','" + type + "', '" + text.display("config.database.addons.submit") + "');\" type=\"button\">");
							} else {
								options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<br><input class=\"importfileselect\" value=\"" + text.display("config.database.addons.select") + "\" onclick=\"doSubmit(this,'" + entry + "','" + type + "', '" + text.display("config.database.addons.submit") + "');\" type=\"button\">");
							}
						} else if (type.equals("designs")) {
							if (disabled) {
								options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<br><input disabled class=\"importfileselect\" value=\"" + text.display("config.database.designs.select") + "\" onclick=\"doSubmit(this,'" + entry + "','" + type + "', '" + text.display("config.database.designs.submit") + "');\" type=\"button\">");
							} else {
								options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<br><input class=\"importfileselect\" value=\"" + text.display("config.database.designs.select") + "\" onclick=\"doSubmit(this,'" + entry + "','" + type + "', '" + text.display("config.database.designs.submit") + "');\" type=\"button\">");
							}
						} else {
							if (disabled) {
								options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<br><input disabled class=\"importfileselect\" value=\"" + text.display("config.database.import.select") + "\" onclick=\"doSubmit(this,'" + entry + "','" + type + "', '" + text.display("config.database.import.submit") + "');\" type=\"button\">");
							} else {
								options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<br><input class=\"importfileselect\" value=\"" + text.display("config.database.import.select") + "\" onclick=\"doSubmit(this,'" + entry + "','" + type + "', '" + text.display("config.database.import.submit") + "');\" type=\"button\">");
							}
						}
						if (! description.equals("")) {
							options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<div>" + description + "</div>");
						}
						options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "</td>" + "\r\n");
						options_ends.put("" + number_of_datafiles, "" + options_ends.get("" + number_of_datafiles) + "</tr>" + "\r\n");
					}

				} else if (privateMatches.find()) {
					if ((type.equals("")) || (type.equals("backups"))) {
						number_of_datafiles += 1;
						options_starts.put("" + number_of_datafiles, "" + options_starts.get("" + number_of_datafiles) + "<tr valign=\"top\" class=\"WCMimportfile\">" + "\r\n");
//						options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<th valign=\"top\">");
//						options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<input class=\"importfile\" type=\"radio\" name=\"importfile\" value=\"" + entry + "\" onclick=\"if (this.form.submitbutton) this.form.submitbutton.disabled = false;\">");
//						options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "</th>" + "\r\n");
						options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<td valign=\"top\" style=\"width: " + Math.floor(100/number_of_columns) + "%\">");
//						options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<td valign=\"top\">");
//						options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + privateMatches.group(2).replaceAll("_", " "));
						String display_name = "" + privateMatches.group(2).replaceAll("_", " ") + "-" + privateMatches.group(3).replaceAll("_", " ") + "-" + privateMatches.group(4).replaceAll("_", " ") + " " + privateMatches.group(5).replaceAll("_", " ") + ":" + privateMatches.group(6).replaceAll("_", " ") + ":" + privateMatches.group(7).replaceAll("_", " ");
						options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<b>" + display_name + "</b>");
						options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<br><input class=\"importfileselect\" value=\"" + text.display("config.database.restore.select") + "\" onclick=\"doSubmit(this,'" + entry + "','" + type + "', '" + text.display("config.database.restore.submit") + "');\" type=\"button\">");
						options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + " <input class=\"importfileselect\" value=\"" + text.display("config.database.restore.download") + "\" onclick=\"doDownload(this,'" + entry + "','" + type + "');\" type=\"button\">");
						options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + " <input class=\"importfileselect\" value=\"" + text.display("config.database.restore.delete") + "\" onclick=\"doDelete(this,'" + entry + "','" + type + "');\" type=\"button\">");
//						options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + " <a target=\"download_window\" href=\"/" + text.display("adminpath") + "/database/backup_download.jsp?backup=" + entry + "\">" + text.display("config.database.restore.download") + "</a>");
						if (! description.equals("")) {
							options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "<div>" + description + "</div>");
						}
						options_cells.put("" + number_of_datafiles, "" + options_cells.get("" + number_of_datafiles) + "</td>" + "\r\n");
						options_ends.put("" + number_of_datafiles, "" + options_ends.get("" + number_of_datafiles) + "</tr>" + "\r\n");
					}
				}
			}
		}

		options = "<table cellspacing=\"20\" valign=\"top\" width=\"100%\" class=\"WCMimportfile\">";
		String options_start = "";
		String options_cell = "";
		String options_end = "";
		int column = 0;
		for (int i=1; i<=number_of_datafiles; i++) {
			column += 1;
			if (column == 1) {
				options_start += "" + options_starts.get("" + i);
			}
			options_cell += "" + options_cells.get("" + i);
			if (column == number_of_columns) {
				options_end += "" + options_ends.get("" + i);
				options += options_start + options_cell + options_end;
				column = 0;
				options_start = "";
				options_cell = "";
				options_end = "";
			}
		}
		if (column > 0) {
			while (column < number_of_columns) {
				column += 1;
				options_cell += "<th valign=\"top\">";
				options_cell += "</th>" + "\r\n";
				options_cell += "<td valign=\"top\" style=\"width: " + Math.floor(100/number_of_columns) + "%\">";
				options_cell += "</td>" + "\r\n";
			}
			options_end += "" + options_ends.get("" + number_of_datafiles);
			options += options_start + options_cell + options_end;
		}
		options += "</tr></table>";
		return options;
	}



	/* adjust_links */

	public String adjust_links(String content, String old_id, String new_id) {
		String oldcontent = "";
		while (! content.equals(oldcontent)) {
			oldcontent = "" + content;

			content = content.replaceAll("(?is)/(index|page|image|file|link|xml|rss|atom|manifest|product|shopcart|register|admin|post|create|update|delete|login|trackback|stylesheet)\\.(aspx?|jsp|php)\\?(id|add)=" + old_id + "([^0-9])", "/$1.jsp?$3=" + new_id + "$4");
			content = content.replaceAll("(?is)/(index|page|image|file|link|xml|rss|atom|manifest|product|shopcart|register|admin|post|create|update|delete|login|trackback|stylesheet)\\.(aspx?|jsp|php)/(id|add)=" + old_id + "\\?([^0-9])", "/$1.jsp?$3=" + new_id + "&$4");
			content = content.replaceAll("(?is)/(index|page|image|file|link|xml|rss|atom|manifest|product|shopcart|register|admin|post|create|update|delete|login|trackback|stylesheet)\\.(aspx?|jsp|php)/(id|add)=" + old_id + "([^0-9])", "/$1.jsp?$3=" + new_id + "$4");
			content = content.replaceAll("(?is)/(index|page|image|file|link|xml|rss|atom|manifest|product|shopcart|register|admin|data|create|update|delete|login|trackback|stylesheet|post|login_post|logout|contact|search|guestbook|subscribe|unsubscribe)\\.(aspx?|jsp|php)", "/$1.jsp");
			content = content.replaceAll("(?is)%2F(index|page|image|file|link|xml|rss|atom|manifest|product|shopcart|register|admin|post|create|update|delete|login|trackback|stylesheet)\\.(aspx?|jsp|php)\\?(id|add)=" + old_id + "([^0-9])", "%2F$1.jsp?$3=" + new_id + "$4");
			content = content.replaceAll("(?is)%2F(index|page|image|file|link|xml|rss|atom|manifest|product|shopcart|register|admin|post|create|update|delete|login|trackback|stylesheet)\\.(aspx?|jsp|php)%2F(id|add)=" + old_id + "\\?([^0-9])", "%2F$1.jsp?$3=" + new_id + "&$4");
			content = content.replaceAll("(?is)%2F(index|page|image|file|link|xml|rss|atom|manifest|product|shopcart|register|admin|post|create|update|delete|login|trackback|stylesheet)\\.(aspx?|jsp|php)%2F(id|add)=" + old_id + "([^0-9])", "%2F$1.jsp?$3=" + new_id + "$4");
			content = content.replaceAll("(?is)%2F(index|page|image|file|link|xml|rss|atom|manifest|product|shopcart|register|admin|data|create|update|delete|login|trackback|stylesheet|post|login_post|logout|contact|search|guestbook|subscribe|unsubscribe)\\.(aspx?|jsp|php)", "%2F$1.jsp");

			content = content.replaceAll("(?is)<input([^<]+)name=(add|item|id|content_id|stylesheet|template|email_template|email_confirmation|email_notification|page_top|page_up|searchresult)([^<]+)value=" + old_id + "([^0-9])", "<input$1name=$2$3value=" + new_id + "$4");
			content = content.replaceAll("(?is)<input([^<]+)name=(add|item|id|content_id|stylesheet|template|email_template|email_confirmation|email_notification|page_top|page_up|searchresult)([^<]+)value=\"" + old_id + "\"", "<input$1name=$2$3value=\"" + new_id + "\"");
			content = content.replaceAll("(?is)<input([^<]+)name=\"(add|item|id|content_id|stylesheet|template|email_template|email_confirmation|email_notification|page_top|page_up|searchresult)\"([^<]+)value=" + old_id + "([^0-9])", "<input$1name=\"$2\"$3value=" + new_id + "$4");
			content = content.replaceAll("(?is)<input([^<]+)name=\"(add|item|id|content_id|stylesheet|template|email_template|email_confirmation|email_notification|page_top|page_up|searchresult)\"([^<]+)value=\"" + old_id + "\"", "<input$1name=\"$2\"$3value=\"" + new_id + "\"");

			content = content.replaceAll("(?is)<input([^<]+)value=" + old_id + "([^<0-9])([^<]*)name=(add|item|id|content_id|stylesheet|template|email_template|email_confirmation|email_notification|page_top|page_up|searchresult)", "<input$1value=" + new_id + "$2$3name=$4");
			content = content.replaceAll("(?is)<input([^<]+)value=\"" + old_id + "\"([^<]*)name=(add|item|id|content_id|stylesheet|template|email_template|email_confirmation|email_notification|page_top|page_up|searchresult)", "<input$1value=\"" + new_id + "\"$2name=$3");
			content = content.replaceAll("(?is)<input([^<]+)value=" + old_id + "([^<0-9])([^<]*)name=\"(add|item|id|content_id|stylesheet|template|email_template|email_confirmation|email_notification|page_top|page_up|searchresult)\"", "<input$1value=" + new_id + "$2$3name=\"$4\"");
			content = content.replaceAll("(?is)<input([^<]+)value=\"" + old_id + "\"([^<]*)name=\"(add|item|id|content_id|stylesheet|template|email_template|email_confirmation|email_notification|page_top|page_up|searchresult)\"", "<input$1value=\"" + new_id + "\"$2name=\"$3\"");

			content = content.replaceAll("(?s)@@@include:" + old_id + "\\.", "@@@include:" + new_id + ".");
			content = content.replaceAll("(?s)@@@include:([^@]*):(orderitem)=" + old_id + "(:|@@@)", "@@@include:$1:$2=" + new_id + "$3");

			content = content.replaceAll("(?s)@@@webeditor([^@]*):(value)=" + old_id + "(:|@@@)", "@@@webeditor$1:$2=" + new_id + "$3");

			content = content.replaceAll("(?s)@@@list([^@]*):(entry)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(header)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(footer)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(none)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*?):(none)=" + old_id + "(@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(id)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(id)!=" + old_id + "(:|@@@)", "@@@list$1:$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(id) in (.*[(,])" + old_id + "([,)])", "@@@list$1:$2 in $3" + new_id + "$4");
			content = content.replaceAll("(?s)@@@list([^@]*):(top)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(top)!=" + old_id + "(:|@@@)", "@@@list$1:$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(top) in (.*[(,])" + old_id + "([,)])", "@@@list$1:$2 in $3" + new_id + "$4");
			content = content.replaceAll("(?s)@@@list([^@]*):(up)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(up)!=" + old_id + "(:|@@@)", "@@@list$1:$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(up) in (.*[(,])" + old_id + "([,)])", "@@@list$1:$2 in $3" + new_id + "$4");
			content = content.replaceAll("(?s)@@@list([^@]*):(previous)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(previous)!=" + old_id + "(:|@@@)", "@@@list$1:$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(previous) in (.*[(,])" + old_id + "([,)])", "@@@list$1:$2 in $3" + new_id + "$4");
			content = content.replaceAll("(?s)@@@list([^@]*):(next)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(next)!=" + old_id + "(:|@@@)", "@@@list$1:$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(next) in (.*[(,])" + old_id + "([,)])", "@@@list$1:$2 in $3" + new_id + "$4");
			content = content.replaceAll("(?s)@@@list([^@]*):(first)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(first)!=" + old_id + "(:|@@@)", "@@@list$1:$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(first) in (.*[(,])" + old_id + "([,)])", "@@@list$1:$2 in $3" + new_id + "$4");
			content = content.replaceAll("(?s)@@@list([^@]*):(last)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(last)!=" + old_id + "(:|@@@)", "@@@list$1:$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list([^@]*):(last) in (.*[(,])" + old_id + "([,)])", "@@@list$1:$2 in $3" + new_id + "$4");
			content = content.replaceAll("(?s)@@@list([^@]*):(orderitem)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");

			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(entry)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(header)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(footer)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(none)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(id)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(id)!=" + old_id + "(:|@@@)", "@@@list$1:$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(id) in (.*[(,])" + old_id + "([,)])", "@@@list$1:$2 in $3" + new_id + "$4");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(top)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(top)!=" + old_id + "(:|@@@)", "@@@list$1:$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(top) in (.*[(,])" + old_id + "([,)])", "@@@list$1:$2 in $3" + new_id + "$4");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(up)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(up)!=" + old_id + "(:|@@@)", "@@@list$1:$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(up) in (.*[(,])" + old_id + "([,)])", "@@@list$1:$2 in $3" + new_id + "$4");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(previous)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(previous)!=" + old_id + "(:|@@@)", "@@@list$1:$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(previous) in (.*[(,])" + old_id + "([,)])", "@@@list$1:$2 in $3" + new_id + "$4");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(next)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(next)!=" + old_id + "(:|@@@)", "@@@list$1:$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(next) in (.*[(,])" + old_id + "([,)])", "@@@list$1:$2 in $3" + new_id + "$4");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(first)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(first)!=" + old_id + "(:|@@@)", "@@@list$1:$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(first) in (.*[(,])" + old_id + "([,)])", "@@@list$1:$2 in $3" + new_id + "$4");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(last)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(last)!=" + old_id + "(:|@@@)", "@@@list$1:$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(last) in (.*[(,])" + old_id + "([,)])", "@@@list$1:$2 in $3" + new_id + "$4");
			content = content.replaceAll("(?s)@@@list(.*@@@[-_a-zA-Z0-9]+@@@.*):(orderitem)=" + old_id + "(:|@@@)", "@@@list$1:$2=" + new_id + "$3");

			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(entry)=" + old_id + "([:)&])", "@@@extension:$1$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(header)=" + old_id + "([:)&])", "@@@extension:$1$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(footer)=" + old_id + "([:)&])", "@@@extension:$1$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(none)=" + old_id + "([:)&])", "@@@extension:$1$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(id)=" + old_id + "([:)&])", "@@@extension:$1$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(id)!=" + old_id + "([:)&])", "@@@extension:$1$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(top)=" + old_id + "([:)&])", "@@@extension:$1$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(top)!=" + old_id + "([:)&])", "@@@extension:$1$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(up)=" + old_id + "([:)&])", "@@@extension:$1$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(up)!=" + old_id + "([:)&])", "@@@extension:$1$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(previous)=" + old_id + "([:)&])", "@@@extension:$1$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(previous)!=" + old_id + "([:)&])", "@@@extension:$1$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(next)=" + old_id + "([:)&])", "@@@extension:$1$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(next)!=" + old_id + "([:)&])", "@@@extension:$1$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(first)=" + old_id + "([:)&])", "@@@extension:$1$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(first)!=" + old_id + "([:)&])", "@@@extension:$1$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(last)=" + old_id + "([:)&])", "@@@extension:$1$2=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(last)!=" + old_id + "([:)&])", "@@@extension:$1$2!=" + new_id + "$3");
			content = content.replaceAll("(?s)@@@extension:([^@]+[(:&?])(orderitem)=" + old_id + "([:)&])", "@@@extension:$1$2=" + new_id + "$3");

			while (Pattern.compile("(?s)@@@extension:referral([(][^@]+)=" + old_id + "([.:)])").matcher(content).find()) {
				content = content.replaceAll("(?s)@@@extension:referral([(][^@]+)=" + old_id + "([.:)])", "@@@extension:referral$1=" + new_id + "$2");
			}

			content = content.replaceAll("([?&:;])(stylesheet)=" + old_id + "([^0-9])", "$1$2=" + new_id + "$3");
			content = content.replaceAll("([?&:;])(template)=" + old_id + "([^0-9])", "$1$2=" + new_id + "$3");
			content = content.replaceAll("([?&:;])(top)=" + old_id + "([^0-9])", "$1$2=" + new_id + "$3");
			content = content.replaceAll("([?&:;])(up)=" + old_id + "([^0-9])", "$1$2=" + new_id + "$3");
		}

		return content;
	}



	/* adjust_product_options */

	public String adjust_product_options(String product_options, String old_id, String new_id) {
		product_options = product_options.replaceAll("<hosting:publish_email>" + old_id + "</hosting:publish_email>", "<hosting:publish_email>" + new_id + "</hosting:publish_email>");
		product_options = product_options.replaceAll("<hosting:notify_email>" + old_id + "</hosting:notify_email>", "<hosting:notify_email>" + new_id + "</hosting:notify_email>");
		product_options = product_options.replaceAll("<hosting:unpublish_email>" + old_id + "</hosting:unpublish_email>", "<hosting:unpublish_email>" + new_id + "</hosting:unpublish_email>");
		return product_options;
	}



	/* adjust_ids */

	public String adjust_ids(String old_idlist, HashMap ids) {
		String new_idlist = "";
		if (! old_idlist.equals("")) {
			String[] old_ids = old_idlist.split(",");
			for (int i=0; i<old_ids.length; i++) {
				String old_id = old_ids[i];
				if (! new_idlist.equals("")) {
					new_idlist += ",";
				}
				if (old_id.equals("")) {
					new_idlist += " ";
				} else if (old_id.equals("0")) {
					new_idlist += "0";
				} else if (ids != null) {
					new_idlist += "" + ids.get(old_id);
				}
			}
		}
		new_idlist = new_idlist.replaceAll(" ", "");
		return new_idlist;
	}



	/* import_website_options */

	public String import_website_options(JspWriter out, Text mytext, Configuration config, String DOCUMENT_ROOT, String rootpath) {
		return import_website_options(out, mytext, config, DOCUMENT_ROOT, rootpath, "");
	}
	public String import_website_options(JspWriter out, Text mytext, Configuration config, String DOCUMENT_ROOT, String rootpath, String idprefix) {
		ArrayList<String> htmlfiles = import_website_listfolder(DOCUMENT_ROOT + rootpath, "^/(App_Code|App_Data|App_Themes|bizcard|file.original|hardcore|image.original|password|personal|SITEBIZCARD|SITEDUMMY|SITEPERS|SITEPERSCOM|SITEPERSDATA|SITEPERSECOM|SITEPERSSUITE|SITEPROF|SITEPROFCOM|SITEPROFDATA|SITEPROFECOM|SITEPROFSUITE|upload|upload.original|webadmin|WEB-INF|Templates/admin.asp.dwt|Templates/admin.aspx.dwt|Templates/admin.jsp.dwt|Templates/admin.php.dwt||page.original.html|product.original.html|script.original.js|stylesheet.original.css|unavailable.html" + config.get(this, "import_website_exclude") + ")/?$", "(dwt|html|htm)$");
		HashMap<String,String> elementclasses = import_website_fileparts(DOCUMENT_ROOT + rootpath, htmlfiles, config.get(this, "charset"));
		ArrayList<String> titleelement = import_website_titleelements(elementclasses);
		ArrayList<String> contentelement = import_website_contentelements(elementclasses);
		String title_options = mytext.display("config.database.media.insertwebsite.title") + " <select disabled id=\"" + idprefix + "titleelement\" name=\"titleelement\"><option value=\"\">" +  mytext.display("none") + "</option>";
		String content_options = mytext.display("config.database.media.insertwebsite.content") + " <select disabled id=\"" + idprefix + "contentelement\" name=\"contentelement\"><option value=\"\">" + mytext.display("all") + "</option>";
		Iterator i = Common.SortedHashMapKeySetIterator(elementclasses);
		while (i.hasNext()) {
			String elementclass = "" + i.next();
			if (! Pattern.compile("^_(ALL|HTML|TEMPLATE)_.*_$").matcher(elementclass).find()) {
				if (elementclass.equals(titleelement.get(0))) {
					title_options += "<option value=\"" + elementclass + "\" selected>" + elementclass + "</option>";
				} else {
					title_options += "<option value=\"" + elementclass + "\">" + elementclass + "</option>";
				}
				if (elementclass.equals(contentelement.get(0))) {
					content_options += "<option value=\"" + elementclass + "\" selected>" + elementclass + "</option>";
				} else {
					content_options += "<option value=\"" + elementclass + "\">" + elementclass + "</option>";
				}
			}
		}
		title_options += "</select>";
		content_options += "</select>";
		return title_options + "<br>" + "\r\n" + content_options + "<br>" + "\r\n";
	}



	/* import_website */

	public void import_website(JspWriter out, Text mytext, Session mysession, String equals, Configuration config, String DOCUMENT_ROOT, String rootpath, String titleelement, String contentelement) {
		try {
			out.print(mytext.display("config.database.media.insertwebsite.files") + "\r\n");
			out.flush();
		} catch (IOException e) {
		}
		String WCM_doctype = "";
		String WCM_charset = "";
		HashMap<String,String> WCM_files = new HashMap<String,String>();
		HashMap<String,String> WCM_filegroups = new HashMap<String,String>();
		ArrayList<String> filefiles = import_website_listfolder(DOCUMENT_ROOT + rootpath, "^/(App_Code|App_Data|App_Themes|bizcard|file.original|hardcore|image.original|password|personal|SITEBIZCARD|SITEDUMMY|SITEPERS|SITEPERSCOM|SITEPERSDATA|SITEPERSECOM|SITEPERSSUITE|SITEPROF|SITEPROFCOM|SITEPROFDATA|SITEPROFECOM|SITEPROFSUITE|upload|upload.original|webadmin|WEB-INF|Templates/admin.asp.dwt|Templates/admin.aspx.dwt|Templates/admin.jsp.dwt|Templates/admin.php.dwt||page.original.html|product.original.html|script.original.js|stylesheet.original.css|unavailable.html" + config.get(this, "import_website_exclude") + ")/?$", "(doc|exe|pdf|ppt|rtf|txt|xls|zip)$");
		for (int i=0; i<filefiles.size(); i++) {
			String filefile = "" + filefiles.get(i);
			String contentclass = "file";
			String contentgroup = import_website_folderpath(filefile);
			contentgroup = contentgroup.replaceAll("^" + rootpath, "");
			contentgroup = contentgroup.replaceAll("/$", "");
			String contenttype = "";
			String server_filename = filefile.replaceAll("^" + rootpath, "");

			String title = filefile.replaceAll("^" + rootpath, "");

			String content = "";

			String htmlheader = "";

			String htmlbodyonload = "";

			String template = "";
			String stylesheet = "";

			WCM_files.put(filefile, import_website_create_content(config, mysession, equals, contentclass, contentgroup, contenttype, title, content, htmlheader, htmlbodyonload, server_filename, template, stylesheet));

			if ((! contentgroup.equals("")) && (WCM_filegroups.get(contentgroup) == null)) {
				Filegroup new_filegroup = new Filegroup();
				new_filegroup.setFilegroup(contentgroup);
				new_filegroup.create(this);
				WCM_filegroups.put(contentgroup, contentgroup);
			}

			try {
				out.print(WCM_files.get(filefile) + " - " + title + "<br>" + "\r\n");
				out.flush();
			} catch (IOException e) {
			}
		}



		try {
			out.print(mytext.display("config.database.media.insertwebsite.images") + "\r\n");
			out.flush();
		} catch (IOException e) {
		}
		HashMap<String,String> WCM_images = new HashMap<String,String>();
		HashMap<String,String> WCM_imagegroups = new HashMap<String,String>();
		ArrayList<String> imagefiles = import_website_listfolder(DOCUMENT_ROOT + rootpath, "^/(App_Code|App_Data|App_Themes|bizcard|file.original|hardcore|image.original|password|personal|SITEBIZCARD|SITEDUMMY|SITEPERS|SITEPERSCOM|SITEPERSDATA|SITEPERSECOM|SITEPERSSUITE|SITEPROF|SITEPROFCOM|SITEPROFDATA|SITEPROFECOM|SITEPROFSUITE|upload|upload.original|webadmin|WEB-INF|Templates/admin.asp.dwt|Templates/admin.aspx.dwt|Templates/admin.jsp.dwt|Templates/admin.php.dwt||page.original.html|product.original.html|script.original.js|stylesheet.original.css|unavailable.html" + config.get(this, "import_website_exclude") + ")/?$", "(class|gif|ico|jpeg|jpg|png|swf)$");
		for (int i=0; i<imagefiles.size(); i++) {
			String imagefile = "" + imagefiles.get(i);
			String contentclass = "image";
			String contentgroup = import_website_folderpath(imagefile);
			contentgroup = contentgroup.replaceAll("^" + rootpath, "");
			contentgroup = contentgroup.replaceAll("/$", "");
			String contenttype = "";
			String server_filename = imagefile.replaceAll("^" + rootpath, "");

			String title = imagefile.replaceAll("^" + rootpath, "");

			String content = "";

			String htmlheader = "";

			String htmlbodyonload = "";

			String template = "";
			String stylesheet = "";

			WCM_images.put(imagefile, import_website_create_content(config, mysession, equals, contentclass, contentgroup, contenttype, title, content, htmlheader, htmlbodyonload, server_filename, template, stylesheet));

			if ((! contentgroup.equals("")) && (WCM_imagegroups.get(contentgroup) == null)) {
				Imagegroup new_imagegroup = new Imagegroup();
				new_imagegroup.setImagegroup(contentgroup);
				new_imagegroup.create(this);
				WCM_imagegroups.put(contentgroup, contentgroup);
			}

			try {
				out.print(WCM_images.get(imagefile) + " - " + title + "<br>" + "\r\n");
				out.flush();
			} catch (IOException e) {
			}
		}



		try {
			out.print(mytext.display("config.database.media.insertwebsite.scripts") + "\r\n");
			out.flush();
		} catch (IOException e) {
		}
		HashMap<String,String> WCM_scripts = new HashMap<String,String>();
		ArrayList<String> scriptfiles = import_website_listfolder(DOCUMENT_ROOT + rootpath, "^/(App_Code|App_Data|App_Themes|bizcard|file.original|hardcore|image.original|password|personal|SITEBIZCARD|SITEDUMMY|SITEPERS|SITEPERSCOM|SITEPERSDATA|SITEPERSECOM|SITEPERSSUITE|SITEPROF|SITEPROFCOM|SITEPROFDATA|SITEPROFECOM|SITEPROFSUITE|upload|upload.original|webadmin|WEB-INF|Templates/admin.asp.dwt|Templates/admin.aspx.dwt|Templates/admin.jsp.dwt|Templates/admin.php.dwt||page.original.html|product.original.html|script.original.js|stylesheet.original.css|unavailable.html" + config.get(this, "import_website_exclude") + ")/?$", "(js)$");
		for (int i=0; i<scriptfiles.size(); i++) {
			String scriptfile = "" + scriptfiles.get(i);
			String contentclass = "script";
			String contentgroup = "";
			String contenttype = "";
			String server_filename = scriptfile.replaceAll("^" + rootpath, "");

			String title = scriptfile.replaceAll("^" + rootpath, "");

			String content = Common.readFile(DOCUMENT_ROOT + rootpath + scriptfile);

			String htmlheader = "";

			String htmlbodyonload = "";

			String template = "";
			String stylesheet = "";

			WCM_scripts.put(scriptfile, import_website_create_content(config, mysession, equals, contentclass, contentgroup, contenttype, title, content, htmlheader, htmlbodyonload, server_filename, template, stylesheet));

			try {
				out.print(WCM_scripts.get(scriptfile) + " - " + title + "<br>" + "\r\n");
				out.flush();
			} catch (IOException e) {
			}
		}



		try {
			out.print(mytext.display("config.database.media.insertwebsite.stylesheets") + "\r\n");
			out.flush();
		} catch (IOException e) {
		}
		HashMap<String,String> WCM_stylesheets = new HashMap<String,String>();
		ArrayList<String> stylesheetfiles = import_website_listfolder(DOCUMENT_ROOT + rootpath, "^/(App_Code|App_Data|App_Themes|bizcard|file.original|hardcore|image.original|password|personal|SITEBIZCARD|SITEDUMMY|SITEPERS|SITEPERSCOM|SITEPERSDATA|SITEPERSECOM|SITEPERSSUITE|SITEPROF|SITEPROFCOM|SITEPROFDATA|SITEPROFECOM|SITEPROFSUITE|upload|upload.original|webadmin|WEB-INF|Templates/admin.asp.dwt|Templates/admin.aspx.dwt|Templates/admin.jsp.dwt|Templates/admin.php.dwt||page.original.html|product.original.html|script.original.js|stylesheet.original.css|unavailable.html" + config.get(this, "import_website_exclude") + ")/?$", "(css)$");
		for (int i=0; i<stylesheetfiles.size(); i++) {
			String stylesheetfile = "" + stylesheetfiles.get(i);
			String contentclass = "stylesheet";
			String contentgroup = "";
			String contenttype = "";
			String server_filename = stylesheetfile.replaceAll("^" + rootpath, "");

			String title = stylesheetfile.replaceAll("^" + rootpath, "");

			String content = Common.readFile(DOCUMENT_ROOT + rootpath + stylesheetfile);

			String htmlheader = "";

			String htmlbodyonload = "";

			String template = "";
			String stylesheet = "";

			WCM_stylesheets.put(stylesheetfile, import_website_create_content(config, mysession, equals, contentclass, contentgroup, contenttype, title, content, htmlheader, htmlbodyonload, server_filename, template, stylesheet));

			try {
				out.print(WCM_stylesheets.get(stylesheetfile) + " - " + title + "<br>" + "\r\n");
				out.flush();
			} catch (IOException e) {
			}
		}



		try {
			out.print(mytext.display("config.database.media.insertwebsite.templates") + "\r\n");
			out.flush();
		} catch (IOException e) {
		}
		HashMap<String,String> WCM_templates = new HashMap<String,String>();
		ArrayList<String> templatefiles = import_website_listfolder(DOCUMENT_ROOT + rootpath, "^/(App_Code|App_Data|App_Themes|bizcard|file.original|hardcore|image.original|password|personal|SITEBIZCARD|SITEDUMMY|SITEPERS|SITEPERSCOM|SITEPERSDATA|SITEPERSECOM|SITEPERSSUITE|SITEPROF|SITEPROFCOM|SITEPROFDATA|SITEPROFECOM|SITEPROFSUITE|upload|upload.original|webadmin|WEB-INF|Templates/admin.asp.dwt|Templates/admin.aspx.dwt|Templates/admin.jsp.dwt|Templates/admin.php.dwt||page.original.html|product.original.html|script.original.js|stylesheet.original.css|unavailable.html" + config.get(this, "import_website_exclude") + ")/?$", "(dwt)$");
		for (int i=0; i<templatefiles.size(); i++) {
			String templatefile = "" + templatefiles.get(i);
			HashMap<String,String> elements = import_website_fileparts(DOCUMENT_ROOT + rootpath, templatefile, WCM_charset);
			ArrayList<String> titleelements = import_website_titleelements(elements);
			ArrayList<String> contentelements = import_website_contentelements(elements);

			String contentclass = "template";
			String contentgroup = "";
			String contenttype = "";
			String server_filename = "";

			String title = templatefile.replaceAll("^" + rootpath, "");
			if (elements.get(titleelement) != null) title += " - " + elements.get(titleelement);

			String content = "";
			if (elements.get("_TEMPLATE_HTML_CONTENT_") != null) {
				content = "" + elements.get("_TEMPLATE_HTML_CONTENT_");
			}
			if (! titleelement.equals("")) content = content.replaceAll("@@@" + titleelement + ".content@@@", "@@@title@@@");
			if (! contentelement.equals("")) content = content.replaceAll("@@@" + contentelement + ".content@@@", "@@@content@@@");
			if ((elements.get(contentelement) == null) && (contentelements.size() > 0)) content = content.replaceAll("@@@" + contentelements.get(0) + ".content@@@", "@@@content@@@");

			String htmlheader = "";
			if (elements.get("_TEMPLATE_HTML_HEAD_") != null) {
				htmlheader = "" + elements.get("_TEMPLATE_HTML_HEAD_");
			}
			htmlheader = htmlheader.replaceAll("@@@doctitle.content@@@", "");
			htmlheader = htmlheader.replaceAll("@@@[^@]*.content@@@", "");
			htmlheader = htmlheader.replaceAll("(?i)<title>.*?</title>", "");
			htmlheader = htmlheader.replaceAll("(?i)<meta +http-equiv=\"Content-Type\" +content=\".*?\">", "");

			String htmlbodyonload = "";
			if (elements.get("_TEMPLATE_HTML_BODY_") != null) {
				htmlbodyonload = "" + elements.get("_TEMPLATE_HTML_BODY_");
			}

			String template = "";
			String stylesheet = "";
			WCM_templates.put(templatefile, import_website_create_content(config, mysession, equals, contentclass, contentgroup, contenttype, title, content, htmlheader, htmlbodyonload, server_filename, template, stylesheet));

			if ((WCM_doctype.equals("")) && (elements.get("_HTML_DOCTYPE_") != null)) {
				WCM_doctype = "" + elements.get("_HTML_DOCTYPE_");
			}
			if ((WCM_charset.equals("")) && (elements.get("_HTML_CHARSET_") != null)) {
				WCM_charset = "" + elements.get("_HTML_CHARSET_");
			}

			try {
				out.print(WCM_templates.get(templatefile) + " - " + title + "<br>" + "\r\n");
				out.flush();
			} catch (IOException e) {
			}
		}



		try {
			out.print(mytext.display("config.database.media.insertwebsite.pages") + "\r\n");
			out.flush();
		} catch (IOException e) {
		}
		HashMap<String,String> WCM_pages = new HashMap<String,String>();
		HashMap<String,String> WCM_pagegroups = new HashMap<String,String>();
		HashMap<String,String> WCM_classes = new HashMap<String,String>();
		HashMap<String,HashMap<String,String>> WCM_elements = new HashMap<String,HashMap<String,String>>();
		ArrayList<String> htmlfiles = import_website_listfolder(DOCUMENT_ROOT + rootpath, "^/(App_Code|App_Data|App_Themes|bizcard|file.original|hardcore|image.original|password|personal|SITEBIZCARD|SITEDUMMY|SITEPERS|SITEPERSCOM|SITEPERSDATA|SITEPERSECOM|SITEPERSSUITE|SITEPROF|SITEPROFCOM|SITEPROFDATA|SITEPROFECOM|SITEPROFSUITE|upload|upload.original|webadmin|WEB-INF|Templates/admin.asp.dwt|Templates/admin.aspx.dwt|Templates/admin.jsp.dwt|Templates/admin.php.dwt||page.original.html|product.original.html|script.original.js|stylesheet.original.css|unavailable.html" + config.get(this, "import_website_exclude") + ")/?$", "(html|htm)$");
		for (int i=0; i<htmlfiles.size(); i++) {
			String htmlfile = "" + htmlfiles.get(i);
			HashMap<String,String> elements = import_website_fileparts(DOCUMENT_ROOT + rootpath, htmlfile, WCM_charset);
			ArrayList<String> titleelements = import_website_titleelements(elements);
			ArrayList<String> contentelements = import_website_contentelements(elements);

			String contentclass = "";
			String contentgroup = "";
			String contenttype = "";
			String server_filename = "";
			String title = "";
			String content = "";
			String htmlheader = "";
			String htmlbodyonload = "";
			String template = "";
			String stylesheet = "";
			String templatefile = "";

			if (WCM_elements.get(htmlfile) == null) WCM_elements.put(htmlfile, new HashMap<String,String>());
			for (int j=0; j<contentelements.size(); j++) {
				String element = "" + contentelements.get(j);
				if ((! element.equals(titleelement)) && (! element.equals(contentelement)) && (! element.equals("_HTML_CONTENT_"))) {
					contentclass = element;
					contentgroup = "";
					contenttype = "";
					title = htmlfile.replaceAll("^" + rootpath, "");
					if (elements.get(titleelement) != null) title += " - " + elements.get(titleelement);
					title += " - " + element;
					content = "" + elements.get(element);
					htmlheader = "";
					if (elements.get("_HTML_BODY_") != null) {
						htmlbodyonload = "" + elements.get("_HTML_BODY_");
					}
					server_filename = "";
					template = "";
					stylesheet = "";
					WCM_classes.put(element, element);
					if (WCM_elements.get(htmlfile) == null) WCM_elements.put(htmlfile, new HashMap<String,String>());
					((HashMap<String,String>)WCM_elements.get(htmlfile)).put(element, import_website_create_content(config, mysession, equals, contentclass, contentgroup, contenttype, title, content, htmlheader, htmlbodyonload, server_filename, template, stylesheet));
				}
			}

			contentclass = "page";
			contentgroup = import_website_folderpath(htmlfile);
			contentgroup = contentgroup.replaceAll("^" + rootpath, "");
			contentgroup = contentgroup.replaceAll("/$", "");
			contenttype = "";
			server_filename = htmlfile.replaceAll("^" + rootpath, "");

			title = htmlfile.replaceAll("^" + rootpath, "");
			if (elements.get(titleelement) != null) title += " - " + elements.get(titleelement);

			content = "";
			// use full page as default
			if (elements.get("_HTML_CONTENT_") != null) {
				content = "" + elements.get("_HTML_CONTENT_");
			} else if (elements.get("_ALL_CONTENT_") != null) {
				content = "" + elements.get("_ALL_CONTENT_");
			}
			if (contentelement == null) {
				// ignore
			} else if (contentelement.equals("")) {
				// ignore
			} else if ((elements.size() > 0) && (elements.get(contentelement) != null)) {
				// use selected content element
				content = "" + elements.get(contentelement);
			} else if ((contentelements.size() > 0) && (contentelements.get(0) != null)) {
				//  use first alternative content element as selected content element does not exist
				content = "" + elements.get(contentelements.get(0));
			}

			htmlheader = "";
			if (elements.get("_TEMPLATE_HTML_HEAD_") != null) {
				htmlheader = "" + elements.get("_TEMPLATE_HTML_HEAD_");
			}
			htmlheader = htmlheader.replaceAll("@@@doctitle.content@@@", "");
			htmlheader = htmlheader.replaceAll("@@@[^@]*.content@@@", "");
			htmlheader = htmlheader.replaceAll("(?i)<title>.*?</title>", "");
			htmlheader = htmlheader.replaceAll("(?i)<meta +http-equiv=\"Content-Type\" +content=\".*?\">", "");

			htmlbodyonload = "";
			if (elements.get("_TEMPLATE_HTML_BODY_") != null) {
				htmlbodyonload = "" + elements.get("_TEMPLATE_HTML_BODY_");
			}

			template = "";
			stylesheet = "";
			templatefile = "";
			if (elements.get("_TEMPLATE_FILE_") != null) {
				templatefile = "" + elements.get("_TEMPLATE_FILE_");
			}
			if (! templatefile.equals("")) template = "" + WCM_templates.get(templatefile);

			WCM_pages.put(htmlfile, import_website_create_content(config, mysession, equals, contentclass, contentgroup, contenttype, title, content, htmlheader, htmlbodyonload, server_filename, template, stylesheet, (HashMap<String,String>)WCM_elements.get(htmlfile)));

			if ((! contentgroup.equals("")) && (WCM_pagegroups.get(contentgroup) == null)) {
				Contentgroup new_contentgroup = new Contentgroup();
				new_contentgroup.readContentgroup(this, contentgroup);
				if (new_contentgroup.getId().equals("")) {
					new_contentgroup.setContentgroup(contentgroup);
					new_contentgroup.create(this);
				}
				WCM_pagegroups.put(contentgroup, contentgroup);
			}

			if ((WCM_doctype.equals("")) && (elements.get("_HTML_DOCTYPE_") != null)) {
				WCM_doctype = "" + elements.get("_HTML_DOCTYPE_");
			}
			if ((WCM_charset.equals("")) && (elements.get("_HTML_CHARSET_") != null)) {
				WCM_charset = "" + elements.get("_HTML_CHARSET_");
			}

			try {
				out.print(WCM_pages.get(htmlfile) + " - " + title + "<br>" + "\r\n");
				out.flush();
			} catch (IOException e) {
			}
		}

		try {
			out.print(mytext.display("config.database.media.insertwebsite.contentclasses") + "\r\n");
			out.flush();
		} catch (IOException e) {
		}
		String[] elements = (String[])WCM_classes.keySet().toArray(new String[0]);
		for (int i=0; i<elements.length; i++) {
			String element = "" + elements[i];
			Element new_element = new Element();
			new_element.setElement(element);
			new_element.create(this);
			try {
				out.print(element + "<br>" + "\r\n");
				out.flush();
			} catch (IOException e) {
			}
		}

		try {
			out.print(mytext.display("config.database.media.insertwebsite.defaultpage") + "\r\n");
			out.flush();
		} catch (IOException e) {
		}
		ArrayList<String> defaultpage = import_website_defaultpages(htmlfiles);
		if ((defaultpage.size() > 0) && (defaultpage.get(0) != null) && (WCM_pages.get(defaultpage.get(0)) != null)) {
			config.set(this, "default_page", "" + WCM_pages.get(defaultpage.get(0)));
			try {
				out.print(WCM_pages.get(defaultpage.get(0)) + " - " + defaultpage.get(0) + "<br>" + "\r\n");
				out.flush();
			} catch (IOException e) {
			}
		}

		if (! WCM_doctype.equals("")) {
			config.set(this, "doctype", WCM_doctype);
		}
		if (! WCM_charset.equals("")) {
			config.set(this, "charset", WCM_charset);
		}
	}



	/* import_website_create_content */

	public String import_website_create_content(Configuration myconfig, Session mysession, String equals, String contentclass, String contentgroup, String contenttype, String title, String content, String htmlheader, String htmlbodyonload, String server_filename, String template, String stylesheet) {
		return import_website_create_content(myconfig, mysession, equals, contentclass, contentgroup, contenttype, title, content, htmlheader, htmlbodyonload, server_filename, template, stylesheet, new HashMap<String,String>());
	}
	public String import_website_create_content(Configuration myconfig, Session mysession, String equals, String contentclass, String contentgroup, String contenttype, String title, String content, String htmlheader, String htmlbodyonload, String server_filename, String template, String stylesheet, HashMap<String,String> elements) {
		boolean do_create = true;
		if ((contentclass.equals("file")) || (contentclass.equals("image"))) {
			String filenameextension = server_filename.substring(server_filename.lastIndexOf(".")+1);
			if (query_value("select id from " + contentclass + "formats where filenameextension " + equals + " " + quote(filenameextension)).longValue() > 0) {
				// ok
			} else {
				do_create = false;
			}
		}
		if (! server_filename.equals("")) {
			String SQL = "";
			if (db_type(getDatabase()).equals("mssql")) {
				SQL = "select id from content where server_filename is not null and substring(server_filename,1,250)" + equals + quote(server_filename);
			} else if (db_type(getDatabase()).equals("oracle")) {
				SQL = "select id from content where server_filename is not null and to_char(server_filename)" + equals + quote(server_filename);
			} else if (db_type(getDatabase()).equals("db2")) {
				SQL = "select id from content where server_filename is not null and varchar(server_filename,250)" + equals + quote(server_filename);
			} else {
				SQL = "select id from content where server_filename is not null and server_filename" + equals + quote(server_filename);
			}
			if (query_value(SQL).longValue() < 1) {
				// ok
			} else {
				do_create = false;
			}
		}
		if (do_create) {
			Content contentitem = new Content();
			contentitem.setContentClass(contentclass);
			contentitem.setContentGroup(contentgroup);
			contentitem.setContentType(contenttype);
			contentitem.setTitle(title);
			contentitem.setContent(content);
			contentitem.setHtmlHeader(htmlheader);
			contentitem.setHtmlBodyOnload(htmlbodyonload);
			contentitem.setServerFilename(server_filename);
			contentitem.setTemplate(template);
			contentitem.setStyleSheet(stylesheet);
			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			String username = mysession.get("username");
			contentitem.setCreated(timestamp);
			contentitem.setCreatedBy(username);
			contentitem.setUpdated(timestamp);
			contentitem.setUpdatedBy(username);
			contentitem.setPublished(timestamp);
			contentitem.setPublishedBy(username);
			if (elements != null) contentitem.setElement(elements);
			contentitem.create(this, myconfig, "content", "id");
			contentitem.create(this, myconfig, "content_public", "id");
			return contentitem.getId();
		} else {
			return "0";
		}
	}



	/* import_website_folderpath */

	public String import_website_folderpath(String filename) {
		String filepath = "";
		if (filename.lastIndexOf("/") > 0) {
			filepath = filename.substring(0, filename.lastIndexOf("/")+1);
		}
		return filepath;
	}



	/* import_website_listfolder */

	public ArrayList<String> import_website_listfolder(String base_path) {
		return import_website_listfolder(base_path, "", "");
	}
	public ArrayList<String> import_website_listfolder(String base_path, String exclude_paths, String include_files) {
		ArrayList<String> files = new ArrayList<String>();
		if (base_path != null) {
			files = import_website_listfolderfiles(base_path, "/", exclude_paths, include_files);
		}
		return files;
	}



	/* import_website_listfolderfiles */

	public ArrayList<String> import_website_listfolderfiles(String base_path, String path) {
		return import_website_listfolderfiles(base_path, path, "", "");
	}
	public ArrayList<String> import_website_listfolderfiles(String base_path, String path, String exclude_paths, String include_files) {
		ArrayList<String> files = new ArrayList<String>();
		File dir = new File(base_path + path);
		if (dir.exists()) {
			String[] myfiles = new File(base_path + path).list();
			if (myfiles != null) for (int i=0; i<myfiles.length; i++) {
				String entry = "" + myfiles[i];
				File item = new File(base_path + path + entry);
				if ((! exclude_paths.equals("")) && (Pattern.compile(exclude_paths).matcher(path + entry).find())) {
					// ignore
				} else if ((entry.equals(".")) || (entry.equals(".."))) {
					// ignore
				} else if (item.isDirectory()) {
					files.addAll(import_website_listfolderfiles(base_path, path + entry + "/", exclude_paths, include_files));
				} else if ((include_files.equals("")) || (Pattern.compile(include_files).matcher(entry).find())) {
					files.add(path + entry);
				}
			}
			Collections.sort(files);
		}
		return files;
	}



	/* import_website_defaultpages */

	public ArrayList<String> import_website_defaultpages(ArrayList<String> filenames) {
		ArrayList<String> pages = new ArrayList<String>();
		String[] mydefaultpages = new String[] { "/index.html", "/index.htm", "/Default.html", "/Default.htm" };
		for (int i=0; i<mydefaultpages.length; i++) {
			String defaultpage = "" + mydefaultpages[i];
			for (int j=0; j<filenames.size(); j++) {
				String filename = "" + filenames.get(j);
				if (defaultpage.toLowerCase().equals(filename.toLowerCase())) {
					pages.add(filename);
				}
			}
		}
		return pages;
	}



	/* import_website_templatefiles */

	public ArrayList<String> import_website_templatefiles(String base_path, ArrayList<String> filenames, String encoding) {
		ArrayList<String> pages = new ArrayList<String>();
		String[] mytemplates = new String[] { ".dwt$" };
		for (int i=0; i<mytemplates.length; i++) {
			String template = "" + mytemplates[i];
			for (int j=0; j<filenames.size(); j++) {
				String filename = "" + filenames.get(j);
				if (Pattern.compile(template).matcher(filename).find()) {
					pages.add(filename);
				}
			}
		}
		for (int i=0; i<filenames.size(); i++) {
			String filename = "" + filenames.get(i);
			HashMap<String,String> parts = import_website_fileparts(base_path, filename, encoding);
			if ((parts.get("_TEMPLATE_FILE_") != null) && (! parts.get("_TEMPLATE_FILE_").equals("")) && (parts.get("_TEMPLATE_HTML_") != null) && (! parts.get("_TEMPLATE_HTML_").equals(""))) {
				 pages.add(parts.get("_TEMPLATE_FILE_"));
			}
		}
		return pages;
	}



	/* import_website_fileparts */

	public HashMap<String,String> import_website_fileparts(String base_path) {
		return import_website_fileparts(base_path, new ArrayList<String>());
	}
	public HashMap<String,String> import_website_fileparts(String base_path, String filename) {
		ArrayList<String> filenames = new ArrayList<String>();
		filenames.add(filename);
		return import_website_fileparts(base_path, filenames);
	}
	public HashMap<String,String> import_website_fileparts(String base_path, String filename, String encoding) {
		ArrayList<String> filenames = new ArrayList<String>();
		filenames.add(filename);
		return import_website_fileparts(base_path, filenames, encoding);
	}
	public HashMap<String,String> import_website_fileparts(String base_path, ArrayList<String> filenames) {
		return import_website_fileparts(base_path, filenames, "");
	}
	public HashMap<String,String> import_website_fileparts(String base_path, ArrayList<String> filenames, String encoding) {
		HashMap<String,String> parts = new HashMap<String,String>();
		for (int i=0; i<filenames.size(); i++) {
			String filename = "" + filenames.get(i);
			String content = Common.readFile(base_path + filename, encoding);
			parts.put("_ALL_CONTENT_", ""+content);

			if (Pattern.compile("(<!DOCTYPE.*?>)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(content).find()) {
				Matcher matches = Pattern.compile("(<!DOCTYPE.*?>)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(content);
				if (matches.find()) {
					parts.put("_HTML_DOCTYPE_", ""+matches.group(1));
				}
			}

			if (Pattern.compile("<head>(.*?)</head>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(content).find()) {
				Matcher matches = Pattern.compile("<head>(.*?)</head>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(content);
				if (matches.find()) {
					parts.put("_HTML_HEAD_", ""+matches.group(1));
				}
			}

			if (Pattern.compile("<meta .*?[ ;]charset=([^ ;\"<>]+?)[ ;\"].*?>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(content).find()) {
				Matcher matches = Pattern.compile("<meta .*?[ ;]charset=(.+?)[ ;\"].*?>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(content);
				if (matches.find()) {
					parts.put("_HTML_CHARSET_", ""+matches.group(1));
				}
			}

			if (Pattern.compile("<title>(.*?)</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(content).find()) {
				Matcher matches = Pattern.compile("<title>(.*?)</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(content);
				if (matches.find()) {
					parts.put("_HTML_TITLE_", ""+matches.group(1));
				}
			}

			if (Pattern.compile("<body(.*?)>(.*?)</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(content).find()) {
				Matcher matches = Pattern.compile("<body(.*?)>(.*?)</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(content);
				if (matches.find()) {
					parts.put("_HTML_BODY_", ""+matches.group(1));
					parts.put("_HTML_CONTENT_", ""+matches.group(2));
				}
			}

			if (Pattern.compile("<!-- #BeginTemplate \"(.*?)\" -->(.*?)<!-- #EndTemplate -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(content).find()) {
				Matcher matches = Pattern.compile("<!-- #BeginTemplate \"(.*?)\" -->(.*?)<!-- #EndTemplate -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(content);
				if (matches.find()) {
					parts.put("_TEMPLATE_FILE_", ""+matches.group(1));
					parts.put("_TEMPLATE_HTML_", ""+matches.group(2));
					parts.put("_TEMPLATE_HTML_", Pattern.compile("<!-- #BeginEditable \"(.*?)\" -->(.*?)<!-- #EndEditable -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).replaceAll("@@@$1.content@@@"));
					parts.put("_TEMPLATE_HTML_", Pattern.compile("<!-- TemplateBeginEditable name=\"(.*?)\" -->(.*?)<!-- TemplateEndEditable -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).replaceAll("@@@$1.content@@@"));
					parts.put("_TEMPLATE_HTML_", Pattern.compile("<!-- InstanceBeginEditable name=\"(.*?)\" -->(.*?)<!-- InstanceEndEditable -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).replaceAll("@@@$1.content@@@"));
					if (Pattern.compile("<head>(.*?)</head>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).find()) {
						matches = Pattern.compile("<head>(.*?)</head>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_"));
						if (matches.find()) {
							parts.put("_TEMPLATE_HTML_HEAD_", ""+matches.group(1));
						}
					}
					if (Pattern.compile("<title>(.*?)</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).find()) {
						matches = Pattern.compile("<title>(.*?)</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_"));
						if (matches.find()) {
							parts.put("_TEMPLATE_HTML_TITLE_", ""+matches.group(1));
						}
					}
					if (Pattern.compile("<body(.*?)>(.*?)</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).find()) {
						matches = Pattern.compile("<body(.*?)>(.*?)</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_"));
						if (matches.find()) {
							parts.put("_TEMPLATE_HTML_BODY_", ""+matches.group(1));
							parts.put("_TEMPLATE_HTML_CONTENT_", ""+matches.group(2));
						}
					}
				}
			}

			if (Pattern.compile("<!-- InstanceBegin template=\"(.*?)\".*? -->(.*?)<!-- InstanceEnd -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(content).find()) {
				Matcher matches = Pattern.compile("<!-- InstanceBegin template=\"(.*?)\".*? -->(.*?)<!-- InstanceEnd -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(content);
				if (matches.find()) {
					parts.put("_TEMPLATE_FILE_", ""+matches.group(1));
					parts.put("_TEMPLATE_HTML_", ""+matches.group(2));
					parts.put("_TEMPLATE_HTML_", Pattern.compile("<!-- #BeginEditable \"(.*?)\" -->(.*?)<!-- #EndEditable -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).replaceAll("@@@$1.content@@@"));
					parts.put("_TEMPLATE_HTML_", Pattern.compile("<!-- TemplateBeginEditable name=\"(.*?)\" -->(.*?)<!-- TemplateEndEditable -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).replaceAll("@@@$1.content@@@"));
					parts.put("_TEMPLATE_HTML_", Pattern.compile("<!-- InstanceBeginEditable name=\"(.*?)\" -->(.*?)<!-- InstanceEndEditable -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).replaceAll("@@@$1.content@@@"));
					if (Pattern.compile("<head>(.*?)</head>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).find()) {
						matches = Pattern.compile("<head>(.*?)</head>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_"));
						if (matches.find()) {
							parts.put("_TEMPLATE_HTML_HEAD_", ""+matches.group(1));
						}
					}
					if (Pattern.compile("<title>(.*?)</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).find()) {
						matches = Pattern.compile("<title>(.*?)</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_"));
						if (matches.find()) {
							parts.put("_TEMPLATE_HTML_TITLE_", ""+matches.group(1));
						}
					}
					if (Pattern.compile("<body(.*?)>(.*?)</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).find()) {
						matches = Pattern.compile("<body(.*?)>(.*?)</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_"));
						if (matches.find()) {
							parts.put("_TEMPLATE_HTML_BODY_", ""+matches.group(1));
							parts.put("_TEMPLATE_HTML_CONTENT_", ""+matches.group(2));
						}
					}
				}
			}

			if (((parts.get("_TEMPLATE_FILE_") == null) || (("" + parts.get("_TEMPLATE_FILE_")).equals(""))) && ((parts.get("_TEMPLATE_HTML_") == null) || (("" + parts.get("_TEMPLATE_HTML_")).equals("")))) {
				parts.put("_TEMPLATE_FILE_", base_path + filename);
				parts.put("_TEMPLATE_HTML_", "" + content);
				parts.put("_TEMPLATE_HTML_", Pattern.compile("<!-- #BeginEditable \"(.*?)\" -->(.*?)<!-- #EndEditable -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).replaceAll("@@@$1.content@@@"));
				parts.put("_TEMPLATE_HTML_", Pattern.compile("<!-- TemplateBeginEditable name=\"(.*?)\" -->(.*?)<!-- TemplateEndEditable -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).replaceAll("@@@$1.content@@@"));
				parts.put("_TEMPLATE_HTML_", Pattern.compile("<!-- InstanceBeginEditable name=\"(.*?)\" -->(.*?)<!-- InstanceEndEditable -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).replaceAll("@@@$1.content@@@"));
				if (Pattern.compile("<head>(.*?)</head>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).find()) {
					Matcher matches = Pattern.compile("<head>(.*?)</head>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_"));
					if (matches.find()) {
						parts.put("_TEMPLATE_HTML_HEAD_", ""+matches.group(1));
					}
				}
				if (Pattern.compile("<title>(.*?)</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).find()) {
					Matcher matches = Pattern.compile("<title>(.*?)</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_"));
					if (matches.find()) {
						parts.put("_TEMPLATE_HTML_TITLE_", ""+matches.group(1));
					}
				}
				if (Pattern.compile("<body(.*?)>(.*?)</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_")).find()) {
					Matcher matches = Pattern.compile("<body(.*?)>(.*?)</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_TEMPLATE_HTML_"));
					if (matches.find()) {
						parts.put("_TEMPLATE_HTML_BODY_", ""+matches.group(1));
						parts.put("_TEMPLATE_HTML_CONTENT_", ""+matches.group(2));
					}
				}
			}

			if (Pattern.compile("<!-- #BeginEditable \"(.*?)\" -->(.*?)<!-- #EndEditable -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_HTML_CONTENT_")).find()) {
				Pattern p = Pattern.compile("<!-- #BeginEditable \"(.*?)\" -->(.*?)<!-- #EndEditable -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
				Matcher m = p.matcher("" + parts.get("_HTML_CONTENT_"));
				while (m.find()) {
					parts.put("" + m.group(1), "" + m.group(2));
				}
			}

			if (Pattern.compile("<!-- TemplateBeginEditable name=\"(.*?)\" -->(.*?)<!-- TemplateEndEditable -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_HTML_CONTENT_")).find()) {
				Pattern p = Pattern.compile("<!-- TemplateBeginEditable name=\"(.*?)\" -->(.*?)<!-- TemplateEndEditable -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
				Matcher m = p.matcher("" + parts.get("_HTML_CONTENT_"));
				while (m.find()) {
					parts.put("" + m.group(1), "" + m.group(2));
				}
			}

			if (Pattern.compile("<!-- InstanceBeginEditable name=\"(.*?)\" -->(.*?)<!-- InstanceEndEditable -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("" + parts.get("_HTML_CONTENT_")).find()) {
				Pattern p = Pattern.compile("<!-- InstanceBeginEditable name=\"(.*?)\" -->(.*?)<!-- InstanceEndEditable -->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
				Matcher m = p.matcher("" + parts.get("_HTML_CONTENT_"));
				while (m.find()) {
					parts.put("" + m.group(1), "" + m.group(2));
				}
			}
		}
		return parts;
	}



	/* import_website_titleelements */

	public ArrayList<String> import_website_titleelements(HashMap<String,String> parts) {
		ArrayList<String> elements = new ArrayList<String>();
		String[] mytitleelements = new String[] { "title", "_HTML_TITLE_" };
		for (int i=0; i<mytitleelements.length; i++) {
			String titleelement = "" + mytitleelements[i];
			String[] myparts = (String[])parts.keySet().toArray(new String[0]);
			for (int j=0; j<myparts.length; j++) {
				String part = "" + myparts[j];
				if (titleelement.toLowerCase().equals(part.toLowerCase())) {
					elements.add(part);
				}
			}
		}
		return elements;
	}



	/* import_website_contentelements */

	public ArrayList<String> import_website_contentelements(HashMap<String,String> parts) {
		ArrayList<String> elements = new ArrayList<String>();
		String[] myparts;
		String[] mycontentelements = new String[] { "body", "content", "main", "page" };
		for (int i=0; i<mycontentelements.length; i++) {
			String contentelement = "" + mycontentelements[i];
			myparts = (String[])parts.keySet().toArray(new String[0]);
			for (int j=0; j<myparts.length; j++) {
				String part = "" + myparts[j];
				if (contentelement.toLowerCase().equals(part.toLowerCase())) {
					elements.add(part);
				}
			}
		}
		myparts = (String[])parts.keySet().toArray(new String[0]);
		for (int i=0; i<myparts.length; i++) {
			String part = "" + myparts[i];
			if (Pattern.compile("^(body|content|main|page|_ALL_CONTENT_|_HTML_CONTENT_|_HTML_.*_|_TEMPLATE_.*_)$", Pattern.CASE_INSENSITIVE).matcher(part).find()) {
				// ignore
			} else {
				elements.add(part);
			}
		}
		mycontentelements = new String[] { "_HTML_CONTENT_" };
		for (int i=0; i<mycontentelements.length; i++) {
			String contentelement = "" + mycontentelements[i];
			myparts = (String[])parts.keySet().toArray(new String[0]);
			for (int j=0; j<myparts.length; j++) {
				String part = "" + myparts[j];
				if (contentelement.toLowerCase().equals(part.toLowerCase())) {
					elements.add(part);
				}
			}
		}
		return elements;
	}



/*
	DATABASE ENCRYPTION UTILITY FUNCTIONS
*/



	/* encryption */

	public void encryption(String decrypted, String encrypted) {
		decrypted_chars = "" + decrypted;
		encrypted_chars = "" + encrypted;
		encrypted_prefix = "";
	}



	public void encryption(String decrypted, String encrypted, String prefix) {
		char[] chars = (""+decrypted).toCharArray();
		java.util.Arrays.sort(chars);
		String sorted_decrypted_chars = new String(chars);
		chars = (""+encrypted).toCharArray();
		java.util.Arrays.sort(chars);
		String sorted_encrypted_chars = new String(chars);
		if ((decrypted != null) && (encrypted != null) && (prefix != null) && (! decrypted.equals("")) && (! encrypted.equals("")) && (decrypted.length() == encrypted.length()) && (sorted_decrypted_chars.equals(sorted_encrypted_chars)) && (! prefix.equals(""))) {
			decrypted_chars = "" + decrypted;
			encrypted_chars = "" + encrypted;
			encrypted_prefix = "" + prefix;
			enableEncryption();
		} else {
			decrypted_chars = "";
			encrypted_chars = "";
			encrypted_prefix = "";
			disableEncryption();
		}
	}



	/* enableEncryption */

	public void enableEncryption() {
		encryption_enabled = true;
	}



	/* disableEncryption */

	public void disableEncryption() {
		encryption_enabled = false;
	}



	/* encrypt */

	public String encrypt(String value) {
		if ((encryption_enabled == false) || (value == null) || (value.equals("")) || (decrypted_chars.equals("")) || (encrypted_chars.equals(""))) return value;
		String encrypted = "" + encrypted_prefix;
		for (int i=0; i<value.length(); i++) {
			char encrypted_char = value.charAt(i);
			for (int j=0; j<decrypted_chars.length(); j++) {
				if (value.charAt(i) == decrypted_chars.charAt(j)) {
					encrypted_char = encrypted_chars.charAt(j);
					break;
				}
			}
			encrypted += encrypted_char;
		}
		return encrypted;
	}



	/* decrypt */

	public String decrypt(String value) {
		if ((encryption_enabled == false) || (value == null) || (value.equals("")) || (decrypted_chars.equals("")) || (encrypted_chars.equals(""))) return value;
		String decrypted = "";
		if ((! encrypted_prefix.equals("")) && (! value.startsWith(encrypted_prefix))) {
			return value;
		} else if (! encrypted_prefix.equals("")) {
			value = value.replaceAll("^" + encrypted_prefix, "");
		}
		for (int i=0; i<value.length(); i++) {
			char decrypted_char = value.charAt(i);
			for (int j=0; j<encrypted_chars.length(); j++) {
				if (value.charAt(i) == encrypted_chars.charAt(j)) {
					decrypted_char = decrypted_chars.charAt(j);
					break;
				}
			}
			decrypted += decrypted_char;
		}
		return decrypted;
	}



	/* digest */

	public String digest(String value, int maxlength) {
		if ((value == null) || (value.equals(""))) return value;
		try {
			String VALID_CHARS = "0123456789ABCDEFGHJKLMNPQRTUVWXY";
			StringBuffer sKey = new StringBuffer();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] sMD5 = md5.digest((value.toUpperCase()).getBytes());
			for(int lCount=1; lCount<=maxlength && lCount<=sMD5.length; lCount++) {
				int lChar = ((int)(sMD5[lCount-1] & 0xFF)) % 32;
				sKey.append(VALID_CHARS.substring(lChar, lChar+1));
			}
			return "" + sKey;
		} catch (Exception e) {
			return value;
		}
	}



}
