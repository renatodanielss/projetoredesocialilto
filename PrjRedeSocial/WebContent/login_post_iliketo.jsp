<%@ include file="webadmin.jsp" %><%@ page import="com.iliketo.dao.MemberDAO" %><%@ page import="com.iliketo.model.Member" %>
<%@ page import="com.iliketo.dao.GenericDAO" %><%

if (!myrequest.getParameter("username").equals("") && !myrequest.getParameter("username").equals(null)){
	MemberDAO memberDao = new MemberDAO(db, request);
	Member member = null;
	member = (Member) memberDao.readByColumn("username", myrequest.getParameter("username"), Member.class);
	
	Boolean verdadeiro = member.getIdMember() == null;
	System.out.println("Usuário digitado:" + myrequest.getParameter("username"));
	System.out.println("Ativado = " + member.getActivated() + "\nid = " + member.getEmail());
	System.out.println("ID Member: " + member.getIdMember());
	System.out.println("É nulo? " + verdadeiro.toString());
	
	
	if (member.getIdMember() != null && !member.getActivated().equals("0")){
		%>
		<!-- TAG para redirecionar para pagina logout.jsp passando mais um parametro com o valor da pagina retorno realizado pelo Asbru -->
		<jsp:forward page="/logout.jsp">
			<jsp:param value="/page.jsp?id=841" name="redirect"/>
		</jsp:forward>
		<%
	}
	else{
		mysession.set("log", "login");
		UCbrowseWebsite browseWebsite = new UCbrowseWebsite(mytext);
		browseWebsite.doLogin(cms, servletcontext, mysession, myrequest, myresponse, myconfig, db, database);
	}
}
%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>