<%@ include file="../config.jsp" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.UCmaintainOrders" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%@ page import="HardCore.Common" %>
<%@ include file="../webeditor/webeditor.jsp" %>
<%
	UCmaintainOrders maintainOrders = new UCmaintainOrders(mytext);
	HashMap email = maintainOrders.doEmail(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	String error = "";
	Page mypage = new Page(mytext);

	String action = "orders.jsp";

	if ((String)email.get("stylesheet") == null) email.put("stylesheet", "");
	if ((String)email.get("style") == null) email.put("style", "");
	if ((String)email.get("from") == null) email.put("from", "");
	if ((String)email.get("to") == null) email.put("to", "");
	if ((String)email.get("cc") == null) email.put("cc", "");
	if ((String)email.get("bcc") == null) email.put("bcc", "");
	if ((String)email.get("subject") == null) email.put("subject", "");
	if ((String)email.get("content") == null) email.put("content", "");
	if ((String)email.get("content_plaintext") == null) email.put("content_plaintext", "");

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
	String stylesheet = (String)email.get("stylesheet");
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
	if (! adminuser.getHardcoreFormat().equals("")) {
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
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>