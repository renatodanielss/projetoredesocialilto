<%@ include file="webadmin.jsp" %><%

cms.ReadImage(myrequest.getParameter("id"));
cms.CMSLog(myrequest.getParameter("id"), "image", "");
if (db != null) db.close();
if (logdb != null) logdb.close();
cms.DisplayImage(myrequest.getParameter("id"));

%>