<%@ page buffer="256kb" %><%@ include file="config.jsp" %><%

HashMap data = new HashMap();
data.put("id", myrequest.getParameter("id"));
data.put("action", myrequest.getParameter("action"));
data.put("logged", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
data.put("x", myrequest.getParameter("x"));
data.put("y", myrequest.getParameter("y"));
data.put("w", myrequest.getParameter("w"));
data.put("h", myrequest.getParameter("h"));
db.insert("heatmaps", data);

%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>