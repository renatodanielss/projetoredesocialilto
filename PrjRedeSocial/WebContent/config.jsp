<%@ page import="java.net.URLEncoder" %><%@ page import="java.sql.*" %><%@ page import="java.text.*" %><%@ page import="java.util.*" %><%@ page import="java.util.regex.*" %><%@ page import="HardCore.html" %><%@ page import="HardCore.inidb" %><%@ page import="HardCore.Cms" %><%@ page import="HardCore.Cache" %><%@ page import="HardCore.Common" %><%@ page import="HardCore.Configuration" %><%@ page import="HardCore.Currency" %><%@ page import="HardCore.DB" %><%@ page import="HardCore.Device" %><%@ page import="HardCore.Hosting" %><%@ page import="HardCore.License" %><%@ page import="HardCore.Login" %><%@ page import="HardCore.Request" %><%@ page import="HardCore.Response" %><%@ page import="HardCore.Session" %><%@ page import="HardCore.Text" %><%@ page import="HardCore.UCpublishScheduled" %><%@ page import="HardCore.User" %><%@ page import="HardCore.Website" %><%
%>
<%@ page import="com.iliketo.util.*"%>

<%
String configAdminPath = "webadmin";

%><%

HashMap ini_cache_database = new HashMap();
HashMap ini_cache_URLrootpath = new HashMap();
HashMap database_version = new HashMap();
HashMap superadmin = new HashMap();
HashMap default_page = new HashMap();
HashMap default_template = new HashMap();
HashMap default_stylesheet = new HashMap();
HashMap default_version = new HashMap();

//ServletContext servletcontext = getServletContext();
ServletContext servletcontext = getServletConfig().getServletContext();

String DOCUMENT_ROOT = Common.getRealPath(servletcontext, "/").replaceAll("[\\\\/]$", "");

%><%@ include file="ini.jsp" %><%@ include file="defaults.jsp" %><%

Request myrequest = new Request(request);
Response myresponse = new Response(response);
Session mysession = new Session(session);
Text mytext = new Text(myrequest);
Website website = new Website(mytext);
Configuration myconfig = new Configuration();
Device mydevice = new Device();

if (! mysession.get("POST").equals("")) {
	Common.unserialize(mysession.get("POST"), myrequest);
	mysession.set("POST", "");
}

String servername = myrequest.getServerName();
String inifile = Common.getRealPath(servletcontext, "/" + configAdminPath + "/ini.jsp");

if (database_version.containsKey(servername)) {
	myconfig.setTemp("database_version", "" + database_version.get(servername));
}
if (superadmin.containsKey(servername)) {
	myconfig.setTemp("superadmin", "" + superadmin.get(servername));
}

if (default_version.containsKey(servername)) {
	myconfig.setTemp("default_version", "" + default_version.get(servername));
	website.setTemp(servername, "default_version", "" + default_version.get(servername));
}
if (default_page.containsKey(servername)) {
	myconfig.setTemp("default_page", "" + default_page.get(servername));
	website.setTemp(servername, "default_page", "" + default_page.get(servername));
}
if (default_template.containsKey(servername)) {
	myconfig.setTemp("default_template", "" + default_template.get(servername));
}
if (default_stylesheet.containsKey(servername)) {
	myconfig.setTemp("default_stylesheet", "" + default_stylesheet.get(servername));
}

String dummy_database = "access:sun.jdbc.odbc.JdbcOdbcDriver::@jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb)}; DBQ=" + Common.getRealPath(servletcontext, "/" + mytext.display("adminpath") + "/database.mdb");
String original_database = "access:sun.jdbc.odbc.JdbcOdbcDriver::@jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb)}; DBQ=" + Common.getRealPath(servletcontext, "/" + mytext.display("adminpath") + "/database.original.mdb");
// do not use Microsoft Access database as default
dummy_database = "";
original_database = "";

String database = "" + dummy_database;
%><%@ include file="config.cloud.jsp" %><%
String default_database = inidb.ReadINI(inifile, "default", "database", database, inifile, ini_cache_database, ini_cache_URLrootpath);
String website_database = "" + default_database;
//website_database = inidb.ReadINI(inifile, servername, "database", default_database, inifile, ini_cache_database, ini_cache_URLrootpath);
String current_database = "" + website_database;
database = "" + current_database;
String ini_database = "";
if (database.equals(default_database)) {
	ini_database = "default";
} else {
	ini_database = "" + servername;
}

String URLrootpath = "/";
URLrootpath = inidb.ReadINI(inifile, "default", "URLrootpath", URLrootpath, inifile, ini_cache_database, ini_cache_URLrootpath);
//URLrootpath = inidb.ReadINI(inifile, servername, "URLrootpath", URLrootpath, inifile, ini_cache_database, ini_cache_URLrootpath);
myconfig.setTemp("URLrootpath", URLrootpath);
myconfig.setTemp("URLfilepath", "file/");
myconfig.setTemp("URLimagepath", "image/");
myconfig.setTemp("URLstylesheetpath", "stylesheet/");
myconfig.setTemp("URLuploadpath", "upload/");	//configuração pasta 'upload' dentro do diretório raiz

//java.sql.DriverManager.setLogWriter(new java.io.PrintWriter(System.out));
String db_error = "";
DB db = new DB(mytext);
DB logdb = null;

%><%@ include file="config.static.jsp" %><%

db.connect(DB.DSN(database), database);
if (db.isError()) {
	db_error = (db.getMessage() + " " + db.getDebugInfo()).trim();
	database = "" + dummy_database;
	db.connect(DB.DSN(database), database);
}
if (db.isError()) {
	db = null;
}


//***Diretorio de armazenamento de midias***
if (db != null) {
	
	//'csrootpath' nome do campo 'Folder(/Bucket) Path/Name' na configuração Media Library
	if(myconfig.get(db, "csrootpath") != null && !myconfig.get(db, "csrootpath").equals("")){ //se tiver configuração
		
		//Exemplo: DOCUMENT_ROOT = "D:\\Todos Arquivos\\Documentos\\Asbru";	//'Diretorio Raiz'	
		DOCUMENT_ROOT = myconfig.get("csrootpath");
		myconfig.setTemp("URLimagepath", DOCUMENT_ROOT + "/image/");
		
		String diretorioArmazenamento = DOCUMENT_ROOT + "/" + myconfig.get("URLuploadpath"); //diretorio armazenamento + pasta upload padrão
		mysession.set(Str.STORAGE, diretorioArmazenamento); //seta na session o diretorio raiz + pasta upload de arquivos recuperar no html @@@get:storage@@@
	
	}else{
		
		//aramazena o endereço do local padrão de upload na session
		String diretorioArmazenamento = DOCUMENT_ROOT + "/" + myconfig.get("URLuploadpath");
		mysession.set(Str.STORAGE, diretorioArmazenamento);
		
	}
}
//***


if (License.valid(db, myconfig, "hosting")) {
	Hosting hosting = new Hosting(mytext);
	hosting.readClientAddress(db, servername);
	if (! hosting.getClientDatabase().equals("")) {
		website_database = hosting.getClientDatabase();
		current_database = "" + website_database;
		database = "" + current_database;
		ini_database = "" + servername;
		DB myclient_db = new DB(mytext);
		myclient_db.DEBUG = db.DEBUG;
		myclient_db.context_provider = db.context_provider;
		myclient_db.context_factory = db.context_factory;
		myclient_db.datasource_factory = db.datasource_factory;
		myclient_db.datasource_prefix = db.datasource_prefix;
		myclient_db.datasource_maxActive = db.datasource_maxActive;
		myclient_db.datasource_maxIdle = db.datasource_maxIdle;
		myclient_db.datasource_maxWait = db.datasource_maxWait;
		myclient_db.connect(DB.DSN(database), database);
		if (myclient_db.isError()) {
			db_error = (myclient_db.getMessage() + " " + myclient_db.getDebugInfo()).trim();
			current_database = "" + default_database;
			database = "" + default_database;
			ini_database = "default";
		} else {
			db.close();
			db = myclient_db;
		}
		if (db.isError()) {
			db = null;
		}

		String save_session_address_lock = myconfig.getTemp("session_address_lock");
		String save_url_rewriting = myconfig.getTemp("url_rewriting");
		String save_decimals = myconfig.getTemp("decimals");
		String save_grouping = myconfig.getTemp("grouping");
		String save_do_log_page = myconfig.getTemp("do_log_page");
		String save_do_log_image = myconfig.getTemp("do_log_image");
		String save_do_log_file = myconfig.getTemp("do_log_file");
		String save_do_log_link = myconfig.getTemp("do_log_link");
		String save_do_log_product = myconfig.getTemp("do_log_product");
		String save_do_log_stylesheet = myconfig.getTemp("do_log_stylesheet");
		String save_do_log_script = myconfig.getTemp("do_log_script");
		String save_do_log_contact = myconfig.getTemp("do_log_contact");
		String save_do_log_post = myconfig.getTemp("do_log_post");
		String save_do_log_login = myconfig.getTemp("do_log_login");
		String save_do_log_logout = myconfig.getTemp("do_log_logout");
		String save_import_website_exclude = myconfig.getTemp("import_website_exclude");
		String save_getset = myconfig.getTemp("getset");
		String save_affiliate_parameter = myconfig.getTemp("affiliate_parameter");
		String save_affiliate_change = myconfig.getTemp("affiliate_change");
		String save_affiliate_id = myconfig.getTemp("affiliate_id");

		myconfig = new Configuration();
		URLrootpath = hosting.getClientURLrootpath();
		myconfig.setTemp("URLrootpath", URLrootpath);
		myconfig.setTemp("URLfilepath", "file/");
		myconfig.setTemp("URLimagepath", "image/");
		myconfig.setTemp("URLstylesheetpath", "stylesheet/");
		myconfig.setTemp("URLuploadpath", "upload/");
		// Set additional locked hosting client website configuration settings here
		myconfig.setTemp("personal_license", hosting.getPersonalLicense());
		myconfig.setTemp("professional_license", hosting.getProfessionalLicense());
		myconfig.setTemp("enterprise_license", hosting.getEnterpriseLicense());
		myconfig.setTemp("hosting_license", hosting.getHostingLicense());
		myconfig.setTemp("ecommerce_license", hosting.getEcommerceLicense());
		myconfig.setTemp("community_license", hosting.getCommunityLicense());
		myconfig.setTemp("databases_license", hosting.getDatabasesLicense());
		myconfig.setTemp("statistics_license", hosting.getStatisticsLicense());
		//myconfig.setTemp("superadmin", hosting.getSuperadmin());
		//myconfig.setTemp("superadmin_password", hosting.getSuperadminPassword());
		//myconfig.setTemp("superadmin_email", hosting.getSuperadminEmail());

		if (! save_session_address_lock.equals("")) myconfig.setTemp("session_address_lock", save_session_address_lock);
		if (! save_url_rewriting.equals("")) myconfig.setTemp("url_rewriting", save_url_rewriting);
		if (! save_decimals.equals("")) myconfig.setTemp("decimals", save_decimals);
		if (! save_grouping.equals("")) myconfig.setTemp("grouping", save_grouping);
		if (! save_do_log_page.equals("")) myconfig.setTemp("do_log_page", save_do_log_page);
		if (! save_do_log_image.equals("")) myconfig.setTemp("do_log_image", save_do_log_image);
		if (! save_do_log_file.equals("")) myconfig.setTemp("do_log_file", save_do_log_file);
		if (! save_do_log_link.equals("")) myconfig.setTemp("do_log_link", save_do_log_link);
		if (! save_do_log_product.equals("")) myconfig.setTemp("do_log_product", save_do_log_product);
		if (! save_do_log_stylesheet.equals("")) myconfig.setTemp("do_log_stylesheet", save_do_log_stylesheet);
		if (! save_do_log_script.equals("")) myconfig.setTemp("do_log_script", save_do_log_script);
		if (! save_do_log_contact.equals("")) myconfig.setTemp("do_log_contact", save_do_log_contact);
		if (! save_do_log_post.equals("")) myconfig.setTemp("do_log_post", save_do_log_post);
		if (! save_do_log_login.equals("")) myconfig.setTemp("do_log_login", save_do_log_login);
		if (! save_do_log_logout.equals("")) myconfig.setTemp("do_log_logout", save_do_log_logout);
		if (! save_import_website_exclude.equals("")) myconfig.setTemp("import_website_exclude", save_import_website_exclude);
		if (! save_getset.equals("")) myconfig.setTemp("getset", save_getset);
		if (! save_affiliate_parameter.equals("")) myconfig.setTemp("affiliate_parameter", save_affiliate_parameter);
		if (! save_affiliate_change.equals("")) myconfig.setTemp("affiliate_change", save_affiliate_change);
		if (! save_affiliate_id.equals("")) myconfig.setTemp("affiliate_id", save_affiliate_id);

%><%@ include file="config.hosting.jsp" %><%

	}
}

if ((db != null) && (! myconfig.get(db, "logdatabase").equals(""))) {
	logdb = new DB(mytext);
	logdb.connect(DB.DSN(myconfig.get(db, "logdatabase")), myconfig.get(db, "logdatabase"));
	if (logdb.isError()) {
		logdb = null;
	}
}
if (logdb == null) logdb = db;

if ((db != null) && (mysession != null) && (mysession.get("database").equals(""))) {
	mysession.set("database", database);
} else if ((! database.equals(mysession.get("database"))) && (! mysession.get("database").equals(""))) {
	if (mysession != null) {
		Login.logout(mysession);
		mysession.invalidate();
		mysession.regenerate(request);
	}
}
if (mysession.get("REMOTE_ADDR").equals("")) {
	mysession.set("REMOTE_ADDR", myrequest.getRemoteAddr());
	mysession.set("REMOTE_HOST", myrequest.getRemoteHost());
} else if ((! mysession.get("REMOTE_ADDR").equals(myrequest.getRemoteAddr())) && (! mysession.get("REMOTE_HOST").equals(myrequest.getRemoteHost()))) {
	boolean invalidate = true;
	if (myconfig.getTemp("session_address_lock").equals("")) {
		myconfig.setTemp("session_address_lock", myconfig.get(db, "session_address_lock"));
	}
	if (myconfig.getTemp("session_address_lock").equals("")) {
		invalidate = (! Login.addressesMatch(mysession.get("REMOTE_ADDR"), myrequest.getRemoteAddr())) && (! Login.addressesMatch(mysession.get("REMOTE_HOST"), myrequest.getRemoteHost()));
	} else if (myconfig.getTemp("session_address_lock").equals("none")) {
		invalidate = false;
	} else if (myconfig.getTemp("session_address_lock").equals("base")) {
		invalidate = (! Login.addressesMatch(mysession.get("REMOTE_ADDR"), myrequest.getRemoteAddr())) && (! Login.addressesMatch(mysession.get("REMOTE_HOST"), myrequest.getRemoteHost()));
	} else if (myconfig.getTemp("session_address_lock").equals("full")) {
		invalidate = true;
	}
	if (invalidate) {
		Login.logout(mysession);
		mysession.invalidate();
		mysession.regenerate(request);
	}
}

if (! myrequest.getCookie("visitorid").equals("")) {
	if (! myrequest.getCookie("visitorid").equals(mysession.get("visitorid"))) {
		mysession.set("visitorid", myrequest.getCookie("visitorid").replaceAll("\"", ""));
		mysession.set("visitorduration", "");
		mysession.set("visitor", "old");
		mysession.remove("usersegments");
		mysession.remove("usertests");
		mysession.remove("userteststags");
	}
} else if (! mysession.get("visitorid").equals("")) {
	String visitorid = "" + mysession.get("visitorid");
	myresponse.setCookie("visitorid", visitorid, 2*365*24*60*60);
} else {
	java.util.Date now = new java.util.Date();
	String visitorid = "" + (new SimpleDateFormat("yyyyMMddHHmmss").format(now)) + (int)(Math.random()*10) + (int)(Math.random()*10) + (int)(Math.random()*10) + (int)(Math.random()*10) + (int)(Math.random()*10) + (int)(Math.random()*10);
	myresponse.setCookie("visitorid", visitorid, 2*365*24*60*60);
	mysession.set("visitorid", visitorid);
	mysession.set("visitorduration", "0");
	mysession.set("visitor", "new");
	mysession.remove("usersegments");
	mysession.remove("usertests");
	mysession.remove("userteststags");
}
if ((! myconfig.get(db, "experience_license").equals("")) && (! myconfig.get(db, "use_experience").equals("no"))) {
	if (! myconfig.get(db, "use_usersegments").equals("no")) {
		if ((! myrequest.getCookie("usersegments").equals("")) && (mysession.get("usersegments").equals(""))) {
			mysession.set("usersegments", myrequest.getCookie("usersegments").replaceAll("\"", ""));
		}
	}
	if (! myconfig.get(db, "use_usertests").equals("no")) {
		if ((! myrequest.getCookie("usertests").equals("")) && (mysession.get("usertests").equals(""))) {
			mysession.set("usertests", myrequest.getCookie("usertests").replaceAll("\"", ""));
		}
		if ((! myrequest.getCookie("userteststags").equals("")) && (mysession.get("userteststags").equals(""))) {
			mysession.set("userteststags", myrequest.getCookie("userteststags").replaceAll("\"", ""));
		}
	}
}
if ((! mysession.exists("device")) && (! myconfig.get(db, "use_devices").equals("no"))) {
	mydevice.identify(myrequest.getHeader("User-Agent"), mysession, true, (! myconfig.get(db, "use_devices_minor").equals("no")));
}

if ((! mysession.get("username").equals("")) && (mysession.get("username").equals(myconfig.get(db, "superadmin"))) && (! mysession.get("password").equals(myconfig.get(db, "superadmin_password")))) {
	mysession.set("username", "");
	mysession.set("password", "");
	mysession.set("email", "");
	mysession.set("administrator", "");
}

if (mysession.get("session_entry").equals("")) {
	mysession.set("session_entry", myrequest.getURL());
}

if ((! myconfig.getTemp("affiliate_parameter").equals("")) && (! myrequest.getParameter(myconfig.getTemp("affiliate_parameter")).equals("")) && ((! myconfig.getTemp("affiliate_change").equals("no")) || (mysession.get("affiliate_id").equals("")))) {
	mysession.set("affiliate_id", myrequest.getParameter(myconfig.getTemp("affiliate_parameter")));
}

if (( db == null) || ((database.equals(dummy_database)) && (! database.equals(website_database)))) {
	if ((new java.io.File(Common.getRealPath(servletcontext, "/unavailable.jsp")).exists())) {
		myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/unavailable.jsp");
	} else {
		myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/unavailable.html");
	}
} else if (myconfig.get(db, "database_version").equals("")) {
	myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + configAdminPath + "/index.jsp");
}

if (myconfig.get(db, "charset").equals("")) {
//	myconfig.setTemp("charset", "iso-8859-1");
	myconfig.setTemp("charset", "UTF-8");
}
Common.charset(myconfig.get(db, "charset"));
myrequest.setCharset(myconfig.get(db, "charset"));
if (mytext != null) mytext.setCharset(myconfig.get(db, "charset"));
if (! myconfig.get(db, "charset").equals("")) myresponse.setContentType("text/html; charset=" + myconfig.get(db, "charset"));

User adminuser = new User(mytext);
if ((! mysession.get("username").equals("")) && (mysession.get("administrator").equals("administrator"))) {
	adminuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mysession.get("username"));
	adminuser.setUsername(mysession.get("username"));
	if (! mysession.get("username").equals(myconfig.get(db, "superadmin"))) {
		Login.userSession(adminuser, mysession, db);
	}
}

%>