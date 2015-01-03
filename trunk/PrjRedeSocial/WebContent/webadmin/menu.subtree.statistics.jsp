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





} else if (id.startsWith("menuitem_statistics_what_content_pages_groups")) {
	String SQL = "select * from contentgroups order by contentgroup";
	Contentgroup contentgroups = new Contentgroup();
	contentgroups.records(db, SQL);
	while (contentgroups.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontentgroup = contentgroups.getContentgroup();
			String myid = "" + mycontentgroup;
			String mytitle = "" + mycontentgroup;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((reqcontentclass.equals("page")) && (reqcontentgroup.equals(mycontentgroup))) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="pagegroup_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype= &status= &version= &<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_statistics_what_content_pages_types")) {
	String SQL = "select * from contenttypes order by contenttype";
	Contenttype contenttypes = new Contenttype();
	contenttypes.records(db, SQL);
	while (contenttypes.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontenttype = contenttypes.getContenttype();
			String myid = "" + mycontenttype;
			String mytitle = "" + mycontenttype;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((reqcontentclass.equals("page")) && (reqcontenttype.equals(mycontenttype))) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="pagetype_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status= &version= &<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_statistics_what_library_images_groups")) {
	String SQL = "select * from imagegroups order by imagegroup";
	Imagegroup contentgroups = new Imagegroup();
	contentgroups.records(db, SQL);
	while (contentgroups.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontentgroup = contentgroups.getImagegroup();
			String myid = "" + mycontentgroup;
			String mytitle = "" + mycontentgroup;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((reqcontentclass.equals("image")) && (reqcontentgroup.equals(mycontentgroup))) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="imagegroup_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/usage/images.jsp?contentpackage= &contentclass=image&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype= &status= &version= &<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_statistics_what_library_images_types")) {
	String SQL = "select * from imagetypes order by imagetype";
	Imagetype contenttypes = new Imagetype();
	contenttypes.records(db, SQL);
	while (contenttypes.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontenttype = contenttypes.getImagetype();
			String myid = "" + mycontenttype;
			String mytitle = "" + mycontenttype;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((reqcontentclass.equals("image")) && (reqcontenttype.equals(mycontenttype))) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="imagetype_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/usage/images.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status= &version= &<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_statistics_what_library_files_groups")) {
	String SQL = "select * from filegroups order by filegroup";
	Filegroup contentgroups = new Filegroup();
	contentgroups.records(db, SQL);
	while (contentgroups.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontentgroup = contentgroups.getFilegroup();
			String myid = "" + mycontentgroup;
			String mytitle = "" + mycontentgroup;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((reqcontentclass.equals("file")) && (reqcontentgroup.equals(mycontentgroup))) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="filegroup_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/usage/files.jsp?contentpackage= &contentclass=file&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype= &status= &version= &<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_statistics_what_library_files_types")) {
	String SQL = "select * from filetypes order by filetype";
	Filetype contenttypes = new Filetype();
	contenttypes.records(db, SQL);
	while (contenttypes.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontenttype = contenttypes.getFiletype();
			String myid = "" + mycontenttype;
			String mytitle = "" + mycontenttype;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((reqcontentclass.equals("file")) && (reqcontenttype.equals(mycontenttype))) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="filetype_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/usage/files.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status= &version= &<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_statistics_what_library_links_groups")) {
	String SQL = "select * from linkgroups order by linkgroup";
	Linkgroup contentgroups = new Linkgroup();
	contentgroups.records(db, SQL);
	while (contentgroups.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontentgroup = contentgroups.getLinkgroup();
			String myid = "" + mycontentgroup;
			String mytitle = "" + mycontentgroup;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((reqcontentclass.equals("link")) && (reqcontentgroup.equals(mycontentgroup))) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="linkgroup_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/usage/links.jsp?contentpackage= &contentclass=link&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype= &status= &version= &<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_statistics_what_library_links_types")) {
	String SQL = "select * from linktypes order by linktype";
	Linktype contenttypes = new Linktype();
	contenttypes.records(db, SQL);
	while (contenttypes.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontenttype = contenttypes.getLinktype();
			String myid = "" + mycontenttype;
			String mytitle = "" + mycontenttype;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((reqcontentclass.equals("link")) && (reqcontenttype.equals(mycontenttype))) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="linktype_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/usage/links.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status= &version= &<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_statistics_what_ecommerce_products_groups")) {
	String SQL = "select * from productgroups order by productgroup";
	Productgroup contentgroups = new Productgroup();
	contentgroups.records(db, SQL);
	while (contentgroups.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontentgroup = contentgroups.getProductgroup();
			String myid = "" + mycontentgroup;
			String mytitle = "" + mycontentgroup;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((reqcontentclass.equals("product")) && (reqcontentgroup.equals(mycontentgroup))) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="productgroup_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/usage/products.jsp?contentpackage= &contentclass=product&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype= &status= &version= &<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_statistics_what_ecommerce_products_types")) {
	String SQL = "select * from producttypes order by producttype";
	Producttype contenttypes = new Producttype();
	contenttypes.records(db, SQL);
	while (contenttypes.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontenttype = contenttypes.getProducttype();
			String myid = "" + mycontenttype;
			String mytitle = "" + mycontenttype;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((reqcontentclass.equals("product")) && (reqcontenttype.equals(mycontenttype))) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="producttype_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/usage/products.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status= &version= &<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_statistics_what_databases")) {
	String SQL = "select id, title from data order by title, id";
	Databases databases = new Databases(mytext);
	databases.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
	while (databases.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
//		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || databases.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String myid = databases.getId();
			String mytitle = databases.getTitle();
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if (myrequest.getParameter("database").equals(mytitle)) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_statistics_what_databases_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/usage/database.jsp?database=<%= mytitle %>&<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
//		}
	}





} else if (id.startsWith("menuitem_statistics_who_users_groups")) {
	String SQL = "select * from usergroups order by usergroup";
	Usergroup usergroups = new Usergroup();
	usergroups.records(db, SQL);
	while (usergroups.records(db, "")) {
//		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || usergroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String myusergroup = usergroups.getUsergroup();
			String myid = "" + myusergroup;
			String mytitle = "" + myusergroup;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if (requsergroup.equals(myusergroup)) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_statistics_who_users_group_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/usage/users.jsp?contentpackage= &userclass= &usergroup=<%= URLEncoder.encode(myusergroup, myconfig.get(db, "charset")) %>&usertype= &status= &<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
//		}
	}





} else if (id.startsWith("menuitem_statistics_who_users_types")) {
	String SQL = "select * from usertypes order by usertype";
	Usertype usertypes = new Usertype();
	usertypes.records(db, SQL);
	while (usertypes.records(db, "")) {
//		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || usertypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String myusertype = usertypes.getUsertype();
			String myid = "" + myusertype;
			String mytitle = "" + myusertype;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if (requsertype.equals(myusertype)) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_statistics_who_users_type_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/usage/users.jsp?contentpackage= &userclass= &usergroup= &usertype=<%= URLEncoder.encode(myusertype, myconfig.get(db, "charset")) %>&status= &<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
//		}
	}





}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>