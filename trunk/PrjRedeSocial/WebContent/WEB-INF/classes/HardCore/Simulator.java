package HardCore;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.*;
import javax.servlet.ServletContext;

public class Simulator {
	private String id = "";
	private String simulator = "";
	private String useragent = "";

	private Text text = null;
	private	Statement rs = null;
	private boolean _DEBUG_ = false;



	public Simulator() {
		init();
	}
	public Simulator(Text mytext) {
		if (mytext != null) text = mytext;
		init();
	}



	public void init() {
		id = "";
		simulator = "";
		useragent = "";
	}



	public String select_options(ServletContext servletcontext, Request request, DB db, String selected) {
		String myoptions = "";
		if (text == null) text = new Text();
		String mybasefolder = Common.getRealPath(servletcontext, "/" + text.display("adminpath") + "/simulators/");
		String[] myfolders = Common.listFolders(mybasefolder).split("\r\n");
		for (int i=0; i<myfolders.length; i++) {
			String mysimulator = myfolders[i].replaceAll("/$", "");
			if (Common.fileExists(mybasefolder + myfolders[i] + "index.jsp")) {
				myoptions += "<option value=\"" + mysimulator + "\">" + mysimulator + "</option>" + "\r\n";
			}
		}
//QQQ TODO	myoptions += Common.select_options(db, "simulators", "simulator", selected);
		return myoptions;
	}



	public void read(DB db, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			String SQL = "";
			SQL = "select * from simulators where id=" + Common.integer(id);
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
		simulator = "" + record.get("simulator");
		useragent = "" + record.get("useragent");
	}



	public void getForm(Request request) {
		simulator = request.getParameter("simulator");
		useragent = request.getParameter("useragent");
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("simulator", "" + simulator);
		mydata.put("useragent", "" + useragent);
		db.insert("simulators", mydata);
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("simulator", "" + simulator);
			mydata.put("useragent", "" + useragent);
			db.update("simulators", "id", id, mydata);
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete("simulators", "id", id);
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



	public String getSimulator() {
		return simulator;
	}
	public void setSimulator(String value) {
		simulator = value;
	}



	public String getUseragent() {
		return useragent;
	}
	public void setUseragent(String value) {
		useragent = value;
	}



}
