<%@ include file="../../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.Common" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Email" %>
<%@ page import="HardCore.Fileupload" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.RequireUser" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%

Fileupload filepost = new Fileupload(myrequest, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath"), myconfig.get(db, "URLuploadpath"));
if (License.valid(db, myconfig, "community")) {
	RequireUser.Administrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));

	String save_stylesheet = mysession.get("stylesheet");
	String save_template = mysession.get("template");
	String save_version = mysession.get("version");
	String save_mode = mysession.get("mode");
	mysession.set("stylesheet", "");
	mysession.set("template", "");
	mysession.set("version", "");
	mysession.set("mode", "admin");

	String id = myrequest.getParameter("id");
	if (filepost.parameterExists("id")) id = filepost.getParameter("id");
	Page mypage = new Page(mytext);
	String save_default_version = myconfig.get(db, "default_version");
	myconfig.setTemp("default_version", "");
	mypage.public_read(servletcontext, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, id, "", "", mysession.get("template"), myconfig.get(db, "default_template"), mysession.get("stylesheet"), myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
	myconfig.setTemp("default_version", save_default_version);

	mypage.setServerFilename("");
	if (filepost.parameterExists("title")) {
		mypage.setTitle(filepost.getParameter("title"));
	} else {
		mypage.setTitle(myrequest.getParameter("title"));
	}
	if (filepost.parameterExists("content")) {
		mypage.setContent(filepost.getParameter("content"));
	} else {
		mypage.setContent(myrequest.getParameter("content"));
	}
	if (filepost.parameterExists("summary")) {
		mypage.setSummary(filepost.getParameter("summary"));
	} else {
		mypage.setSummary(myrequest.getParameter("summary"));
	}
	if (filepost.parameterExists("keywords")) {
		mypage.setKeywords(filepost.getParameter("keywords"));
	} else if (myrequest.parameterExists("keywords")) {
		mypage.setKeywords(myrequest.getParameter("keywords"));
	}
	if (filepost.parameterExists("metainfo")) {
		mypage.setMetaInfo(filepost.getParameter("metainfo"));
	} else if (myrequest.parameterExists("metainfo")) {
		mypage.setMetaInfo(myrequest.getParameter("metainfo"));
	}
	mypage.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);

	if (mypage.getCreator()) {
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		String username = mysession.get("username");
		mypage.setCreated(timestamp);
		mypage.setCreatedBy(username);
		mypage.setUpdated(timestamp);
		mypage.setUpdatedBy(username);
		if (filepost.parameterExists("created")) {
			if (! filepost.getParameter("created").equals("")) {
				mypage.setCreated(filepost.getParameter("created"));
				mypage.setCreatedBy(username);
			}
		} else {
			if (! myrequest.getParameter("created").equals("")) {
				mypage.setCreated(myrequest.getParameter("created"));
				mypage.setCreatedBy(username);
			}
		}
		if (filepost.parameterExists("updated")) {
			if (! filepost.getParameter("updated").equals("")) {
				mypage.setUpdated(filepost.getParameter("updated"));
				mypage.setUpdatedBy(username);
			}
		} else {
			if (! myrequest.getParameter("updated").equals("")) {
				mypage.setUpdated(myrequest.getParameter("updated"));
				mypage.setUpdatedBy(username);
			}
		}
		mypage.setScheduledPublish("");
		if ((mypage.getPublisher()) && ((! myrequest.parameterExists("publish")) || (myrequest.getParameter("publish").equals("yes"))) && ((! filepost.parameterExists("publish")) || (filepost.getParameter("publish").equals("yes")))) {
			if (filepost.parameterExists("scheduled_publish")) {
				if (filepost.getParameter("scheduled_publish").compareTo(timestamp) <= 0) {
					mypage.setRequestedPublish("");
					mypage.setRequestedUnpublish("");
					mypage.setScheduledPublish("");
					mypage.setScheduledUnpublish("");
					mypage.setPublished(timestamp);
					mypage.setPublishedBy(username);
					mypage.setUnpublished("");
					mypage.setUnpublishedBy("");
				} else {
					mypage.setRequestedPublish("");
					mypage.setRequestedUnpublish("");
					mypage.setScheduledPublish(filepost.getParameter("scheduled_publish"));
					mypage.setScheduledUnpublish("");
					mypage.setPublished("");
					mypage.setPublishedBy("");
					mypage.setUnpublished("");
					mypage.setUnpublishedBy("");
				}
			} else {
				if (myrequest.getParameter("scheduled_publish").compareTo(timestamp) <= 0) {
					mypage.setRequestedPublish("");
					mypage.setRequestedUnpublish("");
					mypage.setScheduledPublish("");
					mypage.setScheduledUnpublish("");
					mypage.setPublished(timestamp);
					mypage.setPublishedBy(username);
					mypage.setUnpublished("");
					mypage.setUnpublishedBy("");
				} else {
					mypage.setRequestedPublish("");
					mypage.setRequestedUnpublish("");
					mypage.setScheduledPublish(myrequest.getParameter("scheduled_publish"));
					mypage.setScheduledUnpublish("");
					mypage.setPublished("");
					mypage.setPublishedBy("");
					mypage.setUnpublished("");
					mypage.setUnpublishedBy("");
				}
			}
		} else {
			if (filepost.parameterExists("scheduled_publish")) {
				if (filepost.getParameter("scheduled_publish").compareTo(timestamp) <= 0) {
					mypage.setRequestedPublish("");
					mypage.setRequestedUnpublish("");
					mypage.setScheduledPublish("");
					mypage.setScheduledUnpublish("");
					mypage.setPublished("");
					mypage.setPublishedBy("");
					mypage.setUnpublished("");
					mypage.setUnpublishedBy("");
				} else {
					mypage.setRequestedPublish(filepost.getParameter("scheduled_publish"));
					mypage.setRequestedUnpublish("");
					mypage.setScheduledPublish("");
					mypage.setScheduledUnpublish("");
					mypage.setPublished("");
					mypage.setPublishedBy("");
					mypage.setUnpublished("");
					mypage.setUnpublishedBy("");
				}
			} else {
				if (myrequest.getParameter("scheduled_publish").compareTo(timestamp) <= 0) {
					mypage.setRequestedPublish("");
					mypage.setRequestedUnpublish("");
					mypage.setScheduledPublish("");
					mypage.setScheduledUnpublish("");
					mypage.setPublished("");
					mypage.setPublishedBy("");
					mypage.setUnpublished("");
					mypage.setUnpublishedBy("");
				} else {
					mypage.setRequestedPublish(myrequest.getParameter("scheduled_publish"));
					mypage.setRequestedUnpublish("");
					mypage.setScheduledPublish("");
					mypage.setScheduledUnpublish("");
					mypage.setPublished("");
					mypage.setPublishedBy("");
					mypage.setUnpublished("");
					mypage.setUnpublishedBy("");
				}
			}
		}
		mypage.create(db, myconfig, "content", "id");
		if ((! mypage.getScheduledPublish().equals("")) && ((mypage.getScheduledPublish().compareTo(myconfig.get(db, "scheduled_next")) < 0) || (myconfig.get(db, "scheduled_next").equals("")))) {
			myconfig.set(db, "scheduled_next", mypage.getScheduledPublish());
		}
		if ((mypage.getPublisher()) && ((! myrequest.parameterExists("publish")) || (myrequest.getParameter("publish").equals("yes"))) && ((! filepost.parameterExists("publish")) || (filepost.getParameter("publish").equals("yes"))) && (((filepost.parameterExists("scheduled_publish")) && (filepost.getParameter("scheduled_publish").compareTo(timestamp) <= 0)) || ((! filepost.parameterExists("scheduled_publish")) && (myrequest.getParameter("scheduled_publish").compareTo(timestamp) <= 0))) && (! mypage.getId().equals("")) && (! mypage.getId().equals("0"))) {
			mypage.create(db, myconfig, "content_public", "id");

			// send trackback to posted trackback urls if any
			String[] trackbackURLs;
			if (filepost.parameterExists("trackback")) {
				trackbackURLs = filepost.getParameter("trackback").split("\r\n");
			} else {
				trackbackURLs = myrequest.getParameter("trackback").split("\r\n");
			}
			for (int i=0; i<trackbackURLs.length; i++) {
				String trackbackURL = trackbackURLs[i];
				if (trackbackURL.matches("^https?://.*$")) {
					try {
						// Construct data
						String data = "";
						if (filepost.parameterExists("title")) {
							data += URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(filepost.getParameter("title"), "UTF-8");
						} else {
							data += URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(myrequest.getParameter("title"), "UTF-8");
						}
						if (filepost.parameterExists("summary")) {
							data += "&" + URLEncoder.encode("excerpt", "UTF-8") + "=" + URLEncoder.encode(filepost.getParameter("summary"), "UTF-8");
						} else {
							data += "&" + URLEncoder.encode("excerpt", "UTF-8") + "=" + URLEncoder.encode(myrequest.getParameter("summary"), "UTF-8");
						}
						if (filepost.parameterExists("blog_name")) {
							data += "&" + URLEncoder.encode("blog_name", "UTF-8") + "=" + URLEncoder.encode(filepost.getParameter("blog_name"), "UTF-8");
						} else {
							data += "&" + URLEncoder.encode("blog_name", "UTF-8") + "=" + URLEncoder.encode(myrequest.getParameter("blog_name"), "UTF-8");
						}
						if (filepost.parameterExists("url")) {
							data += "&" + URLEncoder.encode("url", "UTF-8") + "=" + URLEncoder.encode("http://" + myrequest.getServerName() + filepost.getParameter("url") + mypage.getId(), "UTF-8");
						} else {
							data += "&" + URLEncoder.encode("url", "UTF-8") + "=" + URLEncoder.encode("http://" + myrequest.getServerName() + myrequest.getParameter("url") + mypage.getId(), "UTF-8");
						}
			
						// Send data
						URL url = new URL(trackbackURL);
						URLConnection conn = url.openConnection();
						conn.setDoOutput(true);
						OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
						wr.write(data);
						wr.flush();
						
						// Get the response
						BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						String line;
						while ((line = rd.readLine()) != null) {
						    // Process line...
						}
						wr.close();
						rd.close();
					} catch (Exception e) {
						System.out.println("HardCore:webadmin:module:blog:error:"+e);
					}
				}
			}
		}

		if ((! myrequest.getParameter("ready_to_publish").equals("")) || (! filepost.getParameter("ready_to_publish").equals(""))) {
			String subject = "";
			String body = "";
			Page notification = new Page(mytext);
			if (! filepost.getParameter("email_template").equals("")) {
				notification.public_read(servletcontext, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, filepost.getParameter("email_template"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("template"), myconfig.get(db, "default_template"), mysession.get("stylesheet"), myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				subject = "" + notification.getTitle() + " " + mypage.getTitle();
				body = "" + notification.getContent();
			} else if (! myrequest.getParameter("email_template").equals("")) {
				notification.public_read(servletcontext, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("email_template"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("template"), myconfig.get(db, "default_template"), mysession.get("stylesheet"), myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				subject = "" + notification.getTitle() + " " + mypage.getTitle();
				body = "" + notification.getContent();
			} else if (! myconfig.get(db, "default_publish_ready").equals("")) {
				notification.public_read(servletcontext, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myconfig.get(db, "default_publish_ready"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("template"), myconfig.get(db, "default_template"), mysession.get("stylesheet"), myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
				subject = "" + notification.getTitle() + " " + mypage.getTitle();
				body = "" + notification.getContent();
			}
			if (body.equals("")) {
				subject = "Website content posted: " + mypage.getTitle();
				body = "<p>The user '@@@username@@@' has posted the content item '@@@title@@@'.</p>" + "\r\n" + "\r\n";
				body = body + "<p>Preview:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@preview@@@\">@@@preview@@@</a>" + "\r\n";
				body = body + "<p>Update/publish:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@update@@@\">@@@update@@@</a>" + "\r\n";
				body = body + "<p>Delete:<br>" + "\r\n" + "&nbsp;&nbsp;&nbsp;<a href=\"@@@delete@@@\">@@@delete@@@</a>" + "\r\n";
			}
			body = body.replaceAll("@@@id@@@", mypage.getId());
			body = body.replaceAll("@@@class@@@", mypage.getContentClass());
			body = body.replaceAll("@@@title@@@", mypage.getTitle());
			body = body.replaceAll("@@@username@@@", username);
			body = body.replaceAll("@@@preview@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + mypage.getContentClass() + ".jsp?id=" + mypage.getId() + "&mode=preview&version=&");
			body = body.replaceAll("@@@view@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + mytext.display("adminpath") + "/content/view.jsp?id=" + mypage.getId());
			body = body.replaceAll("@@@update@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + mytext.display("adminpath") + "/content/update.jsp?id=" + mypage.getId());
			body = body.replaceAll("@@@delete@@@", myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/" + mytext.display("adminpath") + "/content/delete.jsp?id=" + mypage.getId());

			String admin_email = Common.join(", ", mypage.publishersEmails(mysession, myconfig, db));
			if (admin_email.equals("")) {
				admin_email = myconfig.get(db, "superadmin_email");
			}
			String from = "";
			if (! mysession.get("email").equals("")) {
				from = mysession.get("email");
			} else if (! mysession.get("username").equals("")) {
				from = mysession.get("username") + "@" + myrequest.getServerName();
			} else if (! myrequest.getRemoteHost().equals("")) {
				from = myrequest.getRemoteHost() + "@" + myrequest.getServerName();
			} else if (! myrequest.getRemoteAddr().equals("")) {
				from = myrequest.getRemoteAddr() + "@" + myrequest.getServerName();
			} else {
				from = "nobody" + "@" + myrequest.getServerName();
			}
			HashMap requestForm = Email.getForm(myrequest, filepost);
			Email.send_email(mytext, requestForm, subject, body, "", from, admin_email, "", "", "", "", myrequest.getServerName(), servletcontext, mysession, myrequest, myresponse, myconfig, db);
		}
	}

	mysession.set("stylesheet", save_stylesheet);
	mysession.set("template", save_template);
	mysession.set("version", save_version);
	mysession.set("mode", save_mode);
}

if (! filepost.getParameter("redirect").equals("")) {
	myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + filepost.getParameter("redirect"));
} else if (! myrequest.getParameter("redirect").equals("")) {
	myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + myrequest.getParameter("redirect"));
}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>