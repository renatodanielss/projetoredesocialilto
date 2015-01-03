<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainFileformats" %>
<%@ page import="HardCore.Fileformat" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainFileformats maintainFileformats = new UCmaintainFileformats(mytext);
	Fileformat fileformat = maintainFileformats.getIndex(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>