<%@ include file="../webadmin.jsp" %><%@ include file="../config.personal.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainUsers" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.User" %><%@ page import="HardCore.RequireUser" %><%

	mytext = new Text(myrequest);

	mysession.set("mode", "");
	RequireUser.User(mytext, mysession.get("username"), myrequest, myresponse, mysession, db);

	UCmaintainUsers maintainUsers = new UCmaintainUsers(mytext);
	User myuser = maintainUsers.doPersonalUpdate(mysession, myrequest, myresponse, myconfig, db);

	Login.userSession(myuser, mysession, db);

	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.doPersonalUpdate(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);

	Page adminpage = maintainContent.getPersonalAdmin(myuser, mypage, servletcontext, mysession, myrequest, myresponse, myconfig, db);
	mysession.set("id", adminpage.getId());
%>
<%@ include file="admin.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>