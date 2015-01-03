<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainHosting" %><%@ page import="HardCore.html" %><%
	UCmaintainHosting maintainHosting = new UCmaintainHosting(mytext);
	LinkedHashMap my_ini = maintainHosting.getIndex(inifile, mysession, myrequest, myresponse, myconfig, db);
	int recordCount = maintainHosting.getRecordCount();
	String error = "";

	myresponse.setContentType("text/xml");
	myresponse.setHeader("Cache-Control", "no-cache");
	myresponse.setHeader("Pragma", "no-cache");
//	myresponse.setHeader("Expires", "-1");
	myresponse.setDateHeader("Expires", new java.util.Date().getTime());
	myresponse.setDateHeader("Last-Modified", new java.util.Date().getTime());

%><%@ include file="index.xml.jsp.xml" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>