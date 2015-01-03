<%@ include file="webadmin.jsp" %><%

cms.ReadLink(myrequest.getParameter("id"));
cms.CMSLog(myrequest.getParameter("id"), "link", "");
if (db != null) db.close();
if (logdb != null) logdb.close();
cms.DisplayLink(myrequest.getParameter("id"));

%>