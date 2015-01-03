<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainUsertypes" %>
<%@ page import="HardCore.Usertype" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.Page" %>
<%
	UCmaintainUsertypes maintainUsertypes = new UCmaintainUsertypes(mytext);
	Usertype usertype = maintainUsertypes.getView(mysession, myrequest, myresponse, myconfig, db);
	HashMap subtypes = usertype.subtypes(db);
	HashMap supertypes = usertype.supertypes(db);
	Page mypage = new Page(mytext);
%>
<%@ include file="view.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>