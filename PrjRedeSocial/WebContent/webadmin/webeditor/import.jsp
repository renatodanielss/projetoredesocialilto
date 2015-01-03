<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.HashMap" %>
<%@ include file="../config.jsp" %>
<%@ include file="Fileupload.jsp" %>
<%
	String content = "";
	HashMap myfileupload = getFileupload(request, "");
	if ((myfileupload != null) && (myfileupload.get("file") != null)) content = new String(("" + myfileupload.get("file")).getBytes("iso-8859-1"), "UTF-8");

	Pattern p = Pattern.compile("^.*<body[^>]*>(.*)</body>.*$", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	Matcher m = p.matcher(content);
	if (m.find()) {
		content = m.group(1);
	}
%>
<%@ include file="import.jsp.html" %>
