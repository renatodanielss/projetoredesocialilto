<%@ include file="../config.jsp" %><%@ page import="HardCore.Contenttype" %><%

	String output = "";
	Contenttype contenttype = new Contenttype();
	contenttype.readContenttype(db, myrequest.getParameter("contenttype"));
	if (contenttype.getId().equals(myrequest.getParameter("id"))) {
		// does not exist or update
		output = "NO";
	} else if (contenttype.getId().equals("")) {
		// does not exist
		output = "NO";
	} else {
		// does exist
		output = "YES";
	}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>