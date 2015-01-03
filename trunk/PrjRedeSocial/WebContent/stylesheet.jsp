<%@ page buffer="256kb" %><%@ include file="webadmin.jsp" %><%

cms.ReadStyleSheet(myrequest.getParameter("id"));

%><%= cms.StyleSheetHeader() %>
<%= cms.DisplayStyleSheet(myrequest.getParameter("id"), "content", "") %>
<% cms.CMSLog(myrequest.getParameter("id"), "stylesheet", ""); %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>