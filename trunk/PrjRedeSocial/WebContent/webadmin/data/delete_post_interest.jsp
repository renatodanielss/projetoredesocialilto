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
String idDeleteInterest = myrequest.getParameter("id"); //recupera do parametro o id do conteudo para deletar


//Deleta uma coleção da dbinterest e todos itens desta coleção
UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext); 
maintainData.doDeleteInterest(mysession, db, idDeleteInterest);


System.out.println("Log - " + "execute delete_post_interest - id do interesse deletado = " + idDeleteInterest);
System.out.println("Log - " + "Mostrar tela  Meus Interesses");
myresponse.sendRedirect("/page.jsp?id=698");	//redireciona para meus interesses


%>


<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>