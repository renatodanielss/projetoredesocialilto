<%@ include file="../config.jsp" %><%@ page import="HardCore.Hostinggroup" %><%

	String output = "";
	Hostinggroup hostinggroup = new Hostinggroup();
	hostinggroup.readHostinggroup(db, myrequest.getParameter("hostinggroup"));
	if (hostinggroup.getId().equals(myrequest.getParameter("id"))) {
		// does not exist or update
		output = "NO";
	} else if (hostinggroup.getId().equals("")) {
		// does not exist
		output = "NO";
	} else {
		// does exist
		output = "YES";
	}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>