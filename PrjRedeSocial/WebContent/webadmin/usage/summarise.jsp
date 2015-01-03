<%@ include file="../config.jsp" %><%@ page import="HardCore.RequireUser" %><%@ page import="HardCore.UsageLog" %><%@ page import="HardCore.UCreportUsage" %><%@ page import="HardCore.MenuContent" %><%

if ((! myrequest.getParameter("username").equals("")) && (! myrequest.getParameter("password").equals(""))) {
	Login.login(mytext, null, "/login.jsp", "-", servletcontext, mysession, myrequest, myresponse, myconfig, db, myconfig.get(db, "require_ssl_user"), database);
}
boolean accesspermission = RequireUser.SuperAdministrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
if (accesspermission) {

	if (myrequest.parameterExists("summarised_period")) {
		if ((! myrequest.getParameter("commit").equals("")) || (! myrequest.getParameter("all").equals(""))) {
			myconfig.set(db, "summarised_period", myrequest.getParameter("summarised_period"));
		} else {
			myconfig.setTemp("summarised_period", myrequest.getParameter("summarised_period"));
		}
	}
	if (myrequest.parameterExists("summarised_details")) {
		if ((! myrequest.getParameter("commit").equals("")) || (! myrequest.getParameter("all").equals(""))) {
			myconfig.set(db, "summarised_details", myrequest.getParameter("summarised_details"));
		} else {
			myconfig.setTemp("summarised_details", myrequest.getParameter("summarised_details"));
		}
	}

	%><%@ include file="summarise.jsp.html" %><%

}

%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>