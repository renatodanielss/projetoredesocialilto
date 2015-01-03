<%@ include file="../config.jsp" %><%@ page import="HardCore.RequireUser" %><%@ page import="HardCore.UCmaintainUsers" %><%@ page import="HardCore.User" %><%@ page import="HardCore.MenuContent" %><%
	RequireUser.Administrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
	myconfig.setTemp("use_userdatabase", "yes");
	myconfig.setTemp("use_userdirectory", "");
	UCmaintainUsers maintainUsers = new UCmaintainUsers(mytext);
	User userEdit = maintainUsers.doSearch(mysession, myrequest, myresponse, myconfig, db);
	int recordCount = maintainUsers.getRecordCount();
	String error = "";

	myresponse.setContentType("text/xml");
	myresponse.setHeader("Cache-Control", "no-cache");
	myresponse.setHeader("Pragma", "no-cache");
//	myresponse.setHeader("Expires", "-1");
	myresponse.setDateHeader("Expires", new java.util.Date().getTime());
	myresponse.setDateHeader("Last-Modified", new java.util.Date().getTime());

%><%@ include file="search.xml.jsp.xml" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>