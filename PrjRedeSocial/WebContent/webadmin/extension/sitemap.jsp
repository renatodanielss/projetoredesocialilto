<%@ include file="../../webadmin.jsp" %><%@ page import="HardCore.Common" %><%@ page import="HardCore.Databases" %><%@ page import="HardCore.Data" %><%@ page import="java.io.File" %><%@ page import="java.util.regex.*" %><%

String menutop = "";
String menulevels = "9";
String changefreq = "";
String id = "";
String databases = "";
String dataurl = "/data.jsp?";
String[] params = mysession.get("extension").split(":");
if ((myrequest.getAttribute("extension") != null) && (! myrequest.getAttribute("extension").equals(""))) {
	params = (""+myrequest.getAttribute("extension")).split(":");
} else {
	params = mysession.get("extension").split(":");
}
for (int i=0; i<params.length; i++) {
	String param = params[i];
	Matcher topMatches = Pattern.compile("^top=([0-9]*)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher levelsMatches = Pattern.compile("^levels=([0-9]*)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher idMatches = Pattern.compile("^id=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher changefreqMatches = Pattern.compile("^changefreq=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher databaseMatches = Pattern.compile("^database=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	if (topMatches.find()) {
		if (topMatches.group(1).equals("0")) {
			menutop = myconfig.get(db, "default_page");
		} else {
			menutop = cms.GetContent("" + topMatches.group(1), "id");
		}
	} else if (levelsMatches.find()) {
		menulevels = "" + levelsMatches.group(1);
	} else if (idMatches.find()) {
		id = "" + idMatches.group(1);
	} else if (changefreqMatches.find()) {
		changefreq = " <changefreq>" + changefreqMatches.group(1) + "</changefreq>" + "\r\n";
	} else if (databaseMatches.find()) {
		if (! databases.equals("")) databases += ",";
		databases += "" + databaseMatches.group(1);
	}
}

String output = "";
HashMap loopdetection = new HashMap();

String save_superadmin = myconfig.get(db, "superadmin");
myconfig.setTemp("superadmin", mysession.get("username"));
if (id.equals("")) {
	id = myrequest.getParameter("id");
}
if (id.equals("")) {
	id = myconfig.get(db, "default_page");
}
id = cms.GetContent(id, "id");

String published = cms.GetContent(menutop, "published");
if (published.equals("")) {
	published = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
}
published = published.replaceAll(" ", "T") + (new SimpleDateFormat("Z").format(new java.util.Date())).substring(0,3) + ":" + (new SimpleDateFormat("Z").format(new java.util.Date())).substring(3);

if ((! menutop.equals("")) && (! menulevels.equals(""))) {
	output = buildMenuTopDown(servletcontext, id, menutop, menulevels, changefreq, loopdetection, mysession, db, myconfig, cms, servername);
} else {
	output = buildMenuBottomUp(servletcontext, id, id, output, menutop, menulevels, changefreq, loopdetection, mysession, db, myconfig, cms, servername);
}
String[] mydatabases = databases.split(",");
for (int i=0; i<mydatabases.length; i++) {
	String mydatabase = mydatabases[i];
	if (! mydatabase.equals("")) {
		output += buildMenuDatabase(servletcontext, mydatabase, dataurl, menulevels, changefreq, mysession, db, myconfig, cms, servername);
	}
}
myconfig.setTemp("superadmin", save_superadmin);
save_superadmin = "";

%><%!

public String buildMenuDatabase(ServletContext servletcontext, String databasename, String dataurl, String menulevels, String changefreq, Session mysession, DB db, Configuration myconfig, Cms cms, String servername) throws Exception {
	String output = "";
	Databases database = new Databases();
	database.readTitle(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, databasename);
	Data data = new Data();
	if (database.getUser()) {
		String SQL = "select id from data" + database.getId() + " order by id";
		data.records(db, SQL);
		while (data.records(db, "")) {
			output += "<url>" + "\r\n" + " <loc>http://" + servername + dataurl + "database=" + databasename + "&amp;id=" + data.getId() + "</loc>" + "\r\n" + " <priority>0." + menulevels + "</priority>" + "\r\n" + changefreq + "</url>" + "\r\n";
		}
	}
	return output;
}

public String buildMenuTopDown(ServletContext servletcontext, String selectedid, String id, String menulevels, String changefreq, HashMap loopdetection, Session mysession, DB db, Configuration config, Cms cms, String servername) throws Exception {
	String output = "";
	loopdetection.put(id, id);
	Content content = new Content();
	content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, config, "select id,title,page_previous,page_next,published,server_filename from content_public where page_up='" + id + "' order by title,id");
	HashMap pageprevious = new HashMap();
	HashMap pagenext = new HashMap();
	HashMap pagetitle = new HashMap();
	HashMap pagepublished = new HashMap();
	HashMap pageserverfilename = new HashMap();
	while (content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, config, "")) {
		pagetitle.put("" + content.getId(), "" + content.getTitle());
		pagepublished.put("" + content.getId(), "" + content.getPublished());
		pageserverfilename.put("" + content.getId(), "" + content.getServerFilename());
		if ((content.getPagePrevious() != null) && (! content.getPagePrevious().equals("")) && (! content.getPagePrevious().equals("0"))) {
			pageprevious.put("" + content.getId(), "" + content.getPagePrevious());
			pagenext.put("" + content.getPagePrevious(), "" + content.getId());
		}
		if ((content.getPageNext() != null) && (! content.getPageNext().equals("")) && (content.getPageNext().equals("0"))) {
			pagenext.put("" + content.getId(), "" + content.getPageNext());
			pageprevious.put("" + content.getPageNext(), "" + content.getId());
		}
	}
	// PAGE SEQUENCES
	String[] pagetitles = (String[])pagetitle.keySet().toArray(new String[0]);
	for (int i=0; i<pagetitles.length; i++) {
		id = "" + pagetitles[i];
		String title = "";
		if (pagetitle.get(id) != null) title = "" + pagetitle.get(id);
		if ((! title.equals("")) && (pageprevious.get(id) == null)) {
			while ((! id.equals("")) && (! id.equals("0")) && (! title.equals(""))) {
				String published = ("" + pagepublished.get(id));
				published = published.replaceAll(" ", "T") + (new SimpleDateFormat("Z").format(new java.util.Date())).substring(0,3) + ":" + (new SimpleDateFormat("Z").format(new java.util.Date())).substring(3);
				String server_filename = "" + pageserverfilename.get(id);
				if (! server_filename.equals("")) {
					if (new java.io.File(Common.getRealPath(servletcontext, config.get(db, "URLrootpath") + server_filename + "/index.jsp")).exists()) {
						output = output + "<url>" + "\r\n" + " <loc>http://" + servername + config.get(db, "URLrootpath") + server_filename + "/" + "</loc>" + "\r\n" + " <lastmod>" + published + "</lastmod>" + "\r\n" + " <priority>0." + menulevels + "</priority>" + "\r\n" + changefreq + "</url>" + "\r\n";
					} else {
						output = output + "<url>" + "\r\n" + " <loc>http://" + servername + config.get(db, "URLrootpath") + server_filename + "</loc>" + "\r\n" + " <lastmod>" + published + "</lastmod>" + "\r\n" + " <priority>0." + menulevels + "</priority>" + "\r\n" + changefreq + "</url>" + "\r\n";
					}
				} else if (config.get(db, "url_rewriting").equals("yes")) {
					output = output + "<url>" + "\r\n" + " <loc>http://" + servername + "/page.jsp/id=" + id + "</loc>" + "\r\n" + " <lastmod>" + published + "</lastmod>" + "\r\n" + " <priority>0." + menulevels + "</priority>" + "\r\n" + changefreq + "</url>" + "\r\n";
				} else {
					output = output + "<url>" + "\r\n" + " <loc>http://" + servername + "/page.jsp?id=" + id + "</loc>" + "\r\n" + " <lastmod>" + published + "</lastmod>" + "\r\n" + " <priority>0." + menulevels + "</priority>" + "\r\n" + changefreq + "</url>" + "\r\n";
				}
				if ((loopdetection.get(id) == null) && (Integer.parseInt("0" + menulevels) > 1)) {
					output = output + buildMenuTopDown(servletcontext, selectedid, id, "" + (Integer.parseInt("0" + menulevels)-1), changefreq, loopdetection, mysession, db, config, cms, servername);
				}
				pagetitle.put(id, "");
				if (pagenext.get(id) != null) {
					id = "" + pagenext.get(id);
				} else {
					id = "";
				}
				title = "" + pagetitle.get(id);
			}
		}
	}
	return output;
}



public String buildMenuBottomUp(ServletContext servletcontext, String selectedid, String id, String output, String menutop, String menulevels, String changefreq, HashMap loopdetection, Session mysession, DB db, Configuration config, Cms cms, String servername) throws Exception {
	// CURRENT PAGE
	loopdetection.put(id, id);
	String title = cms.GetContent(id, "title");
	String published = cms.GetContent(id, "published");
	published = published.replaceAll(" ", "T") + (new SimpleDateFormat("Z").format(new java.util.Date())).substring(0,3) + ":" + (new SimpleDateFormat("Z").format(new java.util.Date())).substring(3);
	String server_filename = cms.GetContent(id, "server_filename");
	if (! server_filename.equals("")) {
		if (new java.io.File(Common.getRealPath(servletcontext, config.get(db, "URLrootpath") + server_filename + "/index.jsp")).exists()) {
			output = "<url>" + "\r\n" + " <loc>http://" + servername + config.get(db, "URLrootpath") + server_filename + "/" + "</loc>" + "\r\n" + " <lastmod>" + published + "</lastmod>" + "\r\n" + " <priority>0." + menulevels + "</priority>" + "\r\n" + changefreq + "</url>" + "\r\n" + output;
		} else {
			output = "<url>" + "\r\n" + " <loc>http://" + servername + config.get(db, "URLrootpath") + server_filename + "</loc>" + "\r\n" + " <lastmod>" + published + "</lastmod>" + "\r\n" + " <priority>0." + menulevels + "</priority>" + "\r\n" + changefreq + "</url>" + "\r\n" + output;
		}
	} else if (config.get(db, "url_rewriting").equals("yes")) {
		output = "<url>" + "\r\n" + " <loc>http://" + servername + "/page.jsp/id=" + id + "</loc>" + "\r\n" + " <lastmod>" + published + "</lastmod>" + "\r\n" + " <priority>0." + menulevels + "</priority>" + "\r\n" + changefreq + "</url>" + "\r\n" + output;
	} else {
		output = "<url>" + "\r\n" + " <loc>http://" + servername + "/page.jsp?id=" + id + "</loc>" + "\r\n" + " <lastmod>" + published + "</lastmod>" + "\r\n" + " <priority>0." + menulevels + "</priority>" + "\r\n" + changefreq + "</url>" + "\r\n" + output;
	}
	output = output + buildMenuTopDown(servletcontext, selectedid, id, menulevels, changefreq, loopdetection, mysession, db, config, cms, servername);

	// PREVIOUS SIBLINGS
	String pagepreviousid = cms.GetContent(id, "page_previous");
	while ((! pagepreviousid.equals("")) && (! pagepreviousid.equals("0")) && (loopdetection.get(pagepreviousid) == null)) {
		loopdetection.put(pagepreviousid, pagepreviousid);
		title = cms.GetContent(pagepreviousid, "title");
		published = cms.GetContent(pagepreviousid, "published");
		published = published.replaceAll(" ", "T") + (new SimpleDateFormat("Z").format(new java.util.Date())).substring(0,3) + ":" + (new SimpleDateFormat("Z").format(new java.util.Date())).substring(3);
		server_filename = cms.GetContent(pagepreviousid, "server_filename");
		if (! server_filename.equals("")) {
			if (new java.io.File(Common.getRealPath(servletcontext, config.get(db, "URLrootpath") + server_filename + "/index.jsp")).exists()) {
				output = "<url>" + "\r\n" + " <loc>http://" + servername + config.get(db, "URLrootpath") + server_filename + "/" + "</loc>" + "\r\n" + " <lastmod>" + published + "</lastmod>" + "\r\n" + " <priority>0." + menulevels + "</priority>" + "\r\n" + changefreq + "</url>" + "\r\n" + output;
			} else {
				output = "<url>" + "\r\n" + " <loc>http://" + servername + config.get(db, "URLrootpath") + server_filename + "</loc>" + "\r\n" + " <lastmod>" + published + "</lastmod>" + "\r\n" + " <priority>0." + menulevels + "</priority>" + "\r\n" + changefreq + "</url>" + "\r\n" + output;
			}
		} else if (config.get(db, "url_rewriting").equals("yes")) {
			output = "<url>" + "\r\n" + " <loc>http://" + servername + "/page.jsp/id=" + pagepreviousid + "</loc>" + "\r\n" + " <lastmod>" + published + "</lastmod>" + "\r\n" + " <priority>0." + menulevels + "</priority>" + "\r\n" + changefreq + "</url>" + "\r\n" + output;
		} else {
			output = "<url>" + "\r\n" + " <loc>http://" + servername + "/page.jsp?id=" + pagepreviousid + "</loc>" + "\r\n" + " <lastmod>" + published + "</lastmod>" + "\r\n" + " <priority>0." + menulevels + "</priority>" + "\r\n" + changefreq + "</url>" + "\r\n" + output;
		}
		pagepreviousid = cms.GetContent(pagepreviousid, "page_previous");
	}


	// NEXT SIBLINGS
	String pagenextid = cms.GetContent(id, "page_next");
	while ((! pagenextid.equals("")) && (! pagenextid.equals("0")) && (loopdetection.get(pagenextid) == null)) {
		loopdetection.put(pagenextid, pagenextid);
		title = cms.GetContent(pagenextid , "title");
		published = cms.GetContent(pagenextid, "published");
		published = published.replaceAll(" ", "T") + (new SimpleDateFormat("Z").format(new java.util.Date())).substring(0,3) + ":" + (new SimpleDateFormat("Z").format(new java.util.Date())).substring(3);
		server_filename = cms.GetContent(pagenextid, "server_filename");
		if (! server_filename.equals("")) {
			if (new java.io.File(Common.getRealPath(servletcontext, config.get(db, "URLrootpath") + server_filename + "/index.jsp")).exists()) {
				output = output + "<url>" + "\r\n" + " <loc>http://" + servername + config.get(db, "URLrootpath") + server_filename + "/" + "</loc>" + "\r\n" + " <lastmod>" + published + "</lastmod>" + "\r\n" + " <priority>0." + menulevels + "</priority>" + "\r\n" + changefreq + "</url>" + "\r\n";
			} else {
				output = output + "<url>" + "\r\n" + " <loc>http://" + servername + config.get(db, "URLrootpath") + server_filename + "</loc>" + "\r\n" + " <lastmod>" + published + "</lastmod>" + "\r\n" + " <priority>0." + menulevels + "</priority>" + "\r\n" + changefreq + "</url>" + "\r\n";
			}
		} else if (config.get(db, "url_rewriting").equals("yes")) {
			output = output + "<url>" + "\r\n" + " <loc>http://" + servername + "/page.jsp/id=" + pagenextid + "</loc>" + "\r\n" + " <lastmod>" + published + "</lastmod>" + "\r\n" + " <priority>0." + menulevels + "</priority>" + "\r\n" + changefreq + "</url>" + "\r\n";
		} else {
			output = output + "<url>" + "\r\n" + " <loc>http://" + servername + "/page.jsp?id=" + pagenextid + "</loc>" + "\r\n" + " <lastmod>" + published + "</lastmod>" + "\r\n" + " <priority>0." + menulevels + "</priority>" + "\r\n" + changefreq + "</url>" + "\r\n";
		}
		pagenextid = cms.GetContent(pagenextid , "page_next");
	}

	// PARENT MENU LEVEL
	String pageupid = cms.GetContent(id, "page_up");
	if ((! pageupid.equals("")) && (! pageupid.equals("0")) && (! pageupid.equals(menutop)) && (loopdetection.get(pageupid) == null)) {
		output = buildMenuBottomUp(servletcontext, selectedid, pageupid, output, menutop, menulevels, changefreq, loopdetection, mysession, db, config, cms, servername);
	}
	return output;
}

%><?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
<url>
 <loc>http://<%= servername %>/</loc>
 <lastmod><%= published %></lastmod>
 <priority>1.0</priority>
<%= changefreq %></url>
<%= output %></urlset>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>