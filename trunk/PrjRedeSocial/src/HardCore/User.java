package HardCore;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.*;
import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.*;

public class User {
	public static boolean DEBUG = false;
	public static boolean DIGEST = false;
	private String id = "";
	private int locked = 0;
	private int failed = 0;
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
	private String gender = "";
	private String title = "";
	private String name = "";
	private String organisation = "";
	private String username = "";
	private String password = "";
	private String email = "";
	private String usertype = "";
	private String usergroup = "";
	private HashMap<String,String> additional_usertypes = new HashMap<String,String>();
	private HashMap<String,String> additional_usergroups = new HashMap<String,String>();
	private String userclass = "";
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
	private String content_editor = "";
	private String hardcore_encoding = ""; // TODO
	private String hardcore_language = "";
	private String hardcore_upload = "";
	private String hardcore_format = "";
	private String hardcore_width = "";
	private String hardcore_height = "";
	private String hardcore_onenter = "";
	private String hardcore_onctrlenter = "";
	private String hardcore_onshiftenter = "";
	private String hardcore_onaltenter = "";
	private String hardcore_toolbar1 = "";
	private String hardcore_toolbar2 = "";
	private String hardcore_toolbar3 = "";
	private String hardcore_toolbar4 = "";
	private String hardcore_toolbar5 = "";
	private String hardcore_formatblock = "";
	private String hardcore_fontname = "";
	private String hardcore_fontsize = "";
	private String hardcore_customscript = "";
	private String index_workspace = "";
	private String index_content = "";
	private String index_library = "";
	private String index_product = "";
	private String index_stock = "";
	private String index_order = "";
	private String index_segments = "";
	private String index_usertests = "";
	private String index_heatmaps = "";
	private String index_sales = "";
	private String index_databases = "";
	private String index_user = "";
	private String index_hosting = "";
	private String index_websites = "";
	private String menu_selection = "";
	private String workspace_sections = "";
	private String statistics_reports = "";
	private String sales_reports = "";
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
	private String card_cvc = "";
	private String card_issue = "";
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
	private boolean user = false;
	private boolean editor = false;
	private boolean creator = false;
	private boolean publisher = false;
	private boolean administrator = false;

	private	Statement rs = null;

	private SearchResult directoryentry = null;
	private Attribute directoryentry_usergroups = null;
	private Attribute directoryentry_usertypes = null;
	private HashMap<String,String> directoryentry_permission = null;
	private HashMap<String,String> usergroups_cache = null;
	private HashMap<String,String> usertypes_cache = null;
	private Text text = new Text();



	public User() {
		init();
	}



	public User(Text mytext) {
		if (mytext != null) text = mytext;
		init();
	}



	public void init() {
		id = "";
		locked = 0;
		failed = 0;
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
		gender = "";
		title = "";
		name = "";
		organisation = "";
		username = "";
		password = "";
		email = "";
		usertype = "";
		usergroup = "";
		additional_usertypes = new HashMap<String,String>();
		additional_usergroups = new HashMap<String,String>();
		userclass = "";
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
		content_editor = "";
		hardcore_encoding = "";
		hardcore_language = "";
		hardcore_upload = "";
		hardcore_format = "";
		hardcore_width = "";
		hardcore_height = "";
		hardcore_onenter = "";
		hardcore_onctrlenter = "";
		hardcore_onshiftenter = "";
		hardcore_onaltenter = "";
		hardcore_toolbar1 = "";
		hardcore_toolbar2 = "";
		hardcore_toolbar3 = "";
		hardcore_toolbar4 = "";
		hardcore_toolbar5 = "";
		hardcore_formatblock = "";
		hardcore_fontname = "";
		hardcore_fontsize = "";
		hardcore_customscript = "";
		index_workspace = "";
		index_content = "";
		index_library = "";
		index_product = "";
		index_stock = "";
		index_order = "";
		index_segments = "";
		index_usertests = "";
		index_heatmaps = "";
		index_sales = "";
		index_databases = "";
		index_user = "";
		index_hosting = "";
		index_websites = "";
		menu_selection = "";
		workspace_sections = "";
		statistics_reports = "";
		sales_reports = "";
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
		card_cvc = "";
		card_issue = "";
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
		user = false;
		editor = false;
		creator = false;
		publisher = false;
		administrator = false;
		getAccessRestrictions("", "", "", "", "", null, null);
		rs = null;
		directoryentry = null;
		directoryentry_usergroups = null;
		directoryentry_usertypes = null;
		directoryentry_permission = null;
		usergroups_cache = null;
		usertypes_cache = null;
	}



	public void read(DB db, String id) {
		read("", "", "", "", "", db, null, id);
	}
	public void read(String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String id) {
		if (db == null) return;
		if (! id.equals("")) {
			String SQL = "select * from users where id=" + Common.integer(id);
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
			additional_usertypes = additionalusertypes(db, null);
			additional_usergroups = additionalusergroups(db, null);
		} else {
			init();
		}
		getAccessRestrictions(session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config);
	}



	public boolean readByEmail(String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String email) {
		if (db == null) return false;
		boolean result = false;
		if (! email.equals("")) {
			String SQL = "select * from users where email=" + db.quote(email);
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
				result = true;
			} else {
				init();
			}
			additional_usertypes = additionalusertypes(db, null);
			additional_usergroups = additionalusergroups(db, null);
		} else {
			init();
		}
		getAccessRestrictions(session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config);
		return result;
	}



	public boolean readByUsername(String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String username) {
		if (DEBUG) System.out.println("DEBUG:HardCore.User.readByUsername:" + username);
		if (db == null) return false;
		boolean result = false;
		if ((config.get(db, "use_userdatabase").equals("ldap")) || (config.get(db, "use_userdirectory").equals("ldap"))) {
@SuppressWarnings("unchecked")
			HashMap<String,String> row = (HashMap<String,String>)Cache.get(db, "users", "username" + ":" + Common.SQL_clean(username));
			if (row == null) {
				if (DEBUG) System.out.println("DEBUG:HardCore.User.readByUsername:ldap:" + username);
				DirContext ldapconn = ldap_bind(config.get(db, "directory_url"), config.get(db, "directory_root"), config.get(db, "directory_dn"), config.get(db, "directory_password"), config.get(db, "directory_parameters"));
				SearchResult ldapentry = readDirectoryByUsername(ldapconn, config.get(db, "directory_root"), config.get(db, "directory_username"), username);
				if (ldapentry != null) {
					row = getDirectoryRecord(db, config, ldapentry, username, "");
					if (row != null) {
						Cache.set(db, "users", "username" + ":" + Common.SQL_clean(username), row);
						Cache.set(db, "users:usergroups", "username" + ":" + Common.SQL_clean(username), usergroups_cache);
						Cache.set(db, "users:usertypes", "username" + ":" + Common.SQL_clean(username), usertypes_cache);
					}
				}
				ldap_close(ldapconn);
				ldapconn = null;
			} else {
				if (DEBUG) System.out.println("DEBUG:HardCore.User.readByUsername:ldap:cached:" + username);
			}
			if (row != null) {
				getRecord(db, row);
@SuppressWarnings("unchecked")
				HashMap<String,String> myusergroups_cache = (HashMap<String,String>)Cache.get(db, "users:usergroups", "username" + ":" + Common.SQL_clean(username));
@SuppressWarnings("unchecked")
				HashMap<String,String> myusertypes_cache = (HashMap<String,String>)Cache.get(db, "users:usertypes", "username" + ":" + Common.SQL_clean(username));
				usergroups_cache = myusergroups_cache;
				usertypes_cache = myusertypes_cache;
				result = true;
			}
		} else if (! username.equals("")) {
			String SQL = "select * from users where username=" + db.quote(Common.SQL_clean(username));
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
				result = true;
			} else {
				init();
			}
			additional_usertypes = additionalusertypes(db, null);
			additional_usergroups = additionalusergroups(db, null);
		} else {
			init();
		}
		getAccessRestrictions(session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config);
		return result;
	}



	public boolean readByUsernameAndPassword(String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String username, String password) {
		if (DEBUG) System.out.println("DEBUG:HardCore.User.readByUsernameAndPassword:" + username);
		if (db == null) return false;
		boolean result = false;
		if ((config.get(db, "use_userdatabase").equals("ldap")) || (config.get(db, "use_userdirectory").equals("ldap"))) {
@SuppressWarnings("unchecked")
			HashMap<String,String> row = (HashMap<String,String>)Cache.get(db, "users", "username" + ":" + Common.SQL_clean(username) + ":" + Common.SQL_clean(password));
			if (row == null) {
				if (DEBUG) System.out.println("DEBUG:HardCore.User.readByUsernameAndPassword:ldap:" + username);
				DirContext ldapconn = ldap_bind(config.get(db, "directory_url"), config.get(db, "directory_root"), config.get(db, "directory_dn"), config.get(db, "directory_password"), config.get(db, "directory_parameters"));
				SearchResult ldapentry = readDirectoryByUsername(ldapconn, config.get(db, "directory_root"), config.get(db, "directory_username"), username);
				if (ldapentry != null) {
					DirContext ldapconn2 = ldap_bind(config.get(db, "directory_url"), config.get(db, "directory_root"), ldapentry.getName(), password, config.get(db, "directory_parameters"));
					if (ldapconn2 != null) {
						row = getDirectoryRecord(db, config, ldapentry, username, password);
						if (row != null) {
							Cache.set(db, "users", "username" + ":" + Common.SQL_clean(username) + ":" + Common.SQL_clean(password), row);
							Cache.set(db, "users:usergroups", "username" + ":" + Common.SQL_clean(username) + ":" + Common.SQL_clean(password), usergroups_cache);
							Cache.set(db, "users:usertypes", "username" + ":" + Common.SQL_clean(username) + ":" + Common.SQL_clean(password), usertypes_cache);
						}
					}
					ldap_close(ldapconn2);
					ldapconn2 = null;
				}
				ldap_close(ldapconn);
				ldapconn = null;
			} else {
				if (DEBUG) System.out.println("DEBUG:HardCore.User.readByUsernameAndPassword:ldap:cached:" + username);
			}
			if (row != null) {
				getRecord(db, row);
@SuppressWarnings("unchecked")
				HashMap<String,String> myusergroups_cache = (HashMap<String,String>)Cache.get(db, "users:usergroups", "username" + ":" + Common.SQL_clean(username) + ":" + Common.SQL_clean(password));
@SuppressWarnings("unchecked")
				HashMap<String,String> myusertypes_cache = (HashMap<String,String>)Cache.get(db, "users:usertypes", "username" + ":" + Common.SQL_clean(username) + ":" + Common.SQL_clean(password));
				usergroups_cache = myusergroups_cache;
				usertypes_cache = myusertypes_cache;
				result = true;
			}
		} else if (! username.equals("")) {
			String mypassword = "";
			if (DIGEST) {
				mypassword = "" + db.digest(password, 250);
			} else {
				mypassword = "" + db.encrypt(Common.SQL_clean(password));
			}
			String SQL = "select * from users where username=" + db.quote(Common.SQL_clean(username)) + " and password=" + db.quote(Common.SQL_clean(mypassword));
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
				result = true;
			} else {
				init();
			}
			additional_usertypes = additionalusertypes(db, null);
			additional_usergroups = additionalusergroups(db, null);
		} else {
			init();
		}
		getAccessRestrictions(session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config);
		return result;
	}



	public DirContext ldap_bind(String directory_url, String directory_root, String dn, String password) {
		return ldap_bind(directory_url, directory_root, dn, password, "");
	}
	public DirContext ldap_bind(String directory_url, String directory_root, String dn, String password, String directory_parameters) {
		Properties props = new Properties();
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		props.setProperty(Context.PROVIDER_URL, directory_url);
		props.setProperty(Context.URL_PKG_PREFIXES, "com.sun.jndi.url");
		props.setProperty(Context.REFERRAL, "ignore");
		props.setProperty(Context.SECURITY_AUTHENTICATION, "simple");
		String[] myparams = directory_parameters.split("[\r\n]+");
		for (int i=0; i<myparams.length; i++) {
			String myparam = myparams[i];
			Matcher myparamMatches = Pattern.compile("^([^=]+)=(.*)$").matcher(myparam);
			if (myparamMatches.find()) {
				String myname = "" + myparamMatches.group(1);
				String myvalue = "" + myparamMatches.group(2);
				props.setProperty(myname, myvalue);
			}
		}
		DirContext ctx = null;
		try {
			props.setProperty(Context.SECURITY_PRINCIPAL, dn);
			props.setProperty(Context.SECURITY_CREDENTIALS, password);
			ctx = new InitialDirContext(props);
			if (DEBUG) System.out.println("DEBUG:HardCore.User.ldap_bind:" + ctx + " - " + directory_url + " - " + directory_parameters + " - " + dn + " - " + password);
			return ctx;
		} catch (Exception e) {
			try {
				props.setProperty(Context.SECURITY_PRINCIPAL, dn + "," + directory_root);
				props.setProperty(Context.SECURITY_CREDENTIALS, password);
				ctx = new InitialDirContext(props);
				if (DEBUG) System.out.println("DEBUG:HardCore.User.ldap_bind:" + ctx + " - " + directory_url + " - " + directory_parameters + " - " + dn + "," + directory_root + " - " + password);
				return ctx;
			} catch (Exception ee) {
				System.out.println("ERROR:HardCore.User.ldap_bind:" + e + " - " + directory_url + " - " + directory_parameters + " - " + dn + " - " + password);
				System.out.println("ERROR:HardCore.User.ldap_bind:" + ee + " - " + directory_url + " - " + directory_parameters + " - " + dn + "," + directory_root + " - " + password);
			}
		}
		return ctx;
	}



	public void ldap_close(DirContext ctx) {
		if (ctx != null) {
			try {
				ctx.close();
			} catch (Exception e) {
			}
		}
	}



	public SearchResult readDirectoryByUsername(DirContext ctx, String directory_root, String directory_username, String username) {
		SearchResult sr = null;
		SearchControls constraints = new SearchControls();
		constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
		NamingEnumeration results = null;
		try {
			results = ctx.search(directory_root, "(" + directory_username + "=" + username + ")", constraints);
			if (results.hasMoreElements()) {
				sr = (SearchResult) results.next();
			}
			results.close();
		} catch (Exception e) {
			System.out.println("ERROR:HardCore.User.readDirectoryByUsername:" + e + " - " + directory_root + " - " + directory_username + " - " + username);
			sr = null;
			if (results != null) try { results.close(); } catch (Exception ee) { };
		}
		if (DEBUG) System.out.println("DEBUG:HardCore.User.readDirectoryByUsername:" + sr);
		return sr;
	}



	public HashMap<String,String> getDirectoryRecord(DB db, Configuration config, SearchResult ldapentry, String myusername, String mypassword) {
		HashMap<String,String> data = new HashMap<String,String>();
		if (ldapentry != null) {
			directoryentry = ldapentry;
			Attributes attributes = directoryentry.getAttributes();
			if (! myusername.equals("")) {
				data.put("username", myusername);
				data.put("password", mypassword);
			} else {
				data.put("username", getDirectoryRecordAttribute(attributes, config.get(db, "directory_username"), ""));
				data.put("password", getDirectoryRecordAttribute(attributes, config.get(db, "directory_password"), ""));
			}

//data.put("_objectClass", getDirectoryRecordAttributes(attributes, "objectClass", ""));
//data.put("_userclass", getDirectoryRecordAttributes(attributes, config.get(db, "directory_userclass"), ""));

			data.put("birthdate", getDirectoryRecordAttribute(attributes, config.get(db, "directory_birthdate"), ""));
			data.put("birthyear", getDirectoryRecordAttribute(attributes, config.get(db, "directory_birthyear"), ""));
			data.put("birthmonth", getDirectoryRecordAttribute(attributes, config.get(db, "directory_birthmonth"), ""));
			data.put("birthday", getDirectoryRecordAttribute(attributes, config.get(db, "directory_birthday"), ""));
			data.put("gender", getDirectoryRecordAttribute(attributes, config.get(db, "directory_gender"), ""));
			data.put("title", getDirectoryRecordAttribute(attributes, config.get(db, "directory_title"), ""));
			data.put("name", getDirectoryRecordAttribute(attributes, config.get(db, "directory_name"), ""));
			data.put("organisation", getDirectoryRecordAttribute(attributes, config.get(db, "directory_organisation"), ""));
			data.put("email", getDirectoryRecordAttribute(attributes, config.get(db, "directory_email"), ""));
			data.put("notes", getDirectoryRecordAttribute(attributes, config.get(db, "directory_notes"), ""));
			data.put("userinfo", getDirectoryRecordAttribute(attributes, config.get(db, "directory_userinfo"), ""));
//			data.put("shopcart", getDirectoryRecordAttribute(attributes, config.get(db, "directory_shopcart"), ""));
//			data.put("wishlist", getDirectoryRecordAttribute(attributes, config.get(db, "directory_wishlist"), ""));

//			data.put("id", getDirectoryRecordAttribute(attributes, config.get(db, "directory_id"), ""));
//			data.put("locked", getDirectoryRecordAttribute(attributes, config.get(db, "directory_locked"), ""));
//			data.put("failed", getDirectoryRecordAttribute(attributes, config.get(db, "directory_failed"), ""));

//			data.put("created", getDirectoryRecordAttribute(attributes, config.get(db, "directory_created"), ""));
//			data.put("updated", getDirectoryRecordAttribute(attributes, config.get(db, "directory_updated"), ""));
//			data.put("created_by", getDirectoryRecordAttribute(attributes, config.get(db, "directory_created_by"), ""));
//			data.put("updated_by", getDirectoryRecordAttribute(attributes, config.get(db, "directory_updated_by"), ""));
//			data.put("keywords", getDirectoryRecordAttribute(attributes, config.get(db, "directory_keywords"), ""));
//			data.put("description", getDirectoryRecordAttribute(attributes, config.get(db, "directory_description"), ""));

//			data.put("scheduled_last", getDirectoryRecordAttribute(attributes, config.get(db, "directory_scheduled_last"), ""));
			data.put("scheduled_publish", getDirectoryRecordAttribute(attributes, config.get(db, "directory_scheduled_publish"), ""));
			data.put("scheduled_notify", getDirectoryRecordAttribute(attributes, config.get(db, "directory_scheduled_notify"), ""));
			data.put("scheduled_unpublish", getDirectoryRecordAttribute(attributes, config.get(db, "directory_scheduled_unpublish"), ""));
			data.put("scheduled_publish_email", getDirectoryRecordAttribute(attributes, config.get(db, "directory_scheduled_publish_email"), ""));
			data.put("scheduled_notify_email", getDirectoryRecordAttribute(attributes, config.get(db, "directory_scheduled_notify_email"), ""));
			data.put("scheduled_unpublish_email", getDirectoryRecordAttribute(attributes, config.get(db, "directory_scheduled_unpublish_email"), ""));

//			data.put("users_type", getDirectoryRecordAttribute(attributes, config.get(db, "directory_users_type"), ""));
//			data.put("users_group", getDirectoryRecordAttribute(attributes, config.get(db, "directory_users_group"), ""));
//			data.put("creators_type", getDirectoryRecordAttribute(attributes, config.get(db, "directory_creators_type"), ""));
//			data.put("creators_group", getDirectoryRecordAttribute(attributes, config.get(db, "directory_creators_group"), ""));
//			data.put("editors_type", getDirectoryRecordAttribute(attributes, config.get(db, "directory_editors_type"), ""));
//			data.put("editors_group", getDirectoryRecordAttribute(attributes, config.get(db, "directory_editors_group"), ""));
//			data.put("publishers_type", getDirectoryRecordAttribute(attributes, config.get(db, "directory_publishers_type"), ""));
//			data.put("publishers_group", getDirectoryRecordAttribute(attributes, config.get(db, "directory_publishers_group"), ""));
//			data.put("administrators_type", getDirectoryRecordAttribute(attributes, config.get(db, "directory_administrators_type"), ""));
//			data.put("administrators_group", getDirectoryRecordAttribute(attributes, config.get(db, "directory_administrators_group"), ""));

			data.put("invoice_name", getDirectoryRecordAttribute(attributes, config.get(db, "directory_invoice_name"), ""));
			data.put("invoice_organisation", getDirectoryRecordAttribute(attributes, config.get(db, "directory_invoice_organisation"), ""));
			data.put("invoice_address", getDirectoryRecordAttribute(attributes, config.get(db, "directory_invoice_address"), ""));
			data.put("invoice_postalcode", getDirectoryRecordAttribute(attributes, config.get(db, "directory_invoice_postalcode"), ""));
			data.put("invoice_city", getDirectoryRecordAttribute(attributes, config.get(db, "directory_invoice_city"), ""));
			data.put("invoice_state", getDirectoryRecordAttribute(attributes, config.get(db, "directory_invoice_state"), ""));
			data.put("invoice_country", getDirectoryRecordAttribute(attributes, config.get(db, "directory_invoice_country"), ""));
			data.put("invoice_phone", getDirectoryRecordAttribute(attributes, config.get(db, "directory_invoice_phone"), ""));
			data.put("invoice_fax", getDirectoryRecordAttribute(attributes, config.get(db, "directory_invoice_fax"), ""));
			data.put("invoice_email", getDirectoryRecordAttribute(attributes, config.get(db, "directory_invoice_email"), ""));
			data.put("invoice_website", getDirectoryRecordAttribute(attributes, config.get(db, "directory_invoice_website"), ""));

			data.put("delivery_name", getDirectoryRecordAttribute(attributes, config.get(db, "directory_delivery_name"), ""));
			data.put("delivery_organisation", getDirectoryRecordAttribute(attributes, config.get(db, "directory_delivery_organisation"), ""));
			data.put("delivery_address", getDirectoryRecordAttribute(attributes, config.get(db, "directory_delivery_address"), ""));
			data.put("delivery_postalcode", getDirectoryRecordAttribute(attributes, config.get(db, "directory_delivery_postalcode"), ""));
			data.put("delivery_city", getDirectoryRecordAttribute(attributes, config.get(db, "directory_delivery_city"), ""));
			data.put("delivery_state", getDirectoryRecordAttribute(attributes, config.get(db, "directory_delivery_state"), ""));
			data.put("delivery_country", getDirectoryRecordAttribute(attributes, config.get(db, "directory_delivery_country"), ""));
			data.put("delivery_phone", getDirectoryRecordAttribute(attributes, config.get(db, "directory_delivery_phone"), ""));
			data.put("delivery_fax", getDirectoryRecordAttribute(attributes, config.get(db, "directory_delivery_fax"), ""));
			data.put("delivery_email", getDirectoryRecordAttribute(attributes, config.get(db, "directory_delivery_email"), ""));
			data.put("delivery_website", getDirectoryRecordAttribute(attributes, config.get(db, "directory_delivery_website"), ""));

			data.put("card_type", getDirectoryRecordAttribute(attributes, config.get(db, "directory_payment_type"), ""));
			data.put("card_number", getDirectoryRecordAttribute(attributes, config.get(db, "directory_payment_number"), ""));
			data.put("card_issuedmonth", getDirectoryRecordAttribute(attributes, config.get(db, "directory_payment_issuedmonth"), ""));
			data.put("card_issuedyear", getDirectoryRecordAttribute(attributes, config.get(db, "directory_payment_issuedyear"), ""));
			data.put("card_expirymonth", getDirectoryRecordAttribute(attributes, config.get(db, "directory_payment_expirymonth"), ""));
			data.put("card_expiryyear", getDirectoryRecordAttribute(attributes, config.get(db, "directory_payment_expiryyear"), ""));
			data.put("card_name", getDirectoryRecordAttribute(attributes, config.get(db, "directory_payment_name"), ""));
			data.put("card_cvc", getDirectoryRecordAttribute(attributes, config.get(db, "directory_payment_cvc"), ""));
			data.put("card_issue", getDirectoryRecordAttribute(attributes, config.get(db, "directory_payment_issue"), ""));
			data.put("card_postalcode", getDirectoryRecordAttribute(attributes, config.get(db, "directory_payment_postalcode"), ""));

			data.put("content_editor", getDirectoryRecordAttribute(attributes, config.get(db, "directory_contenteditor"), ""));
//			data.put("hardcore_encoding", getDirectoryRecordAttribute(attributes, config.get(db, "directory_encoding"), ""));
//			data.put("hardcore_language", getDirectoryRecordAttribute(attributes, config.get(db, "directory_language"), ""));
			data.put("hardcore_upload", getDirectoryRecordAttribute(attributes, config.get(db, "directory_imageupload"), ""));
			data.put("hardcore_format", getDirectoryRecordAttribute(attributes, config.get(db, "directory_format"), ""));
			data.put("hardcore_width", getDirectoryRecordAttribute(attributes, config.get(db, "directory_size_width"), ""));
			data.put("hardcore_height", getDirectoryRecordAttribute(attributes, config.get(db, "directory_size_height"), ""));
			data.put("hardcore_onenter", getDirectoryRecordAttribute(attributes, config.get(db, "directory_enter_onenter"), ""));
			data.put("hardcore_onctrlenter", getDirectoryRecordAttribute(attributes, config.get(db, "directory_enter_onctrlenter"), ""));
			data.put("hardcore_onshiftenter", getDirectoryRecordAttribute(attributes, config.get(db, "directory_enter_onshiftenter"), ""));
			data.put("hardcore_onaltenter", getDirectoryRecordAttribute(attributes, config.get(db, "directory_enter_onaltenter"), ""));
			data.put("hardcore_toolbar1", getDirectoryRecordAttribute(attributes, config.get(db, "directory_hardcore_toolbar1"), ""));
			data.put("hardcore_toolbar2", getDirectoryRecordAttribute(attributes, config.get(db, "directory_hardcore_toolbar2"), ""));
			data.put("hardcore_toolbar3", getDirectoryRecordAttribute(attributes, config.get(db, "directory_hardcore_toolbar3"), ""));
			data.put("hardcore_toolbar4", getDirectoryRecordAttribute(attributes, config.get(db, "directory_hardcore_toolbar4"), ""));
			data.put("hardcore_toolbar5", getDirectoryRecordAttribute(attributes, config.get(db, "directory_hardcore_toolbar5"), ""));
			data.put("hardcore_formatblock", getDirectoryRecordAttribute(attributes, config.get(db, "directory_hardcore_formatblock"), ""));
			data.put("hardcore_fontname", getDirectoryRecordAttribute(attributes, config.get(db, "directory_hardcore_fontname"), ""));
			data.put("hardcore_fontsize", getDirectoryRecordAttribute(attributes, config.get(db, "directory_hardcore_fontsize"), ""));
			data.put("hardcore_customscript", getDirectoryRecordAttribute(attributes, config.get(db, "directory_hardcore_customscript"), ""));
			data.put("index_workspace", getDirectoryRecordAttribute(attributes, config.get(db, "directory_index_workspace"), ""));
			data.put("index_content", getDirectoryRecordAttribute(attributes, config.get(db, "directory_index_content"), ""));
			data.put("index_library", getDirectoryRecordAttribute(attributes, config.get(db, "directory_index_library"), ""));
			data.put("index_product", getDirectoryRecordAttribute(attributes, config.get(db, "directory_index_product"), ""));
			data.put("index_stock", getDirectoryRecordAttribute(attributes, config.get(db, "directory_index_stock"), ""));
			data.put("index_order", getDirectoryRecordAttribute(attributes, config.get(db, "directory_index_order"), ""));
			data.put("index_segments", getDirectoryRecordAttribute(attributes, config.get(db, "directory_index_segments"), ""));
			data.put("index_usertests", getDirectoryRecordAttribute(attributes, config.get(db, "directory_index_usertests"), ""));
			data.put("index_heatmaps", getDirectoryRecordAttribute(attributes, config.get(db, "directory_index_heatmaps"), ""));
			data.put("index_sales", getDirectoryRecordAttribute(attributes, config.get(db, "directory_index_sales"), ""));
			data.put("index_databases", getDirectoryRecordAttribute(attributes, config.get(db, "directory_index_databases"), ""));
			data.put("index_user", getDirectoryRecordAttribute(attributes, config.get(db, "directory_index_user"), ""));
			data.put("index_hosting", getDirectoryRecordAttribute(attributes, config.get(db, "directory_index_hosting"), ""));
			data.put("index_websites", getDirectoryRecordAttribute(attributes, config.get(db, "directory_index_websites"), ""));
			data.put("menu_selection", getDirectoryRecordAttribute(attributes, config.get(db, "directory_menu_selection"), ""));
			data.put("workspace_sections", getDirectoryRecordAttribute(attributes, config.get(db, "directory_workspace_sections"), ""));
			data.put("statistics_reports", getDirectoryRecordAttribute(attributes, config.get(db, "directory_statistics_reports"), ""));
			data.put("sales_reports", getDirectoryRecordAttribute(attributes, config.get(db, "directory_sales_reports"), ""));

			directoryentry_permission = new HashMap<String,String>();
			directoryentry_permission.put("webadmin_browseedit", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_browseedit"), null));
			directoryentry_permission.put("webadmin_structure", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_structure"), null));
			directoryentry_permission.put("webadmin_content", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_content"), null));
			directoryentry_permission.put("webadmin_content_structure", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_content_structure"), null));
			directoryentry_permission.put("webadmin_content_pages", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_content_pages"), null));
			directoryentry_permission.put("webadmin_content_elements", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_content_elements"), null));
			directoryentry_permission.put("webadmin_content_templates", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_content_templates"), null));
			directoryentry_permission.put("webadmin_content_stylesheets", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_content_stylesheets"), null));
			directoryentry_permission.put("webadmin_content_scripts", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_content_scripts"), null));
			directoryentry_permission.put("webadmin_content_packages", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_content_packages"), null));
			directoryentry_permission.put("webadmin_content_bundles", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_content_bundles"), null));
			directoryentry_permission.put("webadmin_library", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_library"), null));
			directoryentry_permission.put("webadmin_library_images", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_library_images"), null));
			directoryentry_permission.put("webadmin_library_files", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_library_files"), null));
			directoryentry_permission.put("webadmin_library_links", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_library_links"), null));
			directoryentry_permission.put("webadmin_library_packages", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_library_packages"), null));
			directoryentry_permission.put("webadmin_library_bundles", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_library_bundles"), null));
			directoryentry_permission.put("webadmin_ecommerce", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_ecommerce"), null));
			directoryentry_permission.put("webadmin_ecommerce_products", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_ecommerce_products"), null));
			directoryentry_permission.put("webadmin_ecommerce_orders", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_ecommerce_orders"), null));
			directoryentry_permission.put("webadmin_ecommerce_packages", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_ecommerce_packages"), null));
			directoryentry_permission.put("webadmin_ecommerce_bundles", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_ecommerce_bundles"), null));
			directoryentry_permission.put("webadmin_databases", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_databases"), null));
			directoryentry_permission.put("webadmin_databases_data", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_databases_data"), null));
			directoryentry_permission.put("webadmin_databases_export", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_databases_export"), null));
			directoryentry_permission.put("webadmin_databases_import", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_databases_import"), null));
			directoryentry_permission.put("webadmin_experience", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_experience"), null));
			directoryentry_permission.put("webadmin_experience_segments", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_experience_segments"), null));
			directoryentry_permission.put("webadmin_experience_usertests", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_experience_usertests"), null));
			directoryentry_permission.put("webadmin_experience_usertestreports", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_experience_usertestreports"), null));
			directoryentry_permission.put("webadmin_experience_heatmaps", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_experience_heatmaps"), null));
			directoryentry_permission.put("webadmin_users", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_users"), null));
			directoryentry_permission.put("webadmin_users_administrators", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_users_administrators"), null));
			directoryentry_permission.put("webadmin_users_templates", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_users_templates"), null));
			directoryentry_permission.put("webadmin_users_users", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_users_users"), null));
			directoryentry_permission.put("webadmin_statistics", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics"), null));
			directoryentry_permission.put("webadmin_statistics_summary", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_summary"), null));
			directoryentry_permission.put("webadmin_statistics_what", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_what"), null));
			directoryentry_permission.put("webadmin_statistics_what_websites", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_what_websites"), null));
			directoryentry_permission.put("webadmin_statistics_what_content", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_what_content"), null));
			directoryentry_permission.put("webadmin_statistics_what_library", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_what_library"), null));
			directoryentry_permission.put("webadmin_statistics_what_ecommerce", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_what_ecommerce"), null));
			directoryentry_permission.put("webadmin_statistics_what_databases", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_what_databases"), null));
			directoryentry_permission.put("webadmin_statistics_when", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_when"), null));
			directoryentry_permission.put("webadmin_statistics_when_daily", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_when_daily"), null));
			directoryentry_permission.put("webadmin_statistics_when_weekly", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_when_weekly"), null));
			directoryentry_permission.put("webadmin_statistics_when_monthly", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_when_monthly"), null));
			directoryentry_permission.put("webadmin_statistics_when_yearly", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_when_yearly"), null));
			directoryentry_permission.put("webadmin_statistics_when_hours", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_when_hours"), null));
			directoryentry_permission.put("webadmin_statistics_when_weekdays", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_when_weekdays"), null));
			directoryentry_permission.put("webadmin_statistics_when_days", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_when_days"), null));
			directoryentry_permission.put("webadmin_statistics_when_weeks", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_when_weeks"), null));
			directoryentry_permission.put("webadmin_statistics_when_months", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_when_months"), null));
			directoryentry_permission.put("webadmin_statistics_who", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_who"), null));
			directoryentry_permission.put("webadmin_statistics_who_countries", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_who_countries"), null));
			directoryentry_permission.put("webadmin_statistics_who_visitors", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_who_visitors"), null));
			directoryentry_permission.put("webadmin_statistics_who_robots", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_who_robots"), null));
			directoryentry_permission.put("webadmin_statistics_who_operatingsystems", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_who_operatingsystems"), null));
			directoryentry_permission.put("webadmin_statistics_who_webbrowsers", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_who_webbrowsers"), null));
			directoryentry_permission.put("webadmin_statistics_who_users", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_who_users"), null));
			directoryentry_permission.put("webadmin_statistics_why", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_why"), null));
			directoryentry_permission.put("webadmin_statistics_why_referers", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_why_referers"), null));
			directoryentry_permission.put("webadmin_statistics_why_searchengines", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_why_searchengines"), null));
			directoryentry_permission.put("webadmin_statistics_why_searchqueries", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_why_searchqueries"), null));
			directoryentry_permission.put("webadmin_statistics_why_searchwords", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_why_searchwords"), null));
			directoryentry_permission.put("webadmin_statistics_how", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_how"), null));
			directoryentry_permission.put("webadmin_statistics_how_entry", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_how_entry"), null));
			directoryentry_permission.put("webadmin_statistics_how_paths", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_how_paths"), null));
			directoryentry_permission.put("webadmin_statistics_how_exit", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_how_exit"), null));
			directoryentry_permission.put("webadmin_statistics_how_duration", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_how_duration"), null));
			directoryentry_permission.put("webadmin_statistics_how_visits", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_statistics_how_visits"), null));
			directoryentry_permission.put("webadmin_updates", getDirectoryRecordAttribute(attributes, config.get(db, "directory_webadmin_updates"), null));

			data.put("userclass", "");
			NamingEnumeration allAttr = attributes.getAll();
			try {
				while (allAttr.hasMore()) {
					Attribute attr = (Attribute)allAttr.next();
					String id = attr.getID();
					if (DEBUG) System.out.println("DEBUG:HardCore.User.getDirectoryRecord:userclass:" + config.get(db, "directory_userclass") + " - " + id);
					if (id.toUpperCase().equals(config.get(db, "directory_userclass").toUpperCase())) {
						NamingEnumeration values = attr.getAll();
						while (values.hasMore()) {
							String value = "" + values.next();
							if (DEBUG) System.out.println("DEBUG:HardCore.User.getDirectoryRecord:userclass:admin:" + config.get(db, "directory_userclass_admin") + " - " + value);
							if (config.get(db, "directory_userclass_admin").equals(value)) {
								data.put("userclass", "administrator");
							}
						}
					}
				}
			} catch (Exception e) {
			}
			if (DEBUG) System.out.println("DEBUG:HardCore.User.getDirectoryRecord:userclass:" + data.get("userclass"));
			data.put("usergroup", "");
			directoryentry_usergroups = attributes.get(config.get(db, "directory_usergroups"));
			usergroups_cache = null;
			usergroups_cache = usergroups(db);
			if (DEBUG) System.out.println("DEBUG:HardCore.User.getDirectoryRecord:usergroups:" + config.get(db, "directory_usergroups") + " - " + directoryentry_usergroups);
			data.put("usertype", "");
			directoryentry_usertypes = attributes.get(config.get(db, "directory_usertypes"));
			usertypes_cache = null;
			usertypes_cache = usertypes(db);
			if (DEBUG) System.out.println("DEBUG:HardCore.User.getDirectoryRecord:usertypes:" + config.get(db, "directory_usertypes") + " - " + directoryentry_usertypes);
		}
		return data;
	}
	public String getDirectoryRecordAttribute(Attributes attributes, String name, String defaultvalue) {
		String value = defaultvalue;
		if (! name.equals("")) {
			try {
				value = "" + attributes.get(name).get();
			} catch (Exception e) {
				value = "" + attributes.get(name);
			}
			if (DEBUG) System.out.println("DEBUG:HardCore.User.getDirectoryRecordAttribute:" + name + " - " + value);
		}
		return value;
	}
	public String getDirectoryRecordAttributes(Attributes attributes, String name, String defaultvalue) {
		String value = defaultvalue;
		if (! name.equals("")) {
			value = "" + attributes.get(name);
			if (DEBUG) System.out.println("DEBUG:HardCore.User.getDirectoryRecordAttributes:" + name + " - " + value);
		}
		return value;
	}



	public void getRecord(DB db, HashMap<String,String> record) {
		id = "" + record.get("id");
		locked = Common.intnumber(record.get("locked"));
		failed = Common.intnumber(record.get("failed"));
		created = "" + record.get("created");
		updated = "" + record.get("updated");
		created_by = "" + record.get("created_by");
		updated_by = "" + record.get("updated_by");
		keywords = db.decrypt("" + record.get("keywords"));
		description = db.decrypt("" + record.get("description"));
		birthdate = db.decrypt("" + record.get("birthdate"));
		birthyear = db.decrypt("" + record.get("birthyear"));
		birthmonth = db.decrypt("" + record.get("birthmonth"));
		birthday = db.decrypt("" + record.get("birthday"));
		gender = db.decrypt("" + record.get("gender"));
		title = db.decrypt("" + record.get("title"));
		name = "" + record.get("name");
		organisation = "" + record.get("organisation");
		username = "" + record.get("username");
		if (DIGEST) {
			password = "" + record.get("password");
		} else {
			password = db.decrypt("" + record.get("password"));
		}
		email = db.decrypt("" + record.get("email"));
		usertype = "" + record.get("usertype");
		usergroup = "" + record.get("usergroup");
		userclass = "" + record.get("userclass");
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
		content_editor = "" + record.get("content_editor");
		hardcore_encoding = "" + record.get("hardcore_encoding");
		hardcore_language = "" + record.get("hardcore_language");
		hardcore_upload = "" + record.get("hardcore_upload");
		hardcore_format = "" + record.get("hardcore_format");
		hardcore_width = "" + record.get("hardcore_width");
		hardcore_height = "" + record.get("hardcore_height");
		hardcore_onenter = "" + record.get("hardcore_onenter");
		hardcore_onctrlenter = "" + record.get("hardcore_onctrlenter");
		hardcore_onshiftenter = "" + record.get("hardcore_onshiftenter");
		hardcore_onaltenter = "" + record.get("hardcore_onaltenter");
		hardcore_toolbar1 = "" + record.get("hardcore_toolbar1");
		hardcore_toolbar2 = "" + record.get("hardcore_toolbar2");
		hardcore_toolbar3 = "" + record.get("hardcore_toolbar3");
		hardcore_toolbar4 = "" + record.get("hardcore_toolbar4");
		hardcore_toolbar5 = "" + record.get("hardcore_toolbar5");
		hardcore_formatblock = "" + record.get("hardcore_formatblock");
		hardcore_fontname = "" + record.get("hardcore_fontname");
		hardcore_fontsize = "" + record.get("hardcore_fontsize");
		hardcore_customscript = "" + record.get("hardcore_customscript");
		index_workspace = "" + record.get("index_workspace");
		index_content = "" + record.get("index_content");
		index_library = "" + record.get("index_library");
		index_product = "" + record.get("index_product");
		index_stock = "" + record.get("index_stock");
		index_order = "" + record.get("index_order");
		index_segments = "" + record.get("index_segments");
		index_usertests = "" + record.get("index_usertests");
		index_heatmaps = "" + record.get("index_heatmaps");
		index_sales = "" + record.get("index_sales");
		index_databases = "" + record.get("index_databases");
		index_user = "" + record.get("index_user");
		index_hosting = "" + record.get("index_hosting");
		index_websites = "" + record.get("index_websites");
		menu_selection = "" + record.get("menu_selection");
		workspace_sections = "" + record.get("workspace_sections");
		statistics_reports = "" + record.get("statistics_reports");
		sales_reports = "" + record.get("sales_reports");
		scheduled_last = "" + record.get("scheduled_last");
		scheduled_publish = "" + record.get("scheduled_publish");
		scheduled_notify = "" + record.get("scheduled_notify");
		scheduled_unpublish = "" + record.get("scheduled_unpublish");
		scheduled_publish_email = "" + record.get("scheduled_publish_email");
		scheduled_notify_email = "" + record.get("scheduled_notify_email");
		scheduled_unpublish_email = "" + record.get("scheduled_unpublish_email");
		card_type = db.decrypt("" + record.get("card_type"));
		card_number = db.decrypt("" + record.get("card_number"));
		card_issuedmonth = db.decrypt("" + record.get("card_issuedmonth"));
		card_issuedyear = db.decrypt("" + record.get("card_issuedyear"));
		card_expirymonth = db.decrypt("" + record.get("card_expirymonth"));
		card_expiryyear = db.decrypt("" + record.get("card_expiryyear"));
		card_name = db.decrypt("" + record.get("card_name"));
		card_cvc = db.decrypt("" + record.get("card_cvc"));
		card_issue = db.decrypt("" + record.get("card_issue"));
		card_postalcode = db.decrypt("" + record.get("card_postalcode"));
		delivery_name = db.decrypt("" + record.get("delivery_name"));
		delivery_organisation = db.decrypt("" + record.get("delivery_organisation"));
		delivery_address = db.decrypt("" + record.get("delivery_address"));
		delivery_postalcode = db.decrypt("" + record.get("delivery_postalcode"));
		delivery_city = db.decrypt("" + record.get("delivery_city"));
		delivery_state = db.decrypt("" + record.get("delivery_state"));
		delivery_country = db.decrypt("" + record.get("delivery_country"));
		delivery_phone = db.decrypt("" + record.get("delivery_phone"));
		delivery_fax = db.decrypt("" + record.get("delivery_fax"));
		delivery_email = db.decrypt("" + record.get("delivery_email"));
		delivery_website = db.decrypt("" + record.get("delivery_website"));
		invoice_name = db.decrypt("" + record.get("invoice_name"));
		invoice_organisation = db.decrypt("" + record.get("invoice_organisation"));
		invoice_address = db.decrypt("" + record.get("invoice_address"));
		invoice_postalcode = db.decrypt("" + record.get("invoice_postalcode"));
		invoice_city = db.decrypt("" + record.get("invoice_city"));
		invoice_state = db.decrypt("" + record.get("invoice_state"));
		invoice_country = db.decrypt("" + record.get("invoice_country"));
		invoice_phone = db.decrypt("" + record.get("invoice_phone"));
		invoice_fax = db.decrypt("" + record.get("invoice_fax"));
		invoice_email = db.decrypt("" + record.get("invoice_email"));
		invoice_website = db.decrypt("" + record.get("invoice_website"));
		notes = db.decrypt("" + record.get("notes"));
		userinfo = db.decrypt("" + record.get("userinfo"));
		shopcart = db.decrypt("" + record.get("shopcart"));
		wishlist = db.decrypt("" + record.get("wishlist"));
	}



	public void getAccessRestrictions(String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config) {
		user = true;
		if ((users_type.equals("*")) && (session_username.equals(""))) {
			user = false;
		} else if ((! users_type.equals("*")) && (! users_type.equals("")) && (! users_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + users_type + "|") >= 0))) {
			user = false;
		}
		if ((users_group.equals("*")) && (session_username.equals(""))) {
			user = false;
		} else if ((! users_group.equals("*")) && (! users_group.equals("")) && (! users_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + users_group + "|") >= 0))) {
			user = false;
		}
		if ((config != null) && (config.get(db, "accessrestrictions").equals("or"))) {
			if (((! users_type.equals("")) && ((users_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + users_type + "|") >= 0))) || ((! users_group.equals("")) && ((users_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + users_group + "|") >= 0)))) {
				user = true;
			}
		}
		if (user && (config != null)) {
			if ((config.get(db, "website_users_type").equals("*")) && (session_username.equals(""))) {
				user = false;
			} else if ((! config.get(db, "website_users_type").equals("*")) && (! config.get(db, "website_users_type").equals("")) && (! config.get(db, "website_users_type").equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_users_type") + "|") >= 0))) {
				user = false;
			}
			if ((config.get(db, "website_users_group").equals("*")) && (session_username.equals(""))) {
				user = false;
			} else if ((! config.get(db, "website_users_group").equals("*")) && (! config.get(db, "website_users_group").equals("")) && (! config.get(db, "website_users_group").equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_users_group") + "|") >= 0))) {
				user = false;
			}
			if ((config != null) && (config.get(db, "accessrestrictions").equals("or"))) {
				if (((! config.get(db, "website_users_type").equals("")) && ((config.get(db, "website_users_type").equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_users_type") + "|") >= 0))) || ((! config.get(db, "website_users_group").equals("")) && ((config.get(db, "website_users_group").equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_users_group") + "|") >= 0)))) {
					user = true;
				}
			}
		}

		editor = true;
		if ((! editors_type.equals("")) && (! editors_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + editors_type + "|") >= 0))) {
			editor = false;
		}
		if ((! editors_group.equals("")) && (! editors_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + editors_group + "|") >= 0))) {
			editor = false;
		}
		if ((config != null) && (config.get(db, "accessrestrictions").equals("or"))) {
			if (((! editors_type.equals("")) && ((editors_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + editors_type + "|") >= 0))) || ((! editors_group.equals("")) && ((editors_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + editors_group + "|") >= 0)))) {
				editor = true;
			}
		}
		if (editor && (config != null)) {
			if ((config.get(db, "website_editors_type").equals("*")) && (session_username.equals(""))) {
				editor = false;
			} else if ((! config.get(db, "website_editors_type").equals("0")) && (! config.get(db, "website_editors_type").equals("*")) && (! config.get(db, "website_editors_type").equals("=")) && (! config.get(db, "website_editors_type").equals("")) && (! config.get(db, "website_editors_type").equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_editors_type") + "|") >= 0))) {
				editor = false;
			}
			if ((config.get(db, "website_editors_group").equals("*")) && (session_username.equals(""))) {
				editor = false;
			} else if ((! config.get(db, "website_editors_group").equals("0")) && (! config.get(db, "website_editors_group").equals("*")) && (! config.get(db, "website_editors_group").equals("=")) && (! config.get(db, "website_editors_group").equals("")) && (! config.get(db, "website_editors_group").equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_editors_group") + "|") >= 0))) {
				editor = false;
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((config.get(db, "website_editors_type").equals("*")) || (config.get(db, "website_editors_group").equals("*"))) && (! session_username.equals(""))) {
					editor = true;
				}
				if (((! config.get(db, "website_editors_type").equals("0")) && (! config.get(db, "website_editors_type").equals("*")) && (! config.get(db, "website_editors_type").equals("=")) && (! config.get(db, "website_editors_type").equals("")) && ((config.get(db, "website_editors_type").equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_editors_type") + "|") >= 0))) || ((! config.get(db, "website_editors_group").equals("0")) && (! config.get(db, "website_editors_group").equals("*")) && (! config.get(db, "website_editors_group").equals("=")) && (! config.get(db, "website_editors_group").equals("")) && ((config.get(db, "website_editors_group").equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_editors_group") + "|") >= 0)))) {
					editor = true;
				}
			}
		}

		creator = true;
		if ((! creators_type.equals("")) && (! creators_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + creators_type + "|") >= 0))) {
			creator = false;
		}
		if ((! creators_group.equals("")) && (! creators_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + creators_group + "|") >= 0))) {
			creator = false;
		}
		if ((config != null) && (config.get(db, "accessrestrictions").equals("or"))) {
			if (((! creators_type.equals("")) && ((creators_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + creators_type + "|") >= 0))) || ((! creators_group.equals("")) && ((creators_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + creators_group + "|") >= 0)))) {
				creator = true;
			}
		}
		if (creator && (config != null)) {
			if ((config.get(db, "website_creators_type").equals("*")) && (session_username.equals(""))) {
				creator = false;
			} else if ((! config.get(db, "website_creators_type").equals("0")) && (! config.get(db, "website_creators_type").equals("*")) && (! config.get(db, "website_creators_type").equals("=")) && (! config.get(db, "website_creators_type").equals("")) && (! config.get(db, "website_creators_type").equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_creators_type") + "|") >= 0))) {
				creator = false;
			}
			if ((config.get(db, "website_creators_group").equals("*")) && (session_username.equals(""))) {
				creator = false;
			} else if ((! config.get(db, "website_creators_group").equals("0")) && (! config.get(db, "website_creators_group").equals("*")) && (! config.get(db, "website_creators_group").equals("=")) && (! config.get(db, "website_creators_group").equals("")) && (! config.get(db, "website_creators_group").equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_creators_group") + "|") >= 0))) {
				creator = false;
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((config.get(db, "website_creators_type").equals("*")) || (config.get(db, "website_creators_group").equals("*"))) && (! session_username.equals(""))) {
					creator = true;
				}
				if (((! config.get(db, "website_creators_type").equals("0")) && (! config.get(db, "website_creators_type").equals("*")) && (! config.get(db, "website_creators_type").equals("=")) && (! config.get(db, "website_creators_type").equals("")) && ((config.get(db, "website_creators_type").equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_creators_type") + "|") >= 0))) || ((! config.get(db, "website_creators_group").equals("0")) && (! config.get(db, "website_creators_group").equals("*")) && (! config.get(db, "website_creators_group").equals("=")) && (! config.get(db, "website_creators_group").equals("")) && ((config.get(db, "website_creators_group").equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_creators_group") + "|") >= 0)))) {
					creator = true;
				}
			}
		}

		publisher = true;
		if ((! publishers_type.equals("")) && (! publishers_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + publishers_type + "|") >= 0))) {
			publisher = false;
		}
		if ((! publishers_group.equals("")) && (! publishers_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + publishers_group + "|") >= 0))) {
			publisher = false;
		}
		if ((config != null) && (config.get(db, "accessrestrictions").equals("or"))) {
			if (((! publishers_type.equals("")) && ((publishers_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + publishers_type + "|") >= 0))) || ((! publishers_group.equals("")) && ((publishers_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + publishers_group + "|") >= 0)))) {
				publisher = true;
			}
		}
		if (publisher && (config != null)) {
			if ((config.get(db, "website_publishers_type").equals("*")) && (session_username.equals(""))) {
				publisher = false;
			} else if ((! config.get(db, "website_publishers_type").equals("0")) && (! config.get(db, "website_publishers_type").equals("*")) && (! config.get(db, "website_publishers_type").equals("=")) && (! config.get(db, "website_publishers_type").equals("")) && (! config.get(db, "website_publishers_type").equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_publishers_type") + "|") >= 0))) {
				publisher = false;
			}
			if ((config.get(db, "website_publishers_group").equals("*")) && (session_username.equals(""))) {
				publisher = false;
			} else if ((! config.get(db, "website_publishers_group").equals("0")) && (! config.get(db, "website_publishers_group").equals("*")) && (! config.get(db, "website_publishers_group").equals("=")) && (! config.get(db, "website_publishers_group").equals("")) && (! config.get(db, "website_publishers_group").equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_publishers_group") + "|") >= 0))) {
				publisher = false;
			}
			if (config.get(db, "accessrestrictions").equals("or")) {
				if (((config.get(db, "website_publishers_type").equals("*")) || (config.get(db, "website_publishers_group").equals("*"))) && (! session_username.equals(""))) {
					publisher = true;
				}
				if (((! config.get(db, "website_publishers_type").equals("0")) && (! config.get(db, "website_publishers_type").equals("*")) && (! config.get(db, "website_publishers_type").equals("=")) && (! config.get(db, "website_publishers_type").equals("")) && ((config.get(db, "website_publishers_type").equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_publishers_type") + "|") >= 0))) || ((! config.get(db, "website_publishers_group").equals("0")) && (! config.get(db, "website_publishers_group").equals("*")) && (! config.get(db, "website_publishers_group").equals("=")) && (! config.get(db, "website_publishers_group").equals("")) && ((config.get(db, "website_publishers_group").equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_publishers_group") + "|") >= 0)))) {
					publisher = true;
				}
			}
		}

		administrator = true;
		if ((! administrators_type.equals("")) && (! administrators_type.equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + administrators_type + "|") >= 0))) {
			administrator = false;
		}
		if ((! administrators_group.equals("")) && (! administrators_group.equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + administrators_group + "|") >= 0))) {
			administrator = false;
		}
		if ((config != null) && (config.get(db, "accessrestrictions").equals("or"))) {
			if (((! administrators_type.equals("")) && ((administrators_type.equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + administrators_type + "|") >= 0))) || ((! administrators_group.equals("")) && ((administrators_group.equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + administrators_group + "|") >= 0)))) {
				administrator = true;
			}
		}
		if (administrator && (config != null)) {
			if ((! config.get(db, "website_administrators_type").equals("")) && (! config.get(db, "website_administrators_type").equals(session_usertype)) && (! (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_administrators_type") + "|") >= 0))) {
				administrator = false;
			}
			if ((! config.get(db, "website_administrators_group").equals("")) && (! config.get(db, "website_administrators_group").equals(session_usergroup)) && (! (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_administrators_group") + "|") >= 0))) {
				administrator = false;
			}
			if ((config != null) && (config.get(db, "accessrestrictions").equals("or"))) {
				if (((! config.get(db, "website_administrators_type").equals("")) && ((config.get(db, "website_administrators_type").equals(session_usertype)) || (("|" + session_usertypes + "|").indexOf("|" + config.get(db, "website_administrators_type") + "|") >= 0))) || ((! config.get(db, "website_administrators_group").equals("")) && ((config.get(db, "website_administrators_group").equals(session_usergroup)) || (("|" + session_usergroups + "|").indexOf("|" + config.get(db, "website_administrators_group") + "|") >= 0)))) {
					administrator = true;
				}
			}
		}

		if ((config != null) && (session_username.equals(config.get(db, "superadmin")))) {
			user = true;
			editor = true;
			creator = true;
			publisher = true;
			administrator = true;
		}
		if (session_username.equals("")) {
			user = false;
			editor = false;
			creator = false;
			publisher = false;
			administrator = false;
		}

		if ((creators_type.equals("0")) || (creators_group.equals("0"))) {
			creator = true;
		}
		if ((publishers_type.equals("0")) || (publishers_group.equals("0"))) {
			publisher = true;
		}
		if (((creators_type.equals("*")) || (creators_group.equals("*"))) && (! session_username.equals(""))) {
			creator = true;
		}
		if (((publishers_type.equals("*")) || (publishers_group.equals("*"))) && (! session_username.equals(""))) {
			publisher = true;
		}

		if ((config != null) && (! config.get(db, "inherit_accessrestrictions").equals("no"))) {
			if (administrator) {
				user = true;
				editor = true;
				creator = true;
				publisher = true;
			} else if (publisher) {
				user = true;
				editor = true;
			} else if (creator) {
				user = true;
				editor = true;
			} else if (editor) {
				user = true;
			}
		}
	}



	public void getForm(Request request, Configuration myconfig, DB db) {
		if (request.parameterExists("birthdate")) birthdate = request.getParameter("birthdate");
		if (request.parameterExists("birthyear")) birthyear = request.getParameter("birthyear");
		if (request.parameterExists("birthmonth")) birthmonth = request.getParameter("birthmonth");
		if (request.parameterExists("birthday")) birthday = request.getParameter("birthday");
		if (request.parameterExists("gender")) gender = request.getParameter("gender");
		if (request.parameterExists("title")) title = request.getParameter("title");
		if (request.parameterExists("name")) name = request.getParameter("name");
		if (request.parameterExists("organisation")) organisation = request.getParameter("organisation");
		if (request.parameterExists("username")) username = request.getParameter("username");
		if (request.parameterExists("password")) password = request.getParameter("password");
		if (request.parameterExists("email")) email = request.getParameter("email");
		if (myconfig.get(db, "use_metainformation").equals("yes")) {
			if (request.parameterExists("keywords")) keywords = request.getParameter("keywords");
			if (request.parameterExists("description")) description = request.getParameter("description");
		}
		if (administrator) {
			if (myconfig.get(db, "use_userdefinition").equals("yes")) {
				if (request.parameterExists("userclass")) { userclass = request.getParameter("userclass"); } else { userclass = ""; }
				if (myconfig.get(db, "use_usertypes").equals("yes")) {
					if (request.parameterExists("usertype")) usertype = request.getParameter("usertype");
					additional_usertypes = new HashMap<String,String>();
					String[] myusertypes = request.getParameters("usertypes");
					for (int i=0; i<myusertypes.length; i++) {
						String myusertype = myusertypes[i];
						additional_usertypes.put(myusertype, myusertype);
					}
				}
				if (myconfig.get(db, "use_usergroups").equals("yes")) {
					if (request.parameterExists("usergroup")) usergroup = request.getParameter("usergroup");
					additional_usergroups = new HashMap<String,String>();
					String[] myusergroups = request.getParameters("usergroups");
					for (int i=0; i<myusergroups.length; i++) {
						String myusergroup = myusergroups[i];
						additional_usergroups.put(myusergroup, myusergroup);
					}
				}
			}
			if (! myconfig.get(db, "use_accessrestrictions").equals("none")) {
				if (request.parameterExists("users_type")) users_type = request.getParameter("users_type");
				if (request.parameterExists("users_group")) users_group = request.getParameter("users_group");
				if (myconfig.get(db, "use_accessrestrictions").equals("all")) {
					if (request.parameterExists("creators_type")) creators_type = request.getParameter("creators_type");
					if (request.parameterExists("creators_group")) creators_group = request.getParameter("creators_group");
					if (request.parameterExists("editors_type")) editors_type = request.getParameter("editors_type");
					if (request.parameterExists("editors_group")) editors_group = request.getParameter("editors_group");
					if (request.parameterExists("publishers_type")) publishers_type = request.getParameter("publishers_type");
					if (request.parameterExists("publishers_group")) publishers_group = request.getParameter("publishers_group");
					if (request.parameterExists("administrators_type")) administrators_type = request.getParameter("administrators_type");
					if (request.parameterExists("administrators_group")) administrators_group = request.getParameter("administrators_group");
				}
			}
		}
		if (request.parameterExists("content_editor")) content_editor = request.getParameter("content_editor");
		if (request.parameterExists("hardcore_encoding")) hardcore_encoding = request.getParameter("hardcore_encoding");
		if (request.parameterExists("hardcore_language")) hardcore_language = request.getParameter("hardcore_language");
		if (request.parameterExists("hardcore_upload")) hardcore_upload = request.getParameter("hardcore_upload");
		if (request.parameterExists("hardcore_format")) hardcore_format = request.getParameter("hardcore_format");
		if (request.parameterExists("hardcore_width")) hardcore_width = request.getParameter("hardcore_width");
		if (request.parameterExists("hardcore_height")) hardcore_height = request.getParameter("hardcore_height");
		if (request.parameterExists("hardcore_onenter")) hardcore_onenter = request.getParameter("hardcore_onenter");
		if (request.parameterExists("hardcore_onctrlenter")) hardcore_onctrlenter = request.getParameter("hardcore_onctrlenter");
		if (request.parameterExists("hardcore_onshiftenter")) hardcore_onshiftenter = request.getParameter("hardcore_onshiftenter");
		if (request.parameterExists("hardcore_onaltenter")) hardcore_onaltenter = request.getParameter("hardcore_onaltenter");
		if (request.parameterExists("hardcore_toolbar1")) hardcore_toolbar1 = request.getParameter("hardcore_toolbar1");
		if (request.parameterExists("hardcore_toolbar2")) hardcore_toolbar2 = request.getParameter("hardcore_toolbar2");
		if (request.parameterExists("hardcore_toolbar3")) hardcore_toolbar3 = request.getParameter("hardcore_toolbar3");
		if (request.parameterExists("hardcore_toolbar4")) hardcore_toolbar4 = request.getParameter("hardcore_toolbar4");
		if (request.parameterExists("hardcore_toolbar5")) hardcore_toolbar5 = request.getParameter("hardcore_toolbar5");
		if (request.parameterExists("hardcore_formatblock")) hardcore_formatblock = request.getParameter("hardcore_formatblock");
		if (request.parameterExists("hardcore_fontname")) hardcore_fontname = request.getParameter("hardcore_fontname");
		if (request.parameterExists("hardcore_fontsize")) hardcore_fontsize = request.getParameter("hardcore_fontsize");
		if (request.parameterExists("hardcore_customscript")) hardcore_customscript = request.getParameter("hardcore_customscript");
		if (request.parameterExists("index_workspace")) index_workspace = request.getParameter("index_workspace");
		if (request.parameterExists("index_content")) index_content = request.getParameter("index_content");
		if (request.parameterExists("index_library")) index_library = request.getParameter("index_library");
		if (request.parameterExists("index_product")) index_product = request.getParameter("index_product");
		if (request.parameterExists("index_stock")) index_stock = request.getParameter("index_stock");
		if (request.parameterExists("index_order")) index_order = request.getParameter("index_order");
		if (request.parameterExists("index_segments")) index_segments = request.getParameter("index_segments");
		if (request.parameterExists("index_usertests")) index_usertests = request.getParameter("index_usertests");
		if (request.parameterExists("index_heatmaps")) index_heatmaps = request.getParameter("index_heatmaps");
		if (request.parameterExists("index_sales")) index_sales = request.getParameter("index_sales");
		if (request.parameterExists("index_databases")) index_databases = request.getParameter("index_databases");
		if (request.parameterExists("index_user")) index_user = request.getParameter("index_user");
		if (request.parameterExists("index_hosting")) index_hosting = request.getParameter("index_hosting");
		if (request.parameterExists("index_websites")) index_websites = request.getParameter("index_websites");
		if (request.parameterExists("menu_selection")) menu_selection = request.getParameter("menu_selection");
		if (request.parameterExists("workspace_sections")) workspace_sections = request.getParameter("workspace_sections");
		if (request.parameterExists("statistics_reports")) statistics_reports = request.getParameter("statistics_reports");
		if (request.parameterExists("sales_reports")) sales_reports = request.getParameter("sales_reports");
		if (request.parameterExists("scheduled_publish")) scheduled_publish = "" + request.getParameter("scheduled_publish");
		if (request.parameterExists("scheduled_notify")) scheduled_notify = "" + request.getParameter("scheduled_notify");
		if (request.parameterExists("scheduled_unpublish")) scheduled_unpublish = "" + request.getParameter("scheduled_unpublish");
		if (request.parameterExists("scheduled_publish_email")) scheduled_publish_email = "" + request.getParameter("scheduled_publish_email");
		if (request.parameterExists("scheduled_notify_email")) scheduled_notify_email = "" + request.getParameter("scheduled_notify_email");
		if (request.parameterExists("scheduled_unpublish_email")) scheduled_unpublish_email = "" + request.getParameter("scheduled_unpublish_email");
		if (request.parameterExists("card_type")) card_type = request.getParameter("card_type");
		if (request.parameterExists("card_number")) card_number = request.getParameter("card_number");
		if (request.parameterExists("card_issuedmonth")) card_issuedmonth = request.getParameter("card_issuedmonth");
		if (request.parameterExists("card_issuedyear")) card_issuedyear = request.getParameter("card_issuedyear");
		if (request.parameterExists("card_expirymonth")) card_expirymonth = request.getParameter("card_expirymonth");
		if (request.parameterExists("card_expiryyear")) card_expiryyear = request.getParameter("card_expiryyear");
		if (request.parameterExists("card_name")) card_name = request.getParameter("card_name");
		if (request.parameterExists("card_cvc")) card_cvc = request.getParameter("card_cvc");
		if (request.parameterExists("card_issue")) card_issue = request.getParameter("card_issue");
		if (request.parameterExists("card_postalcode")) card_postalcode = request.getParameter("card_postalcode");
		if (request.parameterExists("delivery_name")) delivery_name = request.getParameter("delivery_name");
		if (request.parameterExists("delivery_organisation")) delivery_organisation = request.getParameter("delivery_organisation");
		if (request.parameterExists("delivery_address")) delivery_address = request.getParameter("delivery_address");
		if (request.parameterExists("delivery_postalcode")) delivery_postalcode = request.getParameter("delivery_postalcode");
		if (request.parameterExists("delivery_city")) delivery_city = request.getParameter("delivery_city");
		if (request.parameterExists("delivery_state")) delivery_state = request.getParameter("delivery_state");
		if (request.parameterExists("delivery_country")) delivery_country = request.getParameter("delivery_country");
		if (request.parameterExists("delivery_phone")) delivery_phone = request.getParameter("delivery_phone");
		if (request.parameterExists("delivery_fax")) delivery_fax = request.getParameter("delivery_fax");
		if (request.parameterExists("delivery_email")) delivery_email = request.getParameter("delivery_email");
		if (request.parameterExists("delivery_website")) delivery_website = request.getParameter("delivery_website");
		if (request.parameterExists("invoice_name")) invoice_name = request.getParameter("invoice_name");
		if (request.parameterExists("invoice_organisation")) invoice_organisation = request.getParameter("invoice_organisation");
		if (request.parameterExists("invoice_address")) invoice_address = request.getParameter("invoice_address");
		if (request.parameterExists("invoice_postalcode")) invoice_postalcode = request.getParameter("invoice_postalcode");
		if (request.parameterExists("invoice_city")) invoice_city = request.getParameter("invoice_city");
		if (request.parameterExists("invoice_state")) invoice_state = request.getParameter("invoice_state");
		if (request.parameterExists("invoice_country")) invoice_country = request.getParameter("invoice_country");
		if (request.parameterExists("invoice_phone")) invoice_phone = request.getParameter("invoice_phone");
		if (request.parameterExists("invoice_fax")) invoice_fax = request.getParameter("invoice_fax");
		if (request.parameterExists("invoice_email")) invoice_email = request.getParameter("invoice_email");
		if (request.parameterExists("invoice_website")) invoice_website = request.getParameter("invoice_website");
		if (request.parameterExists("notes")) notes = request.getParameter("notes");
		if (request.parameterExists("userinfo")) userinfo = request.getParameter("userinfo");
		if (request.parameterExists("shopcart")) shopcart = request.getParameter("shopcart");
		if (request.parameterExists("wishlist")) wishlist = request.getParameter("wishlist");
		Enumeration parameternames = request.getParameterNames();
		while (parameternames.hasMoreElements()) {
			String inputname = "" + parameternames.nextElement();
			String inputvalue = request.getParameter(inputname);
			if (inputname.startsWith("userinfo_")) {
				String myinputname = inputname.replaceAll("^userinfo_", "").replaceAll("_", " ");
				userinfo = userinfo.replaceAll("<" + myinputname + ">" + ".*" + "</" + myinputname + ">", ("<" + myinputname + ">" + inputvalue + "</" + myinputname + ">").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			}
		}
	}



	public void create(DB db) {
		if (db == null) return;
		if (username.equals("")) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("failed", "0");
		mydata.put("created", "" + created);
		mydata.put("updated", "" + updated);
		mydata.put("created_by", "" + created_by);
		mydata.put("updated_by", "" + updated_by);
		mydata.put("keywords", "" + db.encrypt(keywords));
		mydata.put("description", "" + db.encrypt(description));
		mydata.put("birthdate", "" + db.encrypt(birthdate));
		mydata.put("birthyear", "" + db.encrypt(birthyear));
		mydata.put("birthmonth", "" + db.encrypt(birthmonth));
		mydata.put("birthday", "" + db.encrypt(birthday));
		mydata.put("gender", "" + db.encrypt(gender));
		mydata.put("title", "" + db.encrypt(title));
		mydata.put("name", "" + name);
		mydata.put("organisation", "" + organisation);
		mydata.put("username", "" + username);
		if (DIGEST) {
			mydata.put("password", "" + db.digest(password, 250));
		} else {
			mydata.put("password", "" + db.encrypt(password));
		}
		mydata.put("email", "" + db.encrypt(email));
		mydata.put("usertype", "" + usertype);
		mydata.put("usergroup", "" + usergroup);
		mydata.put("userclass", "" + userclass);
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
		mydata.put("content_editor", "" + content_editor);
		mydata.put("hardcore_encoding", "" + hardcore_encoding);
		mydata.put("hardcore_language", "" + hardcore_language);
		mydata.put("hardcore_upload", "" + hardcore_upload);
		mydata.put("hardcore_format", "" + hardcore_format);
		mydata.put("hardcore_width", "" + hardcore_width);
		mydata.put("hardcore_height", "" + hardcore_height);
		mydata.put("hardcore_onenter", "" + hardcore_onenter);
		mydata.put("hardcore_onctrlenter", "" + hardcore_onctrlenter);
		mydata.put("hardcore_onshiftenter", "" + hardcore_onshiftenter);
		mydata.put("hardcore_onaltenter", "" + hardcore_onaltenter);
		mydata.put("hardcore_toolbar1", "" + hardcore_toolbar1);
		mydata.put("hardcore_toolbar2", "" + hardcore_toolbar2);
		mydata.put("hardcore_toolbar3", "" + hardcore_toolbar3);
		mydata.put("hardcore_toolbar4", "" + hardcore_toolbar4);
		mydata.put("hardcore_toolbar5", "" + hardcore_toolbar5);
		mydata.put("hardcore_formatblock", "" + hardcore_formatblock);
		mydata.put("hardcore_fontname", "" + hardcore_fontname);
		mydata.put("hardcore_fontsize", "" + hardcore_fontsize);
		mydata.put("hardcore_customscript", "" + hardcore_customscript);
		mydata.put("index_workspace", "" + index_workspace);
		mydata.put("index_content", "" + index_content);
		mydata.put("index_library", "" + index_library);
		mydata.put("index_product", "" + index_product);
		mydata.put("index_stock", "" + index_stock);
		mydata.put("index_order", "" + index_order);
		mydata.put("index_segments", "" + index_segments);
		mydata.put("index_usertests", "" + index_usertests);
		mydata.put("index_heatmaps", "" + index_heatmaps);
		mydata.put("index_sales", "" + index_sales);
		mydata.put("index_databases", "" + index_databases);
		mydata.put("index_user", "" + index_user);
		mydata.put("index_hosting", "" + index_hosting);
		mydata.put("index_websites", "" + index_websites);
		mydata.put("menu_selection", "" + menu_selection);
		mydata.put("workspace_sections", "" + workspace_sections);
		mydata.put("statistics_reports", "" + statistics_reports);
		mydata.put("sales_reports", "" + sales_reports);
		mydata.put("scheduled_last", "" + scheduled_last);
		mydata.put("scheduled_publish", "" + scheduled_publish);
		mydata.put("scheduled_notify", "" + scheduled_notify);
		mydata.put("scheduled_unpublish", "" + scheduled_unpublish);
		mydata.put("scheduled_publish_email", "" + scheduled_publish_email);
		mydata.put("scheduled_notify_email", "" + scheduled_notify_email);
		mydata.put("scheduled_unpublish_email", "" + scheduled_unpublish_email);
		mydata.put("card_type", "" + db.encrypt(card_type));
		mydata.put("card_number", "" + db.encrypt(card_number));
		mydata.put("card_issuedmonth", "" + db.encrypt(card_issuedmonth));
		mydata.put("card_issuedyear", "" + db.encrypt(card_issuedyear));
		mydata.put("card_expirymonth", "" + db.encrypt(card_expirymonth));
		mydata.put("card_expiryyear", "" + db.encrypt(card_expiryyear));
		mydata.put("card_name", "" + db.encrypt(card_name));
		mydata.put("card_cvc", "" + db.encrypt(card_cvc));
		mydata.put("card_issue", "" + db.encrypt(card_issue));
		mydata.put("card_postalcode", "" + db.encrypt(card_postalcode));
		mydata.put("delivery_name", "" + db.encrypt(delivery_name));
		mydata.put("delivery_organisation", "" + db.encrypt(delivery_organisation));
		mydata.put("delivery_address", "" + db.encrypt(delivery_address));
		mydata.put("delivery_postalcode", "" + db.encrypt(delivery_postalcode));
		mydata.put("delivery_city", "" + db.encrypt(delivery_city));
		mydata.put("delivery_state", "" + db.encrypt(delivery_state));
		mydata.put("delivery_country", "" + db.encrypt(delivery_country));
		mydata.put("delivery_phone", "" + db.encrypt(delivery_phone));
		mydata.put("delivery_fax", "" + db.encrypt(delivery_fax));
		mydata.put("delivery_email", "" + db.encrypt(delivery_email));
		mydata.put("delivery_website", "" + db.encrypt(delivery_website));
		mydata.put("invoice_name", "" + db.encrypt(invoice_name));
		mydata.put("invoice_organisation", "" + db.encrypt(invoice_organisation));
		mydata.put("invoice_address", "" + db.encrypt(invoice_address));
		mydata.put("invoice_postalcode", "" + db.encrypt(invoice_postalcode));
		mydata.put("invoice_city", "" + db.encrypt(invoice_city));
		mydata.put("invoice_state", "" + db.encrypt(invoice_state));
		mydata.put("invoice_country", "" + db.encrypt(invoice_country));
		mydata.put("invoice_phone", "" + db.encrypt(invoice_phone));
		mydata.put("invoice_fax", "" + db.encrypt(invoice_fax));
		mydata.put("invoice_email", "" + db.encrypt(invoice_email));
		mydata.put("invoice_website", "" + db.encrypt(invoice_website));
		mydata.put("notes", "" + db.encrypt(notes));
		mydata.put("userinfo", "" + db.encrypt(userinfo));
		mydata.put("shopcart", "" + db.encrypt(shopcart));
		mydata.put("wishlist", "" + db.encrypt(wishlist));
		db.insert("users", mydata);
		db.delete("users_usertypes", "username", username);
		Iterator myusertypes = additional_usertypes.keySet().iterator();
		while (myusertypes.hasNext()) {
			String myusertype = "" + myusertypes.next();
			addUsertype(db, myusertype);
		}
		db.delete("users_usergroups", "username", username);
		Iterator myusergroups = additional_usergroups.keySet().iterator();
		while (myusergroups.hasNext()) {
			String myusergroup = "" + myusergroups.next();
			addUsergroup(db, myusergroup);
		}
	}



	public void update(DB db) {
		if (db == null) return;
		if (username.equals("")) return;
		if ((id != null) && (! id.equals(""))) {
			User olduser = new User();
			olduser.read(db, id);
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("failed", "0");
			mydata.put("created", "" + created);
			mydata.put("updated", "" + updated);
			mydata.put("created_by", "" + created_by);
			mydata.put("updated_by", "" + updated_by);
			mydata.put("keywords", "" + db.encrypt(keywords));
			mydata.put("description", "" + db.encrypt(description));
			mydata.put("birthdate", "" + db.encrypt(birthdate));
			mydata.put("birthyear", "" + db.encrypt(birthyear));
			mydata.put("birthmonth", "" + db.encrypt(birthmonth));
			mydata.put("birthday", "" + db.encrypt(birthday));
			mydata.put("gender", "" + db.encrypt(gender));
			mydata.put("title", "" + db.encrypt(title));
			mydata.put("name", "" + name);
			mydata.put("organisation", "" + organisation);
			mydata.put("username", "" + username);
			if (DIGEST) {
				if (! password.equals(olduser.getPassword())) {
					mydata.put("password", "" + db.digest(password, 250));
				} else {
					mydata.put("password", "" + password);
				}
				olduser = null;
			} else {
				mydata.put("password", "" + db.encrypt(password));
			}
			mydata.put("email", "" + db.encrypt(email));
			mydata.put("usertype", "" + usertype);
			mydata.put("usergroup", "" + usergroup);
			mydata.put("userclass", "" + userclass);
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
			mydata.put("content_editor", "" + content_editor);
			mydata.put("hardcore_encoding", "" + hardcore_encoding);
			mydata.put("hardcore_language", "" + hardcore_language);
			mydata.put("hardcore_upload", "" + hardcore_upload);
			mydata.put("hardcore_format", "" + hardcore_format);
			mydata.put("hardcore_width", "" + hardcore_width);
			mydata.put("hardcore_height", "" + hardcore_height);
			mydata.put("hardcore_onenter", "" + hardcore_onenter);
			mydata.put("hardcore_onctrlenter", "" + hardcore_onctrlenter);
			mydata.put("hardcore_onshiftenter", "" + hardcore_onshiftenter);
			mydata.put("hardcore_onaltenter", "" + hardcore_onaltenter);
			mydata.put("hardcore_toolbar1", "" + hardcore_toolbar1);
			mydata.put("hardcore_toolbar2", "" + hardcore_toolbar2);
			mydata.put("hardcore_toolbar3", "" + hardcore_toolbar3);
			mydata.put("hardcore_toolbar4", "" + hardcore_toolbar4);
			mydata.put("hardcore_toolbar5", "" + hardcore_toolbar5);
			mydata.put("hardcore_formatblock", "" + hardcore_formatblock);
			mydata.put("hardcore_fontname", "" + hardcore_fontname);
			mydata.put("hardcore_fontsize", "" + hardcore_fontsize);
			mydata.put("hardcore_customscript", "" + hardcore_customscript);
			mydata.put("index_workspace", "" + index_workspace);
			mydata.put("index_content", "" + index_content);
			mydata.put("index_library", "" + index_library);
			mydata.put("index_product", "" + index_product);
			mydata.put("index_stock", "" + index_stock);
			mydata.put("index_order", "" + index_order);
			mydata.put("index_segments", "" + index_segments);
			mydata.put("index_usertests", "" + index_usertests);
			mydata.put("index_heatmaps", "" + index_heatmaps);
			mydata.put("index_sales", "" + index_sales);
			mydata.put("index_databases", "" + index_databases);
			mydata.put("index_user", "" + index_user);
			mydata.put("index_hosting", "" + index_hosting);
			mydata.put("index_websites", "" + index_websites);
			mydata.put("menu_selection", "" + menu_selection);
			mydata.put("workspace_sections", "" + workspace_sections);
			mydata.put("statistics_reports", "" + statistics_reports);
			mydata.put("sales_reports", "" + sales_reports);
			mydata.put("scheduled_last", "" + scheduled_last);
			mydata.put("scheduled_publish", "" + scheduled_publish);
			mydata.put("scheduled_notify", "" + scheduled_notify);
			mydata.put("scheduled_unpublish", "" + scheduled_unpublish);
			mydata.put("scheduled_publish_email", "" + scheduled_publish_email);
			mydata.put("scheduled_notify_email", "" + scheduled_notify_email);
			mydata.put("scheduled_unpublish_email", "" + scheduled_unpublish_email);
			mydata.put("card_type", "" + db.encrypt(card_type));
			mydata.put("card_number", "" + db.encrypt(card_number));
			mydata.put("card_issuedmonth", "" + db.encrypt(card_issuedmonth));
			mydata.put("card_issuedyear", "" + db.encrypt(card_issuedyear));
			mydata.put("card_expirymonth", "" + db.encrypt(card_expirymonth));
			mydata.put("card_expiryyear", "" + db.encrypt(card_expiryyear));
			mydata.put("card_name", "" + db.encrypt(card_name));
			mydata.put("card_cvc", "" + db.encrypt(card_cvc));
			mydata.put("card_issue", "" + db.encrypt(card_issue));
			mydata.put("card_postalcode", "" + db.encrypt(card_postalcode));
			mydata.put("delivery_name", "" + db.encrypt(delivery_name));
			mydata.put("delivery_organisation", "" + db.encrypt(delivery_organisation));
			mydata.put("delivery_address", "" + db.encrypt(delivery_address));
			mydata.put("delivery_postalcode", "" + db.encrypt(delivery_postalcode));
			mydata.put("delivery_city", "" + db.encrypt(delivery_city));
			mydata.put("delivery_state", "" + db.encrypt(delivery_state));
			mydata.put("delivery_country", "" + db.encrypt(delivery_country));
			mydata.put("delivery_phone", "" + db.encrypt(delivery_phone));
			mydata.put("delivery_fax", "" + db.encrypt(delivery_fax));
			mydata.put("delivery_email", "" + db.encrypt(delivery_email));
			mydata.put("delivery_website", "" + db.encrypt(delivery_website));
			mydata.put("invoice_name", "" + db.encrypt(invoice_name));
			mydata.put("invoice_organisation", "" + db.encrypt(invoice_organisation));
			mydata.put("invoice_address", "" + db.encrypt(invoice_address));
			mydata.put("invoice_postalcode", "" + db.encrypt(invoice_postalcode));
			mydata.put("invoice_city", "" + db.encrypt(invoice_city));
			mydata.put("invoice_state", "" + db.encrypt(invoice_state));
			mydata.put("invoice_country", "" + db.encrypt(invoice_country));
			mydata.put("invoice_phone", "" + db.encrypt(invoice_phone));
			mydata.put("invoice_fax", "" + db.encrypt(invoice_fax));
			mydata.put("invoice_email", "" + db.encrypt(invoice_email));
			mydata.put("invoice_website", "" + db.encrypt(invoice_website));
			mydata.put("notes", "" + db.encrypt(notes));
			mydata.put("userinfo", "" + db.encrypt(userinfo));
			mydata.put("shopcart", "" + db.encrypt(shopcart));
			mydata.put("wishlist", "" + db.encrypt(wishlist));
			db.update("users", "id", id, mydata);
			db.delete("users_usertypes", "username", olduser.getUsername());
			db.delete("users_usertypes", "username", username);
			Iterator myusertypes = additional_usertypes.keySet().iterator();
			while (myusertypes.hasNext()) {
				String myusertype = "" + myusertypes.next();
				addUsertype(db, myusertype);
			}
			db.delete("users_usergroups", "username", olduser.getUsername());
			db.delete("users_usergroups", "username", username);
			Iterator myusergroups = additional_usergroups.keySet().iterator();
			while (myusergroups.hasNext()) {
				String myusergroup = "" + myusergroups.next();
				addUsergroup(db, myusergroup);
			}
		}
	}



	public void loginSuccess(DB db) {
		failed = 0;
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("failed", "0");
			db.update("users", "username", username, mydata);
		}
	}



	public boolean loginFailure(DB db, String limit) {
		failed++;
		if (db == null) return false;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("failed", "0");
			db.updateSet("users", "username", username, "failed=failed+1");

			if ((failed >= Common.number(limit)) && (Common.number(limit) > 0)) {
				String mypassword = "";
				for (int j=0; j<32; j++) {
					mypassword = "" + mypassword + (char)('a' + Integer.parseInt(Common.numberformat("" + Math.random()*25, 0)));
				}
				password = mypassword;

				mydata = new HashMap<String,String>();
				mydata.put("password", db.encrypt(password));
				db.update("users", "username", username, mydata);
				return true;
			}
		}
		return false;
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.deleteWhere("users", "(locked=0 or locked is null) and id=" + Common.integer(id));
			db.delete("users_usertypes", "username", username);
			db.delete("users_usergroups", "username", username);
			if (! username.equals("")) {
				db.delete("permissions", "username", username);
			}
		}
	}



	public void addUsergroup(DB db, String usergroup) {
		if (db == null) return;
		if (! usergroup.equals("")) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("username", "" + username);
			mydata.put("usergroup", "" + usergroup);
			db.insert("users_usergroups", mydata);
		}
	}



	public void addUsertype(DB db, String usertype) {
		if (db == null) return;
		if (! usertype.equals("")) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("username", "" + username);
			mydata.put("usertype", "" + usertype);
			db.insert("users_usertypes", mydata);
		}
	}



	public void removeUsergroup(DB db, String usergroup) {
		if (db == null) return;
		if (! usergroup.equals("")) {
			db.delete("users_usergroups", "username", username, "usergroup", usergroup);
		}
	}



	public void removeUsertype(DB db, String usertype) {
		if (db == null) return;
		if (! usertype.equals("")) {
			db.delete("users_usertypes", "username", username, "usertype", usertype);
		}
	}



	public void renameUsername(DB db, String old_username, String new_username) {
		if (db == null) return;
		if (old_username.equals("")) return;
		if (new_username.equals("")) return;
		db.rename("users", "created_by", old_username, new_username);
		db.rename("users", "updated_by", old_username, new_username);
		db.rename("users_usergroups", "username", old_username, new_username);
		db.rename("users_usertypes", "username", old_username, new_username);
		db.rename("permissions", "username", old_username, new_username);
	}



	public void renameUsergroup(DB db, String old_usergroup, String new_usergroup) {
		if (db == null) return;
		db.rename("users", "usergroup", old_usergroup, new_usergroup);
		db.rename("users", "users_group", old_usergroup, new_usergroup);
		db.rename("users", "creators_group", old_usergroup, new_usergroup);
		db.rename("users", "editors_group", old_usergroup, new_usergroup);
		db.rename("users", "publishers_group", old_usergroup, new_usergroup);
		db.rename("users", "administrators_group", old_usergroup, new_usergroup);
		db.rename("users_usergroups", "usergroup", old_usergroup, new_usergroup);
		db.rename("permissions", "usergroup", old_usergroup, new_usergroup);
	}



	public void renameUsertype(DB db, String old_usertype, String new_usertype) {
		if (db == null) return;
		db.rename("users", "usertype", old_usertype, new_usertype);
		db.rename("users", "users_type", old_usertype, new_usertype);
		db.rename("users", "creators_type", old_usertype, new_usertype);
		db.rename("users", "editors_type", old_usertype, new_usertype);
		db.rename("users", "publishers_type", old_usertype, new_usertype);
		db.rename("users", "administrators_type", old_usertype, new_usertype);
		db.rename("users_usertypes", "usertype", old_usertype, new_usertype);
		db.rename("permissions", "usertype", old_usertype, new_usertype);
	}



	public void closeRecords(DB db) {
		if (db == null) return;
		if (rs != null) db.closeRecords(rs);
		rs = null;
//		init();
	}



	public boolean records(String session_username, String session_usertype, String session_usergroup, String session_usertypes, String session_usergroups, DB db, Configuration config, String SQL) {
		if ((db == null) || db.isClosed()) return false;
		if ((SQL != null) && (! SQL.equals(""))) {
			rs = db.records(SQL);
			return true;
		} else {
			HashMap<String,String> row = db.records(rs);
			if (row != null) {
				getRecord(db, row);
				getAccessRestrictions(session_username, session_usertype, session_usergroup, session_usertypes, session_usergroups, db, config);
				return true;
			} else {
				return false;
			}
		}
	}



	public void schedule(Configuration myconfig, DB db) {
		if (! getScheduledPublish().equals("")) {
			if ((getScheduledPublish().compareTo(myconfig.get(db, "scheduled_next")) < 0) || (myconfig.get(db, "scheduled_next").equals(""))) {
				myconfig.set(db, "scheduled_next", getScheduledPublish());
			}
		}
		if (! getScheduledNotify().equals("")) {
			if ((getScheduledNotify().compareTo(myconfig.get(db, "scheduled_next")) < 0) || (myconfig.get(db, "scheduled_next").equals(""))) {
				myconfig.set(db, "scheduled_next", getScheduledNotify());
			}
		}
		if (! getScheduledUnpublish().equals("")) {
			if ((getScheduledUnpublish().compareTo(myconfig.get(db, "scheduled_next")) < 0) || (myconfig.get(db, "scheduled_next").equals(""))) {
				myconfig.set(db, "scheduled_next", getScheduledUnpublish());
			}
		}
	}



	public HashMap<String,String> usertypes(DB db) {
		if (usertypes_cache != null) {
			return usertypes_cache;
		} else if (directoryentry_usertypes != null) {
			HashMap<String,String> mytypes = new HashMap<String,String>();
			try {
				NamingEnumeration values = directoryentry_usertypes.getAll();
				while (values.hasMore()) {
					String value = "" + values.next();
					value = value.replaceAll("^[a-zA-Z0-9]+=(.+?)(,.+?$|$)", "$1");
					mytypes.put(value, value);
				}
			} catch (Exception e) {
			}
			return mytypes;
		} else {
			Usertype usertypes = new Usertype();
			usertypes.setUsertype(usertype);
			HashMap<String,String> mytypes = usertypes.subtypes(db);
			mytypes = additionalusertypes(db, mytypes);
			return mytypes;
		}
	}



	public HashMap<String,String> usergroups(DB db) {
		if (usergroups_cache != null) {
			return usergroups_cache;
		} else if (directoryentry_usergroups != null) {
			HashMap<String,String> mygroups = new HashMap<String,String>();
			try {
				NamingEnumeration values = directoryentry_usergroups.getAll();
				while (values.hasMore()) {
					String value = "" + values.next();
					value = value.replaceAll("^[a-zA-Z0-9]+=(.+?)(,.+?$|$)", "$1");
					mygroups.put(value, value);
				}
			} catch (Exception e) {
			}
			return mygroups;
		} else {
			Usergroup usergroups = new Usergroup();
			usergroups.setUsergroup(usergroup);
			HashMap<String,String> mygroups = usergroups.subgroups(db);
			mygroups = additionalusergroups(db, mygroups);
			return mygroups;
		}
	}



	public HashMap<String,String> additionalusertypes(DB db, HashMap<String,String> mytypes) {
		if (mytypes == null) {
			mytypes = new HashMap<String,String>();
		}
		if (db == null) return mytypes;
		String SQL = "select usertype from users_usertypes where username=" + db.quote(Common.SQL_clean(getUsername())) + " order by usertype";
		LinkedHashMap<String,HashMap<String,String>> records = db.query_records(SQL);
		for (int i=0; i<records.size(); i++) {
			HashMap<String,String> row = (HashMap<String,String>)records.get("" + i);
			String myusertype = "" + row.get("usertype");
			mytypes.put(myusertype, myusertype);
		}
		return mytypes;
	}



	public HashMap<String,String> additionalusergroups(DB db, HashMap<String,String> mygroups) {
		if (mygroups == null) {
			mygroups = new HashMap<String,String>();
		}
		if (db == null) return mygroups;
		String SQL = "select usergroup from users_usergroups where username=" + db.quote(Common.SQL_clean(getUsername())) + " order by usergroup";
		LinkedHashMap<String,HashMap<String,String>> records = db.query_records(SQL);
		for (int i=0; i<records.size(); i++) {
			HashMap<String,String> row = (HashMap<String,String>)records.get("" + i);
			String myusergroup = "" + row.get("usergroup");
			mygroups.put(myusergroup, myusergroup);
		}
		return mygroups;
	}



	public String select_options(DB db, String selected) {
		return select_options_where(db, selected, "");
	}



	public String template_select_options(DB db, String selected) {
		return select_options_where(db, selected, "where password=''");
	}



	public String select_options_where(DB db, String selected, String SQLwhere) {
		return Common.select_options_where(db, "users", "username", "username", selected, SQLwhere, "id");
	}



	static public String Username(DB db, Configuration config, String id) {
		User myuser = new User();
		myuser.read("", "", "", "", "", db, config, id);
		return myuser.getUsername();
	}



	public String usergroup_select_options(DB db, String selected) {
		Usergroup usergroup = new Usergroup();
		return usergroup.select_options(db, selected);
	}



	public String usertype_select_options(DB db, String selected) {
		Usertype usertype = new Usertype();
		return usertype.select_options(db, selected);
	}



	public String usergroups_checkboxes(DB db) {
		String output = "";
		HashMap<String,String> myusergroups = usergroups(db);
		Usergroup usergroup = new Usergroup();
		HashMap<String,String> allusergroups = usergroup.usergroups(db);
		Iterator allusergroup = Common.SortedHashMapKeySetIterator(allusergroups);
		while (allusergroup.hasNext()) {
			String myusergroup = "" + allusergroup.next();
			output += "<input type=\"checkbox\" name=\"usergroups\" value=\"" + myusergroup + "\"";
			if ((myusergroups.get(myusergroup) != null) && (! myusergroups.get(myusergroup).equals(""))) {
				output += " checked";
			}
			output += "> " + myusergroup + "<br>" + "\r\n";
		}
		return output;
	}



	public String usertypes_checkboxes(DB db) {
		String output = "";
		HashMap<String,String> myusertypes = usertypes(db);
		Usertype usertype = new Usertype();
		HashMap<String,String> allusertypes = usertype.usertypes(db);
		Iterator allusertype = Common.SortedHashMapKeySetIterator(allusertypes);
		while (allusertype.hasNext()) {
			String myusertype = "" + allusertype.next();
			output += "<input type=\"checkbox\" name=\"usertypes\" value=\"" + myusertype + "\"";
			if ((myusertypes.get(myusertype) != null) && (! myusertypes.get(myusertype).equals(""))) {
				output += " checked";
			}
			output += "> " + myusertype + "<br>" + "\r\n";
		}
		return output;
	}



	public String usergroups_list(DB db) {
		String output = "";
		HashMap<String,String> myusergroups = usergroups(db);
		Usergroup usergroup = new Usergroup();
		HashMap<String,String> allusergroups = usergroup.usergroups(db);
		Iterator allusergroup = Common.SortedHashMapKeySetIterator(allusergroups);
		while (allusergroup.hasNext()) {
			String myusergroup = "" + allusergroup.next();
			if ((myusergroups.get(myusergroup) != null) && (! myusergroups.get(myusergroup).equals(""))) {
				if (! output.equals("")) {
					output += "<br>" + "\r\n";
				}
				output += "" + myusergroup;
			}
		}
		return output;
	}



	public String usertypes_list(DB db) {
		String output = "";
		HashMap<String,String> myusertypes = usertypes(db);
		Usertype usertype = new Usertype();
		HashMap<String,String> allusertypes = usertype.usertypes(db);
		Iterator allusertype = Common.SortedHashMapKeySetIterator(allusertypes);
		while (allusertype.hasNext()) {
			String myusertype = "" + allusertype.next();
			if ((myusertypes.get(myusertype) != null) && (! myusertypes.get(myusertype).equals(""))) {
				if (! output.equals("")) {
					output += "<br>" + "\r\n";
				}
				output += "" + myusertype;
			}
		}
		return output;
	}



	public HashMap<String,Permission> permissions(DB db, String action, String resource) {
		Permission mypermissions = new Permission();
		mypermissions.setAction(action);
		mypermissions.setResource(resource);
		mypermissions.setUsername(username);
		return mypermissions.permissions(db);
	}



	public boolean permission(DB db, String action, String resource) {
		if ((directoryentry_permission != null) && (directoryentry_permission.get(resource) != null)) {
			if (action.equals("forbid")) {
				if (directoryentry_permission.get(resource).equals("")) {
					return false;
				} else {
					return true;
				}
			} else {
				if (directoryentry_permission.get(resource).equals("")) {
					return true;
				} else {
					return false;
				}
			}
		}
		HashMap<String,Permission> mypermissions = permissions(db, action, resource);
		if (mypermissions.size() > 0) {
			return true;
		} else {
			return false;
		}
	}



	public String administratorsSQLwhere(DB db, boolean administrators, boolean publishers, boolean editors, boolean creators) {
		String SQLwhere = "";
		if ((! id.equals("")) && (! id.equals("0")) && (! id.equals("-1"))) {
			SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "userclass", "administrator");
			SQLwhere += " and ((1=0)";

			if (creators) {
				String SQLwhereCreator = "";
				if ((getCreatorsType().equals("*")) || (getCreatorsType().equals("0"))) {
					// ok
				} else if (! getCreatorsType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getCreatorsType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getCreatorsType(), getCreatorsType());
					if (! SQLwhereCreator.equals("")) SQLwhereCreator += " and ";
					SQLwhereCreator += "usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
				}
				if ((getCreatorsGroup().equals("*")) || (getCreatorsGroup().equals("0"))) {
					// ok
				} else if (! getCreatorsGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getCreatorsGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getCreatorsGroup(), getCreatorsGroup());
					if (! SQLwhereCreator.equals("")) SQLwhereCreator += " and ";
					SQLwhereCreator += "usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
				}
				if (! SQLwhereCreator.equals("")) SQLwhere += " or (" + SQLwhereCreator + ")";
			}

			if (editors) {
				String SQLwhereEditor = "";
				if ((getEditorsType().equals("*")) || (getEditorsType().equals("0"))) {
					// ok
				} else if (! getEditorsType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getEditorsType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getEditorsType(), getEditorsType());
					if (! SQLwhereEditor.equals("")) SQLwhereEditor += " and ";
					SQLwhereEditor += "usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
				}
				if ((getEditorsGroup().equals("*")) || (getEditorsGroup().equals("0"))) {
					// ok
				} else if (! getEditorsGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getEditorsGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getEditorsGroup(), getEditorsGroup());
					if (! SQLwhereEditor.equals("")) SQLwhereEditor += " and ";
					SQLwhereEditor += "usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
				}
				if (! SQLwhereEditor.equals("")) SQLwhere += " or (" + SQLwhereEditor + ")";
			}

			if (publishers) {
				String SQLwherePublisher = "";
				if ((getPublishersType().equals("*")) || (getPublishersType().equals("0"))) {
					// ok
				} else if (! getPublishersType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getPublishersType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getPublishersType(), getPublishersType());
					if (! SQLwherePublisher.equals("")) SQLwherePublisher += " and ";
					SQLwherePublisher += "usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
				}
				if ((getPublishersGroup().equals("*")) || (getPublishersGroup().equals("0"))) {
					// ok
				} else if (! getPublishersGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getPublishersGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getPublishersGroup(), getPublishersGroup());
					if (! SQLwherePublisher.equals("")) SQLwherePublisher += " and ";
					SQLwherePublisher += "usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
				}
				if (! SQLwherePublisher.equals("")) SQLwhere += " or (" + SQLwherePublisher + ")";
			}

			if (administrators) {
				String SQLwhereAdministrator = "";
				if ((getAdministratorsType().equals("*")) || (getAdministratorsType().equals("0"))) {
					// ok
				} else if (! getAdministratorsType().equals("")) {
					Usertype usertype = new Usertype();
					usertype.setUsertype(getAdministratorsType());
					HashMap<String,String> usertypes = usertype.supertypes(db);
					usertypes.put(getAdministratorsType(), getAdministratorsType());
					if (! SQLwhereAdministrator.equals("")) SQLwhereAdministrator += " and ";
					SQLwhereAdministrator += "usertype in (" + Common.SQL_list_values((String[])usertypes.keySet().toArray(new String[0])) + ")";
				}
				if ((getAdministratorsGroup().equals("*")) || (getAdministratorsGroup().equals("0"))) {
					// ok
				} else if (! getAdministratorsGroup().equals("")) {
					Usergroup usergroup = new Usergroup();
					usergroup.setUsergroup(getAdministratorsGroup());
					HashMap<String,String> usergroups = usergroup.supergroups(db);
					usergroups.put(getAdministratorsGroup(), getAdministratorsGroup());
					if (! SQLwhereAdministrator.equals("")) SQLwhereAdministrator += " and ";
					SQLwhereAdministrator += "usergroup in (" + Common.SQL_list_values((String[])usergroups.keySet().toArray(new String[0])) + ")";
				}
				if (! SQLwhereAdministrator.equals("")) SQLwhere += " or (" + SQLwhereAdministrator + ")";
			}

			SQLwhere += ")";
		}
		return SQLwhere;
	}



	public String[] administratorsEmails(Session mysession, Configuration myconfig, DB db) {
		HashMap<String,String> emails = new HashMap<String,String>();
		if ((! id.equals("")) && (! id.equals("0")) && (! id.equals("-1"))) {
			String admin_email = "";
			String SQL = "select * from users " + administratorsSQLwhere(db,true,false,false,false) + " and  " + db.is_not_blank("email");
			User user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				emails.put(user.getEmail(), user.getEmail());
			}
		}
		return (String[]) emails.values().toArray(new String[0]);
	}



	public String[] publishersEmails(Session mysession, Configuration myconfig, DB db) {
		HashMap<String,String> emails = new HashMap<String,String>();
		if ((! id.equals("")) && (! id.equals("0")) && (! id.equals("-1"))) {
			String admin_email = "";
			String SQL = "select * from users " + administratorsSQLwhere(db,true,true,false,false) + " and  " + db.is_not_blank("email");
			User user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				emails.put(user.getEmail(), user.getEmail());
			}
		}
		return (String[]) emails.values().toArray(new String[0]);
	}



	public String[] editorsEmails(Session mysession, Configuration myconfig, DB db) {
		HashMap<String,String> emails = new HashMap<String,String>();
		if ((! id.equals("")) && (! id.equals("0")) && (! id.equals("-1"))) {
			String admin_email = "";
			String SQL = "select * from users " + administratorsSQLwhere(db,true,true,true,true) + " and  " + db.is_not_blank("email");
			User user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				emails.put(user.getEmail(), user.getEmail());
			}
		}
		return (String[]) emails.values().toArray(new String[0]);
	}



	public int administratorCount(DB db) {
		if (db == null) return 0;
		int administrators = 0;
		String SQL = "select count(*) as administrators from users where userclass=" + db.quote("administrator");
		administrators = Common.intnumber(db.query_value(SQL));
		return administrators;
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



	public String get(String attributename) {
		if (attributename.equals("id")) {
			return getId();
		} else if (attributename.equals("username")) {
			return getUsername();
		} else if (attributename.equals("email")) {
			return getEmail();
		} else {
			return "";
		}
	}
	public void set(String attributename, String attributevalue) {
		if (attributename.equals("id")) {
			setId(attributevalue);
		} else if (attributename.equals("username")) {
			setUsername(attributevalue);
		} else if (attributename.equals("email")) {
			setEmail(attributevalue);
		}
	}



	public String getId() {
		return id;
	}
	public void setId(String value) {
		id = value;
	}



	public int getLocked() {
		return locked;
	}
	public void setLocked(int value) {
		locked = value;
	}



	public int getFailed() {
		return failed;
	}
	public void setFailed(int value) {
		failed = value;
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
		String now = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		String[] nowparts = now.split("-");
		int nowyear = Common.intnumber(nowparts[0]);
		int nowmonth = Common.intnumber(nowparts[1]);
		int nowday = Common.intnumber(nowparts[2]);
		int myage = -1;
		if ((getBirthYear() != null) && (! getBirthYear().equals(""))) {
			myage = nowyear - Common.intnumber(getBirthYear());
			if ((getBirthMonth() != null) && (! getBirthMonth().equals(""))) {
				if (nowmonth < Common.intnumber(getBirthMonth())) {
					myage -= 1;
				} else if ((getBirthDay() != null) && (! getBirthDay().equals(""))) {
					if (nowday < Common.intnumber(getBirthDay())) {
						myage -= 1;
					}
				}
			}
		}
		if (myage < 0) {
			return "";
		} else {
			return "" + myage;
		}
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



	public HashMap<String,String> getAdditionalUsertypes() {
		return additional_usertypes;
	}
	public void setAdditionalUsertypes(HashMap<String,String> value) {
		additional_usertypes = value;
	}



	public HashMap<String,String> getAdditionalUsergroups() {
		return additional_usergroups;
	}
	public void setAdditionalUsergroups(HashMap<String,String> value) {
		additional_usergroups = value;
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



	public String getContentEditor() {
		return content_editor;
	}
	public void setContentEditor(String value) {
		content_editor = value;
	}



	public String getHardcoreEncoding() {
		return hardcore_encoding;
	}
	public void setHardcoreEncoding(String value) {
		hardcore_encoding = value;
	}



	public String getHardcoreLanguage() {
		return hardcore_language;
	}
	public void setHardcoreLanguage(String value) {
		hardcore_language = value;
	}



	public String getHardcoreUpload() {
		return hardcore_upload;
	}
	public void setHardcoreUpload(String value) {
		hardcore_upload = value;
	}



	public String getHardcoreFormat() {
		return hardcore_format;
	}
	public void setHardcoreFormat(String value) {
		hardcore_format = value;
	}



	public String getHardcoreWidth() {
		return hardcore_width;
	}
	public void setHardcoreWidth(String value) {
		hardcore_width = value;
	}



	public String getHardcoreHeight() {
		return hardcore_height;
	}
	public void setHardcoreHeight(String value) {
		hardcore_height = value;
	}



	public String getHardcoreOnEnter() {
		return hardcore_onenter;
	}
	public void setHardcoreOnEnter(String value) {
		hardcore_onenter = value;
	}



	public String getHardcoreOnCtrlEnter() {
		return hardcore_onctrlenter;
	}
	public void setHardcoreOnCtrlEnter(String value) {
		hardcore_onctrlenter = value;
	}



	public String getHardcoreOnShiftEnter() {
		return hardcore_onshiftenter;
	}
	public void setHardcoreOnShiftEnter(String value) {
		hardcore_onshiftenter = value;
	}



	public String getHardcoreOnAltEnter() {
		return hardcore_onaltenter;
	}
	public void setHardcoreOnAltEnter(String value) {
		hardcore_onaltenter = value;
	}



	public String getHardcoreToolbar() {
		return ""; // deprecated
	}



	public String getHardcoreToolbar1() {
		return hardcore_toolbar1;
	}
	public void setHardcoreToolbar1(String value) {
		hardcore_toolbar1 = value;
	}



	public String getHardcoreToolbar2() {
		return hardcore_toolbar2;
	}
	public void setHardcoreToolbar2(String value) {
		hardcore_toolbar2 = value;
	}



	public String getHardcoreToolbar3() {
		return hardcore_toolbar3;
	}
	public void setHardcoreToolbar3(String value) {
		hardcore_toolbar3 = value;
	}



	public String getHardcoreToolbar4() {
		return hardcore_toolbar4;
	}
	public void setHardcoreToolbar4(String value) {
		hardcore_toolbar4 = value;
	}



	public String getHardcoreToolbar5() {
		return hardcore_toolbar5;
	}
	public void setHardcoreToolbar5(String value) {
		hardcore_toolbar5 = value;
	}



	public String getHardcoreFormatBlock() {
		return hardcore_formatblock;
	}
	public void setHardcoreFormatBlock(String value) {
		hardcore_formatblock = value;
	}



	public String getHardcoreFontName() {
		return hardcore_fontname;
	}
	public void setHardcoreFontName(String value) {
		hardcore_fontname = value;
	}



	public String getHardcoreFontSize() {
		return hardcore_fontsize;
	}
	public void setHardcoreFontSize(String value) {
		hardcore_fontsize = value;
	}



	public String getHardcoreCustomScript() {
		return hardcore_customscript;
	}
	public void setHardcoreCustomScript(String value) {
		hardcore_customscript = value;
	}



	public String getIndexWorkspace() {
		return index_workspace;
	}
	public void setIndexWorkspace(String value) {
		index_workspace = value;
	}



	public String getIndexContent() {
		return index_content;
	}
	public void setIndexContent(String value) {
		index_content = value;
	}



	public String getIndexLibrary() {
		return index_library;
	}
	public void setIndexLibrary(String value) {
		index_library = value;
	}



	public String getIndexProduct() {
		return index_product;
	}
	public void setIndexProduct(String value) {
		index_product = value;
	}



	public String getIndexStock() {
		return index_stock;
	}
	public void setIndexStock(String value) {
		index_stock = value;
	}



	public String getIndexOrder() {
		return index_order;
	}
	public void setIndexOrder(String value) {
		index_order = value;
	}



	public String getIndexSegments() {
		return index_segments;
	}
	public void setIndexSegments(String value) {
		index_segments = value;
	}



	public String getIndexUsertests() {
		return index_usertests;
	}
	public void setIndexUsertests(String value) {
		index_usertests = value;
	}



	public String getIndexHeatmaps() {
		return index_heatmaps;
	}
	public void setIndexHeatmaps(String value) {
		index_heatmaps = value;
	}



	public String getIndexSales() {
		return index_sales;
	}
	public void setIndexSales(String value) {
		index_sales = value;
	}



	public String getIndexDatabases() {
		return index_databases;
	}
	public void setIndexDatabases(String value) {
		index_databases = value;
	}



	public String getIndexUser() {
		return index_user;
	}
	public void setIndexUser(String value) {
		index_user = value;
	}



	public String getIndexHosting() {
		return index_hosting;
	}
	public void setIndexHosting(String value) {
		index_hosting = value;
	}



	public String getIndexWebsites() {
		return index_websites;
	}
	public void setIndexWebsites(String value) {
		index_websites = value;
	}



	public String getMenuSelection() {
		return menu_selection;
	}
	public void setMenuSelection(String value) {
		menu_selection = value;
	}



	public String getWorkspaceSections() {
		return workspace_sections;
	}
	public void setWorkspaceSections(String value) {
		workspace_sections = value;
	}



	public String getStatisticsReports() {
		return statistics_reports;
	}
	public void setStatisticsReports(String value) {
		statistics_reports = value;
	}



	public String getSalesReports() {
		return sales_reports;
	}
	public void setSalesReports(String value) {
		sales_reports = value;
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



	public String getCardCVC() {
		return card_cvc;
	}
	public void setCardCVC(String value) {
		card_cvc = value;
	}



	public String getCardIssue() {
		return card_issue;
	}
	public void setCardIssue(String value) {
		card_issue = value;
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
	public String getShopcart(DB db) {
		return getShopcart(db, id);
	}
	public void setShopcart(DB db, String value) {
		setShopcart(db, id, value);
	}
	public String getShopcart(DB db, String myid) {
		String savedshopcart = db.query_string("select shopcart from users where id=" + Common.integer(myid));
		if (savedshopcart != null) {
			shopcart = savedshopcart;
		}
		return shopcart;
	}
	public void setShopcart(DB db, String myid, String value) {
		shopcart = value;
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("shopcart", "" + db.encrypt(shopcart));
		db.update("users", "id", "" + Common.integer(myid), mydata);
	}



	public String getWishlist() {
		return wishlist;
	}
	public void setWishlist(String value) {
		wishlist = value;
	}
	public String getWishlist(DB db) {
		return getWishlist(db, id);
	}
	public void setWishlist(DB db, String value) {
		setWishlist(db, id, value);
	}
	public String getWishlist(DB db, String myid) {
		shopcart = db.query_string("select wishlist from users where id=" + Common.integer(myid));
		return shopcart;
	}
	public void setWishlist(DB db, String myid, String value) {
		wishlist = value;
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("wishlist", "" + db.encrypt(wishlist));
		db.update("users", "id", "" + Common.integer(myid), mydata);
	}



	public boolean getUser() {
		return user;
	}
	public void setUser(boolean value) {
		user = value;
	}



	public boolean getEditor() {
		return editor;
	}
	public void setEditor(boolean value) {
		editor = value;
	}



	public boolean getCreator() {
		return creator;
	}
	public void setCreator(boolean value) {
		creator = value;
	}



	public boolean getPublisher() {
		return publisher;
	}
	public void setPublisher(boolean value) {
		publisher = value;
	}



	public boolean getAdministrator() {
		return administrator;
	}
	public void setAdministrator(boolean value) {
		administrator = value;
	}



}
