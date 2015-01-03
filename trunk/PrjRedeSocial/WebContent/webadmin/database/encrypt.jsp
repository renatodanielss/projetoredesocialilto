<%@ include file="../config.jsp" %><%@ page import="HardCore.RequireUser" %><%@ page import="HardCore.User" %><%

	if (RequireUser.SuperAdministrator(mytext, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"))) {
		%><p>Encrypting user data.</p><%
		User users = new User(mytext);
		String SQL = "select id from users order by id";
		users.records("", "", "", "", "", db, myconfig, SQL);
		while (users.records("", "", "", "", "", db, myconfig, "")) {
			User myuser = new User(mytext);
			myuser.read("", "", "", "", "", db, myconfig, users.getId());
			myuser.update(db);
			%><%= myuser.getId() %> - <%= myuser.getUsername() %><br><%
		}
		%><p>Done.</p><%
	}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>