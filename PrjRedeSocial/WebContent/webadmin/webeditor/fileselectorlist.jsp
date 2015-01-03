<%@ page pageEncoding="UTF-8" %>
<%@ include file="../config.jsp" %>
<%@ include file="config.jsp" %>
<%@ page import="HardCore.UCmaintainHyperlinks" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.regex.*" %>
<%
	UCmaintainHyperlinks maintainHyperlinks = new UCmaintainHyperlinks(mytext);
	maintainHyperlinks.getAccess(mysession, myrequest, myresponse, myconfig, db);

	root_path = getServletConfig().getServletContext().getRealPath(myconfig.get(db, "URLrootpath")).replaceAll("[\\\\/]$", "");
	String[] links = null;
	String links_path = "";
	String section_path = "";
	String hidden_paths = "^$";
	if (! exclude_paths.equals("")) {
		hidden_paths = "^(" + exclude_paths.replaceAll(",", "|") + ")(/.*)?$";
	}
	String allowed_files = "";
	if (! root_path.equals("")) {
		if ((! pages_path.equals("")) && (request.getParameter("section") != null) && (request.getParameter("section").equals("Files"))) {
			section_path = pages_path;
			allowed_files = "\\.(" + file_formats.replaceAll(",", "|") + ")$";
		}
		if (! section_path.equals("")) {
			java.io.File myfh = new java.io.File(root_path + section_path + request.getParameter("category"));
			if (Pattern.compile(hidden_paths, Pattern.CASE_INSENSITIVE).matcher(section_path + request.getParameter("category")).find()) {
				// ignore
			} else if (Pattern.compile(hidden_paths, Pattern.CASE_INSENSITIVE).matcher(request.getParameter("category")).find()) {
				// ignore
			} else if (myfh.exists() && myfh.isDirectory()) {
				links = myfh.list();
				Arrays.sort(links);
				if (request.getParameter("category").equals("/")) {
					links_path = section_path;
				} else {
					links_path = section_path + request.getParameter("category");
				}
			}
		}
	}
%>
<%@ include file="fileselectorlist.jsp.html" %>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>