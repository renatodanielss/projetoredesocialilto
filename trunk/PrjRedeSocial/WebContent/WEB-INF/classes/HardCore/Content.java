package HardCore;

import java.io.*;
import java.io.File;
import java.lang.Integer;
import java.sql.*;
import java.text.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.regex.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Content {
	static public boolean DEBUG = false;
	public boolean _DEBUG_ = false;
	static public boolean METAINFO = true;
	public boolean _METAINFO_ = true;
	private HashMap<String,Content[]> element_content = new HashMap<String,Content[]>();
	private String archiveid = "";
	private String id = "";
	private int locked = 0;
	private int locked_user = 0;
	private int locked_creator = 0;
	private int locked_developer = 0;
	private int locked_editor = 0;
	private int locked_publisher = 0;
	private int locked_administrator = 0;
	private int locked_schedule = 0;
	private int locked_unschedule = 0;
	private String created = "";
	private String updated = "";
	private String published = "";
	private String unpublished = "";
	private String created_by = "";
	private String updated_by = "";
	private String published_by = "";
	private String unpublished_by = "";
	private String scheduled_publish = "";
	private String scheduled_unpublish = "";
	private String requested_publish = "";
	private String requested_unpublish = "";
	private String revision = "";
	private String device = "";
	private String usersegment = "";
	private String usertest = "";
	private String heatmap = "";
	private String heatmapalign = "";
	private String usagelog = "";
	private String version = "";
	private String version_master = "";
	private String status = "";
	private String status_by = "";
	private String title = "";
	private String searchable = "";
	private String menuitem = "";
	private String author = "";
	private String keywords = "";
	private String description = "";
	private String summary = "";
	private String image1 = "";
	private String image2 = "";
	private String image3 = "";
	private String file1 = "";
	private String file2 = "";
	private String file3 = "";
	private String link1 = "";
	private String link2 = "";
	private String link3 = "";
	private String metainfo = "";
	private String segmentation = "";
	private String htmlattributes = "";
	private String htmlheader = "";
	private String htmlbodyonload = "";
	private String url = "";
	private String contentformat = "";
	private String content = "";
	private String doctype = "";
	private String template = "";
	private String stylesheet = "";
	private String scripts = "";
	private String contentpackage = "";
	private String contentbundle = "";
	private String contentclass = "";
	private String contenttype = "";
	private String contentgroup = "";
	private String users_users = "";
	private String users_type = "";
	private String users_group = "";
	private String creators_users = "";
	private String creators_type = "";
	private String creators_group = "";
	private String developers_users = "";
	private String developers_type = "";
	private String developers_group = "";
	private String editors_users = "";
	private String editors_type = "";
	private String editors_group = "";
	private String publishers_users = "";
	private String publishers_type = "";
	private String publishers_group = "";
	private String administrators_users = "";
	private String administrators_type = "";
	private String administrators_group = "";
	private String checkedout = "";
	private String page_up = "";
	private String page_top = "";
	private String page_next = "";
	private String page_previous = "";
	private String page_last = "";
	private String page_first = "";
	private String related = "";
	private String upload_filename = "";
	private String server_filename = "";
	private boolean user = false;
	private boolean creator = false;
	private boolean editor = false;
	private boolean publisher = false;
	private boolean developer = false;
	private boolean administrator = false;
	private HashMap<String,String> element = new HashMap<String,String>();
	private String product_code = "";
	private String product_currency = "";
	private String product_cost = "";
	private String product_price = "";
	private String product_period = "";
	private String product_weight = "";
	private String product_volume = "";
	private String product_width = "";
	private String product_height = "";
	private String product_depth = "";
	private String product_stock = ""; // DEPRECATED
	private String product_comment = ""; // DEPRECATED
	private String product_email = "";
	private String product_url = "";
	private String product_brand = "";
	private String product_colour = "";
	private String product_size = "";
	private String product_info = "";
	private String product_options = "";
	private String product_delivery = "";
	private String product_stock_amount = "";
	private String product_restocked_amount = "";
	private String product_stock_text = "";
	private String product_lowstock_amount = "";
	private String product_lowstock_text = "";
	private String product_nostock_order = "";
	private String product_nostock_text = "";
	private String product_location = "";
	private String product_user = "";
	private String product_page = "";
	private String product_program = "";
	private String product_hosting = "";
	private String product_content = "";
	private String product_contentgroup = "";
	private String product_contenttype = "";
	private String product_imagegroup = "";
	private String product_imagetype = "";
	private String product_filegroup = "";
	private String product_filetype = "";
	private String product_linkgroup = "";
	private String product_linktype = "";
	private String product_productgroup = "";
	private String product_producttype = "";
	private String product_usergroup = "";
	private String product_usertype = "";
	private String contentgroup_users_users = "";
	private String contentgroup_users_type = "";
	private String contentgroup_users_group = "";
	private String contentgroup_creators_users = "";
	private String contentgroup_creators_type = "";
	private String contentgroup_creators_group = "";
	private String contentgroup_developers_users = "";
	private String contentgroup_developers_type = "";
	private String contentgroup_developers_group = "";
	private String contentgroup_editors_users = "";
	private String contentgroup_editors_type = "";
	private String contentgroup_editors_group = "";
	private String contentgroup_publishers_users = "";
	private String contentgroup_publishers_type = "";
	private String contentgroup_publishers_group = "";
	private String contentgroup_administrators_users = "";
	private String contentgroup_administrators_type = "";
	private String contentgroup_administrators_group = "";
	private String contentgroup_doctype = "";
	private String contentgroup_template = "";
	private String contentgroup_stylesheet = "";
	private String contenttype_users_users = "";
	private String contenttype_users_type = "";
	private String contenttype_users_group = "";
	private String contenttype_creators_users = "";
	private String contenttype_creators_type = "";
	private String contenttype_creators_group = "";
	private String contenttype_developers_users = "";
	private String contenttype_developers_type = "";
	private String contenttype_developers_group = "";
	private String contenttype_editors_users = "";
	private String contenttype_editors_type = "";
	private String contenttype_editors_group = "";
	private String contenttype_publishers_users = "";
	private String contenttype_publishers_type = "";
	private String contenttype_publishers_group = "";
	private String contenttype_administrators_users = "";
	private String contenttype_administrators_type = "";
	private String contenttype_administrators_group = "";
	private String contenttype_doctype = "";
	private String contenttype_template = "";
	private String contenttype_stylesheet = "";

	private	Statement rs = null;
	private HashMap<String,LinkedList<HashMap<String,String>>> select_options_cache = new HashMap<String,LinkedList<HashMap<String,String>>>();
	private HashMap<String,String> elements_cache = null;
	public Text text;
	private String title_prefix = "";
	private String title_suffix = "";
	private String display_currencytitle = "";
	private String display_currency = "";
	private String display_price = "";
	public HashMap<String,Boolean> editable = new HashMap<String,Boolean>();



	public Content() {
		init();
	}



	public Content(Text mytext) {
		if (mytext != null) text = mytext;
		init();
	}



	public void debug(boolean mode) {
		_DEBUG_ = mode;
	}



	public void init() {
		_DEBUG_ = DEBUG;
		_METAINFO_ = METAINFO;
		id = "";
		locked = 0;
		locked_user = 0;
		locked_creator = 0;
		locked_developer = 0;
		locked_editor = 0;
		locked_publisher = 0;
		locked_administrator = 0;
		locked_schedule = 0;
		locked_unschedule = 0;
		created = "";
		updated = "";
		published = "";
		unpublished = "";
		created_by = "";
		updated_by = "";
		published_by = "";
		unpublished_by = "";
		scheduled_publish = "";
		scheduled_unpublish = "";
		requested_publish = "";
		requested_unpublish = "";
		revision = "";
		device = "";
		usersegment = "";
		usertest = "";
		heatmap = "";
		heatmapalign = "";
		usagelog = "";
		version = "";
		version_master = "";
		status = "";
		status_by = "";
		title = "";
		searchable = "";
		menuitem = "";
		url = "";
		upload_filename = "";
		server_filename = "";
		author = "";
		keywords = "";
		description = "";
		summary = "";
		image1 = "";
		image2 = "";
		image3 = "";
		file1 = "";
		file2 = "";
		file3 = "";
		link1 = "";
		link2 = "";
		link3 = "";
		metainfo = "";
		segmentation = "";
		htmlattributes = "";
		htmlheader = "";
		htmlbodyonload = "";
		contentformat = "";
		content = "";
		doctype = "";
		template = "";
		stylesheet = "";
		scripts = "";
		contentpackage = "";
		contentbundle = "";
		contentclass = "";
		contenttype = "";
		contentgroup = "";
		users_users = "";
		users_type = "";
		users_group = "";
		creators_users = "";
		creators_type = "";
		creators_group = "";
		developers_users = "";
		developers_type = "";
		developers_group = "";
		editors_users = "";
		editors_type = "";
		editors_group = "";
		publishers_users = "";
		publishers_type = "";
		publishers_group = "";
		administrators_users = "";
		administrators_type = "";
		administrators_group = "";
		checkedout = "";
		page_up = "";
		page_top = "";
		page_next = "";
		page_previous = "";
		page_last = "";
		page_first = "";
		related = "";
		user = false;
		creator = false;
		editor = false;
		publisher = false;
		developer = false;
		administrator = false;
		element = new HashMap<String,String>();
		product_code = "";
		product_currency = "";
		product_cost = "";
		product_price = "";
		product_period = "";
		product_weight = "";
		product_volume = "";
		product_width = "";
		product_height = "";
		product_depth = "";
		product_stock = ""; // DEPRECATED
		product_comment = ""; // DEPRECATED
		product_email = "";
		product_url = "";
		product_brand = "";
		product_colour = "";
		product_size = "";
		product_info = "";
		product_options = "";
		product_delivery = "";
		product_stock_amount = "";
		product_restocked_amount = "";
		product_stock_text = "";
		product_lowstock_amount = "";
		product_lowstock_text = "";
		product_nostock_order = "";
		product_nostock_text = "";
		product_location = "";
		product_user = "";
		product_page = "";
		product_program = "";
		product_hosting = "";
		product_content = "";
		product_contentgroup = "";
		product_contenttype = "";
		product_imagegroup = "";
		product_imagetype = "";
		product_filegroup = "";
		product_filetype = "";
		product_linkgroup = "";
		product_linktype = "";
		product_productgroup = "";
		product_producttype = "";
		product_usergroup = "";
		product_usertype = "";
		contentgroup_users_users = "";
		contentgroup_users_type = "";
		contentgroup_users_group = "";
		contentgroup_creators_users = "";
		contentgroup_creators_type = "";
		contentgroup_creators_group = "";
		contentgroup_developers_users = "";
		contentgroup_developers_type = "";
		contentgroup_developers_group = "";
		contentgroup_editors_users = "";
		contentgroup_editors_type = "";
		contentgroup_editors_group = "";
		contentgroup_publishers_users = "";
		contentgroup_publishers_type = "";
		contentgroup_publishers_group = "";
		contentgroup_administrators_users = "";
		contentgroup_administrators_type = "";
		contentgroup_administrators_group = "";
		contentgroup_doctype = "";
		contentgroup_template = "";
		contentgroup_stylesheet = "";
		contenttype_users_users = "";
		contenttype_users_type = "";
		contenttype_users_group = "";
		contenttype_creators_users = "";
		contenttype_creators_type = "";
		contenttype_creators_group = "";
		contenttype_developers_users = "";
		contenttype_developers_type = "";
		contenttype_developers_group = "";
		contenttype_editors_users = "";
		contenttype_editors_type = "";
		contenttype_editors_group = "";
		contenttype_publishers_users = "";
		contenttype_publishers_type = "";
		contenttype_publishers_group = "";
		contenttype_administrators_users = "";
		contenttype_administrators_type = "";
		contenttype_administrators_group = "";
		contenttype_doctype = "";
		contenttype_template = "";
		contenttype_stylesheet = "";
		title_prefix = "";
		title_suffix = "";
	}



	public boolean exists(DB db, String myid, String table, String column) {
		return exists(db, myid, table, column, "", "", "", "", "", null);
	}
	public boolean exists(DB db, String myid, String table, String column, String session_version, String default_version) {
		return exists(db, myid, table, column, session_version, default_version, "", "", "", null);
	}
	public boolean exists(DB db, String myid, String table, String column, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, Session session) {
		if (db == null) return false;
		if (("" + table).equals("")) {
			table = "content";
		}
		if (("" + column).equals("")) {
			column = "id";
		}

		if ((db != null) && (myid != null) && (! ("" + myid).equals("null")) && (! ("" + myid).equals("")) && (! ("" + myid).equals("0")) && (! ("" + myid).equals("-1"))) {
			String SQL = "";
			String SQL2 = "";
			if ((! myid.equals(""+Common.integer(myid))) && (! Common.SQL_clean(myid).equals(""))) {
				if (db.db_type(db.getDatabase()).equals("mssql")) {
					SQL = "select * from " + Common.SQL_clean(table) + " where (substring(server_filename,1,250)=" + db.quote(Common.SQL_clean(myid)) + ") or (title=" + db.quote(Common.SQL_clean(myid)) + ") order by id";
				} else if (db.db_type(db.getDatabase()).equals("oracle")) {
					SQL = "select * from " + Common.SQL_clean(table) + " where (to_char(server_filename)=" + db.quote(Common.SQL_clean(myid)) + ") or (title=" + db.quote(Common.SQL_clean(myid)) + ") order by id";
				} else if (db.db_type(db.getDatabase()).equals("db2")) {
					SQL = "select * from " + Common.SQL_clean(table) + " where (varchar(server_filename,250)=" + db.quote(Common.SQL_clean(myid)) + ") or (title=" + db.quote(Common.SQL_clean(myid)) + ") order by id";
				} else {
					SQL = "select * from " + Common.SQL_clean(table) + " where (server_filename=" + db.quote(Common.SQL_clean(myid)) + ") or (title=" + db.quote(Common.SQL_clean(myid)) + ") order by id";
				}
			} else if (! session_version.equals("")) {
				SQL = "select * from " + Common.SQL_clean(table) + " where (" + Common.SQL_clean(column) + "=" + Common.integer(myid) + ") or (version_master=" + db.quote("" + Common.integer(myid)) + " and version in (" + db.quote_csv(Common.SQL_clean(session_version)) + ") and device in (null,''," + db.quote_csv(Common.SQL_clean(session_device)) + ")) order by version_master desc, version desc, device desc";
				SQL2 = "select * from " + Common.SQL_clean(table) + " where (" + Common.SQL_clean(column) + "=" + Common.integer(myid) + ") or (version_master=" + db.quote("" + Common.integer(myid)) + " and version in (" + db.quote_csv(Common.SQL_clean(default_version)) + ") and device in (null,''," + db.quote_csv(Common.SQL_clean(session_device)) + ")) order by version_master desc, version desc, device desc";
			} else if (! default_version.equals("")) {
				SQL = "select * from " + Common.SQL_clean(table) + " where (" + Common.SQL_clean(column) + "=" + Common.integer(myid) + ") or (version_master=" + db.quote("" + Common.integer(myid)) + " and version in (" + db.quote_csv(Common.SQL_clean(default_version)) + ") and device in (null,''," + db.quote_csv(Common.SQL_clean(session_device)) + ")) order by version_master desc, version desc, device desc";
			} else if (! session_device.equals("")) {
				SQL = "select * from " + Common.SQL_clean(table) + " where (" + Common.SQL_clean(column) + "=" + Common.integer(myid) + ") or (version_master=" + db.quote("" + Common.integer(myid)) + " and version in (null,'') and device in (null,''," + db.quote_csv(Common.SQL_clean(session_device)) + ")) order by version_master desc, device desc";
			} else {
				SQL = "select * from " + Common.SQL_clean(table) + " where (" + Common.SQL_clean(column) + "=" + Common.integer(myid) + ")";
			}
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				return true;
			} else if (! SQL2.equals("")) {
				row = db.query_record(SQL2);
				if (row != null) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		return false;
	}



	public void scheduled_read(Session session, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String myid, String session_version, String default_version) {
		scheduled_read(db, config, myid, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, "", "", "", session);
	}
	public void scheduled_read(DB db, Configuration config, String myid, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, Session session) {
		read(db, config, myid, "content", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
		previewScheduledQueued(session, config, db);
	}



	public void preview_read(DB db, Configuration config, String myid) {
		preview_read(db, config, myid, "", "", "", "", "", "", "", "", "", "", "", "", null);
	}
	public void preview_read(DB db, Configuration config, String myid, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups) {
		preview_read(db, config, myid, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, "", "", "", "", "", null);
	}
	public void preview_read(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String myid, String session_version, String default_version) {
		preview_read(db, config, myid, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, "", "", "", null);
	}
	public void preview_read(DB db, Configuration config, String myid, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, Session session) {
		read(db, config, myid, "content", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
	}



	public void archive_read(DB db, Configuration config, String myid) {
		archive_read(db, config, myid, "", "", "", "", "", "", "", "", "", "", "", "", null);
	}
	public void archive_read(DB db, Configuration config, String myid, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups) {
		archive_read(db, config, myid, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, "", "", "", "", "", null);
	}
	public void archive_read(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String myid, String session_version, String default_version) {
		archive_read(db, config, myid, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, "", "", "", null);
	}
	public void archive_read(DB db, Configuration config, String myid, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, Session session) {
		read(db, config, myid, "content_archive", "archiveid", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
	}



	public void public_read(DB db, Configuration config, String myid) {
		public_read(db, config, myid, "", "", "", "", "", "", "", "", "", "", "", "", null);
	}
	public void public_read(DB db, Configuration config, String myid, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups) {
		public_read(db, config, myid, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, "", "", "", "", "", null);
	}
	public void public_read(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String myid, String session_version, String default_version) {
		public_read(db, config, myid, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, "", "", "", null);
	}
	public void public_read(DB db, Configuration config, String myid, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, Session session) {
		read(db, config, myid, "content_public", "id", session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session);
	}



	public void read(DB db, Configuration config, String myid) {
		read(db, config, myid, "", "", "", "", "", "", "", "", "", "", "", "", "", "", null);
	}
	public void read(DB db, Configuration config, String myid, String table, String column) {
		read(db, config, myid, table, column, "", "", "", "", "", "", "", "", "", "", "", "", null);
	}
	public void read(DB db, Configuration config, String myid, String table, String column, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups) {
		read(db, config, myid, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, "", "", "", "", "", null);
	}
	public void read(DB db, Configuration config, String myid, String table, String column, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version) {
		read(db, config, myid, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, "", "", "", null);
	}
	public void read(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String myid, String table, String column, String session_version, String default_version) {
		read(db, config, myid, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, "", "", "", null);
	}
	public void read(DB db, Configuration config, String myid, String table, String column, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, Session session) {
		session_usersegments = session_usersegments.replaceAll("(^|,)([^,=]*?)=(0|-[0-9]+)", "");
		String session_usersegments_names = session_usersegments.replaceAll("(^|,)([^,=]*?)=[1-9][0-9]*", "$1$2");
		String session_userteststags = "";
		if (session != null) session_userteststags = session.get("userteststags");
		if (db == null) return;
		if (("" + table).equals("")) {
			table = "content";
		}
		if (("" + column).equals("")) {
			column = "id";
		}
		if ((db != null) && (myid != null) && (! ("" + myid).equals("null")) && (! ("" + myid).equals("")) && (! ("" + myid).equals("0")) && (! ("" + myid).equals("-1"))) {
			String SQL = "";
			String SQL2 = "";
			boolean variants = false;
			if ((! myid.equals(""+Common.integer(myid))) && (! Common.SQL_clean(myid).equals(""))) {
				if (db.db_type(db.getDatabase()).equals("mssql")) {
					SQL = "select * from " + Common.SQL_clean(table) + " where (substring(server_filename,1,250)=" + db.quote(Common.SQL_clean(myid)) + ") or (title=" + db.quote(Common.SQL_clean(myid)) + ") order by id";
				} else if (db.db_type(db.getDatabase()).equals("oracle")) {
					SQL = "select * from " + Common.SQL_clean(table) + " where (to_char(server_filename)=" + db.quote(Common.SQL_clean(myid)) + ") or (title=" + db.quote(Common.SQL_clean(myid)) + ") order by id";
				} else if (db.db_type(db.getDatabase()).equals("db2")) {
					SQL = "select * from " + Common.SQL_clean(table) + " where (varchar(server_filename,250)=" + db.quote(Common.SQL_clean(myid)) + ") or (title=" + db.quote(Common.SQL_clean(myid)) + ") order by id";
				} else {
					SQL = "select * from " + Common.SQL_clean(table) + " where (server_filename=" + db.quote(Common.SQL_clean(myid)) + ") or (title=" + db.quote(Common.SQL_clean(myid)) + ") order by id";
				}
			} else if ((! session_usersegments.equals("")) || (! session_usertests.equals("")) || (! session_userteststags.equals(""))) {
				variants = true;
				if (! session_version.equals("")) {
					SQL = "select * from " + Common.SQL_clean(table) + " where (" + Common.SQL_clean(column) + "=" + Common.integer(myid) + ") or (version_master=" + db.quote("" + Common.integer(myid)) + " and (" + db.is_not_blank("version") + " or " + db.is_not_blank("device") + " or " + db.is_not_blank("usersegment") + " or " + db.is_not_blank("usertest") + ") and ((version is null) or (version in (''," + db.quote_csv(Common.SQL_clean(session_version)) + "))) and ((device is null) or (device in (''," + db.quote_csv(Common.SQL_clean(session_device)) + "))) and ((usersegment is null) or (usersegment in (''," + db.quote_csv(Common.SQL_clean(session_usersegments_names)) + ")))) order by version_master desc, version desc, device desc, usersegment desc, usertest desc";
					SQL2 = "select * from " + Common.SQL_clean(table) + " where (" + Common.SQL_clean(column) + "=" + Common.integer(myid) + ") or (version_master=" + db.quote("" + Common.integer(myid)) + " and (" + db.is_not_blank("version") + " or " + db.is_not_blank("device") + " or " + db.is_not_blank("usersegment") + " or " + db.is_not_blank("usertest") + ") and ((version is null) or (version in (''," + db.quote_csv(Common.SQL_clean(default_version)) + "))) and ((device is null) or (device in (''," + db.quote_csv(Common.SQL_clean(session_device)) + "))) and ((usersegment is null) or (usersegment in (''," + db.quote_csv(Common.SQL_clean(session_usersegments_names)) + ")))) order by version_master desc, version desc, device desc, usersegment desc, usertest desc";
				} else if (! default_version.equals("")) {
					SQL = "select * from " + Common.SQL_clean(table) + " where (" + Common.SQL_clean(column) + "=" + Common.integer(myid) + ") or (version_master=" + db.quote("" + Common.integer(myid)) + " and (" + db.is_not_blank("version") + " or " + db.is_not_blank("device") + " or " + db.is_not_blank("usersegment") + " or " + db.is_not_blank("usertest") + ") and ((version is null) or (version in (''," + db.quote_csv(Common.SQL_clean(default_version)) + "))) and ((device is null) or (device in (''," + db.quote_csv(Common.SQL_clean(session_device)) + "))) and ((usersegment is null) or (usersegment in (''," + db.quote_csv(Common.SQL_clean(session_usersegments_names)) + ")))) order by version_master desc, version desc, device desc, usersegment desc, usertest desc";
				} else {
					SQL = "select * from " + Common.SQL_clean(table) + " where (" + Common.SQL_clean(column) + "=" + Common.integer(myid) + ") or (version_master=" + db.quote("" + Common.integer(myid)) + " and (" + db.is_not_blank("version") + " or " + db.is_not_blank("device") + " or " + db.is_not_blank("usersegment") + " or " + db.is_not_blank("usertest") + ") and ((version is null) or (version in (''))) and ((device is null) or (device in (''," + db.quote_csv(Common.SQL_clean(session_device)) + "))) and ((usersegment is null) or (usersegment in (''," + db.quote_csv(Common.SQL_clean(session_usersegments_names)) + ")))) order by version_master desc, device desc, usersegment desc, usertest desc";
				}
			} else if (! session_device.equals("")) {
				if (! session_version.equals("")) {
					SQL = "select * from " + Common.SQL_clean(table) + " where (" + Common.SQL_clean(column) + "=" + Common.integer(myid) + ") or (version_master=" + db.quote("" + Common.integer(myid)) + " and (" + db.is_not_blank("version") + " or " + db.is_not_blank("device") + ") and ((version is null) or (version in (''," + db.quote_csv(Common.SQL_clean(session_version)) + "))) and ((device is null) or (device in (''," + db.quote_csv(Common.SQL_clean(session_device)) + "))) and (" + db.is_blank("usersegment") + ") and (" + db.is_blank("usertest") + ")) order by version_master desc, version desc, device desc";
					SQL2 = "select * from " + Common.SQL_clean(table) + " where (" + Common.SQL_clean(column) + "=" + Common.integer(myid) + ") or (version_master=" + db.quote("" + Common.integer(myid)) + " and (" + db.is_not_blank("version") + " or " + db.is_not_blank("device") + ") and ((version is null) or (version in (''," + db.quote_csv(Common.SQL_clean(default_version)) + "))) and ((device is null) or (device in (''," + db.quote_csv(Common.SQL_clean(session_device)) + "))) and (" + db.is_blank("usersegment") + ") and (" + db.is_blank("usertest") + ")) order by version_master desc, version desc, device desc";
				} else if (! default_version.equals("")) {
					SQL = "select * from " + Common.SQL_clean(table) + " where (" + Common.SQL_clean(column) + "=" + Common.integer(myid) + ") or (version_master=" + db.quote("" + Common.integer(myid)) + " and (" + db.is_not_blank("version") + " or " + db.is_not_blank("device") + ") and ((version is null) or (version in (''," + db.quote_csv(Common.SQL_clean(default_version)) + "))) and ((device is null) or (device in (''," + db.quote_csv(Common.SQL_clean(session_device)) + "))) and (" + db.is_blank("usersegment") + ") and (" + db.is_blank("usertest") + ")) order by version_master desc, version desc, device desc";
				} else {
					SQL = "select * from " + Common.SQL_clean(table) + " where (" + Common.SQL_clean(column) + "=" + Common.integer(myid) + ") or (version_master=" + db.quote("" + Common.integer(myid)) + " and (" + db.is_not_blank("version") + " or " + db.is_not_blank("device") + ") and ((version is null) or (version in (''))) and ((device is null) or (device in (''," + db.quote_csv(Common.SQL_clean(session_device)) + "))) and (" + db.is_blank("usersegment") + ") and (" + db.is_blank("usertest") + ")) order by version_master desc, device desc";
				}
			} else {
				if (! session_version.equals("")) {
					SQL = "select * from " + Common.SQL_clean(table) + " where (" + Common.SQL_clean(column) + "=" + Common.integer(myid) + ") or (version_master=" + db.quote("" + Common.integer(myid)) + " and version in (" + db.quote_csv(Common.SQL_clean(session_version)) + ")) order by version_master desc, version";
					SQL2 = "select * from " + Common.SQL_clean(table) + " where (" + Common.SQL_clean(column) + "=" + Common.integer(myid) + ") or (version_master=" + db.quote("" + Common.integer(myid)) + " and version in (" + db.quote_csv(Common.SQL_clean(default_version)) + ")) order by version_master desc, version";
				} else if (! default_version.equals("")) {
					SQL = "select * from " + Common.SQL_clean(table) + " where (" + Common.SQL_clean(column) + "=" + Common.integer(myid) + ") or (version_master=" + db.quote("" + Common.integer(myid)) + " and version in (" + db.quote_csv(Common.SQL_clean(default_version)) + ")) order by version_master desc, version";
				} else {
					SQL = "select * from " + Common.SQL_clean(table) + " where (" + Common.SQL_clean(column) + "=" + Common.integer(myid) + ")";
				}
			}

			if (! variants) {
@SuppressWarnings("unchecked")
				HashMap<String,String> row = (HashMap<String,String>)Cache.get(db, Common.SQL_clean(table), "" + Common.SQL_clean(column) + ":" + Common.integer(myid) + ":" + Common.SQL_clean(session_version) + ":" + Common.SQL_clean(default_version) + ":" + Common.SQL_clean(session_device));
				if (row == null) {
					row = db.query_record(SQL);
					if (row != null) Cache.set(db, Common.SQL_clean(table), "" + Common.SQL_clean(column) + ":" + Common.integer(myid) + ":" + Common.SQL_clean(session_version) + ":" + Common.SQL_clean(default_version) + ":" + Common.SQL_clean(session_device), row);
				}
				if (row != null) {
					getRecord(db, config, row, table, column, myid);
					if (column.equals("id")) {
						id = "" + row.get("id");
					} else if (column.equals("archiveid")) {
						archiveid = "" + row.get("archiveid");
					}
				} else if (! SQL2.equals("")) {
					row = db.query_record(SQL2);
					if (row != null) Cache.set(db, Common.SQL_clean(table), "" + Common.SQL_clean(column) + ":" + Common.integer(myid) + ":" + Common.SQL_clean(session_version) + ":" + Common.SQL_clean(default_version), row);
					if (row != null) {
						getRecord(db, config, row, table, column, myid);
						if (column.equals("id")) {
							id = "" + row.get("id");
						} else if (column.equals("archiveid")) {
							archiveid = "" + row.get("archiveid");
						}
					}
				}
			} else {
@SuppressWarnings("unchecked")
				LinkedHashMap<String,HashMap<String,String>> rows = (LinkedHashMap<String,HashMap<String,String>>)Cache.get(db, Common.SQL_clean(table), "" + Common.SQL_clean(column) + ":" + Common.integer(myid) + ":" + Common.SQL_clean(session_version) + ":" + Common.SQL_clean(default_version) + ":" + Common.SQL_clean(session_device) + ":" + Common.SQL_clean(session_usersegments_names));
				if (rows == null) {
					rows = db.query_records(SQL);
					if (rows != null) Cache.set(db, Common.SQL_clean(table), "" + Common.SQL_clean(column) + ":" + Common.integer(myid) + ":" + Common.SQL_clean(session_version) + ":" + Common.SQL_clean(default_version) + ":" + Common.SQL_clean(session_device) + ":" + Common.SQL_clean(session_usersegments_names), rows);
				}
				if (rows != null) {
					read_variants(db, config, myid, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session, rows);
				} else if (! SQL2.equals("")) {
					rows = db.query_records(SQL2);
					if (rows != null) Cache.set(db, Common.SQL_clean(table), "" + Common.SQL_clean(column) + ":" + Common.integer(myid) + ":" + Common.SQL_clean(session_version) + ":" + Common.SQL_clean(default_version) + ":" + Common.SQL_clean(session_device) + ":" + Common.SQL_clean(session_usersegments_names), rows);
					if (rows != null) {
						read_variants(db, config, myid, table, column, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, session_version, default_version, session_device, session_usersegments, session_usertests, session, rows);
					}
				}
			}
		}
		getAccessRestrictions(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config);
	}



	public void read_variants(DB db, Configuration config, String myid, String table, String column, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, String session_version, String default_version, String session_device, String session_usersegments, String session_usertests, Session session, LinkedHashMap<String,HashMap<String,String>> rows) {
		if ((session != null) && (session.getSession() == null)) session = null;
		Segment mysegments = new Segment(session_usersegments);
		Usertest myusertests = new Usertest(session_usertests);
		Usertest myuserteststags = new Usertest();
		if (session != null) myuserteststags = new Usertest(session.get("userteststags"));
		String session_visitor = "";
		if (session != null) session_visitor = session.get("visitor");
		String matched_master = "";
		String matched_version = "";
		String matched_device = "";
		String matched_usersegment = "";
		int matched_weight = -1;
		String matched_usertest = null;
		String original_usertest = null;
		double matched_random_count = 0;
		HashMap<String,String> row = new HashMap<String,String>();
		HashMap<String,Usertest> usertests = new HashMap<String,Usertest>();
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		double random_testsubject = Math.random() * 100;
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			Object rowskey = rowsIterator.next();
			row = (HashMap<String,String>)rows.get(rowskey);
			if (
				((matched_version.equals("")) || (matched_version.equals(row.get("version"))))
				&&
				((matched_device.equals("")) || (matched_device.equals(row.get("device"))))
				&&
				((matched_weight < mysegments.getWeight("" + row.get("usersegment"))) || (matched_usersegment.equals(row.get("usersegment"))))
				&&
				((matched_usertest == null) || (matched_usertest.equals("")) || (! row.get("usertest").equals("")) || (matched_master.equals(row.get("id"))))
			) {
				matched_master = "" + row.get("version_master");
				matched_version = "" + row.get("version");
				matched_device = "" + row.get("device");
				matched_usersegment = "" + row.get("usersegment");
				matched_weight = mysegments.getWeight("" + row.get("usersegment"));
				String myusertest = ("" + row.get("usertest")).replaceAll("::.*$", "");
				String myvariant = ("" + row.get("usertest")).replaceAll("^.*::", "");
				if ((getId().equals("")) && (myid.equals("" + row.get("id")))) {
					// specific content - use this if nothing else
					getRecord(db, config, row, table, column, myid);
				}
				if ((! myusertest.equals("")) && (usertests.get(myusertest) == null)) {
					usertests.put(myusertest, new Usertest());
					usertests.get(myusertest).readUsertest(db, myusertest);
				}
				if ((myusertest.equals("")) || ((usertests.get(myusertest).getActive().equals("1")) && ((usertests.get(myusertest).getScheduled().equals("")) || (usertests.get(myusertest).getScheduled().compareTo(now) <= 0)) && ((usertests.get(myusertest).getUnscheduled().equals("")) || (usertests.get(myusertest).getUnscheduled().compareTo(now) >= 0)) && ((usertests.get(myusertest).getVisitors().equals("all")) || (usertests.get(myusertest).getVisitors().equals(session_visitor))))) {
					if (! myusertest.equals("")) {
						original_usertest = myusertest + "=" + "";
					}
					if ((myusertest.equals("")) && (original_usertest != null) && (session_usertests.matches("(^|^.*,)" + original_usertest + "(,.*$|$)"))) {
						// existing matching original - keep using this
						getRecord(db, config, row, table, column, myid);
						break;
					} else if (session_usertests.matches("(^|^.*,)" + myusertest + "=" + myvariant + "(,.*$|$)")) {
						// existing matching usertest - keep using this
						getRecord(db, config, row, table, column, myid);
						break;
					} else if (session_usertests.matches("(^|^.*,)" + myusertest + "=" + "(.*?)" + "(,.*$|$)")) {
						// existing non-matching usertest - ignore this

					} else if ((! myusertest.equals("")) && (myuserteststags.exists(myusertest)) && (myuserteststags.get(myusertest).equals(myvariant))) {
						// existing matching prepared usertest - start using this
						matched_usertest = "" + myusertest + "=" + myvariant;
						getRecord(db, config, row, table, column, myid);
						break;

					} else if ((! myusertest.equals("")) && (myuserteststags.exists(myusertest)) && (! myuserteststags.get(myusertest).equals(myvariant))) {
						// existing non-matching prepared usertest - ignore this

					} else if (myusertest.equals("")) {
						// default content - use this
						matched_usertest = original_usertest;
						getRecord(db, config, row, table, column, myid);
						// do not break - there may be more higher weighted matching usersegments
						//break;

					} else if (session != null) {
						if ((myusertest.equals("")) || ((usertests.get(myusertest).getActive().equals("1")) && ((usertests.get(myusertest).getScheduled().equals("")) || (usertests.get(myusertest).getScheduled().compareTo(now) <= 0)) && ((usertests.get(myusertest).getUnscheduled().equals("")) || (usertests.get(myusertest).getUnscheduled().compareTo(now) >= 0)))) {
							double myprobability = 100;
							if (! myusertest.equals("")) {
								if ((usertests.get(myusertest).getVisitors().equals("new")) && (! session.get("visitor").equals("new"))) {
									myprobability = -1;
								} else if (! usertests.get(myusertest).getProbability().equals("")) {
									myprobability = Common.number(usertests.get(myusertest).getProbability());
								}
							}
							boolean matched_testsubject = (random_testsubject <= myprobability);
							myprobability = 100;
							matched_random_count += 1;
							double myrandom = Math.random() * 100*matched_random_count;
							boolean matched_random = (myrandom <= myprobability);
							if ((matched_testsubject && matched_random) || ((myusertest.equals("")) && ((matched_usertest == null) || (matched_usertest.equals(""))))) {
								// new usertest - start using this
								if (! myusertest.equals("")) {
									matched_usertest = "" + myusertest + "=" + myvariant;
								} else {
									matched_usertest = original_usertest;
								}
								getRecord(db, config, row, table, column, myid);
							}
						}
					}
				} else if (myid.equals("" + row.get("id"))) {
					getRecord(db, config, row, table, column, myid);
				}
			}
		}
		if ((matched_usertest != null) && (session != null)) {
			String myusertest = matched_usertest.replaceAll("=.*$", "");
			String myvariant = matched_usertest.replaceAll("^.*=", "");
//			String mysessionusertests = session.get("usertests");
			String mysessionusertests = session_usertests;
			if (mysessionusertests.matches("(^|^.*,)" + myusertest + "=[^,]*(,.*$|$)")) {
				// ignore - race condition
			} else if (! mysessionusertests.equals("")) {
				session.set("usertests", mysessionusertests + "," + matched_usertest);
			} else {
				session.set("usertests", matched_usertest);
			}
		}
//		if (column.equals("id")) {
//			id = "" + row.get("id");
//		} else if (column.equals("archiveid")) {
//			archiveid = "" + row.get("archiveid");
//		}
	}



	public void getRecord(DB db, Configuration config, HashMap<String,String> record, String table, String column, String record_id) {
		if (db == null) return;
		if (record.get("archiveid") != null) archiveid = "" + record.get("archiveid"); else archiveid = "";
		id = "" + record.get("id");
		if (record.get("locked") == null) {
			locked = 0;
		} else {
			locked = Integer.parseInt("0" + record.get("locked"));
		}
		if (record.get("locked_user") == null) {
			locked_user = 0;
		} else {
			locked_user = Integer.parseInt("0" + record.get("locked_user"));
		}
		if (record.get("locked_creator") == null) {
			locked_creator = 0;
		} else {
			locked_creator = Integer.parseInt("0" + record.get("locked_creator"));
		}
		if (record.get("locked_developer") == null) {
			locked_developer = 0;
		} else {
			locked_developer = Integer.parseInt("0" + record.get("locked_developer"));
		}
		if (record.get("locked_editor") == null) {
			locked_editor = 0;
		} else {
			locked_editor = Integer.parseInt("0" + record.get("locked_editor"));
		}
		if (record.get("locked_publisher") == null) {
			locked_publisher = 0;
		} else {
			locked_publisher = Integer.parseInt("0" + record.get("locked_publisher"));
		}
		if (record.get("locked_administrator") == null) {
			locked_administrator = 0;
		} else {
			locked_administrator = Integer.parseInt("0" + record.get("locked_administrator"));
		}
		if (record.get("locked_schedule") == null) {
			locked_schedule = 0;
		} else {
			locked_schedule = Integer.parseInt("0" + record.get("locked_schedule"));
		}
		if (record.get("locked_unschedule") == null) {
			locked_unschedule = 0;
		} else {
			locked_unschedule = Integer.parseInt("0" + record.get("locked_unschedule"));
		}
		if (record.get("created") != null) created = "" + record.get("created"); else created = "";
		if (record.get("updated") != null) updated = "" + record.get("updated"); else updated = "";
		if (record.get("published") != null) published = "" + record.get("published"); else published = "";
		if (record.get("unpublished") != null) unpublished = "" + record.get("unpublished"); else unpublished = "";
		if (record.get("created_by") != null) created_by = "" + record.get("created_by"); else created_by = "";
		if (record.get("updated_by") != null) updated_by = "" + record.get("updated_by"); else updated_by = "";
		if (record.get("published_by") != null) published_by = "" + record.get("published_by"); else published_by = "";
		if (record.get("unpublished_by") != null) unpublished_by = "" + record.get("unpublished_by"); else unpublished_by = "";
		if (record.get("scheduled_publish") != null) scheduled_publish = "" + record.get("scheduled_publish"); else scheduled_publish = "";
		if (record.get("scheduled_unpublish") != null) scheduled_unpublish = "" + record.get("scheduled_unpublish"); else scheduled_unpublish = "";
		if (record.get("requested_publish") != null) requested_publish = "" + record.get("requested_publish"); else requested_publish = "";
		if (record.get("requested_unpublish") != null) requested_unpublish = "" + record.get("requested_unpublish"); else requested_unpublish = "";
		if (record.get("revision") != null) revision = "" + record.get("revision"); else revision = "";
		if (record.get("device") != null) device = "" + record.get("device"); else device = "";
		if (record.get("usersegment") != null) usersegment = "" + record.get("usersegment"); else usersegment = "";
		if (record.get("usertest") != null) usertest = "" + record.get("usertest"); else usertest = "";
		if (record.get("heatmap") != null) heatmap = "" + record.get("heatmap"); else heatmap = "";
		if (record.get("heatmapalign") != null) heatmapalign = "" + record.get("heatmapalign"); else heatmapalign = "";
		if (record.get("version") != null) version = "" + record.get("version"); else version = "";
		if (record.get("version_master") != null) version_master = "" + record.get("version_master"); else version_master = "";
		if (record.get("status") != null) status = "" + record.get("status"); else status = "";
		if (record.get("status_by") != null) status_by = "" + record.get("status_by"); else status_by = "";
		if (record.get("title") != null) title = "" + record.get("title"); else title = "";
		if (record.get("searchable") != null) searchable = "" + record.get("searchable"); else searchable = "";
		if (record.get("menuitem") != null) menuitem = "" + record.get("menuitem"); else menuitem = "";
		if (record.get("url") != null) url = "" + record.get("url"); else url = "";
		if (record.get("upload_filename") != null) upload_filename = "" + record.get("upload_filename"); else upload_filename = "";
		if (record.get("server_filename") != null) server_filename = "" + record.get("server_filename"); else server_filename = "";
		if (record.get("author") != null) author = "" + record.get("author"); else author = "";
		if (record.get("keywords") != null) keywords = "" + record.get("keywords"); else keywords = "";
		if (record.get("description") != null) description = "" + record.get("description"); else description = "";
		if (record.get("summary") != null) summary = "" + record.get("summary"); else summary = "";
		if (record.get("image1") != null) image1 = "" + record.get("image1"); else image1 = "";
		if (record.get("image2") != null) image2 = "" + record.get("image2"); else image2 = "";
		if (record.get("image3") != null) image3 = "" + record.get("image3"); else image3 = "";
		if (record.get("file1") != null) file1 = "" + record.get("file1"); else file1 = "";
		if (record.get("file2") != null) file2 = "" + record.get("file2"); else file2 = "";
		if (record.get("file3") != null) file3 = "" + record.get("file3"); else file3 = "";
		if (record.get("link1") != null) link1 = "" + record.get("link1"); else link1 = "";
		if (record.get("link2") != null) link2 = "" + record.get("link2"); else link2 = "";
		if (record.get("link3") != null) link3 = "" + record.get("link3"); else link3 = "";
		if (record.get("metainfo") != null) metainfo = "" + record.get("metainfo"); else metainfo = "";
		if (record.get("segmentation") != null) segmentation = "" + record.get("segmentation"); else segmentation = "";
		if (record.get("htmlattributes") != null) htmlattributes = "" + record.get("htmlattributes"); else htmlattributes = "";
		if (record.get("htmlheader") != null) htmlheader = "" + record.get("htmlheader"); else htmlheader = "";
		if (record.get("htmlbodyonload") != null) htmlbodyonload = "" + record.get("htmlbodyonload"); else htmlbodyonload = "";
		if (record.get("contentformat") != null) contentformat = "" + record.get("contentformat"); else contentformat = "";
		if (record.get("content") != null) content = "" + record.get("content"); else content = "";
		if (record.get("doctype") != null) doctype = "" + record.get("doctype"); else doctype = "";
		if (record.get("template") != null) template = "" + record.get("template"); else template = "";
		if (record.get("stylesheet") != null) stylesheet = "" + record.get("stylesheet"); else stylesheet = "";
		if (record.get("scripts") != null) scripts = "" + record.get("scripts"); else scripts = "";
		if (record.get("contentpackage") != null) contentpackage = "" + record.get("contentpackage"); else contentpackage = "";
		if (record.get("contentbundle") != null) contentbundle = "" + record.get("contentbundle"); else contentbundle = "";
		if (record.get("contentclass") != null) contentclass = "" + record.get("contentclass"); else contentclass = "";
		if (record.get("contenttype") != null) contenttype = "" + record.get("contenttype"); else contenttype = "";
		if (record.get("contentgroup") != null) contentgroup = "" + record.get("contentgroup"); else contentgroup = "";
		if (record.get("users_users") != null) users_users = "" + record.get("users_users"); else users_users = "";
		if (record.get("users_type") != null) users_type = "" + record.get("users_type"); else users_type = "";
		if (record.get("users_group") != null) users_group = "" + record.get("users_group"); else users_group = "";
		if (record.get("creators_users") != null) creators_users = "" + record.get("creators_users"); else creators_users = "";
		if (record.get("creators_type") != null) creators_type = "" + record.get("creators_type"); else creators_type = "";
		if (record.get("creators_group") != null) creators_group = "" + record.get("creators_group"); else creators_group = "";
		if (record.get("developers_users") != null) developers_users = "" + record.get("developers_users"); else developers_users = "";
		if (record.get("developers_type") != null) developers_type = "" + record.get("developers_type"); else developers_type = "";
		if (record.get("developers_group") != null) developers_group = "" + record.get("developers_group"); else developers_group = "";
		if (record.get("editors_users") != null) editors_users = "" + record.get("editors_users"); else editors_users = "";
		if (record.get("editors_type") != null) editors_type = "" + record.get("editors_type"); else editors_type = "";
		if (record.get("editors_group") != null) editors_group = "" + record.get("editors_group"); else editors_group = "";
		if (record.get("publishers_users") != null) publishers_users = "" + record.get("publishers_users"); else publishers_users = "";
		if (record.get("publishers_type") != null) publishers_type = "" + record.get("publishers_type"); else publishers_type = "";
		if (record.get("publishers_group") != null) publishers_group = "" + record.get("publishers_group"); else publishers_group = "";
		if (record.get("administrators_users") != null) administrators_users = "" + record.get("administrators_users"); else administrators_users = "";
		if (record.get("administrators_type") != null) administrators_type = "" + record.get("administrators_type"); else administrators_type = "";
		if (record.get("administrators_group") != null) administrators_group = "" + record.get("administrators_group"); else administrators_group = "";
		if (record.get("checkedout") != null) checkedout = "" + record.get("checkedout"); else checkedout = "";
		if (record.get("page_up") != null) page_up = "" + record.get("page_up"); else page_up = "";
		if (record.get("page_top") != null) page_top = "" + record.get("page_top"); else page_top = "";
		if (record.get("page_next") != null) page_next = "" + record.get("page_next"); else page_next = "";
		if (record.get("page_previous") != null) page_previous = "" + record.get("page_previous"); else page_previous = "";
		if (record.get("page_last") != null) page_last = "" + record.get("page_last"); else page_last = "";
		if (record.get("page_first") != null) page_first = "" + record.get("page_first"); else page_first = "";
		if (record.get("related") != null) related = "" + record.get("related"); else related = "";
		if (record.get("product_code") != null) product_code = "" + record.get("product_code"); else product_code = "";
		if (record.get("product_currency") != null) product_currency = "" + record.get("product_currency"); else product_currency = "";
		if (record.get("product_cost") != null) product_cost = "" + record.get("product_cost"); else product_cost = "";
		if (record.get("product_price") != null) product_price = "" + record.get("product_price"); else product_price = "";
		if (record.get("product_period") != null) product_period = "" + record.get("product_period"); else product_period = "";
		if (record.get("product_weight") != null) product_weight = "" + record.get("product_weight"); else product_weight = "";
		if (record.get("product_volume") != null) product_volume = "" + record.get("product_volume"); else product_volume = "";
		if (record.get("product_width") != null) product_width = "" + record.get("product_width"); else product_width = "";
		if (record.get("product_height") != null) product_height = "" + record.get("product_height"); else product_height = "";
		if (record.get("product_depth") != null) product_depth = "" + record.get("product_depth"); else product_depth = "";
		if (record.get("product_stock") != null) product_stock = "" + record.get("product_stock"); else product_stock = ""; // DEPRECATED
		if (record.get("product_comment") != null) product_comment = "" + record.get("product_comment"); else product_comment = ""; // DEPRECATED
		if (record.get("product_email") != null) product_email = "" + record.get("product_email"); else product_email = "";
		if (record.get("product_url") != null) product_url = "" + record.get("product_url"); else product_url = "";
		if (record.get("product_brand") != null) product_brand = "" + record.get("product_brand"); else product_brand = "";
		if (record.get("product_colour") != null) product_colour = "" + record.get("product_colour"); else product_colour = "";
		if (record.get("product_size") != null) product_size = "" + record.get("product_size"); else product_size = "";
		if (record.get("product_info") != null) product_info = "" + record.get("product_info"); else product_info = "";
		if (record.get("product_options") != null) product_options = "" + record.get("product_options"); else product_options = "";
		if (record.get("product_delivery") != null) product_delivery = "" + record.get("product_delivery"); else product_delivery = "";
		if (record.get("product_stock_amount") != null) product_stock_amount = "" + record.get("product_stock_amount"); else product_stock_amount = "";
		if (record.get("product_restocked_amount") != null) product_restocked_amount = "" + record.get("product_restocked_amount"); else product_restocked_amount = "";
		if (record.get("product_stock_text") != null) product_stock_text = "" + record.get("product_stock_text"); else product_stock_text = "";
		if (record.get("product_lowstock_amount") != null) product_lowstock_amount = "" + record.get("product_lowstock_amount"); else product_lowstock_amount = "";
		if (record.get("product_lowstock_text") != null) product_lowstock_text = "" + record.get("product_lowstock_text"); else product_lowstock_text = "";
		if (record.get("product_nostock_order") != null) product_nostock_order = "" + record.get("product_nostock_order"); else product_nostock_order = "";
		if (record.get("product_nostock_text") != null) product_nostock_text = "" + record.get("product_nostock_text"); else product_nostock_text = "";
		if (record.get("product_location") != null) product_location = "" + record.get("product_location"); else product_location = "";
		if (record.get("product_user") != null) product_user = "" + record.get("product_user"); else product_user = "";
		if (record.get("product_page") != null) product_page = "" + record.get("product_page"); else product_page = "";
		if (record.get("product_program") != null) product_program = "" + record.get("product_program"); else product_program = "";
		if (record.get("product_hosting") != null) product_hosting = "" + record.get("product_hosting"); else product_hosting = "";
		if (record.get("product_content") != null) product_content = "" + record.get("product_content"); else product_content = "";
		if (record.get("product_contentgroup") != null) product_contentgroup = "" + record.get("product_contentgroup"); else product_contentgroup = "";
		if (record.get("product_contenttype") != null) product_contenttype = "" + record.get("product_contenttype"); else product_contenttype = "";
		if (record.get("product_imagegroup") != null) product_imagegroup = "" + record.get("product_imagegroup"); else product_imagegroup = "";
		if (record.get("product_imagetype") != null) product_imagetype = "" + record.get("product_imagetype"); else product_imagetype = "";
		if (record.get("product_filegroup") != null) product_filegroup = "" + record.get("product_filegroup"); else product_filegroup = "";
		if (record.get("product_filetype") != null) product_filetype = "" + record.get("product_filetype"); else product_filetype = "";
		if (record.get("product_linkgroup") != null) product_linkgroup = "" + record.get("product_linkgroup"); else product_linkgroup = "";
		if (record.get("product_linktype") != null) product_linktype = "" + record.get("product_linktype"); else product_linktype = "";
		if (record.get("product_productgroup") != null) product_productgroup = "" + record.get("product_productgroup"); else product_productgroup = "";
		if (record.get("product_producttype") != null) product_producttype = "" + record.get("product_producttype"); else product_producttype = "";
		if (record.get("product_usergroup") != null) product_usergroup = "" + record.get("product_usergroup"); else product_usergroup = "";
		if (record.get("product_usertype") != null) product_usertype = "" + record.get("product_usertype"); else product_usertype = "";
		getElements(db, config, table, column, record_id);
		getRecordGroupsTypes(db);
	}



	public void getElements(DB db, Configuration config, String table, String column, String record_id) {
		element = new HashMap<String,String>();
		if (db == null) return;
		if (((! table.equals("")) && (! column.equals("")) && ((! record_id.equals("")) || (! id.equals("")))) && ((contentclass.equals("page")) || (contentclass.equals("product")) || (contentclass.equals("template")))) {
			if (config.get(db, "use_additionalcontent").equals("yes")) {
				String SQL = "";
				if (! record_id.equals("")) {
					SQL = "select * from " + Common.SQL_clean(table) + "_elements where content_" + Common.SQL_clean(column) + "=" + Common.integer(record_id) + " order by element_order";
				} else {
					SQL = "select * from " + Common.SQL_clean(table) + "_elements where content_" + Common.SQL_clean(column) + "=" + Common.integer(id) + " order by element_order";
				}
@SuppressWarnings("unchecked")
				LinkedHashMap<String,HashMap<String,String>> rows = (LinkedHashMap<String,HashMap<String,String>>)Cache.get(db, Common.SQL_clean(table) + "_elements", "content_" + Common.SQL_clean(column) + ":" + Common.integer(record_id) + ":" + Common.integer(id));
				if (rows == null) {
					rows = db.query_records(SQL);
					if (rows == null) rows = new LinkedHashMap<String,HashMap<String,String>>();
					if (rows != null) Cache.set(db, Common.SQL_clean(table) + "_elements", "content_" + Common.SQL_clean(column) + ":" + Common.integer(record_id) + ":" + Common.integer(id), rows);
				}
				for (int i=0; i<rows.size(); i++) {
					HashMap<String,String> record2 = (HashMap<String,String>)rows.get("" + i);
					if (element.get(record2.get("element")) == null) {
						element.put(record2.get("element"), "" + Common.integer(record2.get("element_id")));
					} else {
						element.put(record2.get("element"), element.get(record2.get("element")) + "," + Common.integer(record2.get("element_id")));
					}
				}
			}
		}
	}



	public void getRecordGroupsTypes(DB db) {
		if (db == null) return;
		if ((! contentclass.equals("script")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("template"))) {
			title_prefix = "";
			title_suffix = "";
			if (! contentgroup.equals("")) {
				String cachecategory = "";
				String cacheid = "";
				String SQL = "";
				if (contentclass.equals("page")) {
					SQL = "select * from contentgroups where contentgroup=" + db.quote(Common.SQL_clean(contentgroup));
					cachecategory = "contentgroups";
					cacheid = "contentgroup_" + Common.SQL_clean(contentgroup);
				} else if (contentclass.equals("file")) {
					SQL = "select * from filegroups where filegroup=" + db.quote(Common.SQL_clean(contentgroup));
					cachecategory = "filegroups";
					cacheid = "filegroup_" + Common.SQL_clean(contentgroup);
				} else if (contentclass.equals("image")) {
					SQL = "select * from imagegroups where imagegroup=" + db.quote(Common.SQL_clean(contentgroup));
					cachecategory = "imagegroups";
					cacheid = "imagegroup_" + Common.SQL_clean(contentgroup);
				} else if (contentclass.equals("link")) {
					SQL = "select * from linkgroups where linkgroup=" + db.quote(Common.SQL_clean(contentgroup));
					cachecategory = "linkgroups";
					cacheid = "linkgroup_" + Common.SQL_clean(contentgroup);
				} else if (contentclass.equals("product")) {
					SQL = "select * from productgroups where productgroup=" + db.quote(Common.SQL_clean(contentgroup));
					cachecategory = "productgroups";
					cacheid = "productgroup_" + Common.SQL_clean(contentgroup);
				} else {
					SQL = "select * from contentgroups where contentgroup=" + db.quote(Common.SQL_clean(contentgroup));
					cachecategory = "contentgroups";
					cacheid = "contentgroup_" + Common.SQL_clean(contentgroup);
				}
@SuppressWarnings("unchecked")
				HashMap<String,String> record2 = (HashMap<String,String>)Cache.get(db, cachecategory, cacheid);
				if (record2 == null) {
					record2 = db.query_record(SQL);
					if (record2 == null) record2 = new HashMap<String,String>();
					if (record2 != null) Cache.set(db, cachecategory, cacheid, record2);
				}
				if (record2 != null) {
					if (record2.get("users_users") != null) contentgroup_users_users = "" + record2.get("users_users"); else contentgroup_users_users = "";
					if (record2.get("users_type") != null) contentgroup_users_type = "" + record2.get("users_type"); else contentgroup_users_type = "";
					if (record2.get("users_group") != null) contentgroup_users_group = "" + record2.get("users_group"); else contentgroup_users_group = "";
					if (record2.get("creators_users") != null) contentgroup_creators_users = "" + record2.get("creators_users"); else contentgroup_creators_users = "";
					if (record2.get("creators_type") != null) contentgroup_creators_type = "" + record2.get("creators_type"); else contentgroup_creators_type = "";
					if (record2.get("creators_group") != null) contentgroup_creators_group = "" + record2.get("creators_group"); else contentgroup_creators_group = "";
					if (record2.get("developers_users") != null) contentgroup_developers_users = "" + record2.get("developers_users"); else contentgroup_developers_users = "";
					if (record2.get("developers_type") != null) contentgroup_developers_type = "" + record2.get("developers_type"); else contentgroup_developers_type = "";
					if (record2.get("developers_group") != null) contentgroup_developers_group = "" + record2.get("developers_group"); else contentgroup_developers_group = "";
					if (record2.get("editors_users") != null) contentgroup_editors_users = "" + record2.get("editors_users"); else contentgroup_editors_users = "";
					if (record2.get("editors_type") != null) contentgroup_editors_type = "" + record2.get("editors_type"); else contentgroup_editors_type = "";
					if (record2.get("editors_group") != null) contentgroup_editors_group = "" + record2.get("editors_group"); else contentgroup_editors_group = "";
					if (record2.get("publishers_users") != null) contentgroup_publishers_users = "" + record2.get("publishers_users"); else contentgroup_publishers_users = "";
					if (record2.get("publishers_type") != null) contentgroup_publishers_type = "" + record2.get("publishers_type"); else contentgroup_publishers_type = "";
					if (record2.get("publishers_group") != null) contentgroup_publishers_group = "" + record2.get("publishers_group"); else contentgroup_publishers_group = "";
					if (record2.get("administrators_users") != null) contentgroup_administrators_users = "" + record2.get("administrators_users"); else contentgroup_administrators_users = "";
					if (record2.get("administrators_type") != null) contentgroup_administrators_type = "" + record2.get("administrators_type"); else contentgroup_administrators_type = "";
					if (record2.get("administrators_group") != null) contentgroup_administrators_group = "" + record2.get("administrators_group"); else contentgroup_administrators_group = "";
					if (record2.get("doctype") != null) contentgroup_doctype = "" + record2.get("doctype"); else contentgroup_doctype = "";
					if (record2.get("template") != null) contentgroup_template = "" + record2.get("template"); else contentgroup_template = "";
					if (record2.get("stylesheet") != null) contentgroup_stylesheet = "" + record2.get("stylesheet"); else contentgroup_stylesheet = "";
					if ((record2.get("title_prefix") != null) && (! record2.get("title_prefix").equals(""))) title_prefix = "" + title_prefix + record2.get("title_prefix") + " ";
					if ((record2.get("title_suffix") != null) && (! record2.get("title_suffix").equals(""))) title_suffix = "" + title_suffix + " " + record2.get("title_suffix");
				}
			}
			if (! contenttype.equals("")) {
				String cachecategory = "";
				String cacheid = "";
				String SQL = "";
				if (contentclass.equals("page")) {
					SQL = "select * from contenttypes where contenttype=" + db.quote(Common.SQL_clean(contenttype));
					cachecategory = "contenttypes";
					cacheid = "contenttype_" + Common.SQL_clean(contenttype);
				} else if (contentclass.equals("file")) {
					SQL = "select * from filetypes where filetype=" + db.quote(Common.SQL_clean(contenttype));
					cachecategory = "filetypes";
					cacheid = "filetype_" + Common.SQL_clean(contenttype);
				} else if (contentclass.equals("image")) {
					SQL = "select * from imagetypes where imagetype=" + db.quote(Common.SQL_clean(contenttype));
					cachecategory = "imagetypes";
					cacheid = "imagetype_" + Common.SQL_clean(contenttype);
				} else if (contentclass.equals("link")) {
					SQL = "select * from linktypes where linktype=" + db.quote(Common.SQL_clean(contenttype));
					cachecategory = "linktypes";
					cacheid = "linktype_" + Common.SQL_clean(contenttype);
				} else if (contentclass.equals("product")) {
					SQL = "select * from producttypes where producttype=" + db.quote(Common.SQL_clean(contenttype));
					cachecategory = "producttypes";
					cacheid = "producttype_" + Common.SQL_clean(contenttype);
				} else {
					SQL = "select * from contenttypes where contenttype=" + db.quote(Common.SQL_clean(contenttype));
					cachecategory = "contentgroups";
					cacheid = "contenttype_" + Common.SQL_clean(contenttype);
				}
@SuppressWarnings("unchecked")
				HashMap<String,String> record2 = (HashMap<String,String>)Cache.get(db, cachecategory, cacheid);
				if (record2 == null) {
					record2 = db.query_record(SQL);
					if (record2 == null) record2 = new HashMap<String,String>();
					if (record2 != null) Cache.set(db, cachecategory, cacheid, record2);
				}
				if (record2 != null) {
					if (record2.get("users_users") != null) contenttype_users_users = "" + record2.get("users_users"); else contenttype_users_users = "";
					if (record2.get("users_type") != null) contenttype_users_type = "" + record2.get("users_type"); else contenttype_users_type = "";
					if (record2.get("users_group") != null) contenttype_users_group = "" + record2.get("users_group"); else contenttype_users_group = "";
					if (record2.get("creators_users") != null) contenttype_creators_users = "" + record2.get("creators_users"); else contenttype_creators_users = "";
					if (record2.get("creators_type") != null) contenttype_creators_type = "" + record2.get("creators_type"); else contenttype_creators_type = "";
					if (record2.get("creators_group") != null) contenttype_creators_group = "" + record2.get("creators_group"); else contenttype_creators_group = "";
					if (record2.get("developers_users") != null) contenttype_developers_users = "" + record2.get("developers_users"); else contenttype_developers_users = "";
					if (record2.get("developers_type") != null) contenttype_developers_type = "" + record2.get("developers_type"); else contenttype_developers_type = "";
					if (record2.get("developers_group") != null) contenttype_developers_group = "" + record2.get("developers_group"); else contenttype_developers_group = "";
					if (record2.get("editors_users") != null) contenttype_editors_users = "" + record2.get("editors_users"); else contenttype_editors_users = "";
					if (record2.get("editors_type") != null) contenttype_editors_type = "" + record2.get("editors_type"); else contenttype_editors_type = "";
					if (record2.get("editors_group") != null) contenttype_editors_group = "" + record2.get("editors_group"); else contenttype_editors_group = "";
					if (record2.get("publishers_users") != null) contenttype_publishers_users = "" + record2.get("publishers_users"); else contenttype_publishers_users = "";
					if (record2.get("publishers_type") != null) contenttype_publishers_type = "" + record2.get("publishers_type"); else contenttype_publishers_type = "";
					if (record2.get("publishers_group") != null) contenttype_publishers_group = "" + record2.get("publishers_group"); else contenttype_publishers_group = "";
					if (record2.get("administrators_users") != null) contenttype_administrators_users = "" + record2.get("administrators_users"); else contenttype_administrators_users = "";
					if (record2.get("administrators_type") != null) contenttype_administrators_type = "" + record2.get("administrators_type"); else contenttype_administrators_type = "";
					if (record2.get("administrators_group") != null) contenttype_administrators_group = "" + record2.get("administrators_group"); else contenttype_administrators_group = "";
					if (record2.get("doctype") != null) contenttype_doctype = "" + record2.get("doctype"); else contenttype_doctype = "";
					if (record2.get("template") != null) contenttype_template = "" + record2.get("template"); else contenttype_template = "";
					if (record2.get("stylesheet") != null) contenttype_stylesheet = "" + record2.get("stylesheet"); else contenttype_stylesheet = "";
					if ((record2.get("title_prefix") != null) && (! record2.get("title_prefix").equals(""))) title_prefix = "" + title_prefix + record2.get("title_prefix") + " ";
					if ((record2.get("title_suffix") != null) && (! record2.get("title_suffix").equals(""))) title_suffix = "" + title_suffix + " " + record2.get("title_suffix");
				}
			}
		}
	}



	public ArrayList<String> getContentpackages(DB db) {
		ArrayList<String> mycontentpackages = new ArrayList<String>();
		String SQL = "select distinct contentpackage from content where (" + db.is_not_blank("contentpackage") + ") order by contentpackage";
		LinkedHashMap<String,HashMap<String,String>> records = db.query_records(SQL);
		for (int i=0; i<records.size(); i++) {
			String contentpackage = "" + ((HashMap<String,String>)records.get("" + i)).get("contentpackage");
			mycontentpackages.add(contentpackage);
		}
		return mycontentpackages;
	}



	public ArrayList<String> getContentbundles(DB db) {
		ArrayList<String> mycontentbundles = new ArrayList<String>();
		String SQL = "select distinct contentbundle from content where (" + db.is_not_blank("contentbundle") + ") order by contentbundle";
		LinkedHashMap<String,HashMap<String,String>> records = db.query_records(SQL);
		for (int i=0; i<records.size(); i++) {
			String contentbundle = "" + ((HashMap<String,String>)records.get("" + i)).get("contentbundle");
			mycontentbundles.add(contentbundle);
		}
		return mycontentbundles;
	}



	public ArrayList<String> listContentpackage(DB db, String contentpackage, String contentclass) {
		ArrayList<String> mycontentpackage = new ArrayList<String>();
		String SQL = "select id,title,version,device,usersegment,usertest from content where contentpackage=" + db.quote(Common.SQL_clean(contentpackage));
		SQL = Common.SQLwhere_in(SQL, "contentclass", contentclass);
		SQL += " order by title,version,device,usersegment,usertest,id";
		LinkedHashMap<String,HashMap<String,String>> records = db.query_records(SQL);
		for (int i=0; i<records.size(); i++) {
			String myid = "" + ((HashMap<String,String>)records.get("" + i)).get("id");
			mycontentpackage.add(myid);
		}
		return mycontentpackage;
	}



	public ArrayList<String> listContentbundle(DB db, String contentbundle, String contentclass) {
		ArrayList<String> mycontentbundle = new ArrayList<String>();
		String SQL = "select id,title,version,device,usersegment,usertest from content where contentbundle=" + db.quote(Common.SQL_clean(contentbundle));
		SQL = Common.SQLwhere_in(SQL, "contentclass", contentclass);
		SQL += " order by title,version,device,usersegment,usertest,id";
		LinkedHashMap<String,HashMap<String,String>> records = db.query_records(SQL);
		for (int i=0; i<records.size(); i++) {
			String myid = "" + ((HashMap<String,String>)records.get("" + i)).get("id");
			mycontentbundle.add(myid);
		}
		return mycontentbundle;
	}



	public void getAccessRestrictions(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config) {
		if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":administrator:"+session_administrator + ":userid:"+session_userid + ":username:"+session_username + ":usertype:"+session_usertype + ":usergroup:"+session_usergroup + ":usertypes:"+session_usertypes + ":usergroups:"+session_usergroups); }
		user = true;
		creator = false;
		editor = false;
		publisher = false;
		developer = false;
		administrator = false;

		if ((config.get(db, "use_accessrestrictions").equals("users")) || (config.get(db, "use_accessrestrictions").equals("all"))) {

			user = true;
			if ((users_type.equals("*")) && (session_username.equals(""))) {
				user = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":users_type:"+users_type); }
			} else if ((users_type.equals("=")) && (! session_username.equals(created_by))) {
				user = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":users_type:"+users_type); }
			} else if ((! users_type.equals("*")) && (! users_type.equals("=")) && (! users_type.equals("")) && (! users_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + users_type + "|") >= 0))) {
				user = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":users_type:"+users_type); }
			}
				if ((users_group.equals("*")) && (session_username.equals(""))) {
					user = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":users_group:"+users_group); }
				} else if ((users_group.equals("=")) && (! session_username.equals(created_by))) {
					user = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":users_group:"+users_group); }
				} else if ((! users_group.equals("*")) && (! users_group.equals("=")) && (! users_group.equals("")) && (! users_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + users_group + "|") >= 0))) {
					user = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":users_group:"+users_group); }
				}
				if (config.get(db, "accessrestrictions").equals("or")) {
					if (((users_type.equals("*")) || (users_group.equals("*"))) && (! session_username.equals(""))) {
						user = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":users_group:"+users_group + ":users_type:"+users_type); }
					}
					if (((users_type.equals("=")) || (users_group.equals("="))) && (session_username.equals(created_by))) {
						user = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":users_group:"+users_group + ":users_type:"+users_type); }
					}
					if (((! users_type.equals("*")) && (! users_type.equals("=")) && (! users_type.equals("")) && ((users_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + users_type + "|") >= 0))) || ((! users_group.equals("*")) && (! users_group.equals("=")) && (! users_group.equals("")) && ((users_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + users_group + "|") >= 0)))) {
						user = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":users_group:"+users_group + ":users_type:"+users_type); }
					}
				}
			if (user) {
				if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
					if (! users_users.equals("")) {
						if (session_userid.equals("")) {
							user = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":users_users:"+users_users); }
						} else if ((! users_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(users_users).find())) {
							user = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":users_users:"+users_users); }
						}
					}
				}
			}
			if (user) {
				if ((config.get(db, "use_contentdefinition").equals("yes")) && (! contentclass.equals("script")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("template")) && (((contentclass.equals("page")) && (config.get(db, "use_contentgroups").equals("yes"))) || ((contentclass.equals("file")) && (config.get(db, "use_filegroups").equals("yes"))) || ((contentclass.equals("image")) && (config.get(db, "use_imagegroups").equals("yes"))) || ((contentclass.equals("link")) && (config.get(db, "use_linkgroups").equals("yes"))) || ((contentclass.equals("product")) && (config.get(db, "use_productgroups").equals("yes"))) || ((! contentclass.equals("page")) && (! contentclass.equals("file")) && (! contentclass.equals("image")) && (! contentclass.equals("link")) && (! contentclass.equals("product")) && (config.get(db, "use_contentgroups").equals("yes"))))) {
					if ((contentgroup_users_type.equals("*")) && (session_username.equals(""))) {
						user = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_users_type:"+contentgroup_users_type); }
					} else if ((! contentgroup_users_type.equals("*")) && (! contentgroup_users_type.equals("")) && (! contentgroup_users_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + contentgroup_users_type + "|") >= 0))) {
						user = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_users_type:"+contentgroup_users_type); }
					}
					if ((contentgroup_users_group.equals("*")) && (session_username.equals(""))) {
						user = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_users_group:"+contentgroup_users_group); }
					} else if ((! contentgroup_users_group.equals("*")) && (! contentgroup_users_group.equals("")) && (! contentgroup_users_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + contentgroup_users_group + "|") >= 0))) {
						user = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_users_group:"+contentgroup_users_group); }
					}
					if (config.get(db, "accessrestrictions").equals("or")) {
						if (((! contentgroup_users_type.equals("")) && ((contentgroup_users_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + contentgroup_users_type + "|") >= 0))) || ((! contentgroup_users_group.equals("")) && ((contentgroup_users_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + contentgroup_users_group + "|") >= 0)))) {
							user = true;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_users_group:"+contentgroup_users_group + ":contentgroup_users_type:"+contentgroup_users_type); }
						}
					}
					if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
						if (! contentgroup_users_users.equals("")) {
							if (session_userid.equals("")) {
								user = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_users_users:"+contentgroup_users_users); }
							} else if ((! contentgroup_users_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(contentgroup_users_users).find())) {
								user = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_users_users:"+contentgroup_users_users); }
							}
						}
					}
				}
			}
			if (user) {
				if ((config.get(db, "use_contentdefinition").equals("yes")) && (! contentclass.equals("script")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("template")) && (((contentclass.equals("page")) && (config.get(db, "use_contenttypes").equals("yes"))) || ((contentclass.equals("file")) && (config.get(db, "use_filetypes").equals("yes"))) || ((contentclass.equals("image")) && (config.get(db, "use_imagetypes").equals("yes"))) || ((contentclass.equals("link")) && (config.get(db, "use_linktypes").equals("yes"))) || ((contentclass.equals("product")) && (config.get(db, "use_producttypes").equals("yes"))) || ((! contentclass.equals("page")) && (! contentclass.equals("file")) && (! contentclass.equals("image")) && (! contentclass.equals("link")) && (! contentclass.equals("product")) && (config.get(db, "use_contenttypes").equals("yes"))))) {
					if ((contenttype_users_type.equals("*")) && (session_username.equals(""))) {
						user = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_users_type:"+contenttype_users_type); }
					} else if ((! contenttype_users_type.equals("*")) && (! contenttype_users_type.equals("")) && (! contenttype_users_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + contenttype_users_type + "|") >= 0))) {
						user = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_users_type:"+contenttype_users_type); }
					}
					if ((contenttype_users_group.equals("*")) && (session_username.equals(""))) {
						user = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_users_group:"+contenttype_users_group); }
					} else if ((! contenttype_users_group.equals("*")) && (! contenttype_users_group.equals("")) && (! contenttype_users_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + contenttype_users_group + "|") >= 0))) {
						user = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_users_group:"+contenttype_users_group); }
					}
					if (config.get(db, "accessrestrictions").equals("or")) {
						if (((! contenttype_users_type.equals("")) && ((contenttype_users_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + contenttype_users_type + "|") >= 0))) || ((! contenttype_users_group.equals("")) && ((contenttype_users_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + contenttype_users_group + "|") >= 0)))) {
							user = true;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_users_group:"+contenttype_users_group + ":contenttype_users_type:"+contenttype_users_type); }
						}
					}
					if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
						if (! contenttype_users_users.equals("")) {
							if (session_userid.equals("")) {
								user = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_users_users:"+contenttype_users_users); }
							} else if ((! contenttype_users_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(contenttype_users_users).find())) {
								user = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_users_users:"+contenttype_users_users); }
							}
						}
					}
				}
			}
			if (user) {
				if ((config.get(db, "website_users_type").equals("*")) && (session_username.equals(""))) {
					user = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_users_type:"+config.get(db, "website_users_type")); }
				} else if ((! config.get(db, "website_users_type").equals("*")) && (! config.get(db, "website_users_type").equals("")) && (! config.get(db, "website_users_type").equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_users_type") + "|") >= 0))) {
					user = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_users_type:"+config.get(db, "website_users_type")); }
				}
					if ((config.get(db, "website_users_group").equals("*")) && (session_username.equals(""))) {
						user = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_users_group:"+config.get(db, "website_users_group")); }
					} else if ((! config.get(db, "website_users_group").equals("*")) && (! config.get(db, "website_users_group").equals("")) && (! config.get(db, "website_users_group").equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_users_group") + "|") >= 0))) {
						user = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_users_group:"+config.get(db, "website_users_group")); }
					}
					if (config.get(db, "accessrestrictions").equals("or")) {
						if (((config.get(db, "website_users_type").equals("*")) || (config.get(db, "website_users_group").equals("*"))) && (! session_username.equals(""))) {
							user = true;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_users_group:"+config.get(db, "website_users_group") + ":website_users_type:"+config.get(db, "website_users_type")); }
						}
						if (((! config.get(db, "website_users_type").equals("*")) && (! config.get(db, "website_users_type").equals("=")) && (! config.get(db, "website_users_type").equals("")) && ((config.get(db, "website_users_type").equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_users_type") + "|") >= 0))) || ((! config.get(db, "website_users_group").equals("*")) && (! config.get(db, "website_users_group").equals("=")) && (! config.get(db, "website_users_group").equals("")) && ((config.get(db, "website_users_group").equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_users_group") + "|") >= 0)))) {
							user = true;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_users_group:"+config.get(db, "website_users_group") + ":website_users_type:"+config.get(db, "website_users_type")); }
						}
					}
			}

		}
		if (config.get(db, "use_accessrestrictions").equals("all")) {

			editor = true;
			if ((editors_type.equals("*")) && (session_username.equals(""))) {
				editor = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":editors_type:"+editors_type); }
			} else if ((editors_type.equals("=")) && (! session_username.equals(created_by))) {
				editor = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":editors_type:"+editors_type); }
			} else if ((editors_type.equals("")) && (! session_administrator.equals("administrator"))) {
				editor = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":editors_type:"+editors_type); }
			} else if ((! editors_type.equals("0")) && (! editors_type.equals("*")) && (! editors_type.equals("=")) && (! editors_type.equals("")) && (! editors_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + editors_type + "|") >= 0))) {
				editor = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":editors_type:"+editors_type); }
			}
			if ((editors_group.equals("*")) && (session_username.equals(""))) {
				editor = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":editors_group:"+editors_group); }
			} else if ((editors_group.equals("=")) && (! session_username.equals(created_by))) {
				editor = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":editors_group:"+editors_group); }
			} else if ((editors_group.equals("")) && (! session_administrator.equals("administrator"))) {
				editor = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":editors_group:"+editors_group); }
			} else if ((! editors_group.equals("0")) && (! editors_group.equals("*")) && (! editors_group.equals("=")) && (! editors_group.equals("")) && (! editors_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + editors_group + "|") >= 0))) {
				editor = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":editors_group:"+editors_group); }
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((editors_type.equals("*")) || (editors_group.equals("*"))) && (! session_username.equals(""))) {
					editor = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":editors_group:"+editors_group + ":editors_type:"+editors_type); }
				}
				if (((editors_type.equals("=")) || (editors_group.equals("="))) && (session_username.equals(created_by))) {
					editor = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":editors_group:"+editors_group + ":editors_type:"+editors_type); }
				}
				if (((editors_type.equals("")) || (editors_group.equals(""))) && (session_administrator.equals("administrator"))) {
					editor = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":editors_group:"+editors_group + ":editors_type:"+editors_type); }
				}
				if (((! editors_type.equals("0")) && (! editors_type.equals("*")) && (! editors_type.equals("=")) && (! editors_type.equals("")) && ((editors_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + editors_type + "|") >= 0))) || ((! editors_group.equals("0")) && (! editors_group.equals("*")) && (! editors_group.equals("=")) && (! editors_group.equals("")) && ((editors_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + editors_group + "|") >= 0)))) {
					editor = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":editors_group:"+editors_group + ":editors_type:"+editors_type); }
				}
			}
			if (editor) {
				if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
					if (! editors_users.equals("")) {
						if (session_userid.equals("")) {
							editor = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":editors_users:"+editors_users); }
						} else if ((! editors_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(editors_users).find())) {
							editor = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":editors_users:"+editors_users); }
						}
					}
				}
			}
			if (editor) {
				if ((config.get(db, "use_contentdefinition").equals("yes")) && (! contentclass.equals("script")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("template")) && (((contentclass.equals("page")) && (config.get(db, "use_contentgroups").equals("yes"))) || ((contentclass.equals("file")) && (config.get(db, "use_filegroups").equals("yes"))) || ((contentclass.equals("image")) && (config.get(db, "use_imagegroups").equals("yes"))) || ((contentclass.equals("link")) && (config.get(db, "use_linkgroups").equals("yes"))) || ((contentclass.equals("product")) && (config.get(db, "use_productgroups").equals("yes"))) || ((! contentclass.equals("page")) && (! contentclass.equals("file")) && (! contentclass.equals("image")) && (! contentclass.equals("link")) && (! contentclass.equals("product")) && (config.get(db, "use_contentgroups").equals("yes"))))) {
					if ((! contentgroup_editors_type.equals("")) && (! contentgroup_editors_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + contentgroup_editors_type + "|") >= 0))) {
						editor = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_editors_type:"+contentgroup_editors_type); }
					}
					if ((! contentgroup_editors_group.equals("")) && (! contentgroup_editors_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + contentgroup_editors_group + "|") >= 0))) {
						editor = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_editors_group:"+contentgroup_editors_group); }
					}
					if (config.get(db, "accessrestrictions").equals("or")) {
						if (((! contentgroup_editors_type.equals("")) && ((contentgroup_editors_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + contentgroup_editors_type + "|") >= 0))) || ((! contentgroup_editors_group.equals("")) && ((contentgroup_editors_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + contentgroup_editors_group + "|") >= 0)))) {
							editor = true;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_editors_group:"+contentgroup_editors_group + ":contentgroup_editors_type:"+contentgroup_editors_type); }
						}
					}
					if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
						if (! contentgroup_editors_users.equals("")) {
							if (session_userid.equals("")) {
								editor = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_editors_users:"+contentgroup_editors_users); }
							} else if ((! contentgroup_editors_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(contentgroup_editors_users).find())) {
								editor = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_editors_users:"+contentgroup_editors_users); }
							}
						}
					}
				}
			}
			if (editor) {
				if ((config.get(db, "use_contentdefinition").equals("yes")) && (! contentclass.equals("script")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("template")) && (((contentclass.equals("page")) && (config.get(db, "use_contenttypes").equals("yes"))) || ((contentclass.equals("file")) && (config.get(db, "use_filetypes").equals("yes"))) || ((contentclass.equals("image")) && (config.get(db, "use_imagetypes").equals("yes"))) || ((contentclass.equals("link")) && (config.get(db, "use_linktypes").equals("yes"))) || ((contentclass.equals("product")) && (config.get(db, "use_producttypes").equals("yes"))) || ((! contentclass.equals("page")) && (! contentclass.equals("file")) && (! contentclass.equals("image")) && (! contentclass.equals("link")) && (! contentclass.equals("product")) && (config.get(db, "use_contenttypes").equals("yes"))))) {
					if ((! contenttype_editors_type.equals("")) && (! contenttype_editors_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + contenttype_editors_type + "|") >= 0))) {
						editor = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_editors_type:"+contenttype_editors_type); }
					}
					if ((! contenttype_editors_group.equals("")) && (! contenttype_editors_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + contenttype_editors_group + "|") >= 0))) {
						editor = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_editors_group:"+contenttype_editors_group); }
					}
					if (config.get(db, "accessrestrictions").equals("or")) {
						if (((! contenttype_editors_type.equals("")) && ((contenttype_editors_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + contenttype_editors_type + "|") >= 0))) || ((! contenttype_editors_group.equals("")) && ((contenttype_editors_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + contenttype_editors_group + "|") >= 0)))) {
							editor = true;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_editors_group:"+contenttype_editors_group + ":contenttype_editors_type:"+contenttype_editors_type); }
						}
					}
					if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
						if (! contenttype_editors_users.equals("")) {
							if (session_userid.equals("")) {
								editor = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_editors_users:"+contenttype_editors_users); }
							} else if ((! contenttype_editors_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(contenttype_editors_users).find())) {
								editor = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_editors_users:"+contenttype_editors_users); }
							}
						}
					}
				}
			}
			if (editor) {
				if ((config.get(db, "website_editors_type").equals("*")) && (session_username.equals(""))) {
					editor = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_editors_type:"+config.get(db, "website_editors_type")); }
				} else if ((config.get(db, "website_editors_type").equals("0")) && (! session_administrator.equals("administrator"))) {
					editor = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_editors_type:"+config.get(db, "website_editors_type")); }
				} else if ((! config.get(db, "website_editors_type").equals("0")) && (! config.get(db, "website_editors_type").equals("*")) && (! config.get(db, "website_editors_type").equals("=")) && (! config.get(db, "website_editors_type").equals("")) && (! config.get(db, "website_editors_type").equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_editors_type") + "|") >= 0))) {
					editor = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_editors_type:"+config.get(db, "website_editors_type")); }
				}
				if ((config.get(db, "website_editors_group").equals("*")) && (session_username.equals(""))) {
					editor = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_editors_group:"+config.get(db, "website_editors_group")); }
				} else if ((config.get(db, "website_editors_group").equals("0")) && (! session_administrator.equals("administrator"))) {
					editor = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_editors_group:"+config.get(db, "website_editors_group")); }
				} else if ((! config.get(db, "website_editors_group").equals("0")) && (! config.get(db, "website_editors_group").equals("*")) && (! config.get(db, "website_editors_group").equals("=")) && (! config.get(db, "website_editors_group").equals("")) && (! config.get(db, "website_editors_group").equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_editors_group") + "|") >= 0))) {
					editor = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_editors_group:"+config.get(db, "website_editors_group")); }
				}
				if (config.get(db, "accessrestrictions").equals("or")) {
					if (((config.get(db, "website_editors_type").equals("*")) || (config.get(db, "website_editors_group").equals("*"))) && (! session_username.equals(""))) {
						editor = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_editors_group:"+config.get(db, "website_editors_group") + ":website_editors_type:"+config.get(db, "website_editors_type")); }
					}
					if (((config.get(db, "website_editors_type").equals("0")) || (config.get(db, "website_editors_group").equals("0"))) && (session_administrator.equals("administrator"))) {
						editor = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_editors_group:"+config.get(db, "website_editors_group") + ":website_editors_type:"+config.get(db, "website_editors_type")); }
					}
					if (((! config.get(db, "website_editors_type").equals("0")) && (! config.get(db, "website_editors_type").equals("*")) && (! config.get(db, "website_editors_type").equals("=")) && (! config.get(db, "website_editors_type").equals("")) && ((config.get(db, "website_editors_type").equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_editors_type") + "|") >= 0))) || ((! config.get(db, "website_editors_group").equals("0")) && (! config.get(db, "website_editors_group").equals("*")) && (! config.get(db, "website_editors_group").equals("=")) && (! config.get(db, "website_editors_group").equals("")) && ((config.get(db, "website_editors_group").equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_editors_group") + "|") >= 0)))) {
						editor = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_editors_group:"+config.get(db, "website_editors_group") + ":website_editors_type:"+config.get(db, "website_editors_type")); }
					}
				}
			}

			creator = true;
			if ((creators_type.equals("*")) && (session_username.equals(""))) {
				creator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":creators_type:"+creators_type); }
			} else if ((creators_type.equals("=")) && (! session_username.equals(created_by))) {
				creator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":creators_type:"+creators_type); }
			} else if ((creators_type.equals("")) && (! session_administrator.equals("administrator"))) {
				creator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":creators_type:"+creators_type); }
			} else if ((! creators_type.equals("0")) && (! creators_type.equals("*")) && (! creators_type.equals("=")) && (! creators_type.equals("")) && (! creators_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + creators_type + "|") >= 0))) {
				creator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":creators_type:"+creators_type); }
			}
				if ((creators_group.equals("*")) && (session_username.equals(""))) {
					creator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":creators_group:"+creators_group); }
				} else if ((creators_group.equals("=")) && (! session_username.equals(created_by))) {
					creator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":creators_group:"+creators_group); }
				} else if ((creators_group.equals("")) && (! session_administrator.equals("administrator"))) {
					creator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":creators_group:"+creators_group); }
				} else if ((! creators_group.equals("0")) && (! creators_group.equals("*")) && (! creators_group.equals("=")) && (! creators_group.equals("")) && (! creators_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + creators_group + "|") >= 0))) {
					creator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":creators_group:"+creators_group); }
				}
				if (config.get(db, "accessrestrictions").equals("or")) {
					if (((creators_type.equals("*")) || (creators_group.equals("*"))) && (! session_username.equals(""))) {
						creator = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":creators_group:"+creators_group + ":creators_type:"+creators_type); }
					}
					if (((creators_type.equals("=")) || (creators_group.equals("="))) && (session_username.equals(created_by))) {
						creator = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":creators_group:"+creators_group + ":creators_type:"+creators_type); }
					}
					if (((creators_type.equals("")) || (creators_group.equals(""))) && (session_administrator.equals("administrator"))) {
						creator = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":creators_group:"+creators_group + ":creators_type:"+creators_type); }
					}
					if (((! creators_type.equals("0")) && (! creators_type.equals("*")) && (! creators_type.equals("=")) && (! creators_type.equals("")) && ((creators_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + creators_type + "|") >= 0))) || ((! creators_group.equals("0")) && (! creators_group.equals("*")) && (! creators_group.equals("=")) && (! creators_group.equals("")) && ((creators_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + creators_group + "|") >= 0)))) {
						creator = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":creators_group:"+creators_group + ":creators_type:"+creators_type); }
					}
				}
			if (creator) {
				if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
					if (! creators_users.equals("")) {
						if (session_userid.equals("")) {
							creator = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":creators_users:"+creators_users); }
						} else if ((! creators_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(creators_users).find())) {
							creator = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":creators_users:"+creators_users); }
						}
					}
				}
			}
			if (creator) {
				if ((config.get(db, "use_contentdefinition").equals("yes")) && (! contentclass.equals("script")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("template")) && (((contentclass.equals("page")) && (config.get(db, "use_contentgroups").equals("yes"))) || ((contentclass.equals("file")) && (config.get(db, "use_filegroups").equals("yes"))) || ((contentclass.equals("image")) && (config.get(db, "use_imagegroups").equals("yes"))) || ((contentclass.equals("link")) && (config.get(db, "use_linkgroups").equals("yes"))) || ((contentclass.equals("product")) && (config.get(db, "use_productgroups").equals("yes"))) || ((! contentclass.equals("page")) && (! contentclass.equals("file")) && (! contentclass.equals("image")) && (! contentclass.equals("link")) && (! contentclass.equals("product")) && (config.get(db, "use_contentgroups").equals("yes"))))) {
					if ((! contentgroup_creators_type.equals("")) && (! contentgroup_creators_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + contentgroup_creators_type + "|") >= 0))) {
						creator = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_creators_type:"+contentgroup_creators_type); }
					}
					if ((! contentgroup_creators_group.equals("")) && (! contentgroup_creators_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + contentgroup_creators_group + "|") >= 0))) {
						creator = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_creators_group:"+contentgroup_creators_group); }
					}
					if (config.get(db, "accessrestrictions").equals("or")) {
						if (((! contentgroup_creators_type.equals("")) && ((contentgroup_creators_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + contentgroup_creators_type + "|") >= 0))) || ((! contentgroup_creators_group.equals("")) && ((contentgroup_creators_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + contentgroup_creators_group + "|") >= 0)))) {
							creator = true;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_creators_group:"+contentgroup_creators_group + ":contentgroup_creators_type:"+contentgroup_creators_type); }
						}
					}
					if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
						if (! contentgroup_creators_users.equals("")) {
							if (session_userid.equals("")) {
								creator = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_creators_users:"+contentgroup_creators_users); }
							} else if ((! contentgroup_creators_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(contentgroup_creators_users).find())) {
								creator = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_creators_users:"+contentgroup_creators_users); }
							}
						}
					}
				}
			}
			if (creator) {
				if ((config.get(db, "use_contentdefinition").equals("yes")) && (! contentclass.equals("script")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("template")) && (((contentclass.equals("page")) && (config.get(db, "use_contenttypes").equals("yes"))) || ((contentclass.equals("file")) && (config.get(db, "use_filetypes").equals("yes"))) || ((contentclass.equals("image")) && (config.get(db, "use_imagetypes").equals("yes"))) || ((contentclass.equals("link")) && (config.get(db, "use_linktypes").equals("yes"))) || ((contentclass.equals("product")) && (config.get(db, "use_producttypes").equals("yes"))) || ((! contentclass.equals("page")) && (! contentclass.equals("file")) && (! contentclass.equals("image")) && (! contentclass.equals("link")) && (! contentclass.equals("product")) && (config.get(db, "use_contenttypes").equals("yes"))))) {
					if ((! contenttype_creators_type.equals("")) && (! contenttype_creators_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + contenttype_creators_type + "|") >= 0))) {
						creator = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_creators_type:"+contenttype_creators_type); }
					}
					if ((! contenttype_creators_group.equals("")) && (! contenttype_creators_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + contenttype_creators_group + "|") >= 0))) {
						creator = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_creators_group:"+contenttype_creators_group); }
					}
					if (config.get(db, "accessrestrictions").equals("or")) {
						if (((! contenttype_creators_type.equals("")) && ((contenttype_creators_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + contenttype_creators_type + "|") >= 0))) || ((! contenttype_creators_group.equals("")) && ((contenttype_creators_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + contenttype_creators_group + "|") >= 0)))) {
							creator = true;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_creators_group:"+contenttype_creators_group + ":contenttype_creators_type:"+contenttype_creators_type); }
						}
					}
					if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
						if (! contenttype_creators_users.equals("")) {
							if (session_userid.equals("")) {
								creator = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_creators_users:"+contenttype_creators_users); }
							} else if ((! contenttype_creators_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(contenttype_creators_users).find())) {
								creator = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_creators_users:"+contenttype_creators_users); }
							}
						}
					}
				}
			}
			if (creator) {
				if ((config.get(db, "website_creators_type").equals("*")) && (session_username.equals(""))) {
					creator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_creators_type:"+config.get(db, "website_creators_type")); }
				} else if ((config.get(db, "website_creators_type").equals("0")) && (! session_administrator.equals("administrator"))) {
					creator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_creators_type:"+config.get(db, "website_creators_type")); }
				} else if ((! config.get(db, "website_creators_type").equals("0")) && (! config.get(db, "website_creators_type").equals("*")) && (! config.get(db, "website_creators_type").equals("=")) && (! config.get(db, "website_creators_type").equals("")) && (! config.get(db, "website_creators_type").equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_creators_type") + "|") >= 0))) {
					creator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_creators_type:"+config.get(db, "website_creators_type")); }
				}
				if ((config.get(db, "website_creators_group").equals("*")) && (session_username.equals(""))) {
					creator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_creators_group:"+config.get(db, "website_creators_group")); }
				} else if ((config.get(db, "website_creators_group").equals("0")) && (! session_administrator.equals("administrator"))) {
					creator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_creators_group:"+config.get(db, "website_creators_group")); }
				} else if ((! config.get(db, "website_creators_group").equals("0")) && (! config.get(db, "website_creators_group").equals("*")) && (! config.get(db, "website_creators_group").equals("=")) && (! config.get(db, "website_creators_group").equals("")) && (! config.get(db, "website_creators_group").equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_creators_group") + "|") >= 0))) {
					creator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_creators_group:"+config.get(db, "website_creators_group")); }
				}
				if (config.get(db, "accessrestrictions").equals("or")) {
					if (((config.get(db, "website_creators_type").equals("*")) || (config.get(db, "website_creators_group").equals("*"))) && (! session_username.equals(""))) {
						creator = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_creators_group:"+config.get(db, "website_creators_group") + ":website_creators_type:"+config.get(db, "website_creators_type")); }
					}
					if (((config.get(db, "website_creators_type").equals("0")) || (config.get(db, "website_creators_group").equals("0"))) && (session_administrator.equals("administrator"))) {
						creator = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_creators_group:"+config.get(db, "website_creators_group") + ":website_creators_type:"+config.get(db, "website_creators_type")); }
					}
					if (((! config.get(db, "website_creators_type").equals("0")) && (! config.get(db, "website_creators_type").equals("*")) && (! config.get(db, "website_creators_type").equals("=")) && (! config.get(db, "website_creators_type").equals("")) && ((config.get(db, "website_creators_type").equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_creators_type") + "|") >= 0))) || ((! config.get(db, "website_creators_group").equals("0")) && (! config.get(db, "website_creators_group").equals("*")) && (! config.get(db, "website_creators_group").equals("=")) && (! config.get(db, "website_creators_group").equals("")) && ((config.get(db, "website_creators_group").equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_creators_group") + "|") >= 0)))) {
						creator = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_creators_group:"+config.get(db, "website_creators_group") + ":website_creators_type:"+config.get(db, "website_creators_type")); }
					}
				}
			}

			publisher = true;
			if ((publishers_type.equals("*")) && (session_username.equals(""))) {
				publisher = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":publishers_type:"+publishers_type); }
			} else if ((publishers_type.equals("=")) && (! session_username.equals(created_by))) {
				publisher = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":publishers_type:"+publishers_type); }
			} else if ((publishers_type.equals("")) && (! session_administrator.equals("administrator"))) {
				publisher = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":publishers_type:"+publishers_type); }
			} else if ((! publishers_type.equals("0")) && (! publishers_type.equals("*")) && (! publishers_type.equals("=")) && (! publishers_type.equals("")) && (! publishers_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + publishers_type + "|") >= 0))) {
				publisher = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":publishers_type:"+publishers_type); }
			}
			if ((publishers_group.equals("*")) && (session_username.equals(""))) {
				publisher = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":publishers_group:"+publishers_group); }
			} else if ((publishers_group.equals("=")) && (! session_username.equals(created_by))) {
				publisher = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":publishers_group:"+publishers_group); }
			} else if ((publishers_group.equals("")) && (! session_administrator.equals("administrator"))) {
				publisher = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":publishers_group:"+publishers_group); }
			} else if ((! publishers_group.equals("0")) && (! publishers_group.equals("*")) && (! publishers_group.equals("=")) && (! publishers_group.equals("")) && (! publishers_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + publishers_group + "|") >= 0))) {
				publisher = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":publishers_group:"+publishers_group); }
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((publishers_type.equals("*")) || (publishers_group.equals("*"))) && (! session_username.equals(""))) {
					publisher = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":publishers_group:"+publishers_group + ":publishers_type:"+publishers_type); }
				}
				if (((publishers_type.equals("=")) || (publishers_group.equals("="))) && (session_username.equals(created_by))) {
					publisher = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":publishers_group:"+publishers_group + ":publishers_type:"+publishers_type); }
				}
				if (((publishers_type.equals("")) || (publishers_group.equals(""))) && (session_administrator.equals("administrator"))) {
					publisher = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":publishers_group:"+publishers_group + ":publishers_type:"+publishers_type); }
				}
				if (((! publishers_type.equals("0")) && (! publishers_type.equals("*")) && (! publishers_type.equals("=")) && (! publishers_type.equals("")) && ((publishers_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + publishers_type + "|") >= 0))) || ((! publishers_group.equals("0")) && (! publishers_group.equals("*")) && (! publishers_group.equals("=")) && (! publishers_group.equals("")) && ((publishers_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + publishers_group + "|") >= 0)))) {
					publisher = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":publishers_group:"+publishers_group + ":publishers_type:"+publishers_type); }
				}
			}
			if (publisher) {
				if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
					if (! publishers_users.equals("")) {
						if (session_userid.equals("")) {
							publisher = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":publishers_users:"+publishers_users); }
						} else if ((! publishers_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(publishers_users).find())) {
							publisher = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":publishers_users:"+publishers_users); }
						}
					}
				}
			}
			if (publisher) {
				if ((config.get(db, "use_contentdefinition").equals("yes")) && (! contentclass.equals("script")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("template")) && (((contentclass.equals("page")) && (config.get(db, "use_contentgroups").equals("yes"))) || ((contentclass.equals("file")) && (config.get(db, "use_filegroups").equals("yes"))) || ((contentclass.equals("image")) && (config.get(db, "use_imagegroups").equals("yes"))) || ((contentclass.equals("link")) && (config.get(db, "use_linkgroups").equals("yes"))) || ((contentclass.equals("product")) && (config.get(db, "use_productgroups").equals("yes"))) || ((! contentclass.equals("page")) && (! contentclass.equals("file")) && (! contentclass.equals("image")) && (! contentclass.equals("link")) && (! contentclass.equals("product")) && (config.get(db, "use_contentgroups").equals("yes"))))) {
					if ((! contentgroup_publishers_type.equals("")) && (! contentgroup_publishers_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + contentgroup_publishers_type + "|") >= 0))) {
						publisher = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_publishers_type:"+contentgroup_publishers_type); }
					}
					if ((! contentgroup_publishers_group.equals("")) && (! contentgroup_publishers_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + contentgroup_publishers_group + "|") >= 0))) {
						publisher = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_publishers_group:"+contentgroup_publishers_group); }
					}
					if (config.get(db, "accessrestrictions").equals("or")) {
						if (((! contentgroup_publishers_type.equals("")) && ((contentgroup_publishers_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + contentgroup_publishers_type + "|") >= 0))) || ((! contentgroup_publishers_group.equals("")) && ((contentgroup_publishers_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + contentgroup_publishers_group + "|") >= 0)))) {
							publisher = true;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_publishers_group:"+contentgroup_publishers_group + ":contentgroup_publishers_type:"+contentgroup_publishers_type); }
						}
					}
					if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
						if (! contentgroup_publishers_users.equals("")) {
							if (session_userid.equals("")) {
								publisher = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_publishers_users:"+contentgroup_publishers_users); }
							} else if ((! contentgroup_publishers_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(contentgroup_publishers_users).find())) {
								publisher = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_publishers_users:"+contentgroup_publishers_users); }
							}
						}
					}
				}
			}
			if (publisher) {
				if ((config.get(db, "use_contentdefinition").equals("yes")) && (! contentclass.equals("script")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("template")) && (((contentclass.equals("page")) && (config.get(db, "use_contenttypes").equals("yes"))) || ((contentclass.equals("file")) && (config.get(db, "use_filetypes").equals("yes"))) || ((contentclass.equals("image")) && (config.get(db, "use_imagetypes").equals("yes"))) || ((contentclass.equals("link")) && (config.get(db, "use_linktypes").equals("yes"))) || ((contentclass.equals("product")) && (config.get(db, "use_producttypes").equals("yes"))) || ((! contentclass.equals("page")) && (! contentclass.equals("file")) && (! contentclass.equals("image")) && (! contentclass.equals("link")) && (! contentclass.equals("product")) && (config.get(db, "use_contenttypes").equals("yes"))))) {
					if ((! contenttype_publishers_type.equals("")) && (! contenttype_publishers_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + contenttype_publishers_type + "|") >= 0))) {
						publisher = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_publishers_type:"+contenttype_publishers_type); }
					}
					if ((! contenttype_publishers_group.equals("")) && (! contenttype_publishers_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + contenttype_publishers_group + "|") >= 0))) {
						publisher = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_publishers_group:"+contenttype_publishers_group); }
					}
					if (config.get(db, "accessrestrictions").equals("or")) {
						if (((! contenttype_publishers_type.equals("")) && ((contenttype_publishers_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + contenttype_publishers_type + "|") >= 0))) || ((! contenttype_publishers_group.equals("")) && ((contenttype_publishers_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + contenttype_publishers_group + "|") >= 0)))) {
							publisher = true;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_publishers_group:"+contenttype_publishers_group + ":contenttype_publishers_type:"+contenttype_publishers_type); }
						}
					}
					if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
						if (! contenttype_publishers_users.equals("")) {
							if (session_userid.equals("")) {
								publisher = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_publishers_users:"+contenttype_publishers_users); }
							} else if ((! contenttype_publishers_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(contenttype_publishers_users).find())) {
								publisher = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_publishers_users:"+contenttype_publishers_users); }
							}
						}
					}
				}
			}
			if (publisher) {
				if ((config.get(db, "website_publishers_type").equals("*")) && (session_username.equals(""))) {
					publisher = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_publishers_type:"+config.get(db, "website_publishers_type")); }
				} else if ((config.get(db, "website_publishers_type").equals("0")) && (! session_administrator.equals("administrator"))) {
					publisher = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_publishers_type:"+config.get(db, "website_publishers_type")); }
				} else if ((! config.get(db, "website_publishers_type").equals("0")) && (! config.get(db, "website_publishers_type").equals("*")) && (! config.get(db, "website_publishers_type").equals("=")) && (! config.get(db, "website_publishers_type").equals("")) && (! config.get(db, "website_publishers_type").equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_publishers_type") + "|") >= 0))) {
					publisher = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_publishers_type:"+config.get(db, "website_publishers_type")); }
				}
				if ((config.get(db, "website_publishers_group").equals("*")) && (session_username.equals(""))) {
					publisher = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_publishers_group:"+config.get(db, "website_publishers_group")); }
				} else if ((config.get(db, "website_publishers_group").equals("0")) && (! session_administrator.equals("administrator"))) {
					publisher = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_publishers_group:"+config.get(db, "website_publishers_group")); }
				} else if ((! config.get(db, "website_publishers_group").equals("0")) && (! config.get(db, "website_publishers_group").equals("*")) && (! config.get(db, "website_publishers_group").equals("=")) && (! config.get(db, "website_publishers_group").equals("")) && (! config.get(db, "website_publishers_group").equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_publishers_group") + "|") >= 0))) {
					publisher = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_publishers_group:"+config.get(db, "website_publishers_group")); }
				}
				if (config.get(db, "accessrestrictions").equals("or")) {
					if (((config.get(db, "website_publishers_type").equals("*")) || (config.get(db, "website_publishers_group").equals("*"))) && (! session_username.equals(""))) {
						publisher = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_publishers_group:"+config.get(db, "website_publishers_group") + ":website_publishers_type:"+config.get(db, "website_publishers_type")); }
					}
					if (((config.get(db, "website_publishers_type").equals("0")) || (config.get(db, "website_publishers_group").equals("0"))) && (session_administrator.equals("administrator"))) {
						publisher = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_publishers_group:"+config.get(db, "website_publishers_group") + ":website_publishers_type:"+config.get(db, "website_publishers_type")); }
					}
					if (((! config.get(db, "website_publishers_type").equals("0")) && (! config.get(db, "website_publishers_type").equals("*")) && (! config.get(db, "website_publishers_type").equals("=")) && (! config.get(db, "website_publishers_type").equals("")) && ((config.get(db, "website_publishers_type").equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_publishers_type") + "|") >= 0))) || ((! config.get(db, "website_publishers_group").equals("0")) && (! config.get(db, "website_publishers_group").equals("*")) && (! config.get(db, "website_publishers_group").equals("=")) && (! config.get(db, "website_publishers_group").equals("")) && ((config.get(db, "website_publishers_group").equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_publishers_group") + "|") >= 0)))) {
						publisher = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_publishers_group:"+config.get(db, "website_publishers_group") + ":website_publishers_type:"+config.get(db, "website_publishers_type")); }
					}
				}
			}

			developer = true;
			if ((developers_type.equals("*")) && (session_username.equals(""))) {
				developer = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":developers_type:"+developers_type); }
			} else if ((developers_type.equals("=")) && (! session_username.equals(created_by))) {
				developer = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":developers_type:"+developers_type); }
			} else if ((developers_type.equals("")) && (! session_administrator.equals("administrator"))) {
				developer = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":developers_type:"+developers_type); }
			} else if ((! developers_type.equals("0")) && (! developers_type.equals("*")) && (! developers_type.equals("=")) && (! developers_type.equals("")) && (! developers_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + developers_type + "|") >= 0))) {
				developer = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":developers_type:"+developers_type); }
			}
			if ((developers_group.equals("*")) && (session_username.equals(""))) {
				developer = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":developers_group:"+developers_group); }
			} else if ((developers_group.equals("=")) && (! session_username.equals(created_by))) {
				developer = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":developers_group:"+developers_group); }
			} else if ((developers_group.equals("")) && (! session_administrator.equals("administrator"))) {
				developer = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":developers_group:"+developers_group); }
			} else if ((! developers_group.equals("0")) && (! developers_group.equals("*")) && (! developers_group.equals("=")) && (! developers_group.equals("")) && (! developers_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + developers_group + "|") >= 0))) {
				developer = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":developers_group:"+developers_group); }
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((developers_type.equals("*")) || (developers_group.equals("*"))) && (! session_username.equals(""))) {
					developer = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":developers_group:"+developers_group + ":developers_type:"+developers_type); }
				}
				if (((developers_type.equals("=")) || (developers_group.equals("="))) && (session_username.equals(created_by))) {
					developer = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":developers_group:"+developers_group + ":developers_type:"+developers_type); }
				}
				if (((developers_type.equals("")) || (developers_group.equals(""))) && (session_administrator.equals("administrator"))) {
					developer = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":developers_group:"+developers_group + ":developers_type:"+developers_type); }
				}
				if (((! developers_type.equals("0")) && (! developers_type.equals("*")) && (! developers_type.equals("=")) && (! developers_type.equals("")) && ((developers_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + developers_type + "|") >= 0))) || ((! developers_group.equals("0")) && (! developers_group.equals("*")) && (! developers_group.equals("=")) && (! developers_group.equals("")) && ((developers_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + developers_group + "|") >= 0)))) {
					developer = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":developers_group:"+developers_group + ":developers_type:"+developers_type); }
				}
			}
			if (developer) {
				if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
					if (! developers_users.equals("")) {
						if (session_userid.equals("")) {
							developer = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":developers_users:"+developers_users); }
						} else if ((! developers_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(developers_users).find())) {
							developer = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":developers_users:"+developers_users); }
						}
					}
				}
			}
			if (developer) {
				if ((config.get(db, "use_contentdefinition").equals("yes")) && (! contentclass.equals("script")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("template")) && (((contentclass.equals("page")) && (config.get(db, "use_contentgroups").equals("yes"))) || ((contentclass.equals("file")) && (config.get(db, "use_filegroups").equals("yes"))) || ((contentclass.equals("image")) && (config.get(db, "use_imagegroups").equals("yes"))) || ((contentclass.equals("link")) && (config.get(db, "use_linkgroups").equals("yes"))) || ((contentclass.equals("product")) && (config.get(db, "use_productgroups").equals("yes"))) || ((! contentclass.equals("page")) && (! contentclass.equals("file")) && (! contentclass.equals("image")) && (! contentclass.equals("link")) && (! contentclass.equals("product")) && (config.get(db, "use_contentgroups").equals("yes"))))) {
					if ((! contentgroup_developers_type.equals("")) && (! contentgroup_developers_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + contentgroup_developers_type + "|") >= 0))) {
						developer = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_developers_type:"+contentgroup_developers_type); }
					}
					if ((! contentgroup_developers_group.equals("")) && (! contentgroup_developers_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + contentgroup_developers_group + "|") >= 0))) {
						developer = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_developers_group:"+contentgroup_developers_group); }
					}
					if (config.get(db, "accessrestrictions").equals("or")) {
						if (((! contentgroup_developers_type.equals("")) && ((contentgroup_developers_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + contentgroup_developers_type + "|") >= 0))) || ((! contentgroup_developers_group.equals("")) && ((contentgroup_developers_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + contentgroup_developers_group + "|") >= 0)))) {
							developer = true;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_developers_group:"+contentgroup_developers_group + ":contentgroup_developers_type:"+contentgroup_developers_type); }
						}
					}
					if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
						if (! contentgroup_developers_users.equals("")) {
							if (session_userid.equals("")) {
								developer = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_developers_users:"+contentgroup_developers_users); }
							} else if ((! contentgroup_developers_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(contentgroup_developers_users).find())) {
								developer = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_developers_users:"+contentgroup_developers_users); }
							}
						}
					}
				}
			}
			if (developer) {
				if ((config.get(db, "use_contentdefinition").equals("yes")) && (! contentclass.equals("script")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("template")) && (((contentclass.equals("page")) && (config.get(db, "use_contenttypes").equals("yes"))) || ((contentclass.equals("file")) && (config.get(db, "use_filetypes").equals("yes"))) || ((contentclass.equals("image")) && (config.get(db, "use_imagetypes").equals("yes"))) || ((contentclass.equals("link")) && (config.get(db, "use_linktypes").equals("yes"))) || ((contentclass.equals("product")) && (config.get(db, "use_producttypes").equals("yes"))) || ((! contentclass.equals("page")) && (! contentclass.equals("file")) && (! contentclass.equals("image")) && (! contentclass.equals("link")) && (! contentclass.equals("product")) && (config.get(db, "use_contenttypes").equals("yes"))))) {
					if ((! contenttype_developers_type.equals("")) && (! contenttype_developers_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + contenttype_developers_type + "|") >= 0))) {
						developer = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_developers_type:"+contenttype_developers_type); }
					}
					if ((! contenttype_developers_group.equals("")) && (! contenttype_developers_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + contenttype_developers_group + "|") >= 0))) {
						developer = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_developers_group:"+contenttype_developers_group); }
					}
					if (config.get(db, "accessrestrictions").equals("or")) {
						if (((! contenttype_developers_type.equals("")) && ((contenttype_developers_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + contenttype_developers_type + "|") >= 0))) || ((! contenttype_developers_group.equals("")) && ((contenttype_developers_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + contenttype_developers_group + "|") >= 0)))) {
							developer = true;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_developers_group:"+contenttype_developers_group + ":contenttype_developers_type:"+contenttype_developers_type); }
						}
					}
					if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
						if (! contenttype_developers_users.equals("")) {
							if (session_userid.equals("")) {
								developer = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_developers_users:"+contenttype_developers_users); }
							} else if ((! contenttype_developers_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(contenttype_developers_users).find())) {
								developer = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_developers_users:"+contenttype_developers_users); }
							}
						}
					}
				}
			}
			if (developer) {
				if ((! config.get(db, "website_developers_type").equals("")) && (! config.get(db, "website_developers_type").equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_developers_type") + "|") >= 0))) {
					developer = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_developers_type:"+config.get(db, "website_developers_type")); }
				}
				if ((! config.get(db, "website_developers_group").equals("")) && (! config.get(db, "website_developers_group").equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_developers_group") + "|") >= 0))) {
					developer = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_developers_group:"+config.get(db, "website_developers_group")); }
				}
				if (config.get(db, "accessrestrictions").equals("or")) {
					if (((! config.get(db, "website_developers_type").equals("")) && ((config.get(db, "website_developers_type").equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_developers_type") + "|") >= 0))) || ((! config.get(db, "website_developers_group").equals("")) && ((config.get(db, "website_developers_group").equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_developers_group") + "|") >= 0)))) {
						developer = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_developers_group:"+config.get(db, "website_developers_group") + ":website_developers_type:"+config.get(db, "website_developers_type")); }
					}
				}
			}

			administrator = true;
			if ((administrators_type.equals("*")) && (session_username.equals(""))) {
				administrator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":administrators_type:"+administrators_type); }
			} else if ((administrators_type.equals("=")) && (! session_username.equals(created_by))) {
				administrator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":administrators_type:"+administrators_type); }
			} else if ((administrators_type.equals("")) && (! session_administrator.equals("administrator"))) {
				administrator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":administrators_type:"+administrators_type); }
			} else if ((! administrators_type.equals("0")) && (! administrators_type.equals("*")) && (! administrators_type.equals("=")) && (! administrators_type.equals("")) && (! administrators_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + administrators_type + "|") >= 0))) {
				administrator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":administrators_type:"+administrators_type); }
			}
			if ((administrators_group.equals("*")) && (session_username.equals(""))) {
				administrator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":administrators_group:"+administrators_group); }
			} else if ((administrators_group.equals("=")) && (! session_username.equals(created_by))) {
				administrator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":administrators_group:"+administrators_group); }
			} else if ((administrators_group.equals("")) && (! session_administrator.equals("administrator"))) {
				administrator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":administrators_group:"+administrators_group); }
			} else if ((! administrators_group.equals("0")) && (! administrators_group.equals("*")) && (! administrators_group.equals("=")) && (! administrators_group.equals("")) && (! administrators_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + administrators_group + "|") >= 0))) {
				administrator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":administrators_group:"+administrators_group); }
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((administrators_type.equals("*")) || (administrators_group.equals("*"))) && (! session_username.equals(""))) {
					administrator = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":administrators_group:"+administrators_group + ":administrators_type:"+administrators_type); }
				}
				if (((administrators_type.equals("=")) || (administrators_group.equals("="))) && (session_username.equals(created_by))) {
					administrator = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":administrators_group:"+administrators_group + ":administrators_type:"+administrators_type); }
				}
				if (((administrators_type.equals("")) || (administrators_group.equals(""))) && (session_administrator.equals("administrator"))) {
					administrator = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":administrators_group:"+administrators_group + ":administrators_type:"+administrators_type); }
				}
				if (((! administrators_type.equals("0")) && (! administrators_type.equals("*")) && (! administrators_type.equals("=")) && (! administrators_type.equals("")) && ((administrators_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + administrators_type + "|") >= 0))) || ((! administrators_group.equals("0")) && (! administrators_group.equals("*")) && (! administrators_group.equals("=")) && (! administrators_group.equals("")) && ((administrators_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + administrators_group + "|") >= 0)))) {
					administrator = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":administrators_group:"+administrators_group + ":administrators_type:"+administrators_type); }
				}
			}
			if (administrator) {
				if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
					if (! administrators_users.equals("")) {
						if (session_userid.equals("")) {
							administrator = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":administrators_users:"+administrators_users); }
						} else if ((! administrators_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(administrators_users).find())) {
							administrator = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":administrators_users:"+administrators_users); }
						}
					}
				}
			}
			if (administrator) {
				if ((config.get(db, "use_contentdefinition").equals("yes")) && (! contentclass.equals("script")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("template")) && (((contentclass.equals("page")) && (config.get(db, "use_contentgroups").equals("yes"))) || ((contentclass.equals("file")) && (config.get(db, "use_filegroups").equals("yes"))) || ((contentclass.equals("image")) && (config.get(db, "use_imagegroups").equals("yes"))) || ((contentclass.equals("link")) && (config.get(db, "use_linkgroups").equals("yes"))) || ((contentclass.equals("product")) && (config.get(db, "use_productgroups").equals("yes"))) || ((! contentclass.equals("page")) && (! contentclass.equals("file")) && (! contentclass.equals("image")) && (! contentclass.equals("link")) && (! contentclass.equals("product")) && (config.get(db, "use_contentgroups").equals("yes"))))) {
					if ((! contentgroup_administrators_type.equals("")) && (! contentgroup_administrators_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + contentgroup_administrators_type + "|") >= 0))) {
						administrator = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_administrators_type:"+contentgroup_administrators_type); }
					}
					if ((! contentgroup_administrators_group.equals("")) && (! contentgroup_administrators_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + contentgroup_administrators_group + "|") >= 0))) {
						administrator = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_administrators_group:"+contentgroup_administrators_group); }
					}
					if (config.get(db, "accessrestrictions").equals("or")) {
						if (((! contentgroup_administrators_type.equals("")) && ((contentgroup_administrators_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + contentgroup_administrators_type + "|") >= 0))) || ((! contentgroup_administrators_group.equals("")) && ((contentgroup_administrators_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + contentgroup_administrators_group + "|") >= 0)))) {
							administrator = true;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_administrators_group:"+contentgroup_administrators_group + ":contentgroup_administrators_type:"+contentgroup_administrators_type); }
						}
					}
					if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
						if (! contentgroup_administrators_users.equals("")) {
							if (session_userid.equals("")) {
								administrator = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_administrators_users:"+contentgroup_administrators_users); }
							} else if ((! contentgroup_administrators_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(contentgroup_administrators_users).find())) {
								administrator = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contentgroup_administrators_users:"+contentgroup_administrators_users); }
							}
						}
					}
				}
			}
			if (administrator) {
				if ((config.get(db, "use_contentdefinition").equals("yes")) && (! contentclass.equals("script")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("template")) && (((contentclass.equals("page")) && (config.get(db, "use_contenttypes").equals("yes"))) || ((contentclass.equals("file")) && (config.get(db, "use_filetypes").equals("yes"))) || ((contentclass.equals("image")) && (config.get(db, "use_imagetypes").equals("yes"))) || ((contentclass.equals("link")) && (config.get(db, "use_linktypes").equals("yes"))) || ((contentclass.equals("product")) && (config.get(db, "use_producttypes").equals("yes"))) || ((! contentclass.equals("page")) && (! contentclass.equals("file")) && (! contentclass.equals("image")) && (! contentclass.equals("link")) && (! contentclass.equals("product")) && (config.get(db, "use_contenttypes").equals("yes"))))) {
					if ((! contenttype_administrators_type.equals("")) && (! contenttype_administrators_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + contenttype_administrators_type + "|") >= 0))) {
						administrator = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_administrators_type:"+contenttype_administrators_type); }
					}
					if ((! contenttype_administrators_group.equals("")) && (! contenttype_administrators_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + contenttype_administrators_group + "|") >= 0))) {
						administrator = false;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_administrators_group:"+contenttype_administrators_group); }
					}
					if (config.get(db, "accessrestrictions").equals("or")) {
						if (((! contenttype_administrators_type.equals("")) && ((contenttype_administrators_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + contenttype_administrators_type + "|") >= 0))) || ((! contenttype_administrators_group.equals("")) && ((contenttype_administrators_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + contenttype_administrators_group + "|") >= 0)))) {
							administrator = true;
							if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_administrators_group:"+contenttype_administrators_group + ":contenttype_administrators_type:"+contenttype_administrators_type); }
						}
					}
					if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
						if (! contenttype_administrators_users.equals("")) {
							if (session_userid.equals("")) {
								administrator = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_administrators_users:"+contenttype_administrators_users); }
							} else if ((! contenttype_administrators_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(contenttype_administrators_users).find())) {
								administrator = false;
								if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":contenttype_administrators_users:"+contenttype_administrators_users); }
							}
						}
					}
				}
			}
			if (administrator) {
				if ((config.get(db, "website_administrators_type").equals("*")) && (session_username.equals(""))) {
					administrator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_administrators_type:"+config.get(db, "website_administrators_type")); }
				} else if ((config.get(db, "website_administrators_type").equals("")) && (! session_administrator.equals("administrator"))) {
					administrator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_administrators_type:"+config.get(db, "website_administrators_type")); }
				} else if ((! config.get(db, "website_administrators_type").equals("0")) && (! config.get(db, "website_administrators_type").equals("*")) && (! config.get(db, "website_administrators_type").equals("=")) && (! config.get(db, "website_administrators_type").equals("")) && (! config.get(db, "website_administrators_type").equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_administrators_type") + "|") >= 0))) {
					administrator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_administrators_type:"+config.get(db, "website_administrators_type")); }
				}
				if ((config.get(db, "website_administrators_group").equals("*")) && (session_username.equals(""))) {
					administrator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_administrators_group:"+config.get(db, "website_administrators_group")); }
				} else if ((config.get(db, "website_administrators_group").equals("")) && (! session_administrator.equals("administrator"))) {
					administrator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_administrators_group:"+config.get(db, "website_administrators_group")); }
				} else if ((! config.get(db, "website_administrators_group").equals("0")) && (! config.get(db, "website_administrators_group").equals("*")) && (! config.get(db, "website_administrators_group").equals("=")) && (! config.get(db, "website_administrators_group").equals("")) && (! config.get(db, "website_administrators_group").equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_administrators_group") + "|") >= 0))) {
					administrator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_administrators_group:"+config.get(db, "website_administrators_group")); }
				}
				if (config.get(db, "accessrestrictions").equals("or")) {
					if (((config.get(db, "website_administrators_type").equals("*")) || (config.get(db, "website_administrators_group").equals("*"))) && (! session_username.equals(""))) {
						administrator = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_administrators_group:"+config.get(db, "website_administrators_group") + ":website_administrators_type:"+config.get(db, "website_administrators_type")); }
					}
					if (((config.get(db, "website_administrators_type").equals("")) || (config.get(db, "website_administrators_group").equals(""))) && (session_administrator.equals("administrator"))) {
						administrator = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_administrators_group:"+config.get(db, "website_administrators_group") + ":website_administrators_type:"+config.get(db, "website_administrators_type")); }
					}
					if (((! config.get(db, "website_administrators_type").equals("0")) && (! config.get(db, "website_administrators_type").equals("*")) && (! config.get(db, "website_administrators_type").equals("=")) && (! config.get(db, "website_administrators_type").equals("")) && ((config.get(db, "website_administrators_type").equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_administrators_type") + "|") >= 0))) || ((! config.get(db, "website_administrators_group").equals("0")) && (! config.get(db, "website_administrators_group").equals("*")) && (! config.get(db, "website_administrators_group").equals("=")) && (! config.get(db, "website_administrators_group").equals("")) && ((config.get(db, "website_administrators_group").equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_administrators_group") + "|") >= 0)))) {
						administrator = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":website_administrators_group:"+config.get(db, "website_administrators_group") + ":website_administrators_type:"+config.get(db, "website_administrators_type")); }
					}
				}
			}

		}

		if ((config != null) && (! config.get(db, "inherit_accessrestrictions").equals("no"))) {
			if (administrator) {
				user = true;
				editor = true;
				creator = true;
				publisher = true;
				developer = true;
			} else if (developer) {
				user = true;
				editor = true;
			} else if (publisher) {
				user = true;
				editor = true;
			} else if (creator) {
				user = true;
				editor = true;
			} else if (editor) {
				user = true;
			}
		}

		if ((id.equals("")) && (! contentclass.equals("script")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("template")) && (contentgroup.equals("")) && (contenttype.equals("")) && (! session_username.equals(config.get(db, "superadmin")))) {
			editor = false;
			creator = false;
			publisher = false;
			developer = false;
			administrator = false;
		}

		if (checkedout.equals("-creators-")) {
			if ((! creator) && (! administrator)) {
				editor = false;
				creator = false;
				publisher = false;
				developer = false;
				administrator = false;
			}
		} else if (checkedout.equals("-editors-")) {
			if ((! creator) && (! editor) && (! developer) && (! publisher) && (! administrator)) {
				editor = false;
				creator = false;
				publisher = false;
				developer = false;
				administrator = false;
			}
		} else if (checkedout.equals("-developers-")) {
			if ((! developer) && (! administrator)) {
				editor = false;
				creator = false;
				publisher = false;
				developer = false;
				administrator = false;
			}
		} else if (checkedout.equals("-publishers-")) {
			if ((! publisher) && (! administrator)) {
				editor = false;
				creator = false;
				publisher = false;
				developer = false;
				administrator = false;
			}
		} else if (checkedout.equals("-administrators-")) {
			if (! administrator) {
				editor = false;
				creator = false;
				publisher = false;
				developer = false;
				administrator = false;
			}
		} else if ((! checkedout.equals("")) && (! checkedout.equals(session_username))) {
			editor = false;
			creator = false;
			publisher = false;
			developer = false;
			administrator = false;
		}

		if (isLockedUser()) {
			user = false;
		}
		if (isLockedEditor()) {
			editor = false;
		}
		if (isLockedCreator()) {
			creator = false;
		}
		if (isLockedPublisher()) {
			publisher = false;
		}
		if (isLockedDeveloper()) {
			developer = false;
		}
		if (isLockedAdministrator()) {
			administrator = false;
		}

		if (session_username.equals(config.get(db, "superadmin"))) {
			user = true;
			editor = true;
			creator = true;
			publisher = true;
			developer = true;
			administrator = true;

			if (session_username.equals("")) {
				editor = false;
				creator = false;
				publisher = false;
				developer = false;
				administrator = false;
			}
		}

		if ((config != null) && (! config.get(db, "inherit_accessrestrictions").equals("no"))) {
			if (administrator) {
				user = true;
				editor = true;
				creator = true;
				publisher = true;
				developer = true;
			} else if (developer) {
				user = true;
				editor = true;
			} else if (publisher) {
				user = true;
				editor = true;
			} else if (creator) {
				user = true;
				editor = true;
			} else if (editor) {
				user = true;
			}
		}

		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		if ((! getScheduledPublish().equals("")) && (getScheduledPublish().compareTo(now) > 0) && (! isLockedSchedule()) && (! config.get(db, "use_accessrestrictions_scheduled").equals(""))) {
			if ((config.get(db, "use_accessrestrictions_scheduled").equals("administrator")) && (! administrator)) {
				user = false;
				editor = false;
				creator = false;
				developer = false;
				publisher = false;
			} else if ((config.get(db, "use_accessrestrictions_scheduled").equals("publisher")) && (! publisher)) {
				user = false;
				editor = false;
				creator = false;
				developer = false;
			} else if ((config.get(db, "use_accessrestrictions_scheduled").equals("developer")) && (! developer)) {
				user = false;
				editor = false;
			} else if ((config.get(db, "use_accessrestrictions_scheduled").equals("creator")) && (! creator)) {
				user = false;
				editor = false;
			} else if ((config.get(db, "use_accessrestrictions_scheduled").equals("editor")) && (! editor)) {
				user = false;
			}
		}

		if (_DEBUG_) { System.out.println("AsbruWCM/Content.getAccessRestrictions:" + id + ":user:"+user + ":creator:"+creator + ":editor:"+editor + ":publisher:"+publisher + ":developer:"+developer + ":administrator:"+administrator); }
	}



	public void getForm(DB db, Configuration config, Fileupload form) {
		if (form.parameterExists("status")) status = form.getParameter("status");
		if (form.parameterExists("title")) title = form.getParameter("title");
		if (form.parameterExists("searchable")) searchable = form.getParameter("searchable");
		if (form.parameterExists("menuitem")) menuitem = form.getParameter("menuitem");
		if (form.parameterExists("contentformat")) contentformat = form.getParameter("contentformat");
		if (form.parameterExists("content")) {
			content = form.getParameter("content");
			if (! contentformat.equals("text")) {
				content = content.replaceAll("value=###([^#]+)###", "value=\"###$1###\"");
				content = content.replaceAll("value=@@@([^@]+)@@@", "value=\"@@@$1@@@\"");
				content = content.replaceAll("VALUE=###([^#]+)###", "VALUE=\"###$1###\"");
				content = content.replaceAll("VALUE=@@@([^@]+)@@@", "VALUE=\"@@@$1@@@\"");
			}
		}
		if (form.parameterExists("summary")) summary = form.getParameter("summary");
		if (form.parameterExists("image1")) image1 = form.getParameter("image1");
		if (form.parameterExists("image2")) image2 = form.getParameter("image2");
		if (form.parameterExists("image3")) image3 = form.getParameter("image3");
		if (form.parameterExists("file1")) file1 = form.getParameter("file1");
		if (form.parameterExists("file2")) file2 = form.getParameter("file2");
		if (form.parameterExists("file3")) file3 = form.getParameter("file3");
		if (form.parameterExists("link1")) link1 = form.getParameter("link1");
		if (form.parameterExists("link2")) link2 = form.getParameter("link2");
		if (form.parameterExists("link3")) link3 = form.getParameter("link3");
		if (form.parameterExists("url")) url = form.getParameter("url");
		if (! form.getParameter("file.filename").equals("")) {
			upload_filename = form.getParameter("file.upload_filename");
			server_filename = form.getParameter("file.server_filename");
		}
		if (config.get(db, "use_presentation").equals("yes")) {
			if (form.parameterExists("template")) template = form.getParameter("template");
			if (form.parameterExists("stylesheet")) stylesheet = form.getParameter("stylesheet");
		}
		if (config.get(db, "use_metainformation").equals("yes")) {
			if (form.parameterExists("author")) author = form.getParameter("author");
			if (form.parameterExists("keywords")) keywords = form.getParameter("keywords");
			if (form.parameterExists("description")) description = form.getParameter("description");
			if (form.parameterExists("metainfo")) metainfo = form.getParameter("metainfo");
			if (form.parameterExists("segmentation")) segmentation = form.getParameter("segmentation");
		}
		if (config.get(db, "use_additionalcontent").equals("yes")) {
			int elementi = 0;
			while (form.parameterExists("element" + elementi + "name")) {
				if (! form.getParameter("element" + elementi + "value").equals("")) {
					element.put(form.getParameter("element" + elementi + "name"), form.getParameter("element" + elementi + "value"));
				} else {
					element.remove(form.getParameter("element" + elementi + "name"));
				}
				elementi++;
			}
		}
		if (config.get(db, "use_revisionhistory").equals("yes")) {
			if (form.parameterExists("revision")) revision = form.getParameter("revision");
		}
		if (config.get(db, "use_versions").equals("yes")) {
			if (form.parameterExists("device")) device = form.getParameter("device");
			if (form.parameterExists("usersegment")) usersegment = form.getParameter("usersegment");
			if (form.parameterExists("usertest")) usertest = form.getParameter("usertest");
			if (form.parameterExists("version")) version = form.getParameter("version");
			if (form.parameterExists("version_master")) version_master = form.getParameter("version_master");
		}
		if (developer) {
			if (config.get(db, "use_advancedscripting").equals("yes")) {
				if (form.parameterExists("scripts")) scripts = form.getParameter("scripts");
				if (form.parameterExists("doctype")) doctype = form.getParameter("doctype");
				if (form.parameterExists("htmlattributes")) htmlattributes = form.getParameter("htmlattributes");
				if (form.parameterExists("htmlheader")) htmlheader = form.getParameter("htmlheader");
				if (form.parameterExists("htmlbodyonload")) htmlbodyonload = form.getParameter("htmlbodyonload");
			}
		}
		if (publisher) {
			if (config.get(db, "use_scheduled_publish").equals("yes")) {
				if (form.parameterExists("scheduled_publish")) scheduled_publish = form.getParameter("scheduled_publish");
			}
			if (config.get(db, "use_scheduled_unpublish").equals("yes")) {
				if (form.parameterExists("scheduled_unpublish")) scheduled_unpublish = form.getParameter("scheduled_unpublish");
			}
		}
		if (form.parameterExists("scheduled_publish")) requested_publish = form.getParameter("scheduled_publish");
		if (form.parameterExists("scheduled_unpublish")) requested_unpublish = form.getParameter("scheduled_unpublish");
		if (form.parameterExists("contentbundle")) contentbundle = form.getParameter("contentbundle");
		if (administrator) {
			if (form.parameterExists("contentpackage")) contentpackage = form.getParameter("contentpackage");
			if (form.parameterExists("contentclass")) contentclass = form.getParameter("contentclass");
			if (config.get(db, "use_contentdefinition").equals("yes")) {
				if ((config.get(db, "use_contenttypes").equals("yes") && (! contentclass.equals("product")) && (! contentclass.equals("file")) && (! contentclass.equals("image")) && (! contentclass.equals("link")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("script"))) || (config.get(db, "use_imagetypes").equals("yes") && contentclass.equals("image")) || (config.get(db, "use_filetypes").equals("yes") && contentclass.equals("file")) || (config.get(db, "use_linktypes").equals("yes") && contentclass.equals("link")) || (config.get(db, "use_producttypes").equals("yes") && contentclass.equals("product"))) {
					if (form.parameterExists("contenttype")) contenttype = form.getParameter("contenttype");
				}
				if ((config.get(db, "use_contentgroups").equals("yes") && (! contentclass.equals("product")) && (! contentclass.equals("file")) && (! contentclass.equals("image")) && (! contentclass.equals("link")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("script"))) || (config.get(db, "use_imagegroups").equals("yes") && contentclass.equals("image")) || (config.get(db, "use_filegroups").equals("yes") && contentclass.equals("file")) || (config.get(db, "use_linkgroups").equals("yes") && contentclass.equals("link")) || (config.get(db, "use_productgroups").equals("yes") && contentclass.equals("product"))) {
					if (form.parameterExists("contentgroup")) contentgroup = form.getParameter("contentgroup");
				}
			}
			if (! config.get(db, "use_accessrestrictions").equals("none")) {
				if (form.parameterExists("users_users")) users_users = form.getParameter("users_users");
				if (form.parameterExists("users_type")) users_type = form.getParameter("users_type");
				if (form.parameterExists("users_group")) users_group = form.getParameter("users_group");
				if (config.get(db, "use_accessrestrictions").equals("all")) {
					if (form.parameterExists("developers_users")) developers_users = form.getParameter("developers_users");
					if (form.parameterExists("developers_type")) developers_type = form.getParameter("developers_type");
					if (form.parameterExists("developers_group")) developers_group = form.getParameter("developers_group");
					if (form.parameterExists("creators_users")) creators_users = form.getParameter("creators_users");
					if (form.parameterExists("creators_type")) creators_type = form.getParameter("creators_type");
					if (form.parameterExists("creators_group")) creators_group = form.getParameter("creators_group");
					if (form.parameterExists("editors_users")) editors_users = form.getParameter("editors_users");
					if (form.parameterExists("editors_type")) editors_type = form.getParameter("editors_type");
					if (form.parameterExists("editors_group")) editors_group = form.getParameter("editors_group");
					if (form.parameterExists("publishers_users")) publishers_users = form.getParameter("publishers_users");
					if (form.parameterExists("publishers_type")) publishers_type = form.getParameter("publishers_type");
					if (form.parameterExists("publishers_group")) publishers_group = form.getParameter("publishers_group");
					if (form.parameterExists("administrators_users")) administrators_users = form.getParameter("administrators_users");
					if (form.parameterExists("administrators_type")) administrators_type = form.getParameter("administrators_type");
					if (form.parameterExists("administrators_group")) administrators_group = form.getParameter("administrators_group");
				}
			}
		}
		if (config.get(db, "use_contentrelations").equals("yes")) {
			if (! form.getParameter("page_up").equals("")) {
				page_up = form.getParameter("page_up");
			} else if (form.parameterExists("page_up")) {
				page_up = "0";
			}
			if (! form.getParameter("page_top").equals("")) {
				page_top = form.getParameter("page_top");
			} else if (form.parameterExists("page_top")) {
				page_top = "0";
			}
			if (! form.getParameter("page_previous").equals("")) {
				page_previous = form.getParameter("page_previous");
			} else if (form.parameterExists("page_previous")) {
				page_previous = "0";
			}
			if (! form.getParameter("page_next").equals("")) {
				page_next = form.getParameter("page_next");
			} else if (form.parameterExists("page_next")) {
				page_next = "0";
			}
			if (! form.getParameter("page_first").equals("")) {
				page_first = form.getParameter("page_first");
			} else if (form.parameterExists("page_first")) {
				page_first = "0";
			}
			if (! form.getParameter("page_last").equals("")) {
				page_last = form.getParameter("page_last");
			} else if (form.parameterExists("page_last")) {
				page_last = "0";
			}
			if (form.parameterExists("related")) {
				related = form.getParameter("related");
			}
		}
		if ((config.get(db, "use_publish").equals("manual-on")) || (config.get(db, "use_publish").equals("manual-off"))) {
			if (form.parameterExists("checkedout")) checkedout = form.getParameter("checkedout");
		}
		if (form.parameterExists("product_code")) product_code = form.getParameter("product_code");
		if (form.parameterExists("product_currency")) product_currency = form.getParameter("product_currency");
		if (form.parameterExists("product_cost")) product_cost = form.getParameter("product_cost");
		if (form.parameterExists("product_price")) product_price = form.getParameter("product_price");
		if (form.parameterExists("product_period")) product_period = form.getParameter("product_period");
		if (form.parameterExists("product_weight")) product_weight = form.getParameter("product_weight");
		if (form.parameterExists("product_volume")) product_volume = form.getParameter("product_volume");
		if (form.parameterExists("product_width")) product_width = form.getParameter("product_width");
		if (form.parameterExists("product_height")) product_height = form.getParameter("product_height");
		if (form.parameterExists("product_depth")) product_depth = form.getParameter("product_depth");
		if (form.parameterExists("product_stock")) product_stock = form.getParameter("product_stock"); // DEPRECATED
		if (form.parameterExists("product_comment")) product_comment = form.getParameter("product_comment"); // DEPRECATED
		if (form.parameterExists("product_email")) product_email = form.getParameter("product_email");
		if (form.parameterExists("product_url")) product_url = form.getParameter("product_url");
		if (form.parameterExists("product_brand")) product_brand = form.getParameter("product_brand");
		if (form.parameterExists("product_colour")) product_colour = form.getParameter("product_colour");
		if (form.parameterExists("product_size")) product_size = form.getParameter("product_size");
		if (form.parameterExists("product_info")) product_info = form.getParameter("product_info");
		if (form.parameterExists("product_options")) product_options = form.getParameter("product_options");
		if (form.parameterExists("product_delivery")) product_delivery = form.getParameter("product_delivery");
		if (form.getParameter("product_stock_amount_update").equals("set")) {
			if (form.parameterExists("product_stock_amount")) product_stock_amount = form.getParameter("product_stock_amount");
		}
		if (form.getParameter("product_restocked_amount_update").equals("set")) {
			if (form.parameterExists("product_restocked_amount")) product_restocked_amount = form.getParameter("product_restocked_amount");
		}
		if (form.parameterExists("product_stock_text")) product_stock_text = form.getParameter("product_stock_text");
		if (form.parameterExists("product_lowstock_amount")) product_lowstock_amount = form.getParameter("product_lowstock_amount");
		if (form.parameterExists("product_lowstock_text")) product_lowstock_text = form.getParameter("product_lowstock_text");
		if (form.parameterExists("product_nostock_order")) product_nostock_order = form.getParameter("product_nostock_order");
		if (form.parameterExists("product_nostock_text")) product_nostock_text = form.getParameter("product_nostock_text");
		if (form.parameterExists("product_location")) product_location = form.getParameter("product_location");
		if (form.parameterExists("product_user")) product_user = form.getParameter("product_user");
		if (form.parameterExists("product_page")) product_page = form.getParameter("product_page");
		if (form.parameterExists("product_program")) product_program = form.getParameter("product_program");
		if (form.parameterExists("product_hosting")) product_hosting = form.getParameter("product_hosting");
		if (form.parameterExists("product_content")) product_content = form.getParameter("product_content");
		if (form.parameterExists("product_contentgroup")) product_contentgroup = form.getParameter("product_contentgroup");
		if (form.parameterExists("product_contenttype")) product_contenttype = form.getParameter("product_contenttype");
		if (form.parameterExists("product_imagegroup")) product_imagegroup = form.getParameter("product_imagegroup");
		if (form.parameterExists("product_imagetype")) product_imagetype = form.getParameter("product_imagetype");
		if (form.parameterExists("product_filegroup")) product_filegroup = form.getParameter("product_filegroup");
		if (form.parameterExists("product_filetype")) product_filetype = form.getParameter("product_filetype");
		if (form.parameterExists("product_linkgroup")) product_linkgroup = form.getParameter("product_linkgroup");
		if (form.parameterExists("product_linktype")) product_linktype = form.getParameter("product_linktype");
		if (form.parameterExists("product_productgroup")) product_productgroup = form.getParameter("product_productgroup");
		if (form.parameterExists("product_producttype")) product_producttype = form.getParameter("product_producttype");
		if (form.parameterExists("product_usergroup")) product_usergroup = form.getParameter("product_usergroup");
		if (form.parameterExists("product_usertype")) product_usertype = form.getParameter("product_usertype");

		getRecordGroupsTypes(db);
	}
	public void getForm(DB db, Configuration config, Request form) {
		if (form.parameterExists("status")) status = form.getParameter("status");
		if (form.parameterExists("title")) title = form.getParameter("title");
		if (form.parameterExists("searchable")) searchable = form.getParameter("searchable");
		if (form.parameterExists("menuitem")) menuitem = form.getParameter("menuitem");
		if (form.parameterExists("contentformat")) contentformat = form.getParameter("contentformat");
		if (form.parameterExists("content")) {
			content = form.getParameter("content");
			if (! contentformat.equals("text")) {
				content = content.replaceAll("value=###([^#]+)###", "value=\"###$1###\"");
				content = content.replaceAll("value=@@@([^@]+)@@@", "value=\"@@@$1@@@\"");
				content = content.replaceAll("VALUE=###([^#]+)###", "VALUE=\"###$1###\"");
				content = content.replaceAll("VALUE=@@@([^@]+)@@@", "VALUE=\"@@@$1@@@\"");
			}
		}
		if (form.parameterExists("summary")) summary = form.getParameter("summary");
		if (form.parameterExists("image1")) image1 = form.getParameter("image1");
		if (form.parameterExists("image2")) image2 = form.getParameter("image2");
		if (form.parameterExists("image3")) image3 = form.getParameter("image3");
		if (form.parameterExists("file1")) file1 = form.getParameter("file1");
		if (form.parameterExists("file2")) file2 = form.getParameter("file2");
		if (form.parameterExists("file3")) file3 = form.getParameter("file3");
		if (form.parameterExists("link1")) link1 = form.getParameter("link1");
		if (form.parameterExists("link2")) link2 = form.getParameter("link2");
		if (form.parameterExists("link3")) link3 = form.getParameter("link3");
		if (form.parameterExists("url")) url = form.getParameter("url");
		if (! form.getParameter("file.filename").equals("")) {
			upload_filename = form.getParameter("file.upload_filename");
			server_filename = form.getParameter("file.server_filename");
		} else if (form.parameterExists("server_filename")) {
			server_filename = form.getParameter("server_filename");
		}
		if (config.get(db, "use_presentation").equals("yes")) {
			if (form.parameterExists("template")) template = form.getParameter("template");
			if (form.parameterExists("stylesheet")) stylesheet = form.getParameter("stylesheet");
		}
		if (config.get(db, "use_metainformation").equals("yes")) {
			if (form.parameterExists("author")) author = form.getParameter("author");
			if (form.parameterExists("keywords")) keywords = form.getParameter("keywords");
			if (form.parameterExists("description")) description = form.getParameter("description");
			if (form.parameterExists("metainfo")) metainfo = form.getParameter("metainfo");
			if (form.parameterExists("segmentation")) segmentation = form.getParameter("segmentation");
		}
		if (config.get(db, "use_additionalcontent").equals("yes")) {
			int elementi = 0;
			while (form.parameterExists("element" + elementi + "name")) {
				if (! form.getParameter("element" + elementi + "value").equals("")) {
					element.put(form.getParameter("element" + elementi + "name"), form.getParameter("element" + elementi + "value"));
				} else {
					element.remove(form.getParameter("element" + elementi + "name"));
				}
				elementi++;
			}
		}
		if (config.get(db, "use_revisionhistory").equals("yes")) {
			if (form.parameterExists("revision")) revision = form.getParameter("revision");
		}
		if (config.get(db, "use_versions").equals("yes")) {
			if (form.parameterExists("device")) device = form.getParameter("device");
			if (form.parameterExists("usersegment")) usersegment = form.getParameter("usersegment");
			if (form.parameterExists("usertest")) usertest = form.getParameter("usertest");
			if (form.parameterExists("version")) version = form.getParameter("version");
			if (form.parameterExists("version_master")) version_master = form.getParameter("version_master");
		}
		if (developer) {
			if (config.get(db, "use_advancedscripting").equals("yes")) {
				if (form.parameterExists("scripts")) scripts = form.getParameter("scripts");
				if (form.parameterExists("doctype")) doctype = form.getParameter("doctype");
				if (form.parameterExists("htmlattributes")) htmlattributes = form.getParameter("htmlattributes");
				if (form.parameterExists("htmlheader")) htmlheader = form.getParameter("htmlheader");
				if (form.parameterExists("htmlbodyonload")) htmlbodyonload = form.getParameter("htmlbodyonload");
			}
		}
		if (publisher) {
			if (config.get(db, "use_scheduled_publish").equals("yes")) {
				if (form.parameterExists("scheduled_publish")) scheduled_publish = form.getParameter("scheduled_publish");
			}
			if (config.get(db, "use_scheduled_unpublish").equals("yes")) {
				if (form.parameterExists("scheduled_unpublish")) scheduled_unpublish = form.getParameter("scheduled_unpublish");
			}
		}
		if (form.parameterExists("scheduled_publish")) requested_publish = form.getParameter("scheduled_publish");
		if (form.parameterExists("scheduled_unpublish")) requested_unpublish = form.getParameter("scheduled_unpublish");
		if (form.parameterExists("contentbundle")) contentbundle = form.getParameter("contentbundle");
		if (administrator) {
			if (form.parameterExists("contentpackage")) contentpackage = form.getParameter("contentpackage");
			if (form.parameterExists("contentclass")) contentclass = form.getParameter("contentclass");
			if (config.get(db, "use_contentdefinition").equals("yes")) {
				if ((config.get(db, "use_contenttypes").equals("yes") && (! contentclass.equals("product")) && (! contentclass.equals("file")) && (! contentclass.equals("image")) && (! contentclass.equals("link")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("script"))) || (config.get(db, "use_imagetypes").equals("yes") && contentclass.equals("image")) || (config.get(db, "use_filetypes").equals("yes") && contentclass.equals("file")) || (config.get(db, "use_linktypes").equals("yes") && contentclass.equals("link")) || (config.get(db, "use_producttypes").equals("yes") && contentclass.equals("product"))) {
					if (form.parameterExists("contenttype")) contenttype = form.getParameter("contenttype");
				}
				if ((config.get(db, "use_contentgroups").equals("yes") && (! contentclass.equals("product")) && (! contentclass.equals("file")) && (! contentclass.equals("image")) && (! contentclass.equals("link")) && (! contentclass.equals("stylesheet")) && (! contentclass.equals("script"))) || (config.get(db, "use_imagegroups").equals("yes") && contentclass.equals("image")) || (config.get(db, "use_filegroups").equals("yes") && contentclass.equals("file")) || (config.get(db, "use_linkgroups").equals("yes") && contentclass.equals("link")) || (config.get(db, "use_productgroups").equals("yes") && contentclass.equals("product"))) {
					if (form.parameterExists("contentgroup")) contentgroup = form.getParameter("contentgroup");
				}
			}
			if (! config.get(db, "use_accessrestrictions").equals("none")) {
				if (form.parameterExists("users_users")) users_users = form.getParameter("users_users");
				if (form.parameterExists("users_type")) users_type = form.getParameter("users_type");
				if (form.parameterExists("users_group")) users_group = form.getParameter("users_group");
				if (config.get(db, "use_accessrestrictions").equals("all")) {
					if (form.parameterExists("developers_users")) developers_users = form.getParameter("developers_users");
					if (form.parameterExists("developers_type")) developers_type = form.getParameter("developers_type");
					if (form.parameterExists("developers_group")) developers_group = form.getParameter("developers_group");
					if (form.parameterExists("creators_users")) creators_users = form.getParameter("creators_users");
					if (form.parameterExists("creators_type")) creators_type = form.getParameter("creators_type");
					if (form.parameterExists("creators_group")) creators_group = form.getParameter("creators_group");
					if (form.parameterExists("editors_users")) editors_users = form.getParameter("editors_users");
					if (form.parameterExists("editors_type")) editors_type = form.getParameter("editors_type");
					if (form.parameterExists("editors_group")) editors_group = form.getParameter("editors_group");
					if (form.parameterExists("publishers_users")) publishers_users = form.getParameter("publishers_users");
					if (form.parameterExists("publishers_type")) publishers_type = form.getParameter("publishers_type");
					if (form.parameterExists("publishers_group")) publishers_group = form.getParameter("publishers_group");
					if (form.parameterExists("administrators_users")) administrators_users = form.getParameter("administrators_users");
					if (form.parameterExists("administrators_type")) administrators_type = form.getParameter("administrators_type");
					if (form.parameterExists("administrators_group")) administrators_group = form.getParameter("administrators_group");
				}
			}
		}
		if (config.get(db, "use_contentrelations").equals("yes")) {
			if (! form.getParameter("page_up").equals("")) {
				page_up = form.getParameter("page_up");
			} else if (form.parameterExists("page_up")) {
				page_up = "0";
			}
			if (! form.getParameter("page_top").equals("")) {
				page_top = form.getParameter("page_top");
			} else if (form.parameterExists("page_top")) {
				page_top = "0";
			}
			if (! form.getParameter("page_previous").equals("")) {
				page_previous = form.getParameter("page_previous");
			} else if (form.parameterExists("page_previous")) {
				page_previous = "0";
			}
			if (! form.getParameter("page_next").equals("")) {
				page_next = form.getParameter("page_next");
			} else if (form.parameterExists("page_next")) {
				page_next = "0";
			}
			if (! form.getParameter("page_first").equals("")) {
				page_first = form.getParameter("page_first");
			} else if (form.parameterExists("page_first")) {
				page_first = "0";
			}
			if (! form.getParameter("page_last").equals("")) {
				page_last = form.getParameter("page_last");
			} else if (form.parameterExists("page_last")) {
				page_last = "0";
			}
			if (form.parameterExists("related")) {
				related = form.getParameter("related");
			}
		}
		if ((config.get(db, "use_publish").equals("manual-on")) || (config.get(db, "use_publish").equals("manual-off"))) {
			if (form.parameterExists("checkedout")) checkedout = form.getParameter("checkedout");
		}
		if (form.parameterExists("product_code")) product_code = form.getParameter("product_code");
		if (form.parameterExists("product_currency")) product_currency = form.getParameter("product_currency");
		if (form.parameterExists("product_cost")) product_cost = form.getParameter("product_cost");
		if (form.parameterExists("product_price")) product_price = form.getParameter("product_price");
		if (form.parameterExists("product_period")) product_period = form.getParameter("product_period");
		if (form.parameterExists("product_weight")) product_weight = form.getParameter("product_weight");
		if (form.parameterExists("product_volume")) product_volume = form.getParameter("product_volume");
		if (form.parameterExists("product_width")) product_width = form.getParameter("product_width");
		if (form.parameterExists("product_height")) product_height = form.getParameter("product_height");
		if (form.parameterExists("product_depth")) product_depth = form.getParameter("product_depth");
		if (form.parameterExists("product_stock")) product_stock = form.getParameter("product_stock"); // DEPRECATED
		if (form.parameterExists("product_comment")) product_comment = form.getParameter("product_comment"); // DEPRECATED
		if (form.parameterExists("product_email")) product_email = form.getParameter("product_email");
		if (form.parameterExists("product_url")) product_url = form.getParameter("product_url");
		if (form.parameterExists("product_brand")) product_brand = form.getParameter("product_brand");
		if (form.parameterExists("product_colour")) product_colour = form.getParameter("product_colour");
		if (form.parameterExists("product_size")) product_size = form.getParameter("product_size");
		if (form.parameterExists("product_info")) product_info = form.getParameter("product_info");
		if (form.parameterExists("product_options")) product_options = form.getParameter("product_options");
		if (form.parameterExists("product_delivery")) product_delivery = form.getParameter("product_delivery");
		if (form.getParameter("product_stock_amount_update").equals("set")) {
			if (form.parameterExists("product_stock_amount")) product_stock_amount = form.getParameter("product_stock_amount");
		}
		if (form.getParameter("product_restocked_amount_update").equals("set")) {
			if (form.parameterExists("product_restocked_amount")) product_restocked_amount = form.getParameter("product_restocked_amount");
		}
		if (form.parameterExists("product_stock_text")) product_stock_text = form.getParameter("product_stock_text");
		if (form.parameterExists("product_lowstock_amount")) product_lowstock_amount = form.getParameter("product_lowstock_amount");
		if (form.parameterExists("product_lowstock_text")) product_lowstock_text = form.getParameter("product_lowstock_text");
		if (form.parameterExists("product_nostock_order")) product_nostock_order = form.getParameter("product_nostock_order");
		if (form.parameterExists("product_nostock_text")) product_nostock_text = form.getParameter("product_nostock_text");
		if (form.parameterExists("product_location")) product_location = form.getParameter("product_location");
		if (form.parameterExists("product_user")) product_user = form.getParameter("product_user");
		if (form.parameterExists("product_page")) product_page = form.getParameter("product_page");
		if (form.parameterExists("product_program")) product_program = form.getParameter("product_program");
		if (form.parameterExists("product_hosting")) product_hosting = form.getParameter("product_hosting");
		if (form.parameterExists("product_content")) product_content = form.getParameter("product_content");
		if (form.parameterExists("product_contentgroup")) product_contentgroup = form.getParameter("product_contentgroup");
		if (form.parameterExists("product_contenttype")) product_contenttype = form.getParameter("product_contenttype");
		if (form.parameterExists("product_imagegroup")) product_imagegroup = form.getParameter("product_imagegroup");
		if (form.parameterExists("product_imagetype")) product_imagetype = form.getParameter("product_imagetype");
		if (form.parameterExists("product_filegroup")) product_filegroup = form.getParameter("product_filegroup");
		if (form.parameterExists("product_filetype")) product_filetype = form.getParameter("product_filetype");
		if (form.parameterExists("product_linkgroup")) product_linkgroup = form.getParameter("product_linkgroup");
		if (form.parameterExists("product_linktype")) product_linktype = form.getParameter("product_linktype");
		if (form.parameterExists("product_productgroup")) product_productgroup = form.getParameter("product_productgroup");
		if (form.parameterExists("product_producttype")) product_producttype = form.getParameter("product_producttype");
		if (form.parameterExists("product_usergroup")) product_usergroup = form.getParameter("product_usergroup");
		if (form.parameterExists("product_usertype")) product_usertype = form.getParameter("product_usertype");

		getRecordGroupsTypes(db);
	}



	public void create(DB db, Configuration config, String table, String column) {
		if (db == null) return;
		if (table.equals("")) {
			table = "content";
		}
		if (column.equals("")) {
			column = "id";
		}

		HashMap<String,String> mydata = new HashMap<String,String>();
		if (table.equals("content")) {
			mydata.put("locked_user", "" + locked_user);
			mydata.put("locked_creator", "" + locked_creator);
			mydata.put("locked_developer", "" + locked_developer);
			mydata.put("locked_editor", "" + locked_editor);
			mydata.put("locked_publisher", "" + locked_publisher);
			mydata.put("locked_administrator", "" + locked_administrator);
			mydata.put("locked_schedule", "" + locked_schedule);
			mydata.put("locked_unschedule", "" + locked_unschedule);
			mydata.put("created", created);
			mydata.put("updated", updated);
			mydata.put("published", published);
			mydata.put("unpublished", unpublished);
			mydata.put("created_by", created_by);
			mydata.put("updated_by", updated_by);
			mydata.put("published_by", published_by);
			mydata.put("unpublished_by", unpublished_by);
			mydata.put("scheduled_publish", scheduled_publish);
			mydata.put("scheduled_unpublish", scheduled_unpublish);
			mydata.put("requested_publish", requested_publish);
			mydata.put("requested_unpublish", requested_unpublish);
			mydata.put("revision", revision);
			mydata.put("device", device);
			mydata.put("usersegment", usersegment);
			mydata.put("usertest", usertest);
			mydata.put("heatmap", heatmap);
			mydata.put("heatmapalign", heatmapalign);
			mydata.put("usagelog", usagelog);
			mydata.put("version", version);
			mydata.put("version_master", version_master);
			mydata.put("status", status);
			mydata.put("status_by", status_by);
			mydata.put("title", title);
			mydata.put("searchable", searchable);
			mydata.put("menuitem", menuitem);
			mydata.put("url", url);
			mydata.put("server_filename", server_filename);
			mydata.put("upload_filename", upload_filename);
			mydata.put("author", author);
			mydata.put("keywords", keywords);
			mydata.put("description", description);
			mydata.put("summary", summary);
			mydata.put("image1", image1);
			mydata.put("image2", image2);
			mydata.put("image3", image3);
			mydata.put("file1", file1);
			mydata.put("file2", file2);
			mydata.put("file3", file3);
			mydata.put("link1", link1);
			mydata.put("link2", link2);
			mydata.put("link3", link3);
			mydata.put("metainfo", metainfo);
			mydata.put("segmentation", segmentation);
			mydata.put("htmlattributes", htmlattributes);
			mydata.put("htmlheader", htmlheader);
			mydata.put("htmlbodyonload", htmlbodyonload);
			mydata.put("contentformat", contentformat);
			mydata.put("content", content);
			mydata.put("doctype", doctype);
			mydata.put("template", template);
			mydata.put("stylesheet", stylesheet);
			mydata.put("scripts", scripts);
			mydata.put("contentpackage", contentpackage);
			mydata.put("contentbundle", contentbundle);
			mydata.put("contentclass", contentclass);
			mydata.put("contenttype", contenttype);
			mydata.put("contentgroup", contentgroup);
			mydata.put("users_users", users_users);
			mydata.put("users_type", users_type);
			mydata.put("users_group", users_group);
			mydata.put("creators_users", creators_users);
			mydata.put("creators_type", creators_type);
			mydata.put("creators_group", creators_group);
			mydata.put("developers_users", developers_users);
			mydata.put("developers_type", developers_type);
			mydata.put("developers_group", developers_group);
			mydata.put("editors_users", editors_users);
			mydata.put("editors_type", editors_type);
			mydata.put("editors_group", editors_group);
			mydata.put("publishers_users", publishers_users);
			mydata.put("publishers_type", publishers_type);
			mydata.put("publishers_group", publishers_group);
			mydata.put("administrators_users", administrators_users);
			mydata.put("administrators_type", administrators_type);
			mydata.put("administrators_group", administrators_group);
			mydata.put("checkedout", checkedout);
			mydata.put("page_up", page_up);
			mydata.put("page_top", page_top);
			mydata.put("page_next", page_next);
			mydata.put("page_previous", page_previous);
			mydata.put("page_last", page_last);
			mydata.put("page_first", page_first);
			mydata.put("related", related);
			mydata.put("product_code", product_code);
			mydata.put("product_currency", product_currency);
			mydata.put("product_cost", product_cost);
			mydata.put("product_price", product_price);
			mydata.put("product_period", product_period);
			mydata.put("product_weight", product_weight);
			mydata.put("product_volume", product_volume);
			mydata.put("product_width", product_width);
			mydata.put("product_height", product_height);
			mydata.put("product_depth", product_depth);
			mydata.put("product_stock", product_stock); // DEPRECATED
			mydata.put("product_comment", product_comment); // DEPRECATED
			mydata.put("product_email", product_email);
			mydata.put("product_url", product_url);
			mydata.put("product_brand", product_brand);
			mydata.put("product_colour", product_colour);
			mydata.put("product_size", product_size);
			mydata.put("product_info", product_info);
			mydata.put("product_options", product_options);
			mydata.put("product_delivery", product_delivery);
			mydata.put("product_stock_amount", "" + Common.integernumber(product_stock_amount));
			mydata.put("product_restocked_amount", "" + Common.integernumber(product_restocked_amount));
			mydata.put("product_stock_text", product_stock_text);
			mydata.put("product_lowstock_amount", "" + Common.integernumber(product_lowstock_amount));
			mydata.put("product_lowstock_text", product_lowstock_text);
			mydata.put("product_nostock_order", product_nostock_order);
			mydata.put("product_nostock_text", product_nostock_text);
			mydata.put("product_location", product_location);
			mydata.put("product_user", product_user);
			mydata.put("product_page", product_page);
			mydata.put("product_program", product_program);
			mydata.put("product_hosting", product_hosting);
			mydata.put("product_content", product_content);
			mydata.put("product_contentgroup", product_contentgroup);
			mydata.put("product_contenttype", product_contenttype);
			mydata.put("product_imagegroup", product_imagegroup);
			mydata.put("product_imagetype", product_imagetype);
			mydata.put("product_filegroup", product_filegroup);
			mydata.put("product_filetype", product_filetype);
			mydata.put("product_linkgroup", product_linkgroup);
			mydata.put("product_linktype", product_linktype);
			mydata.put("product_productgroup", product_productgroup);
			mydata.put("product_producttype", product_producttype);
			mydata.put("product_usergroup", product_usergroup);
			mydata.put("product_usertype", product_usertype);
			db.insert(table, mydata);
			Cache.clear(db, table);
		} else {
			mydata.put("id", "" + id);
			mydata.put("locked_user", "" + locked_user);
			mydata.put("locked_creator", "" + locked_creator);
			mydata.put("locked_developer", "" + locked_developer);
			mydata.put("locked_editor", "" + locked_editor);
			mydata.put("locked_publisher", "" + locked_publisher);
			mydata.put("locked_administrator", "" + locked_administrator);
			mydata.put("locked_schedule", "" + locked_schedule);
			mydata.put("locked_unschedule", "" + locked_unschedule);
			mydata.put("created", created);
			mydata.put("updated", updated);
			mydata.put("published", published);
			mydata.put("unpublished", unpublished);
			mydata.put("created_by", created_by);
			mydata.put("updated_by", updated_by);
			mydata.put("published_by", published_by);
			mydata.put("unpublished_by", unpublished_by);
			mydata.put("scheduled_publish", scheduled_publish);
			mydata.put("scheduled_unpublish", scheduled_unpublish);
			mydata.put("requested_publish", requested_publish);
			mydata.put("requested_unpublish", requested_unpublish);
			mydata.put("revision", revision);
			mydata.put("device", device);
			mydata.put("usersegment", usersegment);
			mydata.put("usertest", usertest);
			mydata.put("heatmap", heatmap);
			mydata.put("heatmapalign", heatmapalign);
			mydata.put("usagelog", usagelog);
			mydata.put("version", version);
			mydata.put("version_master", version_master);
			mydata.put("status", status);
			mydata.put("status_by", status_by);
			mydata.put("title", title);
			mydata.put("searchable", searchable);
			mydata.put("menuitem", menuitem);
			mydata.put("url", url);
			mydata.put("server_filename", server_filename);
			mydata.put("upload_filename", upload_filename);
			mydata.put("author", author);
			mydata.put("keywords", keywords);
			mydata.put("description", description);
			mydata.put("summary", summary);
			mydata.put("image1", image1);
			mydata.put("image2", image2);
			mydata.put("image3", image3);
			mydata.put("file1", file1);
			mydata.put("file2", file2);
			mydata.put("file3", file3);
			mydata.put("link1", link1);
			mydata.put("link2", link2);
			mydata.put("link3", link3);
			mydata.put("metainfo", metainfo);
			mydata.put("segmentation", segmentation);
			mydata.put("htmlattributes", htmlattributes);
			mydata.put("htmlheader", htmlheader);
			mydata.put("htmlbodyonload", htmlbodyonload);
			mydata.put("contentformat", contentformat);
			mydata.put("content", content);
			mydata.put("doctype", doctype);
			mydata.put("template", template);
			mydata.put("stylesheet", stylesheet);
			mydata.put("scripts", scripts);
			mydata.put("contentpackage", contentpackage);
			mydata.put("contentbundle", contentbundle);
			mydata.put("contentclass", contentclass);
			mydata.put("contenttype", contenttype);
			mydata.put("contentgroup", contentgroup);
			mydata.put("users_users", users_users);
			mydata.put("users_type", users_type);
			mydata.put("users_group", users_group);
			mydata.put("creators_users", creators_users);
			mydata.put("creators_type", creators_type);
			mydata.put("creators_group", creators_group);
			mydata.put("developers_users", developers_users);
			mydata.put("developers_type", developers_type);
			mydata.put("developers_group", developers_group);
			mydata.put("editors_users", editors_users);
			mydata.put("editors_type", editors_type);
			mydata.put("editors_group", editors_group);
			mydata.put("publishers_users", publishers_users);
			mydata.put("publishers_type", publishers_type);
			mydata.put("publishers_group", publishers_group);
			mydata.put("administrators_users", administrators_users);
			mydata.put("administrators_type", administrators_type);
			mydata.put("administrators_group", administrators_group);
			mydata.put("checkedout", checkedout);
			mydata.put("page_up", page_up);
			mydata.put("page_top", page_top);
			mydata.put("page_next", page_next);
			mydata.put("page_previous", page_previous);
			mydata.put("page_last", page_last);
			mydata.put("page_first", page_first);
			mydata.put("related", related);
			mydata.put("product_code", product_code);
			mydata.put("product_currency", product_currency);
			mydata.put("product_cost", product_cost);
			mydata.put("product_price", product_price);
			mydata.put("product_period", product_period);
			mydata.put("product_weight", product_weight);
			mydata.put("product_volume", product_volume);
			mydata.put("product_width", product_width);
			mydata.put("product_height", product_height);
			mydata.put("product_depth", product_depth);
			mydata.put("product_stock", product_stock); // DEPRECATED
			mydata.put("product_comment", product_comment); // DEPRECATED
			mydata.put("product_email", product_email);
			mydata.put("product_url", product_url);
			mydata.put("product_brand", product_brand);
			mydata.put("product_colour", product_colour);
			mydata.put("product_size", product_size);
			mydata.put("product_info", product_info);
			mydata.put("product_options", product_options);
			mydata.put("product_delivery", product_delivery);
			mydata.put("product_stock_amount", "" + Common.integernumber(product_stock_amount));
			mydata.put("product_restocked_amount", "" + Common.integernumber(product_restocked_amount));
			mydata.put("product_stock_text", product_stock_text);
			mydata.put("product_lowstock_amount", "" + Common.integernumber(product_lowstock_amount));
			mydata.put("product_lowstock_text", product_lowstock_text);
			mydata.put("product_nostock_order", product_nostock_order);
			mydata.put("product_nostock_text", product_nostock_text);
			mydata.put("product_location", product_location);
			mydata.put("product_user", product_user);
			mydata.put("product_page", product_page);
			mydata.put("product_program", product_program);
			mydata.put("product_hosting", product_hosting);
			mydata.put("product_content", product_content);
			mydata.put("product_contentgroup", product_contentgroup);
			mydata.put("product_contenttype", product_contenttype);
			mydata.put("product_imagegroup", product_imagegroup);
			mydata.put("product_imagetype", product_imagetype);
			mydata.put("product_filegroup", product_filegroup);
			mydata.put("product_filetype", product_filetype);
			mydata.put("product_linkgroup", product_linkgroup);
			mydata.put("product_linktype", product_linktype);
			mydata.put("product_productgroup", product_productgroup);
			mydata.put("product_producttype", product_producttype);
			mydata.put("product_usergroup", product_usergroup);
			mydata.put("product_usertype", product_usertype);
			db.insert(table, mydata);
			Cache.clear(db, table);
		}
		if (column.equals("archiveid")) {
			archiveid = created_id(db, table, column, mydata);
		} else {
			id = created_id(db, table, column, mydata);
		}
		this.create_elements(db, table, column);
		this.create_metainfo(db, this.id, table, column);
		dependencies(db, config, id, table, true);
	}



	public void create_elements(DB db, String table, String column) {
		Iterator elementitems = element.keySet().iterator();
		while (elementitems.hasNext()) {
			String elementitem = "" + elementitems.next();
			String[] elementvalues = ("" + element.get(elementitem)).split(",");
			for (int i=0; i<elementvalues.length; i++) {
				String elementvalue = "" + elementvalues[i];
				if (! elementvalue.equals("")) {
					HashMap<String,String> mydata = new HashMap<String,String>();
					if ((table.equals("content_archive")) && (column.equals("archiveid"))) {
						mydata.put("content_archiveid", "" + Common.integer(archiveid));
						mydata.put("content_id", "" + Common.integer(id));
						mydata.put("element", "" + elementitem);
						mydata.put("element_id", "" + Common.integer(elementvalue));
						mydata.put("element_order", "" + i);
						mydata.put("element_params", "");
					} else {
						mydata.put("content_id", "" + Common.integer(id));
						mydata.put("element", "" + elementitem);
						mydata.put("element_id", "" + Common.integer(elementvalue));
						mydata.put("element_order", "" + i);
						mydata.put("element_params", "");
					}
					db.insert(table + "_elements", mydata);
				}
			}
		}
		Cache.clear(db, table + "_elements");
	}



	public String created_id(DB db, String table, String column) {
		// DEPRECATED
		return created_id(db, table, column, null);
	}
	public String created_id(DB db, String table, String column, HashMap<String,String> data) {
		if (db == null) return "";
		if (table.equals("")) {
			table = "content";
		}
		if (column.equals("")) {
			column = "id";
		}

		String SQL = "";
		LinkedHashMap<String,HashMap<String,String>> records = new LinkedHashMap<String,HashMap<String,String>>();
		if (data != null) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("created", data.get("created"));
			mydata.put("updated", data.get("updated"));
			mydata.put("published", data.get("published"));
			mydata.put("unpublished", data.get("unpublished"));
			mydata.put("scheduled_publish", data.get("scheduled_publish"));
			mydata.put("scheduled_unpublish", data.get("scheduled_unpublish"));
			mydata.put("requested_publish", data.get("requested_publish"));
			mydata.put("requested_unpublish", data.get("requested_unpublish"));
			mydata.put("status", data.get("status"));
			mydata.put("status_by", data.get("status_by"));
			mydata.put("created_by", data.get("created_by"));
			mydata.put("updated_by", data.get("updated_by"));
			mydata.put("published_by", data.get("published_by"));
			mydata.put("unpublished_by", data.get("unpublished_by"));
// blob			mydata.put("revision", data.get("revision"));
// blob			mydata.put("history", data.get("history"));
			mydata.put("device", data.get("device"));
			mydata.put("usersegment", data.get("usersegment"));
			mydata.put("usertest", data.get("usertest"));
			mydata.put("heatmap", data.get("heatmap"));
			mydata.put("heatmapalign", data.get("heatmapalign"));
			mydata.put("usagelog", data.get("usagelog"));
			mydata.put("version", data.get("version"));
			mydata.put("version_master", data.get("version_master"));
			mydata.put("title", data.get("title"));
			mydata.put("searchable", data.get("searchable"));
			mydata.put("menuitem", data.get("menuitem"));
// blob			mydata.put("author", data.get("author"));
// blob			mydata.put("keywords", data.get("keywords"));
// blob			mydata.put("description", data.get("description"));
// blob			mydata.put("metainfo", data.get("metainfo"));
// blob			mydata.put("segmentation", data.get("segmentation"));
// blob			mydata.put("doctype", data.get("doctype"));
// blob			mydata.put("htmlattributes", data.get("htmlattributes"));
// blob			mydata.put("htmlheader", data.get("htmlheader"));
// blob			mydata.put("htmlbodyonload", data.get("htmlbodyonload"));
// blob			mydata.put("url", data.get("url"));
// blob			mydata.put("content", data.get("content"));
// blob			mydata.put("summary", data.get("summary"));
			mydata.put("template", data.get("template"));
			mydata.put("stylesheet", data.get("stylesheet"));
			mydata.put("scripts", data.get("scripts"));
			mydata.put("image1", data.get("image1"));
			mydata.put("image2", data.get("image2"));
			mydata.put("image3", data.get("image3"));
			mydata.put("file1", data.get("file1"));
			mydata.put("file2", data.get("file2"));
			mydata.put("file3", data.get("file3"));
			mydata.put("link1", data.get("link1"));
			mydata.put("link2", data.get("link2"));
			mydata.put("link3", data.get("link3"));
			mydata.put("contentformat", data.get("contentformat"));
			mydata.put("contentclass", data.get("contentclass"));
			mydata.put("contenttype", data.get("contenttype"));
			mydata.put("contentgroup", data.get("contentgroup"));
			mydata.put("contentbundle", data.get("contentbundle"));
			mydata.put("contentpackage", data.get("contentpackage"));
			mydata.put("users_type", data.get("users_type"));
			mydata.put("users_group", data.get("users_group"));
			mydata.put("developers_type", data.get("developers_type"));
			mydata.put("developers_group", data.get("developers_group"));
			mydata.put("creators_type", data.get("creators_type"));
			mydata.put("creators_group", data.get("creators_group"));
			mydata.put("editors_type", data.get("editors_type"));
			mydata.put("editors_group", data.get("editors_group"));
			mydata.put("publishers_type", data.get("publishers_type"));
			mydata.put("publishers_group", data.get("publishers_group"));
			mydata.put("administrators_type", data.get("administrators_type"));
			mydata.put("administrators_group", data.get("administrators_group"));
// blob			mydata.put("users_users", data.get("users_users"));
// blob			mydata.put("developers_users", data.get("developers_users"));
// blob			mydata.put("creators_users", data.get("creators_users"));
// blob			mydata.put("editors_users", data.get("editors_users"));
// blob			mydata.put("publishers_users", data.get("publishers_users"));
// blob			mydata.put("administrators_users", data.get("administrators_users"));
			mydata.put("checkedout", data.get("checkedout"));
			mydata.put("page_top", data.get("page_top"));
			mydata.put("page_up", data.get("page_up"));
			mydata.put("page_next", data.get("page_next"));
			mydata.put("page_previous", data.get("page_previous"));
			mydata.put("page_first", data.get("page_first"));
			mydata.put("page_last", data.get("page_last"));
// blob			mydata.put("related", data.get("related"));
// blob			mydata.put("upload_filename", data.get("upload_filename"));
// blob			mydata.put("server_filename", data.get("server_filename"));
// blob old		mydata.put("product_code", data.get("product_code"));
// blob old		mydata.put("product_sku", data.get("product_sku"));
// blob old		mydata.put("product_currency", data.get("product_currency"));
// blob old		mydata.put("product_price", data.get("product_price"));
// blob old		mydata.put("product_period", data.get("product_period"));
// blob old		mydata.put("product_weight", data.get("product_weight"));
// blob old		mydata.put("product_volume", data.get("product_volume"));
// blob old		mydata.put("product_width", data.get("product_width"));
// blob old		mydata.put("product_height", data.get("product_height"));
// blob old		mydata.put("product_depth", data.get("product_depth"));
// blob old		mydata.put("product_brand", data.get("product_brand"));
// blob old		mydata.put("product_colour", data.get("product_colour"));
// blob old		mydata.put("product_size", data.get("product_size"));
//			mydata.put("product_count", data.get("product_count"));
//			mydata.put("product_tax", data.get("product_tax"));
//			mydata.put("product_points_cost", data.get("product_points_cost"));
//			mydata.put("product_points_reward", data.get("product_points_reward"));
// blob old		mydata.put("product_stock", data.get("product_stock"));
// blob old		mydata.put("product_cost", data.get("product_cost"));
// blob old		mydata.put("product_location", data.get("product_location"));
// ignore		mydata.put("product_stock_amount", data.get("product_stock_amount"));
// blob old		mydata.put("product_stock_text", data.get("product_stock_text"));
// ignore		mydata.put("product_restocked_amount", data.get("product_restocked_amount"));
// ignore		mydata.put("product_lowstock_amount", data.get("product_lowstock_amount"));
// blob old		mydata.put("product_lowstock_text", data.get("product_lowstock_text"));
// blob old		mydata.put("product_nostock_order", data.get("product_nostock_order"));
// blob old		mydata.put("product_nostock_text", data.get("product_nostock_text"));
// blob			mydata.put("product_comment", data.get("product_comment"));
// blob old		mydata.put("product_email", data.get("product_email"));
// blob			mydata.put("product_url", data.get("product_url"));
// blob			mydata.put("product_info", data.get("product_info"));
// blob			mydata.put("product_delivery", data.get("product_delivery"));
// blob			mydata.put("product_user", data.get("product_user"));
// blob old		mydata.put("product_page", data.get("product_page"));
// blob			mydata.put("product_program", data.get("product_program"));
// blob			mydata.put("product_hosting", data.get("product_hosting"));
// blob			mydata.put("product_options", data.get("product_options"));
// blob			mydata.put("product_content", data.get("product_content"));
// blob			mydata.put("product_contentgroup", data.get("product_contentgroup"));
// blob			mydata.put("product_contenttype", data.get("product_contenttype"));
// blob			mydata.put("product_imagegroup", data.get("product_imagegroup"));
// blob			mydata.put("product_imagetype", data.get("product_imagetype"));
// blob			mydata.put("product_filegroup", data.get("product_filegroup"));
// blob			mydata.put("product_filetype", data.get("product_filetype"));
// blob			mydata.put("product_linkgroup", data.get("product_linkgroup"));
// blob			mydata.put("product_linktype", data.get("product_linktype"));
// blob			mydata.put("product_productgroup", data.get("product_productgroup"));
// blob			mydata.put("product_producttype", data.get("product_producttype"));
// blob			mydata.put("product_usergroup", data.get("product_usergroup"));
// blob			mydata.put("product_usertype", data.get("product_usertype"));
			records = db.selectAll(table, mydata, " order by " + Common.SQL_clean(column) + " desc");
		} else {
			// DEPRECATED
			String SQLwhere = "";
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "created", created);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "updated", updated);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "published", published);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "unpublished", unpublished);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "created_by", created_by);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "updated_by", updated_by);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "published_by", published_by);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "unpublished_by", unpublished_by);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "scheduled_publish", scheduled_publish);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "scheduled_unpublish", scheduled_unpublish);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "requested_publish", requested_publish);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "requested_unpublish", requested_unpublish);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "device", device);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "usersegment", usersegment);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "usertest", usertest);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "heatmap", heatmap);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "heatmapalign", heatmapalign);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "usagelog", usagelog);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "version", version);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "version_master", version_master);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "status", status);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "status_by", status_by);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "title", title);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "searchable", searchable);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "menuitem", menuitem);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "url", url);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "image1", image1);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "image2", image2);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "image3", image3);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "file1", file1);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "file2", file2);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "file3", file3);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "link1", link1);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "link2", link2);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "link3", link3);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "htmlbodyonload", htmlbodyonload);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "contentformat", contentformat);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "template", template);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "stylesheet", stylesheet);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "scripts", scripts);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "contentpackage", contentpackage);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "contentbundle", contentbundle);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "contentclass", contentclass);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "contenttype", contenttype);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "contentgroup", contentgroup);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "users_type", users_type);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "users_group", users_group);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "creators_type", creators_type);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "creators_group", creators_group);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "developers_type", developers_type);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "developers_group", developers_group);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "editors_type", editors_type);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "editors_group", editors_group);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "publishers_type", publishers_type);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "publishers_group", publishers_group);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "administrators_type", administrators_type);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "administrators_group", administrators_group);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "checkedout", checkedout);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "page_up", page_up);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "page_top", page_top);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "page_next", page_next);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "page_previous", page_previous);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "page_last", page_last);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "page_first", page_first);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "related", related);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_code", product_code);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_currency", product_currency);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_cost", product_cost);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_price", product_price);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_period", product_period);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_weight", product_weight);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_volume", product_volume);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_width", product_width);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_height", product_height);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_depth", product_depth);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_stock", product_stock); // DEPRECATED
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_comment", product_comment); // DEPRECATED
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_email", product_email);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_url", product_url);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_brand", product_brand);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_colour", product_colour);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_size", product_size);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_info", product_info);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_options", product_options);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_delivery", product_delivery);
// ignore		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_stock_amount", "" + Common.integernumber(product_stock_amount));
// ignore		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_restocked_amount", "" + Common.integernumber(product_restocked_amount));
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_stock_text", product_stock_text);
// ignore		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_lowstock_amount", "" + Common.integernumber(product_lowstock_amount));
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_lowstock_text", product_lowstock_text);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_nostock_order", product_nostock_order);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_nostock_text", product_nostock_text);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_location", product_location);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_user", product_user);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_page", product_page);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_program", product_program);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_hosting", product_hosting);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_content", product_content);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_contentgroup", product_contentgroup);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_contenttype", product_contenttype);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_imagegroup", product_imagegroup);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_imagetype", product_imagetype);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_filegroup", product_filegroup);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_filetype", product_filetype);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_linkgroup", product_linkgroup);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_linktype", product_linktype);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_productgroup", product_productgroup);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_producttype", product_producttype);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_usergroup", product_usergroup);
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "product_usertype", product_usertype);
			SQL = "select * from " + Common.SQL_clean(table) + SQLwhere + " order by " + Common.SQL_clean(column) + " desc";
			records = db.query_records(SQL);
		}
		String errors = "";
		String created_id = "0";
		boolean inserted_row = false;
		for (int i=0; i<records.size() && (! inserted_row); i++) {
			HashMap<String,String> row = (HashMap<String,String>)records.get("" + i);
			inserted_row = true;
			if (row.get("upload_filename") != null) {
				if (! upload_filename.trim().equals((""+row.get("upload_filename")).trim())) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:upload_filename:" + id + " - " + title + " - " + upload_filename + "<>" + row.get("upload_filename") + " - " + row.get(column);
				}
			} else {
				if (! upload_filename.equals("")) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:upload_filename:" + id + " - " + title + " - " + upload_filename + "<>" + row.get("upload_filename") + " - " + row.get(column);
				}
			}
			if (row.get("server_filename") != null) {
				if (! server_filename.trim().equals((""+row.get("server_filename")).trim())) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:server_filename:" + id + " - " + title + " - " + server_filename + "<>" + row.get("server_filename") + " - " + row.get(column);
				}
			} else {
				if (! server_filename.equals("")) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:server_filename:" + id + " - " + title + " - " + server_filename + "<>" + row.get("server_filename") + " - " + row.get(column);
				}
			}
			if (row.get("revision") != null) {
				if (! revision.trim().equals((""+row.get("revision")).trim())) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:revision:" + id + " - " + title + " - " + revision + "<>" + row.get("revision") + " - " + row.get(column);
				}
			} else {
				if (! revision.equals("")) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:revision:" + id + " - " + title + " - " + revision + "<>" + row.get("revision") + " - " + row.get(column);
				}
			}
			if (row.get("author") != null) {
				if (! author.trim().equals((""+row.get("author")).trim())) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:author:" + id + " - " + title + " - " + author + "<>" + row.get("author") + " - " + row.get(column);
				}
			} else {
				if (! author.equals("")) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:author:" + id + " - " + title + " - " + author + "<>" + row.get("author") + " - " + row.get(column);
				}
			}
			if (row.get("keywords") != null) {
				if (! keywords.trim().equals((""+row.get("keywords")).trim())) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:keywords:" + id + " - " + title + " - " + keywords + "<>" + row.get("keywords") + " - " + row.get(column);
				}
			} else {
				if (! keywords.equals("")) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:keywords:" + id + " - " + title + " - " + keywords + "<>" + row.get("keywords") + " - " + row.get(column);
				}
			}
			if (row.get("description") != null) {
				if (! description.trim().equals((""+row.get("description")).trim())) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:description:" + id + " - " + title + " - " + description + "<>" + row.get("description") + " - " + row.get(column);
				}
			} else {
				if (! description.equals("")) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:description:" + id + " - " + title + " - " + description + "<>" + row.get("description") + " - " + row.get(column);
				}
			}
			if (row.get("summary") != null) {
				if (! summary.trim().equals((""+row.get("summary")).trim())) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:summary:" + id + " - " + title + " - " + summary + "<>" + row.get("summary") + " - " + row.get(column);
				}
			} else {
				if (! summary.equals("")) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:summary:" + id + " - " + title + " - " + summary + "<>" + row.get("summary") + " - " + row.get(column);
				}
			}
			if (row.get("metainfo") != null) {
				if (! metainfo.trim().equals((""+row.get("metainfo")).trim())) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:metainfo:" + id + " - " + title + " - " + metainfo + "<>" + row.get("metainfo") + " - " + row.get(column);
				}
			} else {
				if (! metainfo.equals("")) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:metainfo:" + id + " - " + title + " - " + metainfo + "<>" + row.get("metainfo") + " - " + row.get(column);
				}
			}
			if (row.get("segmentation") != null) {
				if (! segmentation.trim().equals((""+row.get("segmentation")).trim())) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:segmentation:" + id + " - " + title + " - " + segmentation + "<>" + row.get("segmentation") + " - " + row.get(column);
				}
			} else {
				if (! segmentation.equals("")) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:segmentation:" + id + " - " + title + " - " + segmentation + "<>" + row.get("segmentation") + " - " + row.get(column);
				}
			}
			if (row.get("htmlattributes") != null) {
				if (! htmlattributes.trim().equals((""+row.get("htmlattributes")).trim())) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:htmlattributes:" + id + " - " + title + " - " + htmlattributes + "<>" + row.get("htmlattributes") + " - " + row.get(column);
				}
			} else {
				if (! htmlattributes.equals("")) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:htmlattributes:" + id + " - " + title + " - " + htmlattributes + "<>" + row.get("htmlattributes") + " - " + row.get(column);
				}
			}
			if (row.get("htmlheader") != null) {
				if (! htmlheader.trim().equals((""+row.get("htmlheader")).trim())) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:htmlheader:" + id + " - " + title + " - " + htmlheader + "<>" + row.get("htmlheader") + " - " + row.get(column);
				}
			} else {
				if (! htmlheader.equals("")) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:htmlheader:" + id + " - " + title + " - " + htmlheader + "<>" + row.get("htmlheader") + " - " + row.get(column);
				}
			}
			if (row.get("content") != null) {
				if (! content.trim().equals((""+row.get("content")).trim())) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:content:" + id + " - " + title + " - " + content + "<>" + row.get("content") + " - " + row.get(column);
				}
			} else {
				if (! content.equals("")) {
					inserted_row = false;
errors += "\r\nERROR:HardCore.Content.create:failed:content:" + id + " - " + title + " - " + content + "<>" + row.get("content") + " - " + row.get(column);
				}
			}
			if (inserted_row) {
				created_id = "" + row.get(column);
			}
		}
if (created_id.equals("0")) {
	System.out.println("ERROR:HardCore.Content.create:failed:" + id + " - " + title + " - " + SQL + " - " + data + errors);
}
		return "" + created_id;
	}



	public void update(DB db, Configuration config) {
		update(db, config, "", "", "");
	}
	public void update(DB db, Configuration config, String myid, String table, String column) {
		if (db == null) return;
		if (myid.equals("")) {
			myid = getId();
		}
		if (table.equals("")) {
			table = "content";
		}
		if (column.equals("")) {
			column = "id";
		}

		if (! myid.equals("")) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("locked_user", "" + locked_user);
			mydata.put("locked_creator", "" + locked_creator);
			mydata.put("locked_developer", "" + locked_developer);
			mydata.put("locked_editor", "" + locked_editor);
			mydata.put("locked_publisher", "" + locked_publisher);
			mydata.put("locked_administrator", "" + locked_administrator);
			mydata.put("locked_schedule", "" + locked_schedule);
			mydata.put("locked_unschedule", "" + locked_unschedule);
			mydata.put("created", created);
			mydata.put("updated", updated);
			mydata.put("published", published);
			mydata.put("unpublished", unpublished);
			mydata.put("created_by", created_by);
			mydata.put("updated_by", updated_by);
			mydata.put("published_by", published_by);
			mydata.put("unpublished_by", unpublished_by);
			mydata.put("scheduled_publish", scheduled_publish);
			mydata.put("scheduled_unpublish", scheduled_unpublish);
			mydata.put("requested_publish", requested_publish);
			mydata.put("requested_unpublish", requested_unpublish);
			mydata.put("revision", revision);
			mydata.put("device", device);
			mydata.put("usersegment", usersegment);
			mydata.put("usertest", usertest);
			mydata.put("heatmap", heatmap);
			mydata.put("heatmapalign", heatmapalign);
			mydata.put("usagelog", usagelog);
			mydata.put("version", version);
			mydata.put("version_master", version_master);
			mydata.put("status", status);
			mydata.put("status_by", status_by);
			mydata.put("title", title);
			mydata.put("searchable", searchable);
			mydata.put("menuitem", menuitem);
			mydata.put("url", url);
			mydata.put("server_filename", server_filename);
			mydata.put("upload_filename", upload_filename);
			mydata.put("author", author);
			mydata.put("keywords", keywords);
			mydata.put("description", description);
			mydata.put("summary", summary);
			mydata.put("image1", image1);
			mydata.put("image2", image2);
			mydata.put("image3", image3);
			mydata.put("file1", file1);
			mydata.put("file2", file2);
			mydata.put("file3", file3);
			mydata.put("link1", link1);
			mydata.put("link2", link2);
			mydata.put("link3", link3);
			mydata.put("metainfo", metainfo);
			mydata.put("segmentation", segmentation);
			mydata.put("htmlattributes", htmlattributes);
			mydata.put("htmlheader", htmlheader);
			mydata.put("htmlbodyonload", htmlbodyonload);
			mydata.put("contentformat", contentformat);
			mydata.put("content", content);
			mydata.put("doctype", doctype);
			mydata.put("template", template);
			mydata.put("stylesheet", stylesheet);
			mydata.put("scripts", scripts);
			mydata.put("contentpackage", contentpackage);
			mydata.put("contentbundle", contentbundle);
			mydata.put("contentclass", contentclass);
			mydata.put("contenttype", contenttype);
			mydata.put("contentgroup", contentgroup);
			mydata.put("users_users", users_users);
			mydata.put("users_type", users_type);
			mydata.put("users_group", users_group);
			mydata.put("creators_users", creators_users);
			mydata.put("creators_type", creators_type);
			mydata.put("creators_group", creators_group);
			mydata.put("developers_users", developers_users);
			mydata.put("developers_type", developers_type);
			mydata.put("developers_group", developers_group);
			mydata.put("editors_users", editors_users);
			mydata.put("editors_type", editors_type);
			mydata.put("editors_group", editors_group);
			mydata.put("publishers_users", publishers_users);
			mydata.put("publishers_type", publishers_type);
			mydata.put("publishers_group", publishers_group);
			mydata.put("administrators_users", administrators_users);
			mydata.put("administrators_type", administrators_type);
			mydata.put("administrators_group", administrators_group);
			mydata.put("checkedout", checkedout);
			mydata.put("page_up", page_up);
			mydata.put("page_top", page_top);
			mydata.put("page_next", page_next);
			mydata.put("page_previous", page_previous);
			mydata.put("page_last", page_last);
			mydata.put("page_first", page_first);
			mydata.put("related", related);
			mydata.put("product_code", product_code);
			mydata.put("product_currency", product_currency);
			mydata.put("product_cost", product_cost);
			mydata.put("product_price", product_price);
			mydata.put("product_period", product_period);
			mydata.put("product_weight", product_weight);
			mydata.put("product_volume", product_volume);
			mydata.put("product_width", product_width);
			mydata.put("product_height", product_height);
			mydata.put("product_depth", product_depth);
			mydata.put("product_stock", product_stock); // DEPRECATED
			mydata.put("product_comment", product_comment); // DEPRECATED
			mydata.put("product_email", product_email);
			mydata.put("product_url", product_url);
			mydata.put("product_brand", product_brand);
			mydata.put("product_colour", product_colour);
			mydata.put("product_size", product_size);
			mydata.put("product_info", product_info);
			mydata.put("product_options", product_options);
			mydata.put("product_delivery", product_delivery);
// ignore		mydata.put("product_stock_amount", "" + Common.integernumber(product_stock_amount));
// ignore		mydata.put("product_restocked_amount", "" + Common.integernumber(product_restocked_amount));
			mydata.put("product_stock_text", product_stock_text);
			mydata.put("product_lowstock_amount", "" + Common.integernumber(product_lowstock_amount));
			mydata.put("product_lowstock_text", product_lowstock_text);
			mydata.put("product_nostock_order", product_nostock_order);
			mydata.put("product_nostock_text", product_nostock_text);
			mydata.put("product_location", product_location);
			mydata.put("product_user", product_user);
			mydata.put("product_page", product_page);
			mydata.put("product_program", product_program);
			mydata.put("product_hosting", product_hosting);
			mydata.put("product_content", product_content);
			mydata.put("product_contentgroup", product_contentgroup);
			mydata.put("product_contenttype", product_contenttype);
			mydata.put("product_imagegroup", product_imagegroup);
			mydata.put("product_imagetype", product_imagetype);
			mydata.put("product_filegroup", product_filegroup);
			mydata.put("product_filetype", product_filetype);
			mydata.put("product_linkgroup", product_linkgroup);
			mydata.put("product_linktype", product_linktype);
			mydata.put("product_productgroup", product_productgroup);
			mydata.put("product_producttype", product_producttype);
			mydata.put("product_usergroup", product_usergroup);
			mydata.put("product_usertype", product_usertype);
			db.update(table, column, myid, mydata);
			Cache.clear(db, table);
		}

		db.delete(table + "_elements", "content_" + column, myid);
		this.create_elements(db, table, column);
		this.update_metainfo(db, myid, table, column);
		dependencies(db, config, myid, table, true);
	}



	public void delete(DB db, Configuration config, String myid, String table, String column) {
		if (db == null) return;
		if (table.equals("")) {
			table = "content";
		}
		if (column.equals("")) {
			column = "id";
		}

		if (! myid.equals("")) {
			db.deleteWhere(table, "(locked=0 or locked is null) and " + Common.SQL_clean(column) + "=" + Common.integer(myid));
			db.delete(table + "_elements", "content_" + column, myid);
			if ((table.equals("content")) && (column.equals("id"))) {
				db.updateSet("content", "page_top", myid, "page_top=" + db.quote("0"));
				db.updateSet("content", "page_up", myid, "page_up=" + db.quote("0"));
				db.updateSet("content", "page_first", myid, "page_first=" + db.quote("0"));
				db.updateSet("content", "page_previous", myid, "page_previous=" + db.quote("0"));
				db.updateSet("content", "page_next", myid, "page_next=" + db.quote("0"));
				db.updateSet("content", "page_last", myid, "page_last=" + db.quote("0"));
				db.updateSet("content_public", "page_top", myid, "page_top=" + db.quote("0"));
				db.updateSet("content_public", "page_up", myid, "page_up=" + db.quote("0"));
				db.updateSet("content_public", "page_first", myid, "page_first=" + db.quote("0"));
				db.updateSet("content_public", "page_previous", myid, "page_previous=" + db.quote("0"));
				db.updateSet("content_public", "page_next", myid, "page_next=" + db.quote("0"));
				db.updateSet("content_public", "page_last", myid, "page_last=" + db.quote("0"));
			}
			Cache.clear(db, table);
			Cache.clear(db, table + "_elements");
			this.delete_metainfo(db, myid, table, column);
			dependencies(db, config, myid, table, false);
		}
	}




	public void create_metainfo(DB db, String myid, String table, String column) {
		if ((! myid.equals("")) && ((table.equals("content")) || (table.equals("content_public")))) {
			Pattern ptn = Pattern.compile("<([^<>]+)>([^<>]*)</\\1>", Pattern.MULTILINE);
			Matcher matches = ptn.matcher(metainfo);
			while (matches.find()) {
				HashMap<String,String> mydata = new HashMap<String,String>();
				mydata.put("content_" + column, "" + Common.integer(myid));
				mydata.put("meta_name", "" + matches.group(1));
				mydata.put("meta_content", "" + matches.group(2));
				db.insert(table + "_meta", mydata);
			}
		}
	}



	public void update_metainfo_all(DB db, Configuration config) {
		String SQL = "select * from content where " + db.is_not_blank("metainfo");
		records("", "", "", "", "", "", "", db, config, SQL);
		while (records("", "", "", "", "", "", "", db, config, "")) {
			update_metainfo(db, getId(), "content", "id");
		}
		SQL = "select * from content_public where " + db.is_not_blank("metainfo");
		records("", "", "", "", "", "", "", db, config, SQL);
		while (records("", "", "", "", "", "", "", db, config, "")) {
			update_metainfo(db, getId(), "content_public", "id");
		}
	}



	public void update_metainfo(DB db, String myid, String table, String column) {
		this.delete_metainfo(db, myid, table, column);
		this.create_metainfo(db, myid, table, column);
	}



	public void delete_metainfo(DB db, String myid, String table, String column) {
		if ((! myid.equals("")) && ((table.equals("content")) || (table.equals("content_public")))) {
			db.delete(table + "_meta", "content_" + column, myid);
		}
	}



	public void renameContentpackage(DB db, String old_contentpackage, String new_contentpackage) {
		db.rename("content", "contentpackage", old_contentpackage, new_contentpackage);
		db.rename("content_archive", "contentpackage", old_contentpackage, new_contentpackage);
		db.rename("content_public", "contentpackage", old_contentpackage, new_contentpackage);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameContentbundle(DB db, String old_contentbundle, String new_contentbundle) {
		db.rename("content", "contentbundle", old_contentbundle, new_contentbundle);
		db.rename("content_archive", "contentbundle", old_contentbundle, new_contentbundle);
		db.rename("content_public", "contentbundle", old_contentbundle, new_contentbundle);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameContentclass(DB db, String old_contentclass, String new_contentclass) {
		db.rename("content", "contentclass", old_contentclass, new_contentclass);
		db.rename("content_archive", "contentclass", old_contentclass, new_contentclass);
		db.rename("content_public", "contentclass", old_contentclass, new_contentclass);
		db.rename("content_elements", "element", old_contentclass, new_contentclass);
		db.rename("content_archive_elements", "element", old_contentclass, new_contentclass);
		db.rename("content_public_elements", "element", old_contentclass, new_contentclass);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
		Cache.clear(db, "content_elements");
		Cache.clear(db, "content_archive_elements");
		Cache.clear(db, "content_public_elements");
	}



	public void renameContentgroup(DB db, String old_contentgroup, String new_contentgroup) {
		db.renameWhere("content", "contentgroup", "contentclass<>" + db.quote("file") + " and contentclass<>" + db.quote("image") + " and contentclass<>" + db.quote("link") + " and contentclass<>" + db.quote("product"), old_contentgroup, new_contentgroup);
		db.renameWhere("content_archive", "contentgroup", "contentclass<>" + db.quote("file") + " and contentclass<>" + db.quote("image") + " and contentclass<>" + db.quote("link") + " and contentclass<>" + db.quote("product"), old_contentgroup, new_contentgroup);
		db.renameWhere("content_public", "contentgroup", "contentclass<>" + db.quote("file") + " and contentclass<>" + db.quote("image") + " and contentclass<>" + db.quote("link") + " and contentclass<>" + db.quote("product"), old_contentgroup, new_contentgroup);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameContenttype(DB db, String old_contenttype, String new_contenttype) {
		db.renameWhere("content", "contenttype", "contentclass<>" + db.quote("file") + " and contentclass<>" + db.quote("image") + " and contentclass<>" + db.quote("link") + " and contentclass<>" + db.quote("product"), old_contenttype, new_contenttype);
		db.renameWhere("content_archive", "contenttype", "contentclass<>" + db.quote("file") + " and contentclass<>" + db.quote("image") + " and contentclass<>" + db.quote("link") + " and contentclass<>" + db.quote("product"), old_contenttype, new_contenttype);
		db.renameWhere("content_public", "contenttype", "contentclass<>" + db.quote("file") + " and contentclass<>" + db.quote("image") + " and contentclass<>" + db.quote("link") + " and contentclass<>" + db.quote("product"), old_contenttype, new_contenttype);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameFilegroup(DB db, String old_contentgroup, String new_contentgroup) {
		db.renameWhere("content", "contentgroup", "contentclass=" + db.quote("file"), old_contentgroup, new_contentgroup);
		db.renameWhere("content_archive", "contentgroup", "contentclass=" + db.quote("file"), old_contentgroup, new_contentgroup);
		db.renameWhere("content_public", "contentgroup", "contentclass=" + db.quote("file"), old_contentgroup, new_contentgroup);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameFiletype(DB db, String old_contenttype, String new_contenttype) {
		db.renameWhere("content", "contenttype", "contentclass=" + db.quote("file"), old_contenttype, new_contenttype);
		db.renameWhere("content_archive", "contenttype", "contentclass=" + db.quote("file"), old_contenttype, new_contenttype);
		db.renameWhere("content_public", "contenttype", "contentclass=" + db.quote("file"), old_contenttype, new_contenttype);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameImagegroup(DB db, String old_contentgroup, String new_contentgroup) {
		db.renameWhere("content", "contentgroup", "contentclass=" + db.quote("image"), old_contentgroup, new_contentgroup);
		db.renameWhere("content_archive", "contentgroup", "contentclass=" + db.quote("image"), old_contentgroup, new_contentgroup);
		db.renameWhere("content_public", "contentgroup", "contentclass=" + db.quote("image"), old_contentgroup, new_contentgroup);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameImagetype(DB db, String old_contenttype, String new_contenttype) {
		db.renameWhere("content", "contenttype", "contentclass=" + db.quote("image"), old_contenttype, new_contenttype);
		db.renameWhere("content_archive", "contenttype", "contentclass=" + db.quote("image"), old_contenttype, new_contenttype);
		db.renameWhere("content_public", "contenttype", "contentclass=" + db.quote("image"), old_contenttype, new_contenttype);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameLinkgroup(DB db, String old_contentgroup, String new_contentgroup) {
		db.renameWhere("content", "contentgroup", "contentclass=" + db.quote("link"), old_contentgroup, new_contentgroup);
		db.renameWhere("content_archive", "contentgroup", "contentclass=" + db.quote("link"), old_contentgroup, new_contentgroup);
		db.renameWhere("content_public", "contentgroup", "contentclass=" + db.quote("link"), old_contentgroup, new_contentgroup);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameLinktype(DB db, String old_contenttype, String new_contenttype) {
		db.renameWhere("content", "contenttype", "contentclass=" + db.quote("link"), old_contenttype, new_contenttype);
		db.renameWhere("content_archive", "contenttype", "contentclass=" + db.quote("link"), old_contenttype, new_contenttype);
		db.renameWhere("content_public", "contenttype", "contentclass=" + db.quote("link"), old_contenttype, new_contenttype);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameProductgroup(DB db, String old_contentgroup, String new_contentgroup) {
		db.renameWhere("content", "contentgroup", "contentclass=" + db.quote("product"), old_contentgroup, new_contentgroup);
		db.renameWhere("content_archive", "contentgroup", "contentclass=" + db.quote("product"), old_contentgroup, new_contentgroup);
		db.renameWhere("content_public", "contentgroup", "contentclass=" + db.quote("product"), old_contentgroup, new_contentgroup);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameProducttype(DB db, String old_contenttype, String new_contenttype) {
		db.renameWhere("content", "contenttype", "contentclass=" + db.quote("product"), old_contenttype, new_contenttype);
		db.renameWhere("content_archive", "contenttype", "contentclass=" + db.quote("product"), old_contenttype, new_contenttype);
		db.renameWhere("content_public", "contenttype", "contentclass=" + db.quote("product"), old_contenttype, new_contenttype);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameUsername(DB db, String old_username, String new_username) {
		db.rename("content", "created_by", old_username, new_username);
		db.rename("content", "updated_by", old_username, new_username);
		db.rename("content", "published_by", old_username, new_username);
		db.rename("content", "unpublished_by", old_username, new_username);
		db.rename("content", "checkedout", old_username, new_username);
		db.rename("content", "status_by", old_username, new_username);
		db.rename("content_archive", "created_by", old_username, new_username);
		db.rename("content_archive", "updated_by", old_username, new_username);
		db.rename("content_archive", "published_by", old_username, new_username);
		db.rename("content_archive", "unpublished_by", old_username, new_username);
		db.rename("content_archive", "checkedout", old_username, new_username);
		db.rename("content_archive", "status_by", old_username, new_username);
		db.rename("content_public", "created_by", old_username, new_username);
		db.rename("content_public", "updated_by", old_username, new_username);
		db.rename("content_public", "published_by", old_username, new_username);
		db.rename("content_public", "unpublished_by", old_username, new_username);
		db.rename("content_public", "checkedout", old_username, new_username);
		db.rename("content_public", "status_by", old_username, new_username);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameUsergroup(DB db, String old_usergroup, String new_usergroup) {
		db.rename("content", "users_group", old_usergroup, new_usergroup);
		db.rename("content", "creators_group", old_usergroup, new_usergroup);
		db.rename("content", "developers_group", old_usergroup, new_usergroup);
		db.rename("content", "editors_group", old_usergroup, new_usergroup);
		db.rename("content", "publishers_group", old_usergroup, new_usergroup);
		db.rename("content", "administrators_group", old_usergroup, new_usergroup);
		db.rename("content_archive", "users_group", old_usergroup, new_usergroup);
		db.rename("content_archive", "creators_group", old_usergroup, new_usergroup);
		db.rename("content_archive", "developers_group", old_usergroup, new_usergroup);
		db.rename("content_archive", "editors_group", old_usergroup, new_usergroup);
		db.rename("content_archive", "publishers_group", old_usergroup, new_usergroup);
		db.rename("content_archive", "administrators_group", old_usergroup, new_usergroup);
		db.rename("content_public", "users_group", old_usergroup, new_usergroup);
		db.rename("content_public", "creators_group", old_usergroup, new_usergroup);
		db.rename("content_public", "developers_group", old_usergroup, new_usergroup);
		db.rename("content_public", "editors_group", old_usergroup, new_usergroup);
		db.rename("content_public", "publishers_group", old_usergroup, new_usergroup);
		db.rename("content_public", "administrators_group", old_usergroup, new_usergroup);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameUsertype(DB db, String old_usertype, String new_usertype) {
		db.rename("content", "users_type", old_usertype, new_usertype);
		db.rename("content", "creators_type", old_usertype, new_usertype);
		db.rename("content", "developers_type", old_usertype, new_usertype);
		db.rename("content", "editors_type", old_usertype, new_usertype);
		db.rename("content", "publishers_type", old_usertype, new_usertype);
		db.rename("content", "administrators_type", old_usertype, new_usertype);
		db.rename("content_archive", "users_type", old_usertype, new_usertype);
		db.rename("content_archive", "creators_type", old_usertype, new_usertype);
		db.rename("content_archive", "developers_type", old_usertype, new_usertype);
		db.rename("content_archive", "editors_type", old_usertype, new_usertype);
		db.rename("content_archive", "publishers_type", old_usertype, new_usertype);
		db.rename("content_archive", "administrators_type", old_usertype, new_usertype);
		db.rename("content_public", "users_type", old_usertype, new_usertype);
		db.rename("content_public", "creators_type", old_usertype, new_usertype);
		db.rename("content_public", "developers_type", old_usertype, new_usertype);
		db.rename("content_public", "editors_type", old_usertype, new_usertype);
		db.rename("content_public", "publishers_type", old_usertype, new_usertype);
		db.rename("content_public", "administrators_type", old_usertype, new_usertype);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameDevice(DB db, String old_device, String new_device) {
		db.rename("content", "device", old_device, new_device);
		db.rename("content_archive", "device", old_device, new_device);
		db.rename("content_public", "device", old_device, new_device);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameUsersegment(DB db, String old_usersegment, String new_usersegment) {
		db.rename("content", "usersegment", old_usersegment, new_usersegment);
		db.rename("content_archive", "usersegment", old_usersegment, new_usersegment);
		db.rename("content_public", "usersegment", old_usersegment, new_usersegment);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameUsertest(DB db, String old_usertest, String new_usertest) {
		db.rename("content", "usertest", old_usertest, new_usertest);
		db.rename("content_archive", "usertest", old_usertest, new_usertest);
		db.rename("content_public", "usertest", old_usertest, new_usertest);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void renameVersion(DB db, String old_version, String new_version) {
		db.rename("content", "version", old_version, new_version);
		db.rename("content_archive", "version", old_version, new_version);
		db.rename("content_public", "version", old_version, new_version);
		Cache.clear(db, "content");
		Cache.clear(db, "content_archive");
		Cache.clear(db, "content_public");
	}



	public void closeRecords(DB db) {
		db.closeRecords(rs);
//		init();
	}



	public boolean records(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String SQL) {
		if ((db == null) || db.isClosed()) return false;
		if ((SQL != null) && (! SQL.equals(""))) {
			rs = db.records(SQL);
			return true;
		} else {
			HashMap<String,String> row = db.records(rs);
			if (row != null) {
				init();
				getRecord(db, config, row, "", "", "");
				getAccessRestrictions(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config);
				return true;
			} else {
				init();
				return false;
			}
		}
	}



	public void preview_delete(DB db, Configuration config, String myid, String rootpath) throws Exception {
		if (db == null) return;
		String SQL = "select * from content where (locked=0 or locked is null) and id=" + Common.integer(myid);
		HashMap<String,String> row = db.query_record(SQL);
		if (row != null) {
			if ((row.get("server_filename") != null) && (! row.get("server_filename").equals(""))) {
				String filename = "" + row.get("server_filename");
				if ((! filename.equals("")) && (Common.fileExists(rootpath + filename + "/index.jsp"))) {
					Common.deleteFile(rootpath + filename + "/index.jsp");
					Common.deleteFolder(rootpath + filename, false);
				} else if ((! filename.equals("")) && (Common.fileExists(rootpath + filename))) {
					Common.deleteFile(rootpath + filename);
				}
			}
		}
		delete(db, config, myid, "content", "id");
		Cache.clear(db, "content");
	}



	public void public_delete(DB db, Configuration config, String myid, String rootpath) throws Exception {
		if (db == null) return;
		String content_filename = "";
		String SQL = "select * from content where (locked=0 or locked is null) and id=" + Common.integer(myid);
		HashMap<String,String> row = db.query_record(SQL);
		if (row != null) {
			if ((row.get("server_filename") != null) && (! row.get("server_filename").equals(""))) {
				content_filename = "" + row.get("server_filename");
			}
		}
		SQL = "select * from content_public where (locked=0 or locked is null) and id=" + Common.integer(myid);
		row = db.query_record(SQL);
		if (row != null) {
			if ((row.get("server_filename") != null) && (! row.get("server_filename").equals(""))) {
				String filename = "" + row.get("server_filename");
				if ((! filename.equals(content_filename)) && (! filename.equals("")) && (Common.fileExists(rootpath + filename + "/index.jsp"))) {
					Common.deleteFile(rootpath + filename + "/index.jsp");
					Common.deleteFolder(rootpath + filename, false);
				} else if ((! filename.equals(content_filename)) && (! filename.equals("")) && (Common.fileExists(rootpath + filename))) {
					Common.deleteFile(rootpath + filename);
				}
			}
		}
		delete(db, config, myid, "content_public", "id");
		published = "";
		published_by = "";
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("published", published);
		mydata.put("published_by", published_by);
		db.update("content", "id", myid, mydata);
		Cache.clear(db, "content");
		Cache.clear(db, "content_public");
	}



	public void archive_delete(DB db, Configuration config, String myid, String rootpath) throws Exception {
		if (db == null) return;
		String SQL = "select * from content_archive where (locked=0 or locked is null) and archiveid=" + Common.integer(myid);
		HashMap<String,String> row = db.query_record(SQL);
		if (row != null) {
			if ((row.get("server_filename") != null) && (! row.get("server_filename").equals(""))) {
				String filename = "" + row.get("server_filename");
				if ((! filename.equals("")) && (Common.fileExists(rootpath + filename + "." + row.get("archiveid")))) {
					Common.deleteFile(rootpath + filename + "." + row.get("archiveid"));
				}
			}
		}
		delete(db, config, myid, "content_archive", "archiveid");
		Cache.clear(db, "content_archive");
	}



	public void archive_delete_all(DB db, Configuration config, String myid, String rootpath) throws Exception {
		if (db == null) return;
		String SQL = "select * from content_archive where (locked=0 or locked is null) and id=" + Common.integer(myid);
		LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
		for (int i=0; i<rows.size(); i++) {
			HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
			if ((row.get("server_filename") != null) && (! row.get("server_filename").equals(""))) {
				String filename = "" + row.get("server_filename");
				if ((! filename.equals("")) && (Common.fileExists(rootpath + filename + "." + row.get("archiveid")))) {
					Common.deleteFile(rootpath + filename + "." + row.get("archiveid"));
				}
			}
		}
		delete(db, config, myid, "content_archive", "id");
		Cache.clear(db, "content_archive");
	}



	public boolean isArchived(DB db) {
		return isArchived(db, getId());
	}
	public boolean isArchived(DB db, String myid) {
		if (db == null) return false;
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String SQL = "select count(*) from content_archive where (id=" + Common.integer(myid) + ") and ((" + db.is_blank("published") + ") or (published<" + db.quote(now) + "))";
		if (db.query_value(SQL) > 0.0) {
			return true;
		} else {
			return false;
		}
	}



	public boolean isScheduled(DB db) {
		return isScheduled(db, getId());
	}
	public boolean isScheduled(DB db, String myid) {
		if (db == null) return false;
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String SQL = "select count(*) from content_archive where (id=" + Common.integer(myid) + ") and ((" + db.is_not_blank("published") + ") and (published>" + db.quote(now) + "))";
		if (db.query_value(SQL) > 0.0) {
			return true;
		} else {
			return false;
		}
	}



	public String CMS_header(String session_mode, String session_administrator, String session_admin_content_editor, DB db, Configuration config, ServletContext servletcontext, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		StringBuffer output = new StringBuffer();
		if (session_mode.equals("heatmap") && session_administrator.equals("administrator")) {
			output.append("<script type=\"text/javascript\" src=\"/" + text.display("adminpath") + "/heatmap.js\"></script>" + "\r\n");
			output.append("<script type=\"text/javascript\" src=\"/" + text.display("adminpath") + "/heatmap.jsp?id=" + id + "&log=" + heatmap + "&align=" + heatmapalign + "&heatmap_start=" + request.getParameter("heatmap_start") + "&heatmap_end=" + request.getParameter("heatmap_end") + "\"></script>" + "\r\n");
		} else if (session_mode.equals("admin") && session_administrator.equals("administrator")) {
			output.append("<link id=\"WCMbrowseeditcss\" rel=\"stylesheet\" type=\"text/css\" href=\"/" + text.display("adminpath") + "/webadmin.browseedit.css\" />");
			output.append("<script type=\"text/javascript\" src=\"/" + text.display("adminpath") + "/jquery/jquery.js\"></script>");
			output.append("<script type=\"text/javascript\" src=\"/" + text.display("adminpath") + "/ajax.js\"></script>");
			output.append("<script type=\"text/javascript\" src=\"/" + text.display("adminpath") + "/browseedit.js.jsp\"></script>");
			output.append("<script type=\"text/javascript\" src=\"/" + text.display("adminpath") + "/webadmin.browseedit.js\"></script>");
			output.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"/" + text.display("adminpath") + "/dtree.css\" />");
			output.append("<script type=\"text/javascript\" src=\"/" + text.display("adminpath") + "/dtree.js\"></script>");
			if (true) {
				if (((getContentClass().equals("page")) || (getContentClass().equals("product")) || (getContentClass().equals("stylesheet")) || (getContentClass().equals("script"))) && (config.get(db, "use_static_filenames").equals("yes"))) {
					output.append("<script type=\"text/javascript\">var baseHref = \"/" + html.encode(getServerFilename()) + "\"; var baseHrefPrefix = \"\";</script>");
				} else {
					output.append("<script type=\"text/javascript\">var baseHref = \"/\"; var baseHrefPrefix = \"/\";</script>");
				}

				String mywebeditor = "HardCore";
				if (! session_admin_content_editor.equals("")) {
					mywebeditor = session_admin_content_editor;
				} else if (! config.get(db, "content_editor").equals("")) {
					mywebeditor = config.get(db, "content_editor");
				}
				output.append(Common.include("/" + text.display("adminpath") + "/webeditors/" + mywebeditor + "/webeditor.jsp", servletcontext, session, request, response));
//				if (! adminuser.getHardcoreCustomScript().equals("")) {
//					output.append(adminuser.getHardcoreCustomScript());
//				} else {
//					output.append(myconfig.get(db, "hardcore_customscript"));
//				}
				output.append("\r\n");
				output.append("<style>" + "\r\n");
				output.append(".WCMoverlay {" + "\r\n");
				output.append(" visibility: hidden;" + "\r\n");
				output.append(" position: absolute;" + "\r\n");
				output.append(" left: 0px;" + "\r\n");
				output.append(" top: 0px;" + "\r\n");
				output.append(" width: 100%;" + "\r\n");
				output.append(" height: 100%;" + "\r\n");
				output.append(" text-align: center;" + "\r\n");
				output.append(" z-index: 9996;" + "\r\n");
				output.append(" background-color: #000;" + "\r\n");
				output.append(" opacity: 0.5;" + "\r\n");
				output.append(" filter: alpha(opacity=50);" + "\r\n");
				output.append(" -moz-opacity: 0.5;" + "\r\n");
				output.append(" -ms-filter: \"progid:DXImageTransform.Microsoft.Alpha(Opacity=50)\"" + "\r\n");
				output.append("}" + "\r\n");

				output.append(".WCMwrapper {" + "\r\n");
				output.append(" visibility: hidden;" + "\r\n");
				output.append(" position: absolute;" + "\r\n");
				output.append(" left: 0px;" + "\r\n");
				output.append(" top: 0px;" + "\r\n");
				output.append(" width: 100%;" + "\r\n");
				output.append(" height: 100%;" + "\r\n");
				output.append(" text-align: center;" + "\r\n");
				output.append(" z-index: 9997;" + "\r\n");
				output.append("}" + "\r\n");

				output.append(".WCMinputs {" + "\r\n");
				output.append(" z-index: 9998;" + "\r\n");
				output.append(" width: 800px;" + "\r\n");
				output.append(" margin: 50px auto;" + "\r\n");
				output.append(" background-color: #fff;" + "\r\n");
//				output.append(" opacity: 1;" + "\r\n");
//				output.append(" filter: alpha(opacity=100);" + "\r\n");
//				output.append(" -moz-opacity: 1;" + "\r\n");
//				output.append(" -ms-filter: \"progid:DXImageTransform.Microsoft.Alpha(Opacity=100)\"" + "\r\n");
				output.append(" border: 1px solid #000;" + "\r\n");
				output.append(" padding: 15px;" + "\r\n");
				output.append(" text-align: center;" + "\r\n");
				output.append("}");
				output.append("</style>");
			} else {
				output.append("<script type=\"text/javascript\">function WCMdoEdit(id) { window.location.href = '" + text.display("adminpath") + "/content/update.jsp?id=' + id + '&' + Math.random(); }</script>");
			}
		}
		return "" + output;
	}



	public String CMS_display(String session_mode, String session_administrator, String session_username, DB db, Configuration config) {
		return CMS_display(session_mode, session_administrator, session_username, "", db, config);
	}
	public String CMS_display(String session_mode, String session_administrator, String session_username, String session_preview_scheduled, DB db, Configuration config) {
		if (session_preview_scheduled == null) session_preview_scheduled = "";
		if (session_preview_scheduled.equals("null")) session_preview_scheduled = "";
		StringBuffer output = new StringBuffer();
		if (session_mode.equals("admin") && session_administrator.equals("administrator")) {
			output.append("<div class=\"WCMoverlay\" id=\"WCMoverlay\" style=\"display: none;\"></div>");
			output.append("<div class=\"WCMwrapper\" id=\"WCMwrapper\" style=\"display: none;\">");
			output.append("<div class=\"WCMinputs\" id=\"WCMinputs\">");
			output.append("<form id=\"contentform\" name=\"contentform\">");
			output.append("<table width=\"100%\">");
			output.append("<tr><th class=\"WCMinnerContentInputName\" align=\"left\">" + text.display("content.title") + "</th></tr>");
			output.append("<tr><td class=\"WCMinnerContentInputValue\" align=\"left\"><input type=\"text\" size=\"100\" maxlength=\"250\" style=\"width: 785px;\" id=\"WCMtitleinput\" name=\"title\"></td></tr>");
			output.append("<tr><th class=\"WCMinnerContentInputName\" align=\"left\">" + text.display("content.content") + "</th></tr>");
			output.append("<tr><td class=\"WCMinnerContentInputValue\" align=\"left\" id=\"WCMwebeditorinput\"></td></tr>");
			output.append("</table>");
			output.append("<p>&nbsp;</p>");
			output.append("<input type=\"hidden\" id=\"WCMidinput\" name=\"id\">");
			output.append("<input type=\"hidden\" id=\"WCMserverfilenameinput\" name=\"server_filename\">");
			if (config.get(db, "use_publish").equals("manual-on")) {
				output.append("<span id=\"WCMpublishinputs\"><input type=\"checkbox\" id=\"WCMpublishinput\" name=\"publish\" value=\"yes\" checked>" + text.display("publish") + "</span>");
			} else if (config.get(db, "use_publish").equals("manual-off")) {
				output.append("<span id=\"WCMpublishinputs\"><input type=\"checkbox\" id=\"WCMpublishinput\" name=\"publish\" value=\"yes\">" + text.display("publish") + "</span>");
			} else if (config.get(db, "use_publish").equals("auto-on-save")) {
				output.append("<span id=\"WCMpublishinputs\"><input type=\"hidden\" id=\"WCMpublishinput\" name=\"publish\" value=\"yes\"></span>");
			}
			if (! config.get(db, "use_workflow").equals("yes")) {
				if (config.get(db, "use_publish").equals("manual-on")) {
					output.append("<span id=\"WCMreadytopublishinputs\"><input type=\"checkbox\" id=\"WCMreadytopublishinput\" name=\"ready_to_publish\" value=\"Ready To Publish\" checked>" + text.display("ready") + "</span>");
				} else if (config.get(db, "use_publish").equals("manual-off")) {
					output.append("<span id=\"WCMreadytopublishinputs\"><input type=\"checkbox\" id=\"WCMreadytopublishinput\" name=\"ready_to_publish\" value=\"Ready To Publish\">" + text.display("ready") + "</span>");
				}
			}
			output.append(" <input type=\"button\" value=\"" + text.display("save") + "\" onClick=\"WCMdoSave();\">");
			output.append(" <input type=\"button\" value=\"" + text.display("cancel") + "\" onClick=\"WCMdoCancel();\">");
			output.append("</form>");
			output.append("</div>");
			output.append("</div>");

			output.append("<div id=\"WCMmenucontainer\" class=\"WCMmenucontainer\">");
			output.append("<div class=\"WCMmenuminwidth0\"><div class=\"WCMmenuminwidth1\"><div class=\"WCMmenuminwidth2\">");
			output.append("<div id=\"WCMmenuhead\">");

			output.append("<table class=\"WCMbrowseEditHeader\">");
			output.append("  <tr class=\"WCMbrowseEditHeader\">");
			output.append("    <td class=\"WCMbrowseEditHeader\" align=\"left\"><a id=\"WCMproduct\" href=\"/" + text.display("adminpath") + "/index.jsp\">" + text.display("product") + "</a></td>");
			output.append("    <td class=\"WCMbrowseEditHeader\" align=\"right\"><a id=\"WCMhome\" href=\"/" + text.display("adminpath") + "/index.jsp\">" + text.display("toolbar.home") + "</a> - <a id=\"WCMhelp\" href=\"#\" onclick=\"window.open('/" + text.display("adminpath") + "/help/index.jsp', 'help_window', 'scrollbars=yes,width=640,height=550,resizable=yes,status=yes', true);\">" + text.display("toolbar.help") + "</a> - <a id=\"WCMlogout\" href=\"/" + text.display("adminpath") + "/logout.jsp\">" + text.display("toolbar.logout") + "</a></td>");
			output.append("  </tr>");
			output.append("</table>");

			output.append("</div>");
			output.append("<div id=\"WCMmenu\" class=\"WCMmenu\">");

			output.append("<ul id=\"WCMmenuoutline\">");
			output.append("	<li><a href=\"javascript:WCMtoggleOutline('');\">" + text.display("browseedit.outline") + "<!--[if gt IE 6]><!--></a><!--<![endif]--><!--[if lt IE 7]><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><![endif]-->");
			output.append("		<ul id=\"WCMmenuoutlineitems\" class=\"WCMmenuskinny\">");
			output.append("			<li><a href=\"javascript:WCMtoggleOutline('none');\">" + text.display("browseedit.outline.none") + "</a></li>");
			output.append("			<li><a href=\"javascript:WCMtoggleOutline('black');\">" + text.display("browseedit.outline.black") + "</a></li>");
			output.append("			<li><a href=\"javascript:WCMtoggleOutline('white');\">" + text.display("browseedit.outline.white") + "</a></li>");
			output.append("		</ul>");
			output.append("		<!--[if lte IE 6]></td></tr></table></a><![endif]-->");
			output.append("	</li>");
			output.append("</ul>");

			if (config.get(db, "use_versions").equals("yes")) {
				output.append("<ul id=\"WCMmenuversion\">");
				output.append("	<li><a href=\"javascript:WCMtoggleVersion('');\">" + text.display("browseedit.version") + "<!--[if gt IE 6]><!--></a><!--<![endif]--><!--[if lt IE 7]><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><![endif]-->");
				output.append("		<ul id=\"WCMmenuversionitems\" class=\"WCMmenuskinny\">");
				output.append("			<li><a href=\"javascript:WCMtoggleVersion('');\">" + text.display("browseedit.version.default") + "</a></li>");
				String[] versions = (String[])db.query_values("select distinct version from versions order by version").values().toArray(new String[0]);
				for (int i=0; i<versions.length; i++) {
					output.append("			<li><a href=\"javascript:WCMtoggleVersion('" + versions[i] + "');\">" + versions[i] + "</a></li>");
				}
				output.append("		</ul>");
				output.append("		<!--[if lte IE 6]></td></tr></table></a><![endif]-->");
				output.append("	</li>");
				output.append("</ul>");
			}

			if ((config.get(db, "use_scheduled_publish").equals("yes")) || (config.get(db, "use_schedule").equals("yes"))) {
				String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
				output.append("<ul id=\"WCMmenupreview\">");
				output.append("	<li><a href=\"javascript:WCMtogglePreview('');\">" + text.display("browseedit.preview") + "<!--[if gt IE 6]><!--></a><!--<![endif]--><!--[if lt IE 7]><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><![endif]-->");
				output.append("		<ul id=\"WCMmenupreviewitems\" class=\"WCMmenuskinny\">");
				if (! session_preview_scheduled.equals("")) {
					output.append("			<li><a href=\"javascript:WCMtogglePreview('" + session_preview_scheduled + "',false);\">" + session_preview_scheduled + "</a></li>");
					output.append("			<li><a href=\"javascript:WCMtogglePreview('',false);\">" + text.display("browseedit.preview.current") + "</a></li>");
					output.append("			<li><a href=\"javascript:WCMtogglePreview('" + session_preview_scheduled + "','" + text.display("browseedit.preview.select.text") + "');\">" + text.display("browseedit.preview.select") + "</a></li>");
				} else {
					output.append("			<li><a href=\"javascript:WCMtogglePreview('',false);\">" + text.display("browseedit.preview.current") + "</a></li>");
					output.append("			<li><a href=\"javascript:WCMtogglePreview('" + now + "','" + text.display("browseedit.preview.select.text") + "');\">" + text.display("browseedit.preview.select") + "</a></li>");
				}
				String SQL = "";
				LinkedHashMap<String,String> datestimes = new LinkedHashMap<String,String>();
//				if (config.get(db, "use_scheduled_publish").equals("yes")) {
					SQL = "select distinct scheduled_publish from content where (locked_schedule is null or locked_schedule <> 1) and (scheduled_publish >= '" + now + "') and " + db.is_not_blank("scheduled_publish") + " order by scheduled_publish";
					datestimes = Common.array_merge_values(datestimes, db.query_values(SQL));
					SQL = "select distinct scheduled_unpublish from content_public where (locked_unschedule is null or locked_unschedule <> 1) and (scheduled_unpublish >= '" + now + "') and " + db.is_not_blank("scheduled_unpublish") + " order by scheduled_unpublish";
					datestimes = Common.array_merge_values(datestimes, db.query_values(SQL));
//				}
//				if (config.get(db, "use_schedule").equals("yes")) {
					SQL = "select distinct published from content_archive where (locked_schedule is null or locked_schedule <> 1) and (published >= '" + now + "') and " + db.is_not_blank("published") + " order by published";
					datestimes = Common.array_merge_values(datestimes, db.query_values(SQL));
//				}
				String[] datetime = (String[])datestimes.values().toArray(new String[0]);
				java.util.Arrays.sort(datetime);
				for (int i=0; i<datetime.length; i++) {
					output.append("			<li><a href=\"javascript:WCMtogglePreview('" + datetime[i] + "',false);\">" + datetime[i] + "</a></li>");
				}
				output.append("		</ul>");
				output.append("		<!--[if lte IE 6]></td></tr></table></a><![endif]-->");
				output.append("	</li>");
				output.append("</ul>");
			}

			output.append("<ul id=\"WCMmenuedit\">");
			output.append("	<li><a href=\"javascript:WCMdoEditAll(true,'" + id + "');\">" + text.display("browseedit.edit") + "<!--[if gt IE 6]><!--></a><!--<![endif]--><!--[if lt IE 7]><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><![endif]-->");
			output.append("		<ul id=\"WCMmenuedititems\">");
			output.append("			<li><a href=\"javascript:WCMdoEditAll(true,'" + id + "');\">" + text.display("browseedit.edit.inline") + "</a></li>");
			output.append("			<!-- pages -->");
			output.append("			<!-- elements -->");
			output.append("			<!-- templates -->");
			output.append("			<!-- stylesheets -->");
			output.append("			<!-- scripts -->");
			output.append("			<!-- images -->");
			output.append("			<!-- files -->");
			output.append("			<!-- links -->");
			output.append("		</ul>");
			output.append("		<!--[if lte IE 6]></td></tr></table></a><![endif]-->");
			output.append("	</li>");
			output.append("</ul>");
			output.append("<ul id=\"WCMmenupublish\">");
			output.append("	<li><a href=\"#\">" + text.display("browseedit.publish") + "<!--[if gt IE 6]><!--></a><!--<![endif]--><!--[if lt IE 7]><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><![endif]-->");
			output.append("		<ul id=\"WCMmenupublishitems\" class=\"WCMmenuleft\">");
			output.append("			<!-- pages -->");
			output.append("			<!-- elements -->");
			output.append("			<!-- templates -->");
			output.append("			<!-- stylesheets -->");
			output.append("			<!-- scripts -->");
			output.append("			<!-- images -->");
			output.append("			<!-- files -->");
			output.append("			<!-- links -->");
			output.append("		</ul>");
			output.append("		<!--[if lte IE 6]></td></tr></table></a><![endif]-->");
			output.append("	</li>");
			output.append("</ul>");
			output.append("<ul id=\"WCMmenuadmin\">");
			output.append("	<li><a href=\"#\">" + text.display("browseedit.admin") + "<!--[if gt IE 6]><!--></a><!--<![endif]--><!--[if lt IE 7]><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><![endif]-->");
			output.append("		<ul id=\"WCMmenuadminitems\" class=\"WCMmenuleft\">");
			output.append("			<!-- pages -->");
			output.append("			<!-- elements -->");
			output.append("			<!-- templates -->");
			output.append("			<!-- stylesheets -->");
			output.append("			<!-- scripts -->");
			output.append("			<!-- images -->");
			output.append("			<!-- files -->");
			output.append("			<!-- links -->");
			output.append("		</ul>");
			output.append("		<!--[if lte IE 6]></td></tr></table></a><![endif]-->");
			output.append("	</li>");
			output.append("</ul>");
			output.append("<ul id=\"WCMmenuaddnew\">");
			output.append("	<li><a href=\"#\">" + text.display("browseedit.addnew") + "<!--[if gt IE 6]><!--></a><!--<![endif]--><!--[if lt IE 7]><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><![endif]-->");
			output.append("		<ul id=\"WCMmenuaddnewitems\" class=\"WCMmenuleft\">");
			output.append("			<!-- pages -->");
			output.append("			<!-- elements -->");
			output.append("			<!-- templates -->");
			output.append("			<!-- stylesheets -->");
			output.append("			<!-- scripts -->");
			output.append("			<!-- images -->");
			output.append("			<!-- files -->");
			output.append("			<!-- links -->");
			output.append("		</ul>");
			output.append("		<!--[if lte IE 6]></td></tr></table></a><![endif]-->");
			output.append("	</li>");
			output.append("</ul>");

			output.append("</div>");

			output.append("<div id=\"WCMeditor_toolbarcontainer\" class=\"WCMeditor_toolbarcontainer\" style=\"display:none;\">");
			output.append("<div id=\"WCMeditor_toolbar\" class=\"WCMeditor_toolbar\"></div>");
			output.append("<div id=\"WCMeditor_save\" class=\"WCMeditor_save\">");
			output.append("<form id=\"contentformall\" name=\"contentformall\">");
			output.append("<input type=\"hidden\" id=\"WCMidinputall\" name=\"id\">");
			if (config.get(db, "use_publish").equals("manual-on")) {
				output.append("<span id=\"WCMpublishinputsall\"><input type=\"checkbox\" id=\"WCMpublishinputall\" name=\"publish\" value=\"yes\" checked>" + text.display("publish") + "</span>");
			} else if (config.get(db, "use_publish").equals("manual-off")) {
				output.append("<span id=\"WCMpublishinputsall\"><input type=\"checkbox\" id=\"WCMpublishinputall\" name=\"publish\" value=\"yes\">" + text.display("publish") + "</span>");
			} else if (config.get(db, "use_publish").equals("auto-on-save")) {
				output.append("<span id=\"WCMpublishinputsall\"><input type=\"hidden\" id=\"WCMpublishinputall\" name=\"publish\" value=\"yes\"></span>");
			}
			if (! config.get(db, "use_workflow").equals("yes")) {
				if (config.get(db, "use_publish").equals("manual-on")) {
output.append("<!-- ");
					output.append("<span id=\"WCMreadytopublishinputsall\"><input type=\"checkbox\" id=\"WCMreadytopublishinputall\" name=\"ready_to_publish\" value=\"Ready To Publish\" checked>" + text.display("ready") + "</span>");
output.append(" -->");
				} else if (config.get(db, "use_publish").equals("manual-off")) {
output.append("<!-- ");
					output.append("<span id=\"WCMreadytopublishinputsall\"><input type=\"checkbox\" id=\"WCMreadytopublishinputall\" name=\"ready_to_publish\" value=\"Ready To Publish\">" + text.display("ready") + "</span>");
output.append(" -->");
				}
			}
			output.append(" &nbsp; <input type=\"button\" value=\"" + text.display("save") + "\" onClick=\"WCMdoSaveAll();\">");
			output.append(" &nbsp; <input type=\"button\" value=\"" + text.display("cancel") + "\" onClick=\"WCMdoCancelAll();\">");
			output.append("</form>");
			output.append("</div>");
			output.append("</div>");

			output.append("</div>");
			output.append("</div>");
			output.append("</div>");
			output.append("</div>");
			output.append("</div>");
			output.append("<hr style=\"display:none\" />");
			output.append("<div id=\"WCMpagecontainer\" class=\"WCMpagecontainer\">");

		}
		return "" + output;
	}



	public String header(DB db, String item, String item2) {
		return header(db, (Configuration) null, item, item2);
	}
	public String header(DB db, Configuration myconfig, String item, String item2) {
		StringBuffer output = new StringBuffer();
		if (item.equals("metainfo")) {
			String[] myinfos = getMetaInfo().split("[\r\n]+");
			for (int j=0; j<myinfos.length; j++) {
				String myinfo = myinfos[j];
				Matcher myinfoMatches = Pattern.compile("<([^<>]+)>(.*?)</([^<>]+)>").matcher(myinfo);
				if (myinfoMatches.find()) {
					String myname = "" + myinfoMatches.group(1);
					String myvalue = "" + myinfoMatches.group(2);
					if (myname.startsWith("_")) {
						// ignore
					} else if ((myconfig != null) && (! myconfig.get(db, "doctype").equals("")) && (myconfig.get(db, "doctype").indexOf("xhtml") > -1)) {
						if (myname.startsWith("http-equiv-")) {
							output.append("<meta http-equiv=\"" + myname.replaceAll("^http-equiv-", "") + "\" content='" + myvalue + "' />" + "\r\n");
						} else if (myname.startsWith("http-equiv ")) {
							output.append("<meta http-equiv=\"" + myname.replaceAll("^http-equiv ", "") + "\" content='" + myvalue + "' />" + "\r\n");
						} else {
							output.append("<meta name=\"" + myname + "\" content=\"" + myvalue + "\" />" + "\r\n");
						}
					} else {
						if (myname.startsWith("http-equiv-")) {
							output.append("<meta http-equiv=\"" + myname.replaceAll("^http-equiv-", "") + "\" content='" + myvalue + "'>" + "\r\n");
						} else if (myname.startsWith("http-equiv ")) {
							output.append("<meta http-equiv=\"" + myname.replaceAll("^http-equiv ", "") + "\" content='" + myvalue + "'>" + "\r\n");
						} else {
							output.append("<meta name=\"" + myname + "\" content=\"" + myvalue + "\">" + "\r\n");
						}
					}
				}
			}
			Iterator contentelements = elements(db, myconfig).keySet().iterator();
			while (contentelements.hasNext()) {
				String elementname = "" + contentelements.next();
				if (element_content.get(elementname) != null) {
					for (int i=0; i<((Content[]) element_content.get(elementname)).length; i++) {
						myinfos = ((Content[]) element_content.get(elementname))[i].getMetaInfo().split("[\r\n]+");
						for (int j=0; j<myinfos.length; j++) {
							String myinfo = myinfos[j];
							Matcher myinfoMatches = Pattern.compile("<([^<>]+)>([^<>]*)</([^<>]+)>").matcher(myinfo);
							if (myinfoMatches.find()) {
								String myname = "" + myinfoMatches.group(1);
								String myvalue = "" + myinfoMatches.group(2);
								if (myname.startsWith("_")) {
									// ignore
								} else if ((myconfig != null) && (myconfig.get(db, "doctype").indexOf("xhtml") > -1)) {
									if (myname.startsWith("http-equiv-")) {
										output.append("<meta http-equiv=\"" + myname.replaceAll("^http-equiv-", "") + "\" content='" + myvalue + "' />" + "\r\n");
									} else if (myname.startsWith("http-equiv ")) {
										output.append("<meta http-equiv=\"" + myname.replaceAll("^http-equiv ", "") + "\" content='" + myvalue + "' />" + "\r\n");
									} else {
										output.append("<meta name=\"" + myname + "\" content=\"" + myvalue + "\" />" + "\r\n");
									}
								} else {
									if (myname.startsWith("http-equiv-")) {
										output.append("<meta http-equiv=\"" + myname.replaceAll("^http-equiv-", "") + "\" content='" + myvalue + "'>" + "\r\n");
									} else if (myname.startsWith("http-equiv ")) {
										output.append("<meta http-equiv=\"" + myname.replaceAll("^http-equiv ", "") + "\" content='" + myvalue + "'>" + "\r\n");
									} else {
										output.append("<meta name=\"" + myname + "\" content=\"" + myvalue + "\">" + "\r\n");
									}
								}
							}
						}
					}
				}
			}
		} else if (item2.equals("")) {
			if (item.equals("title")) {
				String mytitle_prefix = "" + getTitlePrefix();
				String mytitle_suffix = "" + getTitleSuffix();
				if ((mytitle_prefix.equals("")) && (! myconfig.get(db, "default_title_prefix").equals(""))) mytitle_prefix = mytitle_prefix + myconfig.get(db, "default_title_prefix") + " ";
				if ((mytitle_suffix.equals("")) && (! myconfig.get(db, "default_title_suffix").equals(""))) mytitle_suffix = mytitle_suffix + " " + myconfig.get(db, "default_title_suffix");
				output.append(mytitle_prefix + title + mytitle_suffix);
			} else if (item.equals("content")) {
				output.append(content);
			} else if (item.equals("htmlattributes")) {
				output.append(htmlattributes);
			} else if (item.equals("htmlheader")) {
				output.append(htmlheader);
			} else if (item.equals("htmlbodyonload")) {
				if (! htmlbodyonload.equals("")) {
					output.append(" "+htmlbodyonload);
				}
			} else if (item.equals("author")) {
				output.append(author);
				Iterator contentelements = elements(db, myconfig).keySet().iterator();
				int j = 0;
				while (contentelements.hasNext()) {
					String elementname = "" + contentelements.next();
					if (element_content.get(elementname) != null) {
						for (int i=0; i<((Content[]) element_content.get(elementname)).length; i++) {
							if (! ((Content[]) element_content.get(elementname))[i].getAuthor().equals("")) {
								output.append(" " + ((Content[]) element_content.get(elementname))[i].getAuthor());
							}
						}
					}
				}
			} else if (item.equals("description")) {
				output.append(description);
				Iterator contentelements = elements(db, myconfig).keySet().iterator();
				int j = 0;
				while (contentelements.hasNext()) {
					String elementname = "" + contentelements.next();
					if (element_content.get(elementname) != null) {
						for (int i=0; i<((Content[]) element_content.get(elementname)).length; i++) {
							if (! ((Content[]) element_content.get(elementname))[i].getDescription().equals("")) {
								output.append(" " + ((Content[]) element_content.get(elementname))[i].getDescription());
							}
						}
					}
				}
			} else if (item.equals("keywords")) {
				output.append(keywords);
				Iterator contentelements = elements(db, myconfig).keySet().iterator();
				int j = 0;
				while (contentelements.hasNext()) {
					String elementname = "" + contentelements.next();
					if (element_content.get(elementname) != null) {
						for (int i=0; i<((Content[]) element_content.get(elementname)).length; i++) {
							if (! ((Content[]) element_content.get(elementname))[i].getKeywords().equals("")) {
								output.append(" " + ((Content[]) element_content.get(elementname))[i].getKeywords());
							}
						}
					}
				}
			}
		} else {
			if (element_content.get(item) != null) {
				for (int i=0; i<((Content[]) element_content.get(item)).length; i++) {
					if (! ((Content[]) element_content.get(item))[i].header(db, myconfig, item2, "").equals("")) {
						output.append(" " + ((Content[]) element_content.get(item))[i].header(db, myconfig, item2, ""));
					}
				}
			}
		}
		return "" + output;
	}



	public String display(String item, boolean browseedit, String script_name, String query_string, String session_mode, String session_administrator) {
		StringBuffer output = new StringBuffer();
		if (browseedit && (user) && (! id.equals("")) && (session_mode.equals("admin")) && session_administrator.equals("administrator") && (! item.equals("body")) && ((item.equals(" ")) || (item.equals("title")) || (item.equals("content")) || (item.equals("summary")) || (item.equals("description")) || (item.equals("keywords")) || (item.equals("author")) || (item.equals("copyright")))) {
			String redirect = URLEncoder.encode(script_name + "?" + query_string);
			String addnew_href = "javascript:WCMdoAddNew('/" + text.display("adminpath") + "/content/create.jsp?id=" + id + "&redirect=" + redirect + "')";
			String admin_href = "javascript:WCMdoAdmin('/" + text.display("adminpath") + "/content/admin.jsp?id=" + id + "&redirect=" + redirect + "')";
			String edit_href = "javascript:WCMdoEdit(" + id + ");";
			String publish_href = "javascript:WCMdoPublish('/" + text.display("adminpath") + "/content/publish.jsp?id=" + id + "&redirect=" + redirect + "')";
			boolean isPublished = true;
			if ((getPublished().equals("")) && (getScheduledPublish().equals("")) && (getScheduledUnpublish().equals(""))) {
				isPublished = false;
			}
			if ((getUpdated().compareTo(getPublished()) > 0) && (! getPublished().equals(""))) {
				isPublished = false;
			}
			output.append("&nbsp;<table id=\"WCMeditablecontent_" + id + "\" class=\"WCMeditable WCMeditableclass_" + contentclass + "\"><tr><td>");
			output.append("<table class=\"WCMeditable\">");
			output.append("<tr class=\"WCMeditable\"><td><table class=\"WCMeditableheader\">");
			output.append("<tr><td class=\"WCMeditablestatus\">");
			output.append(display_status());
			output.append("</td><td class=\"WCMeditabletitle\">");
			if ((contentclass.equals("script")) || (contentclass.equals("stylesheet")) || (contentclass.equals("template"))) {
				output.append("\"" + title + "\"" + " ");
			}
			output.append(contentclass + " " + item);
			output.append("</td><td class=\"WCMeditablelinks\">");
			if ((editor) && (! id.equals(""))) {
				if (creator) {
					output.append("<nobr><a href=\"" + addnew_href + "\">" + text.display("addnew") + "</a></nobr><br>");
				} else {
					addnew_href = "";
				}
				output.append(" <nobr><a href=\"" + admin_href + "\">" + text.display("admin") + "</a></nobr>");
				output.append("<br><nobr><a href=\"" + edit_href + "\">" + text.display("edit") + "</a></nobr>");
				if ((publisher) && (! isPublished)) {
					output.append("<br><nobr><a href=\"" + publish_href + "\">" + text.display("publish") + "</a></nobr>");
				} else {
					publish_href = "";
				}
			} else {
				addnew_href = "";
				admin_href = "";
				edit_href = "";
				publish_href = "";
			}
			output.append("<script>WCMbrowseEditMenu('" + id + "','" + contentclass + "','" + title + "',\"" + addnew_href + "\",\"" + edit_href + "\",\"" + admin_href + "\",\"" + publish_href + "\", '" + display_status() + "');</script>");
			output.append("</td></tr>");
			output.append("</table></td></tr>");

			if (editable.get(item + "_" + getId()) == null) {
				if ((get(item).indexOf("###")<0) && (get(item).indexOf("@@@")<0)) {
					editable.put(item + "_" + getId(), Boolean.TRUE);
				} else {
					editable.put(item + "_" + getId(), Boolean.FALSE);
				}
			}
			if (editor && (editable.get(item + "_" + getId()) == Boolean.TRUE)) {
				output.append("<tr><td><div id=\"WCMeditable_" + item.replaceAll(" ", "") + "_" + id + "\" class=\"WCMeditablecontentcontent\">");
			} else {
				output.append("<tr><td>");
			}
		}

		if (item.equals("title")) {
			output.append(title);
		} else if (item.equals("content")) {
			output.append(content);
		} else if (item.equals("id")) {
			output.append(id);
		} else if (item.equals("device")) {
			output.append(device);
		} else if (item.equals("usersegment")) {
			output.append(usersegment);
		} else if (item.equals("usertest")) {
			output.append(usertest);
		} else if (item.equals("heatmap")) {
			output.append(heatmap);
		} else if (item.equals("heatmapalign")) {
			output.append(heatmapalign);
		} else if (item.equals("usagelog")) {
			output.append(usagelog);
		} else if (item.equals("version")) {
			output.append(version);
		} else if (item.equals("version_master")) {
			output.append(version_master);
		} else if (item.equals("author")) {
			output.append(author);
		} else if (item.equals("keywords")) {
			output.append(keywords);
		} else if (item.equals("description")) {
			output.append(description);
		} else if (item.equals("summary")) {
			output.append(summary);
		} else if (item.equals("created")) {
			output.append(created);
		} else if (item.equals("updated")) {
			output.append(updated);
		} else if (item.equals("published")) {
			output.append(published);
		} else if (item.equals("unpublished")) {
			output.append(unpublished);
		} else if (item.equals("created_by")) {
			output.append(created_by);
		} else if (item.equals("updated_by")) {
			output.append(updated_by);
		} else if (item.equals("published_by")) {
			output.append(published_by);
		} else if (item.equals("unpublished_by")) {
			output.append(unpublished_by);
		} else if (item.equals("page_top")) {
			output.append(page_top);
		} else if (item.equals("page_up")) {
			output.append(page_up);
		} else if (item.equals("page_first")) {
			output.append(page_first);
		} else if (item.equals("page_last")) {
			output.append(page_last);
		} else if (item.equals("page_previous")) {
			output.append(page_previous);
		} else if (item.equals("page_next")) {
			output.append(page_next);
		} else if (item.equals("image1")) {
			output.append(image1);
		} else if (item.equals("image2")) {
			output.append(image2);
		} else if (item.equals("image3")) {
			output.append(image3);
		} else if (item.equals("file1")) {
			output.append(file1);
		} else if (item.equals("file2")) {
			output.append(file2);
		} else if (item.equals("file3")) {
			output.append(file3);
		} else if (item.equals("link1")) {
			output.append(link1);
		} else if (item.equals("link2")) {
			output.append(link2);
		} else if (item.equals("link3")) {
			output.append(link3);
		} else if (item.equals("filename")) {
			output.append(server_filename);
		} else if (item.equals("server_filename")) {
			output.append(server_filename);
		} else if (item.equals("searchable")) {
			output.append(searchable);
		} else if (item.equals("menuitem")) {
			output.append(menuitem);
		} else if (item.startsWith("metainfo_")) {
			output.append(getMetaInfo(item.substring(9)));
		} else if (item.startsWith("product_info_")) {
			output.append(getMetaInfo(item.substring(13)));
		} else if (item.startsWith("productinfo_")) {
			output.append(getMetaInfo(item.substring(12)));
		}

		if (browseedit && (user) && (! id.equals("")) && (session_mode.equals("admin")) && session_administrator.equals("administrator") && (! item.equals("body")) && ((item.equals(" ")) || (item.equals("title")) || (item.equals("content")) || (item.equals("summary")) || (item.equals("description")) || (item.equals("keywords")) || (item.equals("author")) || (item.equals("copyright")))) {
			if (editor && (editable.get(item + "_" + getId()) == Boolean.TRUE)) {
				output.append("</div></td></tr>");
			} else {
				output.append("</td></tr>");
			}
			output.append("</table>");
			output.append("</td></tr></table>&nbsp;");
		}
		return "" + output;
	}



	public String display_status() {
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String mystatus = "";
		String status_date = "";
		String status_user = "";
		if (((getPublished().equals("")) && (! getUnpublished().equals(""))) || ((getPublished().equals("")) && (! getUnpublished().equals("")) &&  (getPublished().compareTo(getUnpublished()) < 0))) {
			if (! mystatus.equals("")) mystatus += "<br>";
			status_date = getUnpublished();
			status_user = getUnpublishedBy();
			mystatus = mystatus + "<font size=\"1\" color=\"#000000\">" + text.display("content.status.unpublished") + ": " + status_date + "</font>";
		} else if ((getPublished().equals("")) && (! getScheduledUnpublish().equals("")) && (getScheduledUnpublish().compareTo(now) < 0)) {
			if (! mystatus.equals("")) mystatus += "<br>";
			status_date = getScheduledUnpublish();
			status_user = getUpdatedBy();
			mystatus = mystatus + "<font size=\"1\" color=\"#000000\">" + text.display("content.status.expired") + ": " + status_date + "</font>";
		} else if (getPublished().equals("")) {
			if (! mystatus.equals("")) mystatus += "<br>";
			status_date = getCreated();
			status_user = getCreatedBy();
			mystatus = mystatus + "<font size=\"1\" color=\"#0000FF\">" + text.display("content.status.new") + ": " + status_date + "</font>";
		} else if (getUpdated().compareTo(getPublished()) > 0) {
			if (! mystatus.equals("")) mystatus += "<br>";
			status_date = getUpdated();
			status_user = getUpdatedBy();
			mystatus = mystatus + "<font size=\"1\" color=\"orange\">" + text.display("content.status.updated") + ": " + status_date + "</font>";
		} else {
			if (! mystatus.equals("")) mystatus += "<br>";
			status_date = getPublished();
			status_user = getPublishedBy();
			mystatus = mystatus + "<font size=\"1\" color=\"#00C000\">" + text.display("content.status.published") + ": " + status_date + "</font>";
		}
		if ((! getScheduledPublish().equals("")) && (getScheduledPublish().compareTo(now) > 0) && (! isLockedSchedule())) {
			if (! mystatus.equals("")) mystatus += "<br>";
			mystatus = mystatus + "<font size=\"1\" color=\"#40c040\">" + text.display("content.status.scheduled.publish") + "</font>";
			mystatus = mystatus + " <font size=\"1\" color=\"#40c040\">" + getScheduledPublish() + "</font>";
		}
		if ((! getScheduledUnpublish().equals("")) && (getScheduledUnpublish().compareTo(now) > 0) && (! isLockedUnschedule())) {
			if (! mystatus.equals("")) mystatus += "<br>";
			mystatus = mystatus + "<font size=\"1\" color=\"#006000\">" + text.display("content.status.scheduled.unpublish") + "</font>";
			mystatus = mystatus + " <font size=\"1\" color=\"#006000\">" + getScheduledUnpublish() + "</font>";
		}
		if (! getCheckedOut().equals("")) {
			if (! mystatus.equals("")) mystatus += "<br>";
			mystatus = mystatus + "<font size=\"1\" color=\"#FF0000\">" + text.display("content.status.checkedout") + ": " + getCheckedOut() + "</font>";
		}
                if (! getStatus().equals("")) {
			if (! mystatus.equals("")) mystatus += "<br>";
        	        mystatus = "<span title=\"" + getStatus() + " - " + mystatus.replaceAll("<br>", " ").replaceAll("<[^<]*>", " ") + "\"><font size=\"1\">" + getStatus() + " - " + mystatus + "</font></span>";
                } else {
			if (! mystatus.equals("")) mystatus += "<br>";
	                mystatus = "<span title=\"" + mystatus.replaceAll("<br>", " ").replaceAll("<[^<]*>", " ") + "\">" + mystatus + "</span>";
                }
		return "" + mystatus;
	}



	public String display_status_icon() {
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String mystatus = "";
		String myicon = "";
		String status_date = "";
		String status_user = "";
		if (((getPublished().equals("")) && (! getUnpublished().equals(""))) || ((getPublished().equals("")) && (! getUnpublished().equals("")) &&  (getPublished().compareTo(getUnpublished()) < 0))) {
			if (! mystatus.equals("")) mystatus += "\r\n";
			status_date = getUnpublished();
			status_user = getUnpublishedBy();
			myicon = "unpublished";
			mystatus = mystatus + "" + text.display("content.status.unpublished") + ": " + status_date + "\r\n";
		} else if ((getPublished().equals("")) && (! getScheduledUnpublish().equals("")) && (getScheduledUnpublish().compareTo(now) < 0)) {
			if (! mystatus.equals("")) mystatus += "\r\n";
			status_date = getScheduledUnpublish();
			status_user = getUpdatedBy();
			myicon = "expired";
			mystatus = mystatus + "" + text.display("content.status.expired") + ": " + status_date + "\r\n";
		} else if (getPublished().equals("")) {
			if (! mystatus.equals("")) mystatus += "\r\n";
			status_date = getCreated();
			status_user = getCreatedBy();
			myicon = "new";
			mystatus = mystatus + "" + text.display("content.status.new") + ": " + status_date + "\r\n";
		} else if (getUpdated().compareTo(getPublished()) > 0) {
			if (! mystatus.equals("")) mystatus += "\r\n";
			status_date = getUpdated();
			status_user = getUpdatedBy();
			myicon = "updated";
			mystatus = mystatus + "" + text.display("content.status.updated") + ": " + status_date + "\r\n";
		} else {
			if (! mystatus.equals("")) mystatus += "\r\n";
			status_date = getPublished();
			status_user = getPublishedBy();
			myicon = "published";
			mystatus = mystatus + "" + text.display("content.status.published") + ": " + status_date + "\r\n";
		}
		if ((! getScheduledPublish().equals("")) && (getScheduledPublish().compareTo(now) > 0) && (! isLockedSchedule())) {
			if (! mystatus.equals("")) mystatus += "\r\n";
//			myicon += "-publish";
			mystatus = mystatus + "" + text.display("content.status.scheduled.publish") + " " + getScheduledPublish() + "\r\n";
		}
		if ((! getScheduledUnpublish().equals("")) && (getScheduledUnpublish().compareTo(now) > 0) && (! isLockedUnschedule())) {
			if (! mystatus.equals("")) mystatus += "\r\n";
//			myicon += "-unpublish";
			mystatus = mystatus + "" + text.display("content.status.scheduled.unpublish") + " " + getScheduledUnpublish() + "\r\n";
		}
		if (! getCheckedOut().equals("")) {
			if (! mystatus.equals("")) mystatus += "\r\n";
//			myicon += "-checkedout";
			mystatus = mystatus + "" + text.display("content.status.checkedout") + ": " + getCheckedOut() + "\r\n";
		}
                if (! getStatus().equals("")) {
			if (! mystatus.equals("")) mystatus += "\r\n";
//			myicon += "-status";
        	        mystatus = "" + getStatus() + "\r\n" + mystatus;
                }
                mystatus = "<img src=\"/" + text.display("adminpath") + "/content/status." + myicon + ".gif\" title=\"" + mystatus.trim().replaceAll("\r\n", " ") + "\">";
		return "" + mystatus;
	}



	public String select_options(DB db, String contentclass, String selected) {
		return select_options_prefix(db, contentclass, selected, "", "");
	}



	public String select_options_ids(DB db, String contentclass, String ids) {
		if ((ids == null) || (ids.equals(""))) {
			ids = "0";
		}
		return select_options_prefix(db, contentclass, ids, "", ids);
	}



	public String template_select_options(DB db, String selected) {
		return select_options_prefix(db, "template", selected, "", "");
	}



	public String stylesheet_select_options(DB db, String selected) {
		return select_options_prefix(db, "stylesheet", selected, "", "");
	}



	public String select_options_prefix(DB db, String contentclass, String selectedlist, String prefix, String idslist) {
		if (db == null) return "";
		StringBuffer options = new StringBuffer();
		String[] selected = selectedlist.split("\\|");
		String ids = idslist.replaceAll("\\|", ",");
		if (! select_options_cache.containsKey(contentclass+idslist)) {
			String SQLwhere = "";
			if (! ids.equals("")) {
				SQLwhere = " where id in (" + Common.SQL_clean(ids) + ") ";
			} else if ((contentclass != null) && (contentclass.equals("element"))) {
				SQLwhere = " where contentclass<>" + db.quote("page") + " and contentclass<>" + db.quote("image") + " and contentclass<>" + db.quote("file") + " and contentclass<>" + db.quote("link") + " and contentclass<>" + db.quote("product") + " and contentclass<>" + db.quote("template") + " and contentclass<>" + db.quote("stylesheet") + " and contentclass<>" + db.quote("script") + " ";
			} else if ((contentclass != null) && (! contentclass.equals(""))) {
				SQLwhere = " where contentclass=" + db.quote(Common.SQL_clean(contentclass)) + " ";
			}
			String SQL = "select id, title, version, device, usersegment, usertest from content " + SQLwhere + " order by title, version, device, usersegment, usertest, id";
			LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
			select_options_cache.put(contentclass+idslist, new LinkedList<HashMap<String,String>>());
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				((LinkedList<HashMap<String,String>>)select_options_cache.get(contentclass+idslist)).add(row);
			}
		}
		if (select_options_cache.containsKey(contentclass+idslist)) {
			Iterator<HashMap<String,String>> rows = ((LinkedList<HashMap<String,String>>)select_options_cache.get(contentclass+idslist)).iterator();
			while (rows.hasNext()) {
				HashMap<String,String> row = (HashMap<String,String>)rows.next();
				String myid = "" + row.get("id");
				String mytitle = "" + row.get("title");
				String myversion = "" + row.get("version");
				String mydevice = "" + row.get("device");
				String myusersegment = "" + row.get("usersegment");
				String myusertest = "" + row.get("usertest");
				options.append("<option value=\"");
				options.append(prefix);
				options.append(myid);
				options.append("\"");
				for (int j=0; j<selected.length; j++) {
					if ((selected[j] != null) && (selected[j].equals(myid))) {
						options.append(" selected");
					}
				}
				options.append(">");
				options.append(mytitle);
				if (! myversion.equals("")) {
					options.append(" (" + myversion + ")");
				}
				if (! mydevice.equals("")) {
					options.append(" (" + mydevice + ")");
				}
				if (! myusersegment.equals("")) {
					options.append(" (" + myusersegment + ")");
				}
				if (! myusertest.equals("")) {
					options.append(" (" + myusertest + ")");
				}
				options.append(" (" + myid + ")");
			}
		}
		return "" + options;
	}



	public String select_options_prefix_server_filename(DB db, String contentclass, String selected, String prefix) {
		if (db == null) return "";
		StringBuffer options = new StringBuffer();
		if (! select_options_cache.containsKey(contentclass)) {
			String SQLwhere = "";
			if ((contentclass != null) && (! contentclass.equals(""))) {
				SQLwhere = " where contentclass=" + db.quote(Common.SQL_clean(contentclass)) + " ";
			}
			String SQL = "select id, title, version, device, usersegment, usertest, server_filename from content " + SQLwhere + " order by title, version, device, usersegment, usertest, id";
			LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
			select_options_cache.put(contentclass, new LinkedList<HashMap<String,String>>());
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				((LinkedList<HashMap<String,String>>)select_options_cache.get(contentclass)).add(row);
			}
		}
		if (select_options_cache.containsKey(contentclass)) {
			Iterator<HashMap<String,String>> rows = ((LinkedList<HashMap<String,String>>)select_options_cache.get(contentclass)).iterator();
			while (rows.hasNext()) {
				HashMap<String,String> row = (HashMap<String,String>)rows.next();
				String myid = "" + row.get("id");
				String mytitle = "" + row.get("title");
				String mydevice = "" + row.get("device");
				String myusersegment = "" + row.get("usersegment");
				String myusertest = "" + row.get("usertest");
				String myversion = "" + row.get("version");
				String myserver_filename = "" + row.get("server_filename");
				options.append("<option value=\"");
				options.append(prefix);
				options.append(myserver_filename);
				options.append("\"");
				if ((selected != null) && (selected.equals(prefix + myserver_filename))) {
					options.append(" selected");
				}
				options.append(">");
				options.append(mytitle);
				if (! myversion.equals("")) {
					options.append(" (" + myversion + ")");
				}
				if (! mydevice.equals("")) {
					options.append(" (" + mydevice + ")");
				}
				if (! myusersegment.equals("")) {
					options.append(" (" + myusersegment + ")");
				}
				if (! myusertest.equals("")) {
					options.append(" (" + myusertest + ")");
				}
				options.append(" (" + myid + ")");
			}
		}
		return "" + options;
	}



	public String archive_select_options(DB db, String myid, String selected) {
		if (db == null) return "";
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		StringBuffer output = new StringBuffer();
		HashMap<String,String> myoptions = new HashMap<String,String>();
		if (! myid.equals("")) {
			String SQL = "select archiveid, updated, updated_by, published, published_by from content_archive where id=" + Common.integer(myid) + " order by archiveid desc";
			LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				String myarchiveid = "" + row.get("archiveid");
				String myupdated = "" + row.get("updated");
				String myupdated_by = "" + row.get("updated_by");
				String mypublished = "" + row.get("published");
				String mypublished_by = "" + row.get("published_by");
				if (mypublished_by.equals("")) mypublished_by = myupdated_by;
				String mykey = "";
				StringBuffer myoption = new StringBuffer();
				myoption.append("<option value=\"");
				myoption.append(myarchiveid);
				myoption.append("\"");
				if (selected.equals(myarchiveid)) {
					myoption.append(" selected");
				}
				myoption.append(">");
				if ((! mypublished.equals("")) && (mypublished.compareTo(now) > 0)) {
					myoption.append("" + mypublished + " - " + mypublished_by + " (" + myarchiveid + ")");
					mykey = mypublished + ":" + Common.numberformat(myarchiveid,0,10);
				} else {
					myoption.append("" + myupdated + " - " + myupdated_by + " (" + myarchiveid + ")");
					mykey = myupdated + ":" + Common.numberformat(myarchiveid,0,10);
				}
				myoptions.put(mykey, ""+myoption);
			}
			LinkedHashMap<String,String> mysortedoptions = Common.LinkedHashMapSortedByKey(myoptions, false);
			Iterator entries = mysortedoptions.keySet().iterator();
			while (entries.hasNext()) {
				String entry = "" + entries.next();
				output.append(myoptions.get(entry));
			}
		}
		return "" + output;
	}



	public String device_select_options(DB db, String selected) {
		Device mydevice =  new Device(text);
		return mydevice.select_options(db, selected);
	}



	public String usersegment_select_options(DB db, String selected) {
		Segment mysegment =  new Segment();
		return mysegment.select_options(db, selected);
	}



	public String usertest_select_options(DB db, String selected) {
		Usertest myusertest =  new Usertest();
		return myusertest.select_options(db, selected);
	}



	public String version_select_options(DB db, String selected) {
		return Common.select_options(db, "versions", "version", selected);
	}



	public String version_master_select_options(DB db, String contentclass, String selected) {
		if (db == null) return "";
		StringBuffer options = new StringBuffer();
		String SQL = "";
		if (contentclass.equals("element")) {
			SQL = "select id, title from content where (contentclass<>" + db.quote("template") + " and contentclass<>" + db.quote("stylesheet") + " and contentclass<>" + db.quote("script") + " and contentclass<>" + db.quote("page") + " and contentclass<>" + db.quote("file") + " and contentclass<>" + db.quote("image") + " and contentclass<>" + db.quote("link") + ") and " + db.is_blank("version") + " and " + db.is_blank("device") + " and " + db.is_blank("usersegment") + " and " + db.is_blank("usertest") + " order by title";
		} else if (! contentclass.equals("")) {
			SQL = "select id, title from content where contentclass=" + db.quote(Common.SQL_clean(contentclass)) + " and " + db.is_blank("version") + " and " + db.is_blank("device") + " and " + db.is_blank("usersegment") + " and " + db.is_blank("usertest") + " order by title";
		} else {
			SQL = "select id, title from content where " + db.is_blank("version") + " and " + db.is_blank("device") + " and " + db.is_blank("usersegment") + " and " + db.is_blank("usertest") + " order by title";
		}
		LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
		for (int i=0; i<rows.size(); i++) {
			HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
			String myid = "" + row.get("id");
			String mytitle = "" + row.get("title");
			options.append("<option value=\"");
			options.append(myid);
			options.append("\"");
			if (selected.equals(myid)) {
				options.append(" selected");
			}
			options.append(">");
			options.append(mytitle);
		}
		return "" + options;
	}



	public String productdelivery_select_options(String DOCUMENT_ROOT, String selected) {
		String options = "";
		LinkedHashMap<String,String> records_array = new LinkedHashMap<String,String>();
		if (Common.folderExists(DOCUMENT_ROOT + "/" + text.display("adminpath") + "/productdelivery/")) {
			String[] files = new File(DOCUMENT_ROOT + "/" + text.display("adminpath") + "/productdelivery/").list();
			for (int i=0; i<files.length; i++) {
				String option = files[i].replaceAll("\\.jsp$", "");
				if ((files[i].matches(".*\\.jsp$")) && (records_array.get(option) == null)) {
					records_array.put(option, option);
				}
			}
		}
		if (Common.folderExists(DOCUMENT_ROOT + "/" + text.display("adminpath") + "/productavailability/")) {
			String[] files = new File(DOCUMENT_ROOT + "/" + text.display("adminpath") + "/productavailability/").list();
			for (int i=0; i<files.length; i++) {
				String option = files[i].replaceAll("\\.jsp$", "");
				if ((files[i].matches(".*\\.jsp$")) && (records_array.get(option) == null)) {
					records_array.put(option, option);
				}
			}
		}
		records_array = Common.LinkedHashMapSortedByValue(records_array, true);
		Iterator entries = records_array.keySet().iterator();
		while (entries.hasNext()) {
			String entry = "" + entries.next();
			if (selected.equals(entry)) {
				options = options + "<option value=\"" + entry + "\" selected>" + entry;
			} else {
				options = options + "<option value=\"" + entry + "\">" + entry;
			}
		}
		return options;
	}



	public String contenttype_select_options(DB db, String selected) {
		if (contentclass.equals("file")) {
			return Filetype.select_options(db, selected);
		} else if (contentclass.equals("image")) {
			return Imagetype.select_options(db, selected);
		} else if (contentclass.equals("link")) {
			return Linktype.select_options(db, selected);
		} else if (contentclass.equals("product")) {
			return Producttype.select_options(db, selected);
		} else {
			return Contenttype.select_options(db, selected);
		}
	}



	public String contentgroup_select_options(DB db, String selected) {
		if (contentclass.equals("file")) {
			return Filegroup.select_options(db, selected);
		} else if (contentclass.equals("image")) {
			return Imagegroup.select_options(db, selected);
		} else if (contentclass.equals("link")) {
			return Linkgroup.select_options(db, selected);
		} else if (contentclass.equals("product")) {
			return Productgroup.select_options(db, selected);
		} else {
			return Contentgroup.select_options(db, selected);
		}
	}



	public String contentbundle_select_options(DB db, String selected) {
		return Common.select_options(db, "content", "contentbundle", selected);
	}



	public HashMap<String,String> elements(DB db) {
		return elements(db, null);
	}
	public HashMap<String,String> elements(DB db, Configuration myconfig) {
		HashMap<String,String> myelements = new HashMap<String,String>();
		if ((myconfig != null) && (! myconfig.getTemp("elements").equals(""))) {
			String[] elementnames = myconfig.getTemp("elements").trim().split(",");
			for (int j=0; j<elementnames.length; j++) {
				String elementname = elementnames[j];
				if (! elementname.equals("")) {
					myelements.put(elementname, "");
				}
			}
			return myelements;
		} else if (elements_cache != null) {
			return elements_cache;
		} else {
			if ((db == null) || db.isClosed()) return myelements;
			String SQL = "select element from elements order by element";
@SuppressWarnings("unchecked")
			LinkedHashMap<String,HashMap<String,String>> rows = (LinkedHashMap<String,HashMap<String,String>>)Cache.get(db, "elements", "element");
			if (rows == null) {
				rows = db.query_records(SQL);
				if (rows == null) rows = new LinkedHashMap<String,HashMap<String,String>>();
				if (rows != null) Cache.set(db, "elements", "element", rows);
			}
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				String elementname = "" + row.get("element");
				myelements.put(elementname, "");
			}
			elements_cache = myelements;
		}
		return myelements;
	}



	public void checkout(DB db, String username) {
		checkout(db, new Configuration(), username);
	}
	public void checkout(DB db, Configuration myconfig, String username) {
		if (db == null) return;
		String SQL = "";
		if (! username.equals("")) {
			if (getCheckoutPermissions(username, db, myconfig)) {
				SQL = "update content set checkedout=" + db.quote(Common.SQL_clean(username)) + " where id=" + Common.integer(id);
			} else {
				SQL = "update content set checkedout=" + db.quote(Common.SQL_clean(username)) + " where (" + db.is_blank("checkedout") + ") and id=" + Common.integer(id);
			}
		}
		if (! SQL.equals("")) {
			checkedout = username;
			db.execute(SQL);
			Cache.clear(db, "content");
		}
	}



	public void checkin(DB db, String username, String superadmin) {
		checkin(db, new Configuration(), username, superadmin);
	}
	public void checkin(DB db, Configuration myconfig, String username, String superadmin) {
		if (db == null) return;
		String SQL = "";
		if (username.equals(superadmin)) {
			SQL = "update content set checkedout='' where id=" + Common.integer(id);
		} else if (! username.equals("")) {
			if (getCheckoutPermissions(username, db, myconfig)) {
				SQL = "update content set checkedout='' where id=" + Common.integer(id);
			} else {
				SQL = "update content set checkedout='' where checkedout=" + db.quote(Common.SQL_clean(username)) + " and id=" + Common.integer(id);
			}
		}
		if (! SQL.equals("")) {
			checkedout = "";
			db.execute(SQL);
			Cache.clear(db, "content");
		}
	}



	public void update_usersegment(DB db, Configuration config, String value) {
		if (db == null) return;
		String SQL = "";
		String SQL2 = "";
		if (value != null) {
			SQL = "update content set usersegment=" + db.quote(Common.SQL_clean(value)) + " where id=" + Common.integer(id);
			SQL2 = "update content_public set usersegment=" + db.quote(Common.SQL_clean(value)) + " where id=" + Common.integer(id);
		}
		if (! SQL.equals("")) {
			usersegment = Common.SQL_clean(value);
			db.execute(SQL);
			db.execute(SQL2);
			Cache.clear(db, "content");
			Cache.clear(db, "content_public");
		}
	}



	public void update_usertest(DB db, Configuration config, String value) {
		if (db == null) return;
		String SQL = "";
		String SQL2 = "";
		if (value != null) {
			SQL = "update content set usertest=" + db.quote(Common.SQL_clean(value)) + " where id=" + Common.integer(id);
			SQL2 = "update content_public set usertest=" + db.quote(Common.SQL_clean(value)) + " where id=" + Common.integer(id);
		}
		if (! SQL.equals("")) {
			usertest = Common.SQL_clean(value);
			db.execute(SQL);
			db.execute(SQL2);
			Cache.clear(db, "content");
			Cache.clear(db, "content_public");
		}
	}



	public void update_heatmap(DB db, Configuration config, String log, String align) {
		if (db == null) return;
		String SQL = "";
		String SQL2 = "";
		if ((log != null) && (align != null)) {
			SQL = "update content set heatmap=" + db.quote(Common.SQL_clean(log)) + ", heatmapalign=" + db.quote(Common.SQL_clean(align)) + " where id=" + Common.integer(id);
			SQL2 = "update content_public set heatmap=" + db.quote(Common.SQL_clean(log)) + ", heatmapalign=" + db.quote(Common.SQL_clean(align)) + " where id=" + Common.integer(id);
		} else if (log != null) {
			SQL = "update content set heatmap=" + db.quote(Common.SQL_clean(log)) + " where id=" + Common.integer(id);
			SQL2 = "update content_public set heatmap=" + db.quote(Common.SQL_clean(log)) + " where id=" + Common.integer(id);
		} else if (align != null) {
			SQL = "update content set heatmapalign=" + db.quote(Common.SQL_clean(align)) + " where id=" + Common.integer(id);
			SQL2 = "update content_public set heatmapalign=" + db.quote(Common.SQL_clean(align)) + " where id=" + Common.integer(id);
		}
		if (! SQL.equals("")) {
			heatmap = Common.SQL_clean(log);
			db.execute(SQL);
			db.execute(SQL2);
			Cache.clear(db, "content");
			Cache.clear(db, "content_public");
		}
	}



	public void archive(DB db, Configuration config) {
		String save_checkedout = "" + checkedout;
		checkedout = "";
		create(db, config, "content_archive", "archiveid");
		checkedout = save_checkedout;
	}



	public void unpublish(DB db, String username, String timestamp) {
		if (db == null) return;
		if (! username.equals("")) {
			unpublished = timestamp;
			unpublished_by = username;
			String SQL = "update content set unpublished_by=" + db.quote(Common.SQL_clean(username)) + ", unpublished=" + db.quote(Common.SQL_clean(timestamp)) + " where id=" + Common.integer(id);
			db.execute(SQL);
			Cache.clear(db, "content");
		}
	}



	static public String elementTitle(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String myid) {
		Content temp_element = new Content(null);
		temp_element.preview_read(db, config, myid, session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups);
		return temp_element.getTitle();
	}



	public boolean validFilenameExtension(DB db, String filenameextension) {
		if (db == null) return false;
		boolean result = false;
		String SQL = "";
		HashMap<String,String> row = null;
		if (contentclass.equals("image")) {
			SQL = "select id from imageformats where filenameextension=" + db.quote(Common.SQL_clean(filenameextension));
			row = db.query_record(SQL);
		} else if (contentclass.equals("file")) {
			SQL = "select id from fileformats where filenameextension=" + db.quote(Common.SQL_clean(filenameextension));
			row = db.query_record(SQL);
		}
		if (row != null) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}



	public String currency_options(DB db, String selected) {
		if (db == null) return "";
		StringBuffer options = new StringBuffer();
		String SQL = "select id, symbol from currencies order by symbol";
		LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
		for (int i=0; i<rows.size(); i++) {
			HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
			String myid = "" + row.get("id");
			String mysymbol = "" + row.get("symbol");
			options.append("<option value=\"");
			options.append(myid);
			options.append("\"");
			if (selected.equals(myid)) {
				options.append(" selected");
			}
			options.append(">");
			options.append(mysymbol);
		}
		return "" + options;
	}



	public String currency_symbol(DB db, String myid) {
		if (db == null) return "";
		String mysymbol = "";
		if (! myid.equals("")) {
			String SQL = "select symbol from currencies where id=" + Common.integer(myid);
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				mysymbol = "" + row.get("symbol");
			}
		}
		return mysymbol;
	}



	public String list_titles(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String ids) {
		String output = "";
		if (! ids.equals("")) {
			records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "select id, locked, created, created_by, updated, published, unpublished, scheduled_publish, scheduled_unpublish, requested_publish, requested_unpublish, status, status_by, title, contentclass, contenttype, contentgroup, version, device, usersegment, usertest, checkedout, users_users, users_type, users_group, creators_users, creators_type, creators_group, editors_users, editors_type, editors_group, developers_users, developers_type, developers_group, publishers_users, publishers_type, publishers_group, administrators_users, administrators_type, administrators_group from content where id in (" + ids + ") order by title");
			boolean first = true;
			while (records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				if (! first) output += "<br>\r\n";
				if (user) output += title;
				first = false;
			}
		}
		return output;
	}



	public String list_links(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String ids) {
		String output = "";
		if (! ids.equals("")) {
			records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "select id, locked, created, created_by, updated, published, unpublished, scheduled_publish, scheduled_unpublish, requested_publish, requested_unpublish, status, status_by, title, contentclass, contenttype, contentgroup, version, device, usersegment, usertest, checkedout, users_users, users_type, users_group, creators_users, creators_type, creators_group, editors_users, editors_type, editors_group, developers_users, developers_type, developers_group, publishers_users, publishers_type, publishers_group, administrators_users, administrators_type, administrators_group from content where id in (" + ids + ") order by title");
			boolean first = true;
			while (records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				if (! first) output += "<br>\r\n";
				if (user) {
					if ((contentclass.equals("page")) || (contentclass.equals("image")) || (contentclass.equals("file")) || (contentclass.equals("link")) || (contentclass.equals("product")) || (contentclass.equals("template")) || (contentclass.equals("stylesheet")) || (contentclass.equals("script"))) {
						output += "<a href=\"/" + contentclass + ".jsp?id=" + id + "\">" + title + "</a>";
					} else {
						output += "<a href=\"/element.jsp?id=" + id + "\">" + title + "</a>";
					}
				}
				first = false;
			}
		}
		return output;
	}



	public HashMap<String,String> dependencies(DB db, Configuration myconfig, String contentid, String table, boolean insertdependencies) {
		HashMap<String,String> ids = new HashMap<String,String>();
		if ((! myconfig.get(db, "use_contentdependencies_tables").equals("no")) && (db != null) && (! table.equals("content_archive"))) {
			String[] myversions = version_master.split(",");
			for (int i=0; i<myversions.length; i++) {
				String myid = myversions[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|version_master|") < 0) {
					ids.put(myid, ids.get(myid) + "|version_master|");
				}
			}
			String[] mytemplates = template.split(",");
			for (int i=0; i<mytemplates.length; i++) {
				String myid = mytemplates[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|template|") < 0) {
					ids.put(myid, ids.get(myid) + "|template|");
				}
			}
			String[] mystylesheets = stylesheet.split(",");
			for (int i=0; i<mystylesheets.length; i++) {
				String myid = mystylesheets[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|stylesheet|") < 0) {
					ids.put(myid, ids.get(myid) + "|stylesheet|");
				}
			}
			String[] myscripts = scripts.split(",");
			for (int i=0; i<myscripts.length; i++) {
				String myid = myscripts[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|script|") < 0) {
					ids.put(myid, ids.get(myid) + "|script|");
				}
			}
			String[] myrelated = related.split(",");
			for (int i=0; i<myrelated.length; i++) {
				String myid = myrelated[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|related|") < 0) {
					ids.put(myid, ids.get(myid) + "|related|");
				}
			}
			String[] myimage1s = image1.split(",");
			for (int i=0; i<myimage1s.length; i++) {
				String myid = myimage1s[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|image1|") < 0) {
					ids.put(myid, ids.get(myid) + "|image1|");
				}
			}
			String[] myimage2s = image2.split(",");
			for (int i=0; i<myimage2s.length; i++) {
				String myid = myimage2s[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|image2|") < 0) {
					ids.put(myid, ids.get(myid) + "|image2|");
				}
			}
			String[] myimage3s = image3.split(",");
			for (int i=0; i<myimage3s.length; i++) {
				String myid = myimage3s[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|image3|") < 0) {
					ids.put(myid, ids.get(myid) + "|image3|");
				}
			}
			String[] myfile1s = file1.split(",");
			for (int i=0; i<myfile1s.length; i++) {
				String myid = myfile1s[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|file1|") < 0) {
					ids.put(myid, ids.get(myid) + "|file1|");
				}
			}
			String[] myfile2s = file2.split(",");
			for (int i=0; i<myfile2s.length; i++) {
				String myid = myfile2s[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|file2|") < 0) {
					ids.put(myid, ids.get(myid) + "|file2|");
				}
			}
			String[] myfile3s = file3.split(",");
			for (int i=0; i<myfile3s.length; i++) {
				String myid = myfile3s[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|file3|") < 0) {
					ids.put(myid, ids.get(myid) + "|file3|");
				}
			}
			String[] mylink1s = link1.split(",");
			for (int i=0; i<mylink1s.length; i++) {
				String myid = mylink1s[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|link1|") < 0) {
					ids.put(myid, ids.get(myid) + "|link1|");
				}
			}
			String[] mylink2s = link2.split(",");
			for (int i=0; i<mylink2s.length; i++) {
				String myid = mylink2s[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|link2|") < 0) {
					ids.put(myid, ids.get(myid) + "|link2|");
				}
			}
			String[] mylink3s = link3.split(",");
			for (int i=0; i<mylink3s.length; i++) {
				String myid = mylink3s[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|link3|") < 0) {
					ids.put(myid, ids.get(myid) + "|link3|");
				}
			}
			String[] mypagetops = page_top.split(",");
			for (int i=0; i<mypagetops.length; i++) {
				String myid = mypagetops[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|page_top|") < 0) {
					ids.put(myid, ids.get(myid) + "|page_top|");
				}
			}
			String[] mypageups = page_up.split(",");
			for (int i=0; i<mypageups.length; i++) {
				String myid = mypageups[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|page_up|") < 0) {
					ids.put(myid, ids.get(myid) + "|page_up|");
				}
			}
			String[] mypagefirsts = page_first.split(",");
			for (int i=0; i<mypagefirsts.length; i++) {
				String myid = mypagefirsts[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|page_first|") < 0) {
					ids.put(myid, ids.get(myid) + "|page_first|");
				}
			}
			String[] mypageprevs = page_previous.split(",");
			for (int i=0; i<mypageprevs.length; i++) {
				String myid = mypageprevs[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|page_previous|") < 0) {
					ids.put(myid, ids.get(myid) + "|page_previous|");
				}
			}
			String[] mypagenexts = page_next.split(",");
			for (int i=0; i<mypagenexts.length; i++) {
				String myid = mypagenexts[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|page_next|") < 0) {
					ids.put(myid, ids.get(myid) + "|page_next|");
				}
			}
			String[] mypagelasts = page_last.split(",");
			for (int i=0; i<mypagelasts.length; i++) {
				String myid = mypagelasts[i];
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|page_last|") < 0) {
					ids.put(myid, ids.get(myid) + "|page_last|");
				}
			}

			Pattern p = Pattern.compile("[/&\\?](id|template|stylesheet)=([0-9]+)[^0-9]");
			Matcher m;
			m = p.matcher("" + content);
			while (m.find()) {
				String myid = m.group(2);
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|content|") < 0) {
					ids.put(myid, ids.get(myid) + "|content|");
				}
			}
			m = p.matcher("" + summary);
			while (m.find()) {
				String myid = m.group(2);
				if (ids.get(myid) == null) {
					ids.put(myid, "");
				}
				if ((""+ids.get(myid)).indexOf("|summary|") < 0) {
					ids.put(myid, ids.get(myid) + "|summary|");
				}
			}

			p = Pattern.compile("(href|src)=(\"([^\"]+)\"|([^ >$]+)[ >$])");
			m = p.matcher("" + content);
			while (m.find()) {
				String myfilename = "";
				if (m.group(3) != null) {
					myfilename = m.group(3);
				} else if (m.group(4) != null) {
					myfilename = m.group(4);
				}
				myfilename = myfilename.replaceAll("^/", "");
				myfilename = myfilename.replaceAll("\\?.*$", "");
				myfilename = myfilename.replaceAll("#.*$", "");
				Content mycontentitem = new Content();
				mycontentitem.read(db, myconfig, myfilename, table, "id");
				String myid = mycontentitem.getId();
				if (! myid.equals("")) {
					if (ids.get(myid) == null) {
						ids.put(myid, "");
					}
					if ((""+ids.get(myid)).indexOf("|content|") < 0) {
						ids.put(myid, ids.get(myid) + "|content|");
					}
				} else {
					myfilename = "/" + myfilename;
					mycontentitem.read(db, myconfig, myfilename, table, "id");
					myid = mycontentitem.getId();
					if (! myid.equals("")) {
						if (ids.get(myid) == null) {
							ids.put(myid, "");
						}
						if ((""+ids.get(myid)).indexOf("|content|") < 0) {
							ids.put(myid, ids.get(myid) + "|content|");
						}
					}
				}
			}
			m = p.matcher("" + summary);
			while (m.find()) {
				String myfilename = "";
				if (m.group(3) != null) {
					myfilename = m.group(3);
				} else if (m.group(4) != null) {
					myfilename = m.group(4);
				}
				myfilename = myfilename.replaceAll("^/", "");
				myfilename = myfilename.replaceAll("\\?.*$", "");
				myfilename = myfilename.replaceAll("#.*$", "");
				Content mycontentitem = new Content();
				mycontentitem.read(db, myconfig, myfilename, table, "id");
				String myid = mycontentitem.getId();
				if (! myid.equals("")) {
					if (ids.get(myid) == null) {
						ids.put(myid, "");
					}
					if ((""+ids.get(myid)).indexOf("|summary|") < 0) {
						ids.put(myid, ids.get(myid) + "|summary|");
					}
				} else {
					myfilename = "/" + myfilename;
					mycontentitem.read(db, myconfig, myfilename, table, "id");
					myid = mycontentitem.getId();
					if (! myid.equals("")) {
						if (ids.get(myid) == null) {
							ids.put(myid, "");
						}
						if ((""+ids.get(myid)).indexOf("|summary|") < 0) {
							ids.put(myid, ids.get(myid) + "|summary|");
						}
					}
				}
			}

			if (table.equals("")) {
				table = "content";
			}

			db.delete(table + "_dependencies", "content_id", contentid);
			if (insertdependencies) {
				Iterator dependencyids = ids.keySet().iterator();
				while (dependencyids.hasNext()) {
					String dependencyid = "" + dependencyids.next();
					String dependencyvalue = "" + ids.get(dependencyid);
					if ((dependencyid != null) && (! dependencyid.equals(""))) {
						HashMap<String,String> mydata = new HashMap<String,String>();
						mydata.put("content_id", contentid);
						mydata.put("dependency", dependencyvalue);
						mydata.put("dependency_id", dependencyid);
						db.insert(table + "_dependencies", mydata);
					}
				}
			}
		}
		return ids;
	}



	public HashMap<String,String> dependents(Configuration myconfig, DB db) {
		HashMap<String,String> ids = new HashMap<String,String>();
		if (db == null) return ids;
		if ((! id.equals("")) && (! id.equals("0")) && (! id.equals("-1"))) {
			String SQL = "";
			LinkedHashMap<String,HashMap<String,String>> rows;

                       	ids = dependents_contentitems(myconfig, db, "content", id, ids);
                       	ids = dependents_contentitems(myconfig, db, "content_public", id, ids);

			SQL = "select contentgroup from contentgroups where (template=" + db.quote("" + Common.integer(id)) + ") or (stylesheet=" + db.quote(Common.integer(id)) + " or stylesheet like " + db.quote(Common.integer(id) + ",%") + " or stylesheet like " + db.quote("%," + Common.integer(id) + ",%") + " or stylesheet like " + db.quote("%," + Common.integer(id)) + ") order by contentgroup";
			rows = db.query_records(SQL);
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				ids.put("0", ids.get("0") + "|contentgroup_" + row.get("contentgroup") + "|");
			}
			SQL = "select contenttype from contenttypes where (template=" + db.quote("" + Common.integer(id)) + ") or (stylesheet=" + db.quote("" + Common.integer(id)) + " or stylesheet like " + db.quote("" + Common.integer(id) + ",%") + " or stylesheet like " + db.quote("%," + Common.integer(id) + ",%") + " or stylesheet like " + db.quote("%," + Common.integer(id)) + ") order by contenttype";
			rows = db.query_records(SQL);
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				ids.put("0", ids.get("0") + "|contenttype_" + row.get("contenttype") + "|");
			}
			SQL = "select productgroup from productgroups where (template=" + db.quote("" + Common.integer(id)) + ") or (stylesheet=" + db.quote("" + Common.integer(id)) + " or stylesheet like " + db.quote("" + Common.integer(id) + ",%") + " or stylesheet like " + db.quote("%," + Common.integer(id) + ",%") + " or stylesheet like " + db.quote("%," + Common.integer(id)) + ") order by productgroup";
			rows = db.query_records(SQL);
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				ids.put("0", ids.get("0") + "|productgroup_" + row.get("productgroup") + "|");
			}
			SQL = "select producttype from producttypes where (template=" + db.quote("" + Common.integer(id)) + ") or (stylesheet=" + db.quote("" + Common.integer(id)) + " or stylesheet like " + db.quote("" + Common.integer(id) + ",%") + " or stylesheet like " + db.quote("%," + Common.integer(id) + ",%") + " or stylesheet like " + db.quote("%," + Common.integer(id)) + ") order by producttype";
			rows = db.query_records(SQL);
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				ids.put("0", ids.get("0") + "|producttype_" + row.get("producttype") + "|");
			}

			SQL = "select domain from websites where (default_page=" + db.quote("" + Common.integer(id)) + ")";
			rows = db.query_records(SQL);
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				ids.put("0", ids.get("0") + "|website_" + row.get("domain") + "|");
			}
			SQL = "select client_address from hosting where ((scheduled_publish_email=" + db.quote("" + Common.integer(id)) + ") or (scheduled_notify_email=" + db.quote("" + Common.integer(id)) + ") or (scheduled_unpublish_email=" + db.quote("" + Common.integer(id)) + "))";
			rows = db.query_records(SQL);
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				ids.put("0", ids.get("0") + "|hosting_" + row.get("client_address") + "|");
			}
			SQL = "select username from users where ((scheduled_publish_email=" + db.quote("" + Common.integer(id)) + ") or (scheduled_notify_email=" + db.quote("" + Common.integer(id)) + ") or (scheduled_unpublish_email=" + db.quote("" + Common.integer(id)) + "))";
			rows = db.query_records(SQL);
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				ids.put("0", ids.get("0") + "|user_" + row.get("username") + "|");
			}
			SQL = "select usergroup from usergroups where (login_page=" + db.quote("" + Common.integer(id)) + ")";
			rows = db.query_records(SQL);
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				ids.put("0", ids.get("0") + "|usergroup_" + row.get("usergroup") + "|");
			}
			SQL = "select usertype from usertypes where (login_page=" + db.quote("" + Common.integer(id)) + ")";
			rows = db.query_records(SQL);
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				ids.put("0", ids.get("0") + "|usertype_" + row.get("usertype") + "|");
			}
			if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQL = "select configname from config where ((configname like " + db.quote("default_%") + ") or (configname like " + db.quote("retrieve_password_%") + ")) and (substring(configvalue,1,250)=" + db.quote("" + Common.integer(id)) + ")";
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQL = "select configname from config where ((configname like " + db.quote("default_%") + ") or (configname like " + db.quote("retrieve_password_%") + ")) and (to_char(configvalue)=" + db.quote("" + Common.integer(id)) + ")";
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQL = "select configname from config where ((configname like " + db.quote("default_%") + ") or (configname like " + db.quote("retrieve_password_%") + ")) and (varchar(configvalue,250)=" + db.quote("" + Common.integer(id)) + ")";
			} else {
				SQL = "select configname from config where ((configname like " + db.quote("default_%") + ") or (configname like " + db.quote("retrieve_password_%") + ")) and (configvalue=" + db.quote("" + Common.integer(id)) + ")";
			}
			rows = db.query_records(SQL);
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				ids.put("0", ids.get("0") + "|" + row.get("configname") + "|");
			}
		}
		return ids;
	}



	public HashMap<String,String> dependents_contentitems(Configuration myconfig, DB db, String table, String myid, HashMap<String,String> ids) {
		if (db == null) return ids;
		if ((! myid.equals("")) && (! myid.equals("0")) && (! myid.equals("-1"))) {
			LinkedHashMap<String,HashMap<String,String>> rows = null;
			String SQL = "";
// MYSQL may be extremely slow in handling "or" queries - fx. two queries each returning empty sets in 0.00sec may take 5+sec when combined
//			SQL = "select id,contentclass,template,version_master,page_up,page_top,page_next,page_previous,page_last,page_first,image1,image2,image3,file1,file2,file3,link1,link2,link3 from " + table + " where id<>" + Common.integer(myid) + " and ((template=" + db.quote("" + Common.integer(myid)) + ") or (version_master=" + db.quote("" + Common.integer(myid)) + ") or (page_up=" + db.quote("" + Common.integer(myid)) + ") or (page_top=" + db.quote("" + Common.integer(myid)) + ") or (page_next=" + db.quote("" + Common.integer(myid)) + ") or (page_previous=" + db.quote("" + Common.integer(myid)) + ") or (page_last=" + db.quote("" + Common.integer(myid)) + ") or (page_first=" + db.quote("" + Common.integer(myid)) + ") or (image1=" + db.quote("" + Common.integer(myid)) + ") or (image2=" + db.quote("" + Common.integer(myid)) + ") or (image3=" + db.quote("" + Common.integer(myid)) + ") or (file1=" + db.quote("" + Common.integer(myid)) + ") or (file2=" + db.quote("" + Common.integer(myid)) + ") or (file3=" + db.quote("" + Common.integer(myid)) + ") or (link1=" + db.quote("" + Common.integer(myid)) + ") or (link2=" + db.quote("" + Common.integer(myid)) + ") or (link3=" + db.quote("" + Common.integer(myid)) + "))";
			if (! myconfig.get(db, "use_contentdependencies_tables").equals("no")) {
				SQL = "select * from " + table + "_dependencies where dependency_id=" + Common.integer(myid);
				LinkedHashMap<String,HashMap<String,String>> rows2 = db.query_records(SQL);
				for (int i=0; i<rows2.size(); i++) {
					HashMap<String,String> row = (HashMap<String,String>)rows2.get("" + i);
					if ((""+row.get("dependency")).indexOf("|version_master|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|version_master|");
					}
					if ((""+row.get("dependency")).indexOf("|template|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|template|");
					}
					if ((""+row.get("dependency")).indexOf("|stylesheet|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|stylesheet|");
					}
					if ((""+row.get("dependency")).indexOf("|script|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|script|");
					}
					if ((""+row.get("dependency")).indexOf("|related|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|related|");
					}
					if ((""+row.get("dependency")).indexOf("|image1|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|image1|");
					}
					if ((""+row.get("dependency")).indexOf("|image2|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|image2|");
					}
					if ((""+row.get("dependency")).indexOf("|image3|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|image3|");
					}
					if ((""+row.get("dependency")).indexOf("|file1|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|file1|");
					}
					if ((""+row.get("dependency")).indexOf("|file2|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|file2|");
					}
					if ((""+row.get("dependency")).indexOf("|file3|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|file3|");
					}
					if ((""+row.get("dependency")).indexOf("|link1|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|link1|");
					}
					if ((""+row.get("dependency")).indexOf("|link2|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|link2|");
					}
					if ((""+row.get("dependency")).indexOf("|link3|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|link3|");
					}
					if ((""+row.get("dependency")).indexOf("|page_top|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|page_top|");
					}
					if ((""+row.get("dependency")).indexOf("|page_up|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|page_up|");
					}
					if ((""+row.get("dependency")).indexOf("|page_first|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|page_first|");
					}
					if ((""+row.get("dependency")).indexOf("|page_previous|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|page_previous|");
					}
					if ((""+row.get("dependency")).indexOf("|page_next|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|page_next|");
					}
					if ((""+row.get("dependency")).indexOf("|page_last|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|page_last|");
					}
					if ((""+row.get("dependency")).indexOf("|content|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|content|");
					}
					if ((""+row.get("dependency")).indexOf("|summary|") >= 0) {
						ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|summary|");
					}
				}
			} else if (myconfig.get(db, "use_contentdependencies_tables").equals("no")) {
				SQL = "select id,contentclass,template from " + table + " where id<>" + Common.integer(myid) + " and (template='" + Common.integer(myid) + "')";
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					if (row.get("template").equals(myid)) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|template|");
					}
				}
				SQL = "select id,contentclass,version_master from " + table + " where id<>" + Common.integer(myid) + " and (version_master='" + Common.integer(myid) + "')";
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					if (row.get("version_master").equals(myid)) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|version_master|");
					}
				}
				SQL = "select id,contentclass,page_up from " + table + " where id<>" + Common.integer(myid) + " and (page_up='" + Common.integer(myid) + "')";
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					if (row.get("page_up").equals(myid)) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|page_up|");
					}
				}
				SQL = "select id,contentclass,page_top from " + table + " where id<>" + Common.integer(myid) + " and (page_top='" + Common.integer(myid) + "')";
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					if (row.get("page_top").equals(myid)) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|page_top|");
					}
				}
				SQL = "select id,contentclass,page_next from " + table + " where id<>" + Common.integer(myid) + " and (page_next='" + Common.integer(myid) + "')";
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					if (row.get("page_next").equals(myid)) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|page_next|");
					}
				}
				SQL = "select id,contentclass,page_previous from " + table + " where id<>" + Common.integer(myid) + " and (page_previous='" + Common.integer(myid) + "')";
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					if (row.get("page_previous").equals(myid)) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|page_previous|");
					}
				}
				SQL = "select id,contentclass,page_last from " + table + " where id<>" + Common.integer(myid) + " and (page_last='" + Common.integer(myid) + "')";
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					if (row.get("page_last").equals(myid)) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|page_last|");
					}
				}
				SQL = "select id,contentclass,page_first from " + table + " where id<>" + Common.integer(myid) + " and (page_first='" + Common.integer(myid) + "')";
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					if (row.get("page_first").equals(myid)) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|page_first|");
					}
				}
				SQL = "select id,contentclass,image1 from " + table + " where id<>" + Common.integer(myid) + " and (image1='" + Common.integer(myid) + "')";
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					if (row.get("image1").equals(myid)) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|image1|");
					}
				}
				SQL = "select id,contentclass,image2 from " + table + " where id<>" + Common.integer(myid) + " and (image2='" + Common.integer(myid) + "')";
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					if (row.get("image2").equals(myid)) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|image2|");
					}
				}
				SQL = "select id,contentclass,image3 from " + table + " where id<>" + Common.integer(myid) + " and (image3='" + Common.integer(myid) + "')";
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					if (row.get("image3").equals(myid)) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|image3|");
					}
				}
				SQL = "select id,contentclass,file1 from " + table + " where id<>" + Common.integer(myid) + " and (file1='" + Common.integer(myid) + "')";
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					if (row.get("file1").equals(myid)) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|file1|");
					}
				}
				SQL = "select id,contentclass,file2 from " + table + " where id<>" + Common.integer(myid) + " and (file2='" + Common.integer(myid) + "')";
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					if (row.get("file2").equals(myid)) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|file2|");
					}
				}
				SQL = "select id,contentclass,file3 from " + table + " where id<>" + Common.integer(myid) + " and (file3='" + Common.integer(myid) + "')";
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					if (row.get("file3").equals(myid)) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|file3|");
					}
				}
				SQL = "select id,contentclass,link1 from " + table + " where id<>" + Common.integer(myid) + " and (link1='" + Common.integer(myid) + "')";
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					if (row.get("link1").equals(myid)) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|link1|");
					}
				}
				SQL = "select id,contentclass,link2 from " + table + " where id<>" + Common.integer(myid) + " and (link2='" + Common.integer(myid) + "')";
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					if (row.get("link2").equals(myid)) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|link2|");
					}
				}
				SQL = "select id,contentclass,link3 from " + table + " where id<>" + Common.integer(myid) + " and (link3='" + Common.integer(myid) + "')";
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					if (row.get("link3").equals(myid)) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|link3|");
					}
				}

				if (db.db_type(db.getDatabase()).equals("oracle")) {
					SQL = "select id,contentclass,stylesheet,scripts,related from " + table + " where id<>" + Common.integer(myid) + " and ((stylesheet='" + Common.integer(myid) + "' or stylesheet like '" + Common.integer(myid) + ",%' or stylesheet like '%," + Common.integer(myid) + ",%' or stylesheet like '%," + Common.integer(myid) + "') or (scripts='" + Common.integer(myid) + "' or scripts like '" + Common.integer(myid) + ",%' or scripts like '%," + Common.integer(myid) + ",%' or scripts like '%," + Common.integer(myid) + "') or (to_char(related)='" + Common.integer(myid) + "' or to_char(related) like '" + Common.integer(myid) + ",%' or to_char(related) like '%," + Common.integer(myid) + ",%' or to_char(related) like '%," + Common.integer(myid) + "'))";
				} else {
					SQL = "select id,contentclass,stylesheet,scripts,related from " + table + " where id<>" + Common.integer(myid) + " and ((stylesheet='" + Common.integer(myid) + "' or stylesheet like '" + Common.integer(myid) + ",%' or stylesheet like '%," + Common.integer(myid) + ",%' or stylesheet like '%," + Common.integer(myid) + "') or (scripts='" + Common.integer(myid) + "' or scripts like '" + Common.integer(myid) + ",%' or scripts like '%," + Common.integer(myid) + ",%' or scripts like '%," + Common.integer(myid) + "') or (related='" + Common.integer(myid) + "' or related like '" + Common.integer(myid) + ",%' or related like '%," + Common.integer(myid) + ",%' or related like '%," + Common.integer(myid) + "'))";
				}
				rows = db.query_records(SQL);
				for (int j=0; j<rows.size(); j++) {
					HashMap<String,String> row = (HashMap<String,String>)rows.get("" + j);
					if ((row.get("contentclass").equals("page")) || (row.get("contentclass").equals("product")) || (row.get("contentclass").equals("image")) || (row.get("contentclass").equals("file")) || (row.get("contentclass").equals("link")) || (row.get("contentclass").equals("template")) || (row.get("contentclass").equals("stylesheet")) || (row.get("contentclass").equals("script"))) {
						if ((""+ids.get(row.get("id"))).indexOf("|" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + row.get("contentclass") + "|");
						}
					} else {
						if ((""+ids.get(row.get("id"))).indexOf("|" + "element_" + row.get("contentclass") + "|") < 0) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|" + "element_" + row.get("contentclass") + "|");
						}
					}
					String[] mystylesheets = (""+row.get("stylesheet")).split(",");
					for (int i=0; i<mystylesheets.length; i++) {
						if (mystylesheets[i].equals(myid)) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|stylesheet|");
						}
					}
					String[] myscripts = (""+row.get("scripts")).split(",");
					for (int i=0; i<myscripts.length; i++) {
						if (myscripts[i].equals(myid)) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|script|");
						}
					}
					String[] myrelated = (""+row.get("related")).split(",");
					for (int i=0; i<myrelated.length; i++) {
						if (myrelated[i].equals(myid)) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|related|");
						}
					}
				}

				Pattern p = Pattern.compile("[/&\\?](id|template|stylesheet)=\\Q" + myid + "\\E[^0-9]");
				Matcher m;
				SQL = "select id,content from " + table + " " + Common.SQLwhere_contains_or_or(db, myconfig, "", "content", "id=" + Common.integer(myid), "content", "template=" + Common.integer(myid), "content", "stylesheet=" + Common.integer(myid));
				LinkedHashMap<String,HashMap<String,String>> rows2 = db.query_records(SQL);
				for (int i=0; i<rows2.size(); i++) {
					HashMap<String,String> row = (HashMap<String,String>)rows2.get("" + i);
					m = p.matcher("" + row.get("content"));
					if (m.find()) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|content|");
					}
				}
				SQL = "select id,summary from " + table + " " + Common.SQLwhere_contains_or_or(db, myconfig, "", "summary", "id=" + Common.integer(myid), "summary", "template=" + Common.integer(myid), "summary", "stylesheet=" + Common.integer(myid));
				rows2 = db.query_records(SQL);
				for (int i=0; i<rows2.size(); i++) {
					HashMap<String,String> row = (HashMap<String,String>)rows2.get("" + i);
					m = p.matcher("" + row.get("summary"));
					if (m.find()) {
						ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|summary|");
					}
				}

				if (! server_filename.equals("")) {
					p = Pattern.compile(" ((href=\"/\\Q" + server_filename + "\\E\")|(href=/\\Q" + server_filename + "\\E[ >]))");
					SQL = "select id,content from " + table + " " + Common.SQLwhere_contains_or(db, myconfig, "", "content", "href=/" + server_filename, "content", "href=\"/" + server_filename + "\"");
					rows2 = db.query_records(SQL);
					for (int i=0; i<rows2.size(); i++) {
						HashMap<String,String> row = (HashMap<String,String>)rows2.get("" + i);
						m = p.matcher("" + row.get("content"));
						if (m.find()) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|content|");
						}
					}
					SQL = "select id,summary from " + table + " " + Common.SQLwhere_contains_or(db, myconfig, "", "summary", "href=/" + server_filename, "summary", "href=\"/" + server_filename + "\"");
					rows2 = db.query_records(SQL);
					for (int i=0; i<rows2.size(); i++) {
						HashMap<String,String> row = (HashMap<String,String>)rows2.get("" + i);
						m = p.matcher("" + row.get("summary"));
						if (m.find()) {
							ids.put(row.get("id"), "" + ids.get(row.get("id")) + "|summary|");
						}
					}
				}
			}

			SQL = "select content_id,element from " + table + "_elements where (element_id=" + Common.integer(myid) + ")";
			LinkedHashMap<String,HashMap<String,String>> rows3 = db.query_records(SQL);
			for (int i=0; i<rows3.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows3.get("" + i);
//				ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|element_" + row.get("element") + "|");
				ids.put(row.get("content_id"), "" + ids.get(row.get("content_id")) + "|template|");
			}
		}
		return ids;
	}



	public String administratorsSQLfromwhere(DB db, boolean administrators, boolean publishers, boolean editors, boolean creators, boolean developers, boolean users, boolean broad_search) {
		return administratorsSQLfromwhere(db, administrators, publishers, editors, creators, developers, users, broad_search, true);
	}
	public String administratorsSQLfromwhere(DB db, boolean administrators, boolean publishers, boolean editors, boolean creators, boolean developers, boolean users, boolean broad_search, boolean subgroupstypes) {
		String SQLwhere = "";
		if ((id != null) && (! id.equals("")) && (! id.equals("0")) && (! id.equals("-1"))) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "users.userclass", "administrator");
			SQLwhere += " and ((1=0)";

			boolean restricted_users = false;
			boolean restricted_developers = false;
			boolean restricted_creators = false;
			boolean restricted_editors = false;
			boolean restricted_publishers = false;
			boolean restricted_administrators = false;

			String SQLwhereUser = "";
			if (users) {
				if ((getUsersType().equals("*")) || (getUsersType().equals("0"))) {
					// ok
				} else if (! getUsersType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getUsersType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getUsersType(), getUsersType());
					if (! SQLwhereUser.equals("")) SQLwhereUser += " and ";
					SQLwhereUser += "(";
					SQLwhereUser += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereUser += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereUser += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereUser += ")";
				}
				if ((getUsersGroup().equals("*")) || (getUsersGroup().equals("0"))) {
					// ok
				} else if (! getUsersGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getUsersGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getUsersGroup(), getUsersGroup());
					if (! SQLwhereUser.equals("")) SQLwhereUser += " and ";
					SQLwhereUser += "(";
					SQLwhereUser += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereUser += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereUser += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereUser += ")";
				}
				if (! getUsersUsers().equals("")) {
					String SQLwhereUserUsers = "users.id in (" + Common.SQL_list_values(getUsersUsers().split(",")) + ")";
					if (! SQLwhereUser.equals("")) {
						SQLwhereUser = "(" + SQLwhereUser + ") or (" + SQLwhereUserUsers + ")";
					} else {
						SQLwhereUser = SQLwhereUserUsers;
					}
				}
				if (! SQLwhereUser.equals("")) {
					SQLwhere += " or (" + SQLwhereUser + ")";
					restricted_users = true;
				}
			}

			String SQLwhereDeveloper = "";
			if (developers) {
				if ((getDevelopersType().equals("*")) || (getDevelopersType().equals("0"))) {
					// ok
				} else if (! getDevelopersType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getDevelopersType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getDevelopersType(), getDevelopersType());
					if (! SQLwhereDeveloper.equals("")) SQLwhereDeveloper += " and ";
					SQLwhereDeveloper += "(";
					SQLwhereDeveloper += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereDeveloper += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereDeveloper += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereDeveloper += ")";
				}
				if ((getDevelopersGroup().equals("*")) || (getDevelopersGroup().equals("0"))) {
					// ok
				} else if (! getDevelopersGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getDevelopersGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getDevelopersGroup(), getDevelopersGroup());
					if (! SQLwhereDeveloper.equals("")) SQLwhereDeveloper += " and ";
					SQLwhereDeveloper += "(";
					SQLwhereDeveloper += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereDeveloper += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereDeveloper += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereDeveloper += ")";
				}
				if (! getDevelopersUsers().equals("")) {
					String SQLwhereDeveloperUsers = "users.id in (" + Common.SQL_list_values(getDevelopersUsers().split(",")) + ")";
					if (! SQLwhereDeveloper.equals("")) {
						SQLwhereDeveloper = "(" + SQLwhereDeveloper + ") or (" + SQLwhereDeveloperUsers + ")";
					} else {
						SQLwhereDeveloper = SQLwhereDeveloperUsers;
					}
				}
				if (! SQLwhereDeveloper.equals("")) {
					if (! SQLwhereDeveloper.equals(SQLwhereUser)) {
						SQLwhere += " or (" + SQLwhereDeveloper + ")";
					}
					restricted_developers = true;
				}
			}

			String SQLwhereCreator = "";
			if (creators) {
				if ((getCreatorsType().equals("*")) || (getCreatorsType().equals("0"))) {
					// ok
				} else if (! getCreatorsType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getCreatorsType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getCreatorsType(), getCreatorsType());
					if (! SQLwhereCreator.equals("")) SQLwhereCreator += " and ";
					SQLwhereCreator += "(";
					SQLwhereCreator += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereCreator += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereCreator += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereCreator += ")";
				}
				if ((getCreatorsGroup().equals("*")) || (getCreatorsGroup().equals("0"))) {
					// ok
				} else if (! getCreatorsGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getCreatorsGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getCreatorsGroup(), getCreatorsGroup());
					if (! SQLwhereCreator.equals("")) SQLwhereCreator += " and ";
					SQLwhereCreator += "(";
					SQLwhereCreator += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereCreator += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereCreator += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereCreator += ")";
				}
				if (! getCreatorsUsers().equals("")) {
					String SQLwhereCreatorUsers = "users.id in (" + Common.SQL_list_values(getCreatorsUsers().split(",")) + ")";
					if (! SQLwhereCreator.equals("")) {
						SQLwhereCreator = "(" + SQLwhereCreator + ") or (" + SQLwhereCreatorUsers + ")";
					} else {
						SQLwhereCreator = SQLwhereCreatorUsers;
					}
				}
				if (! SQLwhereCreator.equals("")) {
					if ((! SQLwhereCreator.equals(SQLwhereDeveloper)) && (! SQLwhereCreator.equals(SQLwhereUser))) {
						SQLwhere += " or (" + SQLwhereCreator + ")";
					}
					restricted_creators = true;
				}
			}

			String SQLwhereEditor = "";
			if (editors) {
				if ((getEditorsType().equals("*")) || (getEditorsType().equals("0"))) {
					// ok
				} else if (! getEditorsType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getEditorsType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getEditorsType(), getEditorsType());
					if (! SQLwhereEditor.equals("")) SQLwhereEditor += " and ";
					SQLwhereEditor += "(";
					SQLwhereEditor += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereEditor += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereEditor += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereEditor += ")";
				}
				if ((getEditorsGroup().equals("*")) || (getEditorsGroup().equals("0"))) {
					// ok
				} else if (! getEditorsGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getEditorsGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getEditorsGroup(), getEditorsGroup());
					if (! SQLwhereEditor.equals("")) SQLwhereEditor += " and ";
					SQLwhereEditor += "(";
					SQLwhereEditor += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereEditor += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereEditor += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereEditor += ")";
				}
				if (! getEditorsUsers().equals("")) {
					String SQLwhereEditorUsers = "users.id in (" + Common.SQL_list_values(getEditorsUsers().split(",")) + ")";
					if (! SQLwhereEditor.equals("")) {
						SQLwhereEditor = "(" + SQLwhereEditor + ") or (" + SQLwhereEditorUsers + ")";
					} else {
						SQLwhereEditor = SQLwhereEditorUsers;
					}
				}
				if (! SQLwhereEditor.equals("")) {
					if ((! SQLwhereEditor.equals(SQLwhereCreator)) && (! SQLwhereEditor.equals(SQLwhereDeveloper)) && (! SQLwhereEditor.equals(SQLwhereUser))) {
						SQLwhere += " or (" + SQLwhereEditor + ")";
					}
					restricted_editors = true;
				}
			}

			String SQLwherePublisher = "";
			if (publishers) {
				if ((getPublishersType().equals("*")) || (getPublishersType().equals("0"))) {
					// ok
				} else if (! getPublishersType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getPublishersType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getPublishersType(), getPublishersType());
					if (! SQLwherePublisher.equals("")) SQLwherePublisher += " and ";
					SQLwherePublisher += "(";
					SQLwherePublisher += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwherePublisher += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwherePublisher += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwherePublisher += ")";
				}
				if ((getPublishersGroup().equals("*")) || (getPublishersGroup().equals("0"))) {
					// ok
				} else if (! getPublishersGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getPublishersGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getPublishersGroup(), getPublishersGroup());
					if (! SQLwherePublisher.equals("")) SQLwherePublisher += " and ";
					SQLwherePublisher += "(";
					SQLwherePublisher += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwherePublisher += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwherePublisher += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwherePublisher += ")";
				}
				if (! getPublishersUsers().equals("")) {
					String SQLwherePublisherUsers = "users.id in (" + Common.SQL_list_values(getPublishersUsers().split(",")) + ")";
					if (! SQLwherePublisher.equals("")) {
						SQLwherePublisher = "(" + SQLwherePublisher + ") or (" + SQLwherePublisherUsers + ")";
					} else {
						SQLwherePublisher = SQLwherePublisherUsers;
					}
				}
				if (! SQLwherePublisher.equals("")) {
					if ((! SQLwherePublisher.equals(SQLwhereEditor)) && (! SQLwherePublisher.equals(SQLwhereCreator)) && (! SQLwherePublisher.equals(SQLwhereDeveloper)) && (! SQLwherePublisher.equals(SQLwhereUser))) {
						SQLwhere += " or (" + SQLwherePublisher + ")";
					}
					restricted_publishers = true;
				}
			}

			String SQLwhereAdministrator = "";
			if (administrators) {
				if ((getAdministratorsType().equals("*")) || (getAdministratorsType().equals("0"))) {
					// ok
				} else if (! getAdministratorsType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getAdministratorsType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getAdministratorsType(), getAdministratorsType());
					if (! SQLwhereAdministrator.equals("")) SQLwhereAdministrator += " and ";
					SQLwhereAdministrator += "(";
					SQLwhereAdministrator += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereAdministrator += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereAdministrator += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereAdministrator += ")";
				}
				if ((getAdministratorsGroup().equals("*")) || (getAdministratorsGroup().equals("0"))) {
					// ok
				} else if (! getAdministratorsGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getAdministratorsGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getAdministratorsGroup(), getAdministratorsGroup());
					if (! SQLwhereAdministrator.equals("")) SQLwhereAdministrator += " and ";
					SQLwhereAdministrator += "(";
					SQLwhereAdministrator += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereAdministrator += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereAdministrator += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereAdministrator += ")";
				}
				if (! getAdministratorsUsers().equals("")) {
					String SQLwhereAdministratorUsers = "users.id in (" + Common.SQL_list_values(getAdministratorsUsers().split(",")) + ")";
					if (! SQLwhereAdministrator.equals("")) {
						SQLwhereAdministrator = "(" + SQLwhereAdministrator + ") or (" + SQLwhereAdministratorUsers + ")";
					} else {
						SQLwhereAdministrator = SQLwhereAdministratorUsers;
					}
				}
				if (! SQLwhereAdministrator.equals("")) {
					if ((! SQLwhereAdministrator.equals(SQLwherePublisher)) && (! SQLwhereAdministrator.equals(SQLwhereEditor)) && (! SQLwhereAdministrator.equals(SQLwhereCreator)) && (! SQLwhereAdministrator.equals(SQLwhereDeveloper)) && (! SQLwhereAdministrator.equals(SQLwhereUser))) {
						SQLwhere += " or (" + SQLwhereAdministrator + ")";
					}
					restricted_administrators = true;
				}
			}

			String SQLwhereContentgroupUser = "";
			if (users) {
				if ((getContentgroupUsersType().equals("*")) || (getContentgroupUsersType().equals("0"))) {
					// ok
				} else if (! getContentgroupUsersType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getContentgroupUsersType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getContentgroupUsersType(), getContentgroupUsersType());
					if (! SQLwhereContentgroupUser.equals("")) SQLwhereContentgroupUser += " and ";
					SQLwhereContentgroupUser += "(";
					SQLwhereContentgroupUser += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContentgroupUser += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContentgroupUser += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContentgroupUser += ")";
				}
				if ((getContentgroupUsersGroup().equals("*")) || (getContentgroupUsersGroup().equals("0"))) {
					// ok
				} else if (! getContentgroupUsersGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getContentgroupUsersGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getContentgroupUsersGroup(), getContentgroupUsersGroup());
					if (! SQLwhereContentgroupUser.equals("")) SQLwhereContentgroupUser += " and ";
					SQLwhereContentgroupUser += "(";
					SQLwhereContentgroupUser += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContentgroupUser += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContentgroupUser += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContentgroupUser += ")";
				}
				if (! getContentgroupUsersUsers().equals("")) {
					String SQLwhereContentgroupUsersUsers = "users.id in (" + Common.SQL_list_values(getContentgroupUsersUsers().split(",")) + ")";
					if (! SQLwhereContentgroupUser.equals("")) {
						SQLwhereContentgroupUser = "(" + SQLwhereContentgroupUser + ") or (" + SQLwhereContentgroupUsersUsers + ")";
					} else {
						SQLwhereContentgroupUser = SQLwhereContentgroupUsersUsers;
					}
				}
				if (! SQLwhereContentgroupUser.equals("")) {
					if ((! SQLwhereContentgroupUser.equals(SQLwhereAdministrator)) && (! SQLwhereContentgroupUser.equals(SQLwherePublisher)) && (! SQLwhereContentgroupUser.equals(SQLwhereEditor)) && (! SQLwhereContentgroupUser.equals(SQLwhereCreator)) && (! SQLwhereContentgroupUser.equals(SQLwhereDeveloper)) && (! SQLwhereContentgroupUser.equals(SQLwhereUser))) {
						SQLwhere += " or (" + SQLwhereContentgroupUser + ")";
					}
					restricted_users = true;
				}
			}

			String SQLwhereContentgroupDeveloper = "";
			if (developers) {
				if ((getContentgroupDevelopersType().equals("*")) || (getContentgroupDevelopersType().equals("0"))) {
					// ok
				} else if (! getContentgroupDevelopersType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getContentgroupDevelopersType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getContentgroupDevelopersType(), getContentgroupDevelopersType());
					if (! SQLwhereContentgroupDeveloper.equals("")) SQLwhereContentgroupDeveloper += " and ";
					SQLwhereContentgroupDeveloper += "(";
					SQLwhereContentgroupDeveloper += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContentgroupDeveloper += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContentgroupDeveloper += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContentgroupDeveloper += ")";
				}
				if ((getContentgroupDevelopersGroup().equals("*")) || (getContentgroupDevelopersGroup().equals("0"))) {
					// ok
				} else if (! getContentgroupDevelopersGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getContentgroupDevelopersGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getContentgroupDevelopersGroup(), getContentgroupDevelopersGroup());
					if (! SQLwhereContentgroupDeveloper.equals("")) SQLwhereContentgroupDeveloper += " and ";
					SQLwhereContentgroupDeveloper += "(";
					SQLwhereContentgroupDeveloper += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContentgroupDeveloper += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContentgroupDeveloper += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContentgroupDeveloper += ")";
				}
				if (! getContentgroupDevelopersUsers().equals("")) {
					String SQLwhereContentgroupDevelopersUsers = "users.id in (" + Common.SQL_list_values(getContentgroupDevelopersUsers().split(",")) + ")";
					if (! SQLwhereContentgroupDeveloper.equals("")) {
						SQLwhereContentgroupDeveloper = "(" + SQLwhereContentgroupDeveloper + ") or (" + SQLwhereContentgroupDevelopersUsers + ")";
					} else {
						SQLwhereContentgroupDeveloper = SQLwhereContentgroupDevelopersUsers;
					}
				}
				if (! SQLwhereContentgroupDeveloper.equals("")) {
					if ((! SQLwhereContentgroupDeveloper.equals(SQLwhereContentgroupUser)) && (! SQLwhereContentgroupDeveloper.equals(SQLwhereAdministrator)) && (! SQLwhereContentgroupDeveloper.equals(SQLwherePublisher)) && (! SQLwhereContentgroupDeveloper.equals(SQLwhereEditor)) && (! SQLwhereContentgroupDeveloper.equals(SQLwhereCreator)) && (! SQLwhereContentgroupDeveloper.equals(SQLwhereDeveloper)) && (! SQLwhereContentgroupDeveloper.equals(SQLwhereUser))) {
						SQLwhere += " or (" + SQLwhereContentgroupDeveloper + ")";
					}
					restricted_developers = true;
				}
			}

			String SQLwhereContentgroupCreator = "";
			if (creators) {
				if ((getContentgroupCreatorsType().equals("*")) || (getContentgroupCreatorsType().equals("0"))) {
					// ok
				} else if (! getContentgroupCreatorsType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getContentgroupCreatorsType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getContentgroupCreatorsType(), getContentgroupCreatorsType());
					if (! SQLwhereContentgroupCreator.equals("")) SQLwhereContentgroupCreator += " and ";
					SQLwhereContentgroupCreator += "(";
					SQLwhereContentgroupCreator += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContentgroupCreator += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContentgroupCreator += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContentgroupCreator += ")";
				}
				if ((getContentgroupCreatorsGroup().equals("*")) || (getContentgroupCreatorsGroup().equals("0"))) {
					// ok
				} else if (! getContentgroupCreatorsGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getContentgroupCreatorsGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getContentgroupCreatorsGroup(), getContentgroupCreatorsGroup());
					if (! SQLwhereContentgroupCreator.equals("")) SQLwhereContentgroupCreator += " and ";
					SQLwhereContentgroupCreator += "(";
					SQLwhereContentgroupCreator += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContentgroupCreator += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContentgroupCreator += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContentgroupCreator += ")";
				}
				if (! getContentgroupCreatorsUsers().equals("")) {
					String SQLwhereContentgroupCreatorsUsers = "users.id in (" + Common.SQL_list_values(getContentgroupCreatorsUsers().split(",")) + ")";
					if (! SQLwhereContentgroupCreator.equals("")) {
						SQLwhereContentgroupCreator = "(" + SQLwhereContentgroupCreator + ") or (" + SQLwhereContentgroupCreatorsUsers + ")";
					} else {
						SQLwhereContentgroupCreator = SQLwhereContentgroupCreatorsUsers;
					}
				}
				if (! SQLwhereContentgroupCreator.equals("")) {
					if ((! SQLwhereContentgroupCreator.equals(SQLwhereContentgroupDeveloper)) && (! SQLwhereContentgroupCreator.equals(SQLwhereContentgroupUser)) && (! SQLwhereContentgroupCreator.equals(SQLwhereAdministrator)) && (! SQLwhereContentgroupCreator.equals(SQLwherePublisher)) && (! SQLwhereContentgroupCreator.equals(SQLwhereEditor)) && (! SQLwhereContentgroupCreator.equals(SQLwhereCreator)) && (! SQLwhereContentgroupCreator.equals(SQLwhereDeveloper)) && (! SQLwhereContentgroupCreator.equals(SQLwhereUser))) {
						SQLwhere += " or (" + SQLwhereContentgroupCreator + ")";
					}
					restricted_creators = true;
				}
			}

			String SQLwhereContentgroupEditor = "";
			if (editors) {
				if ((getContentgroupEditorsType().equals("*")) || (getContentgroupEditorsType().equals("0"))) {
					// ok
				} else if (! getContentgroupEditorsType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getContentgroupEditorsType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getContentgroupEditorsType(), getContentgroupEditorsType());
					if (! SQLwhereContentgroupEditor.equals("")) SQLwhereContentgroupEditor += " and ";
					SQLwhereContentgroupEditor += "(";
					SQLwhereContentgroupEditor += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContentgroupEditor += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContentgroupEditor += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContentgroupEditor += ")";
				}
				if ((getContentgroupEditorsGroup().equals("*")) || (getContentgroupEditorsGroup().equals("0"))) {
					// ok
				} else if (! getContentgroupEditorsGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getContentgroupEditorsGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getContentgroupEditorsGroup(), getContentgroupEditorsGroup());
					if (! SQLwhereContentgroupEditor.equals("")) SQLwhereContentgroupEditor += " and ";
					SQLwhereContentgroupEditor += "(";
					SQLwhereContentgroupEditor += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContentgroupEditor += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContentgroupEditor += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContentgroupEditor += ")";
				}
				if (! getContentgroupEditorsUsers().equals("")) {
					String SQLwhereContentgroupEditorsUsers = "users.id in (" + Common.SQL_list_values(getContentgroupEditorsUsers().split(",")) + ")";
					if (! SQLwhereContentgroupEditor.equals("")) {
						SQLwhereContentgroupEditor = "(" + SQLwhereContentgroupEditor + ") or (" + SQLwhereContentgroupEditorsUsers + ")";
					} else {
						SQLwhereContentgroupEditor = SQLwhereContentgroupEditorsUsers;
					}
				}
				if (! SQLwhereContentgroupEditor.equals("")) {
					if ((! SQLwhereContentgroupEditor.equals(SQLwhereContentgroupCreator)) && (! SQLwhereContentgroupEditor.equals(SQLwhereContentgroupDeveloper)) && (! SQLwhereContentgroupEditor.equals(SQLwhereContentgroupUser)) && (! SQLwhereContentgroupEditor.equals(SQLwhereAdministrator)) && (! SQLwhereContentgroupEditor.equals(SQLwherePublisher)) && (! SQLwhereContentgroupEditor.equals(SQLwhereEditor)) && (! SQLwhereContentgroupEditor.equals(SQLwhereCreator)) && (! SQLwhereContentgroupEditor.equals(SQLwhereDeveloper)) && (! SQLwhereContentgroupEditor.equals(SQLwhereUser))) {
						SQLwhere += " or (" + SQLwhereContentgroupEditor + ")";
					}
					restricted_editors = true;
				}
			}

			String SQLwhereContentgroupPublisher = "";
			if (publishers) {
				if ((getContentgroupPublishersType().equals("*")) || (getContentgroupPublishersType().equals("0"))) {
					// ok
				} else if (! getContentgroupPublishersType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getContentgroupPublishersType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getContentgroupPublishersType(), getContentgroupPublishersType());
					if (! SQLwhereContentgroupPublisher.equals("")) SQLwhereContentgroupPublisher += " and ";
					SQLwhereContentgroupPublisher += "(";
					SQLwhereContentgroupPublisher += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContentgroupPublisher += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContentgroupPublisher += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContentgroupPublisher += ")";
				}
				if ((getContentgroupPublishersGroup().equals("*")) || (getContentgroupPublishersGroup().equals("0"))) {
					// ok
				} else if (! getContentgroupPublishersGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getContentgroupPublishersGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getContentgroupPublishersGroup(), getContentgroupPublishersGroup());
					if (! SQLwhereContentgroupPublisher.equals("")) SQLwhereContentgroupPublisher += " and ";
					SQLwhereContentgroupPublisher += "(";
					SQLwhereContentgroupPublisher += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContentgroupPublisher += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContentgroupPublisher += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContentgroupPublisher += ")";
				}
				if (! getContentgroupPublishersUsers().equals("")) {
					String SQLwhereContentgroupPublishersUsers = "users.id in (" + Common.SQL_list_values(getContentgroupPublishersUsers().split(",")) + ")";
					if (! SQLwhereContentgroupPublisher.equals("")) {
						SQLwhereContentgroupPublisher = "(" + SQLwhereContentgroupPublisher + ") or (" + SQLwhereContentgroupPublishersUsers + ")";
					} else {
						SQLwhereContentgroupPublisher = SQLwhereContentgroupPublishersUsers;
					}
				}
				if (! SQLwhereContentgroupPublisher.equals("")) {
					if ((! SQLwhereContentgroupPublisher.equals(SQLwhereContentgroupEditor)) && (! SQLwhereContentgroupPublisher.equals(SQLwhereContentgroupCreator)) && (! SQLwhereContentgroupPublisher.equals(SQLwhereContentgroupDeveloper)) && (! SQLwhereContentgroupPublisher.equals(SQLwhereContentgroupUser)) && (! SQLwhereContentgroupPublisher.equals(SQLwhereAdministrator)) && (! SQLwhereContentgroupPublisher.equals(SQLwherePublisher)) && (! SQLwhereContentgroupPublisher.equals(SQLwhereEditor)) && (! SQLwhereContentgroupPublisher.equals(SQLwhereCreator)) && (! SQLwhereContentgroupPublisher.equals(SQLwhereDeveloper)) && (! SQLwhereContentgroupPublisher.equals(SQLwhereUser))) {
						SQLwhere += " or (" + SQLwhereContentgroupPublisher + ")";
					}
					restricted_publishers = true;
				}
			}

			String SQLwhereContentgroupAdministrator = "";
			if (administrators) {
				if ((getContentgroupAdministratorsType().equals("*")) || (getContentgroupAdministratorsType().equals("0"))) {
					// ok
				} else if (! getContentgroupAdministratorsType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getContentgroupAdministratorsType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getContentgroupAdministratorsType(), getContentgroupAdministratorsType());
					if (! SQLwhereContentgroupAdministrator.equals("")) SQLwhereContentgroupAdministrator += " and ";
					SQLwhereContentgroupAdministrator += "(";
					SQLwhereContentgroupAdministrator += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContentgroupAdministrator += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContentgroupAdministrator += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContentgroupAdministrator += ")";
				}
				if ((getContentgroupAdministratorsGroup().equals("*")) || (getContentgroupAdministratorsGroup().equals("0"))) {
					// ok
				} else if (! getContentgroupAdministratorsGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getContentgroupAdministratorsGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getContentgroupAdministratorsGroup(), getContentgroupAdministratorsGroup());
					if (! SQLwhereContentgroupAdministrator.equals("")) SQLwhereContentgroupAdministrator += " and ";
					SQLwhereContentgroupAdministrator += "(";
					SQLwhereContentgroupAdministrator += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContentgroupAdministrator += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContentgroupAdministrator += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContentgroupAdministrator += ")";
				}
				if (! getContentgroupAdministratorsUsers().equals("")) {
					String SQLwhereContentgroupAdministratorsUsers = "users.id in (" + Common.SQL_list_values(getContentgroupAdministratorsUsers().split(",")) + ")";
					if (! SQLwhereContentgroupAdministrator.equals("")) {
						SQLwhereContentgroupAdministrator = "(" + SQLwhereContentgroupAdministrator + ") or (" + SQLwhereContentgroupAdministratorsUsers + ")";
					} else {
						SQLwhereContentgroupAdministrator = SQLwhereContentgroupAdministratorsUsers;
					}
				}
				if (! SQLwhereContentgroupAdministrator.equals("")) {
					if ((! SQLwhereContentgroupAdministrator.equals(SQLwhereContentgroupPublisher)) && (! SQLwhereContentgroupAdministrator.equals(SQLwhereContentgroupEditor)) && (! SQLwhereContentgroupAdministrator.equals(SQLwhereContentgroupCreator)) && (! SQLwhereContentgroupAdministrator.equals(SQLwhereContentgroupDeveloper)) && (! SQLwhereContentgroupAdministrator.equals(SQLwhereContentgroupUser)) && (! SQLwhereContentgroupAdministrator.equals(SQLwhereAdministrator)) && (! SQLwhereContentgroupAdministrator.equals(SQLwherePublisher)) && (! SQLwhereContentgroupAdministrator.equals(SQLwhereEditor)) && (! SQLwhereContentgroupAdministrator.equals(SQLwhereCreator)) && (! SQLwhereContentgroupAdministrator.equals(SQLwhereDeveloper)) && (! SQLwhereContentgroupAdministrator.equals(SQLwhereUser))) {
						SQLwhere += " or (" + SQLwhereContentgroupAdministrator + ")";
					}
					restricted_administrators = true;
				}
			}

			String SQLwhereContenttypeUser = "";
			if (users) {
				if ((getContenttypeUsersType().equals("*")) || (getContenttypeUsersType().equals("0"))) {
					// ok
				} else if (! getContenttypeUsersType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getContenttypeUsersType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getContenttypeUsersType(), getContenttypeUsersType());
					if (! SQLwhereContenttypeUser.equals("")) SQLwhereContenttypeUser += " and ";
					SQLwhereContenttypeUser += "(";
					SQLwhereContenttypeUser += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContenttypeUser += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContenttypeUser += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContenttypeUser += ")";
				}
				if ((getContenttypeUsersGroup().equals("*")) || (getContenttypeUsersGroup().equals("0"))) {
					// ok
				} else if (! getContenttypeUsersGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getContenttypeUsersGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getContenttypeUsersGroup(), getContenttypeUsersGroup());
					if (! SQLwhereContenttypeUser.equals("")) SQLwhereContenttypeUser += " and ";
					SQLwhereContenttypeUser += "(";
					SQLwhereContenttypeUser += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContenttypeUser += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContenttypeUser += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContenttypeUser += ")";
				}
				if (! getContenttypeUsersUsers().equals("")) {
					String SQLwhereContenttypeUsersUsers = "users.id in (" + Common.SQL_list_values(getContenttypeUsersUsers().split(",")) + ")";
					if (! SQLwhereContenttypeUser.equals("")) {
						SQLwhereContenttypeUser = "(" + SQLwhereContenttypeUser + ") or (" + SQLwhereContenttypeUsersUsers + ")";
					} else {
						SQLwhereContenttypeUser = SQLwhereContenttypeUsersUsers;
					}
				}
				if (! SQLwhereContenttypeUser.equals("")) {
					if ((! SQLwhereContenttypeUser.equals(SQLwhereContentgroupAdministrator)) && (! SQLwhereContenttypeUser.equals(SQLwhereContentgroupPublisher)) && (! SQLwhereContenttypeUser.equals(SQLwhereContentgroupEditor)) && (! SQLwhereContenttypeUser.equals(SQLwhereContentgroupCreator)) && (! SQLwhereContenttypeUser.equals(SQLwhereContentgroupDeveloper)) && (! SQLwhereContenttypeUser.equals(SQLwhereContentgroupUser)) && (! SQLwhereContenttypeUser.equals(SQLwhereAdministrator)) && (! SQLwhereContenttypeUser.equals(SQLwherePublisher)) && (! SQLwhereContenttypeUser.equals(SQLwhereEditor)) && (! SQLwhereContenttypeUser.equals(SQLwhereCreator)) && (! SQLwhereContenttypeUser.equals(SQLwhereDeveloper)) && (! SQLwhereContenttypeUser.equals(SQLwhereUser))) {
						SQLwhere += " or (" + SQLwhereContenttypeUser + ")";
					}
					restricted_users = true;
				}
			}

			String SQLwhereContenttypeDeveloper = "";
			if (developers) {
				if ((getContenttypeDevelopersType().equals("*")) || (getContenttypeDevelopersType().equals("0"))) {
					// ok
				} else if (! getContenttypeDevelopersType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getContenttypeDevelopersType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getContenttypeDevelopersType(), getContenttypeDevelopersType());
					if (! SQLwhereContenttypeDeveloper.equals("")) SQLwhereContenttypeDeveloper += " and ";
					SQLwhereContenttypeDeveloper += "(";
					SQLwhereContenttypeDeveloper += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContenttypeDeveloper += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContenttypeDeveloper += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContenttypeDeveloper += ")";
				}
				if ((getContenttypeDevelopersGroup().equals("*")) || (getContenttypeDevelopersGroup().equals("0"))) {
					// ok
				} else if (! getContenttypeDevelopersGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getContenttypeDevelopersGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getContenttypeDevelopersGroup(), getContenttypeDevelopersGroup());
					if (! SQLwhereContenttypeDeveloper.equals("")) SQLwhereContenttypeDeveloper += " and ";
					SQLwhereContenttypeDeveloper += "(";
					SQLwhereContenttypeDeveloper += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContenttypeDeveloper += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContenttypeDeveloper += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContenttypeDeveloper += ")";
				}
				if (! getContenttypeDevelopersUsers().equals("")) {
					String SQLwhereContenttypeDevelopersUsers = "users.id in (" + Common.SQL_list_values(getContenttypeDevelopersUsers().split(",")) + ")";
					if (! SQLwhereContenttypeDeveloper.equals("")) {
						SQLwhereContenttypeDeveloper = "(" + SQLwhereContenttypeDeveloper + ") or (" + SQLwhereContenttypeDevelopersUsers + ")";
					} else {
						SQLwhereContenttypeDeveloper = SQLwhereContenttypeDevelopersUsers;
					}
				}
				if (! SQLwhereContenttypeDeveloper.equals("")) {
					if ((! SQLwhereContenttypeDeveloper.equals(SQLwhereContenttypeUser)) && (! SQLwhereContenttypeDeveloper.equals(SQLwhereContentgroupAdministrator)) && (! SQLwhereContenttypeDeveloper.equals(SQLwhereContentgroupPublisher)) && (! SQLwhereContenttypeDeveloper.equals(SQLwhereContentgroupEditor)) && (! SQLwhereContenttypeDeveloper.equals(SQLwhereContentgroupCreator)) && (! SQLwhereContenttypeDeveloper.equals(SQLwhereContentgroupDeveloper)) && (! SQLwhereContenttypeDeveloper.equals(SQLwhereContentgroupUser)) && (! SQLwhereContenttypeDeveloper.equals(SQLwhereAdministrator)) && (! SQLwhereContenttypeDeveloper.equals(SQLwherePublisher)) && (! SQLwhereContenttypeDeveloper.equals(SQLwhereEditor)) && (! SQLwhereContenttypeDeveloper.equals(SQLwhereCreator)) && (! SQLwhereContenttypeDeveloper.equals(SQLwhereDeveloper)) && (! SQLwhereContenttypeDeveloper.equals(SQLwhereUser))) {
						SQLwhere += " or (" + SQLwhereContenttypeDeveloper + ")";
					}
					restricted_developers = true;
				}
			}

			String SQLwhereContenttypeCreator = "";
			if (creators) {
				if ((getContenttypeCreatorsType().equals("*")) || (getContenttypeCreatorsType().equals("0"))) {
					// ok
				} else if (! getContenttypeCreatorsType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getContenttypeCreatorsType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getContenttypeCreatorsType(), getContenttypeCreatorsType());
					if (! SQLwhereContenttypeCreator.equals("")) SQLwhereContenttypeCreator += " and ";
					SQLwhereContenttypeCreator += "(";
					SQLwhereContenttypeCreator += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContenttypeCreator += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContenttypeCreator += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContenttypeCreator += ")";
				}
				if ((getContenttypeCreatorsGroup().equals("*")) || (getContenttypeCreatorsGroup().equals("0"))) {
					// ok
				} else if (! getContenttypeCreatorsGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getContenttypeCreatorsGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getContenttypeCreatorsGroup(), getContenttypeCreatorsGroup());
					if (! SQLwhereContenttypeCreator.equals("")) SQLwhereContenttypeCreator += " and ";
					SQLwhereContenttypeCreator += "(";
					SQLwhereContenttypeCreator += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContenttypeCreator += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContenttypeCreator += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContenttypeCreator += ")";
				}
				if (! getContenttypeCreatorsUsers().equals("")) {
					String SQLwhereContenttypeCreatorsUsers = "users.id in (" + Common.SQL_list_values(getContenttypeCreatorsUsers().split(",")) + ")";
					if (! SQLwhereContenttypeCreator.equals("")) {
						SQLwhereContenttypeCreator = "(" + SQLwhereContenttypeCreator + ") or (" + SQLwhereContenttypeCreatorsUsers + ")";
					} else {
						SQLwhereContenttypeCreator = SQLwhereContenttypeCreatorsUsers;
					}
				}
				if (! SQLwhereContenttypeCreator.equals("")) {
					if ((! SQLwhereContenttypeCreator.equals(SQLwhereContenttypeDeveloper)) && (! SQLwhereContenttypeCreator.equals(SQLwhereContenttypeUser)) && (! SQLwhereContenttypeCreator.equals(SQLwhereContentgroupAdministrator)) && (! SQLwhereContenttypeCreator.equals(SQLwhereContentgroupPublisher)) && (! SQLwhereContenttypeCreator.equals(SQLwhereContentgroupEditor)) && (! SQLwhereContenttypeCreator.equals(SQLwhereContentgroupCreator)) && (! SQLwhereContenttypeCreator.equals(SQLwhereContentgroupDeveloper)) && (! SQLwhereContenttypeCreator.equals(SQLwhereContentgroupUser)) && (! SQLwhereContenttypeCreator.equals(SQLwhereAdministrator)) && (! SQLwhereContenttypeCreator.equals(SQLwherePublisher)) && (! SQLwhereContenttypeCreator.equals(SQLwhereEditor)) && (! SQLwhereContenttypeCreator.equals(SQLwhereCreator)) && (! SQLwhereContenttypeCreator.equals(SQLwhereDeveloper)) && (! SQLwhereContenttypeCreator.equals(SQLwhereUser))) {
						SQLwhere += " or (" + SQLwhereContenttypeCreator + ")";
					}
					restricted_creators = true;
				}
			}

			String SQLwhereContenttypeEditor = "";
			if (editors) {
				if ((getContenttypeEditorsType().equals("*")) || (getContenttypeEditorsType().equals("0"))) {
					// ok
				} else if (! getContenttypeEditorsType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getContenttypeEditorsType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getContenttypeEditorsType(), getContenttypeEditorsType());
					if (! SQLwhereContenttypeEditor.equals("")) SQLwhereContenttypeEditor += " and ";
					SQLwhereContenttypeEditor += "(";
					SQLwhereContenttypeEditor += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContenttypeEditor += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContenttypeEditor += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContenttypeEditor += ")";
				}
				if ((getContenttypeEditorsGroup().equals("*")) || (getContenttypeEditorsGroup().equals("0"))) {
					// ok
				} else if (! getContenttypeEditorsGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getContenttypeEditorsGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getContenttypeEditorsGroup(), getContenttypeEditorsGroup());
					if (! SQLwhereContenttypeEditor.equals("")) SQLwhereContenttypeEditor += " and ";
					SQLwhereContenttypeEditor += "(";
					SQLwhereContenttypeEditor += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContenttypeEditor += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContenttypeEditor += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContenttypeEditor += ")";
				}
				if (! getContenttypeEditorsUsers().equals("")) {
					String SQLwhereContenttypeEditorsUsers = "users.id in (" + Common.SQL_list_values(getContenttypeEditorsUsers().split(",")) + ")";
					if (! SQLwhereContenttypeEditor.equals("")) {
						SQLwhereContenttypeEditor = "(" + SQLwhereContenttypeEditor + ") or (" + SQLwhereContenttypeEditorsUsers + ")";
					} else {
						SQLwhereContenttypeEditor = SQLwhereContenttypeEditorsUsers;
					}
				}
				if (! SQLwhereContenttypeEditor.equals("")) {
					if ((! SQLwhereContenttypeEditor.equals(SQLwhereContenttypeCreator)) && (! SQLwhereContenttypeEditor.equals(SQLwhereContenttypeDeveloper)) && (! SQLwhereContenttypeEditor.equals(SQLwhereContenttypeUser)) && (! SQLwhereContenttypeEditor.equals(SQLwhereContentgroupAdministrator)) && (! SQLwhereContenttypeEditor.equals(SQLwhereContentgroupPublisher)) && (! SQLwhereContenttypeEditor.equals(SQLwhereContentgroupEditor)) && (! SQLwhereContenttypeEditor.equals(SQLwhereContentgroupCreator)) && (! SQLwhereContenttypeEditor.equals(SQLwhereContentgroupDeveloper)) && (! SQLwhereContenttypeEditor.equals(SQLwhereContentgroupUser)) && (! SQLwhereContenttypeEditor.equals(SQLwhereAdministrator)) && (! SQLwhereContenttypeEditor.equals(SQLwherePublisher)) && (! SQLwhereContenttypeEditor.equals(SQLwhereEditor)) && (! SQLwhereContenttypeEditor.equals(SQLwhereCreator)) && (! SQLwhereContenttypeEditor.equals(SQLwhereDeveloper)) && (! SQLwhereContenttypeEditor.equals(SQLwhereUser))) {
						SQLwhere += " or (" + SQLwhereContenttypeEditor + ")";
					}
					restricted_editors = true;
				}
			}

			String SQLwhereContenttypePublisher = "";
			if (publishers) {
				if ((getContenttypePublishersType().equals("*")) || (getContenttypePublishersType().equals("0"))) {
					// ok
				} else if (! getContenttypePublishersType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getContenttypePublishersType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getContenttypePublishersType(), getContenttypePublishersType());
					if (! SQLwhereContenttypePublisher.equals("")) SQLwhereContenttypePublisher += " and ";
					SQLwhereContenttypePublisher += "(";
					SQLwhereContenttypePublisher += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContenttypePublisher += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContenttypePublisher += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContenttypePublisher += ")";
				}
				if ((getContenttypePublishersGroup().equals("*")) || (getContenttypePublishersGroup().equals("0"))) {
					// ok
				} else if (! getContenttypePublishersGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getContenttypePublishersGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getContenttypePublishersGroup(), getContenttypePublishersGroup());
					if (! SQLwhereContenttypePublisher.equals("")) SQLwhereContenttypePublisher += " and ";
					SQLwhereContenttypePublisher += "(";
					SQLwhereContenttypePublisher += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContenttypePublisher += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContenttypePublisher += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContenttypePublisher += ")";
				}
				if (! getContenttypePublishersUsers().equals("")) {
					String SQLwhereContenttypePublishersUsers = "users.id in (" + Common.SQL_list_values(getContenttypePublishersUsers().split(",")) + ")";
					if (! SQLwhereContenttypePublisher.equals("")) {
						SQLwhereContenttypePublisher = "(" + SQLwhereContenttypePublisher + ") or (" + SQLwhereContenttypePublishersUsers + ")";
					} else {
						SQLwhereContenttypePublisher = SQLwhereContenttypePublishersUsers;
					}
				}
				if (! SQLwhereContenttypePublisher.equals("")) {
					if ((! SQLwhereContenttypePublisher.equals(SQLwhereContenttypeEditor)) && (! SQLwhereContenttypePublisher.equals(SQLwhereContenttypeCreator)) && (! SQLwhereContenttypePublisher.equals(SQLwhereContenttypeDeveloper)) && (! SQLwhereContenttypePublisher.equals(SQLwhereContenttypeUser)) && (! SQLwhereContenttypePublisher.equals(SQLwhereContentgroupAdministrator)) && (! SQLwhereContenttypePublisher.equals(SQLwhereContentgroupPublisher)) && (! SQLwhereContenttypePublisher.equals(SQLwhereContentgroupEditor)) && (! SQLwhereContenttypePublisher.equals(SQLwhereContentgroupCreator)) && (! SQLwhereContenttypePublisher.equals(SQLwhereContentgroupDeveloper)) && (! SQLwhereContenttypePublisher.equals(SQLwhereContentgroupUser)) && (! SQLwhereContenttypePublisher.equals(SQLwhereAdministrator)) && (! SQLwhereContenttypePublisher.equals(SQLwherePublisher)) && (! SQLwhereContenttypePublisher.equals(SQLwhereEditor)) && (! SQLwhereContenttypePublisher.equals(SQLwhereCreator)) && (! SQLwhereContenttypePublisher.equals(SQLwhereDeveloper)) && (! SQLwhereContenttypePublisher.equals(SQLwhereUser))) {
						SQLwhere += " or (" + SQLwhereContenttypePublisher + ")";
					}
					restricted_publishers = true;
				}
			}

			String SQLwhereContenttypeAdministrator = "";
			if (administrators) {
				if ((getContenttypeAdministratorsType().equals("*")) || (getContenttypeAdministratorsType().equals("0"))) {
					// ok
				} else if (! getContenttypeAdministratorsType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getContenttypeAdministratorsType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getContenttypeAdministratorsType(), getContenttypeAdministratorsType());
					if (! SQLwhereContenttypeAdministrator.equals("")) SQLwhereContenttypeAdministrator += " and ";
					SQLwhereContenttypeAdministrator += "(";
					SQLwhereContenttypeAdministrator += "users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContenttypeAdministrator += " or ";
						HashMap<String,String> usertypes_users = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContenttypeAdministrator += "(users.username in (" + Common.SQL_list_values((String[])usertypes_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContenttypeAdministrator += ")";
				}
				if ((getContenttypeAdministratorsGroup().equals("*")) || (getContenttypeAdministratorsGroup().equals("0"))) {
					// ok
				} else if (! getContenttypeAdministratorsGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getContenttypeAdministratorsGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getContenttypeAdministratorsGroup(), getContenttypeAdministratorsGroup());
					if (! SQLwhereContenttypeAdministrator.equals("")) SQLwhereContenttypeAdministrator += " and ";
					SQLwhereContenttypeAdministrator += "(";
					SQLwhereContenttypeAdministrator += "users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
					if (subgroupstypes) {
						SQLwhereContenttypeAdministrator += " or ";
						HashMap<String,String> usergroups_users = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ") order by username");
						SQLwhereContenttypeAdministrator += "(users.username in (" + Common.SQL_list_values((String[])usergroups_users.keySet().toArray(new String[0])) + "))";
					}
					SQLwhereContenttypeAdministrator += ")";
				}
				if (! getContenttypeAdministratorsUsers().equals("")) {
					String SQLwhereContenttypeAdministratorsUsers = "users.id in (" + Common.SQL_list_values(getContenttypeAdministratorsUsers().split(",")) + ")";
					if (! SQLwhereContenttypeAdministrator.equals("")) {
						SQLwhereContenttypeAdministrator = "(" + SQLwhereContenttypeAdministrator + ") or (" + SQLwhereContenttypeAdministratorsUsers + ")";
					} else {
						SQLwhereContenttypeAdministrator = SQLwhereContenttypeAdministratorsUsers;
					}
				}
				if (! SQLwhereContenttypeAdministrator.equals("")) {
					if ((! SQLwhereContenttypeAdministrator.equals(SQLwhereContenttypePublisher)) && (! SQLwhereContenttypeAdministrator.equals(SQLwhereContenttypeEditor)) && (! SQLwhereContenttypeAdministrator.equals(SQLwhereContenttypeCreator)) && (! SQLwhereContenttypeAdministrator.equals(SQLwhereContenttypeDeveloper)) && (! SQLwhereContenttypeAdministrator.equals(SQLwhereContenttypeUser)) && (! SQLwhereContenttypeAdministrator.equals(SQLwhereContentgroupAdministrator)) && (! SQLwhereContenttypeAdministrator.equals(SQLwhereContentgroupPublisher)) && (! SQLwhereContenttypeAdministrator.equals(SQLwhereContentgroupEditor)) && (! SQLwhereContenttypeAdministrator.equals(SQLwhereContentgroupCreator)) && (! SQLwhereContenttypeAdministrator.equals(SQLwhereContentgroupDeveloper)) && (! SQLwhereContenttypeAdministrator.equals(SQLwhereContentgroupUser)) && (! SQLwhereContenttypeAdministrator.equals(SQLwhereAdministrator)) && (! SQLwhereContenttypeAdministrator.equals(SQLwherePublisher)) && (! SQLwhereContenttypeAdministrator.equals(SQLwhereEditor)) && (! SQLwhereContenttypeAdministrator.equals(SQLwhereCreator)) && (! SQLwhereContenttypeAdministrator.equals(SQLwhereDeveloper)) && (! SQLwhereContenttypeAdministrator.equals(SQLwhereUser))) {
						SQLwhere += " or (" + SQLwhereContenttypeAdministrator + ")";
					}
					restricted_administrators = true;
				}
			}

			if (broad_search && ((! restricted_users) || (! restricted_developers) || (! restricted_creators) || (! restricted_editors) || (! restricted_publishers) || (! restricted_administrators))) {
				SQLwhere += " or (1=1)";
			}

			SQLwhere += ")";
		}
		String SQLfrom = " from users";
		return SQLfrom + " " + SQLwhere;
	}



	public String[] administratorsEmails(Session mysession, Configuration myconfig, DB db) {
		HashMap<String,String> emails = new HashMap<String,String>();
		if ((id != null) && (! id.equals("")) && (! id.equals("0")) && (! id.equals("-1"))) {
			String admin_email = "";
			String SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,true,false,false,false,false,false,false,false) + " and (" + db.is_not_blank("username") + ")";
			User user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				String myusername = user.getUsername();
				String myemail = user.getEmail();
				user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myusername);
				if (user.getEmail().equals("")) user.setEmail(myemail);
				if (! user.getEmail().equals("")) {
					emails.put(user.getEmail(), user.getEmail());
				}
			}
			SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,true,false,false,false,false,false,false,true) + " and (" + db.is_not_blank("username") + ")";
			user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				String myusername = user.getUsername();
				String myemail = user.getEmail();
				user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myusername);
				if (user.getEmail().equals("")) user.setEmail(myemail);
				if (! user.getEmail().equals("")) {
					emails.put(user.getEmail(), user.getEmail());
				}
			}
		}
		if ((emails.size() == 0) && (! myconfig.get(db, "superadmin_email").equals(""))) {
			emails.put(myconfig.get(db, "superadmin_email"), myconfig.get(db, "superadmin_email"));
		}
		return (String[]) emails.values().toArray(new String[0]);
	}



	public String[] publishersEmails(Session mysession, Configuration myconfig, DB db) {
		HashMap<String,String> emails = new HashMap<String,String>();
		if ((id != null) && (! id.equals("")) && (! id.equals("0")) && (! id.equals("-1"))) {
			String admin_email = "";
			String SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,true,true,false,false,false,false,false,false) + " and (" + db.is_not_blank("username") + ")";
			User user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				String myusername = user.getUsername();
				String myemail = user.getEmail();
				user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myusername);
				if (user.getEmail().equals("")) user.setEmail(myemail);
				if (! user.getEmail().equals("")) {
					emails.put(user.getEmail(), user.getEmail());
				}
			}
			SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,true,true,false,false,false,false,false,true) + " and (" + db.is_not_blank("username") + ")";
			user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				String myusername = user.getUsername();
				String myemail = user.getEmail();
				user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myusername);
				if (user.getEmail().equals("")) user.setEmail(myemail);
				if (! user.getEmail().equals("")) {
					emails.put(user.getEmail(), user.getEmail());
				}
			}
		}
		if ((emails.size() == 0) && (! myconfig.get(db, "superadmin_email").equals(""))) {
			emails.put(myconfig.get(db, "superadmin_email"), myconfig.get(db, "superadmin_email"));
		}
		return (String[]) emails.values().toArray(new String[0]);
	}



	public String[] editorsEmails(Session mysession, Configuration myconfig, DB db) {
		HashMap<String,String> emails = new HashMap<String,String>();
		if ((id != null) && (! id.equals("")) && (! id.equals("0")) && (! id.equals("-1"))) {
			String admin_email = "";
			String SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,true,true,true,true,true,false,false,false) + " and (" + db.is_not_blank("username") + ")";
			User user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				String myusername = user.getUsername();
				String myemail = user.getEmail();
				user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myusername);
				if (user.getEmail().equals("")) user.setEmail(myemail);
				if (! user.getEmail().equals("")) {
					emails.put(user.getEmail(), user.getEmail());
				}
			}
			SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,true,true,true,true,true,false,false,true) + " and (" + db.is_not_blank("username") + ")";
			user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				String myusername = user.getUsername();
				String myemail = user.getEmail();
				user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myusername);
				if (user.getEmail().equals("")) user.setEmail(myemail);
				if (! user.getEmail().equals("")) {
					emails.put(user.getEmail(), user.getEmail());
				}
			}
		}
		if ((emails.size() == 0) && (! myconfig.get(db, "superadmin_email").equals(""))) {
			emails.put(myconfig.get(db, "superadmin_email"), myconfig.get(db, "superadmin_email"));
		}
		return (String[]) emails.values().toArray(new String[0]);
	}



	public String[] developersEmails(Session mysession, Configuration myconfig, DB db) {
		HashMap<String,String> emails = new HashMap<String,String>();
		if ((id != null) && (! id.equals("")) && (! id.equals("0")) && (! id.equals("-1"))) {
			String admin_email = "";
			String SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,true,false,false,false,true,false,false,false) + " and (" + db.is_not_blank("username") + ")";
			User user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				String myusername = user.getUsername();
				String myemail = user.getEmail();
				user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myusername);
				if (user.getEmail().equals("")) user.setEmail(myemail);
				if (! user.getEmail().equals("")) {
					emails.put(user.getEmail(), user.getEmail());
				}
			}
			SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,true,false,false,false,true,false,false,true) + " and (" + db.is_not_blank("username") + ")";
			user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				String myusername = user.getUsername();
				String myemail = user.getEmail();
				user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myusername);
				if (user.getEmail().equals("")) user.setEmail(myemail);
				if (! user.getEmail().equals("")) {
					emails.put(user.getEmail(), user.getEmail());
				}
			}
		}
		if ((emails.size() == 0) && (! myconfig.get(db, "superadmin_email").equals(""))) {
			emails.put(myconfig.get(db, "superadmin_email"), myconfig.get(db, "superadmin_email"));
		}
		return (String[]) emails.values().toArray(new String[0]);
	}



	public String[] creatorsEmails(Session mysession, Configuration myconfig, DB db) {
		HashMap<String,String> emails = new HashMap<String,String>();
		if ((id != null) && (! id.equals("")) && (! id.equals("0")) && (! id.equals("-1"))) {
			String admin_email = "";
			String SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,true,false,false,true,false,false,false,false) + " and (" + db.is_not_blank("username") + ")";
			User user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				String myusername = user.getUsername();
				String myemail = user.getEmail();
				user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myusername);
				if (user.getEmail().equals("")) user.setEmail(myemail);
				if (! user.getEmail().equals("")) {
					emails.put(user.getEmail(), user.getEmail());
				}
			}
			SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,true,false,false,true,false,false,false,true) + " and (" + db.is_not_blank("username") + ")";
			user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				String myusername = user.getUsername();
				String myemail = user.getEmail();
				user.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myusername);
				if (user.getEmail().equals("")) user.setEmail(myemail);
				if (! user.getEmail().equals("")) {
					emails.put(user.getEmail(), user.getEmail());
				}
			}
		}
		if ((emails.size() == 0) && (! myconfig.get(db, "superadmin_email").equals(""))) {
			emails.put(myconfig.get(db, "superadmin_email"), myconfig.get(db, "superadmin_email"));
		}
		return (String[]) emails.values().toArray(new String[0]);
	}



	public String get(String name) {
		if (name.equals("id")) return "" + getId();
		if (name.equals("locked")) return "" + getLocked();
		if (name.equals("locked_user")) return "" + getLockedUser();
		if (name.equals("locked_creator")) return "" + getLockedCreator();
		if (name.equals("locked_developer")) return "" + getLockedDeveloper();
		if (name.equals("locked_editor")) return "" + getLockedEditor();
		if (name.equals("locked_publisher")) return "" + getLockedPublisher();
		if (name.equals("locked_administrator")) return "" + getLockedAdministrator();
		if (name.equals("locked_schedule")) return "" + getLockedSchedule();
		if (name.equals("locked_unschedule")) return "" + getLockedUnschedule();
		if (name.equals("created")) return "" + getCreated();
		if (name.equals("updated")) return "" + getUpdated();
		if (name.equals("published")) return "" + getPublished();
		if (name.equals("unpublished")) return "" + getUnpublished();
		if (name.equals("scheduled_publish")) return "" + getScheduledPublish();
		if (name.equals("scheduled_unpublish")) return "" + getScheduledUnpublish();
		if (name.equals("requested_publish")) return "" + getRequestedPublish();
		if (name.equals("requested_unpublish")) return "" + getRequestedUnpublish();
		if (name.equals("status")) return "" + getStatus();
		if (name.equals("status_by")) return "" + getStatusBy();
		if (name.equals("created_by")) return "" + getCreatedBy();
		if (name.equals("updated_by")) return "" + getUpdatedBy();
		if (name.equals("published_by")) return "" + getPublishedBy();
		if (name.equals("unpublished_by")) return "" + getUnpublishedBy();
		if (name.equals("revision")) return "" + getRevision();
		if (name.equals("device")) return "" + getDevice();
		if (name.equals("usersegment")) return "" + getUsersegment();
		if (name.equals("usertest")) return "" + getUsertest();
		if (name.equals("heatmap")) return "" + getHeatmap();
		if (name.equals("heatmapalign")) return "" + getHeatmapAlign();
		if (name.equals("usagelog")) return "" + getUsagelog();
		if (name.equals("version")) return "" + getVersion();
		if (name.equals("version_master")) return "" + getVersionMaster();
		if (name.equals("title")) return "" + getTitle();
		if (name.equals("searchable")) return "" + getSearchable();
		if (name.equals("menuitem")) return "" + getMenuitem();
		if (name.equals("author")) return "" + getAuthor();
		if (name.equals("keywords")) return "" + getKeywords();
		if (name.equals("description")) return "" + getDescription();
		if (name.equals("metainfo")) return "" + getMetaInfo();
		if (name.startsWith("metainfo_")) return "" + getMetaInfo(name.substring(9));
		if (name.equals("segmentation")) return "" + getSegmentation();
		if (name.startsWith("segmentation_")) return "" + getSegmentation(name.substring(13));
		if (name.equals("htmlattributes")) return "" + getHtmlAttributes();
		if (name.equals("htmlheader")) return "" + getHtmlHeader();
		if (name.equals("htmlbodyonload")) return "" + getHtmlBodyOnload();
		if (name.equals("url")) return "" + getUrl();
		if (name.equals("content")) return "" + getContent();
		if (name.equals("summary")) return "" + getSummary();
		if (name.equals("doctype")) return "" + getDocType();
		if (name.equals("template")) return "" + getTemplate();
		if (name.equals("stylesheet")) return "" + getStyleSheet();
		if (name.equals("scripts")) return "" + getScripts();
		if (name.equals("image1")) return "" + getImage1();
		if (name.equals("image2")) return "" + getImage2();
		if (name.equals("image3")) return "" + getImage3();
		if (name.equals("file1")) return "" + getFile1();
		if (name.equals("file2")) return "" + getFile2();
		if (name.equals("file3")) return "" + getFile3();
		if (name.equals("link1")) return "" + getLink1();
		if (name.equals("link2")) return "" + getLink2();
		if (name.equals("link3")) return "" + getLink3();
		if (name.equals("contentformat")) return "" + getContentFormat();
		if (name.equals("contentclass")) return "" + getContentClass();
		if (name.equals("contenttype")) return "" + getContentType();
		if (name.equals("contentgroup")) return "" + getContentGroup();
		if (name.equals("contentpackage")) return "" + getContentPackage();
		if (name.equals("contentbundle")) return "" + getContentBundle();
		if (name.equals("users_type")) return "" + getUsersType();
		if (name.equals("users_group")) return "" + getUsersGroup();
		if (name.equals("developers_type")) return "" + getDevelopersType();
		if (name.equals("developers_group")) return "" + getDevelopersGroup();
		if (name.equals("creators_type")) return "" + getCreatorsType();
		if (name.equals("creators_group")) return "" + getCreatorsGroup();
		if (name.equals("editors_type")) return "" + getEditorsType();
		if (name.equals("editors_group")) return "" + getEditorsGroup();
		if (name.equals("publishers_type")) return "" + getPublishersType();
		if (name.equals("publishers_group")) return "" + getPublishersGroup();
		if (name.equals("administrators_type")) return "" + getAdministratorsType();
		if (name.equals("administrators_group")) return "" + getAdministratorsGroup();
		if (name.equals("users_users")) return "" + getUsersUsers();
		if (name.equals("developers_users")) return "" + getDevelopersUsers();
		if (name.equals("creators_users")) return "" + getCreatorsUsers();
		if (name.equals("editors_users")) return "" + getEditorsUsers();
		if (name.equals("publishers_users")) return "" + getPublishersUsers();
		if (name.equals("administrators_users")) return "" + getAdministratorsUsers();
		if (name.equals("checkedout")) return "" + getCheckedOut();
		if (name.equals("page_top")) return "" + getPageTop();
		if (name.equals("page_up")) return "" + getPageUp();
		if (name.equals("page_next")) return "" + getPageNext();
		if (name.equals("page_previous")) return "" + getPagePrevious();
		if (name.equals("page_first")) return "" + getPageFirst();
		if (name.equals("page_last")) return "" + getPageLast();
		if (name.equals("related")) return "" + getRelated();
		if (name.equals("upload_filename")) return "" + getUploadFilename();
		if (name.equals("server_filename")) return "" + getServerFilename();
		if (name.equals("product_code")) return "" + getProductCode();
		if (name.equals("product_currency")) return "" + getProductCurrency();
		if (name.equals("product_cost")) return "" + getProductCost();
		if (name.equals("product_price")) return "" + getProductPrice();
		if (name.equals("product_period")) return "" + getProductPeriod();
		if (name.equals("product_weight")) return "" + getProductWeight();
		if (name.equals("product_volume")) return "" + getProductVolume();
		if (name.equals("product_width")) return "" + getProductWidth();
		if (name.equals("product_height")) return "" + getProductHeight();
		if (name.equals("product_depth")) return "" + getProductDepth();
		if (name.equals("product_stock")) return "" + getProductStock(); // DEPRECATED
		if (name.equals("product_comment")) return "" + getProductComment(); // DEPRECATED
		if (name.equals("product_email")) return "" + getProductEmail();
		if (name.equals("product_url")) return "" + getProductURL();
		if (name.equals("product_brand")) return "" + getProductBrand();
		if (name.equals("product_colour")) return "" + getProductColour();
		if (name.equals("product_size")) return "" + getProductSize();
		if (name.equals("product_info")) return "" + getProductInfo();
		if (name.startsWith("product_info_")) return "" + getProductInfo(name.substring(13));
		if (name.startsWith("productinfo_")) return "" + getProductInfo(name.substring(12));
		if (name.equals("product_delivery")) return "" + getProductDelivery();
//		if (name.equals("product_stock_amount")) return "" + getProductStockAmount(db);
//		if (name.equals("product_restocked_amount")) return "" + getProductRestockedAmount(db);
		if (name.equals("product_stock_text")) return "" + getProductStockText();
		if (name.equals("product_lowstock_amount")) return "" + getProductLowStockAmount();
		if (name.equals("product_lowstock_text")) return "" + getProductLowStockText();
		if (name.equals("product_nostock_order")) return "" + getProductNoStockOrder();
		if (name.equals("product_nostock_text")) return "" + getProductNoStockText();
		if (name.equals("product_location")) return "" + getProductLocation();
		if (name.equals("product_user")) return "" + getProductUser();
		if (name.equals("product_page")) return "" + getProductPage();
		if (name.equals("product_program")) return "" + getProductProgram();
		if (name.equals("product_hosting")) return "" + getProductHosting();
		if (name.equals("product_options")) return "" + getProductOptions();
		if (name.equals("product_content")) return "" + getProductContent();
		if (name.equals("product_contentgroup")) return "" + getProductContentgroup();
		if (name.equals("product_contenttype")) return "" + getProductContenttype();
		if (name.equals("product_imagegroup")) return "" + getProductImagegroup();
		if (name.equals("product_imagetype")) return "" + getProductImagetype();
		if (name.equals("product_filegroup")) return "" + getProductFilegroup();
		if (name.equals("product_filetype")) return "" + getProductFiletype();
		if (name.equals("product_linkgroup")) return "" + getProductLinkgroup();
		if (name.equals("product_linktype")) return "" + getProductLinktype();
		if (name.equals("product_productgroup")) return "" + getProductProductgroup();
		if (name.equals("product_producttype")) return "" + getProductProducttype();
		if (name.equals("product_usergroup")) return "" + getProductUsergroup();
		if (name.equals("product_usertype")) return "" + getProductUsertype();
		return "";
	}



	public String getArchiveId() {
		if (archiveid == null) {
			return "";
		} else {
			return "" + archiveid;
		}
	}
	public void setArchiveId(String value) {
		archiveid = value;
	}



	public String getId() {
		if (id == null) {
			return "";
		} else {
			return "" + id;
		}
	}
	public void setId(String value) {
		id = value;
	}



	public int getLocked() {
		return locked;
	}
	public void setLocked(int value) {
		locked = value;
	}



	public boolean isLockedUser() {
		return locked_user == 1;
	}
	public int getLockedUser() {
		return locked_user;
	}
	public void setLockedUser(int value) {
		locked_user = value;
	}



	public boolean isLockedCreator() {
		return locked_creator == 1;
	}
	public int getLockedCreator() {
		return locked_creator;
	}
	public void setLockedCreator(int value) {
		locked_creator = value;
	}



	public boolean isLockedDeveloper() {
		return locked_developer == 1;
	}
	public int getLockedDeveloper() {
		return locked_developer;
	}
	public void setLockedDeveloper(int value) {
		locked_developer = value;
	}



	public boolean isLockedEditor() {
		return locked_editor == 1;
	}
	public int getLockedEditor() {
		return locked_editor;
	}
	public void setLockedEditor(int value) {
		locked_editor = value;
	}



	public boolean isLockedPublisher() {
		return locked_publisher == 1;
	}
	public int getLockedPublisher() {
		return locked_publisher;
	}
	public void setLockedPublisher(int value) {
		locked_publisher = value;
	}



	public boolean isLockedAdministrator() {
		return locked_administrator == 1;
	}
	public int getLockedAdministrator() {
		return locked_administrator;
	}
	public void setLockedAdministrator(int value) {
		locked_administrator = value;
	}



	public boolean isLockedSchedule() {
		return locked_schedule == 1;
	}
	public int getLockedSchedule() {
		return locked_schedule;
	}
	public void setLockedSchedule(int value) {
		locked_schedule = value;
	}



	public boolean isLockedUnschedule() {
		return locked_unschedule == 1;
	}
	public int getLockedUnschedule() {
		return locked_unschedule;
	}
	public void setLockedUnschedule(int value) {
		locked_unschedule = value;
	}



	public String getCreated() {
		if (created == null) {
			return "";
		} else {
			return "" + created;
		}
	}
	public void setCreated(String value) {
		created = value;
	}



	public String getUpdated() {
		if (updated == null) {
			return "";
		} else {
			return "" + updated;
		}
	}
	public void setUpdated(String value) {
		updated = value;
	}



	public String getPublished() {
		if (published == null) {
			return "";
		} else {
			return "" + published;
		}
	}
	public void setPublished(String value) {
		published = value;
	}



	public String getUnpublished() {
		if (unpublished == null) {
			return "";
		} else {
			return "" + unpublished;
		}
	}
	public void setUnpublished(String value) {
		unpublished = value;
	}



	public String getCreatedBy() {
		if (created_by == null) {
			return "";
		} else {
			return "" + created_by;
		}
	}
	public void setCreatedBy(String value) {
		created_by = value;
	}



	public String getUpdatedBy() {
		if (updated_by == null) {
			return "";
		} else {
			return "" + updated_by;
		}
	}
	public void setUpdatedBy(String value) {
		updated_by = value;
	}



	public String getPublishedBy() {
		if (published_by == null) {
			return "";
		} else {
			return "" + published_by;
		}
	}
	public void setPublishedBy(String value) {
		published_by = value;
	}



	public String getUnpublishedBy() {
		if (unpublished_by == null) {
			return "";
		} else {
			return "" + unpublished_by;
		}
	}
	public void setUnpublishedBy(String value) {
		unpublished_by = value;
	}



	public String getScheduledPublish() {
		if (scheduled_publish == null) {
			return "";
		} else {
			return "" + scheduled_publish;
		}
	}
	public void setScheduledPublish(String value) {
		scheduled_publish = value;
	}



	public String getScheduledUnpublish() {
		if (scheduled_unpublish == null) {
			return "";
		} else {
			return "" + scheduled_unpublish;
		}
	}
	public void setScheduledUnpublish(String value) {
		scheduled_unpublish = value;
	}



	public String getRequestedPublish() {
		if (requested_publish == null) {
			return "";
		} else {
			return "" + requested_publish;
		}
	}
	public void setRequestedPublish(String value) {
		requested_publish = value;
	}



	public String getRequestedUnpublish() {
		if (requested_unpublish == null) {
			return "";
		} else {
			return "" + requested_unpublish;
		}
	}
	public void setRequestedUnpublish(String value) {
		requested_unpublish = value;
	}



	public String getRevision() {
		if (revision == null) {
			return "";
		} else {
			return "" + revision;
		}
	}
	public void setRevision(String value) {
		revision = value;
	}



	public String getDevice() {
		if (device == null) {
			return "";
		} else {
			return "" + device;
		}
	}
	public void setDevice(String value) {
		device = value;
	}



	public String getUsersegment() {
		if (usersegment == null) {
			return "";
		} else {
			return "" + usersegment;
		}
	}
	public void setUsersegment(String value) {
		usersegment = value;
	}



	public String getUsertest() {
		if (usertest == null) {
			return "";
		} else {
			return "" + usertest;
		}
	}
	public void setUsertest(String value) {
		usertest = value;
	}



	public String getHeatmap() {
		if (heatmap == null) {
			return "";
		} else {
			return "" + heatmap;
		}
	}
	public void setHeatmap(String value) {
		heatmap = value;
	}



	public String getHeatmapAlign() {
		if (heatmapalign == null) {
			return "";
		} else {
			return "" + heatmapalign;
		}
	}
	public void setHeatmapAlign(String value) {
		heatmapalign = value;
	}



	public String getUsagelog() {
		if (usagelog == null) {
			return "";
		} else {
			return "" + usagelog;
		}
	}
	public void setUsagelog(String value) {
		usagelog = value;
	}



	public String getVersion() {
		if (version == null) {
			return "";
		} else {
			return "" + version;
		}
	}
	public void setVersion(String value) {
		version = value;
	}



	public String getVersionMaster() {
		if (version_master == null) {
			return "";
		} else {
			return "" + version_master;
		}
	}
	public void setVersionMaster(String value) {
		version_master = value;
	}



	public String getStatus() {
		if (status == null) {
			return "";
		} else {
			return "" + status;
		}
	}
	public void setStatus(String value) {
		status = value;
	}



	public String getStatusBy() {
		if (status_by == null) {
			return "";
		} else {
			return status_by;
		}
	}
	public void setStatusBy(String value) {
		status_by = "" + value;
	}



	public String getTitle() {
		if (title == null) {
			return "";
		} else {
			return "" + title;
		}
	}
	public void setTitle(String value) {
		title = value;
	}



	public String getTitlePrefix() {
		if (title_prefix == null) {
			return "";
		} else {
			return "" + title_prefix;
		}
	}
	public void setTitlePrefix(String value) {
		title_prefix = value;
	}



	public String getTitleSuffix() {
		if (title_suffix == null) {
			return "";
		} else {
			return "" + title_suffix;
		}
	}
	public void setTitleSuffix(String value) {
		title_suffix = value;
	}



	public String getSearchable() {
		if (searchable == null) {
			return "";
		} else {
			return "" + searchable;
		}
	}
	public void setSearchable(String value) {
		searchable = value;
	}



	public String getMenuitem() {
		if (menuitem == null) {
			return "";
		} else {
			return "" + menuitem;
		}
	}
	public void setMenuitem(String value) {
		menuitem = value;
	}



	public String getAuthor() {
		if (author == null) {
			return "";
		} else {
			return "" + author;
		}
	}
	public void setAuthor(String value) {
		author = value;
	}



	public String getKeywords() {
		if (keywords == null) {
			return "";
		} else {
			return "" + keywords;
		}
	}
	public void setKeywords(String value) {
		keywords = value;
	}



	public String getDescription() {
		if (description == null) {
			return "";
		} else {
			return "" + description;
		}
	}
	public void setDescription(String value) {
		description = value;
	}



	public String getSummary() {
		if (summary == null) {
			return "";
		} else {
			return "" + summary;
		}
	}
	public void setSummary(String value) {
		summary = value;
	}



	public String getImage1() {
		if (image1 == null) {
			return "";
		} else {
			return "" + image1;
		}
	}
	public void setImage1(String value) {
		image1 = value;
	}



	public String getImage2() {
		if (image2 == null) {
			return "";
		} else {
			return "" + image2;
		}
	}
	public void setImage2(String value) {
		image2 = value;
	}



	public String getImage3() {
		if (image3 == null) {
			return "";
		} else {
			return "" + image3;
		}
	}
	public void setImage3(String value) {
		image3 = value;
	}



	public String getFile1() {
		if (file1 == null) {
			return "";
		} else {
			return "" + file1;
		}
	}
	public void setFile1(String value) {
		file1 = value;
	}



	public String getFile2() {
		if (file2 == null) {
			return "";
		} else {
			return "" + file2;
		}
	}
	public void setFile2(String value) {
		file2 = value;
	}



	public String getFile3() {
		if (file3 == null) {
			return "";
		} else {
			return "" + file3;
		}
	}
	public void setFile3(String value) {
		file3 = value;
	}



	public String getLink1() {
		if (link1 == null) {
			return "";
		} else {
			return "" + link1;
		}
	}
	public void setLink1(String value) {
		link1 = value;
	}



	public String getLink2() {
		if (link2 == null) {
			return "";
		} else {
			return "" + link2;
		}
	}
	public void setLink2(String value) {
		link2 = value;
	}



	public String getLink3() {
		if (link3 == null) {
			return "";
		} else {
			return "" + link3;
		}
	}
	public void setLink3(String value) {
		link3 = value;
	}



	public String getMetaInfo() {
		if (metainfo == null) {
			return "";
		} else {
			return "" + metainfo;
		}
	}
	public void setMetaInfo(String value) {
		metainfo = value;
	}
	public String getMetaInfo(String metainfoname) {
		String value = "";
		Matcher matches = Pattern.compile("^<\\Q" + metainfoname + "\\E>(.*?)</\\Q" + metainfoname + "\\E>$", Pattern.MULTILINE).matcher(metainfo);
		Matcher matches2 = Pattern.compile("^<_\\Q" + metainfoname + "\\E>(.*?)</_\\Q" + metainfoname + "\\E>$", Pattern.MULTILINE).matcher(metainfo);
		if (matches.find()) {
			value = "" + matches.group(1);
		} else if (matches2.find()) {
			value = "" + matches2.group(1);
		}
		return value;
	}
	public HashMap<String,String> getMetaInfos() {
		HashMap<String,String> values = new HashMap<String,String>();
		String[] myinfos = getMetaInfo().split("[\r\n]+");
		for (int j=0; j<myinfos.length; j++) {
			String myinfo = myinfos[j];
			Matcher myinfoMatches = Pattern.compile("<([^<>]+)>(.*?)</([^<>]+)>").matcher(myinfo);
			if (myinfoMatches.find()) {
				String myname = "" + myinfoMatches.group(1);
				String myvalue = "" + myinfoMatches.group(2);
				values.put(myname, myvalue);
			}
		}
		return values;
	}



	public String getSegmentation() {
		if (segmentation == null) {
			return "";
		} else {
			return "" + segmentation;
		}
	}
	public void setSegmentation(String value) {
		segmentation = value;
	}
	public String getSegmentation(String segmentationname) {
		String value = "";
		Matcher matches = Pattern.compile("^<\\Q" + segmentationname + "\\E>(.*?)</\\Q" + segmentationname + "\\E>$", Pattern.MULTILINE).matcher(segmentation);
		if (matches.find()) {
			value = "" + matches.group(1);
		}
		return value;
	}
	public HashMap<String,String> getSegmentations() {
		HashMap<String,String> values = new HashMap<String,String>();
		String[] mysegmentations = getSegmentation().split("[\r\n]+");
		for (int j=0; j<mysegmentations.length; j++) {
			String mysegmentation = mysegmentations[j];
			Matcher mysegmentationMatches = Pattern.compile("<([^<>]+)>(.*?)</([^<>]+)>").matcher(mysegmentation);
			if (mysegmentationMatches.find()) {
				String myname = "" + mysegmentationMatches.group(1);
				String myvalue = "" + mysegmentationMatches.group(2);
				values.put(myname, myvalue);
			}
		}
		return values;
	}



	public String getHtmlAttributes() {
		if (htmlattributes == null) {
			return "";
		} else {
			return "" + htmlattributes;
		}
	}
	public void setHtmlAttributes(String value) {
		htmlattributes = value;
	}



	public String getHtmlHeader() {
		if (htmlheader == null) {
			return "";
		} else {
			return "" + htmlheader;
		}
	}
	public void setHtmlHeader(String value) {
		htmlheader = value;
	}



	public String getHtmlBodyOnload() {
		if (htmlbodyonload == null) {
			return "";
		} else {
			return "" + htmlbodyonload;
		}
	}
	public void setHtmlBodyOnload(String value) {
		htmlbodyonload = value;
	}



	public String getUrl() {
		if (url == null) {
			return "";
		} else {
			return "" + url;
		}
	}
	public void setUrl(String value) {
		url = value;
	}



	public String getContentFormat() {
		if (contentformat == null) {
			return "";
		} else {
			return "" + contentformat;
		}
	}
	public void setContentFormat(String value) {
		contentformat = value;
	}



	public String getContent() {
		if (content == null) {
			return "";
		} else {
			return "" + content;
		}
	}
	public void setContent(String value) {
		content = value;
	}



	public String getDocType() {
		if (doctype == null) {
			return "";
		} else {
			return "" + doctype;
		}
	}
	public void setDocType(String value) {
		doctype = value;
	}



	public String getTemplate() {
		if (template == null) {
			return "";
		} else {
			return "" + template;
		}
	}
	public void setTemplate(String value) {
		template = value;
	}



	public String getStyleSheet() {
		if (stylesheet == null) {
			return "";
		} else {
			return "" + stylesheet;
		}
	}
	public void setStyleSheet(String value) {
		stylesheet = value;
	}



	public String getScripts() {
		if (scripts == null) {
			return "";
		} else {
			return "" + scripts;
		}
	}
	public void setScripts(String value) {
		scripts = value;
	}



	public String getContentPackage() {
		if (contentpackage == null) {
			return "";
		} else {
			return "" + contentpackage;
		}
	}
	public void setContentPackage(String value) {
		contentpackage = value;
	}



	public String getContentBundle() {
		if (contentbundle == null) {
			return "";
		} else {
			return "" + contentbundle;
		}
	}
	public void setContentBundle(String value) {
		contentbundle = value;
	}



	public String getContentClass() {
		if (contentclass == null) {
			return "";
		} else {
			return "" + contentclass;
		}
	}
	public void setContentClass(String value) {
		contentclass = value;
	}



	public String getContentType() {
		if (contenttype == null) {
			return "";
		} else {
			return "" + contenttype;
		}
	}
	public void setContentType(String value) {
		contenttype = value;
	}



	public String getContentGroup() {
		if (contentgroup == null) {
			return "";
		} else {
			return "" + contentgroup;
		}
	}
	public void setContentGroup(String value) {
		contentgroup = value;
	}



	public String getUsersUsers() {
		if (users_users == null) {
			return "";
		} else {
			return "" + users_users;
		}
	}
	public void setUsersUsers(String value) {
		users_users = value;
	}



	public String getUsersType() {
		if (users_type == null) {
			return "";
		} else {
			return "" + users_type;
		}
	}
	public void setUsersType(String value) {
		users_type = value;
	}



	public String getUsersGroup() {
		if (users_group == null) {
			return "";
		} else {
			return "" + users_group;
		}
	}
	public void setUsersGroup(String value) {
		users_group = value;
	}



	public String getCreatorsUsers() {
		if (creators_users == null) {
			return "";
		} else {
			return "" + creators_users;
		}
	}
	public void setCreatorsUsers(String value) {
		creators_users = value;
	}



	public String getCreatorsType() {
		if (creators_type == null) {
			return "";
		} else {
			return "" + creators_type;
		}
	}
	public void setCreatorsType(String value) {
		creators_type = value;
	}



	public String getCreatorsGroup() {
		if (creators_group == null) {
			return "";
		} else {
			return "" + creators_group;
		}
	}
	public void setCreatorsGroup(String value) {
		creators_group = value;
	}



	public String getDevelopersUsers() {
		if (developers_users == null) {
			return "";
		} else {
			return "" + developers_users;
		}
	}
	public void setDevelopersUsers(String value) {
		developers_users = value;
	}



	public String getDevelopersType() {
		if (developers_type == null) {
			return "";
		} else {
			return "" + developers_type;
		}
	}
	public void setDevelopersType(String value) {
		developers_type = value;
	}



	public String getDevelopersGroup() {
		if (developers_group == null) {
			return "";
		} else {
			return "" + developers_group;
		}
	}
	public void setDevelopersGroup(String value) {
		developers_group = value;
	}



	public String getEditorsUsers() {
		if (editors_users == null) {
			return "";
		} else {
			return "" + editors_users;
		}
	}
	public void setEditorsUsers(String value) {
		editors_users = value;
	}



	public String getEditorsType() {
		if (editors_type == null) {
			return "";
		} else {
			return "" + editors_type;
		}
	}
	public void setEditorsType(String value) {
		editors_type = value;
	}



	public String getEditorsGroup() {
		if (editors_group == null) {
			return "";
		} else {
			return "" + editors_group;
		}
	}
	public void setEditorsGroup(String value) {
		editors_group = value;
	}



	public String getPublishersUsers() {
		if (publishers_users == null) {
			return "";
		} else {
			return "" + publishers_users;
		}
	}
	public void setPublishersUsers(String value) {
		publishers_users = value;
	}



	public String getPublishersType() {
		if (publishers_type == null) {
			return "";
		} else {
			return "" + publishers_type;
		}
	}
	public void setPublishersType(String value) {
		publishers_type = value;
	}



	public String getPublishersGroup() {
		if (publishers_group == null) {
			return "";
		} else {
			return "" + publishers_group;
		}
	}
	public void setPublishersGroup(String value) {
		publishers_group = value;
	}



	public String getAdministratorsUsers() {
		if (administrators_users == null) {
			return "";
		} else {
			return "" + administrators_users;
		}
	}
	public void setAdministratorsUsers(String value) {
		administrators_users = value;
	}



	public String getAdministratorsType() {
		if (administrators_type == null) {
			return "";
		} else {
			return "" + administrators_type;
		}
	}
	public void setAdministratorsType(String value) {
		administrators_type = value;
	}



	public String getAdministratorsGroup() {
		if (administrators_group == null) {
			return "";
		} else {
			return "" + administrators_group;
		}
	}
	public void setAdministratorsGroup(String value) {
		administrators_group = value;
	}



	public String getCheckedOut() {
		if (checkedout == null) {
			return "";
		} else {
			return "" + checkedout;
		}
	}
	public void setCheckedOut(String value) {
		checkedout = value;
	}



	public String getPageUp() {
		if (page_up == null) {
			return "";
		} else {
			return "" + page_up;
		}
	}
	public void setPageUp(String value) {
		page_up = value;
	}



	public String getPageTop() {
		if (page_top == null) {
			return "";
		} else {
			return "" + page_top;
		}
	}
	public void setPageTop(String value) {
		page_top = value;
	}



	public String getPagePrevious() {
		if (page_previous == null) {
			return "";
		} else {
			return "" + page_previous;
		}
	}
	public void setPagePrevious(String value) {
		page_previous = value;
	}



	public String getPageNext() {
		if (page_next == null) {
			return "";
		} else {
			return "" + page_next;
		}
	}
	public void setPageNext(String value) {
		page_next = value;
	}



	public String getPageLast() {
		if (page_last == null) {
			return "";
		} else {
			return "" + page_last;
		}
	}
	public void setPageLast(String value) {
		page_last = value;
	}



	public String getPageFirst() {
		if (page_first == null) {
			return "";
		} else {
			return "" + page_first;
		}
	}
	public void setPageFirst(String value) {
		page_first = value;
	}



	public String getRelated() {
		if (related == null) {
			return "";
		} else {
			return "" + related;
		}
	}
	public void setRelated(String value) {
		related = value;
	}



	public String getUploadFilename() {
		if (upload_filename == null) {
			return "";
		} else {
			return "" + upload_filename;
		}
	}
	public void setUploadFilename(String value) {
		upload_filename = value;
	}



	public String getServerFilename() {
		if (server_filename == null) {
			return "";
		} else {
			return "" + server_filename;
		}
	}
	public void setServerFilename(String value) {
		server_filename = value;
	}



	public boolean getUser() {
		return user;
	}
	public void setUser(boolean value) {
		user = value;
	}



	public boolean getCreator() {
		return creator;
	}
	public void setCreator(boolean value) {
		creator = value;
	}



	public boolean getEditor() {
		return editor;
	}
	public void setEditor(boolean value) {
		editor = value;
	}



	public boolean getPublisher() {
		return publisher;
	}
	public void setPublisher(boolean value) {
		publisher = value;
	}



	public boolean getDeveloper() {
		return developer;
	}
	public void setDeveloper(boolean value) {
		developer = value;
	}



	public boolean getAdministrator() {
		return administrator;
	}
	public void setAdministrator(boolean value) {
		administrator = value;
	}



	public String getElement(String name) {
		if (element.get(name) != null) {
			return "" + element.get(name);
		} else {
			return "";
		}
	}
	public HashMap<String,String> getElement() {
		return element;
	}
	public void setElement(HashMap<String,String> value) {
		element = value;
	}



	public String getProductCode() {
		if (product_code == null) {
			return "";
		} else {
			return "" + product_code;
		}
	}
	public void setProductCode(String value) {
		product_code = value;
	}



	public String getProductCurrency() {
		if (product_currency == null) {
			return "";
		} else {
			return "" + product_currency;
		}
	}
	public void setProductCurrency(String value) {
		product_currency = value;
	}



	public String getProductCost() {
		if (product_cost == null) {
			return "";
		} else {
			return "" + product_cost;
		}
	}
	public void setProductCost(String value) {
		product_cost = value;
	}



	public String getProductPrice() {
		if (product_price == null) {
			return "";
		} else {
			return "" + product_price;
		}
	}
	public void setProductPrice(String value) {
		product_price = value;
	}



	public String getProductPeriod() {
		if (product_period == null) {
			return "";
		} else {
			return "" + product_period;
		}
	}
	public void setProductPeriod(String value) {
		product_period = value;
	}



	public String getProductWeight() {
		if (product_weight == null) {
			return "";
		} else {
			return "" + product_weight;
		}
	}
	public void setProductWeight(String value) {
		product_weight = value;
	}



	public String getProductVolume() {
		if (product_volume == null) {
			return "";
		} else {
			return "" + product_volume;
		}
	}
	public void setProductVolume(String value) {
		product_volume = value;
	}



	public String getProductWidth() {
		if (product_width == null) {
			return "";
		} else {
			return "" + product_width;
		}
	}
	public void setProductWidth(String value) {
		product_width = value;
	}



	public String getProductHeight() {
		if (product_height == null) {
			return "";
		} else {
			return "" + product_height;
		}
	}
	public void setProductHeight(String value) {
		product_height = value;
	}



	public String getProductDepth() {
		if (product_depth == null) {
			return "";
		} else {
			return "" + product_depth;
		}
	}
	public void setProductDepth(String value) {
		product_depth = value;
	}



	// DEPRECATED
	public String getProductStock() {
		if (product_stock == null) {
			return "";
		} else {
			return "" + product_stock;
		}
	}
	public void setProductStock(String value) {
		product_stock = value;
	}



	 // DEPRECATED
	public String getProductComment() {
		if (product_comment == null) {
			return "";
		} else {
			return "" + product_comment;
		}
	}
	public void setProductComment(String value) {
		product_comment = value;
	}



	public String getProductEmail() {
		if (product_email == null) {
			return "";
		} else {
			return "" + product_email;
		}
	}
	public void setProductEmail(String value) {
		product_email = value;
	}



	public String getProductURL() {
		if (product_url == null) {
			return "";
		} else {
			return "" + product_url;
		}
	}
	public void setProductURL(String value) {
		product_url = value;
	}



	public String getProductBrand() {
		if (product_brand == null) {
			return "";
		} else {
			return "" + product_brand;
		}
	}
	public void setProductBrand(String value) {
		product_brand = value;
	}



	public String getProductColour() {
		if (product_colour == null) {
			return "";
		} else {
			return "" + product_colour;
		}
	}
	public void setProductColour(String value) {
		product_colour = value;
	}



	public String getProductSize() {
		if (product_size == null) {
			return "";
		} else {
			return "" + product_size;
		}
	}
	public void setProductSize(String value) {
		product_size = value;
	}



	public String getProductInfo() {
		if (product_info == null) {
			return "";
		} else {
			return "" + product_info;
		}
	}
	public void setProductInfo(String value) {
		product_info = value;
	}
	public String getProductInfo(String productinfoname) {
		String value = "";
		Matcher matches = Pattern.compile("^<\\Q" + productinfoname + "\\E>(.*?)</\\Q" + productinfoname + "\\E>$", Pattern.MULTILINE).matcher(product_info);
		Matcher matches2 = Pattern.compile("^<_\\Q" + productinfoname + "\\E>(.*?)</_\\Q" + productinfoname + "\\E>$", Pattern.MULTILINE).matcher(product_info);
		if (matches.find()) {
			value = "" + matches.group(1);
		} else if (matches2.find()) {
			value = "" + matches2.group(1);
		}
		return value;
	}
	public HashMap<String,String> getProductInfos() {
		HashMap<String,String> values = new HashMap<String,String>();
		String[] myinfos = getProductInfo().split("[\r\n]+");
		for (int j=0; j<myinfos.length; j++) {
			String myinfo = myinfos[j];
			Matcher myinfoMatches = Pattern.compile("<([^<>]+)>(.*?)</([^<>]+)>").matcher(myinfo);
			if (myinfoMatches.find()) {
				String myname = "" + myinfoMatches.group(1);
				String myvalue = "" + myinfoMatches.group(2);
				values.put(myname, myvalue);
			}
		}
		return values;
	}



	public String getProductOptions() {
		if (product_options == null) {
			return "";
		} else {
			return "" + product_options;
		}
	}
	public void setProductOptions(String value) {
		product_options = value;
	}



	public String getProductDelivery() {
		if (product_delivery == null) {
			return "";
		} else {
			return "" + product_delivery;
		}
	}
	public void setProductDelivery(String value) {
		product_delivery = value;
	}



	public String getProductStockAmount(DB db) {
		if ((! getVersionMaster().equals("")) && (! getVersionMaster().equals("0"))) {
			if (exists(db, ""+Common.intnumber(getVersionMaster()), "content_public", "id")) {
				return "" + Common.integernumber(db.query_value("select product_stock_amount from content_public where id=" + Common.intnumber(getVersionMaster())));
			} else {
				return "" + Common.integernumber(db.query_value("select product_stock_amount from content where id=" + Common.intnumber(getVersionMaster())));
			}
		} else if (! id.equals("")) {
			if (exists(db, ""+Common.intnumber(id), "content_public", "id")) {
				return "" + Common.integernumber(db.query_value("select product_stock_amount from content_public where id=" + Common.intnumber(id)));
			} else {
				return "" + Common.integernumber(db.query_value("select product_stock_amount from content where id=" + Common.intnumber(id)));
			}
		} else {
			return "";
		}
	}
	public void setProductStockAmount(DB db, String value) {
		setProductStockAmount(db, Common.integernumber(value));
	}
	public void setProductStockAmount(DB db, long value) {
		if ((! getVersionMaster().equals("")) && (! getVersionMaster().equals("0"))) {
			db.execute("update content set product_stock_amount=" + value + " where id=" + Common.intnumber(getVersionMaster()));
			Cache.clear(db, "content");
			db.execute("update content_public set product_stock_amount=" + value + " where id=" + Common.intnumber(getVersionMaster()));
			Cache.clear(db, "content_public");
		} else if (! id.equals("")) {
			db.execute("update content set product_stock_amount=" + value + " where id=" + Common.intnumber(id));
			Cache.clear(db, "content_public");
			db.execute("update content_public set product_stock_amount=" + value + " where id=" + Common.intnumber(id));
			Cache.clear(db, "content_public");
		}
	}
	public void addProductStockAmount(DB db, String value) {
		addProductStockAmount(db, Common.integernumber(value));
	}
	public void addProductStockAmount(DB db, long value) {
		if ((! getVersionMaster().equals("")) && (! getVersionMaster().equals("0"))) {
			db.execute("update content set product_stock_amount=0 where product_stock_amount is null and id=" + Common.intnumber(getVersionMaster()));
			db.execute("update content set product_stock_amount=product_stock_amount+(" + value + ") where id=" + Common.intnumber(getVersionMaster()));
			Cache.clear(db, "content");
			db.execute("update content_public set product_stock_amount=0 where product_stock_amount is null and id=" + Common.intnumber(getVersionMaster()));
			db.execute("update content_public set product_stock_amount=product_stock_amount+(" + value + ") where id=" + Common.intnumber(getVersionMaster()));
			Cache.clear(db, "content_public");
		} else if (! id.equals("")) {
			db.execute("update content set product_stock_amount=0 where product_stock_amount is null and id=" + Common.intnumber(id));
			db.execute("update content set product_stock_amount=product_stock_amount+(" + value + ") where id=" + Common.intnumber(id));
			Cache.clear(db, "content");
			db.execute("update content_public set product_stock_amount=0 where product_stock_amount is null and id=" + Common.intnumber(id));
			db.execute("update content_public set product_stock_amount=product_stock_amount+(" + value + ") where id=" + Common.intnumber(id));
			Cache.clear(db, "content_public");
		}
	}



	public String getProductRestockedAmount(DB db) {
		if ((! getVersionMaster().equals("")) && (! getVersionMaster().equals("0"))) {
			if (exists(db, ""+Common.intnumber(getVersionMaster()), "content_public", "id")) {
				return "" + Common.integernumber(db.query_value("select product_restocked_amount from content_public where id=" + Common.intnumber(getVersionMaster())));
			} else {
				return "" + Common.integernumber(db.query_value("select product_restocked_amount from content where id=" + Common.intnumber(getVersionMaster())));
			}
		} else if (! id.equals("")) {
			if (exists(db, ""+Common.intnumber(id), "content_public", "id")) {
				return "" + Common.integernumber(db.query_value("select product_restocked_amount from content_public where id=" + Common.intnumber(id)));
			} else {
				return "" + Common.integernumber(db.query_value("select product_restocked_amount from content where id=" + Common.intnumber(id)));
			}
		} else {
			return "";
		}
	}
	public void setProductRestockedAmount(DB db, String value) {
		setProductRestockedAmount(db, Common.integernumber(value));
	}
	public void setProductRestockedAmount(DB db, long value) {
		if ((! getVersionMaster().equals("")) && (! getVersionMaster().equals("0"))) {
			db.execute("update content set product_restocked_amount=" + value + " where id=" + Common.intnumber(getVersionMaster()));
			Cache.clear(db, "content");
			db.execute("update content_public set product_restocked_amount=" + value + " where id=" + Common.intnumber(getVersionMaster()));
			Cache.clear(db, "content_public");
		} else if (! id.equals("")) {
			db.execute("update content set product_restocked_amount=" + value + " where id=" + Common.intnumber(id));
			Cache.clear(db, "content_public");
			db.execute("update content_public set product_restocked_amount=" + value + " where id=" + Common.intnumber(id));
			Cache.clear(db, "content_public");
		}
	}
	public void addProductRestockedAmount(DB db, String value) {
		addProductRestockedAmount(db, Common.integernumber(value));
	}
	public void addProductRestockedAmount(DB db, long value) {
		if ((! getVersionMaster().equals("")) && (! getVersionMaster().equals("0"))) {
			db.execute("update content set product_restocked_amount=0 where product_restocked_amount is null and id=" + Common.intnumber(getVersionMaster()));
			db.execute("update content set product_restocked_amount=product_restocked_amount+(" + value + ") where id=" + Common.intnumber(getVersionMaster()));
			Cache.clear(db, "content");
			db.execute("update content_public set product_restocked_amount=0 where product_restocked_amount is null and id=" + Common.intnumber(getVersionMaster()));
			db.execute("update content_public set product_restocked_amount=product_restocked_amount+(" + value + ") where id=" + Common.intnumber(getVersionMaster()));
			Cache.clear(db, "content_public");
		} else if (! id.equals("")) {
			db.execute("update content set product_restocked_amount=0 where product_restocked_amount is null and id=" + Common.intnumber(id));
			db.execute("update content set product_restocked_amount=product_restocked_amount+(" + value + ") where id=" + Common.intnumber(id));
			Cache.clear(db, "content");
			db.execute("update content_public set product_restocked_amount=0 where product_restocked_amount is null and id=" + Common.intnumber(id));
			db.execute("update content_public set product_restocked_amount=product_restocked_amount+(" + value + ") where id=" + Common.intnumber(id));
			Cache.clear(db, "content_public");
		}
	}



	public String getProductStockText() {
		if (product_stock_text == null) {
			return "";
		} else {
			return "" + product_stock_text;
		}
	}
	public void setProductStockText(String value) {
		product_stock_text = value;
	}



	public String getProductLowStockAmount() {
		if (product_lowstock_amount == null) {
			return "";
		} else {
			return "" + product_lowstock_amount;
		}
	}
	public void setProductLowStockAmount(String value) {
		product_lowstock_amount = value;
	}



	public String getProductLowStockText() {
		if (product_lowstock_text == null) {
			return "";
		} else {
			return "" + product_lowstock_text;
		}
	}
	public void setProductLowStockText(String value) {
		product_lowstock_text = value;
	}



	public String getProductNoStockOrder() {
		if (product_nostock_order == null) {
			return "";
		} else {
			return "" + product_nostock_order;
		}
	}
	public void setProductNoStockOrder(String value) {
		product_nostock_order = value;
	}



	public String getProductNoStockText() {
		if (product_nostock_text == null) {
			return "";
		} else {
			return "" + product_nostock_text;
		}
	}
	public void setProductNoStockText(String value) {
		product_nostock_text = value;
	}



	public String getProductLocation() {
		if (product_location == null) {
			return "";
		} else {
			return "" + product_location;
		}
	}
	public void setProductLocation(String value) {
		product_location = value;
	}



	public String getProductUser() {
		if (product_user == null) {
			return "";
		} else {
			return "" + product_user;
		}
	}
	public void setProductUser(String value) {
		product_user = value;
	}



	public String getProductPage() {
		if (product_page == null) {
			return "";
		} else {
			return "" + product_page;
		}
	}
	public void setProductPage(String value) {
		product_page = value;
	}



	public String getProductProgram() {
		if (product_program == null) {
			return "";
		} else {
			return "" + product_program;
		}
	}
	public void setProductProgram(String value) {
		product_program = value;
	}



	public String getProductHosting() {
		if (product_hosting == null) {
			return "";
		} else {
			return "" + product_hosting;
		}
	}
	public void setProductHosting(String value) {
		product_hosting = value;
	}



	public String getProductContent() {
		if (product_content == null) {
			return "";
		} else {
			return "" + product_content;
		}
	}
	public void setProductContent(String value) {
		product_content = value;
	}



	public String getProductContentgroup() {
		if (product_contentgroup == null) {
			return "";
		} else {
			return "" + product_contentgroup;
		}
	}
	public void setProductContentgroup(String value) {
		product_contentgroup = value;
	}



	public String getProductContenttype() {
		if (product_contenttype == null) {
			return "";
		} else {
			return "" + product_contenttype;
		}
	}
	public void setProductContenttype(String value) {
		product_contenttype = value;
	}



	public String getProductImagegroup() {
		if (product_imagegroup == null) {
			return "";
		} else {
			return "" + product_imagegroup;
		}
	}
	public void setProductImagegroup(String value) {
		product_imagegroup = value;
	}



	public String getProductImagetype() {
		if (product_imagetype == null) {
			return "";
		} else {
			return "" + product_imagetype;
		}
	}
	public void setProductImagetype(String value) {
		product_imagetype = value;
	}



	public String getProductFilegroup() {
		if (product_filegroup == null) {
			return "";
		} else {
			return "" + product_filegroup;
		}
	}
	public void setProductFilegroup(String value) {
		product_filegroup = value;
	}



	public String getProductFiletype() {
		if (product_filetype == null) {
			return "";
		} else {
			return "" + product_filetype;
		}
	}
	public void setProductFiletype(String value) {
		product_filetype = value;
	}



	public String getProductLinkgroup() {
		if (product_linkgroup == null) {
			return "";
		} else {
			return "" + product_linkgroup;
		}
	}
	public void setProductLinkgroup(String value) {
		product_linkgroup = value;
	}



	public String getProductLinktype() {
		if (product_linktype == null) {
			return "";
		} else {
			return "" + product_linktype;
		}
	}
	public void setProductLinktype(String value) {
		product_linktype = value;
	}



	public String getProductProductgroup() {
		if (product_productgroup == null) {
			return "";
		} else {
			return "" + product_productgroup;
		}
	}
	public void setProductProductgroup(String value) {
		product_productgroup = value;
	}



	public String getProductProducttype() {
		if (product_producttype == null) {
			return "";
		} else {
			return "" + product_producttype;
		}
	}
	public void setProductProducttype(String value) {
		product_producttype = value;
	}



	public String getProductUsergroup() {
		if (product_usergroup == null) {
			return "";
		} else {
			return "" + product_usergroup;
		}
	}
	public void setProductUsergroup(String value) {
		product_usergroup = value;
	}



	public String getProductUsertype() {
		if (product_usertype == null) {
			return "";
		} else {
			return "" + product_usertype;
		}
	}
	public void setProductUsertype(String value) {
		product_usertype = value;
	}



	public HashMap<String,Content[]> getElementContent() {
		return element_content;
	}



	public String getContentgroupUsersUsers() {
		if (contentgroup_users_users == null) {
			return "";
		} else {
			return "" + contentgroup_users_users;
		}
	}
	public void setContentgroupUsersUsers(String value) {
		contentgroup_users_users = value;
	}



	public String getContentgroupUsersType() {
		if (contentgroup_users_type == null) {
			return "";
		} else {
			return "" + contentgroup_users_type;
		}
	}
	public void setContentgroupUsersType(String value) {
		contentgroup_users_type = value;
	}



	public String getContentgroupUsersGroup() {
		if (contentgroup_users_group == null) {
			return "";
		} else {
			return "" + contentgroup_users_group;
		}
	}
	public void setContentgroupUsersGroup(String value) {
		contentgroup_users_group = value;
	}



	public String getContentgroupCreatorsUsers() {
		if (contentgroup_creators_users == null) {
			return "";
		} else {
			return "" + contentgroup_creators_users;
		}
	}
	public void setContentgroupCreatorsUsers(String value) {
		contentgroup_creators_users = value;
	}



	public String getContentgroupCreatorsType() {
		if (contentgroup_creators_type == null) {
			return "";
		} else {
			return "" + contentgroup_creators_type;
		}
	}
	public void setContentgroupCreatorsType(String value) {
		contentgroup_creators_type = value;
	}



	public String getContentgroupCreatorsGroup() {
		if (contentgroup_creators_group == null) {
			return "";
		} else {
			return "" + contentgroup_creators_group;
		}
	}
	public void setContentgroupCreatorsGroup(String value) {
		contentgroup_creators_group = value;
	}



	public String getContentgroupDevelopersUsers() {
		if (contentgroup_developers_users == null) {
			return "";
		} else {
			return "" + contentgroup_developers_users;
		}
	}
	public void setContentgroupDevelopersUsers(String value) {
		contentgroup_developers_users = value;
	}



	public String getContentgroupDevelopersType() {
		if (contentgroup_developers_type == null) {
			return "";
		} else {
			return "" + contentgroup_developers_type;
		}
	}
	public void setContentgroupDevelopersType(String value) {
		contentgroup_developers_type = value;
	}



	public String getContentgroupDevelopersGroup() {
		if (contentgroup_developers_group == null) {
			return "";
		} else {
			return "" + contentgroup_developers_group;
		}
	}
	public void setContentgroupDevelopersGroup(String value) {
		contentgroup_developers_group = value;
	}



	public String getContentgroupEditorsUsers() {
		if (contentgroup_editors_users == null) {
			return "";
		} else {
			return "" + contentgroup_editors_users;
		}
	}
	public void setContentgroupEditorsUsers(String value) {
		contentgroup_editors_users = value;
	}



	public String getContentgroupEditorsType() {
		if (contentgroup_editors_type == null) {
			return "";
		} else {
			return "" + contentgroup_editors_type;
		}
	}
	public void setContentgroupEditorsType(String value) {
		contentgroup_editors_type = value;
	}



	public String getContentgroupEditorsGroup() {
		if (contentgroup_editors_group == null) {
			return "";
		} else {
			return "" + contentgroup_editors_group;
		}
	}
	public void setContentgroupEditorsGroup(String value) {
		contentgroup_editors_group = value;
	}



	public String getContentgroupPublishersUsers() {
		if (contentgroup_publishers_users == null) {
			return "";
		} else {
			return "" + contentgroup_publishers_users;
		}
	}
	public void setContentgroupPublishersUsers(String value) {
		contentgroup_publishers_users = value;
	}



	public String getContentgroupPublishersType() {
		if (contentgroup_publishers_type == null) {
			return "";
		} else {
			return "" + contentgroup_publishers_type;
		}
	}
	public void setContentgroupPublishersType(String value) {
		contentgroup_publishers_type = value;
	}



	public String getContentgroupPublishersGroup() {
		if (contentgroup_publishers_group == null) {
			return "";
		} else {
			return "" + contentgroup_publishers_group;
		}
	}
	public void setContentgroupPublishersGroup(String value) {
		contentgroup_publishers_group = value;
	}



	public String getContentgroupAdministratorsUsers() {
		if (contentgroup_administrators_users == null) {
			return "";
		} else {
			return "" + contentgroup_administrators_users;
		}
	}
	public void setContentgroupAdministratorsUsers(String value) {
		contentgroup_administrators_users = value;
	}



	public String getContentgroupAdministratorsType() {
		if (contentgroup_administrators_type == null) {
			return "";
		} else {
			return "" + contentgroup_administrators_type;
		}
	}
	public void setContentgroupAdministratorsType(String value) {
		contentgroup_administrators_type = value;
	}



	public String getContentgroupAdministratorsGroup() {
		if (contentgroup_administrators_group == null) {
			return "";
		} else {
			return "" + contentgroup_administrators_group;
		}
	}
	public void setContentgroupAdministratorsGroup(String value) {
		contentgroup_administrators_group = value;
	}



	public String getContenttypeUsersUsers() {
		if (contenttype_users_users == null) {
			return "";
		} else {
			return "" + contenttype_users_users;
		}
	}
	public void setContenttypeUsersUsers(String value) {
		contenttype_users_users = value;
	}



	public String getContenttypeUsersType() {
		if (contenttype_users_type == null) {
			return "";
		} else {
			return "" + contenttype_users_type;
		}
	}
	public void setContenttypeUsersType(String value) {
		contenttype_users_type = value;
	}



	public String getContenttypeUsersGroup() {
		if (contenttype_users_group == null) {
			return "";
		} else {
			return "" + contenttype_users_group;
		}
	}
	public void setContenttypeUsersGroup(String value) {
		contenttype_users_group = value;
	}



	public String getContenttypeCreatorsUsers() {
		if (contenttype_creators_users == null) {
			return "";
		} else {
			return "" + contenttype_creators_users;
		}
	}
	public void setContenttypeCreatorsUsers(String value) {
		contenttype_creators_users = value;
	}



	public String getContenttypeCreatorsType() {
		if (contenttype_creators_type == null) {
			return "";
		} else {
			return "" + contenttype_creators_type;
		}
	}
	public void setContenttypeCreatorsType(String value) {
		contenttype_creators_type = value;
	}



	public String getContenttypeCreatorsGroup() {
		if (contenttype_creators_group == null) {
			return "";
		} else {
			return "" + contenttype_creators_group;
		}
	}
	public void setContenttypeCreatorsGroup(String value) {
		contenttype_creators_group = value;
	}



	public String getContenttypeDevelopersUsers() {
		if (contenttype_developers_users == null) {
			return "";
		} else {
			return "" + contenttype_developers_users;
		}
	}
	public void setContenttypeDevelopersUsers(String value) {
		contenttype_developers_users = value;
	}



	public String getContenttypeDevelopersType() {
		if (contenttype_developers_type == null) {
			return "";
		} else {
			return "" + contenttype_developers_type;
		}
	}
	public void setContenttypeDevelopersType(String value) {
		contenttype_developers_type = value;
	}



	public String getContenttypeDevelopersGroup() {
		if (contenttype_developers_group == null) {
			return "";
		} else {
			return "" + contenttype_developers_group;
		}
	}
	public void setContenttypeDevelopersGroup(String value) {
		contenttype_developers_group = value;
	}



	public String getContenttypeEditorsUsers() {
		if (contenttype_editors_users == null) {
			return "";
		} else {
			return "" + contenttype_editors_users;
		}
	}
	public void setContenttypeEditorsUsers(String value) {
		contenttype_editors_users = value;
	}



	public String getContenttypeEditorsType() {
		if (contenttype_editors_type == null) {
			return "";
		} else {
			return "" + contenttype_editors_type;
		}
	}
	public void setContenttypeEditorsType(String value) {
		contenttype_editors_type = value;
	}



	public String getContenttypeEditorsGroup() {
		if (contenttype_editors_group == null) {
			return "";
		} else {
			return "" + contenttype_editors_group;
		}
	}
	public void setContenttypeEditorsGroup(String value) {
		contenttype_editors_group = value;
	}



	public String getContenttypePublishersUsers() {
		if (contenttype_publishers_users == null) {
			return "";
		} else {
			return "" + contenttype_publishers_users;
		}
	}
	public void setContenttypePublishersUsers(String value) {
		contenttype_publishers_users = value;
	}



	public String getContenttypePublishersType() {
		if (contenttype_publishers_type == null) {
			return "";
		} else {
			return "" + contenttype_publishers_type;
		}
	}
	public void setContenttypePublishersType(String value) {
		contenttype_publishers_type = value;
	}



	public String getContenttypePublishersGroup() {
		if (contenttype_publishers_group == null) {
			return "";
		} else {
			return "" + contenttype_publishers_group;
		}
	}
	public void setContenttypePublishersGroup(String value) {
		contenttype_publishers_group = value;
	}



	public String getContenttypeAdministratorsUsers() {
		if (contenttype_administrators_users == null) {
			return "";
		} else {
			return "" + contenttype_administrators_users;
		}
	}
	public void setContenttypeAdministratorsUsers(String value) {
		contenttype_administrators_users = value;
	}



	public String getContenttypeAdministratorsType() {
		if (contenttype_administrators_type == null) {
			return "";
		} else {
			return "" + contenttype_administrators_type;
		}
	}
	public void setContenttypeAdministratorsType(String value) {
		contenttype_administrators_type = value;
	}



	public String getContenttypeAdministratorsGroup() {
		if (contenttype_administrators_group == null) {
			return "";
		} else {
			return "" + contenttype_administrators_group;
		}
	}
	public void setContenttypeAdministratorsGroup(String value) {
		contenttype_administrators_group = value;
	}



	public String getContentgroupDocType() {
		if (contentgroup_doctype == null) {
			return "";
		} else {
			return "" + contentgroup_doctype;
		}
	}
	public void setContentgroupDocType(String value) {
		contentgroup_doctype = value;
	}



	public String getContentgroupTemplate() {
		if (contentgroup_template == null) {
			return "";
		} else {
			return "" + contentgroup_template;
		}
	}
	public void setContentgroupTemplate(String value) {
		contentgroup_template = value;
	}



	public String getContentgroupStylesheet() {
		if (contentgroup_stylesheet == null) {
			return "";
		} else {
			return "" + contentgroup_stylesheet;
		}
	}
	public void setContentgroupStylesheet(String value) {
		contentgroup_stylesheet = value;
	}



	public String getContenttypeDocType() {
		if (contenttype_doctype == null) {
			return "";
		} else {
			return "" + contenttype_doctype;
		}
	}
	public void setContenttypeDocType(String value) {
		contenttype_doctype = value;
	}



	public String getContenttypeTemplate() {
		if (contenttype_template == null) {
			return "";
		} else {
			return "" + contenttype_template;
		}
	}
	public void setContenttypeTemplate(String value) {
		contenttype_template = value;
	}



	public String getContenttypeStylesheet() {
		if (contenttype_stylesheet == null) {
			return "";
		} else {
			return "" + contenttype_stylesheet;
		}
	}
	public void setContenttypeStylesheet(String value) {
		contenttype_stylesheet = value;
	}



	public boolean previewScheduled(Session mysession) {
		boolean preview = true;
		if (mysession.get("mode").equals("preview")) {
			if (! mysession.get("preview_scheduled").equals("")) {
				if (! getScheduledPublish().equals("")) {
					if (getScheduledPublish().compareTo(mysession.get("preview_scheduled")) > 0) {
						preview = false;
					}
				}
				if (! getScheduledUnpublish().equals("")) {
					if (getScheduledUnpublish().compareTo(mysession.get("preview_scheduled")) <= 0) {
						init();
					}
				}
			}
		}
		return preview;
	}



	public void previewScheduledQueued(Session session, Configuration config, DB db) {
		if (! session.get("preview_scheduled").equals("")) {
			String preview_id = getId();
			Content currentcontent = new Content();
			currentcontent.preview_read(db, config, preview_id, session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), session.get("version"), config.get(db, "default_version"), session.get("device"), session.get("usersegments"), session.get("usertests"), session);
			Content publishedcontent = new Content();
			publishedcontent.public_read(db, config, preview_id, session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), session.get("version"), config.get(db, "default_version"), session.get("device"), session.get("usersegments"), session.get("usertests"), session);
			if ((getScheduledPublish().equals("")) || (getScheduledPublish().compareTo(session.get("preview_scheduled"))>0)) {
				// current content is not scheduled or scheduled after preview datetime - ignore
				init();
			}
			if ((getScheduledPublish().equals("")) && (! publishedcontent.getId().equals(""))) {
				// use already published content
				public_read(db, config, publishedcontent.getId(), session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), session.get("version"), config.get(db, "default_version"), session.get("device"), session.get("usersegments"), session.get("usertests"), session);
			}
			Content scheduledcontent = new Content();
			String SQL = "";
			if (! getScheduledPublish().equals("")) {
				// check for queued scheduled published content newer than current scheduled / already published content
				SQL = "select * from content_archive where id=" + preview_id + " and published>=" + db.quote(Common.SQL_clean(getScheduledPublish())) + " and published<=" + db.quote(Common.SQL_clean(session.get("preview_scheduled"))) + " order by published desc";
			} else if (! getPublished().equals("")) {
				// check for queued scheduled published content newer than current scheduled / already published content
				SQL = "select * from content_archive where id=" + preview_id + " and published>=" + db.quote(Common.SQL_clean(getPublished())) + " and published<=" + db.quote(Common.SQL_clean(session.get("preview_scheduled"))) + " order by published desc";
			} else {
				// check for queued scheduled published content
				SQL = "select * from content_archive where id=" + preview_id + " and published<=" + db.quote(Common.SQL_clean(session.get("preview_scheduled"))) + " order by published desc";
			}
			scheduledcontent.records(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, SQL);
			if (scheduledcontent.records(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, "")) {
				archive_read(db, config, scheduledcontent.getArchiveId(), session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), session.get("version"), config.get(db, "default_version"), session.get("device"), session.get("usersegments"), session.get("usertests"), session);
			}
			scheduledcontent.closeRecords(db);
			if ((! getScheduledUnpublish().equals("")) && (getScheduledUnpublish().compareTo(session.get("preview_scheduled"))<0)) {
				// content is scheduled to expire before before preview datetime - ignore
				init();
			}
		}
	}



	public boolean getCheckoutPermissions(String username, DB db, Configuration myconfig) {
		if (_DEBUG_) { System.out.println("AsbruWCM/Content.getCheckoutPermissions:" + id + ":username:"+username + ":checkedout:"+getCheckedOut()); }
		boolean permissions;
		if (getCheckedOut().equals(username)) {
			// ok - checked out by this user
			permissions = true;
		} else if ((! getCheckedOut().equals("")) && (username.equals(myconfig.get(db, "superadmin")))) {
			// ok - superadmin
			permissions = true;
		} else if (getCheckedOut().equals("-creators-")) {
			if (getCreator()) {
				// ok - creator
				permissions = true;
			} else {
				// not creator
				permissions = false;
			}
		} else if (getCheckedOut().equals("-editors-")) {
			if (getEditor()) {
				// ok - editor
				permissions = true;
			} else {
				// not editor
				permissions = false;
			}
		} else if (getCheckedOut().equals("-developers-")) {
			if (getDeveloper()) {
				// ok - developer
				permissions = true;
			} else {
				// not developer
				permissions = false;
			}
		} else if (getCheckedOut().equals("-publishers-")) {
			if (getPublisher()) {
				// ok - publisher
				permissions = true;
			} else {
				// not publisher
				permissions = false;
			}
		} else if (getCheckedOut().equals("-administrators-")) {
			if (getAdministrator()) {
				// ok - administrator
				permissions = true;
			} else {
				// not administrator
				permissions = false;
			}
		} else if (! getCheckedOut().equals("")) {
			// checked out by other user
			permissions = false;
		} else {
			// ok - not checked out
			permissions = true;
		}
		if (_DEBUG_) { System.out.println("AsbruWCM/Content.getCheckoutPermissions:" + id + ":username:"+username + ":checkedout:"+getCheckedOut() + ":permissions:"+permissions); }
		return permissions;
	}



	public void setDisplayCurrency(DB db, String session_currency, String default_currency) {
		display_currencytitle = "";
		display_currency = "";
		display_price = "";
		if (session_currency.equals("")) {
			session_currency = "" + default_currency;
		}
		if (getProductCurrency().equals("")) {
			setProductCurrency(default_currency);
		}
		if (getProductCurrency().equals(session_currency)) {
			Currency my_product_currency = new Currency();
			my_product_currency.read(db, getProductCurrency());
			display_currencytitle = my_product_currency.getTitle();
			display_currency = my_product_currency.getSymbol();
			display_price = getProductPrice();
		} else {
			Currency my_product_currency = new Currency();
			my_product_currency.read(db, getProductCurrency());
			Currency my_session_currency = new Currency();
			my_session_currency.read(db, session_currency);
			display_currencytitle = my_session_currency.getTitle();
			display_currency = my_session_currency.getSymbol();
			double my_product_price;
			try {
				my_product_price = Double.parseDouble("0" + getProductPrice());
			} catch (Exception e) {
				my_product_price = 0.0;
			}
			double my_session_currency_rate;
			try {
				my_session_currency_rate = Double.parseDouble("0" + my_session_currency.getRate());
			} catch (Exception e) {
				my_session_currency_rate = 1.0;
			}
			double my_product_currency_rate;
			try {
				my_product_currency_rate = Double.parseDouble("0" + my_product_currency.getRate());
			} catch (Exception e) {
				my_product_currency_rate = 1.0;
			}
			display_price = "" + (my_product_price * my_session_currency_rate / my_product_currency_rate);
		}
	}



	public String getDisplayCurrencyTitle() {
		return display_currencytitle;
	}



	public String getDisplayCurrency() {
		return display_currency;
	}



	public String getDisplayPrice() {
		return display_price;
	}



}
