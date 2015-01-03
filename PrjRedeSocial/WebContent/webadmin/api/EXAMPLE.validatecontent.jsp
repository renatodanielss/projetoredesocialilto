<%@ include file="../config.jsp" %><%@ page import="java.util.regex.*" %><%@ page import="HardCore.RequireUser" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.Fileupload" %><%@ page import="HardCore.http" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.MenuContent" %><%@ page import="HardCore.html" %><%

// "/webadmin/api/validatecontent.jsp" example/template file
// Rename and modify this file to use your own custom content validation functionality

String error = "";
Fileupload filepost = new Fileupload(myrequest, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath"), myconfig.get(db, "URLuploadpath"), -1);
String id = myrequest.getParameter("id");
String title = filepost.getParameter("title");
String content = filepost.getParameter("content");
String summary = filepost.getParameter("summary");

if (false) {
        error = "ERROR:CONFIRM:" + "There is a problem with the content.\r\n.....\r\nSelect \"OK\" to save or \"Cancel\" to edit the content.";
} else if (false) {
        error = "ERROR:ALERT:" + "There is a problem with the content.\r\n.....\r\nThis problem must be resolved before the content can be saved.";
} else if (false) {
        error = "OK:ALERT:" + "There were no problems with the content.\r\n.....\r\nThe content will be saved.";
} else {
        error = "OK"; // silently save the content
}

%><%= error %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>