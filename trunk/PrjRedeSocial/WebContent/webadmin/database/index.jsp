<%@ include file="../config.jsp" %>

<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>

<%

response.sendRedirect(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + mytext.display("adminpath") + "/");

%>