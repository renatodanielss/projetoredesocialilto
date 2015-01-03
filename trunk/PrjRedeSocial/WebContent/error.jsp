<%@ page buffer="256kb" %><%@ include file="webadmin.jsp" %><%

String id = "";
String contentclass = "";
// JSP - web.xml error-page
String filename = (String)request.getAttribute("javax.servlet.error.request_uri");
String statuscode = "" + request.getAttribute("javax.servlet.error.status_code");
String querystring = "" + request.getAttribute("javax.servlet.forward.query_string");
if (filename == null) {
	// IIS - web.config httpErrors handler
	filename = myrequest.getQueryString().replaceAll("^([0-9]+);https?://[^/]+(/[^\\?&]*)([\\?](.*?))?$", "$2");
	statuscode = myrequest.getQueryString().replaceAll("^([0-9]+);https?://[^/]+(/[^\\?&]*)([\\?](.*?))?$", "$1");
	querystring = myrequest.getQueryString().replaceAll("^([0-9]+);https?://[^/]+(/[^\\?&]*)([\\?](.*?))?$", "$4");
}

if (filename != null) {
	filename = filename.replaceAll("^"+URLrootpath, "");
	HashMap<String,String> mycontent = new HashMap<String,String>();
	String SQL = "";
	if (db != null) {
		SQL = "select id,contentclass,server_filename from content_public where server_filename like " + db.quote(filename);
		mycontent = db.query_record(SQL);
		if (mycontent != null) {
			id = ""+mycontent.get("id");
			contentclass = ""+mycontent.get("contentclass");
		}
	}
}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %><%

if ((! id.equals("")) && (! contentclass.equals(""))) {
	if (! querystring.equals("")) querystring = "&" + querystring;
	response.setStatus(response.SC_OK);
	RequestDispatcher dispatcher = request.getRequestDispatcher("/" + contentclass + ".jsp?id=" + id + querystring);
	dispatcher.forward(request, response);

} else if ((statuscode != null) && Common.fileExists(Common.getRealPath(servletcontext, "/error" + statuscode + ".jsp"))) {
	if (! querystring.equals("")) querystring = "&" + querystring;
	response.setStatus(response.SC_NOT_FOUND);
	myresponse.setContentType("text/html");
	RequestDispatcher dispatcher = request.getRequestDispatcher("/error" + statuscode + ".jsp?" + filename + querystring);
	dispatcher.forward(request, response);

} else if ((statuscode != null) && Common.fileExists(Common.getRealPath(servletcontext, "/error" + statuscode + ".html"))) {
	if (! querystring.equals("")) querystring = "&" + querystring;
	response.setStatus(response.SC_NOT_FOUND);
	myresponse.setContentType("text/html");
	RequestDispatcher dispatcher = request.getRequestDispatcher("/error" + statuscode + ".html?" + filename + querystring);
	dispatcher.forward(request, response);

} else if ((statuscode != null) && Common.fileExists(Common.getRealPath(servletcontext, "/error.html"))) {
	if (! querystring.equals("")) querystring = "&" + querystring;
	response.setStatus(response.SC_NOT_FOUND);
	myresponse.setContentType("text/html");
	RequestDispatcher dispatcher = request.getRequestDispatcher("/error.html?" + filename + querystring);
	dispatcher.forward(request, response);

} else {
	response.setStatus(response.SC_NOT_FOUND);
	myresponse.setContentType("text/html");
}

%>