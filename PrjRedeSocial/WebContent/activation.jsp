<%@ include file="webadmin.jsp" %><%@ page import="com.iliketo.dao.MemberDAO" %><%@ page import="com.iliketo.model.Member" %>
<%@ page import="com.iliketo.dao.GenericDAO" %><%

//if (!myrequest.getParameter("user").equals("") && !myrequest.getParameter("user").equals(null)){
	MemberDAO memberDao = new MemberDAO(db, request);
	Member member = null;
	member = (Member) memberDao.readByColumn("username", myrequest.getParameter("user"), Member.class);
	
	if (member != null && member.getActivated() != null){
		if (member.getActivated().equals(myrequest.getParameter("activationkey")) && !member.getActivated().equals("0")){
			Boolean verdadeiro = member.getActivated() == "0";
			System.out.println("Usu�rio link:" + myrequest.getParameter("user"));
			System.out.println("Ativado = " + member.getActivated() + "\nid = " + member.getEmail());
			System.out.println("Verdadeiro ou falso: " + verdadeiro.toString());
			member.setActivated("0");
			memberDao.update(member, false);
			%>
			<!-- TAG para redirecionar para pagina logout.jsp passando mais um parametro com o valor da p�gina retorno realizado pelo Asbru -->
			<jsp:forward page="/logout.jsp">
				<jsp:param value="/page.jsp?id=842" name="redirect"/>
			</jsp:forward>
			<%
		}
		else{
			%>
			<!-- TAG para redirecionar para pagina logout.jsp passando mais um parametro com o valor da p�gina retorno realizado pelo Asbru -->
			<jsp:forward page="/logout.jsp">
				<jsp:param value="/page.jsp?id=848" name="redirect"/>
			</jsp:forward>
			<%
		}
	}
	else{
		%>
		<!-- TAG para redirecionar para pagina logout.jsp passando mais um parametro com o valor da p�gina retorno realizado pelo Asbru -->
		<jsp:forward page="/logout.jsp">
			<jsp:param value="/page.jsp?id=848" name="redirect"/>
		</jsp:forward>
		<%
	}
	
	//if (member.getActivated().equals("0")){
		//mysession.set("log", "login");
		//UCbrowseWebsite browseWebsite = new UCbrowseWebsite(mytext);
		//browseWebsite.doLogin(cms, servletcontext, mysession, myrequest, myresponse, myconfig, db, database);
	//}
	//else{

	//}
//}
%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>