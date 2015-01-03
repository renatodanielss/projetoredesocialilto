<%@ page buffer="256kb" %><%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainUsers" %><% cms.CMSLog(myrequest.getParameter("id"), "subscribe", ""); %><%

UCmaintainUsers maintainUsers = new UCmaintainUsers(mytext);
maintainUsers.doSubscribe(mysession, myrequest, myresponse, myconfig, db);

%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>