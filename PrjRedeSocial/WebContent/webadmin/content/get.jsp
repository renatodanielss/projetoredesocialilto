<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.Workflow" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%@ page import="HardCore.Common" %>
<%@ page import="java.net.URLEncoder" %>
<%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.getView(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	Workflow workflow = new Workflow(mytext);
	String error = maintainContent.getError();

	String baseHref = "/";
	String baseHrefPrefix = "/";
	if (myconfig.get(db, "use_static_filenames").equals("yes")) {
		String server_filename = mypage.getServerFilename();
		if (! server_filename.equals("")) {
			baseHref = "/" + server_filename;
			baseHrefPrefix = "";
		}
	}

	String toolbar;
	String toolbar1;
	String toolbar2;
	String toolbar3;
	String toolbar4;
	String toolbar5;
	if ((! adminuser.getHardcoreToolbar().equals("")) || (! adminuser.getHardcoreToolbar1().equals("")) || (! adminuser.getHardcoreToolbar2().equals("")) || (! adminuser.getHardcoreToolbar3().equals("")) || (! adminuser.getHardcoreToolbar4().equals("")) || (! adminuser.getHardcoreToolbar5().equals(""))) {
		toolbar = adminuser.getHardcoreToolbar();
		toolbar1 = adminuser.getHardcoreToolbar1();
		toolbar2 = adminuser.getHardcoreToolbar2();
		toolbar3 = adminuser.getHardcoreToolbar3();
		toolbar4 = adminuser.getHardcoreToolbar4();
		toolbar5 = adminuser.getHardcoreToolbar5();
	} else {
		toolbar = myconfig.get(db, "hardcore_toolbar");
		toolbar1 = myconfig.get(db, "hardcore_toolbar1");
		toolbar2 = myconfig.get(db, "hardcore_toolbar2");
		toolbar3 = myconfig.get(db, "hardcore_toolbar3");
		toolbar4 = myconfig.get(db, "hardcore_toolbar4");
		toolbar5 = myconfig.get(db, "hardcore_toolbar5");
	}
	String stylesheet;
	if (mypage.getStyleSheet().equals("0")) {
		stylesheet = "";
	} else if (mypage.getStyleSheet().equals("")) {
		stylesheet = "/stylesheet.jsp?id=" + myconfig.get(db, "default_stylesheet");
	} else {
		stylesheet = "/stylesheet.jsp?id=" + mypage.getStyleSheet();
	}

	String webeditor_format = "";
	String mydoctype = mypage.header(db, myconfig, "doctype", "").trim();
	if (mydoctype.equals("")) {
		mydoctype = website.getDefaultDocType();
	}
	if (mydoctype.equals("")) {
		mydoctype = myconfig.get(db, "doctype");
	}
	if (mydoctype.indexOf("xhtml") > -1) {
		webeditor_format = "xhtml";
	}

	String onenter;
	if (! adminuser.getHardcoreOnEnter().equals("")) {
		onenter = adminuser.getHardcoreOnEnter();
	} else {
		onenter = myconfig.get(db, "hardcore_onenter");
	}
	String onshiftenter;
	if (! adminuser.getHardcoreOnShiftEnter().equals("")) {
		onshiftenter = adminuser.getHardcoreOnShiftEnter();
	} else {
		onshiftenter = myconfig.get(db, "hardcore_onshiftenter");
	}
	String onctrlenter;
	if (! adminuser.getHardcoreOnCtrlEnter().equals("")) {
		onctrlenter = adminuser.getHardcoreOnCtrlEnter();
	} else {
		onctrlenter = myconfig.get(db, "hardcore_onctrlenter");
	}
	String onaltenter;
	if (! adminuser.getHardcoreOnAltEnter().equals("")) {
		onaltenter = adminuser.getHardcoreOnAltEnter();
	} else {
		onaltenter = myconfig.get(db, "hardcore_onaltenter");
	}
	String width;
	if (! adminuser.getHardcoreWidth().equals("")) {
		width = adminuser.getHardcoreWidth();
	} else {
		width = myconfig.get(db, "hardcore_width");
	}
	String height;
	if (! adminuser.getHardcoreHeight().equals("")) {
		height = adminuser.getHardcoreHeight();
	} else {
		height = myconfig.get(db, "hardcore_height");
	}
	String format;
	if (! mypage.getContentFormat().equals("")) {
		format = mypage.getContentFormat();
	} else if (! adminuser.getHardcoreFormat().equals("")) {
		format = adminuser.getHardcoreFormat();
	} else {
		format = myconfig.get(db, "hardcore_format");
	}
	String encoding;
	if (! adminuser.getHardcoreEncoding().equals("")) {
		encoding = adminuser.getHardcoreEncoding();
	} else {
		encoding = myconfig.get(db, "hardcore_encoding");
	}

%>
<%@ include file="get.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>
