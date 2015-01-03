<%@ include file="webadmin.jsp" %><%

%><% cms.CMSLog(myrequest.getParameter("id"), "logout", ""); %><%

UCbrowseWebsite browseWebsite = new UCbrowseWebsite(mytext);
browseWebsite.doLogout(mysession, myrequest, myresponse, myconfig, db);

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>