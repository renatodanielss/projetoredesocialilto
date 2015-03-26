<%@page import="com.iliketo.dao.IliketoDAO"%>
<%@page import="com.iliketo.util.Str"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>


<%

System.out.println("Log - " + "execute action post_category_group - Criar nova Categoria/Grupo");

cms.CMSLog(myrequest.getParameter("id"), "post", myrequest.getParameter("database"));

if (! myrequest.getParameter("database").equals("")) {
		
	//valida categoria existente		
	if(IliketoDAO.readRecordExistsDatabase(db, "dbcategory", "name_category", myrequest.getParameter("name_category"))){
		
		//se já existir uma categoria, mostra na tela a categoria existente
		String msg = "Category already exists, choose category below or other name!";			
		String nameCategory = myrequest.getParameter("name_category");
		
		//reponse mesma pagina com parametros para recuperar na pagina o erro e a lista de categoria existente
		myresponse.sendRedirect("/page.jsp?id=601&error=" + msg + "&category=" + nameCategory);											
		
	}else{
	
		if(myrequest.getParameter("database").equals("dbcategory")){
			
			UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
			Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_category");
			
			
			myrequest.setParameter("database", "dbgroup"); //muda database para criar registro na dbgroup
			String idCreateCategory = data.getId();	//id criado da categoria
			myrequest.setParameter("fk_category_id", idCreateCategory); //seta chave estrangeira para criar novo grupo
			myrequest.setParameter("name_group", myrequest.getParameter("name_category")); //seta nome da categoria no name do grupo, os dois terão o mesmo nome
			data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_group");
			
			
			myrequest.setParameter("database", "dbforum"); //muda database para criar registro na dbforum
			String idCreateGroup = data.getId(); //id criado da grupo		
			myrequest.setParameter("fk_group_id", idCreateGroup); //seta chave estrangeira para criar novo forum
			data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_forum");

			//Category, Group e forum criados
			System.out.println("Log - Create Category/Group - name: " + myrequest.getParameter("name_category"));
			
			
			String nameCategory = myrequest.getParameter("name_category");
			String post = "/post_participate_category.jsp?database=dbcollection&id=" + mysession.get("s_id_collection"); //recupera o id da coleção na sessão
			
			%>
			<!-- request para chamar jsp para atualizar o membro participante que criou a categoria/grupo -->
			<jsp:forward page="<%=post%>">
				<jsp:param value="<%=idCreateCategory%>" name="id_category"/>
				<jsp:param value="<%=nameCategory%>" name="name_category"/>
			</jsp:forward>
			<%
		}
	}
}


%>


<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
