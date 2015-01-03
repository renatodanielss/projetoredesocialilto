<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainFileformats" %>
<%@ page import="HardCore.Fileformat" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainFileformats maintainFileformats = new UCmaintainFileformats(mytext);
	Fileformat fileformat = maintainFileformats.getCreate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="create.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>