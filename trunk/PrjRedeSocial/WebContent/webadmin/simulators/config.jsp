<%@ include file="../../webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.Simulator" %><%@ page import="HardCore.RequireUser" %><%@ page import="HardCore.Fileupload" %><%@ page import="java.net.URLEncoder" %><%

UCmaintainContent maintainContent = new UCmaintainContent(mytext);
Page mypage = maintainContent.doPreview(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
cms.CMSpage(myrequest.getParameter("id"), mypage);

Fileupload filepost = maintainContent.fileupload;

String htmlhead = HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("id"), "metainfo", ""));
htmlhead += HardCore.Page.OutputContent(mytext, servletcontext, request, response, session, null, cms.PageHeader(myrequest.getParameter("id"), "htmlheader", ""));

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>