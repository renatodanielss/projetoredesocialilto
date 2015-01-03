<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.MenuContent" %><%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Content content;
	if (! myrequest.getMethod().equals("POST")) {
		content = maintainContent.getMetaData(mysession, myrequest, myresponse, myconfig, db);
		%><%@ include file="metadata.jsp.html" %><%
	} else if (myrequest.getParameter("format").equals("csv")) {
		content = maintainContent.doMetaData(mysession, myrequest, myresponse, myconfig, db);
		%><%@ include file="metadata_report.jsp.csv" %><%
	} else {
		content = maintainContent.doMetaData(mysession, myrequest, myresponse, myconfig, db);
		%><%@ include file="metadata_report.jsp.html" %><%
	}
%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>