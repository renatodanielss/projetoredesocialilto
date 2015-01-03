<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainHosting" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainHosting maintainHosting = new UCmaintainHosting(mytext);
	LinkedHashMap my_ini = maintainHosting.getIndex(inifile, mysession, myrequest, myresponse, myconfig, db);
	int recordCount = maintainHosting.getRecordCount();
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>