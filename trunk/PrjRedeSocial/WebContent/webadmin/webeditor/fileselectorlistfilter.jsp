<%@ page pageEncoding="UTF-8" %><%@ include file="../config.jsp" %><%@ include file="config.jsp" %><%@ page import="HardCore.UCmaintainHyperlinks" %><%@ page import="java.io.File" %><%@ page import="java.util.regex.*" %><%

	UCmaintainHyperlinks maintainHyperlinks = new UCmaintainHyperlinks(mytext);
	maintainHyperlinks.getAccess(mysession, myrequest, myresponse, myconfig, db);

	root_path = getServletConfig().getServletContext().getRealPath(myconfig.get(db, "URLrootpath")).replaceAll("[\\\\/]$", "");
	String hidden_paths = "^$";
	String[] files = null;
	if (! exclude_paths.equals("")) {
		hidden_paths = "^(" + exclude_paths.replaceAll(",", "|") + ")(/.*)?$";
	}

	if (! myrequest.getParameter("path").equals("")) {
		pages_path = "/" + myrequest.getParameter("path");
		pages_path = pages_path.replaceAll("\\\\", "/");
		pages_path = pages_path.replaceAll("/\\.\\.", "").replaceAll("\\.\\./", "");
	}

	if (! root_path.equals("")) {
		if (! pages_path.equals("")) {
			java.io.File myfh = new java.io.File(root_path + pages_path);
			if (myfh.exists() && myfh.isDirectory()) {
				files = myfh.list();
				Arrays.sort(files);
			}
		}
	}
%><%!

	public String folderMenu(String root_path, String hidden_paths, String base_path, String[] dh, String menu, String text, String section) {
		String output = "";
		int menuitem = 0;
		if (dh != null) {
			output += "<ul>\r\n";
			output += "<li rel=\"folder\" class=\"jstree-closed\" id=\"" + menu + "\"><a href=\"javascript:openit('" + section + "','','" + text + "','" + menu + "')\">" + text + "</a>";
			output += folderSubMenu(root_path, hidden_paths, base_path, "", dh, menu, text, section);
			output += "</li>\r\n";
			output += "</ul>\r\n";
		}
		return output;
	}

	public String folderSubMenu(String root_path, String hidden_paths, String base_path, String path, String[] dh, String menu, String text, String section) {
		return folderSubMenu(root_path, hidden_paths, base_path, path, dh, menu, text, section, 0, 0);
	}
	public String folderSubMenu(String root_path, String hidden_paths, String base_path, String path, String[] dh, String menu, String text, String section, int menuitem, int indent) {
		String output = "";
		if (dh != null) {
			int i = 0;
			for (int folder=0; folder<dh.length; folder++) {
				String entry = dh[folder];
				java.io.File di = new java.io.File(root_path + base_path + path + entry);
				if (Pattern.compile(hidden_paths, Pattern.CASE_INSENSITIVE).matcher("" + base_path + path + entry).find()) {
					// ignore
				} else if ((di.isDirectory()) && (! entry.equals(".")) && (! entry.equals(".."))) {
					i = i + 1;
					String[] subdh = di.list();
					Arrays.sort(subdh);
					menuitem++;
					if (output.equals("")) output += "\r\n<ul>\r\n";
					output += "<li rel=\"folder\" class=\"jstree-closed\" id=\"" + menu + "_" + i + "\" name=\"" + (path + entry) + "/" + "\"><a href=\"javascript:openit('" + section + "','" + (path + entry) + "/" + "','" + entry + "','" + menu + "_" + i + "')\">" + entry + "</a>";
//					output += folderSubMenu(root_path, hidden_paths, base_path, path + entry + "/", subdh, menu + "_" + i, "'" + entry + "'", section, menuitem, indent);
					output += "</li>\r\n";
				}
			}
			if (! output.equals("")) output += "</ul>\r\n";
		}
		return output;
	}

%><%

if (! myrequest.getParameter("path").equals("")) {
	%><%= folderSubMenu(root_path, hidden_paths, "/", myrequest.getParameter("path"), files, myrequest.getParameter("menuitem"), "Text('fileselectorlistfilter_files')", "Files") %><%
} else {
	%><%@ include file="fileselectorlistfilter.jsp.html" %><%
}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>