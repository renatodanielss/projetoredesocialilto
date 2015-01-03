<%@ include file="../../webadmin.jsp" %><%@ page import="java.net.*" %><%@ page import="javax.servlet.http.HttpUtils" %><%@ page import="java.util.regex.*" %><%@ page import="HardCore.Common" %><%@ page import="HardCore.html" %><%


String output = "";
String id = myrequest.getParameter("id");

Page content = new Page(mytext);
content.read_primary_only(db, myconfig, id, "content_public", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), mysession.get("version"), myconfig.get(db, "default_version"), mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession);

String urlpathquery = "";
if (! content.getServerFilename().equals("")) {
	urlpathquery = myconfig.get(db, "URLrootpath") + content.getServerFilename();
} else {
	urlpathquery = "" + myrequest.getServletPath();
}
if (! myrequest.getQueryString().equals("")) {
	urlpathquery += "?" + myrequest.getQueryString();
}

output += "<?xml version=\"1.0\" encoding=\"" + myconfig.get(db, "charset") + "\" ?>" + "\r\n";

String stylesheet = "";
if (content.getStyleSheet().equals("0")) {
	stylesheet = "";
} else if (! content.getStyleSheet().equals("")) {
	stylesheet = "/stylesheet.jsp?id=" + content.getStyleSheet();
} else if ((! myconfig.get(db, "default_stylesheet").equals("")) && (! myconfig.get(db, "default_stylesheet").equals("0"))) {
	stylesheet = "/stylesheet.jsp?id=" + myconfig.get(db, "default_stylesheet");
}
if (! stylesheet.equals("")) {
	output += "<?xml-stylesheet type=\"text/css\" href=\"" + stylesheet + "\" ?>" + "\r\n";
}

//output += "<rss version=\"2.0\">" + "\r\n";
output += "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\">" + "\r\n";
output += "<channel>" + "\r\n";
output += "<title>" + html.encode(content.getTitle()) + "</title>" + "\r\n";
output += "<link>" + myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + urlpathquery + "</link>" + "\r\n";
output += "<atom:link href=\"" + myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + urlpathquery + "\" rel=\"self\" type=\"application/rss+xml\" />" + "\r\n";
output += "<description>" + html.encode(content.getSummary())+ html.encode(content.getDescription()) + "</description>" + "\r\n";
if (! content.getAuthor().equals("")) {
//	output += "<author>" + html.encode(content.getAuthor()) + "</author>" + "\r\n";
}

String list = "";
String listtitle = "title";
String listdescription = "description";
String listauthor = "";
String listpubdate = "published:format=%a, %d %b %Y %H:%M:%S %Z:en:GB";
String listlink = "@@@fileaddress@@@";
String[] params;
if ((myrequest.getAttribute("extension") != null) && (! myrequest.getAttribute("extension").equals(""))) {
	params = (""+myrequest.getAttribute("extension")).split(":");
} else {
	params = mysession.get("extension").split(":");
}
for (int i=0; i<params.length; i++) {
	String param = params[i];
	Matcher titleMatches = Pattern.compile("^rss_title=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher descriptionMatches = Pattern.compile("^rss_description=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher authorMatches = Pattern.compile("^rss_author=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher pubdateMatches = Pattern.compile("^rss_pubdate=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	if (titleMatches.find()) {
		listtitle = "" + titleMatches.group(1);
	} else if (descriptionMatches.find()) {
		listdescription = "" + descriptionMatches.group(1);
	} else if (authorMatches.find()) {
		listauthor = "" + authorMatches.group(1);
	} else if (pubdateMatches.find()) {
		listpubdate = "" + pubdateMatches.group(1) + ":format=%a, %d %b %Y %H:%M:%S %Z:en:GB";
//		listpubdate = "" + pubdateMatches.group(1);
	} else {
		Matcher databaseMatches = Pattern.compile("^database=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
		if (databaseMatches.find()) {
			listlink = "/data.jsp?database=" + databaseMatches.group(1) + "&amp;id=@@@id@@@";
		}
		if (! list.equals("")) {
			list += ":";
		}
		list += param;
	}
}
String listcontent = "";
listcontent += "<item>" + "\r\n";
listcontent += "<title>@@@" + listtitle + "@@@</title>" + "\r\n";
listcontent += "<description>@@@" + listdescription + "@@@</description>" + "\r\n";
if (! listauthor.equals("")) {
	listcontent += "<author>@@@" + listauthor + "@@@</author>" + "\r\n";
}
listcontent += "<pubDate>@@@" + listpubdate + "@@@</pubDate>" + "\r\n";
listcontent += "<link>" + myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + listlink + "</link>" + "\r\n";
listcontent += "<guid>" + myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + listlink + "</guid>" + "\r\n";
listcontent += "</item>" + "\r\n";
String listoutput = content.parse_output_list(list, "content_public", "id", servletcontext, myrequest, myresponse, mysession, myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, mysession.get("version"), "", mysession.get("device"), mysession.get("usersegments"), mysession.get("usertests"), mysession.get("currency"), "", "", "", listcontent, null);
listoutput = html.encode(listoutput);
listoutput = listoutput.replaceAll("&lt;item&gt;", "<item>");
listoutput = listoutput.replaceAll("&lt;/item&gt;", "</item>");
listoutput = listoutput.replaceAll("&lt;title&gt;", "<title>");
listoutput = listoutput.replaceAll("&lt;/title&gt;", "</title>");
listoutput = listoutput.replaceAll("&lt;description&gt;", "<description>");
listoutput = listoutput.replaceAll("&lt;/description&gt;", "</description>");
listoutput = listoutput.replaceAll("&lt;author&gt;", "<author>");
listoutput = listoutput.replaceAll("&lt;/author&gt;", "</author>");
listoutput = listoutput.replaceAll("&lt;pubDate&gt;", "<pubDate>");
listoutput = listoutput.replaceAll("&lt;/pubDate&gt;", "</pubDate>");
listoutput = listoutput.replaceAll("&lt;link&gt;", "<link>");
listoutput = listoutput.replaceAll("&lt;/link&gt;", "</link>");
listoutput = listoutput.replaceAll("&lt;guid&gt;", "<guid>");
listoutput = listoutput.replaceAll("&lt;/guid&gt;", "</guid>");
output += listoutput;

output += "</channel>" + "\r\n";
output += "</rss>" + "\r\n";

output = output.replaceAll("@@@" + listtitle + "@@@", "");
output = output.replaceAll("@@@" + listdescription + "@@@", "");
if (! listauthor.equals("")) {
	output = output.replaceAll("@@@" + listauthor + "@@@", "");
}
output = output.replaceAll("@@@" + listpubdate + "@@@", "");
// encode unencoded & characters
output = output.replaceAll("&(?!(\\#((x([\\dA-Fa-f]){1,5})|(104857[0-5]|10485[0-6]\\d|1048[0-4]\\d\\d|104[0-7]\\d{3}|10[0-3]\\d{4}|0?\\d{1,6}))|([A-Za-z\\d.]{2,31}));)", "&amp;").replaceAll("&amp;amp;", "&amp;");

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>