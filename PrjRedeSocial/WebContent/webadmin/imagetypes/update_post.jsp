<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainImagetypes" %>
<%@ page import="HardCore.Imagetype" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainImagetypes maintainImagetypes = new UCmaintainImagetypes(mytext);
	Imagetype imagetype = maintainImagetypes.doUpdate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="update_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>