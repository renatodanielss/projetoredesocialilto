<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainData" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Data" %>
<%@ page import="HardCore.Databases" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.html" %>
<%@ page import="HardCore.Common" %>
<%@ page import="HardCore.Fileupload" %>
<%
	UCmaintainData maintainData = new UCmaintainData(mytext);
	String error = maintainData.doImport(DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);
	Fileupload filepost = maintainData.filepost;
%>
<%@ include file="import_data.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>