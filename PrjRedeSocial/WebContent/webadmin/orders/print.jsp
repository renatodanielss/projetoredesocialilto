<%@ page buffer="256kb" %><%@ include file="../../webadmin.jsp" %><%@ page import="HardCore.RequireUser" %><%

RequireUser.Administrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
RequireUser.OrderAdministrator(mytext, mysession, myrequest, myresponse, myconfig, db);

mysession.set("mode", "preview");
mysession.set("version", "");
mysession.set("stylesheet", "");
mysession.set("template", "");
mysession.set("preview_scheduled", "");
cms.ReadPage(myrequest.getParameter("print"));
cms.HttpHeaders(myrequest.getParameter("print"));
if (! myconfig.get(db, "charset").equals("")) myresponse.setContentType("text/html; charset=" + myconfig.get(db, "charset"));

%><%= cms.PageDOCTYPE(myrequest.getParameter("print")) %>
<html>
<head><% if (! cms.Page(myrequest.getParameter("print")).getServerFilename().equals("")) { %><base href="<%= myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + myconfig.getTemp("URLrootpath") + cms.Page(myrequest.getParameter("print")).getServerFilename() %>"><!--[if lte IE 6]></base><![endif]--><% } %>
<meta http-equiv="Content-Type" content="text/html; charset=<%= myconfig.get(db, "charset") %>"<%= cms.XHTMLclosed %>>
<meta name="author" content="<%= cms.PageHeader(myrequest.getParameter("print"), "author", "") %>"<%= cms.XHTMLclosed %>>
<meta name="description" content="<%= cms.PageHeader(myrequest.getParameter("print"), "description", "") %>"<%= cms.XHTMLclosed %>>
<meta name="keywords" content="<%= cms.PageHeader(myrequest.getParameter("print"), "keywords", "") %>"<%= cms.XHTMLclosed %>>
<title><%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("print"), "title", "")) %></title>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("print"), "metainfo", "")) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.CMSHeader(myrequest.getParameter("print"))) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageStyleSheet(myrequest.getParameter("print"))) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageScripts(myrequest.getParameter("print"))) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("print"), "htmlheader", "")) %>
</head>
<body<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("print"), "htmlbodyonload", "", cms.BODYmargins)) %>>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.DisplayPage(myrequest.getParameter("print"), "body", "")) %>
</body>
</html>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>