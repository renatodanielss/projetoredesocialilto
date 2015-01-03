<%@ include file="../config.jsp" %>
<%@ page import="HardCore.RequireUser" %>
<%
	boolean accesspermission = RequireUser.SuperAdministrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
	if (accesspermission) Cache.clear(db);
%>
ok
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>