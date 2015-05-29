<%@page import="com.iliketo.dao.IliketoDAO"%>
<%@ include file="../config.jsp" %>
<%@ page import="HardCore.UCmaintainData" %>
<%@ page import="HardCore.Data" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>

<!-- 
	parametros para deletar: 
	database = nome da tabela no banco de dados
	id = valor do id do registro na tabela do banco de dados para deletar
 -->
 
<%

String nameDatabase = myrequest.getParameter("database"); //recupera do parametro a database do conteudo para deletar
String idDeleteMember = myrequest.getParameter("id"); //recupera do parametro o id do conteudo para deletar
String password = myrequest.getParameter("passwordConfirm"); //recupera do parametro o password digitado para confirmar delete

String username = mysession.get("username");
String userId = mysession.get("userid");

//valida id do membro na session
if(idDeleteMember != null && idDeleteMember.equals(userId)){
	
	//valida autenticidade de usuario e senha
	if(IliketoDAO.authenticUsernamePassword(db, nameDatabase, username, password)){
		
		//Exclui membro do sistema, exclui todas coleções e itens
		UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext); 
		maintainData.doDeleteMember(mysession, db, idDeleteMember);

		System.out.println("Log - " + "execute delete_post_member_store - Loja excluida do sistema com sucesso!");
		System.out.println("Log - " + "id: " + idDeleteMember + " / username: " + username);
		
		//forçar logout do usuario excluido
		System.out.println("Log - " + "Mostrar tela Login!");	
		myresponse.sendRedirect("/logout.jsp");
		
	}else{
		System.out.println("Log - " + "execute delete_post_member_store - Error invalid password!");
		System.out.println("Log - " + "Mostrar tela Personal");
		myresponse.sendRedirect("/page.jsp?id=783&invalid=password");
	}
}else{
	System.out.println("Log - " + "execute delete_post_member_store - Error invalid id member or session expired!");
	System.out.println("Log - " + "Mostrar tela Personal");
	myresponse.sendRedirect("/page.jsp?id=783&invalid=account");
}




%>


<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>