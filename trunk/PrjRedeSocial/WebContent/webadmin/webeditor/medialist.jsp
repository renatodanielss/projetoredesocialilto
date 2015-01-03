<%@ page pageEncoding="UTF-8" %>
<%@ include file="config.jsp" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.regex.*" %>
<%@ page import="java.util.*" %>
<%
	String[] links = null;
	String links_path = "";
	String section_path = "";
	String allowed_files = "";
	if (! root_path.equals("")) {
		if (! images_path.equals("")) {
			section_path = images_path;
			allowed_files = "\\.(" + image_formats.replaceAll(",", "|") + ")$";
		}
		if (! section_path.equals("")) {
			java.io.File fh = new java.io.File(root_path + section_path + request.getParameter("category"));
			if (fh.exists() && fh.isDirectory()) {
				links = fh.list();
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
<%@ include file="medialist.jsp.html" %>
