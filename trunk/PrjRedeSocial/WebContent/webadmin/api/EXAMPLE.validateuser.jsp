<%@ include file="../config.jsp" %><%@ page import="java.util.regex.*" %><%@ page import="HardCore.RequireUser" %><%@ page import="HardCore.UCmaintainUsers" %><%@ page import="HardCore.Fileupload" %><%@ page import="HardCore.http" %><%@ page import="HardCore.User" %><%@ page import="HardCore.MenuContent" %><%@ page import="HardCore.html" %><%

// "/webadmin/api/validateuser.jsp" example/template file
// Rename and modify this file to use your own custom user validation functionality

String error = "";
Fileupload filepost = new Fileupload(myrequest, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath"), myconfig.get(db, "URLuploadpath"), -1);
String id = myrequest.getParameter("id");
String name = filepost.getParameter("name");
String email = filepost.getParameter("email");
String username = filepost.getParameter("username");
String password = filepost.getParameter("password");
String xxxxxxxxxx = filepost.getParameter("xxxxxxxxxx");

if (myrequest.getParameter("request").equals("/register.jsp")) {
	// validate public website user registration
} else if (myrequest.getParameter("request").equals("/webadmin/users/create.jsp")) {
	// validate add new user through web content management system user database administration
} else if (myrequest.getParameter("request").equals("/webadmin/users/update.jsp")) {
	// validate update user through web content management system user database administration
}

if (false) {
	// website registration - display error and require website administrator to confirm to save
        error = "ERROR:" + "There is a problem with the user account.\r\n.....\r\nPlease check the user account details and try again.";
} else if (false) {
	// web content management system user database administration - display error and require website administrator to confirm to save
        error = "ERROR:CONFIRM:" + "There is a problem with the user account.\r\n.....\r\nSelect \"OK\" to save or \"Cancel\" to edit the user account.";
} else if (false) {
	// web content management system user database administration - display error and do not save
        error = "ERROR:ALERT:" + "There is a problem with the user account.\r\n.....\r\nThis problem must be resolved before the user account can be saved.";
} else if (false) {
	// web content management system user database administration - display notice and save
        error = "OK:ALERT:" + "There were no problems with the user account.\r\n.....\r\nThe user account will be saved.";
} else {
	// website registration - register (depending on other validation)
	// web content management system user database administration - save
        error = "OK";
}

%><%= error %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>