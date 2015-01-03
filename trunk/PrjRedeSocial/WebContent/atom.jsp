<%@ page buffer="256kb" %><%@ include file="webadmin.jsp" %><%

String save_session_mode = mysession.get("mode");
mysession.set("mode", "");
cms.ReadPage(myrequest.getParameter("id"));
myresponse.setContentType("application/atom+xml");
HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, out, cms.DisplayPage(myrequest.getParameter("id"), "body", ""));
mysession.set("mode", save_session_mode);

%><% cms.CMSLog(myrequest.getParameter("id"), "page", ""); %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>