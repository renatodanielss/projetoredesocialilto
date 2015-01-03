<%@ include file="../config.jsp" %><%@ page import="HardCore.RequireUser" %><%@ page import="HardCore.UCmaintainUsers" %><%@ page import="HardCore.User" %><%@ page import="HardCore.MenuContent" %><%
	RequireUser.Administrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
	myconfig.setTemp("use_userdatabase", "yes");
	myconfig.setTemp("use_userdirectory", "");
	UCmaintainUsers maintainUsers = new UCmaintainUsers(mytext);
	User user;
	if (! myrequest.getMethod().equals("POST")) {
		user = maintainUsers.getExport(mysession, myrequest, myresponse, myconfig, db);
		%><%@ include file="export.jsp.html" %><%
	} else {
		user = maintainUsers.doExport(mysession, myrequest, myresponse, myconfig, db);
		%><%@ include file="export.jsp.csv" %><%
	}
%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>