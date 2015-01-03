<%@ include file="../config.jsp" %>
<%@ page import="HardCore.RequireUser" %>
<%@ page import="HardCore.UCmaintainUsers" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.User" %>
<%@ page import="HardCore.MenuContent" %>
<%
	RequireUser.Administrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
	myconfig.setTemp("use_userdatabase", "yes");
	myconfig.setTemp("use_userdirectory", "");
	UCmaintainUsers maintainUsers = new UCmaintainUsers(mytext);
	User user = maintainUsers.getView(mysession, myrequest, myresponse, myconfig, db);
	String error = maintainUsers.getError();
	Page scheduled_publish_email = user.getScheduledPublishEmailPage(mysession, db, myconfig);
	Page scheduled_notify_email = user.getScheduledNotifyEmailPage(mysession, db, myconfig);
	Page scheduled_unpublish_email = user.getScheduledUnpublishEmailPage(mysession, db, myconfig);
%>
<%@ include file="view.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>