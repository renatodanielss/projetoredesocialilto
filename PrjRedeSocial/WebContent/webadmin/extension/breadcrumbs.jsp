<%@ include file="../../webadmin.jsp" %><%



// long-to-short page titles translation table
HashMap shortTitleFor = new HashMap();
shortTitleFor.put("This is a long page title", "A Short Title");
shortTitleFor.put("This is another long page title", "Another Short Title");



String separator;
if ((myrequest.getAttribute("extension") != null) && (! myrequest.getAttribute("extension").equals(""))) {
	separator = ""+myrequest.getAttribute("extension");
} else {
	separator = mysession.get("extension");
}
String output = "";
HashMap loopdetection = new HashMap();
String id = "";
if (((id.equals("")) || (id.equals("0"))) && (myrequest.getRequestURI().startsWith("/webadmin/"))) {
	id = mysession.get("id");
}
if (((id.equals("")) || (id.equals("0"))) && (! myrequest.getParameter("id").equals("")) && (myrequest.getParameter("database").equals(""))) {
	id = myrequest.getParameter("id");
}
if ((id.equals("")) || (id.equals("0"))) {
	id = mysession.get("id");
}
String requestid = "" + id;
while ((! id.equals("")) && (! id.equals("0")) && (loopdetection.get(id) == null)) {
	loopdetection.put(id, id);
	String title = html.encodeHtmlEntities(html.decodeHtmlEntities(cms.GetContent(id, "title", false)));
	String server_filename = cms.GetContent(id, "server_filename", false);
	String device = cms.GetContent(id, "device", false);
	String version = cms.GetContent(id, "version", false);
	String version_master = cms.GetContent(id, "version_master", false);
	String menuitem = cms.GetContent(id, "menuitem", false);
	if ((shortTitleFor.get(title) != null) && (! (""+shortTitleFor.get(title)).equals(""))) {
		title = "" + shortTitleFor.get(title);
	}
	if (menuitem.equals("no")) {
		// ignore
	} else {
		if (! output.equals("")) {
			output = separator + output;
		}
		if (! server_filename.equals("")) {
			if (! output.equals("")) {
				output = "<a href=\"" + myconfig.get(db, "URLrootpath") + server_filename + "\">" + title + "</a>" + output;
			} else {
				output = "<a href=\"" + myconfig.get(db, "URLrootpath") + server_filename + "\" class=\"currentpage\">" + title + "</a>" + output;
			}
		} else if ((! version_master.equals("")) && (! version_master.equals("0"))) {
			if (! output.equals("")) {
				output = "<a href=\"/page.jsp?id=" + version_master + "\">" + title + "</a>" + output;
			} else {
				output = "<a href=\"/page.jsp?id=" + version_master + "\" class=\"currentpage\">" + title + "</a>" + output;
			}
		} else {
			if (! output.equals("")) {
				output = "<a href=\"/page.jsp?id=" + id + "\">" + title + "</a>" + output;
			} else {
				output = "<a href=\"/page.jsp?id=" + id + "\" class=\"currentpage\">" + title + "</a>" + output;
			}
		}
	}
	if (id.equals(requestid)) {
		id = cms.GetContent(id, "page_up", false);
		if ((id.equals("")) || (id.equals("0"))) {
			output = "";
		}
	} else {
		id = cms.GetContent(id, "page_up", false);
	}
}

mysession.set("extension", output);

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>