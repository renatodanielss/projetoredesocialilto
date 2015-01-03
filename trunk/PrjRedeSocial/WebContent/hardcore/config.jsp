<%

String REQUEST_URI = "";
if (request.getRequestURI() != null) REQUEST_URI += request.getRequestURI();
String QUERY_STRING = "";
if (request.getQueryString() != null) QUERY_STRING += request.getQueryString();
response.sendRedirect((request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + REQUEST_URI + "?" + QUERY_STRING).replaceAll("/hardcore/", "/webadmin/"));

%>