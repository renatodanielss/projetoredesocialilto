<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCconfigureSystem" %>
<%@ page import="HardCore.Currency" %>
<%@ page import="HardCore.DB" %>
<%@ page import="HardCore.Fileupload" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.User" %>
<%@ page import="HardCore.RequireUser" %>
<%@ page import="HardCore.MenuContent" %>
<% if (RequireUser.SuperAdministrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"))) { %>
<%@ include file="database.restorebackup.jsp.html" %>
<% } %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>