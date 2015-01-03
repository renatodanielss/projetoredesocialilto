<%@ page pageEncoding="UTF-8" %><%@ include file="../config.jsp" %><%@ page import="HardCore.UCmaintainMedia" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.Fileupload" %><%@ page import="HardCore.Login" %><%

	Fileupload filepost = new Fileupload(myrequest, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath"), myconfig.get(db, "URLuploadpath"));
	if ((mysession.get("username").equals("")) && (! filepost.getParameter("username").equals(""))) {
		Login.login(mytext, null, "-", "-", mysession, filepost, myresponse, myconfig, db, myconfig.get(db, "require_ssl_user"), database);
	}

	Page content = new Page(mytext);
	UCmaintainMedia maintainMedia = new UCmaintainMedia(mytext);
	maintainMedia.getAccess(mysession, myrequest, myresponse, myconfig, db);
	if ((adminuser.getHardcoreUpload().equals("yes")) || ((adminuser.getHardcoreUpload().equals("")) && (myconfig.get(db, "hardcore_upload").equals("yes")))) {
		if (myrequest.getParameter("action").equals("Delete")) {
			content = maintainMedia.doDelete(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, filepost);
		} else if (myrequest.getParameter("action").equals("Create")) {
			content = maintainMedia.doCreate(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, filepost);
		} else if (myrequest.getParameter("action").equals("Update")) {
			content = maintainMedia.doUpdate(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, filepost);
		}
	}
	String error = maintainMedia.getError();

%><%= " "+error %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>