package HardCore;

import java.sql.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Usertype {
	private String id = "";
	private String usertype = "";
	private String parenttype = "";
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
	private HashMap<String,String> subtype = new HashMap<String,String>();
	private HashMap<String,String> supertype = new HashMap<String,String>();

	private	Statement rs = null;



	public Usertype() {
		init();
	}



	public void init() {
		id = "";
		usertype = "";
		parenttype = "";
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
		subtype = new HashMap<String,String>();
		supertype = new HashMap<String,String>();
	}



	public void read(DB db, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			String SQL = "select * from usertypes where id=" + Common.integer(id);
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void readUsertype(DB db, String usertypevalue) {
		if (db == null) return;
		if (! usertypevalue.equals("")) {
			String SQL = "select * from usertypes where usertype='" + Common.SQL_clean(usertypevalue) + "'";
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
		usertype = "" + record.get("usertype");
		parenttype = "" + record.get("parenttype");
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
		usertype = "" + request.getParameter("usertype");
		parenttype = "" + request.getParameter("parenttype");
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
			if (name.startsWith("subtype")) {
				subtype.put(value, value);
			}
			if (name.startsWith("supertype")) {
				supertype.put(value, value);
			}
		}
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("usertype", "" + usertype);
		mydata.put("parenttype", "" + parenttype);
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
		db.insert("usertypes", mydata);

		Iterator subtypes = subtype.keySet().iterator();
		while (subtypes.hasNext()) {
			String mysubtype = "" + subtypes.next();
			HashMap<String,String> mydata2 = new HashMap<String,String>();
			mydata2.put("usertype", "" + usertype);
			mydata2.put("subtype", "" + mysubtype);
			db.insert("usertypes2", mydata2);
		}
		Iterator supertypes = supertype.keySet().iterator();
		while (supertypes.hasNext()) {
			String mysupertype = "" + supertypes.next();
			HashMap<String,String> mydata2 = new HashMap<String,String>();
			mydata2.put("usertype", "" + mysupertype);
			mydata2.put("subtype", "" + usertype);
			db.insert("usertypes2", mydata2);
		}
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			Usertype old = new Usertype();
			old.read(db, id);
			db.delete("usertypes2", "usertype", old.getUsertype());
			db.delete("usertypes2", "subtype", old.getUsertype());

			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("usertype", "" + usertype);
			mydata.put("parenttype", "" + parenttype);
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
			db.update("usertypes", "id", id, mydata);

			if ((! old.getUsertype().equals("")) && (! usertype.equals(""))) {
				db.rename("usertypes", "parenttype", old.getUsertype(), usertype);
			}

			Iterator subtypes = subtype.keySet().iterator();
			while (subtypes.hasNext()) {
				String mysubtype = "" + subtypes.next();
				HashMap<String,String> mydata2 = new HashMap<String,String>();
				mydata2.put("usertype", "" + usertype);
				mydata2.put("subtype", "" + mysubtype);
				db.insert("usertypes2", mydata2);
			}
			Iterator supertypes = supertype.keySet().iterator();
			while (supertypes.hasNext()) {
				String mysupertype = "" + supertypes.next();
				HashMap<String,String> mydata2 = new HashMap<String,String>();
				mydata2.put("usertype", "" + mysupertype);
				mydata2.put("subtype", "" + usertype);
				db.insert("usertypes2", mydata2);
			}
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			Usertype old = new Usertype();
			old.read(db, id);
			db.delete("usertypes2", "usertype", old.getUsertype());
			db.delete("usertypes2", "subtype", old.getUsertype());
			db.delete("usertypes", "id", id);
		}
	}



	public void renameUsertype(DB db, String old_usertype, String new_usertype) {
		if (db == null) return;
		db.rename("usertypes2", "usertype", old_usertype, new_usertype);
		db.rename("usertypes2", "subtype", old_usertype, new_usertype);
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
		return Common.select_options(db, "usertypes", "usertype", selected);
	}



	public static String dtree_options(DB db, String pre, String mid, String post) {
		return Common.output_column_X2(db, "usertypes", "usertype", pre, mid, post);
	}



	public HashMap<String,String> subtypes(DB db) {
		HashMap<String,String> subtypes = new HashMap<String,String>();
		if (db == null) return subtypes;
		String SQL = "select subtype from usertypes2 where usertype='" + Common.SQL_clean(usertype) + "' order by subtype";
		LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
		if (rows != null) {
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				String mysubtype = "" + row.get("subtype");
				subtypes.put(mysubtype, mysubtype);
			}
		}
		return subtypes;
	}



	public HashMap<String,String> supertypes(DB db) {
		HashMap<String,String> supertypes = new HashMap<String,String>();
		if (db == null) return supertypes;
		String SQL = "select usertype from usertypes2 where subtype='" + Common.SQL_clean(usertype) + "' order by usertype";
		LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
		if (rows != null) {
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				String mysupertype = "" + row.get("usertype");
				supertypes.put(mysupertype, mysupertype);
			}
		}
		return supertypes;
	}



	public HashMap<String,String> usertypes(DB db) {
		HashMap<String,String> mytypes = new HashMap<String,String>();
		if (db == null) return mytypes;
		String SQL = "select usertype from usertypes order by usertype";
		LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
		if (rows != null) {
			for (int i=0; i<rows.size(); i++) {
				HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
				String myusertype = "" + row.get("usertype");
				mytypes.put(myusertype, myusertype);
			}
		}
		return mytypes;
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



	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String value) {
		usertype = value;
	}



	public String getParenttype() {
		return parenttype;
	}
	public void setParenttype(String value) {
		parenttype = value;
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
