<%@ include file="../webadmin.jsp" %><%

UCbrowseWebsite browseWebsite = new UCbrowseWebsite(mytext);
cms.ReadPersonal();
if (! myconfig.get(db, "charset").equals("")) myresponse.setContentType("text/html; charset=" + myconfig.get(db, "charset"));

%><%= cms.PersonalDOCTYPE() %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=<%= myconfig.get(db, "charset") %>"<%= cms.XHTMLclosed %>>
<meta name="author" content="<%= cms.PageHeader(myrequest.getQueryString(), "author", "") %>"<%= cms.XHTMLclosed %>>
<meta name="description" content="<%= cms.PageHeader(myrequest.getQueryString(), "description", "") %>"<%= cms.XHTMLclosed %>>
<meta name="keywords" content="<%= cms.PageHeader(myrequest.getQueryString(), "keywords", "") %>"<%= cms.XHTMLclosed %>>
<title><%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getQueryString(), "title", "")) %></title>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getQueryString(), "metainfo", "")) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.CMSHeader(myrequest.getQueryString())) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageStyleSheet(myrequest.getQueryString())) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageScripts(myrequest.getQueryString())) %>
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getQueryString(), "htmlheader", "")) %>
</head>
<body<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getQueryString(), "htmlbodyonload", "", cms.BODYmargins)) %>>
<%= cms.CMSDisplay(myrequest.getQueryString()) %>
<%= cms.CMSStyleSheet(myrequest.getQueryString()) %>
<%= cms.CMSTemplate(myrequest.getQueryString()) %>
<% HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, out, cms.DisplayPage(myrequest.getQueryString(), "body", "")); %>
</body>
</html>
<% cms.CMSLog(myrequest.getQueryString(), "page", ""); %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>