<%@ include file="../config.jsp" %><%@ page import="HardCore.Contentgroup" %><%

	String output = "";
	Contentgroup contentgroup = new Contentgroup();
	contentgroup.readContentgroup(db, myrequest.getParameter("contentgroup"));
	if (contentgroup.getId().equals(myrequest.getParameter("id"))) {
		// does not exist or update
		output = "NO";
	} else if (contentgroup.getId().equals("")) {
		// does not exist
		output = "NO";
	} else {
		// does exist
		output = "YES";
	}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>