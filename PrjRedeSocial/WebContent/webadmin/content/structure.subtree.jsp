<%@ include file="../config.jsp" %><%@ page import="HardCore.Content" %><%

LinkedHashMap children = new LinkedHashMap();	// null ~ new LinkedHashMap()
Content content_versions = new Content();	// null ~ new Content()
Content content_devices = new Content();	// null ~ new Content()
Content content_additional = new Content();	// null ~ new Content()
boolean content_additional_content = true;	// false ~ true
boolean content_additional_media = true;	// false ~ true
boolean select_page_tops = true;		// false ~ true

String mode = myrequest.getParameter("mode");
if (mode.equals("structure")) {
	children = new LinkedHashMap();		// null ~ new LinkedHashMap()
	content_versions = null;		// null ~ new Content()
	content_devices = null;			// null ~ new Content()
	content_additional = null;		// null ~ new Content()
	content_additional_content = false;	// false ~ true
	content_additional_media = false;	// false ~ true
	select_page_tops = true;		// false ~ true
}

String id = myrequest.getParameter("id");
Double descendents = 0.0;
Double orphans = 0.0;



	String SQLorphans = "(" + db.is_blank("page_up") + " or (page_up='0'))";
	String SQLtops = Common.join(",", (String[])db.query_values("select distinct page_top from content where " + db.is_not_blank("page_top") + " and (page_top<>'0')").values().toArray(new String[0]));
	if ((SQLtops.equals("")) || (SQLtops.equals(","))) {
		SQLtops = "1=0";
	} else {
		SQLorphans += " and id not in (" + SQLtops + ")";
		SQLtops = "id in (" + SQLtops + ") and (" + db.is_blank("page_up") + " or (page_up='0')) and (" + db.is_blank("page_top") + " or (page_top='0'))";
	}

	Content content = null;
	HashMap pageordered = new HashMap();
	HashMap pagetitle = new HashMap();
	HashMap pagecontentclass = new HashMap();
	HashMap pageversion = new HashMap();
	HashMap pageversionmaster = new HashMap();
	HashMap pagedevice = new HashMap();
	HashMap pagetop = new HashMap();
	HashMap pageup = new HashMap();
	HashMap pagefirst = new HashMap();
	HashMap pageprevious = new HashMap();
	HashMap pagenext = new HashMap();
	HashMap pagelast = new HashMap();
	HashMap pageimage1 = new HashMap();
	HashMap pageimage2 = new HashMap();
	HashMap pageimage3 = new HashMap();
	HashMap pagefile1 = new HashMap();
	HashMap pagefile2 = new HashMap();
	HashMap pagefile3 = new HashMap();
	HashMap pagelink1 = new HashMap();
	HashMap pagelink2 = new HashMap();
	HashMap pagelink3 = new HashMap();
	HashMap pagefilename = new HashMap();
	HashMap pagetype = new HashMap();
	HashMap pagerelations = new HashMap();
	String myid = "";
	String mytitle = "";
	String mycontentclass = "";
	String mytype = "";
	String myrelations = "";
	String myselected = "";
	id = id.replaceAll("[^-0-9]", "");
 	if ((id.equals("")) || (id.equals("0")) || (id.startsWith("-"))) {
		id = id.replaceAll("^-", "");
		if ((id.equals("")) || (id.equals("0"))) {
			id = myrequest.getParameter("root");
		}
		if ((id.equals("")) || (id.equals("0"))) {
			id = myconfig.get(db, "default_page");
		}
		String SQL = "";
		content = new Content();
		content.read(db, myconfig, id, "content", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		if (content.getUser()) {
			myid = "" + content.getId();
			mytitle = "" + content.getTitle();
			if (! content.getVersion().equals("")) {
				mytitle += " (" + content.getVersion() + ")";
			}
			if (! content.getDevice().equals("")) {
				mytitle += " (" + content.getDevice() + ")";
			}
			myrelations = " rootnode page undraggable";
			if (! content.getPageTop().equals("")) myrelations += " page_top_" + content.getPageTop();
			if (! content.getPageUp().equals("")) myrelations += " page_up_" + content.getPageUp();
			if (! content.getPageFirst().equals("")) myrelations += " page_first_" + content.getPageFirst();
			if (! content.getPagePrevious().equals("")) myrelations += " page_previous_" + content.getPagePrevious();
			if (! content.getPageNext().equals("")) myrelations += " page_next_" + content.getPageNext();
			if (! content.getPageLast().equals("")) myrelations += " page_last_" + content.getPageLast();
			myselected = "";
			if (select_page_tops) {
				descendents = db.query_value("select count(*) from content where page_top='" + myid + "'");
				if (descendents > 0) {
					myrelations += " page_top";
					myselected += "jstree-clicked";
				}
			}
			mytype = "page";
			descendents = db.query_value("select count(*) from content where page_up='" + myid + "'");
			if (descendents > 0) {
				mytype = "pagefolder";
			}
%>
<li class="jstree-closed<%= myrelations %>" rel="<%= mytype %>" id="content_<%= myid %>"><a class="<%= myselected %>" href="#" title="<%= mytitle %> (<%= myid %>)"><%= mytitle %> (<%= myid %>)</a></li>
<%
			SQL = "select id,title,contentclass,version,version_master,device,page_top,page_up,page_first,page_previous,page_next,page_last,image1,image2,image3,file1,file2,file3,link1,link2,link3,server_filename from content where (id<>" + id + ") and (contentclass in ('page','product')) and (" + SQLtops + ") order by version, device, title, id";
			descendents = 0.0;
			%><%@ include file="structure.subtree.content.jsp.html" %><%

			if (! SQLorphans.equals("")) {
				orphans = db.query_value("select count(*) from content where (id<>" + id + ") and (contentclass in ('page','product')) and (" + SQLorphans + ")");
//				if (orphans > 0) {
					mytype = "pagefolder";
					myrelations = " root undraggable";
					myid = "000";
					myselected = "";
					mytitle = "" + mytext.display("content.structure.orphans");
					String mycount = "" + Common.intnumber(orphans);
					%><li class="jstree-closed<%= myrelations %>" rel="<%= mytype %>" id="<%= myid %>"><a class="<%= myselected %>" href="#" title="<%= mytitle %> (<%= mycount %>)"><%= mytitle %> (<%= mycount %>)</a></li><%
//				}
			}
		}
 	} else if (id.equals("000")) {
		id = myconfig.get(db, "default_page");
		String SQL = "select id,title,contentclass,version,version_master,device,page_top,page_up,page_first,page_previous,page_next,page_last,image1,image2,image3,file1,file2,file3,link1,link2,link3,server_filename from content where (id<>" + id + ") and (contentclass in ('page','product')) and (" + SQLorphans + ") order by version, device, title, id";
		descendents = -1.0;
		%><%@ include file="structure.subtree.content.jsp.html" %><%
	} else {
		String SQL = "";
		content = new Content();
		content.read(db, myconfig, id, "content", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		if (content.getUser()) {

			if (children != null) {
				SQL = "select id,title,contentclass,version,version_master,device,page_top,page_up,page_first,page_previous,page_next,page_last,image1,image2,image3,file1,file2,file3,link1,link2,link3,server_filename from content where (page_up='" + id + "') or ((page_top='" + id + "') and (" + SQLorphans + ")) order by title, version, device, id";
				descendents = 0.0;
				%><%@ include file="structure.subtree.content.jsp.html" %><%
				content = null;
			}
			children = null;

			if (content_versions != null) {
				SQL = "select id,title,contentclass,version,version_master,device,page_top,page_up,page_first,page_previous,page_next,page_last,image1,image2,image3,file1,file2,file3,link1,link2,link3 from content where version_master=" + id + " order by title, version, device, id";
				content_versions.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}

			if (content_devices != null) {
				SQL = "select id,title,contentclass,version,version_master,device,page_top,page_up,page_first,page_previous,page_next,page_last,image1,image2,image3,file1,file2,file3,link1,link2,link3 from content where version_master=" + id + " order by title, version, device, id";
				content_devices.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}

			if (content_additional != null) {
				SQL = "select id,title,contentclass,version,version_master,device,page_top,page_up,page_first,page_previous,page_next,page_last,image1,image2,image3,file1,file2,file3,link1,link2,link3 from content where id=" + id + " order by title, version, device, id";
				content_additional.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
			}

%><%@ include file="structure.subtree.jsp.html" %><%

		}
	}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>