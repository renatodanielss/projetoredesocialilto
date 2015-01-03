<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Workflow" %><%@ page import="HardCore.html" %><%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Content pageEdit = maintainContent.getIndex(mysession, myrequest, myresponse, myconfig, db, true, false);
	Workflow workflow = new Workflow(mytext);
	int recordCount = maintainContent.getRecordCount();
	String error = "";

	myresponse.setContentType("text/xml");
	myresponse.setHeader("Cache-Control", "no-cache");
	myresponse.setHeader("Pragma", "no-cache");
//	myresponse.setHeader("Expires", "-1");
	myresponse.setDateHeader("Expires", new java.util.Date().getTime());
	myresponse.setDateHeader("Last-Modified", new java.util.Date().getTime());

%><%@ include file="index.xml.jsp.xml" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>