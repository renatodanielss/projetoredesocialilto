<%@ include file="../../config.jsp" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.RequireUser" %>
<%

if (License.valid(db, myconfig, "community")) {
	RequireUser.Administrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));

	String save_stylesheet = mysession.get("stylesheet");
	String save_template = mysession.get("template");
	String save_version = mysession.get("version");
	String save_mode = mysession.get("mode");
	mysession.set("stylesheet", "");
	mysession.set("template", "");
	mysession.set("version", "");
	mysession.set("mode", "admin");

	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.doDelete(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
	String error = maintainContent.getError();

	mysession.set("stylesheet", save_stylesheet);
	mysession.set("template", save_template);
	mysession.set("version", save_version);
	mysession.set("mode", save_mode);
}

if (! myrequest.getParameter("redirect").equals("")) {
	myresponse.sendRedirect(myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + myrequest.getParameter("redirect"));
}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>