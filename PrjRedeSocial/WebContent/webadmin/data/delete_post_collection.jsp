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

String nameDatabase = myrequest.getParameter("database"); //recupera do parametro o id do conteudo para deletar
String idDeleteColecao = myrequest.getParameter("id"); //recupera do parametro o id do conteudo para deletar

//Deleta uma coleção da dbcollection usando a coluna id da tabela
UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext); 
Data data = maintainData.doDeleteCollectionAndItens(mysession, myrequest, myresponse, myconfig, db, nameDatabase, "id", idDeleteColecao);


//Detela todos os itens da dbcollectionitem usando a chave estrangeira fk_collection_id
String nameCol = "fk_collection_id";
data = maintainData.doDeleteCollectionAndItens(mysession, myrequest, myresponse, myconfig, db, "dbcollectionitem", nameCol, idDeleteColecao);


System.out.println("Log - " + "execute delete_post_collection - id da coleção deletada = " + idDeleteColecao);
System.out.println("Log - " + "Mostrar tela Minhas Coleções");
myresponse.sendRedirect("/page.jsp?id=48");	//redireciona para Minhas coleções


%>


<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>