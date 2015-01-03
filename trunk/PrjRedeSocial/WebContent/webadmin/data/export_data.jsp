<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Databases" %><%@ page import="HardCore.MenuContent" %><%@ page import="HardCore.html" %><%@ page import="HardCore.Common" %><%
	UCmaintainData maintainData = new UCmaintainData(mytext);
	maintainData.doExport(out, mysession, myrequest, myresponse, myconfig, db);
%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>