<%@ include file="../config.jsp" %><%@ page import="HardCore.Linktype" %><%

	String output = "";
	Linktype linktype = new Linktype();
	linktype.readLinktype(db, myrequest.getParameter("linktype"));
	if (linktype.getId().equals(myrequest.getParameter("id"))) {
		// does not exist or update
		output = "NO";
	} else if (linktype.getId().equals("")) {
		// does not exist
		output = "NO";
	} else {
		// does exist
		output = "YES";
	}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>