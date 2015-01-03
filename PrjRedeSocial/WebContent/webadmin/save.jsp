<%@ include file="config.jsp" %>
<%@ page import="HardCore.RequireUser" %>
<%

if (db != null) RequireUser.Administrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));

%>
<%@ include file="save.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>