<%@ include file="../config.jsp" %><%@ page import="HardCore.Linkgroup" %><%

	String output = "";
	Linkgroup linkgroup = new Linkgroup();
	linkgroup.readLinkgroup(db, myrequest.getParameter("linkgroup"));
	if (linkgroup.getId().equals(myrequest.getParameter("id"))) {
		// does not exist or update
		output = "NO";
	} else if (linkgroup.getId().equals("")) {
		// does not exist
		output = "NO";
	} else {
		// does exist
		output = "YES";
	}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>