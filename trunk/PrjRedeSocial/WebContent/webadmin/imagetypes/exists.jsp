<%@ include file="../config.jsp" %><%@ page import="HardCore.Imagetype" %><%

	String output = "";
	Imagetype imagetype = new Imagetype();
	imagetype.readImagetype(db, myrequest.getParameter("imagetype"));
	if (imagetype.getId().equals(myrequest.getParameter("id"))) {
		// does not exist or update
		output = "NO";
	} else if (imagetype.getId().equals("")) {
		// does not exist
		output = "NO";
	} else {
		// does exist
		output = "YES";
	}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>