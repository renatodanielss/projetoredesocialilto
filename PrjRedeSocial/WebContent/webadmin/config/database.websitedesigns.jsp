<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCconfigureSystem" %>
<%@ page import="HardCore.Currency" %>
<%@ page import="HardCore.DB" %>
<%@ page import="HardCore.Fileupload" %>
<%@ page import="HardCore.Page" %>
<%@ page import="HardCore.User" %>
<%@ page import="HardCore.RequireUser" %>
<%@ page import="HardCore.MenuContent" %>
<% if (RequireUser.Administrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"))) { %>
<%@ include file="database.websitedesigns.jsp.html" %>
<% } %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>