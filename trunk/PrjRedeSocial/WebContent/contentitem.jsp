<%@ include file="webadmin.jsp" %><%

UCbrowseWebsite browseWebsite = new UCbrowseWebsite(mytext);
Page mypage = browseWebsite.getElement(servletcontext, mysession, myrequest, myresponse, myconfig, db);

%><% HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, out, mypage.display("content", "", myrequest.getServletPath(), myrequest.getQueryString(), mysession.get("mode"), mysession.get("administrator"))); %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>