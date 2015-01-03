<%@ include file="config.jsp" %>
<%@ page import="HardCore.UCaccessAdministration" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.http" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Workflow" %>
<%@ page import="HardCore.UCmaintainContent" %>
<%
	if (UCaccessAdministration.doIndex(mytext, wizard, mysession, myrequest, myresponse, myconfig, db)) {
		UCmaintainContent maintainContent = new UCmaintainContent(mytext);
//		maintainContent.getIndexPersonal(mysession, myrequest, myresponse, myconfig, db);
//		Content pageCreated = maintainContent.getCreatedRecords();
//		Content pageExpired = maintainContent.getExpiredRecords();
//		Content pageUpdated = maintainContent.getUpdatedRecords();
//		Content pageCheckedout = maintainContent.getCheckedoutRecords();
//		Content pageWorkflow = maintainContent.getWorkflowRecords();
		Workflow workflow = new Workflow(mytext);
		String column_name = mytext.display("menu.content");
		Content mypage = new Content();
		boolean myworkflows = false;
%>
<%@ include file="index.jsp.html" %>
<%
//		pageCreated.closeRecords(db);
//		pageExpired.closeRecords(db);
//		pageUpdated.closeRecords(db);
//		pageCheckedout.closeRecords(db);
//		pageWorkflow.closeRecords(db);
		mypage.closeRecords(db);
	}
%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>