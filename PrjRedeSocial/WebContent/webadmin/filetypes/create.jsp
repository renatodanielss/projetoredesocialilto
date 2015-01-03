<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainFiletypes" %>
<%@ page import="HardCore.Filetype" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainFiletypes maintainFiletypes = new UCmaintainFiletypes(mytext);
	Filetype filetype = maintainFiletypes.getCreate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="create.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>