<%@ include file="../config.jsp" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.UCmaintainContent" %><%

	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.getView(servletcontext, mysession, myrequest, myresponse, myconfig, db);

%><%= mypage.getId()+":::"+mypage.getVersionMaster()+":::"+mypage.getVersion()+":::"+mypage.getDevice()+":::"+mypage.getUsersegment()+":::"+mypage.getUsertest()+":::"+mypage.getTitle()+":::" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>