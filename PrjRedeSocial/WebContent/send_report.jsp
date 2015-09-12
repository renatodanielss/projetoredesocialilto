<%@ include file="webadmin.jsp" %>
	
	<!-- TAG para redirecionar para pagina post.jsp passando mais um parametro com o valor da pagina retorno realizado pelo Asbru -->
	<jsp:forward page="/post.jsp?database=dbreport">
		<jsp:param value="<%=1%>" name="report_sent"/>
	</jsp:forward>
	
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>