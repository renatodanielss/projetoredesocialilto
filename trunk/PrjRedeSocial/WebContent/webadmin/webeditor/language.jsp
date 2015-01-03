<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.net.URLEncoder" %><%
Cookie mylanguagecookie = new Cookie("AcceptLanguage", "" + URLEncoder.encode("" + request.getHeader("Accept-Language")));
response.addCookie(mylanguagecookie);
%>