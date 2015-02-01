package HardCore;

import java.util.*;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;

public class UCmaintainHosting {
	private DB client_db = null;
	private Configuration client_config = new Configuration();
	private Hosting hosting = null;
	private HostingAPI hostingAPI = null;
	private int record_count = 0;
	private Text text = new Text();



	public UCmaintainHosting() {
	}



	public UCmaintainHosting(Text mytext) {
		if (mytext != null) text = mytext;
		hosting = new Hosting(text);
	}



	public LinkedHashMap<String,HashMap<String,String>> getIndex(String inifile, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new LinkedHashMap<String,HashMap<String,String>>();
		LinkedHashMap<String,HashMap<String,String>> clients = new LinkedHashMap<String,HashMap<String,String>>();
//		if (License.valid(db, myconfig, "hosting")) {
//			clients = inidb.AllINI(inifile);
			if (myrequest.getParameter("pagesize").equals(" ")) {
				mysession.set("admin_pagesize", "");
			} else if (! myrequest.getParameter("pagesize").equals("")) {
				mysession.set("admin_pagesize", html.encodeHtmlEntities(myrequest.getParameter("pagesize")));
			}
			String SQLwhere = "";
			if (myrequest.getParameter("hostingclass").equals(" ")) {
				mysession.set("admin_hostingclass", "");
			} else if (! myrequest.getParameter("hostingclass").equals("")) {
				mysession.set("admin_hostingclass", html.encodeHtmlEntities(myrequest.getParameter("hostingclass")));
			}
			if (myrequest.getParameter("hostingtype").equals(" ")) {
				mysession.set("admin_hostingtype", "");
			} else if (! myrequest.getParameter("hostingtype").equals("")) {
				mysession.set("admin_hostingtype", html.encodeHtmlEntities(myrequest.getParameter("hostingtype")));
			}
			if (myrequest.getParameter("hostinggroup").equals(" ")) {
				mysession.set("admin_hostinggroup", "");
			} else if (! myrequest.getParameter("hostinggroup").equals("")) {
				mysession.set("admin_hostinggroup", html.encodeHtmlEntities(myrequest.getParameter("hostinggroup")));
			}
			if (myrequest.getParameter("status").equals(" ")) {
				mysession.set("admin_status", "");
			} else if (! myrequest.getParameter("status").equals("")) {
				mysession.set("admin_status", html.encodeHtmlEntities(myrequest.getParameter("status")));
			}
			if (mysession.get("admin_hostingclass").equals("-")) {
				SQLwhere = Common.SQLwhere_like(db, SQLwhere, "superadmin_email", "");
			}
			if (mysession.get("admin_hostingtype").equals("-")) {
				SQLwhere = Common.SQLwhere_like(db, SQLwhere, "hostingtype", "");
			} else if (! mysession.get("admin_hostingtype").equals("")) {
				SQLwhere = Common.SQLwhere_like(db, SQLwhere, "hostingtype", mysession.get("admin_hostingtype"));
			}
			if (mysession.get("admin_hostinggroup").equals("-")) {
				SQLwhere = Common.SQLwhere_like(db, SQLwhere, "hostinggroup", "");
			} else if (! mysession.get("admin_hostinggroup").equals("")) {
				SQLwhere = Common.SQLwhere_like(db, SQLwhere, "hostinggroup", mysession.get("admin_hostinggroup"));
			}
			String now = Common.strftime("%Y-%m-%d %H:%M:%S", new Date());
			if (mysession.get("admin_status").equals("pending")) {
				SQLwhere = Common.SQLwhere(SQLwhere, "(" + db.is_not_blank("scheduled_publish") + " and (scheduled_publish > '" + now + "'))");
			} else if (mysession.get("admin_status").equals("active")) {
				SQLwhere = Common.SQLwhere(SQLwhere, "((" + db.is_blank("scheduled_publish") + " or (scheduled_publish <= '" + now + "')) and (" + db.is_blank("scheduled_unpublish") + " or (scheduled_unpublish > '" + now + "')))");
			} else if (mysession.get("admin_status").equals("expired")) {
				SQLwhere = Common.SQLwhere(SQLwhere, "(" + db.is_not_blank("scheduled_unpublish") + " and (scheduled_unpublish <= '" + now + "'))");
			} else if (mysession.get("admin_status").equals("expiring")) {
				SQLwhere = Common.SQLwhere(SQLwhere, "((" + db.is_not_blank("scheduled_notify") + " and (scheduled_notify <= '" + now + "')) and (" + db.is_not_blank("scheduled_unpublish") + " and (scheduled_unpublish > '" + now + "')))");
			}

			String SQL = "select count(*) as count from hosting " + SQLwhere;
			record_count = db.query_value(SQL).intValue();

			Hosting hosting = new Hosting(text);
			String offset = html.encodeHtmlEntities(myrequest.getParameter("offset"));
			String limit = html.encodeHtmlEntities(myrequest.getParameter("page_size"));
			if ((! offset.equals("")) && (! limit.equals(""))) {
				if (SQLwhere.length() < 6) SQLwhere += "      ";
				SQL = Common.SQLlimit(db.db_type(db.getDatabase()), "id", "*", "hosting", SQLwhere.substring(6), "client_address", false, Integer.parseInt("0" + offset), Integer.parseInt("0" + limit));
			} else if (mysession.get("admin_pagesize").equals("")) {
				SQL = "select * from hosting " + SQLwhere + " order by client_address, id";
			} else {
				SQL = "select * from hosting " + SQLwhere + " order by client_address, id";
			}
			hosting.records(db, SQL);
			while (hosting.records(db, "")) {
				clients.put(hosting.getClientAddress(), new HashMap<String,String>());
				((HashMap<String,String>)clients.get(hosting.getClientAddress())).put("database", hosting.getClientDatabase());
				((HashMap<String,String>)clients.get(hosting.getClientAddress())).put("URLrootpath", hosting.getClientURLrootpath());
				((HashMap<String,String>)clients.get(hosting.getClientAddress())).put("superadmin", hosting.getSuperadmin());
				((HashMap<String,String>)clients.get(hosting.getClientAddress())).put("superadmin_password", hosting.getSuperadminPassword());
				((HashMap<String,String>)clients.get(hosting.getClientAddress())).put("superadmin_email", hosting.getSuperadminEmail());
				((HashMap<String,String>)clients.get(hosting.getClientAddress())).put("hostinggroup", hosting.getHostinggroup());
				((HashMap<String,String>)clients.get(hosting.getClientAddress())).put("hostingtype", hosting.getHostingtype());
				((HashMap<String,String>)clients.get(hosting.getClientAddress())).put("scheduled_publish", hosting.getScheduledPublish());
				((HashMap<String,String>)clients.get(hosting.getClientAddress())).put("scheduled_notify", hosting.getScheduledNotify());
				((HashMap<String,String>)clients.get(hosting.getClientAddress())).put("scheduled_unpublish", hosting.getScheduledUnpublish());
				((HashMap<String,String>)clients.get(hosting.getClientAddress())).put("status", hosting.displayStatus());
			}
//		} else {
//			clients.put(myrequest.getServerName(), new HashMap());
//			((HashMap)clients.get(myrequest.getServerName())).put("database", inidb.ReadINI(inifile, myrequest.getServerName(), "database", "", "", null, null));
//			((HashMap)clients.get(myrequest.getServerName())).put("URLrootpath", inidb.ReadINI(inifile, myrequest.getServerName(), "URLrootpath", "", "", null, null));
//			((HashMap)clients.get(myrequest.getServerName())).put("superadmin", "");
//			((HashMap)clients.get(myrequest.getServerName())).put("superadmin_password", "");
//			((HashMap)clients.get(myrequest.getServerName())).put("superadmin_email", "");
//			((HashMap)clients.get(myrequest.getServerName())).put("hostinggroup", "");
//			((HashMap)clients.get(myrequest.getServerName())).put("hostingtype", "");
//			((HashMap)clients.get(myrequest.getServerName())).put("scheduled_publish", "");
//			((HashMap)clients.get(myrequest.getServerName())).put("scheduled_notify", "");
//			((HashMap)clients.get(myrequest.getServerName())).put("scheduled_unpublish", "");
//			((HashMap)clients.get(myrequest.getServerName())).put("status", "");
//		}
		return clients;
	}



	public String getView(String inifile, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		String error = "";
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
//		if (License.valid(db, myconfig, "hosting")) {
//			client_address = html.encodeHtmlEntities(myrequest.getParameter("id"));
//			client_database = inidb.ReadINI(inifile, client_address, "database", "", "", null, null);
//			client_URLrootpath = inidb.ReadINI(inifile, client_address, "URLrootpath", "", "", null, null);
			hosting = new Hosting(text);
			hosting.readClientAddress(db, myrequest.getParameter("id"));
//		} else {
//			if (! myrequest.getParameter("id").equals("")) {
//				client_address = myrequest.getServerName();
//				client_database = inidb.ReadINI(inifile, myrequest.getServerName(), "database", "", "", null, null);
//				client_URLrootpath = inidb.ReadINI(inifile, myrequest.getServerName(), "URLrootpath", "", "", null, null);
//			}
//		}
		if (! hosting.getClientDatabase().equals("")) {
			client_db = new DB(text);
			client_db.connect(DB.DSN(hosting.getClientDatabase()), hosting.getClientDatabase());
			if (client_db.isError()) {
				client_db = null;
				error = text.display("error.hosting.database.connection");
			}
		} else {
			client_db = null;
		}
		client_config = new Configuration();
		return error;
	}



	public String getCreate(String inifile, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		String error = "";
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
//		if (License.valid(db, myconfig, "hosting")) {
//			client_address = html.encodeHtmlEntities(myrequest.getParameter("id"));
//			client_database = inidb.ReadINI(inifile, client_address, "database", "", "", null, null);
//			client_URLrootpath = inidb.ReadINI(inifile, client_address, "URLrootpath", "", "", null, null);
			hosting = new Hosting(text);
			hosting.readClientAddress(db, myrequest.getParameter("id"));
//		} else {
//			if (! myrequest.getParameter("id").equals("")) {
//				client_address = myrequest.getServerName();
//				client_database = inidb.ReadINI(inifile, myrequest.getServerName(), "database", "", "", null, null);
//				client_URLrootpath = inidb.ReadINI(inifile, myrequest.getServerName(), "URLrootpath", "", "", null, null);
//			}
//		}
		if (! hosting.getClientDatabase().equals("")) {
			client_db = new DB(text);
			client_db.connect(DB.DSN(hosting.getClientDatabase()), hosting.getClientDatabase());
			if (client_db.isError()) {
				client_db = null;
				error = text.display("error.hosting.database.connection");
			}
		} else {
			client_db = null;
		}
		client_config = new Configuration();
		return error;
	}



	public String getUpdate(String inifile, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		String error = "";
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
//		if (License.valid(db, myconfig, "hosting")) {
//			client_address = html.encodeHtmlEntities(myrequest.getParameter("id"));
//			client_database = inidb.ReadINI(inifile, client_address, "database", "", "", null, null);
//			client_URLrootpath = inidb.ReadINI(inifile, client_address, "URLrootpath", "", "", null, null);
			hosting = new Hosting(text);
			hosting.readClientAddress(db, myrequest.getParameter("id"));
//		} else {
//			if (! myrequest.getParameter("id").equals("")) {
//				client_address = myrequest.getServerName();
//				client_database = inidb.ReadINI(inifile, myrequest.getServerName(), "database", "", "", null, null);
//				client_URLrootpath = inidb.ReadINI(inifile, myrequest.getServerName(), "URLrootpath", "", "", null, null);
//			}
//		}
		if (! hosting.getClientDatabase().equals("")) {
			client_db = new DB(text);
			client_db.connect(DB.DSN(hosting.getClientDatabase()), hosting.getClientDatabase());
			if (client_db.isError()) {
				client_db = null;
				error = text.display("error.hosting.database.connection");
			}
		} else {
			client_db = null;
		}
		client_config = new Configuration();
		return error;
	}



	public String getDelete(String inifile, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		String error = "";
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
//		if (License.valid(db, myconfig, "hosting")) {
//			client_address = html.encodeHtmlEntities(myrequest.getParameter("id"));
//			client_database = inidb.ReadINI(inifile, client_address, "database", "", "", null, null);
//			client_URLrootpath = inidb.ReadINI(inifile, client_address, "URLrootpath", "", "", null, null);
			hosting = new Hosting(text);
			hosting.readClientAddress(db, myrequest.getParameter("id"));
//		} else {
//			if (! myrequest.getParameter("id").equals("")) {
//				client_address = myrequest.getServerName();
//				client_database = inidb.ReadINI(inifile, myrequest.getServerName(), "database", "", "", null, null);
//				client_URLrootpath = inidb.ReadINI(inifile, myrequest.getServerName(), "URLrootpath", "", "", null, null);
//			}
//		}
		if (! hosting.getClientDatabase().equals("")) {
			client_db = new DB(text);
			client_db.connect(DB.DSN(hosting.getClientDatabase()), hosting.getClientDatabase());
			if (client_db.isError()) {
				client_db = null;
				error = text.display("error.hosting.database.connection");
			}
		} else {
			client_db = null;
		}
		client_config = new Configuration();
		return error;
	}



	public HashMap<String,String> getDeleteMultiple(String inifile, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new HashMap<String,String>();
		HashMap<String,String> clients = new HashMap<String,String>();
//		if (License.valid(db, myconfig, "hosting")) {
			String ids[] = myrequest.getParameters("id");
			if (ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					String id = ids[i];
					clients.put(id, id);
				}
			}
//		}
		return clients;
	}



	public String doCreate(ServletContext server, String DOCUMENT_ROOT, String inifile, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String original_database) {
		String error = "";
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
		Hosting hosting = new Hosting(text);
//		if (License.valid(db, myconfig, "hosting")) {
			String client_address = html.encodeHtmlEntities(myrequest.getParameter("client_address"));
			String client_database = html.encodeHtmlEntities(myrequest.getParameter("client_database"));
			String client_URLrootpath = html.encodeHtmlEntities(myrequest.getParameter("client_URLrootpath"));
			String client_superadmin = html.encodeHtmlEntities(myrequest.getParameter("superadmin"));
			String client_superadmin_password = html.encodeHtmlEntities(myrequest.getParameter("superadmin_password"));
			String client_superadmin_email = html.encodeHtmlEntities(myrequest.getParameter("superadmin_email"));
	
			hostingAPI = new HostingAPI(text, server, mysession, myrequest, inifile, db, original_database, myconfig);
			if (License.valid(db, myconfig, "hosting")) {
				error = hostingAPI.hosting_pre_create(client_address, client_database, client_URLrootpath, client_superadmin, client_superadmin_password, client_superadmin_email);
			}
			if (error.equals("")) {
				hosting.readClientAddress(db, client_address);
				hosting.getForm(myrequest);
				hosting.setClientAddress(client_address);
				Cms.CMSAudit("action=create hosting=" + hosting.getClientAddress() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
				hosting.create(db);

//				if (! client_address.equals("")) {
//					inidb.WriteJSP(DOCUMENT_ROOT + "/ini.jsp", client_address, "ini_cache_database", client_database);
//					inidb.WriteJSP(DOCUMENT_ROOT + "/ini.jsp", client_address, "ini_cache_URLrootpath", client_URLrootpath);
//					inidb.WriteINI(inifile, client_address, "database", client_database);
//					inidb.WriteINI(inifile, client_address, "URLrootpath", client_URLrootpath);
//				}
			
				if (! hosting.getClientDatabase().equals("")) {
					client_db = new DB(text);
					client_db.connect(DB.DSN(client_database), client_database);
					if (client_db.isError()) {
						client_db = null;
						error = text.display("error.hosting.database.connection");
					}
				} else {
					client_db = null;
				}
			
				client_config = new Configuration();
			
				if (client_db != null) {
					client_config.set(client_db, "personal_license", myrequest.getParameter("personal_license"));
					client_config.set(client_db, "professional_license", myrequest.getParameter("professional_license"));
					client_config.set(client_db, "ecommerce_license", myrequest.getParameter("ecommerce_license"));
					client_config.set(client_db, "community_license", myrequest.getParameter("community_license"));
					client_config.set(client_db, "databases_license", myrequest.getParameter("databases_license"));
					client_config.set(client_db, "statistics_license", myrequest.getParameter("statistics_license"));
					client_config.set(client_db, "superadmin", myrequest.getParameter("superadmin"));
					client_config.set(client_db, "superadmin_password", myrequest.getParameter("superadmin_password"));
					client_config.set(client_db, "superadmin_email", myrequest.getParameter("superadmin_email"));
					client_config.set(client_db, "contact_form_recipient", myrequest.getParameter("superadmin_email"));
				}
		
//				if ((client_db != null) && (! client_address.equals(""))) {
//					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "database_version", client_config.get(client_db, "database_version"));
//					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "superadmin", client_config.get(client_db, "superadmin"));
//					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "default_page", client_config.get(client_db, "default_page"));
//					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "default_template", client_config.get(client_db, "default_template"));
//					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "default_stylesheet", client_config.get(client_db, "default_stylesheet"));
//					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "default_version", client_config.get(client_db, "default_version"));
//				}
		
				if (License.valid(db, myconfig, "hosting")) {
					error = "" + error + hostingAPI.hosting_post_create(client_address, client_database, client_URLrootpath, client_superadmin, client_superadmin_password, client_superadmin_email);
				}

				hosting.schedule(myconfig, db);
				UCpublishScheduled publishScheduled = new UCpublishScheduled(text);
				publishScheduled.doScheduled(server, mysession, myrequest, myresponse, myconfig, db);
			}
//		} else {
//			error = "" + error + text.display("error.hosting.trial.create");
//			error = "" + error + "<p><a href=\"index.jsp\">" + text.display("error.hosting.trial.back") + "</a>.";
//		}
		return error;
	}



	public String doUpdate(ServletContext server, String DOCUMENT_ROOT, String inifile, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String original_database) {
		String error = "";
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
//		if (License.valid(db, myconfig, "hosting")) {
			String client_address = html.encodeHtmlEntities(myrequest.getParameter("id"));
			String client_database = html.encodeHtmlEntities(myrequest.getParameter("client_database"));
			String client_URLrootpath = html.encodeHtmlEntities(myrequest.getParameter("client_URLrootpath"));
			String client_superadmin = html.encodeHtmlEntities(myrequest.getParameter("superadmin"));
			String client_superadmin_password = html.encodeHtmlEntities(myrequest.getParameter("superadmin_password"));
			String client_superadmin_email = html.encodeHtmlEntities(myrequest.getParameter("superadmin_email"));
	
			hostingAPI = new HostingAPI(text, server, mysession, myrequest, inifile, db, original_database, myconfig);
			if (License.valid(db, myconfig, "hosting")) {
				error = hostingAPI.hosting_pre_update(client_address, client_database, client_URLrootpath, client_superadmin, client_superadmin_password, client_superadmin_email);
			}
			if (error.equals("")) {
				Hosting hosting = new Hosting(text);
				hosting.readClientAddress(db, client_address);
				hosting.getForm(myrequest);
				hosting.setClientAddress(client_address);
				Cms.CMSAudit("action=update hosting=" + hosting.getClientAddress() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
				hosting.update(db);

//				if (! client_address.equals("")) {
//					inidb.WriteJSP(DOCUMENT_ROOT + "/ini.jsp", client_address, "ini_cache_database", client_database);
//					inidb.WriteJSP(DOCUMENT_ROOT + "/ini.jsp", client_address, "ini_cache_URLrootpath", client_URLrootpath);
//					inidb.WriteINI(inifile, client_address, "database", client_database);
//					inidb.WriteINI(inifile, client_address, "URLrootpath", client_URLrootpath);
//				}

				if (! hosting.getClientDatabase().equals("")) {
					client_db = new DB(text);
					client_db.connect(DB.DSN(client_database), client_database);
					if (client_db.isError()) {
						client_db = null;
						error = text.display("error.hosting.database.connection");
					}
				} else {
					client_db = null;
				}
			
				client_config = new Configuration();

				if (client_db != null) {
					client_config.set(client_db, "personal_license", myrequest.getParameter("personal_license"));
					client_config.set(client_db, "professional_license", myrequest.getParameter("professional_license"));
					client_config.set(client_db, "ecommerce_license", myrequest.getParameter("ecommerce_license"));
					client_config.set(client_db, "community_license", myrequest.getParameter("community_license"));
					client_config.set(client_db, "databases_license", myrequest.getParameter("databases_license"));
					client_config.set(client_db, "statistics_license", myrequest.getParameter("statistics_license"));
					client_config.set(client_db, "superadmin", myrequest.getParameter("superadmin"));
					client_config.set(client_db, "superadmin_password", myrequest.getParameter("superadmin_password"));
					client_config.set(client_db, "superadmin_email", myrequest.getParameter("superadmin_email"));
				}

//				if ((client_db != null) && (! client_address.equals(""))) {
//					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "database_version", client_config.get(client_db, "database_version"));
//					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "superadmin", client_config.get(client_db, "superadmin"));
//					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "default_page", client_config.get(client_db, "default_page"));
//					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "default_template", client_config.get(client_db, "default_template"));
//					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "default_stylesheet", client_config.get(client_db, "default_stylesheet"));
//					inidb.WriteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "default_version", client_config.get(client_db, "default_version"));
//				}

				if (License.valid(db, myconfig, "hosting")) {
					error = "" + error + hostingAPI.hosting_post_update(client_address, client_database, client_URLrootpath, client_superadmin, client_superadmin_password, client_superadmin_email);
				}

				hosting.schedule(myconfig, db);
				UCpublishScheduled publishScheduled = new UCpublishScheduled(text);
				publishScheduled.doScheduled(server, mysession, myrequest, myresponse, myconfig, db);
			}
//		} else {
//			error = "" + error + text.display("error.hosting.trial.update");
//			error = "" + error + "<p><a href=\"index.jsp\">" + text.display("error.hosting.trial.back") + "</a>.";
//		}
		return error;
	}



	public String doDelete(ServletContext server, String DOCUMENT_ROOT, String inifile, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String original_database) throws Exception {
		String error = "";
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
//		if (License.valid(db, myconfig, "hosting")) {
			String client_address = html.encodeHtmlEntities(myrequest.getParameter("id"));
			String client_database = html.encodeHtmlEntities(myrequest.getParameter("client_database"));
			String client_URLrootpath = html.encodeHtmlEntities(myrequest.getParameter("client_URLrootpath"));
			String client_superadmin = html.encodeHtmlEntities(myrequest.getParameter("superadmin"));
			String client_superadmin_password = html.encodeHtmlEntities(myrequest.getParameter("superadmin_password"));
			String client_superadmin_email = html.encodeHtmlEntities(myrequest.getParameter("superadmin_email"));
	
			hostingAPI = new HostingAPI(text, server, mysession, myrequest, inifile, db, original_database, myconfig);
			if (License.valid(db, myconfig, "hosting")) {
				error = hostingAPI.hosting_pre_delete(client_address, client_database, client_URLrootpath, client_superadmin, client_superadmin_password, client_superadmin_email);
			}
			if (error.equals("")) {
				Hosting hosting = new Hosting(text);
				hosting.readClientAddress(db, client_address);
				Cms.CMSAudit("action=delete hosting=" + hosting.getClientAddress() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
				hosting.delete(db);

//				if (! client_address.equals("")) {
//					inidb.DeleteJSP(DOCUMENT_ROOT + "/ini.jsp", client_address, "ini_cache_database", client_database);
//					inidb.DeleteJSP(DOCUMENT_ROOT + "/ini.jsp", client_address, "ini_cache_URLrootpath", client_URLrootpath);
//					inidb.DeleteINI(inifile, client_address, "database", client_database);
//					inidb.DeleteINI(inifile, client_address, "URLrootpath", client_URLrootpath);
//					inidb.DeleteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "database_version", "");
//					inidb.DeleteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "superadmin", "");
//					inidb.DeleteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "default_page", "");
//					inidb.DeleteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "default_template", "");
//					inidb.DeleteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "default_stylesheet", "");
//					inidb.DeleteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "default_version", "");
//				}

				client_db = null;

				client_config = new Configuration();

				if (License.valid(db, myconfig, "hosting")) {
					error = "" + error + hostingAPI.hosting_post_delete(client_address, client_database, client_URLrootpath, client_superadmin, client_superadmin_password, client_superadmin_email);
				}
			}
//		} else {
//			error = "" + error + text.display("error.hosting.trial.delete");
//			error = "" + error + "<p><a href=\"index.jsp\">" + text.display("error.hosting.trial.back") + "</a>.";
//		}
		return error;
	}



	public String doDeleteMultiple(ServletContext server, String DOCUMENT_ROOT, String inifile, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String original_database) throws Exception {
		String error = "";
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
//		if (License.valid(db, myconfig, "hosting")) {
			String ids[] = myrequest.getParameters("id");
			if (ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					String id = ids[i];
					String client_address = id;
					Hosting hosting = new Hosting(text);
					hosting.readClientAddress(db, client_address);
					String client_database = hosting.getClientDatabase();
					String client_URLrootpath = hosting.getClientURLrootpath();
					String client_superadmin = hosting.getSuperadmin();
					String client_superadmin_password = hosting.getSuperadminPassword();
					String client_superadmin_email = hosting.getSuperadminEmail();

					hostingAPI = new HostingAPI(text, server, mysession, myrequest, inifile, db, original_database, myconfig);
					String myerror = "";
					if (License.valid(db, myconfig, "hosting")) {
						myerror = hostingAPI.hosting_pre_delete(client_address, client_database, client_URLrootpath, client_superadmin, client_superadmin_password, client_superadmin_email);
					}
					if (myerror.equals("")) {
						hosting = new Hosting(text);
						hosting.readClientAddress(db, client_address);
						Cms.CMSAudit("action=delete hosting=" + hosting.getClientAddress() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
						hosting.delete(db);

//						if (! client_address.equals("")) {
//							inidb.DeleteJSP(DOCUMENT_ROOT + "/ini.jsp", client_address, "ini_cache_database", client_database);
//							inidb.DeleteJSP(DOCUMENT_ROOT + "/ini.jsp", client_address, "ini_cache_URLrootpath", client_URLrootpath);
//							inidb.DeleteINI(inifile, client_address, "database", client_database);
//							inidb.DeleteINI(inifile, client_address, "URLrootpath", client_URLrootpath);
//							inidb.DeleteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "database_version", "");
//							inidb.DeleteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "superadmin", "");
//							inidb.DeleteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "default_page", "");
//							inidb.DeleteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "default_template", "");
//							inidb.DeleteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "default_stylesheet", "");
//							inidb.DeleteJSP(DOCUMENT_ROOT + "/defaults.jsp", client_address, "default_version", "");
//						}

						client_db = null;

						client_config = new Configuration();

						if (License.valid(db, myconfig, "hosting")) {
							error = "" + error + hostingAPI.hosting_post_delete(client_address, client_database, client_URLrootpath, client_superadmin, client_superadmin_password, client_superadmin_email);
						}
					} else {
						error = "" + error + id + ": " + myerror + "<br>" + "\r\n";
					}
				}
			}
//		} else {
//			error = "" + error + text.display("error.hosting.trial.delete");
//			error = "" + error + "<p><a href=\"index.jsp\">" + text.display("error.hosting.trial.back") + "</a>.";
//		}
		return error;
	}



	public String doMoveMultiple(ServletContext server, String DOCUMENT_ROOT, String inifile, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String original_database) {
		String error = "";
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
		String ids[] = myrequest.getParameters("id");
		if (ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				String client_address = id;
				Hosting hosting = new Hosting(text);
				hosting.readClientAddress(db, client_address);
				if (myrequest.getParameter("hostinggroup").equals(" ")) {
					hosting.setHostinggroup("");
				} else if (! myrequest.getParameter("hostinggroup").equals("")) {
					hosting.setHostinggroup(myrequest.getParameter("hostinggroup"));
				}
				if (myrequest.getParameter("hostingtype").equals(" ")) {
					hosting.setHostingtype("");
				} else if (! myrequest.getParameter("hostingtype").equals("")) {
					hosting.setHostingtype(myrequest.getParameter("hostingtype"));
				}
				Cms.CMSAudit("action=update hosting=" + hosting.getClientAddress() + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
				hosting.update(db);
			}
		}
		return error;
	}



	public DB getClientDB() {
		return client_db;
	}



	public Configuration getClientConfig() {
		return client_config;
	}



	public Hosting getHosting() {
		return hosting;
	}



	public Page getScheduledPublishEmailPage(Session mysession, DB db, Configuration myconfig) {
		Page page = new Page(text);
		if (hosting != null) {
			page.read_primary_only(db, myconfig, hosting.getScheduledPublishEmail(), "content_public", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
		}
		return page;
	}



	public Page getScheduledNotifyEmailPage(Session mysession, DB db, Configuration myconfig) {
		Page page = new Page(text);
		if (hosting != null) {
			page.read_primary_only(db, myconfig, hosting.getScheduledNotifyEmail(), "content_public", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
		}
		return page;
	}



	public Page getScheduledUnpublishEmailPage(Session mysession, DB db, Configuration myconfig) {
		Page page = new Page(text);
		if (hosting != null) {
			page.read_primary_only(db, myconfig, hosting.getScheduledUnpublishEmail(), "content_public", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
		}
		return page;
	}



	public int getRecordCount() {
		return record_count;
	}



}
