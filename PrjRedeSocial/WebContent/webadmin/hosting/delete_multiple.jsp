<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainHosting" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainHosting maintainHosting = new UCmaintainHosting(mytext);
	HashMap my_ini = maintainHosting.getDeleteMultiple(inifile, mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete_multiple.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>