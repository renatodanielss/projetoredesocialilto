<%@ include file="../config.jsp" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.Usertest" %><%

	Page mypage = new Page();
	Usertest usertest = new Usertest();
	usertest.readUsertest(db, myrequest.getParameter("usertest"));

%><%@ include file="usertest.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>