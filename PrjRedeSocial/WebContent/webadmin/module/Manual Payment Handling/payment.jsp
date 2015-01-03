<%@ include file="../../config.jsp" %>
<%

String output = "";

mysession.set("payment", output);

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>