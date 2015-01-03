<%@ page buffer="256kb" %><%@ include file="webadmin.jsp" %><%

cms.ReadProduct(myrequest.getParameter("id"));
cms.HttpHeaders(myrequest.getParameter("id"));
if (! myconfig.get(db, "charset").equals("")) myresponse.setContentType("text/html; charset=" + myconfig.get(db, "charset"));

%><%= cms.ProductDOCTYPE(myrequest.getParameter("id")) %>
<html<%= cms.ProductHTML(myrequest.getParameter("id")) %>>
<head><% if (! cms.Product(myrequest.getParameter("id")).getServerFilename().equals("")) { %><base href="<%= myrequest.getProtocol() + myrequest.getServerName() + myrequest.getServerPort() + myconfig.getTemp("URLrootpath") + cms.Product(myrequest.getParameter("id")).getServerFilename() %>"><!--[if lte IE 6]></base><![endif]--><% } %>
<meta http-equiv="Content-Type" content="text/html; charset=<%= myconfig.get(db, "charset") %>"<%= cms.XHTMLclosed %>>
<meta name="author" content="<%= cms.ProductHeader(myrequest.getParameter("id"), "author", "") %>"<%= cms.XHTMLclosed %>>
<meta name="description" content="<%= cms.ProductHeader(myrequest.getParameter("id"), "description", "") %>"<%= cms.XHTMLclosed %>>
<meta name="keywords" content="<%= cms.ProductHeader(myrequest.getParameter("id"), "keywords", "") %>"<%= cms.XHTMLclosed %>>
<title><%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.ProductHeader(myrequest.getParameter("id"), "title", "")) %></title>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.ProductHeader(myrequest.getParameter("id"), "metainfo", "")) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.CMSHeader(myrequest.getParameter("id"))) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.ProductStyleSheet(myrequest.getParameter("id"))) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.ProductScripts(myrequest.getParameter("id"))) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.ProductHeader(myrequest.getParameter("id"), "htmlheader", "")) %>
</head>
<body<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.ProductHeader(myrequest.getParameter("id"), "htmlbodyonload", "", cms.BODYmargins)) %>>
<%= cms.CMSDisplay(myrequest.getParameter("id")) %>
<%= cms.CMSStyleSheet(myrequest.getParameter("id")) %>
<%= cms.CMSTemplate(myrequest.getParameter("id")) %>
<% HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, out, cms.DisplayProduct(myrequest.getParameter("id"), "body", "")); %>
</body>
</html>
<% cms.CMSLog(myrequest.getParameter("id"), "product", ""); %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>