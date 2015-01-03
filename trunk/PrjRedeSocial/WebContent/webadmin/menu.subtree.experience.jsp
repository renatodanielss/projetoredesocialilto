<%@ include file="config.jsp" %><%@ page import="HardCore.Segment" %><%@ page import="HardCore.Usertest" %><%@ page import="HardCore.RequireUser" %><%

boolean single_menu_selection = false;
if (! adminuser.getMenuSelection().equals("")) {
	single_menu_selection = adminuser.getMenuSelection().equals("single");
} else if (! myconfig.get(db, "use_menu_selection").equals("")) {
	single_menu_selection = myconfig.get(db, "use_menu_selection").equals("single");
}

String menu = myrequest.getParameter("menu");
String segment = myrequest.getParameter("segment");
String usertest = myrequest.getParameter("usertest");
String heatmap = myrequest.getParameter("heatmap");
if (! myrequest.parameterExists("segment")) segment = mysession.get("admin_segment");
if (! myrequest.parameterExists("usertest")) usertest = mysession.get("admin_usertest");
if (! myrequest.parameterExists("heatmap")) heatmap = mysession.get("admin_heatmap");
if (segment.equals("")) segment = "";
if (usertest.equals("")) usertest = "";
if (heatmap.equals("")) heatmap = "";

String reqsegment = "" + segment;
String requsertest = "" + usertest;
String reqheatmap = "" + heatmap;
if (single_menu_selection) {
	segment = " ";
	usertest = " ";
	heatmap = " ";
	reqsegment = myrequest.getParameter("segment");
	requsertest = myrequest.getParameter("usertest");
	reqheatmap = myrequest.getParameter("heatmap");
}

String mode = myrequest.getParameter("mode");

String id = myrequest.getParameter("id");



if (id.startsWith("DEBUG")) {
	// DEBUG





} else if (! RequireUser.isExperienceAdministrator(mysession, myconfig, db)) {
	// ignore





} else if (id.startsWith("menuitem_experience_segments")) {
	usertest = " ";
	heatmap = " ";

	int countsegments = 0;
	String SQL = "select distinct segmentid from segments order by segmentid";
	Segment segments = new Segment();
	segments.records(db, SQL);
	while (segments.records(db, "")) {
			countsegments += 1;
			String mysegment = segments.getSegmentId();
			String myid = "" + mysegment;
			String mytitle = "" + mysegment;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((myrequest.getParameter("url").indexOf("/content/") >= 0) && (reqsegment.equals(mysegment))) {
				myselected += "jstree-wcm-menu-selected";
			} else if ((myrequest.getParameter("url").indexOf("/content/") >= 0) && (mysession.get("admin_segment").equals(mysegment))) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="segment_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?segment=<%= URLEncoder.encode(mysegment, myconfig.get(db, "charset")) %>&usertest=<%= URLEncoder.encode(usertest, myconfig.get(db, "charset")) %>&heatmap=<%= URLEncoder.encode(heatmap, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&<%= Math.random() %>" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}

	if (countsegments > 0) {
		String mysegment = "-";
		String mytitle = mytext.display("menu.experience.segments.usage");
		String myid = "usage";
		String myrelations = " pagefolder";
		String mytype = "pagefolder";
		String myselected = "";
		if (myrequest.getParameter("url").indexOf("/usage/usersegments") >= 0) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="segment_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/usage/usersegments.jsp?menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&<%= Math.random() %>" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}





} else if (id.startsWith("menuitem_experience_usertests")) {
	segment = " ";
	heatmap = " ";

	int countusertests = 0;
	String SQL = "select distinct usertest from usertests order by usertest";
	Usertest usertests = new Usertest();
	usertests.records(db, SQL);
	while (usertests.records(db, "")) {
			countusertests += 1;
			String myusertest = usertests.getUsertest();
			String myid = "" + myusertest;
			String mytitle = "" + myusertest;
			String myrelations = " folder";
			String mytype = "folder";
			String myselected = "";
%>
<li class="jstree-open<%= myrelations %>" rel="<%= mytype %>" id="usertest_<%= myid %>"><a href="#" title="<%= mytitle %>"><%= mytitle %></a>
<ul>
<%
			mytitle = mytext.display("menu.experience.usertests.content");
			myrelations = " page";
			mytype = "page";
			myselected = "";
			if ((myrequest.getParameter("url").indexOf("/content/") >= 0) && (requsertest.equals(myusertest))) {
				myselected += "jstree-wcm-menu-selected";
			} else if ((myrequest.getParameter("url").indexOf("/content/") >= 0) && (mysession.get("admin_usertest").equals(myusertest))) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="usertest_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?segment=<%= URLEncoder.encode(segment, myconfig.get(db, "charset")) %>&usertest=<%= URLEncoder.encode(myusertest, myconfig.get(db, "charset")) %>&heatmap=<%= URLEncoder.encode(heatmap, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&<%= Math.random() %>" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
			mytitle = mytext.display("menu.experience.usertests.results");
			myrelations = " page";
			mytype = "page";
			myselected = "";
			if ((myrequest.getParameter("url").indexOf("/experience/usertest") >= 0) && (id.startsWith("menuitem_experience_usertests")) && (requsertest.equals(myusertest))) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="usertest_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/experience/usertest.jsp?segment=<%= URLEncoder.encode(segment, myconfig.get(db, "charset")) %>&usertest=<%= URLEncoder.encode(myusertest, myconfig.get(db, "charset")) %>&heatmap=<%= URLEncoder.encode(heatmap, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&<%= Math.random() %>" title="<%= mytitle %>"><%= mytitle %></a></li>
</ul>
</li>
<%
	}

	if (countusertests > 0) {
		String mysegment = "-";
		String mytitle = mytext.display("menu.experience.usertests.usage");
		String myid = "usage";
		String myrelations = " pagefolder";
		String mytype = "pagefolder";
		String myselected = "";
		if (myrequest.getParameter("url").indexOf("/usage/usertests") >= 0) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="segment_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/usage/usertests.jsp?menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&<%= Math.random() %>" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}





}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>