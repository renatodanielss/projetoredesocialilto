package HardCore;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.*;

public class Device {
	private String id = "";
	private String device = "";
	private String useragent = "";

	private Text text = null;
	private	Statement rs = null;
	private boolean _DEBUG_ = false;



	public Device() {
		init();
	}
	public Device(Text mytext) {
		if (mytext != null) text = mytext;
		init();
	}
	public Device(Text mytext, String myuseragent) {
		if (mytext != null) text = mytext;
		init();
		identify(myuseragent, null, false);
	}
	public Device(String myuseragent) {
		init();
		identify(myuseragent, null, false);
	}
	public Device(String myuseragent, Session session) {
		init();
		identify(myuseragent, session, false);
	}
	public Device(String myuseragent, Session session, boolean refresh) {
		init();
		identify(myuseragent, session, refresh);
	}
	public Device(Text mytext, String myuseragent, Session session) {
		if (mytext != null) text = mytext;
		init();
		identify(myuseragent, session, false);
	}
	public Device(Text mytext, String myuseragent, Session session, boolean refresh) {
		if (mytext != null) text = mytext;
		init();
		identify(myuseragent, session, refresh);
	}



	public void init() {
		id = "";
		device = "";
		useragent = "";
	}



	public HashMap<String,String> identify(String myuseragent) {
		return identify(myuseragent, null, false, true);
	}
	public HashMap<String,String> identify(String myuseragent, Session session) {
		return identify(myuseragent, session, false, true);
	}
	public HashMap<String,String> identify(String myuseragent, Session session, boolean refresh) {
		return identify(myuseragent, session, refresh, true);
	}
	public HashMap<String,String> identify(String myuseragent, Session session, boolean refresh, boolean minorclients) {
		HashMap<String,String> mydata = new HashMap<String,String>();
		if (myuseragent == null) return mydata;
		StringBuilder mydevices = new StringBuilder();
		UsageLog usagelog = new UsageLog();
		HashMap<String,String> mydevice = usagelog.getClientDevice(myuseragent);
		HashMap<String,String> myos = usagelog.getClientOS(myuseragent);
		HashMap<String,String> mybrowser = usagelog.getClientBrowser(myuseragent, minorclients);
		if (! mydevice.get("clientdevice").equals("")) {
			mydata.put(""+mydevice.get("clientdevice"), "yes");
			if (mydevices.length()>0) mydevices.append(",");
			mydevices.append(mydevice.get("clientdevice"));
		}
		if (! mydevice.get("clientdeviceid").equals("")) {
			mydata.put(""+mydevice.get("clientdeviceid"), "yes");
			if (mydevices.length()>0) mydevices.append(",");
			mydevices.append(mydevice.get("clientdeviceid"));
		}
//		if (! mydevice.get("clientdeviceversion").equals("")) {
//			mydata.put(""+mydevice.get("clientdeviceversion"), "yes");
//			if (mydevices.length()>0) mydevices.append(",");
//			mydevices.append(mydevice.get("clientdeviceversion"));
//		}
		if (! myos.get("clientos").equals("")) {
			mydata.put(""+myos.get("clientos"), "yes");
			if (mydevices.length()>0) mydevices.append(",");
			mydevices.append(myos.get("clientos"));
		}
		if (! myos.get("clientosversion").equals("")) {
			mydata.put(""+myos.get("clientosversion"), "yes");
			if (mydevices.length()>0) mydevices.append(",");
			mydevices.append(myos.get("clientosversion"));
		}
		if (! mybrowser.get("clientbrowser").equals("")) {
			mydata.put(""+mybrowser.get("clientbrowser"), "yes");
			if (mydevices.length()>0) mydevices.append(",");
			mydevices.append(mybrowser.get("clientbrowser"));
			if (! mybrowser.get("clientversion").equals("")) {
				mydata.put(""+mybrowser.get("clientbrowser")+mybrowser.get("clientversion").replaceAll("\\.[0-9]+", ""), "yes");
				if (mydevices.length()>0) mydevices.append(",");
				mydevices.append(mybrowser.get("clientbrowser")+mybrowser.get("clientversion").replaceAll("\\.[0-9]+", ""));
			}
		}
		if ((session != null) && (refresh || (! session.exists("device")))) {
			session.set("device", ""+mydevices);
		}
		return mydata;
	}



	public String select_options(DB db, String selected) {
		String myoptions = "";
		if (text == null) text = new Text();
		UsageLog usagelog = new UsageLog();
//QQQ TODO	myoptions += Common.select_options(db, "devices", "device", selected);
		myoptions += "<option disabled>" + text.display("devices.devices") + "</option>" + "\r\n";
		myoptions += usagelog.getClientDeviceOptions(selected);
		myoptions += "<option disabled>" + text.display("devices.webbrowsers") + "</option>" + "\r\n";
		myoptions += usagelog.getClientBrowserOptions(selected);
		myoptions += "<option disabled>" + text.display("devices.operatingsystems") + "</option>" + "\r\n";
		myoptions += usagelog.getClientOSOptions(selected);
		return myoptions;
	}



	public void read(DB db, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			String SQL = "";
			SQL = "select * from devices where id=" + Common.integer(id);
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
		device = "" + record.get("device");
		useragent = "" + record.get("useragent");
	}



	public void getForm(Request request) {
		device = request.getParameter("device");
		useragent = request.getParameter("useragent");
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("device", "" + device);
		mydata.put("useragent", "" + useragent);
		db.insert("devices", mydata);
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("device", "" + device);
			mydata.put("useragent", "" + useragent);
			db.update("devices", "id", id, mydata);
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete("devices", "id", id);
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



	public String getDevice() {
		return device;
	}
	public void setDevice(String value) {
		device = value;
	}



	public String getUseragent() {
		return useragent;
	}
	public void setUseragent(String value) {
		useragent = value;
	}



}
