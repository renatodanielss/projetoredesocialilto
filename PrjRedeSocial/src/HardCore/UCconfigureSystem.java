package HardCore;

import java.io.*;
import java.io.File;
import java.text.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.regex.*;
import javax.servlet.*;
import javax.servlet.jsp.*;

public class UCconfigureSystem {
	private String error = "";
	private String database = "";
	private String current_database = "";
	private String dummy_database2 = "";
	private String dummy_database3 = "";
	private Configuration config = new Configuration();
	private User user = null;
	private Page page = null;
	private Currency currencies = new Currency();
	private LinkedHashMap websitesettings = new LinkedHashMap();
	private Fileupload filepost = null;
	private Text text = new Text();



	public UCconfigureSystem() {
	}



	public UCconfigureSystem(Text mytext) {
		if (mytext != null) text = mytext;
		user = new User(text);
		page = new Page(text);
	}



	public void doConfigure(String ini_database, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		if (db == null) return;
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		setConfigFromRequest(myrequest, myconfig, db);
		doLicenseRestrictions(db, myconfig);
		mysession.set("username", myconfig.get(db, "superadmin"));
		mysession.set("password", myconfig.get(db, "superadmin_password"));
		mysession.set("administrator", "administrator");
		mysession.set("email", myconfig.get(db, "superadmin_email"));
		mysession.set("version", "");
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		if (ini_database.equals("default")) {
			if (myrequest.getMethod().equals("POST")) {
				if (myrequest.parameterExists("superadmin")) {
					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", ini_database, "superadmin", myconfig.get(db, "superadmin"));
				} else if (myrequest.parameterExists("default_version")) {
					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", ini_database, "default_page", myconfig.get(db, "default_page"));
					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", ini_database, "default_template", myconfig.get(db, "default_template"));
					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", ini_database, "default_stylesheet", myconfig.get(db, "default_stylesheet"));
					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", ini_database, "default_version", myconfig.get(db, "default_version"));
				}
			}
		}
		websitesettings = db.query_records("select * from config where configname like 'config_%' order by configname");
	}



	public String doConfigureLicenses(String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		if (db == null) return "";
		String error = "";
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
		setConfigFromRequest(myrequest, myconfig, db);
		if ((! myconfig.get(db, "personal_license").equals("")) && (! License.valid(db, myconfig, "personal"))) {
			error += text.display("error.config.licenses.invalid.personal") + " ";
		}
		if ((! myconfig.get(db, "professional_license").equals("")) && (! License.valid(db, myconfig, "professional"))) {
			error += text.display("error.config.licenses.invalid.professional") + " ";
		}
		if ((! myconfig.get(db, "enterprise_license").equals("")) && (! License.valid(db, myconfig, "enterprise"))) {
			error += text.display("error.config.licenses.invalid.enterprise") + " ";
		}
		if ((! myconfig.get(db, "hosting_license").equals("")) && (! License.valid(db, myconfig, "hosting"))) {
			error += text.display("error.config.licenses.invalid.hosting") + " ";
		}
		if ((! myconfig.get(db, "ecommerce_license").equals("")) && (! License.valid(db, myconfig, "ecommerce"))) {
			error += text.display("error.config.licenses.invalid.ecommerce") + " ";
		}
		if ((! myconfig.get(db, "community_license").equals("")) && (! License.valid(db, myconfig, "community"))) {
			error += text.display("error.config.licenses.invalid.community") + " ";
		}
		if ((! myconfig.get(db, "databases_license").equals("")) && (! License.valid(db, myconfig, "databases"))) {
			error += text.display("error.config.licenses.invalid.databases") + " ";
		}
		if ((! myconfig.get(db, "statistics_license").equals("")) && (! License.valid(db, myconfig, "statistics"))) {
			error += text.display("error.config.licenses.invalid.statistics") + " ";
		}
		if ((! myconfig.get(db, "experience_license").equals("")) && (! License.valid(db, myconfig, "experience"))) {
			error += text.display("error.config.licenses.invalid.experience") + " ";
		}
		doLicenseRestrictions(db, myconfig);
		return error;
	}



	public void doLicenseRestrictions(DB db, Configuration myconfig) {
		HashMap<String,String> feature = new HashMap<String,String>();
		if (! License.valid(db, myconfig, "hosting")) {
		}
		if (! License.valid(db, myconfig, "enterprise")) {
		}
		if (! License.valid(db, myconfig, "professional")) {
			feature.put("content_editor", "HardCore");
			feature.put("file_upload", "");
			feature.put("use_publish", "auto-on-save");
			feature.put("use_scheduled_publish", "");
			feature.put("use_archive", "none");
			feature.put("use_checkin", "none");
			feature.put("use_checkout", "none");
//			feature.put("use_contentdefinition", "");
//			feature.put("use_contentclasses", "");
//			feature.put("use_contenttypes", "");
//			feature.put("use_contentgroups", "");
			feature.put("use_imagetypes", "");
			feature.put("use_imagegroups", "");
			feature.put("use_filetypes", "");
			feature.put("use_filegroups", "");
			feature.put("use_linktypes", "");
			feature.put("use_linkgroups", "");
			feature.put("use_producttypes", "");
			feature.put("use_productgroups", "");
			feature.put("activedit25_upload", "");
			feature.put("activedit25_license", "");
			feature.put("ewebeditpro_upload", "");
			feature.put("ewebeditpro_license", "");
			feature.put("ewebeditpro2_upload", "");
			feature.put("ewebeditpro2_license", "");
			feature.put("use_revisionhistory", "");
			feature.put("use_userdefinition", "");
			feature.put("use_userclasses", "");
			feature.put("use_usertypes", "");
			feature.put("use_usergroups", "");
			feature.put("use_versions", "");
			feature.put("use_contentrelations", "");
			feature.put("use_workflow", "");
//			if (! myconfig.get(db, "use_accessrestrictions").equals("users")) {
//				feature.put("use_accessrestrictions", "none");
//			}
		}
		if (! License.valid(db, myconfig, "personal")) {
			feature.put("use_additionalcontent", "");
			feature.put("use_advancedscripting", "");
			feature.put("use_metainformation", "");
			feature.put("use_presentation", "");
			feature.put("use_contentdefinition", "");
			feature.put("use_contentclasses", "");
			feature.put("use_contenttypes", "");
			feature.put("use_contentgroups", "");
			feature.put("use_userdatabase", "");
		}
		Iterator names = feature.keySet().iterator();
		while (names.hasNext()) {
			String name = (String) names.next();
			myconfig.set(db, name, "" + feature.get(name));
		}
	}



	public DB doDatabase(JspWriter out, ServletContext server, String old_database, String old_current_database, String dummy_database, String original_database, String ini_database, String inifile, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Text mytext) throws Exception {
//		if (db == null) return null;
		config = myconfig;
		boolean accesspermission = true;
		if (db != null) {
			accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		}
		if (! accesspermission) return null;
		database = "" + old_database;
		current_database = "" + old_current_database;

		dummy_database2 = "access:sun.jdbc.odbc.JdbcOdbcDriver::@jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb)}; " + "DBQ=";
		String[] dummy_database2_parts = Common.getRealPath(server, "/").split("\\\\");
		for (int j=0; j<dummy_database2_parts.length-1; j++) {
			dummy_database2 = "" + dummy_database2 + dummy_database2_parts[j] + "\\";
		}
		dummy_database2 = "" + dummy_database2 + "database.mdb";

		int temp_len = database.length()-36;
		if (temp_len < 0) {
			temp_len = 0;
		}
		if ((database.length() == dummy_database.length()+33) && ( database.substring(0, temp_len) == dummy_database.substring(0, dummy_database.length()-3) )) {
			dummy_database3 = "" + database;
		} else {
			dummy_database3 = "access:sun.jdbc.odbc.JdbcOdbcDriver::@jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb)}; " + "DBQ=";
			String[] dummy_database3_parts = Common.getRealPath(server, "/" + text.display("adminpath") + "/").split("\\\\");
			for (int j=0; j<dummy_database3_parts.length; j++) {
				dummy_database3 = "" + dummy_database3 + dummy_database3_parts[j] + "\\";
			}
			dummy_database3 = "" + dummy_database3 + "database.";
			
			dummy_database3 = "" + dummy_database3 + ".mdb";
		}

		filepost = new Fileupload(myrequest, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath"), myconfig.get(db, "URLuploadpath"));

		if (myrequest.getMethod().equals("POST")) {
			error = "";

			// CHECK/COPY DEFAULT MICROSOFT ACCESS DATABASE FILE
			temp_len = filepost.getParameter("database").length()-36;
			if (temp_len < 0) {
				temp_len = 0;
			}
			if ((filepost.getParameter("database").equals(dummy_database) && (! dummy_database.equals(""))) || (filepost.getParameter("database").equals(dummy_database2) && (! dummy_database2.equals(""))) || ((dummy_database.length()>3) && filepost.getParameter("database").substring(0, temp_len).equals(dummy_database.substring(0, dummy_database.length()-3)))) {
				File fh = new File(filepost.getParameter("database").substring(94));
				File fh2 = new File(original_database.substring(94));
				if ((! fh.exists()) && (fh2.exists())) {
					BufferedInputStream is = null;
					BufferedOutputStream os = null;
					try {
						is = new BufferedInputStream(new FileInputStream(original_database.substring(94)));
						os = new BufferedOutputStream(new FileOutputStream(filepost.getParameter("database").substring(94)));
						int b;
						while ((b = is.read()) != -1) os.write(b);
						is.close();
						os.close();
					} catch (FileNotFoundException e) {
						error = text.display("error.config.database.create");
						if (is != null) try { is.close(); } catch (IOException ee) { ; }
						if (os != null) try { os.close(); } catch (IOException ee) { ; }
					} catch (IOException e) {
						error = text.display("error.config.database.create");
						if (is != null) try { is.close(); } catch (IOException ee) { ; }
						if (os != null) try { os.close(); } catch (IOException ee) { ; }
					}
				}
			}

			// CHECK DATABASE CONNECTION
			if (error.equals("") && (! filepost.getParameter("database").equals(""))) {
				if (ini_database.equals("default")) {
					database = "" + filepost.getParameter("database");
					current_database = "" + filepost.getParameter("database");
					db = new DB(text);
					db.connect(DB.DSN(database), database);
					if (db.isError()) {
						error = text.display("error.config.database.connection");
						error = error + "<br>" + db.getMessage() + " " + db.getDebugInfo();
						database = "" + dummy_database;
						db.connect(DB.DSN(database), database);
					}
					if (db.isError()) {
						db = null;
					}
				}
			} else if (error.equals("")) {
				if (db.isError()) {
					error = text.display("error.config.database.connection");
					error += "<br>" + db.getMessage() + " " + db.getDebugInfo();
				}
			}

			// SAVE SETTINGS
			Cache.clear(db);
//			config = new Configuration();
			String save_superadmin = config.get(db, "superadmin");
			String save_superadmin_password = config.get(db, "superadmin_password");
			String save_superadmin_email = config.get(db, "superadmin_email");
			String save_personal_license = config.get(db, "personal_license");
			String save_professional_license = config.get(db, "professional_license");
			String save_enterprise_license = config.get(db, "enterprise_license");
			String save_hosting_license = config.get(db, "hosting_license");
			String save_ecommerce_license = config.get(db, "ecommerce_license");
			String save_community_license = config.get(db, "community_license");
			String save_databases_license = config.get(db, "databases_license");
			String save_statistics_license = config.get(db, "statistics_license");
			String save_experience_license = config.get(db, "experience_license");
			String save_contact_form_recipient = config.get(db, "contact_form_recipient");
			String save_contact_form_recipients = config.get(db, "contact_form_recipients");
			String save_contact_form_mailserver = config.get(db, "contact_form_mailserver");
			String save_order_form_recipient = config.get(db, "order_form_recipient");
			boolean append_config_settings = true;
			String save_print_contents = config.get(db, "print_contents");
			String save_print_products = config.get(db, "print_products");
			String save_print_orders = config.get(db, "print_orders");
			String save_print_users = config.get(db, "print_users");
			boolean save_is_superadmin = false;
			if ((config.get(db, "superadmin").equals("")) && (config.get(db, "superadmin_password").equals(""))) {
				save_is_superadmin = true;
			} else if ((mysession.get("username").equals(config.get(db, "superadmin"))) && (mysession.get("password").equals(config.get(db, "superadmin_password")))) {
				save_is_superadmin = true;
			}

			// INITIALISE DATABASE
			if (error.equals("")) {
				if (db != null) {
					if ((! config.get(db, "database_init").equals("")) && (filepost.getParameter("create").equals("yes"))) {
						String[] mycommands = config.get(db, "database_init").split(";");
						for (int i=0; i<mycommands.length; i++) {
							String SQL = "" + mycommands[i];
							if (SQL.startsWith("#")) {
								if (out != null) out.println("<p><b>" + SQL.substring(1) + "</b></p>" + "\r\n");
								if (out != null) out.flush();
							} else if (SQL.startsWith("!")) {
								SQL = SQL.substring(1);
								db.execute(SQL);
							} else {
								db.execute(out, SQL);
							}
						}
					}
					if (filepost.getParameter("drop").equals("yes")) {
						append_config_settings = false;
						Databases databases = new Databases(text);
						String SQL = "select * from data order by id";
						databases.records("", "", "", "", "", "", "", db, myconfig, SQL);
						while(databases.records("", "", "", "", "", "", "", db, myconfig, "")) {
							db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/database.delete." + DB.db_type(database) + ".sql", "data" + databases.getId(), "", "");
						}
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/drop." + DB.db_type(database) + ".sql", "", "", "");
					}
					if (filepost.getParameter("create").equals("yes")) {
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/create." + DB.db_type(database) + ".sql", "", "", "");
					}
					if (filepost.getParameter("delete").equals("yes")) {
						append_config_settings = false;
						Databases databases = new Databases(text);
						String SQL = "select * from data order by id";
						databases.records("", "", "", "", "", "", "", db, myconfig, SQL);
						while(databases.records("", "", "", "", "", "", "", db, myconfig, "")) {
							db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/databases/database.delete." + DB.db_type(database) + ".sql", "data" + databases.getId(), "", "");
						}
						db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/delete." + DB.db_type(database) + ".sql", "", "", "");
					}
					if (filepost.getParameter("insert").equals("yes")) {
						boolean supersuperadmin;
						if (ini_database.equals("default")) {
							supersuperadmin = true;
						} else {
							supersuperadmin = false;
						}
						HashMap<String,String> myparams = new HashMap<String,String>();
						Iterator parameternames = filepost.getParameterNames();
						while (parameternames.hasNext()) {
							String param = "" + parameternames.next();
							String value = filepost.getParameter(param);
							if (param.startsWith("import_")) {
								myparams.put("@@@import:" + param.substring(7) + "@@@", value);
							}
						}
						if (! filepost.getParameter("file.server_filename").equals("")) {
							String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
							myconfig.set(db, "database_import_started", timestamp);
							myconfig.set(db, "database_import_completed", "");
							String filename = DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/importing.html";
							Common.deleteFile(filename);
							PrintWriter fileout = new PrintWriter(new FileOutputStream(filename));
							db.importXML(fileout, out, server, DOCUMENT_ROOT, ini_database, mysession, myrequest, myresponse, myconfig, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath") + filepost.getParameter("file.server_filename"), "", "", DB.equals(database), supersuperadmin, database, myparams);
							fileout.close();
						} else if (! filepost.getParameter("importfile").equals("")) {
							String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
							myconfig.set(db, "database_import_started", timestamp);
							myconfig.set(db, "database_import_completed", "");
							String filename = DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/importing.html";
							Common.deleteFile(filename);
							PrintWriter fileout = new PrintWriter(new FileOutputStream(filename));
							db.importXML(fileout, out, server, DOCUMENT_ROOT, ini_database, mysession, myrequest, myresponse, myconfig, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/" + filepost.getParameter("importfile"), "", "", DB.equals(database), supersuperadmin, database, myparams);
							fileout.close();
						}
					}
					if (filepost.getParameter("insertwebsite").equals("yes")) {
						db.import_website(out, mytext, mysession, DB.equals(database), myconfig, DOCUMENT_ROOT, myconfig.get(db, "URLrootpath"), filepost.getParameter("titleelement"), filepost.getParameter("contentelement"));
						myconfig.set(db, "use_additionalcontent", "yes");
						myconfig.set(db, "use_advancedscripting", "yes");
						myconfig.set(db, "use_contentdefinition", "yes");
						myconfig.set(db, "use_contentclasses", "yes");
						myconfig.set(db, "use_contentgroups", "yes");
						myconfig.set(db, "use_imagegroups", "yes");
						myconfig.set(db, "use_filegroups", "yes");
						myconfig.set(db, "use_presentation", "yes");
						myconfig.set(db, "use_static_filenames", "yes");
					}
					if (filepost.getParameter("insertimages").equals("yes")) {
						db.import_folder(out, db, DB.equals(database), myconfig, DOCUMENT_ROOT, myconfig.get(db, "URLrootpath"), myconfig.get(db, "URLimagepath"), "image");
					}
					if (filepost.getParameter("insertfiles").equals("yes")) {
						db.import_folder(out, db, DB.equals(database), myconfig, DOCUMENT_ROOT, myconfig.get(db, "URLrootpath"), myconfig.get(db, "URLfilepath"), "file");
					}
				}
			}

			// RESTORE SETTINGS
			Cache.clear(db);
			config = new Configuration();
			if (config.get(db, "superadmin").equals("")) { config.set(db, "superadmin", save_superadmin); }
			if (config.get(db, "superadmin_password").equals("")) { config.set(db, "superadmin_password", save_superadmin_password); }
			if (config.get(db, "superadmin_email").equals("")) { config.set(db, "superadmin_email", save_superadmin_email); }
			if (config.get(db, "personal_license").equals("")) { config.set(db, "personal_license", save_personal_license); }
			if (config.get(db, "professional_license").equals("")) { config.set(db, "professional_license", save_professional_license); }
			if (config.get(db, "enterprise_license").equals("")) { config.set(db, "enterprise_license", save_enterprise_license); }
			if (config.get(db, "hosting_license").equals("")) { config.set(db, "hosting_license", save_hosting_license); }
			if (config.get(db, "ecommerce_license").equals("")) { config.set(db, "ecommerce_license", save_ecommerce_license); }
			if (config.get(db, "community_license").equals("")) { config.set(db, "community_license", save_community_license); }
			if (config.get(db, "databases_license").equals("")) { config.set(db, "databases_license", save_databases_license); }
			if (config.get(db, "statistics_license").equals("")) { config.set(db, "statistics_license", save_statistics_license); }
			if (config.get(db, "experience_license").equals("")) { config.set(db, "experience_license", save_experience_license); }
			if (config.get(db, "contact_form_recipient").equals("")) { config.set(db, "contact_form_recipient", save_contact_form_recipient); }
			if (config.get(db, "contact_form_recipients").equals("")) { config.set(db, "contact_form_recipients", save_contact_form_recipients); }
			if (config.get(db, "contact_form_mailserver").equals("")) { config.set(db, "contact_form_mailserver", save_contact_form_mailserver); }
			if (config.get(db, "order_form_recipient").equals("")) { config.set(db, "order_form_recipient", save_order_form_recipient); }
			if (save_is_superadmin) {
				mysession.set("username", config.get(db, "superadmin"));
				mysession.set("password", config.get(db, "superadmin_password"));
				mysession.set("email", config.get(db, "superadmin_email"));
				mysession.set("administrator", "administrator");
			}

			if (append_config_settings) {
				if (config.get(db, "print_contents").equals("")) {
					config.set(db, "print_contents", save_print_contents);
				} else if (! save_print_contents.equals("")) {
					config.set(db, "print_contents", save_print_contents + "," + config.get(db, "print_contents"));
				}
				if (config.get(db, "print_products").equals("")) {
					config.set(db, "print_products", save_print_products);
				} else if (! save_print_products.equals("")) {
					config.set(db, "print_products", save_print_products + "," + config.get(db, "print_products"));
				}
				if (config.get(db, "print_orders").equals("")) {
					config.set(db, "print_orders", save_print_orders);
				} else if (! save_print_orders.equals("")) {
					config.set(db, "print_orders", save_print_orders + "," + config.get(db, "print_orders"));
				}
				if (config.get(db, "print_users").equals("")) {
					config.set(db, "print_users", save_print_users);
				} else if (! save_print_users.equals("")) {
					config.set(db, "print_users", save_print_users + "," + config.get(db, "print_users"));
				}
			}

			doLicenseRestrictions(db, config);

			// SAVE DATABASE CONFIGURATION
			if (error.equals("")) {
				if (ini_database.equals("default")) {
//					config = new Configuration();

					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", ini_database, "superadmin", config.get(db, "superadmin"));
					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", ini_database, "default_page", config.get(db, "default_page"));
					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", ini_database, "default_template", config.get(db, "default_template"));
					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", ini_database, "default_stylesheet", config.get(db, "default_stylesheet"));
					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", ini_database, "default_version", config.get(db, "default_version"));

					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", ini_database, "database_version", config.get(db, "database_version"));
					inidb.WriteJSP(DOCUMENT_ROOT + "/ini.jsp", ini_database, "ini_cache_database", database);
					inidb.WriteINI(inifile, ini_database, "database", database);
					current_database = "" + database;

					mysession.set("username", config.get(db, "superadmin"));
					mysession.set("password", config.get(db, "superadmin_password"));
					mysession.set("email", config.get(db, "superadmin_email"));
					mysession.set("administrator", "administrator");
				}
			}

			// DO DATABASE UPGRADE AND PUBLISH TO STATIC FILENAMES
//			if (error.equals("")) {
				if (db != null) {
					if (filepost.getParameter("create").equals("yes")) {
						Cache.clear(db);
						doUpgrade(out, server, DOCUMENT_ROOT, database, ini_database, inifile, myrequest.getServerName(), mysession, myrequest, myresponse, myconfig, db);
						Cache.clear(db);
					}
				}
//			}

			String completed = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			config.set(db, "database_import_completed", completed);
			Cache.clear(db);

		} else if (error.equals("")) {
			if ((db != null) && (db.isError())) {
				error = text.display("error.config.database.connection");
				error = error + "<br>" + db.getMessage() + " " + db.getDebugInfo();
			}
		}

		if (! filepost.getParameter("file.server_filename").equals("")) {
			File fh = new File(DOCUMENT_ROOT + "/" + filepost.getParameter("file.server_filename"));
			if (fh.exists()) {
				fh.delete();
			}
		}
		Cache.clear(db);
		return db;
	}



	private void ini2db(HashMap<String,HashMap<String,String>> my_ini, String address, DB client_db, DB db) {
		if (! address.equals("default")) {
			Configuration my_config = new Configuration();
			Hosting hosting = new Hosting(text);
			hosting.setClientAddress(address);
			hosting.setClientDatabase("" + ((HashMap<String,String>)my_ini.get(address)).get("database"));
			hosting.setClientURLrootpath("" + ((HashMap<String,String>)my_ini.get(address)).get("URLrootpath"));
			hosting.setPersonalLicense(my_config.get(client_db, "personal_license"));
			hosting.setProfessionalLicense(my_config.get(client_db, "professional_license"));
			hosting.setEnterpriseLicense(my_config.get(client_db, "enterprise_license"));
			hosting.setHostingLicense(my_config.get(client_db, "hosting_license"));
			hosting.setEcommerceLicense(my_config.get(client_db, "ecommerce_license"));
			hosting.setCommunityLicense(my_config.get(client_db, "community_license"));
			hosting.setDatabasesLicense(my_config.get(client_db, "databases_license"));
			hosting.setStatisticsLicense(my_config.get(client_db, "statistics_license"));
			hosting.setCustomerExperienceLicense(my_config.get(client_db, "experience_license"));
			hosting.setSuperadmin(my_config.get(client_db, "superadmin"));
			hosting.setSuperadminPassword(my_config.get(client_db, "superadmin_password"));
			hosting.setSuperadminEmail(my_config.get(client_db, "superadmin_email"));
			hosting.create(db);
		}
	}



	public void doUpgrade(JspWriter out, ServletContext server, String DOCUMENT_ROOT, String database, String ini_database, String inifile, String servername, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;

		HashMap<String,HashMap<String,String>> my_ini;
		if (! License.valid(db, myconfig, "hosting")) {
			my_ini = new HashMap<String,HashMap<String,String>>();
			my_ini.put(ini_database, new HashMap<String,String>());
			((HashMap<String,String>)my_ini.get(ini_database)).put("database", database);
			((HashMap<String,String>)my_ini.get(ini_database)).put("URLrootpath", myconfig.get(db, "URLrootpath"));
		} else if (myconfig.get(db, "database_version").compareTo("6.2") < 0) {
			my_ini = inidb.AllINI(inifile);
		} else {
			my_ini = new HashMap<String,HashMap<String,String>>();
			String SQL = "select * from hosting order by client_address";
			Hosting hosting = new Hosting(text);
			hosting.records(db, SQL);
			while (hosting.records(db, "")) {
				if (my_ini.get(hosting.getClientAddress()) == null) {
					my_ini.put(hosting.getClientAddress(), new HashMap<String,String>());
				}
				((HashMap<String,String>)my_ini.get(hosting.getClientAddress())).put("database", hosting.getClientDatabase());
				((HashMap<String,String>)my_ini.get(hosting.getClientAddress())).put("URLrootpath", hosting.getClientURLrootpath());
				((HashMap<String,String>)my_ini.get(hosting.getClientAddress())).put("superadmin", hosting.getSuperadmin());
				((HashMap<String,String>)my_ini.get(hosting.getClientAddress())).put("superadmin_password", hosting.getSuperadminPassword());
				((HashMap<String,String>)my_ini.get(hosting.getClientAddress())).put("superadmin_email", hosting.getSuperadminEmail());
				((HashMap<String,String>)my_ini.get(hosting.getClientAddress())).put("hostinggroup", hosting.getHostinggroup());
				((HashMap<String,String>)my_ini.get(hosting.getClientAddress())).put("hostingtype", hosting.getHostingtype());
				((HashMap<String,String>)my_ini.get(hosting.getClientAddress())).put("scheduled_publish", hosting.getScheduledPublish());
				((HashMap<String,String>)my_ini.get(hosting.getClientAddress())).put("scheduled_notify", hosting.getScheduledNotify());
				((HashMap<String,String>)my_ini.get(hosting.getClientAddress())).put("scheduled_unpublish", hosting.getScheduledUnpublish());
				((HashMap<String,String>)my_ini.get(hosting.getClientAddress())).put("status", hosting.displayStatus());
			}
			my_ini.put(ini_database, new HashMap<String,String>());
			((HashMap<String,String>)my_ini.get(ini_database)).put("database", database);
			((HashMap<String,String>)my_ini.get(ini_database)).put("URLrootpath", myconfig.get(db, "URLrootpath"));
		}

		Iterator addresses = my_ini.keySet().iterator();
		while (addresses.hasNext()) {
			String address = (String) addresses.next();
try {
			String my_database = "" + ((HashMap<String,String>)my_ini.get(address)).get("database");
			String my_URLrootpath = "" + ((HashMap<String,String>)my_ini.get(address)).get("URLrootpath");

			DB client_db = new DB(text);
//System.out.println("HardCore/UCconfigureSystem.doUpgrade:"+address:":"+DB.DSN(my_database)+":"+my_database);
			client_db.connect(DB.DSN(my_database), my_database);
			if (client_db.isError()) {
//System.out.println("HardCore/UCconfigureSystem.doUpgrade:ERROR:"+client_db.getMessage());
				out.println("<p>" + text.display("error") + "<br>" + text.display("database.upgrade.domain") + " " + address + "<br>" + text.display("database.upgrade.database") + " " + my_database + "<br>" + text.display("error") + " " + client_db.getMessage());
			} else {
				Cache.clear(client_db);
				Configuration my_config = new Configuration();
				String my_database_version = my_config.get(client_db, "database_version");

				if ((my_database_version.compareTo("8.0") < 0) && (! License.valid(client_db, my_config, "enterprise")) && (License.valid(client_db, my_config, "professional")) && (License.restriction(client_db, my_config, "administrator") < 0) && (my_config.get(client_db, "enterprise_license").equals(""))) {
					String myprofessionallicense = my_config.get(client_db, "professional_license");
					String myowner = License.owner(myprofessionallicense);
					String myenterpriselicense = License.generate(myowner, "enterprise");
					my_config.set(client_db, "enterprise_license", myenterpriselicense);
				}

				if (! my_database_version.equals("9.0")) {
					out.println("<p>" + text.display("database.upgrade.upgrade") + " " + my_database_version + "<br>" + text.display("database.upgrade.domain") + " " + address + "<br>" + text.display("database.upgrade.database") + " " + my_database + "<br>" + "\r\n");
				} else {
					out.println("<p>" + text.display("database.upgrade.ok") + " " + my_database_version + "<br>" + text.display("database.upgrade.domain") + " " + address + "<br>" + text.display("database.upgrade.database") + " " + my_database + "<br>" + "\r\n");
				}
				if ((my_database_version.equals("")) || (my_database_version.equals("2.0"))) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-2.0-2.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-2.1-2.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-2.2-2.3.sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-2.3-4.0.sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-4.0-5.0.sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.0-5.4." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.4-5.5." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.5-5.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.6-6.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.0-6.2." + DB.db_type(my_database) + ".sql", "", "", "");
					ini2db(my_ini, address, client_db, db);
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.2-6.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.3-6.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.6-6.7." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.7-6.8." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.8-6.9." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.9-7.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("2.1")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-2.1-2.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-2.2-2.3.sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-2.3-4.0.sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-4.0-5.0.sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.0-5.4." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.4-5.5." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.5-5.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.6-6.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.0-6.2." + DB.db_type(my_database) + ".sql", "", "", "");
					ini2db(my_ini, address, client_db, db);
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.2-6.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.3-6.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.6-6.7." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.7-6.8." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.8-6.9." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.9-7.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("2.2")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-2.2-2.3.sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-2.3-4.0.sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-4.0-5.0.sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.0-5.4." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.4-5.5." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.5-5.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.6-6.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.0-6.2." + DB.db_type(my_database) + ".sql", "", "", "");
					ini2db(my_ini, address, client_db, db);
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.2-6.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.3-6.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.6-6.7." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.7-6.8." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.8-6.9." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.9-7.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("2.3")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-2.3-4.0.sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-4.0-5.0.sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.0-5.4." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.4-5.5." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.5-5.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.6-6.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.0-6.2." + DB.db_type(my_database) + ".sql", "", "", "");
					ini2db(my_ini, address, client_db, db);
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.2-6.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.3-6.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.6-6.7." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.7-6.8." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.8-6.9." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.9-7.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("4.0")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-4.0-5.0.sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.0-5.4." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.4-5.5." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.5-5.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.6-6.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.0-6.2." + DB.db_type(my_database) + ".sql", "", "", "");
					ini2db(my_ini, address, client_db, db);
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.2-6.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.3-6.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.6-6.7." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.7-6.8." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.8-6.9." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.9-7.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("5.0")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.0-5.4." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.4-5.5." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.5-5.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.6-6.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.0-6.2." + DB.db_type(my_database) + ".sql", "", "", "");
					ini2db(my_ini, address, client_db, db);
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.2-6.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.3-6.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.6-6.7." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.7-6.8." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.8-6.9." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.9-7.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("5.4")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.4-5.5." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.5-5.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.6-6.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.0-6.2." + DB.db_type(my_database) + ".sql", "", "", "");
					ini2db(my_ini, address, client_db, db);
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.2-6.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.3-6.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.6-6.7." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.7-6.8." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.8-6.9." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.9-7.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("5.5")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.5-5.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.6-6.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.0-6.2." + DB.db_type(my_database) + ".sql", "", "", "");
					ini2db(my_ini, address, client_db, db);
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.2-6.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.3-6.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.6-6.7." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.7-6.8." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.8-6.9." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.9-7.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("5.6")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.6-6.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.0-6.2." + DB.db_type(my_database) + ".sql", "", "", "");
					ini2db(my_ini, address, client_db, db);
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.2-6.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.3-6.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.6-6.7." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.7-6.8." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.8-6.9." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.9-7.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("5.8")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-5.8-6.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.0-6.2." + DB.db_type(my_database) + ".sql", "", "", "");
					ini2db(my_ini, address, client_db, db);
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.2-6.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.3-6.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.6-6.7." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.7-6.8." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.8-6.9." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.9-7.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("6.0")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.0-6.2." + DB.db_type(my_database) + ".sql", "", "", "");
					ini2db(my_ini, address, client_db, db);
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.2-6.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.3-6.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.6-6.7." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.7-6.8." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.8-6.9." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.9-7.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("6.2")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.2-6.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.3-6.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.6-6.7." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.7-6.8." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.8-6.9." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.9-7.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("6.3")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.3-6.6." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.6-6.7." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.7-6.8." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.8-6.9." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.9-7.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("6.6")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.6-6.7." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.7-6.8." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.8-6.9." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.9-7.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("6.7")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.7-6.8." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.8-6.9." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.9-7.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("6.8")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.8-6.9." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.9-7.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("6.9")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-6.9-7.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("7.0")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.0-7.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("7.1")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.1-7.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("7.2")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.2-7.3." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("7.3")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-7.3-8.0." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("8.0")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.0-8.1." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("8.1")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.1-8.2." + DB.db_type(my_database) + ".sql", "", "", "");
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				} else if (my_database_version.equals("8.2")) {
					client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade-8.2-9.0." + DB.db_type(my_database) + ".sql", "", "", "");
				}
				client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade." + DB.db_type(my_database) + ".sql", "", "", "");
				client_db.execute_sql(out, DOCUMENT_ROOT + "/" + text.display("adminpath") + "/database/upgrade.sql", "", "", "");
				Cache.clear(client_db);
				my_config = new Configuration();

				if (my_URLrootpath.equals("")) {
					my_URLrootpath = "/";
					my_URLrootpath = inidb.ReadINI(inifile, "default", "URLrootpath", my_URLrootpath, "", null, null);
					my_URLrootpath = inidb.ReadINI(inifile, servername, "URLrootpath", my_URLrootpath, "", null, null);
				}
				my_config.setTemp("URLrootpath", my_URLrootpath);
				my_config.setTemp("URLfilepath", "file/");
				my_config.setTemp("URLimagepath", "image/");
				my_config.setTemp("URLstylesheetpath", "stylesheet/");
				my_config.setTemp("URLuploadpath", "upload/");

				String SQL = "select id from content where (content like '%asp%') or (content like '%php%') or (htmlattributes like '%asp%') or (htmlattributes like '%php%')";
				Content list_content = new Content(text);
				list_content.records("", "", "", "", "", "", "", client_db, my_config, SQL);
				while (list_content.records("", "", "", "", "", "", "", client_db, my_config, "")) {
					mysession.set("mode", "");
					Page record_content = new Page(text);
					my_config.setTemp("adjust_links", "no");
					record_content.read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", client_db, my_config, list_content.getId(), "content", "id", "", myconfig.get(db, "default_version"), "", "", "", "", myconfig.get(db, "default_template"), "", myconfig.get(db, "default_stylesheet"), "", myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
					my_config.setTemp("adjust_links", "yes");
					String old_content = record_content.getContent();
					record_content.setContent(record_content.adjust_links("", record_content.getContent(), client_db, my_config));
					String new_content = record_content.getContent();
					String old_htmlattributes = record_content.getHtmlAttributes();
					record_content.setHtmlAttributes(record_content.adjust_links("", record_content.getHtmlAttributes(), client_db, my_config));
					String new_htmlattributes = record_content.getHtmlAttributes();
					if ((! old_content.equals(new_content)) || (! old_htmlattributes.equals(new_htmlattributes))) {
						out.println("<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + "<br>" + "\r\n");
						record_content.update(client_db, my_config, record_content.getId(), "content", "id");
					}
					mysession.set("mode", "");
				}
				SQL = "select id from content_public where (content like '%asp%') or (content like '%php%') or (htmlattributes like '%asp%') or (htmlattributes like '%php%')";
				list_content = new Content(text);
				list_content.records("", "", "", "", "", "", "", client_db, my_config, SQL);
				while (list_content.records("", "", "", "", "", "", "", client_db, my_config, "")) {
					mysession.set("mode", "");
					Page record_content = new Page(text);
					my_config.setTemp("adjust_links", "no");
					record_content.read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", client_db, my_config, list_content.getId(), "content_public", "id", "", myconfig.get(db, "default_version"), "", "", "", "", myconfig.get(db, "default_template"), "", myconfig.get(db, "default_stylesheet"), "", myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
					my_config.setTemp("adjust_links", "yes");
					String old_content = record_content.getContent();
					record_content.setContent(record_content.adjust_links("", record_content.getContent(), client_db, my_config));
					String new_content = record_content.getContent();
					String old_htmlattributes = record_content.getHtmlAttributes();
					record_content.setHtmlAttributes(record_content.adjust_links("", record_content.getHtmlAttributes(), client_db, my_config));
					String new_htmlattributes = record_content.getHtmlAttributes();
					if ((! old_content.equals(new_content)) || (! old_htmlattributes.equals(new_htmlattributes))) {
						out.println("<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + "<br>" + "\r\n");
						record_content.update(client_db, my_config, record_content.getId(), "content_public", "id");
					}
					mysession.set("mode", "");
				}

				SQL = "select id from content where ((contentclass = " + db.quote("page") + " or contentclass = " + db.quote("script") + " or contentclass = " + db.quote("stylesheet") + " or contentclass = " + db.quote("product") + ") and " + db.is_not_blank("server_filename") + ")";
				list_content = new Content(text);
				list_content.records("", "", "", "", "", "", "", client_db, my_config, SQL);
				while (list_content.records("", "", "", "", "", "", "", client_db, my_config, "")) {
					mysession.set("mode", "");
					Page record_content = new Page(text);
					record_content.read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", client_db, my_config, list_content.getId(), "content", "id", "", myconfig.get(db, "default_version"), "", "", "", "", myconfig.get(db, "default_template"), "", myconfig.get(db, "default_stylesheet"), "", myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
					String filecontent;
					if ((my_config.get(client_db, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.html?$").matcher(record_content.getServerFilename()).find())) {
						filecontent = Common.readFile(DOCUMENT_ROOT + "/" + record_content.getContentClass() + ".original.html");
//handled elsewhere by Page.outputStaticFile
					} else if ((my_config.get(client_db, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.css$").matcher(record_content.getServerFilename()).find())) {
						filecontent = Common.readFile(DOCUMENT_ROOT + "/" + record_content.getContentClass() + ".original.css");
//handled elsewhere by Page.outputStaticFile
					} else if ((my_config.get(client_db, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.js$").matcher(record_content.getServerFilename()).find())) {
						filecontent = Common.readFile(DOCUMENT_ROOT + "/" + record_content.getContentClass() + ".original.js");
//handled elsewhere by Page.outputStaticFile
					} else if (my_config.get(client_db, "URLrootpath").equals("/")) {
						filecontent = Common.readFile(DOCUMENT_ROOT + "/" + record_content.getContentClass() + ".original.jsp");
					} else {
						filecontent = Common.readFile(DOCUMENT_ROOT + "/" + record_content.getContentClass() + ".original.hosting.jsp");
					}
					filecontent = filecontent.replaceAll("myrequest\\.getParameter\\(\"id\"\\)", "\"" + record_content.getId() + "\"");
					if (my_config.get(client_db, "use_static_content").equals("no")) {
						if (! Pattern.compile("^[a-z0-9][-_.a-z0-9\\/]*\\.jsp$").matcher(record_content.getServerFilename()).find()) {
							out.println("<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename() + "/index.jsp" + "<br>" + "\r\n");
							Common.deleteFile(DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename() + "/index.jsp");
							Common.deleteFolder(DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename(), false);
						} else if (Pattern.compile("^[a-z0-9][-_.a-z0-9\\/]*\\.jsp$").matcher(record_content.getServerFilename()).find()) {
							out.println("<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename() + "<br>" + "\r\n");
							Common.deleteFile(DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename());
						}
					} else if ((my_config.get(client_db, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.html?$").matcher(record_content.getServerFilename()).find())) {
						out.println("<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename() + "<br>" + "\r\n");
						Common.deleteFile(DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename());
						Common.writeFile(DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename(), filecontent, my_config.get(client_db, "charset"));
					} else if ((my_config.get(client_db, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.css$").matcher(record_content.getServerFilename()).find())) {
						out.println("<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename() + "<br>" + "\r\n");
						Common.deleteFile(DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename());
						Common.writeFile(DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename(), filecontent, my_config.get(client_db, "charset"));
					} else if ((my_config.get(client_db, "use_static_content").equals("yes")) && (Pattern.compile("^[a-zA-Z0-9][-_.a-zA-Z0-9\\/]*\\.js$").matcher(record_content.getServerFilename()).find())) {
						out.println("<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename() + "<br>" + "\r\n");
						Common.deleteFile(DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename());
						Common.writeFile(DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename(), filecontent, my_config.get(client_db, "charset"));
					} else if (! Pattern.compile("^[a-z0-9][-_.a-z0-9\\/]*\\.jsp$").matcher(record_content.getServerFilename()).find()) {
						out.println("<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename() + "/index.jsp" + "<br>" + "\r\n");
						Common.createFolder(DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename() + "/index.jsp");
						Common.deleteFile(DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename() + "/index.jsp");
						Common.writeFile(DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename() + "/index.jsp", filecontent, my_config.get(client_db, "charset"));
					} else if (Pattern.compile("^[a-z0-9][-_.a-z0-9\\/]*\\.jsp$").matcher(record_content.getServerFilename()).find()) {
						out.println("<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename() + "<br>" + "\r\n");
						Common.deleteFile(DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename());
						Common.writeFile(DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename(), filecontent, my_config.get(client_db, "charset"));
					} else {
						out.println("<p>%" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename() + "%<br>" + "\r\n");
					}
					mysession.set("mode", "");
					record_content.outputStaticFile(server, myrequest.getRequest(), myresponse.getResponse(), mysession.getSession(), null, my_config, client_db);
				}

				SQL = "select id from content where ((contentclass = 'image' or contentclass = 'file') and " + db.is_not_blank("server_filename") + ")";
				list_content = new Content(text);
				list_content.records("", "", "", "", "", "", "", client_db, my_config, SQL);
				while (list_content.records("", "", "", "", "", "", "", client_db, my_config, "")) {
					mysession.set("mode", "");
					Page record_content = new Page(text);
					record_content.read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", client_db, my_config, list_content.getId(), "content", "id", "", myconfig.get(db, "default_version"), "", "", "", "", myconfig.get(db, "default_template"), "", myconfig.get(db, "default_stylesheet"), "", myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
					out.println("<p>" + record_content.getContentClass() + " - " + record_content.getId() + " - " + record_content.getTitle() + " - " + DOCUMENT_ROOT + my_config.get(client_db, "URLrootpath") + record_content.getServerFilename() + "<br>" + "\r\n");
					String filename = record_content.getServerFilename();
					filename = filename.replaceAll("^\\Q" + Common.getRealPath(server, "/") + "\\E", "");
					filename = filename.replaceAll("[/\\\\]+", "/");
					String exists = Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/exists"), "\"" + my_config.get(db, "URLrootpath").replaceAll("^/", "") + filename + "\"" + " " + "\"" + my_config.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:doUpgrade1\"", "", server, mysession, myrequest, myresponse);
					if (exists.equals("")) {
						Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/upload"), "\"" + my_config.get(db, "URLrootpath").replaceAll("^/", "") + filename + "\"" + " " + "\"" + my_config.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:doUpgrade2\"", "", server, mysession, myrequest, myresponse);
					}
					String lastmodified = Common.fileLastModified(Common.getRealPath(server, my_config.get(db, "URLrootpath") + filename));
					if ((lastmodified.equals("")) || (record_content.getUpdated().compareTo(lastmodified) > 0) || (record_content.getPublished().compareTo(lastmodified) > 0)) {
						Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/download"), "\"" + my_config.get(db, "URLrootpath").replaceAll("^/", "") + filename + "\"" + " " + "\"" + my_config.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:doUpgrade3\"", "", server, mysession, myrequest, myresponse);
					}
					mysession.set("mode", "");
				}

				Content metacontent = new Content(text);
				metacontent.update_metainfo_all(client_db, my_config);

				out.println("<p>orders<br>" + "\r\n");
				SQL = "select * from orders where createdmonth is null or createdmonth=0";
				Order list_orders = new Order();
				list_orders.records(client_db, SQL);
				while (list_orders.records(client_db, "")) {
					list_orders.update(client_db);
					out.println(".");
				}
				out.println("<p>" + "\r\n");
			}
			Cache.clear(client_db);
			client_db.close();
			client_db = null;
			out.println("<hr>");
			out.flush();
} catch( Exception e) {
	System.out.println("HardCore/UCconfigureSystem.doUpgrade:ERROR:"+address+":::"+e);
}
		}
		out.println(text.display("database.upgrade.done"));
		out.flush();
	}



	public void doExport(JspWriter out, ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		myconfig.delete(db, "database_import_started");
		myconfig.delete(db, "database_import_completed");
		db.exportXML(out, server, myrequest.getServerName(), DOCUMENT_ROOT, myconfig, myrequest);
	}



	public void doDownload(JspWriter out, PageContext pagecontext, ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		if ((! myrequest.getParameter("backup").equals("")) && (myrequest.getParameter("backup").startsWith("database.")) && (myrequest.getParameter("backup").endsWith(".xml"))) {
			String filename = "/" + text.display("adminpath") + "/database/" + myrequest.getParameter("backup");
//			myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + filename);
			myresponse.returnFile(DOCUMENT_ROOT + filename, server, "x-application/octet-stream");
			out.clear();
			out = pagecontext.pushBody();
		} else {
			myresponse.getResponse().sendError(404);
		}
	}



	public void doDelete(JspWriter out, ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if (db == null) return;
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return;
		if ((! myrequest.getParameter("backup").equals("")) && (myrequest.getParameter("backup").startsWith("database." + myrequest.getServerName() + ".")) && (myrequest.getParameter("backup").endsWith(".xml"))) {
			String filename = "/" + text.display("adminpath") + "/database/" + myrequest.getParameter("backup");
			filename = filename.replaceAll("[^-_:. /\\\\a-zA-Z0-9]", "");
			Common.deleteFile(Common.getRealPath(server, filename));
			filename = filename.replaceAll("\\\\.xml", ".html");
			Common.deleteFile(Common.getRealPath(server, filename));
		}
	}



	public Configuration getConfig() {
		return config;
	}



	public String getDatabase() {
		return database;
	}



	public String getCurrentDatabase() {
		return current_database;
	}



	public String getDummyDatabase2() {
		return dummy_database2;
	}



	public String getDummyDatabase3() {
		return dummy_database3;
	}



	public String getError() {
		return error;
	}



	public Page getPage() {
		return page;
	}



	public User getUser() {
		return user;
	}



	public Currency getCurrencies() {
		return currencies;
	}



	public LinkedHashMap getWebsiteSettings() {
		return websitesettings;
	}



	public Fileupload getFileupload() {
		return filepost;
	}



	private void setConfigFromRequest(Request myrequest, Configuration myconfig, DB db) {
		if (myrequest.getMethod().equals("POST")) {
			String[] tobedeleted = myrequest.getParameter("delete").split(":");
			for (int i=0; i<tobedeleted.length; i++) {
				myconfig.delete(db, tobedeleted[i]);
			}
			Enumeration parameternames = myrequest.getParameterNames();
			while (parameternames.hasMoreElements()) {
				String parametername = "" + parameternames.nextElement();
				if (! parametername.equals("delete")) {
					myconfig.set(db, parametername, Common.join(",", myrequest.getParameters(parametername)));
				}
			}
		}
	}



}
