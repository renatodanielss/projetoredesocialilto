<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainFiletypes" %>
<%@ page import="HardCore.Filetype" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainFiletypes maintainFiletypes = new UCmaintainFiletypes(mytext);
	Filetype filetype = maintainFiletypes.doDelete(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="delete_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>