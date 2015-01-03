<%@ page buffer="256kb" %><%@ include file="webadmin.jsp" %><%

UCbrowseWebsite browseWebsite = new UCbrowseWebsite(mytext);
Page content = browseWebsite.getManifest(servletcontext, mysession, myrequest, myresponse, myconfig, db, website);

String save_session_mode = mysession.get("mode");
mysession.set("mode", "");

myresponse.setContentType("text/cache-manifest");

%>CACHE MANIFEST

<% HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, out, content.getBody()); %>
<%
mysession.set("mode", save_session_mode);
%>
<% cms.CMSLog(myrequest.getParameter("id"), "page", ""); %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>