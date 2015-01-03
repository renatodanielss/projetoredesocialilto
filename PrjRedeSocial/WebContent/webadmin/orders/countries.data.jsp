<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCreportOrders" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Common" %>
<%@ page import="HardCore.Login" %>
<%
	mysession.set("log", "");
	Login.login(mytext, null, "-", "-", servletcontext, mysession, myrequest, myresponse, myconfig, db, "", database);
	if (mysession.get("administrator").equals("administrator")) {
		UCreportOrders reportOrders = new UCreportOrders(mytext);
		HashMap usage = reportOrders.getCountries(mysession, myrequest, myresponse, myconfig, db, logdb, database);
%>
<%@ include file="countries.data.jsp.html" %>
<%
	}
%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>