<%@ include file="../../webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.Page" %><%

UCmaintainContent maintainContent = new UCmaintainContent(mytext);
Page mypage = maintainContent.doPreview(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
if (mypage.getStyleSheetContents().size() > 0) cms.CMSstylesheet("PREVIEW", ((Content)mypage.getStyleSheetContents().get(0)));
cms.CMSpage(myrequest.getParameter("id"), mypage);
if (! myrequest.getParameter("id").equals("")) {
	mysession.set("id", myrequest.getParameter("id"));
} else if (! myrequest.getParameter("preview").equals("")) {
	mysession.set("id", myrequest.getParameter("preview"));
} else {
	mysession.set("id", mypage.getId());
}
cms.HttpHeaders(myrequest.getParameter("id"));
if (! myconfig.get(db, "charset").equals("")) myresponse.setContentType("text/html; charset=" + myconfig.get(db, "charset"));

%><%= cms.PageDOCTYPE(myrequest.getParameter("id")) %>
<html>
<head><% if (! mypage.getServerFilename().equals("")) { %>
<script type="text/javascript">document.write('<base href="' + document.location.protocol + "//" + document.location.host + '<%= myconfig.getTemp("URLrootpath") + mypage.getServerFilename() %>' + '"><!--[if IE]></base><![endif]-->');</script><% } %>
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
<%= HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.DisplayPage(myrequest.getParameter("id"), "body", "")) %>
</body>
</html>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>