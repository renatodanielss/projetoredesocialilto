<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.MenuContent" %><%@ page import="HardCore.html" %><%

	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.doUpdate(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "UTF-8");
	String error = maintainContent.getError();

	if (! error.equals("")) {
		%><%= "ERROR: " + error %><%
	} else {
		%><%= "OK" %><%
	}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>