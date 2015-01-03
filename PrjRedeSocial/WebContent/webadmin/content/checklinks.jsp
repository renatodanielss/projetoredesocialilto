<%@ include file="../config.jsp" %><%@ page import="java.util.regex.*" %><%@ page import="HardCore.RequireUser" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.Fileupload" %><%@ page import="HardCore.http" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.MenuContent" %><%@ page import="HardCore.html" %><%

//RequireUser.Administrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));

boolean DEBUG = false;

String error = "";
String[] ids = myrequest.getParameters("id");
boolean check_orphans = true;
if (myrequest.getParameter("orphans").equals("no")) {
	check_orphans = false;
}
HashMap checked = new HashMap();
Fileupload filepost = new Fileupload(myrequest, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath"), myconfig.get(db, "URLuploadpath"), -1);
if (filepost.parameterExists("content")) {
	if (! myconfig.get(db, "checklinks_domains").equals("-")) {
		String content = filepost.getParameter("content");
		content = content.replaceAll("[\r\n\t]", " ");

		Matcher m = Pattern.compile("((href=\"(/page\\.jsp\\?id=)\")|(href=(/page\\.jsp\\?id= )))").matcher(content);
		if (m.find()) {
			String url = "";
			if (m.group(3) != null) {
				url = m.group(3);
			} else if (m.group(5) != null) {
				url = m.group(5);
			}
			checked.put(url, url);
			error += "/page.jsp?id=" + " - " + mytext.display("content.primarycontent.checklinks.blank") + "\r\n";
		}
		m = Pattern.compile("((href=\"( *)\")|(href=( )))").matcher(content);
		if (m.find()) {
			String url = "";
			if (m.group(3) != null) {
				url = m.group(3);
			} else if (m.group(5) != null) {
				url = m.group(5);
			}
			checked.put(url, url);
			error += " - " + mytext.display("content.primarycontent.checklinks.blank") + "\r\n";
		}

		m = Pattern.compile("((href=\"(https?://[^\"]*)\")|(href=(https?://[^\" >]*)))").matcher(content);
		while (m.find()) {
			String url = "";
			if (m.group(3) != null) {
				url = m.group(3);
			} else if (m.group(5) != null) {
				url = m.group(5);
			}
			if (checked.get(url) == null) {
				checked.put(url, url);
				if ((myconfig.get(db, "checklinks_domains").equals("")) || (Pattern.compile("(^|\\.)" + myconfig.get(db, "checklinks_domains") + "(/.*|$)").matcher(url).find())) {
if (DEBUG) System.out.println("DEBUG:checklinks:"+url+":::"+(new java.util.Date()));
					String urlcheck = http.check(url);
if (DEBUG) System.out.println("DEBUG:checklinks:"+url+":::"+(new java.util.Date()));
					if (urlcheck.equals("")) {
						error += url + " - " + mytext.display("content.primarycontent.checklinks.unreachable") + "\r\n";
					} else if (! urlcheck.startsWith("200 ")) { 
						error += url + " - " + urlcheck + "\r\n";
					} else {
						// OK
						// error += url + " - " + urlcheck + "\r\n";
					}
				}
			}
		}

		m = Pattern.compile("((href=\"(/page\\.jsp\\?id=[0-9]+[^\"]*)\")|(href=(/page\\.jsp\\?id=[0-9]+[^\" >]*)))").matcher(content);
		while (m.find()) {
			String url = "";
			if (m.group(3) != null) {
				url = m.group(3);
			} else if (m.group(5) != null) {
				url = m.group(5);
			}
			if (checked.get(url) == null) {
				checked.put(url, url);
				Matcher m2 = Pattern.compile("id=([0-9]+)([^0-9]|$)").matcher(url);
				if (m2.find()) {
					String myid = "";
					if (m2.group(1) != null) {
						myid = "" + m2.group(1);
					}
					Content mycontent = new Content(mytext);
					mycontent.read(db, myconfig, myid, "content", "id");
					if ((mycontent.getId().equals("")) || (mycontent.getId().equals("0"))) {
						error += url + " - " + mytext.display("content.primarycontent.checklinks.nonexisting") + "\r\n";
					} else if (mycontent.display_status().indexOf(mytext.display("content.status.new")) > 0) {
						error += url + " - " + mytext.display("content.primarycontent.checklinks.unpublished") + " - " + mycontent.getTitle() + "\r\n";
					} else if (mycontent.display_status().indexOf(mytext.display("content.status.unpublished")) > 0) {
						error += url + " - " + mytext.display("content.primarycontent.checklinks.unpublished") + " - " + mycontent.getTitle() + "\r\n";
					} else if (mycontent.display_status().indexOf(mytext.display("content.status.expired")) > 0) {
						error += url + " - " + mytext.display("content.primarycontent.checklinks.expired") + " - " + mycontent.getTitle() + "\r\n";
					}
					mycontent = null;
					System.gc();
				}
			}
		}

		m = Pattern.compile("((href=\"([^\"]*)\")|(href=([^\" >]*)))").matcher(content);
		while (m.find()) {
			String url = "";
			if (m.group(3) != null) {
				url = m.group(3);
			} else if (m.group(5) != null) {
				url = m.group(5);
			}
			if ((url.startsWith("mailto:")) || (url.startsWith("javascript:")) || (url.startsWith("#")) || (url.startsWith("?"))) {
				// ignore
			} else if (checked.get(url) == null) {
				checked.put(url, url);
				String path = "";
				if (url.startsWith("/")) {
					path = "";
				} else {
					path = myconfig.get(db, "URLrootpath") + filepost.getParameter("server_filename");
					if (path.lastIndexOf("/") >= 0) {
						path = path.substring(0, path.lastIndexOf("/")+1);
					}
				}
if (DEBUG) System.out.println("DEBUG:checklinks:"+"http://" + myrequest.getServerName() + myrequest.getServerPort() + path + url+":::"+(new java.util.Date()));
				String urlcheck = http.check("http://" + myrequest.getServerName() + myrequest.getServerPort() + path + url);
if (DEBUG) System.out.println("DEBUG:checklinks:"+"http://" + myrequest.getServerName() + myrequest.getServerPort() + path + url+":::"+(new java.util.Date()));
				if (urlcheck.equals("")) {
					error += url + " (" + "http://" + myrequest.getServerName() + myrequest.getServerPort() + path + url + ")" + " - " + mytext.display("content.primarycontent.checklinks.unreachable") + "\r\n";
				} else if (! urlcheck.startsWith("200 ")) { 
					error += url + " (" + "http://" + myrequest.getServerName() + myrequest.getServerPort() + path + url + ")" + " - " + urlcheck + "\r\n";
				} else {
					// OK
					// error += url + " (" + "http://" + myrequest.getServerName() + myrequest.getServerPort() + path + url + ")" + " - " + urlcheck + "\r\n";
					Content mycontent = new Content(mytext);
					mycontent.read(db, myconfig, url.replaceAll("^" + myconfig.get(db, "URLrootpath"), "").replaceAll("^/", ""), "content", "id");
					if ((mycontent.getId().equals("")) || (mycontent.getId().equals("0"))) {
						// ignore
					} else if (mycontent.display_status().indexOf(mytext.display("content.status.new")) > 0) {
						error += url + " - " + mytext.display("content.primarycontent.checklinks.unpublished") + " - " + mycontent.getTitle() + "\r\n";
					} else if (mycontent.display_status().indexOf(mytext.display("content.status.unpublished")) > 0) {
						error += url + " - " + mytext.display("content.primarycontent.checklinks.unpublished") + " - " + mycontent.getTitle() + "\r\n";
					} else if (mycontent.display_status().indexOf(mytext.display("content.status.expired")) > 0) {
						error += url + " - " + mytext.display("content.primarycontent.checklinks.expired") + " - " + mycontent.getTitle() + "\r\n";
					}
					mycontent = null;
					System.gc();
				}
			}
		}
	}

	if (! error.equals("")) {
		%><%= mytext.display("content.primarycontent.checklinks.error") + error + mytext.display("content.primarycontent.checklinks.confirm") %><%
	} else {
		%><%= "OK" %><%
	}
} else if (myrequest.parameterExists("id")) {
	if (myrequest.getParameter("id").equals("*")) {
		ids = Common.csv(db, "content", "id", "title", "where contentclass<>'image' and contentclass<>'file' and contentclass<>'link' and contentclass<>'script' and contentclass<>'stylesheet'").split(",");
	}
	String errors = "";
	for (int i=0; i < ids.length; i++) {
		String id = ids[i];
		error = "";
		UCmaintainContent maintainContent = new UCmaintainContent(mytext);
		Page mypage = maintainContent.getPageById(id, servletcontext, mysession, myrequest, myresponse, myconfig, db);
		String orphan = "";
		HashMap dependents = new HashMap();
		if (check_orphans) {
			dependents = mypage.dependents(myconfig, db);
			if (dependents.size() == 0) {
				orphan = mytext.display("content.primarycontent.checklinks.orphan");
			}
		}

		String content = mypage.getContent();
		content = content.replaceAll("[\r\n\t]", " ");

		Matcher m = Pattern.compile("((href=\"(/page\\.jsp\\?id=)\")|(href=(/page\\.jsp\\?id= )))").matcher(content);
		if (m.find()) {
			String url = "";
			if (m.group(3) != null) {
				url = m.group(3);
			} else if (m.group(5) != null) {
				url = m.group(5);
			}
			checked.put(url, url);
			error += "/page.jsp?id=" + " - " + mytext.display("content.primarycontent.checklinks.blank") + "<br>" + "\r\n";
		}
		m = Pattern.compile("((href=\"( *)\")|(href=( )))").matcher(content);
		if (m.find()) {
			String url = "";
			if (m.group(3) != null) {
				url = m.group(3);
			} else if (m.group(5) != null) {
				url = m.group(5);
			}
			checked.put(url, url);
			error += " - " + mytext.display("content.primarycontent.checklinks.blank") + "<br>" + "\r\n";
		}

		m = Pattern.compile("((href=\"(https?://[^\"]*)\")|(href=(https?://[^\" >]*)))").matcher(content);
		while (m.find()) {
			String url = "";
			if (m.group(3) != null) {
				url = m.group(3);
			} else if (m.group(5) != null) {
				url = m.group(5);
			}
			if (checked.get(url) == null) {
				checked.put(url, url);
				if ((myconfig.get(db, "checklinks_domains").equals("")) || (Pattern.compile("(^|\\.)" + myconfig.get(db, "checklinks_domains") + "(/.*|$)").matcher(url).find())) {
if (DEBUG) System.out.println("DEBUG:checklinks:"+url+":::"+(new java.util.Date()));
					String urlcheck = http.check(url);
if (DEBUG) System.out.println("DEBUG:checklinks:"+url+":::"+(new java.util.Date()));
					if (urlcheck.equals("")) {
						error += "- " + url + " - " + mytext.display("content.primarycontent.checklinks.unreachable") + "<br>" + "\r\n";
					} else if (! urlcheck.startsWith("200 ")) { 
						error += "- " + url + " - " + urlcheck + "<br>" + "\r\n";
					} else {
						// OK
						// error += "- " + url + " - " + urlcheck + "<br>" + "\r\n";
					}
				}
			}
		}

		m = Pattern.compile("((href=\"(/page\\.jsp\\?id=[0-9]+[^\"]*)\")|(href=(/page\\.jsp\\?id=[0-9]+[^\" >]*)))").matcher(content);
		while (m.find()) {
			String url = "";
			if (m.group(3) != null) {
				url = m.group(3);
			} else if (m.group(5) != null) {
				url = m.group(5);
			}
			if (checked.get(url) == null) {
				checked.put(url, url);
				Matcher m2 = Pattern.compile("id=([0-9]+)([^0-9]|$)").matcher(url);
				if (m2.find()) {
					String myid = "";
					if (m2.group(1) != null) {
						myid = "" + m2.group(1);
					}
					Content mycontent = new Content(mytext);
					mycontent.read(db, myconfig, myid, "content", "id");
					if ((mycontent.getId().equals("")) || (mycontent.getId().equals("0"))) {
						error += url + " - " + mytext.display("content.primarycontent.checklinks.nonexisting") + "<br>" + "\r\n";
					} else if (mycontent.display_status().indexOf(mytext.display("content.status.new")) > 0) {
						error += url + " - " + mytext.display("content.primarycontent.checklinks.unpublished") + " - " + mycontent.getTitle() + "<br>" + "\r\n";
					} else if (mycontent.display_status().indexOf(mytext.display("content.status.unpublished")) > 0) {
						error += url + " - " + mytext.display("content.primarycontent.checklinks.unpublished") + " - " + mycontent.getTitle() + "<br>" + "\r\n";
					} else if (mycontent.display_status().indexOf(mytext.display("content.status.expired")) > 0) {
						error += url + " - " + mytext.display("content.primarycontent.checklinks.expired") + " - " + mycontent.getTitle() + "<br>" + "\r\n";
					}
					mycontent = null;
					System.gc();
				}
			}
		}

		m = Pattern.compile("((href=\"([^\"]*)\")|(href=([^\" >]*)))").matcher(content);
		while (m.find()) {
			String url = "";
			if (m.group(3) != null) {
				url = m.group(3);
			} else if (m.group(5) != null) {
				url = m.group(5);
			}
			if ((url.startsWith("mailto:")) || (url.startsWith("javascript:")) || (url.startsWith("#")) || (url.startsWith("?"))) {
				// ignore
			} else if (checked.get(url) == null) {
				checked.put(url, url);
				String path = "";
				if (url.startsWith("/")) {
					path = "";
				} else {
					path = myconfig.get(db, "URLrootpath") + filepost.getParameter("server_filename");
					if (path.lastIndexOf("/") >= 0) {
						path = path.substring(0, path.lastIndexOf("/")+1);
					}
				}
if (DEBUG) System.out.println("DEBUG:checklinks:"+"http://" + myrequest.getServerName() + myrequest.getServerPort() + path + url+":::"+(new java.util.Date()));
				String urlcheck = http.check("http://" + myrequest.getServerName() + myrequest.getServerPort() + path + url);
if (DEBUG) System.out.println("DEBUG:checklinks:"+"http://" + myrequest.getServerName() + myrequest.getServerPort() + path + url+":::"+(new java.util.Date()));
				if (urlcheck.equals("")) {
					error += url + " (" + "http://" + myrequest.getServerName() + myrequest.getServerPort() + path + url + ")" + " - " + mytext.display("content.primarycontent.checklinks.unreachable") + "<br>" + "\r\n";
				} else if (! urlcheck.startsWith("200 ")) { 
					error += url + " (" + "http://" + myrequest.getServerName() + myrequest.getServerPort() + path + url + ")" + " - " + urlcheck + "<br>" + "\r\n";
				} else {
					// OK
					// error += url + " (" + "http://" + myrequest.getServerName() + myrequest.getServerPort() + path + url + ")" + " - " + urlcheck + "<br>" + "\r\n";
					Content mycontent = new Content(mytext);
					mycontent.read(db, myconfig, url.replaceAll("^" + myconfig.get(db, "URLrootpath"), "").replaceAll("^/", ""), "content", "id");
					if ((mycontent.getId().equals("")) || (mycontent.getId().equals("0"))) {
						// ignore
					} else if (mycontent.display_status().indexOf(mytext.display("content.status.new")) > 0) {
						error += url + " - " + mytext.display("content.primarycontent.checklinks.unpublished") + " - " + mycontent.getTitle() + "<br>" + "\r\n";
					} else if (mycontent.display_status().indexOf(mytext.display("content.status.unpublished")) > 0) {
						error += url + " - " + mytext.display("content.primarycontent.checklinks.unpublished") + " - " + mycontent.getTitle() + "<br>" + "\r\n";
					} else if (mycontent.display_status().indexOf(mytext.display("content.status.expired")) > 0) {
						error += url + " - " + mytext.display("content.primarycontent.checklinks.expired") + " - " + mycontent.getTitle() + "<br>" + "\r\n";
					}
					mycontent = null;
					System.gc();
				}
			}
		}

		if ((error.equals("")) && ((dependents.size() > 0) || (! check_orphans))) {
			errors += "<p>" + mypage.getTitle() + " (" + mypage.getId() + ") " + mytext.display("content.primarycontent.checklinks.ok") + "</p>" + "\r\n";
		} else if ((error.equals("")) && ((dependents.size() == 0) && (check_orphans))) {
			errors += "<p>" + mypage.getTitle() + " (" + mypage.getId() + ") " + mytext.display("content.primarycontent.checklinks.orphan") + "</p>" + "\r\n";
		} else if ((dependents.size() == 0) && (check_orphans)) {
			errors += "<p>" + mypage.getTitle() + " (" + mypage.getId() + ") " +  mytext.display("content.primarycontent.checklinks.orphan") + " " + mytext.display("content.primarycontent.checklinks.errors") + "<br>" + "\r\n" + error + "</p>" + "\r\n";
		} else {
			errors += "<p>" + mypage.getTitle() + " (" + mypage.getId() + ") " + mytext.display("content.primarycontent.checklinks.errors") + "<br>" + "\r\n" + error + "</p>" + "\r\n";
		}
	}
	%><%@ include file="checklinks.jsp.html" %><%
} else {
	%><%= "OK" %><%
}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>