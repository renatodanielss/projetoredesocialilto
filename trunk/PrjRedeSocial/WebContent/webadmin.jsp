<%@ include file="config.jsp" %><%@ page import="HardCore.Cms" %><%@ page import="HardCore.UCbrowseWebsite" %><%@ page import="HardCore.UCpublishScheduled" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Page" %><%

Cms cms = new Cms(this, mytext, servletcontext, mysession, myrequest, myresponse, myconfig, db, logdb, website, DOCUMENT_ROOT, pageContext, out);
//cms.DEBUG = true; cms.DEBUGdatetime = (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS").format(new java.util.Date())); System.out.println("HardCore.Cms:BEGIN:"+cms.DEBUGdatetime+":"+cms.DEBUGdatetime+":"+myrequest.getRequest().getRequestURL()+" - "+myrequest.getRequest().getQueryString()+" - "+myrequest.getRequest().getHeader("cookie")+" - "+this.getClass());

%>