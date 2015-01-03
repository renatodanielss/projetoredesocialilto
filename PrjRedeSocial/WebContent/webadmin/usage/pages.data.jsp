<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCreportUsage" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Common" %>
<%@ page import="HardCore.Login" %>
<%
	mysession.set("log", "");
	Login.login(mytext, null, "-", "-", servletcontext, mysession, myrequest, myresponse, myconfig, db, "", database);
	if (mysession.get("administrator").equals("administrator")) {
		UCreportUsage reportUsage = new UCreportUsage(mytext);
		HashMap usage = reportUsage.getPages(mysession, myrequest, myresponse, myconfig, db, logdb, database);
%>
<%@ include file="pages.data.jsp.html" %>
<%
	}
%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>