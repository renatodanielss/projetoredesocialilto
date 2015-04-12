<%@page import="com.iliketo.dao.IliketoDAO"%>
<%@page import="com.iliketo.util.Str"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>


<%

System.out.println("Log - " + "execute action post_participate_category");

cms.CMSLog(myrequest.getParameter("id"), "post", myrequest.getParameter("database"));

//passar database dbcollection no parametro, valida s_id_collection na sessao
if (myrequest.getParameter("database").equals("dbcollection") && !mysession.get(Str.S_ID_COLLECTION).equals("")) {
	
	String myIdCollection = mysession.get(Str.S_ID_COLLECTION);		//id collection da sessao
	String idCategory = myrequest.getParameter("id_category");		//recupera do parametro o id da categoria
	String nameCategory = myrequest.getParameter("name_category"); 	//recupera do parametro o nome da categoria
	String resultButton = "";
	boolean isParticipate = false;
	

	//valida id_category e name_category
	if(!idCategory.equals("") && !nameCategory.equals("")){
		
		//valida se colecao participa da categoria
		if(IliketoDAO.validateCollectionCategory(db, myIdCollection, idCategory)){
			resultButton = "Leave category";	//ja participa
			isParticipate = true;
		}else{
			resultButton = "Participate";		//nao participa
			isParticipate = false;
		}
		
		
		//verifica solicitacao ajax no momento onload do grupo para carregar informacao no botao para "Participate" ou "Leave category"
		if(myrequest.getParameter("ajaxButton").equals("load_button_isParticipate")){
			response.setContentType("text/html"); 			//set type conteudo resposta
			response.getWriter().write(resultButton);		//write conteudo para retornar resposta para o ajax
		}else{
			
			//valida se colecao participa da categoria
			if(isParticipate){
				//ja participa
				//Limpa os dados da categoria/grupo
				myrequest.setParameter("fk_category_id", "");	//seta parametro em branco
				myrequest.setParameter("name_category", "");	//seta parametro em branco
				System.out.println("Log - Coleção: '"+ mysession.get("s_collection") +"' saiu da categoria/grupo: " + nameCategory);
				resultButton = "Participate";		//remove categoria - nao participa
				
			}else{
				//nao participa
				//Atualizar dados da categoria na database dbcollection
				myrequest.setParameter("fk_category_id", idCategory);
				myrequest.setParameter("name_category", nameCategory);
				System.out.println("Log - Coleção: '"+ mysession.get("s_collection") +"' entrou para a categoria/grupo: " + nameCategory);
				resultButton = "Leave category";	//seta nova categoria - ja participa
			}
			
			String idReal = IliketoDAO.getValueOfDatabase(db, "id", "dbcollection", "id_collection", myIdCollection);//recupera id real do Asbru
			myrequest.setParameter("id", idReal);	//Asbru usa referencia id real para update
			
			//chama método update
			UCmaintainDataILiketo maintainData = new UCmaintainDataILiketo(mytext);
			Data data = maintainData.doPost(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, "");
			
			
			//verifica se ja estiver no grupo, retorna resultado para o ajax
			if(myrequest.getParameter("ajaxButton").equals("execute_button_group")){
				response.setContentType("text/html"); 			//set type conteudo resposta
				response.getWriter().write(resultButton);		//write conteudo para retornar resposta para o ajax
			}else{
				%>
				<!-- jsp responsavel para redirecionar para o grupo -->
				<jsp:forward page="/redirect_info_group.jsp">
					<jsp:param value="<%=idCategory%>" name="id_category"/>
				</jsp:forward>
				<%
			}
		}
			
	}else{
		System.out.println("Log - Error não encontrado id_category ou name_category no parametro");
	}
}else{
	System.out.println("Log - Error não encontrado database dbcollection ou S_ID_COLLECTION da colecao do usuario na sessao");
}


%>

<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
