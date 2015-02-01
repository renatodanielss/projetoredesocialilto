package HardCore;

import java.io.*;
import java.io.File;
import java.util.*;
import java.util.HashMap;
import java.util.regex.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HostingAPI {

//	Asbru Web Content Management system Hosting API
//	(C) 1999-2012 - Asbru Ltd. - www.asbrusoft.com

//	The Asbru Web Content Management system Hosting Edition hosting clients administration 
//	section gives access to configure client website domain names, databases and directories.

//	However, it does not create and initialize the client website databases and directories, as
//	such server setup is very hosting service provider specific.

//	This hosting API provides a simple way to implement automated client database creation and
//	initialization etc. so that all client setup can be done exclusive through the hosting clients
//	administration. The hosting API could also be used for integration with domain name
//	registration and configuration and billing/accounting systems etc.

//	As default the hosting API includes support for Microsoft Access and MySQL databases.

//	The hosting API steps are:
//	1) An administrator submits a create/update/delete request for a client website
//	2) The "hosting_pre_" create/update/delete hosting API function is called
//	   (to check any restrictions and create/initialize a new database/directory etc.)
//	3) A success or failure response is returned by the hosting API function
//	4a) If the response is "failure" the response is displayed to the administrator
//	   (and the requested client website create/update/delete is not applied)
//	4b) If the response is "success" the requested action is applied.
//	5) The "hosting_post_" create/update/delete hosting API function is called
//	   (to clean up and send confirmation/notification to the client/administrator/billing etc.)
//	6) A success or failure response is returned by the hosting API function
//	7a) If the response is "failure" the response is displayed to the administrator
//	   (with special instructions/warnings to the administrator etc.)
//	7b) If the response is "success" the administrator is returned to the hosting clients index.

//	The basic client website configuration details are passed as parameters to the functions.
//	Other parameters can be read from the html form/http request, inifile and client databases.
//	A simple error string response is returned from the functions.
//	An empty string indicates success. Any non-empty string indicates failure.



// Only do any work if called via this domain name (if restricted)
String hosting_api_domainname = "";

// Root path for where the Asbru Web Content Management system is installed
String hosting_api_rootpath = "";

// Root path for where the databases are located on the server
String hosting_api_database_rootpath = "";

// Username and password for database superadmin access (if Used)
String hosting_api_database_username = "";
String hosting_api_database_password = "";

// Details for email notification on successful client website creation
String hosting_api_email_sender = "sales@hardcoreinternet.co.uk";
String hosting_api_email_subject = "HardCore Web Content Management Website Details";
// The actual email message is "/hardcore.email" under hosting_api_rootpath / adminpath
// or "/hardcore.XXXXX.email" where XXXXX is the id of the copied client website



	ServletContext servletcontext = null;
	Session mysession = null;
	Request myrequest = null;
	String inifile = null;
	String original_database = null;
	String original_database_name = null;
	DB db = null;
	Configuration myconfig = null;
	Text text = null;



	public HostingAPI(Text mytext, ServletContext myservletcontext, Session session, Request request, String myinifile, DB mydb, String my_original_database, Configuration config) {
		if (mytext != null) text = mytext;
		servletcontext = myservletcontext;
		mysession = session;
		myrequest = request;
		inifile = myinifile;
		db = mydb;
		myconfig = config;

		original_database = my_original_database;
		Pattern p = Pattern.compile(".*Microsoft Access Driver.*DBQ=(.+)$");
		Matcher m = p.matcher(original_database);
		if (m.find()) {
			original_database_name = m.group(1);
		} else {
			original_database_name = "";
		}
		hosting_api_rootpath = Common.getRealPath(servletcontext, "/");
		hosting_api_database_rootpath = myconfig.get(db, "hosting_api_database_rootpath");
		hosting_api_database_username = myconfig.get(db, "hosting_api_database_username");
		hosting_api_database_password = myconfig.get(db, "hosting_api_database_password");
	}



	public String hosting_availability(String client_address, String client_database, String client_URLrootpath) {
		String error = "";
		Pattern p;
		Matcher m;

		Hosting hosting = new Hosting(text);
		if ((hosting_api_domainname.equals("")) || (myrequest.getServerName().equals(hosting_api_domainname))) {
			// Get the details for the client which is being copied (if any)
			String copy_address = "";
			String copy_database = "";
			String copy_URLrootpath = "";
			String id = mysession.get("hosting_api_id");
			if (id.equals("")) {
				id = html.encodeHtmlEntities(myrequest.getParameter("id"));
			}
			if (! id.equals("")) {
				// The new client website is to be copied from an existing client website
				copy_address = id;
				hosting = new Hosting(text);
				hosting.readClientAddress(db, copy_address);
				copy_database = hosting.getClientDatabase();
				copy_URLrootpath = hosting.getClientURLrootpath();
			}

			// EXPERIMENTAL: automatic conversion of different programming language format database connection strings
			copy_database = DB.translatedconnectionstring(copy_database);
			client_database = DB.translatedconnectionstring(client_database);

			// Validate format of database to be copied and split into parts
			String copy_database_username = "";
			String copy_database_password = "";
			String copy_database_name = "";
			if (DB.db_type(copy_database).equals("mysql")) {
				p = Pattern.compile("mysql:com\\.mysql\\.jdbc\\.Driver:(.+):(.+)@jdbc:mysql://localhost/(.+)");
				m = p.matcher(copy_database);
				if (m.find()) {
					copy_database_username = m.group(1);
					copy_database_password = m.group(2);
					copy_database_name = m.group(3);
				}
			} else if (DB.db_type(copy_database).equals("access")) {
				p = Pattern.compile(".*Microsoft Access Driver.*DBQ=(.+)$");
				m = p.matcher(copy_database);
				if (m.find()) {
					copy_database_username = "";
					copy_database_password = "";
					copy_database_name = m.group(1);
				}
			}

			// Validate format of database to be created and split into parts
			String client_database_username = "";
			String client_database_password = "";
			String client_database_name = "";
			if (DB.db_type(client_database).equals("mysql")) {
				p = Pattern.compile("mysql:com\\.mysql\\.jdbc\\.Driver:(.+):(.+)@jdbc:mysql://localhost/(.+)");
				m = p.matcher(client_database);
				if (m.find()) {
					client_database_username = m.group(1);
					client_database_password = m.group(2);
					client_database_name = m.group(3);
				}
			} else if (DB.db_type(client_database).equals("access")) {
				p = Pattern.compile(".*Microsoft Access Driver.*DBQ=(.+)$");
				m = p.matcher(client_database);
				if (m.find()) {
					client_database_username = "";
					client_database_password = "";
					client_database_name = m.group(1);
				}
			}

			// Validate format of URLrootpath to be created
			if (! client_URLrootpath.equals("/")) {
				p = Pattern.compile("^\\/[-a-zA-Z0-9]+\\/$");
				m = p.matcher(client_URLrootpath);
				if (! m.find()) {
					error = "" + error + "Invalid client URLrootpath: " + client_URLrootpath + ". ";
				}
			}

			// Validate format of address to be created
			p = Pattern.compile("^[-a-zA-Z0-9\\.]+$");
			m = p.matcher(client_address);
			if (! m.find()) {
				error = "" + error + "Invalid client address: " + client_address + ". ";
			}



			// Check if the client address is already used by the web content management system
			// Even if it is not it may still be used by the web server etc. but this is not checked
			hosting = new Hosting(text);
			hosting.readClientAddress(db, client_address);
			boolean client_address_already_in_use;
			if (hosting.getClientAddress().equals(client_address)) {
				client_address_already_in_use = true;
			} else {
				client_address_already_in_use = false;
			}

			// Check if the client URLrootpath is already in use
			boolean client_URLrootpath_exists = new File(Common.getRealPath(servletcontext, client_URLrootpath)).exists();

			// Check if the client database name is already in use
			boolean client_database_exists;
			if (! client_database_name.equals("")) {
				if (DB.db_type(client_database).equals("mysql")) {
					client_database_exists = new File(hosting_api_database_rootpath + client_database_name).exists();
				} else if (DB.db_type(client_database).equals("access")) {
					client_database_exists = new File(client_database_name).exists();
				} else {
					client_database_exists = false;
				}
			} else {
				client_database_exists = false;
			}

			// Check if the copy database name is already in use
			boolean copy_database_exists;
			if (! copy_database_name.equals("")) {
				if (DB.db_type(copy_database).equals("mysql")) {
					copy_database_exists = new File(hosting_api_database_rootpath + copy_database_name).exists();
				} else if (DB.db_type(copy_database).equals("access")) {
					copy_database_exists = new File(copy_database_name).exists();
				} else {
					copy_database_exists = false;
				}
			} else {
				copy_database_exists = false;
			}

			if (client_address_already_in_use) {
				error = "" + error + "The client address " + client_address + " is already in use. ";
			} else if (client_database_exists && client_URLrootpath_exists) {
				// Both client database and URLrootpath exist - assume this is simply to be a new alias name
			} else if (client_database_exists) {
				error = "" + error + "The client database " + client_database + " is already in use. ";
			} else if (client_URLrootpath_exists) {
				error = "" + error + "The client URLrootpath " + client_URLrootpath + " is already in use. ";
			}
		}

		return error;
	}



	public String hosting_pre_create(String client_address, String client_database, String client_URLrootpath, String client_superadmin, String client_superadmin_password, String client_superadmin_email) {
		String error = "";
		Pattern p;
		Matcher m;

		Hosting hosting = new Hosting(text);
		if ((hosting_api_domainname.equals("")) || (myrequest.getServerName().equals(hosting_api_domainname))) {
			// Get the details for the client which is being copied (if any)
			String copy_address = "";
			String copy_database = "";
			String copy_URLrootpath = "";
			String id = mysession.get("hosting_api_id");
			if (id.equals("")) {
				id = html.encodeHtmlEntities(myrequest.getParameter("id"));
			}
			if (! id.equals("")) {
				// The new client website is to be copied from an existing client website
				copy_address = id;
				hosting = new Hosting(text);
				hosting.readClientAddress(db, copy_address);
				copy_database = hosting.getClientDatabase();
				copy_URLrootpath = hosting.getClientURLrootpath();
			}

			// EXPERIMENTAL: automatic conversion of different programming language format database connection strings
			copy_database = DB.translatedconnectionstring(copy_database);
			client_database = DB.translatedconnectionstring(client_database);

			// Validate format of database to be copied and split into parts
			String copy_database_username = "";
			String copy_database_password = "";
			String copy_database_name = "";
			if (DB.db_type(copy_database).equals("mysql")) {
				p = Pattern.compile("mysql:com\\.mysql\\.jdbc\\.Driver:(.+):(.+)@jdbc:mysql://localhost/(.+)");
				m = p.matcher(copy_database);
				if (m.find()) {
					copy_database_username = m.group(1);
					copy_database_password = m.group(2);
					copy_database_name = m.group(3);
				}
			} else if (DB.db_type(copy_database).equals("access")) {
				p = Pattern.compile(".*Microsoft Access Driver.*DBQ=(.+)$");
				m = p.matcher(copy_database);
				if (m.find()) {
					copy_database_username = "";
					copy_database_password = "";
					copy_database_name = m.group(1);
				}
			}

			// Validate format of database to be created and split into parts
			String client_database_username = "";
			String client_database_password = "";
			String client_database_name = "";
			if (DB.db_type(client_database).equals("mysql")) {
				p = Pattern.compile("mysql:com\\.mysql\\.jdbc\\.Driver:(.+):(.+)@jdbc:mysql://localhost/(.+)");
				m = p.matcher(client_database);
				if (m.find()) {
					client_database_username = m.group(1);
					client_database_password = m.group(2);
					client_database_name = m.group(3);
				}
			} else if (DB.db_type(client_database).equals("access")) {
				p = Pattern.compile(".*Microsoft Access Driver.*DBQ=(.+)$");
				m = p.matcher(client_database);
				if (m.find()) {
					client_database_username = "";
					client_database_password = "";
					client_database_name = m.group(1);
				}
			}

			// Validate format of URLrootpath to be created
			if (! client_URLrootpath.equals("/")) {
				p = Pattern.compile("^\\/[-a-zA-Z0-9]+\\/$");
				m = p.matcher(client_URLrootpath);
				if (! m.find()) {
					error = "" + error + "Invalid client URLrootpath: " + client_URLrootpath + ". ";
				}
			}

			// Validate format of address to be created
			p = Pattern.compile("^[-a-zA-Z0-9\\.]+$");
			m = p.matcher(client_address);
			if (! m.find()) {
				error = "" + error + "Invalid client address: " + client_address + ". ";
			}



			// Check if the client address is already used by the web content management system
			// Even if it is not it may still be used by the web server etc. but this is not checked
			hosting = new Hosting(text);
			hosting.readClientAddress(db, client_address);
			boolean client_address_already_in_use;
			if (hosting.getClientAddress().equals(client_address)) {
				client_address_already_in_use = true;
			} else {
				client_address_already_in_use = false;
			}

			// Check if the client URLrootpath is already in use
			boolean client_URLrootpath_exists = new File(Common.getRealPath(servletcontext, client_URLrootpath)).exists();

			// Check if the client database name is already in use
			boolean client_database_exists;
			if (! client_database_name.equals("")) {
				if (DB.db_type(client_database).equals("mysql")) {
					client_database_exists = new File(hosting_api_database_rootpath + client_database_name).exists();
				} else if (DB.db_type(client_database).equals("access")) {
					client_database_exists = new File(client_database_name).exists();
				} else {
					client_database_exists = false;
				}
			} else {
				client_database_exists = false;
			}

			// Check if the copy database name is already in use
			boolean copy_database_exists;
			if (! copy_database_name.equals("")) {
				if (DB.db_type(copy_database).equals("mysql")) {
					copy_database_exists = new File(hosting_api_database_rootpath + copy_database_name).exists();
				} else if (DB.db_type(copy_database).equals("access")) {
					copy_database_exists = new File(copy_database_name).exists();
				} else {
					copy_database_exists = false;
				}
			} else {
				copy_database_exists = false;
			}



			if (client_address_already_in_use) {
				error = "" + error + "The client address " + client_address + " is already in use. ";
			} else if (client_database_exists && client_URLrootpath_exists) {
				// Both client database and URLrootpath exist - assume this is simply to be a new alias name
			} else if (client_database_exists) {
				error = "" + error + "The client database " + client_database + " is already in use. ";
			} else if (client_URLrootpath_exists) {
				error = "" + error + "The client URLrootpath " + client_URLrootpath + " is already in use. ";
			} else if (error.equals("")) {
				if (! client_URLrootpath.equals("/")) {
					// Create directories under the client URLrootpath
					new File(Common.getRealPath(servletcontext, client_URLrootpath)).mkdirs();
					new File(Common.getRealPath(servletcontext, client_URLrootpath + "file")).mkdirs();
					new File(Common.getRealPath(servletcontext, client_URLrootpath + "image")).mkdirs();
					new File(Common.getRealPath(servletcontext, client_URLrootpath + "upload")).mkdirs();

					// Copy the default client content files
					if (! copy_URLrootpath.equals("")) {
						Common.copyFolder(Common.getRealPath(servletcontext, copy_URLrootpath + "file"), Common.getRealPath(servletcontext, client_URLrootpath + "file"));
						Common.copyFolder(Common.getRealPath(servletcontext, copy_URLrootpath + "image"), Common.getRealPath(servletcontext, client_URLrootpath + "image"));
						Common.copyFolder(Common.getRealPath(servletcontext, copy_URLrootpath + "upload"), Common.getRealPath(servletcontext, client_URLrootpath + "upload"));
					} else {
						Common.copyFolder(Common.getRealPath(servletcontext, "/file.original"), Common.getRealPath(servletcontext, client_URLrootpath + "file"));
						Common.copyFolder(Common.getRealPath(servletcontext, "/image.original"), Common.getRealPath(servletcontext, client_URLrootpath + "image"));
						Common.copyFolder(Common.getRealPath(servletcontext, "/upload.original"), Common.getRealPath(servletcontext, client_URLrootpath + "upload"));
					}
				} else {
					// Client website has own website root folder
					// NOT AUTOMATED
				}

				if (! client_database_name.equals("")) {
					if (DB.db_type(client_database).equals("mysql")) {
						// Create the client database
						ExecCommand execCommand = new ExecCommand("mysqladmin -u " + hosting_api_database_username + " -p" + hosting_api_database_password + " create " + client_database_name, null);

						// Create the client database username and password
						execCommand = new ExecCommand("mysql -u " + hosting_api_database_username + " -p" + hosting_api_database_password + " " + client_database_name + " -e \"grant all on " + client_database_name + ".* to '" + client_database_username + "'@'localhost' identified by '" + client_database_password + "'\"", null);

						// Initialise the default client database content
						if ((DB.db_type(copy_database).equals("mysql")) && (copy_database_exists)) {
							// Connect to the client database
							// - database connection is not required to initialise MySQL database using mysqldump

							execCommand = new ExecCommand("mysqldump -a -c -u " + hosting_api_database_username + " -p" + hosting_api_database_password + " " + copy_database_name + " | mysql -u " + hosting_api_database_username + " -p" + hosting_api_database_password + " " + client_database_name, null);
						} else {
							// Connect to the client database
							DB client_db = new DB(text);
							client_db.connect(DB.DSN(client_database), client_database);
							if (client_db.isError()) {
								client_db = null;
							}

							if (client_db != null) {
							        client_db.execute_sql(null, "/" + text.display("adminpath") + "/database/create." + DB.db_type(client_database) + ".sql", "", "", "");
							}
						}
					} else if (DB.db_type(client_database).equals("access")) {
						// Copy the client database
						if ((DB.db_type(copy_database).equals("access")) && (copy_database_exists)) {
							Common.copyFile(copy_database_name, client_database_name);
						} else {
							Common.copyFile(original_database_name, client_database_name);
						}
					}
				}
			}
		}

		return error;
	}

	public String hosting_post_create(String client_address, String client_database, String client_URLrootpath, String client_superadmin, String client_superadmin_password, String client_superadmin_email) {
		String error = "";

		if ((hosting_api_domainname.equals("")) || (myrequest.getServerName().equals(hosting_api_domainname))) {
			// Validate the database connection
			DB testdb = new DB(text);
			testdb.connect(DB.DSN(client_database), client_database);
			if (testdb.isError()) {
				error = "Database connection failed: " + client_database + " - " + testdb.getWarnings();
			}

			// Get the details for the client which is being copied (if any)
			String copy_address;
			if (! myrequest.getParameter("id").equals("")) {
				copy_address = html.encodeHtmlEntities(myrequest.getParameter("id"));
			} else {
				copy_address = "";
			}
		}

		return error;
	}
	
	
	
	public String hosting_pre_update(String client_address, String client_database, String client_URLrootpath, String client_superadmin, String client_superadmin_password, String client_superadmin_email) {
		String error = "";
		Pattern p;
		Matcher m;

		if ((hosting_api_domainname.equals("")) || (myrequest.getServerName().equals(hosting_api_domainname))) {
			// Validate format of URLrootpath to be updated
			if (! client_URLrootpath.equals("/")) {
				p = Pattern.compile("^\\/[-a-zA-Z0-9]+\\/$");
				m = p.matcher(client_URLrootpath);
				if (! m.find()) {
					error = "" + error + "Invalid client URLrootpath: " + client_URLrootpath + ". ";
				}
			}

			// Validate format of address to be updated
			p = Pattern.compile("^[-a-zA-Z0-9\\.]+$");
			m = p.matcher(client_address);
			if (! m.find()) {
				error = "" + error + "Invalid client address: " + client_address + ". ";
			}
		}

		return error;
	}

	public String hosting_post_update(String client_address, String client_database, String client_URLrootpath, String client_superadmin, String client_superadmin_password, String client_superadmin_email) {
		String error = "";

		if ((hosting_api_domainname.equals("")) || (myrequest.getServerName().equals(hosting_api_domainname))) {
			// Validate the database connection
			DB testdb = new DB(text);
			testdb.connect(DB.DSN(client_database), client_database);
			if (testdb.isError()) {
				error = "Database connection failed: " + client_database + " - " + testdb.getWarnings();
			}
		}

		return error;
	}
	
	
	
	public String hosting_pre_delete(String client_address, String client_database, String client_URLrootpath, String client_superadmin, String client_superadmin_password, String client_superadmin_email) {
		String error = "";
		Pattern p;
		Matcher m;

		if ((hosting_api_domainname.equals("")) || (myrequest.getServerName().equals(hosting_api_domainname))) {
			// Validate format of URLrootpath to be deleted
			if (! client_URLrootpath.equals("/")) {
				p = Pattern.compile("^\\/[-a-zA-Z0-9]+\\/$");
				m = p.matcher(client_URLrootpath);
				if (! m.find()) {
					error = "" + error + "Invalid client URLrootpath: " + client_URLrootpath + ". ";
				}
			}

			// Validate format of address to be deleted
			p = Pattern.compile("^[-a-zA-Z0-9\\.]+$");
			m = p.matcher(client_address);
			if (! m.find()) {
				error = "" + error + "Invalid client address: " + client_address + ". ";
			}
		}

		return error;
	}

	public String hosting_post_delete(String client_address, String client_database, String client_URLrootpath, String client_superadmin, String client_superadmin_password, String client_superadmin_email) throws Exception {
		String error = "";
		Pattern p;
		Matcher m;

		if ((hosting_api_domainname.equals("")) || (myrequest.getServerName().equals(hosting_api_domainname))) {
			// Validate format of database and split into parts
			String client_database_username = "";
			String client_database_password = "";
			String client_database_name = "";
			if (DB.db_type(client_database).equals("mysql")) {
//				p = Pattern.compile("mysql:com\\.mysql\\.jdbc\\.Driver:(.+):(.+)@jdbc:mysql://localhost/(.+)");
//				p = Pattern.compile("mysql:mysql://(.+):(.+)@localhost/(.+)");
				p = Pattern.compile("mysql:.+[:/](.+):(.+)@.*localhost/(.+)");
				m = p.matcher(client_database);
				if (m.find()) {
					client_database_username = m.group(1);
					client_database_password = m.group(2);
					client_database_name = m.group(3);
				}
			} else if (DB.db_type(client_database).equals("access")) {
				p = Pattern.compile(".*Microsoft Access Driver.*DBQ=(.+)$");
				m = p.matcher(client_database);
				if (m.find()) {
					client_database_username = "";
					client_database_password = "";
					client_database_name = m.group(1);
				}
			}

			// Check if the client database is still used by another client website
			Hosting hosting = new Hosting(text);
			boolean client_database_still_in_use = false;
			hosting.readClientDatabase(db, client_database);
			if (! hosting.getId().equals("")) {
				client_database_still_in_use = true;
			} else {
				client_database_still_in_use = false;
			}

			// Check if the client URLrootpath is still used by another client website
			boolean client_URLrootpath_still_in_use = false;
			hosting.readClientURLrootpath(db, client_URLrootpath);
			if (! hosting.getId().equals("")) {
				client_URLrootpath_still_in_use = true;
			} else {
				client_URLrootpath_still_in_use = false;
			}

			// Check if the client database name exists
			boolean client_database_exists;
			if (! client_database_name.equals("")) {
				if (DB.db_type(client_database).equals("mysql")) {
					client_database_exists = new File(hosting_api_database_rootpath + client_database_name).exists();
				} else if (DB.db_type(client_database).equals("access")) {
					client_database_exists = new File(client_database_name).exists();
				} else {
					client_database_exists = false;
				}
			} else {
				client_database_exists = false;
			}
	
			// Check if the client URLrootpath exists
			boolean client_URLrootpath_exists = new File(Common.getRealPath(servletcontext, client_URLrootpath)).exists();

			if (error.equals("")) {
				if ((client_database_exists) && (! client_database_still_in_use)) {
					if (DB.db_type(client_database).equals("mysql")) {
						// Close the client database
						// - no need to close MySQL database
	
						// Delete the client database
						ExecCommand execCommand = new ExecCommand("mysqladmin -u " + hosting_api_database_username + " -p" + hosting_api_database_password + " -f drop " + client_database_name, null);
	
						// Check if the client database name still exists
						client_database_exists = new File(hosting_api_database_rootpath + client_database_name).exists();
						if (client_database_exists) {
							error = "" + error + "Could not delete client database (please delete manually): " + client_database_name + ". ";
						}
					} else if (DB.db_type(client_database).equals("access")) {
						// Close the client database
						// - no need to close Microsoft Access database

						// Delete the client database
						Common.deleteFile(client_database_name);

						// Check if the client database name still exists
						client_database_exists = new File(client_database_name).exists();
						if (client_database_exists) {
							error = "" + error + "Could not delete client database (please delete manually): " + client_database_name + ". ";
						}
					}
				}

				if ((! client_URLrootpath.equals("")) && (! client_URLrootpath.equals("/")) && (client_URLrootpath_exists) && (! client_URLrootpath_still_in_use)) {
					// Delete the client URLrootpath folders and files
					Common.deleteFolder(Common.getRealPath(servletcontext, client_URLrootpath));

					// Check if the client URLrootpath still exists
					client_URLrootpath_exists = new File(Common.getRealPath(servletcontext, client_URLrootpath)).exists();
					if (client_URLrootpath_exists) {
						error = "" + error + "Could not delete client folder (please delete manually): " + Common.getRealPath(servletcontext, client_URLrootpath) + ". ";
					}
				}
			}
		}

		return error;
	}



}
