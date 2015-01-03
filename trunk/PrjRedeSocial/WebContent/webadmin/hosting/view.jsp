<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainHosting" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Hosting" %>
<%@ page import="HardCore.Page" %>
<%
	UCmaintainHosting maintainHosting = new UCmaintainHosting(mytext);
	String error = maintainHosting.getView(inifile, mysession, myrequest, myresponse, myconfig, db);
	DB client_db = maintainHosting.getClientDB();
	Configuration client_config = maintainHosting.getClientConfig();
	Page scheduled_publish_email = maintainHosting.getScheduledPublishEmailPage(mysession, db, myconfig);
	Page scheduled_notify_email = maintainHosting.getScheduledNotifyEmailPage(mysession, db, myconfig);
	Page scheduled_unpublish_email = maintainHosting.getScheduledUnpublishEmailPage(mysession, db, myconfig);
%>
<%@ include file="view.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>