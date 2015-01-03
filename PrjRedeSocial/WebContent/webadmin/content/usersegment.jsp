<%@ include file="../config.jsp" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.Segment" %><%

	Page mypage = new Page();
	Segment usersegment = new Segment();
	usersegment.readSegmentId(db, myrequest.getParameter("usersegment"));

%><%@ include file="usersegment.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>