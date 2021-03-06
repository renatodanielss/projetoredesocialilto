<%@ include file="../webadmin.jsp" %><%@ include file="../config.personal.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainUsers" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.User" %><%@ page import="HardCore.RequireUser" %>
<%@ page import="com.iliketo.control.UpdateUserController" import="com.iliketo.model.Member" %><%@ page import="com.iliketo.dao.IliketoDAO"%><%
	
	//***Executa e valida Registro iliketo**** 
	Page mypage = null;
			
	String idRegisterUser = IliketoDAO.getValueOfDatabase(db, "id", "dbmembers", "id_member", mysession.get("userid"));
	mytext = new Text(myrequest);

	mysession.set("mode", "");
	RequireUser.User(mytext, mysession.get("username"), myrequest, myresponse, mysession, db);

	UCmaintainUsers maintainUsers = new UCmaintainUsers(mytext);
	User myuser = maintainUsers.doPersonalUpdate(mysession, myrequest, myresponse, myconfig, db);

	Login.userSession(myuser, mysession, db);

	UCmaintainContent maintainContent = new UCmaintainContent(mytext);
	mypage = maintainContent.doPersonalUpdate(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db);

	Page adminpage = maintainContent.getPersonalAdmin(myuser, mypage, servletcontext, mysession, myrequest, myresponse, myconfig, db);
	mysession.set("id", adminpage.getId());
	
	//verifica resposta para advertiser/anunciantes
	String pageResposta = "/page.jsp?id=620";	//resposta update padrao colecionador, loja
	Member membro = (Member) mysession.getSession().getAttribute("member");
	if(membro != null && membro.getAccountType().equals("Advertiser")){
		pageResposta = "/page.jsp?id=1070";		//resposta update anunciante
	}
			
	%>
	<!-- TAG para redirecionar para pagina post.jsp passando mais um parametro com o valor da pagina retorno realizado pelo Asbru -->
	<jsp:forward page="/post.jsp?database=dbmembers">
		<jsp:param value="<%=idRegisterUser%>" name="id"/>
		<jsp:param value="<%=pageResposta%>" name="redirect"/>
	</jsp:forward>
	<%
	
%>
<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>