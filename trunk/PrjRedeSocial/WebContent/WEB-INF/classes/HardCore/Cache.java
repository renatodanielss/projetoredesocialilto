package HardCore;

import java.io.*;
import java.io.File;
import java.lang.Integer;
import java.net.URLEncoder;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.jcs.JCS.*;

public class Cache {
	static public boolean CACHE = true;
	static public boolean DEBUG = false;



	public Cache() {
		init();
	}



	static public void init() {
		if (! CACHE) return;
		if (DEBUG) System.out.println("HardCore.Cache.init:");
	}



	static public org.apache.jcs.JCS init(DB db) {
		if (! CACHE) return null;
		if (db == null) return null;
		if (DEBUG) System.out.println("HardCore.Cache.init:" + db.getDatabaseName());

		org.apache.jcs.JCS cache = null;
		String identifier = db.getDatabaseName();
		try {
			cache = org.apache.jcs.JCS.getInstance(identifier) ;
		} catch (Exception e) {
			System.out.println("ERROR:AsbruWCM.Cache.init:" + identifier + " - " + e + " - configuration error? - /WEB-INF/classes/cache.ccf");
		}
		if (DEBUG) System.out.println("HardCore.Cache.init:" + identifier + " - " + cache);
		return cache;
	}



//	static public Object get(String category, String id) {
	static public Object get(DB db, String category, String id) {
		if (! CACHE) return null;
		if (db == null) return null;
		if (DEBUG) System.out.println("HardCore.Cache.get:" + db.getDatabaseName() + " - " + category + ":" + id);

		org.apache.jcs.JCS cache = init(db);
		if (cache == null) return null;

		Object data = null;

		// get cached data

		String identifier = db.getDatabaseName();
		String group = identifier + "_" + category;
		String key = identifier + "_" + category + "_" + id;
		try {
			data = (Object)cache.getFromGroup(key, group);
		} catch (Exception e) {
			System.out.println("ERROR:AsbruWCM.Cache.get:" + identifier + " - " + category + ":" + id + " - " + e);
		}

		if (DEBUG) {
			if (data == null) {
				System.out.println("HardCore.Cache.get:miss:" + identifier + " - " + category + ":" + id);
			} else {
				System.out.println("HardCore.Cache.get:hit:" + identifier + " - " + category + ":" + id);
			}
		}
		return data;
	}



//	static public void set(String category, String id, Object data) {
	static public void set(DB db, String category, String id, Object data) {
		if (! CACHE) return;
		if (db == null) return;
		if (DEBUG) System.out.println("HardCore.Cache.set:" + db.getDatabaseName() + " - " + category + ":" + id);

		if (data == null) return;
		org.apache.jcs.JCS cache = init(db);
		if (cache == null) return;

		// do cache data

		if (! category.equals("usagelog")) {
			String identifier = db.getDatabaseName();
			String group = identifier + "_" + category;
			String key = identifier + "_" + category + "_" + id;
			try {
				cache.remove(key, group);
			} catch (Exception e) {
			}
			try {
				cache.putInGroup(key, group, data);
			} catch (Exception e) {
				System.out.println("ERROR:HardCore.Cache.set:" + identifier + " - " + category + ":" + id + " - " + e);
			}
		}
	}



//	static public void clear() {
	static public void clear(DB db) {
		if (! CACHE) return;
		if (db == null) return;
		if (DEBUG) System.out.println("HardCore.Cache.clear:" + db.getDatabaseName());

		org.apache.jcs.JCS cache = init(db);
		if (cache == null) return;

		// clear all cached data

		String identifier = db.getDatabaseName();
		try {
			cache.clear();
		} catch (Exception e) {
			System.out.println("ERROR:HardCore.Cache.clear:" + identifier + " - " + e);
		}
	}



//	static public void clear(String category) {
	static public void clear(DB db, String category) {
		if (! CACHE) return;
		if (db == null) return;
		if (DEBUG) System.out.println("HardCore.Cache.clear:" + db.getDatabaseName() + " - " + category);

		org.apache.jcs.JCS cache = init(db);
		if (cache == null) return;

		// clear category of cached data

		String identifier = db.getDatabaseName();
		String group = identifier + "_" + category;
		try {
			cache.invalidateGroup(group);
		} catch (Exception e) {
			System.out.println("ERROR:HardCore.Cache.clear:" + identifier + " - " + category + " - " + e);
		}
	}



//	static public void clear(String category, String id) {
	static public void clear(DB db, String category, String id) {
		if (! CACHE) return;
		if (db == null) return;
		if (DEBUG) System.out.println("HardCore.Cache.clear:" + db.getDatabaseName() + " - " + category + ":" + id);

		org.apache.jcs.JCS cache = init(db);
		if (cache == null) return;

		// clear cached data

		String identifier = db.getDatabaseName();
		String group = identifier + "_" + category;
		String key = identifier + "_" + category + "_" + id;
		try {
			cache.remove(key, group);
		} catch (Exception e) {
			System.out.println("ERROR:HardCore.Cache.clear:" + identifier + " - " + category + ":" + id + " - " + e);
		}
	}



//	static public void clear(String category, String id, Object data) {
	static public void clear(DB db, String category, String id, Object data) {
		if (! CACHE) return;
		if (db == null) return;
		if (DEBUG) System.out.println("HardCore.Cache.clear:" + db.getDatabaseName() + " - " + category + ":" + id);

		org.apache.jcs.JCS cache = init(db);
		if (cache == null) return;

		// clear cached data value

		String identifier = db.getDatabaseName();
		String group = identifier + "_" + category;
		String key = identifier + "_" + category + "_" + id;
		Object cached = null;
		try {
			cached = (Object)cache.getFromGroup(key, group);
		} catch (Exception e) {
		}
		if (data == cached) {
			try {
				cache.remove(key, group);
			} catch (Exception e) {
				System.out.println("ERROR:HardCore.Cache.clear:" + identifier + " - " + category + ":" + id + ":" + key + " - " + e);
			}

			if (DEBUG) System.out.println("HardCore.Cache.clear:" + identifier + " - " + category + ":" + id + ":" + key);
		}
	}



}
