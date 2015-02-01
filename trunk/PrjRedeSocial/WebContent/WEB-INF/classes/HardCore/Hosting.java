package HardCore;

import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.*;

public class Hosting {
	private String id = "";
	private String user_id = "";
	private String client_address = "";
	private String client_urlrootpath = "";
	private String client_database = "";
	private String personal_license = "";
	private String professional_license = "";
	private String enterprise_license = "";
	private String hosting_license = "";
	private String ecommerce_license = "";
	private String community_license = "";
	private String databases_license = "";
	private String statistics_license = "";
	private String experience_license = "";
	private String superadmin = "";
	private String superadmin_password = "";
	private String superadmin_email = "";
	private String hostingtype = "";
	private String hostinggroup = "";
	private String scheduled_last = "";
	private String scheduled_publish = "";
	private String scheduled_notify = "";
	private String scheduled_unpublish = "";
	private String scheduled_publish_email = "";
	private String scheduled_notify_email = "";
	private String scheduled_unpublish_email = "";

	private	Statement rs = null;
	private Text text = new Text();



	public Hosting() {
		init();
	}



	public Hosting(Text mytext) {
		if (mytext != null) text = mytext;
		init();
	}



	public void init() {
		id = "";
		user_id = "";
		client_address = "";
		client_urlrootpath = "";
		client_database = "";
		personal_license = "";
		professional_license = "";
		enterprise_license = "";
		hosting_license = "";
		ecommerce_license = "";
		community_license = "";
		databases_license = "";
		statistics_license = "";
		experience_license = "";
		superadmin = "";
		superadmin_password = "";
		superadmin_email = "";
		hostingtype = "";
		hostinggroup = "";
		scheduled_last = "";
		scheduled_publish = "";
		scheduled_notify = "";
		scheduled_unpublish = "";
		scheduled_publish_email = "";
		scheduled_notify_email = "";
		scheduled_unpublish_email = "";
	}



	public void read(DB db, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			String SQL = "select * from hosting where id=" + Common.SQL_clean(id) + "";
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void readClientAddress(DB db, String client_address) {
		if (db == null) return;
		if (! client_address.equals("")) {
			String SQL = "select * from hosting where client_address='" + Common.SQL_clean(client_address) + "'";
			String[] parts = client_address.split("\\.");
			for (int i=0; i<parts.length-1; i++) {
				client_address = client_address.replaceAll("^" + parts[i] + "\\.", "");
				SQL += " or client_address='" + Common.SQL_clean(client_address) + "'";
			}
			SQL += " order by " + Common.SQLlength(db, "client_address") + " desc";
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void readClientDatabase(DB db, String client_database) {
		if (db == null) return;
		if (! client_database.equals("")) {
			String SQL = "select * from hosting where client_database='" + Common.SQL_clean(client_database) + "'";
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void readClientURLrootpath(DB db, String client_urlrootpath) {
		if (db == null) return;
		if (! client_urlrootpath.equals("")) {
			String SQL = "select * from hosting where client_urlrootpath='" + Common.SQL_clean(client_urlrootpath) + "'";
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void getRecord(DB db, HashMap<String,String> record) {
		if (record.get("id") != null) id = "" + record.get("id"); else id = "";
		if (record.get("user_id") != null) user_id = "" + record.get("user_id"); else user_id = "";
		if (record.get("client_address") != null) client_address = "" + record.get("client_address"); else client_address = "";
		if (record.get("client_urlrootpath") != null) client_urlrootpath = "" + record.get("client_urlrootpath"); else client_urlrootpath = "";
		if (record.get("client_database") != null) client_database = "" + record.get("client_database"); else client_database = "";
		if (record.get("personal_license") != null) personal_license = "" + record.get("personal_license"); else personal_license = "";
		if (record.get("professional_license") != null) professional_license = "" + record.get("professional_license"); else professional_license = "";
		if (record.get("enterprise_license") != null) enterprise_license = "" + record.get("enterprise_license"); else enterprise_license = "";
		if (record.get("hosting_license") != null) hosting_license = "" + record.get("hosting_license"); else hosting_license = "";
		if (record.get("ecommerce_license") != null) ecommerce_license = "" + record.get("ecommerce_license"); else ecommerce_license = "";
		if (record.get("community_license") != null) community_license = "" + record.get("community_license"); else community_license = "";
		if (record.get("databases_license") != null) databases_license = "" + record.get("databases_license"); else databases_license = "";
		if (record.get("statistics_license") != null) statistics_license = "" + record.get("statistics_license"); else statistics_license = "";
		if (record.get("experience_license") != null) experience_license = "" + record.get("experience_license"); else experience_license = "";
		if (record.get("superadmin") != null) superadmin = "" + record.get("superadmin"); else superadmin = "";
		if (record.get("superadmin_password") != null) superadmin_password = "" + record.get("superadmin_password"); else superadmin_password = "";
		if (record.get("superadmin_email") != null) superadmin_email = "" + record.get("superadmin_email"); else superadmin_email = "";
		if (record.get("hostingtype") != null) hostingtype = "" + record.get("hostingtype"); else hostingtype = "";
		if (record.get("hostinggroup") != null) hostinggroup = "" + record.get("hostinggroup"); else hostinggroup = "";
		if (record.get("scheduled_last") != null) scheduled_last = "" + record.get("scheduled_last"); else scheduled_last = "";
		if (record.get("scheduled_publish") != null) scheduled_publish = "" + record.get("scheduled_publish"); else scheduled_publish = "";
		if (record.get("scheduled_notify") != null) scheduled_notify = "" + record.get("scheduled_notify"); else scheduled_notify = "";
		if (record.get("scheduled_unpublish") != null) scheduled_unpublish = "" + record.get("scheduled_unpublish"); else scheduled_unpublish = "";
		if (record.get("scheduled_publish_email") != null) scheduled_publish_email = "" + record.get("scheduled_publish_email"); else scheduled_publish_email = "";
		if (record.get("scheduled_notify_email") != null) scheduled_notify_email = "" + record.get("scheduled_notify_email"); else scheduled_notify_email = "";
		if (record.get("scheduled_unpublish_email") != null) scheduled_unpublish_email = "" + record.get("scheduled_unpublish_email"); else scheduled_unpublish_email = "";
	}



	public void getForm(Request request) {
		client_address = "" + request.getParameter("client_address");
		client_urlrootpath = "" + request.getParameter("client_URLrootpath");
		client_database = "" + request.getParameter("client_database");
		personal_license = "" + request.getParameter("personal_license");
		professional_license = "" + request.getParameter("professional_license");
		enterprise_license = "" + request.getParameter("enterprise_license");
		hosting_license = "" + request.getParameter("hosting_license");
		ecommerce_license = "" + request.getParameter("ecommerce_license");
		community_license = "" + request.getParameter("community_license");
		databases_license = "" + request.getParameter("databases_license");
		statistics_license = "" + request.getParameter("statistics_license");
		experience_license = "" + request.getParameter("experience_license");
		superadmin = "" + request.getParameter("superadmin");
		superadmin_password = "" + request.getParameter("superadmin_password");
		superadmin_email = "" + request.getParameter("superadmin_email");
		hostingtype = "" + request.getParameter("hostingtype");
		hostinggroup = "" + request.getParameter("hostinggroup");
		scheduled_publish = "" + request.getParameter("scheduled_publish");
		scheduled_notify = "" + request.getParameter("scheduled_notify");
		scheduled_unpublish = "" + request.getParameter("scheduled_unpublish");
		scheduled_publish_email = "" + request.getParameter("scheduled_publish_email");
		scheduled_notify_email = "" + request.getParameter("scheduled_notify_email");
		scheduled_unpublish_email = "" + request.getParameter("scheduled_unpublish_email");
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("user_id", "" + Common.integernumber(user_id));
		mydata.put("client_address", "" + client_address);
		mydata.put("client_urlrootpath", "" + client_urlrootpath);
		mydata.put("client_database", "" + client_database);
		mydata.put("personal_license", "" + personal_license);
		mydata.put("professional_license", "" + professional_license);
		mydata.put("enterprise_license", "" + enterprise_license);
		mydata.put("hosting_license", "" + hosting_license);
		mydata.put("ecommerce_license", "" + ecommerce_license);
		mydata.put("community_license", "" + community_license);
		mydata.put("databases_license", "" + databases_license);
		mydata.put("statistics_license", "" + statistics_license);
		mydata.put("experience_license", "" + experience_license);
		mydata.put("superadmin", "" + superadmin);
		mydata.put("superadmin_password", "" + superadmin_password);
		mydata.put("superadmin_email", "" + superadmin_email);
		mydata.put("hostingtype", "" + hostingtype);
		mydata.put("hostinggroup", "" + hostinggroup);
		mydata.put("scheduled_last", "" + scheduled_last);
		mydata.put("scheduled_publish", "" + scheduled_publish);
		mydata.put("scheduled_notify", "" + scheduled_notify);
		mydata.put("scheduled_unpublish", "" + scheduled_unpublish);
		mydata.put("scheduled_publish_email", "" + scheduled_publish_email);
		mydata.put("scheduled_notify_email", "" + scheduled_notify_email);
		mydata.put("scheduled_unpublish_email", "" + scheduled_unpublish_email);
		db.insert("hosting", mydata);
		updateAliases(db);
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("user_id", "" + Common.integernumber(user_id));
			mydata.put("client_address", "" + client_address);
			mydata.put("client_urlrootpath", "" + client_urlrootpath);
			mydata.put("client_database", "" + client_database);
			mydata.put("personal_license", "" + personal_license);
			mydata.put("professional_license", "" + professional_license);
			mydata.put("enterprise_license", "" + enterprise_license);
			mydata.put("hosting_license", "" + hosting_license);
			mydata.put("ecommerce_license", "" + ecommerce_license);
			mydata.put("community_license", "" + community_license);
			mydata.put("databases_license", "" + databases_license);
			mydata.put("statistics_license", "" + statistics_license);
			mydata.put("experience_license", "" + experience_license);
			mydata.put("superadmin", "" + superadmin);
			mydata.put("superadmin_password", "" + superadmin_password);
			mydata.put("superadmin_email", "" + superadmin_email);
			mydata.put("hostingtype", "" + hostingtype);
			mydata.put("hostinggroup", "" + hostinggroup);
			mydata.put("scheduled_last", "" + scheduled_last);
			mydata.put("scheduled_publish", "" + scheduled_publish);
			mydata.put("scheduled_notify", "" + scheduled_notify);
			mydata.put("scheduled_unpublish", "" + scheduled_unpublish);
			mydata.put("scheduled_publish_email", "" + scheduled_publish_email);
			mydata.put("scheduled_notify_email", "" + scheduled_notify_email);
			mydata.put("scheduled_unpublish_email", "" + scheduled_unpublish_email);
			db.update("hosting", "id", id, mydata);
			updateAliases(db);
		}
	}



	public void updateAliases(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals("")) && (! client_urlrootpath.equals("")) && (! client_database.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("user_id", "" + Common.integernumber(user_id));
			mydata.put("personal_license", "" + personal_license);
			mydata.put("professional_license", "" + professional_license);
			mydata.put("enterprise_license", "" + enterprise_license);
			mydata.put("hosting_license", "" + hosting_license);
			mydata.put("ecommerce_license", "" + ecommerce_license);
			mydata.put("community_license", "" + community_license);
			mydata.put("databases_license", "" + databases_license);
			mydata.put("statistics_license", "" + statistics_license);
			mydata.put("experience_license", "" + experience_license);
			mydata.put("superadmin", "" + superadmin);
			mydata.put("superadmin_password", "" + superadmin_password);
			mydata.put("superadmin_email", "" + superadmin_email);
			mydata.put("hostingtype", "" + hostingtype);
			mydata.put("hostinggroup", "" + hostinggroup);
			mydata.put("scheduled_last", "" + scheduled_last);
			mydata.put("scheduled_publish", "" + scheduled_publish);
			mydata.put("scheduled_notify", "" + scheduled_notify);
			mydata.put("scheduled_unpublish", "" + scheduled_unpublish);
			mydata.put("scheduled_publish_email", "" + scheduled_publish_email);
			mydata.put("scheduled_notify_email", "" + scheduled_notify_email);
			mydata.put("scheduled_unpublish_email", "" + scheduled_unpublish_email);
			db.updateWhere("hosting", "client_urlrootpath='" + Common.SQL_clean(client_urlrootpath) + "' and client_database='" + Common.SQL_clean(client_database) + "'", mydata);
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete("hosting", "id", id);
		}
	}



	public void closeRecords(DB db) {
		if (db == null) return;
		if (rs != null) db.closeRecords(rs);
		rs = null;
//		init();
	}



	public boolean records(DB db, String SQL) {
		if ((db == null) || db.isClosed()) return false;
		if ((SQL != null) && (! SQL.equals(""))) {
			rs = db.records(SQL);
			return true;
		} else {
			HashMap<String,String> row = db.records(rs);
			if (row != null) {
				getRecord(db, row);
				return true;
			} else {
				init();
				return false;
			}
		}
	}



	public void schedule(Configuration myconfig, DB db) {
		if (! getScheduledPublish().equals("")) {
			if ((getScheduledPublish().compareTo(myconfig.get(db, "scheduled_next")) < 0) || (myconfig.get(db, "scheduled_next").equals(""))) {
				myconfig.set(db, "scheduled_next", getScheduledPublish());
			}
		}
		if (! getScheduledNotify().equals("")) {
			if ((getScheduledNotify().compareTo(myconfig.get(db, "scheduled_next")) < 0) || (myconfig.get(db, "scheduled_next").equals(""))) {
				myconfig.set(db, "scheduled_next", getScheduledNotify());
			}
		}
		if (! getScheduledUnpublish().equals("")) {
			if ((getScheduledUnpublish().compareTo(myconfig.get(db, "scheduled_next")) < 0) || (myconfig.get(db, "scheduled_next").equals(""))) {
				myconfig.set(db, "scheduled_next", getScheduledUnpublish());
			}
		}
	}



	static public String template_select_options(DB db, String selected) {
		return select_options_where(db, selected, "where " + db.is_blank("superadmin_email") + "");
	}



	static public String select_options_where(DB db, String selected, String SQLwhere) {
		return Common.select_options_where(db, "hosting", "client_address", selected, SQLwhere);
	}



	public String Hostinggroup_select_options(DB db, String selected) {
		Hostinggroup hostinggroup = new Hostinggroup();
		return hostinggroup.select_options(db, selected);
	}



	public String Hostingtype_select_options(DB db, String selected) {
		Hostingtype hostingtype = new Hostingtype();
		return hostingtype.select_options(db, selected);
	}



	public String displayStatus() {
		String now = Common.strftime("%Y-%m-%d %H:%M:%S", new Date());
		String status = "";
		if ((! scheduled_publish.equals("")) && (scheduled_publish.compareTo(now) > 0)) {
			status = "<font size=\"1\" color=\"#0000FF\">" + text.display("hosting.status.pending") + "</font>";
		} else if (((! scheduled_notify.equals("")) && (scheduled_notify.compareTo(now) <= 0)) && ((! scheduled_unpublish.equals("")) && (scheduled_unpublish.compareTo(now) > 0))) {
			status = "<font size=\"1\" color=\"orange\">" + text.display("hosting.status.expiring") + "</font>";
		} else if (((scheduled_publish.equals("")) || (scheduled_publish.compareTo(now) <= 0)) && ((scheduled_unpublish.equals("")) || (scheduled_unpublish.compareTo(now) > 0))) {
			status = "<font size=\"1\" color=\"#00C000\">" + text.display("hosting.status.active") + "</font>";
		} else if ((! scheduled_unpublish.equals("")) && (scheduled_unpublish.compareTo(now) <= 0)) {
			status = "<font size=\"1\" color=\"#000000\">" + text.display("hosting.status.expired") + "</font>";
		}
		return status;
	}



	public String getId() {
		return id;
	}
	public void setId(String value) {
		id = value;
	}



	public String getUserId() {
		return user_id;
	}
	public void setUserId(String value) {
		user_id = value;
	}



	public String getClientAddress() {
		return client_address;
	}
	public void setClientAddress(String value) {
		client_address = value;
	}



	public String getClientURLrootpath() {
		return client_urlrootpath;
	}
	public void setClientURLrootpath(String value) {
		client_urlrootpath = value;
	}



	public String getClientDatabase() {
		return client_database;
	}
	public void setClientDatabase(String value) {
		client_database = value;
	}



	public String getPersonalLicense() {
		return personal_license;
	}
	public void setPersonalLicense(String value) {
		personal_license = value;
	}



	public String getProfessionalLicense() {
		return professional_license;
	}
	public void setProfessionalLicense(String value) {
		professional_license = value;
	}



	public String getEnterpriseLicense() {
		return enterprise_license;
	}
	public void setEnterpriseLicense(String value) {
		enterprise_license = value;
	}



	public String getHostingLicense() {
		return hosting_license;
	}
	public void setHostingLicense(String value) {
		hosting_license = value;
	}



	public String getEcommerceLicense() {
		return ecommerce_license;
	}
	public void setEcommerceLicense(String value) {
		ecommerce_license = value;
	}



	public String getCommunityLicense() {
		return community_license;
	}
	public void setCommunityLicense(String value) {
		community_license = value;
	}



	public String getDatabasesLicense() {
		return databases_license;
	}
	public void setDatabasesLicense(String value) {
		databases_license = value;
	}



	public String getStatisticsLicense() {
		return statistics_license;
	}
	public void setStatisticsLicense(String value) {
		statistics_license = value;
	}



	public String getCustomerExperienceLicense() {
		return experience_license;
	}
	public void setCustomerExperienceLicense(String value) {
		experience_license = value;
	}



	public String getSuperadmin() {
		return superadmin;
	}
	public void setSuperadmin(String value) {
		superadmin = value;
	}



	public String getSuperadminPassword() {
		return superadmin_password;
	}
	public void setSuperadminPassword(String value) {
		superadmin_password = value;
	}



	public String getSuperadminEmail() {
		return superadmin_email;
	}
	public void setSuperadminEmail(String value) {
		superadmin_email = value;
	}



	public String getHostingtype() {
		return hostingtype;
	}
	public void setHostingtype(String value) {
		hostingtype = value;
	}



	public String getHostinggroup() {
		return hostinggroup;
	}
	public void setHostinggroup(String value) {
		hostinggroup = value;
	}



	public String getScheduledLast() {
		return scheduled_last;
	}
	public void setScheduledLast(String value) {
		scheduled_last = value;
	}



	public String getScheduledPublish() {
		return scheduled_publish;
	}
	public void setScheduledPublish(String value) {
		scheduled_publish = value;
	}



	public String getScheduledNotify() {
		return scheduled_notify;
	}
	public void setScheduledNotify(String value) {
		scheduled_notify = value;
	}



	public String getScheduledUnpublish() {
		return scheduled_unpublish;
	}
	public void setScheduledUnpublish(String value) {
		scheduled_unpublish = value;
	}



	public String getScheduledPublishEmail() {
		return scheduled_publish_email;
	}
	public void setScheduledPublishEmail(String value) {
		scheduled_publish_email = value;
	}



	public String getScheduledNotifyEmail() {
		return scheduled_notify_email;
	}
	public void setScheduledNotifyEmail(String value) {
		scheduled_notify_email = value;
	}



	public String getScheduledUnpublishEmail() {
		return scheduled_unpublish_email;
	}
	public void setScheduledUnpublishEmail(String value) {
		scheduled_unpublish_email = value;
	}



}
