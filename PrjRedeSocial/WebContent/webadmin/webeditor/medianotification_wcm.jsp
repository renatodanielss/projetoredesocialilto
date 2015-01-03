<%@ page pageEncoding="UTF-8" %>
<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainMedia" %>
<%@ page import="HardCore.Page" %>
<%
	Page content = new Page(mytext);
	UCmaintainMedia maintainMedia = new UCmaintainMedia(mytext);
	maintainMedia.getAccess(mysession, myrequest, myresponse, myconfig, db);
	if ((adminuser.getHardcoreUpload().equals("yes")) || ((adminuser.getHardcoreUpload().equals("")) && (myconfig.get(db, "hardcore_upload").equals("yes")))) {
		if (myrequest.getParameter("action").equals("Delete")) {
			content = maintainMedia.doDelete(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
		} else if (myrequest.getParameter("action").equals("Create")) {
			content = maintainMedia.doCreate(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
		} else if (myrequest.getParameter("action").equals("Update")) {
			content = maintainMedia.doUpdate(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
		}
	}
	String error = maintainMedia.getError();
%>
<%@ include file="medianotification_wcm.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>