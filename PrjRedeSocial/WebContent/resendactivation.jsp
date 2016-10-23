<%@ include file="webadmin.jsp" %><%@ page import="com.iliketo.dao.MemberDAO" %><%@ page import="com.iliketo.model.Member" %>
<%@ page import="com.iliketo.dao.GenericDAO" %>
<%@ page import="com.iliketo.control.EmailController.tipoEmail" %>
<%@ page import="com.iliketo.control.EmailController" %>
<%@ page import="org.apache.log4j.Logger" %>
<%

//if (!myrequest.getParameter("user").equals("") && !myrequest.getParameter("user").equals(null)){
	MemberDAO memberDao = new MemberDAO(db, request);
	Member member = new Member();
	member = (Member) memberDao.readByColumn("username", myrequest.getParameter("email"), Member.class);
	
	final Logger log = Logger.getLogger("resendactivation.jsp");
	log.info("Membro:" + member.toString());
	log.info("Membro Activated:" + member.getActivated());
	log.info("Locale:" + myrequest.getLocale().toString());
	
	if (member != null && !member.getActivated().equals("0")){
		//enviar email sobre status do pagamento completo aqui
		EmailController email = new EmailController(tipoEmail.EMAIL_ANUNCIO); //mudar o tipo de email
		email.reenviaEmailAtivacao(member, myrequest.getLocale().toString(), myrequest);
		%>
		<!-- TAG para redirecionar para pagina logout.jsp passando mais um parametro com o valor da página retorno realizado pelo Asbru -->
		<jsp:forward page="/page.jsp?id=1170">
			<jsp:param value="1" name="resendEmail"/>
		</jsp:forward>
		<%
	}
	else{
		%>
		<!-- TAG para redirecionar para pagina logout.jsp passando mais um parametro com o valor da página retorno realizado pelo Asbru -->
		<jsp:forward page="/logout.jsp">
			<jsp:param value="/page.jsp?id=1174" name="redirect"/>
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