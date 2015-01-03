<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainUsertypes" %>
<%@ page import="HardCore.Usertype" %>
<%@ page import="HardCore.MenuContent" %>
<%
	UCmaintainUsertypes maintainUsertypes = new UCmaintainUsertypes(mytext);
	Usertype usertype = maintainUsertypes.doCreate(mysession, myrequest, myresponse, myconfig, db);
%>
<%@ include file="create_post.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>