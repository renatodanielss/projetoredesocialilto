<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Website" %><%@ page import="HardCore.MenuContent" %><%
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	boolean accesspermission = maintainContent.getStructure(servletcontext, mysession, myrequest, myresponse, myconfig, db);
	String error = maintainContent.getError();
	if (accesspermission) {
		%><%@ include file="structure.jsp.html" %><%
	}
%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>