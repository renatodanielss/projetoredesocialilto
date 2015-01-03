<%@ include file="../config.jsp" %><%@ page import="HardCore.Usertest" %><%

	String output = "";
	Usertest usertest = new Usertest();
	usertest.readUsertest(db, myrequest.getParameter("usertest"));
	if (usertest.getId().equals(myrequest.getParameter("id"))) {
		// does not exist or update
		output = "NO";
	} else if (usertest.getId().equals("")) {
		// does not exist
		output = "NO";
	} else {
		// does exist
		output = "YES";
	}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>