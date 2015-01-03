<%@ include file="webadmin.jsp" %><% cms.CMSLog(myrequest.getParameter("template"), "contact", ""); %><%

	UCbrowseWebsite browseWebsite = new UCbrowseWebsite(mytext);
	browseWebsite.doEmail(servletcontext, mysession, myrequest, myresponse, myconfig, db);

%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>