<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<title>Asbru Web Content Editor</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Generator" content="Asbru Web Content Editor">
<meta http-equiv="Copyright" content="(C) 2002-2014 - Asbru Ltd. - www.asbrusoft.com">
<% if ((request.getParameter("stylesheet") != null) && (! request.getParameter("stylesheet").equals(""))) { %><link rel="stylesheet" type="text/css" href="<%= request.getParameter("stylesheet") %>"><% } %>
</head>
<% if ((request.getParameter("direction") != null) && (! request.getParameter("direction").equals(""))) { %>
<body style="margin: 0px;" dir="<%= request.getParameter("direction") %>">
<% } else { %>
<body style="margin: 0px;">
<% } %>
<%= request.getParameter("content") %>
</body>
</html>
