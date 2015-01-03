<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainOrders" %><%@ page import="HardCore.Order" %><%@ page import="HardCore.Workflow" %><%@ page import="HardCore.html" %><%
	UCmaintainOrders maintainOrders = new UCmaintainOrders(mytext);
	Order order = maintainOrders.doSearch(mysession, myrequest, myresponse, myconfig, db);
	Workflow workflow = new Workflow(mytext);
	int recordCount = maintainOrders.getRecordCount();
	String error = "";

	myresponse.setContentType("text/xml");
	myresponse.setHeader("Cache-Control", "no-cache");
	myresponse.setHeader("Pragma", "no-cache");
//	myresponse.setHeader("Expires", "-1");
	myresponse.setDateHeader("Expires", new java.util.Date().getTime());
	myresponse.setDateHeader("Last-Modified", new java.util.Date().getTime());

%><%@ include file="search.xml.jsp.xml" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>