package HardCore;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Website {
	private String id = "";
	private String domain = "";
	private String default_page = "";
	private String default_version = "";
	private String default_country = "";
	private String default_state = "";
	private String default_price = "";
	private String default_doctype = "";
	private String default_template = "";
	private String default_stylesheet = "";
	private String default_page_nonexisting = "";
	private String default_page_unpublished = "";
	private String default_page_expired = "";
	private String default_login = "";
	private String default_searchresults_page = "";
	private String default_searchresults_entry = "";
	private String default_list_entry = "";
	private String default_publish_ready = "";
	private String default_register_confirmation_page = "";
	private String default_register_notification_page = "";
	private String default_personal_admin_page = "";
	private String retrieve_password_page = "";
	private String retrieve_password_confirmation = "";
	private String retrieve_password_email = "";
	private String retrieve_password_error = "";
	private String language = "";
	private String remote = "";
	private String useragent = "";
	private String referrer = "";
	private String keywords = "";
	private HashMap<String,HashMap<String,String>> websites = new HashMap<String,HashMap<String,String>>();

	private	Statement rs = null;
	private Text text = new Text();



	public Website() {
		init();
	}



	public Website(Text mytext) {
		if (mytext != null) text = mytext;
		init();
	}



	public void init() {
		id = "";
		domain = "";
		default_page = "";
		default_version = "";
		default_country = "";
		default_state = "";
		default_price = "";
		default_doctype = "";
		default_template = "";
		default_stylesheet = "";
		default_page_nonexisting = "";
		default_page_unpublished = "";
		default_page_expired = "";
		default_login = "";
		default_searchresults_page = "";
		default_searchresults_entry = "";
		default_list_entry = "";
		default_publish_ready = "";
		default_register_confirmation_page = "";
		default_register_notification_page = "";
		default_personal_admin_page = "";
		retrieve_password_page = "";
		retrieve_password_confirmation = "";
		retrieve_password_email = "";
		retrieve_password_error = "";
		language = "";
		remote = "";
		useragent = "";
		referrer = "";
		keywords = "";
	}



	public boolean exists(Request myrequest, DB db, String domain, String language) {
		if (db == null) return false;
		String SQL = "";
		if (websites.containsKey(domain)) {
			return ((String) ((HashMap<String,String>) websites.get(domain)).get("domain")).equals(domain);
		} else if (! domain.equals("")) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("domain", "");
			mydata.put("default_page", "");
			mydata.put("default_version", "");
			mydata.put("default_country", "");
			mydata.put("default_state", "");
			mydata.put("default_price", "");
			mydata.put("default_doctype", "");
			mydata.put("default_template", "");
			mydata.put("default_stylesheet", "");
			mydata.put("default_page_nonexisting", "");
			mydata.put("default_page_unpublished", "");
			mydata.put("default_page_expired", "");
			mydata.put("default_login", "");
			mydata.put("default_searchresults_page", "");
			mydata.put("default_searchresults_entry", "");
			mydata.put("default_list_entry", "");
			mydata.put("default_publish_ready", "");
			mydata.put("default_register_confirmation_page", "");
			mydata.put("default_register_notification_page", "");
			mydata.put("default_personal_admin_page", "");
			mydata.put("retrieve_password_page", "");
			mydata.put("retrieve_password_confirmation", "");
			mydata.put("retrieve_password_email", "");
			mydata.put("retrieve_password_error", "");
			mydata.put("language", "");
			mydata.put("remote", "");
			mydata.put("useragent", "");
			mydata.put("referrer", "");
			mydata.put("keywords", "");
			websites.put(domain, mydata);
//			SQL = "select * from websites " + Common.SQLwhere_equals_or("", "domain", Common.SQL_clean(domain), "domain", "") + " order by remote,language," + Common.SQLlength(db,"useragent") + ",useragent," + Common.SQLlength(db,"referrer") + ",referrer," + Common.SQLlength(db,"keywords") + ",keywords";
			SQL = "select * from websites " + Common.SQLwhere_equals_blank_or_partial(db, "", "domain", Common.SQL_clean(domain), ".") + " order by remote,language," + Common.SQLlength(db,"domain") + ",domain," + Common.SQLlength(db,"useragent") + ",useragent," + Common.SQLlength(db,"referrer") + ",referrer," + Common.SQLlength(db,"keywords") + ",keywords";
@SuppressWarnings("unchecked")
			LinkedHashMap<String,HashMap<String,String>> rows = (LinkedHashMap<String,HashMap<String,String>>)Cache.get(db, "websites", "domain:" + Common.SQL_clean(domain) + ":" + 0);
			if (rows == null) {
				rows = db.query_records(SQL);
				if (rows == null) rows = new LinkedHashMap<String,HashMap<String,String>>();
				if (rows != null) Cache.set(db, "websites", "domain:" + Common.SQL_clean(domain) + ":" + 0, rows);
			}
			if (rows.size()>0) {
				boolean returnvalue = false;
				int languageindex = language.length() + 1;
				if (matchUserAgent(rows.get("0").get("useragent"), myrequest) && matchRemote(rows.get("0").get("remote"), myrequest) && matchLanguage(rows.get("0").get("language"), language, languageindex) && matchReferrer(rows.get("0").get("referrer"), myrequest) && matchKeywords(rows.get("0").get("keywords"), myrequest)) {
					if (! rows.get("0").get("language").equals("")) {
						languageindex = language.indexOf(rows.get("0").get("language"));
					}
					mydata = rows.get("0");
					mydata.put("domain", domain);
					websites.put(domain, mydata);
					returnvalue = true;
				}
				for (int i=1; i<rows.size(); i++) {
//					if (! websites.containsKey(domain)) {
						if (matchUserAgent(rows.get(""+i).get("useragent"), myrequest) && matchRemote(rows.get(""+i).get("remote"), myrequest) && matchLanguage(rows.get(""+i).get("language"), language, languageindex) && matchReferrer(rows.get(""+i).get("referrer"), myrequest) && matchKeywords(rows.get(""+i).get("keywords"), myrequest)) {
							if (! rows.get(""+i).get("language").equals("")) {
								languageindex = language.indexOf(rows.get(""+i).get("language"));
							}
							mydata = rows.get(""+i);
							mydata.put("domain", domain);
							websites.put(domain, mydata);
							returnvalue = true;
						}
//					}
				}
				return returnvalue;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}



	public String get(Request myrequest, DB db, String domain, String language, String attribute) {
		if (db == null) return "";
		String SQL = "";
		if (websites.containsKey(domain)) {
			getRecord(db, (HashMap<String,String>) websites.get(domain));
			return (String) ((HashMap<String,String>) websites.get(domain)).get(attribute);
		} else if ((db != null) && (! domain.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("domain", "");
			mydata.put("default_page", "");
			mydata.put("default_version", "");
			mydata.put("default_country", "");
			mydata.put("default_state", "");
			mydata.put("default_price", "");
			mydata.put("default_doctype", "");
			mydata.put("default_template", "");
			mydata.put("default_stylesheet", "");
			mydata.put("default_page_nonexisting", "");
			mydata.put("default_page_unpublished", "");
			mydata.put("default_page_expired", "");
			mydata.put("default_login", "");
			mydata.put("default_searchresults_page", "");
			mydata.put("default_searchresults_entry", "");
			mydata.put("default_list_entry", "");
			mydata.put("default_publish_ready", "");
			mydata.put("default_register_confirmation_page", "");
			mydata.put("default_register_notification_page", "");
			mydata.put("default_personal_admin_page", "");
			mydata.put("retrieve_password_page", "");
			mydata.put("retrieve_password_confirmation", "");
			mydata.put("retrieve_password_email", "");
			mydata.put("retrieve_password_error", "");
			mydata.put("language", "");
			mydata.put("remote", "");
			mydata.put("useragent", "");
			mydata.put("referrer", "");
			mydata.put("keywords", "");
			websites.put(domain, mydata);
//			SQL = "select * from websites " + Common.SQLwhere_equals_or("", "domain", Common.SQL_clean(domain), "domain", "") + " order by remote,language," + Common.SQLlength(db,"useragent") + ",useragent," + Common.SQLlength(db,"referrer") + ",referrer," + Common.SQLlength(db,"keywords") + ",keywords";
			SQL = "select * from websites " + Common.SQLwhere_equals_blank_or_partial(db, "", "domain", Common.SQL_clean(domain), ".") + " order by remote,language," + Common.SQLlength(db,"domain") + ",domain," + Common.SQLlength(db,"useragent") + ",useragent," + Common.SQLlength(db,"referrer") + ",referrer," + Common.SQLlength(db,"keywords") + ",keywords";
@SuppressWarnings("unchecked")
			LinkedHashMap<String,HashMap<String,String>> rows = (LinkedHashMap<String,HashMap<String,String>>)Cache.get(db, "websites", "domain:" + Common.SQL_clean(domain) + ":" + 0);
			if (rows == null) {
				rows = db.query_records(SQL);
				if (rows == null) rows = new LinkedHashMap<String,HashMap<String,String>>();
				if (rows != null) Cache.set(db, "websites", "domain:" + Common.SQL_clean(domain) + ":" + 0, rows);
			}
			if (rows.size()>0) {
				int languageindex = language.length() + 1;
				if (matchUserAgent(rows.get("0").get("useragent"), myrequest) && matchRemote(rows.get("0").get("remote"), myrequest) && matchLanguage(rows.get("0").get("language"), language, languageindex) && matchReferrer(rows.get("0").get("referrer"), myrequest) && matchKeywords(rows.get("0").get("keywords"), myrequest)) {
					if (! rows.get("0").get("language").equals("")) {
						languageindex = language.indexOf(rows.get("0").get("language"));
					}
					mydata = rows.get("0");
					mydata.put("domain", domain);
					websites.put(domain, mydata);
				}
				for (int i=1; i<rows.size(); i++) {
//					if (! websites.containsKey(domain)) {
						if (matchUserAgent(rows.get(""+i).get("useragent"), myrequest) && matchRemote(rows.get(""+i).get("remote"), myrequest) && matchLanguage(rows.get(""+i).get("language"), language, languageindex) && matchReferrer(rows.get(""+i).get("referrer"), myrequest) && matchKeywords(rows.get(""+i).get("keywords"), myrequest)) {
							if (! rows.get(""+i).get("language").equals("")) {
								languageindex = language.indexOf(rows.get(""+i).get("language"));
							}
							mydata = rows.get(""+i);
							mydata.put("domain", domain);
							websites.put(domain, mydata);
						}
//					}
				}
				if (websites.containsKey(domain)) {
					getRecord(db, (HashMap<String,String>) websites.get(domain));
					return (String) ((HashMap<String,String>) websites.get(domain)).get(attribute);
				} else {
					return "";
				}
			} else {
				return "";
			}
		} else {
			return "";
		}
	}



	public boolean matchLanguage(String row_language, String language, int languageindex) {
		boolean language_match = false;
		if (row_language.equals("")) {
			language_match = true;
		} else if ((language.indexOf(row_language) >= 0) && (language.indexOf(row_language) <= languageindex)) {
			language_match = true;
		}
		return language_match;
	}



	public boolean matchUserAgent(String row_useragent, Request myrequest) {
		boolean useragent_match = false;
		if (row_useragent.equals("")) {
			useragent_match = true;
		} else if (myrequest.getUserAgent().indexOf(row_useragent) >= 0) {
			useragent_match = true;
		}
		return useragent_match;
	}



	public boolean matchRemote(String row_remote, Request myrequest) {
		boolean remote_match = false;
		if (row_remote.equals("")) {
			remote_match = true;
		} else if (row_remote.matches("^([0-9.]+)-([0-9.]+)$")) {
			String[] matches = row_remote.split("-");
			if ((matches[0].compareTo(myrequest.getRemoteAddr())<=0) && (matches[1].compareTo(myrequest.getRemoteAddr())>=0)) {
				remote_match = true;
			}
		} else if (row_remote.matches("^([0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+)$")) {
			if (row_remote.equals(myrequest.getRemoteAddr())) {
				remote_match = true;
			}
		} else if (row_remote.matches("^([0-9.]+)$")) {
			if (myrequest.getRemoteAddr().startsWith(row_remote)) {
				remote_match = true;
			}
		} else {
			if (myrequest.getRemoteHost().endsWith(row_remote)) {
				remote_match = true;
			}
		}
		return remote_match;
	}



	public boolean matchReferrer(String row_referrer, Request myrequest) {
		boolean referrer_match = false;
		String referrer = URLDecoder.decode(myrequest.getHeader("referer").replaceAll("\\+", " ")).toLowerCase();
		if (referrer.indexOf("\\?")>=0) {
			referrer = referrer.substring(0,referrer.indexOf("\\?"));
		}
		if (row_referrer.equals("")) {
			referrer_match = true;
		} else if (referrer.indexOf(row_referrer.toLowerCase().trim())>=0) {
			referrer_match = true;
		}
		return referrer_match;
	}



	public boolean matchKeywords(String row_keywords, Request myrequest) {
		boolean keywords_match = true;
		String referrer = URLDecoder.decode(myrequest.getHeader("referer").replaceAll("\\+", " ")).toLowerCase();
		if (referrer.indexOf("\\?")>=0) {
			referrer = referrer.substring(referrer.indexOf("\\?"));
		}
		if (row_keywords.equals("")) {
			keywords_match = true;
		} else {
			String[] mykeywords = row_keywords.split(",");
			for (int i=0; i<mykeywords.length ; i++) {
				if (referrer.indexOf(mykeywords[i].toLowerCase().trim())<0) {
					keywords_match = false;
					break;
				}
			}
		}
		return keywords_match;
	}



	public void setTemp(String domain, String attribute, String value) {
		if (! websites.containsKey(domain)) websites.put(domain, new HashMap<String,String>());
		((HashMap<String,String>) websites.get(domain)).put(attribute, value);
	}



	public void read(DB db, String id) {
		if (db == null) return;
		if (! id.equals("")) {
			String SQL = "select * from websites where id=" + Common.integer(id);
			HashMap row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void readByDomain(DB db, String domain) {
		if (db == null) return;
		if (! domain.equals("")) {
			String SQL = "select * from websites where domain='" + Common.SQL_clean(domain) + "' order by remote,language";
			HashMap row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void getRecord(DB db, HashMap record) {
		id = "" + record.get("id");
		domain = "" + record.get("domain");
		default_page = "" + record.get("default_page");
		default_version = "" + record.get("default_version");
		default_country = "" + record.get("default_country");
		default_state = "" + record.get("default_state");
		default_price = "" + record.get("default_price");
		default_doctype = "" + record.get("default_doctype");
		default_template = "" + record.get("default_template");
		default_stylesheet = "" + record.get("default_stylesheet");
		default_page_nonexisting = "" + record.get("default_page_nonexisting");
		default_page_unpublished = "" + record.get("default_page_unpublished");
		default_page_expired = "" + record.get("default_page_expired");
		default_login = "" + record.get("default_login");
		default_searchresults_page = "" + record.get("default_searchresults_page");
		default_searchresults_entry = "" + record.get("default_searchresults_entry");
		default_list_entry = "" + record.get("default_list_entry");
		default_publish_ready = "" + record.get("default_publish_ready");
		default_register_confirmation_page = "" + record.get("default_register_confirm_page");
		default_register_notification_page = "" + record.get("default_register_notify_page");
		default_personal_admin_page = "" + record.get("default_personal_admin_page");
		retrieve_password_page = "" + record.get("retrieve_password_page");
		retrieve_password_confirmation = "" + record.get("retrieve_password_confirmation");
		retrieve_password_email = "" + record.get("retrieve_password_email");
		retrieve_password_error = "" + record.get("retrieve_password_error");
		language = "" + record.get("language");
		remote = "" + record.get("remote");
		useragent = "" + record.get("useragent");
		referrer = "" + record.get("referrer");
		keywords = "" + record.get("keywords");
	}



	public void getForm(Request request) {
		domain = request.getParameter("domain");
		default_page = request.getParameter("default_page");
		default_version = request.getParameter("default_version");
		default_country = request.getParameter("default_country");
		default_state = request.getParameter("default_state");
		default_price = request.getParameter("default_price");
		default_doctype = request.getParameter("default_doctype");
		default_template = request.getParameter("default_template");
		default_stylesheet = request.getParameter("default_stylesheet");
		default_page_nonexisting = request.getParameter("default_page_nonexisting");
		default_page_unpublished = request.getParameter("default_page_unpublished");
		default_page_expired = request.getParameter("default_page_expired");
		default_login = request.getParameter("default_login");
		default_searchresults_page = request.getParameter("default_searchresults_page");
		default_searchresults_entry = request.getParameter("default_searchresults_entry");
		default_list_entry = request.getParameter("default_list_entry");
		default_publish_ready = request.getParameter("default_publish_ready");
		default_register_confirmation_page = request.getParameter("default_register_confirmation_page");
		default_register_notification_page = request.getParameter("default_register_notification_page");
		default_personal_admin_page = request.getParameter("default_personal_admin_page");
		retrieve_password_page = request.getParameter("retrieve_password_page");
		retrieve_password_confirmation = request.getParameter("retrieve_password_confirmation");
		retrieve_password_email = request.getParameter("retrieve_password_email");
		retrieve_password_error = request.getParameter("retrieve_password_error");
		language = request.getParameter("language");
		remote = request.getParameter("remote");
		useragent = request.getParameter("useragent");
		referrer = request.getParameter("referrer");
		keywords = request.getParameter("keywords");
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("domain", "" + domain);
		mydata.put("default_page", "" + default_page);
		mydata.put("default_version", "" + default_version);
		mydata.put("default_country", "" + default_country);
		mydata.put("default_state", "" + default_state);
		mydata.put("default_price", "" + default_price);
		mydata.put("default_doctype", "" + default_doctype);
		mydata.put("default_template", "" + default_template);
		mydata.put("default_stylesheet", "" + default_stylesheet);
		mydata.put("default_page_nonexisting", "" + default_page_nonexisting);
		mydata.put("default_page_unpublished", "" + default_page_unpublished);
		mydata.put("default_page_expired", "" + default_page_expired);
		mydata.put("default_login", "" + default_login);
		mydata.put("default_searchresults_page", "" + default_searchresults_page);
		mydata.put("default_searchresults_entry", "" + default_searchresults_entry);
		mydata.put("default_list_entry", "" + default_list_entry);
		mydata.put("default_publish_ready", "" + default_publish_ready);
		mydata.put("default_register_confirm_page", "" + default_register_confirmation_page);
		mydata.put("default_register_notify_page", "" + default_register_notification_page);
		mydata.put("default_personal_admin_page", "" + default_personal_admin_page);
		mydata.put("retrieve_password_page", "" + retrieve_password_page);
		mydata.put("retrieve_password_confirmation", "" + retrieve_password_confirmation);
		mydata.put("retrieve_password_email", "" + retrieve_password_email);
		mydata.put("retrieve_password_error", "" + retrieve_password_error);
		mydata.put("language", "" + language);
		mydata.put("remote", "" + remote);
		mydata.put("useragent", "" + useragent);
		mydata.put("referrer", "" + referrer);
		mydata.put("keywords", "" + keywords);
		db.insert("websites", mydata);
		Cache.clear(db, "websites");
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("domain", "" + domain);
			mydata.put("default_page", "" + default_page);
			mydata.put("default_version", "" + default_version);
			mydata.put("default_country", "" + default_country);
			mydata.put("default_state", "" + default_state);
			mydata.put("default_price", "" + default_price);
			mydata.put("default_doctype", "" + default_doctype);
			mydata.put("default_template", "" + default_template);
			mydata.put("default_stylesheet", "" + default_stylesheet);
			mydata.put("default_page_nonexisting", "" + default_page_nonexisting);
			mydata.put("default_page_unpublished", "" + default_page_unpublished);
			mydata.put("default_page_expired", "" + default_page_expired);
			mydata.put("default_login", "" + default_login);
			mydata.put("default_searchresults_page", "" + default_searchresults_page);
			mydata.put("default_searchresults_entry", "" + default_searchresults_entry);
			mydata.put("default_list_entry", "" + default_list_entry);
			mydata.put("default_publish_ready", "" + default_publish_ready);
			mydata.put("default_register_confirm_page", "" + default_register_confirmation_page);
			mydata.put("default_register_notify_page", "" + default_register_notification_page);
			mydata.put("default_personal_admin_page", "" + default_personal_admin_page);
			mydata.put("retrieve_password_page", "" + retrieve_password_page);
			mydata.put("retrieve_password_confirmation", "" + retrieve_password_confirmation);
			mydata.put("retrieve_password_email", "" + retrieve_password_email);
			mydata.put("retrieve_password_error", "" + retrieve_password_error);
			mydata.put("language", "" + language);
			mydata.put("remote", "" + remote);
			mydata.put("useragent", "" + useragent);
			mydata.put("referrer", "" + referrer);
			mydata.put("keywords", "" + keywords);
			db.update("websites", "id", id, mydata);
			Cache.clear(db, "websites");
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete("websites", "id", id);
			Cache.clear(db, "websites");
		}
	}



	public boolean records(DB db, String SQL) {
		if ((db == null) || db.isClosed()) return false;
		if ((SQL != null) && (! SQL.equals(""))) {
			rs = db.records(SQL);
			return true;
		} else {
			HashMap row = db.records(rs);
			if (row != null) {
				getRecord(db, row);
				return true;
			} else {
				init();
				return false;
			}
		}
	}



	public String getDefaultPageTitle(DB db, Configuration config) {
		Content mypage = new Content(text);
		mypage.read(db, config, default_page);
		return mypage.getTitle();
	}



	public String getDefaultPageNonexistingTitle(DB db, Configuration config) {
		Content mypage = new Content(text);
		mypage.read(db, config, default_page_nonexisting);
		return mypage.getTitle();
	}



	public String getDefaultPageUnpublishedTitle(DB db, Configuration config) {
		Content mypage = new Content(text);
		mypage.read(db, config, default_page_unpublished);
		return mypage.getTitle();
	}



	public String getDefaultPageExpiredTitle(DB db, Configuration config) {
		Content mypage = new Content(text);
		mypage.read(db, config, default_page_expired);
		return mypage.getTitle();
	}



	public String getDefaultLoginTitle(DB db, Configuration config) {
		Content mypage = new Content(text);
		mypage.read(db, config, default_login);
		return mypage.getTitle();
	}



	public String getDefaultSearchresultsPageTitle(DB db, Configuration config) {
		Content mypage = new Content(text);
		mypage.read(db, config, default_searchresults_page);
		return mypage.getTitle();
	}



	public String getDefaultSearchresultsEntryTitle(DB db, Configuration config) {
		Content mypage = new Content(text);
		mypage.read(db, config, default_searchresults_entry);
		return mypage.getTitle();
	}



	public String getDefaultRegisterConfirmationPageTitle(DB db, Configuration config) {
		Content mypage = new Content(text);
		mypage.read(db, config, default_register_confirmation_page);
		return mypage.getTitle();
	}



	public String getDefaultRegisterNotificationPageTitle(DB db, Configuration config) {
		Content mypage = new Content(text);
		mypage.read(db, config, default_register_notification_page);
		return mypage.getTitle();
	}



	public String getDefaultRetrievePasswordPageTitle(DB db, Configuration config) {
		Content mypage = new Content(text);
		mypage.read(db, config, retrieve_password_page);
		return mypage.getTitle();
	}



	public String getDefaultPasswordConfirmationTitle(DB db, Configuration config) {
		Content mypage = new Content(text);
		mypage.read(db, config, retrieve_password_confirmation);
		return mypage.getTitle();
	}



	public String getDefaultRetrievePasswordEmailTitle(DB db, Configuration config) {
		Content mypage = new Content(text);
		mypage.read(db, config, retrieve_password_email);
		return mypage.getTitle();
	}



	public String getDefaultRetrievePasswordErrorTitle(DB db, Configuration config) {
		Content mypage = new Content(text);
		mypage.read(db, config, retrieve_password_error);
		return mypage.getTitle();
	}



	public String getDefaultTemplateTitle(DB db, Configuration config) {
		Content mypage = new Content(text);
		mypage.read(db, config, default_template);
		return mypage.getTitle();
	}



	public String getDefaultStylesheetTitle(DB db, Configuration config) {
		Content mypage = new Content(text);
		mypage.read(db, config, default_stylesheet);
		return mypage.getTitle();
	}



	public String page_select_options(DB db, String selected) {
		Page page = new Page(text);
		return page.select_options(db, "page", selected);
	}



	public String template_select_options(DB db, String selected) {
		Page template = new Page(text);
		return template.select_options(db, "template", selected);
	}



	public String stylesheet_select_options(DB db, String selected) {
		Page stylesheet = new Page(text);
		return stylesheet.select_options(db, "stylesheet", selected);
	}



	public String version_select_options(DB db, String selected) {
		Version version = new Version();
		return version.select_options(db, selected);
	}



	public String getId() {
		return id;
	}
	public void setId(String value) {
		id = value;
	}



	public String getDomain() {
		return domain;
	}
	public void setDomain(String value) {
		domain = value;
	}



	public String getDefaultPage() {
		return default_page;
	}
	public void setDefaultPage(String value) {
		default_page = value;
	}



	public String getDefaultVersion() {
		return default_version;
	}
	public void setDefaultVersion(String value) {
		default_version = value;
	}



	public String getDefaultCountry() {
		return default_country;
	}
	public void setDefaultCountry(String value) {
		default_country = value;
	}



	public String getDefaultState() {
		return default_state;
	}
	public void setDefaultState(String value) {
		default_state = value;
	}



	public String getDefaultPrice() {
		return default_price;
	}
	public void setDefaultPrice(String value) {
		default_price = value;
	}



	public String getDefaultDocType() {
		return default_doctype;
	}
	public void setDefaultDocType(String value) {
		default_doctype = value;
	}



	public String getDefaultTemplate() {
		return default_template;
	}
	public void setDefaultTemplate(String value) {
		default_template = value;
	}



	public String getDefaultStylesheet() {
		return default_stylesheet;
	}
	public void setDefaultStylesheet(String value) {
		default_stylesheet = value;
	}



	public String getDefaultPageNonexisting() {
		return default_page_nonexisting;
	}
	public void setDefaultPageNonexisting(String value) {
		default_page_nonexisting = value;
	}



	public String getDefaultPageUnpublished() {
		return default_page_unpublished;
	}
	public void setDefaultPageUnpublished(String value) {
		default_page_unpublished = value;
	}



	public String getDefaultPageExpired() {
		return default_page_expired;
	}
	public void setDefaultPageExpired(String value) {
		default_page_expired = value;
	}



	public String getDefaultLogin() {
		return default_login;
	}
	public void setDefaultLogin(String value) {
		default_login = value;
	}



	public String getDefaultSearchresultsPage() {
		return default_searchresults_page;
	}
	public void setDefaultSearchresultsPage(String value) {
		default_searchresults_page = value;
	}



	public String getDefaultSearchresultsEntry() {
		return default_searchresults_entry;
	}
	public void setDefaultSearchresultsEntry(String value) {
		default_searchresults_entry = value;
	}



	public String getDefaultListEntry() {
		return default_list_entry;
	}
	public void setDefaultListEntry(String value) {
		default_list_entry = value;
	}



	public String getDefaultPublishReady() {
		return default_publish_ready;
	}
	public void setDefaultPublishReady(String value) {
		default_publish_ready = value;
	}



	public String getDefaultRegisterConfirmationPage() {
		return default_register_confirmation_page;
	}
	public void setDefaultRegisterConfirmationPage(String value) {
		default_register_confirmation_page = value;
	}



	public String getDefaultRegisterNotificationPage() {
		return default_register_notification_page;
	}
	public void setDefaultRegisterNotificationPage(String value) {
		default_register_notification_page = value;
	}



	public String getDefaultPersonalAdminPage() {
		return default_personal_admin_page;
	}
	public void setDefaultPersonalAdminPage(String value) {
		default_personal_admin_page = value;
	}



	public String getDefaultRetrievePasswordPage() {
		return retrieve_password_page;
	}
	public void setDefaultRetrievePasswordPage(String value) {
		retrieve_password_page = value;
	}



	public String getDefaultPasswordConfirmation() {
		return retrieve_password_confirmation;
	}
	public void setDefaultPasswordConfirmation(String value) {
		retrieve_password_confirmation = value;
	}



	public String getDefaultRetrievePasswordEmail() {
		return retrieve_password_email;
	}
	public void setDefaultRetrievePasswordEmail(String value) {
		retrieve_password_email = value;
	}



	public String getDefaultRetrievePasswordError() {
		return retrieve_password_error;
	}
	public void setDefaultRetrievePasswordError(String value) {
		retrieve_password_error = value;
	}



	public String getLanguage() {
		return language;
	}
	public void setLanguage(String value) {
		language = value;
	}



	public String getRemote() {
		return remote;
	}
	public void setRemote(String value) {
		remote = value;
	}



	public String getUserAgent() {
		return useragent;
	}
	public void setUserAgent(String value) {
		useragent = value;
	}



	public String getReferrer() {
		return referrer;
	}
	public void setReferrer(String value) {
		referrer = value;
	}



	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String value) {
		keywords = value;
	}



}
