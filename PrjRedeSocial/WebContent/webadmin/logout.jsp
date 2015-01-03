<%@ include file="config.jsp" %><%@ page import="HardCore.UCaccessAdministration" %><%

UCaccessAdministration accessAdministration = new UCaccessAdministration();
accessAdministration.doLogout(mysession, myrequest, myresponse, myconfig, db);

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>