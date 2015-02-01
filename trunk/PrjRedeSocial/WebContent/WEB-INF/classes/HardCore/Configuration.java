package HardCore;

import java.util.HashMap;
import java.sql.*;

public class Configuration {
	private HashMap<String,String> config = new HashMap<String,String>();



	public String get(DB db, String name) {
		if (config.containsKey(name)) {
			return "" + config.get(name);
		} else if (db == null) {
			return "";
		} else {
@SuppressWarnings("unchecked")
			HashMap<String,String> row = (HashMap<String,String>)Cache.get(db, "config", "" + Common.SQL_clean(name));
			if (row == null) {
				row = db.query_record("select configvalue from config where configname=" + db.quote(Common.SQL_clean(name)));
				if (row != null) Cache.set(db, "config", "" + Common.SQL_clean(name), row);
			}
			if ((row == null) && ((config.get("database_version") == null) || ((""+config.get("database_version")).compareTo("2.1") < 0))) {
				row = db.query_record("select config.value as configvalue from config where config.name=" + db.quote(Common.SQL_clean(name)), false);
				if (row != null) Cache.set(db, "config", "" + Common.SQL_clean(name), row);
			}
			if (row == null) {
				row = new HashMap<String,String>();
				row.put("configvalue", "");
				Cache.set(db, "config", "" + Common.SQL_clean(name), row);
			}
			if (row != null) {
				config.put(name, "" + row.get("configvalue"));
			}
			if (config.get(name) != null) {
				return "" + config.get(name);
			} else {
				config.put(name, "");
				return "";
			}
		}
	}



	public void set(DB db, String name, String value) {
		if (db == null) return;
		if (name == null) return;
		if (name.equals("")) return;
		HashMap<String,String> row = db.query_record("select configvalue from config where configname=" + db.quote(Common.SQL_clean(name)));
		if (row != null) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("configvalue", value);
			db.update("config", "configname", name, mydata);
		} else {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("configname", name);
			mydata.put("configvalue", value);
			db.insert("config", mydata);
		}
		config.put(name, value);
		Cache.clear(db, "config");
	}



	public void delete(DB db, String name) {
		if (db == null) return;
		if (name == null) return;
		if (name.equals("")) return;
		db.execute("delete from config where configname=" + db.quote(Common.SQL_clean(name)));
		config.put(name, "");
		Cache.clear(db, "config");
	}



	public void clear(DB db, String name) {
		config.remove(name);
		Cache.clear(db, "config", "" + Common.SQL_clean(name));
	}



	public String getDB(DB db, String name) {
		clear(db, name);
		return get(db, name);
	}



	public String get(String name) {
		return getTemp(name);
	}
	public String getTemp(String name) {
		if (config.containsKey(name)) {
			return "" + config.get(name);
		} else {
			return "";
		}
	}



	public void set(String name, String value) {
		setTemp(name, value);
	}
	public void setTemp(String name, String value) {
		config.put(name, value);
	}



}
