<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainSegments" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.Segment" %>
<%@ page import="HardCore.UsageLog" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UsageLog usagelog = new UsageLog();
	UCmaintainSegments maintainSegments = new UCmaintainSegments(mytext);
	Segment segment = maintainSegments.getUpdate(mysession, myrequest, myresponse, myconfig, db);
	Page scheduled_publish_email = segment.getScheduledPublishEmailPage(mysession, db, myconfig);
	Page scheduled_notify_email = segment.getScheduledNotifyEmailPage(mysession, db, myconfig);
	Page scheduled_unpublish_email = segment.getScheduledUnpublishEmailPage(mysession, db, myconfig);
%>
<%@ include file="update.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>