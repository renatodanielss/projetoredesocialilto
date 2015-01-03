<%@ include file="../config.jsp" %><%@ page import="HardCore.RequireUser" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Workflow" %><%

	boolean accesspermission = RequireUser.isAdministrator(mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), db.getDatabase(), mysession.get("database"));
	if (accesspermission) {
		UCmaintainContent maintainContent = new UCmaintainContent(mytext);
		Content pageCreate = maintainContent.getIndex(mysession, myrequest, myresponse, myconfig, db, false, true);
		Workflow workflow = new Workflow(mytext);
		%><%@ include file="index.create.jsp.html" %><%
	} else {
		if (! myresponse.getResponse().isCommitted()) {
			myresponse.getResponse().sendError(403);
		}
	}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>