<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.Workflow" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%@ page import="HardCore.http" %>
<%@ page import="HardCore.Common" %>
<%@ page import="java.net.URLEncoder" %>
<%
	String save_session_mode = mysession.get("mode");
	String save_session_preview_scheduled = mysession.get("preview_scheduled");
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.getCreate(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	Workflow workflow = new Workflow(mytext);

	String stylesheet = "";
	if (mypage.getStyleSheet().equals("0")) {
		stylesheet = "";
	} else if (mypage.getStyleSheet().equals("")) {
		stylesheet = "/stylesheet.jsp?id=" + myconfig.get(db, "default_stylesheet");
	} else {
		stylesheet = "/stylesheet.jsp?id=" + mypage.getStyleSheet();
	}

%>
<%@ include file="accessrestrictions.jsp.html" %>
<%
	mysession.set("mode", save_session_mode);
	mysession.set("preview_scheduled", save_session_preview_scheduled);

%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>