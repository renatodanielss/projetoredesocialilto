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
String idDeleteItem = myrequest.getParameter("id"); //recupera do parametro o id do conteudo para deletar


//Deleta o item da dbcollectionitem usando a coluna id da tabela
UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext); 
maintainData.doDeleteItem(mysession, db, idDeleteItem);


System.out.println("Log - " + "execute delete_post_item - id do item deletado = " + idDeleteItem);
System.out.println("Log - " + "Mostrar tela Profile my Collection");
myresponse.sendRedirect("/page.jsp?id=505");	//redireciona para Profile my Collection


%>


<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>