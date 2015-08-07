<%@ include file="webadmin.jsp" %><%@ page import="com.iliketo.dao.MemberDAO" %><%@ page import="com.iliketo.model.Member" %>
<%@ page import="com.iliketo.dao.GenericDAO" %><%

//if (!myrequest.getParameter("user").equals("") && !myrequest.getParameter("user").equals(null)){
	MemberDAO memberDao = new MemberDAO(db, request);
	Member member = new Member();
	member = (Member) memberDao.readByColumn("username", myrequest.getParameter("user"), Member.class);
	
	User user = new User();
	user.readByUsername(myconfig.get(db, "superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("user"));
	
	
	if (member.getRetrievePassword().equals(myrequest.getParameter("resetkey")) && !member.getRetrievePassword().equals("0")){
		Boolean verdadeiro = member.getRetrievePassword() == "0";
		System.out.println("Usuário link:" + myrequest.getParameter("user"));
		System.out.println("Recuperação = " + member.getRetrievePassword() + "\nid = " + member.getEmail());
		System.out.println("Verdadeiro ou falso: " + verdadeiro.toString());
		member.setRetrievePassword ("0");
		memberDao.update(member);
		myrequest.setParameter("old_parameter", user.getPassword());
		%>
		<!-- TAG para redirecionar para pagina logout.jsp passando mais um parametro com o valor da página retorno realizado pelo Asbru -->
		<jsp:forward page="/page.jsp?id=859">
			<jsp:param value="<%=user.getUsername()%>" name="username"/>
			<jsp:param value="<%=user.getPassword()%>" name="password"/>
		</jsp:forward>
		<%
	}
	else{
		%>
		<!-- TAG para redirecionar para pagina logout.jsp passando mais um parametro com o valor da página retorno realizado pelo Asbru -->
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