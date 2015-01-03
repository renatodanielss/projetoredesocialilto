package HardCore;

import java.sql.*;
import java.util.HashMap;
import java.util.regex.*;

public class Imagetype {
	private String id = "";
	private String imagetype = "";
	private String parenttype = "";
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
	private String template = "";
	private String stylesheet = "";

	private boolean user = false;
	private boolean creator = false;
	private boolean editor = false;
	private boolean publisher = false;
	private boolean developer = false;
	private boolean administrator = false;

	private	Statement rs = null;
	private User dummy_user = new User(null);
	private boolean _DEBUG_ = false;



	public Imagetype() {
		init();
	}



	public void init() {
		id = "";
		imagetype = "";
		parenttype = "";
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
		template = "";
		stylesheet = "";
		dummy_user = new User(null);
	}



	public void read(DB db, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			String SQL = "select * from imagetypes where id=" + Common.integer(id);
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void readImagetype(DB db, String imagetypevalue) {
		if (db == null) return;
		if (! imagetypevalue.equals("")) {
			String SQL = "select * from imagetypes where imagetype='" + Common.SQL_clean(imagetypevalue) + "'";
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void getRecord(DB db, HashMap<String,String> record) {
		id = "" + record.get("id");
		imagetype = "" + record.get("imagetype");
		parenttype = "" + record.get("parenttype");
		users_users = "" + record.get("users_users");
		users_type = "" + record.get("users_type");
		users_group = "" + record.get("users_group");
		creators_users = "" + record.get("creators_users");
		creators_type = "" + record.get("creators_type");
		creators_group = "" + record.get("creators_group");
		developers_users = "" + record.get("developers_users");
		developers_type = "" + record.get("developers_type");
		developers_group = "" + record.get("developers_group");
		editors_users = "" + record.get("editors_users");
		editors_type = "" + record.get("editors_type");
		editors_group = "" + record.get("editors_group");
		publishers_users = "" + record.get("publishers_users");
		publishers_type = "" + record.get("publishers_type");
		publishers_group = "" + record.get("publishers_group");
		administrators_users = "" + record.get("administrators_users");
		administrators_type = "" + record.get("administrators_type");
		administrators_group = "" + record.get("administrators_group");
		template = "" + record.get("template");
		stylesheet = "" + record.get("stylesheet");
	}



	public void getForm(Request request) {
		imagetype = "" + request.getParameter("imagetype");
		parenttype = "" + request.getParameter("parenttype");
		users_users = "" + request.getParameter("users_users");
		users_type = "" + request.getParameter("users_type");
		users_group = "" + request.getParameter("users_group");
		creators_users = "" + request.getParameter("creators_users");
		creators_type = "" + request.getParameter("creators_type");
		creators_group = "" + request.getParameter("creators_group");
		developers_users = "" + request.getParameter("developers_users");
		developers_type = "" + request.getParameter("developers_type");
		developers_group = "" + request.getParameter("developers_group");
		editors_users = "" + request.getParameter("editors_users");
		editors_type = "" + request.getParameter("editors_type");
		editors_group = "" + request.getParameter("editors_group");
		publishers_users = "" + request.getParameter("publishers_users");
		publishers_type = "" + request.getParameter("publishers_type");
		publishers_group = "" + request.getParameter("publishers_group");
		administrators_users = "" + request.getParameter("administrators_users");
		administrators_type = "" + request.getParameter("administrators_type");
		administrators_group = "" + request.getParameter("administrators_group");
		template = "" + request.getParameter("template");
		stylesheet = "" + request.getParameter("stylesheet");
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("imagetype", "" + imagetype);
		mydata.put("parenttype", "" + parenttype);
		mydata.put("users_users", "" + users_users);
		mydata.put("users_type", "" + users_type);
		mydata.put("users_group", "" + users_group);
		mydata.put("creators_users", "" + creators_users);
		mydata.put("creators_type", "" + creators_type);
		mydata.put("creators_group", "" + creators_group);
		mydata.put("developers_users", "" + developers_users);
		mydata.put("developers_type", "" + developers_type);
		mydata.put("developers_group", "" + developers_group);
		mydata.put("editors_users", "" + editors_users);
		mydata.put("editors_type", "" + editors_type);
		mydata.put("editors_group", "" + editors_group);
		mydata.put("publishers_users", "" + publishers_users);
		mydata.put("publishers_type", "" + publishers_type);
		mydata.put("publishers_group", "" + publishers_group);
		mydata.put("administrators_users", "" + administrators_users);
		mydata.put("administrators_type", "" + administrators_type);
		mydata.put("administrators_group", "" + administrators_group);
		mydata.put("template", "" + template);
		mydata.put("stylesheet", "" + stylesheet);
		db.insert("imagetypes", mydata);
		Cache.clear(db, "imagetypes");
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			Imagetype old_imagetype = new Imagetype();
			old_imagetype.read(db, id);

			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("imagetype", "" + imagetype);
			mydata.put("parenttype", "" + parenttype);
			mydata.put("users_users", "" + users_users);
			mydata.put("users_type", "" + users_type);
			mydata.put("users_group", "" + users_group);
			mydata.put("creators_users", "" + creators_users);
			mydata.put("creators_type", "" + creators_type);
			mydata.put("creators_group", "" + creators_group);
			mydata.put("developers_users", "" + developers_users);
			mydata.put("developers_type", "" + developers_type);
			mydata.put("developers_group", "" + developers_group);
			mydata.put("editors_users", "" + editors_users);
			mydata.put("editors_type", "" + editors_type);
			mydata.put("editors_group", "" + editors_group);
			mydata.put("publishers_users", "" + publishers_users);
			mydata.put("publishers_type", "" + publishers_type);
			mydata.put("publishers_group", "" + publishers_group);
			mydata.put("administrators_users", "" + administrators_users);
			mydata.put("administrators_type", "" + administrators_type);
			mydata.put("administrators_group", "" + administrators_group);
			mydata.put("template", "" + template);
			mydata.put("stylesheet", "" + stylesheet);
			db.update("imagetypes", "id", id, mydata);

			if ((! old_imagetype.getImagetype().equals("")) && (! imagetype.equals(""))) {
				db.rename("imagetypes", "parenttype", old_imagetype.getImagetype(), imagetype);
			}
			Cache.clear(db, "imagetypes");
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete("imagetypes", "id", id);
		}
		Cache.clear(db, "imagetypes");
	}



	public void renameUsergroup(DB db, String old_usergroup, String new_usergroup) {
		db.rename("imagetypes", "users_group", old_usergroup, new_usergroup);
		db.rename("imagetypes", "creators_group", old_usergroup, new_usergroup);
		db.rename("imagetypes", "developers_group", old_usergroup, new_usergroup);
		db.rename("imagetypes", "editors_group", old_usergroup, new_usergroup);
		db.rename("imagetypes", "publishers_group", old_usergroup, new_usergroup);
		db.rename("imagetypes", "administrators_group", old_usergroup, new_usergroup);
		Cache.clear(db, "imagetypes");
	}



	public void renameUsertype(DB db, String old_usertype, String new_usertype) {
		db.rename("imagetypes", "users_type", old_usertype, new_usertype);
		db.rename("imagetypes", "creators_type", old_usertype, new_usertype);
		db.rename("imagetypes", "developers_type", old_usertype, new_usertype);
		db.rename("imagetypes", "editors_type", old_usertype, new_usertype);
		db.rename("imagetypes", "publishers_type", old_usertype, new_usertype);
		db.rename("imagetypes", "administrators_type", old_usertype, new_usertype);
		Cache.clear(db, "imagetypes");
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



	public static String select_options(DB db, String selected) {
		return Common.select_options(db, "imagetypes", "imagetype", selected);
	}
	public static String select_options(String selectedlist, DB db, Configuration config, Session session) {
		return select_options(selectedlist, db, config, session, "");
	}
	public static String select_options(String selectedlist, DB db, Configuration config, Session session, String suffix) {
		if (db == null) return "";
		StringBuffer options = new StringBuffer();
		String[] selected = selectedlist.split("\\|");
		String SQL = "select * from imagetypes order by imagetype";
		Imagetype myimagetypes = new Imagetype();
		myimagetypes.records(db, SQL);
		while (myimagetypes.records(db, "")) {
			if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || myimagetypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
				String name = "" + myimagetypes.getImagetype() + suffix;
				String value = "" + myimagetypes.getImagetype();
				options.append("<option value=\"");
				options.append(html.encode(value));
				options.append("\"");
				for (int j=0; j<selected.length; j++) {
					if ((selected[j] != null) && (selected[j].equals(value))) {
						options.append(" selected");
					}
				}
				options.append(">");
				options.append(name);
			}
		}
		return "" + options;
	}



	public static String dtree_options(DB db, String pre, String mid, String post) {
		return Common.output_column_X2(db, "imagetypes", "imagetype", pre, mid, post);
	}
	public static String dtree_options(String pre_output, String mid_output, String post_output, DB db, Configuration config, Session session) {
		if (db == null) return "";
		StringBuffer output = new StringBuffer();
		String SQL = "select * from imagetypes order by imagetype";
		Imagetype myimagetypes = new Imagetype();
		myimagetypes.records(db, SQL);
		while (myimagetypes.records(db, "")) {
			if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || myimagetypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
				String value = "" + myimagetypes.getImagetype();
				output.append(pre_output + value.replaceAll("'", "\\\\'").replaceAll("\"", "\\\\\"") + mid_output + value.replaceAll("'", "\\\\\\\\\\\\'").replaceAll("\"", "%22") + post_output);
			}
		}
		return "" + output;
	}



	public String usertype_select_options(DB db, String selected) {
		return dummy_user.usertype_select_options(db, selected);
	}



	public String usergroup_select_options(DB db, String selected) {
		return dummy_user.usergroup_select_options(db, selected);
	}



	public boolean getAccessRestrictions(String session_administrator, String session_userid, String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config) {
		if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":administrator:"+session_administrator + ":userid:"+session_userid + ":username:"+session_username + ":usertype:"+session_usertype + ":usergroup:"+session_usergroup + ":usertypes:"+session_usertypes + ":usergroups:"+session_usergroups); }
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
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":users_type:"+users_type); }
			} else if ((! users_type.equals("*")) && (! users_type.equals("=")) && (! users_type.equals("")) && (! users_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + users_type + "|") >= 0))) {
				user = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":users_type:"+users_type); }
			}
				if ((users_group.equals("*")) && (session_username.equals(""))) {
					user = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":users_group:"+users_group); }
				} else if ((! users_group.equals("*")) && (! users_group.equals("=")) && (! users_group.equals("")) && (! users_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + users_group + "|") >= 0))) {
					user = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":users_group:"+users_group); }
				}
				if (config.get(db, "accessrestrictions").equals("or")) {
					if (((users_type.equals("*")) || (users_group.equals("*"))) && (! session_username.equals(""))) {
						user = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":users_group:"+users_group + ":users_type:"+users_type); }
					}
					if (((! users_type.equals("*")) && (! users_type.equals("=")) && (! users_type.equals("")) && ((users_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + users_type + "|") >= 0))) || ((! users_group.equals("*")) && (! users_group.equals("=")) && (! users_group.equals("")) && ((users_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + users_group + "|") >= 0)))) {
						user = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":users_group:"+users_group + ":users_type:"+users_type); }
					}
				}
			if (user) {
				if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
					if (! users_users.equals("")) {
						if (session_userid.equals("")) {
							user = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":users_users:"+users_users); }
						} else if ((! users_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(users_users).find())) {
							user = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":users_users:"+users_users); }
						}
					}
				}
			}

		}
		if (config.get(db, "use_accessrestrictions").equals("all")) {

			editor = true;
			if ((editors_type.equals("*")) && (session_username.equals(""))) {
				editor = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":editors_type:"+editors_type); }
			} else if ((editors_type.equals("")) && (! session_administrator.equals("administrator"))) {
				editor = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":editors_type:"+editors_type); }
			} else if ((! editors_type.equals("0")) && (! editors_type.equals("*")) && (! editors_type.equals("=")) && (! editors_type.equals("")) && (! editors_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + editors_type + "|") >= 0))) {
				editor = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":editors_type:"+editors_type); }
			}
			if ((editors_group.equals("*")) && (session_username.equals(""))) {
				editor = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":editors_group:"+editors_group); }
			} else if ((editors_group.equals("")) && (! session_administrator.equals("administrator"))) {
				editor = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":editors_group:"+editors_group); }
			} else if ((! editors_group.equals("0")) && (! editors_group.equals("*")) && (! editors_group.equals("=")) && (! editors_group.equals("")) && (! editors_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + editors_group + "|") >= 0))) {
				editor = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":editors_group:"+editors_group); }
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((editors_type.equals("*")) || (editors_group.equals("*"))) && (! session_username.equals(""))) {
					editor = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":editors_group:"+editors_group + ":editors_type:"+editors_type); }
				}
				if (((editors_type.equals("")) || (editors_group.equals(""))) && (session_administrator.equals("administrator"))) {
					editor = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":editors_group:"+editors_group + ":editors_type:"+editors_type); }
				}
				if (((! editors_type.equals("0")) && (! editors_type.equals("*")) && (! editors_type.equals("=")) && (! editors_type.equals("")) && ((editors_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + editors_type + "|") >= 0))) || ((! editors_group.equals("0")) && (! editors_group.equals("*")) && (! editors_group.equals("=")) && (! editors_group.equals("")) && ((editors_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + editors_group + "|") >= 0)))) {
					editor = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":editors_group:"+editors_group + ":editors_type:"+editors_type); }
				}
			}
			if (editor) {
				if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
					if (! editors_users.equals("")) {
						if (session_userid.equals("")) {
							editor = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":editors_users:"+editors_users); }
						} else if ((! editors_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(editors_users).find())) {
							editor = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":editors_users:"+editors_users); }
						}
					}
				}
			}

			creator = true;
			if ((creators_type.equals("*")) && (session_username.equals(""))) {
				creator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":creators_type:"+creators_type); }
			} else if ((creators_type.equals("")) && (! session_administrator.equals("administrator"))) {
				creator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":creators_type:"+creators_type); }
			} else if ((! creators_type.equals("0")) && (! creators_type.equals("*")) && (! creators_type.equals("=")) && (! creators_type.equals("")) && (! creators_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + creators_type + "|") >= 0))) {
				creator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":creators_type:"+creators_type); }
			}
				if ((creators_group.equals("*")) && (session_username.equals(""))) {
					creator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":creators_group:"+creators_group); }
				} else if ((creators_group.equals("")) && (! session_administrator.equals("administrator"))) {
					creator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":creators_group:"+creators_group); }
				} else if ((! creators_group.equals("0")) && (! creators_group.equals("*")) && (! creators_group.equals("=")) && (! creators_group.equals("")) && (! creators_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + creators_group + "|") >= 0))) {
					creator = false;
					if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":creators_group:"+creators_group); }
				}
				if (config.get(db, "accessrestrictions").equals("or")) {
					if (((creators_type.equals("*")) || (creators_group.equals("*"))) && (! session_username.equals(""))) {
						creator = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":creators_group:"+creators_group + ":creators_type:"+creators_type); }
					}
					if (((creators_type.equals("")) || (creators_group.equals(""))) && (session_administrator.equals("administrator"))) {
						creator = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":creators_group:"+creators_group + ":creators_type:"+creators_type); }
					}
					if (((! creators_type.equals("0")) && (! creators_type.equals("*")) && (! creators_type.equals("=")) && (! creators_type.equals("")) && ((creators_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + creators_type + "|") >= 0))) || ((! creators_group.equals("0")) && (! creators_group.equals("*")) && (! creators_group.equals("=")) && (! creators_group.equals("")) && ((creators_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + creators_group + "|") >= 0)))) {
						creator = true;
						if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":creators_group:"+creators_group + ":creators_type:"+creators_type); }
					}
				}
			if (creator) {
				if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
					if (! creators_users.equals("")) {
						if (session_userid.equals("")) {
							creator = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":creators_users:"+creators_users); }
						} else if ((! creators_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(creators_users).find())) {
							creator = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":creators_users:"+creators_users); }
						}
					}
				}
			}

			publisher = true;
			if ((publishers_type.equals("*")) && (session_username.equals(""))) {
				publisher = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":publishers_type:"+publishers_type); }
			} else if ((publishers_type.equals("")) && (! session_administrator.equals("administrator"))) {
				publisher = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":publishers_type:"+publishers_type); }
			} else if ((! publishers_type.equals("0")) && (! publishers_type.equals("*")) && (! publishers_type.equals("=")) && (! publishers_type.equals("")) && (! publishers_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + publishers_type + "|") >= 0))) {
				publisher = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":publishers_type:"+publishers_type); }
			}
			if ((publishers_group.equals("*")) && (session_username.equals(""))) {
				publisher = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":publishers_group:"+publishers_group); }
			} else if ((publishers_group.equals("")) && (! session_administrator.equals("administrator"))) {
				publisher = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":publishers_group:"+publishers_group); }
			} else if ((! publishers_group.equals("0")) && (! publishers_group.equals("*")) && (! publishers_group.equals("=")) && (! publishers_group.equals("")) && (! publishers_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + publishers_group + "|") >= 0))) {
				publisher = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":publishers_group:"+publishers_group); }
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((publishers_type.equals("*")) || (publishers_group.equals("*"))) && (! session_username.equals(""))) {
					publisher = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":publishers_group:"+publishers_group + ":publishers_type:"+publishers_type); }
				}
				if (((publishers_type.equals("")) || (publishers_group.equals(""))) && (session_administrator.equals("administrator"))) {
					publisher = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":publishers_group:"+publishers_group + ":publishers_type:"+publishers_type); }
				}
				if (((! publishers_type.equals("0")) && (! publishers_type.equals("*")) && (! publishers_type.equals("=")) && (! publishers_type.equals("")) && ((publishers_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + publishers_type + "|") >= 0))) || ((! publishers_group.equals("0")) && (! publishers_group.equals("*")) && (! publishers_group.equals("=")) && (! publishers_group.equals("")) && ((publishers_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + publishers_group + "|") >= 0)))) {
					publisher = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":publishers_group:"+publishers_group + ":publishers_type:"+publishers_type); }
				}
			}
			if (publisher) {
				if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
					if (! publishers_users.equals("")) {
						if (session_userid.equals("")) {
							publisher = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":publishers_users:"+publishers_users); }
						} else if ((! publishers_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(publishers_users).find())) {
							publisher = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":publishers_users:"+publishers_users); }
						}
					}
				}
			}

			developer = true;
			if ((developers_type.equals("*")) && (session_username.equals(""))) {
				developer = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":developers_type:"+developers_type); }
			} else if ((developers_type.equals("")) && (! session_administrator.equals("administrator"))) {
				developer = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":developers_type:"+developers_type); }
			} else if ((! developers_type.equals("0")) && (! developers_type.equals("*")) && (! developers_type.equals("=")) && (! developers_type.equals("")) && (! developers_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + developers_type + "|") >= 0))) {
				developer = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":developers_type:"+developers_type); }
			}
			if ((developers_group.equals("*")) && (session_username.equals(""))) {
				developer = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":developers_group:"+developers_group); }
			} else if ((developers_group.equals("")) && (! session_administrator.equals("administrator"))) {
				developer = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":developers_group:"+developers_group); }
			} else if ((! developers_group.equals("0")) && (! developers_group.equals("*")) && (! developers_group.equals("=")) && (! developers_group.equals("")) && (! developers_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + developers_group + "|") >= 0))) {
				developer = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":developers_group:"+developers_group); }
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((developers_type.equals("*")) || (developers_group.equals("*"))) && (! session_username.equals(""))) {
					developer = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":developers_group:"+developers_group + ":developers_type:"+developers_type); }
				}
				if (((developers_type.equals("")) || (developers_group.equals(""))) && (session_administrator.equals("administrator"))) {
					developer = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":developers_group:"+developers_group + ":developers_type:"+developers_type); }
				}
				if (((! developers_type.equals("0")) && (! developers_type.equals("*")) && (! developers_type.equals("=")) && (! developers_type.equals("")) && ((developers_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + developers_type + "|") >= 0))) || ((! developers_group.equals("0")) && (! developers_group.equals("*")) && (! developers_group.equals("=")) && (! developers_group.equals("")) && ((developers_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + developers_group + "|") >= 0)))) {
					developer = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":developers_group:"+developers_group + ":developers_type:"+developers_type); }
				}
			}
			if (developer) {
				if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
					if (! developers_users.equals("")) {
						if (session_userid.equals("")) {
							developer = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":developers_users:"+developers_users); }
						} else if ((! developers_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(developers_users).find())) {
							developer = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":developers_users:"+developers_users); }
						}
					}
				}
			}

			administrator = true;
			if ((administrators_type.equals("*")) && (session_username.equals(""))) {
				administrator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":administrators_type:"+administrators_type); }
			} else if ((administrators_type.equals("")) && (! session_administrator.equals("administrator"))) {
				administrator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":administrators_type:"+administrators_type); }
			} else if ((! administrators_type.equals("0")) && (! administrators_type.equals("*")) && (! administrators_type.equals("=")) && (! administrators_type.equals("")) && (! administrators_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + administrators_type + "|") >= 0))) {
				administrator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":administrators_type:"+administrators_type); }
			}
			if ((administrators_group.equals("*")) && (session_username.equals(""))) {
				administrator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":administrators_group:"+administrators_group); }
			} else if ((administrators_group.equals("")) && (! session_administrator.equals("administrator"))) {
				administrator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":administrators_group:"+administrators_group); }
			} else if ((! administrators_group.equals("0")) && (! administrators_group.equals("*")) && (! administrators_group.equals("=")) && (! administrators_group.equals("")) && (! administrators_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + administrators_group + "|") >= 0))) {
				administrator = false;
				if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":administrators_group:"+administrators_group); }
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((administrators_type.equals("*")) || (administrators_group.equals("*"))) && (! session_username.equals(""))) {
					administrator = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":administrators_group:"+administrators_group + ":administrators_type:"+administrators_type); }
				}
				if (((administrators_type.equals("")) || (administrators_group.equals(""))) && (session_administrator.equals("administrator"))) {
					administrator = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":administrators_group:"+administrators_group + ":administrators_type:"+administrators_type); }
				}
				if (((! administrators_type.equals("0")) && (! administrators_type.equals("*")) && (! administrators_type.equals("=")) && (! administrators_type.equals("")) && ((administrators_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + administrators_type + "|") >= 0))) || ((! administrators_group.equals("0")) && (! administrators_group.equals("*")) && (! administrators_group.equals("=")) && (! administrators_group.equals("")) && ((administrators_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + administrators_group + "|") >= 0)))) {
					administrator = true;
					if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":administrators_group:"+administrators_group + ":administrators_type:"+administrators_type); }
				}
			}
			if (administrator) {
				if (config.get(db, "use_useraccessrestrictions").equals("yes")) {
					if (! administrators_users.equals("")) {
						if (session_userid.equals("")) {
							administrator = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":administrators_users:"+administrators_users); }
						} else if ((! administrators_users.equals(session_userid)) && (! Pattern.compile("(^|,)\\Q" + session_userid + "\\E(,|$)").matcher(administrators_users).find())) {
							administrator = false;
							if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":administrators_users:"+administrators_users); }
						}
					}
				}
			}

		}

		if ((id.equals("")) && (imagetype.equals("")) && (! session_username.equals(config.get(db, "superadmin")))) {
			editor = false;
			creator = false;
			publisher = false;
			developer = false;
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
		if (_DEBUG_) { System.out.println("AsbruWCM/Imagetype.getAccessRestrictions:" + id + ":user:"+user + ":creator:"+creator + ":editor:"+editor + ":publisher:"+publisher + ":developer:"+developer + ":administrator:"+administrator); }
		return editor;
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



	public String getId() {
		return id;
	}
	public void setId(String value) {
		id = value;
	}



	public String getImagetype() {
		return imagetype;
	}
	public void setImagetype(String value) {
		imagetype = value;
	}



	public String getParenttype() {
		return parenttype;
	}
	public void setParenttype(String value) {
		parenttype = value;
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



	public String getDevelopersUsers() {
		return developers_users;
	}
	public void setDevelopersUsers(String value) {
		developers_users = value;
	}



	public String getDevelopersType() {
		return developers_type;
	}
	public void setDevelopersType(String value) {
		developers_type = value;
	}



	public String getDevelopersGroup() {
		return developers_group;
	}
	public void setDevelopersGroup(String value) {
		developers_group = value;
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



	public String getTemplate() {
		return template;
	}
	public void setTemplate(String value) {
		template = value;
	}



	public String getStyleSheet() {
		return stylesheet;
	}
	public void setStyleSheet(String value) {
		stylesheet = value;
	}



}
