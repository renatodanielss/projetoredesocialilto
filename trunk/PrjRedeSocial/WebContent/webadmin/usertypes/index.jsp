<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainUsertypes" %>
<%@ page import="HardCore.Usertype" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainUsertypes maintainUsertypes = new UCmaintainUsertypes(mytext);
	Usertype usertype = maintainUsertypes.getIndex(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="index.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>