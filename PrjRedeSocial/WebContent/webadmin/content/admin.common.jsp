<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.File" %>
<%@ page import="HardCore.Image" %>
<%@ page import="HardCore.Workflow" %>
<%@ page import="HardCore.Simulator" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%@ page import="HardCore.http" %>
<%@ page import="HardCore.Common" %>
<%@ page import="java.net.URLEncoder" %>
<%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.getUpdate(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	Workflow workflow = new Workflow(mytext);
	Simulator simulator = new Simulator(mytext);

	String myurlhost = "" + myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort();
	String myurlscript = "/" + mytext.display("adminpath") + "/content/stylesheets.jsp";
	String myurlquery = "id=" + mypage.getTemplate() + "&contentclass=" + URLEncoder.encode(mypage.getContentClass()) + "&contentgroup=" + URLEncoder.encode(mypage.getContentGroup()) + "&contenttype=" + URLEncoder.encode(mypage.getContentType()) + "&" + Math.random();
	String template_stylesheets = "" + http.get(myurlhost, myurlscript, myurlquery, myrequest);
	String stylesheets = mypage.getStyleSheet();
	if (stylesheets.equals("")) {
		myurlquery = "contentclass=" + URLEncoder.encode(mypage.getContentClass()) + "&contentgroup=" + URLEncoder.encode(mypage.getContentGroup()) + "&contenttype=" + URLEncoder.encode(mypage.getContentType()) + "&" + Math.random();
		stylesheets = "" + http.get(myurlhost, myurlscript, myurlquery, myrequest);
	}
	if (stylesheets.equals("")) {
		stylesheets = myconfig.get(db, "default_stylesheet");
	}
	String stylesheet = "";
	if ((stylesheets.equals("0")) && (! template_stylesheets.equals(""))) {
		stylesheet = "/stylesheet.jsp?id=" + template_stylesheets;
	} else if (stylesheets.equals("0")) {
		stylesheet = "";
	} else if ((! stylesheets.equals("")) && (! template_stylesheets.equals(""))) {
		stylesheet = "/stylesheet.jsp?id=" + template_stylesheets + "," + stylesheets;
	} else if (! stylesheets.equals("")) {
		stylesheet = "/stylesheet.jsp?id=" + stylesheets;
	} else if (! template_stylesheets.equals("")) {
		stylesheet = "/stylesheet.jsp?id=" + template_stylesheets;
	} else {
		stylesheet = "";
	}

	String webeditor_format = "";
	String mydoctype = mypage.header(db, myconfig, "doctype", "").trim();
	if (mydoctype.equals("")) {
		mydoctype = website.getDefaultDocType();
	}
	if (mydoctype.equals("")) {
		mydoctype = myconfig.get(db, "doctype");
	}
	if (! mypage.getContentFormat().equals("")) {
		webeditor_format = mypage.getContentFormat();
	} else if (mydoctype.indexOf("xhtml") > -1) {
		webeditor_format = "xhtml";
	}

	// Clear past requested publish date/time which may have been set by previous versions of the web content management system
	String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
	if ((! mypage.getRequestedPublish().equals("")) && (mypage.getRequestedPublish().compareTo(timestamp) <= 0)) {
		mypage.setRequestedPublish("");
	}
	mysession.set("mode", "admin");
	mysession.set("preview_scheduled", "");
	if (! mypage.getScheduledPublish().equals("")) {
		mysession.set("mode", "preview");
		mysession.set("preview_scheduled", mypage.getScheduledPublish());
	} else if (! mypage.getRequestedPublish().equals("")) {
		mysession.set("mode", "preview");
		mysession.set("preview_scheduled", mypage.getRequestedPublish());
	} else {
//		mysession.set("mode", "preview");
//		mysession.set("preview_scheduled", "" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
	}

	boolean workflowpermissions = workflow.permissions(db, mypage.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), mypage.getContentClass(), mypage.getContentGroup(), mypage.getContentType(), mypage.getVersion(), (mypage.getStatusBy().equals(mysession.get("username"))));
	boolean create = false;
//	create = mypage.getCreator() && (! ((myconfig.get(db, "use_workflow").equals("yes")) && (! mypage.getAdministrator()) && (! workflowpermissions)));
// TODO "add new" - "save" and "save & close" must detect and handle creating new content item
	boolean update = mypage.getEditor();
	if (! (((mypage.getEditor()) && ((mypage.getScheduledPublish().equals("")) || mypage.getPublisher())) || (mypage.getPublisher() && (! myconfig.get(db, "inherit_accessrestrictions").equals("no"))) || ((myconfig.get(db, "use_scheduled_publish").equals("yes")) && (myconfig.get(db, "use_schedule").equals("yes")) && (mypage.getUser()) && (mypage.isScheduled(db))))) {
		update = false;
	}
	if ((myconfig.get(db, "use_workflow").equals("yes")) && (! mypage.getAdministrator()) && (! workflowpermissions)) {
		update = false;
	}
	workflowpermissions = workflowpermissions && mypage.getCheckoutPermissions(mysession.get("username"), db, myconfig);
	boolean viewupdate =  (((myrequest.getParameter("archive").equals("")) && (myconfig.get(db, "use_workflow").equals("yes")) && ((mypage.getAdministrator()) || (workflowpermissions))));
	boolean viewpublish = ((mypage.getPublisher()) && ((! myconfig.get(db, "use_workflow").equals("yes")) || ((mypage.getAdministrator()) || (workflowpermissions))));

%>
