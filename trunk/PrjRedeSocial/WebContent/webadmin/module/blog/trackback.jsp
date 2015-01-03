<%@ include file="../../../config.jsp" %>
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

if (License.valid(db, myconfig, "community")) {
	if ((! myrequest.getParameter("title").equals("")) && (! myrequest.getParameter("excerpt").equals("")) && (! myrequest.getParameter("blog_name").equals("")) && (myrequest.getParameter("url").matches("^https?://.+$"))) {
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		mysession.set("stylesheet", "");
		mysession.set("template", "");
		mysession.set("version", "");
		mysession.set("mode", "");

		Page mypage = new Page(mytext);
		String save_default_version = myconfig.get(db, "default_version");
		myconfig.setTemp("default_version", "");
		mypage.public_read(servletcontext, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("id"), "", "", mysession.get("template"), myconfig.get(db, "default_template"), mysession.get("stylesheet"), myconfig.get(db, "default_stylesheet"), mysession.get("currency"), myconfig.get(db, "default_currency"), myconfig.get(db, "default_shopcartsummary_page"), myconfig.get(db, "default_shopcartsummary_entry"));
		myconfig.setTemp("default_version", save_default_version);

		mypage.setServerFilename("");
		String title = myrequest.getParameter("title");
		title = title.replaceAll("<", "&lt;");
		title = title.replaceAll(">", "&gt;");
		String excerpt = myrequest.getParameter("excerpt");
		excerpt = excerpt.replaceAll("<", "&lt;");
		excerpt = excerpt.replaceAll(">", "&gt;");
		excerpt = excerpt.replaceAll("\r\n", "<br>");
		excerpt = excerpt.replaceAll("\r", "<br>");
		excerpt = excerpt.replaceAll("\n", "<br>");
		String blog_name = myrequest.getParameter("blog_name");
		blog_name = blog_name.replaceAll("<", "&lt;");
		blog_name = blog_name.replaceAll(">", "&gt;");
		String url = myrequest.getParameter("url");
		url = url.replaceAll("<", "&lt;");
		url = url.replaceAll(">", "&gt;");
		String content = mypage.getContent();
		content = content.replaceAll("@@@title@@@",title);
		content = content.replaceAll("@@@content@@@", excerpt);
		content = content.replaceAll("@@@blog_name@@@", blog_name);
		content = content.replaceAll("@@@blog_url@@@", url);
		content = content.replaceAll("@@@created@@@", timestamp);
		content = content.replaceAll("@@@updated@@@", timestamp);
		content = content.replaceAll("@@@published@@@", timestamp);
		mypage.setTitle(title);
		mypage.setContent(content);
		mypage.setSummary("");
		mypage.setPageTop(myrequest.getParameter("top"));
		mypage.setPageUp(myrequest.getParameter("up"));
		mypage.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig);

		if (mypage.getCreator()) {
			String username = mysession.get("username");
			mypage.setCreated(timestamp);
			mypage.setCreatedBy(username);
			mypage.setUpdated(timestamp);
			mypage.setUpdatedBy(username);
			if ((! myrequest.getParameter("publish").equals("")) && (mypage.getPublisher())) {
				if ((myrequest.getParameter("scheduled_publish").equals("")) || (myrequest.getParameter("scheduled_publish").compareTo(timestamp) <= 0)) {
					mypage.setScheduledPublish("");
					mypage.setPublished(timestamp);
					mypage.setPublishedBy(username);
				} else {
					mypage.setScheduledPublish(myrequest.getParameter("scheduled_publish"));
					mypage.setPublished("");
					mypage.setPublishedBy("");
				}
			} else {
				mypage.setScheduledPublish("");
				mypage.setPublished("");
				mypage.setPublishedBy("");
			}
			mypage.create(db, myconfig, "content", "id");
			if ((mypage.getPublisher()) && (! mypage.getId().equals("")) && (! mypage.getId().equals("0"))) {
				mypage.create(db, myconfig, "content_public", "id");
			}

			if (false) {
				String subject = "";
				String body = "";
				Page notification = new Page(mytext);
				if (! myrequest.getParameter("email_template").equals("")) {
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
				HashMap requestForm = Email.getForm(myrequest);
				Email.send_email(mytext, requestForm, subject, body, "", from, admin_email, "", "", "", "", myrequest.getServerName(), servletcontext, mysession, myrequest, myresponse, myconfig, db);
			}
		}
	}
}

if (! myrequest.getParameter("redirect").equals("")) {
	myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + myrequest.getParameter("redirect"));
} else {
	myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + "/page.jsp?id=" + myrequest.getParameter("top") + "#" + myrequest.getParameter("up"));
}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>