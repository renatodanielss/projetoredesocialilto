<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Generator" content="Asbru Web Content Editor">
<meta http-equiv="Copyright" content="(C) 2002-2014 - Asbru Ltd. - www.asbrusoft.com">
<title>Asbru Web Content Editor</title>
<link id="stylesheet" rel="stylesheet" type="text/css" href="empty.css">
<% if ((request.getParameter("basehref") != null) && (! request.getParameter("basehref").equals(""))) { %>
<base href="<%= request.getParameter("basehref") %>">
<% } %>
<script type="text/javascript">
//if (parent.webeditor.baseHref) document.write('<base href="' + parent.webeditor.baseHref + '">');
window.onerror = error_handler;
function error_handler() { return true; }
</script>
<style type="text/css">
/* form { border: 1px dashed red; } */
/* table, caption, thead, tbody, tfoot, tr, td, th { border: 1px dashed gray; } */
/* object, embed { border: 1px dashed gray; } */
</style>
</head>
<body class="WebEditor" bgcolor="#FFFFFF" text="#000000" link="#000000" vlink="#000000" alink="#000000" style="margin: 0;">
</body>
</html>
