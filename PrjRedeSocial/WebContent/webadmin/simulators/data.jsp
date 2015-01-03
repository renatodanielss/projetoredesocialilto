<%

String contenturl = myrequest.getParameter("url");
if (contenturl.equals("")) contenturl = "/" + mytext.display("adminpath") + "/simulators/default.html";
if (myrequest.getQueryString().equals("")) {
	contenturl += "?useragent=" + URLEncoder.encode(deviceUserAgent);
} else {
	contenturl += "&useragent=" + URLEncoder.encode(deviceUserAgent);
}
String contenttitle = myrequest.getParameter("title");
if (contenttitle.equals("")) contenttitle = "Preview";

if (myrequest.getMethod().equals("POST")) {

%>
<form id="contentform" name="contentform" action="<%= html.encode(contenturl) %>" method="POST" enctype="multipart/form-data" target="frame">
<%
	Iterator filepostnames = filepost.getParameterNames();
	while (filepostnames.hasNext()) {
		String name = "" + filepostnames.next();
		String[] values = filepost.getParameters(name);
		if (values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
%>
<input type="hidden" name="<%= html.encode(name) %>" value="<%= html.encode(value) %>">
<%
			}
		}
	}
%>
</form>
<%

} else {

%>
<input type="hidden" id="contenturl" name="contenturl" value="<%= html.encode(contenturl) %>&">
<input type="hidden" id="contenttitle" name="contenttitle" value="<%= html.encode(contenttitle) %>&">
<%

}

%>
