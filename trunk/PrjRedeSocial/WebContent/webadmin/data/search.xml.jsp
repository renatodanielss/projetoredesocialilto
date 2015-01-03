<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Databases" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %><%
	Databases datadatabase = new Databases(mytext);
	datadatabase.read(db, myconfig, myrequest.getParameter("database"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
	UCmaintainData maintainData = new UCmaintainData(mytext);
	Data data = maintainData.doSearch(mysession, myrequest, myresponse, myconfig, db);
	int recordCount = maintainData.getRecordCount();
	Page mypage = new Page();
	String error = "";

	myresponse.setContentType("text/xml");
	myresponse.setHeader("Cache-Control", "no-cache");
	myresponse.setHeader("Pragma", "no-cache");
//	myresponse.setHeader("Expires", "-1");
	myresponse.setDateHeader("Expires", new java.util.Date().getTime());
	myresponse.setDateHeader("Last-Modified", new java.util.Date().getTime());

%><%@ include file="search.xml.jsp.xml" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>