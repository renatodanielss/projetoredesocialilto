<%@ page buffer="256kb" %><%@ include file="webadmin.jsp" %><%

cms.ReadPage(myrequest.getParameter("id"));
cms.HttpHeaders(myrequest.getParameter("id"));
if (! myconfig.get(db, "charset").equals("")) myresponse.setContentType("text/html; charset=" + myconfig.get(db, "charset"));

%><%= cms.PageDOCTYPE(myrequest.getParameter("id")) %>
<html<%= cms.PageHTML(myrequest.getParameter("id")) %>>
<head><% if (! cms.Page(myrequest.getParameter("id")).getServerFilename().equals("")) { %><base href="<%= myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + myconfig.getTemp("URLrootpath") + cms.Page(myrequest.getParameter("id")).getServerFilename() %>"><!--[if lte IE 6]></base><![endif]--><% } %>
<meta http-equiv="Content-Type" content="text/html; charset=<%= myconfig.get(db, "charset") %>"<%= cms.XHTMLclosed %>>
<meta name="author" content="<%= cms.PageHeader(myrequest.getParameter("id"), "author", "") %>"<%= cms.XHTMLclosed %>>
<meta name="description" content="<%= cms.PageHeader(myrequest.getParameter("id"), "description", "") %>"<%= cms.XHTMLclosed %>>
<meta name="keywords" content="<%= cms.PageHeader(myrequest.getParameter("id"), "keywords", "") %>"<%= cms.XHTMLclosed %>>
<title><%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("id"), "title", "")) %></title>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("id"), "metainfo", "")) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.CMSHeader(myrequest.getParameter("id"))) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageStyleSheet(myrequest.getParameter("id"))) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageScripts(myrequest.getParameter("id"))) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("id"), "htmlheader", "")) %>
</head>
<body<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("id"), "htmlbodyonload", "", cms.BODYmargins)) %>>
<%= cms.CMSDisplay(myrequest.getParameter("id")) %>
<%= cms.CMSStyleSheet(myrequest.getParameter("id")) %>
<%= cms.CMSTemplate(myrequest.getParameter("id")) %>
<% HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, out, cms.DisplayPage(myrequest.getParameter("id"), "body", "")); %>
</body>
</html>
<% cms.CMSLog(myrequest.getParameter("id"), "page", ""); %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>