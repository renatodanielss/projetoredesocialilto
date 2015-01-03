<%@ include file="../config.jsp" %><%@ page import="HardCore.Filetype" %><%

	String output = "";
	Filetype filetype = new Filetype();
	filetype.readFiletype(db, myrequest.getParameter("filetype"));
	if (filetype.getId().equals(myrequest.getParameter("id"))) {
		// does not exist or update
		output = "NO";
	} else if (filetype.getId().equals("")) {
		// does not exist
		output = "NO";
	} else {
		// does exist
		output = "YES";
	}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>