<%@ page pageEncoding="UTF-8" %>
<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainHyperlinks" %>
<%@ page import="HardCore.Page" %>
<%
	Page content = new Page(mytext);
	UCmaintainHyperlinks maintainHyperlinks = new UCmaintainHyperlinks(mytext);
	maintainHyperlinks.getAccess(mysession, myrequest, myresponse, myconfig, db);
	if ((adminuser.getHardcoreUpload().equals("yes")) || ((adminuser.getHardcoreUpload().equals("")) && (myconfig.get(db, "hardcore_upload").equals("yes")))) {
		if (myrequest.getParameter("action").equals("Delete")) {
			content = maintainHyperlinks.doDelete(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
		} else if (myrequest.getParameter("action").equals("Create")) {
			content = maintainHyperlinks.doCreate(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
		} else if (myrequest.getParameter("action").equals("Update")) {
			content = maintainHyperlinks.doUpdate(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
		}
	}
	String error = maintainHyperlinks.getError();
%>
<%@ include file="hyperlinknotification_wcm.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>