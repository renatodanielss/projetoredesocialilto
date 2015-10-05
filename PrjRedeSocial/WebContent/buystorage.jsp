<%@ include file="webadmin.jsp" %><%@ page import="com.iliketo.dao.MemberDAO" %><%@ page import="com.iliketo.model.Member" %>
<%@ page import="com.iliketo.dao.GenericDAO" %><%

	MemberDAO memberDao = new MemberDAO(db, request);
	Member member = new Member();
	member = (Member) memberDao.readByColumn("username", myrequest.getParameter("user"), Member.class);
	
	member.setTotalSpace("1073741824");
	memberDao.update(member, false);
	
	%>
	<!-- TAG para redirecionar para pagina logout.jsp passando mais um parametro com o valor da página retorno realizado pelo Asbru -->
	<jsp:forward page="/page.jsp?id=160">
		<jsp:param value="<%=1%>" name="storage_completed"/>
	</jsp:forward>
	<% %>
			
	<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>