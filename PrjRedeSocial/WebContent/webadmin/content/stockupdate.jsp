<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%

	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	String output = maintainContent.doStock(servletcontext, mysession, myrequest, myresponse, myconfig, db);

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>