<%@ include file="../config.jsp" %><%@ page import="HardCore.Usertype" %><%

	String output = "";
	Usertype usertype = new Usertype();
	usertype.readUsertype(db, myrequest.getParameter("usertype"));
	if (usertype.getId().equals(myrequest.getParameter("id"))) {
		// does not exist or update
		output = "NO";
	} else if (usertype.getId().equals("")) {
		// does not exist
		output = "NO";
	} else {
		// does exist
		output = "YES";
	}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>