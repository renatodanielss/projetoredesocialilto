package HardCore;

import java.io.*;
import java.io.File;
import java.text.*;
import java.util.*;
import java.util.regex.*;

import javax.servlet.*;

public class UCbrowseWebsite {
	private Text text = new Text();
	private String uuid;


	public UCbrowseWebsite() {
	}



	public UCbrowseWebsite(Text mytext) {
		if (mytext != null) text = mytext;
	}

	public UCbrowseWebsite(Text mytext, String uuid) {
		if (mytext != null) text = mytext;
		
		this.uuid = uuid;
	}

	public void doLog(String requestid, String requestclass, String requestdatabase, Session mysession, Request myrequest, Configuration myconfig, DB db) {
		if (myconfig.get(db, "do_log_" + requestclass).equals("yes")) {
			UsageLog usagelog = new UsageLog(requestid, requestclass, requestdatabase, mysession, myrequest);
			usagelog.create(db);
			if (Common.intnumber(Common.numberformat("" + Math.random()*1000, 0)) == 0) {
				usagelog.clean(db, myconfig.get(db, "log_period"));
			}
		}
	}



	public Page doRetrievePassword(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return doRetrievePassword(server, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Page doRetrievePassword(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) throws Exception {
		if (mywebsite == null) mywebsite = new Website(text);
		Page page = new Page(text);
		if ((! myrequest.getParameter("email").equals("")) || (! myrequest.getParameter("username").equals(""))) {
			User user = new User(text);
			if (! myrequest.getParameter("email").equals("")) {
				user.readByEmail(myconfig.get(db, "superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("email"));
			}
			if ((! myrequest.getParameter("username").equals("")) && (user.getEmail().equals(""))) {
				user.readByUsername(myconfig.get(db, "superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("username"));
			}
			if (User.DIGEST) {
				String password = "";
				for (int i=1; i<=8; i++) {
					password = "" + password + (char)('a'+Integer.parseInt(Common.numberformat("" + Math.random()*25, 0)));
				}
				user.setPassword(password);
				String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
				String username = mysession.get("username");
				user.setUpdated(timestamp);
				user.setUpdatedBy(username);
				Cms.CMSAudit("action=password user=" + user.getUsername() + " [" + user.getId() + "]" + " username=" + mysession.get("username") + " userid=" + mysession.get("userid"));
				user.update(db);
				mysession.set("password", password);
				mysession.set("user_updated", timestamp);
			}
			if (! user.getEmail().equals("")) {
				String sender = "";
				if ((! myrequest.getParameter("from").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(myrequest.getParameter("from"))>=0)) {
					sender = html.encodeHtmlEntities(myrequest.getParameter("from"));
				} else {
					sender = "password@" + myrequest.getServerName();
				}
				String cc = "";
				if ((! myrequest.getParameter("cc").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(myrequest.getParameter("cc"))>=0)) {
					cc = html.encodeHtmlEntities(myrequest.getParameter("cc"));
				}
				String bcc = "";
				if ((! myrequest.getParameter("bcc").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(myrequest.getParameter("bcc"))>=0)) {
					bcc = html.encodeHtmlEntities(myrequest.getParameter("bcc"));
				}
				Page email = new Page(text);
				if ((mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) && (! mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "retrieve_password_email").equals(""))) {
					email = getPageById(mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "retrieve_password_email"), server, mysession, myrequest, myresponse, myconfig, db);
				} else {
					email = getPageById(myconfig.get(db, "retrieve_password_email"), server, mysession, myrequest, myresponse, myconfig, db);
				}
				String body = email.display("body", "", myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"));
				body = body.replaceAll("@@@username@@@", user.getUsername().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("@@@password@@@", user.getPassword().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				body = body.replaceAll("@@@email@@@", user.getEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				
				body = body.replaceAll("@@@emailbackground@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/image.jsp?id=535");
				
				body = body.replaceAll("@@@logoimage@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/image.jsp?id=545");
				
				body = body.replaceAll("@@@resetpasswordlink@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/resetpassword.jsp?user=" + user.getUsername() + "&resetkey=" + uuid);
				
				HashMap<String,String> requestForm = Email.getForm(myrequest);
				Email.send_email(text, requestForm, email.getTitle(), body, "", sender, user.getEmail(), cc, bcc, "", "", myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
				if ((mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) && (! mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "retrieve_password_confirmation").equals(""))) {
					page = getPageById(mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "retrieve_password_confirmation"), server, mysession, myrequest, myresponse, myconfig, db);
				} else {
					page = getPageById(myconfig.get(db, "retrieve_password_confirmation"), server, mysession, myrequest, myresponse, myconfig, db);
				}
				page.setBody(page.getBody().replaceAll("@@@username@@@", user.getUsername().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				page.setBody(page.getBody().replaceAll("@@@password@@@", user.getPassword().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				page.setBody(page.getBody().replaceAll("@@@email@@@", user.getEmail().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				
				page.setBody(page.getBody().replaceAll("@@@emailbackground@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/image.jsp?id=535"));
				
				page.setBody(page.getBody().replaceAll("@@@logoimage@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/image.jsp?id=545"));
				
				page.setBody(page.getBody().replaceAll("@@@resetpasswordlink@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/resetpassword.jsp?user=" + user.getUsername() + "&resetkey=" + uuid));
				
			} else {
				if ((mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) && (! mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "retrieve_password_error").equals(""))) {
					page = getPageById(mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "retrieve_password_error"), server, mysession, myrequest, myresponse, myconfig, db);
				} else {
					page = getPageById(myconfig.get(db, "retrieve_password_error"), server, mysession, myrequest, myresponse, myconfig, db);
				}
				page.setBody(page.getBody().replaceAll("@@@username@@@", myrequest.getParameter("username").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
				page.setBody(page.getBody().replaceAll("@@@email@@@", myrequest.getParameter("email").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$")));
			}
		} else {
			if ((mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) && (! mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "retrieve_password_page").equals(""))) {
				page = getPageById(mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "retrieve_password_page"), server, mysession, myrequest, myresponse, myconfig, db);
			} else {
				page = getPageById(myconfig.get(db, "retrieve_password_page"), server, mysession, myrequest, myresponse, myconfig, db);
			}
		}
		return page;
	}



	public Page getData(String databasename, String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return getData(databasename, id, server, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Page getData(String databasename, String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) throws Exception {
		Page mypage;
		setSessionModeFromRequest(mysession, myrequest);
		setSessionVersionFromRequest(mysession, myrequest);
		setSessionPresentationFromRequest(mysession, myrequest);
		setSessionCurrencyFromRequest(mysession, myrequest, db, myconfig);
		boolean accesspermission = true;
		if (mywebsite == null) mywebsite = new Website(text);

		Databases database = new Databases(text);
		database.readTitle(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, databasename);
		accesspermission = RequireUser.checkDataUserAccessRestrictions(text, true, database, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) database = new Databases(text);

		String website_template = "";
		String website_stylesheet = "";
		if ((mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin"))) {
			accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
			if ((mysession.get("mode").equals("admin")) && (! myrequest.getParameter("menu").equals(""))) {
				mysession.set("menu", html.encodeHtmlEntities(myrequest.getParameter("menu")));
			}
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
				setSessionCurrencyForVersion(mysession, db, myconfig);
			}
			mysession.set("default_country", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_country"));
			mysession.set("default_state", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_state"));
			mysession.set("default_price", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_price"));
			website_template = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_template");
			website_stylesheet = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_stylesheet");
			mypage = new Page(text);
			mypage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, database.getViewPage(), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		} else {
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
				setSessionCurrencyForVersion(mysession, db, myconfig);
			}
			mysession.set("default_country", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_country"));
			mysession.set("default_state", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_state"));
			mysession.set("default_price", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_price"));
			website_template = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_template");
			website_stylesheet = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_stylesheet");
			mypage = new Page(text);
			mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, database.getViewPage(), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		}

		if (mysession.get("currency").equals("")) {
			setSessionCurrencyForVersion(mysession, db, myconfig);
		}

		setPageStyleSheet(mypage, mysession, myconfig, db);
		accesspermission = RequireUser.checkUserAccessRestrictions(text, true, mypage, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) mypage = new Page(text);

		Data data = new Data();
		data.read(db, "data" + database.getId(), id);
		data.getAccessRestrictions(database, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);
		if (! data.getUser()) data.init();
		data.adjustContent(database.columns);
		mypage.parse_input(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, id, "content_public", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		mypage.parse_input_elements(mysession, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, id, "content_public", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"));
		if (mypage.getBody().equals("")) {
			mypage.setBody(mypage.getContent());
		}
		mypage.setBody(mypage.getBody().replaceAll("@@@id@@@", id));
		mypage.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, id, "content_public", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		mypage.parse_output_data(mysession, myrequest, myresponse, db, myconfig, mysession.get("version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), database, data);
		mypage.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, id, "content_public", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));

		return mypage;
	}



	public Data getDataData(String databasename, String id, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return getDataData(databasename, id, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Data getDataData(String databasename, String id, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) {
		setSessionModeFromRequest(mysession, myrequest);
		setSessionVersionFromRequest(mysession, myrequest);
		setSessionPresentationFromRequest(mysession, myrequest);
		setSessionCurrencyFromRequest(mysession, myrequest, db, myconfig);
		boolean accesspermission = true;

		Databases database = new Databases(text);
		database.readTitle(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, databasename);
		accesspermission = RequireUser.checkDataUserAccessRestrictions(text, true, database, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) database = new Databases(text);

		Data data = new Data();
		data.read(db, "data" + database.getId(), id);
		data.adjustContent(database.columns);

		return data;
	}



	public Content getContent(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return getContentById(myrequest.getParameter("id"), mysession, myrequest, myresponse, myconfig, db);
	}



	public Content getContentById(String id, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return getContentById(id, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Content getContentById(String id, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) {
		return getContentById(id, mysession, myrequest, myresponse, myconfig, db, mywebsite, true);
	}
	public Content getContentById(String id, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite, boolean doexperience) {
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		Content mycontent = new Content(text);
		if (! doexperience) mysession = new Session(mysession);
		setSessionModeFromRequest(mysession, myrequest);
		setSessionVersionFromRequest(mysession, myrequest);
		setSessionCurrencyFromRequest(mysession, myrequest, db, myconfig);
		boolean accesspermission = true;
		boolean preview = false;
		if (mywebsite == null) mywebsite = new Website(text);

		if ((mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin"))) {
			preview = true;
			accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
			if ((mysession.get("mode").equals("admin")) && (! myrequest.getParameter("menu").equals(""))) {
				mysession.set("menu", html.encodeHtmlEntities(myrequest.getParameter("menu")));
			}
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
				setSessionCurrencyForVersion(mysession, db, myconfig);
			}
			mysession.set("default_country", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_country"));
			mysession.set("default_state", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_state"));
			mysession.set("default_price", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_price"));
			mycontent = new Content(text);
			if (Common.number(myrequest.getParameter("archive"))>0) {
				mycontent.archive_read(db, myconfig, myrequest.getParameter("archive"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if (mysession.get("mode").equals("preview")) {
					if ((! mycontent.getScheduledPublish().equals("")) && (mycontent.getScheduledPublish().compareTo(timestamp) > 0)) {
						if (myrequest.getParameter("mode").equals("preview")) {
							mysession.set("preview_scheduled", mycontent.getScheduledPublish());
						}
						mycontent.archive_read(db, myconfig, myrequest.getParameter("archive"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
					}
				}
			} else if (! id.equals("")) {
				mycontent.scheduled_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if (mysession.get("mode").equals("preview")) {
					if ((! mycontent.getScheduledPublish().equals("")) && (mycontent.getScheduledPublish().compareTo(timestamp) > 0)) {
						if (myrequest.getParameter("mode").equals("preview")) {
							mysession.set("preview_scheduled", mycontent.getScheduledPublish());
						}
						mycontent.scheduled_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
					}
				}
			}
		}
		if (! preview) {
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
				setSessionCurrencyForVersion(mysession, db, myconfig);
			}
			mysession.set("default_country", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_country"));
			mysession.set("default_state", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_state"));
			mysession.set("default_price", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_price"));
			mycontent = new Content(text);
			if (! id.equals("")) {
				mycontent.public_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			}
		}

		if (mysession.get("currency").equals("")) {
			setSessionCurrencyForVersion(mysession, db, myconfig);
		}

		if (doexperience) setSessionUserExperience(mycontent, mysession, myresponse, db, myconfig);
		accesspermission = RequireUser.checkUserAccessRestrictions(text, true, mycontent, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) mycontent = new Content(text);
		return mycontent;
	}



	public Page getManifest(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) throws Exception {
		Page page = new Page(text);
		StringBuilder myoutput = new StringBuilder("");
		if (! myrequest.getParameter("id").equals("")) {
			page = getPageById(myrequest.getParameter("id"), server, mysession, myrequest, myresponse, myconfig, db, mywebsite);
			myoutput.append(page.getBody());
		}
		String SQL = "select * from content_public";
		String SQLwhere = "";
		if (! myrequest.getParameter("contentbundle").equals("")) {
			if (! SQLwhere.equals("")) SQLwhere += " and ";
			SQLwhere += "contentbundle in (" + db.quote_csv(myrequest.getParameter("contentbundle")) + ")";
		}
		if (! myrequest.getParameter("contentclass").equals("")) {
			if (! SQLwhere.equals("")) SQLwhere += " and ";
			SQLwhere += "contentclass in (" + db.quote_csv(myrequest.getParameter("contentclass")) + ")";
		}
		if (! myrequest.getParameter("contentgroup").equals("")) {
			if (! SQLwhere.equals("")) SQLwhere += " and ";
			SQLwhere += "contentgroup in (" + db.quote_csv(myrequest.getParameter("contentgroup")) + ")";
		}
		if (! myrequest.getParameter("contenttype").equals("")) {
			if (! SQLwhere.equals("")) SQLwhere += " and ";
			SQLwhere += "contenttype in (" + db.quote_csv(myrequest.getParameter("contenttype")) + ")";
		}
		if (! myrequest.getParameter("contentpackage").equals("")) {
			if (! SQLwhere.equals("")) SQLwhere += " and ";
			SQLwhere += "contentpackage in (" + db.quote_csv(myrequest.getParameter("contentpackage")) + ")";
		}
		if (! SQLwhere.equals("")) {
			SQL += " where contentclass in ('page','product','image','file','link','script','stylesheet') and " + SQLwhere + " order by server_filename, id";
			String lastpublished = "0000-00-00 00:00:00";
			Content content = new Content(text);
			content.records("", "", "", "", "", "", "", db, myconfig, SQL);
			while (content.records("", "", "", "", "", "", "", db, myconfig, "")) {
				if (content.getUser()) {
					String url = "";
					if (! content.getServerFilename().equals("")) {
						url = myconfig.get(db, "URLrootpath") + content.getServerFilename();
					} else {
						url = "/" + content.getContentClass() + ".jsp?id=" + content.getId();
					}
//					myoutput.append("# " + content.getPublished() + " " + content.getTitle() + "\r\n");
					myoutput.append(url + "\r\n");
					if (content.getPublished().compareTo(lastpublished) > 0) {
						lastpublished = content.getPublished();
					}
				}
			}
			content.closeRecords(db);

			myoutput.append("\r\n" + "# last updated: " + lastpublished + "\r\n");
			if (! myrequest.getParameter("reload").equals("")) {
				String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
				myoutput.append("\r\n" + "# reloaded: " + now + "\r\n");
			}
		}
		page.setBody(myoutput.toString());
		return page;
	}



	public Page getPersonal(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return getPersonal(server, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Page getPersonal(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) throws Exception {
		Page page = new Page(text);
		String username = "";
		if (! myrequest.getQueryString().equals("")) {
			username = myrequest.getQueryString();
		} else if (! mysession.get("username").equals("")) {
			username = mysession.get("username");
		}
		if (! username.equals("")) {
			String SQL = "select id from content where created_by='" + username + "' order by created";
			Content content = new Content(text);
			content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			if (content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				page = getPageById(content.getId(), server, mysession, myrequest, myresponse, myconfig, db);
			}
			content.closeRecords(db);
		}
		return page;
	}



	public Page getPage(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return getPageById(myrequest.getParameter("id"), server, mysession, myrequest, myresponse, myconfig, db);
	}
	public Page getPage(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) throws Exception {
		return getPageById(myrequest.getParameter("id"), server, mysession, myrequest, myresponse, myconfig, db, mywebsite);
	}



	public Page getPageById(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return getPageById(id, server, mysession, myrequest, myresponse, myconfig, db, false);
	}
	public Page getPageById(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, boolean product) throws Exception {
		return getPageById(id, server, mysession, myrequest, myresponse, myconfig, db, null, product);
	}
	public Page getPageById(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) throws Exception {
		return getPageById(id, server, mysession, myrequest, myresponse, myconfig, db, mywebsite, false);
	}
	public Page getPageById(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite, boolean product) throws Exception {
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String timestamp = now;
		if ((myconfig.get(db, "scheduled_next").compareTo(timestamp) <= 0) && (! myconfig.get(db, "scheduled_next").equals(""))) {
			UCpublishScheduled publishScheduled = new UCpublishScheduled(text);
			publishScheduled.doScheduled(server, mysession, myrequest, myresponse, myconfig, db);
		}

		Page mypage;
		setSessionModeFromRequest(mysession, myrequest);
		setSessionVersionFromRequest(mysession, myrequest);
		setSessionPresentationFromRequest(mysession, myrequest);
		setSessionCurrencyFromRequest(mysession, myrequest, db, myconfig);
		boolean accesspermission = true;
		if (mywebsite == null) mywebsite = new Website(text);

		if ((mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin"))) {
			accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
			if ((mysession.get("mode").equals("admin")) && (! myrequest.getParameter("menu").equals(""))) {
				mysession.set("menu", html.encodeHtmlEntities(myrequest.getParameter("menu")));
			}
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
				setSessionCurrencyForVersion(mysession, db, myconfig);
			}
			mysession.set("default_country", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_country"));
			mysession.set("default_state", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_state"));
			mysession.set("default_price", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_price"));
			String website_template = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_template");
			String website_stylesheet = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_stylesheet");
			mypage = new Page(text);
			if (Common.number(myrequest.getParameter("archive"))>0) {
				mypage.archive_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("archive"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				if ((mysession.get("mode").equals("preview")) && (! mypage.getScheduledPublish().equals("")) && (mypage.getScheduledPublish().compareTo(timestamp) > 0)) {
					if (myrequest.getParameter("mode").equals("preview")) {
						mysession.set("preview_scheduled", mypage.getScheduledPublish());
					}
					mypage.archive_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("archive"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				}
			} else if (! id.equals("")) {
				mypage.scheduled_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, id, mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				if ((mysession.get("mode").equals("preview")) && (! mypage.getScheduledPublish().equals("")) && (mypage.getScheduledPublish().compareTo(timestamp) > 0)) {
					if (myrequest.getParameter("mode").equals("preview")) {
						mysession.set("preview_scheduled", mypage.getScheduledPublish());
					}
					mypage.scheduled_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, id, mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				}
			} else if (mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) {
				if (myrequest.getParameter("mode").equals("preview")) {
					mysession.set("preview_scheduled", "");
				}
				mypage.scheduled_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else {
				if (myrequest.getParameter("mode").equals("preview")) {
					mysession.set("preview_scheduled", "");
				}
				mypage.scheduled_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			}
			if (mypage.getId().equals("")) {
				if (myrequest.getParameter("mode").equals("preview")) {
					mysession.set("preview_scheduled", "");
				}
				mypage.scheduled_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			}
		} else {
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
				setSessionCurrencyForVersion(mysession, db, myconfig);
			}
			mysession.set("default_country", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_country"));
			mysession.set("default_state", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_state"));
			mysession.set("default_price", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_price"));
			String website_template = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_template");
			String website_stylesheet = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_stylesheet");
			mypage = new Page(text);
			if (! id.equals("")) {
				mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, id, mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else if (mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) {
				mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else {
				mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			}
		}
		if (mypage.getId().equals("")) {
			String website_template = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_template");
			String website_stylesheet = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_stylesheet");
			Page previewpage = new Page(text);
			if (Common.number(myrequest.getParameter("archive"))>0) {
				previewpage.archive_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("archive"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else if (! id.equals("")) {
				previewpage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, id, mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else if (mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) {
				previewpage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else {
				previewpage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			}
			if (previewpage.getId().equals("")) {
				if ((mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) && (mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_page_nonexisting").equals("-"))) {
					mypage = new Page(text);
					if (! myresponse.getResponse().isCommitted()) {
						myresponse.getResponse().sendError(404);
					}
				} else if ((mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) && (! mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_page_nonexisting").equals(""))) {
					mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_page_nonexisting"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else if ((myconfig.get(db, "default_page_nonexisting").equals("-"))) {
					mypage = new Page(text);
					if (! myresponse.getResponse().isCommitted()) {
						myresponse.getResponse().sendError(404);
					}
				} else if ((! myconfig.get(db, "default_page_nonexisting").equals(""))) {
					mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_page_nonexisting"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				}
			} else if (previewpage.getPublished().equals("")) {
				if ((! previewpage.getScheduledUnpublish().equals("")) && (previewpage.getScheduledUnpublish().compareTo(now)<0) && (mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) && (mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_page_expired").equals("-"))) {
					mypage = new Page(text);
					if (! myresponse.getResponse().isCommitted()) {
						myresponse.getResponse().sendError(404);
					}
				} else if ((! previewpage.getScheduledUnpublish().equals("")) && (previewpage.getScheduledUnpublish().compareTo(now)<0) && (mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) && (! mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_page_expired").equals(""))) {
					mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_page_expired"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else if ((! previewpage.getScheduledUnpublish().equals("")) && (previewpage.getScheduledUnpublish().compareTo(now)<0) && (myconfig.get(db, "default_page_expired").equals("-"))) {
					mypage = new Page(text);
					if (! myresponse.getResponse().isCommitted()) {
						myresponse.getResponse().sendError(404);
					}
				} else if ((! previewpage.getScheduledUnpublish().equals("")) && (previewpage.getScheduledUnpublish().compareTo(now)<0) && (! myconfig.get(db, "default_page_expired").equals(""))) {
					mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_page_expired"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else if ((mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) && (mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_page_unpublished").equals("-"))) {
					mypage = new Page(text);
					if (! myresponse.getResponse().isCommitted()) {
						myresponse.getResponse().sendError(404);
					}
				} else if ((mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) && (! mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_page_unpublished").equals(""))) {
					mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_page_unpublished"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else if ((myconfig.get(db, "default_page_unpublished").equals("-"))) {
					mypage = new Page(text);
					if (! myresponse.getResponse().isCommitted()) {
						myresponse.getResponse().sendError(404);
					}
				} else if ((! myconfig.get(db, "default_page_unpublished").equals(""))) {
					mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_page_unpublished"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				}
			}
		}

		if (mysession.get("currency").equals("")) {
			setSessionCurrencyForVersion(mysession, db, myconfig);
		}

		if (product) {
			mypage.setDisplayCurrency(db, mysession.get("currency"), myconfig.get(db, "default_currency"));
			mypage.parse_output_product(server, mysession, myrequest, myresponse, db, myconfig);
		}

		setPageStyleSheet(mypage, mysession, myconfig, db);
		clearSessionPrintModeFromRequest(mysession, myrequest);
		setSessionUserExperience(mypage, mysession, myresponse, db, myconfig);
		accesspermission = RequireUser.checkUserAccessRestrictions(text, true, mypage, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) mypage = new Page(text);
		handleFormDataValidation(myrequest, myresponse);
		return mypage;
	}



	public Page getProduct(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return getProductById(myrequest.getParameter("id"), server, mysession, myrequest, myresponse, myconfig, db);
	}



	public Page getProductById(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return getPageById(id, server, mysession, myrequest, myresponse, myconfig, db, true);
	}
	public Page getProductById(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) throws Exception {
		return getPageById(id, server, mysession, myrequest, myresponse, myconfig, db, mywebsite, true);
	}



	public Page getTemplate(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return getTemplateById(myrequest.getParameter("id"), server, mysession, myrequest, myresponse, myconfig, db);
	}
	public Page getTemplate(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) throws Exception {
		return getTemplateById(myrequest.getParameter("id"), server, mysession, myrequest, myresponse, myconfig, db, mywebsite);
	}



	public Page getTemplateById(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return getTemplateById(id, server, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Page getTemplateById(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) throws Exception {
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		Page mytemplate = new Page(text);
		setSessionModeFromRequest(mysession, myrequest);
		setSessionVersionFromRequest(mysession, myrequest);
		boolean accesspermission = true;
		boolean preview = false;
		if (mywebsite == null) mywebsite = new Website(text);

		if ((myrequest.getParameter("mode").equals("preview")) && (Common.number(myrequest.getParameter("archive"))>0) && (myrequest.getServletPath().equals("/template.jsp"))) {
			preview = true;
			mytemplate.archive_read(db, myconfig, myrequest.getParameter("archive"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			if ((mysession.get("mode").equals("preview")) && (! mytemplate.getScheduledPublish().equals("")) && (mytemplate.getScheduledPublish().compareTo(timestamp) > 0)) {
				if (myrequest.getParameter("mode").equals("preview")) {
					mysession.set("preview_scheduled", mytemplate.getScheduledPublish());
				}
				mytemplate.archive_read(db, myconfig, myrequest.getParameter("archive"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			}
			mysession.set("version", "");
			mysession.set("template", "");
			mysession.set("stylesheet", "");
			myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/page.jsp?template=" + Common.crlf_clean(id) + "&" + Math.random());
		} else if ((myrequest.getParameter("mode").equals("preview")) && (! id.equals("")) && (myrequest.getServletPath().equals("/template.jsp"))) {
			preview = true;
			mytemplate.preview_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			if ((mysession.get("mode").equals("preview")) && (! mytemplate.getScheduledPublish().equals("")) && (mytemplate.getScheduledPublish().compareTo(timestamp) > 0)) {
				if (myrequest.getParameter("mode").equals("preview")) {
					mysession.set("preview_scheduled", mytemplate.getScheduledPublish());
				}
				mytemplate.preview_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			}
			mysession.set("version", "");
			mysession.set("template", "");
			mysession.set("stylesheet", "");
			myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/page.jsp?template=" + Common.crlf_clean(id) + "&" + Math.random());
		} else if ((mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin"))) {
			preview = true;
			accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
			}
			if (! id.equals("")) {
				mytemplate.scheduled_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if ((mysession.get("mode").equals("preview")) && (! mytemplate.getScheduledPublish().equals("")) && (mytemplate.getScheduledPublish().compareTo(timestamp) > 0)) {
					if (myrequest.getParameter("mode").equals("preview")) {
						mysession.set("preview_scheduled", mytemplate.getScheduledPublish());
					}
					mytemplate.scheduled_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				}
			}
		}
		if (! preview) {
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
			}
			if (! id.equals("")) {
				if (mysession.get("mode").equals("admin")) {
					mytemplate.read_primary_only(db, myconfig, id, "content", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				} else {
					mytemplate.read_primary_only(db, myconfig, id, "content_public", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				}
			}
		}

		accesspermission = RequireUser.checkUserAccessRestrictions(text, true, mytemplate, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) mytemplate = new Page(text);
		return mytemplate;
	}



	public Page getStyleSheet(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return getStyleSheetById(myrequest.getParameter("id"), server, mysession, myrequest, myresponse, myconfig, db);
	}



	public Page getStyleSheetById(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return getStyleSheetById(id, server, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Page getStyleSheetById(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) throws Exception {
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		Page mystylesheet = new Page(text);
		setSessionModeFromRequest(mysession, myrequest);
		setSessionVersionFromRequest(mysession, myrequest);
		boolean accesspermission = true;
		boolean preview = false;
		if (mywebsite == null) mywebsite = new Website(text);

		if ((myrequest.parameterExists("mode")) && (myrequest.parameterExists("archive")) && (myrequest.getParameter("mode").equals("preview")) && (Common.number(myrequest.getParameter("archive"))>0) && (myrequest.getServletPath().equals("/stylesheet.jsp"))) {
			preview = true;
			mystylesheet.archive_read(db, myconfig, myrequest.getParameter("archive"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			if ((mysession.get("mode").equals("preview")) && (! mystylesheet.getScheduledPublish().equals("")) && (mystylesheet.getScheduledPublish().compareTo(timestamp) > 0)) {
				if (myrequest.getParameter("mode").equals("preview")) {
					mysession.set("preview_scheduled", mystylesheet.getScheduledPublish());
				}
				mystylesheet.archive_read(db, myconfig, myrequest.getParameter("archive"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			}
			mysession.set("version", "");
			mysession.set("template", "");
			mysession.set("stylesheet", "");
			myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/page.jsp?stylesheet=" + Common.crlf_clean(id) + "&" + Math.random());
		} else if ((myrequest.parameterExists("mode")) && (myrequest.getParameter("mode").equals("preview")) && (! id.equals("")) && (myrequest.getServletPath().equals("/stylesheet.jsp"))) {
			preview = true;
			mystylesheet.preview_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			if ((mysession.get("mode").equals("preview")) && (! mystylesheet.getScheduledPublish().equals("")) && (mystylesheet.getScheduledPublish().compareTo(timestamp) > 0)) {
				if (myrequest.getParameter("mode").equals("preview")) {
					mysession.set("preview_scheduled", mystylesheet.getScheduledPublish());
				}
				mystylesheet.preview_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			}
			mysession.set("version", "");
			mysession.set("template", "");
			mysession.set("stylesheet", "");
			myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/page.jsp?stylesheet=" + Common.crlf_clean(id) + "&" + Math.random());
		} else if ((mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin"))) {
			preview = true;
			accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
			}
			if (! id.equals("")) {
				mystylesheet.scheduled_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if ((mysession.get("mode").equals("preview")) && (! mystylesheet.getScheduledPublish().equals("")) && (mystylesheet.getScheduledPublish().compareTo(timestamp) > 0)) {
					if (myrequest.getParameter("mode").equals("preview")) {
						mysession.set("preview_scheduled", mystylesheet.getScheduledPublish());
					}
					mystylesheet.scheduled_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				}
			}
		}
		if (! preview) {
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
			}
			if (! id.equals("")) {
				if (mysession.get("mode").equals("admin")) {
					mystylesheet.read_primary_only(db, myconfig, id, "content", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				} else {
					mystylesheet.read_primary_only(db, myconfig, id, "content_public", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				}
			}
		}

		setSessionUserExperience(mystylesheet, mysession, myresponse, db, myconfig);
		accesspermission = RequireUser.checkUserAccessRestrictions(text, true, mystylesheet, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) mystylesheet = new Page(text);
		return mystylesheet;
	}



	public Page getElement(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return getElement(server, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Page getElement(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) throws Exception {
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		setSessionModeFromRequest(mysession, myrequest);
		setSessionVersionFromRequest(mysession, myrequest);
		setSessionPresentationFromRequest(mysession, myrequest);
		setSessionCurrencyFromRequest(mysession, myrequest, db, myconfig);
		boolean accesspermission = true;
		if (mywebsite == null) mywebsite = new Website(text);

		String mytemplate = "0";
		if (myrequest.parameterExists("template")) {
			mytemplate = myrequest.getParameter("template");
		}
		String mystylesheet = "";
		if (myrequest.parameterExists("stylesheet")) {
			mystylesheet = myrequest.getParameter("stylesheet");
		}

		Page myelement;
		if ((mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin"))) {
			accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
				setSessionCurrencyForVersion(mysession, db, myconfig);
			}
			mysession.set("default_country", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_country"));
			mysession.set("default_state", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_state"));
			mysession.set("default_price", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_price"));
			myelement = new Page(text);
			if (Common.number(myrequest.getParameter("archive"))>0) {
				myelement.archive_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("archive"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mytemplate, "", myconfig.get(db, "default_template"), mystylesheet, "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				if ((mysession.get("mode").equals("preview")) && (! myelement.getScheduledPublish().equals("")) && (myelement.getScheduledPublish().compareTo(timestamp) > 0)) {
					if (myrequest.getParameter("mode").equals("preview")) {
						mysession.set("preview_scheduled", myelement.getScheduledPublish());
					}
					myelement.archive_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("archive"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mytemplate, "", myconfig.get(db, "default_template"), mystylesheet, "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				}
			} else if (! myrequest.getParameter("id").equals("")) {
				myelement.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mytemplate, "", myconfig.get(db, "default_template"), mystylesheet, "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				if ((mysession.get("mode").equals("preview")) && (! myelement.getScheduledPublish().equals("")) && (myelement.getScheduledPublish().compareTo(timestamp) > 0)) {
					if (myrequest.getParameter("mode").equals("preview")) {
						mysession.set("preview_scheduled", myelement.getScheduledPublish());
					}
					myelement.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mytemplate, "", myconfig.get(db, "default_template"), mystylesheet, "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				}
			}
		} else {
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
				setSessionCurrencyForVersion(mysession, db, myconfig);
			}
			mysession.set("default_country", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_country"));
			mysession.set("default_state", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_state"));
			mysession.set("default_price", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_price"));
			myelement = new Page(text);
			if (! myrequest.getParameter("id").equals("")) {
				myelement.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mytemplate, "", myconfig.get(db, "default_template"), mystylesheet, "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			}
		}

		if (mysession.get("currency").equals("")) {
			setSessionCurrencyForVersion(mysession, db, myconfig);
		}

		setPageStyleSheet(myelement, mysession, myconfig, db);
		accesspermission = RequireUser.checkUserAccessRestrictions(text, true, myelement, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) myelement = new Page(text);
		return myelement;
	}



	public Content getImage(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return getImage(null, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Content getImage(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) {
		return getImage(null, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Content getImage(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) {
		return getImageById(myrequest.getParameter("id"), server, mysession, myrequest, myresponse, myconfig, db, mywebsite);
	}
	public Content getImageById(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) {
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		Content myimage = new Content(text);
		setSessionModeFromRequest(mysession, myrequest);
		setSessionVersionFromRequest(mysession, myrequest);
		boolean accesspermission = true;
		boolean preview = false;
		if (mywebsite == null) mywebsite = new Website(text);

		if ((mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin"))) {
			preview = true;
			accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
			}
			myimage = new Content(text);
			if (Common.number(myrequest.getParameter("archive"))>0) {
				myimage.archive_read(db, myconfig, myrequest.getParameter("archive"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if ((mysession.get("mode").equals("preview")) && (! myimage.getScheduledPublish().equals("")) && (myimage.getScheduledPublish().compareTo(timestamp) > 0)) {
					if (myrequest.getParameter("mode").equals("preview")) {
						mysession.set("preview_scheduled", myimage.getScheduledPublish());
					}
					myimage.archive_read(db, myconfig, myrequest.getParameter("archive"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				}
			} else if (! id.equals("")) {
				myimage.scheduled_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if ((mysession.get("mode").equals("preview")) && (! myimage.getScheduledPublish().equals("")) && (myimage.getScheduledPublish().compareTo(timestamp) > 0)) {
					if (myrequest.getParameter("mode").equals("preview")) {
						mysession.set("preview_scheduled", myimage.getScheduledPublish());
					}
					myimage.scheduled_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				}
			} else if (! myrequest.getQueryString().equals("")) {
				myimage.scheduled_read(db, myconfig, URLDecoder.decode(myrequest.getQueryString()).replaceAll("^/", ""), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if (myimage.getId().equals("")) {
					myimage.public_read(db, myconfig, URLDecoder.decode(myrequest.getQueryString()).replaceAll("^/", ""), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
					myimage.scheduled_read(db, myconfig, myimage.getId(), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				}
				if ((mysession.get("mode").equals("preview")) && (! myimage.getScheduledPublish().equals("")) && (myimage.getScheduledPublish().compareTo(timestamp) > 0)) {
					if (myrequest.getParameter("mode").equals("preview")) {
						mysession.set("preview_scheduled", myimage.getScheduledPublish());
					}
					myimage.scheduled_read(db, myconfig, myimage.getId(), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				}
			}
		}
		if (! preview) {
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
			}
			myimage = new Content(text);
			if (! id.equals("")) {
				myimage.public_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			} else if (! myrequest.getQueryString().equals("")) {
				myimage.public_read(db, myconfig, URLDecoder.decode(myrequest.getQueryString()).replaceAll("^/", ""), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			}
		}

		if ((! myimage.getServerFilename().equals("")) && (server != null)) {
			String server_filename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + myimage.getServerFilename());
			String lastmodified = Common.fileLastModified(server_filename);
			if ((lastmodified.equals("")) || (myimage.getUpdated().compareTo(lastmodified) > 0) || (myimage.getPublished().compareTo(lastmodified) > 0)) {
				Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/download"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + myimage.getServerFilename() + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:getImage1\"", "", server, mysession, myrequest, myresponse);
			}
		}

		setSessionUserExperience(myimage, mysession, myresponse, db, myconfig);
		accesspermission = RequireUser.checkUserAccessRestrictions(text, true, myimage, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) myimage = new Content(text);
		if (! myimage.getContentClass().equals("image")) myimage = new Content(text);
		return myimage;
	}



	public Content getFile(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return getFile(null, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Content getFile(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) {
		return getFile(null, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Content getFile(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) {
		return getFileById(myrequest.getParameter("id"), server, mysession, myrequest, myresponse, myconfig, db, mywebsite);
	}
	public Content getFileById(String id, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) {
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		Content myfile = new Content(text);
		setSessionModeFromRequest(mysession, myrequest);
		setSessionVersionFromRequest(mysession, myrequest);
		boolean accesspermission = true;
		boolean preview = false;
		if (mywebsite == null) mywebsite = new Website(text);

		if ((mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin"))) {
			preview = true;
			accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
			}
			myfile = new Content(text);
			if (Common.number(myrequest.getParameter("archive"))>0) {
				myfile.archive_read(db, myconfig, myrequest.getParameter("archive"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if ((mysession.get("mode").equals("preview")) && (! myfile.getScheduledPublish().equals("")) && (myfile.getScheduledPublish().compareTo(timestamp) > 0)) {
					if (myrequest.getParameter("mode").equals("preview")) {
						mysession.set("preview_scheduled", myfile.getScheduledPublish());
					}
					myfile.archive_read(db, myconfig, myrequest.getParameter("archive"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				}
			} else if (! id.equals("")) {
				myfile.scheduled_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if ((mysession.get("mode").equals("preview")) && (! myfile.getScheduledPublish().equals("")) && (myfile.getScheduledPublish().compareTo(timestamp) > 0)) {
					if (myrequest.getParameter("mode").equals("preview")) {
						mysession.set("preview_scheduled", myfile.getScheduledPublish());
					}
					myfile.scheduled_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				}
			} else if (! myrequest.getQueryString().equals("")) {
				myfile.scheduled_read(db, myconfig, URLDecoder.decode(myrequest.getQueryString()).replaceAll("^/", ""), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if (myfile.getId().equals("")) {
					myfile.public_read(db, myconfig, URLDecoder.decode(myrequest.getQueryString()).replaceAll("^/", ""), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
					myfile.scheduled_read(db, myconfig, myfile.getId(), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				}
				if ((mysession.get("mode").equals("preview")) && (! myfile.getScheduledPublish().equals("")) && (myfile.getScheduledPublish().compareTo(timestamp) > 0)) {
					if (myrequest.getParameter("mode").equals("preview")) {
						mysession.set("preview_scheduled", myfile.getScheduledPublish());
					}
					myfile.scheduled_read(db, myconfig, myfile.getId(), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				}
			}
		}
		if (! preview) {
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
			}
			myfile = new Content(text);
			if (! id.equals("")) {
				myfile.public_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			} else if (! myrequest.getQueryString().equals("")) {
				myfile.public_read(db, myconfig, URLDecoder.decode(myrequest.getQueryString()).replaceAll("^/", ""), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			}
		}

		if ((! myfile.getServerFilename().equals("")) && (server != null)) {
			String server_filename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + myfile.getServerFilename());
			String lastmodified = Common.fileLastModified(server_filename);
			if ((lastmodified.equals("")) || (myfile.getUpdated().compareTo(lastmodified) > 0) || (myfile.getPublished().compareTo(lastmodified) > 0)) {
				Common.executeFile(Common.getRealPath(server, "/" + text.display("adminpath") + "/api/download"), "\"" + myconfig.get(db, "URLrootpath").replaceAll("^/", "") + myfile.getServerFilename() + "\"" + " " + "\"" + myconfig.get(db, "csURLrootpath") + "\"" + " " + "\"QQQ:DEBUG:getFile1\"", "", server, mysession, myrequest, myresponse);
			}
		}

		setSessionUserExperience(myfile, mysession, myresponse, db, myconfig);
		accesspermission = RequireUser.checkUserAccessRestrictions(text, true, myfile, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) myfile = new Content(text);
		if ((! myfile.getContentClass().equals("file")) && (! myfile.getContentClass().equals("image"))) myfile = new Content(text);
		return myfile;
	}



	public Content getLink(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return getLink(mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Content getLink(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) {
		return getLinkById(myrequest.getParameter("id"), mysession, myrequest, myresponse, myconfig, db, mywebsite);
	}
	public Content getLinkById(String id, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) {
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		Content mylink = new Content(text);
		setSessionModeFromRequest(mysession, myrequest);
		setSessionVersionFromRequest(mysession, myrequest);
		boolean accesspermission = true;
		boolean preview = false;
		if (mywebsite == null) mywebsite = new Website(text);

		if ((mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin"))) {
			preview = true;
			accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
			}
			mylink = new Content(text);
			if (Common.number(myrequest.getParameter("archive"))>0) {
				mylink.archive_read(db, myconfig, myrequest.getParameter("archive"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if ((mysession.get("mode").equals("preview")) && (! mylink.getScheduledPublish().equals("")) && (mylink.getScheduledPublish().compareTo(timestamp) > 0)) {
					if (myrequest.getParameter("mode").equals("preview")) {
						mysession.set("preview_scheduled", mylink.getScheduledPublish());
					}
					mylink.archive_read(db, myconfig, myrequest.getParameter("archive"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				}
			} else if (! id.equals("")) {
				mylink.scheduled_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if ((mysession.get("mode").equals("preview")) && (! mylink.getScheduledPublish().equals("")) && (mylink.getScheduledPublish().compareTo(timestamp) > 0)) {
					if (myrequest.getParameter("mode").equals("preview")) {
						mysession.set("preview_scheduled", mylink.getScheduledPublish());
					}
					mylink.scheduled_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				}
			} else if (! myrequest.getQueryString().equals("")) {
				mylink.scheduled_read(db, myconfig, URLDecoder.decode(myrequest.getQueryString()).replaceAll("^/", ""), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				if (mylink.getId().equals("")) {
					mylink.public_read(db, myconfig, URLDecoder.decode(myrequest.getQueryString()).replaceAll("^/", ""), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
					mylink.scheduled_read(db, myconfig, mylink.getId(), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				}
				if ((mysession.get("mode").equals("preview")) && (! mylink.getScheduledPublish().equals("")) && (mylink.getScheduledPublish().compareTo(timestamp) > 0)) {
					if (myrequest.getParameter("mode").equals("preview")) {
						mysession.set("preview_scheduled", mylink.getScheduledPublish());
					}
					mylink.scheduled_read(db, myconfig, mylink.getId(), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
				}
			}
		}
		if (! preview) {
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
			}
			mylink = new Content(text);
			if (! id.equals("")) {
				mylink.public_read(db, myconfig, id, mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			} else if (! myrequest.getQueryString().equals("")) {
				mylink.public_read(db, myconfig, URLDecoder.decode(myrequest.getQueryString()).replaceAll("^/", ""), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);
			}
		}

		setSessionUserExperience(mylink, mysession, myresponse, db, myconfig);
		accesspermission = RequireUser.checkUserAccessRestrictions(text, true, mylink, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) mylink = new Content(text);
		if (! mylink.getContentClass().equals("link")) mylink = new Content(text);
		return mylink;
	}



	public Page getGuestbookPage(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return getGuestbookPage(server, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Page getGuestbookPage(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) throws Exception {
		setSessionModeFromRequest(mysession, myrequest);
		setSessionVersionFromRequest(mysession, myrequest);
		setSessionPresentationFromRequest(mysession, myrequest);
		setSessionCurrencyFromRequest(mysession, myrequest, db, myconfig);
		boolean accesspermission = true;
		if (mywebsite == null) mywebsite = new Website(text);

		Page myguestbookpage;
		if ((mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin"))) {
			accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
			if ((mysession.get("mode").equals("admin")) && (! myrequest.getParameter("menu").equals(""))) {
				mysession.set("menu", html.encodeHtmlEntities(myrequest.getParameter("menu")));
			}
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
				setSessionCurrencyForVersion(mysession, db, myconfig);
			}
			mysession.set("default_country", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_country"));
			mysession.set("default_state", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_state"));
			mysession.set("default_price", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_price"));
			myguestbookpage = new Page(text);
			if (Common.number(myrequest.getParameter("archive"))>0) {
				myguestbookpage.archive_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("archive"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else if (! myrequest.getParameter("id").equals("")) {
				myguestbookpage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else {
				myguestbookpage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_guestbook_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			}
		} else {
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
				setSessionCurrencyForVersion(mysession, db, myconfig);
			}
			mysession.set("default_country", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_country"));
			mysession.set("default_state", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_state"));
			mysession.set("default_price", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_price"));
			myguestbookpage = new Page(text);
			if (! myrequest.getParameter("id").equals("")) {
				myguestbookpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else {
				myguestbookpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_guestbook_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			}
		}

		if (mysession.get("currency").equals("")) {
			setSessionCurrencyForVersion(mysession, db, myconfig);
		}

		setPageStyleSheet(myguestbookpage, mysession, myconfig, db);
		accesspermission = RequireUser.checkUserAccessRestrictions(text, true, myguestbookpage, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) myguestbookpage = new Page(text);

		Page myguestbookentry = new Page(text);
		if (! myrequest.getParameter("entry").equals("")) {
			myguestbookentry.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("entry"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		} else {
			myguestbookentry.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_guestbook_entry"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		}
		myguestbookpage.parse_output_guestbook(db, myconfig, myguestbookentry, mysession, mysession.get("version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"));

		accesspermission = RequireUser.checkUserAccessRestrictions(text, true, myguestbookentry, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) myguestbookentry = new Page(text);

		return myguestbookpage;
	}



	public Page getShopcartPage(Website mywebsite, ServletContext server, String inifile, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String original_database) throws Exception {
		if (mywebsite == null) mywebsite = new Website(text);
		Page myshopcartpage = new Page(text);
		if (! myrequest.getParameter("complete").equals("")) {
			if ((! myconfig.get(db, "captcha").equals("")) && (myconfig.get(db, "captcha_shopcart").equals("yes")) && ((myconfig.get(db, "captcha_user").equals("yes")) || (mysession.get("username").equals("")))) {
				mysession.set("captcha_error", "");
				CAPTCHA mycaptcha = new CAPTCHA(text);
				if (! mycaptcha.valid(mysession, server, myrequest, myconfig, db)) {
//					error += "<br>" + mycaptcha.error;
					mysession.set("captcha_error", mycaptcha.error);
					myrequest.setParameter("confirm", "confirm");
					myrequest.setParameter("complete", "");
					mysession.set("POST", Common.serialize(myrequest));
					if (! mysession.get("captcha_url").equals("")) {
						myresponse.sendRedirect(Common.crlf_clean(mysession.get("captcha_url")));
					}
					return myshopcartpage;
				}
			}
		}
		if (((! myrequest.getParameter("checkout").equals("")) || (! myrequest.getParameter("confirm").equals("")) || (! myrequest.getParameter("complete").equals(""))) && (myconfig.get(db, "authorize_shopcart").equals("yes")) && (! Common.authorized(mysession, myrequest, "shopcart"))) {
			return myshopcartpage;
		}
		if (((! myrequest.getParameter("invoice_email").equals("")) && (" " + myconfig.get(db, "banned_emails").replaceAll("\r"," ").replaceAll("\n"," ") + " ").matches(".* " + myrequest.getParameter("invoice_email").replaceAll("\\.", "\\\\.") + " .*")) || ((! myrequest.getParameter("delivery_email").equals("")) && (" " + myconfig.get(db, "banned_emails").replaceAll("\r"," ").replaceAll("\n"," ") + " ").matches(".* " + myrequest.getParameter("delivery_email").replaceAll("\\.", "\\\\.") + " .*"))) {
			// spam trap
			myshopcartpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_shopcart_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		} else if (! myrequest.getParameter("validate_email").equals("")) {
			// spam trap
			myshopcartpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_shopcart_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		} else if (myrequest.getParameter("delivery_address").matches(".*href.*http://.*")) {
			// spam trap
			myshopcartpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_shopcart_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		} else if (myrequest.getParameter("invoice_address").matches(".*href.*http://.*")) {
			// spam trap
			myshopcartpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_shopcart_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		} else {
			String save_session_shopcart = mysession.get("shopcart");
			Order order = new Order();
			String payment = "";
			String delivery = "";
			Shopcart myshopcart = getShopcart(server, mysession, myrequest, myresponse, myconfig, db, (! myrequest.getParameter("complete").equals("")));
			String myorderavailability = myshopcart.getOrderAvailability();
			if (myorderavailability.equals("")) {
				if ((! myrequest.getParameter("complete").equals("")) && (! myrequest.getParameter("add").equals(""))) {
					mysession.set("order_complete_flag", "0");
					mysession.set("shopcart", "");
					myshopcart.save(mysession, db);
				}
				myshopcart = getShopcart(server, mysession, myrequest, myresponse, myconfig, db, (! myrequest.getParameter("complete").equals("")));
				order = doOrder(myshopcart, mywebsite, server, mysession, myrequest, myresponse, myconfig, db);
				payment = doPayment(order, mywebsite, server, mysession, myrequest, myresponse, myconfig, db);
				delivery = doDeliver(order, mywebsite, server, inifile, mysession, myrequest, myresponse, myconfig, db, original_database);
				doOrderEmails(myshopcart, order, payment, delivery, server, mysession, myrequest, myresponse, myconfig, db);
			}

			setSessionModeFromRequest(mysession, myrequest);
			setSessionVersionFromRequest(mysession, myrequest);
			setSessionPresentationFromRequest(mysession, myrequest);
			setSessionCurrencyFromRequest(mysession, myrequest, db, myconfig);
			boolean accesspermission = true;

			if (mysession.get("currency").equals("")) {
				setSessionCurrencyForVersion(mysession, db, myconfig);
			}

			if ((mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin"))) {
				accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
				if ((mysession.get("mode").equals("admin")) && (! myrequest.getParameter("menu").equals(""))) {
					mysession.set("menu", html.encodeHtmlEntities(myrequest.getParameter("menu")));
				}
				if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
					mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
					setSessionCurrencyForVersion(mysession, db, myconfig);
				}
				mysession.set("default_country", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_country"));
				mysession.set("default_state", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_state"));
				mysession.set("default_price", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_price"));
				String website_template = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_template");
				String website_stylesheet = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_stylesheet");
				myshopcartpage = new Page(text);
				if (Common.number(myrequest.getParameter("archive"))>0) {
					myshopcartpage.archive_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("archive"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else if (! myrequest.getParameter("id").equals("")) {
					myshopcartpage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else if ((! myorderavailability.equals("")) && (! mysession.get("shopcart").equals("")) && ((myrequest.getQueryString().equals("complete")) || (! myrequest.getParameter("complete").equals("")))) {
					myshopcartpage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_confirm_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else if ((((! myshopcart.getOrderQuantity().equals("0")) && (myshopcart.getOutOfStock().equals(""))) || (! myorderavailability.equals(""))) && (! mysession.get("shopcart").equals("")) && ((myrequest.getQueryString().equals("complete")) || (! myrequest.getParameter("complete").equals("")))) {
					myshopcartpage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_complete_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else if ((((! myshopcart.getOrderQuantity().equals("0")) && (myshopcart.getOutOfStock().equals(""))) || (! myorderavailability.equals(""))) && (! mysession.get("shopcart").equals("")) && ((myrequest.getQueryString().equals("confirm")) || (! myrequest.getParameter("confirm").equals("")))) {
					myshopcartpage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_confirm_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else if ((((! myshopcart.getOrderQuantity().equals("0")) && (myshopcart.getOutOfStock().equals(""))) || (! myorderavailability.equals(""))) && (! mysession.get("shopcart").equals("")) && ((myrequest.getQueryString().equals("checkout")) || (! myrequest.getParameter("checkout").equals("")))) {
					myshopcartpage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_checkout_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else if ((((myshopcart.getOrderQuantity().equals("0")) && (myorderavailability.equals(""))) || (mysession.get("shopcart").equals(""))) && ((myrequest.getQueryString().equals("complete")) || (! myrequest.getParameter("complete").equals("")) || (myrequest.getQueryString().equals("confirm")) || (! myrequest.getParameter("confirm").equals("")) || (myrequest.getQueryString().equals("checkout")) || (! myrequest.getParameter("checkout").equals(""))) && (! myconfig.get(db, "default_empty_page").equals(""))) {
					myshopcartpage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_empty_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else {
					myshopcartpage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_shopcart_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				}
			} else {
				if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
					mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
					setSessionCurrencyForVersion(mysession, db, myconfig);
				}
				mysession.set("default_country", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_country"));
				mysession.set("default_state", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_state"));
				mysession.set("default_price", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_price"));
				String website_template = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_template");
				String website_stylesheet = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_stylesheet");
				myshopcartpage = new Page(text);
				if (! myrequest.getParameter("id").equals("")) {
					myshopcartpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else if ((! myorderavailability.equals("")) && (! mysession.get("shopcart").equals("")) && ((myrequest.getQueryString().equals("complete")) || (! myrequest.getParameter("complete").equals("")))) {
					myshopcartpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_confirm_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else if ((((! myshopcart.getOrderQuantity().equals("0")) && (myshopcart.getOutOfStock().equals(""))) || (! myorderavailability.equals(""))) && (! mysession.get("shopcart").equals("")) && ((myrequest.getQueryString().equals("complete")) || (! myrequest.getParameter("complete").equals("")))) {
					myshopcartpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_complete_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else if ((((! myshopcart.getOrderQuantity().equals("0")) && (myshopcart.getOutOfStock().equals(""))) || (! myorderavailability.equals(""))) && (! mysession.get("shopcart").equals("")) && ((myrequest.getQueryString().equals("confirm")) || (! myrequest.getParameter("confirm").equals("")))) {
					myshopcartpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_confirm_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else if ((((! myshopcart.getOrderQuantity().equals("0")) && (myshopcart.getOutOfStock().equals(""))) || (! myorderavailability.equals(""))) && (! mysession.get("shopcart").equals("")) && ((myrequest.getQueryString().equals("checkout")) || (! myrequest.getParameter("checkout").equals("")))) {
					myshopcartpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_checkout_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else if ((((myshopcart.getOrderQuantity().equals("0")) && (myorderavailability.equals(""))) || (mysession.get("shopcart").equals(""))) && ((myrequest.getQueryString().equals("complete")) || (! myrequest.getParameter("complete").equals("")) || (myrequest.getQueryString().equals("confirm")) || (! myrequest.getParameter("confirm").equals("")) || (myrequest.getQueryString().equals("checkout")) || (! myrequest.getParameter("checkout").equals(""))) && (! myconfig.get(db, "default_empty_page").equals(""))) {
					myshopcartpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_empty_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else {
					myshopcartpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_shopcart_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				}
			}

			setPageStyleSheet(myshopcartpage, mysession, myconfig, db);
			accesspermission = RequireUser.checkUserAccessRestrictions(text, true, myshopcartpage, mysession, myrequest, myresponse, myconfig, db);
			if (! accesspermission) myshopcartpage = new Page(text);

			Page myshopcartitem = new Page(text);
			if (! myrequest.getParameter("item").equals("")) {
				myshopcartitem.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("item"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else if ((! myorderavailability.equals("")) && (! mysession.get("shopcart").equals("")) && ((myrequest.getQueryString().equals("complete")) || (! myrequest.getParameter("complete").equals("")))) {
				myshopcartitem.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_confirm_entry"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else if ((((! myshopcart.getOrderQuantity().equals("0")) && (myshopcart.getOutOfStock().equals(""))) || (! myorderavailability.equals(""))) && (! mysession.get("shopcart").equals("")) && ((myrequest.getQueryString().equals("complete")) || (! myrequest.getParameter("complete").equals("")))) {
				myshopcartitem.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_complete_entry"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else if ((((! myshopcart.getOrderQuantity().equals("0")) && (myshopcart.getOutOfStock().equals(""))) || (! myorderavailability.equals(""))) && (! mysession.get("shopcart").equals("")) && ((myrequest.getQueryString().equals("confirm")) || (! myrequest.getParameter("confirm").equals("")))) {
				myshopcartitem.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_confirm_entry"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else if ((((! myshopcart.getOrderQuantity().equals("0")) && (myshopcart.getOutOfStock().equals(""))) || (! myorderavailability.equals(""))) && (! mysession.get("shopcart").equals("")) && ((myrequest.getQueryString().equals("checkout")) || (! myrequest.getParameter("checkout").equals("")))) {
				myshopcartitem.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_checkout_entry"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else {
				myshopcartitem.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_shopcart_entry"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			}
			accesspermission = RequireUser.checkUserAccessRestrictions(text, true, myshopcartitem, mysession, myrequest, myresponse, myconfig, db);
			if (! accesspermission) myshopcartitem = new Page(text);

			if (! myrequest.getParameter("id").equals("")) {
				myshopcartpage.parse_output_shopcart(myshopcart, payment, delivery, server, myrequest, myresponse, mysession, "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), db, myconfig, myshopcartitem, false);
			} else if ((! myorderavailability.equals("")) && (! mysession.get("shopcart").equals("")) && ((myrequest.getQueryString().equals("complete")) || (! myrequest.getParameter("complete").equals("")))) {
				myshopcartpage.parse_output_shopcart(myshopcart, payment, delivery, server, myrequest, myresponse, mysession, "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), db, myconfig, myshopcartitem, false);
			} else if ((! mysession.get("shopcart").equals("")) && ((myrequest.getQueryString().equals("complete")) || (! myrequest.getParameter("complete").equals("")))) {
				myshopcartpage.parse_output_shopcart(myshopcart, payment, delivery, server, myrequest, myresponse, mysession, "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), db, myconfig, myshopcartitem, false, false);
			} else if ((! mysession.get("shopcart").equals("")) && ((myrequest.getQueryString().equals("confirm")) || (! myrequest.getParameter("confirm").equals("")))) {
				myshopcartpage.parse_output_shopcart(myshopcart, payment, delivery, server, myrequest, myresponse, mysession, "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), db, myconfig, myshopcartitem, false);
			} else if ((! mysession.get("shopcart").equals("")) && ((myrequest.getQueryString().equals("checkout")) || (! myrequest.getParameter("checkout").equals("")))) {
				myshopcartpage.parse_output_shopcart(myshopcart, payment, delivery, server, myrequest, myresponse, mysession, "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), db, myconfig, myshopcartitem, false);
			} else {
				myshopcartpage.parse_output_shopcart(myshopcart, payment, delivery, server, myrequest, myresponse, mysession, "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), db, myconfig, myshopcartitem, true);
			}
			myshopcartpage.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, myshopcartpage.getId(), "content_public", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			if (myorderavailability.equals("")) {
				if ((! order.getId().equals("")) && ((myrequest.getQueryString().equals("complete")) || (! myrequest.getParameter("complete").equals("")))) {
					myshopcart.delete(mysession);
					myshopcart.save(mysession, db);
				}
				if ((! myrequest.getParameter("complete").equals("")) && (! myrequest.getParameter("add").equals(""))) {
					mysession.set("shopcart", save_session_shopcart);
					myshopcart.save(mysession, db);
				}
			}
			mysession.set("shopcart_availability", myorderavailability);
			mysession.set("shopcart_outofstock", myshopcart.getOutOfStock());

			if (! myrequest.getParameter("redirect").equals("")) {
				if ((myrequest.getParameter("redirect").startsWith("http://")) || (myrequest.getParameter("redirect").startsWith("https://"))) {
					if ((! myconfig.get(db, "redirect_urls").trim().equals("")) && (! Common.startsWithAnyOf(Common.crlf_clean(myrequest.getParameter("redirect")), myconfig.get(db, "redirect_urls")))) {
						// ignore
					} else {
						myresponse.sendRedirect(Common.crlf_clean(myrequest.getParameter("redirect")));
					}
				} else {
					myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + Common.crlf_clean(myrequest.getParameter("redirect")));
				}
			}
		}
		return myshopcartpage;
	}



	public Page getLoginPage(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return getLoginPage(server, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Page getLoginPage(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) throws Exception {
		if (mywebsite == null) mywebsite = new Website(text);
		Page myloginpage;
		setSessionModeFromRequest(mysession, myrequest);
		setSessionVersionFromRequest(mysession, myrequest);
		setSessionPresentationFromRequest(mysession, myrequest);
		setSessionCurrencyFromRequest(mysession, myrequest, db, myconfig);
		myloginpage = new Page(text);
		if (! myrequest.getParameter("id").equals("")) {
			mysession.set("login_page", html.encodeHtmlEntities(myrequest.getParameter("id")));
			myloginpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		} else if (! mysession.get("login_page").equals("")) {
			myloginpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mysession.get("login_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		} else if ((mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) && (! mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_login").equals(""))) {
			myloginpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_login"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		} else if (! myconfig.get(db, "default_login").equals("")) {
			myloginpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_login"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		}
		setPageStyleSheet(myloginpage, mysession, myconfig, db);
		String body = myloginpage.getBody();
		body = Pattern.compile("/login_post.jsp").matcher(body).replaceAll("/login_post.jsp?url="+URLEncoder.encode(myrequest.getParameter("url")));
		myloginpage.setBody(body);

		if (mysession.get("currency").equals("")) {
			setSessionCurrencyForVersion(mysession, db, myconfig);
		}

		boolean accesspermission = RequireUser.checkUserAccessRestrictions(text, true, myloginpage, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) myloginpage = new Page(text);
		return myloginpage;
	}



	public Shopcart getShopcart(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return getShopcart(server, mysession, myrequest, myresponse, myconfig, db, true);
	}
	public Shopcart getShopcart(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, boolean stockcheck) {
		setSessionModeFromRequest(mysession, myrequest);
		setSessionVersionFromRequest(mysession, myrequest);
		setSessionPresentationFromRequest(mysession, myrequest);
		setSessionCurrencyForVersion(mysession, db, myconfig);
		setSessionCurrencyFromRequest(mysession, myrequest, db, myconfig);
		Shopcart myshopcart = new Shopcart(text);
		myshopcart.read(mysession, db, myconfig);
		myshopcart.getForm(myrequest, mysession);
		if (myshopcart.getDeliveryCountry().equals("")) {
			myshopcart.setDeliveryCountry(mysession.get("default_country"));
			if (myshopcart.getDeliveryState().equals("")) {
				myshopcart.setDeliveryState(mysession.get("default_state"));
			}
		}
		if (myshopcart.getDeliveryCountry().equals("")) {
			myshopcart.setDeliveryCountry(myconfig.get(db, "default_country"));
			if (myshopcart.getDeliveryState().equals("")) {
				myshopcart.setDeliveryState(myconfig.get(db, "default_state"));
			}
		}
		myshopcart.create(mysession);
		myshopcart.save(mysession, db);
		myshopcart.calculate(server, mysession, myrequest, myresponse, mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), stockcheck);
		return myshopcart;
	}



	public Order doOrder(Shopcart myshopcart, Website website, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		Order order = new Order();
		if (((myrequest.getQueryString().equals("complete")) || (! myrequest.getParameter("complete").equals(""))) && (! myshopcart.getOrderQuantity().equals("0")) && (myshopcart.getOutOfStock().equals(""))) {
			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			String username = mysession.get("username");
			order.setUserId(mysession.get("userid"));
			order.setCreated(timestamp);
			order.setCreatedBy(username);
			order.setUpdated(timestamp);
			order.setUpdatedBy(username);
			order.setPublished("");
			order.setPublishedBy("");
			myshopcart.calculate(server, mysession, myrequest, myresponse, "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), false);
			if (Common.number(myshopcart.getOrderTotal()) == 0) {
				order.setPaid(timestamp);
				order.setStatus(myconfig.get(db, "order_workflow_free"));
			} else {
				order.setStatus(myconfig.get(db, "order_workflow_new"));
			}
			if (myshopcart.getOrderQuantity().equals("0")) {
				order = new Order();
			} else if (mysession.get("order_complete_flag").equals("1")) {
				order = new Order();
			} else {
				mysession.set("order_complete_flag", "1");
				if (((order.getUserId().equals("")) || (order.getUserId().equals("0"))) && (! myconfig.get(db, "productdelivery_username_default").equals(""))) {
					User user = doDeliverUser(order, website, server, mysession, myrequest, myresponse, myconfig, db, myconfig.get(db, "productdelivery_username_default"));
		 		}
				order.create(server, mysession, myrequest, myresponse, "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), myshopcart);
			}
		} else {
			mysession.set("order_complete_flag", "0");
		}
		return order;
	}



	public void doOrderEmails(Shopcart myshopcart, Order order, String payment, String delivery, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if ((! order.getId().equals("")) && ((myrequest.getQueryString().equals("complete")) || (! myrequest.getParameter("complete").equals(""))) && (Common.number(myshopcart.getOrderQuantity()) > 0)) {
			String sender = "";
			if (! mysession.get("invoice_email").equals("")) {
				sender = mysession.get("invoice_email");
			} else if (! mysession.get("delivery_email").equals("")) {
				sender = mysession.get("delivery_email");
			} else {
				sender = "shopcart@" + myrequest.getServerName();
			}
			String recipient = "";
			if ((! myrequest.getParameter("order_form_recipient").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(myrequest.getParameter("order_form_recipient"))>=0)) {
				recipient = html.encodeHtmlEntities(myrequest.getParameter("order_form_recipient"));
			} else {
				recipient = myconfig.get(db, "order_form_recipient");
			}
			HashMap<String,String> requestForm = Email.getForm(myrequest);

			if (! myrequest.getParameter("email_confirmation").equals("no")) {
				Page myconfirmationpage = new Page(text);
				if (! myrequest.getParameter("email_confirmation").equals("")) {
					myconfirmationpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("email_confirmation"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else {
					myconfirmationpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_confirmation_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				}
				boolean accesspermission = RequireUser.checkUserAccessRestrictions(text, true, myconfirmationpage, mysession, myrequest, myresponse, myconfig, db);
				if (! accesspermission) myconfirmationpage = new Page(text);
				Page myconfirmationentry = new Page(text);
				myconfirmationentry.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_confirmation_entry"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				myconfirmationpage.parse_output_delivery(order, null, null, null, "", server, mysession, myrequest, myresponse, db, myconfig);
				myconfirmationpage.parse_output_shopcart(myshopcart, payment, delivery, server, myrequest, myresponse, mysession, "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), db, myconfig, myconfirmationentry, false, false);
				if (! mysession.get("invoice_email").equals("")) {
					Email.send_email(text, requestForm, myconfirmationpage.getTitle(), myconfirmationpage.getBody(), "", recipient, mysession.get("invoice_email"), "", "", "", "", myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
				} else if  (! mysession.get("delivery_email").equals("")) {
					Email.send_email(text, requestForm, myconfirmationpage.getTitle(), myconfirmationpage.getBody(), "", recipient, mysession.get("delivery_email"), "", "", "", "", myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
				}
			}

			if (! myrequest.getParameter("email_notification").equals("no")) {
				Page mynotificationpage = new Page(text);
				if (! myrequest.getParameter("email_notification").equals("")) {
					mynotificationpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("email_notification"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				} else {
					mynotificationpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_notification_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				}
				boolean accesspermission = RequireUser.checkUserAccessRestrictions(text, true, mynotificationpage, mysession, myrequest, myresponse, myconfig, db);
				if (! accesspermission) mynotificationpage = new Page(text);
				Page mynotificationentry = new Page(text);
				mynotificationentry.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_notification_entry"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				mynotificationpage.parse_output_delivery(order, null, null, null, "", server, mysession, myrequest, myresponse, db, myconfig);
				mynotificationpage.parse_output_shopcart(myshopcart, payment, delivery, server, myrequest, myresponse, mysession, "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), db, myconfig, mynotificationentry, false, false);
				if (! mysession.get("invoice_email").equals("")) {
					Email.send_email(text, requestForm, mynotificationpage.getTitle(), mynotificationpage.getBody(), "", mysession.get("invoice_email"), recipient, "", "", "", "", myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
				} else if  (! mysession.get("delivery_email").equals("")) {
					Email.send_email(text, requestForm, mynotificationpage.getTitle(), mynotificationpage.getBody(), "", mysession.get("delivery_email"), recipient, "", "", "", "", myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
				} else {
					Email.send_email(text, requestForm, mynotificationpage.getTitle(), mynotificationpage.getBody(), "", "shopcart@" + myrequest.getServerName(), recipient, "", "", "", "", myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
				}
			}
		}
	}



	public String doPayment(Order order, Website website, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		String output = "";
		if ((! order.getId().equals("")) && (! myconfig.get(db, "payment_provider").equals(""))) {
			output = Common.execute("/" + text.display("adminpath") + "/module/" + myconfig.get(db, "payment_provider") + "/payment.jsp", "order", order, "payment", server, mysession.getSession(), myrequest.getRequest(), myresponse.getResponse());
			order.read(db, order.getId());
		}
		return output;
	}



	public String doDeliver(Order order, Website website, ServletContext server, String inifile, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String original_database) throws Exception {
		String output = "";
		if ((! order.getId().equals("")) && ((Common.number(order.getOrderTotal()) == 0) || (! order.getPaid().equals("")))) {
			int delivered = 0;
			int undelivered = 0;
			while (order.orderitems(db)) {
				if ((order.getOrderitem().getProductDelivery().equals("new")) || (order.getOrderitem().getProductDelivery().equals(""))) {
					// New user / hosting
					User user = doDeliverUser(order, website, server, mysession, myrequest, myresponse, myconfig, db, "");
// if (! order.getUserId().equals(user.getId())) {
// TODO handle existing default user account id - old logged in registered user account ~ new default user account
// }
					Hosting hosting = doDeliverHosting(user, order, website, server, inifile, mysession, myrequest, myresponse, myconfig, db, original_database);
					String program_output = doDeliverProgram(order, website, server, mysession, myrequest, myresponse, myconfig, db);
					output += program_output;
					output += doDeliverEmail(user, hosting, program_output, order, website, server, mysession, myrequest, myresponse, myconfig, db);
					output += doDeliverPage(user, hosting, program_output, order, website, server, mysession, myrequest, myresponse, myconfig, db);
					if ((! user.getId().equals("")) || (! hosting.getId().equals("")) || (! program_output.equals(""))) {
						delivered++;
					} else {
						undelivered++;
					}
				} else if (order.getOrderitem().getProductDelivery().equals("renew")) {
					// QQQ Renew hosting / user scheduled expiration
					undelivered++;
				} else if (order.getOrderitem().getProductDelivery().equals("addon")) {
					// Add hosting licenses/address / user groups/types
					User user = doAddOnUser(order, website, server, mysession, myrequest, myresponse, myconfig, db);
					Hosting hosting = doAddOnHosting(order, website, server, inifile, mysession, myrequest, myresponse, myconfig, db, original_database);
					String program_output = doDeliverProgram(order, website, server, mysession, myrequest, myresponse, myconfig, db);
					output += program_output;
					output += doDeliverEmail(user, hosting, program_output, order, website, server, mysession, myrequest, myresponse, myconfig, db);
					output += doDeliverPage(user, hosting, program_output, order, website, server, mysession, myrequest, myresponse, myconfig, db);
					if ((! user.getId().equals("")) || (! hosting.getId().equals("")) || (! program_output.equals(""))) {
						delivered++;
					} else {
						undelivered++;
					}
				} else {
					undelivered++;
				}
			}
			if (((order.getUserId().equals("")) || (order.getUserId().equals("0"))) && (! myconfig.get(db, "productdelivery_username_default").equals(""))) {
				User user = doDeliverUser(order, website, server, mysession, myrequest, myresponse, myconfig, db, myconfig.get(db, "productdelivery_username_default"));
	 		}
			if (undelivered == 0) {
				order.setStatus(myconfig.get(db, "order_workflow_delivered_complete"));
			} else if (delivered > 0) {
				order.setStatus(myconfig.get(db, "order_workflow_delivered_partial"));
			} else if (Common.number(order.getOrderTotal()) == 0) {
				order.setStatus(myconfig.get(db, "order_workflow_free"));
			} else if (! order.getPaid().equals("")) {
				order.setStatus(myconfig.get(db, "order_workflow_paid"));
			}
			order.update(db);
		}
		return output;
	}



	public User doDeliverUser(Order order, Website website, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String user_template_username) {
		User user = new User(text);
		if ((user_template_username.equals("")) && (order.getOrderitem() != null) && (! order.getOrderitem().getProductUser().equals(""))) {
			user_template_username = order.getOrderitem().getProductUser();
		}
		if ((! user_template_username.equals(""))) {
			String order_delivery_email = "";
			if (! order.getDeliveryEmail().equals("")) {
				order_delivery_email = order.getDeliveryEmail();
			} else {
				order_delivery_email = order.getInvoiceEmail();
			}
			String username = "";
			String[] usernames = order_delivery_email.toLowerCase().replaceAll("[^.@_a-z0-9]", "").split("[.@_]");
			for (int key=0; key<usernames.length; key++) {
				String value = usernames[key];
			        if ((! myconfig.get(db, "productdelivery_username_min").equals("")) && (value.length() < Common.number(myconfig.get(db, "productdelivery_username_min")))) {
			        	// too short
			        	usernames[key] = "";
			        }
			        if ((! myconfig.get(db, "productdelivery_username_max").equals("")) && (value.length() > Common.number(myconfig.get(db, "productdelivery_username_max")))) {
			        	// too long
			        	usernames[key] = "";
			        }
				if (! Pattern.compile("^[a-z]").matcher(value).find()) {
			        	// does not start with letter
			        	usernames[key] = "";
			        }
				if ((! myconfig.get(db, "productdelivery_username_blocked").equals("")) && (Pattern.compile("(" + myconfig.get(db, "productdelivery_username_blocked").replaceAll("[ \\r\\n\\t,]", "|") + ")").matcher(value).find())) {
		        		// blocked username
			        	usernames[key] = "";
			        }
			}
			Common.array_unshift(usernames, order_delivery_email);
			for (int key=0; key<usernames.length; key++) {
				String myusername = usernames[key];
				user = new User(text);
				user.readByUsername(myconfig.get(db, "superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myusername);
				if (user.getId().equals("")) {
					username = myusername;
					break;
				}
			}
			while(username.equals("")) {
			        String myusername = myconfig.get(db, "productdelivery_username_prefix") + (Math.round(Math.random()*89999999+10000000));
				user = new User(text);
				user.readByUsername(myconfig.get(db, "superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myusername);
				if (user.getId().equals("")) {
					username = myusername;
					break;
				}
			}
			if (! username.equals("")) {
				user = new User(text);
				user.readByUsername(myconfig.get(db, "superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, user_template_username);
				HashMap<String,Permission> permissions = user.permissions(db, "", "");
				String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
				user.setCreated(timestamp);
				user.setCreatedBy(username);
				user.setUpdated(timestamp);
				user.setUpdatedBy(username);
				user.setUsername(username);
				user.setPassword("" + (Math.round(Math.random()*89999999+10000000)));
				if (! order.getDeliveryName().equals("")) {
					user.setName(order.getDeliveryName());
				} else {
					user.setName(order.getInvoiceName());
				}
				if (! order.getDeliveryEmail().equals("")) {
					user.setEmail(order.getDeliveryEmail());
				} else {
					user.setEmail(order.getInvoiceEmail());
				}
				user.setCardType(order.getCardType());
				user.setCardNumber(order.getCardNumber());
				user.setCardIssuedMonth(order.getCardIssuedMonth());
				user.setCardIssuedYear(order.getCardIssuedYear());
				user.setCardExpiryMonth(order.getCardExpiryMonth());
				user.setCardExpiryYear(order.getCardExpiryYear());
				user.setCardName(order.getCardName());
				user.setCardCVC(order.getCardCVC());
				user.setCardIssue(order.getCardIssue());
				user.setCardPostalcode(order.getCardPostalcode());
				user.setDeliveryName(order.getDeliveryName());
				user.setDeliveryOrganisation(order.getDeliveryOrganisation());
				user.setDeliveryAddress(order.getDeliveryAddress());
				user.setDeliveryPostalcode(order.getDeliveryPostalcode());
				user.setDeliveryCity(order.getDeliveryCity());
				user.setDeliveryState(order.getDeliveryState());
				user.setDeliveryCountry(order.getDeliveryCountry());
				user.setDeliveryPhone(order.getDeliveryPhone());
				user.setDeliveryFax(order.getDeliveryFax());
				user.setDeliveryEmail(order.getDeliveryEmail());
				user.setDeliveryWebsite(order.getDeliveryWebsite());
				user.setInvoiceName(order.getInvoiceName());
				user.setInvoiceOrganisation(order.getInvoiceOrganisation());
				user.setInvoiceAddress(order.getInvoiceAddress());
				user.setInvoicePostalcode(order.getInvoicePostalcode());
				user.setInvoiceCity(order.getInvoiceCity());
				user.setInvoiceState(order.getInvoiceState());
				user.setInvoiceCountry(order.getInvoiceCountry());
				user.setInvoicePhone(order.getInvoicePhone());
				user.setInvoiceFax(order.getInvoiceFax());
				user.setInvoiceEmail(order.getInvoiceEmail());
				user.setInvoiceWebsite(order.getInvoiceWebsite());
				if (order.getOrderitem() != null) {
					String[] my_product_options = order.getOrderitem().getProductOptions().split("[\\r\\n]+");
					for (int i=0; i<my_product_options.length; i++) {
						String product_option = my_product_options[i];
						Pattern p = Pattern.compile("<([^<>]+)>([^<>]*)<\\/([^<>]+)>");
						Matcher m = p.matcher(product_option);
						if (m.find()) {
							String myname = m.group(1);
							String myvalue = m.group(2);
							if (myname.equals("user:group")) {
								user.setUsergroup(myvalue);
							} else if (myname.equals("user:type")) {
								user.setUsertype(myvalue);
							} else if (myname.equals("user:password")) {
								user.setPassword(myvalue);
							} else if (myname.equals("user:publish")) {
								user.setScheduledPublish(myvalue);
							} else if (myname.equals("user:notify")) {
								user.setScheduledNotify(myvalue);
							} else if (myname.equals("user:unpublish")) {
								user.setScheduledUnpublish(myvalue);
							} else if (myname.equals("user:publish_email")) {
								user.setScheduledPublishEmail(myvalue);
							} else if (myname.equals("user:notify_email")) {
								user.setScheduledNotifyEmail(myvalue);
							} else if (myname.equals("user:unpublish_email")) {
								user.setScheduledUnpublishEmail(myvalue);
							}
						}
					}
				}
				user.setScheduledLast("");
				Date mynow = new Date();
				if (user.getScheduledPublish().equals("")) {
					// starting now for remaining fixed given period
					user.setScheduledPublish(Common.strftime("%Y-%m-%d %H:%M:%S", mynow));
				} else if (user.getScheduledPublish().compareTo(Common.strftime("%Y-%m-%d %H:%M:%S", mynow)) >= 0) {
					// future fixed given period
				} else if (user.getScheduledUnpublish().compareTo(Common.strftime("%Y-%m-%d %H:%M:%S", mynow)) >= 0) {
					// starting now for remaining fixed given period
					user.setScheduledPublish(Common.strftime("%Y-%m-%d %H:%M:%S", mynow));
				} else {
					// starting now for duration of given period
					if ((user.getScheduledNotify().equals("")) || (user.getScheduledPublish().equals(""))) {
						user.setScheduledNotify("");
					} else {
						user.setScheduledNotify(Common.strftime("%Y-%m-%d %H:%M:%S", new Date(mynow.getTime() + (Common.strtotime(user.getScheduledNotify()).getTime() - Common.strtotime(user.getScheduledPublish()).getTime()))));
					}
					if ((user.getScheduledUnpublish().equals("")) || (user.getScheduledPublish().equals(""))) {
						user.setScheduledUnpublish("");
					} else {
						user.setScheduledUnpublish(Common.strftime("%Y-%m-%d %H:%M:%S", new Date(mynow.getTime() + (Common.strtotime(user.getScheduledUnpublish()).getTime() - Common.strtotime(user.getScheduledPublish()).getTime()))));
					}
					user.setScheduledPublish(Common.strftime("%Y-%m-%d %H:%M:%S", mynow));
				}
				user.create(db);
				Iterator mypermissions = permissions.keySet().iterator();
				while (mypermissions.hasNext()) {
					String mypermission = "" + mypermissions.next();
					if (! ((Permission)permissions.get(mypermission)).getUsername().equals("")) {
						((Permission)permissions.get(mypermission)).setUsername(username);
						((Permission)permissions.get(mypermission)).create(db);
					}
				}
				user.schedule(myconfig, db);
				UCpublishScheduled publishScheduled = new UCpublishScheduled(text);
				publishScheduled.doScheduled(server, mysession, myrequest, myresponse, myconfig, db);

				if ((order.getUserId().equals("")) || (order.getUserId().equals("0"))) {
					user.readByUsername(myconfig.get(db, "superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, user.getUsername());
					order.setUserId(user.getId());
					if (order.getCreatedBy().equals("")) {
						order.setCreatedBy(user.getUsername());
						order.setUpdatedBy(user.getUsername());
					}
					order.update(db);
				}
			}
		}
		return user;
	}



	public User doAddOnUser(Order order, Website website, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		// QQQ add-on user group(s)/type(s)
		User user = new User(text);
		return user;
	}



	public Hosting doDeliverHosting(User user, Order order, Website website, ServletContext server, String inifile, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String original_database) {
		Hosting hosting = new Hosting(text);
		if ((! order.getOrderitem().getProductHosting().equals("")) && ((! order.getDeliveryEmail().equals("")) || (! order.getInvoiceEmail().equals("")))) {
			String domain_prefix = myconfig.get(db, "productdelivery_hosting_www");
			String domain_postfix = myconfig.get(db, "productdelivery_hosting_domain");
			String order_delivery_email = "";
			if (! order.getDeliveryEmail().equals("")) {
				order_delivery_email = order.getDeliveryEmail();
			} else {
				order_delivery_email = order.getInvoiceEmail();
			}
			String product_options_subdomain = "";
//			String[] product_options = order.getOrderitem().getProductOptions().split("[\\r\\n]+");
//			if (! product_options[0].equals("")) {
//				Pattern p = Pattern.compile("<([^<>]+)>([^<>]*)<\\/([^<>]+)>");
//				Matcher m = p.matcher(product_options[0]);
//				if (m.find()) {
//					product_options_subdomain = m.group(2).toLowerCase().replaceAll("[^.@_a-z0-9]", "");
//				}
//			}
			String[] my_product_options = order.getOrderitem().getProductOptions().split("[\\r\\n]+");
			for (int i=0; i<my_product_options.length; i++) {
				String product_option = my_product_options[i];
				Pattern p = Pattern.compile("<([^<>]+)>([^<>]*)<\\/([^<>]+)>");
				Matcher m = p.matcher(product_option);
				if (m.find()) {
					String myname = m.group(1);
					String myvalue = m.group(2);
					if (myname.equals("hosting:subdomain")) {
						product_options_subdomain = "" + myvalue;
					}
				}
			}
			String subdomain = "";
			String[] subdomains = (product_options_subdomain + " " + order_delivery_email.toLowerCase().replaceAll("[^.@_a-z0-9]", "")).split("[.@_ ,]");
			for (int key=0; key<subdomains.length; key++) {
				String value = subdomains[key];
			        if ((! myconfig.get(db, "productdelivery_hosting_min").equals("")) && (value.length() < Common.number(myconfig.get(db, "productdelivery_hosting_min")))) {
			        	// too short
			        	subdomains[key] = "";
			        }
			        if ((! myconfig.get(db, "productdelivery_hosting_max").equals("")) && (value.length() > Common.number(myconfig.get(db, "productdelivery_hosting_max")))) {
			        	// too long
			        	subdomains[key] = "";
			        }
				if (! Pattern.compile("^[a-z]").matcher(value).find()) {
			        	// does not start with letter
			        	subdomains[key] = "";
			        }
				if (new File(Common.getRealPath(server, value)).exists()) {
					// path exists
			        	subdomains[key] = "";
				}
				if ((DB.db_type(myconfig.get(db, "productdelivery_hosting_database")).equals("mysql")) && (new File(myconfig.get(db, "hosting_api_database_rootpath") + value).exists())) {
					// database exists
			        	subdomains[key] = "";
				}
				if ((! myconfig.get(db, "productdelivery_hosting_blocked").equals("")) && (Pattern.compile("(" + myconfig.get(db, "productdelivery_hosting_blocked").replaceAll("[ \\r\\n\\t,]", "|") + ")").matcher(value).find())) {
		        		// blocked username
			        	subdomains[key] = "";
			        }
			}
//			Common.array_unshift(subdomains, order_delivery_email);
			for (int key=0; key<subdomains.length; key++) {
				String mysubdomain = subdomains[key];
				if (! mysubdomain.equals("")) {
					hosting = new Hosting(text);
					hosting.readClientAddress(db, domain_prefix + mysubdomain + domain_postfix);
					if (hosting.getId().equals("")) {
						subdomain = mysubdomain;
						break;
					}
				}
			}
			while(subdomain.equals("")) {
			        String mysubdomain = myconfig.get(db, "productdelivery_hosting_prefix") + (Math.round(Math.random()*89999999+10000000));
				hosting = new Hosting(text);
				hosting.readClientAddress(db, domain_prefix + mysubdomain + domain_postfix);
				if (hosting.getId().equals("")) {
					subdomain = mysubdomain;
					break;
				}
			}
			if (! subdomain.equals("")) {
				hosting = new Hosting(text);
				hosting.readClientAddress(db, order.getOrderitem().getProductHosting());
				mysession.set("hosting_api_id", order.getOrderitem().getProductHosting());

				String client_address = domain_prefix + subdomain + domain_postfix;
				String client_database = myconfig.get(db, "productdelivery_hosting_database");
				client_database = client_database.replaceAll("@@@domain@@@", subdomain.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				client_database = client_database.replaceAll("@@@username@@@", subdomain.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				client_database = client_database.replaceAll("@@@password@@@", "" + (Math.round(Math.random()*89999999+10000000)));
				client_database = client_database.replaceAll("@@@random@@@", "" + (Math.round(Math.random()*89999999+10000000)));
				String client_URLrootpath = "/" + subdomain + "/";
				String client_superadmin = hosting.getSuperadmin();
				String client_superadmin_password = "" + (Math.round(Math.random()*89999999+10000000));
				String client_superadmin_email = "";
				if (! order.getDeliveryEmail().equals("")) {
					client_superadmin_email = order.getDeliveryEmail();
				} else {
					client_superadmin_email = order.getInvoiceEmail();
				}

				if ((user != null) && (! user.getId().equals(""))) {
					hosting.setUserId(user.getId());
				}
				hosting.setClientAddress(client_address);
				hosting.setClientURLrootpath(client_URLrootpath);
				hosting.setClientDatabase(client_database);
				hosting.setSuperadmin(client_superadmin);
				hosting.setSuperadminPassword(client_superadmin_password);
				hosting.setSuperadminEmail(client_superadmin_email);
				if (! License.product(hosting.getPersonalLicense()).equals("")) {
					hosting.setPersonalLicense(License.generate(client_address, License.product(hosting.getPersonalLicense())));
				}
				if (! License.product(hosting.getProfessionalLicense()).equals("")) {
					hosting.setProfessionalLicense(License.generate(client_address, License.product(hosting.getProfessionalLicense())));
				}
				if (! License.product(hosting.getEnterpriseLicense()).equals("")) {
					hosting.setEnterpriseLicense(License.generate(client_address, License.product(hosting.getEnterpriseLicense())));
				}
				if (! License.product(hosting.getHostingLicense()).equals("")) {
					hosting.setHostingLicense(License.generate(client_address, License.product(hosting.getHostingLicense())));
				}
				if (! License.product(hosting.getEcommerceLicense()).equals("")) {
					hosting.setEcommerceLicense(License.generate(client_address, License.product(hosting.getEcommerceLicense())));
				}
				if (! License.product(hosting.getCommunityLicense()).equals("")) {
					hosting.setCommunityLicense(License.generate(client_address, License.product(hosting.getCommunityLicense())));
				}
				if (! License.product(hosting.getDatabasesLicense()).equals("")) {
					hosting.setDatabasesLicense(License.generate(client_address, License.product(hosting.getDatabasesLicense())));
				}
				if (! License.product(hosting.getStatisticsLicense()).equals("")) {
					hosting.setStatisticsLicense(License.generate(client_address, License.product(hosting.getStatisticsLicense())));
				}
				my_product_options = order.getOrderitem().getProductOptions().split("[\\r\\n]+");
				for (int i=0; i<my_product_options.length; i++) {
					String product_option = my_product_options[i];
					Pattern p = Pattern.compile("<([^<>]+)>([^<>]*)<\\/([^<>]+)>");
					Matcher m = p.matcher(product_option);
					if (m.find()) {
						String myname = m.group(1);
						String myvalue = m.group(2);
						if (myname.equals("hosting:group")) {
							hosting.setHostinggroup(myvalue);
						} else if (myname.equals("hosting:type")) {
							hosting.setHostingtype(myvalue);
						} else if (myname.equals("hosting:password")) {
							hosting.setSuperadminPassword(myvalue);
							client_superadmin_password = "" + myvalue;
						} else if (myname.equals("hosting:publish")) {
							hosting.setScheduledPublish(myvalue);
						} else if (myname.equals("hosting:notify")) {
							hosting.setScheduledNotify(myvalue);
						} else if (myname.equals("hosting:unpublish")) {
							hosting.setScheduledUnpublish(myvalue);
						} else if (myname.equals("hosting:publish_email")) {
							hosting.setScheduledPublishEmail(myvalue);
						} else if (myname.equals("hosting:notify_email")) {
							hosting.setScheduledNotifyEmail(myvalue);
						} else if (myname.equals("hosting:unpublish_email")) {
							hosting.setScheduledUnpublishEmail(myvalue);
						} else if (myname.equals("hosting:personal")) {
							if (! License.product(myvalue).equals("")) {
								hosting.setPersonalLicense(License.generate(client_address, License.product(myvalue)));
							}
						} else if (myname.equals("hosting:professional")) {
							if (! License.product(myvalue).equals("")) {
								hosting.setProfessionalLicense(License.generate(client_address, License.product(myvalue)));
							}
						} else if (myname.equals("hosting:enterprise")) {
							if (! License.product(myvalue).equals("")) {
								hosting.setEnterpriseLicense(License.generate(client_address, License.product(myvalue)));
							}
						} else if (myname.equals("hosting:hosting")) {
							if (! License.product(myvalue).equals("")) {
								hosting.setHostingLicense(License.generate(client_address, License.product(myvalue)));
							}
						} else if (myname.equals("hosting:community")) {
							if (! License.product(myvalue).equals("")) {
								hosting.setCommunityLicense(License.generate(client_address, License.product(myvalue)));
							}
						} else if (myname.equals("hosting:databases")) {
							if (! License.product(myvalue).equals("")) {
								hosting.setDatabasesLicense(License.generate(client_address, License.product(myvalue)));
							}
						} else if (myname.equals("hosting:ecommerce")) {
							if (! License.product(myvalue).equals("")) {
								hosting.setEcommerceLicense(License.generate(client_address, License.product(myvalue)));
							}
						} else if (myname.equals("hosting:statistics")) {
							if (! License.product(myvalue).equals("")) {
								hosting.setStatisticsLicense(License.generate(client_address, License.product(myvalue)));
							}
						}
					}
				}
				hosting.setScheduledLast("");
				Date mynow = new Date();
				if (hosting.getScheduledPublish().equals("")) {
					// starting now for remaining fixed given period
					hosting.setScheduledPublish(Common.strftime("%Y-%m-%d %H:%M:%S", mynow));
				} else if (hosting.getScheduledPublish().compareTo(Common.strftime("%Y-%m-%d %H:%M:%S", mynow)) >= 0) {
					// future fixed given period
				} else if (hosting.getScheduledUnpublish().compareTo(Common.strftime("%Y-%m-%d %H:%M:%S", mynow)) >= 0) {
					// starting now for remaining fixed given period
					hosting.setScheduledPublish(Common.strftime("%Y-%m-%d %H:%M:%S", mynow));
				} else {
					// starting now for duration of given period
					if (! hosting.getScheduledNotify().equals("")) {
						hosting.setScheduledNotify(Common.strftime("%Y-%m-%d %H:%M:%S", new Date(mynow.getTime() + (Common.strtotime(hosting.getScheduledNotify()).getTime() - Common.strtotime(hosting.getScheduledPublish()).getTime()))));
					}
					if (! hosting.getScheduledUnpublish().equals("")) {
						hosting.setScheduledUnpublish(Common.strftime("%Y-%m-%d %H:%M:%S", new Date(mynow.getTime() + (Common.strtotime(hosting.getScheduledUnpublish()).getTime() - Common.strtotime(hosting.getScheduledPublish()).getTime()))));
					}
					hosting.setScheduledPublish(Common.strftime("%Y-%m-%d %H:%M:%S", mynow));
				}

				HostingAPI hostingAPI = new HostingAPI(text, server, mysession, myrequest, inifile, db, original_database, myconfig);
				String error = hostingAPI.hosting_pre_create(client_address, client_database, client_URLrootpath, client_superadmin, client_superadmin_password, client_superadmin_email);
				if (error.equals("")) {
					hosting.create(db);
					hosting.schedule(myconfig, db);
					UCpublishScheduled publishScheduled = new UCpublishScheduled(text);
					publishScheduled.doScheduled(server, mysession, myrequest, myresponse, myconfig, db);

					DB client_db = new DB(text);
					client_db.connect(DB.DSN(client_database), client_database);
					if (client_db.isError()) {
						client_db = null;
						error = "Unable to connect to client database.";
					}

					Configuration client_config = new Configuration();

					if (client_db != null) {
						client_config.set(client_db, "superadmin", hosting.getSuperadmin());
						client_config.set(client_db, "superadmin_password", hosting.getSuperadminPassword());
						client_config.set(client_db, "superadmin_email", hosting.getSuperadminEmail());
						client_config.set(client_db, "contact_form_recipient", hosting.getSuperadminEmail());
						client_config.set(client_db, "order_form_recipient", hosting.getSuperadminEmail());
						client_config.set(client_db, "personal_license", hosting.getPersonalLicense());
						client_config.set(client_db, "professional_license", hosting.getProfessionalLicense());
						client_config.set(client_db, "enterprise_license", hosting.getEnterpriseLicense());
						client_config.set(client_db, "hosting_license", hosting.getHostingLicense());
						client_config.set(client_db, "community_license", hosting.getCommunityLicense());
						client_config.set(client_db, "databases_license", hosting.getDatabasesLicense());
						client_config.set(client_db, "ecommerce_license", hosting.getEcommerceLicense());
						client_config.set(client_db, "statistics_license", hosting.getStatisticsLicense());
						my_product_options = order.getOrderitem().getProductOptions().split("[\\r\\n]+");
						for (int i=0; i<my_product_options.length; i++) {
							String product_option = my_product_options[i];
							Pattern p = Pattern.compile("<([^<>]+)>([^<>]*)<\\/([^<>]+)>");
							Matcher m = p.matcher(product_option);
							if (m.find()) {
								String myname = m.group(1);
								String myvalue = m.group(2);
								if (myname.startsWith("hosting:config:")) {
									client_config.set(client_db, myname.substring(15), myvalue);
								}
							}
						}
					}

					error = hostingAPI.hosting_post_create(client_address, client_database, client_URLrootpath, client_superadmin, client_superadmin_password, client_superadmin_email);

					doDeliverHostingAlias(hosting, hosting, order, website, server, inifile, mysession, myrequest, myresponse, myconfig, db, original_database);
				}
				mysession.set("hosting_api_id", "");
			}
		}
		return hosting;
	}



	public Hosting doAddOnHosting(Order order, Website website, ServletContext server, String inifile, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String original_database) {
		Hosting hosting = new Hosting(text);
		if (! order.getOrderitem().getProductHosting().equals("")) {
			Hosting hosting_product = new Hosting(text);
			hosting_product.readClientAddress(db, order.getOrderitem().getProductHosting());
			mysession.set("hosting_api_id", order.getOrderitem().getProductHosting());

			String subdomain = "";
			String[] my_product_options = order.getOrderitem().getProductOptions().split("[\\r\\n]+");
			for (int i=0; i<my_product_options.length; i++) {
				String product_option = my_product_options[i];
				Pattern p = Pattern.compile("<([^<>]+)>([^<>]*)<\\/([^<>]+)>");
				Matcher m = p.matcher(product_option);
				if (m.find()) {
					String myname = m.group(1);
					String myvalue = m.group(2);
					if (myname.equals("hosting:subdomain")) {
						subdomain = "" + myvalue;
					}
				}
			}
			if (! subdomain.equals("")) {
				String domain_prefix = myconfig.get(db, "productdelivery_hosting_www");
				String domain_postfix = myconfig.get(db, "productdelivery_hosting_domain");
				String client_address = domain_prefix + subdomain + domain_postfix;
				hosting = new Hosting(text);
				hosting.readClientAddress(db, client_address);
				String client_database = hosting.getClientDatabase();

				String error = "";
				DB client_db = new DB(text);
				client_db.connect(DB.DSN(client_database), client_database);
				if (client_db.isError()) {
					client_db = null;
					error = "Unable to connect to client database.";
				}
				Configuration client_config = new Configuration();

				if ((! hosting.getId().equals("")) && (client_db != null)) {
					// add hosting license(s)
					if (! hosting_product.getId().equals("")) {
						if (! License.product(hosting_product.getPersonalLicense()).equals("")) {
							hosting.setPersonalLicense(License.generate(client_address, License.product(hosting_product.getPersonalLicense())));
						}
						if (! License.product(hosting_product.getProfessionalLicense()).equals("")) {
							hosting.setProfessionalLicense(License.generate(client_address, License.product(hosting_product.getProfessionalLicense())));
						}
						if (! License.product(hosting_product.getEnterpriseLicense()).equals("")) {
							hosting.setEnterpriseLicense(License.generate(client_address, License.product(hosting_product.getEnterpriseLicense())));
						}
						if (! License.product(hosting_product.getHostingLicense()).equals("")) {
							hosting.setHostingLicense(License.generate(client_address, License.product(hosting_product.getHostingLicense())));
						}
						if (! License.product(hosting_product.getEcommerceLicense()).equals("")) {
							hosting.setEcommerceLicense(License.generate(client_address, License.product(hosting_product.getEcommerceLicense())));
						}
						if (! License.product(hosting_product.getCommunityLicense()).equals("")) {
							hosting.setCommunityLicense(License.generate(client_address, License.product(hosting_product.getCommunityLicense())));
						}
						if (! License.product(hosting_product.getDatabasesLicense()).equals("")) {
							hosting.setDatabasesLicense(License.generate(client_address, License.product(hosting_product.getDatabasesLicense())));
						}
						if (! License.product(hosting_product.getStatisticsLicense()).equals("")) {
							hosting.setStatisticsLicense(License.generate(client_address, License.product(hosting_product.getStatisticsLicense())));
						}
					}

					my_product_options = order.getOrderitem().getProductOptions().split("[\\r\\n]+");
					for (int i=0; i<my_product_options.length; i++) {
						String product_option = my_product_options[i];
						Pattern p = Pattern.compile("<([^<>]+)>([^<>]*)<\\/([^<>]+)>");
						Matcher m = p.matcher(product_option);
						if (m.find()) {
							String myname = m.group(1);
							String myvalue = m.group(2);
							if (myname.startsWith("hosting:config:")) {
								client_config.set(client_db, myname.substring(15), myvalue);
							} else if (myname.equals("hosting:group")) {
								hosting.setHostinggroup(myvalue);
							} else if (myname.equals("hosting:type")) {
								hosting.setHostingtype(myvalue);
//							} else if (myname.equals("hosting:password")) {
//								hosting.setSuperadminPassword(myvalue);
//								client_config.set(client_db, "superadmin_password", myvalue);
							} else if (myname.equals("hosting:personal")) {
								if (! License.product(myvalue).equals("")) {
									hosting.setPersonalLicense(License.generate(client_address, License.product(myvalue)));
									client_config.set(client_db, "personal_license", License.generate(client_address, License.product(myvalue)));
								}
							} else if (myname.equals("hosting:professional")) {
								if (! License.product(myvalue).equals("")) {
									hosting.setProfessionalLicense(License.generate(client_address, License.product(myvalue)));
									client_config.set(client_db, "professional_license", License.generate(client_address, License.product(myvalue)));
								}
							} else if (myname.equals("hosting:enterprise")) {
								if (! License.product(myvalue).equals("")) {
									hosting.setEnterpriseLicense(License.generate(client_address, License.product(myvalue)));
									client_config.set(client_db, "enterprise_license", License.generate(client_address, License.product(myvalue)));
								}
							} else if (myname.equals("hosting:hosting")) {
								if (! License.product(myvalue).equals("")) {
									hosting.setHostingLicense(License.generate(client_address, License.product(myvalue)));
									client_config.set(client_db, "hosting_license", License.generate(client_address, License.product(myvalue)));
								}
							} else if (myname.equals("hosting:community")) {
								if (! License.product(myvalue).equals("")) {
									hosting.setCommunityLicense(License.generate(client_address, License.product(myvalue)));
									client_config.set(client_db, "community_license", License.generate(client_address, License.product(myvalue)));
								}
							} else if (myname.equals("hosting:databases")) {
								if (! License.product(myvalue).equals("")) {
									hosting.setDatabasesLicense(License.generate(client_address, License.product(myvalue)));
									client_config.set(client_db, "databases_license", License.generate(client_address, License.product(myvalue)));
								}
							} else if (myname.equals("hosting:ecommerce")) {
								if (! License.product(myvalue).equals("")) {
									hosting.setEcommerceLicense(License.generate(client_address, License.product(myvalue)));
									client_config.set(client_db, "ecommerce_license", License.generate(client_address, License.product(myvalue)));
								}
							} else if (myname.equals("hosting:statistics")) {
								if (! License.product(myvalue).equals("")) {
									hosting.setStatisticsLicense(License.generate(client_address, License.product(myvalue)));
									client_config.set(client_db, "statistics_license", License.generate(client_address, License.product(myvalue)));
								}
							}
						}
					}

					hosting.update(db);

					doDeliverHostingAlias(hosting, hosting_product, order, website, server, inifile, mysession, myrequest, myresponse, myconfig, db, original_database);
				}
			}
			mysession.set("hosting_api_id", "");
		}
		return hosting;
	}



	public void doDeliverHostingAlias(Hosting hosting, Hosting hosting_product, Order order, Website website, ServletContext server, String inifile, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String original_database) {
		// add hosting address alias
		String client_address = "";
		String[] my_product_options = order.getOrderitem().getProductOptions().split("[\\r\\n]+");
		for (int i=0; i<my_product_options.length; i++) {
			String product_option = my_product_options[i];
			Pattern p = Pattern.compile("<([^<>]+)>([^<>]*)<\\/([^<>]+)>");
			Matcher m = p.matcher(product_option);
			if (m.find()) {
				String myname = m.group(1);
				String myvalue = m.group(2);
				if (myname.equals("hosting:domain")) {
					client_address = "" + myvalue;
				}
			}
		}

		// check if valid domain format
		if (! client_address.matches("^[a-z0-9][-a-z0-9.]*\\.[-a-z0-9.]*[a-z][a-z]$")) {
			client_address = "";
		}

		// check if domain is domain prefix/postfix
		String domain_prefix = myconfig.get(db, "productdelivery_hosting_www");
		String domain_postfix = myconfig.get(db, "productdelivery_hosting_domain");
		if (( domain_postfix == null) || (domain_postfix.equals(""))) {
			// ignore
		} else if ((client_address.equals(domain_postfix.substring(1))) || (client_address.equals(domain_prefix + domain_postfix.substring(1)))) {
			// domain is domain prefix/postfix
			client_address = "";
		}

		// check if (part of) domain is already in use for other website
		if (! client_address.equals("")) {
			String[] client_address_parts = client_address.split("[\\.]+");
			String my_client_address = "";
			for (int i=client_address_parts.length-1; i>=0; i--) {
				if (! my_client_address.equals("")) {
					my_client_address = "." + my_client_address;
				}
				my_client_address = client_address_parts[i] + my_client_address;
				Hosting hosting_exists = new Hosting(text);
				hosting_exists.readClientAddress(db, my_client_address);
				if (! hosting_exists.getId().equals("")) {
					if ((! hosting_exists.getClientDatabase().equals(hosting.getClientDatabase())) || (! hosting_exists.getClientURLrootpath().equals(hosting.getClientURLrootpath()))) {
						// (part of) domain is already in use with other website
						client_address = "";
					}
				}
			}
		}

		// clone existing hosting with new client_address
		if (! client_address.equals("")) {
			String client_database = hosting.getClientDatabase();
			String client_URLrootpath = hosting.getClientURLrootpath();
			String client_superadmin = hosting.getSuperadmin();
			String client_superadmin_password = hosting.getSuperadminPassword();
			String client_superadmin_email = hosting.getSuperadminEmail();

			HostingAPI hostingAPI = new HostingAPI(text, server, mysession, myrequest, inifile, db, original_database, myconfig);
			String error = hostingAPI.hosting_pre_create(client_address, client_database, client_URLrootpath, client_superadmin, client_superadmin_password, client_superadmin_email);
			if (error.equals("")) {
				hosting.setClientAddress(client_address);
				hosting.setScheduledLast("");
				hosting.setScheduledPublish(hosting_product.getScheduledPublish());
				hosting.setScheduledNotify(hosting_product.getScheduledNotify());
				hosting.setScheduledUnpublish(hosting_product.getScheduledUnpublish());
				hosting.setScheduledPublishEmail(hosting_product.getScheduledPublishEmail());
				hosting.setScheduledNotifyEmail(hosting_product.getScheduledNotifyEmail());
				hosting.setScheduledUnpublishEmail(hosting_product.getScheduledUnpublishEmail());
				Date mynow = new Date();
				if (hosting.getScheduledPublish().equals("")) {
					// starting now for remaining fixed given period
					hosting.setScheduledPublish(Common.strftime("%Y-%m-%d %H:%M:%S", mynow));
				} else if (hosting.getScheduledPublish().compareTo(Common.strftime("%Y-%m-%d %H:%M:%S", mynow)) >= 0) {
					// future fixed given period
				} else if (hosting.getScheduledUnpublish().compareTo(Common.strftime("%Y-%m-%d %H:%M:%S", mynow)) >= 0) {
					// starting now for remaining fixed given period
					hosting.setScheduledPublish(Common.strftime("%Y-%m-%d %H:%M:%S", mynow));
				} else {
					// starting now for duration of given period
					if (! hosting.getScheduledNotify().equals("")) {
						hosting.setScheduledNotify(Common.strftime("%Y-%m-%d %H:%M:%S", new Date(mynow.getTime() + (Common.strtotime(hosting.getScheduledNotify()).getTime() - Common.strtotime(hosting.getScheduledPublish()).getTime()))));
					}
					if (! hosting.getScheduledUnpublish().equals("")) {
						hosting.setScheduledUnpublish(Common.strftime("%Y-%m-%d %H:%M:%S", new Date(mynow.getTime() + (Common.strtotime(hosting.getScheduledUnpublish()).getTime() - Common.strtotime(hosting.getScheduledPublish()).getTime()))));
					}
					hosting.setScheduledPublish(Common.strftime("%Y-%m-%d %H:%M:%S", mynow));
				}
				hosting.create(db);
				hosting.schedule(myconfig, db);
				UCpublishScheduled publishScheduled = new UCpublishScheduled(text);
				publishScheduled.doScheduled(server, mysession, myrequest, myresponse, myconfig, db);
				error = hostingAPI.hosting_post_create(client_address, client_database, client_URLrootpath, client_superadmin, client_superadmin_password, client_superadmin_email);
			}
		}
	}



	public String doDeliverProgram(Order order, Website website, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		String output = "";
		if (! order.getOrderitem().getProductProgram().equals("")) {
			output = Common.execute("/" + text.display("adminpath") + "/productdelivery/" + order.getOrderitem().getProductProgram() + ".jsp", "order", order, "shopcartitem", order.getOrderitem(), "productdelivery", server, mysession.getSession(), myrequest.getRequest(), myresponse.getResponse());
		}
		return output;
	}



	public String doDeliverEmail(User user, Hosting hosting, String program_output, Order order, Website website, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		String output = "";
		if ((! order.getOrderitem().getProductEmail().equals("")) && ((! order.getDeliveryEmail().equals("")) || (! order.getInvoiceEmail().equals(""))) && (! myconfig.get(db, "order_form_recipient").equals(""))) {
			String save_session_userid = mysession.get("userid");
			String save_session_username = mysession.get("username");
			String save_session_password = mysession.get("password");
			String save_session_name = mysession.get("name");
			String save_session_email = mysession.get("email");
			String save_session_usergroup = mysession.get("usergroup");
			String save_session_usertype = mysession.get("usertype");
			String save_session_usergroups = mysession.get("usergroups");
			String save_session_usertypes = mysession.get("usertypes");
			if (user != null) {
				mysession.set("userid", user.getId());
				mysession.set("username", user.getUsername());
				mysession.set("password", user.getPassword());
				mysession.set("name", user.getName());
				mysession.set("email", user.getEmail());
				mysession.set("usergroup", user.getUsergroup());
				mysession.set("usertype", user.getUsertype());
				mysession.set("usergroups", "");
				mysession.set("usertypes", "");
			}
			String sender = "";
			if ((! myrequest.getParameter("order_form_recipient").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(myrequest.getParameter("order_form_recipient"))>=0)) {
				sender = html.encodeHtmlEntities(myrequest.getParameter("order_form_recipient"));
			} else {
				sender = myconfig.get(db, "order_form_recipient");
			}
			String recipient = "";
			if (! order.getDeliveryEmail().equals("")) {
				recipient = order.getDeliveryEmail();
			} else {
				recipient = order.getInvoiceEmail();
			}
			String bcc = sender;
			Page email = new Page(text);
			email.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, order.getOrderitem().getProductEmail(), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			email.setBody(email.getContent());
			email.parse_output_delivery(order, order.getOrderitem(), user, hosting, program_output, server, mysession, myrequest, myresponse, db, myconfig);
			email.parse_output_extensions(server, myrequest.getRequest(), myresponse.getResponse(), mysession.getSession());
			email.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, order.getOrderitem().getProductEmail(), "content_public", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			String title = email.getTitle();
			String body = email.getBody();
			Email.send_email(text, new HashMap<String,String>(), title, body, "", sender, recipient, "", bcc, "", "", myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
			if (user != null) {
				mysession.set("useride", save_session_userid);
				mysession.set("username", save_session_username);
				mysession.set("password", save_session_password);
				mysession.set("name", save_session_name);
				mysession.set("email", save_session_email);
				mysession.set("usergroup", save_session_usergroup);
				mysession.set("usertype", save_session_usertype);
				mysession.set("usergroups", save_session_usergroups);
				mysession.set("usertypes", save_session_usertypes);
			}
		}
		return output;
	}



	public String doDeliverPage(User user, Hosting hosting, String program_output, Order order, Website website, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		String output = "";
		if (! order.getOrderitem().getProductPage().equals("")) {
			Page productpage = new Page(text);
			productpage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, order.getOrderitem().getProductPage(), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			productpage.setBody(productpage.getContent());
			productpage.parse_output_delivery(order, order.getOrderitem(), user, hosting, program_output, server, mysession, myrequest, myresponse, db, myconfig);
			productpage.parse_output_extensions(server, myrequest.getRequest(), myresponse.getResponse(), mysession.getSession());
			productpage.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, order.getOrderitem().getProductPage(), "content_public", "id", mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			output = productpage.getBody();
		}
		return output;
	}



	public void doGuestbook(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if ((! myrequest.getParameter("subject").equals("")) || (! myrequest.getParameter("title").equals(""))) {
			Guestbook guestbookinput = new Guestbook();
			guestbookinput.getForm(myrequest);

			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			String username = mysession.get("username");
			guestbookinput.setCreated(timestamp);
			guestbookinput.setCreatedBy(username);
			guestbookinput.setUpdated(timestamp);
			guestbookinput.setUpdatedBy(username);
			if (! myconfig.get(db, "guestbook_administrator_approval").equals("yes")) {
				guestbookinput.setPublished(timestamp);
				guestbookinput.setPublishedBy(username);
			}
			guestbookinput.create(db);

			if (! myconfig.get(db, "contact_form_recipient").equals("")) {
				String sender = "";
				if (! myrequest.getParameter("email").equals("")) {
					sender = html.encodeHtmlEntities(myrequest.getParameter("email"));
				} else {
					sender = "guestbook@" + myrequest.getServerName();
				}
				HashMap<String,String> requestForm = Email.getForm(myrequest);
				String subject = "";
				if (requestForm.get("subject") != null) subject += requestForm.get("subject");
				if (requestForm.get("title") != null) subject += requestForm.get("title");
				Email.send_email(text, requestForm, subject, "", "", sender, myconfig.get(db, "contact_form_recipient"), "", "", "", "", myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
			}
		}
	}



	public Page doSearch(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return doSearch(server, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Page doSearch(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Website mywebsite) throws Exception {
		if (mywebsite == null) mywebsite = new Website(text);
		Page mypage;
		setSessionModeFromRequest(mysession, myrequest);
//		setSessionVersionFromRequest(mysession, myrequest);
		setSessionPresentationFromRequest(mysession, myrequest);
		setSessionCurrencyFromRequest(mysession, myrequest, db, myconfig);
		boolean accesspermission = true;

		Databases database = new Databases(text);
		if (! myrequest.getParameter("database").equals("")) {
			database.readTitle(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("database"));
		}
		accesspermission = RequireUser.checkDataUserAccessRestrictions(text, true, database, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) database = new Databases(text);

		if ((mysession.get("mode").equals("preview")) || (mysession.get("mode").equals("admin"))) {
			accesspermission = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
			if ((mysession.get("mode").equals("admin")) && (! myrequest.getParameter("menu").equals(""))) {
				mysession.set("menu", html.encodeHtmlEntities(myrequest.getParameter("menu")));
			}
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
				setSessionCurrencyForVersion(mysession, db, myconfig);
			}
			mysession.set("default_country", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_country"));
			mysession.set("default_state", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_state"));
			mysession.set("default_price", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_price"));
			String website_template = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_template");
			String website_stylesheet = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_stylesheet");
			mypage = new Page(text);
			if (Common.number(myrequest.getParameter("archive"))>0) {
				mypage.archive_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("archive"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else if (! myrequest.getParameter("id").equals("")) {
				mypage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else if (! myrequest.getParameter("database").equals("")) {
				mypage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, database.getSearchresults(), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else if ((mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) && (! mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_searchresults_page").equals(""))) {
				mypage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_searchresults_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else {
				mypage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_searchresults_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			}
		} else {
			if ((mysession.get("version").equals("")) || (mysession.get("version").equals(" "))) {
				mysession.set("version", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_version"));
				setSessionCurrencyForVersion(mysession, db, myconfig);
			}
			mysession.set("default_country", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_country"));
			mysession.set("default_state", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_state"));
			mysession.set("default_price", mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_price"));
			String website_template = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_template");
			String website_stylesheet = mywebsite.get(myrequest, db, myrequest.getServerName(), html.encodeHtmlEntities(myrequest.getHeader("Accept-Language")), "default_stylesheet");
			mypage = new Page(text);
			if (! myrequest.getParameter("id").equals("")) {
				mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else if (! myrequest.getParameter("database").equals("")) {
				mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, database.getSearchresults(), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else if ((mywebsite.exists(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"))) && (! mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_searchresults_page").equals(""))) {
				mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mywebsite.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_searchresults_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			} else {
				mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_searchresults_page"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("template"), website_template, myconfig.get(db, "default_template"), mysession.get("stylesheet"), website_stylesheet, myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
			}
		}

		if (mysession.get("currency").equals("")) {
			setSessionCurrencyForVersion(mysession, db, myconfig);
		}

		setPageStyleSheet(mypage, mysession, myconfig, db);
		accesspermission = RequireUser.checkUserAccessRestrictions(text, true, mypage, mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) mypage = new Page(text);

		return mypage;
	}



	public void doLogin(Cms cms, ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String database) throws Exception {
		Login.login(text, cms, "/login.jsp", "/", server, mysession, myrequest, myresponse, myconfig, db, myconfig.get(db, "require_ssl_user"), database);
	}



	public void doLogout(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		Login.logout(mysession, myrequest, myresponse, db, myconfig.get(db, "require_ssl_logout_user"));
	}



	public void doEmail(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		if ((! myconfig.get(db, "captcha").equals("")) && (myconfig.get(db, "captcha_contact").equals("yes")) && ((myconfig.get(db, "captcha_user").equals("yes")) || (mysession.get("username").equals("")))) {
			mysession.set("captcha_error", "");
			CAPTCHA mycaptcha = new CAPTCHA(text);
			if (! mycaptcha.valid(mysession, server, myrequest, myconfig, db)) {
//				error += "<br>" + mycaptcha.error;
				mysession.set("captcha_error", mycaptcha.error);
				mysession.set("POST", Common.serialize(myrequest));
				if (! mysession.get("captcha_url").equals("")) {
					myresponse.sendRedirect(Common.crlf_clean(mysession.get("captcha_url")));
				}
				return;
			}
		}
		if ((myconfig.get(db, "authorize_contact").equals("yes")) && (! Common.authorized(mysession, myrequest, "contact"))) {
			return;
		}
		String sender = "";
		if (! myrequest.getParameter("email").equals("")) {
			sender = html.encodeHtmlEntities(myrequest.getParameter("email"));
		} else if ((! myrequest.getParameter("from").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(myrequest.getParameter("from"))>=0)) {
			sender = html.encodeHtmlEntities(myrequest.getParameter("from"));
		} else {
			sender = "contactform@" + myrequest.getServerName();
		}
		String recipient = "";
		if ((! myrequest.getParameter("to").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(myrequest.getParameter("to"))>=0)) {
			recipient = html.encodeHtmlEntities(myrequest.getParameter("to"));
		} else {
			recipient = myconfig.get(db, "contact_form_recipient");
		}
		String cc = "";
		if ((! myrequest.getParameter("cc").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(myrequest.getParameter("cc"))>=0)) {
			cc = html.encodeHtmlEntities(myrequest.getParameter("cc"));
		}
		String bcc = "";
		if ((! myrequest.getParameter("bcc").equals("")) && (myconfig.get(db, "contact_form_recipients").indexOf(myrequest.getParameter("bcc"))>=0)) {
			bcc = html.encodeHtmlEntities(myrequest.getParameter("bcc"));
		}
		HashMap<String,String> requestForm = Email.getForm(myrequest);
		String subject = "";
		if (requestForm.get("subject") != null) subject += requestForm.get("subject");
		if (requestForm.get("title") != null) subject += requestForm.get("title");
		Email.send_email(text, requestForm, subject, "", "", sender, recipient, cc, bcc, "", "", myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);

		if ((myrequest.getParameter("redirect").startsWith("http:")) || (myrequest.getParameter("redirect").startsWith("https:"))) {
			if ((! myconfig.get(db, "redirect_urls").trim().equals("")) && (! Common.startsWithAnyOf(Common.crlf_clean(myrequest.getParameter("redirect")), myconfig.get(db, "redirect_urls")))) {
				// ignore
			} else {
				myresponse.sendRedirect(Common.crlf_clean(myrequest.getParameter("redirect")));
			}
		} else if (! myrequest.getParameter("redirect").equals("")) {
			myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + Common.crlf_clean(myrequest.getParameter("redirect")));
		} else {
			myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/");
		}
	}



	private void setSessionModeFromRequest(Session mysession, Request myrequest) {
		if ((myrequest.parameterExists("preview_scheduled")) && (mysession.get("mode").equals("admin"))) {
			mysession.set("preview_scheduled", myrequest.getParameter("preview_scheduled").replaceAll("[^-:0-9a-zA-Z ]", "").trim());
		} else if ((! myrequest.parameterExists("mode")) && (mysession.get("mode").equals("admin"))) {
			// default
		} else if ((myrequest.parameterExists("mode")) && (! myrequest.getParameter("mode").equals("")) && (myrequest.getQueryString().indexOf("mode=") != -1)) {
			mysession.set("mode", html.encodeHtmlEntities(myrequest.getParameter("mode")));
			if (mysession.get("mode").equals("admin")) {
				mysession.set("preview_scheduled", myrequest.getParameter("preview_scheduled").replaceAll("[^-:0-9a-zA-Z ]", "").trim());
			} else if (mysession.get("mode").equals("preview")) {
				mysession.set("preview_scheduled", myrequest.getParameter("scheduled_publish").replaceAll("[^-:0-9a-zA-Z ]", "").trim());
			}
		}
//QQQ		if ((mysession.get("mode").equals("preview")) && (myrequest.parameterExists("preview_website"))) {
//QQQ			mysession.set("preview_website", myrequest.getParameter("preview_website"));
//QQQ		}
//QQQ		if (! mysession.get("preview_website").equals("")) {
//QQQ			mysession.set("mode", "preview");
//QQQ			mysession.set("preview_scheduled", myrequest.getParameter("scheduled_publish"));
//QQQ		}
	}



	private void setSessionVersionFromRequest(Session mysession, Request myrequest) {
		if ((myrequest.parameterExists("mode")) && (myrequest.getParameter("mode").equals("preview")) && (myrequest.getQueryString().indexOf("mode=") != -1)) {
			mysession.remove("version");
			mysession.remove("device");
			mysession.remove("usersegments");
			mysession.remove("usertests");
			mysession.remove("useragent");
		}
		if ((myrequest.parameterExists("version")) && (myrequest.getQueryString().indexOf("version=") != -1)) {
			mysession.set("version", html.encodeHtmlEntities(myrequest.getParameter("version")));
		}
		if ((myrequest.parameterExists("device")) && (myrequest.getQueryString().indexOf("device=") != -1)) {
			if (myrequest.getParameter("device").equals("?")) {
				Device mydevice = new Device(myrequest.getHeader("User-Agent"), mysession, true);
			} else {
				mysession.set("device", html.encodeHtmlEntities(myrequest.getParameter("device")));
			}
		}
		if ((myrequest.parameterExists("useragent")) && (myrequest.getQueryString().indexOf("useragent=") != -1)) {
			if (mysession.get("mode").equals("admin")) {
				mysession.set("useragent", myrequest.getParameter("useragent"));
			} else if (mysession.get("mode").equals("preview")) {
				mysession.set("useragent", myrequest.getParameter("useragent"));
			}
		}
		if (! mysession.get("useragent").equals("")) {
			Device mydevice = new Device(mysession.get("useragent"), mysession, true);
		}
	}



	private void setSessionPresentationFromRequest(Session mysession, Request myrequest) {
		if ((myrequest.parameterExists("mode")) && (myrequest.getParameter("mode").equals("preview")) && (myrequest.getQueryString().indexOf("mode=") != -1)) {
			mysession.set("stylesheet", "");
			mysession.set("template", "");
		}
		if ((myrequest.parameterExists("stylesheet")) && (myrequest.getQueryString().indexOf("stylesheet=") != -1)) {
			mysession.set("stylesheet", html.encodeHtmlEntities(myrequest.getParameter("stylesheet")));
		}
		if ((myrequest.parameterExists("template")) && (myrequest.getQueryString().indexOf("template=") != -1)) {
			mysession.set("template", html.encodeHtmlEntities(myrequest.getParameter("template")));
		}
	}



	private void setSessionCurrencyFromRequest(Session mysession, Request myrequest, DB db, Configuration myconfig) {
		if ((myrequest.parameterExists("mode")) && (myrequest.getParameter("mode").equals("preview")) && (myrequest.getQueryString().indexOf("mode=") != -1)) {
			mysession.set("currency", myconfig.get(db, "default_currency"));
		}
		if ((myrequest.parameterExists("version")) && (myrequest.getQueryString().indexOf("version=") != -1)) {
			mysession.set("version", html.encodeHtmlEntities(myrequest.getParameter("version")));
			setSessionCurrencyForVersion(mysession, db, myconfig);
		}
	}



	private void setSessionCurrencyForVersion(Session mysession, DB db, Configuration myconfig) {
		if (! mysession.get("version").equals("")) {
			Version version = new Version();
			version.read(db, mysession.get("version"));
			if (! version.getCurrencies().equals("")) {
				mysession.set("currency", version.getCurrencies());
			} else {
				mysession.set("currency", myconfig.get(db, "default_currency"));
			}
		} else {
			mysession.set("currency", myconfig.get(db, "default_currency"));
		}
	}



	private void setSessionUserExperience(Content content, Session mysession, Response myresponse, DB db, Configuration myconfig) {
		if ((! myconfig.get(db, "experience_license").equals("")) && (! myconfig.get(db, "use_experience").equals("no"))) {
			if (! myconfig.get(db, "use_usersegments").equals("no")) {
				Segment myusersegment = new Segment(mysession.get("usersegments"));
				HashMap<String,String> mysegmentations = content.getSegmentations();
				Iterator mysegments = mysegmentations.keySet().iterator();
				while (mysegments.hasNext()) {
					String mysegment = "" + mysegments.next();
					String myweight = "" + mysegmentations.get(mysegment);
					myusersegment.setWeight(mysegment, myweight);
				}
				myusersegment.setSegments(mysession);
				if (! mysession.get("usersegments").equals("")) {
					myresponse.setCookie("usersegments", mysession.get("usersegments"), 2*365*24*60*60);
				}
			}
			if (! myconfig.get(db, "use_usertests").equals("no")) {
				if (! mysession.get("usertests").equals("")) {
					myresponse.setCookie("usertests", mysession.get("usertests"), 2*365*24*60*60);
				}
			}
		}
	}



	private void setPageStyleSheet(Page mypage, Session mysession, Configuration myconfig, DB db) {
		if (! mysession.get("stylesheet").equals("")) {
			mypage.setStyleSheet(mysession.get("stylesheet"));
		} else if (mypage.getStyleSheet().equals("")) {
			mypage.setStyleSheet(myconfig.get(db, "default_stylesheet"));
		}
	}



	private void clearSessionPrintModeFromRequest(Session mysession, Request myrequest) {
		if ((myrequest.getParameter("mode").equals("print")) && (myrequest.getQueryString().indexOf("mode=") != -1)) {
			mysession.set("mode", "");
			mysession.set("stylesheet", "");
			mysession.set("template", "");
		}
	}



	private void handleFormDataValidation(Request myrequest, Response myresponse) {
		String redirect = "";
		String redirect_invalid = myrequest.getParameter("redirect_invalid");
		if (! redirect_invalid.equals("")) {
			redirect_invalid = redirect_invalid.replaceAll("##", "###").replaceAll("@@", "@@@");
			if (redirect_invalid.indexOf("###")>=0) {
				Pattern p = Pattern.compile("###([_ a-zA-Z0-9\\w]+)###");
				Matcher m = p.matcher(redirect_invalid);
				while (m.find()) {
					String elementname = "" + m.group(1);
					String elementvalue = "";
					if (! myrequest.getParameter(elementname).equals("")) {
						elementvalue = URLEncoder.encode(myrequest.getParameter(elementname));
					} else if (! myrequest.getParameter(elementname.replaceAll(" ", "_")).equals("")) {
						elementvalue = URLEncoder.encode(myrequest.getParameter(elementname.replaceAll(" ", "_")));
					}
					redirect_invalid = redirect_invalid.replaceAll("###" + elementname.replaceAll("###", "") + "###", elementvalue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					m.reset(redirect_invalid);
				}
			}
		}

		String[] invalid = Common.validateFormData(myrequest);
		if ((invalid.length > 0) && (! redirect_invalid.equals(""))) {
			if (redirect_invalid.indexOf("?")>=0) {
				redirect = redirect_invalid + "&invalid=" + Common.join(",", invalid);
			} else {
				redirect = redirect_invalid + "?invalid=" + Common.join(",", invalid);
			}
		}

		if ((redirect.startsWith("http://")) || (redirect.startsWith("https://"))) {
			myresponse.sendRedirect(Common.crlf_clean(redirect));
		} else if (! redirect.equals("")) {
			myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + Common.crlf_clean(redirect));
		}
	}



}
