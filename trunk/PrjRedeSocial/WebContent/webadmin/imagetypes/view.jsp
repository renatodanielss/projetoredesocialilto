<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainImagetypes" %>
<%@ page import="HardCore.Imagetype" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainImagetypes maintainImagetypes = new UCmaintainImagetypes(mytext);
	Imagetype imagetype = maintainImagetypes.getView(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="view.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>