<%@ include file="../config.jsp" %><%@ page import="HardCore.RequireUser" %><%@ page import="HardCore.UCmaintainUsers" %><%@ page import="HardCore.MenuContent" %><%
	RequireUser.Administrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
	myconfig.setTemp("use_userdatabase", "yes");
	myconfig.setTemp("use_userdirectory", "");
	UCmaintainUsers maintainUsers = new UCmaintainUsers(mytext);
	if (! myrequest.getMethod().equals("POST")) {
		maintainUsers.getImport(mysession, myrequest, myresponse, myconfig, db);
	} else {
		maintainUsers.doImport(out, servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
	}
%>
<%@ include file="import.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>