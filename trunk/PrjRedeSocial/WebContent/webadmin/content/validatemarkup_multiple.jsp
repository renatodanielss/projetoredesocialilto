<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%

	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	String content = maintainContent.doValidateMarkupMultiple(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);

%>
<%@ include file="validatemarkup_multiple.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>