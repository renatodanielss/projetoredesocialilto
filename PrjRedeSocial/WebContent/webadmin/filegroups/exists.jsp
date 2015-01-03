<%@ include file="../config.jsp" %><%@ page import="HardCore.Filegroup" %><%

	String output = "";
	Filegroup filegroup = new Filegroup();
	filegroup.readFilegroup(db, myrequest.getParameter("filegroup"));
	if (filegroup.getId().equals(myrequest.getParameter("id"))) {
		// does not exist or update
		output = "NO";
	} else if (filegroup.getId().equals("")) {
		// does not exist
		output = "NO";
	} else {
		// does exist
		output = "YES";
	}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>