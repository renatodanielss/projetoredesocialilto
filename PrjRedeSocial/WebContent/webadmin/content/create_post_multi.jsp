<%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.Content" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.Fileupload" %><%@ page import="HardCore.Login" %><%@ page import="HardCore.MenuContent" %><%@ page import="HardCore.html" %><%

//	Fileupload filepost = new Fileupload(myrequest, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath"), myconfig.get(db, "URLuploadpath"));
	Fileupload filepost = new Fileupload(myrequest, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath"), myconfig.get(db, "URLuploadpath"), 0 , "UTF-8");
	if (! myrequest.getParameter("LOCALE").equals("")) {
		mytext.init(myrequest.getParameter("LOCALE"));
	} else if (! filepost.getParameter("LOCALE").equals("")) {
		mytext.init(filepost.getParameter("LOCALE"));
	}
	if ((mysession.get("username").equals("")) && (! filepost.getParameter("username").equals(""))) {
		Login.login(mytext, null, "-", "-", mysession, filepost, myresponse, myconfig, db, myconfig.get(db, "require_ssl_user"), database);
	}

	String error = "";
	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	Page mypage = maintainContent.doCreate(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, filepost);
	error = maintainContent.getError();

%><%= " "+error %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>