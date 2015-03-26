<%@page import="com.iliketo.dao.IliketoDAO"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="com.iliketo.util.*" %>

<%

System.out.println("Log - " + "Mostrar tela Grupo da categoria");

String idCollection = mysession.get("s_id_collection"); //recupera o id da coleção da sessão
String idCategory = myrequest.getParameter("id_category"); //recupera o id da categoria do parametro

if(idCollection != null && !idCollection.equals("") && idCategory != null & !idCategory.equals("")){
	
	//recupera id do grupo e forum da categoria para setar os valores na sessão e reuperar na pagina html
	String idGroup = IliketoDAO.getValueOfDatabase(db, "id_group", "dbgroup", "fk_category_id", idCategory);
	String idForum = IliketoDAO.getValueOfDatabase(db, "id_forum", "dbforum", "fk_group_id", idGroup);
	
	mysession.set("s_id_category", idCategory);
	mysession.set("s_id_group", idGroup);
	mysession.set("s_id_forum", idForum);
	
	myresponse.sendRedirect("/page.jsp?id=623&group=" + idGroup); //redireciona para o grupo da categoria
	
}else{
	
	//não existe id da coleção na sessão ou não foi passado id da categoria no parametro
	System.out.println("Log - " + "Não achou id da coleção ou não existe parametro do id da categoria");
	myresponse.sendRedirect("/page.jsp?id=160");	//redireciona para home
	
}

%>


<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
