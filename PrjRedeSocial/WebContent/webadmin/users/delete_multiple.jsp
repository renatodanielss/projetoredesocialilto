<%@ include file="../config.jsp" %>
<%@ page import="HardCore.RequireUser" %>
<%@ page import="HardCore.UCmaintainUsers" %>
<%@ page import="HardCore.User" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%
	RequireUser.Administrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
	myconfig.setTemp("use_userdatabase", "yes");
	myconfig.setTemp("use_userdirectory", "");
	UCmaintainUsers maintainUsers = new UCmaintainUsers(mytext);
	User user = maintainUsers.getDeleteMultiple(mysession, myrequest, myresponse, myconfig, db);
	String error = maintainUsers.getError();
%>
<%@ include file="delete_multiple.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>