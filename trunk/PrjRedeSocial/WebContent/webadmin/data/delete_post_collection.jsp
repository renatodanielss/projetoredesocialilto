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
String idDeleteColecao = myrequest.getParameter("id"); //recupera do parametro o id do conteudo para deletar


//Deleta uma cole��o da dbcollection e todos itens desta cole��o
UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext); 
maintainData.doDeleteCollection(mysession, db, idDeleteColecao);


System.out.println("Log - " + "execute delete_post_collection - id da cole��o deletada = " + idDeleteColecao);
System.out.println("Log - " + "Mostrar tela Minhas Cole��es");
myresponse.sendRedirect("/page.jsp?id=48");	//redireciona para Minhas cole��es


%>


<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>