package HardCore;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.*;

public class Data {
	private String id = "";
	private HashMap<String,String> content = new HashMap<String,String>();
	private String created_by = "";
	private String users_users = "";
	private String users_type = "";
	private String users_group = "";
	private String creators_users = "";
	private String creators_type = "";
	private String creators_group = "";
	private String editors_users = "";
	private String editors_type = "";
	private String editors_group = "";
	private String publishers_users = "";
	private String publishers_type = "";
	private String publishers_group = "";
	private String administrators_users = "";
	private String administrators_type = "";
	private String administrators_group = "";
	private boolean user = false;
	private boolean creator = false;
	private boolean editor = false;
	private boolean publisher = false;
	private boolean administrator = false;

	private	Statement rs = null;
	static public boolean csv_options = false;



	public Data() {
		init();
	}



	public void init() {
		id = "";
		content = new HashMap<String,String>();
	}



	public void read(DB db, String database, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			String SQL = "select * from " + Common.SQL_clean(database) + " where id=" + Common.integer(id);
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void readTitle(DB db, String database, String title) {
		if (db == null) return;
		if (! title.equals("")) {
			String SQL = "select * from " + Common.SQL_clean(database) + " where title='" + Common.SQL_clean(title) + "'";
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void readWhere(DB db, String database, String column, String value) {
		if (db == null) return;
		if (! column.equals("")) {
			String SQL = "select * from " + Common.SQL_clean(database) + " where " + Common.SQL_clean(column) + "='" + Common.SQL_clean(value) + "'";
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void getRecord(DB db, HashMap<String,String> record) {
		Iterator columns = record.keySet().iterator();
		while (columns.hasNext()) {
			String column = "" + columns.next();
			if (column.equals("id")) {
				if (record.get("id") != null) {
					id = "" + record.get("id");
				} else {
					id = "";
				}
			} else {
				if (record.get(column) != null) {
					content.put(column, record.get(column));
				} else {
					content.put(column, "");
				}
			}
		}
	}



	public void getAccessRestrictions(Databases database, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config) {
		created_by = getCreatedBy(database.columns);
		users_users = database.getUsersUsers();
		users_type = database.getUsersType();
		users_group = database.getUsersGroup();
		creators_users = database.getCreatorsUsers();
		creators_type = database.getCreatorsType();
		creators_group = database.getCreatorsGroup();
		editors_users = database.getEditorsUsers();
		editors_type = database.getEditorsType();
		editors_group = database.getEditorsGroup();
		publishers_users = database.getPublishersUsers();
		publishers_type = database.getPublishersType();
		publishers_group = database.getPublishersGroup();
		administrators_users = database.getAdministratorsUsers();
		administrators_type = database.getAdministratorsType();
		administrators_group = database.getAdministratorsGroup();
		user = true;
		if ((users_type.equals("*")) && (session_username.equals(""))) {
			user = false;
		} else if ((users_type.equals("=")) && ((! session_username.equals(created_by)) || (session_username.equals("")))) {
			user = false;
		} else if ((! users_type.equals("*")) && (! users_type.equals("=")) && (! users_type.equals("")) && (! users_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + users_type + "|") >= 0))) {
			user = false;
		}
		if (config.get(db, "use_userdefinition").equals("yes")) {
			if ((users_group.equals("*")) && (session_username.equals(""))) {
				user = false;
			} else if ((users_group.equals("=")) && ((! session_username.equals(created_by)) || (session_username.equals("")))) {
				user = false;
			} else if ((! users_group.equals("*")) && (! users_group.equals("=")) && (! users_group.equals("")) && (! users_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + users_group + "|") >= 0))) {
				user = false;
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((users_type.equals("*")) || (users_group.equals("*"))) && (! session_username.equals(""))) {
					user = true;
				}
				if (((users_type.equals("=")) || (users_group.equals("="))) && (session_username.equals(created_by)) && (! session_username.equals(""))) {
					user = true;
				}
				if (((! users_type.equals("*")) && (! users_type.equals("=")) && (! users_type.equals("")) && ((users_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + users_type + "|") >= 0))) || ((! users_group.equals("*")) && (! users_group.equals("=")) && (! users_group.equals("")) && ((users_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + users_group + "|") >= 0)))) {
					user = true;
				}
			}
		}
		if (user) {
			if (! users_users.equals("")) {
				if (session_userid.equals("")) {
					user = false;
				} else if ((! users_users.equals(session_userid)) && (! Pattern.compile("(^|,)" + session_userid + "(,|$)").matcher(users_users).find())) {
					user = false;
				}
			}
		}
		if (user) {
			if ((config.get(db, "website_users_type").equals("*")) && (session_username.equals(""))) {
				user = false;
			} else if ((! config.get(db, "website_users_type").equals("*")) && (! config.get(db, "website_users_type").equals("=")) && (! config.get(db, "website_users_type").equals("")) && (! config.get(db, "website_users_type").equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_users_type") + "|") >= 0))) {
				user = false;
			}
			if (config.get(db, "use_userdefinition").equals("yes")) {
				if ((config.get(db, "website_users_group").equals("*")) && (session_username.equals(""))) {
					user = false;
				} else if ((! config.get(db, "website_users_group").equals("*")) && (! config.get(db, "website_users_group").equals("=")) && (! config.get(db, "website_users_group").equals("")) && (! config.get(db, "website_users_group").equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_users_group") + "|") >= 0))) {
					user = false;
				}
				if (config.get(db, "accessrestrictions").equals("or")) {
					if (((config.get(db, "website_users_type").equals("*")) || (config.get(db, "website_users_group").equals("*"))) && (! session_username.equals(""))) {
						user = true;
					}
					if (((! config.get(db, "website_users_type").equals("*")) && (! config.get(db, "website_users_type").equals("=")) && (! config.get(db, "website_users_type").equals("")) && ((config.get(db, "website_users_type").equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_users_type") + "|") >= 0))) || ((! config.get(db, "website_users_group").equals("*")) && (! config.get(db, "website_users_group").equals("=")) && (! config.get(db, "website_users_group").equals("")) && ((config.get(db, "website_users_group").equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_users_group") + "|") >= 0)))) {
						user = true;
					}
				}
			}
		}

		editor = true;
		if ((editors_type.equals("*")) && (session_username.equals(""))) {
			editor = false;
		} else if ((editors_type.equals("=")) && ((! session_username.equals(created_by)) || (session_username.equals("")))) {
			editor = false;
		} else if ((editors_type.equals("")) && (! session_administrator.equals("administrator"))) {
			editor = false;
		} else if ((! editors_type.equals("0")) && (! editors_type.equals("*")) && (! editors_type.equals("=")) && (! editors_type.equals("")) && (! editors_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + editors_type + "|") >= 0))) {
			editor = false;
		}
		if ((editors_group.equals("*")) && (session_username.equals(""))) {
			editor = false;
		} else if ((editors_group.equals("=")) && ((! session_username.equals(created_by)) || (session_username.equals("")))) {
			editor = false;
		} else if ((editors_group.equals("")) && (! session_administrator.equals("administrator"))) {
			editor = false;
		} else if ((! editors_group.equals("0")) && (! editors_group.equals("*")) && (! editors_group.equals("=")) && (! editors_group.equals("")) && (! editors_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + editors_group + "|") >= 0))) {
			editor = false;
		}
		if (config.get(db, "accessrestrictions").equals("or")) {
			if (((editors_type.equals("*")) || (editors_group.equals("*"))) && (! session_username.equals(""))) {
				editor = true;
			}
			if (((editors_type.equals("=")) || (editors_group.equals("="))) && (session_username.equals(created_by)) && (! session_username.equals(""))) {
				editor = true;
			}
			if (((editors_type.equals("")) || (editors_group.equals(""))) && (session_administrator.equals("administrator"))) {
				editor = true;
			}
			if (((! editors_type.equals("0")) && (! editors_type.equals("*")) && (! editors_type.equals("=")) && (! editors_type.equals("")) && ((editors_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + editors_type + "|") >= 0))) || ((! editors_group.equals("0")) && (! editors_group.equals("*")) && (! editors_group.equals("=")) && (! editors_group.equals("")) && ((editors_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + editors_group + "|") >= 0)))) {
				editor = true;
			}
		}
		if (editor) {
			if (! editors_users.equals("")) {
				if (session_userid.equals("")) {
					editor = false;
				} else if ((! editors_users.equals(session_userid)) && (! Pattern.compile("(^|,)" + session_userid + "(,|$)").matcher(editors_users).find())) {
					editor = false;
				}
			}
		}
		if (editor) {
			if ((config.get(db, "website_editors_type").equals("*")) && (session_username.equals(""))) {
				editor = false;
			} else if ((config.get(db, "website_editors_type").equals("0")) && (! session_administrator.equals("administrator"))) {
				editor = false;
			} else if ((! config.get(db, "website_editors_type").equals("0")) && (! config.get(db, "website_editors_type").equals("*")) && (! config.get(db, "website_editors_type").equals("=")) && (! config.get(db, "website_editors_type").equals("")) && (! config.get(db, "website_editors_type").equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_editors_type") + "|") >= 0))) {
				editor = false;
			}
			if ((config.get(db, "website_editors_group").equals("*")) && (session_username.equals(""))) {
				editor = false;
			} else if ((config.get(db, "website_editors_group").equals("0")) && (! session_administrator.equals("administrator"))) {
				editor = false;
			} else if ((! config.get(db, "website_editors_group").equals("0")) && (! config.get(db, "website_editors_group").equals("*")) && (! config.get(db, "website_editors_group").equals("=")) && (! config.get(db, "website_editors_group").equals("")) && (! config.get(db, "website_editors_group").equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_editors_group") + "|") >= 0))) {
				editor = false;
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((config.get(db, "website_editors_type").equals("*")) || (config.get(db, "website_editors_group").equals("*"))) && (! session_username.equals(""))) {
					editor = true;
				}
				if (((config.get(db, "website_editors_type").equals("0")) || (config.get(db, "website_editors_group").equals("0"))) && (session_administrator.equals("administrator"))) {
					editor = true;
				}
				if (((! config.get(db, "website_editors_type").equals("0")) && (! config.get(db, "website_editors_type").equals("*")) && (! config.get(db, "website_editors_type").equals("=")) && (! config.get(db, "website_editors_type").equals("")) && ((config.get(db, "website_editors_type").equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_editors_type") + "|") >= 0))) || ((! config.get(db, "website_editors_group").equals("0")) && (! config.get(db, "website_editors_group").equals("*")) && (! config.get(db, "website_editors_group").equals("=")) && (! config.get(db, "website_editors_group").equals("")) && ((config.get(db, "website_editors_group").equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_editors_group") + "|") >= 0)))) {
					editor = true;
				}
			}
		}

		creator = true;
		if ((creators_type.equals("*")) && (session_username.equals(""))) {
			creator = false;
		} else if ((creators_type.equals("=")) && ((! session_username.equals(created_by)) || (session_username.equals("")))) {
			creator = false;
		} else if ((creators_type.equals("")) && (! session_administrator.equals("administrator"))) {
			creator = false;
		} else if ((! creators_type.equals("0")) && (! creators_type.equals("*")) && (! creators_type.equals("=")) && (! creators_type.equals("")) && (! creators_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + creators_type + "|") >= 0))) {
			creator = false;
		}
		if (config.get(db, "use_userdefinition").equals("yes")) {
			if ((creators_group.equals("*")) && (session_username.equals(""))) {
				creator = false;
			} else if ((creators_group.equals("=")) && ((! session_username.equals(created_by)) || (session_username.equals("")))) {
				creator = false;
			} else if ((creators_group.equals("")) && (! session_administrator.equals("administrator"))) {
				creator = false;
			} else if ((! creators_group.equals("0")) && (! creators_group.equals("*")) && (! creators_group.equals("=")) && (! creators_group.equals("")) && (! creators_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + creators_group + "|") >= 0))) {
				creator = false;
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((creators_type.equals("*")) || (creators_group.equals("*"))) && (! session_username.equals(""))) {
					creator = true;
				}
				if (((creators_type.equals("=")) || (creators_group.equals("="))) && (session_username.equals(created_by)) && (! session_username.equals(""))) {
					creator = true;
				}
				if (((creators_type.equals("")) || (creators_group.equals(""))) && (session_administrator.equals("administrator"))) {
					creator = true;
				}
				if (((! creators_type.equals("0")) && (! creators_type.equals("*")) && (! creators_type.equals("=")) && (! creators_type.equals("")) && ((creators_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + creators_type + "|") >= 0))) || ((! creators_group.equals("0")) && (! creators_group.equals("*")) && (! creators_group.equals("=")) && (! creators_group.equals("")) && ((creators_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + creators_group + "|") >= 0)))) {
					creator = true;
				}
			}
		}
		if (creator) {
			if (! creators_users.equals("")) {
				if (session_userid.equals("")) {
					creator = false;
				} else if ((! creators_users.equals(session_userid)) && (! Pattern.compile("(^|,)" + session_userid + "(,|$)").matcher(creators_users).find())) {
					creator = false;
				}
			}
		}
		if (creator) {
			if ((config.get(db, "website_creators_type").equals("*")) && (session_username.equals(""))) {
				creator = false;
			} else if ((config.get(db, "website_creators_type").equals("0")) && (! session_administrator.equals("administrator"))) {
				creator = false;
			} else if ((! config.get(db, "website_creators_type").equals("0")) && (! config.get(db, "website_creators_type").equals("*")) && (! config.get(db, "website_creators_type").equals("=")) && (! config.get(db, "website_creators_type").equals("")) && (! config.get(db, "website_creators_type").equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_creators_type") + "|") >= 0))) {
				creator = false;
			}
			if ((config.get(db, "website_creators_group").equals("*")) && (session_username.equals(""))) {
				creator = false;
			} else if ((config.get(db, "website_creators_group").equals("0")) && (! session_administrator.equals("administrator"))) {
				creator = false;
			} else if ((! config.get(db, "website_creators_group").equals("0")) && (! config.get(db, "website_creators_group").equals("*")) && (! config.get(db, "website_creators_group").equals("=")) && (! config.get(db, "website_creators_group").equals("")) && (! config.get(db, "website_creators_group").equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_creators_group") + "|") >= 0))) {
				creator = false;
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((config.get(db, "website_creators_type").equals("*")) || (config.get(db, "website_creators_group").equals("*"))) && (! session_username.equals(""))) {
					creator = true;
				}
				if (((config.get(db, "website_creators_type").equals("0")) || (config.get(db, "website_creators_group").equals("0"))) && (session_administrator.equals("administrator"))) {
					creator = true;
				}
				if (((! config.get(db, "website_creators_type").equals("0")) && (! config.get(db, "website_creators_type").equals("*")) && (! config.get(db, "website_creators_type").equals("=")) && (! config.get(db, "website_creators_type").equals("")) && ((config.get(db, "website_creators_type").equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_creators_type") + "|") >= 0))) || ((! config.get(db, "website_creators_group").equals("0")) && (! config.get(db, "website_creators_group").equals("*")) && (! config.get(db, "website_creators_group").equals("=")) && (! config.get(db, "website_creators_group").equals("")) && ((config.get(db, "website_creators_group").equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_creators_group") + "|") >= 0)))) {
					creator = true;
				}
			}
		}

		publisher = true;
		if ((publishers_type.equals("*")) && (session_username.equals(""))) {
			publisher = false;
		} else if ((publishers_type.equals("=")) && ((! session_username.equals(created_by)) || (session_username.equals("")))) {
			publisher = false;
		} else if ((publishers_type.equals("")) && (! session_administrator.equals("administrator"))) {
			publisher = false;
		} else if ((! publishers_type.equals("0")) && (! publishers_type.equals("*")) && (! publishers_type.equals("=")) && (! publishers_type.equals("")) && (! publishers_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + publishers_type + "|") >= 0))) {
			publisher = false;
		}
		if ((publishers_group.equals("*")) && (session_username.equals(""))) {
			publisher = false;
		} else if ((publishers_group.equals("=")) && ((! session_username.equals(created_by)) || (session_username.equals("")))) {
			publisher = false;
		} else if ((publishers_group.equals("")) && (! session_administrator.equals("administrator"))) {
			publisher = false;
		} else if ((! publishers_group.equals("0")) && (! publishers_group.equals("*")) && (! publishers_group.equals("=")) && (! publishers_group.equals("")) && (! publishers_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + publishers_group + "|") >= 0))) {
			publisher = false;
		}
		if (config.get(db, "accessrestrictions").equals("or")) {
			if (((publishers_type.equals("*")) || (publishers_group.equals("*"))) && (! session_username.equals(""))) {
				publisher = true;
			}
			if (((publishers_type.equals("=")) || (publishers_group.equals("="))) && (session_username.equals(created_by)) && (! session_username.equals(""))) {
				publisher = true;
			}
			if (((publishers_type.equals("")) || (publishers_group.equals(""))) && (session_administrator.equals("administrator"))) {
				publisher = true;
			}
			if (((! publishers_type.equals("0")) && (! publishers_type.equals("*")) && (! publishers_type.equals("=")) && (! publishers_type.equals("")) && ((publishers_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + publishers_type + "|") >= 0))) || ((! publishers_group.equals("0")) && (! publishers_group.equals("*")) && (! publishers_group.equals("=")) && (! publishers_group.equals("")) && ((publishers_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + publishers_group + "|") >= 0)))) {
				publisher = true;
			}
		}
		if (publisher) {
			if (! publishers_users.equals("")) {
				if (session_userid.equals("")) {
					publisher = false;
				} else if ((! creators_users.equals(session_userid)) && (! Pattern.compile("(^|,)" + session_userid + "(,|$)").matcher(publishers_users).find())) {
					publisher = false;
				}
			}
		}
		if (publisher) {
			if ((config.get(db, "website_publishers_type").equals("*")) && (session_username.equals(""))) {
				publisher = false;
			} else if ((config.get(db, "website_publishers_type").equals("0")) && (! session_administrator.equals("administrator"))) {
				publisher = false;
			} else if ((! config.get(db, "website_publishers_type").equals("0")) && (! config.get(db, "website_publishers_type").equals("*")) && (! config.get(db, "website_publishers_type").equals("=")) && (! config.get(db, "website_publishers_type").equals("")) && (! config.get(db, "website_publishers_type").equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_publishers_type") + "|") >= 0))) {
				publisher = false;
			}
			if ((config.get(db, "website_publishers_group").equals("*")) && (session_username.equals(""))) {
				publisher = false;
			} else if ((config.get(db, "website_publishers_group").equals("0")) && (! session_administrator.equals("administrator"))) {
				publisher = false;
			} else if ((! config.get(db, "website_publishers_group").equals("0")) && (! config.get(db, "website_publishers_group").equals("*")) && (! config.get(db, "website_publishers_group").equals("=")) && (! config.get(db, "website_publishers_group").equals("")) && (! config.get(db, "website_publishers_group").equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_publishers_group") + "|") >= 0))) {
				publisher = false;
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((config.get(db, "website_publishers_type").equals("*")) || (config.get(db, "website_publishers_group").equals("*"))) && (! session_username.equals(""))) {
					publisher = true;
				}
				if (((config.get(db, "website_publishers_type").equals("0")) || (config.get(db, "website_publishers_group").equals("0"))) && (session_administrator.equals("administrator"))) {
					publisher = true;
				}
				if (((! config.get(db, "website_publishers_type").equals("0")) && (! config.get(db, "website_publishers_type").equals("*")) && (! config.get(db, "website_publishers_type").equals("=")) && (! config.get(db, "website_publishers_type").equals("")) && ((config.get(db, "website_publishers_type").equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_publishers_type") + "|") >= 0))) || ((! config.get(db, "website_publishers_group").equals("0")) && (! config.get(db, "website_publishers_group").equals("*")) && (! config.get(db, "website_publishers_group").equals("=")) && (! config.get(db, "website_publishers_group").equals("")) && ((config.get(db, "website_publishers_group").equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_publishers_group") + "|") >= 0)))) {
					publisher = true;
				}
			}
		}

		administrator = true;
		if ((administrators_type.equals("*")) && (session_username.equals(""))) {
			administrator = false;
		} else if ((administrators_type.equals("=")) && ((! session_username.equals(created_by)) || (session_username.equals("")))) {
			administrator = false;
		} else if ((administrators_type.equals("")) && (! session_administrator.equals("administrator"))) {
			administrator = false;
		} else if ((! administrators_type.equals("0")) && (! administrators_type.equals("*")) && (! administrators_type.equals("=")) && (! administrators_type.equals("")) && (! administrators_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + administrators_type + "|") >= 0))) {
			administrator = false;
		}
		if ((administrators_group.equals("*")) && (session_username.equals(""))) {
			administrator = false;
		} else if ((administrators_group.equals("=")) && ((! session_username.equals(created_by)) || (session_username.equals("")))) {
			administrator = false;
		} else if ((administrators_group.equals("")) && (! session_administrator.equals("administrator"))) {
			administrator = false;
		} else if ((! administrators_group.equals("0")) && (! administrators_group.equals("*")) && (! administrators_group.equals("=")) && (! administrators_group.equals("")) && (! administrators_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + administrators_group + "|") >= 0))) {
			administrator = false;
		}
		if (config.get(db, "accessrestrictions").equals("or")) {
			if (((administrators_type.equals("*")) || (administrators_group.equals("*"))) && (! session_username.equals(""))) {
				administrator = true;
			}
			if (((administrators_type.equals("=")) || (administrators_group.equals("="))) && (session_username.equals(created_by)) && (! session_username.equals(""))) {
				administrator = true;
			}
			if (((administrators_type.equals("")) || (administrators_group.equals(""))) && (session_administrator.equals("administrator"))) {
				administrator = true;
			}
			if (((! administrators_type.equals("0")) && (! administrators_type.equals("*")) && (! administrators_type.equals("=")) && (! administrators_type.equals("")) && ((administrators_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + administrators_type + "|") >= 0))) || ((! administrators_group.equals("0")) && (! administrators_group.equals("*")) && (! administrators_group.equals("=")) && (! administrators_group.equals("")) && ((administrators_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + administrators_group + "|") >= 0)))) {
				administrator = true;
			}
		}
		if (administrator) {
			if (! administrators_users.equals("")) {
				if (session_userid.equals("")) {
					administrator = false;
				} else if ((! administrators_users.equals(session_userid)) && (! Pattern.compile("(^|,)" + session_userid + "(,|$)").matcher(administrators_users).find())) {
					administrator = false;
				}
			}
		}
		if (administrator) {
			if ((config.get(db, "website_administrators_type").equals("*")) && (session_username.equals(""))) {
				administrator = false;
			} else if ((config.get(db, "website_administrators_type").equals("")) && (! session_administrator.equals("administrator"))) {
				administrator = false;
			} else if ((! config.get(db, "website_administrators_type").equals("0")) && (! config.get(db, "website_administrators_type").equals("*")) && (! config.get(db, "website_administrators_type").equals("=")) && (! config.get(db, "website_administrators_type").equals("")) && (! config.get(db, "website_administrators_type").equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_administrators_type") + "|") >= 0))) {
				administrator = false;
			}
			if ((config.get(db, "website_administrators_group").equals("*")) && (session_username.equals(""))) {
				administrator = false;
			} else if ((config.get(db, "website_administrators_group").equals("")) && (! session_administrator.equals("administrator"))) {
				administrator = false;
			} else if ((! config.get(db, "website_administrators_group").equals("0")) && (! config.get(db, "website_administrators_group").equals("*")) && (! config.get(db, "website_administrators_group").equals("=")) && (! config.get(db, "website_administrators_group").equals("")) && (! config.get(db, "website_administrators_group").equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_administrators_group") + "|") >= 0))) {
				administrator = false;
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((config.get(db, "website_administrators_type").equals("*")) || (config.get(db, "website_administrators_group").equals("*"))) && (! session_username.equals(""))) {
					administrator = true;
				}
				if (((config.get(db, "website_administrators_type").equals("")) || (config.get(db, "website_administrators_group").equals(""))) && (session_administrator.equals("administrator"))) {
					administrator = true;
				}
				if (((! config.get(db, "website_administrators_type").equals("0")) && (! config.get(db, "website_administrators_type").equals("*")) && (! config.get(db, "website_administrators_type").equals("=")) && (! config.get(db, "website_administrators_type").equals("")) && ((config.get(db, "website_administrators_type").equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_administrators_type") + "|") >= 0))) || ((! config.get(db, "website_administrators_group").equals("0")) && (! config.get(db, "website_administrators_group").equals("*")) && (! config.get(db, "website_administrators_group").equals("=")) && (! config.get(db, "website_administrators_group").equals("")) && ((config.get(db, "website_administrators_group").equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_administrators_group") + "|") >= 0)))) {
					administrator = true;
				}
			}
		}

		if (session_username.equals(config.get(db, "superadmin"))) {
			user = true;
			editor = true;
			creator = true;
			publisher = true;
			administrator = true;

			if (session_username.equals("")) {
				editor = false;
				creator = false;
				publisher = false;
				administrator = false;
			}
		}

		if ((config != null) && (! config.get(db, "inherit_accessrestrictions").equals("no"))) {
			if (administrator) {
				user = true;
				editor = true;
				creator = true;
				publisher = true;
			} else if (publisher) {
				user = true;
				editor = true;
			} else if (creator && ((! creators_type.equals("0")) || ((config.get(db, "use_userdefinition").equals("yes")) && (! creators_group.equals("0"))))) {
				user = true;
				editor = true;
			} else if (editor) {
				user = true;
			}
		}
	}



	public void getForm(Request request) {
		Iterator columns = content.keySet().iterator();
		while (columns.hasNext()) {
			String column = "" + columns.next();
			String value = "";
			String[] values = request.getParameters(column);
			for (int i=0; i<values.length; i++) {
				if (! value.equals("")) value += "|";
				value += values[i];
			}
			content.put(column, value);
		}
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String column = "" + params.nextElement();
			if (column.startsWith("col")) {
				String value = "";
				String[] values = request.getParameters(column);
				for (int i=0; i<values.length; i++) {
					if (! value.equals("")) value += "|";
					value += values[i];
				}
				content.put(column, value);
			}
		}
	}



	public void getFormData(HashMap columns, Request request) {
		Iterator mycolumns = columns.keySet().iterator();
		while (mycolumns.hasNext()) {
			HashMap column = (HashMap)columns.get("" + mycolumns.next());
			String id = "" + column.get("id");
			String order = "" + column.get("order");
			String name = "" + column.get("name");
			String index = "" + column.get("index");
			String type = "" + column.get("type");
			String param1 = "" + column.get("param1");
			String param2 = "" + column.get("param2");
			String options = "" + column.get("options");
			if (request.parameterExists(name)) {
				String value = "";
				String[] values = request.getParameters(name);
				for (int i=0; i<values.length; i++) {
					if (! value.equals("")) value += "|";
					value += values[i];
				}
				content.put("col" + id, value);
			} else if (request.parameterExists(name.replaceAll(" ", "_"))) {
				String value = "";
				String[] values = request.getParameters(name.replaceAll(" ", "_"));
				for (int i=0; i<values.length; i++) {
					if (! value.equals("")) value += "|";
					value += values[i];
				}
				content.put("col" + id, value);
			}
			if ((! type.equals("html")) && ((request.parameterExists(name)) || (request.parameterExists(name.replaceAll(" ", "_"))))) {
				String inputvalue = "" + content.get("col" + id);
				inputvalue = inputvalue.replaceAll("&", "&amp;");
				inputvalue = inputvalue.replaceAll("<", "&lt;");
				inputvalue = inputvalue.replaceAll(">", "&gt;");
				inputvalue = inputvalue.replaceAll("\r", "");
				inputvalue = inputvalue.replaceAll("\n", "<br>");
				inputvalue = inputvalue.replaceAll("@@@", "<nobr>@@@</nobr>");
				inputvalue = inputvalue.replaceAll("###", "<nobr>###</nobr>");
				content.put("col" + id, "" + inputvalue);
			}
		}
	}



	public void getFormData(HashMap columns, Fileupload filepost) {
		Iterator mycolumns = columns.keySet().iterator();
		while (mycolumns.hasNext()) {
			HashMap column = (HashMap)columns.get("" + mycolumns.next());
			String id = "" + column.get("id");
			String order = "" + column.get("order");
			String name = "" + column.get("name");
			String index = "" + column.get("index");
			String type = "" + column.get("type");
			String param1 = "" + column.get("param1");
			String param2 = "" + column.get("param2");
			String options = "" + column.get("options");
			if (filepost.parameterExists(name)) {
				String value = "";
				String[] values = filepost.getParameters(name);
				for (int i=0; i<values.length; i++) {
					if (! value.equals("")) value += "|";
					value += values[i];
				}
				content.put("col" + id, value);
			} else if (filepost.parameterExists(name.replaceAll(" ", "_"))) {
				String value = "";
				String[] values = filepost.getParameters(name.replaceAll(" ", "_"));
				for (int i=0; i<values.length; i++) {
					if (! value.equals("")) value += "|";
					value += values[i];
				}
				content.put("col" + id, value);
			}
			if ((! type.equals("html")) && ((filepost.parameterExists(name)) || (filepost.parameterExists(name.replaceAll(" ", "_"))))) {
				String inputvalue = "" + content.get("col" + id);
				inputvalue = inputvalue.replaceAll("&", "&amp;");
				inputvalue = inputvalue.replaceAll("<", "&lt;");
				inputvalue = inputvalue.replaceAll(">", "&gt;");
				inputvalue = inputvalue.replaceAll("\r", "");
				inputvalue = inputvalue.replaceAll("\n", "<br>");
				inputvalue = inputvalue.replaceAll("@@@", "<nobr>@@@</nobr>");
				inputvalue = inputvalue.replaceAll("###", "<nobr>###</nobr>");
				content.put("col" + id, "" + inputvalue);
			}
		}
	}



	public void getFormData(HashMap columns, HashMap data) {
		Iterator mycolumns = columns.keySet().iterator();
		while (mycolumns.hasNext()) {
			HashMap column = (HashMap)columns.get("" + mycolumns.next());
			String id = "" + column.get("id");
			String order = "" + column.get("order");
			String name = "" + column.get("name");
			String index = "" + column.get("index");
			String type = "" + column.get("type");
			String param1 = "" + column.get("param1");
			String param2 = "" + column.get("param2");
			String options = "" + column.get("options");
			if (data.get(name) != null) {
				String value = "" + data.get(name);
				content.put("col" + id, value);
			} else if (data.get(name.replaceAll(" ", "_")) != null) {
				String value = "" + data.get(name.replaceAll(" ", "_"));
				content.put("col" + id, value);
			}
			if ((! type.equals("html")) && ((data.get(name) != null) || (data.get(name.replaceAll(" ", "_")) != null))) {
				String inputvalue = "" + content.get("col" + id);
				inputvalue = inputvalue.replaceAll("&", "&amp;");
				inputvalue = inputvalue.replaceAll("<", "&lt;");
				inputvalue = inputvalue.replaceAll(">", "&gt;");
				inputvalue = inputvalue.replaceAll("\r", "");
				inputvalue = inputvalue.replaceAll("\n", "<br>");
				inputvalue = inputvalue.replaceAll("@@@", "<nobr>@@@</nobr>");
				inputvalue = inputvalue.replaceAll("###", "<nobr>###</nobr>");
				content.put("col" + id, "" + inputvalue);
			}
		}
	}



	public void adjustContent(HashMap columns) {
		Iterator mycolumns = columns.keySet().iterator();
		while (mycolumns.hasNext()) {
			HashMap column = (HashMap)columns.get("" + mycolumns.next());
			String id = "" + column.get("id");
			String order = "" + column.get("order");
			String name = "" + column.get("name");
			String index = "" + column.get("index");
			String type = "" + column.get("type");
			String param1 = "" + column.get("param1");
			String param2 = "" + column.get("param2");
			String options = "" + column.get("options");
			if (type.equals("number")) {
				int digits = Integer.parseInt(param1);
				int decimals = Integer.parseInt(param2);
				NumberFormat numberformat = NumberFormat.getInstance();
				numberformat.setGroupingUsed(false);
				numberformat.setMinimumIntegerDigits(1);
				numberformat.setMinimumFractionDigits(decimals);
				numberformat.setMaximumFractionDigits(decimals);
				if (content.get("col" + id) != null) {
					content.put("col" + id, numberformat.format(Common.number("" + content.get("col" + id))));
				} else {
					content.put("col" + id, numberformat.format(0));
				}
			}
		}
	}



	public String getCreated(HashMap columns) {
		Iterator mycolumns = columns.keySet().iterator();
		while (mycolumns.hasNext()) {
			HashMap column = (HashMap)columns.get("" + mycolumns.next());
			String id = "" + column.get("id");
			String order = "" + column.get("order");
			String name = "" + column.get("name");
			String index = "" + column.get("index");
			String type = "" + column.get("type");
			String param1 = "" + column.get("param1");
			String param2 = "" + column.get("param2");
			String options = "" + column.get("options");
			if (type.equals("created")) {
				return "" + content.get("col" + id);
			}
		}
		return "";
	}



	public String getCreatedBy(HashMap columns) {
		Iterator mycolumns = columns.keySet().iterator();
		while (mycolumns.hasNext()) {
			HashMap column = (HashMap)columns.get("" + mycolumns.next());
			String id = "" + column.get("id");
			String order = "" + column.get("order");
			String name = "" + column.get("name");
			String index = "" + column.get("index");
			String type = "" + column.get("type");
			String param1 = "" + column.get("param1");
			String param2 = "" + column.get("param2");
			String options = "" + column.get("options");
			if (type.equals("createdby")) {
				return "" + content.get("col" + id);
			}
		}
		return "";
	}



	public void setCreated(HashMap columns, String timestamp, String username) {
		Iterator mycolumns = columns.keySet().iterator();
		while (mycolumns.hasNext()) {
			HashMap column = (HashMap)columns.get("" + mycolumns.next());
			String id = "" + column.get("id");
			String order = "" + column.get("order");
			String name = "" + column.get("name");
			String index = "" + column.get("index");
			String type = "" + column.get("type");
			String param1 = "" + column.get("param1");
			String param2 = "" + column.get("param2");
			String options = "" + column.get("options");
			if (type.equals("created")) {
				content.put("col" + id, timestamp);
			} else if (type.equals("createdby")) {
				content.put("col" + id, username);
			}
		}
	}



	public void setUpdated(HashMap columns, String timestamp, String username) {
		Iterator mycolumns = columns.keySet().iterator();
		while (mycolumns.hasNext()) {
			HashMap column = (HashMap)columns.get("" + mycolumns.next());
			String id = "" + column.get("id");
			String order = "" + column.get("order");
			String name = "" + column.get("name");
			String index = "" + column.get("index");
			String type = "" + column.get("type");
			String param1 = "" + column.get("param1");
			String param2 = "" + column.get("param2");
			String options = "" + column.get("options");
			if (type.equals("updated")) {
				content.put("col" + id, timestamp);
			} else if (type.equals("updatedby")) {
				content.put("col" + id, username);
			}
		}
	}



	public void create(DB db, String database, HashMap columns) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		Iterator mycolumns = columns.keySet().iterator();
		while (mycolumns.hasNext()) {
			String mycolumn = "" + mycolumns.next();
			HashMap column = (HashMap)columns.get(mycolumn);
			String myid = "" + column.get("id");
			if (! myid.equals("")) {
				if (content.get("col" + myid) == null) {
					mydata.put("col" + myid, "");
				} else {
					mydata.put("col" + myid, "" + content.get("col" + myid));
				}
			}
		}
		db.insert(database, mydata);
		id = created_id(db, database, columns);
	}



	public String created_id(DB db, String database, HashMap columns) {
		if (db == null) return "";
		HashMap<String,String> mydata = new HashMap<String,String>();
		Iterator mycolumns = columns.keySet().iterator();
		while (mycolumns.hasNext()) {
			String mycolumn = "" + mycolumns.next();
			String column = "" + ((HashMap)columns.get(mycolumn)).get("id");
			if (! column.equals("")) {
				if (content.get("col" + column) == null) {
					mydata.put("col" + column, "");
				} else {
					mydata.put("col" + column, "" + content.get("col" + column));
				}
			}
		}
		HashMap row = db.select(database, mydata);
		if (row != null) {
			return "" + row.get("id");
		} else {
			return "";
		}
	}



	public void update(DB db, String database, HashMap columns) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			Iterator mycolumns = columns.keySet().iterator();
			while (mycolumns.hasNext()) {
				String mycolumn = "" + mycolumns.next();
				HashMap column = (HashMap)columns.get(mycolumn);
				String myid = "" + column.get("id");
				if (! myid.equals("")) {
					if (content.get("col" + myid) == null) {
						mydata.put("col" + myid, "");
					} else {
						mydata.put("col" + myid, "" + content.get("col" + myid));
					}
				}
			}
			db.update(database, "id", id, mydata);
		}
	}
	
	/**
	 * Método responsável por retornar a col+id correspondente ao nome da coluna no banco de dados
	 * Example: nameCol = col+id
	 * Example: title_item = col1
	 * Example: description_item = col2
	 * @param columns
	 * @param nameCol
	 * @return
	 */
	public String getColAdjustContent(HashMap columns, String nameCol) {
		Iterator mycolumns = columns.keySet().iterator();
		while (mycolumns.hasNext()) {
			HashMap column = (HashMap)columns.get("" + mycolumns.next());
			String id = "" + column.get("id");
			String name = "" + column.get("name");
			if (name.equals(nameCol)) {
				return "col" + id;
			}
		}
		return null;
	}
	
	/**
	 * Método responsavel por deletar qualquer conteudo de uma database/table
	 * Passar o nome da coluna e o id
	 * @param db
	 * @param database
	 * @param Column
	 * @param id
	 */
	public void deleteIliketo(DB db, String database, String column, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete(database, column, id);
		}
	}
	
	public void delete(DB db, String database) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete(database, "id", id);
		}
	}



	public void delete_all(DB db, String database) {
		if (db == null) return;
		if ((database != null) && (! database.equals(""))) {
			db.deleteAll(database);
		}
	}



	public void closeRecords(DB db) {
		db.closeRecords(rs);
		rs = null;
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



	public void export(Writer out, DB db, String database, HashMap<String,HashMap<String,String>> columns) {
		if (db == null) return;
		if (! database.equals("")) {
			String SQLselect = "id";
			Vector<String> v = new Vector<String>(columns.keySet());
			Collections.sort(v, new Comparator<String>() {
				public int compare(String o1, String o2) {
					int i1 = Integer.parseInt("0"+o1);
					int i2 = Integer.parseInt("0"+o2);
					if (i1 < i2) {
						return -1;
					} else if (i1 > i2) {
						return 1;
					} else {
						return 0;
					}
				}
			});
			Iterator mycolumns = v.iterator();
			while (mycolumns.hasNext()) {
				HashMap<String,String> column = (HashMap<String,String>)columns.get("" + mycolumns.next());
				String id = "" + column.get("id");
				if (! SQLselect.equals("")) {
					SQLselect += ",";
				}
				SQLselect += "col" + id;
			}
			String output = "id";
			v = new Vector<String>(columns.keySet());
			Collections.sort(v, new Comparator<String>() {
				public int compare(String o1, String o2) {
					int i1 = Integer.parseInt("0"+o1);
					int i2 = Integer.parseInt("0"+o2);
					if (i1 < i2) {
						return -1;
					} else if (i1 > i2) {
						return 1;
					} else {
						return 0;
					}
				}
			});
			mycolumns = v.iterator();
			while (mycolumns.hasNext()) {
				HashMap<String,String> column = (HashMap<String,String>)columns.get("" + mycolumns.next());
				String id = "" + column.get("id");
				String name = "" + column.get("name");
				if (! output.equals("")) {
					output += "\t";
				}
				output += "" + name;
			}
			try {
				out.write(output + "\r\n");
			} catch (IOException e) {
			}
			String SQL = "select " + SQLselect + " from " + Common.SQL_clean(database) + " order by id";
			records(db, SQL);
			while (records(db, "")) {
				adjustContent(columns);
				output = "" + getId();
				v = new Vector<String>(columns.keySet());
				Collections.sort(v, new Comparator<String>() {
					public int compare(String o1, String o2) {
						int i1 = Integer.parseInt("0"+o1);
						int i2 = Integer.parseInt("0"+o2);
						if (i1 < i2) {
							return -1;
						} else if (i1 > i2) {
							return 1;
						} else {
							return 0;
						}
					}
				});
				mycolumns = v.iterator();
				while (mycolumns.hasNext()) {
					HashMap<String,String> column = (HashMap<String,String>)columns.get("" + mycolumns.next());
					String id = "" + column.get("id");
					if (! output.equals("")) {
						output += "\t";
					}
					output += ("" + content.get("col" + id)).replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t");
				}
				try {
					out.write(output + "\r\n");
				} catch (IOException e) {
				}
			}
		}
	}



	public void importfile(DB db, String database, HashMap<String,HashMap<String,String>> columns, String filename) {
		importfile(db, database, columns, filename, true);
	}
	public void importfile(DB db, String database, HashMap<String,HashMap<String,String>> columns, String filename, boolean updateexisting) {
		if (db == null) return;
		if ((! database.equals("")) && (! filename.equals(""))) {
			boolean first = true;
			BufferedReader file = null;
			try {
				file = new BufferedReader(new FileReader(filename));
			} catch (FileNotFoundException e) {
				if (file != null) try { file.close(); } catch (IOException ee) { ; }
			}
			String my_line;
			while ((my_line = read_line(file)) != null) {
				if (first) {
					first = false;
				} else {
					String[] values = (my_line+"\tEOL").split("\\t");
					read(db, database, values[0]);
					boolean existingdata = getId().equals(values[0]);
					setId(values[0]);
					int value = 1;
					Vector<String> v = new Vector<String>(columns.keySet());
					Collections.sort(v, new Comparator<String>() {
						public int compare(String o1, String o2) {
							int i1 = Integer.parseInt("0"+o1);
							int i2 = Integer.parseInt("0"+o2);
							if (i1 < i2) {
								return -1;
							} else if (i1 > i2) {
								return 1;
							} else {
								return 0;
							}
						}
					});
					Iterator mycolumns = v.iterator();
					while (mycolumns.hasNext()) {
						HashMap<String,String> column = (HashMap<String,String>)columns.get("" + mycolumns.next());
						String id = "" + column.get("id");
						String name = "" + column.get("name");
						if (value < values.length) {
							content.put("col" + id, "" + values[value].trim().replaceAll("\\\\r", "\r").replaceAll("\\\\n", "\n").replaceAll("\\\\t", "\t"));
						} else {
							content.put("col" + id, "");
						}
						value++;
					}
					adjustContent(columns);
					if (! getId().equals("")) {
						if (existingdata && updateexisting) {
							update(db, database, columns);
						} else {
							create(db, database, columns);
						}
					}
				}
			}
			try {	
				file.close();
			} catch (IOException ee) {
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



	public String list_titles(DB db, String table, String column, String ids) {
		if (db == null) return "";
		StringBuffer output = new StringBuffer();
		if ((! table.equals("")) && (! column.equals("")) && (! ids.equals(""))) {
//			String SQL = "select " + column + " from " + table + " where id in (" + ids + ") order by " + column;
// "select column ... order by column" may not work with Microsoft Access - use column number instead
			String SQL = "";
			if (db.db_type(db.getDatabase()).equals("mssql")) {
				SQL = "select id, substring(" + Common.SQL_clean(column) + ",1,250) as " + Common.SQL_clean(column) + " from " + Common.SQL_clean(table) + " where id in (" + ids + ") order by 2";
			} else if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQL = "select id, to_char(" + Common.SQL_clean(column) + ") as " + Common.SQL_clean(column) + " from " + Common.SQL_clean(table) + " where id in (" + ids + ") order by 2";
			} else if (db.db_type(db.getDatabase()).equals("db2")) {
				SQL = "select id, varchar(" + Common.SQL_clean(column) + ",250) as " + Common.SQL_clean(column) + " from " + Common.SQL_clean(table) + " where id in (" + ids + ") order by 2";
			} else {
				SQL = "select id, " + Common.SQL_clean(column) + " from " + Common.SQL_clean(table) + " where id in (" + ids + ") order by 2";
			}
			LinkedHashMap rows = db.query_records(SQL);
			for (int i=0; i<rows.size(); i++) {
				HashMap row = (HashMap)rows.get("" + i);
				if (output.length() != 0) { output.append("<br>" + "\r\n"); }
				output.append("" + row.get(column) + " [" + row.get("id") + "]");
			}
		}
		return "" + output;
	}



	public String select_options(String optionslist, String selectedlist) {
		String output = "";
		if (csv_options) {
			optionslist = optionslist.replaceAll(",", "|");
			selectedlist = selectedlist.replaceAll(",", "|");
		}
		String[] options = optionslist.split("\\|");
		String[] selected = selectedlist.split("\\|");
		for (int i=0; i<options.length; i++) {
			output += "<option value=\"" + html.encode(options[i]) + "\"";
			for (int j=0; j<selected.length; j++) {
				if (options[i].equals(selected[j])) {
					output += " selected";
				} else if (html.encode(options[i]).equals(selected[j])) {
					output += " selected";
				} else if (options[i].equals(html.encode(selected[j]))) {
					output += " selected";
				} else if (html.encode(options[i]).equals(html.encode(selected[j]))) {
					output += " selected";
				}
			}
			output += ">" + options[i];
		}
		return output;
	}



	public String radio_options(String name, String optionslist, String checkedlist) {
		String output = "";
		if (csv_options) {
			optionslist = optionslist.replaceAll(",", "|");
			checkedlist = checkedlist.replaceAll(",", "|");
		}
		String[] options = optionslist.split("\\|");
		String[] mychecked = checkedlist.split("\\|");
		for (int i=0; i<options.length; i++) {
			output += "<input type=\"radio\" name=\"" + name + "\" value=\"" + html.encode(options[i]) + "\"";
			for (int j=0; j<mychecked.length; j++) {
				if (options[i].equals(mychecked[j])) {
					output += " checked";
				} else if (html.encode(options[i]).equals(mychecked[j])) {
					output += " checked";
				} else if (options[i].equals(html.encode(mychecked[j]))) {
					output += " checked";
				} else if (html.encode(options[i]).equals(html.encode(mychecked[j]))) {
					output += " checked";
				}
			}
			output += ">" + options[i] + "<br>";
		}
		return output;
	}



	public String checkbox_options(String name, String optionslist, String checkedlist) {
		String output = "";
		if (csv_options) {
			optionslist = optionslist.replaceAll(",", "|");
			checkedlist = checkedlist.replaceAll(",", "|");
		}
		String[] options = optionslist.split("\\|");
		String[] mychecked = checkedlist.split("\\|");
		for (int i=0; i<options.length; i++) {
			output += "<input type=\"checkbox\" name=\"" + name + "\" value=\"" + html.encode(options[i]) + "\"";
			for (int j=0; j<mychecked.length; j++) {
				if (options[i].equals(mychecked[j])) {
					output += " checked";
				} else if (html.encode(options[i]).equals(mychecked[j])) {
					output += " checked";
				} else if (options[i].equals(html.encode(mychecked[j]))) {
					output += " checked";
				} else if (html.encode(options[i]).equals(html.encode(mychecked[j]))) {
					output += " checked";
				}
			}
			output += ">" + options[i] + "<br>";
		}
		return output;
	}



	public String getId() {
		return id;
	}
	public void setId(String value) {
		id = value;
	}



	public boolean contentExists(String column) {
		if (content.get(column) != null) {
			return true;
		} else {
			return false;
		}
	}
	public String getContent(String column) {
		if (content.get(column) != null) {
			return "" + content.get(column);
		} else {
			return "";
		}
	}
	public void setContent(String column, String value) {
		content.put(column, value);
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



	public boolean getAdministrator() {
		return administrator;
	}
	public void setAdministrator(boolean value) {
		administrator = value;
	}



}
