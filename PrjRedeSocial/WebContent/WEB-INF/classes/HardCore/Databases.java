package HardCore;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.regex.*;

public class Databases {
	private String id = "";
	private String title = "";
	private String content = "";
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
	private String searchresults = "";
	private String searchresult = "";
	private String viewpage = "";
	private String adminindex = "";
	private boolean user = false;
	private boolean creator = false;
	private boolean editor = false;
	private boolean publisher = false;
	private boolean administrator = false;

	private	Statement rs = null;
	public HashMap<String,HashMap<String,String>> columns = new HashMap<String,HashMap<String,String>>();
	public HashMap<String,HashMap<String,String>> namedcolumns = new HashMap<String,HashMap<String,String>>();
	private String titleid = "";
	private String titlename = "";
	private String titlecolumn = "id";
	private Text text = new Text();



	public Databases() {
		init();
	}



	public Databases(Text mytext) {
		if (mytext != null) text = mytext;
		init();
	}



	public void init() {
		id = "";
		title = "";
		content = "";
		users_users = "";
		users_type = "";
		users_group = "";
		creators_users = "";
		creators_type = "";
		creators_group = "";
		editors_users = "";
		editors_type = "";
		editors_group = "";
		publishers_users = "";
		publishers_type = "";
		publishers_group = "";
		administrators_users = "";
		administrators_type = "";
		administrators_group = "";
		searchresults = "";
		searchresult = "";
		viewpage = "";
		adminindex = "";
		columns = new HashMap<String,HashMap<String,String>>();
		namedcolumns = new HashMap<String,HashMap<String,String>>();
		titleid = "";
		titlename = "";
		titlecolumn = "id";
	}



	public void read(DB db, Configuration config, String id) {
		read("", "", "", "", "", "", "", db, config, id);
	}
	public void read(DB db, Configuration config, String id, String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups) {
		read(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config, id);
	}
	public void read(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			String SQL = "";
			if (Common.integernumber(id) > 0) {
				SQL = "select * from data where id=" + Common.integer(id);
			} else {
				SQL = "select * from data where title=" + db.quote(Common.SQL_clean(id));
			}
			HashMap row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		} else {
			init();
		}
		getAccessRestrictions(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config);
	}



	public void readTitle(DB db, Configuration config, String title) {
		readTitle("", "", "", "", "", "", "", db, config, title);
	}
	public void readTitle(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String title) {
		if (db == null) return;
		if (! title.equals("")) {
			String SQL = "select * from data where title=" + db.quote(Common.SQL_clean(title));
			HashMap row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		} else {
			init();
		}
		getAccessRestrictions(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config);
	}



	public void getRecord(DB db, HashMap record) {
		id = "" + record.get("id");
		title = "" + record.get("title");
		content = "" + record.get("content");
		users_users = "" + record.get("users_users");
		users_type = "" + record.get("users_type");
		users_group = "" + record.get("users_group");
		creators_users = "" + record.get("creators_users");
		creators_type = "" + record.get("creators_type");
		creators_group = "" + record.get("creators_group");
		editors_users = "" + record.get("editors_users");
		editors_type = "" + record.get("editors_type");
		editors_group = "" + record.get("editors_group");
		publishers_users = "" + record.get("publishers_users");
		publishers_type = "" + record.get("publishers_type");
		publishers_group = "" + record.get("publishers_group");
		administrators_users = "" + record.get("administrators_users");
		administrators_type = "" + record.get("administrators_type");
		administrators_group = "" + record.get("administrators_group");
		searchresults = "" + record.get("searchresults");
		searchresult = "" + record.get("searchresult");
		viewpage = "" + record.get("viewpage");
		adminindex = "" + record.get("adminindex");
		getColumns();
	}



	public void getAccessRestrictions(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config) {
		user = true;
		if ((users_type.equals("*")) && (session_username.equals(""))) {
			user = false;
		} else if ((! users_type.equals("*")) && (! users_type.equals("=")) && (! users_type.equals("")) && (! users_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + users_type + "|") >= 0))) {
			user = false;
		}
		if (config.get(db, "use_userdefinition").equals("yes")) {
			if ((users_group.equals("*")) && (session_username.equals(""))) {
				user = false;
			} else if ((! users_group.equals("*")) && (! users_group.equals("=")) && (! users_group.equals("")) && (! users_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + users_group + "|") >= 0))) {
				user = false;
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((users_type.equals("*")) || (users_group.equals("*"))) && (! session_username.equals(""))) {
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
		} else if ((editors_type.equals("")) && (! session_administrator.equals("administrator"))) {
			editor = false;
		} else if ((! editors_type.equals("0")) && (! editors_type.equals("*")) && (! editors_type.equals("=")) && (! editors_type.equals("")) && (! editors_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + editors_type + "|") >= 0))) {
			editor = false;
		}
		if ((editors_group.equals("*")) && (session_username.equals(""))) {
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
		} else if ((creators_type.equals("")) && (! session_administrator.equals("administrator"))) {
			creator = false;
		} else if ((! creators_type.equals("0")) && (! creators_type.equals("*")) && (! creators_type.equals("=")) && (! creators_type.equals("")) && (! creators_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + creators_type + "|") >= 0))) {
			creator = false;
		}
		if (config.get(db, "use_userdefinition").equals("yes")) {
			if ((creators_group.equals("*")) && (session_username.equals(""))) {
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
		} else if ((publishers_type.equals("")) && (! session_administrator.equals("administrator"))) {
			publisher = false;
		} else if ((! publishers_type.equals("0")) && (! publishers_type.equals("*")) && (! publishers_type.equals("=")) && (! publishers_type.equals("")) && (! publishers_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + publishers_type + "|") >= 0))) {
			publisher = false;
		}
		if ((publishers_group.equals("*")) && (session_username.equals(""))) {
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
		} else if ((administrators_type.equals("")) && (! session_administrator.equals("administrator"))) {
			administrator = false;
		} else if ((! administrators_type.equals("0")) && (! administrators_type.equals("*")) && (! administrators_type.equals("=")) && (! administrators_type.equals("")) && (! administrators_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + administrators_type + "|") >= 0))) {
			administrator = false;
		}
		if ((administrators_group.equals("*")) && (session_username.equals(""))) {
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
		title = "" + request.getParameter("title");
		content = "" + request.getParameter("content");
		users_users = "" + request.getParameter("users_users");
		users_type = "" + request.getParameter("users_type");
		users_group = "" + request.getParameter("users_group");
		creators_users = "" + request.getParameter("creators_users");
		creators_type = "" + request.getParameter("creators_type");
		creators_group = "" + request.getParameter("creators_group");
		editors_users = "" + request.getParameter("editors_users");
		editors_type = "" + request.getParameter("editors_type");
		editors_group = "" + request.getParameter("editors_group");
		publishers_users = "" + request.getParameter("publishers_users");
		publishers_type = "" + request.getParameter("publishers_type");
		publishers_group = "" + request.getParameter("publishers_group");
		administrators_users = "" + request.getParameter("administrators_users");
		administrators_type = "" + request.getParameter("administrators_type");
		administrators_group = "" + request.getParameter("administrators_group");
		searchresults = "" + request.getParameter("searchresults");
		searchresult = "" + request.getParameter("searchresult");
		viewpage = "" + request.getParameter("viewpage");
		adminindex = "" + request.getParameter("adminindex");
		getColumns();
	}



	public void getColumns() {
		columns = new HashMap<String,HashMap<String,String>>();
		namedcolumns = new HashMap<String,HashMap<String,String>>();
		String[] mycolumns = content.split("[\r\n]+");
		for (int i=0; i<mycolumns.length; i++) {
			String[] attribs = mycolumns[i].split("\\|");
			if (attribs.length >= 5) {
				String id = attribs[0];
				String order = attribs[1];
				String name = attribs[2];
				String index = attribs[3];
				String type = attribs[4];
				String param1;
				if (attribs.length >= 6) {
					param1 = attribs[5];
				} else {
					param1 = "";
				}
				String param2;
				if (attribs.length >= 7) {
					param2 = attribs[6];
				} else {
					param2 = "";
				}
				String options = "";
				if (attribs.length >= 8) {
					for (int j=7; j<attribs.length; j++) {
						if (! options.equals("")) options += "|";
						options += attribs[j];
					}
				}
				if ((! id.equals("")) && (! order.equals(""))) {
					columns.put(order, new HashMap<String,String>());
					((HashMap<String,String>)columns.get(order)).put("id", id);
					((HashMap<String,String>)columns.get(order)).put("order", order);
					((HashMap<String,String>)columns.get(order)).put("name", name);
					((HashMap<String,String>)columns.get(order)).put("index", index);
					((HashMap<String,String>)columns.get(order)).put("type", type);
					((HashMap<String,String>)columns.get(order)).put("param1", param1);
					((HashMap<String,String>)columns.get(order)).put("param2", param2);
					((HashMap<String,String>)columns.get(order)).put("options", options);
					namedcolumns.put(name, new HashMap<String,String>());
					((HashMap<String,String>)namedcolumns.get(name)).put("id", id);
					((HashMap<String,String>)namedcolumns.get(name)).put("order", order);
					((HashMap<String,String>)namedcolumns.get(name)).put("name", name);
					((HashMap<String,String>)namedcolumns.get(name)).put("index", index);
					((HashMap<String,String>)namedcolumns.get(name)).put("type", type);
					((HashMap<String,String>)namedcolumns.get(name)).put("param1", param1);
					((HashMap<String,String>)namedcolumns.get(name)).put("param2", param2);
					((HashMap<String,String>)namedcolumns.get(name)).put("options", options);
				}
			}
		}
	}



	public void getTitleColumn() {
		titleid = "";
		titlename = "";
		titlecolumn = "id";
		int firsttextindex = 0;
		int firsttext = 0;
		int firstnumberindex = 0;
		int firstnumber = 0;
		Iterator mycolumns = columns.keySet().iterator();
		while (mycolumns.hasNext()) {
			HashMap<String,String> column = (HashMap<String,String>)columns.get("" + mycolumns.next());
			String id = "" + column.get("id");
			int order = Common.intnumber(column.get("order"));
			String name = "" + column.get("name");
			String index = "" + column.get("index");
			String type = "" + column.get("type");
			String param1 = "" + column.get("param1");
			String param2 = "" + column.get("param2");
			String options = "" + column.get("options");
			String rows = param2;
			if ((type.equals("text")) && (index.equals("index")) && (rows.equals("1")) && ((firsttextindex == 0) || (order < firsttextindex))) {
				firsttextindex = order;
				titleid = id;
				titlename = name;
				titlecolumn = "col" + id;
			} else if ((type.equals("text")) && (rows.equals("1")) && ((firsttext == 0) || (order < firsttext)) && (firsttextindex == 0)) {
				firsttext = order;
				titleid = id;
				titlename = name;
				titlecolumn = "col" + id;
			} else if ((type.equals("number")) && (index.equals("index")) && ((firstnumberindex == 0) || (order < firstnumberindex)) && (firsttextindex == 0) && (firsttext == 0)) {
				firstnumberindex = order;
				titleid = id;
				titlename = name;
				titlecolumn = "col" + id;
			} else if ((type.equals("number")) && ((firstnumber == 0) || (order < firstnumber)) && (firsttextindex == 0) && (firsttext == 0) && (firstnumberindex == 0)) {
				firstnumber = order;
				titleid = id;
				titlename = name;
				titlecolumn = "col" + id;
			}
		}
	}



	public String getTitleColumnId() {
		return titleid;
	}



	public String getTitleColumnName() {
		return titlename;
	}



	public String getTitleColumnColumn() {
		return titlecolumn;
	}



	public String getAttributeColumnColumn(String columnname) {
		return getAttributeColumn(columnname);
	}
	public String getAttributeColumn(String columnname) {
		String databasecolumn = "";
		Iterator mycolumns = columns.keySet().iterator();
		while (mycolumns.hasNext()) {
			HashMap<String,String> column = (HashMap<String,String>)columns.get("" + mycolumns.next());
			String id = "" + column.get("id");
			String order = "" + column.get("order");
			String name = "" + column.get("name");
			String index = "" + column.get("index");
			String type = "" + column.get("type");
			String param1 = "" + column.get("param1");
			String param2 = "" + column.get("param2");
			String options = "" + column.get("options");
			if (name.toLowerCase().equals(columnname.toLowerCase())) {
				databasecolumn = "col" + column.get("id");
			}
		}
		return databasecolumn;
	}



	public String[] getAttributesTables(String columnnames, DB db, Configuration myconfig, Session mysession) {
		return getAttributesTables(null, columnnames, db, myconfig, mysession);
	}
	public String[] getAttributesTables(String databasename, String columnnames, DB db, Configuration myconfig, Session mysession) {
		String[] mydatabasenames = new String[0];
		HashMap<String,String> tablenames = new HashMap<String,String>();
		if (databasename == null) {
			mydatabasenames = new String[1];
			mydatabasenames[0] = getTitle();
			if (! getId().equals("")) {
				tablenames.put(getTitle(), "data" + getId());
			}
		} else {
			mydatabasenames = databasename.split(",");
			if (mydatabasenames.length == 1) {
				mydatabasenames = databasename.split("\\*");
			}
			for (int d=0; d<mydatabasenames.length; d++) {
				String mydatabasename = mydatabasenames[d];
				Databases mydatabase = new Databases();
				String session_administrator = "";
				String session_userid = "";
				String session_username = "";
				String session_usertype = "";
				String session_usergroup = "";
				String session_usertypes = "";
				String session_usergroups = "";
				if (mysession != null) {
					session_administrator = "" + mysession.get("administrator");
					session_userid = "" + mysession.get("userid");
					session_username = "" + mysession.get("username");
					session_usertype = "" + mysession.get("usertype");
					session_usergroup = "" + mysession.get("usergroup");
					session_usertypes = "" + mysession.get("usertypes");
					session_usergroups = "" + mysession.get("usergroups");
				}
				mydatabase.readTitle(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, myconfig, mydatabasename);
				if (! mydatabase.getId().equals("")) {
					tablenames.put(mydatabasename, "data" + mydatabase.getId());
				}
			}
		}
		return (String[])tablenames.values().toArray(new String[tablenames.values().size()]);
	}



	public HashMap<String,Databases> getAttributesDatabases(String databasename, DB db, Configuration myconfig, Session mysession) {
		HashMap<String,Databases> mydatabases = new HashMap<String,Databases>();
		String[] mydatabasenames = new String[0];
		if (databasename == null) {
			mydatabasenames = new String[1];
			mydatabasenames[0] = getTitle();
			if (! getId().equals("")) {
				mydatabases.put(getTitle(), this);
			}
		} else {
			mydatabasenames = databasename.split(",");
			if (mydatabasenames.length == 1) {
				mydatabasenames = databasename.split("\\*");
			}
			for (int d=0; d<mydatabasenames.length; d++) {
				String mydatabasename = mydatabasenames[d];
				Databases mydatabase = new Databases();
				String session_administrator = "";
				String session_userid = "";
				String session_username = "";
				String session_usertype = "";
				String session_usergroup = "";
				String session_usertypes = "";
				String session_usergroups = "";
				if (mysession != null) {
					session_administrator = "" + mysession.get("administrator");
					session_userid = "" + mysession.get("userid");
					session_username = "" + mysession.get("username");
					session_usertype = "" + mysession.get("usertype");
					session_usergroup = "" + mysession.get("usergroup");
					session_usertypes = "" + mysession.get("usertypes");
					session_usergroups = "" + mysession.get("usergroups");
				}
				mydatabase.readTitle(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, myconfig, mydatabasename);
				if (! mydatabase.getId().equals("")) {
					mydatabases.put(mydatabasename, mydatabase);
				}
			}
		}
		return mydatabases;
	}



	public String[] getAttributesColumns(String columnnames, DB db, Configuration myconfig, Session mysession) {
		return getAttributesColumns(null, columnnames, db, myconfig, mysession);
	}
	public String[] getAttributesColumns(String databasename, String columnnames, DB db, Configuration myconfig, Session mysession) {
		HashMap<String,Databases> mydatabases = getAttributesDatabases(databasename, db, myconfig, mysession);
		String[] mydatabasenames = new String[0];
		if (databasename == null) {
			mydatabasenames = new String[1];
			mydatabasenames[0] = getTitle();
		} else {
			mydatabasenames = databasename.split(",");
			if (mydatabasenames.length == 1) {
				mydatabasenames = databasename.split("\\*");
			}
		}
		if ((columnnames.indexOf("@@@") < 0) && (columnnames.indexOf(",") > 0)) {
			columnnames = "@@@" + columnnames.replaceAll(",", "@@@,@@@") + "@@@";
		}
		HashMap<String,String> columnids = new HashMap<String,String>();
		Pattern p = Pattern.compile("@@@([^@]+?)@@@");
		Matcher m = p.matcher(columnnames);
		while (m.find()) {
			String attributename = "" + m.group(1);
			if (! attributename.equals("")) {
				String columnid = "";
				for (int d=0; d<mydatabasenames.length; d++) {
					String mydatabasename = mydatabasenames[d];
					Databases mydatabase = (Databases)mydatabases.get(mydatabasename);
					if (mydatabase != null) {
						if ((columnid.equals("")) && (mydatabase != null) && (! mydatabase.getId().equals(""))) {
							// use first matching column name
							columnid = mydatabase.getAttributeColumn(attributename);
							if ((! columnid.equals("")) && (mydatabasenames.length > 1)) {
								columnid = "data" + mydatabase.getId() + "." + columnid;
							}
						}
					}
				}
				if (! columnid.equals("")) {
					columnids.put(attributename, columnid);
				}
			}
		}
		return (String[])columnids.values().toArray(new String[columnids.values().size()]);
	}



	public String getAttributesColumnsFormat(String columnnames, DB db, Configuration myconfig, Session mysession) {
		return getAttributesColumnsFormat(null, columnnames, db, myconfig, mysession);
	}
	public String getAttributesColumnsFormat(String databasename, String columnnames, DB db, Configuration myconfig, Session mysession) {
		HashMap<String,Databases> mydatabases = getAttributesDatabases(databasename, db, myconfig, mysession);
		String[] mydatabasenames = new String[0];
		boolean jointables = false;
		if (databasename == null) {
			mydatabasenames = new String[1];
			mydatabasenames[0] = getTitle();
			jointables = false;
		} else {
			mydatabasenames = databasename.split(",");
			if (mydatabasenames.length == 1) {
				mydatabasenames = databasename.split("\\*");
				// do not join tables on matching column names if tables separated by "*", or if single table
				jointables = false;
			} else {
				 // join tables on matching column names if tables separated by ","
				jointables = true;
			}
		}
		if ((columnnames.indexOf("@@@") < 0) && (columnnames.indexOf(",") > 0)) {
			columnnames = "@@@" + columnnames.replaceAll(",", "@@@,@@@") + "@@@";
		}
		String optionformat = "" + columnnames;
		HashMap<String,String> columnids = new HashMap<String,String>();
		HashMap<String,String> mycolumnnames = new HashMap<String,String>();
		HashMap<String,String> joincolumns = new HashMap<String,String>();
		Pattern p = Pattern.compile("@@@([^@]+?)@@@");
		Matcher m = p.matcher(columnnames);
		while (m.find()) {
			String attributename = "" + m.group(1);
			if (! attributename.equals("")) {
				String columnid = "";
				for (int d=0; d<mydatabasenames.length; d++) {
					String mydatabasename = mydatabasenames[d];
					Databases mydatabase = (Databases)mydatabases.get(mydatabasename);
					if (mydatabase != null) {
						if ((columnid.equals("")) && (! mydatabase.getId().equals(""))) {
							// use first matching column name
							columnid = mydatabase.getAttributeColumn(attributename);
							if ((! columnid.equals("")) && (mydatabasenames.length > 1)) {
								columnid = "data" + mydatabase.getId() + "." + columnid;
							}
						}
						if (jointables && (! mydatabase.getId().equals(""))) {
							// find matching column names to join on
							String mycolumnid = "data" + mydatabase.getId() + "." + mydatabase.getAttributeColumn(attributename);
							if (mycolumnnames.get(attributename) == null) {
								mycolumnnames.put(attributename, mycolumnid);
							} else {
								String myjoin = "" + mycolumnnames.get(attributename);
								while (joincolumns.get(myjoin) != null) {
									myjoin = "" + joincolumns.get(myjoin);
								}
								joincolumns.put(myjoin, mycolumnid);
							}
						}
					}
				}
				if (! columnid.equals("")) {
					columnids.put(attributename, columnid);
					optionformat = optionformat.replaceAll("@@@" + attributename + "@@@", "@@@" + columnid + "@@@");
				}
			}
		}
		return optionformat;
	}



	public HashMap<String,String> getAttributesJoins(String databasename, String columnnames, DB db, Configuration myconfig, Session mysession) {
		HashMap<String,Databases> mydatabases = getAttributesDatabases(databasename, db, myconfig, mysession);
		String[] mydatabasenames = new String[0];
		boolean jointables = false;
		if (databasename == null) {
			mydatabasenames = new String[1];
			mydatabasenames[0] = getTitle();
			jointables = false;
		} else {
			mydatabasenames = databasename.split(",");
			if (mydatabasenames.length == 1) {
				mydatabasenames = databasename.split("\\*");
				// do not join tables on matching column names if tables separated by "*", or if single table
				jointables = false;
			} else {
				 // join tables on matching column names if tables separated by ","
				jointables = true;
			}
		}
		if ((columnnames.indexOf("@@@") < 0) && (columnnames.indexOf(",") > 0)) {
			columnnames = "@@@" + columnnames.replaceAll(",", "@@@,@@@") + "@@@";
		}
		String optionformat = "" + columnnames;
		HashMap<String,String> columnids = new HashMap<String,String>();
		HashMap<String,String> mycolumnnames = new HashMap<String,String>();
		HashMap<String,String> joincolumns = new HashMap<String,String>();
		Pattern p = Pattern.compile("@@@([^@]+?)@@@");
		Matcher m = p.matcher(columnnames);
		while (m.find()) {
			String attributename = "" + m.group(1);
			if (! attributename.equals("")) {
				String columnid = "";
				for (int d=0; d<mydatabasenames.length; d++) {
					String mydatabasename = mydatabasenames[d];
					Databases mydatabase = (Databases)mydatabases.get(mydatabasename);
					if (mydatabase != null) {
						if ((columnid.equals("")) && (! mydatabase.getId().equals(""))) {
							// use first matching column name
							columnid = mydatabase.getAttributeColumn(attributename);
							if ((! columnid.equals("")) && (mydatabasenames.length > 1)) {
								columnid = "data" + mydatabase.getId() + "." + columnid;
							}
						}
						if (jointables && (! mydatabase.getId().equals(""))) {
							// find matching column names to join on
							String mycolumnid = mydatabase.getAttributeColumn(attributename);
							if (! mycolumnid.equals("")) {
								mycolumnid = "data" + mydatabase.getId() + "." + mycolumnid;
								if (mycolumnnames.get(attributename) == null) {
									mycolumnnames.put(attributename, mycolumnid);
								} else {
									String myjoin = "" + mycolumnnames.get(attributename);
									while (joincolumns.get(myjoin) != null) {
										myjoin = "" + joincolumns.get(myjoin);
									}
									joincolumns.put(myjoin, mycolumnid);
								}
							}
						}
					}
				}
				if (! columnid.equals("")) {
					columnids.put(attributename, columnid);
					optionformat = optionformat.replaceAll("@@@" + attributename + "@@@", "@@@" + columnid + "@@@");
				}
			}
		}
		return joincolumns;
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("title", "" + title);
		mydata.put("content", "" + content);
		mydata.put("users_users", "" + users_users);
		mydata.put("users_type", "" + users_type);
		mydata.put("users_group", "" + users_group);
		mydata.put("creators_users", "" + creators_users);
		mydata.put("creators_type", "" + creators_type);
		mydata.put("creators_group", "" + creators_group);
		mydata.put("editors_users", "" + editors_users);
		mydata.put("editors_type", "" + editors_type);
		mydata.put("editors_group", "" + editors_group);
		mydata.put("publishers_users", "" + publishers_users);
		mydata.put("publishers_type", "" + publishers_type);
		mydata.put("publishers_group", "" + publishers_group);
		mydata.put("administrators_users", "" + administrators_users);
		mydata.put("administrators_type", "" + administrators_type);
		mydata.put("administrators_group", "" + administrators_group);
		mydata.put("searchresults", "" + searchresults);
		mydata.put("searchresult", "" + searchresult);
		mydata.put("viewpage", "" + viewpage);
		mydata.put("adminindex", "" + adminindex);
		db.insert("data", mydata);
		id = created_id(db);
	}



	public String created_id(DB db) {
		if (db == null) return "0";
		String SQLwhere = "";
		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "title", title);
		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "users_type", users_type);
		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "users_group", users_group);
		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "creators_type", creators_type);
		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "creators_group", creators_group);
		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "editors_type", editors_type);
		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "editors_group", editors_group);
		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "publishers_type", publishers_type);
		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "publishers_group", publishers_group);
		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "administrators_type", administrators_type);
		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "administrators_group", administrators_group);
		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "searchresults", searchresults);
		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "searchresult", searchresult);
		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "viewpage", viewpage);
		SQLwhere = Common.SQLwhere_like(db, SQLwhere, "adminindex", adminindex);
		String SQL = "select * from data " + SQLwhere + " order by id desc";
		String my_created_id = "0";
		boolean inserted_row = false;
		LinkedHashMap rows = db.query_records(SQL);
		Iterator rowsIterator = rows.keySet().iterator();
		while (rowsIterator.hasNext()) {
			Object mykey = rowsIterator.next();
			HashMap row = (HashMap)rows.get(mykey);
			if (! inserted_row) {
				inserted_row = true;
				if (row.get("content") != null) {
					if (! content.trim().equals((""+row.get("content")).trim())) {
						inserted_row = false;
					}
				} else if (row.get("users_users") != null) {
					if (! users_users.trim().equals((""+row.get("users_users")).trim())) {
						inserted_row = false;
					}
				} else if (row.get("creators_users") != null) {
					if (! creators_users.trim().equals((""+row.get("creators_users")).trim())) {
						inserted_row = false;
					}
				} else if (row.get("editors_users") != null) {
					if (! editors_users.trim().equals((""+row.get("editors_users")).trim())) {
						inserted_row = false;
					}
				} else if (row.get("publishers_users") != null) {
					if (! publishers_users.trim().equals((""+row.get("publishers_users")).trim())) {
						inserted_row = false;
					}
				} else if (row.get("administrators_users") != null) {
					if (! administrators_users.trim().equals((""+row.get("administrators_users")).trim())) {
						inserted_row = false;
					}
				} else {
					if (! content.equals("")) {
						inserted_row = false;
					}
				}
				if (inserted_row) {
					my_created_id = "" + row.get("id");
				}
			}
		}
		return "" + my_created_id;
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("title", "" + title);
			mydata.put("content", "" + content);
			mydata.put("users_users", "" + users_users);
			mydata.put("users_type", "" + users_type);
			mydata.put("users_group", "" + users_group);
			mydata.put("creators_users", "" + creators_users);
			mydata.put("creators_type", "" + creators_type);
			mydata.put("creators_group", "" + creators_group);
			mydata.put("editors_users", "" + editors_users);
			mydata.put("editors_type", "" + editors_type);
			mydata.put("editors_group", "" + editors_group);
			mydata.put("publishers_users", "" + publishers_users);
			mydata.put("publishers_type", "" + publishers_type);
			mydata.put("publishers_group", "" + publishers_group);
			mydata.put("administrators_users", "" + administrators_users);
			mydata.put("administrators_type", "" + administrators_type);
			mydata.put("administrators_group", "" + administrators_group);
			mydata.put("searchresults", "" + searchresults);
			mydata.put("searchresult", "" + searchresult);
			mydata.put("viewpage", "" + viewpage);
			mydata.put("adminindex", "" + adminindex);
			db.update("data", "id", id, mydata);
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete("data", "id", id);
		}
	}



	public void renameUsergroup(DB db, String old_usergroup, String new_usergroup) {
		db.rename("data", "users_group", old_usergroup, new_usergroup);
		db.rename("data", "creators_group", old_usergroup, new_usergroup);
		db.rename("data", "editors_group", old_usergroup, new_usergroup);
		db.rename("data", "publishers_group", old_usergroup, new_usergroup);
		db.rename("data", "administrators_group", old_usergroup, new_usergroup);
	}



	public void renameUsertype(DB db, String old_usertype, String new_usertype) {
		db.rename("data", "users_type", old_usertype, new_usertype);
		db.rename("data", "creators_type", old_usertype, new_usertype);
		db.rename("data", "editors_type", old_usertype, new_usertype);
		db.rename("data", "publishers_type", old_usertype, new_usertype);
		db.rename("data", "administrators_type", old_usertype, new_usertype);
	}



	public boolean records(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String SQL) {
		if ((db == null) || db.isClosed()) return false;
		if ((SQL != null) && (! SQL.equals(""))) {
			rs = db.records(SQL);
			return true;
		} else {
			HashMap row = db.records(rs);
			if (row != null) {
				getRecord(db, row);
				getAccessRestrictions(session_administrator, session_userid, session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config);
				return true;
			} else {
				init();
				return false;
			}
		}
	}



	public String usertype_select_options(DB db, String selected) {
		User myuser = new User();
		return myuser.usertype_select_options(db, selected);
	}



	public String usergroup_select_options(DB db, String selected) {
		User myuser = new User();
		return myuser.usergroup_select_options(db, selected);
	}



	public String administratorsSQLfromwhere(DB db, boolean administrators, boolean publishers, boolean editors, boolean creators, boolean users, boolean broad_search) {
		return administratorsSQLfromwhere(db, administrators, publishers, editors, creators, users, broad_search, true);
	}
	public String administratorsSQLfromwhere(DB db, boolean administrators, boolean publishers, boolean editors, boolean creators, boolean users, boolean broad_search, boolean subgroupstypes) {
		String SQLwhere = "";
		if ((id != null) && (! id.equals("")) && (! id.equals("0")) && (! id.equals("-1"))) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "users.userclass", "administrator");
			SQLwhere += " and ((1=0)";

			boolean restricted_users = false;
			boolean restricted_creators = false;
			boolean restricted_editors = false;
			boolean restricted_publishers = false;
			boolean restricted_administrators = false;

			if (users) {
				String SQLwhereUser = "";
				if ((getUsersType().equals("*")) || (getUsersType().equals("0"))) {
					// ok
				} else if (! getUsersType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getUsersType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getUsersType(), getUsersType());
					if (! SQLwhereUser.equals("")) SQLwhereUser += " and ";
					SQLwhereUser += "(";
					SQLwhereUser += "users.usertype in (" + Common.SQL_list_values(db, usertypes) + ")";
					if (subgroupstypes) {
						SQLwhereUser += " or ";
						SQLwhereUser += "(users.username=users_usertypes.username and users_usertypes.usertype in (" + Common.SQL_list_values(db, usertypes) + "))";
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
					SQLwhereUser += "users.usergroup in (" + Common.SQL_list_values(db, usergroups) + ")";
					if (subgroupstypes) {
						SQLwhereUser += " or ";
						SQLwhereUser += "(users.username=users_usergroups.username and users_usergroups.usergroup in (" + Common.SQL_list_values(db, usergroups) + "))";
					}
					SQLwhereUser += ")";
				}
				if (! getUsersUsers().equals("")) {
					String SQLwhereUserUsers = "users.id in (" + Common.SQL_list_values(db, getUsersUsers().split(",")) + ")";
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

			if (creators) {
				String SQLwhereCreator = "";
				if ((getCreatorsType().equals("*")) || (getCreatorsType().equals("0"))) {
					// ok
				} else if (! getCreatorsType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getCreatorsType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getCreatorsType(), getCreatorsType());
					if (! SQLwhereCreator.equals("")) SQLwhereCreator += " and ";
					SQLwhereCreator += "(";
					SQLwhereCreator += "users.usertype in (" + Common.SQL_list_values(db, usertypes) + ")";
					if (subgroupstypes) {
						SQLwhereCreator += " or ";
						SQLwhereCreator += "(users.username=users_usertypes.username and users_usertypes.usertype in (" + Common.SQL_list_values(db, usertypes) + "))";
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
					SQLwhereCreator += "users.usergroup in (" + Common.SQL_list_values(db, usergroups) + ")";
					if (subgroupstypes) {
						SQLwhereCreator += " or ";
						SQLwhereCreator += "(users.username=users_usergroups.username and users_usergroups.usergroup in (" + Common.SQL_list_values(db, usergroups) + "))";
					}
					SQLwhereCreator += ")";
				}
				if (! getCreatorsUsers().equals("")) {
					String SQLwhereCreatorUsers = "users.id in (" + Common.SQL_list_values(db, getCreatorsUsers().split(",")) + ")";
					if (! SQLwhereCreator.equals("")) {
						SQLwhereCreator = "(" + SQLwhereCreator + ") or (" + SQLwhereCreatorUsers + ")";
					} else {
						SQLwhereCreator = SQLwhereCreatorUsers;
					}
				}
				if (! SQLwhereCreator.equals("")) {
					SQLwhere += " or (" + SQLwhereCreator + ")";
					restricted_creators = true;
				}
			}

			if (editors) {
				String SQLwhereEditor = "";
				if ((getEditorsType().equals("*")) || (getEditorsType().equals("0"))) {
					// ok
				} else if (! getEditorsType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getEditorsType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getEditorsType(), getEditorsType());
					if (! SQLwhereEditor.equals("")) SQLwhereEditor += " and ";
					SQLwhereEditor += "(";
					SQLwhereEditor += "users.usertype in (" + Common.SQL_list_values(db, usertypes) + ")";
					if (subgroupstypes) {
						SQLwhereEditor += " or ";
						SQLwhereEditor += "(users.username=users_usertypes.username and users_usertypes.usertype in (" + Common.SQL_list_values(db, usertypes) + "))";
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
					SQLwhereEditor += "users.usergroup in (" + Common.SQL_list_values(db, usergroups) + ")";
					if (subgroupstypes) {
						SQLwhereEditor += " or ";
						SQLwhereEditor += "(users.username=users_usergroups.username and users_usergroups.usergroup in (" + Common.SQL_list_values(db, usergroups) + "))";
					}
					SQLwhereEditor += ")";
				}
				if (! getEditorsUsers().equals("")) {
					String SQLwhereEditorUsers = "users.id in (" + Common.SQL_list_values(db, getEditorsUsers().split(",")) + ")";
					if (! SQLwhereEditor.equals("")) {
						SQLwhereEditor = "(" + SQLwhereEditor + ") or (" + SQLwhereEditorUsers + ")";
					} else {
						SQLwhereEditor = SQLwhereEditorUsers;
					}
				}
				if (! SQLwhereEditor.equals("")) {
					SQLwhere += " or (" + SQLwhereEditor + ")";
					restricted_editors = true;
				}
			}

			if (publishers) {
				String SQLwherePublisher = "";
				if ((getPublishersType().equals("*")) || (getPublishersType().equals("0"))) {
					// ok
				} else if (! getPublishersType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getPublishersType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getPublishersType(), getPublishersType());
					if (! SQLwherePublisher.equals("")) SQLwherePublisher += " and ";
					SQLwherePublisher += "(";
					SQLwherePublisher += "users.usertype in (" + Common.SQL_list_values(db, usertypes) + ")";
					if (subgroupstypes) {
						SQLwherePublisher += " or ";
						SQLwherePublisher += "(users.username=users_usertypes.username and users_usertypes.usertype in (" + Common.SQL_list_values(db, usertypes) + "))";
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
					SQLwherePublisher += "users.usergroup in (" + Common.SQL_list_values(db, usergroups) + ")";
					if (subgroupstypes) {
						SQLwherePublisher += " or ";
						SQLwherePublisher += "(users.username=users_usergroups.username and users_usergroups.usergroup in (" + Common.SQL_list_values(db, usergroups) + "))";
					}
					SQLwherePublisher += ")";
				}
				if (! getPublishersUsers().equals("")) {
					String SQLwherePublisherUsers = "users.id in (" + Common.SQL_list_values(db, getPublishersUsers().split(",")) + ")";
					if (! SQLwherePublisher.equals("")) {
						SQLwherePublisher = "(" + SQLwherePublisher + ") or (" + SQLwherePublisherUsers + ")";
					} else {
						SQLwherePublisher = SQLwherePublisherUsers;
					}
				}
				if (! SQLwherePublisher.equals("")) {
					SQLwhere += " or (" + SQLwherePublisher + ")";
					restricted_publishers = true;
				}
			}

			if (administrators) {
				String SQLwhereAdministrator = "";
				if ((getAdministratorsType().equals("*")) || (getAdministratorsType().equals("0"))) {
					// ok
				} else if (! getAdministratorsType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getAdministratorsType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getAdministratorsType(), getAdministratorsType());
					if (! SQLwhereAdministrator.equals("")) SQLwhereAdministrator += " and ";
					SQLwhereAdministrator += "(";
					SQLwhereAdministrator += "users.usertype in (" + Common.SQL_list_values(db, usertypes) + ")";
					if (subgroupstypes) {
						SQLwhereAdministrator += " or ";
						SQLwhereAdministrator += "(users.username=users_usertypes.username and users_usertypes.usertype in (" + Common.SQL_list_values(db, usertypes) + "))";
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
					SQLwhereAdministrator += "users.usergroup in (" + Common.SQL_list_values(db, usergroups) + ")";
					if (subgroupstypes) {
						SQLwhereAdministrator += " or ";
						SQLwhereAdministrator += "(users.username=users_usergroups.username and users_usergroups.usergroup in (" + Common.SQL_list_values(db, usergroups) + "))";
					}
					SQLwhereAdministrator += ")";
				}
				if (! getAdministratorsUsers().equals("")) {
					String SQLwhereAdministratorUsers = "users.id in (" + Common.SQL_list_values(db, getAdministratorsUsers().split(",")) + ")";
					if (! SQLwhereAdministrator.equals("")) {
						SQLwhereAdministrator = "(" + SQLwhereAdministrator + ") or (" + SQLwhereAdministratorUsers + ")";
					} else {
						SQLwhereAdministrator = SQLwhereAdministratorUsers;
					}
				}
				if (! SQLwhereAdministrator.equals("")) {
					SQLwhere += " or (" + SQLwhereAdministrator + ")";
					restricted_administrators = true;
				}
			}

			if (broad_search && ((! restricted_users) || (! restricted_creators) || (! restricted_editors) || (! restricted_publishers) || (! restricted_administrators))) {
				SQLwhere += " or (1=1)";
			}

			SQLwhere += ")";
		}
		String SQLfrom = " from users";
		if (subgroupstypes) {
			if (SQLwhere.indexOf("users_usergroups")>=0) {
				SQLfrom += ",users_usergroups";
			}
			if (SQLwhere.indexOf("users_usertypes")>=0) {
				SQLfrom += ",users_usertypes";
			}
		}
		return SQLfrom + " " + SQLwhere;
	}



	public String[] administratorsEmails(Session mysession, Configuration myconfig, DB db) {
		HashMap<String,String> emails = new HashMap<String,String>();
		if ((id != null) && (! id.equals("")) && (! id.equals("0")) && (! id.equals("-1"))) {
			String admin_email = "";
			String SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,true,false,false,false,false,false,false) + " and (" + db.is_not_blank("username") + ")";
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
			SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,true,false,false,false,false,false,true) + " and (" + db.is_not_blank("username") + ")";
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
			String SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,true,true,false,false,false,false,false) + " and (" + db.is_not_blank("username") + ")";
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
			SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,true,true,false,false,false,false,true) + " and (" + db.is_not_blank("username") + ")";
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
			String SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,true,true,true,true,false,false,false) + " and (" + db.is_not_blank("username") + ")";
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
			SQL = "select distinct users.id as id, users.email as email, users.username as username " + administratorsSQLfromwhere(db,true,true,true,true,false,false,true) + " and (" + db.is_not_blank("username") + ")";
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



	public String getTable() {
		return "data" + id;
	}



	public String getId() {
		return id;
	}
	public void setId(String value) {
		id = value;
	}



	public String getTitle() {
		return title;
	}
	public void setTitle(String value) {
		title = value;
	}



	public String getContent() {
		return content;
	}
	public void setContent(String value) {
		content = value;
	}



	public String getUsersUsers() {
		return users_users;
	}
	public void setUsersUsers(String value) {
		users_users = value;
	}



	public String getUsersType() {
		return users_type;
	}
	public void setUsersType(String value) {
		users_type = value;
	}



	public String getUsersGroup() {
		return users_group;
	}
	public void setUsersGroup(String value) {
		users_group = value;
	}



	public String getCreatorsUsers() {
		return creators_users;
	}
	public void setCreatorsUsers(String value) {
		creators_users = value;
	}



	public String getCreatorsType() {
		return creators_type;
	}
	public void setCreatorsType(String value) {
		creators_type = value;
	}



	public String getCreatorsGroup() {
		return creators_group;
	}
	public void setCreatorsGroup(String value) {
		creators_group = value;
	}



	public String getEditorsUsers() {
		return editors_users;
	}
	public void setEditorsUsers(String value) {
		editors_users = value;
	}



	public String getEditorsType() {
		return editors_type;
	}
	public void setEditorsType(String value) {
		editors_type = value;
	}



	public String getEditorsGroup() {
		return editors_group;
	}
	public void setEditorsGroup(String value) {
		editors_group = value;
	}



	public String getPublishersUsers() {
		return publishers_users;
	}
	public void setPublishersUsers(String value) {
		publishers_users = value;
	}



	public String getPublishersType() {
		return publishers_type;
	}
	public void setPublishersType(String value) {
		publishers_type = value;
	}



	public String getPublishersGroup() {
		return publishers_group;
	}
	public void setPublishersGroup(String value) {
		publishers_group = value;
	}



	public String getAdministratorsUsers() {
		return administrators_users;
	}
	public void setAdministratorsUsers(String value) {
		administrators_users = value;
	}



	public String getAdministratorsType() {
		return administrators_type;
	}
	public void setAdministratorsType(String value) {
		administrators_type = value;
	}



	public String getAdministratorsGroup() {
		return administrators_group;
	}
	public void setAdministratorsGroup(String value) {
		administrators_group = value;
	}



	public String getSearchresults() {
		return searchresults;
	}
	public void setSearchresults(String value) {
		searchresults = value;
	}



	public String getSearchresult() {
		return searchresult;
	}
	public void setSearchresult(String value) {
		searchresult = value;
	}



	public String getViewPage() {
		return viewpage;
	}
	public void setViewPage(String value) {
		viewpage = value;
	}



	public String getAdminIndex() {
		return adminindex;
	}
	public void setAdminIndex(String value) {
		adminindex = value;
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
