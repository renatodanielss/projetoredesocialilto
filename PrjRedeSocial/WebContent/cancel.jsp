<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<p>Redirecting...</p>
<jsp:forward page="/page.jsp?id=1123">
	<jsp:param value="1" name="cancel"/>
</jsp:forward>

<% HttpSession nsession = request.getSession(false);
if(nsession!=null)
	session.invalidate();
%>