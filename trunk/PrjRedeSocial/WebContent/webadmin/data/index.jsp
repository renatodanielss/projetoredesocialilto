<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainData" %>
<%@ page import="HardCore.Data" %>
<%@ page import="HardCore.Databases" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.MenuContent" %>
<%
	Databases datadatabase = new Databases(mytext);
	datadatabase.read(db, myconfig, myrequest.getParameter("database"), mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"));
	UCmaintainData maintainData = new UCmaintainData(mytext);
	Data data = maintainData.getIndex(mysession, myrequest, myresponse, myconfig, db);
	int recordCount = maintainData.getRecordCount();
	Page mypage = new Page();
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>