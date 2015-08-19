<%@ include file="webadmin.jsp" %><%@ page import="com.iliketo.dao.MemberDAO" %><%@ page import="com.iliketo.model.Member" %>
<%@ page import="com.iliketo.dao.GenericDAO" %><%

/*MemberDAO memberDao = new MemberDAO(db, request);
Member member = new Member();
member = (Member) memberDao.readByColumn("username", myrequest.getParameter("user"), Member.class);

if (member.getActivated().equals(myrequest.getParameter("activationkey")) && !member.getActivated().equals("0")){
	Boolean verdadeiro = member.getActivated() == "0";
	System.out.println("Usuário link:" + myrequest.getParameter("user"));
	System.out.println("Ativado = " + member.getActivated() + "\nid = " + member.getEmail());
	System.out.println("Verdadeiro ou falso: " + verdadeiro.toString());
	member.setActivated("0");
	memberDao.update(member);*/
	
	System.out.println("Report: " + myrequest.getParameter("report"));
	System.out.println("Message: " + myrequest.getParameter("message"));
	
	%>
	<!-- TAG para redirecionar para pagina post.jsp passando mais um parametro com o valor da pagina retorno realizado pelo Asbru -->
	<jsp:forward page="/post.jsp?database=dbreport">
		<jsp:param value="<%=myrequest.getParameter("report")%>" name="report"/>
		<jsp:param value="<%=myrequest.getParameter("message")%>" name="message"/>
	</jsp:forward>

<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>