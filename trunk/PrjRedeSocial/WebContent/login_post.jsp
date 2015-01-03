<%@ include file="webadmin.jsp" %><%

mysession.set("log", "login");
UCbrowseWebsite browseWebsite = new UCbrowseWebsite(mytext);
browseWebsite.doLogin(cms, servletcontext, mysession, myrequest, myresponse, myconfig, db, database);

%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>