package HardCore;

import java.sql.*;
import java.text.*;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.regex.*;

public class Segment {
	private HashMap<String,String> segments = new HashMap<String,String>();

	private String id = "";
	private String segmentid = "";
	private String segment = "";
	private String weight = "";

	private String active = "";
	private String scheduled = "";
	private String unscheduled = "";

	private String datetimefull = "";
	private String datetimeyear = "";
	private String datetimemonth = "";
	private String datetimeday = "";
	private String datetimeweek = "";
	private String datetimeweekday = "";
	private String datetimehour = "";
	private String datetimemin = "";
	private String datetimesec = "";

	private String clientaddr = "";
	private String clienthost = "";
	private String clientuser = "";
	private String clientuseragent = "";
	private String clientos = "";
	private String clientosversion = "";
	private String clientbrowser = "";
	private String clientversion = "";

	private String clientdevice = "";
	private String clientdeviceid = "";
	private String clientdeviceversion = "";

	private String preferredlanguage = "";
	private String acceptedlanguages = "";

	private String refererid = "";
	private String refererclass = "";
	private String refererdatabase = "";
	private String refererhost = "";
	private String refererpath = "";
	private String refererquery = "";
	private String referersearchengine = "";
	private String referersearchquery = "";

	private String requesthost = "";
	private String requestport = "";
	private String requestmethod = "";
	private String requestpath = "";
	private String requestquery = "";
	private String requestprotocol = "";
	private String requestid = "";
	private String requestclass = "";
	private String requestdatabase = "";

	private String geoipcountry = "";
	private String geoipregion = "";
	private String geoipcity = "";
	private String geoippostalcode = "";
	private String geoiplatitude = "";
	private String geoiplongitude = "";
	private String geoiptimezone = "";

	private String created = "";
	private String updated = "";
	private String created_by = "";
	private String updated_by = "";
	private String keywords = "";
	private String description = "";
	private String birthdate = "";
	private String birthyear = "";
	private String birthmonth = "";
	private String birthday = "";
	private String age = "";
	private String gender = "";
	private String title = "";
	private String name = "";
	private String organisation = "";
	private String username = "";
	private String password = "";
	private String email = "";
	private String usertype = "";
	private String usergroup = "";
	private String userclass = "";

	private String scheduled_last = "";
	private String scheduled_publish = "";
	private String scheduled_notify = "";
	private String scheduled_unpublish = "";
	private String scheduled_publish_email = "";
	private String scheduled_notify_email = "";
	private String scheduled_unpublish_email = "";

	private String card_type = "";
	private String card_number = "";
	private String card_issuedmonth = "";
	private String card_issuedyear = "";
	private String card_expirymonth = "";
	private String card_expiryyear = "";
	private String card_name = "";
	private String card_postalcode = "";

	private String delivery_name = "";
	private String delivery_organisation = "";
	private String delivery_address = "";
	private String delivery_postalcode = "";
	private String delivery_city = "";
	private String delivery_state = "";
	private String delivery_country = "";
	private String delivery_phone = "";
	private String delivery_fax = "";
	private String delivery_email = "";
	private String delivery_website = "";

	private String invoice_name = "";
	private String invoice_organisation = "";
	private String invoice_address = "";
	private String invoice_postalcode = "";
	private String invoice_city = "";
	private String invoice_state = "";
	private String invoice_country = "";
	private String invoice_phone = "";
	private String invoice_fax = "";
	private String invoice_email = "";
	private String invoice_website = "";

	private String notes = "";
	private String userinfo = "";
	private String shopcart = "";
	private String wishlist = "";

	private Text text = null;
	private	Statement rs = null;
	private boolean _DEBUG_ = false;



	public Segment() {
		init();
	}
	public Segment(String usersegmentsvalues) {
		init();
		identify(usersegmentsvalues);
	}
	public Segment(Session session) {
		init();
		identify(session);
	}
	public Segment(Session session, DB db, Request request, String requestid, String requestclass, String requestdatabase) {
		init();
		identify(session, db, request, requestid, requestclass, requestdatabase);
	}
	public Segment(Session session, DB db, Request request, String requestid, String requestclass, String requestdatabase, User user) {
		init();
		identify(session, db, request, requestid, requestclass, requestdatabase, user);
	}



	public void init() {
		id = "";
		segmentid = "";
		segment = "";
		weight = "";

		active = "";
		scheduled = "";
		unscheduled = "";

		datetimefull = "";
		datetimeyear = "";
		datetimemonth = "";
		datetimeday = "";
		datetimeweek = "";
		datetimeweekday = "";
		datetimehour = "";
		datetimemin = "";
		datetimesec = "";

		clientaddr = "";
		clienthost = "";
		clientuser = "";
		clientuseragent = "";
		clientos = "";
		clientosversion = "";
		clientbrowser = "";
		clientversion = "";

		clientdevice = "";
		clientdeviceid = "";
		clientdeviceversion = "";

		preferredlanguage = "";
		acceptedlanguages = "";

		refererid = "";
		refererclass = "";
		refererdatabase = "";
		refererhost = "";
		refererpath = "";
		refererquery = "";
		referersearchengine = "";
		referersearchquery = "";

		requesthost = "";
		requestport = "";
		requestmethod = "";
		requestpath = "";
		requestquery = "";
		requestprotocol = "";
		requestid = "";
		requestclass = "";
		requestdatabase = "";

		geoipcountry = "";
		geoipregion = "";
		geoipcity = "";
		geoippostalcode = "";
		geoiplatitude = "";
		geoiplongitude = "";
		geoiptimezone = "";

		created = "";
		updated = "";
		created_by = "";
		updated_by = "";
		keywords = "";
		description = "";
		birthdate = "";
		birthyear = "";
		birthmonth = "";
		birthday = "";
		age = "";
		gender = "";
		title = "";
		name = "";
		organisation = "";
		username = "";
		password = "";
		email = "";
		usertype = "";
		usergroup = "";
		userclass = "";

		scheduled_last = "";
		scheduled_publish = "";
		scheduled_notify = "";
		scheduled_unpublish = "";
		scheduled_publish_email = "";
		scheduled_notify_email = "";
		scheduled_unpublish_email = "";

		card_type = "";
		card_number = "";
		card_issuedmonth = "";
		card_issuedyear = "";
		card_expirymonth = "";
		card_expiryyear = "";
		card_name = "";
		card_postalcode = "";

		delivery_name = "";
		delivery_organisation = "";
		delivery_address = "";
		delivery_postalcode = "";
		delivery_city = "";
		delivery_state = "";
		delivery_country = "";
		delivery_phone = "";
		delivery_fax = "";
		delivery_email = "";
		delivery_website = "";

		invoice_name = "";
		invoice_organisation = "";
		invoice_address = "";
		invoice_postalcode = "";
		invoice_city = "";
		invoice_state = "";
		invoice_country = "";
		invoice_phone = "";
		invoice_fax = "";
		invoice_email = "";
		invoice_website = "";

		notes = "";
		userinfo = "";
		shopcart = "";
		wishlist = "";
	}



	public void read(DB db, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			String SQL = "select * from segments where id=" + Common.integer(id);
@SuppressWarnings("unchecked")
			HashMap<String,String> row = (HashMap<String,String>)Cache.get(db, "segments", "id:" + Common.integer(id));
			if (row == null) {
				row = db.query_record(SQL);
				if (row == null) row = new HashMap<String,String>();
				if (row != null) Cache.set(db, "segments", "id:" + Common.integer(id), row);
			}
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void readSegmentId(DB db, String value) {
		if (db == null) return;
		if ((value != null) && (! value.equals(""))) {
			String SQL = "select * from segments where segmentid=" + db.quote(Common.SQL_clean(value));
@SuppressWarnings("unchecked")
			HashMap<String,String> row = (HashMap<String,String>)Cache.get(db, "segments", "segmentid:" + Common.SQL_clean(value));
			if (row == null) {
				row = db.query_record(SQL);
				if (row == null) row = new HashMap<String,String>();
				if (row != null) Cache.set(db, "segments", "segmentid:" + Common.SQL_clean(value), row);
			}
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void getRecord(DB db, HashMap<String,String> record) {
		id = "" + record.get("id");
		segmentid = "" + record.get("segmentid");
		segment = "" + record.get("segment");
		weight = "" + record.get("weight");

		active = "" + record.get("active");
		scheduled = "" + record.get("scheduled");
		unscheduled = "" + record.get("unscheduled");

		datetimefull = "" + record.get("datetimefull");
		datetimeyear = "" + record.get("datetimeyear");
		datetimemonth = "" + record.get("datetimemonth");
		datetimeday = "" + record.get("datetimeday");
		datetimeweek = "" + record.get("datetimeweek");
		datetimeweekday = "" + record.get("datetimeweekday");
		datetimehour = "" + record.get("datetimehour");
		datetimemin = "" + record.get("datetimemin");
		datetimesec = "" + record.get("datetimesec");

		clientaddr = "" + record.get("clientaddr");
		clienthost = "" + record.get("clienthost");
		clientuser = "" + record.get("clientuser");
		clientuseragent = "" + record.get("clientuseragent");
		clientos = "" + record.get("clientos");
		clientosversion = "" + record.get("clientosversion");
		clientbrowser = "" + record.get("clientbrowser");
		clientversion = "" + record.get("clientversion");

		clientdevice = "" + record.get("clientdevice");
		clientdeviceid = "" + record.get("clientdeviceid");
		clientdeviceversion = "" + record.get("clientdeviceversion");

		preferredlanguage = "" + record.get("preferredlanguage");
		acceptedlanguages = "" + record.get("acceptedlanguages");

		refererid = "" + record.get("refererid");
		refererclass = "" + record.get("refererclass");
		refererdatabase = "" + record.get("refererdatabase");
		refererhost = "" + record.get("refererhost");
		refererpath = "" + record.get("refererpath");
		refererquery = "" + record.get("refererquery");
		referersearchengine = "" + record.get("referersearchengine");
		referersearchquery = "" + record.get("referersearchquery");

		requesthost = "" + record.get("requesthost");
		requestport = "" + record.get("requestport");
		requestmethod = "" + record.get("requestmethod");
		requestpath = "" + record.get("requestpath");
		requestquery = "" + record.get("requestquery");
		requestprotocol = "" + record.get("requestprotocol");
		requestid = "" + record.get("requestid");
		requestclass = "" + record.get("requestclass");
		requestdatabase = "" + record.get("requestdatabase");

		geoipcountry = "" + record.get("geoipcountry");
		geoipregion = "" + record.get("geoipregion");
		geoipcity = "" + record.get("geoipcity");
		geoippostalcode = "" + record.get("geoippostalcode");
		geoiplatitude = "" + record.get("geoiplatitude");
		geoiplongitude = "" + record.get("geoiplongitude");
		geoiptimezone = "" + record.get("geoiptimezone");

		created = "" + record.get("created");
		updated = "" + record.get("updated");
		created_by = "" + record.get("created_by");
		updated_by = "" + record.get("updated_by");
		keywords = "" + record.get("keywords");
		description = "" + record.get("description");
		birthdate = "" + record.get("birthdate");
		birthyear = "" + record.get("birthyear");
		birthmonth = "" + record.get("birthmonth");
		birthday = "" + record.get("birthday");
		age = "" + record.get("age");
		gender = "" + record.get("gender");
		title = "" + record.get("title");
		name = "" + record.get("name");
		organisation = "" + record.get("organisation");
		username = "" + record.get("username");
		password = "" + record.get("password");
		email = "" + record.get("email");
		usertype = "" + record.get("usertype");
		usergroup = "" + record.get("usergroup");
		userclass = "" + record.get("userclass");

		scheduled_last = "" + record.get("scheduled_last");
		scheduled_publish = "" + record.get("scheduled_publish");
		scheduled_notify = "" + record.get("scheduled_notify");
		scheduled_unpublish = "" + record.get("scheduled_unpublish");
		scheduled_publish_email = "" + record.get("scheduled_publish_email");
		scheduled_notify_email = "" + record.get("scheduled_notify_email");
		scheduled_unpublish_email = "" + record.get("scheduled_unpublish_email");

		card_type = "" + record.get("card_type");
		card_number = "" + record.get("card_number");
		card_issuedmonth = "" + record.get("card_issuedmonth");
		card_issuedyear = "" + record.get("card_issuedyear");
		card_expirymonth = "" + record.get("card_expirymonth");
		card_expiryyear = "" + record.get("card_expiryyear");
		card_name = "" + record.get("card_name");
		card_postalcode = "" + record.get("card_postalcode");

		delivery_name = "" + record.get("delivery_name");
		delivery_organisation = "" + record.get("delivery_organisation");
		delivery_address = "" + record.get("delivery_address");
		delivery_postalcode = "" + record.get("delivery_postalcode");
		delivery_city = "" + record.get("delivery_city");
		delivery_state = "" + record.get("delivery_state");
		delivery_country = "" + record.get("delivery_country");
		delivery_phone = "" + record.get("delivery_phone");
		delivery_fax = "" + record.get("delivery_fax");
		delivery_email = "" + record.get("delivery_email");
		delivery_website = "" + record.get("delivery_website");

		invoice_name = "" + record.get("invoice_name");
		invoice_organisation = "" + record.get("invoice_organisation");
		invoice_address = "" + record.get("invoice_address");
		invoice_postalcode = "" + record.get("invoice_postalcode");
		invoice_city = "" + record.get("invoice_city");
		invoice_state = "" + record.get("invoice_state");
		invoice_country = "" + record.get("invoice_country");
		invoice_phone = "" + record.get("invoice_phone");
		invoice_fax = "" + record.get("invoice_fax");
		invoice_email = "" + record.get("invoice_email");
		invoice_website = "" + record.get("invoice_website");

		notes = "" + record.get("notes");
		userinfo = "" + record.get("userinfo");
		shopcart = "" + record.get("shopcart");
		wishlist = "" + record.get("wishlist");
	}



	public void getForm(Request request) {
		segmentid = request.getParameter("segmentid");
		segment = request.getParameter("segment");
		weight = request.getParameter("weight");

		active = request.getParameter("active");
		scheduled = request.getParameter("scheduled");
		unscheduled = request.getParameter("unscheduled");

		datetimefull = request.getParameter("datetimefull");
		datetimeyear = request.getParameter("datetimeyear");
		datetimemonth = request.getParameter("datetimemonth");
		datetimeday = request.getParameter("datetimeday");
		datetimeweek = request.getParameter("datetimeweek");
		datetimeweekday = request.getParameter("datetimeweekday");
		datetimehour = request.getParameter("datetimehour");
		datetimemin = request.getParameter("datetimemin");
		datetimesec = request.getParameter("datetimesec");

		clientaddr = request.getParameter("clientaddr");
		clienthost = request.getParameter("clienthost");
		clientuser = request.getParameter("clientuser");
		clientuseragent = request.getParameter("clientuseragent");
		clientos = request.getParameter("clientos");
		clientosversion = request.getParameter("clientosversion");
		clientbrowser = request.getParameter("clientbrowser");
		clientversion = request.getParameter("clientversion");

		clientdevice = request.getParameter("clientdevice");
		clientdeviceid = request.getParameter("clientdeviceid");
		clientdeviceversion = request.getParameter("clientdeviceversion");

		preferredlanguage = request.getParameter("preferredlanguage");
		acceptedlanguages = request.getParameter("acceptedlanguages");

		refererid = request.getParameter("refererid");
		refererclass = request.getParameter("refererclass");
		refererdatabase = request.getParameter("refererdatabase");
		refererhost = request.getParameter("refererhost");
		refererpath = request.getParameter("refererpath");
		refererquery = request.getParameter("refererquery");
		referersearchengine = request.getParameter("referersearchengine");
		referersearchquery = request.getParameter("referersearchquery");

		requesthost = request.getParameter("requesthost");
		requestport = request.getParameter("requestport");
		requestmethod = request.getParameter("requestmethod");
		requestpath = request.getParameter("requestpath");
		requestquery = request.getParameter("requestquery");
		requestprotocol = request.getParameter("requestprotocol");
		requestid = request.getParameter("requestid");
		requestclass = request.getParameter("requestclass");
		requestdatabase = request.getParameter("requestdatabase");

		geoipcountry = request.getParameter("geoipcountry");
		geoipregion = request.getParameter("geoipregion");
		geoipcity = request.getParameter("geoipcity");
		geoippostalcode = request.getParameter("geoippostalcode");
		geoiplatitude = request.getParameter("geoiplatitude");
		geoiplongitude = request.getParameter("geoiplongitude");
		geoiptimezone = request.getParameter("geoiptimezone");

		created = request.getParameter("created");
		updated = request.getParameter("updated");
		created_by = request.getParameter("created_by");
		updated_by = request.getParameter("updated_by");
		keywords = request.getParameter("keywords");
		description = request.getParameter("description");
		birthdate = request.getParameter("birthdate");
		birthyear = request.getParameter("birthyear");
		birthmonth = request.getParameter("birthmonth");
		birthday = request.getParameter("birthday");
		age = request.getParameter("age");
		gender = request.getParameter("gender");
		title = request.getParameter("title");
		name = request.getParameter("name");
		organisation = request.getParameter("organisation");
		username = request.getParameter("username");
		password = request.getParameter("password");
		email = request.getParameter("email");
		usertype = request.getParameter("usertype");
		usergroup = request.getParameter("usergroup");
		userclass = request.getParameter("userclass");

		scheduled_last = request.getParameter("scheduled_last");
		scheduled_publish = request.getParameter("scheduled_publish");
		scheduled_notify = request.getParameter("scheduled_notify");
		scheduled_unpublish = request.getParameter("scheduled_unpublish");
		scheduled_publish_email = request.getParameter("scheduled_publish_email");
		scheduled_notify_email = request.getParameter("scheduled_notify_email");
		scheduled_unpublish_email = request.getParameter("scheduled_unpublish_email");

		card_type = request.getParameter("card_type");
		card_number = request.getParameter("card_number");
		card_issuedmonth = request.getParameter("card_issuedmonth");
		card_issuedyear = request.getParameter("card_issuedyear");
		card_expirymonth = request.getParameter("card_expirymonth");
		card_expiryyear = request.getParameter("card_expiryyear");
		card_name = request.getParameter("card_name");
		card_postalcode = request.getParameter("card_postalcode");

		delivery_name = request.getParameter("delivery_name");
		delivery_organisation = request.getParameter("delivery_organisation");
		delivery_address = request.getParameter("delivery_address");
		delivery_postalcode = request.getParameter("delivery_postalcode");
		delivery_city = request.getParameter("delivery_city");
		delivery_state = request.getParameter("delivery_state");
		delivery_country = request.getParameter("delivery_country");
		delivery_phone = request.getParameter("delivery_phone");
		delivery_fax = request.getParameter("delivery_fax");
		delivery_email = request.getParameter("delivery_email");
		delivery_website = request.getParameter("delivery_website");

		invoice_name = request.getParameter("invoice_name");
		invoice_organisation = request.getParameter("invoice_organisation");
		invoice_address = request.getParameter("invoice_address");
		invoice_postalcode = request.getParameter("invoice_postalcode");
		invoice_city = request.getParameter("invoice_city");
		invoice_state = request.getParameter("invoice_state");
		invoice_country = request.getParameter("invoice_country");
		invoice_phone = request.getParameter("invoice_phone");
		invoice_fax = request.getParameter("invoice_fax");
		invoice_email = request.getParameter("invoice_email");
		invoice_website = request.getParameter("invoice_website");

		notes = request.getParameter("notes");
		userinfo = request.getParameter("userinfo");
		shopcart = request.getParameter("shopcart");
		wishlist = request.getParameter("wishlist");
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();

		mydata.put("segmentid", "" + segmentid);
		mydata.put("segment", "" + segment);
		mydata.put("weight", "" + weight);

		mydata.put("active", "" + active);
		mydata.put("scheduled", "" + scheduled);
		mydata.put("unscheduled", "" + unscheduled);

		mydata.put("datetimefull", "" + datetimefull);
		mydata.put("datetimeyear", "" + datetimeyear);
		mydata.put("datetimemonth", "" + datetimemonth);
		mydata.put("datetimeday", "" + datetimeday);
		mydata.put("datetimeweek", "" + datetimeweek);
		mydata.put("datetimeweekday", "" + datetimeweekday);
		mydata.put("datetimehour", "" + datetimehour);
		mydata.put("datetimemin", "" + datetimemin);
		mydata.put("datetimesec", "" + datetimesec);

		mydata.put("clientaddr", "" + clientaddr);
		mydata.put("clienthost", "" + clienthost);
		mydata.put("clientuser", "" + clientuser);
		mydata.put("clientuseragent", "" + clientuseragent);
		mydata.put("clientos", "" + clientos);
		mydata.put("clientosversion", "" + clientosversion);
		mydata.put("clientbrowser", "" + clientbrowser);
		mydata.put("clientversion", "" + clientversion);

		mydata.put("clientdevice", "" + clientdevice);
		mydata.put("clientdeviceid", "" + clientdeviceid);
		mydata.put("clientdeviceversion", "" + clientdeviceversion);

		mydata.put("preferredlanguage", "" + preferredlanguage);
		mydata.put("acceptedlanguages", "" + acceptedlanguages);

		mydata.put("refererid", "" + refererid);
		mydata.put("refererclass", "" + refererclass);
		mydata.put("refererdatabase", "" + refererdatabase);
		mydata.put("refererhost", "" + refererhost);
		mydata.put("refererpath", "" + refererpath);
		mydata.put("refererquery", "" + refererquery);
		mydata.put("referersearchengine", "" + referersearchengine);
		mydata.put("referersearchquery", "" + referersearchquery);

		mydata.put("requesthost", "" + requesthost);
		mydata.put("requestport", "" + requestport);
		mydata.put("requestmethod", "" + requestmethod);
		mydata.put("requestpath", "" + requestpath);
		mydata.put("requestquery", "" + requestquery);
		mydata.put("requestprotocol", "" + requestprotocol);
		mydata.put("requestid", "" + requestid);
		mydata.put("requestclass", "" + requestclass);
		mydata.put("requestdatabase", "" + requestdatabase);

		mydata.put("geoipcountry", "" + geoipcountry);
		mydata.put("geoipregion", "" + geoipregion);
		mydata.put("geoipcity", "" + geoipcity);
		mydata.put("geoippostalcode", "" + geoippostalcode);
		mydata.put("geoiplatitude", "" + geoiplatitude);
		mydata.put("geoiplongitude", "" + geoiplongitude);
		mydata.put("geoiptimezone", "" + geoiptimezone);

		mydata.put("created", "" + created);
		mydata.put("updated", "" + updated);
		mydata.put("created_by", "" + created_by);
		mydata.put("updated_by", "" + updated_by);
		mydata.put("keywords", "" + keywords);
		mydata.put("description", "" + description);
		mydata.put("birthdate", "" + birthdate);
		mydata.put("birthyear", "" + birthyear);
		mydata.put("birthmonth", "" + birthmonth);
		mydata.put("birthday", "" + birthday);
		mydata.put("age", "" + age);
		mydata.put("gender", "" + gender);
		mydata.put("title", "" + title);
		mydata.put("name", "" + name);
		mydata.put("organisation", "" + organisation);
		mydata.put("username", "" + username);
		mydata.put("password", "" + password);
		mydata.put("email", "" + email);
		mydata.put("usertype", "" + usertype);
		mydata.put("usergroup", "" + usergroup);
		mydata.put("userclass", "" + userclass);

		mydata.put("scheduled_last", "" + scheduled_last);
		mydata.put("scheduled_publish", "" + scheduled_publish);
		mydata.put("scheduled_notify", "" + scheduled_notify);
		mydata.put("scheduled_unpublish", "" + scheduled_unpublish);
		mydata.put("scheduled_publish_email", "" + scheduled_publish_email);
		mydata.put("scheduled_notify_email", "" + scheduled_notify_email);
		mydata.put("scheduled_unpublish_email", "" + scheduled_unpublish_email);

		mydata.put("card_type", "" + card_type);
		mydata.put("card_number", "" + card_number);
		mydata.put("card_issuedmonth", "" + card_issuedmonth);
		mydata.put("card_issuedyear", "" + card_issuedyear);
		mydata.put("card_expirymonth", "" + card_expirymonth);
		mydata.put("card_expiryyear", "" + card_expiryyear);
		mydata.put("card_name", "" + card_name);
		mydata.put("card_postalcode", "" + card_postalcode);

		mydata.put("delivery_name", "" + delivery_name);
		mydata.put("delivery_organisation", "" + delivery_organisation);
		mydata.put("delivery_address", "" + delivery_address);
		mydata.put("delivery_postalcode", "" + delivery_postalcode);
		mydata.put("delivery_city", "" + delivery_city);
		mydata.put("delivery_state", "" + delivery_state);
		mydata.put("delivery_country", "" + delivery_country);
		mydata.put("delivery_phone", "" + delivery_phone);
		mydata.put("delivery_fax", "" + delivery_fax);
		mydata.put("delivery_email", "" + delivery_email);
		mydata.put("delivery_website", "" + delivery_website);

		mydata.put("invoice_name", "" + invoice_name);
		mydata.put("invoice_organisation", "" + invoice_organisation);
		mydata.put("invoice_address", "" + invoice_address);
		mydata.put("invoice_postalcode", "" + invoice_postalcode);
		mydata.put("invoice_city", "" + invoice_city);
		mydata.put("invoice_state", "" + invoice_state);
		mydata.put("invoice_country", "" + invoice_country);
		mydata.put("invoice_phone", "" + invoice_phone);
		mydata.put("invoice_fax", "" + invoice_fax);
		mydata.put("invoice_email", "" + invoice_email);
		mydata.put("invoice_website", "" + invoice_website);

		mydata.put("notes", "" + notes);
		mydata.put("userinfo", "" + userinfo);
		mydata.put("shopcart", "" + shopcart);
		mydata.put("wishlist", "" + wishlist);

		db.insert("segments", mydata);
		Cache.clear(db, "segments");
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();

			mydata.put("segmentid", "" + segmentid);
			mydata.put("segment", "" + segment);
			mydata.put("weight", "" + weight);

			mydata.put("active", "" + active);
			mydata.put("scheduled", "" + scheduled);
			mydata.put("unscheduled", "" + unscheduled);

			mydata.put("datetimefull", "" + datetimefull);
			mydata.put("datetimeyear", "" + datetimeyear);
			mydata.put("datetimemonth", "" + datetimemonth);
			mydata.put("datetimeday", "" + datetimeday);
			mydata.put("datetimeweek", "" + datetimeweek);
			mydata.put("datetimeweekday", "" + datetimeweekday);
			mydata.put("datetimehour", "" + datetimehour);
			mydata.put("datetimemin", "" + datetimemin);
			mydata.put("datetimesec", "" + datetimesec);

			mydata.put("clientaddr", "" + clientaddr);
			mydata.put("clienthost", "" + clienthost);
			mydata.put("clientuser", "" + clientuser);
			mydata.put("clientuseragent", "" + clientuseragent);
			mydata.put("clientos", "" + clientos);
			mydata.put("clientosversion", "" + clientosversion);
			mydata.put("clientbrowser", "" + clientbrowser);
			mydata.put("clientversion", "" + clientversion);

			mydata.put("clientdevice", "" + clientdevice);
			mydata.put("clientdeviceid", "" + clientdeviceid);
			mydata.put("clientdeviceversion", "" + clientdeviceversion);

			mydata.put("preferredlanguage", "" + preferredlanguage);
			mydata.put("acceptedlanguages", "" + acceptedlanguages);

			mydata.put("refererid", "" + refererid);
			mydata.put("refererclass", "" + refererclass);
			mydata.put("refererdatabase", "" + refererdatabase);
			mydata.put("refererhost", "" + refererhost);
			mydata.put("refererpath", "" + refererpath);
			mydata.put("refererquery", "" + refererquery);
			mydata.put("referersearchengine", "" + referersearchengine);
			mydata.put("referersearchquery", "" + referersearchquery);

			mydata.put("requesthost", "" + requesthost);
			mydata.put("requestport", "" + requestport);
			mydata.put("requestmethod", "" + requestmethod);
			mydata.put("requestpath", "" + requestpath);
			mydata.put("requestquery", "" + requestquery);
			mydata.put("requestprotocol", "" + requestprotocol);
			mydata.put("requestid", "" + requestid);
			mydata.put("requestclass", "" + requestclass);
			mydata.put("requestdatabase", "" + requestdatabase);

			mydata.put("geoipcountry", "" + geoipcountry);
			mydata.put("geoipregion", "" + geoipregion);
			mydata.put("geoipcity", "" + geoipcity);
			mydata.put("geoippostalcode", "" + geoippostalcode);
			mydata.put("geoiplatitude", "" + geoiplatitude);
			mydata.put("geoiplongitude", "" + geoiplongitude);
			mydata.put("geoiptimezone", "" + geoiptimezone);

			mydata.put("created", "" + created);
			mydata.put("updated", "" + updated);
			mydata.put("created_by", "" + created_by);
			mydata.put("updated_by", "" + updated_by);
			mydata.put("keywords", "" + keywords);
			mydata.put("description", "" + description);
			mydata.put("birthdate", "" + birthdate);
			mydata.put("birthyear", "" + birthyear);
			mydata.put("birthmonth", "" + birthmonth);
			mydata.put("birthday", "" + birthday);
			mydata.put("age", "" + age);
			mydata.put("gender", "" + gender);
			mydata.put("title", "" + title);
			mydata.put("name", "" + name);
			mydata.put("organisation", "" + organisation);
			mydata.put("username", "" + username);
			mydata.put("password", "" + password);
			mydata.put("email", "" + email);
			mydata.put("usertype", "" + usertype);
			mydata.put("usergroup", "" + usergroup);
			mydata.put("userclass", "" + userclass);

			mydata.put("scheduled_last", "" + scheduled_last);
			mydata.put("scheduled_publish", "" + scheduled_publish);
			mydata.put("scheduled_notify", "" + scheduled_notify);
			mydata.put("scheduled_unpublish", "" + scheduled_unpublish);
			mydata.put("scheduled_publish_email", "" + scheduled_publish_email);
			mydata.put("scheduled_notify_email", "" + scheduled_notify_email);
			mydata.put("scheduled_unpublish_email", "" + scheduled_unpublish_email);

			mydata.put("card_type", "" + card_type);
			mydata.put("card_number", "" + card_number);
			mydata.put("card_issuedmonth", "" + card_issuedmonth);
			mydata.put("card_issuedyear", "" + card_issuedyear);
			mydata.put("card_expirymonth", "" + card_expirymonth);
			mydata.put("card_expiryyear", "" + card_expiryyear);
			mydata.put("card_name", "" + card_name);
			mydata.put("card_postalcode", "" + card_postalcode);

			mydata.put("delivery_name", "" + delivery_name);
			mydata.put("delivery_organisation", "" + delivery_organisation);
			mydata.put("delivery_address", "" + delivery_address);
			mydata.put("delivery_postalcode", "" + delivery_postalcode);
			mydata.put("delivery_city", "" + delivery_city);
			mydata.put("delivery_state", "" + delivery_state);
			mydata.put("delivery_country", "" + delivery_country);
			mydata.put("delivery_phone", "" + delivery_phone);
			mydata.put("delivery_fax", "" + delivery_fax);
			mydata.put("delivery_email", "" + delivery_email);
			mydata.put("delivery_website", "" + delivery_website);

			mydata.put("invoice_name", "" + invoice_name);
			mydata.put("invoice_organisation", "" + invoice_organisation);
			mydata.put("invoice_address", "" + invoice_address);
			mydata.put("invoice_postalcode", "" + invoice_postalcode);
			mydata.put("invoice_city", "" + invoice_city);
			mydata.put("invoice_state", "" + invoice_state);
			mydata.put("invoice_country", "" + invoice_country);
			mydata.put("invoice_phone", "" + invoice_phone);
			mydata.put("invoice_fax", "" + invoice_fax);
			mydata.put("invoice_email", "" + invoice_email);
			mydata.put("invoice_website", "" + invoice_website);

			mydata.put("notes", "" + notes);
			mydata.put("userinfo", "" + userinfo);
			mydata.put("shopcart", "" + shopcart);
			mydata.put("wishlist", "" + wishlist);

			db.update("segments", "id", id, mydata);
			Cache.clear(db, "segments");
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete("segments", "id", id);
			Cache.clear(db, "segments");
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
		segments = new HashMap<String,String>();
		String[] mynamevalues = values.split(",");
		for (int i=0; i<mynamevalues.length; i++) {
			String[] mynamevalue = mynamevalues[i].split("=");
			if (mynamevalue.length == 2) {
				String myname = mynamevalue[0];
				String myvalue = mynamevalue[1];
				if (! myname.equals("")) {
					segments.put(myname, myvalue);
				}
			} else if (mynamevalue.length == 1) {
				String myname = mynamevalue[0];
				String myvalue = "";
				if (! myname.equals("")) {
					segments.put(myname, myvalue);
				}
			}
		}
	}



	public void identify(Session session) {
		identify(session.get("usersegments"));
	}



	public String identify(Session session, DB db, Request request, String reqid, String reqclass, String reqdatabase) {
		return identify(session, db, request, reqid, reqclass, reqdatabase, null);
	}
	public String identify(Session session, DB db, Request request, String reqid, String reqclass, String reqdatabase, User user) {
		identify(session);
		if (db == null) return setSegments(session);
		HashMap<String,String> oldsegments = new HashMap<String,String>(segments);
		UsageLog usagelog = new UsageLog(reqid, reqclass, reqdatabase, session, request, false);
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String SQL = "select * from segments where (active=" + db.quote("1") + ") and ((" + db.is_blank("scheduled") + ") or (scheduled <= " + db.quote(now) + ")) and ((" + db.is_blank("unscheduled") + ") or (unscheduled > " + db.quote(now) + "))";
		records(db, SQL);
		while (records(db, "")) {
			boolean matched = true;
			boolean usermatched = false;
			if ((datetimefull != null) && (! datetimefull.equals(""))) {
				if (datetimefull.startsWith("<")) {
					if (usagelog.getData("datetimefull").compareTo(datetimefull.substring(1)) >= 0) {
						matched = false;
					}
				} else if (datetimefull.startsWith(">")) {
					if (usagelog.getData("datetimefull").compareTo(datetimefull.substring(1)) <= 0) {
						matched = false;
					}
				} else if (datetimefull.startsWith("=")) {
					if (! usagelog.getData("datetimefull").startsWith(datetimefull.substring(1))) {
						matched = false;
					}
				} else if (! usagelog.getData("datetimefull").startsWith(datetimefull)) {
					matched = false;
				}
			}
			if ((datetimeyear != null) && (! datetimeyear.equals(""))) {
				if (Pattern.compile("^[0-9]+-[0-9]+$").matcher(datetimeyear).find()) {
					String[] myyear = datetimeyear.split("-");
					String fromyear = "" + myyear[0];
					String toyear = "" + myyear[1];
					if ((Common.intnumber(usagelog.getData("datetimeyear")) < Common.intnumber(fromyear)) || (Common.intnumber(usagelog.getData("datetimeyear")) > Common.intnumber(toyear))) {
						matched = false;
					}
				} else if (datetimeyear.startsWith("<")) {
					if (Common.intnumber(usagelog.getData("datetimeyear")) >= Common.intnumber(datetimeyear.substring(1))) {
						matched = false;
					}
				} else if (datetimeyear.startsWith(">")) {
					if (Common.intnumber(usagelog.getData("datetimeyear")) <= Common.intnumber(datetimeyear.substring(1))) {
						matched = false;
					}
				} else if (datetimeyear.startsWith("=")) {
					if (Common.intnumber(usagelog.getData("datetimeyear")) != Common.intnumber(datetimeyear.substring(1))) {
						matched = false;
					}
				} else if (Common.intnumber(usagelog.getData("datetimeyear")) != Common.intnumber(datetimeyear)) {
					matched = false;
				}
			}
			if ((datetimemonth != null) && (! datetimemonth.equals(""))) {
				if (Pattern.compile("^[0-9]+-[0-9]+$").matcher(datetimemonth).find()) {
					String[] mymonth = datetimemonth.split("-");
					String frommonth = "" + mymonth[0];
					String tomonth = "" + mymonth[1];
					if ((Common.intnumber(usagelog.getData("datetimemonth")) < Common.intnumber(frommonth)) || (Common.intnumber(usagelog.getData("datetimemonth")) > Common.intnumber(tomonth))) {
						matched = false;
					}
				} else if (datetimemonth.startsWith("<")) {
					if (Common.intnumber(usagelog.getData("datetimemonth")) >= Common.intnumber(datetimemonth.substring(1))) {
						matched = false;
					}
				} else if (datetimemonth.startsWith(">")) {
					if (Common.intnumber(usagelog.getData("datetimemonth")) <= Common.intnumber(datetimemonth.substring(1))) {
						matched = false;
					}
				} else if (datetimemonth.startsWith("=")) {
					if (Common.intnumber(usagelog.getData("datetimemonth")) != Common.intnumber(datetimemonth.substring(1))) {
						matched = false;
					}
				} else if (Common.intnumber(usagelog.getData("datetimemonth")) != Common.intnumber(datetimemonth)) {
					matched = false;
				}
			}
			if ((datetimeday != null) && (! datetimeday.equals(""))) {
				if (Pattern.compile("^[0-9]+-[0-9]+$").matcher(datetimeday).find()) {
					String[] myday = datetimeday.split("-");
					String fromday = "" + myday[0];
					String today = "" + myday[1];
					if ((Common.intnumber(usagelog.getData("datetimeday")) < Common.intnumber(fromday)) || (Common.intnumber(usagelog.getData("datetimeday")) > Common.intnumber(today))) {
						matched = false;
					}
				} else if (datetimeday.startsWith("<")) {
					if (Common.intnumber(usagelog.getData("datetimeday")) >= Common.intnumber(datetimeday.substring(1))) {
						matched = false;
					}
				} else if (datetimeday.startsWith(">")) {
					if (Common.intnumber(usagelog.getData("datetimeday")) <= Common.intnumber(datetimeday.substring(1))) {
						matched = false;
					}
				} else if (datetimeday.startsWith("=")) {
					if (Common.intnumber(usagelog.getData("datetimeday")) != Common.intnumber(datetimeday.substring(1))) {
						matched = false;
					}
				} else if (Common.intnumber(usagelog.getData("datetimeday")) != Common.intnumber(datetimeday)) {
					matched = false;
				}
			}
			if ((datetimeweek != null) && (! datetimeweek.equals(""))) {
				if (Pattern.compile("^[0-9]+-[0-9]+$").matcher(datetimeweek).find()) {
					String[] myweek = datetimeweek.split("-");
					String fromweek = "" + myweek[0];
					String toweek = "" + myweek[1];
					if ((Common.intnumber(usagelog.getData("datetimeweek")) < Common.intnumber(fromweek)) || (Common.intnumber(usagelog.getData("datetimeweek")) > Common.intnumber(toweek))) {
						matched = false;
					}
				} else if (datetimeweek.startsWith("<")) {
					if (Common.intnumber(usagelog.getData("datetimeweek")) >= Common.intnumber(datetimeweek.substring(1))) {
						matched = false;
					}
				} else if (datetimeweek.startsWith(">")) {
					if (Common.intnumber(usagelog.getData("datetimeweek")) <= Common.intnumber(datetimeweek.substring(1))) {
						matched = false;
					}
				} else if (datetimeweek.startsWith("=")) {
					if (Common.intnumber(usagelog.getData("datetimeweek")) != Common.intnumber(datetimeweek.substring(1))) {
						matched = false;
					}
				} else if (Common.intnumber(usagelog.getData("datetimeweek")) != Common.intnumber(datetimeweek)) {
					matched = false;
				}
			}
			if ((datetimeweekday != null) && (! datetimeweekday.equals(""))) {
				if (Pattern.compile("^[0-9]+-[0-9]+$").matcher(datetimeweekday).find()) {
					String[] myweekday = datetimeweekday.split("-");
					String fromweekday = "" + myweekday[0];
					String toweekday = "" + myweekday[1];
					if ((Common.intnumber(usagelog.getData("datetimeweekday")) < Common.intnumber(fromweekday)) || (Common.intnumber(usagelog.getData("datetimeweekday")) > Common.intnumber(toweekday))) {
						matched = false;
					}
				} else if (datetimeweekday.startsWith("<")) {
					if (Common.intnumber(usagelog.getData("datetimeweekday")) >= Common.intnumber(datetimeweekday.substring(1))) {
						matched = false;
					}
				} else if (datetimeweekday.startsWith(">")) {
					if (Common.intnumber(usagelog.getData("datetimeweekday")) <= Common.intnumber(datetimeweekday.substring(1))) {
						matched = false;
					}
				} else if (datetimeweekday.startsWith("=")) {
					if (Common.intnumber(usagelog.getData("datetimeweekday")) != Common.intnumber(datetimeweekday.substring(1))) {
						matched = false;
					}
				} else if (Common.intnumber(usagelog.getData("datetimeweekday")) != Common.intnumber(datetimeweekday)) {
					matched = false;
				}
			}
			if ((datetimehour != null) && (! datetimehour.equals(""))) {
				if (Pattern.compile("^[0-9]+-[0-9]+$").matcher(datetimehour).find()) {
					String[] myhour = datetimehour.split("-");
					String fromhour = "" + myhour[0];
					String tohour = "" + myhour[1];
					if ((Common.intnumber(usagelog.getData("datetimehour")) < Common.intnumber(fromhour)) || (Common.intnumber(usagelog.getData("datetimehour")) > Common.intnumber(tohour))) {
						matched = false;
					}
				} else if (datetimehour.startsWith("<")) {
					if (Common.intnumber(usagelog.getData("datetimehour")) >= Common.intnumber(datetimehour.substring(1))) {
						matched = false;
					}
				} else if (datetimehour.startsWith(">")) {
					if (Common.intnumber(usagelog.getData("datetimehour")) <= Common.intnumber(datetimehour.substring(1))) {
						matched = false;
					}
				} else if (datetimehour.startsWith("=")) {
					if (Common.intnumber(usagelog.getData("datetimehour")) != Common.intnumber(datetimehour.substring(1))) {
						matched = false;
					}
				} else if (Common.intnumber(usagelog.getData("datetimehour")) != Common.intnumber(datetimehour)) {
					matched = false;
				}
			}
			if ((datetimemin != null) && (! datetimemin.equals(""))) {
				if (Pattern.compile("^[0-9]+-[0-9]+$").matcher(datetimemin).find()) {
					String[] mymin = datetimemin.split("-");
					String frommin = "" + mymin[0];
					String tomin = "" + mymin[1];
					if ((Common.intnumber(usagelog.getData("datetimemin")) < Common.intnumber(frommin)) || (Common.intnumber(usagelog.getData("datetimemin")) > Common.intnumber(tomin))) {
						matched = false;
					}
				} else if (datetimemin.startsWith("<")) {
					if (Common.intnumber(usagelog.getData("datetimemin")) >= Common.intnumber(datetimemin.substring(1))) {
						matched = false;
					}
				} else if (datetimemin.startsWith(">")) {
					if (Common.intnumber(usagelog.getData("datetimemin")) <= Common.intnumber(datetimemin.substring(1))) {
						matched = false;
					}
				} else if (datetimemin.startsWith("=")) {
					if (Common.intnumber(usagelog.getData("datetimemin")) != Common.intnumber(datetimemin.substring(1))) {
						matched = false;
					}
				} else if (Common.intnumber(usagelog.getData("datetimemin")) != Common.intnumber(datetimemin)) {
					matched = false;
				}
			}
			if ((datetimesec != null) && (! datetimesec.equals(""))) {
				if (Pattern.compile("^[0-9]+-[0-9]+$").matcher(datetimesec).find()) {
					String[] mysec = datetimesec.split("-");
					String fromsec = "" + mysec[0];
					String tosec = "" + mysec[1];
					if ((Common.intnumber(usagelog.getData("datetimesec")) < Common.intnumber(fromsec)) || (Common.intnumber(usagelog.getData("datetimesec")) > Common.intnumber(tosec))) {
						matched = false;
					}
				} else if (datetimesec.startsWith("<")) {
					if (Common.intnumber(usagelog.getData("datetimesec")) >= Common.intnumber(datetimesec.substring(1))) {
						matched = false;
					}
				} else if (datetimesec.startsWith(">")) {
					if (Common.intnumber(usagelog.getData("datetimesec")) <= Common.intnumber(datetimesec.substring(1))) {
						matched = false;
					}
				} else if (datetimesec.startsWith("=")) {
					if (Common.intnumber(usagelog.getData("datetimesec")) != Common.intnumber(datetimesec.substring(1))) {
						matched = false;
					}
				} else if (Common.intnumber(usagelog.getData("datetimesec")) != Common.intnumber(datetimesec)) {
					matched = false;
				}
			}

			if ((clientaddr != null) && (! clientaddr.equals(""))) {
				if (clientaddr.endsWith(".")) {
					if (! usagelog.getData("clientaddr").startsWith(clientaddr)) {
						matched = false;
					}
				} else if (clientaddr.startsWith(".")) {
					if (! usagelog.getData("clientaddr").endsWith(clientaddr)) {
						matched = false;
					}
				} else if (! usagelog.getData("clientaddr").equals(clientaddr)) {
					matched = false;
				}
			}
			if ((clienthost != null) && (! clienthost.equals(""))) {
				if (clienthost.endsWith(".")) {
					if (! usagelog.getData("clienthost").startsWith(clienthost)) {
						matched = false;
					}
				} else if (clienthost.startsWith(".")) {
					if (! usagelog.getData("clienthost").endsWith(clienthost)) {
						matched = false;
					}
				} else if (! usagelog.getData("clienthost").equals(clienthost)) {
					matched = false;
				}
			}
			if ((clientuser != null) && (! clientuser.equals(""))) {
				if (! usagelog.getData("clientuser").equals(clientuser)) {
					matched = false;
				}
			}
			if ((clientuseragent != null) && (! clientuseragent.equals(""))) {
				if (! usagelog.getData("clientuseragent").contains(clientuseragent)) {
					matched = false;
				}
			}
			if ((clientos != null) && (! clientos.equals(""))) {
				if (! usagelog.getData("clientos").equals(clientos)) {
					matched = false;
				}
			}
			if ((clientosversion != null) && (! clientosversion.equals(""))) {
				if (! usagelog.getData("clientosversion").contains(clientosversion)) {
					matched = false;
				}
			}
			if ((clientbrowser != null) && (! clientbrowser.equals(""))) {
				if ((usagelog.getData("clientbrowser") + usagelog.getData("clientversion")).equals(clientbrowser)) {
					// ok - MSIE##
				} else if (! usagelog.getData("clientbrowser").equals(clientbrowser)) {
					matched = false;
				}
			}
			if ((clientversion != null) && (! clientversion.equals(""))) {
				if (! usagelog.getData("clientversion").contains(clientversion)) {
					matched = false;
				}
			}
			if ((clientdevice != null) && (! clientdevice.equals(""))) {
				if (! usagelog.getData("clientdevice").equals(clientdevice)) {
					matched = false;
				}
			}
			if ((clientdeviceid != null) && (! clientdeviceid.equals(""))) {
				if (! usagelog.getData("clientdeviceid").equals(clientdeviceid)) {
					matched = false;
				}
			}
			if ((clientdeviceversion != null) && (! clientdeviceversion.equals(""))) {
				if (! usagelog.getData("clientdeviceversion").contains(clientdeviceversion)) {
					matched = false;
				}
			}
			if ((preferredlanguage != null) && (! preferredlanguage.equals(""))) {
				if (preferredlanguage.startsWith("!")) {
					if (usagelog.getData("preferredlanguage").startsWith(preferredlanguage.substring(1))) {
						matched = false;
					}
				} else if (preferredlanguage.startsWith("=")) {
					if (! usagelog.getData("preferredlanguage").startsWith(preferredlanguage.substring(1))) {
						matched = false;
					}
				} else if (! usagelog.getData("preferredlanguage").startsWith(preferredlanguage)) {
					matched = false;
				}
			}
			if ((acceptedlanguages != null) && (! acceptedlanguages.equals(""))) {
				if (acceptedlanguages.startsWith("!")) {
					if (usagelog.getData("acceptedlanguages").contains(acceptedlanguages.substring(1))) {
						matched = false;
					}
				} else if (acceptedlanguages.startsWith("=")) {
					if (! usagelog.getData("acceptedlanguages").contains(acceptedlanguages.substring(1))) {
						matched = false;
					}
				} else if (! usagelog.getData("acceptedlanguages").contains(acceptedlanguages)) {
					matched = false;
				}
			}

			if ((refererid != null) && (! refererid.equals(""))) {
				if (! usagelog.getData("refererid").equals(refererid)) {
					matched = false;
				}
			}
			if ((refererclass != null) && (! refererclass.equals(""))) {
				if (! usagelog.getData("refererclass").equals(refererclass)) {
					matched = false;
				}
			}
			if ((refererdatabase != null) && (! refererdatabase.equals(""))) {
				if (! usagelog.getData("refererdatabase").equals(refererdatabase)) {
					matched = false;
				}
			}
			if ((refererhost != null) && (! refererhost.equals(""))) {
				if (refererhost.endsWith(".")) {
					if (! usagelog.getData("refererhost").startsWith(refererhost)) {
						matched = false;
					}
				} else if (refererhost.startsWith(".")) {
					if (! usagelog.getData("refererhost").endsWith(refererhost)) {
						matched = false;
					}
				} else if (! usagelog.getData("refererhost").equals(refererhost)) {
					matched = false;
				}
			}
			if ((refererpath != null) && (! refererpath.equals(""))) {
				if (! usagelog.getData("refererpath").startsWith(refererpath)) {
					matched = false;
				}
			}
			if ((refererquery != null) && (! refererquery.equals(""))) {
				if (! usagelog.getData("refererquery").contains(refererquery)) {
					matched = false;
				}
			}
			if ((referersearchengine != null) && (! referersearchengine.equals(""))) {
				if (! usagelog.getData("referersearchengine").equals(referersearchengine)) {
					matched = false;
				}
			}
			if ((referersearchquery != null) && (! referersearchquery.equals(""))) {
				if (! usagelog.getData("referersearchquery").contains(referersearchquery)) {
					matched = false;
				}
			}

			if ((requesthost != null) && (! requesthost.equals(""))) {
				if (requesthost.endsWith(".")) {
					if (! usagelog.getData("requesthost").startsWith(requesthost)) {
						matched = false;
					}
				} else if (requesthost.startsWith(".")) {
					if (! usagelog.getData("requesthost").endsWith(requesthost)) {
						matched = false;
					}
				} else if (! usagelog.getData("requesthost").equals(requesthost)) {
					matched = false;
				}
			}
			if ((requestport != null) && (! requestport.equals(""))) {
				if (! usagelog.getData("requestport").equals(requestport)) {
					matched = false;
				}
			}
			if ((requestmethod != null) && (! requestmethod.equals(""))) {
				if (! usagelog.getData("requestmethod").equals(requestmethod)) {
					matched = false;
				}
			}
			if ((requestpath != null) && (! requestpath.equals(""))) {
				if (! usagelog.getData("requestpath").startsWith(requestpath)) {
					matched = false;
				}
			}
			if ((requestquery != null) && (! requestquery.equals(""))) {
				if (! usagelog.getData("requestquery").contains(requestquery)) {
					matched = false;
				}
			}
			if ((requestprotocol != null) && (! requestprotocol.equals(""))) {
				if (! usagelog.getData("requestprotocol").equals(requestprotocol)) {
					matched = false;
				}
			}
			if ((requestid != null) && (! requestid.equals(""))) {
				if (! usagelog.getData("requestid").equals(requestid)) {
					matched = false;
				}
			}
			if ((requestclass != null) && (! requestclass.equals(""))) {
				if (! usagelog.getData("requestclass").equals(requestclass)) {
					matched = false;
				}
			}
			if ((requestdatabase != null) && (! requestdatabase.equals(""))) {
				if (! usagelog.getData("requestdatabase").equals(requestdatabase)) {
					matched = false;
				}
			}

			if ((geoipcountry != null) && (! geoipcountry.equals(""))) {
				if (! usagelog.getData("geoipcountry").equals(geoipcountry)) {
					matched = false;
				}
			}
			if ((geoipregion != null) && (! geoipregion.equals(""))) {
				if (! usagelog.getData("geoipregion").equals(geoipregion)) {
					matched = false;
				}
			}
			if ((geoipcity != null) && (! geoipcity.equals(""))) {
				if (! usagelog.getData("geoipcity").equals(geoipcity)) {
					matched = false;
				}
			}
			if ((geoippostalcode != null) && (! geoippostalcode.equals(""))) {
				if (! usagelog.getData("geoippostalcode").startsWith(geoippostalcode)) {
					matched = false;
				}
			}
			if ((geoiplatitude != null) && (! geoiplatitude.equals(""))) {
				if (! usagelog.getData("geoiplatitude").startsWith(geoiplatitude)) {
					matched = false;
				}
			}
			if ((geoiplongitude != null) && (! geoiplongitude.equals(""))) {
				if (! usagelog.getData("geoiplongitude").startsWith(geoiplongitude)) {
					matched = false;
				}
			}
			if ((geoiptimezone != null) && (! geoiptimezone.equals(""))) {
				if (! usagelog.getData("geoiptimezone").equals(geoiptimezone)) {
					matched = false;
				}
			}

			if ((created != null) && (! created.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (created.equals("?")) {
					if (! user.getCreated().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (created.startsWith("<")) {
					if (user.getCreated().compareTo(created.substring(1)) >= 0) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (created.startsWith(">")) {
					if (user.getCreated().compareTo(created.substring(1)) <= 0) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (created.startsWith("=")) {
					if (! user.getCreated().startsWith(created.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getCreated().startsWith(created)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((updated != null) && (! updated.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (updated.equals("?")) {
					if (! user.getUpdated().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (updated.startsWith("<")) {
					if (user.getUpdated().compareTo(updated.substring(1)) >= 0) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (updated.startsWith(">")) {
					if (user.getUpdated().compareTo(updated.substring(1)) <= 0) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (updated.startsWith("=")) {
					if (! user.getUpdated().startsWith(updated.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getUpdated().startsWith(updated)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((created_by != null) && (! created_by.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (created_by.equals("?")) {
					if (! user.getCreatedBy().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getCreatedBy().startsWith(created_by)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((updated_by != null) && (! updated_by.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (updated_by.equals("?")) {
					if (! user.getUpdatedBy().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getUpdatedBy().startsWith(updated_by)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((keywords != null) && (! keywords.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (keywords.equals("?")) {
					if (! user.getKeywords().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getKeywords().contains(keywords)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((description != null) && (! description.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (description.equals("?")) {
					if (! user.getDescription().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getDescription().contains(description)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((birthdate != null) && (! birthdate.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (birthdate.equals("?")) {
					if (! user.getBirthDate().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (birthdate.startsWith("<")) {
					if (user.getBirthDate().compareTo(birthdate.substring(1)) >= 0) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (birthdate.startsWith(">")) {
					if (user.getBirthDate().compareTo(birthdate.substring(1)) <= 0) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (birthdate.startsWith("=")) {
					if (! user.getBirthDate().startsWith(birthdate.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getBirthDate().startsWith(birthdate)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((birthyear != null) && (! birthyear.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (birthyear.equals("?")) {
					if (! user.getBirthYear().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (birthyear.startsWith("<")) {
					if (Common.intnumber(user.getBirthYear()) >= Common.intnumber(birthyear.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (birthyear.startsWith(">")) {
					if (Common.intnumber(user.getBirthYear()) <= Common.intnumber(birthyear.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (birthyear.startsWith("=")) {
					if (Common.intnumber(user.getBirthYear()) != Common.intnumber(birthyear.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (Common.intnumber(user.getBirthYear()) != Common.intnumber(birthyear)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((birthmonth != null) && (! birthmonth.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (birthmonth.equals("?")) {
					if (! user.getBirthMonth().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (birthmonth.startsWith("<")) {
					if (Common.intnumber(user.getBirthMonth()) >= Common.intnumber(birthmonth.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (birthmonth.startsWith(">")) {
					if (Common.intnumber(user.getBirthMonth()) <= Common.intnumber(birthmonth.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (birthmonth.startsWith("=")) {
					if (Common.intnumber(user.getBirthMonth()) != Common.intnumber(birthmonth.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (Common.intnumber(user.getBirthMonth()) != Common.intnumber(birthmonth)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((birthday != null) && (! birthday.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (birthday.equals("?")) {
					if (! user.getBirthDay().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (birthday.startsWith("<")) {
					if (Common.intnumber(user.getBirthDay()) >= Common.intnumber(birthday.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (birthday.startsWith(">")) {
					if (Common.intnumber(user.getBirthDay()) <= Common.intnumber(birthday.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (birthday.startsWith("=")) {
					if (Common.intnumber(user.getBirthDay()) != Common.intnumber(birthday.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (Common.intnumber(user.getBirthDay()) != Common.intnumber(birthday)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((age != null) && (! age.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (Pattern.compile("^[0-9]+-[0-9]+$").matcher(age).find()) {
					String[] myage = age.split("-");
					String fromage = "" + myage[0];
					String toage = "" + myage[1];
					if ((Common.intnumber(user.getAge()) < Common.intnumber(fromage)) || (Common.intnumber(user.getAge()) > Common.intnumber(toage))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (age.equals("?")) {
					if (! user.getAge().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (age.startsWith("<")) {
					if (Common.intnumber(user.getAge()) >= Common.intnumber(age.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (age.startsWith(">")) {
					if (Common.intnumber(user.getAge()) < Common.intnumber(age.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (age.startsWith("=")) {
					if (Common.intnumber(user.getAge()) != Common.intnumber(age.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (Common.intnumber(user.getAge()) != Common.intnumber(age)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((gender != null) && (! gender.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (gender.equals("?")) {
					if (! user.getGender().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getGender().equals(gender)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((title != null) && (! title.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (title.equals("?")) {
					if (! user.getTitle().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getTitle().equals(title)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((name != null) && (! name.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (name.equals("?")) {
					if (! user.getName().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getName().equals(name)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((organisation != null) && (! organisation.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (organisation.equals("?")) {
					if (! user.getOrganisation().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getOrganisation().equals(organisation)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((username != null) && (! username.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (username.equals("?")) {
					if (! user.getUsername().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getUsername().contains(username)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((password != null) && (! password.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (password.equals("?")) {
					if (! user.getPassword().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (password.startsWith("<")) {
					if (user.getPassword().length() >= Common.intnumber(password.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (password.startsWith(">")) {
					if (user.getPassword().length() <= Common.intnumber(password.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getPassword().contains(password)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((email != null) && (! email.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (email.equals("?")) {
					if (! user.getEmail().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getEmail().contains(email)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((usertype != null) && (! usertype.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (usertype.equals("?")) {
					if (! user.getUsertype().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getUsertype().equals(usertype)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((usergroup != null) && (! usergroup.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (usergroup.equals("?")) {
					if (! user.getUsergroup().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getUsergroup().equals(usergroup)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((userclass != null) && (! userclass.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (userclass.equals("?")) {
					if (! user.getUserclass().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getUserclass().equals(userclass)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}

			if ((scheduled_last != null) && (! scheduled_last.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (scheduled_last.equals("?")) {
					if (! user.getScheduledLast().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (scheduled_last.startsWith("<")) {
					if (user.getScheduledLast().compareTo(scheduled_last.substring(1)) >= 0) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (scheduled_last.startsWith(">")) {
					if (user.getScheduledLast().compareTo(scheduled_last.substring(1)) <= 0) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (scheduled_last.startsWith("=")) {
					if (! user.getScheduledLast().startsWith(scheduled_last.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getScheduledLast().startsWith(scheduled_last)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((scheduled_publish != null) && (! scheduled_publish.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (scheduled_publish.equals("?")) {
					if (! user.getScheduledPublish().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (scheduled_publish.startsWith("<")) {
					if (user.getScheduledPublish().compareTo(scheduled_publish.substring(1)) >= 0) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (scheduled_publish.startsWith(">")) {
					if (user.getScheduledPublish().compareTo(scheduled_publish.substring(1)) <= 0) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (scheduled_publish.startsWith("=")) {
					if (! user.getScheduledPublish().startsWith(scheduled_publish.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (user.getScheduledPublish().compareTo(scheduled_publish) >= 0) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((scheduled_notify != null) && (! scheduled_notify.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (scheduled_notify.equals("?")) {
					if (! user.getScheduledNotify().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (scheduled_notify.startsWith("<")) {
					if (user.getScheduledNotify().compareTo(scheduled_notify.substring(1)) >= 0) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (scheduled_notify.startsWith(">")) {
					if (user.getScheduledNotify().compareTo(scheduled_notify.substring(1)) <= 0) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (scheduled_notify.startsWith("=")) {
					if (! user.getScheduledNotify().startsWith(scheduled_notify.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (user.getScheduledNotify().compareTo(scheduled_notify) >= 0) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((scheduled_unpublish != null) && (! scheduled_unpublish.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (scheduled_unpublish.equals("?")) {
					if (! user.getScheduledUnpublish().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (scheduled_unpublish.startsWith("<")) {
					if (user.getScheduledUnpublish().compareTo(scheduled_unpublish.substring(1)) >- 0) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (scheduled_unpublish.startsWith(">")) {
					if (user.getScheduledUnpublish().compareTo(scheduled_unpublish.substring(1)) <= 0) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (scheduled_unpublish.startsWith("=")) {
					if (! user.getScheduledUnpublish().startsWith(scheduled_unpublish.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (user.getScheduledUnpublish().compareTo(scheduled_unpublish) >= 0) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((scheduled_publish_email != null) && (! scheduled_publish_email.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (scheduled_publish_email.equals("?")) {
					if (! user.getScheduledPublishEmail().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getScheduledPublishEmail().equals(scheduled_publish_email)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((scheduled_notify_email != null) && (! scheduled_notify_email.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (scheduled_notify_email.equals("?")) {
					if (! user.getScheduledNotifyEmail().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getScheduledNotifyEmail().equals(scheduled_notify_email)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((scheduled_unpublish_email != null) && (! scheduled_unpublish_email.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (scheduled_unpublish_email.equals("?")) {
					if (! user.getScheduledUnpublishEmail().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getScheduledUnpublishEmail().equals(scheduled_unpublish_email)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}

			if ((card_type != null) && (! card_type.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (card_type.equals("?")) {
					if (! user.getCardType().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getCardType().equals(card_type)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((card_number != null) && (! card_number.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (card_number.equals("?")) {
					if (! user.getCardNumber().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getCardNumber().startsWith(card_number)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((card_issuedmonth != null) && (! card_issuedmonth.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (card_issuedmonth.equals("?")) {
					if (! user.getCardIssuedMonth().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (card_issuedmonth.startsWith("<")) {
					if (Common.intnumber(user.getCardIssuedMonth()) >= Common.intnumber(card_issuedmonth.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (card_issuedmonth.startsWith(">")) {
					if (Common.intnumber(user.getCardIssuedMonth()) <= Common.intnumber(card_issuedmonth.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (card_issuedmonth.startsWith("=")) {
					if (Common.intnumber(user.getCardIssuedMonth()) != Common.intnumber(card_issuedmonth.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (Common.intnumber(user.getCardIssuedMonth()) != Common.intnumber(card_issuedmonth)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((card_issuedyear != null) && (! card_issuedyear.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (card_issuedyear.equals("?")) {
					if (! user.getCardIssuedYear().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (card_issuedyear.startsWith("<")) {
					if (Common.intnumber(user.getCardIssuedYear()) >= Common.intnumber(card_issuedyear.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (card_issuedyear.startsWith(">")) {
					if (Common.intnumber(user.getCardIssuedYear()) <= Common.intnumber(card_issuedyear.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (card_issuedyear.startsWith("=")) {
					if (Common.intnumber(user.getCardIssuedYear()) != Common.intnumber(card_issuedyear.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (Common.intnumber(user.getCardIssuedYear()) != Common.intnumber(card_issuedyear)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((card_expirymonth != null) && (! card_expirymonth.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (card_expirymonth.equals("?")) {
					if (! user.getCardExpiryMonth().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (card_expirymonth.startsWith("<")) {
					if (Common.intnumber(user.getCardExpiryMonth()) >= Common.intnumber(card_expirymonth.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (card_expirymonth.startsWith(">")) {
					if (Common.intnumber(user.getCardExpiryMonth()) <= Common.intnumber(card_expirymonth.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (card_expirymonth.startsWith("=")) {
					if (Common.intnumber(user.getCardExpiryMonth()) != Common.intnumber(card_expirymonth.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (Common.intnumber(user.getCardExpiryMonth()) != Common.intnumber(card_expirymonth)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((card_expiryyear != null) && (! card_expiryyear.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (card_expiryyear.equals("?")) {
					if (! user.getCardExpiryYear().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (card_expiryyear.startsWith("<")) {
					if (Common.intnumber(user.getCardExpiryYear()) >= Common.intnumber(card_expiryyear.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (card_expiryyear.startsWith(">")) {
					if (Common.intnumber(user.getCardExpiryYear()) <= Common.intnumber(card_expiryyear.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (card_expiryyear.startsWith("=")) {
					if (Common.intnumber(user.getCardExpiryYear()) != Common.intnumber(card_expiryyear.substring(1))) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (Common.intnumber(user.getCardExpiryYear()) != Common.intnumber(card_expiryyear)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((card_name != null) && (! card_name.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (card_name.equals("?")) {
					if (! user.getCardName().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getCardName().contains(card_name)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((card_postalcode != null) && (! card_postalcode.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (card_postalcode.equals("?")) {
					if (! user.getCardPostalcode().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getCardPostalcode().startsWith(card_postalcode)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}

			if ((delivery_name != null) && (! delivery_name.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (delivery_name.equals("?")) {
					if (! user.getDeliveryName().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getDeliveryName().contains(delivery_name)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((delivery_organisation != null) && (! delivery_organisation.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (delivery_organisation.equals("?")) {
					if (! user.getDeliveryOrganisation().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getDeliveryOrganisation().equals(delivery_organisation)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((delivery_address != null) && (! delivery_address.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (delivery_address.equals("?")) {
					if (! user.getDeliveryAddress().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getDeliveryAddress().contains(delivery_address)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((delivery_postalcode != null) && (! delivery_postalcode.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (delivery_postalcode.equals("?")) {
					if (! user.getDeliveryPostalcode().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getDeliveryPostalcode().startsWith(delivery_postalcode)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((delivery_city != null) && (! delivery_city.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (delivery_city.equals("?")) {
					if (! user.getDeliveryCity().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getDeliveryCity().equals(delivery_city)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((delivery_state != null) && (! delivery_state.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (delivery_state.equals("?")) {
					if (! user.getDeliveryState().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getDeliveryState().equals(delivery_state)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((delivery_country != null) && (! delivery_country.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (delivery_country.equals("?")) {
					if (! user.getDeliveryCountry().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getDeliveryCountry().equals(delivery_country)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((delivery_phone != null) && (! delivery_phone.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (delivery_phone.equals("?")) {
					if (! user.getDeliveryPhone().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getDeliveryPhone().startsWith(delivery_phone)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((delivery_fax != null) && (! delivery_fax.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (delivery_fax.equals("?")) {
					if (! user.getDeliveryFax().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getDeliveryFax().startsWith(delivery_fax)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((delivery_email != null) && (! delivery_email.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (delivery_email.equals("?")) {
					if (! user.getDeliveryEmail().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getDeliveryEmail().contains(delivery_email)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((delivery_website != null) && (! delivery_website.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (delivery_website.equals("?")) {
					if (! user.getDeliveryWebsite().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getDeliveryWebsite().contains(delivery_website)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}

			if ((invoice_name != null) && (! invoice_name.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (invoice_name.equals("?")) {
					if (! user.getInvoiceName().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getInvoiceName().contains(invoice_name)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((invoice_organisation != null) && (! invoice_organisation.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (invoice_organisation.equals("?")) {
					if (! user.getInvoiceOrganisation().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getInvoiceOrganisation().equals(invoice_organisation)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((invoice_address != null) && (! invoice_address.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (invoice_address.equals("?")) {
					if (! user.getInvoiceAddress().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getInvoiceAddress().contains(invoice_address)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((invoice_postalcode != null) && (! invoice_postalcode.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (invoice_postalcode.equals("?")) {
					if (! user.getInvoicePostalcode().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getInvoicePostalcode().startsWith(invoice_postalcode)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((invoice_city != null) && (! invoice_city.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (invoice_city.equals("?")) {
					if (! user.getInvoiceCity().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getInvoiceCity().equals(invoice_city)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((invoice_state != null) && (! invoice_state.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (invoice_state.equals("?")) {
					if (! user.getInvoiceState().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getInvoiceState().equals(invoice_state)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((invoice_country != null) && (! invoice_country.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (invoice_country.equals("?")) {
					if (! user.getInvoiceCountry().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getInvoiceCountry().equals(invoice_country)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((invoice_phone != null) && (! invoice_phone.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (invoice_phone.equals("?")) {
					if (! user.getInvoicePhone().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getInvoicePhone().startsWith(invoice_phone)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((invoice_fax != null) && (! invoice_fax.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (invoice_fax.equals("?")) {
					if (! user.getInvoiceFax().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getInvoiceFax().startsWith(invoice_fax)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((invoice_email != null) && (! invoice_email.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (invoice_email.equals("?")) {
					if (! user.getInvoiceEmail().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getInvoiceEmail().contains(invoice_email)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((invoice_website != null) && (! invoice_website.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (invoice_website.equals("?")) {
					if (! user.getInvoiceWebsite().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getInvoiceWebsite().contains(invoice_website)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}

			if ((notes != null) && (! notes.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (notes.equals("?")) {
					if (! user.getNotes().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getNotes().contains(notes)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((userinfo != null) && (! userinfo.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (userinfo.equals("?")) {
					if (! user.getUserinfo().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getUserinfo().contains(userinfo)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((shopcart != null) && (! shopcart.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (shopcart.equals("?")) {
					if (! user.getShopcart().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getShopcart().contains(shopcart)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}
			if ((wishlist != null) && (! wishlist.equals(""))) {
				if (user == null) {
					matched = false;
				} else if (wishlist.equals("?")) {
					if (! user.getWishlist().equals("")) {
						matched = false;
					} else {
						usermatched = true;
					}
				} else if (! user.getWishlist().contains(wishlist)) {
					matched = false;
				} else {
					usermatched = true;
				}
			}

			if (matched) {
				if (usermatched) {
					segments.put(getSegmentId(), "" + Common.intnumber(getWeight()));
				} else if (oldsegments.get(getSegmentId()) == null) {
					if (segments.get(getSegmentId()) == null) {
						segments.put(getSegmentId(), "" + Common.intnumber(getWeight()));
					} else if ((Common.intnumber(segments.get(getSegmentId())) >= 0) && (Common.intnumber(getWeight()) > Common.intnumber(segments.get(getSegmentId())))) {
						segments.put(getSegmentId(), "" + Common.intnumber(getWeight()));
					} else if ((Common.intnumber(segments.get(getSegmentId())) < 0) && (Common.intnumber(getWeight()) < Common.intnumber(segments.get(getSegmentId())))) {
						segments.put(getSegmentId(), "" + Common.intnumber(getWeight()));
					}
				}
			}
		}

		String output = setSegments(session);
		return output;
	}



	public String getSegments() {
		String output = "";
		Iterator mysegments = Common.LinkedHashMapSortedByNumericValue(segments, false).keySet().iterator();
		while (mysegments.hasNext()) {
			String mysegment = "" + mysegments.next();
			if (! output.equals("")) output += ",";
			output += mysegment + "=" + Common.intnumber(segments.get(mysegment));
		}
		return output;
	}



	public String setSegments(Session session) {
		String output = getSegments();
		session.set("usersegments", output);
		return output;
	}



	public String select_options(DB db, String selected) {
		return Common.select_options(db, "segments", "segmentid", selected);
	}



	public String usergroup_select_options(DB db, String selected) {
		Usergroup usergroup = new Usergroup();
		return usergroup.select_options(db, selected);
	}



	public String usertype_select_options(DB db, String selected) {
		Usertype usertype = new Usertype();
		return usertype.select_options(db, selected);
	}



	public LinkedHashMap<String,String> content_ids(DB db) {
		if (db == null) return new LinkedHashMap<String,String>();

		String SQL = "select id from content where usersegment=" + db.quote(segmentid);
		LinkedHashMap<String,String> myids = db.query_values(SQL);
		SQL = "select id from content_public where usersegment=" + db.quote(segmentid);
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

		SQL = "select distinct usertest from content where id in (" + Common.SQL_list_values(myids) + ")";
		LinkedHashMap<String,String> myusertests = db.query_values(SQL);
		SQL = "select distinct usertest from content_public where id in (" + Common.SQL_list_values(myids) + ")";
		LinkedHashMap<String,String> myusertests2 = db.query_values(SQL);
		myusertests = Common.array_merge_values(myusertests, myusertests2);

		SQL = "select distinct version_master from content where usersegment=" + db.quote(segmentid);
		LinkedHashMap<String,String> mymasters = db.query_values(SQL);
		SQL = "select distinct version_master from content_public where usersegment=" + db.quote(segmentid);
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
			SQL = "select distinct id from content where version_master in (" + Common.SQL_list_values(mymasters) + ") and ((version in (" + Common.SQL_list_values(mylanguages) + ") and " + db.is_not_blank("version") + ") or (device in (" + Common.SQL_list_values(mydevices) + ") and " + db.is_not_blank("device") + ") or (usertest in (" + Common.SQL_list_values(myusertests) + ") and " + db.is_not_blank("usertest") + "))";
			LinkedHashMap<String,String> myversions = db.query_values(SQL);
			SQL = "select distinct id from content_public where version_master in (" + Common.SQL_list_values(mymasters) + ") and ((version in (" + Common.SQL_list_values(mylanguages) + ") and " + db.is_not_blank("version") + ") or (device in (" + Common.SQL_list_values(mydevices) + ") and " + db.is_not_blank("device") + ") or (usertest in (" + Common.SQL_list_values(myusertests) + ") and " + db.is_not_blank("usertest") + "))";
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



	public Page getScheduledPublishEmailPage(Session mysession, DB db, Configuration myconfig) {
		Page page = new Page(text);
		page.read_primary_only(db, myconfig, getScheduledPublishEmail(), "content_public", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
		return page;
	}



	public Page getScheduledNotifyEmailPage(Session mysession, DB db, Configuration myconfig) {
		Page page = new Page(text);
		page.read_primary_only(db, myconfig, getScheduledNotifyEmail(), "content_public", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
		return page;
	}



	public Page getScheduledUnpublishEmailPage(Session mysession, DB db, Configuration myconfig) {
		Page page = new Page(text);
		page.read_primary_only(db, myconfig, getScheduledUnpublishEmail(), "content_public", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
		return page;
	}



	public String getId() {
		return id;
	}
	public void setId(String value) {
		id = value;
	}



	public String getSegmentId() {
		return segmentid;
	}
	public void setSegmentId(String value) {
		segmentid = value;
	}



	public String getSegment() {
		return segment;
	}
	public void setSegment(String value) {
		segment = value;
	}



	public String getWeight() {
		return weight;
	}
	public int getWeight(String mysegment) {
		return Common.intnumber(segments.get(mysegment));
	}
	public void setWeight(String value) {
		weight = value;
	}
	public void setWeight(String mysegment, String value) {
		int myweight = Common.intnumber(segments.get(mysegment));
		if (value.matches("^[+][0-9]+$")) {
			myweight += Common.intnumber(value.substring(1));
		} else if (value.matches("^[-][0-9]+$")) {
			myweight -= Common.intnumber(value.substring(1));
		} else if (value.matches("^[=][0-9]+$")) {
			myweight = Common.intnumber(value.substring(1));
		} else {
			myweight = Common.intnumber(value);
		}
		if (myweight > 999999) myweight = 999999;
		if (myweight < -999999) myweight = -999999;
		segments.put(mysegment, "" + myweight);
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



	public String getDateTimeFull() {
		return datetimefull;
	}
	public void setDateTimeFull(String value) {
		datetimefull = value;
	}



	public String getDateTimeYear() {
		return datetimeyear;
	}
	public void setDateTimeYear(String value) {
		datetimeyear = value;
	}



	public String getDateTimeMonth() {
		return datetimemonth;
	}
	public void setDateTimeMonth(String value) {
		datetimemonth = value;
	}



	public String getDateTimeDay() {
		return datetimeday;
	}
	public void setDateTimeDay(String value) {
		datetimeday = value;
	}



	public String getDateTimeWeek() {
		return datetimeweek;
	}
	public void setDateTimeWeek(String value) {
		datetimeweek = value;
	}



	public String getDateTimeWeekday() {
		return datetimeweekday;
	}
	public void setDateTimeWeekday(String value) {
		datetimeweekday = value;
	}



	public String getDateTimeHour() {
		return datetimehour;
	}
	public void setDateTimeHour(String value) {
		datetimehour = value;
	}



	public String getDateTimeMinute() {
		return datetimemin;
	}
	public void setDateTimeMinute(String value) {
		datetimemin = value;
	}



	public String getDateTimeSecond() {
		return datetimesec;
	}
	public void setDateTimeSecond(String value) {
		datetimesec = value;
	}




	public String getClientAddress() {
		return clientaddr;
	}
	public void setClientAddress(String value) {
		clientaddr = value;
	}



	public String getClientHost() {
		return clienthost;
	}
	public void setClientHost(String value) {
		clienthost = value;
	}



	public String getClientUser() {
		return clientuser;
	}
	public void setClientUser(String value) {
		clientuser = value;
	}



	public String getClientUserAgent() {
		return clientuseragent;
	}
	public void setClientUserAgent(String value) {
		clientuseragent = value;
	}



	public String getClientOS() {
		return clientos;
	}
	public void setClientOS(String value) {
		clientos = value;
	}



	public String getClientOSVersion() {
		return clientosversion;
	}
	public void setClientOSVersion(String value) {
		clientosversion = value;
	}



	public String getClientBrowser() {
		return clientbrowser;
	}
	public void setClientBrowser(String value) {
		clientbrowser = value;
	}



	public String getClientBrowserVersion() {
		return clientversion;
	}
	public void setClientBrowserVersion(String value) {
		clientversion = value;
	}



	public String getClientDevice() {
		return clientdevice;
	}
	public void setClientDevice(String value) {
		clientdevice = value;
	}



	public String getClientDeviceId() {
		return clientdeviceid;
	}
	public void setClientDeviceId(String value) {
		clientdeviceid = value;
	}



	public String getClientDeviceVersion() {
		return clientdeviceversion;
	}
	public void setClientDeviceVersion(String value) {
		clientdeviceversion = value;
	}



	public String getPreferredLanguage() {
		return preferredlanguage;
	}
	public void setPreferredLanguagen(String value) {
		preferredlanguage = value;
	}



	public String getAcceptedLanguages() {
		return acceptedlanguages;
	}
	public void setAcceptedLanguages(String value) {
		acceptedlanguages = value;
	}



	public String getReferrerId() {
		return refererid;
	}
	public void setReferrerId(String value) {
		refererid = value;
	}



	public String getReferrerClass() {
		return refererclass;
	}
	public void setReferrerClass(String value) {
		refererclass = value;
	}



	public String getReferrerDatabase() {
		return refererdatabase;
	}
	public void setReferrerDatabase(String value) {
		refererdatabase = value;
	}



	public String getReferrerHost() {
		return refererhost;
	}
	public void setReferrerHost(String value) {
		refererhost = value;
	}



	public String getReferrerPath() {
		return refererpath;
	}
	public void setReferrerPath(String value) {
		refererpath = value;
	}



	public String getReferrerQuery() {
		return refererquery;
	}
	public void setReferrerQuery(String value) {
		refererquery = value;
	}



	public String getReferrerSearchEngine() {
		return referersearchengine;
	}
	public void setReferrerSearchEngine(String value) {
		referersearchengine = value;
	}



	public String getReferrerSearchQuery() {
		return referersearchquery;
	}
	public void setReferrerSearchQuery(String value) {
		referersearchquery = value;
	}




	public String getRequestHost() {
		return requesthost;
	}
	public void setRequestHost(String value) {
		requesthost = value;
	}



	public String getRequestPort() {
		return requestport;
	}
	public void setRequestPort(String value) {
		requestport = value;
	}



	public String getRequestMethod() {
		return requestmethod;
	}
	public void setRequestMethod(String value) {
		requestmethod = value;
	}



	public String getRequestPath() {
		return requestpath;
	}
	public void setRequestPath(String value) {
		requestpath = value;
	}



	public String getRequestQuery() {
		return requestquery;
	}
	public void setRequestQuery(String value) {
		requestquery = value;
	}



	public String getRequestProtocol() {
		return requestprotocol;
	}
	public void setRequestProtocol(String value) {
		requestprotocol = value;
	}



	public String getRequestId() {
		return requestid;
	}
	public void setRequestId(String value) {
		requestid = value;
	}



	public String getRequestClass() {
		return requestclass;
	}
	public void setRequestClass(String value) {
		requestclass = value;
	}



	public String getRequestDatabase() {
		return requestdatabase;
	}
	public void setRequestDatabase(String value) {
		requestdatabase = value;
	}




	public String getGeoIPCountry() {
		return geoipcountry;
	}
	public void setGeoIPCountry(String value) {
		geoipcountry = value;
	}




	public String getGeoIPRegion() {
		return geoipregion;
	}
	public void setGeoIPRegion(String value) {
		geoipregion = value;
	}




	public String getGeoIPCity() {
		return geoipcity;
	}
	public void setGeoIPCity(String value) {
		geoipcity = value;
	}




	public String getGeoIPPostalcode() {
		return geoippostalcode;
	}
	public void setGeoIPPostalcode(String value) {
		geoippostalcode = value;
	}




	public String getGeoIPLatitude() {
		return geoiplatitude;
	}
	public void setGeoIPLatitude(String value) {
		geoiplatitude = value;
	}




	public String getGeoIPLongitude() {
		return geoiplongitude;
	}
	public void setGeoIPLongitude(String value) {
		geoiplongitude = value;
	}




	public String getGeoIPTimezone() {
		return geoiptimezone;
	}
	public void setGeoIPTimezone(String value) {
		geoiptimezone = value;
	}




	public String getCreated() {
		return created;
	}
	public void setCreated(String value) {
		created = value;
	}



	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String value) {
		updated = value;
	}



	public String getCreatedBy() {
		return created_by;
	}
	public void setCreatedBy(String value) {
		created_by = value;
	}



	public String getUpdatedBy() {
		return updated_by;
	}
	public void setUpdatedBy(String value) {
		updated_by = value;
	}



	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String value) {
		keywords = value;
	}



	public String getDescription() {
		return description;
	}
	public void setDescription(String value) {
		description = value;
	}



	public String getBirthDate() {
		return birthdate;
	}
	public void setBirthDate(String value) {
		birthdate = value;
	}



	public String getBirthYear() {
		return birthyear;
	}
	public void setBirthYear(String value) {
		birthyear = value;
	}



	public String getBirthMonth() {
		return birthmonth;
	}
	public void setBirthMonth(String value) {
		birthmonth = value;
	}



	public String getBirthDay() {
		return birthday;
	}
	public void setBirthDay(String value) {
		birthday = value;
	}



	public String getAge() {
		return age;
	}
	public void setAge(String value) {
		age = value;
	}



	public String getGender() {
		return gender;
	}
	public void setGender(String value) {
		gender = value;
	}



	public String getTitle() {
		return title;
	}
	public void setTitle(String value) {
		title = value;
	}



	public String getName() {
		return name;
	}
	public void setName(String value) {
		name = value;
	}



	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String value) {
		organisation = value;
	}



	public String getUsername() {
		return username;
	}
	public void setUsername(String value) {
		username = value;
	}



	public String getPassword() {
		return password;
	}
	public void setPassword(String value) {
		password = value;
	}



	public String getEmail() {
		return email;
	}
	public void setEmail(String value) {
		email = value;
	}



	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String value) {
		usertype = value;
	}



	public String getUsergroup() {
		return usergroup;
	}
	public void setUsergroup(String value) {
		usergroup = value;
	}



	public String getUserclass() {
		return userclass;
	}
	public void setUserclass(String value) {
		userclass = value;
	}




	public String getScheduledLast() {
		return scheduled_last;
	}
	public void setScheduledLast(String value) {
		scheduled_last = value;
	}



	public String getScheduledPublish() {
		return scheduled_publish;
	}
	public void setScheduledPublish(String value) {
		scheduled_publish = value;
	}



	public String getScheduledNotify() {
		return scheduled_notify;
	}
	public void setScheduledNotify(String value) {
		scheduled_notify = value;
	}



	public String getScheduledUnpublish() {
		return scheduled_unpublish;
	}
	public void setScheduledUnpublish(String value) {
		scheduled_unpublish = value;
	}



	public String getScheduledPublishEmail() {
		return scheduled_publish_email;
	}
	public void setScheduledPublishEmail(String value) {
		scheduled_publish_email = value;
	}



	public String getScheduledNotifyEmail() {
		return scheduled_notify_email;
	}
	public void setScheduledNotifyEmail(String value) {
		scheduled_notify_email = value;
	}



	public String getScheduledUnpublishEmail() {
		return scheduled_unpublish_email;
	}
	public void setScheduledUnpublishEmail(String value) {
		scheduled_unpublish_email = value;
	}



	public String getCardType() {
		return card_type;
	}
	public void setCardType(String value) {
		card_type = value;
	}



	public String getCardNumber() {
		return card_number;
	}
	public void setCardNumber(String value) {
		card_number = value;
	}



	public String getCardIssuedMonth() {
		return card_issuedmonth;
	}
	public void setCardIssuedMonth(String value) {
		card_issuedmonth = value;
	}



	public String getCardIssuedYear() {
		return card_issuedyear;
	}
	public void setCardIssuedYear(String value) {
		card_issuedyear = value;
	}



	public String getCardExpiryMonth() {
		return card_expirymonth;
	}
	public void setCardExpiryMonth(String value) {
		card_expirymonth = value;
	}



	public String getCardExpiryYear() {
		return card_expiryyear;
	}
	public void setCardExpiryYear(String value) {
		card_expiryyear = value;
	}



	public String getCardName() {
		return card_name;
	}
	public void setCardName(String value) {
		card_name = value;
	}



	public String getCardPostalcode() {
		return card_postalcode;
	}
	public void setCardPostalcode(String value) {
		card_postalcode = value;
	}



	public String getDeliveryName() {
		return delivery_name;
	}
	public void setDeliveryName(String value) {
		delivery_name = value;
	}



	public String getDeliveryOrganisation() {
		return delivery_organisation;
	}
	public void setDeliveryOrganisation(String value) {
		delivery_organisation = value;
	}



	public String getDeliveryAddress() {
		return delivery_address;
	}
	public void setDeliveryAddress(String value) {
		delivery_address = value;
	}



	public String getDeliveryPostalcode() {
		return delivery_postalcode;
	}
	public void setDeliveryPostalcode(String value) {
		delivery_postalcode = value;
	}



	public String getDeliveryCity() {
		return delivery_city;
	}
	public void setDeliveryCity(String value) {
		delivery_city = value;
	}



	public String getDeliveryState() {
		return delivery_state;
	}
	public void setDeliveryState(String value) {
		delivery_state = value;
	}



	public String getDeliveryCountry() {
		return delivery_country;
	}
	public void setDeliveryCountry(String value) {
		delivery_country = value;
	}



	public String getDeliveryPhone() {
		return delivery_phone;
	}
	public void setDeliveryPhone(String value) {
		delivery_phone = value;
	}



	public String getDeliveryFax() {
		return delivery_fax;
	}
	public void setDeliveryFax(String value) {
		delivery_fax = value;
	}



	public String getDeliveryEmail() {
		return delivery_email;
	}
	public void setDeliveryEmail(String value) {
		delivery_email = value;
	}



	public String getDeliveryWebsite() {
		return delivery_website;
	}
	public void setDeliveryWebsite(String value) {
		delivery_website = value;
	}



	public String getInvoiceName() {
		return invoice_name;
	}
	public void setInvoiceName(String value) {
		invoice_name = value;
	}



	public String getInvoiceOrganisation() {
		return invoice_organisation;
	}
	public void setInvoiceOrganisation(String value) {
		invoice_organisation = value;
	}



	public String getInvoiceAddress() {
		return invoice_address;
	}
	public void setInvoiceAddress(String value) {
		invoice_address = value;
	}



	public String getInvoicePostalcode() {
		return invoice_postalcode;
	}
	public void setInvoicePostalcode(String value) {
		invoice_postalcode = value;
	}



	public String getInvoiceCity() {
		return invoice_city;
	}
	public void setInvoiceCity(String value) {
		invoice_city = value;
	}



	public String getInvoiceState() {
		return invoice_state;
	}
	public void setInvoiceState(String value) {
		invoice_state = value;
	}



	public String getInvoiceCountry() {
		return invoice_country;
	}
	public void setInvoiceCountry(String value) {
		invoice_country = value;
	}



	public String getInvoicePhone() {
		return invoice_phone;
	}
	public void setInvoicePhone(String value) {
		invoice_phone = value;
	}



	public String getInvoiceFax() {
		return invoice_fax;
	}
	public void setInvoiceFax(String value) {
		invoice_fax = value;
	}



	public String getInvoiceEmail() {
		return invoice_email;
	}
	public void setInvoiceEmail(String value) {
		invoice_email = value;
	}



	public String getInvoiceWebsite() {
		return invoice_website;
	}
	public void setInvoiceWebsite(String value) {
		invoice_website = value;
	}



	public String getNotes() {
		return notes;
	}
	public void setNotes(String value) {
		notes = value;
	}



	public String getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(String value) {
		userinfo = value;
	}



	public String getShopcart() {
		return shopcart;
	}
	public void setShopcart(String value) {
		shopcart = value;
	}



	public String getWishlist() {
		return wishlist;
	}
	public void setWishlist(String value) {
		wishlist = value;
	}



}
