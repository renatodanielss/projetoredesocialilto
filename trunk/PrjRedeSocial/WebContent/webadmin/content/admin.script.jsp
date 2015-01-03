<%@ page import="HardCore.File" %><%@ page import="HardCore.Image" %><%@ include file="../config.jsp" %>
<%
	String save_session_mode = mysession.get("mode");
	String save_session_preview_scheduled = mysession.get("preview_scheduled");
%>
<%@ include file="admin.script.jsp.js" %>
<%
	mysession.set("mode", save_session_mode);
	mysession.set("preview_scheduled", save_session_preview_scheduled);

%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>