<%@ include file="../../webadmin.jsp" %><%



String output = "";
String SQLwhere = "";
String url = "/page.jsp?id=@@@id@@@&keywords=";
String separator = " ";
String all = "";
String pre = "";
String post = "";
String selected = "";
String count = "";
String[] params;
if ((myrequest.getAttribute("extension") != null) && (! myrequest.getAttribute("extension").equals(""))) {
	params = (""+myrequest.getAttribute("extension")).split(":");
} else {
	params = mysession.get("extension").split(":");
}
for (int i=0; i<params.length; i++) {
	String param = params[i];
	Matcher idMatches = Pattern.compile("^id=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher idNotMatches = Pattern.compile("^id!=([0-9]+?)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher topMatches = Pattern.compile("^top=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher upMatches = Pattern.compile("^up=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher groupMatches = Pattern.compile("^group=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher typeMatches = Pattern.compile("^type=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher urlMatches = Pattern.compile("^url=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher separatorMatches = Pattern.compile("^separator=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher allMatches = Pattern.compile("^all=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher preMatches = Pattern.compile("^pre=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher postMatches = Pattern.compile("^post=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher countMatches = Pattern.compile("^count=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher selectedMatches = Pattern.compile("^selected=(.+)$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher publishedMatches = Pattern.compile("^published$", Pattern.CASE_INSENSITIVE).matcher(param);
	Matcher unpublishedMatches = Pattern.compile("^unpublished$", Pattern.CASE_INSENSITIVE).matcher(param);
	if (idMatches.find()) {
		String ids = "" + idMatches.group(1);
		SQLwhere = Common.SQLwhere_in(db, SQLwhere, "id", ids);
	} else if (idNotMatches.find()) {
		String ids = "" + idNotMatches.group(1);
		SQLwhere = Common.SQLwhere_not_in(db, SQLwhere, "id", ids);
	} else if (topMatches.find()) {
		String ids = "" + topMatches.group(1);
		SQLwhere = Common.SQLwhere_in(db, SQLwhere, "page_top", ids);
	} else if (upMatches.find()) {
		String ids = "" + upMatches.group(1);
		SQLwhere = Common.SQLwhere_in(db, SQLwhere, "page_up", ids);
	} else if (groupMatches.find()) {
		String groups = "" + groupMatches.group(1);
		SQLwhere = Common.SQLwhere_in(db, SQLwhere, "contentgroup", groups);
	} else if (typeMatches.find()) {
		String types = "" + typeMatches.group(1);
		SQLwhere = Common.SQLwhere_in(db, SQLwhere, "contenttype", types);
	} else if (publishedMatches.find()) {
		SQLwhere = Common.SQLwhere_not_equals(db, SQLwhere, "published", "");
	} else if (unpublishedMatches.find()) {
		SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "published", "");
	} else if (urlMatches.find()) {
		url = "" + urlMatches.group(1);
		url = url.replaceAll("\\.xxx", ".jsp");
	} else if (separatorMatches.find()) {
		separator = "" + separatorMatches.group(1);
		separator = separator.replaceAll("&lt;", "<");
		separator = separator.replaceAll("&gt;", ">");
	} else if (allMatches.find()) {
		all = "" + allMatches.group(1);
		all = all.replaceAll("&lt;", "<");
		all = all.replaceAll("&gt;", ">");
	} else if (preMatches.find()) {
		pre = "" + preMatches.group(1);
		pre = pre.replaceAll("&lt;", "<");
		pre = pre.replaceAll("&gt;", ">");
	} else if (postMatches.find()) {
		post = "" + postMatches.group(1);
		post = post.replaceAll("&lt;", "<");
		post = post.replaceAll("&gt;", ">");
	} else if (countMatches.find()) {
		count = "" + countMatches.group(1);
	} else if (selectedMatches.find()) {
		selected = "" + selectedMatches.group(1);
	}
}



HashMap keywords = new HashMap();
keywords.put("", "0");
String SQL = "select id,keywords from content " + SQLwhere;
Content contents = new Content(mytext);
contents.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
while (contents.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
	keywords.put("", ""+(Common.integernumber(keywords.get(""))+1));
	String[] mykeywords = contents.getKeywords().replaceAll("[., ]+", " ").split(" ");
	for (int i=0; i<mykeywords.length; i++) {
		String keyword = "" + mykeywords[i];
		keyword = keyword.trim();
		if (! keyword.equals("")) {
			keywords.put(keyword, ""+(Common.integernumber(keywords.get(keyword))+1));
		}
	}
}

if (! all.equals("")) {
	output += "<a href=\"" + url + "\"";
	if (selected.equals("")) {
		output += " class=\"selected\"";
	}
	output += ">" + all;
	output += count.replace("#", ""+keywords.get(""));
	output += "</a>" + "\r\n";
}
Iterator i = Common.SortedHashMapKeySetIterator(keywords);
while (i.hasNext()) {
	String keyword = "" + i.next();
	keyword = keyword.trim();
	if (! keyword.equals("")) {
		if (! output.equals("")) output += separator;
		output += "<a href=\"" + url + keyword + "\"";
		if (selected.equals(keyword)) {
			output += " class=\"selected\"";
		}
		output += ">" + keyword;
		output += count.replace("#", ""+keywords.get(keyword));
		output += "</a>" + "\r\n";
	}
}
if (! output.equals("")) output = pre + output + post;

mysession.set("extension", output);

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>