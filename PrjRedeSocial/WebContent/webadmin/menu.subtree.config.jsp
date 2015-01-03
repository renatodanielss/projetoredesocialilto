<%@ include file="config.jsp" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Contentgroup" %><%@ page import="HardCore.Contenttype" %><%@ page import="HardCore.Element" %><%@ page import="HardCore.Version" %><%@ page import="HardCore.Imagegroup" %><%@ page import="HardCore.Imagetype" %><%@ page import="HardCore.Filegroup" %><%@ page import="HardCore.Filetype" %><%@ page import="HardCore.Linkgroup" %><%@ page import="HardCore.Linktype" %><%@ page import="HardCore.Productgroup" %><%@ page import="HardCore.Producttype" %><%@ page import="HardCore.Databases" %><%@ page import="HardCore.Usergroup" %><%@ page import="HardCore.Usertype" %><%@ page import="HardCore.Workflow" %><%@ page import="HardCore.Hostinggroup" %><%@ page import="HardCore.Hostingtype" %><%

LinkedHashMap children = new LinkedHashMap();	// null ~ new LinkedHashMap()
Content content_versions = new Content();	// null ~ new Content()
Content content_additional = new Content();	// null ~ new Content()
boolean content_additional_content = true;	// false ~ true
boolean content_additional_media = true;	// false ~ true
boolean select_page_tops = true;		// false ~ true

boolean single_menu_selection = false;
if (! adminuser.getMenuSelection().equals("")) {
	single_menu_selection = adminuser.getMenuSelection().equals("single");
} else if (! myconfig.get(db, "use_menu_selection").equals("")) {
	single_menu_selection = myconfig.get(db, "use_menu_selection").equals("single");
}

String menu = myrequest.getParameter("menu");
String contentpackage = myrequest.getParameter("contentpackage");
String contentclass = myrequest.getParameter("contentclass");
String contentbundle = myrequest.getParameter("contentbundle");
String contentgroup = myrequest.getParameter("contentgroup");
String contenttype = myrequest.getParameter("contenttype");
String version = myrequest.getParameter("version");
String status = myrequest.getParameter("status");
String userclass = myrequest.getParameter("userclass");
String usergroup = myrequest.getParameter("usergroup");
String usertype = myrequest.getParameter("usertype");
String hostingclass = myrequest.getParameter("hostingclass");
String hostinggroup = myrequest.getParameter("hostinggroup");
String hostingtype = myrequest.getParameter("hostingtype");
if (! myrequest.parameterExists("contentpackage")) contentpackage = mysession.get("admin_contentpackage");
if (! myrequest.parameterExists("contentclass")) contentclass = mysession.get("admin_contentclass");
if (! myrequest.parameterExists("contentbundle")) contentbundle = mysession.get("admin_contentbundle");
if (! myrequest.parameterExists("contentgroup")) contentgroup = mysession.get("admin_contentgroup");
if (! myrequest.parameterExists("contenttype")) contenttype = mysession.get("admin_contenttype");
if (! myrequest.parameterExists("version")) version = mysession.get("admin_version");
if (! myrequest.parameterExists("status")) status = mysession.get("admin_status");
if (! myrequest.parameterExists("userclass")) userclass = mysession.get("admin_userclass");
if (! myrequest.parameterExists("usergroup")) usergroup = mysession.get("admin_usergroup");
if (! myrequest.parameterExists("usertype")) usertype = mysession.get("admin_usertype");
if (! myrequest.parameterExists("hostingclass")) hostingclass = mysession.get("admin_hostingclass");
if (! myrequest.parameterExists("hostinggroup")) hostinggroup = mysession.get("admin_hostinggroup");
if (! myrequest.parameterExists("hostingtype")) hostingtype = mysession.get("admin_hostingtype");
if (contentpackage.equals("")) contentpackage = " ";
if (contentclass.equals("")) contentclass = " ";
if (contentbundle.equals("")) contentbundle = " ";
if (contentgroup.equals("")) contentgroup = " ";
if (contenttype.equals("")) contenttype = " ";
if (version.equals("")) version = " ";
if (status.equals("")) status = " ";
if (userclass.equals("")) userclass = " ";
if (usergroup.equals("")) usergroup = " ";
if (usertype.equals("")) usertype = " ";

String reqcontentpackage = "" + contentpackage;
String reqcontentclass = "" + contentclass;
String reqcontentbundle = "" + contentbundle;
String reqcontentgroup = "" + contentgroup;
String reqcontenttype = "" + contenttype;
String reqversion = "" + version;
String reqstatus = "" + status;
String requserclass = "" + userclass;
String requsergroup = "" + usergroup;
String requsertype = "" + usertype;
String reqhostingclass = "" + hostingclass;
String reqhostinggroup = "" + hostinggroup;
String reqhostingtype = "" + hostingtype;
if (single_menu_selection) {
	contentpackage = " ";
	contentclass = " ";
	contentbundle = " ";
	contentgroup = " ";
	contenttype = " ";
	version = " ";
	status = " ";
	userclass = "";
	usergroup = "";
	usertype = "";
	reqcontentpackage = myrequest.getParameter("contentpackage");
	reqcontentclass = myrequest.getParameter("contentclass");
	reqcontentbundle = myrequest.getParameter("contentbundle");
	reqcontentgroup = myrequest.getParameter("contentgroup");
	reqcontenttype = myrequest.getParameter("contenttype");
	reqversion = myrequest.getParameter("version");
	reqstatus = myrequest.getParameter("status");
	requserclass = myrequest.getParameter("userclass");
	requsergroup = myrequest.getParameter("usergroup");
	requsertype = myrequest.getParameter("usertype");
	reqhostingclass = myrequest.getParameter("hostingclass");
	reqhostinggroup = myrequest.getParameter("hostinggroup");
	reqhostingtype = myrequest.getParameter("hostingtype");
}

String mode = myrequest.getParameter("mode");
if (mode.equals("structure")) {
	children = new LinkedHashMap();		// null ~ new LinkedHashMap()
	content_versions = null;		// null ~ new Content()
	content_additional = null;		// null ~ new Content()
	content_additional_content = false;	// false ~ true
	content_additional_media = false;	// false ~ true
	select_page_tops = true;		// false ~ true
} else { // if (mode.equals("menu"))
	children = new LinkedHashMap();		// null ~ new LinkedHashMap()
	content_versions = null;		// null ~ new Content()
	content_additional = null;		// null ~ new Content()
	content_additional_content = false;	// false ~ true
	content_additional_media = false;	// false ~ true
	select_page_tops = false;		// false ~ true
}

String id = myrequest.getParameter("id");
Double descendents = 0.0;



if (id.startsWith("DEBUG")) {
	// DEBUG





} else if (id.startsWith("menuitem_configuration_workflows")) {
	String SQL = "select distinct title from workflow where title<>'-orders-' order by title";
	Workflow workflows = new Workflow(mytext);
	workflows.records(db, SQL);
	while (workflows.records(db, "")) {
		String myworkflow = workflows.getTitle();
		String myid = "" + myworkflow;
		String mytitle = "" + myworkflow;
		if (mytitle.equals("")) {
			mytitle = " ";
		}
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if (myrequest.getParameter("workflow").equals(myworkflow)) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_configuration_workflows_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/workflows/index.jsp?menu=config&workflow=<%= URLEncoder.encode(myworkflow, myconfig.get(db, "charset")) %>&<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
	if (License.valid(db, myconfig, "ecommerce") && (myconfig.get(db, "use_workflow_orders").equals("yes"))) {
		String myworkflow = "-orders-";
		String myid = "" + myworkflow;
		String mytitle = "" + mytext.display("workflows.title.orders");
		if (mytitle.equals("")) {
			mytitle = " ";
		}
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if (myrequest.getParameter("workflow").equals(myworkflow)) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_configuration_workflows_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/workflows/index.jsp?menu=config&workflow=<%= URLEncoder.encode(myworkflow, myconfig.get(db, "charset")) %>&<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}





}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>