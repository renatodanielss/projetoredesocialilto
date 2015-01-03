<%@ include file="../config.jsp" %><%@ page import="HardCore.Producttype" %><%

	String output = "";
	Producttype producttype = new Producttype();
	producttype.readProducttype(db, myrequest.getParameter("producttype"));
	if (producttype.getId().equals(myrequest.getParameter("id"))) {
		// does not exist or update
		output = "NO";
	} else if (producttype.getId().equals("")) {
		// does not exist
		output = "NO";
	} else {
		// does exist
		output = "YES";
	}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>