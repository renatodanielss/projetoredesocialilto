<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="com.iliketo.util.ModelILiketo" %><%@ page import="org.apache.log4j.Logger" %>

<%
	ModelILiketo model = new ModelILiketo(request, response);
	model.addAttribute("express_mark", session.getAttribute("EXPRESS_MARK"));
	
%>

<p>Redirecting...</p>
<jsp:forward page="/page.jsp?id=1123">
	<jsp:param value="1" name="cancel"/>
</jsp:forward>

<% HttpSession nsession = request.getSession(false);
if(nsession!=null)
	session.invalidate();
%>