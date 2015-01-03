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





} else if ((id.startsWith("menuitem_content_pages_bundles")) || (id.startsWith("menuitem_content_elements_bundles")) || (id.startsWith("menuitem_library_images_bundles")) || (id.startsWith("menuitem_library_files_bundles")) || (id.startsWith("menuitem_library_links_bundles")) || (id.startsWith("menuitem_ecommerce_products_bundles"))) {
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
	} else if (id.startsWith("menuitem_ecommerce_products_bundles")) {
		mycontentclass = "product";
	}
	String myparentid = "";
	if ((! single_menu_selection) && (myparentid.equals(""))) {
		String mycontentbundle = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_content_pages_bundles")) && (reqcontentclass.equals("page")) && (reqcontentbundle.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_content_elements_bundles")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqcontentbundle.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_images_bundles")) && (reqcontentclass.equals("image")) && (reqcontentbundle.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_files_bundles")) && (reqcontentclass.equals("file")) && (reqcontentbundle.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_links_bundles")) && (reqcontentclass.equals("link")) && (reqcontentbundle.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_ecommerce_products_bundles")) && (reqcontentclass.equals("product")) && (reqcontentbundle.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
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
		if ((id.startsWith("menuitem_content_pages_bundles")) && (reqcontentclass.equals("page")) && (reqcontentbundle.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_content_elements_bundles")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqcontentbundle.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_images_bundles")) && (reqcontentclass.equals("image")) && (reqcontentbundle.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_files_bundles")) && (reqcontentclass.equals("file")) && (reqcontentbundle.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_links_bundles")) && (reqcontentclass.equals("link")) && (reqcontentbundle.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_ecommerce_products_bundles")) && (reqcontentclass.equals("product")) && (reqcontentbundle.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
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
		String myid = "" + mycontentbundle.replaceAll(" ", "_");
		String mytitle = "" + mycontentbundle;
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_content_pages_bundles")) && (reqcontentclass.equals("page")) && (reqcontentbundle.equals(mycontentbundle)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_content_elements_bundles")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqcontentbundle.equals(mycontentbundle)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_images_bundles")) && (reqcontentclass.equals("image")) && (reqcontentbundle.equals(mycontentbundle)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_files_bundles")) && (reqcontentclass.equals("file")) && (reqcontentbundle.equals(mycontentbundle)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_links_bundles")) && (reqcontentclass.equals("link")) && (reqcontentbundle.equals(mycontentbundle)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_ecommerce_products_bundles")) && (reqcontentclass.equals("product")) && (reqcontentbundle.equals(mycontentbundle)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_content_<%= mycontentclass %>s_bundles_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(mycontentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}





} else if ((id.startsWith("menuitem_content_pages_versions")) || (id.startsWith("menuitem_content_elements_versions")) || (id.startsWith("menuitem_library_images_versions")) || (id.startsWith("menuitem_library_files_versions")) || (id.startsWith("menuitem_library_links_versions")) || (id.startsWith("menuitem_library_links_versions")) || (id.startsWith("menuitem_ecommerce_products_versions"))) {
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
	} else if (id.startsWith("menuitem_ecommerce_products_versions")) {
		mycontentclass = "product";
	}
	if (! single_menu_selection) {
		String myversion = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_content_pages_versions")) && (reqcontentclass.equals("page")) && (reqversion.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_content_elements_versions")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqversion.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_images_versions")) && (reqcontentclass.equals("image")) && (reqversion.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_files_versions")) && (reqcontentclass.equals("file")) && (reqversion.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_links_versions")) && (reqcontentclass.equals("link")) && (reqversion.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_ecommerce_products_versions")) && (reqcontentclass.equals("product")) && (reqversion.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
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
		if ((id.startsWith("menuitem_content_pages_versions")) && (reqcontentclass.equals("page")) && (reqversion.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_content_elements_versions")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqversion.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_images_versions")) && (reqcontentclass.equals("image")) && (reqversion.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_files_versions")) && (reqcontentclass.equals("file")) && (reqversion.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_library_links_versions")) && (reqcontentclass.equals("link")) && (reqversion.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		} else if ((id.startsWith("menuitem_ecommerce_products_versions")) && (reqcontentclass.equals("product")) && (reqversion.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
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
			if ((id.startsWith("menuitem_content_pages_versions")) && (reqcontentclass.equals("page")) && (reqversion.equals(myversion)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
				myselected += "jstree-wcm-menu-selected";
			} else if ((id.startsWith("menuitem_content_elements_versions")) && ((! reqcontentclass.equals("page,element,template,stylesheet,script")) && (! reqcontentclass.equals("page")) && (! reqcontentclass.equals("template")) && (! reqcontentclass.equals("stylesheet")) && (! reqcontentclass.equals("script")) && (! reqcontentclass.equals("image")) && (! reqcontentclass.equals("file")) && (! reqcontentclass.equals("link"))) && (reqversion.equals(myversion)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
				myselected += "jstree-wcm-menu-selected";
			} else if ((id.startsWith("menuitem_library_images_versions")) && (reqcontentclass.equals("image")) && (reqversion.equals(myversion)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
				myselected += "jstree-wcm-menu-selected";
			} else if ((id.startsWith("menuitem_library_files_versions")) && (reqcontentclass.equals("file")) && (reqversion.equals(myversion)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
				myselected += "jstree-wcm-menu-selected";
			} else if ((id.startsWith("menuitem_library_links_versions")) && (reqcontentclass.equals("link")) && (reqversion.equals(myversion)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
				myselected += "jstree-wcm-menu-selected";
			} else if ((id.startsWith("menuitem_ecommerce_products_versions")) && (reqcontentclass.equals("product")) && (reqversion.equals(myversion)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>version_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(myversion, myconfig.get(db, "charset")) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if ((id.startsWith("menuitem_content_packages")) || (id.startsWith("menuitem_library_packages")) || (id.startsWith("menuitem_ecommerce_packages"))) {
	String mycontentclass = "" + contentclass;
	if ((id.startsWith("menuitem_content_packages")) && (mycontentclass.equals(" "))) {
		mycontentclass = "page,element,template,stylesheet,script";
	}
	if ((id.startsWith("menuitem_library_packages")) && (mycontentclass.equals(" "))) {
		mycontentclass = "image,file,link";
	}
	if (id.startsWith("menuitem_ecommerce_packages")) {
		mycontentclass = "product";
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





} else if ((id.startsWith("menuitem_content_bundles")) || (id.startsWith("menuitem_library_bundles")) || (id.startsWith("menuitem_ecommerce_bundles"))) {
	String mycontentclass = " ";
	if ((id.startsWith("menuitem_content_bundles")) && (mycontentclass.equals(" "))) {
		mycontentclass = "page,element,template,stylesheet,script";
	}
	if ((id.startsWith("menuitem_library_bundles")) && (mycontentclass.equals(" "))) {
		mycontentclass = "image,file,link";
	}
	if (id.startsWith("menuitem_ecommerce_bundles")) {
		mycontentclass = "product";
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
		String myid = "" + mycontentbundle.replaceAll(" ", "_");
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





} else if (id.startsWith("menuitem_ecommerce_products_groups")) {
	String mycontentclass = "product";
	String myparentid = "";
	if (id.startsWith("menuitem_ecommerce_products_groups_")) {
		myparentid = id.replaceAll("^menuitem_ecommerce_products_groups_", "").replaceAll("_", " ");
	}
	if ((! single_menu_selection) && (myparentid.equals(""))) {
		String mycontentgroup = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_ecommerce_products_groups")) && (reqcontentclass.equals("product")) && (reqcontentgroup.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>group_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
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
		if ((id.startsWith("menuitem_ecommerce_products_groups")) && (reqcontentclass.equals("product")) && (reqcontentgroup.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		}
		if (myparentid.equals("")) {
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>group_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}
%><%
	String SQL = "select * from productgroups where " + db.is_blank("parentgroup") + " order by productgroup";
	if (! myparentid.equals("")) {
		SQL = "select * from productgroups where (parentgroup='" + Common.SQL_clean(myparentid) + "') order by productgroup";
	}
	Productgroup contentgroups = new Productgroup();
	contentgroups.records(db, SQL);
	while (contentgroups.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontentgroup = contentgroups.getProductgroup();
			String myid = "" + mycontentgroup.replaceAll(" ", "_");
			String mytitle = "" + mycontentgroup;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((id.startsWith("menuitem_ecommerce_products_groups")) && (reqcontentclass.equals("product")) && (reqcontentgroup.equals(mycontentgroup)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
				myselected += "jstree-wcm-menu-selected";
			}
			descendents = db.query_value("select count(*) from productgroups where parentgroup='" + Common.SQL_clean(mycontentgroup) + "'");
			if (descendents > 0) {
				mytype = "pagefolder";
				myrelations = " jstree-closed";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_ecommerce_products_groups_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_ecommerce_orders_groups")) {
	String mycontentclass = "product";
	String myparentid = "";
	if (id.startsWith("menuitem_ecommerce_orders_groups_")) {
		myparentid = id.replaceAll("^menuitem_ecommerce_orders_groups_", "").replaceAll("_", " ");
	}
	if ((! single_menu_selection) && (myparentid.equals(""))) {
		String mycontentgroup = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_ecommerce_orders_groups")) && (reqcontentclass.equals("order")) && (reqcontentgroup.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && ((myrequest.getParameter("url").indexOf("/orders/index.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/create.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/update.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/delete.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/view.jsp")>0) || (myrequest.getParameter("url").indexOf("/orderitems/")>0))) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>group_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/orders/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=order&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
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
		if ((id.startsWith("menuitem_ecommerce_orders_groups")) && (reqcontentclass.equals("order")) && (reqcontentgroup.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && ((myrequest.getParameter("url").indexOf("/orders/index.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/create.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/update.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/delete.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/view.jsp")>0) || (myrequest.getParameter("url").indexOf("/orderitems/")>0))) {
			myselected += "jstree-wcm-menu-selected";
		}
		if (myparentid.equals("")) {
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>group_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/orders/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=order&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}
%><%
	String SQL = "select * from productgroups where " + db.is_blank("parentgroup") + " order by productgroup";
	if (! myparentid.equals("")) {
		SQL = "select * from productgroups where (parentgroup='" + Common.SQL_clean(myparentid) + "') order by productgroup";
	}
	Productgroup contentgroups = new Productgroup();
	contentgroups.records(db, SQL);
	while (contentgroups.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontentgroup = contentgroups.getProductgroup();
			String myid = "" + mycontentgroup.replaceAll(" ", "_");
			String mytitle = "" + mycontentgroup;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((id.startsWith("menuitem_ecommerce_orders_groups")) && (reqcontentclass.equals("order")) && (reqcontentgroup.equals(mycontentgroup)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && ((myrequest.getParameter("url").indexOf("/orders/index.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/create.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/update.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/delete.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/view.jsp")>0) || (myrequest.getParameter("url").indexOf("/orderitems/")>0))) {
				myselected += "jstree-wcm-menu-selected";
			}
			descendents = db.query_value("select count(*) from productgroups where parentgroup='" + Common.SQL_clean(mycontentgroup) + "'");
			if (descendents > 0) {
				mytype = "pagefolder";
				myrelations = " jstree-closed";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_ecommerce_orders_groups_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/orders/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=order&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_ecommerce_stock_groups")) {
	version = "-";
	String mycontentclass = "product";
	String myparentid = "";
	if (id.startsWith("menuitem_ecommerce_stock_groups_")) {
		myparentid = id.replaceAll("^menuitem_ecommerce_stock_groups_", "").replaceAll("_", " ");
	}
	if ((! single_menu_selection) && (myparentid.equals(""))) {
		String mycontentgroup = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_ecommerce_stock_groups")) && (reqcontentclass.equals("product")) && (reqcontentgroup.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")>=0)) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>group_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/stock.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
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
		if ((id.startsWith("menuitem_ecommerce_stock_groups")) && (reqcontentclass.equals("product")) && (reqcontentgroup.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")>=0)) {
			myselected += "jstree-wcm-menu-selected";
		}
		if (myparentid.equals("")) {
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>group_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/stock.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}
%><%
	String SQL = "select * from productgroups where " + db.is_blank("parentgroup") + " order by productgroup";
	if (! myparentid.equals("")) {
		SQL = "select * from productgroups where (parentgroup='" + Common.SQL_clean(myparentid) + "') order by productgroup";
	}
	Productgroup contentgroups = new Productgroup();
	contentgroups.records(db, SQL);
	while (contentgroups.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontentgroup = contentgroups.getProductgroup();
			String myid = "" + mycontentgroup.replaceAll(" ", "_");
			String mytitle = "" + mycontentgroup;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((id.startsWith("menuitem_ecommerce_stock_groups")) && (reqcontentclass.equals("product")) && (reqcontentgroup.equals(mycontentgroup)) && (myrequest.getParameter("url").indexOf("stock.jsp")>=0)) {
				myselected += "jstree-wcm-menu-selected";
			}
			descendents = db.query_value("select count(*) from productgroups where parentgroup='" + Common.SQL_clean(mycontentgroup) + "'");
			if (descendents > 0) {
				mytype = "pagefolder";
				myrelations = " jstree-closed";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_ecommerce_stock_groups_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/stock.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_ecommerce_products_types")) {
	String mycontentclass = "product";
	String myparentid = "";
	if (id.startsWith("menuitem_ecommerce_products_types_")) {
		myparentid = id.replaceAll("^menuitem_ecommerce_products_types_", "").replaceAll("_", " ");
	}
	if ((! single_menu_selection) && (myparentid.equals(""))) {
		String mycontenttype = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_ecommerce_products_types")) && (reqcontentclass.equals("product")) && (reqcontenttype.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>type_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
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
		if ((id.startsWith("menuitem_ecommerce_products_types")) && (reqcontentclass.equals("product")) && (reqcontenttype.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
			myselected += "jstree-wcm-menu-selected";
		}
		if (myparentid.equals("")) {
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>type_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}
%><%
	String SQL = "select * from producttypes where " + db.is_blank("parenttype") + " order by producttype";
	if (! myparentid.equals("")) {
		SQL = "select * from producttypes where (parenttype='" + Common.SQL_clean(myparentid) + "') order by producttype";
	}
	Producttype contenttypes = new Producttype();
	contenttypes.records(db, SQL);
	while (contenttypes.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontenttype = contenttypes.getProducttype();
			String myid = "" + mycontenttype.replaceAll(" ", "_");
			String mytitle = "" + mycontenttype;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((id.startsWith("menuitem_ecommerce_products_types")) && (reqcontentclass.equals("product")) && (reqcontenttype.equals(mycontenttype)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && (myrequest.getParameter("url").indexOf("/orders/")<0)) {
				myselected += "jstree-wcm-menu-selected";
			}
			descendents = db.query_value("select count(*) from producttypes where parenttype='" + Common.SQL_clean(mycontenttype) + "'");
			if (descendents > 0) {
				mytype = "pagefolder";
				myrelations = " jstree-closed";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_ecommerce_products_types_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_ecommerce_orders_types")) {
	String mycontentclass = "product";
	String myparentid = "";
	if (id.startsWith("menuitem_ecommerce_orders_types_")) {
		myparentid = id.replaceAll("^menuitem_ecommerce_orders_types_", "").replaceAll("_", " ");
	}
	if ((! single_menu_selection) && (myparentid.equals(""))) {
		String mycontenttype = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_ecommerce_orders_types")) && (reqcontentclass.equals("order")) && (reqcontenttype.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && ((myrequest.getParameter("url").indexOf("/orders/index.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/create.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/update.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/delete.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/view.jsp")>0) || (myrequest.getParameter("url").indexOf("/orderitems/")>0))) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>type_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/orders/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=order&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
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
		if ((id.startsWith("menuitem_ecommerce_orders_types")) && (reqcontentclass.equals("order")) && (reqcontenttype.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && ((myrequest.getParameter("url").indexOf("/orders/index.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/create.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/update.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/delete.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/view.jsp")>0) || (myrequest.getParameter("url").indexOf("/orderitems/")>0))) {
			myselected += "jstree-wcm-menu-selected";
		}
		if (myparentid.equals("")) {
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>type_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/orders/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=order&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}
%><%
	String SQL = "select * from producttypes where " + db.is_blank("parenttype") + " order by producttype";
	if (! myparentid.equals("")) {
		SQL = "select * from producttypes where (parenttype='" + Common.SQL_clean(myparentid) + "') order by producttype";
	}
	Producttype contenttypes = new Producttype();
	contenttypes.records(db, SQL);
	while (contenttypes.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontenttype = contenttypes.getProducttype();
			String myid = "" + mycontenttype.replaceAll(" ", "_");
			String mytitle = "" + mycontenttype;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((id.startsWith("menuitem_ecommerce_orders_types")) && (reqcontentclass.equals("order")) && (reqcontenttype.equals(mycontenttype)) && (myrequest.getParameter("url").indexOf("stock.jsp")<0) && ((myrequest.getParameter("url").indexOf("/orders/index.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/create.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/update.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/delete.jsp")>0) || (myrequest.getParameter("url").indexOf("/orders/view.jsp")>0) || (myrequest.getParameter("url").indexOf("/orderitems/")>0))) {
				myselected += "jstree-wcm-menu-selected";
			}
			descendents = db.query_value("select count(*) from producttypes where parenttype='" + Common.SQL_clean(mycontenttype) + "'");
			if (descendents > 0) {
				mytype = "pagefolder";
				myrelations = " jstree-closed";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_ecommerce_orders_types_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/orders/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=order&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_ecommerce_stock_types")) {
	version = "-";
	String mycontentclass = "product";
	String myparentid = "";
	if (id.startsWith("menuitem_ecommerce_stock_types_")) {
		myparentid = id.replaceAll("^menuitem_ecommerce_stock_types_", "").replaceAll("_", " ");
	}
	if ((! single_menu_selection) && (myparentid.equals(""))) {
		String mycontenttype = " ";
		String mytitle = "" + mytext.display("all");
		String myid = "all";
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((id.startsWith("menuitem_ecommerce_stock_types")) && (reqcontentclass.equals("product")) && (reqcontenttype.equals(" ")) && (myrequest.getParameter("url").indexOf("stock.jsp")>=0)) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>type_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/stock.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
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
		if ((id.startsWith("menuitem_ecommerce_stock_types")) && (reqcontentclass.equals("product")) && (reqcontenttype.equals("-")) && (myrequest.getParameter("url").indexOf("stock.jsp")>=0)) {
			myselected += "jstree-wcm-menu-selected";
		}
		if (myparentid.equals("")) {
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="<%= mycontentclass %>type_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/stock.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}
%><%
	String SQL = "select * from producttypes where " + db.is_blank("parenttype") + " order by producttype";
	if (! myparentid.equals("")) {
		SQL = "select * from producttypes where (parenttype='" + Common.SQL_clean(myparentid) + "') order by producttype";
	}
	Producttype contenttypes = new Producttype();
	contenttypes.records(db, SQL);
	while (contenttypes.records(db, "")) {
		if ((! myconfig.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig)) {
			String mycontenttype = contenttypes.getProducttype();
			String myid = "" + mycontenttype.replaceAll(" ", "_");
			String mytitle = "" + mycontenttype;
			String myrelations = " page";
			String mytype = "page";
			String myselected = "";
			if ((id.startsWith("menuitem_ecommerce_stock_types")) && (reqcontentclass.equals("product")) && (reqcontenttype.equals(mycontenttype)) && (myrequest.getParameter("url").indexOf("stock.jsp")>=0)) {
				myselected += "jstree-wcm-menu-selected";
			}
			descendents = db.query_value("select count(*) from producttypes where parenttype='" + Common.SQL_clean(mycontenttype) + "'");
			if (descendents > 0) {
				mytype = "pagefolder";
				myrelations = " jstree-closed";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_ecommerce_stock_types_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/stock.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= mycontentclass %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(status, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= URLEncoder.encode(menu, myconfig.get(db, "charset")) %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if ((id.startsWith("menuitem_content_pages_status_workflow")) || (id.startsWith("menuitem_content_elements_status_workflow")) || (id.startsWith("menuitem_library_images_status_workflow")) || (id.startsWith("menuitem_library_files_status_workflow")) || (id.startsWith("menuitem_library_links_status_workflow")) || (id.startsWith("menuitem_ecommerce_products_status_workflow"))) {
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
	} else if (id.startsWith("menuitem_ecommerce_products_status_workflow")) {
		mycontentclass = "product";
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
		} else if ((id.startsWith("menuitem_ecommerce_products_status_workflow")) && (reqcontentclass.equals("product")) && (reqstatus.equals(mystatus))) {
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
			} else if ((id.startsWith("menuitem_ecommerce_products_status_workflow")) && (reqcontentclass.equals("product")) && (reqstatus.equals(mystatus))) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_content_<%= mycontentclass %>_status_workflow_<%= myid %>" rel="page"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/content/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= URLEncoder.encode(mycontentclass, myconfig.get(db, "charset")) %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(mystatus, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= myrequest.getParameter("menu") %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
//		}
	}





} else if (id.startsWith("menuitem_ecommerce_orders_status_workflow")) {
	String mycontentclass = "order";
%><%
	if (true) {
		String mystatus = "-";
		String myid = "none";
		String mytitle = "" + mytext.display("none");
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((reqcontentclass.equals("order")) && (reqstatus.equals(mystatus))) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_ecommerce_orders_status_workflow_<%= myid %>" rel="page"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/orders/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= URLEncoder.encode(mycontentclass, myconfig.get(db, "charset")) %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(mystatus, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= myrequest.getParameter("menu") %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}
%><%
	String SQL = "select distinct tostate from workflow where " + db.is_not_blank("tostate") + " and (title='-orders-') order by tostate";
	Workflow workflows = new Workflow(mytext);
	workflows.records(db, SQL);
	while (workflows.records(db, "")) {
		String mystatus = workflows.getToState();
		String myid = workflows.getToState();
		String mytitle = workflows.getToState();
		String myrelations = " page";
		String mytype = "page";
		String myselected = "";
		if ((reqcontentclass.equals("order")) && (reqstatus.equals(mystatus))) {
			myselected += "jstree-wcm-menu-selected";
		}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_ecommerce_orders_status_workflow_<%= myid %>" rel="page"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/orders/index.jsp?contentpackage=<%= URLEncoder.encode(contentpackage, myconfig.get(db, "charset")) %>&contentclass=<%= URLEncoder.encode(mycontentclass, myconfig.get(db, "charset")) %>&contentbundle=<%= URLEncoder.encode(contentbundle, myconfig.get(db, "charset")) %>&contentgroup=<%= URLEncoder.encode(contentgroup, myconfig.get(db, "charset")) %>&contenttype=<%= URLEncoder.encode(contenttype, myconfig.get(db, "charset")) %>&status=<%= URLEncoder.encode(mystatus, myconfig.get(db, "charset")) %>&stock=<%= URLEncoder.encode(stock, myconfig.get(db, "charset")) %>&version=<%= URLEncoder.encode(version) %>&menu=<%= myrequest.getParameter("menu") %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
	}





} else if (id.startsWith("menuitem_ecommerce_orders_what_ecommerce_products_groups")) {
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
			if ((reqcontentclass.equals("product")) && (reqcontentgroup.equals(mycontentgroup)) && (myrequest.getParameter("url").indexOf("/orders/")>0)) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="productgroup_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/orders/products.jsp?contentpackage= &contentclass=product&contentgroup=<%= URLEncoder.encode(mycontentgroup, myconfig.get(db, "charset")) %>&contenttype= &status= &version= &userclass= &usergroup= &usertype= &menu=<%= menu %>&<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_ecommerce_orders_what_ecommerce_products_types")) {
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
			if ((reqcontentclass.equals("product")) && (reqcontenttype.equals(mycontenttype)) && (myrequest.getParameter("url").indexOf("/orders/")>0)) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="producttype_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/orders/products.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=<%= URLEncoder.encode(mycontenttype, myconfig.get(db, "charset")) %>&status= &version= &userclass= &usergroup= &usertype= &menu=<%= menu %>&<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
		}
	}





} else if (id.startsWith("menuitem_ecommerce_orders_who_users_groups")) {
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
			if ((requsergroup.equals(myusergroup)) && (myrequest.getParameter("url").indexOf("/orders/")>0)) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_ecommerce_orders_who_users_group_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/orders/users.jsp?contentpackage= &contentgroup= &contenttype= &userclass= &usergroup=<%= URLEncoder.encode(myusergroup, myconfig.get(db, "charset")) %>&usertype= &status= &menu=<%= menu %>&<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
//		}
	}





} else if (id.startsWith("menuitem_ecommerce_orders_who_users_types")) {
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
			if ((requsertype.equals(myusertype)) && (myrequest.getParameter("url").indexOf("/orders/")>0)) {
				myselected += "jstree-wcm-menu-selected";
			}
%>
<li class="<%= myrelations %>" rel="<%= mytype %>" id="menuitem_ecommerce_orders_who_users_type_<%= myid %>"><a class="<%= myselected %>" href="/<%= mytext.display("adminpath") %>/orders/users.jsp?contentpackage= &contentgroup= &contenttype= &userclass= &usergroup= &usertype=<%= URLEncoder.encode(myusertype, myconfig.get(db, "charset")) %>&status= &menu=<%= menu %>&<%= Math.random() %>&" title="<%= mytitle %>"><%= mytitle %></a></li>
<%
//		}
	}





}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>