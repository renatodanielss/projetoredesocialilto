<%@ include file="../config.jsp" %>
<%@ page import="HardCore.html" %>
<%@ page import="HardCore.Fileupload" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%

	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.getView(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	Fileupload filepost = maintainContent.getFileupload(DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, -1);
%>
<%@ include file="compare.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>