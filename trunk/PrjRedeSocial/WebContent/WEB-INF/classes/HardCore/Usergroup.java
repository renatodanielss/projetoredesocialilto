package HardCore;

import java.sql.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Usergroup {
	private String id = "";
	private String usergroup = "";
	private String parentgroup = "";
	private String login_page = "";
	private String users_type = "";
	private String users_group = "";
	private String creators_type = "";
	private String creators_group = "";
	private String editors_type = "";
	private String editors_group = "";
	private String publishers_type = "";
	private String publishers_group = "";
	private String administrators_type = "";
	private String administrators_group = "";
	private String subscribers_type = "";
	private String subscribers_group = "";
	private HashMap<String,String> subgroup = new HashMap<String,String>();
	private HashMap<String,String> supergroup = new HashMap<String,String>();

	private	Statement rs = null;



	public Usergroup() {
		init();
	}



	public void init() {
		id = "";
		usergroup = "";
		parentgroup = "";
		login_page = "";
		users_type = "";
		users_group = "";
		creators_type = "";
		creators_group = "";
		editors_type = "";
		editors_group = "";
		publishers_type = "";
		publishers_group = "";
		administrators_type = "";
		administrators_group = "";
		subscribers_type = "";
		subscribers_group = "";
		subgroup = new HashMap<String,String>();
		supergroup = new HashMap<String,String>();
	}



	public void read(DB db, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			String SQL = "select * from usergroups where id=" + Common.integer(id);
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void readUsergroup(DB db, String usergroupvalue) {
		if (db == null) return;
		if (! usergroupvalue.equals("")) {
			String SQL = "select * from usergroups where usergroup='" + Common.SQL_clean(usergroupvalue) + "'";
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
		usergroup = "" + record.get("usergroup");
		parentgroup = "" + record.get("parentgroup");
		login_page = "" + record.get("login_page");
		users_type = "" + record.get("users_type");
		users_group = "" + record.get("users_group");
		creators_type = "" + record.get("creators_type");
		creators_group = "" + record.get("creators_group");
		editors_type = "" + record.get("editors_type");
		editors_group = "" + record.get("editors_group");
		publishers_type = "" + record.get("publishers_type");
		publishers_group = "" + record.get("publishers_group");
		administrators_type = "" + record.get("administrators_type");
		administrators_group = "" + record.get("administrators_group");
		subscribers_type = "" + record.get("subscribers_type");
		subscribers_group = "" + record.get("subscribers_group");
	}



	public void getForm(Request request) {
		usergroup = "" + request.getParameter("usergroup");
		parentgroup = "" + request.getParameter("parentgroup");
		login_page = "" + request.getParameter("login_page");
		users_type = "" + request.getParameter("users_type");
		users_group = "" + request.getParameter("users_group");
		creators_type = "" + request.getParameter("creators_type");
		creators_group = "" + request.getParameter("creators_group");
		editors_type = "" + request.getParameter("editors_type");
		editors_group = "" + request.getParameter("editors_group");
		publishers_type = "" + request.getParameter("publishers_type");
		publishers_group = "" + request.getParameter("publishers_group");
		administrators_type = "" + request.getParameter("administrators_type");
		administrators_group = "" + request.getParameter("administrators_group");
		subscribers_type = "" + request.getParameter("subscribers_type");
		subscribers_group = "" + request.getParameter("subscribers_group");
		Enumeration parameternames = request.getParameterNames();
		while (parameternames.hasMoreElements()) {
			String name = "" + parameternames.nextElement();
			String value = "" + request.getParameter(name);
			if (name.startsWith("subgroup")) {
				subgroup.put(value, value);
			}
			if (name.startsWith("supergroup")) {
				supergroup.put(value, value);
			}
		}
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("usergroup", "" + usergroup);
		mydata.put("parentgroup", "" + parentgroup);
		mydata.put("login_page", "" + login_page);
		mydata.put("users_type", "" + users_type);
		mydata.put("users_group", "" + users_group);
		mydata.put("creators_type", "" + creators_type);
		mydata.put("creators_group", "" + creators_group);
		mydata.put("editors_type", "" + editors_type);
		mydata.put("editors_group", "" + editors_group);
		mydata.put("publishers_type", "" + publishers_type);
		mydata.put("publishers_group", "" + publishers_group);
		mydata.put("administrators_type", "" + administrators_type);
		mydata.put("administrators_group", "" + administrators_group);
		mydata.put("subscribers_type", "" + subscribers_type);
		mydata.put("subscribers_group", "" + subscribers_group);
		db.insert("usergroups", mydata);

		Iterator subgroups = subgroup.keySet().iterator();
		while (subgroups.hasNext()) {
			String mysubgroup = "" + subgroups.next();
			HashMap<String,String> mydata2 = new HashMap<String,String>();
			mydata2.put("usergroup", "" + usergroup);
			mydata2.put("subgroup", "" + mysubgroup);
			db.insert("usergroups2", mydata2);
		}
		Iterator supergroups = supergroup.keySet().iterator();
		while (supergroups.hasNext()) {
			String mysupergroup = "" + supergroups.next();
			HashMap<String,String> mydata2 = new HashMap<String,String>();
			mydata2.put("usergroup", "" + mysupergroup);
			mydata2.put("subgroup", "" + usergroup);
			db.insert("usergroups2", mydata2);
		}
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			Usergroup old = new Usergroup();
			old.read(db, id);
			db.delete("usergroups2", "usergroup", old.getUsergroup());
			db.delete("usergroups2", "subgroup", old.getUsergroup());

			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("usergroup", "" + usergroup);
			mydata.put("parentgroup", "" + parentgroup);
			mydata.put("login_page", "" + login_page);
			mydata.put("users_type", "" + users_type);
			mydata.put("users_group", "" + users_group);
			mydata.put("creators_type", "" + creators_type);
			mydata.put("creators_group", "" + creators_group);
			mydata.put("editors_type", "" + editors_type);
			mydata.put("editors_group", "" + editors_group);
			mydata.put("publishers_type", "" + publishers_type);
			mydata.put("publishers_group", "" + publishers_group);
			mydata.put("administrators_type", "" + administrators_type);
			mydata.put("administrators_group", "" + administrators_group);
			mydata.put("subscribers_type", "" + subscribers_type);
			mydata.put("subscribers_group", "" + subscribers_group);
			db.update("usergroups", "id", id, mydata);

			if ((! old.getUsergroup().equals("")) && (! usergroup.equals(""))) {
				db.rename("usergroups", "parentgroup", old.getUsergroup(), usergroup);
			}

			Iterator subgroups = subgroup.keySet().iterator();
			while (subgroups.hasNext()) {
				String mysubgroup = "" + subgroups.next();
				HashMap<String,String> mydata2 = new HashMap<String,String>();
				mydata2.put("usergroup", "" + usergroup);
				mydata2.put("subgroup", "" + mysubgroup);
				db.insert("usergroups2", mydata2);
			}
			Iterator supergroups = supergroup.keySet().iterator();
			while (supergroups.hasNext()) {
				String mysupergroup = "" + supergroups.next();
				HashMap<String,String> mydata2 = new HashMap<String,String>();
				mydata2.put("usergroup", "" + mysupergroup);
				mydata2.put("subgroup", "" + usergroup);
				db.insert("usergroups2", mydata2);
			}
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			Usergroup old = new Usergroup();
			old.read(db, id);
			db.delete("usergroups2", "usergroup", old.getUsergroup());
			db.delete("usergroups2", "subgroup", old.getUsergroup());
			db.delete("usergroups", "id", id);
		}
	}



	public void renameUsergroup(DB db, String old_usergroup, String new_usergroup) {
		if (db == null) return;
		db.rename("usergroups2", "usergroup", old_usergroup, new_usergroup);
		db.rename("usergroups2", "subgroup", old_usergroup, new_usergroup);
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



	public String select_options(DB db, String selected) {
		return Common.select_options(db, "usergroups", "usergroup", selected);
	}



	public static String dtree_options(DB db, String pre, String mid, String post) {
		return Common.output_column_X2(db, "usergroups", "usergroup", pre, mid, post);
	}



	public HashMap<String,String> subgroups(DB db) {
		HashMap<String,String> subgroups = new HashMap<String,String>();
		if (db == null) return subgroups;
		String SQL = "select subgroup from usergroups2 where usergroup='" + Common.SQL_clean(usergroup) + "' order by subgroup";
		LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
		if (rows != null) {
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				String mysubgroup = "" + row.get("subgroup");
				subgroups.put(mysubgroup, mysubgroup);
			}
		}
		return subgroups;
	}



	public HashMap<String,String> supergroups(DB db) {
		HashMap<String,String> supergroups = new HashMap<String,String>();
		if (db == null) return supergroups;
		String SQL = "select usergroup from usergroups2 where subgroup='" + Common.SQL_clean(usergroup) + "' order by usergroup";
		LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
		if (rows != null) {
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				String mysupergroup = "" + row.get("usergroup");
				supergroups.put(mysupergroup, mysupergroup);
			}
		}
		return supergroups;
	}



	public HashMap<String,String> usergroups(DB db) {
		HashMap<String,String> mygroups = new HashMap<String,String>();
		if (db == null) return mygroups;
		String SQL = "select usergroup from usergroups order by usergroup";
		LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
		if (rows != null) {
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				String myusergroup = "" + row.get("usergroup");
				mygroups.put(myusergroup, myusergroup);
			}
		}
		return mygroups;
	}



	public String usertype_select_options(DB db, String selected) {
		User myuser = new User();
		return myuser.usertype_select_options(db, selected);
	}



	public String usergroup_select_options(DB db, String selected) {
		User myuser = new User();
		return myuser.usergroup_select_options(db, selected);
	}



	public String getId() {
		return id;
	}
	public void setId(String value) {
		id = value;
	}



	public String getUsergroup() {
		return usergroup;
	}
	public void setUsergroup(String value) {
		usergroup = value;
	}



	public String getParentgroup() {
		return parentgroup;
	}
	public void setParentgroup(String value) {
		parentgroup = value;
	}



	public String getLoginPage() {
		return login_page;
	}
	public void setLoginPage(String value) {
		login_page = value;
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



	public String getSubscribersType() {
		return subscribers_type;
	}
	public void setSubscribersType(String value) {
		subscribers_type = value;
	}



	public String getSubscribersGroup() {
		return subscribers_group;
	}
	public void setSubscribersGroup(String value) {
		subscribers_group = value;
	}



}
