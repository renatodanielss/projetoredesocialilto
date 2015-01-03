package HardCore;

import java.sql.*;
import java.text.*;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.regex.*;

public class Usertest {
	private HashMap<String,String> usertests = new HashMap<String,String>();

	private String id = "";
	private String usertest = "";
	private String description = "";
	private String variants = "";
	private String type = "";
	private String confidence = "";
	private String probability = "";
	private String visitors = "";
	private String goals = "";
	private String active = "";
	private String scheduled = "";
	private String unscheduled = "";

	private	Statement rs = null;



	public Usertest() {
		init();
	}
	public Usertest(String usertestsvalues) {
		init();
		identify(usertestsvalues);
	}
	public Usertest(Session session) {
		init();
		identify(session);
	}
	public Usertest(Session session, DB db) {
		init();
		identify(session, db);
	}



	public void init() {
		id = "";
		usertest = "";
		description = "";
		variants = "";
		type = "";
		confidence = "95";
		probability = "100";
		visitors = "";
		goals = "";
		active = "";
		scheduled = "";
		unscheduled = "";
	}



	public void read(DB db, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			String SQL = "select * from usertests where id=" + Common.integer(id);
@SuppressWarnings("unchecked")
			HashMap<String,String> row = (HashMap<String,String>)Cache.get(db, "usertests", "id:" + Common.integer(id));
			if (row == null) {
				row = db.query_record(SQL);
				if (row == null) row = new HashMap<String,String>();
				if (row != null) Cache.set(db, "usertests", "id:" + Common.integer(id), row);
			}
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void readUsertest(DB db, String value) {
		if (db == null) return;
		if ((value != null) && (! value.equals(""))) {
			String SQL = "select * from usertests where usertest=" + db.quote(Common.SQL_clean(value));
@SuppressWarnings("unchecked")
			HashMap<String,String> row = (HashMap<String,String>)Cache.get(db, "usertests", "usertest:" + Common.SQL_clean(value));
			if (row == null) {
				row = db.query_record(SQL);
				if (row == null) row = new HashMap<String,String>();
				if (row != null) Cache.set(db, "usertests", "usertest:" + Common.SQL_clean(value), row);
			}
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void getRecord(DB db, HashMap<String,String> record) {
		if (record.get("id") != null) { id = "" + record.get("id"); } else { id = ""; }
		if (record.get("usertest") != null) { usertest = "" + record.get("usertest"); } else { usertest = ""; }
		if (record.get("description") != null) { description = "" + record.get("description"); } else { description = ""; }
		if (record.get("variants") != null) { variants = "" + record.get("variants"); } else { variants = ""; }
		if (record.get("type") != null) { type = "" + record.get("type"); } else { type = ""; }
		if (record.get("confidence") != null) { confidence = "" + record.get("confidence"); } else { confidence = ""; }
		if (record.get("probability") != null) { probability = "" + record.get("probability"); } else { probability = ""; }
		if (record.get("visitors") != null) { visitors = "" + record.get("visitors"); } else { visitors = ""; }
		if (record.get("goals") != null) { goals = "" + record.get("goals"); } else { goals = ""; }
		if (record.get("active") != null) { active = "" + record.get("active"); } else { active = ""; }
		if (record.get("scheduled") != null) { scheduled = "" + record.get("scheduled"); } else { scheduled = ""; }
		if (record.get("unscheduled") != null) { unscheduled = "" + record.get("unscheduled"); } else { unscheduled = ""; }
	}



	public void getForm(Request request) {
		usertest = "" + request.getParameter("usertest");
		description = "" + request.getParameter("description");
		variants = "" + request.getParameter("variants");
		type = "" + request.getParameter("type");
		confidence = "" + request.getParameter("confidence");
		probability = "" + request.getParameter("probability");
		visitors = "" + request.getParameter("visitors");
		goals = "" + request.getParameter("goals");
		active = "" + request.getParameter("active");
		scheduled = "" + request.getParameter("scheduled");
		unscheduled = "" + request.getParameter("unscheduled");
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("usertest", "" + usertest);
		mydata.put("description", "" + description);
		mydata.put("variants", "" + variants);
		mydata.put("type", "" + type);
		mydata.put("confidence", "" + confidence);
		mydata.put("probability", "" + probability);
		mydata.put("visitors", "" + visitors);
		mydata.put("goals", "" + goals);
		mydata.put("active", "" + active);
		mydata.put("scheduled", "" + scheduled);
		mydata.put("unscheduled", "" + unscheduled);
		db.insert("usertests", mydata);
		Cache.clear(db, "usertests");
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("usertest", "" + usertest);
			mydata.put("description", "" + description);
			mydata.put("variants", "" + variants);
			mydata.put("type", "" + type);
			mydata.put("confidence", "" + confidence);
			mydata.put("probability", "" + probability);
			mydata.put("visitors", "" + visitors);
			mydata.put("goals", "" + goals);
			mydata.put("active", "" + active);
			mydata.put("scheduled", "" + scheduled);
			mydata.put("unscheduled", "" + unscheduled);
			db.update("usertests", "id", id, mydata);
			Cache.clear(db, "usertests");
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete("usertests", "id", id);
			Cache.clear(db, "usertests");
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



	public void identify(String values) {
		usertests = new HashMap<String,String>();
		String[] mynamevalues = values.split(",");
		for (int i=0; i<mynamevalues.length; i++) {
			String[] mynamevalue = mynamevalues[i].split("=");
			if (mynamevalue.length == 2) {
				String myname = mynamevalue[0];
				String myvalue = mynamevalue[1];
				if (! myname.equals("")) {
					usertests.put(myname, myvalue);
				}
			} else if (mynamevalue.length == 1) {
				String myname = mynamevalue[0];
				String myvalue = "";
				if (! myname.equals("")) {
					usertests.put(myname, myvalue);
				}
			}
		}
	}



	public void identify(Session session) {
		identify(session.get("userteststags") + "," + session.get("usertests"));
	}



	public String identify(Session session, DB db) {
		identify(session);
		if (db == null) return setUsertests(session);
		HashMap<String,String> oldusertests = new HashMap<String,String>(usertests);
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String SQL = "select * from usertests where (active=" + db.quote("1") + ") and ((" + db.is_blank("scheduled") + ") or (scheduled <= " + db.quote(now) + ")) and ((" + db.is_blank("unscheduled") + ") or (unscheduled > " + db.quote(now) + "))";
		records(db, SQL);
		while (records(db, "")) {
			if (usertests.get(getUsertest()) == null) {
				// usertest variant not already assigned
				if ((getVisitors().equals("all")) || (getVisitors().equals(session.get("visitor")))) {
					// new visitor or usertest for all visitors
					double myprobability = 100;
					if (! getProbability().equals("")) {
						myprobability = Common.number(getProbability());
					}
					double myrandom = Math.random() * 100;
					if (myrandom <= myprobability) {
						// visitor picked as test subject
						String[] myvariants = getVariantsValuesValues();
						myrandom = Math.random() * ((double)myvariants.length + 1.0);
						long myvariant = (long)Math.floor(myrandom);
						if (myvariant >= myvariants.length) {
							usertests.put(getUsertest(), "");
						} else {
							usertests.put(getUsertest(), "" + myvariants[(int)myvariant]);
						}
					}
				}
			}
		}
		String output = setUsertests(session);
		return output;
	}



	public String getUsertests() {
		String output = "";
		Iterator myusertests = Common.LinkedHashMapSortedByKey(usertests, true).keySet().iterator();
		while (myusertests.hasNext()) {
			String myusertest = "" + myusertests.next();
			if (! output.equals("")) output += ",";
			output += myusertest + "=" + usertests.get(myusertest);
		}
		return output;
	}



	public String setUsertests(Session session) {
		String output = getUsertests();
		session.set("userteststags", output);
		session.set("usertests", "" + session.get("usertests"));
		return output;
	}



	public String select_options(DB db, String selectedlist) {
		if (db == null) return "";
		StringBuffer options = new StringBuffer();
		String[] selected = selectedlist.split("\\|");
		String SQL = "select * from usertests order by usertest";
		Usertest myusertests = new Usertest();
		myusertests.records(db, SQL);
		while (myusertests.records(db, "")) {
			options.append(myusertests.select_options_variants(db, selectedlist));
		}
		return "" + options;
	}



	public String select_options_variants(DB db, String selectedlist) {
		if (db == null) return "";
		StringBuffer options = new StringBuffer();
		String[] selected = selectedlist.split("\\|");
		String[] myvariants = getVariantsValues();
		for (int i=0; i<myvariants.length; i++) {
			String name = "" + myvariants[i];
			String value = "" + myvariants[i];
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
		return "" + options;
	}



	public LinkedHashMap<String,String> content_ids(DB db) {
		if (db == null) return new LinkedHashMap<String,String>();
		String[] myvariants = getVariantsValues();

		String SQL = "select id from content where usertest in (" + Common.SQL_list_values(myvariants) + ")";
		LinkedHashMap<String,String> myids = db.query_values(SQL);
		SQL = "select id from content_public where usertest in (" + Common.SQL_list_values(myvariants) + ")";
		LinkedHashMap<String,String> myids2 = db.query_values(SQL);
		myids = Common.array_merge_values(myids, myids2);

		SQL = "select distinct version from content where id in (" + Common.SQL_list_values(myids) + ")";
		LinkedHashMap<String,String> mylanguages = db.query_values(SQL);
		SQL = "select distinct version from content_public where id in (" + Common.SQL_list_values(myids) + ")";
		LinkedHashMap<String,String> mylanguages2 = db.query_values(SQL);
		mylanguages = Common.array_merge_values(mylanguages, mylanguages2);

		SQL = "select distinct device from content where id in (" + Common.SQL_list_values(myids) + ")";
		LinkedHashMap<String,String> mydevices = db.query_values(SQL);
		SQL = "select distinct device from content_public where id in (" + Common.SQL_list_values(myids) + ")";
		LinkedHashMap<String,String> mydevices2 = db.query_values(SQL);
		mydevices = Common.array_merge_values(mydevices, mydevices2);

		SQL = "select distinct usersegment from content where id in (" + Common.SQL_list_values(myids) + ")";
		LinkedHashMap<String,String> myusersegments = db.query_values(SQL);
		SQL = "select distinct usersegment from content_public where id in (" + Common.SQL_list_values(myids) + ")";
		LinkedHashMap<String,String> myusersegments2 = db.query_values(SQL);
		myusersegments = Common.array_merge_values(myusersegments, myusersegments2);

		SQL = "select distinct version_master from content where usertest in (" + Common.SQL_list_values(myvariants) + ")";
		LinkedHashMap<String,String> mymasters = db.query_values(SQL);
		SQL = "select distinct version_master from content_public where usertest in (" + Common.SQL_list_values(myvariants) + ")";
		LinkedHashMap<String,String> mymasters2 = db.query_values(SQL);
		mymasters = Common.array_merge_values(mymasters, mymasters2);

		myids = Common.array_merge_values(myids, mymasters);

//		if (myids.size() > 0) {
//			SQL = "select distinct content_id from content_elements where element_id in (" + Common.SQL_list_values(myids) + ")";
//			LinkedHashMap<String,String> myelements = db.query_values(SQL);
//			SQL = "select distinct content_id from content_public_elements where element_id in (" + Common.SQL_list_values(myids) + ")";
//			LinkedHashMap<String,String> myelements2 = db.query_values(SQL);
//			myelements = Common.array_merge_values(myelements, myelements2);

//			SQL = "select distinct id from content where template in (" + Common.SQL_list_values(myids) + ")";
//			LinkedHashMap<String,String> mytemplates = db.query_values(SQL);
//			SQL = "select distinct id from content_public where template in (" + Common.SQL_list_values(myids) + ")";
//			LinkedHashMap<String,String> mytemplates2 = db.query_values(SQL);
//			mytemplates = Common.array_merge_values(mytemplates, mytemplates2);

//			myids = Common.array_merge_values(myids, myelements);
//			myids = Common.array_merge_values(myids, mytemplates);
//		}

		if (mymasters.size() > 0) {
			SQL = "select distinct id from content where version_master in (" + Common.SQL_list_values(mymasters) + ") and ((version in (" + Common.SQL_list_values(mylanguages) + ") and " + db.is_not_blank("version") + ") or (device in (" + Common.SQL_list_values(mydevices) + ") and " + db.is_not_blank("device") + ") or (usersegment in (" + Common.SQL_list_values(myusersegments) + ") and " + db.is_not_blank("usersegment") + "))";
			LinkedHashMap<String,String> myversions = db.query_values(SQL);
			SQL = "select distinct id from content_public where version_master in (" + Common.SQL_list_values(mymasters) + ") and ((version in (" + Common.SQL_list_values(mylanguages) + ") and " + db.is_not_blank("version") + ") or (device in (" + Common.SQL_list_values(mydevices) + ") and " + db.is_not_blank("device") + ") or (usersegment in (" + Common.SQL_list_values(myusersegments) + ") and " + db.is_not_blank("usersegment") + "))";
			LinkedHashMap<String,String> myversions2 = db.query_values(SQL);
			myversions = Common.array_merge_values(myversions, myversions2);

			myids = Common.array_merge_values(myids, myversions);

//			SQL = "select distinct id from content where version_master in (" + Common.SQL_list_values(mymasters) + ")";
//			myversions = db.query_values(SQL);
//			SQL = "select distinct id from content_public where version_master in (" + Common.SQL_list_values(mymasters) + ")";
//			myversions2 = db.query_values(SQL);
//			myversions = Common.array_merge_values(myversions, myversions2);

//			myids = Common.array_merge_values(myids, myversions);
		}

		return myids;
	}



	public boolean exists(String name) {
		if (usertests.get(name) == null) {
			return false;
		} else {
			return true;
		}
	}
	public String get(String name) {
		if (usertests.get(name) == null) {
			return "";
		} else {
			return "" + usertests.get(name);
		}
	}
	public void set(String name, String value) {
		usertests.put(name, value);
	}



	public String getId() {
		return id;
	}
	public void setId(String value) {
		id = value;
	}



	public String getUsertest() {
		return usertest;
	}
	public void setUsertest(String value) {
		usertest = value;
	}



	public String getDescription() {
		return description;
	}
	public void setDescription(String value) {
		description = value;
	}



	public String getVariants() {
		String myvariants = variants.trim();
		myvariants = myvariants.replaceAll("[\\r\\n]+", "\r\n");
		return myvariants;
	}
	public void setVariants(String value) {
		String myvariants = value.trim();
		myvariants = myvariants.replaceAll("[\\r\\n]+", "\r\n");
		variants = myvariants;
	}



	public String[] getVariantsValues() {
		String[] myvalues = getVariants().split("\r\n");
		String[] myvariants = getVariants().split("\r\n");
		for (int i=0; i<myvariants.length; i++) {
			String value = "" + usertest + "::" + myvariants[i];
			myvalues[i] = value;
		}
		return myvalues;
	}
	public String[] getVariantsValuesValues() {
		String[] myvalues = getVariants().split("\r\n");
		String[] myvariants = getVariants().split("\r\n");
		for (int i=0; i<myvariants.length; i++) {
			String value = "" + myvariants[i];
			myvalues[i] = value;
		}
		return myvalues;
	}



	public String getType() {
		return type;
	}
	public void setType(String value) {
		type = value;
	}



	public String getConfidence() {
		return confidence;
	}
	public void setConfidence(String value) {
		confidence = value;
	}



	public String getProbability() {
		return probability;
	}
	public void setProbability(String value) {
		probability = value;
	}



	public String getVisitors() {
		return visitors;
	}
	public void setVisitors(String value) {
		visitors = value;
	}



	public String getGoals() {
		return goals;
	}
	public void setGoals(String value) {
		goals = value;
	}



	public String getActive() {
		return active;
	}
	public void setActive(String value) {
		active = value;
	}



	public String getScheduled() {
		return scheduled;
	}
	public void setScheduled(String value) {
		scheduled = value;
	}



	public String getUnscheduled() {
		return unscheduled;
	}
	public void setUnscheduled(String value) {
		unscheduled = value;
	}



}
