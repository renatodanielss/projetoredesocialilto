package HardCore;

import java.sql.*;
import java.util.HashMap;
import java.util.regex.*;

public class Permission {
	private String id = "";
	private String action = "";
	private String resource = "";
	private String username = "";
	private String userclass = "";
	private String usergroup = "";
	private String usertype = "";

	private	Statement rs = null;



	public Permission() {
		init();
	}



	public void init() {
		id = "";
		action = "";
		resource = "";
		username = "";
		userclass = "";
		usergroup = "";
		usertype = "";
	}



	public Permission copy() {
		Permission mypermission = new Permission();
		mypermission.setId(id);
		mypermission.setAction(action);
		mypermission.setResource(resource);
		mypermission.setUsername(username);
		mypermission.setUserclass(userclass);
		mypermission.setUsergroup(usergroup);
		mypermission.setUsertype(usertype);
		return mypermission;
	}



	public boolean exists(DB db) {
		if (db == null) return false;
		boolean value = false;
		String SQL;
		if (db.db_type(db.getDatabase()).equals("oracle")) {
			SQL = "select * from permissions where action='" + Common.SQL_clean(action) + "' and \"resource\" like '" + Common.SQL_clean(resource) + "' and username='" + Common.SQL_clean(username) + "' and userclass='" + Common.SQL_clean(userclass) + "' and usergroup='" + Common.SQL_clean(usergroup) + "' and usertype='" + Common.SQL_clean(usertype) + "'";
		} else {
			SQL = "select * from permissions where action='" + Common.SQL_clean(action) + "' and resource like '" + Common.SQL_clean(resource) + "' and username='" + Common.SQL_clean(username) + "' and userclass='" + Common.SQL_clean(userclass) + "' and usergroup='" + Common.SQL_clean(usergroup) + "' and usertype='" + Common.SQL_clean(usertype) + "'";
		}
		HashMap<String,String> row = db.query_record(SQL);
		if (row != null) {
			value = true;
		} else {
			value = false;
		}
		return value;
	}



	public void read(DB db, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			String SQL = "select * from permissions where id=" + Common.SQL_clean(id);
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		} else {
			init();
		}
	}



	public void getRecord(DB db, HashMap<String,String> record) {
		id = "" + record.get("id");
		action = "" + record.get("action");
		resource = "" + record.get("resource");
		username = "" + record.get("username");
		userclass = "" + record.get("userclass");
		usergroup = "" + record.get("usergroup");
		usertype = "" + record.get("usertype");
	}



	public void getForm(Request request) {
		action = request.getParameter("action");
		resource = request.getParameter("resource");
		username = request.getParameter("username");
		userclass = request.getParameter("userclass");
		usergroup = request.getParameter("usergroup");
		usertype = request.getParameter("usertype");
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("action", "" + action);
		mydata.put("resource", "" + resource);
		mydata.put("username", "" + username);
		mydata.put("userclass", "" + userclass);
		mydata.put("usergroup", "" + usergroup);
		mydata.put("usertype", "" + usertype);
		db.insert("permissions", mydata);
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("action", "" + action);
			mydata.put("resource", "" + resource);
			mydata.put("username", "" + username);
			mydata.put("userclass", "" + userclass);
			mydata.put("usergroup", "" + usergroup);
			mydata.put("usertype", "" + usertype);
			db.update("permissions", "id", id, mydata);
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete("permissions", "id", id);
		}
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



	public HashMap<String,Permission> permissions(DB db) {
		if (db == null) return new HashMap<String,Permission>();
		HashMap<String,Permission> mypermissions = new HashMap<String,Permission>();
		String SQL = "select * from permissions where 1=1";
		if (! action.equals("")) {
			SQL += " and action='" + Common.SQL_clean(action) + "'";
		}
		if (! resource.equals("")) {
			if (db.db_type(db.getDatabase()).equals("oracle")) {
				SQL += " and \"resource\" like '" + Common.SQL_clean(resource) + "'";
			} else {
				SQL += " and resource like '" + Common.SQL_clean(resource) + "'";
			}
		}
		SQL += " and (" + db.is_blank("username") + " or username='" + Common.SQL_clean(username) + "')";
		SQL += " and (" + db.is_blank("userclass") + " or userclass='" + Common.SQL_clean(userclass) + "')";
		SQL += " and (" + db.is_blank("usergroup") + " or usergroup='" + Common.SQL_clean(usergroup) + "')";
		SQL += " and (" + db.is_blank("usertype") + " or usertype='" + Common.SQL_clean(usertype) + "')";
		if (db.db_type(db.getDatabase()).equals("oracle")) {
			SQL += " order by \"resource\",action,username,userclass,usergroup,usertype,id";
		} else {
			SQL += " order by resource,action,username,userclass,usergroup,usertype,id";
		}
		Permission mypermission = new Permission();
		mypermission.records(db, SQL);
		while (mypermission.records(db, "")) {
			mypermissions.put("" + mypermission.getId(), mypermission.copy());
		}
		return mypermissions;
	}



	public String getId() {
		return id;
	}
	public void setId(String value) {
		id = value;
	}



	public String getAction() {
		return action;
	}
	public void setAction(String value) {
		action = value;
	}



	public String getResource() {
		return resource;
	}
	public void setResource(String value) {
		resource = value;
	}



	public String getUsername() {
		return username;
	}
	public void setUsername(String value) {
		username = value;
	}



	public String getUserclass() {
		return userclass;
	}
	public void setUserclass(String value) {
		userclass = value;
	}



	public String getUsergroup() {
		return usergroup;
	}
	public void setUsergroup(String value) {
		usergroup = value;
	}



	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String value) {
		usertype = value;
	}



}
