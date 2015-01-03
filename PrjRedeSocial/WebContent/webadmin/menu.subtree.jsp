<%@ include file="config.jsp" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Contentgroup" %><%@ page import="HardCore.Contenttype" %><%@ page import="HardCore.Element" %><%@ page import="HardCore.Version" %><%@ page import="HardCore.Imagegroup" %><%@ page import="HardCore.Imagetype" %><%@ page import="HardCore.Filegroup" %><%@ page import="HardCore.Filetype" %><%@ page import="HardCore.Linkgroup" %><%@ page import="HardCore.Linktype" %><%@ page import="HardCore.Productgroup" %><%@ page import="HardCore.Producttype" %><%@ page import="HardCore.Databases" %><%@ page import="HardCore.Usergroup" %><%@ page import="HardCore.Usertype" %><%@ page import="HardCore.Workflow" %><%@ page import="HardCore.Hostinggroup" %><%@ page import="HardCore.Hostingtype" %><%

LinkedHashMap children = new LinkedHashMap();	// null ~ new LinkedHashMap()
Content content_versions = new Content();	// null ~ new Content()
Content content_devices = new Content();	// null ~ new Content()
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
String stock = myrequest.getParameter("stock");
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
if (! myrequest.parameterExists("stock")) stock = mysession.get("admin_stock");
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
if (stock.equals("")) stock = " ";
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
String reqstock = "" + stock;
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
	stock = " ";
	userclass = " ";
	usergroup = " ";
	usertype = " ";
	reqcontentpackage = myrequest.getParameter("contentpackage");
	reqcontentclass = myrequest.getParameter("contentclass");
	reqcontentbundle = myrequest.getParameter("contentbundle");
	reqcontentgroup = myrequest.getParameter("contentgroup");
	reqcontenttype = myrequest.getParameter("contenttype");
	reqversion = myrequest.getParameter("version");
	reqstatus = myrequest.getParameter("status");
	reqstock = myrequest.getParameter("stock");
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
	content_devices = null;			// null ~ new Content()
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
Double orphans = 0.0;



if (id.startsWith("DEBUG")) {
	// DEBUG





} else if ((id.startsWith("menuitem_content_pages_bundles")) || (id.startsWith("menuitem_content_elements_bundles")) || (id.startsWith("menuitem_library_images_bundles")) || (id.startsWith("menuitem_library_files_bundles")) || (id.startsWith("menuitem_library_links_bundles"))) {
	String mycontentclass = "page";
	if (id.startsWith("menuitem_content_elements_bundles")) {
		if ((contentclass.equals("")) || (contentclass.equals(" ")) || (contentclass.equals("-")) || (contentclass.equals("page")) || (contentclass.equals("script")) || (contentclass.equals("stylesheet")) || (contentclass.equals("template")) || (contentclass.equals("image")) || (contentclass.equals("file")) || (contentclass.equals("link")) || (contentclass.equals("product")) || (contentclass.equals("element")) || (contentclass.equals("page,element,template,stylesheet,script"))) {
			mycontentclass = "element";
		} else {
			mycontentclass = "" + contentclass;
		}
	} else if (id.startsWith("menuitem_library_images_bundles")) {
		mycontentclass = "image";
	} else if (id.startsWith("menuitem_library_files_bundles")) {
		mycontentclass = "file";
	} else if (id.startsWith("menuitem_library_links_bundles")) {
		mycontentclass = "link";
	}
	String myparentid = "";
	if ((! single_menu_selection) && (myparentid.equals(""))) {
		String mycontentbundle = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_content_pages_bundles")) && (reqcontentclass.equals("page")) && (reqcontentbundle.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_content_elements_bundles")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqcontentbundle.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_images_bundles")) && (reqcontentclass.equals("image")) && (reqcontentbundle.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_files_bundles")) && (reqcontentclass.equals("file")) && (reqcontentbundle.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_links_bundles")) && (reqcontentclass.equals("link")) && (reqcontentbundle.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_content_<%= mycontentclass %>s_bundles_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(mycontentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	if (true) {
		String mycontentbundle = "-";
		String mytitle = "" + mytext.display("none");
		String myid = "none";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_content_pages_bundles")) && (reqcontentclass.equals("page")) && (reqcontentbundle.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_content_elements_bundles")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqcontentbundle.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_images_bundles")) && (reqcontentclass.equals("image")) && (reqcontentbundle.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_files_bundles")) && (reqcontentclass.equals("file")) && (reqcontentbundle.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_links_bundles")) && (reqcontentclass.equals("link")) && (reqcontentbundle.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_content_<%= mycontentclass %>s_bundles_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(mycontentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	String SQL = "select distinct contentbundle from content where " + db.is_not_blank("contentbundle") + " order by contentbundle";
	LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
	for (int i=0; i<rows.size(); i++) {
		HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
		String mycontentbundle = "" + row.get("contentbundle");
		String myid = "" + mycontentbundle.replaceAll(" ", "_").replaceAll("&", "_").replaceAll("/", "_");
		String mytitle = "" + mycontentbundle;
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_content_pages_bundles")) && (reqcontentclass.equals("page")) && (reqcontentbundle.equals(mycontentbundle)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_content_elements_bundles")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqcontentbundle.equals(mycontentbundle)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_images_bundles")) && (reqcontentclass.equals("image")) && (reqcontentbundle.equals(mycontentbundle)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_files_bundles")) && (reqcontentclass.equals("file")) && (reqcontentbundle.equals(mycontentbundle)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_links_bundles")) && (reqcontentclass.equals("link")) && (reqcontentbundle.equals(mycontentbundle)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_content_<%= mycontentclass %>s_bundles_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(mycontentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}





} else if ((id.startsWith("menuitem_content_pages_groups")) || (id.startsWith("menuitem_content_elements_groups")) || (id.matches("^menuitem_content_(.+)_groups_(.+)$"))) {
	String mycontentclass = "page";
	if (id.startsWith("menuitem_content_elements_groups")) {
		if ((contentclass.equals("")) || (contentclass.equals(" ")) || (contentclass.equals("-")) || (contentclass.equals("page")) || (contentclass.equals("script")) || (contentclass.equals("stylesheet")) || (contentclass.equals("template")) || (contentclass.equals("image")) || (contentclass.equals("file")) || (contentclass.equals("link")) || (contentclass.equals("product")) || (contentclass.equals("element")) || (contentclass.equals("page,element,template,stylesheet,script"))) {
			mycontentclass = "element";
		} else {
			mycontentclass = "" + contentclass;
		}
	}
	String myparentid = "";
	if (id.startsWith("menuitem_content_pages_groups_")) {
		myparentid = id.replaceAll("^menuitem_content_pages_groups_", "").replaceAll("_", " ");
	} else if (id.startsWith("menuitem_content_elements_groups_")) {
		myparentid = id.replaceAll("^menuitem_content_elements_groups_", "").replaceAll("_", " ");
	} else if (id.matches("^menuitem_content_(.+)_groups_(.+)$")) {
		myparentid = id.replaceAll("^menuitem_content_(.+)_groups_", "").replaceAll("_", " ");
	}
	if ((! single_menu_selection) && (myparentid.equals(""))) {
		String mycontentgroup = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_content_pages_groups")) && (reqcontentclass.equals("page")) && (reqcontentgroup.equals(" "))) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_content_elements_groups")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqcontentgroup.equals(" "))) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_content_<%= mycontentclass %>s_groups_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	if (true) {
		String mycontentgroup = "-";
		String mytitle = "" + mytext.display("none");
		String myid = "none";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_content_pages_groups")) && (reqcontentclass.equals("page")) && (reqcontentgroup.equals("-"))) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_content_elements_groups")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqcontentgroup.equals("-"))) {
			myselected += "jstree-wcm-menu-selected";
		}
		if (myparentid.equals("")) {
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_content_<%= mycontentclass %>s_groups_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}
%><%
	String SQL = "select * from contentgroups where (parentgroup is null) or (parentgroup='') order by contentgroup";
	if (! myparentid.equals("")) {
		SQL = "select * from contentgroups where (parentgroup='" + Common.SQL_clean(myparentid) + "') order by contentgroup";
	}
	Contentgroup contentgroups = new Contentgroup();
	contentgroups.records(db, SQL);
	while (contentgroups.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontentgroup = contentgroups.getContentgroup();
			String myid = "" + mycontentgroup.replaceAll(" ", "_").replaceAll("&", "_").replaceAll("/", "_");
			String mytitle = "" + mycontentgroup;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((id.startsWith("menuitem_content_pages_groups")) && (reqcontentclass.equals("page")) && (reqcontentgroup.equals(mycontentgroup))) {
				myselected += "jstree-wcm-menu-selected";
			} else if ((id.startsWith("menuitem_content_elements_groups")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqcontentgroup.equals(mycontentgroup))) {
				myselected += "jstree-wcm-menu-selected";
			}
			descendents = db.query_value("select count(*) from contentgroups where parentgroup='" + Common.SQL_clean(mycontentgroup) + "'");
			if (descendents > 0) {
				mytype = "pagefolder";
				myrelations = " jstree-closed";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_content_<%= mycontentclass %>s_groups_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if ((id.startsWith("menuitem_content_pages_types")) || (id.startsWith("menuitem_content_elements_types")) || (id.matches("^menuitem_content_(.+)_types_(.+)$"))) {
	String mycontentclass = "page";
	if (id.startsWith("menuitem_content_elements_types")) {
		if ((contentclass.equals("")) || (contentclass.equals(" ")) || (contentclass.equals("-")) || (contentclass.equals("page")) || (contentclass.equals("script")) || (contentclass.equals("stylesheet")) || (contentclass.equals("template")) || (contentclass.equals("image")) || (contentclass.equals("file")) || (contentclass.equals("link")) || (contentclass.equals("product")) || (contentclass.equals("element")) || (contentclass.equals("page,element,template,stylesheet,script"))) {
			mycontentclass = "element";
		} else {
			mycontentclass = "" + contentclass;
		}
	}
	String myparentid = "";
	if (id.startsWith("menuitem_content_pages_types_")) {
		myparentid = id.replaceAll("^menuitem_content_pages_types_", "").replaceAll("_", " ");
	} else if (id.startsWith("menuitem_content_elements_types_")) {
		myparentid = id.replaceAll("^menuitem_content_elements_types_", "").replaceAll("_", " ");
	} else if (id.matches("^menuitem_content_(.+)_types_(.+)$")) {
		myparentid = id.replaceAll("^menuitem_content_(.+)_types_", "").replaceAll("_", " ");
	}
	if ((! single_menu_selection) && (myparentid.equals(""))) {
		String mycontenttype = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_content_pages_types")) && (reqcontentclass.equals("page")) && (reqcontenttype.equals(" "))) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_content_elements_types")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqcontenttype.equals(" "))) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_content_<%= mycontentclass %>s_types_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	if (true) {
		String mycontenttype = "-";
		String mytitle = "" + mytext.display("none");
		String myid = "none";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_content_pages_types")) && (reqcontentclass.equals("page")) && (reqcontenttype.equals("-"))) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_content_elements_types")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqcontenttype.equals("-"))) {
			myselected += "jstree-wcm-menu-selected";
		}
		if (myparentid.equals("")) {
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_content_<%= mycontentclass %>s_types_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}
%><%
	String SQL = "select * from contenttypes where (parenttype is null) or (parenttype='') order by contenttype";
	if (! myparentid.equals("")) {
		SQL = "select * from contenttypes where (parenttype='" + Common.SQL_clean(myparentid) + "') order by contenttype";
	}
	Contenttype contenttypes = new Contenttype();
	contenttypes.records(db, SQL);
	while (contenttypes.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontenttype = contenttypes.getContenttype();
			String myid = "" + mycontenttype.replaceAll(" ", "_").replaceAll("&", "_").replaceAll("/", "_");
			String mytitle = "" + mycontenttype;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((id.startsWith("menuitem_content_pages_types")) && (reqcontentclass.equals("page")) && (reqcontenttype.equals(mycontenttype))) {
				myselected += "jstree-wcm-menu-selected";
			} else if ((id.startsWith("menuitem_content_elements_types")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqcontenttype.equals(mycontenttype))) {
				myselected += "jstree-wcm-menu-selected";
			}
			descendents = db.query_value("select count(*) from contenttypes where parenttype='" + Common.SQL_clean(mycontenttype) + "'");
			if (descendents > 0) {
				mytype = "pagefolder";
				myrelations = " jstree-closed";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_content_<%= mycontentclass %>s_types_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if ((id.startsWith("menuitem_content_pages_versions")) || (id.startsWith("menuitem_content_elements_versions")) || (id.startsWith("menuitem_library_images_versions")) || (id.startsWith("menuitem_library_files_versions")) || (id.startsWith("menuitem_library_links_versions")) || (id.startsWith("menuitem_library_links_versions"))) {
	String mycontentclass = "page";
	if (id.startsWith("menuitem_content_elements_versions")) {
		if ((contentclass.equals("")) || (contentclass.equals(" ")) || (contentclass.equals("-")) || (contentclass.equals("page")) || (contentclass.equals("script")) || (contentclass.equals("stylesheet")) || (contentclass.equals("template")) || (contentclass.equals("image")) || (contentclass.equals("file")) || (contentclass.equals("link")) || (contentclass.equals("product")) || (contentclass.equals("element")) || (contentclass.equals("page,element,template,stylesheet,script"))) {
			mycontentclass = "element";
		} else {
			mycontentclass = "" + contentclass;
		}
	} else if (id.startsWith("menuitem_library_images_versions")) {
		mycontentclass = "image";
	} else if (id.startsWith("menuitem_library_files_versions")) {
		mycontentclass = "file";
	} else if (id.startsWith("menuitem_library_links_versions")) {
		mycontentclass = "link";
	}
	if (! single_menu_selection) {
		String myversion = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_content_pages_versions")) && (reqcontentclass.equals("page")) && (reqversion.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_content_elements_versions")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqversion.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_images_versions")) && (reqcontentclass.equals("image")) && (reqversion.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_files_versions")) && (reqcontentclass.equals("file")) && (reqversion.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_links_versions")) && (reqcontentclass.equals("link")) && (reqversion.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>version_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(myversion, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	if (true) {
		String myversion = "-";
		String mytitle = "" + mytext.display("none");
		String myid = "none";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_content_pages_versions")) && (reqcontentclass.equals("page")) && (reqversion.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_content_elements_versions")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqversion.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_images_versions")) && (reqcontentclass.equals("image")) && (reqversion.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_files_versions")) && (reqcontentclass.equals("file")) && (reqversion.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_links_versions")) && (reqcontentclass.equals("link")) && (reqversion.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>version_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(myversion, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	String SQL = "select * from versions order by version";
	Version versions = new Version();
	versions.records(db, SQL);
	while (versions.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || versions.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String myversion = versions.getVersion();
			String myid = "" + myversion;
			String mytitle = "" + myversion;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((id.startsWith("menuitem_content_pages_versions")) && (reqcontentclass.equals("page")) && (reqversion.equals(myversion)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
				myselected += "jstree-wcm-menu-selected";
			} else if ((id.startsWith("menuitem_content_elements_versions")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqversion.equals(myversion)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
				myselected += "jstree-wcm-menu-selected";
			} else if ((id.startsWith("menuitem_library_images_versions")) && (reqcontentclass.equals("image")) && (reqversion.equals(myversion)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
				myselected += "jstree-wcm-menu-selected";
			} else if ((id.startsWith("menuitem_library_files_versions")) && (reqcontentclass.equals("file")) && (reqversion.equals(myversion)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
				myselected += "jstree-wcm-menu-selected";
			} else if ((id.startsWith("menuitem_library_links_versions")) && (reqcontentclass.equals("link")) && (reqversion.equals(myversion)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0)) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>version_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(myversion, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_content_elements_classes")) {
	String mycontentclass = "element";
	if (! single_menu_selection) {
		String myelement = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if (reqcontentclass.equals("element")) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="contentclass_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= URLEncoder.encode(mycontentclass, myconfig.get(db, "charset")) %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	String SQL = "select * from elements order by element";
	Element elements = new Element();
	elements.records(db, SQL);
	while (elements.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || elements.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String myelement = elements.getElement();
			String myid = "" + myelement;
			String mytitle = "" + myelement;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if (((! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqcontentclass.equals(myelement))) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="contentclass_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= URLEncoder.encode(myelement, myconfig.get(db, "charset")) %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if ((id.startsWith("menuitem_content_packages")) || (id.startsWith("menuitem_library_packages"))) {
	String mycontentclass = "" + contentclass;
	if ((id.startsWith("menuitem_content_packages")) && (mycontentclass.equals(" "))) {
		mycontentclass = "page,element,template,stylesheet,script";
	}
	if ((id.startsWith("menuitem_library_packages")) && (mycontentclass.equals(" "))) {
		mycontentclass = "image,file,link";
	}
	if (! single_menu_selection) {
		String mycontentpackage = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if (reqcontentpackage.equals(" ")) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="package_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(mycontentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= URLEncoder.encode(mycontentclass, myconfig.get(db, "charset")) %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	if (true) {
		String mycontentpackage = "-";
		String mytitle = "" + mytext.display("none");
		String myid = "none";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if (reqcontentpackage.equals("-")) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="package_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(mycontentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= URLEncoder.encode(mycontentclass, myconfig.get(db, "charset")) %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	Content content = new Content();
	ArrayList contentpackages = content.getContentpackages(db);
	for (int i=0; i<contentpackages.size(); i++) {
		String mycontentpackage = "" + contentpackages.get(i);
		String myid = "" + mycontentpackage;
		String mytitle = "" + mycontentpackage;
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if (reqcontentpackage.equals(mycontentpackage)) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="package_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(mycontentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= URLEncoder.encode(mycontentclass, myconfig.get(db, "charset")) %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}





} else if ((id.startsWith("menuitem_content_bundles")) || (id.startsWith("menuitem_library_bundles"))) {
	String mycontentclass = " ";
	if ((id.startsWith("menuitem_content_bundles")) && (mycontentclass.equals(" "))) {
		mycontentclass = "page,element,template,stylesheet,script";
	}
	if ((id.startsWith("menuitem_library_bundles")) && (mycontentclass.equals(" "))) {
		mycontentclass = "image,file,link";
	}
	if (! single_menu_selection) {
		String mycontentbundle = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if (reqcontentbundle.equals(" ")) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="bundle_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=+&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(mycontentbundle, myconfig.get(db, "charset")) %>&contentgroup=+&contenttype=+&status=+&stock=+&version=+&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	if (true) {
		String mycontentbundle = "-";
		String mytitle = "" + mytext.display("none");
		String myid = "none";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if (reqcontentbundle.equals("-")) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="bundle_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=+&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(mycontentbundle, myconfig.get(db, "charset")) %>&contentgroup=+&contenttype=+&status=+&stock=+&version=+&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	String SQL = "select distinct contentbundle from content where " + db.is_not_blank("contentbundle") + " order by contentbundle";
	LinkedHashMap<String,HashMap<String,String>> rows = db.query_records(SQL);
	for (int i=0; i<rows.size(); i++) {
		HashMap<String,String> row = (HashMap<String,String>)rows.get("" + i);
		String mycontentbundle = "" + row.get("contentbundle");
		String myid = "" + mycontentbundle.replaceAll(" ", "_").replaceAll("&", "_").replaceAll("/", "_");
		String mytitle = "" + mycontentbundle;
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if (reqcontentbundle.equals(mycontentbundle)) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="bundle_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=+&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(mycontentbundle, myconfig.get(db, "charset")) %>&contentgroup=+&contenttype=+&status=+&stock=+&version=+&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}





} else if (id.startsWith("menuitem_library_images_groups")) {
	String mycontentclass = "image";
	String myparentid = "";
	if (id.startsWith("menuitem_library_images_groups_")) {
		myparentid = id.replaceAll("^menuitem_library_images_groups_", "").replaceAll("_", " ");
	}
	if ((! single_menu_selection) && (myparentid.equals(""))) {
		String mycontentgroup = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_library_images_groups")) && (reqcontentclass.equals("image")) && (reqcontentgroup.equals(" "))) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>group_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	if (true) {
		String mycontentgroup = "-";
		String mytitle = "" + mytext.display("none");
		String myid = "none";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_library_images_groups")) && (reqcontentclass.equals("image")) && (reqcontentgroup.equals("-"))) {
			myselected += "jstree-wcm-menu-selected";
		}
		if (myparentid.equals("")) {
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>group_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}
%><%
	String SQL = "select * from imagegroups where (parentgroup is null) or (parentgroup='') order by imagegroup";
	if (! myparentid.equals("")) {
		SQL = "select * from imagegroups where (parentgroup='" + Common.SQL_clean(myparentid) + "') order by imagegroup";
	}
	Imagegroup contentgroups = new Imagegroup();
	contentgroups.records(db, SQL);
	while (contentgroups.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontentgroup = contentgroups.getImagegroup();
			String myid = "" + mycontentgroup.replaceAll(" ", "_").replaceAll("&", "_").replaceAll("/", "_");
			String mytitle = "" + mycontentgroup;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((id.startsWith("menuitem_library_images_groups")) && (reqcontentclass.equals("image")) && (reqcontentgroup.equals(mycontentgroup))) {
				myselected += "jstree-wcm-menu-selected";
			}
			descendents = db.query_value("select count(*) from imagegroups where parentgroup='" + Common.SQL_clean(mycontentgroup) + "'");
			if (descendents > 0) {
				mytype = "pagefolder";
				myrelations = " jstree-closed";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_library_images_groups_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_library_images_types")) {
	String mycontentclass = "image";
	String myparentid = "";
	if (id.startsWith("menuitem_library_images_types_")) {
		myparentid = id.replaceAll("^menuitem_library_images_types_", "").replaceAll("_", " ");
	}
	if ((! single_menu_selection) && (myparentid.equals(""))) {
		String mycontenttype = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_library_images_types")) && (reqcontentclass.equals("image")) && (reqcontenttype.equals(" "))) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>type_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	if (true) {
		String mycontenttype = "-";
		String mytitle = "" + mytext.display("none");
		String myid = "none";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_library_images_types")) && (reqcontentclass.equals("image")) && (reqcontenttype.equals("-"))) {
			myselected += "jstree-wcm-menu-selected";
		}
		if (myparentid.equals("")) {
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>type_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}
%><%
	String SQL = "select * from imagetypes where (parenttype is null) or (parenttype='') order by imagetype";
	if (! myparentid.equals("")) {
		SQL = "select * from imagetypes where (parenttype='" + Common.SQL_clean(myparentid) + "') order by imagetype";
	}
	Imagetype contenttypes = new Imagetype();
	contenttypes.records(db, SQL);
	while (contenttypes.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontenttype = contenttypes.getImagetype();
			String myid = "" + mycontenttype.replaceAll(" ", "_").replaceAll("&", "_").replaceAll("/", "_");
			String mytitle = "" + mycontenttype;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((id.startsWith("menuitem_library_images_types")) && (reqcontentclass.equals("image")) && (reqcontenttype.equals(mycontenttype))) {
				myselected += "jstree-wcm-menu-selected";
			}
			descendents = db.query_value("select count(*) from imagetypes where parenttype='" + Common.SQL_clean(mycontenttype) + "'");
			if (descendents > 0) {
				mytype = "pagefolder";
				myrelations = " jstree-closed";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_library_images_types_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_library_files_groups")) {
	String mycontentclass = "file";
	String myparentid = "";
	if (id.startsWith("menuitem_library_files_groups_")) {
		myparentid = id.replaceAll("^menuitem_library_files_groups_", "").replaceAll("_", " ");
	}
	if ((! single_menu_selection) && (myparentid.equals(""))) {
		String mycontentgroup = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_library_files_groups")) && (reqcontentclass.equals("file")) && (reqcontentgroup.equals(" "))) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>group_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	if (true) {
		String mycontentgroup = "-";
		String mytitle = "" + mytext.display("none");
		String myid = "none";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_library_files_groups")) && (reqcontentclass.equals("file")) && (reqcontentgroup.equals("-"))) {
			myselected += "jstree-wcm-menu-selected";
		}
		if (myparentid.equals("")) {
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>group_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}
%><%
	String SQL = "select * from filegroups where (parentgroup is null) or (parentgroup='') order by filegroup";
	if (! myparentid.equals("")) {
		SQL = "select * from filegroups where (parentgroup='" + Common.SQL_clean(myparentid) + "') order by filegroup";
	}
	Filegroup contentgroups = new Filegroup();
	contentgroups.records(db, SQL);
	while (contentgroups.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontentgroup = contentgroups.getFilegroup();
			String myid = "" + mycontentgroup.replaceAll(" ", "_").replaceAll("&", "_").replaceAll("/", "_");
			String mytitle = "" + mycontentgroup;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((id.startsWith("menuitem_library_files_groups")) && (reqcontentclass.equals("file")) && (reqcontentgroup.equals(mycontentgroup))) {
				myselected += "jstree-wcm-menu-selected";
			}
			descendents = db.query_value("select count(*) from filegroups where parentgroup='" + Common.SQL_clean(mycontentgroup) + "'");
			if (descendents > 0) {
				mytype = "pagefolder";
				myrelations = " jstree-closed";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_library_files_groups_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_library_files_types")) {
	String mycontentclass = "file";
	String myparentid = "";
	if (id.startsWith("menuitem_library_files_types_")) {
		myparentid = id.replaceAll("^menuitem_library_files_types_", "").replaceAll("_", " ");
	}
	if ((! single_menu_selection) && (myparentid.equals(""))) {
		String mycontenttype = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_library_files_types")) && (reqcontentclass.equals("file")) && (reqcontenttype.equals(" "))) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>type_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	if (true) {
		String mycontenttype = "-";
		String mytitle = "" + mytext.display("none");
		String myid = "none";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_library_files_types")) && (reqcontentclass.equals("file")) && (reqcontenttype.equals("-"))) {
			myselected += "jstree-wcm-menu-selected";
		}
		if (myparentid.equals("")) {
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>type_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}
%><%
	String SQL = "select * from filetypes where (parenttype is null) or (parenttype='') order by filetype";
	if (! myparentid.equals("")) {
		SQL = "select * from filetypes where (parenttype='" + Common.SQL_clean(myparentid) + "') order by filetype";
	}
	Filetype contenttypes = new Filetype();
	contenttypes.records(db, SQL);
	while (contenttypes.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontenttype = contenttypes.getFiletype();
			String myid = "" + mycontenttype.replaceAll(" ", "_").replaceAll("&", "_").replaceAll("/", "_");
			String mytitle = "" + mycontenttype;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((id.startsWith("menuitem_library_files_types")) && (reqcontentclass.equals("file")) && (reqcontenttype.equals(mycontenttype))) {
				myselected += "jstree-wcm-menu-selected";
			}
			descendents = db.query_value("select count(*) from filetypes where parenttype='" + Common.SQL_clean(mycontenttype) + "'");
			if (descendents > 0) {
				mytype = "pagefolder";
				myrelations = " jstree-closed";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_library_files_types_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_library_links_groups")) {
	String mycontentclass = "link";
	String myparentid = "";
	if (id.startsWith("menuitem_library_links_groups_")) {
		myparentid = id.replaceAll("^menuitem_library_links_groups_", "").replaceAll("_", " ");
	}
	if ((! single_menu_selection) && (myparentid.equals(""))) {
		String mycontentgroup = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_library_links_groups")) && (reqcontentclass.equals("link")) && (reqcontentgroup.equals(" "))) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>group_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	if (true) {
		String mycontentgroup = "-";
		String mytitle = "" + mytext.display("none");
		String myid = "none";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_library_links_groups")) && (reqcontentclass.equals("link")) && (reqcontentgroup.equals("-"))) {
			myselected += "jstree-wcm-menu-selected";
		}
		if (myparentid.equals("")) {
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>group_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}
%><%
	String SQL = "select * from linkgroups where (parentgroup is null) or (parentgroup='') order by linkgroup";
	if (! myparentid.equals("")) {
		SQL = "select * from linkgroups where (parentgroup='" + Common.SQL_clean(myparentid) + "') order by linkgroup";
	}
	Linkgroup contentgroups = new Linkgroup();
	contentgroups.records(db, SQL);
	while (contentgroups.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontentgroup = contentgroups.getLinkgroup();
			String myid = "" + mycontentgroup.replaceAll(" ", "_").replaceAll("&", "_").replaceAll("/", "_");
			String mytitle = "" + mycontentgroup;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((id.startsWith("menuitem_library_links_groups")) && (reqcontentclass.equals("link")) && (reqcontentgroup.equals(mycontentgroup))) {
				myselected += "jstree-wcm-menu-selected";
			}
			descendents = db.query_value("select count(*) from linkgroups where parentgroup='" + Common.SQL_clean(mycontentgroup) + "'");
			if (descendents > 0) {
				mytype = "pagefolder";
				myrelations = " jstree-closed";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_library_links_groups_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_library_links_types")) {
	String mycontentclass = "link";
	String myparentid = "";
	if (id.startsWith("menuitem_library_links_types_")) {
		myparentid = id.replaceAll("^menuitem_library_links_types_", "").replaceAll("_", " ");
	}
	if ((! single_menu_selection) && (myparentid.equals(""))) {
		String mycontenttype = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_library_links_types")) && (reqcontentclass.equals("link")) && (reqcontenttype.equals(" "))) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>type_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	if (true) {
		String mycontenttype = "-";
		String mytitle = "" + mytext.display("none");
		String myid = "none";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_library_links_types")) && (reqcontentclass.equals("link")) && (reqcontenttype.equals("-"))) {
			myselected += "jstree-wcm-menu-selected";
		}
		if (myparentid.equals("")) {
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>type_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}
%><%
	String SQL = "select * from linktypes where (parenttype is null) or (parenttype='') order by linktype";
	if (! myparentid.equals("")) {
		SQL = "select * from linktypes where (parenttype='" + Common.SQL_clean(myparentid) + "') order by linktype";
	}
	Linktype contenttypes = new Linktype();
	contenttypes.records(db, SQL);
	while (contenttypes.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontenttype = contenttypes.getLinktype();
			String myid = "" + mycontenttype.replaceAll(" ", "_").replaceAll("&", "_").replaceAll("/", "_");
			String mytitle = "" + mycontenttype;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((id.startsWith("menuitem_library_links_types")) && (reqcontentclass.equals("link")) && (reqcontenttype.equals(mycontenttype))) {
				myselected += "jstree-wcm-menu-selected";
			}
			descendents = db.query_value("select count(*) from linktypes where parenttype='" + Common.SQL_clean(mycontenttype) + "'");
			if (descendents > 0) {
				mytype = "pagefolder";
				myrelations = " jstree-closed";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_library_links_types_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if ((id.startsWith("menuitem_content_pages_status_workflow")) || (id.startsWith("menuitem_content_elements_status_workflow")) || (id.startsWith("menuitem_library_images_status_workflow")) || (id.startsWith("menuitem_library_files_status_workflow")) || (id.startsWith("menuitem_library_links_status_workflow"))) {
	String mycontentclass = "page";
	if (id.startsWith("menuitem_content_elements_status_workflow")) {
		mycontentclass = "" + contentclass;
		if ((mycontentclass.equals("")) || (mycontentclass.equals(" ")) || (mycontentclass.equals("page,element,template,stylesheet,script")) || (mycontentclass.equals("page")) || (mycontentclass.equals("template")) || (mycontentclass.equals("stylesheet")) || (mycontentclass.equals("script")) || (mycontentclass.equals("image")) || (mycontentclass.equals("file")) || (mycontentclass.equals("link"))) {
			mycontentclass = "element";
		}
	} else if (id.startsWith("menuitem_library_images_status_workflow")) {
		mycontentclass = "image";
	} else if (id.startsWith("menuitem_library_files_status_workflow")) {
		mycontentclass = "file";
	} else if (id.startsWith("menuitem_library_links_status_workflow")) {
		mycontentclass = "link";
	}
%><%
	if (true) {
		String mystatus = "-";
		String myid = "none";
		String mytitle = "" + mytext.display("none");
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_content_pages_status_workflow")) && (reqcontentclass.equals("page")) && (reqstatus.equals(mystatus))) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_content_elements_status_workflow")) && (reqcontentclass.equals(mycontentclass)) && (reqstatus.equals(mystatus))) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_images_status_workflow")) && (reqcontentclass.equals("image")) && (reqstatus.equals(mystatus))) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_files_status_workflow")) && (reqcontentclass.equals("file")) && (reqstatus.equals(mystatus))) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_links_status_workflow")) && (reqcontentclass.equals("link")) && (reqstatus.equals(mystatus))) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_content_<%= mycontentclass %>_status_workflow_<%= myid %>" rel="page"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= URLEncoder.encode(mycontentclass, myconfig.get(db, "charset")) %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(mystatus, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= myrequest.getParameter("menu") %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	String SQL = "select distinct tostate from workflow where " + db.is_not_blank("tostate") + " order by tostate";
	Workflow workflows = new Workflow(mytext);
	workflows.records(db, SQL);
	while (workflows.records(db, "")) {
//		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || workflows.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mystatus = workflows.getToState();
			String myid = workflows.getToState();
			String mytitle = workflows.getToState();
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((id.startsWith("menuitem_content_pages_status_workflow")) && (reqcontentclass.equals("page")) && (reqstatus.equals(mystatus))) {
				myselected += "jstree-wcm-menu-selected";
			} else if ((id.startsWith("menuitem_content_elements_status_workflow")) && (reqcontentclass.equals(mycontentclass)) && (reqstatus.equals(mystatus))) {
				myselected += "jstree-wcm-menu-selected";
			} else if ((id.startsWith("menuitem_library_images_status_workflow")) && (reqcontentclass.equals("image")) && (reqstatus.equals(mystatus))) {
				myselected += "jstree-wcm-menu-selected";
			} else if ((id.startsWith("menuitem_library_files_status_workflow")) && (reqcontentclass.equals("file")) && (reqstatus.equals(mystatus))) {
				myselected += "jstree-wcm-menu-selected";
			} else if ((id.startsWith("menuitem_library_links_status_workflow")) && (reqcontentclass.equals("link")) && (reqstatus.equals(mystatus))) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_content_<%= mycontentclass %>_status_workflow_<%= myid %>" rel="page"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= URLEncoder.encode(mycontentclass, myconfig.get(db, "charset")) %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(mystatus, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= myrequest.getParameter("menu") %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
//		}
	}





} else if (id.equals("menu_library")) {
	// ignore





} else if (! adminuser.permission(db, "forbid", "webadmin_content_structure")) { // content structure

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
			myrelations = " rootnode page";
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
			SQL = "select id,title,contentclass,version,version_master,device,page_top,page_up,page_first,page_previous,page_next,page_last,image1,image2,image3,file1,file2,file3,link1,link2,link3,server_filename from content where (id<>" + id + ") and (contentclass='page') and (" + SQLtops + ") order by version, device, title, id";
			descendents = 0.0;
			%><%@ include file="menu.subtree.content.jsp.html" %><%

			if (! SQLorphans.equals("")) {
				orphans = db.query_value("select count(*) from content where (id<>" + id + ") and (contentclass='page') and (" + SQLorphans + ")");
				if (orphans > 0) {
					mytype = "pagefolder";
					myrelations = " root undraggable";
					myid = "000";
					myselected = "";
					mytitle = "" + mytext.display("menu.structure.orphans");
					String mycount = "" + Common.intnumber(orphans);
					%><li class="jstree-closed<%= myrelations %>" rel="<%= mytype %>" id="<%= myid %>"><a class="<%= myselected %>" href="#" title="<%= mytitle %> (<%= mycount %>)"><%= mytitle %> (<%= mycount %>)</a></li><%
				}
			}
		}
 	} else if (id.equals("000")) {
		id = myconfig.get(db, "default_page");
		String SQL = "select id,title,contentclass,version,version_master,device,page_top,page_up,page_first,page_previous,page_next,page_last,image1,image2,image3,file1,file2,file3,link1,link2,link3,server_filename from content where (id<>" + id + ") and (contentclass='page') and (" + SQLorphans + ") order by version, device, title, id";
		descendents = -1.0;
		%><%@ include file="menu.subtree.content.jsp.html" %><%
	} else {
		String SQL = "";
		content = new Content();
		content.read(db, myconfig, id, "content", "id", mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
		if (content.getUser()) {

			if (children != null) {
				SQL = "select id,title,contentclass,version,version_master,device,page_top,page_up,page_first,page_previous,page_next,page_last,image1,image2,image3,file1,file2,file3,link1,link2,link3,server_filename from content where (page_up='" + id + "') or ((page_top='" + id + "') and (" + SQLorphans + ")) order by title, version, device, id";
				descendents = 0.0;
				%><%@ include file="menu.subtree.content.jsp.html" %><%
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

%><%@ include file="menu.subtree.jsp.html" %><%

		}
	}
}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>