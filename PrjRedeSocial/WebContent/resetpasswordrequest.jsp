<%@ include file="webadmin.jsp" %><%@ page import="com.iliketo.dao.MemberDAO" %><%@ page import="com.iliketo.model.Member" %>
<%@ page import="com.iliketo.dao.GenericDAO" %>
<%@ page import="com.iliketo.control.EmailController.tipoEmail" %>
<%@ page import="com.iliketo.control.EmailController" %>
<%@ page import="org.apache.log4j.Logger" %>
<%

	//Declarando chave de recuperação de senha
	String uuid1 = "";
	String uuid2 = "";
	String uuid = "";
	
	MemberDAO memberDao = new MemberDAO(db, request);
	Member member = new Member();
	member = (Member) memberDao.readByColumn("email", myrequest.getParameter("email"), Member.class);
	
	User user = new User();
	
	if (user.readByEmail(myconfig.get(db, "superadmin"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, myrequest.getParameter("email")) && member.getIdMember() != null){
		if (member.getRetrievePassword().equals("0") || member.getRetrievePassword().equals("1")){
			uuid1 = CmsConfigILiketo.generateRandomKey(32);
			uuid2 = CmsConfigILiketo.generateRandomKey(32);
			uuid = uuid1+uuid2;
			member.setRetrievePassword(uuid);
			memberDao.update(member, false);
		} else{
			uuid = member.getRetrievePassword();
		}
		
		Logger log = Logger.getLogger(EmailController.class);
		log.info("Email de envio resetpasswordrequest.jsp");
		
		EmailController email = new EmailController(tipoEmail.EMAIL_RECOVERY); //mudar o tipo de email
		email.enviaEmailRecuperacaoSenha(member, user, myrequest.getLocale().toString(), myrequest, uuid);
		
		%>
		<!-- TAG para redirecionar para pagina passando mais um parametro com o valor da página retorno realizado pelo Asbru -->
		<jsp:forward page="/page.jsp?id=362">
			<jsp:param value="<%=member.getEmail()%>" name="emailretrieve"/>
		</jsp:forward>
		<%
	}
	else{
		HashMap<String, String> msgTextoLanguage = new HashMap<String, String>();
		msgTextoLanguage.put("pt_BR", "Este endere&ccedil;o de email n&atilde;o est&aacute; cadastrado!");
		msgTextoLanguage.put("en_US", "This email address is not registered!");
		
		String language = request.getLocale().toString().equals("pt_BR") ? "pt_BR" : "en_US";
		
		%>
		<!-- TAG para redirecionar para pagina logout.jsp passando mais um parametro com o valor da página retorno realizado pelo Asbru -->
		<jsp:forward page="/page.jsp?id=364">
			<jsp:param value="<%=msgTextoLanguage.get(language)%>" name="error"/>
		</jsp:forward>
		<%
	}
%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>