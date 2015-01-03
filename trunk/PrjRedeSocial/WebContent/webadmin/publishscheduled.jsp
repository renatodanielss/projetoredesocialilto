<%@ include file="../webadmin.jsp" %><%

	UCpublishScheduled publishScheduled = new UCpublishScheduled(mytext);
	publishScheduled.doScheduled(servletcontext, mysession, myrequest, myresponse, myconfig, db);

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>