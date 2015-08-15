<%@ include file="webadmin.jsp" %><%@ page import="com.iliketo.dao.MemberDAO" %><%@ page import="com.iliketo.model.Member" %>
<%@ page import="com.iliketo.dao.GenericDAO" %><%

	MemberDAO memberDao = new MemberDAO(db, request);
	Member member = new Member();
	member = (Member) memberDao.readByColumn("username", myrequest.getParameter("user"), Member.class);
	
	if (!member.getRetrievePassword().equals("1")){
		
		User user = new User();
		user.readByUsername(myconfig.get(db, "superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("user"));
		
		System.out.println("Password: " + user.getPassword());
		
		if (member.getRetrievePassword().equals(myrequest.getParameter("resetkey")) && !member.getRetrievePassword().equals("0")){
			Boolean verdadeiro = member.getRetrievePassword() == "0";
			System.out.println("Usu�rio link:" + myrequest.getParameter("user"));
			System.out.println("Recupera��o = " + member.getRetrievePassword() + "\nid = " + member.getEmail());
			System.out.println("Verdadeiro ou falso: " + verdadeiro.toString());
			member.setRetrievePassword ("1");
			memberDao.update(member);
			
			ServletContext server = request.getServletContext();
			
			Text text = new Text();
			
			myrequest.setParameter("username", user.getUsername());
			myrequest.setParameter("password", user.getPassword());
			
			Login.login(text, null, "/login.jsp", "-", server, mysession, myrequest, myresponse, myconfig, db, myconfig.get(db, "require_ssl_user"), database);
			
			%>
			<!-- TAG para redirecionar para pagina logout.jsp passando mais um parametro com o valor da p�gina retorno realizado pelo Asbru -->
			<jsp:forward page="/page.jsp?id=859">
				<jsp:param value="<%=user.getPassword()%>" name="old_password"/>
			</jsp:forward>
			<%
		}
		else{
			%>
			<!-- TAG para redirecionar para pagina logout.jsp passando mais um parametro com o valor da p�gina retorno realizado pelo Asbru -->
			<jsp:forward page="/logout.jsp">
				<jsp:param value="/page.jsp?id=863" name="redirect"/>
			</jsp:forward>
			<%
		}
		
	%>
	<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>
    <%
    }
	else{
		%>
		<!-- TAG para redirecionar para pagina logout.jsp passando mais um parametro com o valor da p�gina retorno realizado pelo Asbru -->
		<jsp:forward page="/logout.jsp">
			<jsp:param value="/page.jsp?id=863" name="redirect"/>
		</jsp:forward>
		<%
	}
    %>