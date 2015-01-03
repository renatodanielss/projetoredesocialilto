<%@ include file="../../webadmin.jsp" %><%@ page import="java.util.regex.*" %><%

// database connection strings
HashMap databaseConnection = new HashMap();

%><%@ include file="database.config.jsp" %><%

String output = "";
String mydatabase = "";
String mytable = "";
String myattribute = "";
String mycolumn = "";
String myvalue = "";
String SQLwhere = "";
String SQL = "";
String[] params;
if ((myrequest.getAttribute("extension") != null) && (! myrequest.getAttribute("extension").equals(""))) {
	params = (""+myrequest.getAttribute("extension")).split(":");
} else {
	params = mysession.get("extension").split(":");
}
if (params.length == 5) {
	mydatabase = params[0];
	mytable = params[1];
	myattribute = params[2];
	mycolumn = params[3];
	myvalue = params[4];
	SQL = "select " + Common.SQL_clean(myattribute) + " from " + Common.SQL_clean(mytable) + " where " + Common.SQL_clean(mycolumn) + "='" + Common.SQL_clean(myvalue) + "'";
} else if (params.length == 4) {
	mydatabase = params[0];
	mytable = params[1];
	myattribute = params[2];
	mycolumn = "*";
	myvalue = "*";
	SQLwhere = params[3];
	SQL = "select " + Common.SQL_clean(myattribute) + " from " + Common.SQL_clean(mytable) + " where " + SQLwhere;
}

if (! SQL.equals("")) {
	DB mydb = new DB(mytext);
	if (databaseConnection.get(mydatabase+":"+mytable+":"+myattribute+":"+mycolumn+":"+myvalue) != null) {
		mydb.connect(DB.DSN(""+databaseConnection.get(mydatabase+":"+mytable+":"+myattribute+":"+mycolumn+":"+myvalue)), ""+databaseConnection.get(mydatabase+":"+mytable+":"+myattribute+":"+mycolumn+":"+myvalue));
	} else if (databaseConnection.get(mydatabase+":"+mytable+":"+myattribute+":"+mycolumn) != null) {
		mydb.connect(DB.DSN(""+databaseConnection.get(mydatabase+":"+mytable+":"+myattribute+":"+mycolumn)), ""+databaseConnection.get(mydatabase+":"+mytable+":"+myattribute+":"+mycolumn));
	} else if (databaseConnection.get(mydatabase+":"+mytable+":"+myattribute) != null) {
		mydb.connect(DB.DSN(""+databaseConnection.get(mydatabase+":"+mytable+":"+myattribute)), ""+databaseConnection.get(mydatabase+":"+mytable+":"+myattribute));
	} else if (databaseConnection.get(mydatabase+":"+mytable) != null) {
		mydb.connect(DB.DSN(""+databaseConnection.get(mydatabase+":"+mytable)), ""+databaseConnection.get(mydatabase+":"+mytable));
	} else if (databaseConnection.get(mydatabase) != null) {
		mydb.connect(DB.DSN(""+databaseConnection.get(mydatabase)), ""+databaseConnection.get(mydatabase));
	}
	if (mydb.isError()) {
		mydb = null;
	}

	if (mydb != null) {
		LinkedHashMap myrows = mydb.query_records(SQL);
		Iterator i = myrows.keySet().iterator();
		if (i.hasNext()) {
			String id = "" + i.next();
			HashMap myrow = (HashMap)myrows.get(id);
			output += ""+myrow.get(myattribute);
		}
	}
	if (mydb != null) mydb.close();
}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>