package HardCore;

import java.io.*;
import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.regex.*;

public class Workflow {
	private String id = "";
	private String title = "";
	private String action = "";
	private String fromstate = "";
	private String tostate = "";
	private String usertype = "";
	private String usergroup = "";
	private String contentclass = "";
	private String contenttype = "";
	private String contentgroup = "";
	private String version = "";
	private String notify_email = "";
	private String contentchanges = "";
	private String workflow_program = "";
	private String userrestriction = "";
	private boolean autoarchive = false;
	private boolean autocheckin = false;
	private boolean autocheckout = false;
	private boolean autodelete = false;
	private boolean autopublish = false;
	private boolean autounpublish = false;
	private boolean autounschedule = false;
	private boolean lock_user = false;
	private boolean unlock_user = false;
	private boolean lock_creator = false;
	private boolean unlock_creator = false;
	private boolean lock_developer = false;
	private boolean unlock_developer = false;
	private boolean lock_editor = false;
	private boolean unlock_editor = false;
	private boolean lock_publisher = false;
	private boolean unlock_publisher = false;
	private boolean lock_administrator = false;
	private boolean unlock_administrator = false;
	private boolean lock_schedule = false;
	private boolean unlock_schedule = false;
	private boolean lock_unschedule = false;
	private boolean unlock_unschedule = false;

	private	Statement rs = null;
	Text text = new Text();



	public Workflow() {
		init();
	}



	public Workflow(Text mytext) {
		if (mytext != null) text = mytext;
		init();
	}



	public void init() {
		id = "";
		title = "";
		action = "";
		fromstate = "";
		tostate = "";
		usertype = "";
		usergroup = "";
		contentclass = "";
		contenttype = "";
		contentgroup = "";
		version = "";
		notify_email = "";
		contentchanges = "";
		workflow_program = "";
		userrestriction = "";
		autoarchive = false;
		autocheckin = false;
		autocheckout = false;
		autodelete = false;
		autopublish = false;
		autounpublish = false;
		autounschedule = false;
		lock_user = false;
		unlock_user = false;
		lock_creator = false;
		unlock_creator = false;
		lock_developer = false;
		unlock_developer = false;
		lock_editor = false;
		unlock_editor = false;
		lock_publisher = false;
		unlock_publisher = false;
		lock_administrator = false;
		unlock_administrator = false;
		lock_schedule = false;
		unlock_schedule = false;
		lock_unschedule = false;
		unlock_unschedule = false;
	}



	public void read(DB db, String id) {
		if (db == null) return;
		if ((! id.equals("")) && (! id.equals(" ")) && (! id.equals("0"))) {
			String SQL = "select * from workflow where id=" + Common.integer(id);
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
		title = "" + record.get("title");
		action = "" + record.get("action");
		fromstate = "" + record.get("fromstate");
		tostate = "" + record.get("tostate");
		usertype = "" + record.get("usertype");
		usergroup = "" + record.get("usergroup");
		contentclass = "" + record.get("contentclass");
		contenttype = "" + record.get("contenttype");
		contentgroup = "" + record.get("contentgroup");
		version = "" + record.get("version");
		notify_email = "" + record.get("notify_email");
		contentchanges = "" + record.get("contentchanges");
		workflow_program = "" + record.get("workflow_program");
		userrestriction = "" + record.get("userrestriction");
	}



	public void getForm(Request request) {
		title = request.getParameter("title");
		action = request.getParameter("action");
		fromstate = request.getParameter("fromstate");
		tostate = request.getParameter("tostate");
		usertype = request.getParameter("usertype");
		usergroup = request.getParameter("usergroup");
		contentclass = ("|" + Common.join("|", request.getParameters("contentclass")) + "|").replaceAll("\\|\\|", "");
		contentgroup = ("|" + Common.join("|", request.getParameters("contentgroup")) + "|").replaceAll("\\|\\|", "");
		contenttype = ("|" + Common.join("|", request.getParameters("contenttype")) + "|").replaceAll("\\|\\|", "");
		version = ("|" + Common.join("|", request.getParameters("version")) + "|").replaceAll("\\|\\|", "");
		notify_email = request.getParameter("notify_email");
		contentchanges = request.getParameter("contentchanges");
		workflow_program = request.getParameter("workflow_program");
		userrestriction = request.getParameter("userrestriction");
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("title", "" + title);
		mydata.put("action", "" + action);
		mydata.put("fromstate", "" + fromstate);
		mydata.put("tostate", "" + tostate);
		mydata.put("usertype", "" + usertype);
		mydata.put("usergroup", "" + usergroup);
		mydata.put("contentclass", "" + contentclass);
		mydata.put("contenttype", "" + contenttype);
		mydata.put("contentgroup", "" + contentgroup);
		mydata.put("version", "" + version);
		mydata.put("notify_email", "" + notify_email);
		mydata.put("contentchanges", "" + contentchanges);
		mydata.put("workflow_program", "" + workflow_program);
		mydata.put("userrestriction", "" + userrestriction);
		db.insert("workflow", mydata);
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("title", "" + title);
			mydata.put("action", "" + action);
			mydata.put("fromstate", "" + fromstate);
			mydata.put("tostate", "" + tostate);
			mydata.put("usertype", "" + usertype);
			mydata.put("usergroup", "" + usergroup);
			mydata.put("contentclass", "" + contentclass);
			mydata.put("contenttype", "" + contenttype);
			mydata.put("contentgroup", "" + contentgroup);
			mydata.put("version", "" + version);
			mydata.put("notify_email", "" + notify_email);
			mydata.put("contentchanges", "" + contentchanges);
			mydata.put("workflow_program", "" + workflow_program);
			mydata.put("userrestriction", "" + userrestriction);
			db.update("workflow", "id", id, mydata);
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete("workflow", "id", id);
		}
	}



	public void renameContentclass(DB db, String old_contentclass, String new_contentclass) {
		if (db == null) return;
		db.rename("workflow", "contentclass", old_contentclass, new_contentclass);
		Workflow myworkflow = new Workflow(text);
		myworkflow.records(db, "select * from workflow where contentclass like '%|" + Common.SQL_clean(old_contentclass) + "|%'");
		while (myworkflow.records(db, "")) {
			myworkflow.contentclass = myworkflow.contentclass.replaceAll("\\|" + old_contentclass + "\\|", "|" + new_contentclass + "|");
			myworkflow.contentclass = myworkflow.contentclass.replaceAll("^\\|\\|$", "").replaceAll("\\|\\|", "|");
			myworkflow.update(db);
		}
	}



	public void renameContentgroup(DB db, String old_contentgroup, String new_contentgroup) {
		if (db == null) return;
		db.rename("workflow", "contentgroup", old_contentgroup, new_contentgroup);
		Workflow myworkflow = new Workflow(text);
		myworkflow.records(db, "select * from workflow where contentgroup like '%|" + Common.SQL_clean(old_contentgroup) + "|%'");
		while (myworkflow.records(db, "")) {
			myworkflow.contentgroup = myworkflow.contentgroup.replaceAll("\\|" + old_contentgroup + "\\|", "|" + new_contentgroup + "|");
			myworkflow.contentgroup = myworkflow.contentgroup.replaceAll("^\\|\\|$", "").replaceAll("\\|\\|", "|");
			myworkflow.update(db);
		}
	}



	public void renameContenttype(DB db, String old_contenttype, String new_contenttype) {
		if (db == null) return;
		db.rename("workflow", "contenttype", old_contenttype, new_contenttype);
		Workflow myworkflow = new Workflow(text);
		myworkflow.records(db, "select * from workflow where contenttype like '%|" + Common.SQL_clean(old_contenttype) + "|%'");
		while (myworkflow.records(db, "")) {
			myworkflow.contenttype = myworkflow.contenttype.replaceAll("\\|" + old_contenttype + "\\|", "|" + new_contenttype + "|");
			myworkflow.contenttype = myworkflow.contenttype.replaceAll("^\\|\\|$", "").replaceAll("\\|\\|", "|");
			myworkflow.update(db);
		}
	}



	public void renameImagegroup(DB db, String old_contentgroup, String new_contentgroup) {
		renameContentgroup(db, old_contentgroup, new_contentgroup);
	}
	public void renameImagetype(DB db, String old_contenttype, String new_contenttype) {
		renameContenttype(db, old_contenttype, new_contenttype);
	}
	public void renameFilegroup(DB db, String old_contentgroup, String new_contentgroup) {
		renameContentgroup(db, old_contentgroup, new_contentgroup);
	}
	public void renameFiletype(DB db, String old_contenttype, String new_contenttype) {
		renameContenttype(db, old_contenttype, new_contenttype);
	}
	public void renameLinkgroup(DB db, String old_contentgroup, String new_contentgroup) {
		renameContentgroup(db, old_contentgroup, new_contentgroup);
	}
	public void renameLinktype(DB db, String old_contenttype, String new_contenttype) {
		renameContenttype(db, old_contenttype, new_contenttype);
	}
	public void renameProductgroup(DB db, String old_contentgroup, String new_contentgroup) {
		renameContentgroup(db, old_contentgroup, new_contentgroup);
	}
	public void renameProducttype(DB db, String old_contenttype, String new_contenttype) {
		renameContenttype(db, old_contenttype, new_contenttype);
	}



	public void renameVersion(DB db, String old_version, String new_version) {
		if (db == null) return;
		db.rename("workflow", "version", old_version, new_version);
		Workflow myworkflow = new Workflow(text);
		myworkflow.records(db, "select * from workflow where version like '%|" + Common.SQL_clean(old_version) + "|%'");
		while (myworkflow.records(db, "")) {
			myworkflow.version = myworkflow.version.replaceAll("\\|" + old_version + "\\|", "|" + new_version + "|");
			myworkflow.version = myworkflow.version.replaceAll("^\\|\\|$", "").replaceAll("\\|\\|", "|");
			myworkflow.update(db);
		}
	}



	public void renameUsergroup(DB db, String old_usergroup, String new_usergroup) {
		if (db == null) return;
		db.rename("workflow", "usergroup", old_usergroup, new_usergroup);
	}



	public void renameUsertype(DB db, String old_usertype, String new_usertype) {
		if (db == null) return;
		db.rename("workflow", "usertype", old_usertype, new_usertype);
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



	public String getNotifyEmailTitle(Session session, Configuration config, DB db) {
		Content mycontent = new Content(text);
		mycontent.read(db, config, notify_email, "content", "id", session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"));
		return mycontent.getTitle();
	}



	public String select_options(DB db, String selected) {
		return Common.select_options(db, "workflow", "title", selected);
	}



	public String action_select_options(DB db, String selected) {
		return Common.select_options(db, "workflow", "action", selected);
	}



	public String state_select_options(DB db, String selected) {
		return Common.select_options(db, "workflow", "tostate", selected);
	}
	public String state_select_options(DB db, String selected, boolean nonblank) {
		if (nonblank) {
			return Common.select_options_where(db, "workflow", "tostate", selected, "where " + db.is_not_blank("tostate"));
		} else {
			return Common.select_options(db, "workflow", "tostate", selected);
		}
	}
	public String state_select_options_where(DB db, String selected, String name, String value, boolean nonblank) {
		String SQLwhere = Common.SQLwhere_equals(db, "-", name, value);
		if (nonblank) {
			return Common.select_options_where(db, "workflow", "tostate", selected, "where " + SQLwhere + " and " + db.is_not_blank("tostate"));
		} else {
			return Common.select_options_where(db, "workflow", "tostate", selected, "where " + SQLwhere);
		}
	}



	public HashMap<String,HashMap<String,String>> actions(DB db, String where_fromstate, String where_tostate, String where_usergroups, String where_usertypes, String where_contentclass, String where_contentgroup, String where_contenttype, String where_version, boolean administrator) {
		return actions(db, where_fromstate, where_tostate, where_usergroups, where_usertypes, where_contentclass, where_contentgroup, where_contenttype, where_version, administrator, 0);
	}
	public HashMap<String,HashMap<String,String>> actions(DB db, String where_fromstate, String where_tostate, String where_usergroups, String where_usertypes, String where_contentclass, String where_contentgroup, String where_contenttype, String where_version, boolean administrator, boolean sameuser) {
		if (sameuser) {
			return actions(db, where_fromstate, where_tostate, where_usergroups, where_usertypes, where_contentclass, where_contentgroup, where_contenttype, where_version, administrator, 1);
		} else {
			return actions(db, where_fromstate, where_tostate, where_usergroups, where_usertypes, where_contentclass, where_contentgroup, where_contenttype, where_version, administrator, -1);
		}
	}
	public HashMap<String,HashMap<String,String>> actions(DB db, String where_fromstate, String where_tostate, String where_usergroups, String where_usertypes, String where_contentclass, String where_contentgroup, String where_contenttype, String where_version, boolean administrator, int sameuser) {
		if (db == null) return null;
		HashMap<String,HashMap<String,String>> myactions = new HashMap<String,HashMap<String,String>>();
		String SQL = "select * from workflow";
		String SQLwhere = "";
		if (! administrator) {
			if (where_fromstate.equals("<>")) {
				SQLwhere = Common.SQLwhere_not_equals(db, SQLwhere, "fromstate", where_tostate);
			} else if (! where_fromstate.equals("*")) {
				SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "fromstate", where_fromstate);
			}
			if (where_tostate.equals("<>")) {
				SQLwhere = Common.SQLwhere_not_equals(db, SQLwhere, "tostate", where_fromstate);
			} else if (! where_tostate.equals("*")) {
				SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "tostate", where_tostate);
			}
			if (where_usergroups.equals("")) {
				if (db.db_type(db.getDatabase()).equals("mssql")) {
					SQLwhere = Common.SQLwhere(SQLwhere, "(usergroup is null or cast(usergroup as varchar) in (''))");
				} else if (db.db_type(db.getDatabase()).equals("oracle")) {
					SQLwhere = Common.SQLwhere(SQLwhere, "(usergroup is null or dbms_lob.substr(usergroup,255) in (''))");
				} else if (db.db_type(db.getDatabase()).equals("db2")) {
					SQLwhere = Common.SQLwhere(SQLwhere, "(usergroup is null or varchar(usergroup,255) in (''))");
				} else {
					SQLwhere = Common.SQLwhere(SQLwhere, "(usergroup is null or usergroup in (''))");
				}
			} else if (! where_usergroups.equals("*")) {
				if (db.db_type(db.getDatabase()).equals("mssql")) {
					SQLwhere = Common.SQLwhere(SQLwhere, "(usergroup is null or cast(usergroup as varchar) in ('','" + where_usergroups.replaceAll("\\|","','") + "'))");
				} else if (db.db_type(db.getDatabase()).equals("oracle")) {
					SQLwhere = Common.SQLwhere(SQLwhere, "(usergroup is null or dbms_lob.substr(usergroup,255) in ('','" + where_usergroups.replaceAll("\\|","','") + "'))");
				} else if (db.db_type(db.getDatabase()).equals("db2")) {
					SQLwhere = Common.SQLwhere(SQLwhere, "(usergroup is null or varchar(usergroup,255) in ('','" + where_usergroups.replaceAll("\\|","','") + "'))");
				} else {
					SQLwhere = Common.SQLwhere(SQLwhere, "(usergroup is null or usergroup in ('','" + where_usergroups.replaceAll("\\|","','") + "'))");
				}
			}
			if (where_usertypes.equals("")) {
				if (db.db_type(db.getDatabase()).equals("mssql")) {
					SQLwhere = Common.SQLwhere(SQLwhere, "(usertype is null or cast(usertype as varchar) in (''))");
				} else if (db.db_type(db.getDatabase()).equals("oracle")) {
					SQLwhere = Common.SQLwhere(SQLwhere, "(usertype is null or dbms_lob.substr(usertype,255) in (''))");
				} else if (db.db_type(db.getDatabase()).equals("db2")) {
					SQLwhere = Common.SQLwhere(SQLwhere, "(usertype is null or varchar(usertype,255) in (''))");
				} else {
					SQLwhere = Common.SQLwhere(SQLwhere, "(usertype is null or usertype in (''))");
				}
			} else if (! where_usertypes.equals("*")) {
				if (db.db_type(db.getDatabase()).equals("mssql")) {
					SQLwhere = Common.SQLwhere(SQLwhere, "(usertype is null or cast(usertype as varchar) in ('','" + where_usertypes.replaceAll("\\|","','") + "'))");
				} else if (db.db_type(db.getDatabase()).equals("oracle")) {
					SQLwhere = Common.SQLwhere(SQLwhere, "(usertype is null or dbms_lob.substr(usertype,255) in ('','" + where_usertypes.replaceAll("\\|","','") + "'))");
				} else if (db.db_type(db.getDatabase()).equals("db2")) {
					SQLwhere = Common.SQLwhere(SQLwhere, "(usertype is null or varchar(usertype,255) in ('','" + where_usertypes.replaceAll("\\|","','") + "'))");
				} else {
					SQLwhere = Common.SQLwhere(SQLwhere, "(usertype is null or usertype in ('','" + where_usertypes.replaceAll("\\|","','") + "'))");
				}
			}
			if (sameuser > 0) {
				SQLwhere = Common.SQLwhere(SQLwhere, "(userrestriction is null or userrestriction <> 'different')");
			} else if (sameuser < 0) {
				SQLwhere = Common.SQLwhere(SQLwhere, "(userrestriction is null or userrestriction <> 'same')");
			}
		}
		if (where_contentclass.equals("-order-")) {
			SQLwhere = Common.SQLwhere_like(db, SQLwhere, "contentclass", "%|" + where_contentclass + "|%");
		} else if (! where_contentclass.equals("*")) {
			SQLwhere = Common.SQLwhere_like_or_or(db, SQLwhere, "contentclass", "%|" + where_contentclass + "|%", "contentclass", "", "contentclass", "||");
		}
		if (! where_contentgroup.equals("*")) {
			SQLwhere = Common.SQLwhere_like_or_or(db, SQLwhere, "contentgroup", "%|" + where_contentgroup + "|%", "contentgroup", "", "contentgroup", "||");
		}
		if (! where_contenttype.equals("*")) {
			SQLwhere = Common.SQLwhere_like_or_or(db, SQLwhere, "contenttype", "%|" + where_contenttype + "|%", "contenttype", "", "contenttype", "||");
		}
		if (! where_version.equals("*")) {
			SQLwhere = Common.SQLwhere_like_or_or(db, SQLwhere, "version", "%|" + where_version + "|%", "version", "", "version", "||");
		}
		String SQLorder = " order by title,action,id";
		SQL += SQLwhere + SQLorder;
		LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
		for (int i=0; i<rows.size(); i++) {
			HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
			String id = "" + row.get("id");
			myactions.put(id, row);
		}
		return myactions;
	}



	public String workflow_action_select_options(DB db, String where_fromstate, String where_usergroups, String where_usertypes, String where_contentclass, String where_contentgroup, String where_contenttype, String where_version, boolean administrator) {
		return workflow_action_select_options(db, where_fromstate, where_usergroups, where_usertypes, where_contentclass, where_contentgroup, where_contenttype, where_version, administrator, 0);
	}
	public String workflow_action_select_options(DB db, String where_fromstate, String where_usergroups, String where_usertypes, String where_contentclass, String where_contentgroup, String where_contenttype, String where_version, boolean administrator, boolean sameuser) {
		if (sameuser) {
			return workflow_action_select_options(db, where_fromstate, where_usergroups, where_usertypes, where_contentclass, where_contentgroup, where_contenttype, where_version, administrator, 1);
		} else {
			return workflow_action_select_options(db, where_fromstate, where_usergroups, where_usertypes, where_contentclass, where_contentgroup, where_contenttype, where_version, administrator, -1);
		}
	}
	public String workflow_action_select_options(DB db, String where_fromstate, String where_usergroups, String where_usertypes, String where_contentclass, String where_contentgroup, String where_contenttype, String where_version, boolean administrator, int sameuser) {
		if (db == null) return "";
		StringBuffer options = new StringBuffer();
		HashMap<String,HashMap<String,String>> myactions = actions(db, where_fromstate, "*", where_usergroups, where_usertypes, where_contentclass, where_contentgroup, where_contenttype, where_version, administrator, sameuser);
		HashMap<String,String> sortedactions = new HashMap<String,String>();
		Iterator ids = myactions.keySet().iterator();
		while (ids.hasNext()) {
			String myid = "" + ids.next();
			HashMap<String,String> row = (HashMap<String,String>)myactions.get(myid);
			String mytitle = "" + row.get("title");
			if (mytitle.equals("-orders-")) mytitle = text.display("workflows.title.orders");
			String myaction = "" + row.get("action");
			String mytostate = "" + row.get("tostate");
			if (! mytitle.equals("")) {
				sortedactions.put(myid, mytitle + ": " + myaction);
			} else {
				sortedactions.put(myid, myaction);
			}
//			if (administrator) {
				sortedactions.put(myid, "" + sortedactions.get(myid) + " [=&gt;" + mytostate + "]");
//			}
		}
		ids = Common.LinkedHashMapSortedByValue(sortedactions, true).keySet().iterator();
		while (ids.hasNext()) {
			String myid = "" + ids.next();
			HashMap<String,String> row = (HashMap<String,String>)myactions.get(myid);
			if (row != null) {
				String mytitle = "" + row.get("title");
				if (mytitle.equals("-orders-")) mytitle = text.display("workflows.title.orders");
				String myaction = "" + row.get("action");
				String mytostate = "" + row.get("tostate");
				options.append("<option value=\"" + myid + "\">");
				if (! mytitle.equals("")) {
					options.append(mytitle + ": ");
				}
				options.append(myaction);
//				if (administrator) {
					options.append(" [=&gt;" + mytostate + "]");
//				}
				options.append("</option>" + "\r\n");
			}
		}
		return "" + options;
	}



	public boolean permission(DB db, String where_fromstate, String where_tostate, String where_usergroups, String where_usertypes, String where_contentclass, String where_contentgroup, String where_contenttype, String where_version) {
		return permission(db, where_fromstate, where_tostate, where_usergroups, where_usertypes, where_contentclass, where_contentgroup, where_contenttype, where_version, false, true);
	}
	public boolean permission(DB db, String where_fromstate, String where_tostate, String where_usergroups, String where_usertypes, String where_contentclass, String where_contentgroup, String where_contenttype, String where_version, boolean sameuser) {
		return permission(db, where_fromstate, where_tostate, where_usergroups, where_usertypes, where_contentclass, where_contentgroup, where_contenttype, where_version, sameuser, true);
	}
	public boolean permission(DB db, String where_fromstate, String where_tostate, String where_usergroups, String where_usertypes, String where_contentclass, String where_contentgroup, String where_contenttype, String where_version, boolean sameuser, boolean matchstates) {
		return permission(db, where_fromstate, where_tostate, where_usergroups, where_usertypes, where_contentclass, where_contentgroup, where_contenttype, where_version, sameuser, true, true);
	}
	public boolean permission(DB db, String where_fromstate, String where_tostate, String where_usergroups, String where_usertypes, String where_contentclass, String where_contentgroup, String where_contenttype, String where_version, boolean sameuser, boolean matchstates, boolean allowblanks) {
		if (db == null) return false;
		if (matchstates && ((! fromstate.equals(where_fromstate)) || (! tostate.equals(where_tostate)))) {
			// TODO - deprecated - should not be checked here
			return false;
		} else {
			boolean permission = false;
			HashMap<String,HashMap<String,String>> myactions = actions(db, where_fromstate, tostate, where_usergroups, where_usertypes, where_contentclass, where_contentgroup, where_contenttype, where_version, false, sameuser);
			if (myactions.get(id) != null) {
				permission = true;
			} else if ((where_fromstate.equals(where_tostate)) && (allowblanks || (! where_fromstate.equals("")))) {
				permission = false;
				myactions = actions(db, where_fromstate, "*", where_usergroups, where_usertypes, where_contentclass, where_contentgroup, where_contenttype, where_version, false, sameuser);
				if (myactions.size() > 0) {
					permission = true;
				}
			}
			return permission;
		}
	}



	public boolean permissionEnd(DB db, String where_fromstate, String where_usergroups, String where_usertypes, String where_contentclass, String where_contentgroup, String where_contenttype, String where_version, boolean sameuser) {
		if (db == null) return false;
		HashMap<String,HashMap<String,String>> myactions = actions(db, where_fromstate, "", where_usergroups, where_usertypes, where_contentclass, where_contentgroup, where_contenttype, where_version, false, sameuser);
		if (myactions.size() > 0) {
			return true;
		} else {
			return false;
		}
	}



	public boolean permissions(DB db, String where_fromstate, String where_usergroups, String where_usertypes, String where_contentclass, String where_contentgroup, String where_contenttype, String where_version) {
		return permissions(db, where_fromstate, where_usergroups, where_usertypes, where_contentclass, where_contentgroup, where_contenttype, where_version, false);
	}
	public boolean permissions(DB db, String where_fromstate, String where_usergroups, String where_usertypes, String where_contentclass, String where_contentgroup, String where_contenttype, String where_version, boolean sameuser) {
		if (db == null) return false;
		boolean permission = false;
		HashMap<String,HashMap<String,String>> myactions = actions(db, where_fromstate, "*", where_usergroups, where_usertypes, where_contentclass, where_contentgroup, where_contenttype, where_version, false, sameuser);
		if (myactions.size() > 0) {
			permission = true;
		}
		return permission;
	}



	public String[] adminUsernames(Content content, Session session, Configuration config, DB db) {
		HashMap<String,String> userdata = new HashMap<String,String>();
		userdata = adminData(content, session, config, db, true, true, true, true, true, true, true, "username");
		if ((userdata.size() == 0) && (! config.get(db, "superadmin").equals(""))) {
			userdata.put(config.get(db, "superadmin"), config.get(db, "superadmin"));
		}
		return (String[]) userdata.values().toArray(new String[0]);
	}



	public String[] adminEmails(Content content, Session session, Configuration config, DB db) {
		HashMap<String,String> userdata = new HashMap<String,String>();
		if ((id != null) && (! id.equals("")) && (! id.equals("0")) && (! id.equals("-1"))) {
			boolean administrators = (! config.get(db, "workflow_notify_administrators").equals("no"));
			boolean publishers = (! config.get(db, "workflow_notify_publishers").equals("no"));
			boolean editors = (! config.get(db, "workflow_notify_editors").equals("no"));
			boolean creators = (! config.get(db, "workflow_notify_creators").equals("no"));
			boolean developers = (! config.get(db, "workflow_notify_developers").equals("no"));
			boolean users = (! config.get(db, "workflow_notify_users").equals("no"));
			boolean broad_search = (config.get(db, "workflow_notify_broad").equals("yes"));
			userdata = adminData(content, session, config, db, administrators, publishers, editors, creators, developers, users, broad_search, "email");
		}
		if ((userdata.size() == 0) && (! config.get(db, "superadmin_email").equals(""))) {
			userdata.put(config.get(db, "superadmin_email"), config.get(db, "superadmin_email"));
		}
		return (String[]) userdata.values().toArray(new String[0]);
	}



	public String[] adminEmails(Order order, Session session, Configuration config, DB db) {
		HashMap<String,String> userdata = new HashMap<String,String>();
		if ((id != null) && (! id.equals("")) && (! id.equals("0")) && (! id.equals("-1"))) {
			boolean administrators = (! config.get(db, "workflow_notify_administrators").equals("no"));
			boolean broad_search = (config.get(db, "workflow_notify_broad").equals("yes"));
			userdata = adminData(order, session, config, db, administrators, broad_search, "email");
		}
		if ((userdata.size() == 0) && (! config.get(db, "superadmin_email").equals(""))) {
			userdata.put(config.get(db, "superadmin_email"), config.get(db, "superadmin_email"));
		}
		return (String[]) userdata.values().toArray(new String[0]);
	}



	public HashMap<String,String> adminData(Content content, Session session, Configuration config, DB db, boolean administrators, boolean publishers, boolean editors, boolean creators, boolean developers, boolean users, boolean broad_search, String attribute) {
		return adminData(content, session, config, db, administrators, publishers, editors, creators, developers, users, broad_search, attribute, false);
	}
	public HashMap<String,String> adminData(Content content, Session session, Configuration config, DB db, boolean administrators, boolean publishers, boolean editors, boolean creators, boolean developers, boolean users, boolean broad_search, String attribute, boolean sameuser) {
		String SQLusername = "users.username as username, ";
		if (attribute.equals("username")) {
			SQLusername = "";
		}
		String SQL1 = "select distinct users.id as id, " + SQLusername + "users." + attribute + " as " + attribute + " " + content.administratorsSQLfromwhere(db, administrators, publishers, editors, creators, developers, users, broad_search, false) + " and " + db.is_not_blank("users." + attribute);
		String SQL2 = "select distinct users.id as id, " + SQLusername + "users." + attribute + " as " + attribute + " " + content.administratorsSQLfromwhere(db, administrators, publishers, editors, creators, developers, users, broad_search, true) + " and " + db.is_not_blank("users." + attribute);
		String SQL3 = "select distinct users.id as id, " + SQLusername + "users." + attribute + " as " + attribute + " " + content.administratorsSQLfromwhere(db, true, true, true, true, true, true, broad_search, false) + " and " + db.is_not_blank("users." + attribute);
		String SQL4 = "select distinct users.id as id, " + SQLusername + "users." + attribute + " as " + attribute + " " + content.administratorsSQLfromwhere(db, true, true, true, true, true, true, broad_search, true) + " and " + db.is_not_blank("users." + attribute);
		HashMap<String,HashMap<String,String>> myactions = actions(db, content.getStatus(), "*", "*", "*", content.getContentClass(), content.getContentGroup(), content.getContentType(), content.getVersion(), false, sameuser);
		return adminData(myactions, SQL1, SQL2, SQL3, SQL4, session, config, db, attribute);
	}



	public HashMap<String,String> adminData(Order order, Session session, Configuration config, DB db, boolean administrators, boolean broad_search, String attribute) {
		return adminData(order, session, config, db, administrators, broad_search, attribute, false);
	}
	public HashMap<String,String> adminData(Order order, Session session, Configuration config, DB db, boolean administrators, boolean broad_search, String attribute, boolean sameuser) {
		String SQLusername = "users.username as username, ";
		if (attribute.equals("username")) {
			SQLusername = "";
		}
		String SQL1 = "select distinct users.id as id, " + SQLusername + "users." + attribute + " as " + attribute + " " + order.administratorsSQLfromwhere(db, config, administrators, broad_search, false) + " and " + db.is_not_blank("users." + attribute);
		String SQL2 = "select distinct users.id as id, " + SQLusername + "users." + attribute + " as " + attribute + " " + order.administratorsSQLfromwhere(db, config, administrators, broad_search, true) + " and " + db.is_not_blank("users." + attribute);
		String SQL3 = "select distinct users.id as id, " + SQLusername + "users." + attribute + " as " + attribute + " " + order.administratorsSQLfromwhere(db, config, true, broad_search, false) + " and " + db.is_not_blank("users." + attribute);
		String SQL4 = "select distinct users.id as id, " + SQLusername + "users." + attribute + " as " + attribute + " " + order.administratorsSQLfromwhere(db, config, true, broad_search, true) + " and " + db.is_not_blank("users." + attribute);
		HashMap<String,HashMap<String,String>> myactions = actions(db, order.getStatus(), "*", "*", "*", "-order-", "", "", "", false, sameuser);
		return adminData(myactions, SQL1, SQL2, SQL3, SQL4, session, config, db, attribute);
	}



	public HashMap<String,String> adminData(HashMap<String,HashMap<String,String>> myactions, String SQL1, String SQL2, String SQL3, String SQL4, Session session, Configuration config, DB db, String attribute) {
		HashMap<String,String> userdata = new HashMap<String,String>();
		if (myactions.size() > 0) {
			SQL1 += " and ((1=0)";
			SQL2 += " and ((1=0)";
			SQL3 += " and ((1=0)";
			SQL4 += " and ((1=0)";
			Iterator ids = myactions.keySet().iterator();
			while (ids.hasNext()) {
				String id = "" + ids.next();
				HashMap<String,String> row = (HashMap<String,String>)myactions.get(id);
				String workflow_fromstate = "" + row.get("fromstate");
				String workflow_tostate = "" + row.get("tostate");
				String workflow_usergroup = "" + row.get("usergroup");
				String workflow_usertype = "" + row.get("usertype");
				String SQLwhere = "";
				if (! workflow_usertype.equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(workflow_usertype);
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(workflow_usertype, workflow_usertype);
				        HashMap<String,String> user_usertypes = db.query_values("select distinct username from users_usertypes where usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")");
					if (! SQLwhere.equals("")) SQLwhere = SQLwhere + " and ";
					SQLwhere = SQLwhere + "((users.usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")) or (users.username in (" + Common.SQL_list_values((String[])user_usertypes.values().toArray(new String[0])) + ")))";
				}
				if (! workflow_usergroup.equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(workflow_usergroup);
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(workflow_usergroup, workflow_usergroup);
				        HashMap<String,String> user_usergroups = db.query_values("select distinct username from users_usergroups where usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")");
					if (! SQLwhere.equals("")) SQLwhere = SQLwhere + " and ";
					SQLwhere = SQLwhere + "((users.usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")) or (users.username in (" + Common.SQL_list_values((String[])user_usergroups.values().toArray(new String[0])) + ")))";
				}
				if ((workflow_usertype.equals("")) && (workflow_usergroup.equals(""))) {
					SQLwhere = "1=1";
				}
				if ((workflow_fromstate.equals(workflow_tostate)) && (! config.get(db, "workflow_notify_viewers").equals("yes"))) {
					// ignore
					SQLwhere = "";
				}
				if (! SQLwhere.equals("")) SQL1 += " or (" + SQLwhere + ")";
				if (! SQLwhere.equals("")) SQL2 += " or (" + SQLwhere + ")";
				if (! SQLwhere.equals("")) SQL3 += " or (" + SQLwhere + ")";
				if (! SQLwhere.equals("")) SQL4 += " or (" + SQLwhere + ")";
			}
			SQL1 += ")";
			SQL2 += ")";
			SQL3 += ")";
			SQL4 += ")";
		}
		User user = new User(text);
		user.records(session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, SQL1);
		while (user.records(session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, "")) {
			String myusername = user.getUsername();
			String myvalue = user.get(attribute);
			user.readByUsername(session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, myusername);
			if ((user.get(attribute) != null) && (! user.get(attribute).equals(""))) myvalue = user.get(attribute);
			userdata.put(myvalue, myvalue);
		}
		user.records(session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, SQL2);
		while (user.records(session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, "")) {
			String myusername = user.getUsername();
			String myvalue = user.get(attribute);
			user.readByUsername(session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, myusername);
			if ((user.get(attribute) != null) && (! user.get(attribute).equals(""))) myvalue = user.get(attribute);
			userdata.put(myvalue, myvalue);
		}
		if ((userdata.size() == 0) && ((! SQL1.equals(SQL3)) || (! SQL2.equals(SQL4)))) {
			user.records(session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, SQL3);
			while (user.records(session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, "")) {
				String myusername = user.getUsername();
				String myvalue = user.get(attribute);
				user.readByUsername(session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, myusername);
				if ((user.get(attribute) != null) && (! user.get(attribute).equals(""))) myvalue = user.get(attribute);
				userdata.put(myvalue, myvalue);
			}
			user.records(session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, SQL4);
			while (user.records(session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, "")) {
				String myusername = user.getUsername();
				String myvalue = user.get(attribute);
				user.readByUsername(session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config, myusername);
				if ((user.get(attribute) != null) && (! user.get(attribute).equals(""))) myvalue = user.get(attribute);
				userdata.put(myvalue, myvalue);
			}
		}
		return userdata;
	}



	public void update_content(Content contentitem) {
		String[] mycontentchanges = contentchanges.split("[\r\n]+");
		for (int i=0; i<mycontentchanges.length; i++) {
			String contentchange = mycontentchanges[i];
			Matcher contentchangeMatches = Pattern.compile("^([^=]+)=([^\r\n]*)$").matcher(contentchange);
			if (contentchangeMatches.find()) {
				String myname = "" + contentchangeMatches.group(1);
				String myvalue = "" + contentchangeMatches.group(2);
				if (myname.equals("locked")) {
					contentitem.setLocked(Integer.parseInt("0" + myvalue));
				} else if (myname.equals("created")) {
					contentitem.setCreated(myvalue);
				} else if (myname.equals("updated")) {
					contentitem.setUpdated(myvalue);
				} else if (myname.equals("published")) {
					contentitem.setPublished(myvalue);
				} else if (myname.equals("created_by")) {
					contentitem.setCreatedBy(myvalue);
				} else if (myname.equals("updated_by")) {
					contentitem.setUpdatedBy(myvalue);
				} else if (myname.equals("published_by")) {
					contentitem.setPublishedBy(myvalue);
				} else if (myname.equals("scheduled_publish")) {
					contentitem.setScheduledPublish(myvalue);
				} else if (myname.equals("scheduled_unpublish")) {
					contentitem.setScheduledUnpublish(myvalue);
				} else if (myname.equals("revision")) {
					contentitem.setRevision(myvalue);
				} else if (myname.equals("version")) {
					contentitem.setVersion(myvalue);
				} else if (myname.equals("version_master")) {
					contentitem.setVersionMaster(myvalue);
				} else if (myname.equals("title")) {
					contentitem.setTitle(myvalue);
				} else if (myname.equals("searchable")) {
					contentitem.setSearchable(myvalue);
				} else if (myname.equals("url")) {
					contentitem.setUrl(myvalue);
				} else if (myname.equals("author")) {
					contentitem.setAuthor(myvalue);
				} else if (myname.equals("keywords")) {
					contentitem.setKeywords(myvalue);
				} else if (myname.equals("description")) {
					contentitem.setDescription(myvalue);
				} else if (myname.equals("summary")) {
					contentitem.setSummary(myvalue);
				} else if (myname.equals("image1")) {
					contentitem.setImage1(myvalue);
				} else if (myname.equals("image2")) {
					contentitem.setImage2(myvalue);
				} else if (myname.equals("image3")) {
					contentitem.setImage3(myvalue);
				} else if (myname.equals("file1")) {
					contentitem.setFile1(myvalue);
				} else if (myname.equals("file2")) {
					contentitem.setFile2(myvalue);
				} else if (myname.equals("file3")) {
					contentitem.setFile3(myvalue);
				} else if (myname.equals("link1")) {
					contentitem.setLink1(myvalue);
				} else if (myname.equals("link2")) {
					contentitem.setLink2(myvalue);
				} else if (myname.equals("link3")) {
					contentitem.setLink3(myvalue);
				} else if (myname.equals("metainfo")) {
					contentitem.setMetaInfo(myvalue);
				} else if (myname.equals("htmlheader")) {
					contentitem.setHtmlHeader(myvalue);
				} else if (myname.equals("htmlbodyonload")) {
					contentitem.setHtmlBodyOnload(myvalue);
				} else if (myname.equals("contentformat")) {
					contentitem.setContentFormat(myvalue);
				} else if (myname.equals("content")) {
					contentitem.setContent(myvalue);
				} else if (myname.equals("template")) {
					contentitem.setTemplate(myvalue);
				} else if (myname.equals("stylesheet")) {
					contentitem.setStyleSheet(myvalue);
				} else if (myname.equals("contentclass")) {
					contentitem.setContentClass(myvalue);
				} else if (myname.equals("contenttype")) {
					contentitem.setContentType(myvalue);
				} else if (myname.equals("contentgroup")) {
					contentitem.setContentGroup(myvalue);
				} else if (myname.equals("users_type")) {
					contentitem.setUsersType(myvalue);
				} else if (myname.equals("users_group")) {
					contentitem.setUsersGroup(myvalue);
				} else if (myname.equals("creators_type")) {
					contentitem.setCreatorsType(myvalue);
				} else if (myname.equals("creators_group")) {
					contentitem.setCreatorsGroup(myvalue);
				} else if (myname.equals("developers_type")) {
					contentitem.setDevelopersType(myvalue);
				} else if (myname.equals("developers_group")) {
					contentitem.setDevelopersGroup(myvalue);
				} else if (myname.equals("editors_type")) {
					contentitem.setEditorsType(myvalue);
				} else if (myname.equals("editors_group")) {
					contentitem.setEditorsGroup(myvalue);
				} else if (myname.equals("publishers_type")) {
					contentitem.setPublishersType(myvalue);
				} else if (myname.equals("publishers_group")) {
					contentitem.setPublishersGroup(myvalue);
				} else if (myname.equals("administrators_type")) {
					contentitem.setAdministratorsType(myvalue);
				} else if (myname.equals("administrators_group")) {
					contentitem.setAdministratorsGroup(myvalue);
				} else if (myname.equals("checkedout")) {
					contentitem.setCheckedOut(myvalue);
				} else if (myname.equals("page_up")) {
					contentitem.setPageUp(myvalue);
				} else if (myname.equals("page_top")) {
					contentitem.setPageTop(myvalue);
				} else if (myname.equals("page_next")) {
					contentitem.setPageNext(myvalue);
				} else if (myname.equals("page_previous")) {
					contentitem.setPagePrevious(myvalue);
				} else if (myname.equals("page_last")) {
					contentitem.setPageLast(myvalue);
				} else if (myname.equals("page_first")) {
					contentitem.setPageFirst(myvalue);
				} else if (myname.equals("product_code")) {
					contentitem.setProductCode(myvalue);
				} else if (myname.equals("product_currency")) {
					contentitem.setProductCurrency(myvalue);
				} else if (myname.equals("product_price")) {
					contentitem.setProductPrice(myvalue);
				} else if (myname.equals("product_period")) {
					contentitem.setProductPeriod(myvalue);
				} else if (myname.equals("product_stock")) {
					contentitem.setProductStock(myvalue);
				} else if (myname.equals("product_comment")) {
					contentitem.setProductComment(myvalue);
				} else if (myname.equals("product_email")) {
					contentitem.setProductEmail(myvalue);
				} else if (myname.equals("product_url")) {
					contentitem.setProductURL(myvalue);
				} else if (myname.equals("product_info")) {
					contentitem.setProductInfo(myvalue);
				} else if (myname.equals("product_options")) {
					contentitem.setProductOptions(myvalue);
				} else if (myname.equals("product_delivery")) {
					contentitem.setProductDelivery(myvalue);
				} else if (myname.equals("product_user")) {
					contentitem.setProductUser(myvalue);
				} else if (myname.equals("product_page")) {
					contentitem.setProductPage(myvalue);
				} else if (myname.equals("product_program")) {
					contentitem.setProductProgram(myvalue);
				} else if (myname.equals("product_hosting")) {
					contentitem.setProductHosting(myvalue);
				}
			} else if (contentchange.equals("ARCHIVE")) {
				autoarchive = true;
			} else if (contentchange.equals("CHECKIN")) {
				autocheckin = true;
			} else if (contentchange.equals("CHECKOUT")) {
				autocheckout = true;
			} else if (contentchange.equals("DELETE")) {
				autodelete = true;
			} else if (contentchange.equals("PUBLISH")) {
				autopublish = true;
			} else if (contentchange.equals("UNPUBLISH")) {
				autounpublish = true;
			} else if (contentchange.equals("UNSCHEDULE")) {
				autounschedule = true;
				contentitem.setScheduledPublish("");
				contentitem.setScheduledUnpublish("");
			} else if (contentchange.equals("LOCK USER")) {
				contentitem.setLockedUser(1);
				lock_user = true;
			} else if (contentchange.equals("UNLOCK USER")) {
				contentitem.setLockedUser(0);
				unlock_user = true;
			} else if (contentchange.equals("LOCK CREATOR")) {
				contentitem.setLockedCreator(1);
				lock_creator = true;
			} else if (contentchange.equals("UNLOCK CREATOR")) {
				contentitem.setLockedCreator(0);
				unlock_creator = true;
			} else if (contentchange.equals("LOCK DEVELOPER")) {
				contentitem.setLockedDeveloper(1);
				lock_developer = true;
			} else if (contentchange.equals("UNLOCK DEVELOPER")) {
				contentitem.setLockedDeveloper(0);
				unlock_developer = true;
			} else if (contentchange.equals("LOCK EDITOR")) {
				contentitem.setLockedEditor(1);
				lock_editor = true;
			} else if (contentchange.equals("UNLOCK EDITOR")) {
				contentitem.setLockedEditor(0);
				unlock_editor = true;
			} else if (contentchange.equals("LOCK PUBLISHER")) {
				contentitem.setLockedPublisher(1);
				lock_publisher = true;
			} else if (contentchange.equals("UNLOCK PUBLISHER")) {
				contentitem.setLockedPublisher(0);
				unlock_publisher = true;
			} else if (contentchange.equals("LOCK ADMINISTRATOR")) {
				contentitem.setLockedAdministrator(1);
				lock_administrator = true;
			} else if (contentchange.equals("UNLOCK ADMINISTRATOR")) {
				contentitem.setLockedAdministrator(0);
				unlock_administrator = true;
			} else if (contentchange.equals("LOCK SCHEDULE")) {
				contentitem.setLockedSchedule(1);
				lock_schedule = true;
			} else if (contentchange.equals("UNLOCK SCHEDULE")) {
				contentitem.setLockedSchedule(0);
				unlock_schedule = true;
			} else if (contentchange.equals("LOCK UNSCHEDULE")) {
				contentitem.setLockedUnschedule(1);
				lock_unschedule = true;
			} else if (contentchange.equals("UNLOCK UNSCHEDULE")) {
				contentitem.setLockedUnschedule(0);
				unlock_unschedule = true;
			}
		}
	}



	public String workflowprogram_select_options(String DOCUMENT_ROOT, String selected) {
		String options = "";
		LinkedHashMap<String,String> records_array = new LinkedHashMap<String,String>();
		if (Common.folderExists(DOCUMENT_ROOT + "/" + text.display("adminpath") + "/workflowaction/")) {
			String[] files = new File(DOCUMENT_ROOT + "/" + text.display("adminpath") + "/workflowaction/").list();
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



	public String getAction() {
		return action;
	}
	public void setAction(String value) {
		title = action;
	}



	public String getFromState() {
		return fromstate;
	}
	public void setFromState(String value) {
		fromstate = value;
	}



	public String getToState() {
		return tostate;
	}
	public void setToState(String value) {
		tostate = value;
	}



	public String getUserType() {
		return usertype;
	}
	public void setUserType(String value) {
		usertype = value;
	}



	public String getUserGroup() {
		return usergroup;
	}
	public void setUserGroup(String value) {
		usergroup = value;
	}



	public String getContentClass() {
		return contentclass;
	}
	public void setContentClass(String value) {
		contentclass = value;
	}



	public String getContentType() {
		return contenttype;
	}
	public void setContentType(String value) {
		contenttype = value;
	}



	public String getContentGroup() {
		return contentgroup;
	}
	public void setContentGroup(String value) {
		contentgroup = value;
	}



	public String getVersion() {
		return version;
	}
	public void setVersion(String value) {
		version = value;
	}



	public String getNotifyEmail() {
		return notify_email;
	}
	public void setNotifyEmail(String value) {
		notify_email = value;
	}



	public String getContentChanges() {
		return contentchanges;
	}
	public void setContentChanges(String value) {
		contentchanges = value;
	}



	public String getWorkflowProgram() {
		return workflow_program;
	}
	public void setWorkflowProgram(String value) {
		workflow_program = value;
	}



	public String getUserRestriction() {
		return userrestriction;
	}
	public void setUserRestriction(String value) {
		userrestriction = value;
	}



	public boolean getAutoArchive() {
		return autoarchive;
	}



	public boolean getAutoCheckin() {
		return autocheckin;
	}



	public boolean getAutoCheckout() {
		return autocheckout;
	}



	public boolean getAutoDelete() {
		return autodelete;
	}



	public boolean getAutoPublish() {
		return autopublish;
	}



	public boolean getAutoUnpublish() {
		return autounpublish;
	}



	public boolean getAutoUnschedule() {
		return autounschedule;
	}



	public boolean getLockUser() {
		return lock_user;
	}



	public boolean getUnlockUser() {
		return unlock_user;
	}



	public boolean getLockCreator() {
		return lock_creator;
	}



	public boolean getUnlockCreator() {
		return unlock_creator;
	}



	public boolean getLockDeveloper() {
		return lock_developer;
	}



	public boolean getUnlockDeveloper() {
		return unlock_developer;
	}



	public boolean getLockEditor() {
		return lock_editor;
	}



	public boolean getUnlockEditor() {
		return unlock_editor;
	}



	public boolean getLockPublisher() {
		return lock_publisher;
	}



	public boolean getUnlockPublisher() {
		return unlock_publisher;
	}



	public boolean getLockAdministrator() {
		return lock_administrator;
	}



	public boolean getUnlockAdministrator() {
		return unlock_administrator;
	}



	public boolean getLockSchedule() {
		return lock_schedule;
	}



	public boolean getUnlockSchedule() {
		return unlock_schedule;
	}



	public boolean getLockUnschedule() {
		return lock_unschedule;
	}



	public boolean getUnlockUnschedule() {
		return unlock_unschedule;
	}



}
