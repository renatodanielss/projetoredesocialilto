package HardCore;

import java.sql.*;
import java.util.HashMap;

public class Imageformat {
	private String id = "";
	private String filenameextension = "";

	private	Statement rs = null;



	public Imageformat() {
		init();
	}



	public void init() {
		id = "";
		filenameextension = "";
	}



	public void read(DB db, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			String SQL = "select * from imageformats where id=" + Common.integer(id);
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void readImageformat(DB db, String filenameextensionvalue) {
		if (db == null) return;
		if (! filenameextensionvalue.equals("")) {
			String SQL = "select * from imageformats where filenameextension='" + Common.SQL_clean(filenameextensionvalue) + "'";
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
		filenameextension = "" + record.get("filenameextension");
	}



	public void getForm(Request request) {
		filenameextension = "" + request.getParameter("filenameextension");
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("filenameextension", "" + filenameextension);
		db.insert("imageformats", mydata);
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("filenameextension", "" + filenameextension);
			db.update("imageformats", "id", id, mydata);
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete("imageformats", "id", id);
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



	public String getId() {
		return id;
	}
	public void setId(String value) {
		id = value;
	}



	public String getFilenameextension() {
		return filenameextension;
	}
	public void setFilenameextension(String value) {
		filenameextension = value;
	}



}
