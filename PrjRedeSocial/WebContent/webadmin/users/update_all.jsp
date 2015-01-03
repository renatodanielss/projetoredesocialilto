<%@ include file="../config.jsp" %><%@ page import="HardCore.RequireUser" %><%@ page import="HardCore.UCmaintainUsers" %><%@ page import="HardCore.User" %><%@ page import="HardCore.MenuContent" %><%
	RequireUser.Administrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
	myconfig.setTemp("use_userdatabase", "yes");
	myconfig.setTemp("use_userdirectory", "");
	UCmaintainUsers maintainUsers = new UCmaintainUsers(mytext);
	User user;
	user = maintainUsers.doExport(mysession, myrequest, myresponse, myconfig, db);
	%><%@ include file="update_all.jsp.csv" %><%
%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>