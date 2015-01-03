<%@ include file="../config.jsp" %><%@ page import="HardCore.Hostingtype" %><%

	String output = "";
	Hostingtype hostingtype = new Hostingtype();
	hostingtype.readHostingtype(db, myrequest.getParameter("hostingtype"));
	if (hostingtype.getId().equals(myrequest.getParameter("id"))) {
		// does not exist or update
		output = "NO";
	} else if (hostingtype.getId().equals("")) {
		// does not exist
		output = "NO";
	} else {
		// does exist
		output = "YES";
	}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>