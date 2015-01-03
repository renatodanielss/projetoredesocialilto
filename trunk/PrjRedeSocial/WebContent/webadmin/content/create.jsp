<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.Contentgroup" %>
<%@ page import="HardCore.Contenttype" %>
<%@ page import="HardCore.Workflow" %>
<%@ page import="HardCore.Simulator" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%@ page import="HardCore.http" %>
<%@ page import="HardCore.Common" %>
<%@ page import="java.net.URLEncoder" %>
<%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.getCreate(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	Workflow workflow = new Workflow(mytext);
	Simulator simulator = new Simulator(mytext);
	String error = maintainContent.getError();

	String myurlhost = "" + myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort();
	String myurlscript = "/" + mytext.display("adminpath") + "/content/stylesheets.jsp";
	String myurlquery = "id=" + mypage.getTemplate() + "&" + Math.random();
	String template_stylesheets = "" + http.get(myurlhost, myurlscript, myurlquery, myrequest);
	String stylesheet = "";
	if (mypage.getStyleSheet().equals("0")) {
		stylesheet = "";
	} else if ((! mypage.getStyleSheet().equals("")) && (! template_stylesheets.equals(""))) {
		stylesheet = "/stylesheet.jsp?id=" + template_stylesheets + "," + mypage.getStyleSheet();
	} else if (! template_stylesheets.equals("")) {
		stylesheet = "/stylesheet.jsp?id=" + template_stylesheets;
	} else if (! mypage.getStyleSheet().equals("")) {
		stylesheet = "/stylesheet.jsp?id=" + mypage.getStyleSheet();
	} else {
		stylesheet = "/stylesheet.jsp?id=" + myconfig.get(db, "default_stylesheet");
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
%>
<%@ include file="create.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>