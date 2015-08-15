<%@ page buffer="256kb" %><%@ include file="../webadmin.jsp" %><%@ page import="com.iliketo.dao.MemberDAO" %>
<%@ page import="com.iliketo.model.Member" %><%

//Declarando chave de recuperação de senha
String uuid1 = "";
String uuid2 = "";
String uuid = "";

MemberDAO memberDao = new MemberDAO(db, request);
Member member = new Member();
member = (Member) memberDao.readByColumn("email", myrequest.getParameter("email"), Member.class);

System.out.println("Email: " + myrequest.getParameter("email"));
System.out.println("Username: " + myrequest.getParameter("username"));

//Gerando chave de recuperação de senha
if (member.getIdMember() != null){
	if (member.getRetrievePassword().equals("0") || member.getRetrievePassword().equals("1")){
		uuid1 = CmsConfigILiketo.generateRandomKey(32);
		uuid2 = CmsConfigILiketo.generateRandomKey(32);
		uuid = uuid1+uuid2;
		member.setRetrievePassword(uuid);
		memberDao.update(member);
	} else{
		uuid = member.getRetrievePassword();
	}
}

UCbrowseWebsite browseWebsite = new UCbrowseWebsite(mytext, uuid);
Page mypage = browseWebsite.doRetrievePassword(servletcontext, mysession, myrequest, myresponse, myconfig, db, website);
cms.CMSpage(myrequest.getParameter("id"), mypage);
cms.HttpHeaders(myrequest.getParameter("id"));
if (! myconfig.get(db, "charset").equals("")) myresponse.setContentType("text/html; charset=" + myconfig.get(db, "charset"));

%><%= cms.PageDOCTYPE(myrequest.getParameter("id")) %>
<html>
<head>
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
<% cms.CMSLog(mypage.getId(), "page", ""); %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>