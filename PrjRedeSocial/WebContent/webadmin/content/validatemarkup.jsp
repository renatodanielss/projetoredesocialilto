<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%

	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	String content = maintainContent.getValidateMarkup(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);

%><%= content %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>