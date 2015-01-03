<%@ page import="HardCore.Common" %><%@ page import="java.io.*" %><%

String date = "" + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
String dateformat = "%Y-%m-%d %H:%M:%S";
String[] params;
if ((request.getAttribute("extension") != null) && (! request.getAttribute("extension").equals(""))) {
	params = (""+request.getAttribute("extension")).split(",");
} else {
	params = (""+session.getAttribute("extension")).split(",");
}
if (params.length > 1) {
	if (! params[0].equals("")) {
		date = "" + params[0];
	}
	if (! params[1].equals("")) {
		dateformat = "" + params[1];
	}
}
for (int i=2; i<params.length; i++) {
	dateformat += "," + params[i];
}

String output = "" + date;
try {
	output = Common.strftime(dateformat, Common.strtodate("YYYY-mm-dd HH:MM:SS", date));
} catch (Exception e) {
}

session.setAttribute("extension", output);

%><%= output %>