<%@ page buffer="256kb" %><%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainUsers" %><% cms.CMSLog(myrequest.getParameter("id"), "unsubscribe", ""); %><%

UCmaintainUsers maintainUsers = new UCmaintainUsers(mytext);
maintainUsers.doUnsubscribe(mysession, myrequest, myresponse, myconfig, db);

%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>