<%@ include file="webadmin.jsp" %><%

cms.ReadFile(myrequest.getParameter("id"));
cms.CMSLog(myrequest.getParameter("id"), "file", "");
if (db != null) db.close();
if (logdb != null) logdb.close();
cms.DisplayFile(myrequest.getParameter("id"));

%>