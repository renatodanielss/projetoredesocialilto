<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.Contentgroup" %>
<%@ page import="HardCore.Contenttype" %>
<%@ page import="HardCore.Workflow" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%@ page import="HardCore.http" %>
<%@ page import="HardCore.Common" %>
<%@ page import="java.net.URLEncoder" %>
<%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.getDelete(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	Workflow workflow = new Workflow(mytext);
	String error = maintainContent.getError();

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
<%@ include file="delete.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>