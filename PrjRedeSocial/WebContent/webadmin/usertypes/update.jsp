<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainUsertypes" %>
<%@ page import="HardCore.Usertype" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Page" %>
<%
	UCmaintainUsertypes maintainUsertypes = new UCmaintainUsertypes(mytext);
	Usertype usertype = maintainUsertypes.getUpdate(mysession, myrequest, myresponse, myconfig, db);
	HashMap subtypes = usertype.subtypes(db);
	HashMap supertypes = usertype.supertypes(db);
	Page mypage = new Page(mytext);
%>
<%@ include file="update.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>