<%@ include file="../../config.jsp" %><%@ page import="HardCore.RequireUser" %><%@ page import="HardCore.MenuContent" %><% RequireUser.Administrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, database, mysession.get("database")); %><%



String title = "Example Module";

String content = "<b>Example Module</b><br>This is the Example Module page";

String menu = "<div class=\"dtree\"><img src=\"/" + mytext.display("adminpath") + "/dtree/base.gif\"><a href=\"/" + mytext.display("adminpath") + "/module/example/index.jsp\">Example Module</a></div>";



%><%@ include file="../../module.jsp.html" %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>