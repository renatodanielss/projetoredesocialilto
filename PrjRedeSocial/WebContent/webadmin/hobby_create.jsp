<%@ include file="config.jsp" %>
<%@ page import="HardCore.UCaccessAdministration" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="com.iliketo.dao.*" %>
<%@ page import="com.iliketo.model.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.iliketo.util.*" %>
<%

	//valida usuario adm asbru
	UCaccessAdministration.doIndex(mytext, "", mysession, myrequest, myresponse, myconfig, db);

	//JSP cria registro da categoria/grupo de um hobby no banco de dados
	final Logger log = Logger.getLogger(request.getRequestURL().toString());
	log.info(request.getRequestURL());
	
	String cat = request.getParameter("name_category").trim();		//nome categoria
	String country = request.getParameter("country").trim();		//pais categoria/grupo do hobby
	
	//valida se existe categoria
	if(cat.length() >= 2){
		if(!IliketoDAO.readRecordExistsDatabase(db, "dbcategory", "name_category", cat)){
			//Cria nova categoria/grupo
			CategoryDAO categoryDAO = new CategoryDAO(db, request);
			Category category = new Category();
			category.setNameCategory(cat);
			category.setHobby("yes");
			category.setCountry(country);
			String idCreated = categoryDAO.create(category);
			
			//cria novo forum da categoria/grupo
			ForumDAO forumDAO = new ForumDAO(db, request);
			Forum forum = new Forum();
			forum.setIdCategory(idCreated);
			forum.setNameCategory(cat);	
			forumDAO.create(forum);

			log.info("Nova categoria/grupo do hobby criado - name: " + cat);
			response.sendRedirect("/webadmin/hobby_add.jsp?info=Categoria do hobby criada com sucesso! name: " + cat);
		}else{
			log.info("Nao criou categoria hobby, erro ou jah existe! - name: " + cat);	
			response.sendRedirect("/webadmin/hobby_add.jsp?info=Nao criou categoria do hobby, erro ou jah existe! - name: " + cat);
		}
	}else{
		log.info("Nome tem que ser maior que 2 caracteres! - name: " + cat);	
		response.sendRedirect("/webadmin/hobby_add.jsp?info=Nome tem que ser maior que 2 caracteres! - name: " + cat);
	}
	
	
	
%>

<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>