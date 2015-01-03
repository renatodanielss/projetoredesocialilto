<%@ include file="config.jsp" %>
<%@ page import="HardCore.UCaccessAdministration" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCaccessAdministration.doIndex(mytext, "", mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="index.experience.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>