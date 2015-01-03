<%@ page import="java.net.URLEncoder" %><%@ page import="java.sql.*" %><%@ page import="java.text.*" %><%@ page import="java.util.*" %><%@ page import="java.util.regex.*" %><%@ page import="HardCore.html" %><%@ page import="HardCore.inidb" %><%@ page import="HardCore.Cms" %><%@ page import="HardCore.Cache" %><%@ page import="HardCore.Common" %><%@ page import="HardCore.Configuration" %><%@ page import="HardCore.Currency" %><%@ page import="HardCore.DB" %><%@ page import="HardCore.Device" %><%@ page import="HardCore.Hosting" %><%@ page import="HardCore.License" %><%@ page import="HardCore.Login" %><%@ page import="HardCore.Request" %><%@ page import="HardCore.Response" %><%@ page import="HardCore.Session" %><%@ page import="HardCore.Text" %><%@ page import="HardCore.UCpublishScheduled" %><%@ page import="HardCore.User" %><%@ page import="HardCore.Website" %><%

//ServletContext servletcontext = getServletContext();
ServletContext servletcontext = getServletConfig().getServletContext();

String DOCUMENT_ROOT = Common.getRealPath(servletcontext, "/").replaceAll("[\\\\/]$", "");

Cookie mycookie = new Cookie("AcceptLanguage", "" + URLEncoder.encode("" + request.getHeader("Accept-Language")));
try {
	response.addCookie(mycookie);
} catch (Exception e) {
}

Request myrequest = new Request(request);
Response myresponse = new Response(response);
Session mysession = new Session(session);
Text mytext = new Text(myrequest);
Website website = new Website(mytext);
Device mydevice = new Device(mytext, myrequest.getHeader("User-Agent"), mysession);
Configuration myconfig = new Configuration();

String servername = myrequest.getServerName();
String inifile = Common.getRealPath(servletcontext, "/ini." + mytext.display("adminpath") + ".jsp");
String oldinifile = Common.getRealPath(servletcontext, "/" + mytext.display("adminpath") + "/ini.jsp");
java.io.File fh = new java.io.File(inifile);
java.io.File oldfh = new java.io.File(oldinifile);
if ((! fh.exists()) && (oldfh.exists())) {
	Common.moveFile(oldinifile, inifile);
}
if ((! fh.exists()) && (oldfh.exists())) {
	Common.copyFile(oldinifile, inifile);
}
oldinifile = Common.getRealPath(servletcontext, "/hardcore/ini.jsp");
fh = new java.io.File(inifile);
oldfh = new java.io.File(oldinifile);
if ((! fh.exists()) && (oldfh.exists())) {
	Common.moveFile(oldinifile, inifile);
}
if ((! fh.exists()) && (oldfh.exists())) {
	Common.copyFile(oldinifile, inifile);
}

String dummy_database = "access:sun.jdbc.odbc.JdbcOdbcDriver::@jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb)}; DBQ=" + Common.getRealPath(servletcontext, "/" + mytext.display("adminpath") + "/database.mdb");
String original_database = "access:sun.jdbc.odbc.JdbcOdbcDriver::@jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb)}; DBQ=" + Common.getRealPath(servletcontext, "/" + mytext.display("adminpath") + "/database.original.mdb");
// do not use Microsoft Access database as default
dummy_database = "";
original_database = "";

//POSTGRESQL - INIT
String dbname = "db_asbru";
String username = "asbru_user";
String password = "dFGdMGV5d1fv0FCv3x4f";
String hostname = "localhost";
String port = "5432";
//exemplo = pgsql:org.postgresql.Driver:username:password@jdbc:postgresql://localhost/database
dummy_database = "pgsql:org.postgresql.Driver:" + username + ":" + password + "@jdbc:postgresql://" + hostname + ":" + port + "/" + dbname;
original_database = "pgsql:org.postgresql.Driver:" + username + ":" + password + "@jdbc:postgresql://" + hostname + ":" + port + "/" + dbname;
//POSTGRESQL – END

String database = "" + dummy_database;
%><%@ include file="../config.cloud.jsp" %><%
String default_database = inidb.ReadINI(inifile, "default", "database", database, "", null, null);
String website_database = "" + default_database;
//website_database = inidb.ReadINI(inifile, servername, "database", default_database, "", null, null);
String current_database = "" + website_database;
database = "" + current_database;
String ini_database = "";
if (database.equals(default_database)) {
	ini_database = "default";
} else {
	ini_database = "" + servername;
}

String URLrootpath = "/";
URLrootpath = inidb.ReadINI(inifile, "default", "URLrootpath", URLrootpath, "", null, null);
//URLrootpath = inidb.ReadINI(inifile, servername, "URLrootpath", URLrootpath, "", null, null);
myconfig.setTemp("URLrootpath", URLrootpath);
myconfig.setTemp("URLfilepath", "file/");
myconfig.setTemp("URLimagepath", "image/");
myconfig.setTemp("URLstylesheetpath", "stylesheet/");
myconfig.setTemp("URLuploadpath", "upload/");

//java.sql.DriverManager.setLogWriter(new java.io.PrintWriter(System.out));
String db_error = "";
DB db = new DB(mytext);
DB logdb = null;

%><%@ include file="../config.static.jsp" %><%

db.connect(DB.DSN(database), database);
if (db.isError()) {
	db_error = (db.getMessage() + " " + db.getDebugInfo()).trim();
	if (! (new java.io.File(Common.getRealPath(servletcontext, "/" + mytext.display("adminpath") + "/database.mdb")).exists())) {
		Common.copyFile(Common.getRealPath(servletcontext, "/" + mytext.display("adminpath") + "/database.original.mdb"), Common.getRealPath(servletcontext, "/" + mytext.display("adminpath") + "/database.mdb"));
	}
	database = "" + dummy_database;
	db.connect(DB.DSN(database), database);
}
if (db.isError()) {
	db = null;
}

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

%><%@ include file="../config.hosting.jsp" %><%

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
if (myrequest.getRequestURI().startsWith("/webadmin/")) {
	myresponse.setCookie("usersegments", "", 2*365*24*60*60);
	myresponse.setCookie("usertests", "", 2*365*24*60*60);
	myresponse.setCookie("userteststags", "", 2*365*24*60*60);
	mysession.remove("usersegments");
	mysession.remove("usertests");
	mysession.remove("userteststags");
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

String wizard = "";
//if ((database.equals(dummy_database)) && (! database.equals(website_database))) {
//	if ((new java.io.File(Common.getRealPath(servletcontext, "/unavailable.jsp")).exists())) {
//		myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/unavailable.jsp");
//		return;
//	} else {
//		myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/unavailable.html");
//		return;
//	}
//} else 
if ((ini_database.equals("default") && inidb.ReadINI(inifile, ini_database, "database", "", "", null, null).equals("")) || (myconfig.get(db, "database_version").equals("")) || (db == null)) {
	wizard = "database";
} else if (! myconfig.get(db, "database_version").equals("9.0")) {
	wizard = "upgrade";
} else if ((! License.valid(db, myconfig, "personal")) && (! License.valid(db, myconfig, "hosting"))) {
	wizard = "license";
} else if (myconfig.get(db, "superadmin_email").equals("")) {
	wizard = "superadmin";
} else if (myconfig.get(db, "contact_form_recipient").equals("")) {
	wizard = "superadmin";
} else if (License.valid(db, myconfig, "ecommerce") && (myconfig.get(db, "order_form_recipient").equals(""))) {
	wizard = "superadmin";
} else if (myconfig.get(db, "default_page").equals("")) {
	wizard = "website";
} else if ((myconfig.get(db, "default_stylesheet").equals("")) || (myconfig.get(db, "default_template").equals(""))) {
	wizard = "design";
} else if (! myconfig.get(db, "quickstart_settings").equals("")) {
	wizard = "settings";
} else {
	wizard = "";
}

if (! wizard.equals("")) {
	java.util.Date mytime = new java.util.Date();
	String fiveminsago = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(mytime.getTime()-5*60*1000));
	String database_import_last_modified = Common.fileLastModified(DOCUMENT_ROOT + "/" + mytext.display("adminpath") + "/database/importing.html");
	boolean database_import_running = (! database_import_last_modified.equals("")) && (database_import_last_modified.compareTo(fiveminsago)>0);
	if (database_import_running) {
		wizard = "importing";
	}
} else if (! myrequest.getParameter("quickstart").equals("")) {
	wizard = myrequest.getParameter("quickstart");
}
if (! wizard.equals("")) {
	mysession.set("administrator", "");
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

if (myconfig.get(db, "use_publish").equals("")) {
	myconfig.setTemp("use_publish", "auto-on-save");
}

String doscheduled_timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
if ((myconfig.get(db, "scheduled_next").compareTo(doscheduled_timestamp) <= 0) && (! myconfig.get(db, "scheduled_next").equals(""))) {
	UCpublishScheduled publishScheduled = new UCpublishScheduled(mytext);
	publishScheduled.doScheduled(servletcontext, mysession, myrequest, myresponse, myconfig, db);
}

%><%@ include file="module/config.jsp" %>