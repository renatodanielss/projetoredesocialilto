package HardCore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import com.iliketo.dao.MemberDAO;
import com.iliketo.model.Member;

public class Login {



//	static public void login(Cms cms, String defaultLoginURL, String defaultRedirectURL, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String require_ssl, String database) throws Exception {
//		login(new Text(), cms, defaultLoginURL, defaultRedirectURL, null, mysession, myrequest, myresponse, myconfig, db, require_ssl, database);
//	}
//	static public void login(Cms cms, String defaultLoginURL, String defaultRedirectURL, ServletContext servletcontext, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String require_ssl, String database) throws Exception {
//		login(new Text(), cms, defaultLoginURL, defaultRedirectURL, servletcontext, mysession, myrequest, myresponse, myconfig, db, require_ssl, database);
//	}
//	static public void login(Text text, Cms cms, String defaultLoginURL, String defaultRedirectURL, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String require_ssl, String database) throws Exception {
//		login(new Text(), cms, defaultLoginURL, defaultRedirectURL, null, mysession, myrequest, myresponse, myconfig, db, require_ssl, database);
//	}
	static public void login(Text text, Cms cms, String defaultLoginURL, String defaultRedirectURL, ServletContext servletcontext, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String require_ssl, String database) throws Exception {
		Cms.CMSAudit("action=login username=" + myrequest.getParameter("username"));
		String save_session_database = database;
		String save_session_administrator = mysession.get("administrator");
		String save_session_userid = mysession.get("userid");
		String save_session_username = mysession.get("username");
		String save_session_password = mysession.get("password");
		String save_session_name = mysession.get("name");
		String save_session_email = mysession.get("email");
		String save_session_usertype = mysession.get("usertype");
		String save_session_usergroup = mysession.get("usergroup");

		if (! myrequest.getParameter("username").equals("")) {
			mysession.regenerate(myrequest.getRequest());
		}

		mysession.set("database", "");
		mysession.set("administrator", "");
		mysession.set("userid", "");
		mysession.set("username", "");
		mysession.set("password", "");
		mysession.set("name", "");
		mysession.set("email", "");
		mysession.set("usertype", "");
		mysession.set("usergroup", "");

		mysession.set("user_organisation", "");
		mysession.set("user_created", "");
		mysession.set("user_updated", "");
		mysession.set("user_scheduled_publish", "");
		mysession.set("user_scheduled_notify", "");
		mysession.set("user_scheduled_unpublish", "");
		mysession.set("user_card_type", "");
		mysession.set("user_card_number", "");
		mysession.set("user_card_issuedmonth", "");
		mysession.set("user_card_issuedyear", "");
		mysession.set("user_card_expirymonth", "");
		mysession.set("user_card_expiryyear", "");
		mysession.set("user_card_name", "");
		mysession.set("user_card_cvc", "");
		mysession.set("user_card_issue", "");
		mysession.set("user_card_postalcode", "");
		mysession.set("user_delivery_name", "");
		mysession.set("user_delivery_organisation", "");
		mysession.set("user_delivery_address", "");
		mysession.set("user_delivery_postalcode", "");
		mysession.set("user_delivery_city", "");
		mysession.set("user_delivery_state", "");
		mysession.set("user_delivery_country", "");
		mysession.set("user_delivery_phone", "");
		mysession.set("user_delivery_fax", "");
		mysession.set("user_delivery_email", "");
		mysession.set("user_delivery_website", "");
		mysession.set("user_invoice_name", "");
		mysession.set("user_invoice_organisation", "");
		mysession.set("user_invoice_address", "");
		mysession.set("user_invoice_postalcode", "");
		mysession.set("user_invoice_city", "");
		mysession.set("user_invoice_state", "");
		mysession.set("user_invoice_country", "");
		mysession.set("user_invoice_phone", "");
		mysession.set("user_invoice_fax", "");
		mysession.set("user_invoice_email", "");
		mysession.set("user_invoice_website", "");
		mysession.set("user_notes", "");
		mysession.set("user_userinfo", "");

		String url = "";
		if (! myrequest.getParameter("url").equals("")) {
//			url = html.encodeHtmlEntities(myrequest.getParameter("url"));
			url = myrequest.getParameter("url");
		} else {
			url = defaultRedirectURL;
		}

		String username = html.encodeHtmlEntities(myrequest.getParameter("username").trim());
		String password = html.encodeHtmlEntities(myrequest.getParameter("password").trim());

		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		User myuser = null;
		if (((username.equals(myconfig.get(db, "superadmin"))) || (myconfig.get(db, "superadmin").equals(""))) && ((password.equals(myconfig.get(db, "superadmin_password"))) || (myconfig.get(db, "superadmin_password").equals("")))) {
			mysession.set("login_page", "");
			mysession.set("database", database);
			mysession.set("administrator", "administrator");
			mysession.set("userid", "0");
			mysession.set("username", myconfig.get(db, "superadmin"));
			mysession.set("password", myconfig.get(db, "superadmin_password"));
			mysession.set("name", "");
			mysession.set("email", myconfig.get(db, "superadmin_email"));
			mysession.set("usertype", "");
			mysession.set("usergroup", "");
			myconfig.set(db, "superadmin_failed", "0");
			if (! myconfig.get(db, "login_notification_superadmin").equals("")) {
				doEmail(text, myconfig.get(db, "superadmin_email"), myconfig.get(db, "login_notification_superadmin"), text.display("config.website.security.login.notification.superadmin.subject"), username, servletcontext, mysession, myrequest, myresponse, myconfig, db);
			}
			if (mysession.get("log").equals("login")) {
				if (! myrequest.getParameter("id").equals("")) {
					if (cms != null) cms.CMSLog(myrequest.getParameter("id"), "login", "");
				} else {
					if (cms != null) cms.CMSLog(myconfig.get(db, "default_page"), "login", "");
				}
				mysession.set("log", "");
			}
			Cms.CMSAudit("action=login.ok username=" + username + " userid=" + mysession.get("userid") + " userclass=" + mysession.get("administrator"));
			if (url.equals("-")) {
				// ignore
			} else if (require_ssl.equals("yes")) {
				if (db != null) db.close();
//				myresponse.sendRedirect("https://" + myrequest.getServerName() + Common.crlf_clean(url));
				myresponse.sendRedirect(myresponse.encodeRedirectURL("https://" + myrequest.getServerName() + Common.crlf_clean(url)));
			} else {
				if (db != null) db.close();
//				myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + Common.crlf_clean(url));
				myresponse.sendRedirect(myresponse.encodeRedirectURL(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + Common.crlf_clean(url)));
			}
		} else if (username.equals(myconfig.get(db, "superadmin"))) {
			if (! myconfig.get(db, "login_notification_superadmin_failure").equals("")) {
				doEmail(text, myconfig.get(db, "superadmin_email"), myconfig.get(db, "login_notification_superadmin_failure"), text.display("config.website.security.login.notification.superadmin.failure.subject"), username, servletcontext, mysession, myrequest, myresponse, myconfig, db);
			}
			if (! myconfig.get(db, "login_notification_superadmin_lock").equals("")) {
				myconfig.set(db, "superadmin_failed", "" + (Common.intnumber(myconfig.get(db, "superadmin_failed"))+1));
				if (Common.intnumber(myconfig.get(db, "superadmin_failed")) >= Common.intnumber(myconfig.get(db, "login_notification_superadmin_lock"))) {
					String mypassword = "";
					for (int j=0; j<32; j++) {
						mypassword = "" + mypassword + (char)('a' + Integer.parseInt(Common.numberformat("" + Math.random()*25, 0)));
					}
					myconfig.set(db, "superadmin_password", mypassword);
					if (! myconfig.get(db, "login_notification_superadmin_redirect").equals("")) {
						if (db != null) db.close();
						myresponse.sendRedirect(myresponse.encodeRedirectURL(myconfig.get(db, "login_notification_superadmin_redirect")));
					}
				}
			}
			myuser = null;
		} else if ((! username.equals("")) && (! password.equals(""))) {
			myuser = new User(text);
			if (! myuser.readByUsernameAndPassword(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username, password)) {
				if ((! myconfig.get(db, "login_notification_admin_failure").equals("")) && (myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username)) && (myuser.getUserclass().equals("administrator"))) {
					doEmail(text, myconfig.get(db, "superadmin_email"), myconfig.get(db, "login_notification_admin_failure"), text.display("config.website.security.login.notification.admin.failure.subject"), username, servletcontext, mysession, myrequest, myresponse, myconfig, db);
				} else if ((! myconfig.get(db, "login_notification_user_failure").equals("")) && (myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username))) {
					doEmail(text, myconfig.get(db, "superadmin_email"), myconfig.get(db, "login_notification_user_failure"), text.display("config.website.security.login.notification.user.failure.subject"), username, servletcontext, mysession, myrequest, myresponse, myconfig, db);
				}
				if ((! myconfig.get(db, "login_notification_admin_lock").equals("")) && (myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username)) && (myuser.getUserclass().equals("administrator"))) {
					if (myuser.loginFailure(db, myconfig.get(db, "login_notification_admin_lock")) && (! myconfig.get(db, "login_notification_admin_redirect").equals(""))) {
						if (db != null) db.close();
						myresponse.sendRedirect(myresponse.encodeRedirectURL(myconfig.get(db, "login_notification_admin_redirect")));
					}
				} else if ((! myconfig.get(db, "login_notification_user_lock").equals("")) && (myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username))) {
					if (myuser.loginFailure(db, myconfig.get(db, "login_notification_user_lock")) && (! myconfig.get(db, "login_notification_user_redirect").equals(""))) {
						if (db != null) db.close();
						myresponse.sendRedirect(myresponse.encodeRedirectURL(myconfig.get(db, "login_notification_user_redirect")));
					}
				}
				myuser = null;
			} else if (((! myuser.getScheduledPublish().equals("")) && (myuser.getScheduledPublish().compareTo(now) > 0)) || ((! myuser.getScheduledUnpublish().equals("")) && (myuser.getScheduledUnpublish().compareTo(now) < 0))) {
				if ((! myconfig.get(db, "login_notification_admin_failure").equals("")) && (myuser.getUserclass().equals("administrator"))) {
					doEmail(text, myconfig.get(db, "superadmin_email"), myconfig.get(db, "login_notification_admin_failure"), text.display("config.website.security.login.notification.admin.failure.subject"), username, servletcontext, mysession, myrequest, myresponse, myconfig, db);
				} else if (! myconfig.get(db, "login_notification_user_failure").equals("")) {
					doEmail(text, myconfig.get(db, "superadmin_email"), myconfig.get(db, "login_notification_user_failure"), text.display("config.website.security.login.notification.user.failure.subject"), username, servletcontext, mysession, myrequest, myresponse, myconfig, db);
				}
				if ((! myconfig.get(db, "login_notification_admin_lock").equals("")) && (myuser.getUserclass().equals("administrator"))) {
					if (myuser.loginFailure(db, myconfig.get(db, "login_notification_admin_lock")) && (! myconfig.get(db, "login_notification_admin_redirect").equals(""))) {
						if (db != null) db.close();
						myresponse.sendRedirect(myresponse.encodeRedirectURL(myconfig.get(db, "login_notification_admin_redirect")));
					}
				} else if (! myconfig.get(db, "login_notification_user_lock").equals("")) {
					if (myuser.loginFailure(db, myconfig.get(db, "login_notification_user_lock")) && (! myconfig.get(db, "login_notification_user_redirect").equals(""))) {
						if (db != null) db.close();
						myresponse.sendRedirect(myresponse.encodeRedirectURL(myconfig.get(db, "login_notification_user_redirect")));
					}
				}
				myuser = null;
			} else {
				myuser.loginSuccess(db);
				if ((! myconfig.get(db, "login_notification_admin").equals("")) && (myuser.getUserclass().equals("administrator"))) {
					doEmail(text, myconfig.get(db, "superadmin_email"), myconfig.get(db, "login_notification_admin"), text.display("config.website.security.login.notification.admin.subject"), username, servletcontext, mysession, myrequest, myresponse, myconfig, db);
				} else if (! myconfig.get(db, "login_notification_user").equals("")) {
					doEmail(text, myconfig.get(db, "superadmin_email"), myconfig.get(db, "login_notification_user"), text.display("config.website.security.login.notification.user.subject"), username, servletcontext, mysession, myrequest, myresponse, myconfig, db);
				}
			}
		} else if (! username.equals("")) {
			myuser = new User(text);
			if ((! myconfig.get(db, "login_notification_admin_failure").equals("")) && (myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username)) && (myuser.getUserclass().equals("administrator"))) {
				doEmail(text, myconfig.get(db, "superadmin_email"), myconfig.get(db, "login_notification_admin_failure"), text.display("config.website.security.login.notification.admin.failure.subject"), username, servletcontext, mysession, myrequest, myresponse, myconfig, db);
			} else if ((! myconfig.get(db, "login_notification_user_failure").equals("")) && (myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username))) {
				doEmail(text, myconfig.get(db, "superadmin_email"), myconfig.get(db, "login_notification_user_failure"), text.display("config.website.security.login.notification.user.failure.subject"), username, servletcontext, mysession, myrequest, myresponse, myconfig, db);
			}
			if ((! myconfig.get(db, "login_notification_admin_lock").equals("")) && (myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username)) && (myuser.getUserclass().equals("administrator"))) {
				if (myuser.loginFailure(db, myconfig.get(db, "login_notification_admin_lock")) && (! myconfig.get(db, "login_notification_admin_redirect").equals(""))) {
					if (db != null) db.close();
					myresponse.sendRedirect(myresponse.encodeRedirectURL(myconfig.get(db, "login_notification_admin_redirect")));
				}
			} else if ((! myconfig.get(db, "login_notification_user_lock").equals("")) && (myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username))) {
				if (myuser.loginFailure(db, myconfig.get(db, "login_notification_user_lock")) && (! myconfig.get(db, "login_notification_user_redirect").equals(""))) {
					if (db != null) db.close();
					myresponse.sendRedirect(myresponse.encodeRedirectURL(myconfig.get(db, "login_notification_user_redirect")));
				}
			}
			myuser = null;
		} else if ((myrequest.getRemoteUser() != null) && (! myrequest.getRemoteUser().equals(""))) {
			myuser = new User(text);
			if (! myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getRemoteUser())) {
				if ((! myconfig.get(db, "login_notification_admin_failure").equals("")) && (myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getRemoteUser())) && (myuser.getUserclass().equals("administrator"))) {
					doEmail(text, myconfig.get(db, "superadmin_email"), myconfig.get(db, "login_notification_admin_failure"), text.display("config.website.security.login.notification.admin.failure.subject"), myrequest.getRemoteUser(), servletcontext, mysession, myrequest, myresponse, myconfig, db);
				} else if ((! myconfig.get(db, "login_notification_user_failure").equals("")) && (myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getRemoteUser()))) {
					doEmail(text, myconfig.get(db, "superadmin_email"), myconfig.get(db, "login_notification_user_failure"), text.display("config.website.security.login.notification.user.failure.subject"), myrequest.getRemoteUser(), servletcontext, mysession, myrequest, myresponse, myconfig, db);
				}
				if ((! myconfig.get(db, "login_notification_admin_lock").equals("")) && (myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getRemoteUser())) && (myuser.getUserclass().equals("administrator"))) {
					if (myuser.loginFailure(db, myconfig.get(db, "login_notification_admin_lock")) && (! myconfig.get(db, "login_notification_admin_redirect").equals(""))) {
						if (db != null) db.close();
						myresponse.sendRedirect(myresponse.encodeRedirectURL(myconfig.get(db, "login_notification_admin_redirect")));
					}
				} else if ((! myconfig.get(db, "login_notification_user_lock").equals("")) && (myuser.readByUsername(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getRemoteUser()))) {
					if (myuser.loginFailure(db, myconfig.get(db, "login_notification_user_lock")) && (! myconfig.get(db, "login_notification_user_redirect").equals(""))) {
						if (db != null) db.close();
						myresponse.sendRedirect(myresponse.encodeRedirectURL(myconfig.get(db, "login_notification_user_redirect")));
					}
				}
				myuser = null;
			} else if (((! myuser.getScheduledPublish().equals("")) && (myuser.getScheduledPublish().compareTo(now) > 0)) || ((! myuser.getScheduledUnpublish().equals("")) && (myuser.getScheduledUnpublish().compareTo(now) < 0))) {
				if ((! myconfig.get(db, "login_notification_admin_failure").equals("")) && (myuser.getUserclass().equals("administrator"))) {
					doEmail(text, myconfig.get(db, "superadmin_email"), myconfig.get(db, "login_notification_admin_failure"), text.display("config.website.security.login.notification.admin.failure.subject"), myrequest.getRemoteUser(), servletcontext, mysession, myrequest, myresponse, myconfig, db);
				} else if (! myconfig.get(db, "login_notification_admin_failure").equals("")) {
					doEmail(text, myconfig.get(db, "superadmin_email"), myconfig.get(db, "login_notification_user_failure"), text.display("config.website.security.login.notification.user.failure.subject"), myrequest.getRemoteUser(), servletcontext, mysession, myrequest, myresponse, myconfig, db);
				}
				if ((! myconfig.get(db, "login_notification_admin_lock").equals("")) && (myuser.getUserclass().equals("administrator"))) {
					if (myuser.loginFailure(db, myconfig.get(db, "login_notification_admin_lock")) && (! myconfig.get(db, "login_notification_admin_redirect").equals(""))) {
						if (db != null) db.close();
						myresponse.sendRedirect(myresponse.encodeRedirectURL(myconfig.get(db, "login_notification_admin_redirect")));
					}
				} else if (! myconfig.get(db, "login_notification_user_lock").equals("")) {
					if (myuser.loginFailure(db, myconfig.get(db, "login_notification_user_lock")) && (! myconfig.get(db, "login_notification_user_redirect").equals(""))) {
						if (db != null) db.close();
						myresponse.sendRedirect(myresponse.encodeRedirectURL(myconfig.get(db, "login_notification_user_redirect")));
					}
				}
				myuser = null;
			} else {
				myuser.loginSuccess(db);
				if ((! myconfig.get(db, "login_notification_admin").equals("")) && (myuser.getUserclass().equals("administrator"))) {
					doEmail(text, myconfig.get(db, "superadmin_email"), myconfig.get(db, "login_notification_admin"), text.display("config.website.security.login.notification.admin.subject"), username, servletcontext, mysession, myrequest, myresponse, myconfig, db);
				} else if (! myconfig.get(db, "login_notification_user").equals("")) {
					doEmail(text, myconfig.get(db, "superadmin_email"), myconfig.get(db, "login_notification_user"), text.display("config.website.security.login.notification.user.subject"), username, servletcontext, mysession, myrequest, myresponse, myconfig, db);
				}
			}
		}

		if (myuser != null) {
			mysession.set("login_page", "");
			mysession.set("database", database);
			userSession(myuser, mysession, db);
			if ((! myconfig.get(db, "experience_license").equals("")) && (! myconfig.get(db, "use_experience").equals("no")) && (! myconfig.get(db, "use_usersegments").equals("no"))) {
				Segment mysegment = new Segment(mysession, db, myrequest, "", "", "", myuser);
			}
			if (mysession.get("log").equals("login")) {
				if (! myrequest.getParameter("id").equals("")) {
					if (cms != null) cms.CMSLog(myrequest.getParameter("id"), "login", "");
				} else {
					if (cms != null) cms.CMSLog(myconfig.get(db, "default_page"), "login", "");
				}
				mysession.set("log", "");
			}
			Cms.CMSAudit("action=login.ok username=" + username + " userid=" + mysession.get("userid") + " userclass=" + mysession.get("administrator"));

			if ((mysession.get("administrator").equals("administrator")) && (! myconfig.get(db, "user_password_expiration").equals("")) && (Common.intnumber(myconfig.get(db, "user_password_expiration")) > 0)) {
				String mynow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
				Calendar expirationdate = Calendar.getInstance();
				if (! mysession.get("user_updated").equals("")) {
					expirationdate.setTime(Common.strtodate(mysession.get("user_updated")));
				} else {
					expirationdate.setTime(Common.strtodate(now));
				}
				expirationdate.add(Calendar.SECOND, 24*60*60 * Common.intnumber(myconfig.get(db, "user_password_expiration")));
				if (mynow.compareTo((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(expirationdate.getTime())) > 0) {
					url = "/" + text.display("adminpath") + "/users/password.jsp";
				}
			}

			if (url.equals("-")) {
				// ignore
			} else if (require_ssl.equals("yes")) {
				if (db != null) db.close();
//				myresponse.sendRedirect("https://" + myrequest.getServerName() + Common.crlf_clean(url));
				myresponse.sendRedirect(myresponse.encodeRedirectURL("https://" + myrequest.getServerName() + Common.crlf_clean(url)));
			} else {
				if (db != null) db.close();
//				myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + Common.crlf_clean(url));
				myresponse.sendRedirect(myresponse.encodeRedirectURL(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + Common.crlf_clean(url)));
			}
		} else {
			Cms.CMSAudit("action=login.error username=" + username);
		}

		if (mysession.get("username").equals("")) {
			mysession.set("database", save_session_database);
			mysession.set("administrator", save_session_administrator);
			mysession.set("userid", save_session_userid);
			mysession.set("username", save_session_username);
			mysession.set("password", save_session_password);
			mysession.set("name", save_session_name);
			mysession.set("email", save_session_email);
			mysession.set("usertype", save_session_usertype);
			mysession.set("usergroup", save_session_usergroup);
			if (url.equals("-")) {
				// ignore
			} else if (require_ssl.equals("yes")) {
				if (db != null) db.close();
//				myresponse.sendRedirect("https://" + myrequest.getServerName() + defaultLoginURL + "?url=" + URLEncoder.encode(Common.crlf_clean(url)));
				myresponse.sendRedirect(myresponse.encodeRedirectURL("https://" + myrequest.getServerName() + defaultLoginURL + "?url=" + URLEncoder.encode(Common.crlf_clean(url))));
			} else {
				if (db != null) db.close();
//				myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + defaultLoginURL + "?url=" + URLEncoder.encode(Common.crlf_clean(url)));
				if(username.isEmpty()){
					myresponse.sendRedirect(myresponse.encodeRedirectURL(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + defaultLoginURL + "?url=" + URLEncoder.encode(Common.crlf_clean(url))));
				}else{
					//username ou password invalido
					myresponse.sendRedirect(myresponse.encodeRedirectURL(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + defaultLoginURL + "?url=" + URLEncoder.encode(Common.crlf_clean(url)) + "&error=login"));
				}
			}
		}
	}



	static public void login(Text text, Cms cms, String defaultLoginURL, String defaultRedirectURL, Session mysession, Fileupload myrequest, Response myresponse, Configuration myconfig, DB db, String require_ssl, String database) throws Exception {
		Cms.CMSAudit("action=login username=" + myrequest.getParameter("username"));
		String save_session_database = database;
		String save_session_administrator = mysession.get("administrator");
		String save_session_userid = mysession.get("userid");
		String save_session_username = mysession.get("username");
		String save_session_password = mysession.get("password");
		String save_session_name = mysession.get("name");
		String save_session_email = mysession.get("email");
		String save_session_usertype = mysession.get("usertype");
		String save_session_usergroup = mysession.get("usergroup");

		if (! myrequest.getParameter("username").equals("")) {
			mysession.regenerate(myrequest.getRequest());
		}

		mysession.set("database", "");
		mysession.set("administrator", "");
		mysession.set("userid", "");
		mysession.set("username", "");
		mysession.set("password", "");
		mysession.set("name", "");
		mysession.set("email", "");
		mysession.set("usertype", "");
		mysession.set("usergroup", "");

		mysession.set("user_organisation", "");
		mysession.set("user_created", "");
		mysession.set("user_updated", "");
		mysession.set("user_scheduled_publish", "");
		mysession.set("user_scheduled_notify", "");
		mysession.set("user_scheduled_unpublish", "");
		mysession.set("user_card_type", "");
		mysession.set("user_card_number", "");
		mysession.set("user_card_issuedmonth", "");
		mysession.set("user_card_issuedyear", "");
		mysession.set("user_card_expirymonth", "");
		mysession.set("user_card_expiryyear", "");
		mysession.set("user_card_name", "");
		mysession.set("user_card_cvc", "");
		mysession.set("user_card_issue", "");
		mysession.set("user_card_postalcode", "");
		mysession.set("user_delivery_name", "");
		mysession.set("user_delivery_organisation", "");
		mysession.set("user_delivery_address", "");
		mysession.set("user_delivery_postalcode", "");
		mysession.set("user_delivery_city", "");
		mysession.set("user_delivery_state", "");
		mysession.set("user_delivery_country", "");
		mysession.set("user_delivery_phone", "");
		mysession.set("user_delivery_fax", "");
		mysession.set("user_delivery_email", "");
		mysession.set("user_delivery_website", "");
		mysession.set("user_invoice_name", "");
		mysession.set("user_invoice_organisation", "");
		mysession.set("user_invoice_address", "");
		mysession.set("user_invoice_postalcode", "");
		mysession.set("user_invoice_city", "");
		mysession.set("user_invoice_state", "");
		mysession.set("user_invoice_country", "");
		mysession.set("user_invoice_phone", "");
		mysession.set("user_invoice_fax", "");
		mysession.set("user_invoice_email", "");
		mysession.set("user_invoice_website", "");
		mysession.set("user_notes", "");
		mysession.set("user_userinfo", "");

		String url = "";
		if (! myrequest.getParameter("url").equals("")) {
//			url = html.encodeHtmlEntities(myrequest.getParameter("url"));
			url = myrequest.getParameter("url");
		} else {
			url = defaultRedirectURL;
		}

		String username = html.encodeHtmlEntities(myrequest.getParameter("username").trim());
		String password = html.encodeHtmlEntities(myrequest.getParameter("password").trim());

		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		User myuser = null;
		if (((username.equals(myconfig.get(db, "superadmin"))) || (myconfig.get(db, "superadmin").equals(""))) && ((password.equals(myconfig.get(db, "superadmin_password"))) || (myconfig.get(db, "superadmin_password").equals("")))) {
			mysession.set("login_page", "");
			mysession.set("database", database);
			mysession.set("administrator", "administrator");
			mysession.set("userid", "0");
			mysession.set("username", myconfig.get(db, "superadmin"));
			mysession.set("password", myconfig.get(db, "superadmin_password"));
			mysession.set("name", "");
			mysession.set("email", myconfig.get(db, "superadmin_email"));
			mysession.set("usertype", "");
			mysession.set("usergroup", "");
			Cms.CMSAudit("action=login.ok username=" + username + " userid=" + mysession.get("userid") + " userclass=" + mysession.get("administrator"));
		} else if (username.equals(myconfig.get(db, "superadmin"))) {
			myuser = null;
		} else if ((! username.equals("")) && (! password.equals(""))) {
			myuser = new User(text);
			if (! myuser.readByUsernameAndPassword(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, username, password)) {
				myuser = null;
			} else if (((! myuser.getScheduledPublish().equals("")) && (myuser.getScheduledPublish().compareTo(now) > 0)) || ((! myuser.getScheduledUnpublish().equals("")) && (myuser.getScheduledUnpublish().compareTo(now) < 0))) {
				myuser = null;
			}
		} else if (! username.equals("")) {
			myuser = new User(text);
			myuser = null;
		}

		if (myuser != null) {
			mysession.set("login_page", "");
			mysession.set("database", database);
			userSession(myuser, mysession, db);
			if (mysession.get("log").equals("login")) {
				if (! myrequest.getParameter("id").equals("")) {
					if (cms != null) cms.CMSLog(myrequest.getParameter("id"), "login", "");
				} else {
					if (cms != null) cms.CMSLog(myconfig.get(db, "default_page"), "login", "");
				}
				mysession.set("log", "");
			}
			Cms.CMSAudit("action=login.ok username=" + username + " userid=" + mysession.get("userid") + " userclass=" + mysession.get("administrator"));
		} else {
			Cms.CMSAudit("action=login.error username=" + username);
		}

		if (mysession.get("username").equals("")) {
			mysession.set("database", save_session_database);
			mysession.set("administrator", save_session_administrator);
			mysession.set("userid", save_session_userid);
			mysession.set("username", save_session_username);
			mysession.set("password", save_session_password);
			mysession.set("name", save_session_name);
			mysession.set("email", save_session_email);
			mysession.set("usertype", save_session_usertype);
			mysession.set("usergroup", save_session_usergroup);
		}
	}



	static public void userSession(User myuser, Session mysession, DB db) {
		mysession.set("administrator", myuser.getUserclass());
		mysession.set("userid", myuser.getId());
		mysession.set("username", myuser.getUsername());
		mysession.set("password", myuser.getPassword());
		mysession.set("name", myuser.getName());
		mysession.set("email", myuser.getEmail());
		mysession.set("usertype", myuser.getUsertype());
		mysession.set("usergroup", myuser.getUsergroup());
		
		
		//Login ok - seta dados do membro da tabela dbmembers na sessao
		MemberDAO dao = new MemberDAO(db, null);
		Member member = (Member) dao.readByColumn("id_member", myuser.getId(), Member.class);
		mysession.getSession().setAttribute("member", member);
		System.out.println("Set member in session id: " + member.getIdMember() + " - username: "+ myuser.getUsername());

		
		String myusertypes = myuser.getUsertype();
		Iterator usertypes = myuser.usertypes(db).keySet().iterator();
		while (usertypes.hasNext()) {
			String name = "" + usertypes.next();
			if (! myusertypes.equals("")) {
				myusertypes += "|";
			}
			myusertypes += name;
		}
		mysession.set("usertypes", myusertypes);

		String myusergroups = myuser.getUsergroup();
		Iterator usergroups = myuser.usergroups(db).keySet().iterator();
		while (usergroups.hasNext()) {
			String name = "" + usergroups.next();
			if (! myusergroups.equals("")) {
				myusergroups += "|";
			}
			myusergroups += name;
		}
		mysession.set("usergroups", myusergroups);

		mysession.set("user_organisation", myuser.getOrganisation());
		mysession.set("user_created", myuser.getCreated());
		mysession.set("user_updated", myuser.getUpdated());
		mysession.set("user_scheduled_publish", myuser.getScheduledPublish());
		mysession.set("user_scheduled_notify", myuser.getScheduledNotify());
		mysession.set("user_scheduled_unpublish", myuser.getScheduledUnpublish());
		mysession.set("user_card_type", myuser.getCardType());
		mysession.set("user_card_number", myuser.getCardNumber());
		mysession.set("user_card_issuedmonth", myuser.getCardIssuedMonth());
		mysession.set("user_card_issuedyear", myuser.getCardIssuedYear());
		mysession.set("user_card_expirymonth", myuser.getCardExpiryMonth());
		mysession.set("user_card_expiryyear", myuser.getCardExpiryYear());
		mysession.set("user_card_name", myuser.getCardName());
		mysession.set("user_card_cvc", myuser.getCardCVC());
		mysession.set("user_card_issue", myuser.getCardIssue());
		mysession.set("user_card_postalcode", myuser.getCardPostalcode());
		mysession.set("user_delivery_name", myuser.getDeliveryName());
		mysession.set("user_delivery_organisation", myuser.getDeliveryOrganisation());
		mysession.set("user_delivery_address", myuser.getDeliveryAddress());
		mysession.set("user_delivery_postalcode", myuser.getDeliveryPostalcode());
		mysession.set("user_delivery_city", myuser.getDeliveryCity());
		mysession.set("user_delivery_state", myuser.getDeliveryState());
		mysession.set("user_delivery_country", myuser.getDeliveryCountry());
		mysession.set("user_delivery_phone", myuser.getDeliveryPhone());
		mysession.set("user_delivery_fax", myuser.getDeliveryFax());
		mysession.set("user_delivery_email", myuser.getDeliveryEmail());
		mysession.set("user_delivery_website", myuser.getDeliveryWebsite());
		mysession.set("user_invoice_name", myuser.getInvoiceName());
		mysession.set("user_invoice_organisation", myuser.getInvoiceOrganisation());
		mysession.set("user_invoice_address", myuser.getInvoiceAddress());
		mysession.set("user_invoice_postalcode", myuser.getInvoicePostalcode());
		mysession.set("user_invoice_city", myuser.getInvoiceCity());
		mysession.set("user_invoice_state", myuser.getInvoiceState());
		mysession.set("user_invoice_country", myuser.getInvoiceCountry());
		mysession.set("user_invoice_phone", myuser.getInvoicePhone());
		mysession.set("user_invoice_fax", myuser.getInvoiceFax());
		mysession.set("user_invoice_email", myuser.getInvoiceEmail());
		mysession.set("user_invoice_website", myuser.getInvoiceWebsite());
		mysession.set("user_notes", myuser.getNotes());
		mysession.set("user_userinfo", myuser.getUserinfo());
	}



	static public void logout(Session mysession) {
		Cms.CMSAudit("action=logout username=" + mysession.get("username") + " userid=" + mysession.get("userid") + " userclass=" + mysession.get("administrator"));
		if ((mysession != null) && (mysession.get("mode").equals("admin"))) {
			if (mysession != null) {
				mysession.set("login_page", "");
				mysession.set("stylesheet", "");
				mysession.set("template", "");
				mysession.set("version", "");
			}
		} else {
			if (mysession != null) {
				mysession.set("login_page", "");
				mysession.set("database", "");
				mysession.set("administrator", "");
				mysession.set("userid", "");
				mysession.set("username", "");
				mysession.set("password", "");
				mysession.set("name", "");
				mysession.set("email", "");
				mysession.set("usertype", "");
				mysession.set("usergroup", "");
				mysession.set("usertypes", "");
				mysession.set("usergroups", "");
				mysession.set("mode", "");
				mysession.set("stylesheet", "");
				mysession.set("template", "");
				mysession.set("version", "");

				mysession.set("user_organisation", "");
				mysession.set("user_created", "");
				mysession.set("user_updated", "");
				mysession.set("user_scheduled_publish", "");
				mysession.set("user_scheduled_notify", "");
				mysession.set("user_scheduled_unpublish", "");
				mysession.set("user_card_type", "");
				mysession.set("user_card_number", "");
				mysession.set("user_card_issuedmonth", "");
				mysession.set("user_card_issuedyear", "");
				mysession.set("user_card_expirymonth", "");
				mysession.set("user_card_expiryyear", "");
				mysession.set("user_card_name", "");
				mysession.set("user_card_cvc", "");
				mysession.set("user_card_issue", "");
				mysession.set("user_card_postalcode", "");
				mysession.set("user_delivery_name", "");
				mysession.set("user_delivery_organisation", "");
				mysession.set("user_delivery_address", "");
				mysession.set("user_delivery_postalcode", "");
				mysession.set("user_delivery_city", "");
				mysession.set("user_delivery_state", "");
				mysession.set("user_delivery_country", "");
				mysession.set("user_delivery_phone", "");
				mysession.set("user_delivery_fax", "");
				mysession.set("user_delivery_email", "");
				mysession.set("user_delivery_website", "");
				mysession.set("user_invoice_name", "");
				mysession.set("user_invoice_organisation", "");
				mysession.set("user_invoice_address", "");
				mysession.set("user_invoice_postalcode", "");
				mysession.set("user_invoice_city", "");
				mysession.set("user_invoice_state", "");
				mysession.set("user_invoice_country", "");
				mysession.set("user_invoice_phone", "");
				mysession.set("user_invoice_fax", "");
				mysession.set("user_invoice_email", "");
				mysession.set("user_invoice_website", "");
				mysession.set("user_notes", "");
				mysession.set("user_userinfo", "");
			}
		}
	}



	static public void logout(Session mysession, Request myrequest, Response myresponse, DB db) throws Exception {
		logout(mysession, myrequest, myresponse, db, "");
	}
	static public void logout(Session mysession, Request myrequest, Response myresponse, DB db, String require_ssl) {
		logout(mysession);
		if ((mysession != null) && (mysession.get("mode").equals("admin"))) {
			if (! myrequest.getParameter("redirect").equals("")) {
				if (db != null) db.close();
				mysession.invalidate();
				if (require_ssl.equals("yes")) {
					myresponse.sendRedirect(myresponse.encodeRedirectURL("https://" + myrequest.getServerName() + Common.crlf_clean(myrequest.getParameter("redirect"))));
				} else {
					myresponse.sendRedirect(myresponse.encodeRedirectURL(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + Common.crlf_clean(myrequest.getParameter("redirect"))));
				}
			} else {
				if (db != null) db.close();
				mysession.invalidate();
				if (require_ssl.equals("yes")) {
					myresponse.sendRedirect(myresponse.encodeRedirectURL("https://" + myrequest.getServerName() + "/"));
				} else {
					myresponse.sendRedirect(myresponse.encodeRedirectURL(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/"));
				}
			}
		} else {
			if (! myrequest.getParameter("redirect").equals("")) {
				if (db != null) db.close();
				mysession.invalidate();
				if ((myrequest.getParameter("redirect").startsWith("http://")) || (myrequest.getParameter("redirect").startsWith("https://"))) {
					Configuration myconfig = new Configuration();
					if ((! myconfig.get(db, "redirect_urls").trim().equals("")) && (! Common.startsWithAnyOf(Common.crlf_clean(myrequest.getParameter("redirect")), myconfig.get(db, "redirect_urls")))) {
						// ignore
					} else {
						myresponse.sendRedirect(Common.crlf_clean(myrequest.getParameter("redirect")));
					}
				} else if (require_ssl.equals("yes")) {
					myresponse.sendRedirect(myresponse.encodeRedirectURL("https://" + myrequest.getServerName() + Common.crlf_clean(myrequest.getParameter("redirect"))));
				} else {
					myresponse.sendRedirect(myresponse.encodeRedirectURL("http://" + myrequest.getServerName() + myrequest.getServerPort() + Common.crlf_clean(myrequest.getParameter("redirect"))));
				}
			} else {
				if (db != null) db.close();
				mysession.invalidate();
				if (require_ssl.equals("yes")) {
					myresponse.sendRedirect(myresponse.encodeRedirectURL("https://" + myrequest.getServerName() + "/"));
				} else {
					myresponse.sendRedirect(myresponse.encodeRedirectURL("http://" + myrequest.getServerName() + myrequest.getServerPort() + "/"));
				}
			}
		}
	}



//	static private void doEmail(String sender, String recipient, String subject, String username, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
//		doEmail(new Text(), sender, recipient, subject, username, null, mysession, myrequest, myresponse, myconfig, db);
//	}
//	static private void doEmail(String sender, String recipient, String subject, String username, ServletContext servletcontext, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
//		doEmail(new Text(), sender, recipient, subject, username, servletcontext, mysession, myrequest, myresponse, myconfig, db);
//	}
//	static private void doEmail(Text text, String sender, String recipient, String subject, String username, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
//		doEmail(text, sender, recipient, subject, username, null, mysession, myrequest, myresponse, myconfig, db);
//	}
	static private void doEmail(Text text, String sender, String recipient, String subject, String username, ServletContext servletcontext, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		HashMap<String,String> dummy = new HashMap<String,String>();
		HashMap<String,String> email = new HashMap<String,String>();
		email.put("from", "" + sender);
		email.put("to", "" + recipient);
		email.put("subject", "" + subject + ": " + username);
		email.put("content", "" + username + "<br>" + "\r\n" + "<br>" + "\r\n" + "REMOTE_ADDR: " + myrequest.getRemoteAddr() + "<br>" + "\r\n" + "REMOTE_HOST: " + myrequest.getRemoteHost() + "<br>" + "\r\n" + "REMOTE_USER: " + myrequest.getRemoteUser() + "<br>" + "\r\n" + "<br>" + "\r\n" + "SERVER_NAME: " + myrequest.getServerName() + "<br>" + "\r\n" + "SCRIPT_NAME: " + myrequest.getServletPath() + "<br>" + "\r\n" + "PATH_INFO: " + myrequest.getPathInfo() + "<br>" + "\r\n" + "QUERY_STRING: " + myrequest.getQueryString());
		Email.send_email(text, dummy, (String)email.get("subject"), (String)email.get("content"), "", (String)email.get("from"), (String)email.get("to"), "", "", "", "", myrequest.getServerName(), servletcontext, mysession, myrequest, myresponse, myconfig, db);
	}



	static public boolean addressesMatch(String request_address, String session_address) {
		return addressesMatch(request_address, session_address, 32, 64);
	}
	static public boolean addressesMatch(String request_address, String session_address, int ip32accuracy, int ip64accuracy) {
		Cms.CMSDebug("Login.addressesMatch:"+request_address+":::"+session_address);
		if (Pattern.compile("^[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+$").matcher(request_address).find()) {
			// long IP number
			if (ip64accuracy < 64) request_address = request_address.substring(0, request_address.lastIndexOf("."));
			if (ip64accuracy < 56) request_address = request_address.substring(0, request_address.lastIndexOf("."));
			if (ip64accuracy < 48) request_address = request_address.substring(0, request_address.lastIndexOf("."));
		} else if (Pattern.compile("^[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+$").matcher(request_address).find()) {
			// short IP number
			if (ip32accuracy < 32) request_address = request_address.substring(0, request_address.lastIndexOf("."));
			if (ip32accuracy < 24) request_address = request_address.substring(0, request_address.lastIndexOf("."));
			if (ip32accuracy < 16) request_address = request_address.substring(0, request_address.lastIndexOf("."));
		} else {
			if (request_address.indexOf(".") > 0) request_address = request_address.substring(request_address.indexOf(".")+1);
		}
		if (Pattern.compile("^[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+$").matcher(session_address).find()) {
			// long IP number
			if (ip64accuracy < 64) session_address = session_address.substring(0, session_address.lastIndexOf("."));
			if (ip64accuracy < 56) session_address = session_address.substring(0, session_address.lastIndexOf("."));
			if (ip64accuracy < 48) session_address = session_address.substring(0, session_address.lastIndexOf("."));
		} else if (Pattern.compile("^[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+$").matcher(session_address).find()) {
			// short IP number
			if (ip32accuracy < 32) session_address = session_address.substring(0, session_address.lastIndexOf("."));
			if (ip32accuracy < 24) session_address = session_address.substring(0, session_address.lastIndexOf("."));
			if (ip32accuracy < 16) session_address = session_address.substring(0, session_address.lastIndexOf("."));
		} else {
			if (session_address.indexOf(".") > 0) session_address = session_address.substring(session_address.indexOf(".")+1);
		}
		Cms.CMSDebug("Login.addressesMatch:"+request_address+":::"+session_address+":::"+(request_address.equals(session_address)));
		if (request_address.equals(session_address)) {
			return true;
		} else {
			return false;
		}
	}



}
