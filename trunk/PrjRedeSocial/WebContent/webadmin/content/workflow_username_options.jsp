<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.Fileupload" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.Workflow" %><%@ page import="HardCore.MenuContent" %><%@ page import="HardCore.html" %><%@ page import="HardCore.Common" %><%

UCmaintainContent maintainContent = new UCmaintainContent(mytext);
Page mypage = maintainContent.getView(servletcontext, mysession, myrequest, myresponse, myconfig, db);
if (myrequest.getParameter("id").equals("")) {
	mypage.setId("-");
	mypage.setContentClass(myrequest.getParameter("contentclass"));
	mypage.setContentGroup(myrequest.getParameter("contentgroup"));
	mypage.setContentType(myrequest.getParameter("contenttype"));
	mypage.setVersion(myrequest.getParameter("version"));
}
if ((myrequest.getParameter("workflow").equals("")) || (myrequest.getParameter("workflow").equals("0"))) {
	%><%= adminuser.select_options_where(db, mypage.getCheckedOut(), "where userclass='administrator'") %><%
} else {
	Fileupload filepost = maintainContent.getFileupload(DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
	mypage.getForm(db, myconfig, filepost);
	Workflow workflow = new Workflow(mytext);
	if (! myrequest.getParameter("workflow").equals("")) {
		workflow.read(db, myrequest.getParameter("workflow"));
	} else {
		workflow.setId("-");
		workflow.setToState(mypage.getStatus());
	}

	mypage.setStatus(workflow.getToState());

	String[] adminusernames = workflow.adminUsernames(mypage, mysession, myconfig, db);
	java.util.Arrays.sort(adminusernames);
	for (int i=0; i<adminusernames.length; i++) {
		%><option value="<%= adminusernames[i] %>"><%= adminusernames[i] %></option><%
	}
}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>