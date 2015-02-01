package HardCore;

import java.io.*;
import java.io.File;
import java.nio.*;
import java.nio.channels.*;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.*;
import javax.servlet.*;

public class UCpublishScheduled {
	private String error = "";
	private static boolean doingScheduledFlag = false;
	private Text text = new Text();



	public UCpublishScheduled() {
	}



	public UCpublishScheduled(Text mytext) {
		if (mytext != null) text = mytext;
	}



	private synchronized static boolean doingScheduled(){
		if (doingScheduledFlag) {
			return true;
		} else {
			doingScheduledFlag = true;
			return false;
		}
	}



	public void doScheduled(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		if (doingScheduled()) return;
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String scheduled_next = myconfig.get(db, "scheduled_next");
//		if (scheduled_next.equals("")) {
//			scheduled_next = myconfig.get(db, "scheduled_next_batch");
//		}
		if (scheduled_next.equals("")) {
			if (! myconfig.get(db, "scheduled_last").equals("")) {
				scheduled_next = myconfig.get(db, "scheduled_last");
			}
		}
		myconfig.set(db, "scheduled_next", "");
		myconfig.set(db, "scheduled_next_batch", "");
		String save_session_version = mysession.get("version");
		mysession.set("version", "");
		String save_session_id = mysession.get("id");

		try {
			String SQL = "select id from content where (locked_schedule is null or locked_schedule <> 1) and (scheduled_publish <= " + db.quote(timestamp) + ") and " + db.is_not_blank("scheduled_publish") + "";
			Content content = new Content(text);
			content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				mysession.set("id", content.getId());
				Page mypage = new Page(text);
				mypage.doParseOutput(false);
				mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, content.getId(), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				String old_server_filename = mypage.getServerFilename();
				mypage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, content.getId(), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				if ((! old_server_filename.equals("")) && (! old_server_filename.equals(mypage.getServerFilename()))) {
					String save_server_filename = mypage.getServerFilename();
					mypage.setServerFilename(old_server_filename);
					mypage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
					mypage.setServerFilename(save_server_filename);
				}
				mypage.setPublished(mypage.getScheduledPublish());
				mypage.setPublishedBy(mypage.getUpdatedBy());
				mypage.setScheduledPublish("");
				mypage.update(db, myconfig, mypage.getId(), "content", "id");
				mypage.publishServerFilename(server, myrequest, myresponse, mysession, myconfig, db);
				if (mypage.exists(db, mypage.getId(), "content_public", "id", "", "", "", "", "", mysession)) {
					mypage.update(db, myconfig, mypage.getId(), "content_public", "id");
				} else {
					mypage.create(db, myconfig, "content_public", "id");
				}
				String new_server_filename = mypage.getServerFilename();
				mypage.handleStaticAddressLinks(server, mysession, myrequest, myresponse, myconfig, db, old_server_filename, new_server_filename);
				mypage.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", db, myconfig, mypage.getId(), "content_public", "id", "", "", "", "", "", "", myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				mypage.outputStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
				mypage.exportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
			}
			mysession.set("id", "");

			SQL = "select id from content_public where (locked_unschedule is null or locked_unschedule <> 1) and (scheduled_unpublish <= " + db.quote(timestamp) + ") and " + db.is_not_blank("scheduled_unpublish") + "";
			content = new Content(text);
			content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				Page mypage = new Page(text);
				mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, content.getId(), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				String published_server_filename = mypage.getServerFilename();
				mypage.preview_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, content.getId(), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				if ((! published_server_filename.equals("")) && ((! published_server_filename.equals(mypage.getServerFilename())) || (myconfig.get(db, "use_static_content").equals("yes")))) {
					String save_server_filename = mypage.getServerFilename();
					mypage.setServerFilename(published_server_filename);
					mypage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
					mypage.setServerFilename(save_server_filename);
				} else {
					mypage.unpublishStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
				}
				mypage.public_delete(db, myconfig, mypage.getId(), Common.getRealPath(server, myconfig.get(db, "URLrootpath")));
				mypage.deleteExportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
			}

			if ((scheduled_next.compareTo(timestamp) <= 0) && (! scheduled_next.equals(""))) {
				SQL = "select archiveid,id from content_archive where (locked_schedule is null or locked_schedule <> 1) and (published >= updated) and (published >= " + db.quote(scheduled_next) + ") and (published <= " + db.quote(timestamp) + ") and " + db.is_not_blank("published") + " order by published,id,archiveid";
				content = new Content(text);
				content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
				while (content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
					mysession.set("id", content.getId());
					Page mypage = new Page(text);
					mypage.doParseOutput(false);
					mypage.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, content.getId(), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
					String old_server_filename = mypage.getServerFilename();
					String old_published = mypage.getPublished();
					String old_published_by = mypage.getPublishedBy();
					mypage.archive_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, content.getArchiveId(), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
					if ((! old_server_filename.equals("")) && (! old_server_filename.equals(mypage.getServerFilename()))) {
						String save_server_filename = mypage.getServerFilename();
						mypage.setServerFilename(old_server_filename);
						mypage.deleteStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
						mypage.setServerFilename(save_server_filename);
						old_server_filename = mypage.getServerFilename();
					}
					if ((old_published.equals("")) || (old_published.compareTo(mypage.getPublished()) <= 0)) {
//						mypage.setPublished(mypage.getScheduledPublish());
//						mypage.setPublishedBy(mypage.getUpdatedBy());
						mypage.setScheduledPublish("");
//						mypage.update(db, myconfig, mypage.getId(), "content", "id");
//						mypage.publishServerFilename(server, myrequest, myresponse, mysession, myconfig, db);
						String archiveid = mypage.getArchiveId();
						if ((! mypage.getServerFilename().equals("")) && ((mypage.getContentClass().equals("image")) || (mypage.getContentClass().equals("file")))) {
							String filename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + mypage.getServerFilename());
							if (old_server_filename.equals("")) {
								old_server_filename = mypage.getServerFilename();
							}
							if (Common.fileExists(filename + "." + archiveid) && (! old_server_filename.equals(""))) {
								Common.copyFile(filename + "." + archiveid, Common.getRealPath(server, myconfig.get(db, "URLrootpath") + old_server_filename));
							}
						}
						mypage.setServerFilename(old_server_filename);
						if (mypage.exists(db, mypage.getId(), "content_public", "id", "", "", "", "", "", mysession)) {
							mypage.update(db, myconfig, mypage.getId(), "content_public", "id");
						} else {
							mypage.create(db, myconfig, "content_public", "id");
						}
						String new_server_filename = mypage.getServerFilename();
						mypage.handleStaticAddressLinks(server, mysession, myrequest, myresponse, myconfig, db, old_server_filename, new_server_filename);
						mypage.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", "", "", "", "", "", "", "", db, myconfig, mypage.getId(), "content_public", "id", "", "", "", "", "", "", myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
						mypage.outputStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
						mypage.exportStaticFile(server, myrequest, myresponse, mysession, null, myconfig, db);
					}
				}
				mysession.set("id", "");
			}



			SQL = "select * from users where (scheduled_publish <= " + db.quote(timestamp) + ") and " + db.is_not_blank("scheduled_publish") + " and " + db.is_not_blank("scheduled_publish_email") + " and ((scheduled_publish > scheduled_last) or " + db.is_blank("scheduled_last") + ") order by scheduled_publish";
			User user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				user.setScheduledLast("" + user.getScheduledPublish());
				user.update(db);

				Page page = new Page(text);
				page.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, user.getScheduledPublishEmail(), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				page.setBody(page.getContent());
				page.parse_output_delivery(null, null, user, null, "", server, mysession, myrequest, myresponse, db, myconfig);
				page.parse_output_extensions(server, myrequest, myresponse, mysession);
				page.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, page.getId(), "content_public", "id", "", "", "", "", "", mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));

				String sender = myconfig.get(db, "contact_form_recipient");
				String recipient = user.getEmail();
				String bcc = Common.join(", ", user.publishersEmails(mysession, myconfig, db));
				if (bcc.equals("")) {
					bcc = myconfig.get(db, "superadmin_email");
				}
				if ((! page.getTitle().equals("")) && (! recipient.equals(""))) {
					HashMap<String,String> email = new HashMap<String,String>();
					email.put("stylesheet", page.getStyleSheet());
					if (page.getStyleSheet().equals("0")) {
						email.put("stylesheet", "");
						email.put("style", "");
					} else if (page.getStyleSheet().equals("")) {
						Content stylesheet_content = new Content(text);
						stylesheet_content.public_read(db, myconfig, myconfig.get(db, "default_stylesheet"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
						email.put("stylesheet", "/stylesheet.jsp?id=" + myconfig.get(db, "default_stylesheet"));
						email.put("style", stylesheet_content.getContent());
					} else {
						Content stylesheet_content = new Content(text);
						stylesheet_content.public_read(db, myconfig, page.getStyleSheet(), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
						email.put("stylesheet", "/stylesheet.jsp?id=" + page.getStyleSheet());
						email.put("style", stylesheet_content.getContent());
					}
					Email.send_email(text, email, page.getTitle(), page.getBody(), "", sender, recipient, "", bcc, "" + email.get("stylesheet"), "" + email.get("style"), myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
				}
			}

			SQL = "select * from users where (scheduled_notify <= " + db.quote(timestamp) + ") and " + db.is_not_blank("scheduled_notify") + " and " + db.is_not_blank("scheduled_notify_email") + " and ((scheduled_notify > scheduled_last) or " + db.is_blank("scheduled_last") + ") order by scheduled_notify";
			user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				user.setScheduledLast("" + user.getScheduledNotify());
				user.update(db);

				Page page = new Page(text);
				page.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, user.getScheduledNotifyEmail(), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				page.setBody(page.getContent());
				page.parse_output_delivery(null, null, user, null, "", server, mysession, myrequest, myresponse, db, myconfig);
				page.parse_output_extensions(server, myrequest, myresponse, mysession);
				page.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, page.getId(), "content_public", "id", "", "", "", "", "", mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));

				String sender = myconfig.get(db, "contact_form_recipient");
				String recipient = user.getEmail();
				String bcc = Common.join(", ", user.publishersEmails(mysession, myconfig, db));
				if (bcc.equals("")) {
					bcc = myconfig.get(db, "superadmin_email");
				}
				if ((! page.getTitle().equals("")) && (! recipient.equals(""))) {
					HashMap<String,String> email = new HashMap<String,String>();
					email.put("stylesheet", page.getStyleSheet());
					if (page.getStyleSheet().equals("0")) {
						email.put("stylesheet", "");
						email.put("style", "");
					} else if (page.getStyleSheet().equals("")) {
						Content stylesheet_content = new Content(text);
						stylesheet_content.public_read(db, myconfig, myconfig.get(db, "default_stylesheet"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
						email.put("stylesheet", "/stylesheet.jsp?id=" + myconfig.get(db, "default_stylesheet"));
						email.put("style", stylesheet_content.getContent());
					} else {
						Content stylesheet_content = new Content(text);
						stylesheet_content.public_read(db, myconfig, page.getStyleSheet(), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
						email.put("stylesheet", "/stylesheet.jsp?id=" + page.getStyleSheet());
						email.put("style", stylesheet_content.getContent());
					}
					Email.send_email(text, email, page.getTitle(), page.getBody(), "", sender, recipient, "", bcc, "" + email.get("stylesheet"), "" + email.get("style"), myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
				}
			}

			SQL = "select * from users where (scheduled_unpublish <= " + db.quote(timestamp) + ") and " + db.is_not_blank("scheduled_unpublish") + " and " + db.is_not_blank("scheduled_unpublish_email") + " and ((scheduled_unpublish > scheduled_last) or " + db.is_blank("scheduled_last") + ") order by scheduled_unpublish";
			user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			while (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				user.setScheduledLast("" + user.getScheduledUnpublish());
				user.update(db);

				Page page = new Page(text);
				page.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, user.getScheduledUnpublishEmail(), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				page.setBody(page.getContent());
				page.parse_output_delivery(null, null, user, null, "", server, mysession, myrequest, myresponse, db, myconfig);
				page.parse_output_extensions(server, myrequest, myresponse, mysession);
				page.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, page.getId(), "content_public", "id", "", "", "", "", "", mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));

				String sender = myconfig.get(db, "contact_form_recipient");
				String recipient = user.getEmail();
				String bcc = Common.join(", ", user.publishersEmails(mysession, myconfig, db));
				if (bcc.equals("")) {
					bcc = myconfig.get(db, "superadmin_email");
				}
				if ((! page.getTitle().equals("")) && (! recipient.equals(""))) {
					HashMap<String,String> email = new HashMap<String,String>();
					email.put("stylesheet", page.getStyleSheet());
					if (page.getStyleSheet().equals("0")) {
						email.put("stylesheet", "");
						email.put("style", "");
					} else if (page.getStyleSheet().equals("")) {
						Content stylesheet_content = new Content(text);
						stylesheet_content.public_read(db, myconfig, myconfig.get(db, "default_stylesheet"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
						email.put("stylesheet", "/stylesheet.jsp?id=" + myconfig.get(db, "default_stylesheet"));
						email.put("style", stylesheet_content.getContent());
					} else {
						Content stylesheet_content = new Content(text);
						stylesheet_content.public_read(db, myconfig, page.getStyleSheet(), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
						email.put("stylesheet", "/stylesheet.jsp?id=" + page.getStyleSheet());
						email.put("style", stylesheet_content.getContent());
					}
					Email.send_email(text, email, page.getTitle(), page.getBody(), "", sender, recipient, "", bcc, "" + email.get("stylesheet"), "" + email.get("style"), myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
				}
			}



			SQL = "select * from hosting where (scheduled_publish <= " + db.quote(timestamp) + ") and " + db.is_not_blank("scheduled_publish") + " and " + db.is_not_blank("scheduled_publish_email") + " and ((scheduled_publish > scheduled_last) or " + db.is_blank("scheduled_last") + ") order by scheduled_publish";
			Hosting hosting = new Hosting(text);
			hosting.records(db, SQL);
			while (hosting.records(db, "")) {
				hosting.setScheduledLast("" + hosting.getScheduledPublish());
				hosting.update(db);

				Page page = new Page(text);
				page.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, hosting.getScheduledPublishEmail(), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				page.setBody(page.getContent());
				page.parse_output_delivery(null, null, null, hosting, "", server, mysession, myrequest, myresponse, db, myconfig);
				page.parse_output_extensions(server, myrequest, myresponse, mysession);
				page.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, page.getId(), "content_public", "id", "", "", "", "", "", mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));

				String sender = myconfig.get(db, "contact_form_recipient");
				String recipient = hosting.getSuperadminEmail();
				String bcc = myconfig.get(db, "superadmin_email");
				if ((! page.getTitle().equals("")) && (! recipient.equals(""))) {
					HashMap<String,String> email = new HashMap<String,String>();
					email.put("stylesheet", page.getStyleSheet());
					if (page.getStyleSheet().equals("0")) {
						email.put("stylesheet", "");
						email.put("style", "");
					} else if (page.getStyleSheet().equals("")) {
						Content stylesheet_content = new Content(text);
						stylesheet_content.public_read(db, myconfig, myconfig.get(db, "default_stylesheet"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
						email.put("stylesheet", "/stylesheet.jsp?id=" + myconfig.get(db, "default_stylesheet"));
						email.put("style", stylesheet_content.getContent());
					} else {
						Content stylesheet_content = new Content(text);
						stylesheet_content.public_read(db, myconfig, page.getStyleSheet(), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
						email.put("stylesheet", "/stylesheet.jsp?id=" + page.getStyleSheet());
						email.put("style", stylesheet_content.getContent());
					}
					Email.send_email(text, email, page.getTitle(), page.getBody(), "", sender, recipient, "", bcc, "" + email.get("stylesheet"), "" + email.get("style"), myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
				}
			}

			SQL = "select * from hosting where (scheduled_notify <= " + db.quote(timestamp) + ") and " + db.is_not_blank("scheduled_notify") + " and " + db.is_not_blank("scheduled_notify_email") + " and ((scheduled_notify > scheduled_last) or " + db.is_blank("scheduled_last") + ") order by scheduled_notify";
			hosting = new Hosting(text);
			hosting.records(db, SQL);
			while (hosting.records(db, "")) {
				hosting.setScheduledLast("" + hosting.getScheduledNotify());
				hosting.update(db);

				Page page = new Page(text);
				page.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, hosting.getScheduledNotifyEmail(), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				page.setBody(page.getContent());
				page.parse_output_delivery(null, null, null, hosting, "", server, mysession, myrequest, myresponse, db, myconfig);
				page.parse_output_extensions(server, myrequest, myresponse, mysession);
				page.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, page.getId(), "content_public", "id", "", "", "", "", "", mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));

				String sender = myconfig.get(db, "contact_form_recipient");
				String recipient = hosting.getSuperadminEmail();
				String bcc = myconfig.get(db, "superadmin_email");
				if ((! page.getTitle().equals("")) && (! recipient.equals(""))) {
					HashMap<String,String> email = new HashMap<String,String>();
					email.put("stylesheet", page.getStyleSheet());
					if (page.getStyleSheet().equals("0")) {
						email.put("stylesheet", "");
						email.put("style", "");
					} else if (page.getStyleSheet().equals("")) {
						Content stylesheet_content = new Content(text);
						stylesheet_content.public_read(db, myconfig, myconfig.get(db, "default_stylesheet"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
						email.put("stylesheet", "/stylesheet.jsp?id=" + myconfig.get(db, "default_stylesheet"));
						email.put("style", stylesheet_content.getContent());
					} else {
						Content stylesheet_content = new Content(text);
						stylesheet_content.public_read(db, myconfig, page.getStyleSheet(), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
						email.put("stylesheet", "/stylesheet.jsp?id=" + page.getStyleSheet());
						email.put("style", stylesheet_content.getContent());
					}
					Email.send_email(text, email, page.getTitle(), page.getBody(), "", sender, recipient, "", bcc, "" + email.get("stylesheet"), "" + email.get("style"), myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
				}
			}

			SQL = "select * from hosting where (scheduled_unpublish <= " + db.quote(timestamp) + ") and " + db.is_not_blank("scheduled_unpublish") + " and " + db.is_not_blank("scheduled_unpublish_email") + " and ((scheduled_unpublish > scheduled_last) or " + db.is_blank("scheduled_last") + ") order by scheduled_unpublish";
			hosting = new Hosting(text);
			hosting.records(db, SQL);
			while (hosting.records(db, "")) {
				hosting.setScheduledLast("" + hosting.getScheduledUnpublish());
				hosting.update(db);

				Page page = new Page(text);
				page.public_read(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, hosting.getScheduledUnpublishEmail(), "", "", "", "", "", mysession.get("template"), "", myconfig.get(db, "default_template"), mysession.get("stylesheet"), "", myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				page.setBody(page.getContent());
				page.parse_output_delivery(null, null, null, hosting, "", server, mysession, myrequest, myresponse, db, myconfig);
				page.parse_output_extensions(server, myrequest, myresponse, mysession);
				page.parse_output(server, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), "", mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("administrator"), db, myconfig, page.getId(), "content_public", "id", "", "", "", "", "", mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));

				String sender = myconfig.get(db, "contact_form_recipient");
				String recipient = hosting.getSuperadminEmail();
				String bcc = myconfig.get(db, "superadmin_email");
				if ((! page.getTitle().equals("")) && (! recipient.equals(""))) {
					HashMap<String,String> email = new HashMap<String,String>();
					email.put("stylesheet", page.getStyleSheet());
					if (page.getStyleSheet().equals("0")) {
						email.put("stylesheet", "");
						email.put("style", "");
					} else if (page.getStyleSheet().equals("")) {
						Content stylesheet_content = new Content(text);
						stylesheet_content.public_read(db, myconfig, myconfig.get(db, "default_stylesheet"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
						email.put("stylesheet", "/stylesheet.jsp?id=" + myconfig.get(db, "default_stylesheet"));
						email.put("style", stylesheet_content.getContent());
					} else {
						Content stylesheet_content = new Content(text);
						stylesheet_content.public_read(db, myconfig, page.getStyleSheet(), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
						email.put("stylesheet", "/stylesheet.jsp?id=" + page.getStyleSheet());
						email.put("style", stylesheet_content.getContent());
					}
					Email.send_email(text, email, page.getTitle(), page.getBody(), "", sender, recipient, "", bcc, "" + email.get("stylesheet"), "" + email.get("style"), myrequest.getServerName(), server, mysession, myrequest, myresponse, myconfig, db);
				}
			}

			scheduled_next = getScheduledNext(server, mysession, myrequest, myresponse, myconfig, db, timestamp, "");
			myconfig.set(db, "scheduled_last", timestamp);
			myconfig.set(db, "scheduled_next_batch", scheduled_next);
			myconfig.set(db, "scheduled_next", scheduled_next);

		} catch (Exception e) {
		}
		mysession.set("version", save_session_version);
		mysession.set("id", save_session_id);
		doingScheduledFlag = false;
	}



	public String getScheduledNext(ServletContext server, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, String timestamp, String scheduled_next) {
		try {
			Content content = null;
			User user = null;
			Hosting hosting = null;
			String SQL = "";

			SQL = "select scheduled_publish from content where (locked_schedule is null or locked_schedule <> 1) and (scheduled_publish > " + db.quote(timestamp) + ") and " + db.is_not_blank("scheduled_publish") + " order by scheduled_publish";
			content = new Content(text);
			content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			if (content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				if ((content.getScheduledPublish().compareTo(scheduled_next) < 0) || (scheduled_next.equals(""))) {
					scheduled_next = "" + content.getScheduledPublish();
				}
			}
			content.closeRecords(db);

			SQL = "select scheduled_unpublish from content_public where (locked_unschedule is null or locked_unschedule <> 1) and (scheduled_unpublish > " + db.quote(timestamp) + ") and " + db.is_not_blank("scheduled_unpublish") + " order by scheduled_unpublish";
			content = new Content(text);
			content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			if (content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				if ((content.getScheduledUnpublish().compareTo(scheduled_next) < 0) || (scheduled_next.equals(""))) {
					scheduled_next = "" + content.getScheduledUnpublish();
				}
			}
			content.closeRecords(db);



			SQL = "select scheduled_publish from users where (scheduled_publish > " + db.quote(timestamp) + ") and " + db.is_not_blank("scheduled_publish") + " order by scheduled_publish";
			user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			if (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				if ((user.getScheduledPublish().compareTo(scheduled_next) < 0) || (scheduled_next.equals(""))) {
					scheduled_next = "" + user.getScheduledPublish();
				}
			}
			user.closeRecords(db);

			SQL = "select scheduled_notify from users where (scheduled_notify > " + db.quote(timestamp) + ") and " + db.is_not_blank("scheduled_notify") + " order by scheduled_notify";
			user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			if (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				if ((user.getScheduledNotify().compareTo(scheduled_next) < 0) || (scheduled_next.equals(""))) {
					scheduled_next = "" + user.getScheduledNotify();
				}
			}
			user.closeRecords(db);

			SQL = "select scheduled_unpublish from users where (scheduled_unpublish > " + db.quote(timestamp) + ") and " + db.is_not_blank("scheduled_unpublish") + " order by scheduled_unpublish";
			user = new User(text);
			user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			if (user.records(mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				if ((user.getScheduledUnpublish().compareTo(scheduled_next) < 0) || (scheduled_next.equals(""))) {
					scheduled_next = "" + user.getScheduledUnpublish();
				}
			}
			user.closeRecords(db);



			SQL = "select scheduled_publish from hosting where (scheduled_publish > " + db.quote(timestamp) + ") and " + db.is_not_blank("scheduled_publish") + " and " + db.is_not_blank("scheduled_publish_email") + " order by scheduled_publish";
			hosting = new Hosting(text);
			hosting.records(db, SQL);
			if (hosting.records(db, "")) {
				if ((hosting.getScheduledPublish().compareTo(scheduled_next) < 0) || (scheduled_next.equals(""))) {
					scheduled_next = "" + hosting.getScheduledPublish();
				}
			}
			hosting.closeRecords(db);

			SQL = "select scheduled_notify from hosting where (scheduled_notify > " + db.quote(timestamp) + ") and " + db.is_not_blank("scheduled_notify") + " and " + db.is_not_blank("scheduled_notify_email") + " order by scheduled_notify";
			hosting = new Hosting(text);
			hosting.records(db, SQL);
			if (hosting.records(db, "")) {
				if ((hosting.getScheduledNotify().compareTo(scheduled_next) < 0) || (scheduled_next.equals(""))) {
					scheduled_next = "" + hosting.getScheduledNotify();
				}
			}
			hosting.closeRecords(db);

			SQL = "select scheduled_unpublish from hosting where (scheduled_unpublish > " + db.quote(timestamp) + ") and " + db.is_not_blank("scheduled_unpublish") + " and " + db.is_not_blank("scheduled_unpublish_email") + " order by scheduled_unpublish";
			hosting = new Hosting(text);
			hosting.records(db, SQL);
			if (hosting.records(db, "")) {
				if ((hosting.getScheduledUnpublish().compareTo(scheduled_next) < 0) || (scheduled_next.equals(""))) {
					scheduled_next = "" + hosting.getScheduledUnpublish();
				}
			}
			hosting.closeRecords(db);

			SQL = "select published from content_archive where (locked_schedule is null or locked_schedule <> 1) and (published > " + db.quote(timestamp) + ") and " + db.is_not_blank("published") + " order by published";
			content = new Content(text);
			content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			if (content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
				if ((content.getPublished().compareTo(scheduled_next) < 0) || (scheduled_next.equals(""))) {
					scheduled_next = "" + content.getPublished();
				}
			}
			content.closeRecords(db);

		} catch (Exception e) {
		}
		return scheduled_next;
	}



}
