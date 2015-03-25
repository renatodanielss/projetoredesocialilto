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
			
			//Cria um UCbrowseWebsite
			Page mypage = null;
			UCbrowseWebsite browseWebsite = new UCbrowseWebsite(new Text());
			mypage = browseWebsite.getPage(servletcontext, mysession, myrequest, myresponse, myconfig, db, website);
			
			//coloca os valores do campo do form novamente na page para não perder os dados que o usuário digitou
			mypage.parse_output_register_iliketo(msg, request);
			website.get(myrequest, db, myrequest.getServerName(), myrequest.getHeader("Accept-Language"), "default_doctype");			
			
		}else{
		
			UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
			Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_category");
			
			
			String idCreateCategory = data.getId();	//id criado da categoria
			myrequest.setParameter("fk_category_id", idCreateCategory); //seta chave estrangeira para criar novo grupo
			myrequest.setParameter("name_group", myrequest.getParameter("name_category")); //seta nome da categoria no name do grupo, os dois terão o mesmo nome
			data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_group");
			
			
			String idCreateGroup = data.getId(); //id criado da grupo		
			myrequest.setParameter("fk_group_id", idCreateGroup); //seta chave estrangeira para criar novo forum
			data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "post_forum");
			
			//set session o id da categoria
			mysession.set("id_category", idCreateCategory);
			
			//pagina do grupo da categoria
			myresponse.sendRedirect("/page.jsp?id=623&id_category=" 
						+ idCreateCategory + "&name_category=" + myrequest.getParameter("name_category"));
			
			System.out.println("Log - Create Category/Group - name: " + myrequest.getParameter("name_category"));
			System.out.println("Log - Mostrar tela do Grupo da categoria");
		}
}


%>


<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
