<%@ include file="../../webadmin.jsp" %><%@ page import="java.util.regex.*" %><%



// long-to-short page titles translation table
HashMap shortTitleFor = new HashMap();
shortTitleFor.put("This is a long page title", "A Short Title");
shortTitleFor.put("This is another long page title", "Another Short Title");



String menuclass = " class=\"menu_item\"";
String menufirstclass = " class=\"menu_first menu_item\"";
String menulastclass = " class=\"menu_last menu_item\"";
String menuselected = " class=\"menu_selected menu_item\"";
String menutop = "";
String menutoplevel = "0";
String menulevels = "9";
String menulisttag = "menu";
String menuitemtag = "li";
String id = "";
boolean isTopFirst = false;
boolean fixMSIE6tables = false;
String[] params;
if ((myrequest.getAttribute("extension") != null) && (! myrequest.getAttribute("extension").equals(""))) {
	params = (""+myrequest.getAttribute("extension")).split(":");
} else {
	params = mysession.get("extension").split(":");
}
for (int i=0; i<params.length; i++) {
	String param = params[i];
	Matcher classMatches = Pattern.compile("^(class|style)=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher selectedMatches = Pattern.compile("^selected=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher top0Matches = Pattern.compile("^top=0$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher topMatches = Pattern.compile("^top=([0-9]*)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher topsMatches = Pattern.compile("^top=([,0-9]*)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher levelsMatches = Pattern.compile("^levels=([-+0-9,]*)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher listMatches = Pattern.compile("^list=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher itemMatches = Pattern.compile("^item=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher idMatches = Pattern.compile("^id=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher topFirstMatches = Pattern.compile("^first=top$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher fixMSIE6tablesMatches = Pattern.compile("^special=MSIE6tables$", Pattern.CASE_INSENSITIVE).matcher(param);
	if (classMatches.find()) {
		menuclass = " class=\"" + classMatches.group(2) + "\"";
		menufirstclass = " class=\"menu_first " + classMatches.group(2) + "\"";
		menulastclass = " class=\"menu_last " + classMatches.group(2) + "\"";
		menuselected = " class=\"menu_selected " + classMatches.group(2) + "\"";
	} else if (selectedMatches.find()) {
		menuselected = " class=\"" + selectedMatches.group(1) + "\"";
	} else if (top0Matches.find()) {
		menutop = myconfig.get(db, "default_page");
		menutop = cms.GetContent(menutop, "id", false);
	} else if (topMatches.find()) {
		menutop = cms.GetContent("" + topMatches.group(1), "id", false);
	} else if (topsMatches.find()) {
		menutop = "" + topsMatches.group(1);
	} else if (levelsMatches.find()) {
		menulevels = "" + levelsMatches.group(1);
	} else if (listMatches.find()) {
		menulisttag = "" + listMatches.group(1);
	} else if (itemMatches.find()) {
		menuitemtag = "" + itemMatches.group(1);
	} else if (idMatches.find()) {
		id = "" + idMatches.group(1);
	} else if (topFirstMatches.find()) {
		isTopFirst = true;
	} else if (fixMSIE6tablesMatches.find()) {
		fixMSIE6tables = true;
	}
}

if (menulevels.matches("^([0-9]+)-([0-9]+)$")) {
	menutoplevel = menulevels.replaceAll("^([0-9]+)-([0-9]+)$", "$1");
	menulevels = menulevels.replaceAll("^([0-9]+)-([0-9]+)$", "$2");
} else if (menulevels.matches("^([0-9]+)[-+]$")) {
	menutoplevel = menulevels.replaceAll("^([0-9]+)[-+]$", "$1");
	menulevels = "9";
} else {
	menulevels = menulevels.replaceAll("[^0-9]", "");
}
if (menulevels.equals("")) menulevels = "9";

String output = "";
HashMap loopdetection = new HashMap();

String save_superadmin = myconfig.get(db, "superadmin");
myconfig.setTemp("superadmin", mysession.get("username"));
if ((id.equals("")) && (! myrequest.getParameter("database").equals("")) && (! mysession.get("id").equals("")) && (myrequest.getRequestURI().startsWith("/data.jsp"))) {
	id = mysession.get("id");
}
if (! myrequest.getParameter("menu_id").equals("")) {
	id = myrequest.getParameter("menu_id");
}
if ((id.equals("")) || (id.equals("0"))) {
	id = myrequest.getParameter("menu_id");
}
if (((id.equals("")) || (id.equals("0"))) && (myrequest.getRequestURI().startsWith("/webadmin/"))) {
	id = myrequest.getParameter("print");
}
if ((id.equals("")) || (id.equals("0"))) {
	id = myrequest.getParameter("id");
}
if ((id.equals("")) || (id.equals("0"))) {
	id = mysession.get("id");
}
if ((id.equals("")) || (id.equals("0"))) {
	id = myconfig.get(db, "default_page");
}
id = cms.GetContent(id, "id", false);
if (! cms.GetContent(id, "usersegment", false).equals("")) {
	id = cms.GetContent(id, "version_master", false);
}
if (! cms.GetContent(id, "usertest", false).equals("")) {
	id = cms.GetContent(id, "version_master", false);
}

String selectedid = "" + id;
HashMap selectedids = new HashMap();
while (cms.GetContent(selectedid, "menuitem", false).equals("no")) {
	selectedids.put(selectedid, selectedid);
	selectedid = cms.GetContent(selectedid, "page_up", false);
	if (selectedids.get(selectedid) != null) break;
}
String myid = "" + selectedid;
while (selectedids.get(myid) == null) {
	selectedids.put(myid, myid);
	myid = cms.GetContent(myid, "page_up", false);
	if (menutop.matches("^(.*,)?" + myid + "(,.*)?$")) break;
}

if ((! menutop.equals("")) && (! menulevels.equals(""))) {
	output = buildMenuTopDown(selectedid, selectedids, menutop, menulevels, menuclass, menufirstclass, menulastclass, menuselected, menulisttag, menuitemtag, loopdetection, shortTitleFor, mysession, db, myconfig, cms, true, isTopFirst, fixMSIE6tables);
} else {
	output = buildMenuBottomUp(selectedid, selectedids, id, output, menutop, menulevels, menuclass, menufirstclass, menulastclass, menuselected, menulisttag, menuitemtag, loopdetection, shortTitleFor, mysession, db, myconfig, cms, isTopFirst, fixMSIE6tables);
}
myconfig.setTemp("superadmin", save_superadmin);
save_superadmin = "";

if (! menutoplevel.equals("0")) {
	String myoutput = "";
	String[] myoutputline = output.split("\r\n");
	int mymenulevel = 0;
	for (int i=0; i<myoutputline.length; i++) {
		if (myoutputline[i].startsWith("<" + menulisttag + " ")) {
			mymenulevel += 1;
		}
		if (isTopFirst && (mymenulevel == Common.intnumber(menutoplevel)) && (myoutputline[i].startsWith("<" + menulisttag + " "))) {
			// new top level menu
			myoutputline[i] = myoutputline[i].replaceAll("class=\"", "class=\"menu_top menu_first menu_last ");
			myoutput += "" + myoutputline[i] + "\r\n";
		} else if (isTopFirst && (mymenulevel == Common.intnumber(menutoplevel)) && (myoutputline[i].startsWith("</" + menulisttag + ">"))) {
			myoutput += "" + myoutputline[i] + "\r\n";
		} else if (isTopFirst && (mymenulevel == Common.intnumber(menutoplevel)) && (myoutputline[i].startsWith("</" + menuitemtag + ">"))) {
			myoutput += "" + myoutputline[i] + "\r\n";
		} else if (isTopFirst && (mymenulevel == Common.intnumber(menutoplevel)) && (myoutputline[i].indexOf(menuselected) <= 0)) {
			// ignore top level menu items except selected menu item
		} else if (mymenulevel >= Common.intnumber(menutoplevel)) {
			myoutput += "" + myoutputline[i] + "\r\n";
		}
		if (myoutputline[i].startsWith("</" + menulisttag + ">")) {
			mymenulevel -= 1;
		}
	}
	myoutput = Pattern.compile("</" + menulisttag + ">" + "\r\n" + "<" + menulisttag + " " + "[^>]*" + ">", Pattern.DOTALL).matcher(myoutput).replaceAll("");
	output = myoutput;
}

mysession.set("extension", output);

%><%!

public String buildMenuTopDown(String selectedid, HashMap selectedids, String id, String menulevels, String menuclass, String menufirstclass, String menulastclass, String menuselected, String menulisttag, String menuitemtag, HashMap loopdetection, HashMap shortTitleFor, Session mysession, DB db, Configuration config, Cms cms, boolean isTop, boolean isTopFirst, boolean fixMSIE6tables) {
	String output = "";
	String[] ids = id.split(",");
	HashMap pageprevious = new HashMap();
	HashMap pagenext = new HashMap();
	HashMap pagetitle = new HashMap();
	HashMap pagefilename = new HashMap();
	Content content = new Content();
	if (ids.length <= 1) {
		loopdetection.put(id, id);
		content.read(db, config, id, "", "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		String pageversion = content.getVersion();
		String pagedevice = content.getDevice();
		String pageusersegment = content.getUsersegment();
		String pageusertest = content.getUsertest();
		if (isTopFirst) {
			pagetitle.put("" + id, "" + html.encodeHtmlEntities(html.decodeHtmlEntities(content.getTitle())));
			pagefilename.put("" + id, "" + content.getServerFilename());
		}
		content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, config, "select id,title,page_previous,page_next,menuitem,server_filename,version_master,version,device,usersegment,usertest from content_public where ((" + db.is_blank("version") + " or (version=" + db.quote(Common.SQL_clean(pageversion)) + ")) and (" + db.is_blank("device") + " or (device=" + db.quote(Common.SQL_clean(pagedevice)) + ")) and (" + db.is_blank("usersegment") + " or (usersegment=" + db.quote(Common.SQL_clean(pageusersegment)) + ")) and (" + db.is_blank("usertest") + " or (usertest=" + db.quote(Common.SQL_clean(pageusertest)) + "))) and page_up='" + id + "' order by title,id");
		while (content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, config, "")) {
			if (content.getMenuitem().equals("no")) {
				// ignore
			} else {
				pagetitle.put("" + content.getId(), "" + html.encodeHtmlEntities(html.decodeHtmlEntities(content.getTitle())));
				pagefilename.put("" + content.getId(), "" + content.getServerFilename());
			}
			if ((content.getPagePrevious() != null) && (! content.getPagePrevious().equals("")) && (! content.getPagePrevious().equals("0"))) {
				pageprevious.put("" + content.getId(), "" + content.getPagePrevious());
				pagenext.put("" + content.getPagePrevious(), "" + content.getId());
			} else if (isTopFirst) {
				pagenext.put("" + id, "" + content.getId());
				pageprevious.put("" + content.getId(), "" + id);
			}
			if ((content.getPageNext() != null) && (! content.getPageNext().equals("")) && (content.getPageNext().equals("0"))) {
				pagenext.put("" + content.getId(), "" + content.getPageNext());
				pageprevious.put("" + content.getPageNext(), "" + content.getId());
			}
		}
		if (isTopFirst && (pagetitle.size() <= 1)) {
			pageprevious = new HashMap();
			pagenext = new HashMap();
			pagetitle = new HashMap();
			pagefilename = new HashMap();
		}
	} else {
		for (int i=0; i<ids.length; i++) {
			String myid = "" + ids[i];
			content.read(db, config, myid, "", "", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
			pagetitle.put("" + content.getId(), "" + html.encodeHtmlEntities(html.decodeHtmlEntities(content.getTitle())));
			pagefilename.put("" + content.getId(), "" + content.getServerFilename());
			if (i == 0) {
				pageprevious.put("" + content.getId(), null);
			} else {
				pageprevious.put("" + content.getId(), "" + ids[i-1]);
			}
			if (i == (ids.length-1)) {
				pagenext.put("" + content.getId(), null);
			} else {
				pagenext.put("" + content.getId(), "" + ids[i+1]);
			}
		}
	}
	// PAGE SEQUENCES
	String[] pagetitles = (String[])pagetitle.keySet().toArray(new String[0]);
	int menuindex = 0;
	for (int i=0; i<pagetitles.length; i++) {
		id = "" + pagetitles[i];
		String title = "";
		if (pagetitle.get(id) != null) title = "" + pagetitle.get(id);
		String filename = "";
		if (pagefilename.get(id) != null) filename = "" + pagefilename.get(id);
		if ((! title.equals("")) && (pageprevious.get(id) == null)) {
			while ((! id.equals("")) && (! id.equals("0")) && (! title.equals(""))) {
				String suboutput = "";
				menuindex++;
				String[] mymenulevels = menulevels.split(",");
				if ((loopdetection.get(id) == null) && (mymenulevels.length > 1)) {
					if ((mymenulevels.length >= menuindex) && (Integer.parseInt("0" + mymenulevels[menuindex-1]) > 1)) {
						suboutput = buildMenuTopDown(selectedid, selectedids, id, "" + (Integer.parseInt("0" + mymenulevels[menuindex-1])-1), menuclass, menufirstclass, menulastclass, menuselected, menulisttag, menuitemtag, loopdetection, shortTitleFor, mysession, db, config, cms, false, false, fixMSIE6tables);
					}
				} else if ((loopdetection.get(id) == null) && (Integer.parseInt("0" + menulevels) > 1)) {
					suboutput = buildMenuTopDown(selectedid, selectedids, id, "" + (Integer.parseInt("0" + menulevels)-1), menuclass, menufirstclass, menulastclass, menuselected, menulisttag, menuitemtag, loopdetection, shortTitleFor, mysession, db, config, cms, false, false, fixMSIE6tables);
				}
				String mymenuclass = "";
				if ((id.equals(selectedid)) || (selectedids.get(id) != null)) {
					mymenuclass = "" + menuselected;
				} else {
					mymenuclass = "" + menuclass;
				}
				if (! suboutput.equals("")) {
					if (mymenuclass.equals("")) {
						mymenuclass = " class=\"menu_submenu\"";
					} else {
						mymenuclass = mymenuclass.replaceAll(" class=\"(.*)\"", " class=\"menu_submenu $1\"");
					}
				}
				if (output.equals("")) {
					if (mymenuclass.equals("")) {
						mymenuclass = " class=\"menu_first\"";
					} else {
						mymenuclass = mymenuclass.replaceAll(" class=\"(.*)\"", " class=\"menu_first $1\"");
					}
				}
				if ((pagenext.get(id) == null) || (pagenext.get(id).equals("")) || (pagenext.get(id).equals("0"))) {
					if (mymenuclass.equals("")) {
						mymenuclass = " class=\"menu_last\"";
					} else {
						mymenuclass = mymenuclass.replaceAll(" class=\"(.*)\"", " class=\"menu_last $1\"");
					}
				}
				if ((filename != null) && (! filename.equals(""))) {
					output = output + "<" + menuitemtag + mymenuclass + "><a href=\"" + config.get(db, "URLrootpath") + filename + "\"" + mymenuclass + " title=\"" + title + "\">" + title;
				} else if (config.get(db, "url_rewriting").equals("yes")) {
					output = output + "<" + menuitemtag + mymenuclass + "><a href=\"/page.jsp/id=" + id + "\"" + mymenuclass + " title=\"" + title + "\">" + title;
				} else {
					output = output + "<" + menuitemtag + mymenuclass + "><a href=\"/page.jsp?id=" + id + "\"" + mymenuclass + " title=\"" + title + "\">" + title;
				}
				if (fixMSIE6tables && (! suboutput.equals(""))) {
					output = output + "<!--[if IE 7]><!--></a><!--<![endif]-->" + "\r\n";
				} else {
					output = output + "</a>" + "\r\n";
				}
				output = output + suboutput;
				output = output + "\r\n" + "</" + menuitemtag + ">" + "\r\n";
				pagetitle.put(id, "");
				pagefilename.put(id, "");
				if (pagenext.get(id) != null) {
					id = "" + pagenext.get(id);
				} else {
					id = "";
				}
				while ((! id.equals("")) && (pagetitle.get(id) == null)) {
					if (pagenext.get(id) != null) {
						id = "" + pagenext.get(id);
					} else {
						id = "";
					}
				}
				title = "";
				if (pagetitle.get(id) != null) title = "" + pagetitle.get(id);
				filename = "";
				if (pagefilename.get(id) != null) filename = "" + pagefilename.get(id);
			}
		}
	}
	if (! output.equals("")) {
		String mymenuclass = "" + menuclass;
		if (isTop) {
			if (mymenuclass.equals("")) {
				mymenuclass = " class=\"menu_top\"";
			} else {
				mymenuclass = mymenuclass.replaceAll(" class=\"(.*)\"", " class=\"menu_top $1\"");
			}
		}
		if (fixMSIE6tables && (! isTop)) {
			output = "<!--[if lte IE 6]><table><tr><td><![endif]-->" + "\r\n" + "<" + menulisttag + mymenuclass + ">" + "\r\n" + output + "</" + menulisttag + ">" + "\r\n";
		} else {
			output = "<" + menulisttag + mymenuclass + ">" + "\r\n" + output + "</" + menulisttag + ">" + "\r\n";
		}
		if (fixMSIE6tables && (! isTop)) {
			output = output + "<!--[if lte IE 6]></td></tr></table></a><![endif]-->" + "\r\n";
		}
	}
	return output;
}



public String buildMenuBottomUp(String selectedid, HashMap selectedids, String id, String output, String menutop, String menulevels, String menuclass, String menufirstclass, String menulastclass, String menuselected, String menulisttag, String menuitemtag, HashMap loopdetection, HashMap shortTitleFor, Session mysession, DB db, Configuration config, Cms cms, boolean isTopFirst, boolean fixMSIE6tables) throws Exception {
	// CURRENT PAGE
	HashMap includedmenuitems = new HashMap();
	includedmenuitems.put(id, id);
	loopdetection.put(id, id);
	String title = html.encodeHtmlEntities(html.decodeHtmlEntities(cms.GetContent(id, "title", false)));
	String filename = cms.GetContent(id, "server_filename", false);
	if (shortTitleFor.get(title) != null) {
		title = "" + shortTitleFor.get(title);
	}
	if (cms.GetContent(id, "menuitem", false).equals("no")) {
		// ignore
	} else if (id.equals(selectedid)) {
		if ((filename != null) && (! filename.equals(""))) {
			output = "<" + menuitemtag + menuselected + "><a href=\"" + config.get(db, "URLrootpath") + filename + "\"" + menuselected + " title=\"" + title + "\">" + title + "</a>" + "\r\n" + output;
		} else if (config.get(db, "url_rewriting").equals("yes")) {
			output = "<" + menuitemtag + menuselected + "><a href=\"/page.jsp/id=" + id + "\"" + menuselected + " title=\"" + title + "\">" + title + "</a>" + "\r\n" + output;
		} else {
			output = "<" + menuitemtag + menuselected + "><a href=\"/page.jsp?id=" + id + "\"" + menuselected + " title=\"" + title + "\">" + title + "</a>" + "\r\n" + output;
		}
		if (loopdetection.size() <= 1) {
			output = output + buildMenuTopDown(selectedid, selectedids, id, menulevels, menuclass, menufirstclass, menulastclass, menuselected, menulisttag, menuitemtag, loopdetection, shortTitleFor, mysession, db, config, cms, false, false, fixMSIE6tables);
		}
		output = output + "\r\n" + "</" + menuitemtag + ">" + "\r\n";
	} else if (selectedids.get(id) != null) {
		if ((filename != null) && (! filename.equals(""))) {
			output = "<" + menuitemtag + menuselected + "><a href=\"" + config.get(db, "URLrootpath") + filename + "\"" + menuselected + " title=\"" + title + "\">" + title + "</a>" + "\r\n" + output;
		} else if (config.get(db, "url_rewriting").equals("yes")) {
			output = "<" + menuitemtag + menuselected + "><a href=\"/page.jsp/id=" + id + "\"" + menuselected + " title=\"" + title + "\">" + title + "</a>" + "\r\n" + output;
		} else {
			output = "<" + menuitemtag + menuselected + "><a href=\"/page.jsp?id=" + id + "\"" + menuselected + " title=\"" + title + "\">" + title + "</a>" + "\r\n" + output;
		}
		output = output + "\r\n" + "</" + menuitemtag + ">" + "\r\n";
	} else {
		if ((filename != null) && (! filename.equals(""))) {
			output = "<" + menuitemtag + menuclass + "><a href=\"" + config.get(db, "URLrootpath") + filename + "\"" + menuclass + " title=\"" + title + "\">" + title + "</a>" + output + "</" + menuitemtag + ">" + "\r\n";
		} else if (config.get(db, "url_rewriting").equals("yes")) {
			output = "<" + menuitemtag + menuclass + "><a href=\"/page.jsp/id=" + id + "\"" + menuclass + " title=\"" + title + "\">" + title + "</a>" + output + "</" + menuitemtag + ">" + "\r\n";
		} else {
			output = "<" + menuitemtag + menuclass + "><a href=\"/page.jsp?id=" + id + "\"" + menuclass + " title=\"" + title + "\">" + title + "</a>" + output + "</" + menuitemtag + ">" + "\r\n";
		}
	}

	// PREVIOUS SIBLINGS
	String pagepreviousid = cms.GetContent(id, "page_previous", false);
	while ((! pagepreviousid.equals("")) && (! pagepreviousid.equals("0")) && (loopdetection.get(pagepreviousid) == null)) {
		includedmenuitems.put(pagepreviousid, pagepreviousid);
		loopdetection.put(pagepreviousid, pagepreviousid);
		if ((cms.GetContent(pagepreviousid, "id", false).equals(pagepreviousid)) || (cms.GetContent(pagepreviousid, "version_master", false).equals(pagepreviousid))) {
			title = html.encodeHtmlEntities(html.decodeHtmlEntities(cms.GetContent(pagepreviousid, "title", false)));
			filename = cms.GetContent(pagepreviousid, "server_filename", false);
			if (shortTitleFor.get(title) != null) {
				title = "" + shortTitleFor.get(title);
			}
			if (cms.GetContent(pagepreviousid, "menuitem", false).equals("no")) {
				// ignore
			} else if ((filename != null) && (! filename.equals(""))) {
				output = "<" + menuitemtag + menuclass + "><a href=\"" + config.get(db, "URLrootpath") + filename + "\"" + menuclass + " title=\"" + title + "\">" + title + "</a></" + menuitemtag + ">" + "\r\n" + output;
			} else if (config.get(db, "url_rewriting").equals("yes")) {
				output = "<" + menuitemtag + menuclass + "><a href=\"/page.jsp/id=" + pagepreviousid + "\"" + menuclass + " title=\"" + title + "\">" + title + "</a></" + menuitemtag + ">" + "\r\n" + output;
			} else {
				output = "<" + menuitemtag + menuclass + "><a href=\"/page.jsp?id=" + pagepreviousid + "\"" + menuclass + " title=\"" + title + "\">" + title + "</a></" + menuitemtag + ">" + "\r\n" + output;
			}
		}
		pagepreviousid = cms.GetContent(pagepreviousid, "page_previous", false);
	}

	// NEXT SIBLINGS
	String pagenextid = cms.GetContent(id, "page_next", false);
	while ((! pagenextid.equals("")) && (! pagenextid.equals("0")) && (loopdetection.get(pagenextid) == null)) {
		includedmenuitems.put(pagenextid, pagenextid);
		loopdetection.put(pagenextid, pagenextid);
		if ((cms.GetContent(pagenextid, "id", false).equals(pagenextid)) || (cms.GetContent(pagenextid, "version_master", false).equals(pagenextid))) {
			title = html.encodeHtmlEntities(html.decodeHtmlEntities(cms.GetContent(pagenextid , "title", false)));
			filename = cms.GetContent(pagenextid, "server_filename", false);
			if (shortTitleFor.get(title) != null) {
				title = "" + shortTitleFor.get(title);
			}
			if (cms.GetContent(pagenextid, "menuitem", false).equals("no")) {
				// ignore
			} else if ((filename != null) && (! filename.equals(""))) {
				output = output + "<" + menuitemtag + menuclass + "><a href=\"" + config.get(db, "URLrootpath") + filename + "\"" + menuclass + " title=\"" + title + "\">" + title + "</a></" + menuitemtag + ">" + "\r\n";
			} else if (config.get(db, "url_rewriting").equals("yes")) {
				output = output + "<" + menuitemtag + menuclass + "><a href=\"/page.jsp/id=" + pagenextid + "\"" + menuclass + " title=\"" + title + "\">" + title + "</a></" + menuitemtag + ">" + "\r\n";
			} else {
				output = output + "<" + menuitemtag + menuclass + "><a href=\"/page.jsp?id=" + pagenextid + "\"" + menuclass + " title=\"" + title + "\">" + title + "</a></" + menuitemtag + ">" + "\r\n";
			}
		}
		pagenextid = cms.GetContent(pagenextid , "page_next", false);
	}

	// ORPHAN SIBLINGS
	String parentid = cms.GetContent(id, "page_up", false);
	if (! parentid.equals("")) {
		Content content = new Content();
		content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, config, "select id,title,page_previous,page_next,menuitem,server_filename,version_master,version,device,usersegment,usertest from content_public where " + db.is_not_blank("page_up") + " and page_up <> '0' and page_up='" + parentid + "' order by title,id");
		while (content.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, config, "")) {
			if (content.getMenuitem().equals("no")) {
				// ignore
			} else if ((! content.getUsersegment().equals("")) || (! content.getUsertest().equals(""))) {
				// ignore
			} else if (loopdetection.get(content.getId()) == null) {
				includedmenuitems.put(content.getId(), content.getId());
				loopdetection.put(content.getId(), content.getId());
				title = html.encodeHtmlEntities(html.decodeHtmlEntities(content.getTitle()));
				if (shortTitleFor.get(title) != null) {
					title = "" + shortTitleFor.get(title);
				}
				if ((filename != null) && (! filename.equals(""))) {
					output = output + "<" + menuitemtag + menuclass + "><a href=\"" + config.get(db, "URLrootpath") + content.getServerFilename() + "\"" + menuclass + " title=\"" + title + "\">" + title + "</a></" + menuitemtag + ">" + "\r\n";
				} else if (config.get(db, "url_rewriting").equals("yes")) {
					output = output + "<" + menuitemtag + menuclass + "><a href=\"/page.jsp/id=" + content.getId() + "\"" + menuclass + " title=\"" + title + "\">" + title + "</a></" + menuitemtag + ">" + "\r\n";
				} else {
					output = output + "<" + menuitemtag + menuclass + "><a href=\"/page.jsp?id=" + content.getId() + "\"" + menuclass + " title=\"" + title + "\">" + title + "</a></" + menuitemtag + ">" + "\r\n";
				}
			}
		}
	}

	// WRAP UP MENU LEVEL
	output = "<" + menulisttag + menuclass + ">" + "\r\n" + output + "</" + menulisttag + ">" + "\r\n";

	// PARENT MENU LEVEL
	String pageupid = cms.GetContent(id, "page_up", false);
	if ((! pageupid.equals("")) && (! pageupid.equals("0")) && (! menutop.matches("^(.*,)?" + pageupid + "(,.*)?$")) && (loopdetection.get(pageupid) == null)) {
		output = buildMenuBottomUp(selectedid, selectedids, pageupid, output, menutop, menulevels, menuclass, menufirstclass, menulastclass, menuselected, menulisttag, menuitemtag, loopdetection, shortTitleFor, mysession, db, config, cms, isTopFirst, fixMSIE6tables);
	}
	return output;
}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>