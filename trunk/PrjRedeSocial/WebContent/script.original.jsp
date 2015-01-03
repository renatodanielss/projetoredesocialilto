<%@ include file="/webadmin.jsp" %><%

cms.ReadScript(myrequest.getParameter("id"));

%><%= cms.ScriptHeader() %>
<%= cms.DisplayScript(myrequest.getParameter("id"), "content", "") %>
<% cms.CMSLog(myrequest.getParameter("id"), "script", ""); %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>