<%@ include file="../config.jsp" %>
<%@ page import="HardCore.RequireUser" %>
<%@ page import="HardCore.UCmaintainUsers" %>
<%@ page import="HardCore.User" %>
<%@ page import="HardCore.MenuContent" %>
<%
	RequireUser.Administrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
	myconfig.setTemp("use_userdatabase", "yes");
	myconfig.setTemp("use_userdirectory", "");
	UCmaintainUsers maintainUsers = new UCmaintainUsers(mytext);
	User userEdit = maintainUsers.doSearch(mysession, myrequest, myresponse, myconfig, db);
	User userCreate = maintainUsers.getCreateRecords();
	String error = maintainUsers.getError();
%>
<%@ include file="search.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>