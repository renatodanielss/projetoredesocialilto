<%@ include file="../config.jsp" %><%@ page import="HardCore.Usergroup" %><%

	String output = "";
	Usergroup usergroup = new Usergroup();
	usergroup.readUsergroup(db, myrequest.getParameter("usergroup"));
	if (usergroup.getId().equals(myrequest.getParameter("id"))) {
		// does not exist or update
		output = "NO";
	} else if (usergroup.getId().equals("")) {
		// does not exist
		output = "NO";
	} else {
		// does exist
		output = "YES";
	}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>