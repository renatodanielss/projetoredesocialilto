<%@ include file="../config.jsp" %><%@ page import="HardCore.Productgroup" %><%

	String output = "";
	Productgroup productgroup = new Productgroup();
	productgroup.readProductgroup(db, myrequest.getParameter("productgroup"));
	if (productgroup.getId().equals(myrequest.getParameter("id"))) {
		// does not exist or update
		output = "NO";
	} else if (productgroup.getId().equals("")) {
		// does not exist
		output = "NO";
	} else {
		// does exist
		output = "YES";
	}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>